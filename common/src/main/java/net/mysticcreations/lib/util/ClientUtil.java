package net.mysticcreations.lib.util;

import com.google.common.collect.Streams;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.mysticcreations.lib.mixin.DimSpecialEffectsAccessor;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ClientUtil {
    public static class SpecialDimensionEffects {
        public static void add(ResourceLocation effectsId, DimensionSpecialEffects effectsClass) {
            DimSpecialEffectsAccessor.getEffects().put(effectsId, effectsClass);
        }
    }
    public static class BlockTransparency {
        @ExpectPlatform
        public static void registerFromSupplier(Iterable<Supplier<Block>> registry) {
            throw new AssertionError("ExpectPlatform method not implemented");
        }
        public static Block[] getBlocks(Iterable<Supplier<Block>> registry) {
            return Streams.stream(registry).map(Supplier::get).toArray(Block[]::new);
        }
    }
}
