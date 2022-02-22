package net.joefoxe.hexerei.item;

import net.joefoxe.hexerei.block.ModBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

public class ModItemGroup {

    public static final ItemGroup HEXEREI_GROUP = new ItemGroup("hexereiModTab")
    {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.MIXING_CAULDRON.get().asItem());
        }

    };
}
