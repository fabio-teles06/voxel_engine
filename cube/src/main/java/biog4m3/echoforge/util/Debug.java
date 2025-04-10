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

    public static void log(String tag, String message) {
        log(Level.INFO, tag, message);
    }

    public static void warn(String tag, String message) {
        log(Level.WARN, tag, message);
    }

    public static void error(String tag, String message) {
        log(Level.ERROR, tag, message);
    }

    public static void log(Level level, String tag, String message) {
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
        String formattedMessage = String.format("%s%s %s: %s%s", color, prefix, tag, message, reset);
        System.out.println(formattedMessage);
    }
}
