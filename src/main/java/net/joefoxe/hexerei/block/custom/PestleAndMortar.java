package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.block.ITileEntity;
import net.joefoxe.hexerei.item.custom.BroomRenderProperties;
import net.joefoxe.hexerei.tileentity.DryingRackTile;
import net.joefoxe.hexerei.tileentity.ModTileEntities;
import net.joefoxe.hexerei.tileentity.PestleAndMortarTile;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResultType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
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
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.shapes.VoxelShape;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;

public class PestleAndMortar extends Block implements ITileEntity<PestleAndMortarTile>, ITileEntityProvider, IWaterLoggable {


    public static final IntegerProperty ANGLE = IntegerProperty.create("angle", 0, 180);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @Override
    public BlockRenderType getRenderShape(BlockState p_60550_) {
        return BlockRenderType.MODEL;
    }

//    @SuppressWarnings("deprecation")
//    @Override
//    public RenderShape getRenderShape(BlockState iBlockState) {
//        return RenderShape.MODEL;
//    }

    @org.jetbrains.annotations.Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HorizontalBlock.FACING, context.getHorizontalDirection()).setValue(ANGLE, 0).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    @Override
    public Object getRenderPropertiesInternal() {
        return new BroomRenderProperties();
    }



    // hitbox REMEMBER TO DO THIS
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(6, 0, 10, 10, 1, 11),
            Block.box(6, 0, 5, 10, 1, 6),
            Block.box(5, 0, 6, 6, 1, 10),
            Block.box(10, 0, 6, 11, 1, 10),
            Block.box(6, 1, 6, 10, 2, 10),
            Block.box(11, 3, 5, 12, 6, 11),
            Block.box(4, 3, 5, 5, 6, 11),
            Block.box(5, 3, 11, 11, 6, 12),
            Block.box(5, 3, 4, 11, 6, 5),
            Block.box(12, 6, 5, 13, 7, 11),
            Block.box(11, 6, 4, 12, 7, 5),
            Block.box(4, 6, 4, 5, 7, 5),
            Block.box(4, 6, 11, 5, 7, 12),
            Block.box(11, 6, 11, 12, 7, 12),
            Block.box(3, 6, 5, 4, 7, 11),
            Block.box(5, 6, 3, 11, 7, 4),
            Block.box(5, 6, 12, 11, 7, 13),
            Block.box(5, 2, 5, 11, 3, 11)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }


    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof PestleAndMortarTile) {
            ((PestleAndMortarTile)tileEntity).interactPestleAndMortar(player, hit);
            return ActionResultType.SUCCESS;
        }
        return ActionResultType.PASS;
    }


    public PestleAndMortar(Properties properties) {
        super(properties.noOcclusion());
        this.withPropertiesOf(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.FACING, ANGLE, WATERLOGGED);
    }

    public void setAngle(World worldIn, BlockPos pos, BlockState state, int angle) {
        worldIn.setBlock(pos, state.setValue(ANGLE, Integer.valueOf(MathHelper.clamp(angle, 0, 180))), 2);
    }

    public int getAngle(World worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos).getValue(ANGLE);
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

            tooltip.add(new TranslationTextComponent("tooltip.hexerei.pestle_and_mortar_shift_1").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.pestle_and_mortar_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.pestle_and_mortar_shift_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.pestle_and_mortar_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.pestle_and_mortar_1").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        PestleAndMortarTile te = (PestleAndMortarTile) worldIn.getBlockEntity(pos);

        if(!te.getItems().get(0).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(0)));
        if(!te.getItems().get(1).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(1)));
        if(!te.getItems().get(2).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(2)));
        if(!te.getItems().get(3).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(3)));
        if(!te.getItems().get(4).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(4)));
        if(!te.getItems().get(5).isEmpty())
            worldIn.addFreshEntity(new ItemEntity(worldIn, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, te.getItems().get(5)));

        super.playerWillDestroy(worldIn, pos, state, player);
    }

    @Override
    public Class<PestleAndMortarTile> getTileEntityClass() {
        return PestleAndMortarTile.class;
    }


    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PestleAndMortarTile(ModTileEntities.PESTLE_AND_MORTAR_TILE.get(), pos, state);
    }


    @Nullable
    @Override
    public <T extends TileEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, TileEntityType<T> entityType){
        return entityType == ModTileEntities.PESTLE_AND_MORTAR_TILE.get() ?
                (world2, pos, state2, entity) -> ((PestleAndMortarTile)entity).tick() : null;
    }
}
