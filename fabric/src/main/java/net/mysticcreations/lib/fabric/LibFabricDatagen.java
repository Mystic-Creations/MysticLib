package net.mysticcreations.lib.fabric;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;

public class LibFabricDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator generator) {
        var pack = generator.createPack();
    }
}
