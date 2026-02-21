package net.mysticcreations.lib.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.config.toml.TomlParser;

import java.util.HashMap;
import java.util.Map;

public class ConfigInitializer {
    private static Map<ResourceLocation, ConfigSerializer> definitions = new HashMap<>();

    public static void initializeConfigDefinition(ConfigDefinition definition) {
        initializeConfigDefinition(definition, true);
    }

    public static void initializeConfigDefinition(ConfigDefinition definition, boolean subfolder) {
        ConfigSerializer configSerializer = new ConfigSerializer(definition, subfolder);
        //configSerializer.readFromConfigFile();
        // read from file if exists here otherwise write a blank one
        definitions.put(definition.id, configSerializer);

        //TomlParser parser = new TomlParser(configSerializer.configFile);
    }

    public static void saveAllConfigs() {
        for (ConfigSerializer serializer : definitions.values()) {
            serializer.writeToConfigFile();
        }
    }

}

