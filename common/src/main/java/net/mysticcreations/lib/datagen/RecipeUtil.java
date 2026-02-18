package net.mysticcreations.lib.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class RecipeUtil extends RecipeProvider {
    public RecipeUtil(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> lookup) {
        super(pOutput);
    }

    public static void defineIngredient(Item ingredient, String patternSymbol) {
        //Allows for defining ingredients like e.g.
        /*
        ModItems.Item1 = #
        ModItems.Item2 = %
        ModItems.Item3 = O
        and so on
        might have to change it from a method to a list or something
        I'm dum
         */
    }
    public static void genShaped(RecipeCategory category, Consumer<FinishedRecipe> consumer, Map<Character, ItemLike> ingredients, List<String> pattern, ItemLike output, int outputCount) {
        //Replace Item definedIngredient with smth to link to defineIngredient method
        //1-9 ingredients
        //0-3 characters per row
        //One output, any output count
    }
    public static void genShapeless(Item definedIngredient, Item output, int outputCount) {
        //Replace Item definedIngredient with smth to link to defineIngredient method
        //1-9 ingredients
        //One output, any output count
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> rOutput) {

    }
}
