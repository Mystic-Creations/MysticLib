package net.mysticcreations.lib.config.toml;

import net.mysticcreations.lib.config.fields.StringField;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TomlParser {

    private final List<TomlElement<?>> elements = new ArrayList<>();

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
        boolean comment = false;
        while (index < tomlContent.length()) {

            if (tomlContent.codePointAt(index) == '\n') {
                index += 1;
                comment = false;
                continue;
            }

            if (comment) {
                index += 1;
                continue;
            }

            if (tomlContent.codePointAt(index) == '#') {
                index += 1;
                comment = true;
                continue;
            }

            if (Character.isWhitespace(tomlContent.codePointAt(index))) {
                index += 1;
                continue;
            }



            if (tomlContent.codePointAt(index) == '[') {
                index += 1;
                index += getTrailingWhitespace(tomlContent, index);
                if (index >= tomlContent.length()) {
                    throw new TomlParsingException("Array opened. but no name or closure.");
                }
                if (tomlContent.codePointAt(index) == '"' || TomlStringUtils.isCharInNoQuote(tomlContent.codePointAt(index))) {
                    TomlDottedElementResult result = getDotSeparatedStringASCIElement(tomlContent, index);
                    index += result.length;

                    if (index >= tomlContent.length()) throw new TomlParsingException("Array not closed. Random EOF");

                        // ignore whitespace again
                    index += getTrailingWhitespace(tomlContent, index);

                    if (index >= tomlContent.length()) throw new TomlParsingException("Array not closed. Random EOF");

                    // get the end bracket
                    if (tomlContent.codePointAt(index) == ']') {
                        index += 1;
                        // add the element
                        TomlTable table = new TomlTable(result.name);
                        elements.add(table);
                    } else {
                        throw new TomlParsingException("Array not closed.");
                    }

                    index += getTrailingWhitespace(tomlContent, index);

                }
                // Array Table
                if (tomlContent.codePointAt(index) == '[') {
                    index += 1;

                    TomlDottedElementResult result = getDotSeparatedStringASCIElement(tomlContent, index);
                    index += result.length;

                    // ignore whitespace again
                    index += getTrailingWhitespace(tomlContent, index);

                    // get the end bracket
                    if (tomlContent.codePointAt(index) == ']' && tomlContent.codePointAt(index+1) == ']') {
                        index += 2;
                        // add the element
                        TomlArrayTable table = new TomlArrayTable(result.name);
                        elements.add(table);
                    }
                    index += getTrailingWhitespace(tomlContent, index);

                }
                continue;
            }

            char c = tomlContent.charAt(index);
            if (TomlStringUtils.isCharInNoQuote(c) || c == '"') {
                TomlDottedElementResult nameResult = getDotSeparatedStringASCIElement(tomlContent, index);
                index += nameResult.length;

                index += 1;
                index += getTrailingWhitespace(tomlContent, index);

                // equals sign
                if (tomlContent.codePointAt(index) == '=') {

                    index += 1;
                    index += getTrailingWhitespace(tomlContent, index);

                    if (tomlContent.startsWith("true", index)) {
                        TomlBooleanField field = new TomlBooleanField(nameResult.name, true);
                        elements.add(field);
                    }
                    if (tomlContent.startsWith("false", index)) {
                        TomlBooleanField field = new TomlBooleanField(nameResult.name, false);
                        elements.add(field);
                    }

                    if (tomlContent.codePointAt(index) == '"') {
                        StringFieldValueResult stringValueResult = getDoubleQuotedStringElementWithTriples(tomlContent, index);
                        TomlStringField field = new TomlStringField(nameResult.name, stringValueResult.character, stringValueResult.type);
                        elements.add(field);
                    }
                    if (tomlContent.codePointAt(index) == '\'') {
                        StringResult stringValueResult = getLiteralStringElement(tomlContent, index);
                        TomlStringField field = new TomlStringField(nameResult.name, stringValueResult.character, TomlStringType.LITERAL);
                        elements.add(field);
                    }

                }
                index += getTrailingWhitespace(tomlContent, index);

            }

        }

    }

    private int getTrailingWhitespace(String chars, int index) {
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

    public record TomlDottedElementResult(TomlDottedElementName name, int length) {}

    private TomlDottedElementResult getDotSeparatedStringASCIElement(String chars, int index) throws TomlParsingException {

        TomlDottedElementName element = new TomlDottedElementName();

        int endIndex = -1;
        boolean trailingDot = false;
        boolean shouldDotOrEnd = false;
        int i = index;

        while (index < chars.length()) {

            int character = chars.codePointAt(i);

            if (Character.isWhitespace(character)) {
                i++;
                continue;
            }

            if (character == '"') {
                StringFieldValueResult result = getDoubleQuotedStringElement(chars, i);
                element.addName(result.character, result.type);
                i += result.length();
                shouldDotOrEnd = true;
                continue;
            }

            if (TomlStringUtils.isCharInNoQuote(chars.codePointAt(i))) {

                StringResult result = getNoQuotedStringElement(chars, i);

                if (result == null) {
                    throw new TomlParsingException("todo: give message here");
                }

                element.addName(result.character, TomlStringType.NO_QUOTE);
                index += result.length;
                shouldDotOrEnd = true;
                continue;
            }

            if (character == '.') {
                if (!shouldDotOrEnd) {
                    throw new TomlParsingException("Should expect dot here...");
                }
                if (trailingDot) {
                    throw new TomlParsingException("Two dots without content in between");
                }
                shouldDotOrEnd = false;
                trailingDot = true;
                index += 1;
                continue;
            }

            if (character == '=') {
                if (!shouldDotOrEnd) {
                    throw new TomlParsingException("Should expect equals sign here...");
                }
                shouldDotOrEnd = false;
                // break normally
                if (trailingDot) {
                    throw new TomlParsingException("Dot trailing without content");
                }
                endIndex = i;
                break;
            }

        }

        return new TomlDottedElementResult(element, endIndex - index);
    }

    private StringFieldValueResult getDoubleQuotedStringElement(String chars, int index) throws TomlParsingException {
        return getDoubleQuotedStringElement(chars, index, false);
    }

    private record StringFieldValueResult(String character, int length, TomlStringType type){
    }

    private StringFieldValueResult getDoubleQuotedStringElementWithTriples(String chars, int index) throws TomlParsingException {
        return getDoubleQuotedStringElement(chars, index, true);
    }

    private StringFieldValueResult getDoubleQuotedStringElement(String chars, int index, boolean allowTriples) throws TomlParsingException {
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

            if (firstQuote == '"' && secondQuote == '"' && thirdQuote == '"') {
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
        return new StringFieldValueResult(string.toString(), index + 1, TomlStringType.DOUBLE);
    }

    private StringResult getLiteralStringElement(String chars, int index) throws TomlParsingException {
        if (chars.isEmpty()) {
            return null;
        }
        if (index >= chars.length()) {
            return null;
        }
        if (index < 0) {
            throw new TomlParsingException("Index on getLiteralStringElement negative");
        }
        StringBuilder string = new StringBuilder();
        for (int i = index + 1; i < chars.length(); i++) {
            if (chars.codePointAt(i) == '\n') {
                throw new TomlParsingException("String not finished before line break");
            }

            if (chars.codePointAt(i) == '\'') {
                break;
            }

            string.append(chars.codePointAt(i));
        }
        return new StringResult(string.toString(), index + 1);
    }

    private StringFieldValueResult getTripleDoubleQuotedStringElement(String chars, int index) throws TomlParsingException {

        // todo finish this
        if (index >= chars.length() - 2) {
            throw new TomlParsingException("Triple quote not detected");
        }

        int firstQuote = chars.codePointAt(index);
        int secondQuote = chars.codePointAt(index+1);
        int thirdQuote = chars.codePointAt(index+2);

        if (firstQuote == '"' && secondQuote == '"' && thirdQuote == '"') {
            index += 3;
        } else {
            return null;
        }

        int i = index;
        StringBuilder builder = new StringBuilder();

        while (i < chars.length()) {

            if (chars.codePointAt(i) == '"') {

                if (chars.codePointAt(index + 1) == '"' && chars.codePointAt(index + 2) == '"') {
                    index += 3;

                    int quoteCount = 3;
                    while (i + quoteCount < chars.length() && chars.codePointAt(index + quoteCount) == '"') {
                        quoteCount++;
                    }

                    String extraQuotes = "\"".repeat(quoteCount);
                    builder.append(extraQuotes);

                    break;
                }
            }
            builder.append(chars.codePointAt(i));
            index += 1;
        }

        return new StringFieldValueResult(builder.toString(), builder.length(), TomlStringType.TRIPLE_DOUBLE);
    }

    private StringResult getNoQuotedStringElement(String chars, int index) throws TomlParsingException {

        if (chars.isEmpty()) {
            return null;
        }
        if (index >= chars.length()) {
            return null;
        }
        if (index < 0) {
            throw new TomlParsingException("Index on getDoubleQuotedStingElement negative");
        }
        StringBuilder string = new StringBuilder();
        for (int i = index + 1; i < chars.length(); i++) {

            int character = chars.codePointAt(i);

            Pattern pattern = Pattern.compile("[A-Za-z0-9_-]");
            Matcher matcher = pattern.matcher(TomlStringUtils.getCharacterString(character));

            if (matcher.matches()) {

                string.append(TomlStringUtils.getCharacterString(i));

            } else {
                break;
            }

            string.append(chars.codePointAt(i));
        }
        return new StringResult(string.toString(), index + 1);

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

    public List<TomlElement<?>> getElements() {
        return elements;
    }

}