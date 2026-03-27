package net.mysticcreations.lib;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.example.config.ExampleTomlConfig;
import net.mysticcreations.lib.config.ConfigCommand;
import net.mysticcreations.lib.config.ConfigInitializer;
import net.mysticcreations.lib.config.screen.ConfigScreenNetwork;
import net.mysticcreations.lib.config.toml.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class MysticLib {
    public static final Logger LOGGER = LogManager.getLogger(MysticLib.class);
    public static final String MODID = "mysticlib";

    public static void init() {

        ConfigCommand.register();
        ConfigScreenNetwork.registerPackets();

        // Write common init code here.
        ConfigInitializer.initializeConfigDefinition(new ExampleTomlConfig());
        ConfigInitializer.loadAllConfigs();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
    public static ResourceLocation asExtResource(String modId, String path) {
        return new ResourceLocation(modId, path);
    }
}
