package net.joefoxe.hexerei.item.custom;

import net.minecraft.world.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemUseContext;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.level.Level;

import net.minecraft.util.ActionResult;

public class FloweringLilyPadItem extends BlockItem {
    public FloweringLilyPadItem(Block p_43436_, Item.Properties p_43437_) {
        super(p_43436_, p_43437_);
    }

//    public FloweringLilyPadItem(Block blockIn, Item.Properties builder) {
//        super(blockIn, builder);
//    }


//    public InteractionResult useOn(UseOnContext context) {
//        return InteractionResult.PASS;
//    }
//
//    /**
//     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
//     * {@link #useOn}.
//     */
//    public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
//        BlockHitResult blockraytraceresult = getPlayerPOVHitResult(worldIn, playerIn, ClipContext.Fluid.SOURCE_ONLY);
//        BlockHitResult blockraytraceresult1 = blockraytraceresult.withPosition(blockraytraceresult.getBlockPos().above());
////        if(worldIn.getFluidState(blockraytraceresult.getPos()).getType() != Fluids.WATER)
////            return new InteractionResultHolder<>(InteractionResult.PASS, playerIn.getItemInHand(handIn));
//        InteractionResult actionresulttype = super.useOn(new UseOnContext(playerIn, handIn, blockraytraceresult1));
//        return new InteractionResultHolder<>(actionresulttype, playerIn.getItemInHand(handIn));
//    }



    public ActionResultType useOn(ItemUseContext p_43439_) {
        return ActionResultType.PASS;
    }

    public ActionResult<ItemStack> use(World p_43441_, PlayerEntity p_43442_, Hand p_43443_) {
        BlockRayTraceResult blockhitresult = getPlayerPOVHitResult(p_43441_, p_43442_, RayTraceContext.FluidMode.SOURCE_ONLY);
        BlockRayTraceResult blockhitresult1 = blockhitresult.withPosition(blockhitresult.getBlockPos().above());
        ActionResultType interactionresult = super.useOn(new ItemUseContext(p_43442_, p_43443_, blockhitresult1));
        return new ActionResult<>(interactionresult, p_43442_.getItemInHand(p_43443_));
    }
}
