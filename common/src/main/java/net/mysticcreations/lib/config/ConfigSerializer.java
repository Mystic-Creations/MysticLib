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
import java.util.stream.Stream;

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
                    TomlStringField element = new TomlStringField(field.fieldName,(String) field.value)
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
        for (ConfigCat cat: lateTables) {
            ArrayList<String> tableName = new ArrayList<>(prefix);
            tableName.add(cat.catName);
            TomlTable table = new TomlTable(tableName.toArray(new String[0]));
            builder.addElement(table);
            convertConfigListToToml(builder, cat.items, tableName);
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

                //applyTomlValuesToConfig(config, document, config.items, );
            }
            case JSON -> {
                // do stuff
            }
        }
    }

}
