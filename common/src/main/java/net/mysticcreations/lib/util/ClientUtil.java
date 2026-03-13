package net.mysticcreations.lib.util;

import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceLocation;
import net.mysticcreations.lib.mixin.DimSpecialEffectsAccessor;

public class ClientUtil {
    public static class SpecialDimensionEffects {
        public static void add(ResourceLocation effectsId, DimensionSpecialEffects effectsClass) {
            DimSpecialEffectsAccessor.getEffects().put(effectsId, effectsClass);
        }
    }
}
