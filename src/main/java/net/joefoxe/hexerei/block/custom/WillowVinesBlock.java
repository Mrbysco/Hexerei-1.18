package net.joefoxe.hexerei.block.custom;



import java.util.Random;

import net.joefoxe.hexerei.block.ModBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.level.block.*;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.VoxelShape;

import net.minecraft.block.AbstractTopPlantBlock;
import net.minecraft.block.Block;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PlantBlockHelper;

public class WillowVinesBlock extends AbstractTopPlantBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 9.0D, 4.0D, 12.0D, 16.0D, 12.0D);

    public WillowVinesBlock(AbstractBlock.Properties p_154966_) {
        super(p_154966_, Direction.DOWN, SHAPE, false, 0.1D);
    }

    protected int getBlocksToGrowWhenBonemealed(Random p_154968_) {
        return PlantBlockHelper.getBlocksToGrowWhenBonemealed(p_154968_);
    }

    protected Block getBodyBlock() {
        return ModBlocks.WILLOW_VINES_PLANT.get();
    }

    protected boolean canGrowInto(BlockState p_154971_) {
        return PlantBlockHelper.isValidGrowthState(p_154971_);
    }


    @Override
    public boolean canSurvive(BlockState p_53876_, IWorldReader p_53877_, BlockPos p_53878_) {
        BlockPos blockpos = p_53878_.relative(this.growthDirection.getOpposite());
        BlockState blockstate = p_53877_.getBlockState(blockpos);
        if (!this.canAttachTo(blockstate)) {
            return false;
        } else {
            return blockstate.is(this.getHeadBlock()) || blockstate.is(this.getBodyBlock()) || blockstate.isFaceSturdy(p_53877_, blockpos, this.growthDirection) || blockstate.getBlock() instanceof LeavesBlock;
        }
    }
}