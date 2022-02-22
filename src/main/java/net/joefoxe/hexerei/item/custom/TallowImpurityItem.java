package net.joefoxe.hexerei.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;


import net.minecraft.item.Item.Properties;

public class TallowImpurityItem extends Item {

    public static Food FOOD = new Food.Builder().saturationMod(1).nutrition(1).alwaysEat().build();

    public TallowImpurityItem(Properties properties) {
        super(properties.food(FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entityLiving) {
//        if (!level.isClientSide && entityLiving instanceof ServerPlayer) {
//            ServerPlayer player = (ServerPlayer) entityLiving;
//            ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
//            ItemStack itemstack = ((ServerPlayer) entityLiving).getItemInHand(InteractionHand.MAIN_HAND);
//            entityLiving.curePotionEffects(new ItemStack(Items.MILK_BUCKET));
//            if (itemstack.isEmpty()) {
//                player.setItemInHand(InteractionHand.MAIN_HAND, itemstack3);
//            } else if (!player.inventory.add(itemstack3)) {
//                player.drop(itemstack3, false);
//            } else if (player instanceof ServerPlayer) {
//                player.initMenu(player.containerMenu);
//            }
//        }

        return super.finishUsingItem(stack, world, entityLiving);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        tooltip.add(new TranslationTextComponent("tooltip.hexerei.tallow_impurity_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));


        super.appendHoverText(stack, world, tooltip, flagIn);
    }

}
