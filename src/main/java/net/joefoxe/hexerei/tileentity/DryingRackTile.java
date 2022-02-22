package net.joefoxe.hexerei.tileentity;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.data.recipes.DipperRecipe;
import net.joefoxe.hexerei.data.recipes.DryingRackRecipe;
import net.joefoxe.hexerei.data.recipes.ModRecipeTypes;
import net.joefoxe.hexerei.tileentity.renderer.MixingCauldronRenderer;
import net.joefoxe.hexerei.util.HexereiPacketHandler;
import net.joefoxe.hexerei.util.message.EmitParticlesPacket;
import net.joefoxe.hexerei.util.message.TESyncPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.Random;

import net.minecraft.inventory.IClearable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Hand;

public class DryingRackTile extends LockableLootTileEntity implements ISidedInventory, IClearable, INamedContainerProvider {

//    public final ItemStackHandler itemHandler = createHandler();
//    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);

    public boolean[] crafted = {false, false, false};
    public boolean[] crafting = {false, false, false};
    public int[] dryingTimeMax = {200, 200, 200};
    public int[] dryingTime = {200, 200, 200};
    public ItemStack[] output = {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};

    public DryingRackTile(TileEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState) {
        super(tileEntityTypeIn, blockPos, blockState);
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = itemsIn;
    }

    @Override
    public int getMaxStackSize() {
        return 3;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    public void sync() {
        setChanged();
        if (!level.isClientSide)
            HexereiPacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new TESyncPacket(worldPosition, save(new CompoundNBT())));

        if(this.level != null)
            this.level.sendBlockUpdated(this.worldPosition, this.level.getBlockState(this.worldPosition), this.level.getBlockState(this.worldPosition),
                    Block.UPDATE_CLIENTS);
    }

    LazyOptional<? extends IItemHandler>[] handlers =
            SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == Direction.UP)
                return handlers[0].cast();
            else if (facing == Direction.DOWN)
                return handlers[1].cast();
            else
                return handlers[2].cast();
        }

        return super.getCapability(capability, facing);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {

        return super.getCapability(cap);
    }

    public Item getItemInSlot(int slot) {
        return this.items.get(slot).getItem();
    }

    public int getNumberOfItems() {

        int num = 0;
        for(int i = 0; i < 8; i++)
        {
            if(this.items.get(i) != ItemStack.EMPTY)
                num++;
        }
        return num;

    }


    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        super.deserializeNBT(nbt);
    }

    @Override
    public CompoundNBT serializeNBT() {
        return super.serializeNBT();
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        super.handleUpdateTag(tag);
    }

    @Override
    public void onLoad() {
        super.onLoad();
    }


    public DryingRackTile(BlockPos blockPos, BlockState blockState) {
        this(ModTileEntities.DRYING_RACK_TILE.get(),blockPos, blockState);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index >= 0 && index < this.items.size()) {
            ItemStack itemStack = stack.copy();
            this.items.set(index, itemStack);
            if(index == 0)
                dryingTime[0] = dryingTimeMax[0];
            if(index == 1)
                dryingTime[1] = dryingTimeMax[1];
            if(index == 2)
                dryingTime[2] = dryingTimeMax[2];
            level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, this.level.random.nextFloat() * 0.4F + 1.0F);
        }

        sync();
    }

    @Override
    public ItemStack removeItem(int index, int p_59614_) {
        this.unpackLootTable((PlayerEntity)null);
        ItemStack itemstack = ItemStackHelper.removeItem(this.getItems(), index, p_59614_);
        if (!itemstack.isEmpty()) {
            this.setChanged();
            if(index == 0)
            {
                crafted[0] = false;
                sync();
            }if(index == 0)
            {
                crafted[1] = false;
                sync();
            }if(index == 0)
            {
                crafted[2] = false;
                sync();
            }
        }


        return itemstack;
    }

    public void craft(){
        Inventory inv = new Inventory(3);
        for (int i = 0; i < 3; i++) {
            inv.setItem(i, this.items.get(i));
        }

        Optional<DryingRackRecipe> recipe = level.getRecipeManager()
                .getRecipeFor(ModRecipeTypes.DRYING_RACK_RECIPE, inv, level);

        TileEntity blockEntity = level.getBlockEntity(this.worldPosition);
        if(blockEntity instanceof DryingRackTile) {
            recipe.ifPresent(iRecipe -> {
                ItemStack recipeOutput = iRecipe.getResultItem();
                ItemStack input = iRecipe.getIngredients().get(0).getItems()[0];

                if (input.getItem() == this.items.get(0).getItem()) {
                    // FIRST SLOT MATCHES

                    if (!crafting[0]) {
                        crafting[0] = true;
                        output[0] = recipeOutput.copy();
                        dryingTimeMax[0] = iRecipe.getDryingTime();
                        dryingTime[0] = dryingTimeMax[0];
                        sync();
                    }

                } else {
                    if (crafting[0]) {
                        crafting[0] = false;
                        sync();
                    }
                }

                if (input.getItem() == this.items.get(1).getItem()) {
                    // SECOND SLOT MATCHES

                    if (!crafting[1]) {
                        crafting[1] = true;
                        output[1] = recipeOutput.copy();;
                        dryingTimeMax[1] = iRecipe.getDryingTime();
                        dryingTime[1] = dryingTimeMax[1];
                        sync();
                    }

                } else {
                    if (crafting[1]) {
                        crafting[1] = false;
                        sync();
                    }
                }

                if (input.getItem() == this.items.get(2).getItem()) {
                    // THIRD SLOT MATCHES

                    if (!crafting[2]) {
                        crafting[2] = true;
                        output[2] = recipeOutput.copy();
                        dryingTimeMax[2] = iRecipe.getDryingTime();
                        dryingTime[2] = dryingTimeMax[2];
                        sync();
                    }

                } else {
                    if (crafting[2]) {
                        crafting[2] = false;
                        sync();
                    }
                }

            });
        }
//

    }


    private void craftTheItem(ItemStack output, int slot) {
        output.setCount(this.items.get(slot).getCount());
        this.setItem(slot, output);
    }


    @Override
    public void load(CompoundNBT nbt) {
//        itemHandler.deserializeNBT(nbt.getCompound("inv"));
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(nbt)) {
            ItemStackHelper.loadAllItems(nbt, this.items);
        }
//        super.read(state, nbt);
//        if (nbt.contains("CustomName", 8))
//            this.customName = Component.Serializer.fromJson(nbt.getString("CustomName"));

        if (nbt.contains("dryingTime[0]",  INBT.TAG_INT))
            dryingTime[0] = nbt.getInt("dryingTime[0]");
        if (nbt.contains("dryingTime[1]",  INBT.TAG_INT))
            dryingTime[1] = nbt.getInt("dryingTime[1]");
        if (nbt.contains("dryingTime[2]",  INBT.TAG_INT))
            dryingTime[2] = nbt.getInt("dryingTime[2]");
        if (nbt.contains("crafting[0]",  INBT.TAG_INT))
            crafting[0] = nbt.getInt("crafting[0]") == 1;
        if (nbt.contains("crafting[1]",  INBT.TAG_INT))
            crafting[1] = nbt.getInt("crafting[1]") == 1;
        if (nbt.contains("crafting[2]",  INBT.TAG_INT))
            crafting[2] = nbt.getInt("crafting[2]") == 1;
        if (nbt.contains("crafted[0]",  INBT.TAG_INT))
            crafted[0] = nbt.getInt("crafted[0]") == 1;
        if (nbt.contains("crafted[1]",  INBT.TAG_INT))
            crafted[1] = nbt.getInt("crafted[1]") == 1;
        if (nbt.contains("crafted[2]",  INBT.TAG_INT))
            crafted[2] = nbt.getInt("crafted[2]") == 1;
        super.load(nbt);

    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Hexerei.MOD_ID + ".dipper");
    }

    @Override
    protected Container createMenu(int p_58627_, PlayerInventory p_58628_) {
        return null;
    }

    public void saveAdditional(CompoundNBT compound) {
        ItemStackHelper.saveAllItems(compound, this.items);

        compound.putInt("dryingTime[0]", dryingTime[0]);
        compound.putInt("dryingTime[1]", dryingTime[1]);
        compound.putInt("dryingTime[2]", dryingTime[2]);

        compound.putInt("crafted[0]", crafted[0] ? 1 : 0);
        compound.putInt("crafted[1]", crafted[1] ? 1 : 0);
        compound.putInt("crafted[2]", crafted[2] ? 1 : 0);

        compound.putInt("crafting[0]", crafting[0] ? 1 : 0);
        compound.putInt("crafting[1]", crafting[1] ? 1 : 0);
        compound.putInt("crafting[2]", crafting[2] ? 1 : 0);
    }


//    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.saveAdditional(compound);
//        compound.put("inv", itemHandler.serializeNBT());
//        if (this.customName != null)
//            compound.putString("CustomName", Component.Serializer.toJson(this.customName));
        ItemStackHelper.saveAllItems(compound, this.items);

        compound.putInt("dryingTime[0]", dryingTime[0]);
        compound.putInt("dryingTime[1]", dryingTime[1]);
        compound.putInt("dryingTime[2]", dryingTime[2]);

        compound.putInt("crafted[0]", crafted[0] ? 1 : 0);
        compound.putInt("crafted[1]", crafted[1] ? 1 : 0);
        compound.putInt("crafted[2]", crafted[2] ? 1 : 0);

        compound.putInt("crafting[0]", crafting[0] ? 1 : 0);
        compound.putInt("crafting[1]", crafting[1] ? 1 : 0);
        compound.putInt("crafting[2]", crafting[2] ? 1 : 0);

        return compound;
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

    public static double getDistanceToEntity(Entity entity, BlockPos pos) {
        double deltaX = entity.position().x() - pos.getX() - 0.5f;
        double deltaY = entity.position().y() - pos.getY() - 0.5f;
        double deltaZ = entity.position().z() - pos.getZ() - 0.5f;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY) + (deltaZ * deltaZ));
    }

    public static double getDistance(float x1, float y1, float x2, float y2) {
        double deltaX = x2 - x1;
        double deltaY = y2 - y1;

        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
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

    private float moveTo(float input, float moveTo, float speed)
    {
        float distance = moveTo - input;

        if(Math.abs(distance) <= speed)
        {
            return moveTo;
        }

        if(distance > 0)
        {
            input += speed;
        } else {
            input -= speed;
        }

        return input;
    }

    private float moveToAngle(float input, float movedTo, float speed)
    {
        float distance = movedTo - input;

        if(Math.abs(distance) <= speed)
        {
            return movedTo;
        }

        if(distance > 0)
        {
            if(Math.abs(distance) < 180)
                input += speed;
            else
                input -= speed;
        } else {
            if(Math.abs(distance) < 180)
                input -= speed;
            else
                input += speed;
        }

        if(input < -90){
            input += 360;
        }
        if(input > 270)
            input -= 360;

        return input;
    }

    public float getAngle(Vector3d pos) {
        float angle = (float) Math.toDegrees(Math.atan2(pos.z() - this.getBlockPos().getZ() - 0.5f, pos.x() - this.getBlockPos().getX() - 0.5f));

        if(angle < 0){
            angle += 360;
        }

        return angle;
    }

    public float getSpeed(double pos, double posTo) {
        return (float)(0.01f + 0.10f * (Math.abs(pos - posTo) / 3f));
    }

    public Vector3d rotateAroundVec(Vector3d vector3dCenter,float rotation,Vector3d vector3d)
    {
        Vector3d newVec = vector3d.subtract(vector3dCenter);
        newVec = newVec.yRot(rotation/180f*(float)Math.PI);
        newVec = newVec.add(vector3dCenter);

        return newVec;
    }

    public int putItems (int slot, @Nonnull ItemStack stack) {
        ItemStack stack1 = stack.copy();
        Random rand = new Random();
        if (this.items.get(slot).isEmpty()) {

            if(stack1.getCount() > 2) {
                stack1.setCount(3);
                this.items.set(slot, stack1);
                sync();
                stack.shrink(3);

                for(int i = 0; i < 3; i++)
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 1.0F);
            }
            else
            {
                this.items.set(slot, stack1);
                sync();
                stack.shrink(stack.getCount());

                for(int i = 0; i < stack1.getCount(); i++)
                    level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 1.0F);
            }
            return 1;
        }

        if (!this.items.get(slot).sameItem(stack))
            return 0;
        if(!ItemStack.isSameItemSameTags(stack, this.items.get(slot)))
            return 0;

        int count = this.items.get(slot).getCount();

        if(stack1.getCount() > 2 - count) {
            stack1.setCount(3);
            this.items.set(slot, stack1);
            sync();
            stack.shrink(3 - count);

            for(int i = 0; i < 3 - count; i++)
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 1.0F);
        }
        else
        {
            stack1.setCount(count + stack1.getCount());
            this.items.set(slot, stack1);
            sync();
            stack.shrink(stack.getCount());

            for(int i = 0; i < stack1.getCount(); i++)
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 1.0F);
        }


        return 1;
    }

    public int interactDryingRack (PlayerEntity player, BlockRayTraceResult hit) {
        if(!player.isShiftKeyDown()) {
            if (!player.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                Random rand = new Random();
                if (this.items.get(0).isEmpty() || (player.getItemInHand(Hand.MAIN_HAND) == this.items.get(0))) {
                    putItems(0, player.getItemInHand(Hand.MAIN_HAND));
                    dryingTime[0] = dryingTimeMax[0];
                    crafted[0] = false;

                    return 1;
                }else if(ItemStack.isSameItemSameTags(player.getItemInHand(Hand.MAIN_HAND), this.items.get(0)) && this.items.get(0).getCount() < getMaxStackSize()){
                    putItems(0, player.getItemInHand(Hand.MAIN_HAND));
                } else if (this.items.get(1).isEmpty() || (player.getItemInHand(Hand.MAIN_HAND) == this.items.get(1) && this.items.get(1).getCount() < getMaxStackSize())) {
                    putItems(1, player.getItemInHand(Hand.MAIN_HAND));
                    dryingTime[1] = dryingTimeMax[1];
                    crafted[1] = false;

                    return 1;
                }else if(ItemStack.isSameItemSameTags(player.getItemInHand(Hand.MAIN_HAND), this.items.get(1)) && this.items.get(1).getCount() < getMaxStackSize()){
                    putItems(1, player.getItemInHand(Hand.MAIN_HAND));
                } else if (this.items.get(2).isEmpty() || (player.getItemInHand(Hand.MAIN_HAND) == this.items.get(2) && this.items.get(2).getCount() < getMaxStackSize())) {
                    putItems(2, player.getItemInHand(Hand.MAIN_HAND));
                    dryingTime[2] = dryingTimeMax[2];
                    crafted[2] = false;

                    return 1;
                }else if(ItemStack.isSameItemSameTags(player.getItemInHand(Hand.MAIN_HAND), this.items.get(2)) && this.items.get(2).getCount() < getMaxStackSize()){
                    putItems(2, player.getItemInHand(Hand.MAIN_HAND));
                }
            }
            if(crafted[0]){
                crafted[0] = false;
                dryingTime[0] = dryingTimeMax[0];
                player.inventory.placeItemBackInInventory(this.items.get(0).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(0, ItemStack.EMPTY);
                output[0] = ItemStack.EMPTY;
            }
            if(crafted[1]){
                crafted[1] = false;
                dryingTime[1] = dryingTimeMax[1];
                player.inventory.placeItemBackInInventory(this.items.get(1).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(1, ItemStack.EMPTY);
                output[1] = ItemStack.EMPTY;
            }
            if(crafted[2]){
                crafted[2] = false;
                dryingTime[2] = dryingTimeMax[2];
                player.inventory.placeItemBackInInventory(this.items.get(2).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(2, ItemStack.EMPTY);
                output[2] = ItemStack.EMPTY;
            }
        }
        else
        {
            if(!crafting[0]) {

                crafted[0] = false;
                dryingTime[0] = dryingTimeMax[0];
                player.inventory.placeItemBackInInventory(this.items.get(0).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(0, ItemStack.EMPTY);
                output[0] = ItemStack.EMPTY;
            }
            if(!this.items.get(1).isEmpty() && !crafting[1]) {

                crafted[1] = false;
                dryingTime[1] = dryingTimeMax[1];
                player.inventory.placeItemBackInInventory(this.items.get(1).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(1, ItemStack.EMPTY);
                output[1] = ItemStack.EMPTY;
            }
            if(!this.items.get(2).isEmpty() && !crafting[2]) {

                crafted[2] = false;
                dryingTime[2] = dryingTimeMax[2];
                player.inventory.placeItemBackInInventory(this.items.get(2).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                this.items.set(2, ItemStack.EMPTY);
                output[2] = ItemStack.EMPTY;
            }
        }

        return 0;
    }

//    @Override
    public void tick() {

        if(level instanceof ServerWorld) {
            craft();
        }

        for(int i = 0; i < 3; i++){
            if (crafting[i]) {
                if (dryingTime[i] > 0)
                    dryingTime[i]--;
                if (dryingTime[i] == 0) {
                    crafted[i] = true;
                    crafting[i] = false;
                    craftTheItem(output[i], i);
                }
            }
        }
    }

    @Override
    public int[] getSlotsForFace(Direction p_19238_) {
        return new int[]{0, 1, 2};
    }

    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStackIn);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return this.items.get(index).isEmpty() || this.items.get(index).getCount() < getMaxStackSize();
    }

    @Override
    public boolean canTakeItemThroughFace(int p_19239_, ItemStack p_19240_, Direction p_19241_) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

}
