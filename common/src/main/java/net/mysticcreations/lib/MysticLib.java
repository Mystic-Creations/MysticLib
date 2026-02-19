package net.mysticcreations.lib;

import net.minecraft.resources.ResourceLocation;

public final class MysticLib {
    public static final String MODID = "mysticlib";

    public static void init() {
        // Write common init code here.
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
