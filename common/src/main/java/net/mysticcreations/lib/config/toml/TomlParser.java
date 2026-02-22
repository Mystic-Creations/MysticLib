package net.mysticcreations.lib.config.toml;

import net.mysticcreations.lib.MysticLib;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TomlParser {

    private List<TomlElement<?>> elements;

    public TomlParser(File tomlFile) {

        try(FileReader reader = new FileReader (tomlFile)) {
            String everything = IOUtils.toString(reader);

            parseToml(everything);
            // do something with everything string
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public TomlParser(String rawFileContents) {
        parseToml(rawFileContents);
    }

    private void parseToml(String rawContents) {

        String[] lines = rawContents.split("\\n");

        for (String line : lines) {

            if (line.isEmpty()) {
                continue;
            }

            Pattern arrayTablePattern = Pattern.compile("^\\s*\\[\\[(.+?)\\]\\]\\s*(?:#.*)?$");
            Matcher arrayTableMatcher = arrayTablePattern.matcher(line);

            if (arrayTableMatcher.matches()) {

            }

            Pattern tablePattern = Pattern.compile("^\\s*\\[([^\\[\\]]+?)\\]\\s*(?:#.*)?$");
            Matcher tableMatcher = tablePattern.matcher(line);

            if (tableMatcher.matches()) {

                String tableName = tableMatcher.group(1);

                //MysticLib.LOGGER.error();

                
            }

            Pattern stringValuePattern = Pattern.compile("^\\s*(?:(?:\".*?\"|'.*?')|[A-Za-z0-9_-]+)\\s*=\\s*(?:\".*?\"|'.*?')\\s*(?:#.*)?$");
            Matcher stringValueMatcher = stringValuePattern.matcher(line);
        }

    }

}
