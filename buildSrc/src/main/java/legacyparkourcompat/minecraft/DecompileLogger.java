package legacyparkourcompat.minecraft;

import java.util.regex.Matcher;

interface DecompileLogger {
    void lifecycle(String message, Object... args);

    void warn(String message, Object... args);

    void info(String message, Object... args);

    void error(String message, Object... args);

    static String format(String message, Object... args) {
        if (args == null || args.length == 0) {
            return message;
        }
        String result = message;
        for (Object arg : args) {
            result = result.replaceFirst("\\{\\}", Matcher.quoteReplacement(String.valueOf(arg)));
        }
        return result;
    }
}
