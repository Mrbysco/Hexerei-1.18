package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.block.ITileEntity;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.config.HexConfig;
import net.joefoxe.hexerei.item.ModItems;
import net.joefoxe.hexerei.tileentity.ModTileEntities;
import net.joefoxe.hexerei.tileentity.SageBurningPlateTile;
import net.joefoxe.hexerei.tileentity.SageBurningPlateTile;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Mth;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.level.block.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;

public class SageBurningPlate extends Block implements ITileEntity<SageBurningPlateTile>, ITileEntityProvider, IWaterLoggable {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final IntegerProperty MODE = IntegerProperty.create("mode", 0, 3);

    @Override
    public BlockRenderType getRenderShape(BlockState p_60550_) {
        return BlockRenderType.MODEL;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HorizontalBlock.FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(LIT, false).setValue(MODE, 0);
    }

    // hitbox REMEMBER TO DO THIS
    public static final VoxelShape SHAPE = Block.box(2, 0, 5, 14, 1, 11);

    public static final VoxelShape SHAPE_TURNED = Block.box(5, 0, 2, 11, 1, 14);

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        if (p_220053_1_.getValue(HorizontalBlock.FACING) == Direction.EAST || p_220053_1_.getValue(HorizontalBlock.FACING) == Direction.WEST)
            return SHAPE_TURNED;
        return SHAPE;
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);

        ItemStack itemstack = player.getItemInHand(handIn);
        Random random = new Random();
        if (tileEntity instanceof SageBurningPlateTile) {
            if(itemstack.getItem() == Items.FLINT_AND_STEEL)
            {
                if (((SageBurningPlateTile) tileEntity).getItems().get(0).is(ModItems.DRIED_SAGE_BUNDLE.get()) && !state.getValue(LIT)) {

                    worldIn.setBlock(pos, state.setValue(BlockStateProperties.LIT, Boolean.valueOf(true)), 11);
                    worldIn.playSound((PlayerEntity) null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, random.nextFloat() * 0.4F + 1.0F);
                    itemstack.hurtAndBreak(1, player, player1 -> player1.broadcastBreakEvent(handIn));

                    return ActionResultType.sidedSuccess(worldIn.isClientSide());
                }
                else
                    return ActionResultType.PASS;

            } else if(itemstack.isEmpty() && !player.isShiftKeyDown())
            {
                worldIn.setBlock(pos, state.setValue(MODE, state.getValue(MODE) + 1 > 3 ? 0 : state.getValue(MODE) + 1), 11);
                state = worldIn.getBlockState(pos);
                String s = "display.hexerei.sage_plate_toggle_" + String.valueOf(state.getValue(MODE));
                player.displayClientMessage(new TranslationTextComponent(s), true);

            }
            else
                ((SageBurningPlateTile)tileEntity).interactSageBurningPlate(player, hit);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }


    public SageBurningPlate(Properties properties) {
        super(properties.noOcclusion());
        this.withPropertiesOf(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.FACING, WATERLOGGED, LIT, MODE);
    }

//
//    @SuppressWarnings("deprecation")
//    @Override
//    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
//        return !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
//    }
//
//    @SuppressWarnings("deprecation")
//    @Override
//    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
//        return canSupportCenter(worldIn, pos.below(), Direction.UP);
//    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            TranslationTextComponent string = (TranslationTextComponent) new TranslationTextComponent(HexConfig.SAGE_BURNING_PLATE_RANGE.get() + "").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)));
            TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(ModItems.DRIED_SAGE_BUNDLE.get().getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));

            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_1", itemText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_3", string).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.sage_burning_plate_shift_6").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.sage_burning_plate").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x999999))));

        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        SageBurningPlateTile te = (SageBurningPlateTile) worldIn.getBlockEntity(pos);

        if(!te.getItems().get(0).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, te.getItems().get(0)));

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public Class<SageBurningPlateTile> getTileEntityClass() {
        return SageBurningPlateTile.class;
    }


    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SageBurningPlateTile(ModTileEntities.SAGE_BURNING_PLATE_TILE.get(), pos, state);
    }


    @Nullable
    @Override
    public <T extends TileEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, TileEntityType<T> entityType){
        return entityType == ModTileEntities.SAGE_BURNING_PLATE_TILE.get() ?
                (world2, pos, state2, entity) -> ((SageBurningPlateTile)entity).tick() : null;
    }
}
