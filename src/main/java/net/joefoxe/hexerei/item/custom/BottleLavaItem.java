package net.joefoxe.hexerei.item.custom;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.item.Item.Properties;

public class BottleLavaItem extends Item {

    public static Food FOOD = new Food.Builder().saturationMod(0).nutrition(0).alwaysEat().build();

    public BottleLavaItem(Properties properties) {
        super(properties.food(FOOD));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, World world, LivingEntity entityLiving) {
        if (!world.isClientSide && entityLiving instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entityLiving;
            ItemStack itemstack3 = new ItemStack(Items.GLASS_BOTTLE);
            ItemStack itemstack = ((ServerPlayerEntity) entityLiving).getItemInHand(Hand.MAIN_HAND);
            entityLiving.setSecondsOnFire(10);
            if (itemstack.isEmpty()) {
                player.setItemInHand(Hand.MAIN_HAND, itemstack3);
            } else if (!player.inventory.add(itemstack3)) {
                player.drop(itemstack3, false);
            } else if (player instanceof ServerPlayerEntity) {
                player.initMenu(player.containerMenu);
            }
        }

        return super.finishUsingItem(stack, world, entityLiving);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        super.inventoryTick(stack, world, entity, itemSlot, isSelected);

        Random rand = new Random();

        if(rand.nextDouble() > 0.5d) {
            stack.hurtAndBreak(1, (PlayerEntity) entity, player -> player.broadcastBreakEvent(Hand.MAIN_HAND));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            tooltip.add(new TranslationTextComponent("tooltip.hexerei.bottle_lava_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.bottle_lava"));
        }


        super.appendHoverText(stack, world, tooltip, flagIn);
    }
}

