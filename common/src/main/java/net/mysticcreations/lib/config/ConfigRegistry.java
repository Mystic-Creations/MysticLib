package net.mysticcreations.lib.config;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.config.specification.ConfigSpecification;

import java.util.HashMap;
import java.util.Map;

public class ConfigRegistry {

    private static Map<ResourceLocation, ConfigSpecification> specifications = new HashMap<>();

    public static void registerConfigSpecification(ResourceLocation id ,ConfigSpecification spec) {
        registerConfigSpecification(id, spec, true);
    }

    public static void registerConfigSpecification(ResourceLocation id ,ConfigSpecification spec, boolean subfolder) {
        ConfigSpecParser configSpecParser = new ConfigSpecParser(id, spec.getConfigType() ,subfolder);
        configSpecParser.parseConfigSpecification(spec);
        // read from file if exists here otherwise write a blank one
        specifications.put(id, spec);
    }

}

