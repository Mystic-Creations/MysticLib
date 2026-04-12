package net.lumynity.lib;

import net.minecraft.resources.ResourceLocation;
import net.lumynity.example.config.ExampleTomlConfig;
import net.lumynity.lib.config.ConfigCommand;
import net.lumynity.lib.config.ConfigInitializer;
import net.lumynity.lib.config.screen.ConfigScreenNetwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class LumynLib {
    public static final Logger LOGGER = LogManager.getLogger(LumynLib.class);
    public static final String MOD_ID = "lumynlib";

    private static String registeredId = "minecraft";
    public LumynLib(String modId) {
        registeredId = modId;
    }
    public static LibraryContext getHooked() {
        return new LibraryContext(registeredId);
    }

    public static void init() {
        ConfigCommand.register();
        ConfigScreenNetwork.registerPackets();

        // Write common init code here.
        ConfigInitializer.initializeConfigDefinition(new ExampleTomlConfig());
        ConfigInitializer.loadAllConfigs();
    }

    public static ResourceLocation asResource(String path) { return new ResourceLocation(MOD_ID, path); }
    public static ResourceLocation asMcResource(String path) { return new ResourceLocation("minecraft", path); }
    public static ResourceLocation asFabricResource(String path) { return new ResourceLocation("fabric", path); }
    public static ResourceLocation asForgeResource(String path) { return new ResourceLocation("forge", path); }
    public static ResourceLocation asNeoResource(String path) { return new ResourceLocation("neoforge", path); }
}
