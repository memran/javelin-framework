package io.javelin.cli;

public final class CliException extends RuntimeException {
    private final String fix;

    public CliException(String message) {
        this(message, null);
    }

    public CliException(String message, String fix) {
        super(message);
        this.fix = fix;
    }

    public String fix() {
        return fix;
    }
}
