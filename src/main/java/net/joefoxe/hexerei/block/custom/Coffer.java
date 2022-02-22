package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.block.ITileEntity;
import net.joefoxe.hexerei.container.CofferContainer;
import net.joefoxe.hexerei.items.JarHandler;
import net.joefoxe.hexerei.tileentity.CofferTile;
import net.joefoxe.hexerei.tileentity.ModTileEntities;import net.minecraft.client.gui.screens.Screen;
import net.minecraft.util.Direction;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.state.BooleanProperty;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.state.IntegerProperty;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.ContainerBlock;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;

public class Coffer extends ContainerBlock implements ITileEntity<CofferTile>, ITileEntityProvider, IWaterLoggable {


    public static final IntegerProperty ANGLE = IntegerProperty.create("angle", 0, 180);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderShape(BlockState iBlockState) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return this.defaultBlockState().setValue(HorizontalBlock.FACING, context.getHorizontalDirection()).setValue(ANGLE, 0).setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER));
    }

    // hitbox REMEMBER TO DO THIS
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(2, 0, 4, 14, 4, 12)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();

    public static final VoxelShape SHAPE_TURNED = Stream.of(
            Block.box(4, 0, 2, 12, 4, 14)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_){
        if (p_220053_1_.getValue(HorizontalBlock.FACING) == Direction.EAST || p_220053_1_.getValue(HorizontalBlock.FACING) == Direction.WEST)
            return SHAPE_TURNED;
        return SHAPE;
    }

    @Nullable
    @Override
    public <T extends TileEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, TileEntityType<T> entityType){
        return entityType == ModTileEntities.COFFER_TILE.get() ?
                (world2, pos, state2, entity) -> ((CofferTile)entity).tick() : null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);
        if(!worldIn.isClientSide()) {

            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(tileEntity instanceof CofferTile) {
                INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);

                NetworkHooks.openGui(((ServerPlayerEntity)player), containerProvider, tileEntity.getBlockPos());

            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return ActionResultType.SUCCESS;
    }

    public Coffer(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, Boolean.valueOf(false)));
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


    @Override
    public void attack(BlockState state, World world, BlockPos pos, PlayerEntity player) {
        if (player instanceof FakePlayer)
            return;
        if (world instanceof ServerWorld) {
            ItemStack cloneItemStack = getCloneItemStack(world, pos, state);
            world.destroyBlock(pos, false);
            if (world.getBlockState(pos) != state && !world.isClientSide()) {
                if(player.getItemInHand(Hand.MAIN_HAND).getItem() == Items.AIR)
                    player.setItemInHand(Hand.MAIN_HAND,cloneItemStack);
                else
                    player.getInventory().placeItemBackInInventory(cloneItemStack);
            }

        }
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);

        if (world instanceof ServerWorld) {
            ItemStack cloneItemStack = getCloneItemStack(world, pos, state);
            if (world.getBlockState(pos) != state && !world.isClientSide()) {
                world.addFreshEntity(new ItemEntity(world, pos.getX() + 0.5f, pos.getY() - 0.5f, pos.getZ() + 0.5f, cloneItemStack));
            }

        }
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
    public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack item = new ItemStack(this);
        Optional<CofferTile> tileEntityOptional = Optional.ofNullable(getBlockEntity(worldIn, pos));

        CompoundNBT tag = item.getOrCreateTag();
        CompoundNBT inv = tileEntityOptional.map(coffer -> coffer.itemHandler.serializeNBT())
                .orElse(new CompoundNBT());
        ItemStackHandler empty = tileEntityOptional.map(herb_jar -> herb_jar.itemHandler)
                .orElse(new ItemStackHandler(36));

        boolean flag = false;
        for(int i = 0; i < 36; i++)
        {
            if(!empty.getStackInSlot(i).isEmpty())
            {
                flag = true;
                break;
            }
        }
        if(flag)
            tag.put("Inventory", inv);


        ITextComponent customName = tileEntityOptional.map(CofferTile::getCustomName)
                .orElse(null);

        if (customName != null)
            if(customName.getString().length() > 0)
                item.setHoverName(customName);
        return item;
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        if (stack.hasCustomHoverName()) {
            TileEntity tileentity = worldIn.getBlockEntity(pos);
            ((CofferTile)tileentity).customName = stack.getHoverName();
        }

        if (worldIn.isClientSide())
            return;
        if (stack == null)
            return;
        withTileEntityDo(worldIn, pos, te -> {
            te.readInventory(stack.getOrCreateTag()
                    .getCompound("Inventory"));
        });

    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        CompoundNBT inv = stack.getOrCreateTag().getCompound("Inventory");
        ListNBT tagList = inv.getList("Items", INBT.TAG_COMPOUND);
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            for (int i = 0; i < tagList.size(); i++)
            {
                CompoundNBT itemTags = tagList.getCompound(i);

                TranslationTextComponent itemText2 = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(itemTags).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));
                TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(" - %s", itemText2).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999)));
                int countText = ItemStack.of(itemTags).getCount();
                itemText.append(" x" + countText);

                tooltip.add(itemText);
            }
            if(tagList.size() < 1)
            {
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            }

        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));


            for (int i = 0; i < Math.min(tagList.size(), 3); i++)
            {
                CompoundNBT itemTags = tagList.getCompound(i);

                TranslationTextComponent itemText2 = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(itemTags).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));
                TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(" - %s", itemText2).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999)));
                int countText = ItemStack.of(itemTags).getCount();
                itemText.append(" x" + countText);

                tooltip.add(itemText);
            }
            if(tagList.size() > 3) {
                tooltip.add(new TranslationTextComponent(". . . ").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            }
            else if(tagList.size() < 1)
            {

                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            }
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {

        //world.addParticle(ParticleTypes.ENCHANT, pos.getX() + Math.round(rand.nextDouble()), pos.getY() + 1.2d, pos.getZ() + Math.round(rand.nextDouble()) , (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.035d ,(rand.nextDouble() - 0.5d) / 50d);

        super.animateTick(state, world, pos, rand);
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new CofferContainer(i, worldIn, pos, playerInventory, playerEntity);
            }

            @Override
            public ITextComponent getDisplayName() {
                if(((CofferTile)worldIn.getBlockEntity(pos)).customName != null)
                    return new TranslationTextComponent(((CofferTile)worldIn.getBlockEntity(pos)).customName.getString());
                return new TranslationTextComponent("screen.hexerei.coffer");
            }

        };
    }

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
    @Override
    public Class<net.joefoxe.hexerei.tileentity.CofferTile> getTileEntityClass() {
        return CofferTile.class;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CofferTile(ModTileEntities.COFFER_TILE.get(), pos, state);
    }

}
