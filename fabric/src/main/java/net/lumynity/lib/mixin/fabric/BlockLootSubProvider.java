package net.lumynity.lib.mixin.fabric;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.Block;
import net.lumynity.lib.datagen.extensions.KnownBlocksLootProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;

@Mixin(net.minecraft.data.loot.BlockLootSubProvider.class)
public abstract class BlockLootSubProvider {
    @ModifyExpressionValue(method = "generate(Ljava/util/function/BiConsumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/DefaultedRegistry;iterator()Ljava/util/Iterator;"))
    private Iterator<Block> useKnownBlocks(Iterator<Block> original) {
        if (this instanceof KnownBlocksLootProvider provider) {
            return provider.getKnownBlocks().iterator();
        }

        return original;
    }
}
