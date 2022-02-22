package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.item.custom.FlowerOutputItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Mth;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.potion.Effect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelReader;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.block.DoublePlantBlock;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.system.CallbackI;

import java.util.Random;

import net.minecraft.block.AbstractBlock.OffsetType;
import net.minecraft.block.AbstractBlock.Properties;

public class PickableDoubleFlower extends DoublePlantBlock implements IGrowable {
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    protected static final VoxelShape SHAPE_BOTTOM = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
    private final Effect suspiciousStewEffect;
    private final int effectDuration;
    public static final int MAX_AGE = 3;
    public int type;
    public RegistryObject<FlowerOutputItem> firstOutput;
    public int maxFirstOutput;
    public RegistryObject<FlowerOutputItem> secondOutput;
    public int maxSecondOutput;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public PickableDoubleFlower(Effect p_53512_, int p_53513_, Properties p_53514_, RegistryObject<FlowerOutputItem> firstOutput , int maxFirstOutput, RegistryObject<FlowerOutputItem> secondOutput , int maxSecondOutput) {
        super(p_53514_);
        this.suspiciousStewEffect = p_53512_;
        if (p_53512_.isInstantenous()) {
            this.effectDuration = p_53513_;
        } else {
            this.effectDuration = p_53513_ * 20;
        }

        this.firstOutput = firstOutput;
        this.maxFirstOutput = maxFirstOutput;
        this.secondOutput = secondOutput;
        this.maxSecondOutput = maxSecondOutput;

        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER));

    }
    public PickableDoubleFlower(Effect p_53512_, int p_53513_, Properties p_53514_, RegistryObject<FlowerOutputItem> firstOutput , int maxFirstOutput) {
        super(p_53514_);
        this.suspiciousStewEffect = p_53512_;
        if (p_53512_.isInstantenous()) {
            this.effectDuration = p_53513_;
        } else {
            this.effectDuration = p_53513_ * 20;
        }

        this.firstOutput = firstOutput;
        this.maxFirstOutput = maxFirstOutput;
        this.secondOutput = null;

        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));

    }

    public VoxelShape getShape(BlockState p_53517_, IBlockReader p_53518_, BlockPos p_53519_, ISelectionContext p_53520_) {
        Vector3d vec3 = p_53517_.getOffset(p_53518_, p_53519_);
        if(p_53517_.getValue(HALF) == DoubleBlockHalf.LOWER)
            return SHAPE_BOTTOM.move(vec3.x, vec3.y, vec3.z);
        else
            return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    public Effect getSuspiciousStewEffect() {
        return this.suspiciousStewEffect;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 3;
    }

    public void randomTick(BlockState p_57286_, ServerWorld p_57287_, BlockPos p_57288_, Random p_57289_) {
        int i = p_57286_.getValue(AGE);
        if(p_57286_.getValue(HALF) == DoubleBlockHalf.LOWER){
            if (i < 3 && p_57287_.getRawBrightness(p_57288_.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57287_, p_57288_, p_57286_, p_57289_.nextInt(10) == 0)) {

                p_57287_.setBlock(p_57288_, p_57286_.setValue(AGE, Integer.valueOf(i + 1)), 2);
                p_57287_.setBlock(p_57288_.above(), p_57287_.getBlockState(p_57288_.above()).setValue(AGE, Integer.valueOf(i + 1)), 2);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_57287_, p_57288_, p_57286_);
            }
        }
    }

    public ActionResultType use(BlockState p_57275_, World p_57276_, BlockPos p_57277_, PlayerEntity p_57278_, Hand p_57279_, BlockRayTraceResult p_57280_) {
        int i = p_57275_.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && p_57278_.getItemInHand(p_57279_).is(Items.BONE_MEAL)) {
            return ActionResultType.PASS;
        } else if (i > 1) {

            ItemStack firstOutput = new ItemStack(this.firstOutput.get(), 4);
            ItemStack secondOutput = ItemStack.EMPTY;
            if(this.secondOutput != null)
                secondOutput = new ItemStack(this.secondOutput.get(), this.maxSecondOutput);
            int j = Math.max(1, p_57276_.random.nextInt(firstOutput.getCount()));
            int k = 0;
            if(this.secondOutput != null)
                k = Math.max(1, p_57276_.random.nextInt(secondOutput.getCount()));
            popResource(p_57276_, p_57277_, new ItemStack(firstOutput.getItem(), Math.max(1,(int)Math.floor(j/2f)) + (flag ? (int)Math.ceil(j/2f) : 0)));
            if (p_57276_.random.nextInt(2) == 0 && this.secondOutput != null)
                popResource(p_57276_, p_57277_, new ItemStack(secondOutput.getItem(), Math.max(1,(int)Math.floor(k/2f)) + (flag ? (int)Math.ceil(k/2f) : 0)));
            p_57276_.playSound((PlayerEntity) null, p_57277_, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + p_57276_.random.nextFloat() * 0.4F);
            if(p_57275_.getValue(HALF) == DoubleBlockHalf.LOWER) {
                p_57276_.setBlock(p_57277_, p_57275_.setValue(AGE, Integer.valueOf(0)), 2);

                BlockState blockState = p_57276_.getBlockState(p_57277_.above());

                if(blockState.getBlock() == p_57276_.getBlockState(p_57277_).getBlock())
                {
                    p_57276_.setBlock(p_57277_.above(), blockState.setValue(AGE, Integer.valueOf(0)), 2);
                }
                else if(blockState.isAir()){
                    p_57276_.setBlock(p_57277_.above(), p_57276_.getBlockState(p_57277_).setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.UPPER), 2);
                }

            }else {
                p_57276_.setBlock(p_57277_, p_57275_.setValue(AGE, Integer.valueOf(0)), 2);
//                p_57276_.setBlock(p_57277_.below(), p_57276_.getBlockState(p_57277_.below()).setValue(AGE, Integer.valueOf(0)), 2);

                BlockState blockState = p_57276_.getBlockState(p_57277_.below());

                if(blockState.getBlock() == p_57276_.getBlockState(p_57277_).getBlock())
                {
                    p_57276_.setBlock(p_57277_.below(), blockState.setValue(AGE, Integer.valueOf(0)), 2);
                }
                else if(blockState.isAir()){
                    p_57276_.setBlock(p_57277_.below(), p_57276_.getBlockState(p_57277_).setValue(AGE, Integer.valueOf(0)).setValue(HALF, DoubleBlockHalf.LOWER), 2);
                }
            }


            return ActionResultType.sidedSuccess(p_57276_.isClientSide);
        } else {
            return super.use(p_57275_, p_57276_, p_57277_, p_57278_, p_57279_, p_57280_);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_57282_) {
        p_57282_.add(AGE).add(HALF);
    }

    public boolean isValidBonemealTarget(IBlockReader p_57260_, BlockPos p_57261_, BlockState p_57262_, boolean p_57263_) {
        return p_57262_.getValue(AGE) < 3;
    }

    public boolean isBonemealSuccess(World p_57265_, Random p_57266_, BlockPos p_57267_, BlockState p_57268_) {
        return true;
    }

    public void performBonemeal(ServerWorld level, Random random, BlockPos blockPos, BlockState blockState) {


        if(blockState.getValue(HALF) == DoubleBlockHalf.LOWER) {
            int i = Math.min(3, blockState.getValue(AGE) + 1);
            level.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(i)), 2);
            level.setBlock(blockPos.above(), level.getBlockState(blockPos.above()).setValue(AGE, Integer.valueOf(i)), 2);
        }else {
            int i = Math.min(3, level.getBlockState(blockPos.below()).getValue(AGE) + 1);
            level.setBlock(blockPos, blockState.setValue(AGE, Integer.valueOf(i)), 2);
            level.setBlock(blockPos.below(), level.getBlockState(blockPos.below()).setValue(AGE, Integer.valueOf(i)), 2);
        }
    }
}