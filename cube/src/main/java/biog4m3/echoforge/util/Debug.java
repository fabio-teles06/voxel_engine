package biog4m3.echoforge.util;

public class Debug {
    public enum Level {
        INFO, WARN, ERROR
    }

    private static boolean enabled = true;

    public static void setEnabled(boolean value) {
        enabled = value;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void log(String message) {
        log(Level.INFO, message);
    }

    public static void warn(String message) {
        log(Level.WARN, message);
    }

    public static void error(String message) {
        log(Level.ERROR, message);
    }

    public static void log(Level level, String message) {
        if (!enabled)
            return;

        String prefix;
        String color;

        switch (level) {
            case WARN:
                prefix = "[WARN]";
                color = "\u001B[33m";
                break;
            case ERROR:
                prefix = "[ERROR]";
                color = "\u001B[31m";
                break;
            case INFO:
            default:
                prefix = "[INFO]";
                color = "\u001B[36m";
                break;
        }

        String reset = "\u001B[0m";
        System.out.println(color + prefix + " " + message + reset);
    }
}
