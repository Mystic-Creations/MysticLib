package net.mysticcreations.lib.config.specification;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.config.ConfigFileType;

public abstract class ConfigSpecification {

    public transient final ResourceLocation id;
    public transient final ConfigFileType configType;

    public ConfigSpecification(ResourceLocation id, ConfigFileType type) {
        this.id = id;
        this.configType = type;
    }

    public ConfigFileType getConfigType() {
        // json as default cause js{
        //
        //    public transient is cool
        return ConfigFileType.JSON;
    }
}
