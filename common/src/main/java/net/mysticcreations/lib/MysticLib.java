package net.mysticcreations.lib;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MysticLib {
    public static final Logger LOGGER = LogManager.getLogger(MysticLib.class);
    public static final String MODID = "mysticlib";

    public static void init() {
        // Write common init code here.
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
