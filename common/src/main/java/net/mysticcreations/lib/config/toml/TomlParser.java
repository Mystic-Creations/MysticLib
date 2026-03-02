package net.mysticcreations.lib.config.toml;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TomlParser {

    private List<TomlElement<?>> elements;

    public TomlParser(File tomlFile) throws TomlParsingException{

        try(FileReader reader = new FileReader (tomlFile)) {
            String everything = IOUtils.toString(reader);

            parseToml(everything);
            // do something with everything string
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TomlParser(String rawFileContents) throws TomlParsingException {
        parseToml(rawFileContents);
    }

    private void parseToml(String tomlContent) throws TomlParsingException {

        int index = 0;
        while (index < tomlContent.length()) {

            if (Character.isWhitespace(tomlContent.codePointAt(index))) {
                index += 1;
            }

            if (tomlContent.codePointAt(index) == '\n') {
                index += 1;
            }

            if (tomlContent.codePointAt(index) == '[') {
                int whitespace = getTrailingWhitespace(tomlContent, index);
                index += whitespace;
                if (index >= tomlContent.length()) {
                    throw new TomlParsingException("");
                }
                if (tomlContent.codePointAt(index) == '"' | tomlContent.codePointAt(index) == '\'') {
                }
                // Array Table
                if (tomlContent.codePointAt(index) == '[') {

                }
            }

        }

    }

    public int getTrailingWhitespace(String chars, int index) {
        if (chars.isEmpty()) {
            return 0;
        }
        if (index >= chars.length()) {
            return 0;
        }
        if (index < 0) {
            return 0;
        }
        int whitespace = 0;
        for (int i = index; i < chars.length(); i++) {
            if (Character.isWhitespace(chars.codePointAt(i))) {
                whitespace += 1;
            } else {
                break;
            }
        }
        return whitespace;
    }

    private StringResult getDotSeparatedStringASCIElement(String chars, int index) throws TomlParsingException {

        TomlDottedElementName element = new TomlDottedElementName();

        String buildingString = "";
        int stringType = -1;
        boolean trailingDot = false;

        for (int i = index; index < chars.length(); i++) {

            int character = chars.codePointAt(i);

            if (Character.isWhitespace(character)) {
                continue;
            }

            if (character == '"') {
                StringResult result = getDoubleQuotedStringElement(chars, i);
                element.addName(result.character, TomlStringType.DOUBLE);
            }

            Pattern pattern = Pattern.compile("[A-Za-z0-9_-]");
            Matcher matcher = pattern.matcher("");

            if (matcher.matches()) {

            }

            if (character == '.') {
                if (trailingDot) {
                    throw new TomlParsingException("Two dots without content in between");
                }
                trailingDot = true;
            }

            if (character == '=') {
                // break normally
                if (trailingDot) {
                    throw new TomlParsingException("Dot trailing without content");
                }

            }

        }

        return new StringResult(chars, index);
    }

    private StringResult getDoubleQuotedStringElement(String chars, int index) throws TomlParsingException {
        return getDoubleQuotedStringElement(chars, index, false);
    }

    private StringResult getDoubleQuotedStringElementWithTriples(String chars, int index) throws TomlParsingException {
        return getDoubleQuotedStringElement(chars, index, true);
    }

    private StringResult getDoubleQuotedStringElement(String chars, int index, boolean allowTriples) throws TomlParsingException {
        if (chars.isEmpty()) {
            return null;
        }
        if (index >= chars.length()) {
            return null;
        }
        if (index < 0) {
            throw new TomlParsingException("Index on getDoubleQuotedStingElement negative");
        }
        if (index < chars.length() - 2 && allowTriples) {
            int firstQuote = chars.codePointAt(index);
            int secondQuote = chars.codePointAt(index+1);
            int thirdQuote = chars.codePointAt(index+2);

            if (firstQuote == '"' | secondQuote == '"' | thirdQuote == '"') {
                index += 3;
                return getTripleDoubleQuotedStringElement(chars, index);
            }
        }
        StringBuilder string = new StringBuilder();
        for (int i = index + 1; i < chars.length(); i++) {

            if (chars.codePointAt(i) == '\\') {
                StringResult escape = getEscape(chars, i);
                i += escape.length - 1;
                string.append(escape.character);
                continue;
            }

            if (chars.codePointAt(i) == '\n') {
                throw new TomlParsingException("String not finished before line break");
            }

            if (chars.codePointAt(i) == '\"') {
                break;
            }

            string.append(chars.codePointAt(i));
        }
        return new StringResult(string.toString(), index + 1);
    }

    public StringResult getTripleDoubleQuotedStringElement(String chars, int index) {

        if (index >= chars.length() - 2) {
            return null;
        }

        int firstQuote = chars.codePointAt(index);
        int secondQuote = chars.codePointAt(index+1);
        int thirdQuote = chars.codePointAt(index+2);

        if (firstQuote == '"' | secondQuote == '"' | thirdQuote == '"') {
            index += 3;
        }
        return new StringResult("", 0);
    }

    private record StringResult(String character, int length) {}

    private StringResult getEscape(String chars, int index) throws TomlParsingException {
        if (index < 0) {
            throw new TomlParsingException("Index of escape character check negative");
        }
        switch (chars.codePointAt(index + 1)){
            case 'n':
                return new StringResult("\n", 2);
            case 'b':
                return new StringResult("\b", 2);
            case 't':
                return new StringResult("\t", 2);
            case 'f':
                return new StringResult("\f", 2);
            case 'r':
                return new StringResult("\r", 2);
            case 'e':
                return new StringResult("\u001B", 2);
            case '"':
                return new StringResult("\"", 2);
            case '\\':
                return new StringResult("\\", 2);
            case 'x':
                if (index >= chars.length() - 3) {
                    throw new TomlParsingException("Unicode escape character not finished");
                }
                // parse the Unicode
                String hex2 = chars.substring(index + 2, index + 5);
                try {
                    int codePoint = Integer.parseInt(hex2, 16);
                    // EscapeResult should accept int codepoint, not char
                    return new StringResult(Character.toString(codePoint), 4);
                } catch (NumberFormatException e) {
                    throw new TomlParsingException("Invalid Unicode escape: " + hex2);
                }
            case 'u':
                if (index >= chars.length() - 5) {
                    throw new TomlParsingException("Unicode escape character not finished");
                }
                // parse the Unicode
                String hex4 = chars.substring(index + 2, index + 7);
                try {
                    int codePoint = Integer.parseInt(hex4, 16);
                    // EscapeResult should accept int codepoint, not char
                    return new StringResult(Character.toString(codePoint), 6);
                } catch (NumberFormatException e) {
                    throw new TomlParsingException("Invalid Unicode escape: " + hex4);
                }
            case 'U':
                if (index >= chars.length() - 9) {
                    throw new TomlParsingException("Unicode escape character not finished");
                }
                // parse the Unicode
                String hex8 = chars.substring(index + 2, index + 11);
                try {
                    int codePoint = Integer.parseInt(hex8, 16);
                    // EscapeResult should accept int codepoint, not char
                    return new StringResult(Character.toString(codePoint), 10);
                } catch (NumberFormatException e) {
                    throw new TomlParsingException("Invalid Unicode escape: " + hex8);
                }
            default:
                throw new TomlParsingException("Invalid character escape: " + chars.codePointAt(index + 1));
        }
    }

}
