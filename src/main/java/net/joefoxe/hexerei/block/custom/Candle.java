package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.block.ITileEntity;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.tileentity.*;
import net.minecraft.util.Direction;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.Blocks;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class Candle extends ContainerBlock implements ITileEntity<CandleTile>, ITileEntityProvider, IWaterLoggable {

    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 4);
    public static final IntegerProperty CANDLES_LIT = IntegerProperty.create("candles_lit", 0, 4);
    public static final IntegerProperty SLOT_ONE_TYPE = IntegerProperty.create("slot_one_type", 0, 10);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty PLAYER_NEAR = BooleanProperty.create("player_near");

    protected static final VoxelShape ONE_SHAPE = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 9.0D, 10.0D);
    protected static final VoxelShape TWO_SHAPE = Block.box(3.5D, 0.0D, 3.5D, 12.5D, 9.0D, 12.5D);
    protected static final VoxelShape THREE_SHAPE = Block.box(3.5D, 0.0D, 3.5D, 12.5D, 9.0D, 12.5D);
    protected static final VoxelShape FOUR_SHAPE = Block.box(3.5D, 0.0D, 3.5D, 12.5D, 9.0D, 12.5D);
    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderShape(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        int candleType = 1;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_BLUE.get().asItem())
            candleType = 2;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_BLACK.get().asItem())
            candleType = 3;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_LIME.get().asItem())
            candleType = 4;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_ORANGE.get().asItem())
            candleType = 5;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_PINK.get().asItem())
            candleType = 6;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_PURPLE.get().asItem())
            candleType = 7;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_RED.get().asItem())
            candleType = 8;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_CYAN.get().asItem())
            candleType = 9;
        if(context.getItemInHand().getItem() == ModBlocks.CANDLE_YELLOW.get().asItem())
            candleType = 10;
        if (blockstate.is(ModBlocks.CANDLE.get())
                || blockstate.is(ModBlocks.CANDLE_BLUE.get())
                || blockstate.is(ModBlocks.CANDLE_BLACK.get())
                || blockstate.is(ModBlocks.CANDLE_LIME.get())
                || blockstate.is(ModBlocks.CANDLE_ORANGE.get())
                || blockstate.is(ModBlocks.CANDLE_PINK.get())
                || blockstate.is(ModBlocks.CANDLE_PURPLE.get())
                || blockstate.is(ModBlocks.CANDLE_RED.get())
                || blockstate.is(ModBlocks.CANDLE_CYAN.get())
                || blockstate.is(ModBlocks.CANDLE_YELLOW.get())) {


            if(context.getLevel().getBlockEntity(context.getClickedPos()) instanceof CandleTile)
            {
                CandleTile tile = (CandleTile) context.getLevel().getBlockEntity(context.getClickedPos());
                if(tile.candleType2 == 0) {
                    tile.candleType2 = candleType;
                    tile.candleHeight2 = 7;
                    tile.candleMeltTimer2 = tile.candleMeltTimerMAX;
                }
                else if(tile.candleType3 == 0) {
                    tile.candleType3 = candleType;
                    tile.candleHeight3 = 7;
                    tile.candleMeltTimer3 = tile.candleMeltTimerMAX;
                }
                else if(tile.candleType4 == 0) {
                    tile.candleType4 = candleType;
                    tile.candleHeight4 = 7;
                    tile.candleMeltTimer4 = tile.candleMeltTimerMAX;
                }
            }
            return blockstate.setValue(CANDLES, Integer.valueOf(Math.min(4, blockstate.getValue(CANDLES) + 1)));

        } else {
            FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
            boolean flag = fluidstate.getType() == Fluids.WATER;

            return super.getStateForPlacement(context).setValue(WATERLOGGED, Boolean.valueOf(flag)).setValue(HorizontalBlock.FACING, context.getHorizontalDirection()).setValue(SLOT_ONE_TYPE, candleType).setValue(CANDLES_LIT, 0);
        }
    }


    @Override
    public boolean canBeReplaced(BlockState state, BlockItemUseContext useContext) {
        return (useContext.getItemInHand().getItem() == ModBlocks.CANDLE.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_BLUE.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_BLACK.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_LIME.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_ORANGE.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_PINK.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_PURPLE.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_RED.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_CYAN.get().asItem()
                || useContext.getItemInHand().getItem() == ModBlocks.CANDLE_YELLOW.get().asItem())
                && state.getValue(CANDLES) < 4 ? true : super.canBeReplaced(state, useContext);
    }

    public void dropCandles(World world, BlockPos pos) {

        TileEntity entity = world.getBlockEntity(pos);
        if(entity instanceof CandleTile && !world.isClientSide()) {
            CandleTile candleTile = (CandleTile) entity;
            ItemStack itemStack = new ItemStack(ModBlocks.CANDLE.get());
            if(7 - candleTile.candleHeight1 < 1) {
                if (candleTile.candleType1 == 1) {
                    itemStack = new ItemStack(ModBlocks.CANDLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 2) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLUE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 3) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLACK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 4) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_LIME.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 5) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_ORANGE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 6) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PINK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 7) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PURPLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 8) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_RED.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 9) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_CYAN.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType1 == 10) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_YELLOW.get());
                    popResource((ServerWorld) world, pos, itemStack);
                }
            }


            if(7 - candleTile.candleHeight2 < 1) {
                if (candleTile.candleType2 == 1) {
                    itemStack = new ItemStack(ModBlocks.CANDLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 2) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLUE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 3) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLACK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 4) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_LIME.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 5) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_ORANGE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 6) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PINK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 7) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PURPLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 8) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_RED.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 9) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_CYAN.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType2 == 10) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_YELLOW.get());
                    popResource((ServerWorld) world, pos, itemStack);
                }
            }


            if(7 - candleTile.candleHeight3 < 1) {
                if (candleTile.candleType3 == 1) {
                    itemStack = new ItemStack(ModBlocks.CANDLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 2) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLUE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 3) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLACK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 4) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_LIME.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 5) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_ORANGE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 6) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PINK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 7) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PURPLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 8) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_RED.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 9) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_CYAN.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType3 == 10) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_YELLOW.get());
                    popResource((ServerWorld) world, pos, itemStack);
                }
            }


            if(7 - candleTile.candleHeight4 < 1) {
                if (candleTile.candleType4 == 1) {
                    itemStack = new ItemStack(ModBlocks.CANDLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 2) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLUE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 3) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_BLACK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 4) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_LIME.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 5) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_ORANGE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 6) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PINK.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 7) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_PURPLE.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 8) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_RED.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 9) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_CYAN.get());
                    popResource((ServerWorld) world, pos, itemStack);
                } else if (candleTile.candleType4 == 10) {
                    itemStack = new ItemStack(ModBlocks.CANDLE_YELLOW.get());
                    popResource((ServerWorld) world, pos, itemStack);
                }
            }
        }
    }


    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        dropCandles(world, pos);

        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch(state.getValue(CANDLES)) {
            case 1:
            default:
                return ONE_SHAPE;
            case 2:
                return TWO_SHAPE;
            case 3:
                return THREE_SHAPE;
            case 4:
                return FOUR_SHAPE;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        Random random = new Random();
            if(itemstack.getItem() == Items.FLINT_AND_STEEL)
            {


                if (canBeLit(state, pos, worldIn)) {

                    if (((CandleTile) worldIn.getBlockEntity(pos)).candleLit1 == 0)
                        ((CandleTile) worldIn.getBlockEntity(pos)).candleLit1 = 1;
                    else if (((CandleTile) worldIn.getBlockEntity(pos)).candleLit2 == 0)
                        ((CandleTile) worldIn.getBlockEntity(pos)).candleLit2 = 1;
                    else if (((CandleTile) worldIn.getBlockEntity(pos)).candleLit3 == 0)
                        ((CandleTile) worldIn.getBlockEntity(pos)).candleLit3 = 1;
                    else if (((CandleTile) worldIn.getBlockEntity(pos)).candleLit4 == 0)
                        ((CandleTile) worldIn.getBlockEntity(pos)).candleLit4 = 1;
                    else
                        return ActionResultType.FAIL;

                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 1.0F);
                    itemstack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(handIn));

                    return ActionResultType.sidedSuccess(worldIn.isClientSide());
                }

            }
        return ActionResultType.PASS;
    }

    public Candle(Properties properties) {
        super(properties.noCollission());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(PLAYER_NEAR, Boolean.valueOf(false)).setValue(CANDLES_LIT, 0));
    }

    public static void spawnSmokeParticles(World worldIn, BlockPos pos, boolean spawnExtraSmoke) {
        Random random = worldIn.getRandom();
        BasicParticleType basicparticletype = ParticleTypes.SMOKE;
        worldIn.addParticle(basicparticletype, true, (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.07D, 0.0D);
        if (spawnExtraSmoke) {
            worldIn.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4D, (double)pos.getZ() + 0.25D + random.nextDouble() / 2.0D * (double)(random.nextBoolean() ? 1 : -1), 0.0D, 0.005D, 0.0D);
        }

    }


    public static void extinguish(IWorld world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                spawnSmokeParticles((World)world, pos, true);
            }
        }

    }

    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {
            CandleTile tile = ((CandleTile)worldIn.getBlockEntity(pos));
            boolean flag = (tile.candleLit1 == 1 || tile.candleLit2 == 1 || tile.candleLit3 == 1 || tile.candleLit4 == 1);
            if (flag) {
                if (!worldIn.isClientSide()) {
                    worldIn.playSound((PlayerEntity)null, pos, SoundEvents.GENERIC_EXTINGUISH_FIRE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                }

                extinguish(worldIn, pos, state);
                tile.candleLit1 = 0;
                tile.candleLit2 = 0;
                tile.candleLit3 = 0;
                tile.candleLit4 = 0;

            }

            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.valueOf(true)), 3);
            worldIn.scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
        } else {
            return false;
        }
    }

    public void onProjectileCollision(World worldIn, BlockState state, BlockRayTraceResult hit, ProjectileEntity projectile) {
        if (projectile.isOnFire()) {
            CandleTile tile = ((CandleTile)worldIn.getBlockEntity(hit.getBlockPos()));
            boolean flagLit = (tile.candleLit1 == 1 && tile.candleLit2 == 1 && tile.candleLit3 == 1 && tile.candleLit4 == 1);
            Entity entity = projectile.getOwner();
            boolean flag = entity == null || entity instanceof PlayerEntity || net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(worldIn, entity);
            if (flag && !flagLit && !state.getValue(WATERLOGGED)) {
                if(tile.candleType1 != 0)
                    tile.candleLit1 = 1;
                if(tile.candleType2 != 0)
                    tile.candleLit2 = 1;
                if(tile.candleType3 != 0)
                    tile.candleLit3 = 1;
                if(tile.candleType4 != 0)
                    tile.candleLit4 = 1;
            }

        }

    }


    @SuppressWarnings("deprecation")
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.FACING, CANDLES, WATERLOGGED, PLAYER_NEAR, SLOT_ONE_TYPE, CANDLES_LIT);
    }

    public static boolean canBeLit(BlockState state, BlockPos pos, World world) {
        return !state.getValue(BlockStateProperties.WATERLOGGED) && (((CandleTile)world.getBlockEntity(pos)).candleLit1 == 0 || (((CandleTile)world.getBlockEntity(pos)).candleLit2 == 0 && ((CandleTile)world.getBlockEntity(pos)).candleType2 != 0) || (((CandleTile)world.getBlockEntity(pos)).candleLit3 == 0 && ((CandleTile)world.getBlockEntity(pos)).candleType3 != 0) || (((CandleTile)world.getBlockEntity(pos)).candleLit4 == 0 && ((CandleTile)world.getBlockEntity(pos)).candleType4 != 0));
    }


    public void setPlayerNear(World worldIn, BlockPos pos, BlockState state, boolean near) {
        worldIn.setBlock(pos, state.setValue(PLAYER_NEAR, near), 1);
    }

    public boolean getPlayerNear(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getValue(PLAYER_NEAR);
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos pos, BlockPos facingPos) {

        if(!state.canSurvive(world, pos))
        {
            if(!world.isClientSide() && world instanceof ServerWorld) {
                dropCandles(((ServerWorld) world).getLevel(), pos);
            }
        }

        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, world, pos, facingPos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canSurvive(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return canSupportCenter(worldIn, pos.below(), Direction.UP);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }


    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);

        if (world instanceof ServerWorld) {
            if (world.getBlockState(pos) != state && !world.isClientSide()) {
                dropCandles(world, pos);
            }

        }
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.candle_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.candle_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.candle_shift_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }


//    @Override
//    @OnlyIn(Dist.CLIENT)
//    public void animateTick(BlockState state, Level world, BlockPos pos, Random rand) {
//
//        //world.addParticle(ParticleTypes.ENCHANT, pos.getX() + Math.round(rand.nextDouble()), pos.getY() + 1.2d, pos.getZ() + Math.round(rand.nextDouble()) , (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.035d ,(rand.nextDouble() - 0.5d) / 50d);
//
//        super.animateTick(state, world, pos, rand);
//    }
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
//        return ModTileEntities.CANDLE_TILE.get().create();
//    }
//
//    @Override
//    public boolean hasBlockEntity(BlockState state) {
//        return true;
//    }
//
//    @Override
//    public Class<CandleTile> getTileEntityClass() {
//        return CandleTile.class;
//    }
//





//    @Override
//    public Class<CandleTile> getTileEntityClass() {
//        return CandleTile.class;
//    }
//
//    @Nullable
//    @Override
//    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
//        return new CandleTile(ModTileEntities.CRYSTAL_BALL_TILE.get(), pos, state);
//    }


    @Override
    public Class<CandleTile> getTileEntityClass() {
        return CandleTile.class;
    }


    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CandleTile(ModTileEntities.CANDLE_TILE.get(), pos, state);
    }


    @Nullable
    @Override
    public <T extends TileEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, TileEntityType<T> entityType){
        return entityType == ModTileEntities.CANDLE_TILE.get() ?
                (world2, pos, state2, entity) -> ((CandleTile)entity).tick() : null;
    }

}
