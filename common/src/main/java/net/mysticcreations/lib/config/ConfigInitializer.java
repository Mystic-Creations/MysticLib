package net.mysticcreations.lib.config;

import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class ConfigInitializer {
    private static Map<ResourceLocation, ConfigDefinition> definitions = new HashMap<>();

    public static void initializeConfigDefinition(ResourceLocation id , ConfigDefinition definition) {
        initializeConfigDefinition(id, definition, true);
    }

    public static void initializeConfigDefinition(ResourceLocation id , ConfigDefinition definition, boolean subfolder) {
        ConfigSerializer configSerializer = new ConfigSerializer(id, definition.getConfigType() ,subfolder);
        configSerializer.parseFromDefinition(definition);
        // read from file if exists here otherwise write a blank one
        definitions.put(id, definition);
    }

}

