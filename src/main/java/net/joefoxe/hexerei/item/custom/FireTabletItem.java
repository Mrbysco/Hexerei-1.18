package net.joefoxe.hexerei.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;

import net.minecraft.item.Item.Properties;

public class FireTabletItem extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NO_SPECTATORS.and(Entity::canBeCollidedWith);

    public FireTabletItem(Properties properties) {
        super(properties);
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #use}.
     */

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_shift"));
        } else {
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom"));
        }


        super.appendHoverText(stack, world, tooltip, flagIn);
    }
}