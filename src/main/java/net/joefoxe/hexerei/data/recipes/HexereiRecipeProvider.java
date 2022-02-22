package net.joefoxe.hexerei.data.recipes;


import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.CustomRecipeBuilder;

import java.util.function.Consumer;

public class HexereiRecipeProvider extends RecipeProvider {


    public HexereiRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<IFinishedRecipe> consumer) {

        CustomRecipeBuilder.special(CofferDyeingRecipe.SERIALIZER).save(consumer, "hexerei:coffer_dyeing");
        CustomRecipeBuilder.special(KeychainRecipe.SERIALIZER).save(consumer, "hexerei:keychain_apply");
    }
}
