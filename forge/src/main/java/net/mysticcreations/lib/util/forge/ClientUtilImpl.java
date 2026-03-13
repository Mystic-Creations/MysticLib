package net.mysticcreations.lib.util.forge;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.mysticcreations.lib.util.ClientUtil;

import java.util.function.Supplier;

public class ClientUtilImpl {
    public static class BlockTransparency {
        public static void registerFromSupplier(Iterable<Supplier<Block>> blockRegistryClass) {
            for (Block block : ClientUtil.BlockTransparency.getBlocks(blockRegistryClass)) {
                ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutout());
            }
        }
    }
}
