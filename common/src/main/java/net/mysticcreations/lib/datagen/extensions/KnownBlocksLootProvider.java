package net.mysticcreations.lib.datagen.extensions;

import net.minecraft.world.level.block.Block;

import java.util.stream.Stream;

public interface KnownBlocksLootProvider {
    Stream<Block> getKnownBlocks();
}
