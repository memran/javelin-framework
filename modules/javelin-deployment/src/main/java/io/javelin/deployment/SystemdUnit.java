package io.javelin.deployment;

/** Renders a systemd unit for an executable JAR deployment. */
public record SystemdUnit(String appName, String user, String workingDirectory,
                          String jarPath, String javaHome, String envFile) {
    public String render() {
        String java = javaHome == null || javaHome.isBlank()
                ? "java"
                : javaHome + "/bin/java";
        StringBuilder unit = new StringBuilder()
                .append("[Unit]\n")
                .append("Description=").append(appName).append("\n")
                .append("After=network.target\n\n")
                .append("[Service]\n")
                .append("Type=simple\n")
                .append("User=").append(user).append("\n")
                .append("WorkingDirectory=").append(workingDirectory).append("\n")
                .append("EnvironmentFile=-").append(envFile).append("\n")
                .append("ExecStart=").append(java).append(" -jar ").append(jarPath).append(" serve\n")
                .append("Restart=on-failure\n")
                .append("RestartSec=5\n")
                .append("TimeoutStopSec=15\n")
                .append("NoNewPrivileges=true\n")
                .append("PrivateTmp=true\n\n")
                .append("[Install]\n")
                .append("WantedBy=multi-user.target\n");
        return unit.toString();
    }
}
