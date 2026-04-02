package net.mysticcreations.lib;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.example.config.ExampleTomlConfig;
import net.mysticcreations.lib.config.ConfigCommand;
import net.mysticcreations.lib.config.ConfigInitializer;
import net.mysticcreations.lib.config.screen.ConfigScreenNetwork;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MysticLib {
    public static final Logger LOGGER = LogManager.getLogger(MysticLib.class);
    public static final String MOD_ID = "mysticlib";
    public static final LibraryContext Lib = new LibraryContext(MOD_ID);

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
