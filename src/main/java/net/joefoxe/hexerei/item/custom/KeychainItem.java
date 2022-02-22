package net.joefoxe.hexerei.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


import net.minecraft.item.Item.Properties;

public class KeychainItem extends Item {


    public NonNullList<ItemStack> containedItem = NonNullList.withSize(1, ItemStack.EMPTY);

    public KeychainItem(Properties properties) {
        super(properties);

    }
//ContainerHelper.saveAllItems(tag, this.items);




    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT inv = stack.getOrCreateTag();
        ListNBT tagList = inv.getList("Items", INBT.TAG_COMPOUND);
        CompoundNBT compoundtag = tagList.getCompound(0);
        CompoundNBT itemTags = tagList.getCompound(0);

        TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(compoundtag).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));

        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            if(!ItemStack.of(itemTags).isEmpty())
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.keychain_with_item").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            else
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.keychain_without_item").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_attachments").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        }


        if(!ItemStack.of(itemTags).isEmpty()) {
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.keychain_contains", itemText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        }

        super.appendHoverText(stack, world, tooltip, flagIn);
    }

}
