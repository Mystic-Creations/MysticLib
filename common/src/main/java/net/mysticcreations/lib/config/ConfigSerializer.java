package net.mysticcreations.lib.config;

import com.github.groundbreakingmc.tomly.Tomly;
import com.github.groundbreakingmc.tomly.nodes.Node;
import com.github.groundbreakingmc.tomly.nodes.TomlDocument;
import com.github.groundbreakingmc.tomly.nodes.impl.*;
import com.github.groundbreakingmc.tomly.options.PreserveOptions;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.fields.ConfigCat;
import net.mysticcreations.lib.config.fields.ConfigField;
import net.mysticcreations.lib.config.toml.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class ConfigSerializer {

    private ConfigDefinition config;
    private File configFile;

    public ConfigSerializer(ConfigDefinition config, boolean subfolder) {

        this.config = config;

        String fileExtension = "";
        switch (config.configType) {
            case JSON:
                fileExtension = ".json";
            case TOML:
                fileExtension = ".toml";
        }
        if (subfolder) {
            this.configFile = new File("config/" + config.id.getNamespace() + "/" + config.id.getPath() + fileExtension);
        } else {
            this.configFile = new File("config/" + config.id.getNamespace() + "_" + config.id.getPath() + fileExtension);
        }

    }

    public void writeToConfigFile() {

        switch (config.getConfigType()) {
            case TOML -> {
                TomlBuilderOptions options = new TomlBuilderOptions()
                        .indentAmount(4)
                        .indentTables(true);

                TomlBuilder builder = new TomlBuilder(options);

                convertConfigListToToml(builder, config.items, "");

                try {
                    String toml = builder.build();

                    if (!configFile.exists()) {
                        configFile.getParentFile().mkdirs();
                        configFile.createNewFile();
                    }

                    FileWriter fw = new FileWriter(configFile);

                    fw.write(toml);

                    fw.close();

                } catch (TomlParsingException e) {
                    MysticLib.LOGGER.error("FAILED TO PARSE TOML: {}", Arrays.toString(e.getStackTrace()));
                } catch (IOException e) {
                    MysticLib.LOGGER.error("FAILED TO WRITE CONFIG FILE: {}", Arrays.toString(e.getStackTrace()));
                }
            }
            case JSON -> {
                // do stuff
            }
        }
    }

    public void convertConfigListToToml(TomlBuilder builder, List<ConfigItem> configList, String prefix) {
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {
                TomlTable table = new TomlTable(prefix + cat.catName);
                builder.addElement(table);
                convertConfigListToToml(builder, cat.items, prefix + cat.catName + ".");
            }

            if (item instanceof ConfigField<?> field) {

                if (field.type == String.class) {
                    TomlStringField element = new TomlStringField(prefix + field.fieldName,(String) field.value)
                            .setHeaderComments(field.headerComments)
                            .setInlineComment(field.inlineComment);
                    builder.addElement(element);
                }
                if (Integer.class.isAssignableFrom(field.type)) {
                    TomlIntegerField element = new TomlIntegerField(field.fieldName, (Integer) field.value)
                            .setHeaderComments(field.headerComments)
                            .setInlineComment(field.inlineComment);
                    builder.addElement(element);
                }
                if (Float.class.isAssignableFrom(field.type)) {
                    TomlFloatField element = new TomlFloatField(field.fieldName, (Float) field.value).setHeaderComments(field.headerComments)
                            .setInlineComment(field.inlineComment)
                            .setHeaderComments(field.headerComments);
                    builder.addElement(element);
                }
                if (field.type == Boolean.class) {
                    TomlBooleanField element = new TomlBooleanField(field.fieldName, (Boolean) field.value)
                            .setInlineComment(field.inlineComment)
                            .setHeaderComments(field.headerComments);
                    builder.addElement(element);
                }
            }
        }
    }

    public void readFromConfigFile() {

        switch (config.getConfigType()) {
            case TOML -> {

                if (!configFile.exists()) {
                    configFile.getParentFile().mkdirs();
                    try {
                        configFile.createNewFile();
                        FileWriter fw = new FileWriter(configFile);
                        fw.write("mysticlib = 1");
                        fw.close();
                    } catch (java.io.IOException e) {
                        MysticLib.LOGGER.error("Failed to create config file {}", configFile.toString());
                        throw new RuntimeException(e);
                    }
                }

                PreserveOptions parseOpts = PreserveOptions.builder()
                        .preserveHeaderComments(true)
                        .preserveInlineComments(true)
                        .preserveBlankLines(false)
                        .build();


                TomlDocument document = Tomly.parse(configFile.toPath().toAbsolutePath(), true, parseOpts);

                applyTomlValuesToConfig(config, document, config.items, "");
            }
            case JSON -> {
                // do stuff
            }
        }
    }

    public void applyTomlValuesToConfig(ConfigDefinition config, TomlDocument document, List<ConfigItem> configList, String prefix) {
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {

                TomlTable table = new TomlTable(prefix + cat.catName);

                applyTomlValuesToConfig(config, document, cat.items,prefix + cat.catName + ".");
            }
            if (item instanceof ConfigField<?> field) {

                if (field.type == String.class) {
                    Node node = document.get(prefix + field.fieldName);

                    if (node == null) {
                        MysticLib.LOGGER.warn("Field {} isn't present in config {}", field.fieldName, config.id.toString());
                    }

                    if (node instanceof StringNode) {
                        field.setFieldValue(node.value());
                    } else {
                        MysticLib.LOGGER.warn("Field {} is not a String in config {}", field.fieldName,  config.id.toString());
                    }
                }
                if (Number.class.isAssignableFrom(field.type)) {
                    Node node = document.get(prefix + field.fieldName);

                    if (node == null) {
                        MysticLib.LOGGER.warn("Field {} isn't present in config {}", field.fieldName, config.id.toString());
                    }

                    if (node instanceof NumberNode) {
                        field.setFieldValue(node.value());
                    } else {
                        MysticLib.LOGGER.warn("Field {} is not a Number in config {}", field.fieldName,  config.id.toString());
                    }
                }
                if (field.type == Boolean.class) {
                    Node node = document.get(prefix + field.fieldName);

                    if (node == null) {
                        MysticLib.LOGGER.warn("Field {} isn't present in config {}", field.fieldName, config.id.toString());
                    }

                    if (node instanceof BooleanNode) {
                        field.setFieldValue(node.value());
                    } else {
                        MysticLib.LOGGER.warn("Field {} is not a Boolean in config {}", field.fieldName, config.id.toString());
                    }
                }
            }
        }
    }

}
