package net.joefoxe.hexerei.tileentity;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.config.HexConfig;
import net.joefoxe.hexerei.container.HerbJarContainer;
import net.joefoxe.hexerei.items.JarHandler;
import net.joefoxe.hexerei.util.HexereiPacketHandler;
import net.joefoxe.hexerei.util.HexereiTags;
import net.joefoxe.hexerei.util.message.MessageCountUpdate;
import net.joefoxe.hexerei.util.message.TESyncPacket;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.container.Container;
import net.minecraft.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.world.ContainerHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.players.PlayerList;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

public class HerbJarTile extends LockableLootTileEntity implements IClearable, INamedContainerProvider {

    public JarHandler itemHandler;
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler).cast();

    protected NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    private final IReorderingProcessor[] renderText = new IReorderingProcessor[1];

    private final ITextComponent[] signText = new ITextComponent[]{new StringTextComponent("Text")};

    public int degreesOpened;

    public ITextComponent customName;

    private long lastClickTime;
    private UUID lastClickUUID;



    public HerbJarTile(TileEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        this.itemHandler = createHandler();
    }

    public HerbJarTile(BlockPos blockPos, BlockState blockState) {
        this(ModTileEntities.HERB_JAR_TILE.get(),blockPos, blockState);
    }


//    public HerbJarTile(BlockEntityType<?> tileEntityTypeIn) {
//        super(tileEntityTypeIn);
//        this.itemHandler = createHandler();
//    }

    public void readInventory(CompoundNBT compound) {
        itemHandler.deserializeNBT(compound);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public ItemStack getItemStackInSlot(int slot) {
        return this.items.get(slot);
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    @Override
    public void setChanged() {
        super.setChanged();

    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Hexerei.MOD_ID + ".herb_jar");
    }

//    public HerbJarTile() {
//        this(ModTileEntities.HERB_JAR_TILE.get());
//    }
//
//    @Override
//    public void load(CompoundTag nbt) {
//        itemHandler.deserializeNBT(nbt.getCompound("inv"));
//        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
//        super.load(nbt);
//        if (nbt.contains("CustomName", 8))
//            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));
//    }



//    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.saveAdditional(tag);
        tag.put("inv", itemHandler.serializeNBT());
        return tag;
    }


    @Override
    public void saveAdditional(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
        if (this.customName != null)
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
    }



    @Override
    public void load(CompoundNBT compoundTag) {
        super.load(compoundTag);
        itemHandler.deserializeNBT(compoundTag.getCompound("inv"));
        if (compoundTag.contains("CustomName", 8))
            this.customName = ITextComponent.Serializer.fromJson(compoundTag.getString("CustomName"));
    }

    @Override
    public CompoundNBT getUpdateTag()
    {
        return this.save(new CompoundNBT());
    }

    @Nullable
    public IPacket<IClientPlayNetHandler> getUpdatePacket() {

        return SUpdateTileEntityPacket.create(this, (tag) -> this.getUpdateTag());
    }

    @Override
    public void onDataPacket(final NetworkManager net, final SUpdateTileEntityPacket pkt)
    {
        this.deserializeNBT(pkt.getTag());
    }

    public void sync() {
        setChanged();
        if (!level.isClientSide)
            HexereiPacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TESyncPacket(worldPosition, save(new CompoundNBT())));

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition),
                    Block.UPDATE_CLIENTS);
    }


    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new HerbJarContainer(id, this.level, this.worldPosition, player, player.player);
    }


    @Override
    public void clearContent() {
        super.clearContent();
    }

//    @Override
//    public double getMaxRenderDistanceSquared() {
//        return 4096D;
//    }

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        AxisAlignedBB aabb = super.getRenderBoundingBox().inflate(5, 5, 5);
        return aabb;
    }


    @Nullable
    @OnlyIn(Dist.CLIENT)
    public IReorderingProcessor reorderText(int row, Function<ITextComponent, IReorderingProcessor> textProcessorFunction) {
        if (this.renderText[row] == null && this.customName != null) {
            this.renderText[row] = textProcessorFunction.apply(this.customName);
        }

        return this.renderText[row];
    }


    private JarHandler createHandler() {
        return new JarHandler(1,1024) {
            @Override
            protected void onContentsChanged(int slot) {
                sync();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                if(HexConfig.JARS_ONLY_HOLD_HERBS.get()) {
                    return stack.is(HexereiTags.Items.HERB_ITEM);
                }
                return true;
            }

        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public Item getItemInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot).getItem();
    }

    public int getNumberOfItems() {

        int num = 0;
        for(int i = 0; i < this.itemHandler.getSlots(); i++)
        {
            if(this.itemHandler.getStackInSlot(i) != ItemStack.EMPTY)
                num++;
        }
        return num;

    }

    public static double getDistanceToEntity(Entity entity, BlockPos pos) {
        double deltaX = entity.getX() - pos.getX();
        double deltaY = entity.getY() - pos.getY();
        double deltaZ = entity.getZ() - pos.getZ();

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    @Override
    public ITextComponent getDisplayName() {
        return customName != null ? customName
                : new TranslationTextComponent("");
    }

    @Nonnull
    public ItemStack takeItems (int slot, int count) {

        ItemStack stack = this.itemHandler.getStackInSlot(slot).copy();
        stack.setCount(Math.min(count, this.itemHandler.getStackInSlot(slot).getMaxStackSize()));
        this.itemHandler.getStackInSlot(slot).setCount(this.itemHandler.getStackInSlot(slot).getCount() - stack.getCount());

        return stack;
    }

    public int putItems (int slot, @Nonnull ItemStack stack, int count) {
        if(HexConfig.JARS_ONLY_HOLD_HERBS.get())
            if(!stack.is(HexereiTags.Items.HERB_ITEM))
                return 0;

        if (this.itemHandler.getContents().get(0).isEmpty()) {
            this.itemHandler.insertItem(0, stack.copy(), false);
            sync();
            stack.shrink(count);
            return count;
        }


        if (!this.itemHandler.getContents().get(0).sameItem(stack))
            return 0;
        if(!ItemStack.isSameItemSameTags(stack, this.itemHandler.getContents().get(0)))
            return 0;

        int countAdded = Math.min(count, stack.getCount());
        countAdded = Math.min(countAdded, 1024 - this.itemHandler.getContents().get(0).getCount());

        this.itemHandler.getContents().get(0).setCount(this.itemHandler.getContents().get(0).getCount() + countAdded);
        stack.shrink(countAdded);
        return countAdded;
    }


    @OnlyIn(Dist.CLIENT)
    public void clientUpdateCount (final int slot, final int count) {
        if (!Objects.requireNonNull(getLevel()).isClientSide)
            return;
        Minecraft.getInstance().tell(() -> HerbJarTile.this.clientUpdateCountAsync(slot, count));
    }

    @OnlyIn(Dist.CLIENT)
    private void clientUpdateCountAsync (int slot, int count) {
        if (this.itemHandler.getStackInSlot(0).getCount() != count){
            ItemStack newStack = this.itemHandler.getStackInSlot(0).copy();
            this.itemHandler.setStackInSlot(0, newStack);
        }
    }

    protected void syncClientCount (int slot, int count) {
        if (getLevel() != null && getLevel().isClientSide)
            return;

        PacketDistributor.TargetPoint point = new PacketDistributor.TargetPoint(
                getBlockPos().getX(), getBlockPos().getY(), getBlockPos().getZ(), 500, getLevel().dimension());
        HexereiPacketHandler.instance.send(PacketDistributor.NEAR.with(() -> point), new MessageCountUpdate(getBlockPos(), slot, count));
    }



    public int interactPutItems (PlayerEntity player) {
        int count;
        if (Objects.requireNonNull(getLevel()).getGameTime() - lastClickTime < 10 && player.getUUID().equals(lastClickUUID))
            count = interactPutCurrentInventory(0, player);
        else
            count = interactPutCurrentItem(0, player);

        lastClickTime = getLevel().getGameTime();
        lastClickUUID = player.getUUID();
        if(count > 0)
            sync();

        return count;
    }

    public int interactPutCurrentItem (int slot, PlayerEntity player) {

        int count = 0;
        ItemStack playerStack = player.inventory.getSelected();
        if (!playerStack.isEmpty())
            count = putItems(slot, playerStack, playerStack.getCount());

        return count;
    }


    public int interactPutCurrentInventory (int slot, PlayerEntity player) {
        int count = 0;
        if (!this.itemHandler.getContents().get(0).isEmpty()) {
            for (int i = 0, n = player.inventory.getContainerSize(); i < n; i++) {
                ItemStack subStack = player.inventory.getItem(i);
                if (!subStack.isEmpty()) {
                    int subCount = putItems(slot, subStack, subStack.getCount());
                    if (subCount > 0 && subStack.getCount() == 0)
                        player.inventory.setItem(i, ItemStack.EMPTY);

                    count += subCount;
                }
            }
        }

        if (count > 0)
            if (player instanceof ServerPlayerEntity)
                ((ServerPlayerEntity) player).initMenu(player.containerMenu);

        return count;
    }

    @Override
    public ITextComponent getCustomName() {
        return this.customName;
    }

    @Override
    public boolean hasCustomName() {
        return customName != null;
    }

    @Override
    public ITextComponent getName() {
        return customName;
    }

    public int getDegreesOpened() {
        return this.degreesOpened;
    }
    public void setDegreesOpened(int degrees) {
        this.degreesOpened =  degrees;
    }

    @Override
    public int getContainerSize() {
        return 0;
    }
}
