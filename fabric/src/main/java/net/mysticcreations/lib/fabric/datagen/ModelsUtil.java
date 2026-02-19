package net.mysticcreations.lib.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.renderer.block.model.MultiVariant;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class ModelsUtil extends FabricModelProvider {
    public ModelsUtil(FabricDataOutput output) {
        super(output);
    }

    public static void genFlatItem(ItemModelGenerators itemGen, Item item) {
        itemGen.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }
    public static void genFlatBlockItem(ItemModelGenerators itemGen, Block block) {
        itemGen.generateFlatItem(Item.byBlock(block), ModelTemplates.FLAT_ITEM);
    }

    public static BlockModelGenerators genBlockstatesOnly(Block block) {
        ///Code taken from a mc 26.1 project by JustMili, shit needs to be ported down
        /// This code is for making horizontal rotation blockstates (y-axis), we need to make it so there's multiple choices
        //MysticLib.asResource("block/" + BuiltInRegistries.BLOCK.getKey(block).getPath());  <- ignore this
        ResourceLocation id = ResourceLocation.tryParse(block.getName().getString()); //This defo won't work exactly, need to redo
        MultiVariant model = BlockModelGenerators.plainVariant(id);

        return MultiVariantGenerator.dispatch(plushBlock)
            .with(PropertyDispatch.initial(HorizontalDirectionalBlock.FACING)
                .select(Direction.NORTH, model)
                .select(Direction.EAST, model.with(BlockModelGenerators.Y_ROT_90))
                .select(Direction.SOUTH, model.with(BlockModelGenerators.Y_ROT_180))
                .select(Direction.WEST, model.with(BlockModelGenerators.Y_ROT_270))
            );
    }
    //ADDITIONAL TODO:
    //Make a "createWoodenBlocksFamily" method (planks -> stairs, slabs, fence, fence gate, door, trapdoor, pressure plates, buttons), each member toggleable
    //Make a "createStoneBlocksFamily" method (block -> stairs, slabs, walls, pressure plates, buttons), each member toggleable
    //for both add a "hasCustomBlockModel" and "hasCustomItemModel" where user can list all block and block item models to exclude from generating

    //Do all the same for Forge

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockGen) {}
    @Override
    public void generateItemModels(ItemModelGenerators itemGen) {}
}
