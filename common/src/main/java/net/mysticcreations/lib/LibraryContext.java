package net.mysticcreations.lib;

import net.minecraft.resources.ResourceLocation;

public class LibraryContext {
    private static String registeredId;

    public static void registerFor(String modId) {
        registeredId = modId;
    }

    public static ResourceLocation asModResource(String path) {
        if (registeredId == null) {
            return new ResourceLocation("minecraft", path);
        }
        return new ResourceLocation(registeredId, path);
    }
}