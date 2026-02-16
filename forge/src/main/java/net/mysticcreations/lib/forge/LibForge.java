package net.mysticcreations.lib.forge;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.mysticcreations.lib.MysticLib;

@Mod(MysticLib.MODID)
public final class LibForge {
    public static IEventBus EVENT_BUS;

    public LibForge(FMLJavaModLoadingContext modContext) {
        MinecraftForge.EVENT_BUS.register(this);
        EVENT_BUS = modContext.getModEventBus();

        MysticLib.init();
    }
}
