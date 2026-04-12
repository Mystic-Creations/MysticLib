package net.lumynity.lib.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ConfigInitializer {
    static Map<ResourceLocation, ConfigSerializer> definitions = new HashMap<>();

    public static void initializeConfigDefinition(ConfigDefinition definition) {
        initializeConfigDefinition(definition, false);
    }

    public static void initializeConfigDefinition(ConfigDefinition definition, boolean createSubfolder) {
        ConfigSerializer configSerializer = new ConfigSerializer(definition, createSubfolder);
        //configSerializer.readFromConfigFile();
        definitions.put(definition.id, configSerializer);
    }

    public static void saveAllConfigs() {
        for (ConfigSerializer serializer : definitions.values()) {
            serializer.writeToConfigFile();
        }
    }

    public static void loadAllConfigs() {
        for (ConfigSerializer serializer : definitions.values()) {
            serializer.readFromConfigFile();
        }
    }

}

