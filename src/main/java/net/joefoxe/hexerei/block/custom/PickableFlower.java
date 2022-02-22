package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.item.custom.FlowerOutputItem;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Effect;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.RegistryObject;

import java.util.Random;

public class PickableFlower extends BushBlock implements IGrowable {
    protected static final float AABB_OFFSET = 3.0F;
    protected static final VoxelShape SHAPE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 10.0D, 11.0D);
    private final Effect suspiciousStewEffect;
    private final int effectDuration;
    public static final int MAX_AGE = 3;
    public int type;
    public RegistryObject<FlowerOutputItem> firstOutput;
    public int maxFirstOutput;
    public RegistryObject<FlowerOutputItem> secondOutput;
    public int maxSecondOutput;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public PickableFlower(Effect p_53512_, int p_53513_, AbstractBlock.Properties p_53514_, RegistryObject<FlowerOutputItem> firstOutput , int maxFirstOutput, RegistryObject<FlowerOutputItem> secondOutput , int maxSecondOutput) {
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

        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, Integer.valueOf(0)));

    }
    public PickableFlower(Effect p_53512_, int p_53513_, AbstractBlock.Properties p_53514_, RegistryObject<FlowerOutputItem> firstOutput , int maxFirstOutput) {
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
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }

    public AbstractBlock.OffsetType getOffsetType() {
        return AbstractBlock.OffsetType.XZ;
    }

    public Effect getSuspiciousStewEffect() {
        return this.suspiciousStewEffect;
    }

    public int getEffectDuration() {
        return this.effectDuration;
    }

//    public VoxelShape getShape(BlockState p_57291_, BlockGetter p_57292_, BlockPos p_57293_, CollisionContext p_57294_) {
//        if (p_57291_.getValue(AGE) == 0) {
//            return SAPLING_SHAPE;
//        } else {
//            return p_57291_.getValue(AGE) < 3 ? MID_GROWTH_SHAPE : super.getShape(p_57291_, p_57292_, p_57293_, p_57294_);
//        }
//    }

    public boolean isRandomlyTicking(BlockState p_57284_) {
        return p_57284_.getValue(AGE) < 3;
    }

    public void randomTick(BlockState p_57286_, ServerWorld p_57287_, BlockPos p_57288_, Random p_57289_) {
        int i = p_57286_.getValue(AGE);
        if (i < 3 && p_57287_.getRawBrightness(p_57288_.above(), 0) >= 9 && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(p_57287_, p_57288_, p_57286_,p_57289_.nextInt(5) == 0)) {
            p_57287_.setBlock(p_57288_, p_57286_.setValue(AGE, Integer.valueOf(i + 1)), 2);
            net.minecraftforge.common.ForgeHooks.onCropsGrowPost(p_57287_, p_57288_, p_57286_);
        }

    }

    public ActionResultType use(BlockState p_57275_, World p_57276_, BlockPos p_57277_, PlayerEntity p_57278_, Hand p_57279_, BlockRayTraceResult p_57280_) {
        int i = p_57275_.getValue(AGE);
        boolean flag = i == 3;
        if (!flag && p_57278_.getItemInHand(p_57279_).getItem() == Items.BONE_MEAL) {
            return ActionResultType.PASS;
        } else if (i > 1) {

            ItemStack firstOutput = new ItemStack(this.firstOutput.get(), this.maxFirstOutput);
            ItemStack secondOutput = ItemStack.EMPTY;
            if(this.secondOutput != null)
                secondOutput = new ItemStack(this.secondOutput.get(), this.maxSecondOutput);
            int j = Math.max(1, p_57276_.random.nextInt(firstOutput.getCount()));
            int k = 0;
            if(this.secondOutput != null)
                k = Math.max(1, p_57276_.random.nextInt(secondOutput.getCount()));
            popResource(p_57276_, p_57277_, new ItemStack(firstOutput.getItem(), (int)Math.floor(j/2f) + (flag ? (int)Math.ceil(j/2f) : 0)));
            if (p_57276_.random.nextInt(2) == 0 && this.secondOutput != null)
                popResource(p_57276_, p_57277_, new ItemStack(secondOutput.getItem(), (int)Math.floor(k/2f) + (flag ? (int)Math.ceil(k/2f) : 0)));
            p_57276_.playSound((PlayerEntity) null, p_57277_, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, 1.0F, 0.8F + p_57276_.random.nextFloat() * 0.4F);
            p_57276_.setBlock(p_57277_, p_57275_.setValue(AGE, Integer.valueOf(0)), 2);


            return ActionResultType.sidedSuccess(p_57276_.isClientSide);
        } else {
            return super.use(p_57275_, p_57276_, p_57277_, p_57278_, p_57279_, p_57280_);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_57282_) {
        p_57282_.add(AGE);
    }

    public boolean isValidBonemealTarget(IBlockReader p_57260_, BlockPos p_57261_, BlockState p_57262_, boolean p_57263_) {
        return p_57262_.getValue(AGE) < 3;
    }

    public boolean isBonemealSuccess(World p_57265_, Random p_57266_, BlockPos p_57267_, BlockState p_57268_) {
        return true;
    }

    public void performBonemeal(ServerWorld p_57251_, Random p_57252_, BlockPos p_57253_, BlockState p_57254_) {
        int i = Math.min(3, p_57254_.getValue(AGE) + 1);
        p_57251_.setBlock(p_57253_, p_57254_.setValue(AGE, Integer.valueOf(i)), 2);
    }
}