package net.joefoxe.hexerei.tileentity;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.block.custom.Coffer;
import net.joefoxe.hexerei.container.CofferContainer;
import net.joefoxe.hexerei.util.HexereiPacketHandler;
import net.joefoxe.hexerei.util.message.TESyncPacket;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.inventory.IClearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.world.level.block.entity.TickingBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.client.renderer.texture.Tickable;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Function;

public class CofferTile extends LockableLootTileEntity implements IClearable {

    public final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    protected NonNullList<ItemStack> items = NonNullList.withSize(8, ItemStack.EMPTY);

    public int degreesOpened;
    public int buttonToggled;
    public static final int lidOpenAmount = 112;

    public ITextComponent customName;


    public CofferTile(TileEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
        buttonToggled = 0;
    }

    public CofferTile(BlockPos blockPos, BlockState blockState) {
        this(ModTileEntities.COFFER_TILE.get(),blockPos, blockState);
    }


//    public CofferTile(BlockEntityType<?> tileEntityTypeIn) {
//        super(tileEntityTypeIn);
//
//        buttonToggled = 0;
//    }


    @Override
    public TileEntityType<?> getType() {
        return super.getType();
    }

    public void readInventory(CompoundNBT compound) {
        itemHandler.deserializeNBT(compound);
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    @Override
    public void setChanged() {
        super.setChanged();

//        if(this.level != null)
//            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(),
//                Block.UPDATE_CLIENTS);
    }

    @Override
    public void startOpen(PlayerEntity p_18955_) {
        super.startOpen(p_18955_);
    }

    @Override
    public void stopOpen(PlayerEntity p_18954_) {
        super.stopOpen(p_18954_);
    }

    @Override
    public boolean canPlaceItem(int p_18952_, ItemStack p_18953_) {
        return super.canPlaceItem(p_18952_, p_18953_);
    }

    @Override
    public int countItem(Item p_18948_) {
        return super.countItem(p_18948_);
    }


    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Hexerei.MOD_ID + ".coffer");
    }

    @Override
    protected Container createMenu(int id, PlayerInventory player) {
        return new CofferContainer(id, this.level, this.worldPosition, player, player.player);
    }

    @Override
    public void clearContent() {
        super.clearContent();
        this.items.clear();
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

    @Override
    public void requestModelDataUpdate() {
        super.requestModelDataUpdate();
    }

    @NotNull
    @Override
    public IModelData getModelData() {
        return super.getModelData();
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }






    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }

//    @Override
    public CompoundNBT save(CompoundNBT tag) {
        super.saveAdditional(tag);
//        ContainerHelper.saveAllItems(tag, this.items);
        tag.put("inv", itemHandler.serializeNBT());
        return tag;
    }


    @Override
    public void saveAdditional(CompoundNBT compound) {
        compound.put("inv", itemHandler.serializeNBT());
//        ContainerHelper.saveAllItems(compound, this.items);
        if (this.customName != null)
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
    }



    @Override
    public void load(CompoundNBT compoundTag) {
        super.load(compoundTag);
        itemHandler.deserializeNBT(compoundTag.getCompound("inv"));
//        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (compoundTag.contains("CustomName", 8))
            this.customName = ITextComponent.Serializer.fromJson(compoundTag.getString("CustomName"));
//        if (!this.tryLoadLootTable(compoundTag))
//            ContainerHelper.loadAllItems(compoundTag, this.items);

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






    private ItemStackHandler createHandler() {
        return new ItemStackHandler(36) {
            @Override
            protected void onContentsChanged(int slot) {
                sync();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            public int getSlotLimit(int slot) {
                return 64;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if(!isItemValid(slot, stack)) {
                    return stack;
                }

                return super.insertItem(slot, stack, simulate);
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

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
    }

    public ItemStack getItemStackInSlot(int slot) {
        return this.itemHandler.getStackInSlot(slot);
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

    public int getButtonToggled() {
        return this.buttonToggled;
    }
    public void setButtonToggled(int degrees) {
        this.buttonToggled =  degrees;
    }

//    @Override
    public void tick() {
        if(level.isClientSide)
            return;

        boolean flag = false;
        PlayerEntity playerEntity = this.level.getNearestPlayer(this.worldPosition.getX(),this.worldPosition.getY(),this.worldPosition.getZ(), 5D, false);
        if(playerEntity != null) {
            if (Math.floor(getDistanceToEntity(playerEntity, this.worldPosition)) < 4D) {
                int distanceFromSide = (lidOpenAmount / 2) - Math.abs((lidOpenAmount / 2) - this.degreesOpened);
                flag = true;

                if (this.degreesOpened + Math.floor(((double) distanceFromSide / (double) (lidOpenAmount / 2)) * 6) + 2 < 112)
                    degreesOpened += Math.floor(((double) distanceFromSide / (double) (lidOpenAmount / 2)) * 6) + 2;
                else
                    degreesOpened = 112;
                ((Coffer) this.getBlockState().getBlock()).setAngle(level, worldPosition, this.getBlockState(), this.degreesOpened);
            }
        }


        if(!flag)
        {

            int distanceFromSide = (lidOpenAmount/2)-Math.abs((lidOpenAmount/2)-this.degreesOpened);

            if(this.degreesOpened + Math.floor(((double)distanceFromSide/(double)(lidOpenAmount/2)) * 6) + 2 > 0)
                degreesOpened -= Math.floor(((double)distanceFromSide/(double)(lidOpenAmount/2)) * 6) + 2;
            else
                degreesOpened = 0;
            ((Coffer)this.getBlockState().getBlock()).setAngle(level, worldPosition, this.getBlockState(), this.degreesOpened);

        }
    }

    @Override
    public int getContainerSize() {
        return 0;
    }

    @Override
    public int getMaxStackSize() {
        return super.getMaxStackSize();
    }

}
