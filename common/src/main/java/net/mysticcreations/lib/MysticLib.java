package net.mysticcreations.lib;

import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.example.config.ExampleTomlConfig;
import net.mysticcreations.lib.config.ConfigInitializer;
import net.mysticcreations.lib.config.toml.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.ObjectInputFilter;
import java.util.List;

public final class MysticLib {
    public static final Logger LOGGER = LogManager.getLogger(MysticLib.class);
    public static final String MODID = "mysticlib";

    public static void init() {
        // Write common init code here.

        ExampleTomlConfig config = new ExampleTomlConfig();

        try {
            TomlParser parser = new TomlParser(new File("config/" + config.id.getNamespace() + "/" + config.id.getPath() + ".toml"));
            List<TomlElement<?>> elements = parser.getElements();
            LOGGER.info(elements);
        } catch (TomlParsingException e) {
            throw new RuntimeException(e);
        }


    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MODID, path);
    }
}
