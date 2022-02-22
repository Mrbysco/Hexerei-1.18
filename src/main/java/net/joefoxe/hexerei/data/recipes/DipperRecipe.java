package net.joefoxe.hexerei.data.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.JsonToNBT;
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
import net.minecraft.fluid.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DipperRecipe implements IDipperRecipe{

    private final ResourceLocation id;
    private final ItemStack output;
    private final NonNullList<Ingredient> recipeItems;
    private final FluidStack liquid;
    private final int fluidLevelsConsumed;
    private final int dippingTime;
    private final int dryingTime;
    private final int numberOfDips;
    protected static final List<Boolean> itemMatchesSlot = new ArrayList<>();


    public DipperRecipe(ResourceLocation id, NonNullList<Ingredient> inputs,
                        ItemStack output, FluidStack liquid, int fluidLevelsConsumed,
                        int dippingTime, int dryingTime, int numberOfDips) {
        this.id = id;
        this.output = output;
        this.recipeItems = inputs;
        this.liquid = liquid;
        this.fluidLevelsConsumed = fluidLevelsConsumed;
        this.dippingTime = dippingTime;
        this.dryingTime = dryingTime;
        this.numberOfDips = numberOfDips;

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

    public FluidStack getLiquid() { return this.liquid; }

    public int getFluidLevelsConsumed() { return this.fluidLevelsConsumed; }

    public int getDippingTime() { return this.dippingTime; }

    public int getDryingTime() { return this.dryingTime; }

    public int getNumberOfDips() { return this.numberOfDips; }

    public ItemStack getToastSymbol() {
        return new ItemStack(ModBlocks.CANDLE_DIPPER.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.DIPPER_SERIALIZER.get();
    }

    public static class DipperRecipeType implements IRecipeType<DipperRecipe> {
        @Override
        public String toString() {
            return DipperRecipe.TYPE_ID.toString();
        }
    }


    // for Serializing the recipe into/from a json
    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>>
            implements IRecipeSerializer<DipperRecipe> {

        @Override
        public DipperRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            JsonArray ingredients = JSONUtils.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);
            ItemStack output = ShapedRecipe.itemStackFromJson(JSONUtils.getAsJsonObject(json, "output"));
            FluidStack liquid = deserializeFluidStack(JSONUtils.getAsJsonObject(json, "liquid"));
            int fluidLevelsConsumed = JSONUtils.getAsInt(json, "fluidLevelsConsumed");
            int dippingTime = JSONUtils.getAsInt(json, "dippingTimeInTicks");
            int dryingTime = JSONUtils.getAsInt(json, "dryingTimeInTicks");
            int numberOfDips = JSONUtils.getAsInt(json, "numberOfDips");

            inputs.set(0, Ingredient.fromJson(ingredients.get(0)));

            return new DipperRecipe(recipeId, inputs,
                    output, liquid, fluidLevelsConsumed, dippingTime, dryingTime, numberOfDips);
        }

        @Nullable
        @Override
        public DipperRecipe fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buffer.readInt(), Ingredient.EMPTY);
//            inputs.add(Ingredient.fromNetwork(buffer));
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buffer));
            }

            ItemStack output = buffer.readItem();
            return new DipperRecipe(recipeId, inputs, output,
                    buffer.readFluidStack(), buffer.readInt(), buffer.readInt(), buffer.readInt(), buffer.readInt());
        }

        @Override
        public void toNetwork(PacketBuffer buffer, DipperRecipe recipe) {
            buffer.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients())
                ing.toNetwork(buffer);
            buffer.writeItem(recipe.getResultItem());

            buffer.writeFluidStack(recipe.getLiquid());

            buffer.writeInt(recipe.getFluidLevelsConsumed());
            buffer.writeInt(recipe.getDippingTime());
            buffer.writeInt(recipe.getDryingTime());
            buffer.writeInt(recipe.getNumberOfDips());
        }

        public static FluidStack deserializeFluidStack(JsonObject json) {
            ResourceLocation id = new ResourceLocation(JSONUtils.getAsString(json, "fluid"));
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(id);
            if (fluid == null)
                throw new JsonSyntaxException("Unknown fluid '" + id + "'");
            FluidStack stack = new FluidStack(fluid, 1);

            if (!json.has("nbt"))
                return stack;

            try {
                JsonElement element = json.get("nbt");
                stack.setTag(JsonToNBT.parseTag(
                        element.isJsonObject() ? Hexerei.GSON.toJson(element) : JSONUtils.convertToString(element, "nbt")));

            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }

            return stack;
        }

    }
}
