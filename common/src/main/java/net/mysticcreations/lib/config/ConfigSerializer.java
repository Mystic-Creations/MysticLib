package net.mysticcreations.lib.config;

import com.github.groundbreakingmc.tomly.Tomly;
import com.github.groundbreakingmc.tomly.nodes.Node;
import com.github.groundbreakingmc.tomly.nodes.TomlDocument;
import com.github.groundbreakingmc.tomly.nodes.impl.*;
import com.github.groundbreakingmc.tomly.options.PreserveOptions;
import com.github.groundbreakingmc.tomly.options.WriterOptions;
import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.fields.ConfigCat;
import net.mysticcreations.lib.config.fields.ConfigField;

import java.io.File;
import java.io.FileWriter;
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
                PreserveOptions parseOpts = PreserveOptions.builder()
                        .preserveHeaderComments(true)
                        .preserveInlineComments(true)
                        .preserveBlankLines(false)
                        .build();

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

                TomlDocument document = Tomly.parse(configFile.toPath().toAbsolutePath(), true, parseOpts);

                convertConfigListToToml(document, config.items, "");

                WriterOptions options = WriterOptions.builder()
                        .writeHeaderComments(true)
                        .writeInlineComments(true)
                        .writeBlankLines(false)
                        .build();

                Map<String, Object> raw = document.raw();

                document.save(configFile.toPath(), options);
            }
            case JSON -> {
                // do stuff
            }
        }
    }

    public void convertConfigListToToml(TomlDocument document, List<ConfigItem> configList, String prefix) {
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {
                Map<String, Node> entries = new HashMap<>();
                convertConfigListToToml(entries, cat.items,  "");
                TableNode table = new TableNode(cat.catName, entries, new ArrayList<>(), "");
                document.set(cat.catName, table);
            }

            if (item instanceof ConfigField<?> field) {

                if (field.type == String.class) {
                    StringNode node = new StringNode((String) field.value, field.headerComments, field.inlineComment);
                    document.set(prefix + field.fieldName, node);
                }
                if (Number.class.isAssignableFrom(field.type)) {
                    NumberNode node = new NumberNode((Number) field.value, field.headerComments, field.inlineComment);
                    document.set(prefix + field.fieldName, node);
                }
                if (field.type == Boolean.class) {
                    BooleanNode node = new BooleanNode((Boolean) field.value, field.headerComments, field.inlineComment);
                    document.set(prefix + field.fieldName, node);
                }
            }
        }
    }

    public void convertConfigListToToml(Map<String, Node> list, List<ConfigItem> configList, String prefix) {
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {
                Map<String, Node> entries = new HashMap<>();
                convertConfigListToToml(entries, cat.items,  "");
                TableNode table = new TableNode(cat.catName, entries, new ArrayList<>(), "");
                list.put(cat.catName, table);
            }
            if (item instanceof ConfigField<?> field) {

                if (field.type == String.class) {
                    StringNode node = new StringNode((String) field.value, field.headerComments, field.inlineComment);
                    list.put(prefix + field.fieldName, node);
                }
                if (Number.class.isAssignableFrom(field.type)) {
                    NumberNode node = new NumberNode((Number) field.value, field.headerComments, field.inlineComment);
                    list.put(prefix + field.fieldName, node);
                }
                if (field.type == Boolean.class) {
                    BooleanNode node = new BooleanNode((Boolean) field.value, field.headerComments, field.inlineComment);
                    list.put(prefix + field.fieldName, node);
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
