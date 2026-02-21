package net.mysticcreations.lib.config.toml;

import net.mysticcreations.lib.MysticLib;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.List;

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

            String whiteSpaceRemoved = line.stripLeading();

            if (line.isEmpty()) {
                continue;
            }

            // Scheduled for when I finally learn regex.
        }

    }

}
