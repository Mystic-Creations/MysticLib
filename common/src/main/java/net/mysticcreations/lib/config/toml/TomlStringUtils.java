package net.mysticcreations.lib.config.toml;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TomlStringUtils {

    public static String escapeTomlString(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    public static String escapeTomlTripleDoubleString(String value) {
        return value.replace("\"\"\"", "\\\"\\\"\\\"");
    }

    public static boolean isLiteralStringValid(String literal) {
        return !literal.contains("'") && !literal.contains("\n") && !literal.contains("\r");
    }

    public static boolean isNoQuoteStringValid(String noQuote) {
        Pattern pattern = Pattern.compile("[A-Za-z0-9_-]");
        Matcher matcher = pattern.matcher(noQuote);
        return matcher.matches();
    }
}
