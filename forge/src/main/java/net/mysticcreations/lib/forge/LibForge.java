package net.mysticcreations.lib.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.mysticcreations.lib.MysticLib;

@Mod(MysticLib.MOD_ID)
public final class LibForge {
    public LibForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(MysticLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        MysticLib.init();
    }
}
