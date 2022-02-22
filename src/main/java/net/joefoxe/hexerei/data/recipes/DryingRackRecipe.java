package net.joefoxe.hexerei.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.JSONUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.world.World;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DryingRackRecipe implements IDryingRackRecipe{

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final int dryingTime;
    protected static final List<Boolean> itemMatchesSlot = new ArrayList<>();


    public DryingRackRecipe(ResourceLocation id, NonNullList<Ingredient> inputs,
                            ItemStack output, int dryingTime) {
        this.id = id;
        this.output = output;
        this.recipeItems = inputs;
        this.dryingTime = dryingTime;

        for(int i = 0; i < 8; i++) {
            itemMatchesSlot.add(false);
        }

    }


    @Override
    public boolean matches(IInventory inv, World worldIn) {
        if(recipeItems.get(0).test(inv.getItem(0) )||
                recipeItems.get(0).test(inv.getItem(1)) ||
                        recipeItems.get(0).test(inv.getItem(2)))
            return true;

        return false;


    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack assemble(IInventory inv) {
        return output;
    }

    @Override
    public ItemStack getResultItem() {

        return output.copy();
    }

    public int getDryingTime() { return this.dryingTime; }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.HERB_DRYING_RACK.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.DRYING_RACK_SERIALIZER.get();
    }

    public static class DryingRackRecipeType implements IRecipeType<DryingRackRecipe> {
        @Override
        public String toString() {
            return DryingRackRecipe.TYPE_ID.toString();
        }
    }


    // for Serializing the recipe into/from a json
    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<DryingRackRecipe> {

        @Override
        public DryingRackRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            ItemStack output = ShapedRecipe.itemStackFromJson(JSONUtils.getAsJsonObject(json, "output"));
            int dryingTime = JSONUtils.getAsInt(json, "dryingTimeInTicks");

            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            return new DryingRackRecipe(recipeId, inputs,
                    output, dryingTime);
        }

        @Nullable
        @Override
        public DryingRackRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new DryingRackRecipe(recipeId, inputs, output, buffer.readInt());
        }

        @Override
        public void toNetwork(PacketBuffer buffer, DryingRackRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients())
                ing.toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());

            buffer.writeInt(recipe.getDryingTime());
        }

    }
}
