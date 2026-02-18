package net.mysticcreations.lib.util;

import net.minecraft.resources.ResourceLocation;

import static net.mysticcreations.lib.MysticLib.MODID;

public class ResourceUtil {
    //Library's own "asResource"
    public static ResourceLocation libResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
