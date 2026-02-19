package net.mysticcreations.lib.config;

import net.minecraft.resources.ResourceLocation;

public abstract class ConfigDefinition {
    public transient final ResourceLocation id;
    public transient final FileTypes configType;

    public ConfigDefinition(ResourceLocation id, FileTypes type) {
        this.id = id;
        this.configType = type;
    }

    public FileTypes getConfigType() {
        return configType;
    }
}
