package io.javelin.mail;

import io.javelin.core.Log;

/** Logs only submission metadata; never bodies, credentials or attachment bytes. */
public final class MailSubmissionLogger {
    public void submitted(MailMessage message, String transport) {
        String first = message.to().isEmpty() ? "" : mask(message.to().get(0));
        Log.info("Mail submitted: to=" + first
                + " recipients=" + message.allRecipients().size()
                + " transport=" + transport
                + " attachments=" + message.attachments().size());
    }

    public static String mask(String address) {
        if (address == null || address.isBlank()) {
            return "";
        }
        int at = address.indexOf('@');
        if (at <= 0) {
            return address.charAt(0) + "***";
        }
        return address.charAt(0) + "***" + address.substring(at);
    }
}
