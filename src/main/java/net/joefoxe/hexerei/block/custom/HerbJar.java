package net.joefoxe.hexerei.block.custom;

import net.joefoxe.hexerei.block.ITileEntity;
import net.joefoxe.hexerei.container.HerbJarContainer;
import net.joefoxe.hexerei.items.JarHandler;
import net.joefoxe.hexerei.tileentity.HerbJarTile;
import net.joefoxe.hexerei.tileentity.ModTileEntities;
import net.minecraft.util.Direction;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.material.PushReaction;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.IBlockRenderProperties;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;

public class HerbJar extends Block implements ITileEntity<HerbJarTile>, ITileEntityProvider, IWaterLoggable {

    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
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

        for(Direction direction : context.getNearestLookingDirections()) {
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, Boolean.valueOf(direction == Direction.UP));
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                    return blockstate.setValue(WATERLOGGED, Boolean.valueOf(fluidstate.getType() == Fluids.WATER)).setValue(HorizontalBlock.FACING, context.getHorizontalDirection());
                }
            }
        }

        return null;
    }

    // hitbox REMEMBER TO DO THIS
    public static final VoxelShape SHAPE = Stream.of(
            Block.box(5, -0.5, 5, 11, 0, 11),
            Block.box(5.5, 13, 5.5, 10.5, 15, 10.5),
            Block.box(4.5, 12, 10.5, 11.5, 14, 11.5),
            Block.box(4.5, 12, 4.5, 11.5, 14, 5.5),
            Block.box(4.5, 12, 5.5, 5.5, 14, 10.5),
            Block.box(10.5, 12, 5.5, 11.5, 14, 10.5),
            Block.box(4, 0, 4, 12, 11, 12),
            Block.box(5, 11, 5, 11, 12, 11)
    ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();


    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack itemstack = player.getItemInHand(handIn);

        if ((itemstack.isEmpty() && player.isShiftKeyDown()) || state.getValue(HorizontalBlock.FACING).getOpposite() != hit.getDirection()) {

            TileEntity tileEntity = worldIn.getBlockEntity(pos);

            if(!worldIn.isClientSide()) {
                if (tileEntity instanceof HerbJarTile) {
                    INamedContainerProvider containerProvider = createContainerProvider(worldIn, pos);
                    NetworkHooks.openGui(((ServerPlayerEntity) player), containerProvider, tileEntity.getBlockPos());
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");
                }
            }

            return ActionResultType.SUCCESS;
        }

        TileEntity tileEntity = worldIn.getBlockEntity(pos);

        if (tileEntity instanceof HerbJarTile) {
            ((HerbJarTile)tileEntity).interactPutItems(player);
        }

        return ActionResultType.SUCCESS;
    }


    @Override
    public void playerWillDestroy(World world, BlockPos pos, BlockState state, PlayerEntity player) {

        ItemStack cloneItemStack = getCloneItemStack(world, pos, state);
        if(!world.isClientSide())
            popResource((ServerWorld)world, pos, cloneItemStack);

        super.playerWillDestroy(world, pos, state, player);
    }
    
    protected BlockRayTraceResult rayTraceEyeLevel(World world, PlayerEntity player, double length) {
        Vector3d eyePos = player.getEyePosition(1);
        Vector3d lookPos = player.getViewVector(1);
        Vector3d endPos = eyePos.add(lookPos.x * length, lookPos.y * length, lookPos.z * length);
        RayTraceContext context = new RayTraceContext(eyePos, endPos, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player);
        return world.clip(context);
    }

    @Override
    public void attack(BlockState state, World worldIn, BlockPos pos, PlayerEntity playerIn) {
        BlockRayTraceResult rayResult = rayTraceEyeLevel(worldIn, playerIn, playerIn.getAttribute(net.minecraftforge.common.ForgeMod.REACH_DISTANCE.get()).getValue() + 1);
        if (rayResult.getType() == RayTraceResult.Type.MISS)
            return;

        Direction side = rayResult.getDirection();

        TileEntity tile = worldIn.getBlockEntity(pos);
        HerbJarTile herbJarTile = null;
        //System.out.println(worldIn.isClientSide());
        if(tile instanceof  HerbJarTile)
            herbJarTile = (HerbJarTile) tile;
        if (state.getValue(HorizontalBlock.FACING).getOpposite() != rayResult.getDirection())
            return;

        ItemStack item;
        if (playerIn.isShiftKeyDown()) {
            item = herbJarTile.takeItems(0, herbJarTile.itemHandler.getStackInSlot(0).getCount());
        }
        else {
            item = herbJarTile.takeItems(0, 1);
        }

        if (!item.isEmpty()) {
            if (!playerIn.inventory.add(item)) {
                dropItemStack(worldIn, pos.relative(side), playerIn, item);
                worldIn.sendBlockUpdated(pos, state, state, 3);
            }
            else
                worldIn.playSound(null, pos.getX() + .5f, pos.getY() + .5f, pos.getZ() + .5f, SoundEvents.ITEM_PICKUP, SoundCategory.PLAYERS, .2f, ((worldIn.random.nextFloat() - worldIn.random.nextFloat()) * .7f + 1) * 2);
        }

        super.attack(state, worldIn, pos, playerIn);
    }

    private void dropItemStack (World world, BlockPos pos, PlayerEntity player, @Nonnull ItemStack stack) {
        ItemEntity entity = new ItemEntity(world, pos.getX() + .5f, pos.getY() + .3f, pos.getZ() + .5f, stack);
        Vector3d motion = entity.getDeltaMovement();
        entity.push(-motion.x, -motion.y, -motion.z);
        world.addFreshEntity(entity);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    public HerbJar(Properties properties) {
        super(properties.noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, Boolean.valueOf(false)).setValue(WATERLOGGED, Boolean.valueOf(false)));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(HorizontalBlock.FACING, HANGING, WATERLOGGED);
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
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {

        CompoundNBT inv = stack.getOrCreateTag().getCompound("Inventory");
        ListNBT tagList = inv.getList("Items", INBT.TAG_COMPOUND);
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            if(tagList.size() >= 1) {
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_6").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            }
            for (int i = 0; i < tagList.size(); i++)
            {
//                CompoundTag itemTags = tagList.getCompound(i);
//                itemTags.putInt("Count", 1);
//                TranslatableComponent itemText = new TranslatableComponent(ItemStack.of(itemTags).getDescriptionId());
//                int countText = Integer.parseInt(String.valueOf(itemTags.get("ExtendedCount")));
//                itemText.append(" x" + countText);
//
//                tooltip.add(itemText);

                CompoundNBT itemTags = tagList.getCompound(i);
                itemTags.putInt("Count", 1);
                TranslationTextComponent itemText2 = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(itemTags).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));
                TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(" - %s", itemText2).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999)));
                int countText = Integer.parseInt(String.valueOf(itemTags.get("ExtendedCount")));
                itemText.append(" x" + countText);

                tooltip.add(itemText);
            }
            if(tagList.size() < 1)
            {
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.coffer_shift_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_6").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.herb_jar_shift_7").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

            }

        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

//            tooltip.add(new TranslatableComponent("tooltip.hexerei.herb_jar_shift_4").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x999999))));
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.herb_jar_shift_5").withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0x999999))));

            for (int i = 0; i < Math.min(tagList.size(), 1); i++)
            {
                CompoundNBT itemTags = tagList.getCompound(i);
                itemTags.putInt("Count", 1);
                TranslationTextComponent itemText2 = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(itemTags).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));
                TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(" - %s", itemText2).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999)));
                int countText = Integer.parseInt(String.valueOf(itemTags.get("ExtendedCount")));
                itemText.append(" x" + countText);

                tooltip.add(itemText);
            }
//            if(tagList.size() > 3) {
//                tooltip.add(new TranslatableComponent(". . . "));
//                tooltip.add(new TranslatableComponent(""));
//                tooltip.add(new TranslatableComponent("Hold \u00A7eSHIFT\u00A7r to see more"));
//            }
//            else
//            if(tagList.size() < 1)
//            {
//
//                tooltip.add(new TranslatableComponent("tooltip.hexerei.herb_jar"));
//            }
        }
        super.appendHoverText(stack, world, tooltip, flagIn);
    }
    

    @Override
    public ItemStack getCloneItemStack(IBlockReader worldIn, BlockPos pos, BlockState state) {
        ItemStack item = new ItemStack(this);
        Optional<HerbJarTile> tileEntityOptional = Optional.ofNullable(getBlockEntity(worldIn, pos));
//        System.out.println(worldIn.getBlockEntity(pos));e
        CompoundNBT tag = item.getOrCreateTag();
        JarHandler empty = tileEntityOptional.map(herb_jar -> herb_jar.itemHandler)
                .orElse(new JarHandler(1,1024));
        CompoundNBT inv = tileEntityOptional.map(herb_jar -> herb_jar.itemHandler.serializeNBT())
                .orElse(new CompoundNBT());


        if(!empty.getStackInSlot(0).isEmpty())
            tag.put("Inventory", inv);


        ITextComponent customName = tileEntityOptional.map(HerbJarTile::getCustomName)
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
            ((HerbJarTile)tileentity).customName = stack.getHoverName();
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

    public boolean placeLiquid(IWorld worldIn, BlockPos pos, BlockState state, FluidState fluidStateIn) {
        if (!state.getValue(BlockStateProperties.WATERLOGGED) && fluidStateIn.getType() == Fluids.WATER) {

            worldIn.setBlock(pos, state.setValue(WATERLOGGED, Boolean.valueOf(true)), 3);
            worldIn.scheduleTick(pos, fluidStateIn.getType(), fluidStateIn.getType().getTickDelay(worldIn));
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        if(!stateIn.canSurvive(worldIn, currentPos))
        {
            if(!worldIn.isClientSide() && worldIn instanceof ServerWorld) {
                ItemStack cloneItemStack = getCloneItemStack(worldIn, currentPos, stateIn);
                worldIn.addFreshEntity(new ItemEntity(((ServerWorld) worldIn).getLevel(), currentPos.getX() + 0.5f, currentPos.getY() - 0.5f, currentPos.getZ() + 0.5f, cloneItemStack));
            }
        }

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
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, World world, BlockPos pos, Random rand) {
    }

    private INamedContainerProvider createContainerProvider(World worldIn, BlockPos pos) {
        return new INamedContainerProvider() {
            @Nullable
            @Override
            public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                return new HerbJarContainer(i, worldIn, pos, playerInventory, playerEntity);
            }

            @Override
            public ITextComponent getDisplayName() {
                if(((HerbJarTile)worldIn.getBlockEntity(pos)).customName != null)
                    return new TranslationTextComponent(((HerbJarTile)worldIn.getBlockEntity(pos)).customName.getString());
                return new TranslationTextComponent("screen.hexerei.herb_jar");
            }

        };
    }

    @Override
    public Class<HerbJarTile> getTileEntityClass() {
        return HerbJarTile.class;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new HerbJarTile(ModTileEntities.HERB_JAR_TILE.get(), pos, state);
    }

//    @Override
//    public void initializeClient(Consumer<IBlockRenderProperties> consumer) {
//        ClientRegistry.registerISTER(consumer, JarItemRenderer::new);
//    }
}
