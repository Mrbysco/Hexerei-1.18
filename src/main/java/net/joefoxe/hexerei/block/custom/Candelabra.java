package net.joefoxe.hexerei.block.custom;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.block.Blocks;
import net.minecraft.util.SoundEvents;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.block.BlockRenderType;
import net.minecraft.util.Direction;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.projectile.ProjectileEntity;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.state.properties.BlockStateProperties;

public class Candelabra extends Block implements IWaterLoggable {

    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderShape(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(HorizontalBlock.FACING, context.getHorizontalDirection()).setValue(LIT, Boolean.valueOf(false));
                }
            }
        }

        return null;
    }

    // hitbox REMEMBER TO DO THIS
    public static final VoxelShape GROUNDED_SHAPE = Stream.of(
            Block.box(6.5, 11, 6.5, 9.5, 12, 9.5),
            Block.box(1, 5.5, 7, 3, 7.5, 9),
            Block.box(13, 8.5, 7, 15, 12.5, 9),
            Block.box(6.5, 1, 6.5, 9.5, 2, 9.5),
            Block.box(1, 8.5, 7, 3, 12.5, 9),
            Block.box(3, 5.5, 7.01, 13, 6.5, 9.01),
            Block.box(12.5, 7.5, 6.5, 15.5, 8.5, 9.5),
            Block.box(7, 2, 7, 9, 11, 9),
            Block.box(13, 5.5, 7, 15, 7.5, 9),
            Block.box(6.5, 9.5, 12.5, 9.5, 10.5, 15.5),
            Block.box(0.5, 7.5, 6.5, 3.5, 8.5, 9.5),
            Block.box(7, 10.5, 13, 9, 14.5, 15),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5),
            Block.box(7, 10.5, 1, 9, 14.5, 3),
            Block.box(7, 12, 7, 9, 16, 9),
            Block.box(7, 7.5, 13, 9, 9.5, 15),
            Block.box(7.01, 7.5, 3, 9.01, 8.5, 13),
            Block.box(7, 7.5, 1, 9, 9.5, 3),
            Block.box(6.5, 9.5, 0.5, 9.5, 10.5, 3.5)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape HANGING_SHAPES = Stream.of(
            Block.box(6.5, 11, 6.5, 9.5, 12, 9.5),
            Block.box(1, 5.5, 7, 3, 7.5, 9),
            Block.box(13, 8.5, 7, 15, 12.5, 9),
            Block.box(1, 8.5, 7, 3, 12.5, 9),
            Block.box(3, 5.5, 7.01, 13, 6.5, 9.01),
            Block.box(12.5, 7.5, 6.5, 15.5, 8.5, 9.5),
            Block.box(7, 2, 7, 9, 11, 9),
            Block.box(7, 12, 7, 9, 16, 9),
            Block.box(13, 5.5, 7, 15, 7.5, 9),
            Block.box(6.5, 9.5, 12.5, 9.5, 10.5, 15.5),
            Block.box(0.5, 7.5, 6.5, 3.5, 8.5, 9.5),
            Block.box(7, 10.5, 13, 9, 14.5, 15),
            Block.box(7, 10.5, 1, 9, 14.5, 3),
            Block.box(7, 7.5, 13, 9, 9.5, 15),
            Block.box(7.01, 7.5, 3, 9.01, 8.5, 13),
            Block.box(7, 7.5, 1, 9, 9.5, 3),
            Block.box(6.5, 9.5, 0.5, 9.5, 10.5, 3.5),
            Block.box(7.5, 0, 7.5, 8.5, 2, 8.5)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape GROUNDED_SHAPE_TURNED =Stream.of(
            Block.box(6.5, 11, 6.5, 9.5, 12, 9.5),
            Block.box(7, 5.5, 13, 9, 7.5, 15),
            Block.box(7, 8.5, 1, 9, 12.5, 3),
            Block.box(6.5, 1, 6.5, 9.5, 2, 9.5),
            Block.box(7, 8.5, 13, 9, 12.5, 15),
            Block.box(7, 5.5, 3, 9.01, 6.5, 13),
            Block.box(6.5, 7.5, 0.5, 9.5, 8.5, 3.5),
            Block.box(7, 2, 7, 9, 11, 9),
            Block.box(7, 5.5, 1, 9, 7.5, 3.),
            Block.box(12.5, 9.5, 6.5, 15.5, 10.5, 9.5),
            Block.box(6.5, 7.5, 12.5, 9.5, 8.5, 15.5),
            Block.box(13, 10.5, 7, 15, 14.5, 9.),
            Block.box(5.5, 0, 5.5, 10.5, 1, 10.5),
            Block.box(1, 10.5, 7, 3, 14.5, 9),
            Block.box(7, 12, 7, 9, 16, 9),
            Block.box(13, 7.5, 7, 15, 9.5, 9),
            Block.box(3, 7.5, 7, 13, 8.5, 9),
            Block.box(1, 7.5, 7, 3, 9.5, 9),
            Block.box(0.5, 9.5, 6.5, 3.5, 10.5, 9.5)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape HANGING_SHAPES_TURNED = Stream.of(
            Block.box(6.5, 11, 6.5, 9.5, 12, 9.5),
            Block.box(7, 5.5, 13.0, 9, 7.5, 15.0),
            Block.box(7, 8.5, 1.03, 9, 12.5, 3.0),
            Block.box(7, 8.5, 13.0, 9, 12.5, 15.0),
            Block.box(7.01, 5.5, 3, 9, 6.5, 13.0),
            Block.box(6.5, 7.5, 0.5, 9.5, 8.5, 3.5),
            Block.box(7, 2, 7.0, 9, 11, 9.0),
            Block.box(7, 12, 7.0, 9, 16, 9.0),
            Block.box(7, 5.5, 1.03, 9, 7.5, 3),
            Block.box(12.5, 9.5, 6.5, 15.5, 10.5, 9.5),
            Block.box(6.5, 7.5, 12.5, 9.5, 8.5, 15.5),
            Block.box(13, 10.5, 7.0, 15, 14.5, 9.0),
            Block.box(1, 10.5, 7.0, 3, 14.5, 9.0),
            Block.box(13, 7.5, 7.0, 15, 9.5, 9.0),
            Block.box(3, 7.5, 7, 13, 8.5, 9),
            Block.box(1, 7.5, 7.0, 3, 9.5, 9.0),
            Block.box(0.5, 9.5, 6.5, 3.5, 10.5, 9.5),
            Block.box(7.5, 0, 7.5, 8.5, 2, 8.5)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return state.getValue(HorizontalBlock.FACING) == Direction.NORTH || state.getValue(HorizontalBlock.FACING) == Direction.SOUTH ? (state.getValue(HANGING) ? HANGING_SHAPES : GROUNDED_SHAPE) : (state.getValue(HANGING) ? HANGING_SHAPES_TURNED : GROUNDED_SHAPE_TURNED);
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Random random = new Random();
        if(itemstack.getItem() == Items.FLINT_AND_STEEL)
        {
            if (canBeLit(state)) {

                worldIn.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                worldIn.playSound((PlayerEntity) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 1.0F);
                itemstack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(handIn));

                return ActionResultType.sidedSuccess(worldIn.isClientSide());
            }

        }
        return net.minecraft.util.ActionResultType.PASS;
    }

    public static boolean canBeLit(BlockState state) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && !state.getValue(BlockStateProperties.LIT);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public Candelabra(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(LIT, Boolean.valueOf(false)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(net.minecraft.block.HorizontalBlock.FACING, HANGING, WATERLOGGED, LIT);
    }

//    @Override
//    public void entityInside(BlockState state, Level worldIn, BlockPos pos, Entity entityIn) {
//        if (!entityIn.isImmuneToFire() && state.getValue(LIT) && entityIn instanceof LivingEntity && !EnchantmentHelper.hasFrostWalker((LivingEntity)entityIn)) {
//            entityIn.hurt(DamageSource.IN_FIRE, 0.25f);
//        }
//
//        super.entityInside(state, worldIn, pos, entityIn);
//    }

    public static void extinguish(IWorld world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                spawnSmokeParticles((World)world, pos, true);
            }
        }
    }

    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            boolean flag = state.getValue(LIT);
            if (flag) {
                if (!worldIn.isClientSide()) {
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                extinguish(worldIn, pos, state);
            }

            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.valueOf(true)).setValue(LIT, Boolean.valueOf(false)), 3);
            worldIn.scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
        } else {
            return false;
        }
    }

    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (!worldIn.isClientSide && projectile.isOnFire()) {
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entity);
            if (flag && !state.getValue(LIT) && !state.getValue(WATERLOGGED)) {
                BlockPos blockpos = hit.getBlockPos();
                worldIn.setBlock(blockpos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
            }
        }

    }

    public static void spawnSmokeParticles(World worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.CAMPFIRE_COSY_SMOKE;
        worldIn.addParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }

    public static boolean isLit(BlockState state) {
        return state.hasProperty(LIT) && state.getValue(LIT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        Direction direction = getBlockConnected(state).getOpposite();
        return Block.canSupportCenter(worldIn, pos.relative(direction), direction.getOpposite());
    }

    protected static Direction getBlockConnected(BlockState state) {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return !state.getValue(WATERLOGGED);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.candelabra_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

//    @Override
//    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
//        super.onBlockExploded(state, world, pos, explosion);
//
//        if (world instanceof ServerLevel) {
//            ItemStack cloneItemStack = getItem(world, pos, state);
//            if (world.getBlockState(pos) != state && !level.isClientSide()) {
//                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, cloneItemStack));
//            }
//
//        }
//    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
        if (state.getValue(LIT)) {
            if (rand.nextInt(10) == 0) {
                world.playSound(null,(double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, 0.5F + rand.nextFloat()/2, rand.nextFloat() * 0.7F + 0.6F);
            }

            if(!state.getValue(HANGING)) {
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f, pos.getY() + 17f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f, pos.getY() + 17f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);
            }

            if(state.getValue(HorizontalBlock.FACING) == Direction.EAST || state.getValue(HorizontalBlock.FACING) == Direction.WEST) {

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f + 6f / 16f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f + 6f / 16f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f - 6f / 16f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f - 6f / 16f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f + 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f + 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f - 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f - 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);
            }
            if(state.getValue(HorizontalBlock.FACING) == Direction.NORTH || state.getValue(HorizontalBlock.FACING) == Direction.SOUTH) {

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f + 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f + 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f - 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f, pos.getY() + 16f / 16f, pos.getZ() + 0.5f - 6f / 16f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f + 6f / 16f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f + 6f / 16f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);

                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5f - 6f / 16f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.01d, (rand.nextDouble() - 0.5d) / 100d);
                if (rand.nextInt(3) == 0)
                    world.addParticle(ParticleTypes.SMOKE, pos.getX() + 0.5f - 6f / 16f, pos.getY() + 14f / 16f, pos.getZ() + 0.5f, (rand.nextDouble() - 0.5d) / 100d, (rand.nextDouble() + 0.5d) * 0.035d, (rand.nextDouble() - 0.5d) / 100d);
            }


        }
    }
//
//    private MenuProvider createContainerProvider(Level worldIn, BlockPos pos) {
//        return new MenuProvider() {
//            @Override
//            public Component getDisplayName() {
//                if(((CofferTile)worldIn.getBlockEntity(pos)).customName != null)
//                    return new TranslatableComponent(((CofferTile)worldIn.getBlockEntity(pos)).customName.getString());
//                return new TranslatableComponent("screen.hexerei.coffer");
//            }
//
//            @Nullable
//            @Override
//            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
//                return new CofferContainer(i, worldIn, pos, playerInventory, playerEntity);
//            }
//        };
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity createTileEntity(BlockState state, BlockGetter world) {
//        BlockEntity te = ModTileEntities.COFFER_TILE.get().create();
//        return te;
//    }
//
//    @Override
//    public boolean hasBlockEntity(BlockState state) {
//        return true;
//    }
//
//    @Override
//    public Class<CofferTile> getTileEntityClass() {
//        return CofferTile.class;
//    }
}
