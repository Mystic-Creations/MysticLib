package net.mysticcreations.lib.util;

import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.mysticcreations.lib.MysticLib;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DatagenDataUtil {
    public static class Recipes {
        /**
         * EXAMPLE OF USE
         * var Gen = new DatagenDataUtil.Recipes(MOD_ID, writer);
         *
         * Gen.Building.planksFromLogs(Items.OAK_LOG.get(), Items.OAK_PLANKS.get()); (will output 4 planks)
         */
        private final String modId;
        private final Consumer<FinishedRecipe> writer;

        public final Crafting Crafting = new Crafting();
        public final Building Building = new Building();
        public final Redstone Redstone = new Redstone();
        public final Processing Processing = new Processing();

        public Recipes(String modId, Consumer<FinishedRecipe> writer) {
            this.modId = modId;
            this.writer = writer;
        }

        public class Crafting {
            public void shapeless(RecipeCategory category, Item output, int count, Item... inputs) {
                ShapelessRecipeBuilder builder = ShapelessRecipeBuilder.shapeless(category, output, count);
                for (Item input : inputs) builder.requires(input);
                String inputNames = Arrays.stream(inputs)
                    .map(i -> RecipeProvider.getItemName(i))
                    .collect(Collectors.joining("_and_"));
                builder.unlockedBy(RecipeProvider.getHasName(inputs[0]), RecipeProvider.has(inputs[0]))
                    .save(writer, MysticLib.asExtResource(modId, RecipeProvider.getItemName(output) + "_from_" + inputNames));
            }
            public void shapeless(RecipeCategory category, Item output, Item... inputs) {
                shapeless(category, output, 1, inputs);
            }
        }

        public class Building {
            public void planksFromLogs(Item log, Item planks) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, planks, 4)
                    .requires(log)
                    .unlockedBy(RecipeProvider.getHasName(log), RecipeProvider.has(log))
                    .save(writer);
            }
            public void stairs(Item material, Item stairs) {
                RecipeProvider.stairBuilder(stairs, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void slab(Item material, Item slab) {
                RecipeProvider.slabBuilder(RecipeCategory.BUILDING_BLOCKS, slab, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void fence(Item material, Item fence) {
                RecipeProvider.fenceBuilder(fence, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void fenceGate(Item material, Item fenceGate) {
                RecipeProvider.fenceGateBuilder(fenceGate, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void door(Item material, Item door) {
                RecipeProvider.doorBuilder(door, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void trapdoor(Item material, Item trapdoor) {
                RecipeProvider.trapdoorBuilder(trapdoor, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void wall(Item material, Item wall) {
                RecipeProvider.wallBuilder(RecipeCategory.BUILDING_BLOCKS, wall, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void bars(Item material, Item bars) {
                ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, bars, 16)
                    .define('#', material)
                    .pattern("###")
                    .pattern("###")
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
        }

        public class Redstone {
            public void pressurePlate(Item material, Item pressurePlate) {
                RecipeProvider.pressurePlateBuilder(RecipeCategory.REDSTONE, pressurePlate, Ingredient.of(material))
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
            public void button(Item material, Item button) {
                ShapelessRecipeBuilder.shapeless(RecipeCategory.REDSTONE, button)
                    .requires(material)
                    .unlockedBy(RecipeProvider.getHasName(material), RecipeProvider.has(material))
                    .save(writer);
            }
        }

        public class Processing {
            public void smelt(Item input, Item output, float exp, int cookingTime) {
                SimpleCookingRecipeBuilder.smelting(Ingredient.of(input), RecipeCategory.MISC, output, exp, cookingTime)
                    .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                    .save(writer, RecipeProvider.getConversionRecipeName(output, input) + "_smelting");
            }
            public void smelt(Item input, Item output, float exp) {
                smelt(input, output, exp, 200);
            }
            public void blast(Item input, Item output, float exp, int cookingTime) {
                SimpleCookingRecipeBuilder.blasting(Ingredient.of(input), RecipeCategory.MISC, output, exp, cookingTime)
                    .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                    .save(writer, RecipeProvider.getConversionRecipeName(output, input) + "_blasting");
            }
            public void blast(Item input, Item output, float exp) {
                blast(input, output, exp, 100);
            }
            public void smoke(Item input, Item output, float exp, int cookingTime) {
                SimpleCookingRecipeBuilder.smoking(Ingredient.of(input), RecipeCategory.MISC, output, exp, cookingTime)
                    .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                    .save(writer, RecipeProvider.getConversionRecipeName(output, input) + "_smoking");
            }
            public void smoke(Item input, Item output, float exp) {
                smoke(input, output, exp, 100);
            }
            public void cut(Item input, Item output, int resultCount) {
                SingleItemRecipeBuilder.stonecutting(Ingredient.of(input), RecipeCategory.BUILDING_BLOCKS, output, resultCount)
                    .unlockedBy(RecipeProvider.getHasName(input), RecipeProvider.has(input))
                    .save(writer, RecipeProvider.getConversionRecipeName(output, input) + "_stonecutting");
            }
            public void cut(Item input, Item output) {
                cut(input, output, 1);
            }
            public void smithing(Item base, Item addition, Item template, RecipeCategory category, Item result) {
                SmithingTransformRecipeBuilder.smithing(Ingredient.of(template), Ingredient.of(base), Ingredient.of(addition), category, result)
                    .unlocks(RecipeProvider.getHasName(addition), RecipeProvider.has(addition))
                    .save(writer, RecipeProvider.getConversionRecipeName(result, base) + "_smithing");
            }
        }
    }

    // Might expand on it later
    public static class Advancements {}
    public static class BlockTags {}
    public static class ItemTags {}
    public static class BlockLoot {}
    public static class ChestLoot {}
    public static class EntityLoot {}
}