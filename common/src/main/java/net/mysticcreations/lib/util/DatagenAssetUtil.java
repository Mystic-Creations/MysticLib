package net.mysticcreations.lib.util;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

public class DatagenAssetUtil {
    public static class BlockModels {
        public static class Families {
            public static void createWoodFamily(BlockModelGenerators blockGen, Block planks, Block stairs, Block slab,
                                                Block fence, Block fenceGate, Block door, Block trapdoor) {
                TexturedModel texturedModel = TexturedModel.CUBE.get(planks);
                TextureMapping mapping = texturedModel.getMapping();

                // Planks
                ResourceLocation fullBlock = texturedModel.create(planks, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(planks, fullBlock));

                // Stairs
                ResourceLocation stairsInner = ModelTemplates.STAIRS_INNER.create(stairs, mapping, blockGen.modelOutput);
                ResourceLocation stairsStraight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, mapping, blockGen.modelOutput);
                ResourceLocation stairsOuter = ModelTemplates.STAIRS_OUTER.create(stairs, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, stairsInner, stairsStraight, stairsOuter));
                blockGen.delegateItemModel(stairs, stairsStraight);

                // Slab
                ResourceLocation slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockGen.modelOutput);
                ResourceLocation slabTop = ModelTemplates.SLAB_TOP.create(slab, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, slabBottom, slabTop, fullBlock));
                blockGen.delegateItemModel(slab, slabBottom);

                // Fence
                ResourceLocation fencePost = ModelTemplates.FENCE_POST.create(fence, mapping, blockGen.modelOutput);
                ResourceLocation fenceSide = ModelTemplates.FENCE_SIDE.create(fence, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createFence(fence, fencePost, fenceSide));
                blockGen.delegateItemModel(fence, ModelTemplates.FENCE_INVENTORY.create(fence, mapping, blockGen.modelOutput));

                // Fence gate
                ResourceLocation fgOpen = ModelTemplates.FENCE_GATE_OPEN.create(fenceGate, mapping, blockGen.modelOutput);
                ResourceLocation fgClosed = ModelTemplates.FENCE_GATE_CLOSED.create(fenceGate, mapping, blockGen.modelOutput);
                ResourceLocation fgWallOpen = ModelTemplates.FENCE_GATE_WALL_OPEN.create(fenceGate, mapping, blockGen.modelOutput);
                ResourceLocation fgWallClosed = ModelTemplates.FENCE_GATE_WALL_CLOSED.create(fenceGate, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createFenceGate(fenceGate, fgOpen, fgClosed, fgWallOpen, fgWallClosed, true));

                // Door & Trapdoor
                blockGen.createDoor(door);
                blockGen.createOrientableTrapdoor(trapdoor);
            }

            public static void createStoneFamily(BlockModelGenerators blockGen, Block stoneType, Block stairs, Block slab) {
                TexturedModel texturedModel = TexturedModel.CUBE.get(stoneType);
                TextureMapping mapping = texturedModel.getMapping();

                // Stone block
                ResourceLocation fullBlock = texturedModel.create(stoneType, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(stoneType, fullBlock));

                //Stairs
                ResourceLocation stairsInner = ModelTemplates.STAIRS_INNER.create(stairs, mapping, blockGen.modelOutput);
                ResourceLocation stairsStraight = ModelTemplates.STAIRS_STRAIGHT.create(stairs, mapping, blockGen.modelOutput);
                ResourceLocation stairsOuter = ModelTemplates.STAIRS_OUTER.create(stairs, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createStairs(stairs, stairsInner, stairsStraight, stairsOuter));
                blockGen.delegateItemModel(stairs, stairsStraight);

                // Slab
                ResourceLocation slabBottom = ModelTemplates.SLAB_BOTTOM.create(slab, mapping, blockGen.modelOutput);
                ResourceLocation slabTop = ModelTemplates.SLAB_TOP.create(slab, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createSlab(slab, slabBottom, slabTop, fullBlock));
                blockGen.delegateItemModel(slab, slabBottom);
            }

            public static void createStoneFamily(BlockModelGenerators blockGen, Block block, Block stairs, Block slab, Block wall) {
                // Call no-wall createStoneFamily
                createStoneFamily(blockGen, block, stairs, slab);

                // Wall
                TextureMapping mapping = TexturedModel.CUBE.get(block).getMapping();
                ResourceLocation wallPost = ModelTemplates.WALL_POST.create(wall, mapping, blockGen.modelOutput);
                ResourceLocation wallLow = ModelTemplates.WALL_LOW_SIDE.create(wall, mapping, blockGen.modelOutput);
                ResourceLocation wallTall = ModelTemplates.WALL_TALL_SIDE.create(wall, mapping, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(BlockModelGenerators.createWall(wall, wallPost, wallLow, wallTall));
                blockGen.delegateItemModel(wall, ModelTemplates.WALL_INVENTORY.create(wall, mapping, blockGen.modelOutput));
            }

            public static void createRedstoneFamily(BlockModelGenerators blockGen, Block block, Block pressurePlate, Block button) {
                TextureMapping mapping = TexturedModel.CUBE.get(block).getMapping();

                if (pressurePlate != null) {
                    ResourceLocation ppUp = ModelTemplates.PRESSURE_PLATE_UP.create(pressurePlate, mapping, blockGen.modelOutput);
                    ResourceLocation ppDown = ModelTemplates.PRESSURE_PLATE_DOWN.create(pressurePlate, mapping, blockGen.modelOutput);
                    blockGen.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pressurePlate, ppUp, ppDown));
                }

                if (button != null) {
                    ResourceLocation btn = ModelTemplates.BUTTON.create(button, mapping, blockGen.modelOutput);
                    ResourceLocation btnPressed = ModelTemplates.BUTTON_PRESSED.create(button, mapping, blockGen.modelOutput);
                    blockGen.blockStateOutput.accept(BlockModelGenerators.createButton(button, btn, btnPressed));
                    blockGen.delegateItemModel(button, ModelTemplates.BUTTON_INVENTORY.create(button, mapping, blockGen.modelOutput));
                }
            }

            public static void createGlassFamily(BlockModelGenerators blockGen, Block glass, Block pane) {
                blockGen.createGlassBlocks(glass, pane);
            }
        }

        public static class Individual {
            public static void createCubeAll(BlockModelGenerators blockGen, Block block) {
                blockGen.createTrivialCube(block);
            }

            public static void createCube(BlockModelGenerators blockGen, Block block, RotationType rotationType) {
                createCube(blockGen, block, rotationType, ModelTemplates.CUBE_ALL, TextureMapping.cube(block));
            }

            public static void createCube(BlockModelGenerators blockGen, Block block, RotationType rotationType,
                                          ModelTemplate template, TextureMapping textureMapping) {
                ResourceLocation model = template.create(block, textureMapping, blockGen.modelOutput);
                switch (rotationType) {
                    case NONE -> blockGen.blockStateOutput.accept(BlockModelGenerators.createSimpleBlock(block, model));
                    case HORIZONTAL_Y -> blockGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block,
                            Variant.variant().with(VariantProperties.MODEL, model))
                        .with(BlockModelGenerators.createHorizontalFacingDispatch()));
                    case ALL_DIRECTIONS -> blockGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block,
                            Variant.variant().with(VariantProperties.MODEL, model))
                        .with(BlockModelGenerators.createFacingDispatch()));
                    case LOG_XYZ ->
                        blockGen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, model));
                }
            }

            public enum RotationType {
                NONE,
                HORIZONTAL_Y, // S/W/N/E y-axis
                ALL_DIRECTIONS, // D/U/N/S/W/E
                LOG_XYZ // Log XYZ axis
            }

            public static void createFarmland(BlockModelGenerators blockGen, Block blockForTopFace, Block blockForDirt) {
                TextureMapping dry = new TextureMapping()
                    .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(blockForDirt))
                    .put(TextureSlot.TOP, TextureMapping.getBlockTexture(blockForTopFace));
                TextureMapping moist = new TextureMapping()
                    .put(TextureSlot.DIRT, TextureMapping.getBlockTexture(blockForDirt))
                    .put(TextureSlot.TOP, TextureMapping.getBlockTexture(blockForTopFace, "_moist"));
                ResourceLocation dryModel = ModelTemplates.FARMLAND.create(blockForTopFace, dry, blockGen.modelOutput);
                ResourceLocation moistModel = ModelTemplates.FARMLAND.create(
                    TextureMapping.getBlockTexture(blockForTopFace, "_moist"), moist, blockGen.modelOutput);
                blockGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(blockForTopFace)
                    .with(BlockModelGenerators.createEmptyOrFullDispatch(
                        BlockStateProperties.MOISTURE, 7, moistModel, dryModel)));
            }

            public static void createPlant(BlockModelGenerators blockGen, Block block, BlockModelGenerators.TintState tintState) {
                blockGen.createCrossBlockWithDefaultItem(block, tintState);
            }

            public static void createTallPlant(BlockModelGenerators blockGen, Block block, BlockModelGenerators.TintState tintState) {
                blockGen.createDoublePlant(block, tintState);
            }

            public static void createCrop(BlockModelGenerators blockGen, Block block,
                                          Property<Integer> ageProperty, int... ageToVisualStageMapping) {
                blockGen.createCropBlock(block, ageProperty, ageToVisualStageMapping);
            }
        }

        public static class RotationBlockstateOnly {
            public static void createY(BlockModelGenerators blockGen, Block block, ResourceLocation modelLocation) {
                blockGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block,
                        Variant.variant().with(VariantProperties.MODEL, modelLocation))
                    .with(BlockModelGenerators.createHorizontalFacingDispatch()));
            }

            public static void createUPNWSE(BlockModelGenerators blockGen, Block block, ResourceLocation modelLocation) {
                blockGen.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block,
                        Variant.variant().with(VariantProperties.MODEL, modelLocation))
                    .with(BlockModelGenerators.createFacingDispatch()));
            }

            public static void createXYZ(BlockModelGenerators blockGen, Block block, ResourceLocation modelLocation) {
                blockGen.blockStateOutput.accept(BlockModelGenerators.createAxisAlignedPillarBlock(block, modelLocation));
            }
        }
    }

    public static class ItemModels {
        public static void createFlatItem(ItemModelGenerators itemGen, Item item) {
            itemGen.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }
        public static void createFlatBlockItem(ItemModelGenerators itemGen, Block block) {
            itemGen.generateFlatItem(Item.BY_BLOCK.get(block), ModelTemplates.FLAT_ITEM);
        }
    }
}
