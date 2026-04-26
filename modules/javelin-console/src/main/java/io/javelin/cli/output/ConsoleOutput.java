package io.javelin.cli.output;

import java.io.PrintWriter;

public final class ConsoleOutput {
    private final PrintWriter out;
    private final boolean ansi;

    public ConsoleOutput() {
        this(new PrintWriter(System.out, true), System.console() != null);
    }

    public ConsoleOutput(PrintWriter out, boolean ansi) {
        this.out = out;
        this.ansi = ansi;
    }

    public void success(String message) {
        line(color("32", "✔ ") + message);
    }

    public void info(String message) {
        line(color("36", "ℹ ") + message);
    }

    public void warn(String message) {
        line(color("33", "! ") + message);
    }

    public void error(String message) {
        line(color("31", "✘ ") + message);
    }

    public void line(String message) {
        out.println(message);
    }

    private String color(String code, String text) {
        return ansi ? "\u001B[" + code + "m" + text + "\u001B[0m" : text;
    }
}
