package io.javelin.http.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Matches literal IP addresses against an explicit allow-list of IPs and CIDR ranges.
 * Hostnames and wildcards are never resolved or trusted.
 */
final class ProxyAddressMatcher {
    private static final Pattern IPV4 = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
    private static final Pattern IPV6_LITERAL = Pattern.compile("^[0-9a-fA-F:.]+$");

    private final List<ProxyNetwork> networks;

    ProxyAddressMatcher(List<String> entries) {
        List<ProxyNetwork> parsed = new ArrayList<>();
        if (entries != null) {
            for (String entry : entries) {
                if (entry == null || entry.trim().isEmpty()) {
                    continue;
                }
                parsed.add(parseNetwork(entry.trim()));
            }
        }
        this.networks = List.copyOf(parsed);
    }

    boolean matches(String address) {
        byte[] candidate = parseLiteral(address);
        if (candidate == null) {
            return false;
        }
        for (ProxyNetwork network : networks) {
            if (matches(candidate, network)) {
                return true;
            }
        }
        return false;
    }

    /** Strips header quoting, IPv6 brackets and zone ids from an address token. */
    static String normalize(String value) {
        if (value == null) {
            return "";
        }
        String literal = value.trim();
        if (literal.length() >= 2 && literal.startsWith("\"") && literal.endsWith("\"")) {
            literal = literal.substring(1, literal.length() - 1);
        }
        if (literal.length() > 2 && literal.startsWith("[") && literal.endsWith("]")) {
            literal = literal.substring(1, literal.length() - 1);
        }
        int zone = literal.indexOf('%');
        if (zone >= 0) {
            literal = literal.substring(0, zone);
        }
        return literal;
    }

    /** Returns address bytes for an IP literal, or {@code null} for anything else. */
    static byte[] parseLiteral(String value) {
        String literal = normalize(value);
        if (literal.isEmpty()) {
            return null;
        }

        Matcher ipv4 = IPV4.matcher(literal);
        if (ipv4.matches()) {
            byte[] bytes = new byte[4];
            for (int group = 1; group <= 4; group++) {
                int octet = Integer.parseInt(ipv4.group(group));
                if (octet > 255) {
                    return null;
                }
                bytes[group - 1] = (byte) octet;
            }
            return bytes;
        }
        if (literal.indexOf(':') >= 0 && IPV6_LITERAL.matcher(literal).matches()) {
            try {
                return InetAddress.getByName(literal).getAddress();
            } catch (UnknownHostException exception) {
                return null;
            }
        }
        return null;
    }

    private static ProxyNetwork parseNetwork(String entry) {
        if ("*".equals(entry)) {
            throw new IllegalArgumentException(
                    "Wildcard proxy trust is not allowed; configure explicit proxy IPs or CIDRs");
        }
        int slash = entry.indexOf('/');
        String addressText = slash < 0 ? entry : entry.substring(0, slash);
        byte[] address = parseLiteral(addressText);
        if (address == null) {
            throw new IllegalArgumentException("Invalid trusted proxy address: " + entry);
        }

        int prefixBits = address.length * 8;
        if (slash >= 0) {
            String prefix = entry.substring(slash + 1).trim();
            try {
                prefixBits = Integer.parseInt(prefix);
            } catch (NumberFormatException exception) {
                throw new IllegalArgumentException("Invalid trusted proxy CIDR prefix: " + entry, exception);
            }
            if (prefixBits < 0 || prefixBits > address.length * 8) {
                throw new IllegalArgumentException("Invalid trusted proxy CIDR prefix: " + entry);
            }
        }
        return new ProxyNetwork(address, prefixBits);
    }

    private static boolean matches(byte[] candidate, ProxyNetwork network) {
        if (candidate.length != network.address().length) {
            return false;
        }
        int wholeBytes = network.prefixBits() / 8;
        for (int index = 0; index < wholeBytes; index++) {
            if (candidate[index] != network.address()[index]) {
                return false;
            }
        }
        int remainingBits = network.prefixBits() % 8;
        if (remainingBits > 0) {
            int mask = 0xFF << (8 - remainingBits);
            if ((candidate[wholeBytes] & mask) != (network.address()[wholeBytes] & mask)) {
                return false;
            }
        }
        return true;
    }

    private record ProxyNetwork(byte[] address, int prefixBits) {
    }
}
