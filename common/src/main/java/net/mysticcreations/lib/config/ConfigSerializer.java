package net.mysticcreations.lib.config;

import net.mysticcreations.lib.MysticLib;
import net.mysticcreations.lib.config.fields.*;
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

    public void applyTomlValuesToConfig(List<TomlElement> tomlElements, List<ConfigItem> configList, ArrayList<String> prefix) {
        List<ConfigCat> lateTables = new ArrayList<>();
        for (ConfigItem item : configList) {
            if (item instanceof ConfigCat cat) {
                lateTables.add(cat);
            }

            if (item instanceof ConfigField<?> field) {

                if (field instanceof StringField) {
                    TomlElement element = findTomlElement(field.fieldName, tomlElements, prefix);
                    if (element == null) {
                        continue;
                    }
                    if (!(element instanceof TomlStringField stringTomlField)) {
                        continue;
                    }
                    StringField stringConfigField = (StringField) item;
                    stringConfigField.value = stringTomlField.value;
                }
                if (field instanceof IntegerField) {
                    TomlElement element = findTomlElement(field.fieldName, tomlElements, prefix);
                    if (element == null) {
                        continue;
                    }
                    if (!(element instanceof TomlIntegerField integerTomlField)) {
                        continue;
                    }
                    IntegerField integerConfigField = (IntegerField) item;
                    integerConfigField.value = integerTomlField.value;
                }
                if (field instanceof DoubleField) {
                    TomlElement element = findTomlElement(field.fieldName, tomlElements, prefix);
                    if (element == null) {
                        continue;
                    }
                    if (!(element instanceof TomlDecimalField doubleTomlField)) {
                        continue;
                    }
                    DoubleField doubleConfigField = (DoubleField) item;
                    doubleConfigField.value = doubleTomlField.value;
                }
                if (field instanceof BooleanField) {
                    TomlElement element = findTomlElement(field.fieldName, tomlElements, prefix);
                    if (element == null) {
                        continue;
                    }
                    if (!(element instanceof TomlBooleanField booleanTomlField)) {
                        continue;
                    }
                    BooleanField booleanConfigField = (BooleanField) item;
                    booleanConfigField.value = booleanTomlField.value;
                }
            }
        }
        for (ConfigCat cat: lateTables) {
            ArrayList<String> tableName = new ArrayList<>(prefix);
            tableName.add(cat.catName);
            applyTomlValuesToConfig(tomlElements, cat.items, tableName);
        }
    }

    private TomlElement findTomlElement(String nameInConfig, List<TomlElement> configList, List<String> prefix) {
        List<String> list = new ArrayList<>();
        list.add(nameInConfig);
        return findTomlElement(list, configList, prefix);
    }

    private TomlElement findTomlElement(List<String> nameInConfig, List<TomlElement> configList, List<String> prefix) {
        TomlDottedElementName elementName = new TomlDottedElementName();
        nameInConfig.forEach((it) -> elementName.addName(it, TomlStringUtils.getPreferredStringType(it)));
        TomlDottedElementName prefixName = new TomlDottedElementName();
        nameInConfig.forEach((it) -> prefixName.addName(it, TomlStringUtils.getPreferredStringType(it)));
        TomlTableBase table = null;
        for (TomlElement element:  configList) {

            if (element instanceof TomlTableBase) {
                table = (TomlTableBase) element;
                continue;
            }

            if (prefix.isEmpty()) {
                if (table != null)  {
                    continue;
                }
            } else {
                if (table == null) continue;
                if (!table.name.name.equals(prefix)) {
                    continue;
                }

            }

            if (element.name == elementName) {
                return element;
            }
        }
        return null;
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

                    applyTomlValuesToConfig(elements, config.items, new ArrayList<>());

                } catch (TomlParsingException e) {
                    MysticLib.LOGGER.error("Error while parsing TOML of : {}", this.config.id.toString());
                    MysticLib.LOGGER.error(e);
                    e.printStackTrace();

                }
                //applyTomlValuesToConfig(config, document, config.items, );
            }
            case JSON -> {
                // do stuff
            }
        }
    }

}
