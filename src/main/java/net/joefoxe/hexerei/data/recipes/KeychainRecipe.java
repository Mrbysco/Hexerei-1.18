package net.joefoxe.hexerei.data.recipes;

import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.block.custom.Coffer;
import net.joefoxe.hexerei.item.custom.KeychainItem;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ContainerHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.world.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.world.World;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;


public class KeychainRecipe extends SpecialRecipe {
    public static final SpecialRecipeSerializer<KeychainRecipe> SERIALIZER = new SpecialRecipeSerializer<>(KeychainRecipe::new);

    public KeychainRecipe(ResourceLocation registryName) {
        super(registryName);

    }

    @Override
    public boolean matches(CraftingInventory inventory, World world) {
        int keychain = 0;
        int other = 0;

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof KeychainItem) {
                    ++keychain;
                } else {
                    ++other;
                }

                if (other > 1 || keychain > 1) {
                    return false;
                }
            }
        }

        return keychain == 1 && other == 1;
    }

    @Override
    public ItemStack assemble(CraftingInventory inventory) {
        ItemStack keychain = ItemStack.EMPTY;
        ItemStack other = ItemStack.EMPTY;

        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof KeychainItem) {
                    keychain = stack.copy();
                    keychain.setCount(1);
                } else {
                    other = stack.copy();
                    other.setCount(1);
                }
            }
        }
        if (keychain.getItem() instanceof KeychainItem && !other.isEmpty()) {
            CompoundNBT tag = new CompoundNBT();
            if(keychain.hasTag())
                tag = keychain.getTag();

            ListNBT listtag = new ListNBT();

            if (!other.isEmpty()) {
                CompoundNBT compoundtag = new CompoundNBT();
                compoundtag.putByte("Slot", (byte)0);
                other.save(compoundtag);
                listtag.add(compoundtag);

            }

            tag.put("Items", listtag);

            keychain.setTag(tag);
        }
        System.out.println(other);

        return keychain;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width >= 2 && height >= 1;
    }
    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}