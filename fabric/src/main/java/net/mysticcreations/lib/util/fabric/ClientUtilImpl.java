package net.mysticcreations.lib.util.fabric;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.mysticcreations.lib.util.ClientUtil;

import java.util.function.Supplier;

public class ClientUtilImpl {
    public static class BlockTransparency {
        public static void registerFromSupplier(Iterable<Supplier<Block>> blockRegistryClass) {
            for (Block block : ClientUtil.BlockTransparency.getBlocks(blockRegistryClass)) {
                BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
            }
        }
    }
}
