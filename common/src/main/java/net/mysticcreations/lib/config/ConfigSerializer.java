package net.mysticcreations.lib.config;

import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.fields.ConfigCat;
import net.mysticcreations.lib.config.fields.ConfigField;
import net.mysticcreations.lib.config.toml.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ConfigSerializer {

    private ConfigDefinition config;
    public File configFile;

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

                convertConfigListToToml(builder, config.items, new ArrayList<>());

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

    public void convertConfigListToToml(TomlBuilder builder, List<ConfigItem> configList, ArrayList<String> prefix) {
        List<ConfigCat> lateTables = new ArrayList<>();
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {
                lateTables.add(cat);
            }

            if (item instanceof ConfigField<?> field) {

                if (field.type == String.class) {
                    TomlStringField element = new TomlStringField(field.fieldName,(String) field.value);
                    element.setHeaderComments(field.headerComments);
                    element.setInlineComment(field.inlineComment);
                    builder.addElement(element);
                }
                if (Integer.class.isAssignableFrom(field.type)) {
                    TomlIntegerField element = new TomlIntegerField(field.fieldName, (Integer) field.value);
                    element.setHeaderComments(field.headerComments);
                    element.setInlineComment(field.inlineComment);
                    builder.addElement(element);
                }
                if (Float.class.isAssignableFrom(field.type)) {
                    TomlDecimalField element = new TomlDecimalField(field.fieldName, (Float) field.value);
                    element.setHeaderComments(field.headerComments);
                    element.setInlineComment(field.inlineComment);
                    builder.addElement(element);
                }
                if (field.type == Boolean.class) {
                    TomlBooleanField element = new TomlBooleanField(field.fieldName, (Boolean) field.value);
                    element.setInlineComment(field.inlineComment);
                    element.setHeaderComments(field.headerComments);
                    builder.addElement(element);
                }
            }
        }
        for (ConfigCat cat: lateTables) {
            ArrayList<String> tableName = new ArrayList<>(prefix);
            tableName.add(cat.catName);
            TomlDottedElementName elementName = new TomlDottedElementName();
            tableName.forEach(it -> {
                elementName.addName(it, TomlStringType.DOUBLE);
            });
            TomlTable table = new TomlTable(elementName);
            builder.addElement(table);
            convertConfigListToToml(builder, cat.items, tableName);
        }
    }

    public void readFromConfigFile() {

        switch (config.getConfigType()) {
            case TOML -> {

                if (!configFile.exists()) {
                    MysticLib.LOGGER.info("No config file found. Creating a template one");
                    configFile.getParentFile().mkdirs();
                    try {
                        configFile.createNewFile();
                        writeToConfigFile();
                    } catch (IOException e) {
                        MysticLib.LOGGER.error("Failed to create missing config file");
                    }
                    return;
                }
                try {
                    TomlParser parser = new TomlParser(configFile);

                    List<TomlElement> elements = parser.getElements();

                    MysticLib.LOGGER.info("toml is parse now");

                } catch (TomlParsingException e) {
                    MysticLib.LOGGER.error("Error while parsing TOML of : {}", this.config.id.toString());
                    MysticLib.LOGGER.error(e);
                }
                //applyTomlValuesToConfig(config, document, config.items, );
            }
            case JSON -> {
                // do stuff
            }
        }
    }

}
