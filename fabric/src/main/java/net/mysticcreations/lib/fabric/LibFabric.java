package net.mysticcreations.lib.fabric;

import net.fabricmc.api.ModInitializer;

import net.mysticcreations.lib.MysticLib;

public final class LibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        MysticLib.init();
    }
}
