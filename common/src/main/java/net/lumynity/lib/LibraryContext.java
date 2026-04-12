package net.lumynity.lib;

import net.minecraft.resources.ResourceLocation;

public class LibraryContext {
    private final String registeredId;

    public LibraryContext(String modId) {
        registeredId = modId;
    }

    public ResourceLocation asResource(String path) {
        if (registeredId == null) return LumynLib.asMcResource(path);
        return new ResourceLocation(registeredId, path);
    }
}