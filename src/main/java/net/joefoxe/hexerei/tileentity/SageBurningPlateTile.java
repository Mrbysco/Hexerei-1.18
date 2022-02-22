package net.joefoxe.hexerei.tileentity;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.block.custom.SageBurningPlate;
import net.joefoxe.hexerei.config.HexConfig;
import net.joefoxe.hexerei.item.ModItems;
import net.joefoxe.hexerei.particle.ModParticleTypes;
import net.joefoxe.hexerei.util.HexereiPacketHandler;
import net.joefoxe.hexerei.util.message.EmitExtinguishParticlesPacket;
import net.joefoxe.hexerei.util.message.EmitParticlesPacket;
import net.joefoxe.hexerei.util.message.TESyncPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.network.IPacket;
import net.minecraft.client.network.play.IClientPlayNetHandler;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
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
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Hand;

public class SageBurningPlateTile extends LockableLootTileEntity implements ISidedInventory, IClearable, INamedContainerProvider {

//    public final ItemStackHandler itemHandler = createHandler();
//    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
    protected NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);

    public int burnTimeMax = 20;
    public int burnTime = 20;

    public SageBurningPlateTile(TileEntityType<?> tileEntityTypeIn, BlockPos blockPos, BlockState blockState) {
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
        return 1;
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


    public SageBurningPlateTile(BlockPos blockPos, BlockState blockState) {
        this(ModTileEntities.SAGE_BURNING_PLATE_TILE.get(),blockPos, blockState);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        if (index >= 0 && index < this.items.size()) {
            ItemStack itemStack = stack.copy();
            this.items.set(index, itemStack);
            this.burnTime = this.burnTimeMax;
        }

        sync();
    }

    @Override
    public ItemStack removeItem(int index, int p_59614_) {
        this.unpackLootTable((PlayerEntity)null);
        if(level.getBlockState(worldPosition).getValue(BlockStateProperties.LIT)) {
            Random random = new Random();
            level.setBlock(worldPosition, level.getBlockState(worldPosition).setValue(BlockStateProperties.LIT, false), 11);
            this.level.playSound((PlayerEntity) null, worldPosition, SoundEvents.CANDLE_EXTINGUISH, SoundCategory.PLAYERS, 1.0F, random.nextFloat() * 0.4F + 1.0F);

            sync();
            if(!level.isClientSide)
                HexereiPacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(worldPosition)), new EmitExtinguishParticlesPacket(worldPosition));
        }

        ItemStack itemstack = ItemStackHelper.removeItem(this.getItems(), index, p_59614_);
        sync();


        return itemstack;
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

        if (nbt.contains("burnTime",  INBT.TAG_INT))
            burnTime = nbt.getInt("burnTime");
        if (nbt.contains("burnTimeMax",  INBT.TAG_INT))
            burnTimeMax = nbt.getInt("burnTimeMax");
        super.load(nbt);

    }

    @Override
    protected ITextComponent getDefaultName() {
        return new TranslationTextComponent("container." + Hexerei.MOD_ID + ".sage_burning_plate");
    }

    @Override
    protected Container createMenu(int p_58627_, PlayerInventory p_58628_) {
        return null;
    }

    public void saveAdditional(CompoundNBT compound) {
        ItemStackHelper.saveAllItems(compound, this.items);

        compound.putInt("burnTime", burnTime);

        compound.putInt("burnTimeMax", burnTimeMax);
    }


//    @Override
    public CompoundNBT save(CompoundNBT compound) {
        super.saveAdditional(compound);
//        compound.put("inv", itemHandler.serializeNBT());
//        if (this.customName != null)
//            compound.putString("CustomName", Component.Serializer.toJson(this.customName));
        ItemStackHelper.saveAllItems(compound, this.items);

        compound.putInt("burnTime", burnTime);

        compound.putInt("burnTimeMax", burnTimeMax);

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

        if (this.items.get(slot).isEmpty() && canPlaceItem(slot, stack)) {
            stack1.setCount(1);
            this.items.set(slot, stack1);
            this.burnTime = this.burnTimeMax;
            sync();
            stack.shrink(1);
            level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_PICKUP, SoundCategory.BLOCKS, 1.0F, rand.nextFloat() * 0.4F + 1.0F);
            return 1;
        }

        return 0;
    }

    public int interactSageBurningPlate (PlayerEntity player, BlockRayTraceResult hit) {
        if(!player.isShiftKeyDown()) {

            if (!player.getItemInHand(Hand.MAIN_HAND).isEmpty()) {
                Random rand = new Random();
                if (this.items.get(0).isEmpty()) {
                    putItems(0, player.getItemInHand(Hand.MAIN_HAND));
                    return 1;
                }
            }

        }
        else
        {

            if (!this.items.get(0).isEmpty()) {
                player.inventory.placeItemBackInInventory(this.items.get(0).copy());
                level.playSound((PlayerEntity) null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 1.0F);
                removeItem(0, 1);


            }

        }

        return 0;
    }

    public void extinguishParticles()
    {
        Random rand = new Random();
        float offsetX = 0;
        float offsetZ = 0;
        float damageOutOf5 = (getItems().get(0).getMaxDamage()-getItems().get(0).getDamageValue())/(float)getItems().get(0).getMaxDamage()*5f;
        if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.NORTH)
        {
            offsetX = -0.25f;
            if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                offsetX += 0.09;
            if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                offsetX += 0.18;
            if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                offsetX += 0.25;
            if(damageOutOf5 <= 1f && damageOutOf5 >= 0f)
                offsetX += 0.33;
        }
        if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.SOUTH)
        {
            offsetX = 0.25f;
            if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                offsetX -= 0.09;
            if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                offsetX -= 0.18;
            if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                offsetX -= 0.25;
            if(damageOutOf5 <= 1f && damageOutOf5 >= 0f)
                offsetX -= 0.33;
        }
        if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.WEST)
        {
            offsetZ = 0.25f;
            if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                offsetZ -= 0.09;
            if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                offsetZ -= 0.18;
            if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                offsetZ -= 0.25;
            if(damageOutOf5 <= 1f && damageOutOf5 >= 0f)
                offsetZ -= 0.33;
        }
        if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.EAST)
        {
            offsetZ = -0.25f;
            if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                offsetZ += 0.09;
            if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                offsetZ += 0.18;
            if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                offsetZ += 0.25;
            if(damageOutOf5 <= 1f && damageOutOf5 >= 0f)
                offsetZ += 0.33;
        }

        level.addParticle(ParticleTypes.LARGE_SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
        level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
        level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
        level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
        level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);

    }

    public void emitParticles(){
        Random rand = new Random();
        if(rand.nextInt(4) == 0 && level.isClientSide) {
            float offsetX = 0;
            float offsetZ = 0;
            float damageOutOf5 = (getItems().get(0).getMaxDamage()-getItems().get(0).getDamageValue())/(float)getItems().get(0).getMaxDamage()*5f;
            if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.NORTH)
            {
                offsetX = -0.25f;
                if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                    offsetX += 0.09;
                if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                    offsetX += 0.18;
                if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                    offsetX += 0.25;
                if(damageOutOf5 <= 1f && damageOutOf5 > 0f)
                    offsetX += 0.33;
            }
            if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.SOUTH)
            {
                offsetX = 0.25f;
                if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                    offsetX -= 0.09;
                if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                    offsetX -= 0.18;
                if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                    offsetX -= 0.25;
                if(damageOutOf5 <= 1f && damageOutOf5 > 0f)
                    offsetX -= 0.33;
            }
            if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.WEST)
            {
                offsetZ = 0.25f;
                if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                    offsetZ -= 0.09;
                if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                    offsetZ -= 0.18;
                if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                    offsetZ -= 0.25;
                if(damageOutOf5 <= 1f && damageOutOf5 > 0f)
                    offsetZ -= 0.33;
            }
            if(this.getBlockState().getValue(HorizontalBlock.FACING) == Direction.EAST)
            {
                offsetZ = -0.25f;
                if(damageOutOf5 <= 4f && damageOutOf5 > 3f)
                    offsetZ += 0.09;
                if(damageOutOf5 <= 3f && damageOutOf5 > 2f)
                    offsetZ += 0.18;
                if(damageOutOf5 <= 2f && damageOutOf5 > 1f)
                    offsetZ += 0.25;
                if(damageOutOf5 <= 1f && damageOutOf5 > 0f)
                    offsetZ += 0.33;
            }

                level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
            if(rand.nextInt(10) == 0)
                level.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, worldPosition.getX() + 0.5f + offsetX, worldPosition.getY()+0.25f, worldPosition.getZ() + 0.5f + offsetZ, (rand.nextDouble() - 0.5d) / 50d, (rand.nextDouble() + 0.5d) * 0.045d, (rand.nextDouble() - 0.5d) / 50d);
        }

        if(this.getBlockState().getValue(SageBurningPlate.MODE) != 3){
            for (int i = 0; i < 360; i++) {
                Vector3d vec = new Vector3d(MathHelper.sin((i / 360f) * (2 * MathHelper.PI)) * (rand.nextFloat() * HexConfig.SAGE_BURNING_PLATE_RANGE.get()), MathHelper.sin((rand.nextInt(360) / 360f) * (2 * MathHelper.PI)) * (rand.nextFloat() * HexConfig.SAGE_BURNING_PLATE_RANGE.get()), MathHelper.cos((i / 360f) * (2 * MathHelper.PI)) * (rand.nextFloat() * HexConfig.SAGE_BURNING_PLATE_RANGE.get()));

                Vector3d vec2 = new Vector3d(MathHelper.sin((i / 360f) * (2 * MathHelper.PI)) * (HexConfig.SAGE_BURNING_PLATE_RANGE.get()), 0, MathHelper.cos((i / 360f) * (2 * MathHelper.PI)) * (HexConfig.SAGE_BURNING_PLATE_RANGE.get()));
                BlockPos pos2 = new BlockPos(worldPosition.getX() + 0.5f + vec2.x(), worldPosition.getY() + 0.25f + vec2.y(), worldPosition.getZ() + 0.5f + vec2.z());

                if (rand.nextInt(40) == 0 && (this.getBlockState().getValue(SageBurningPlate.MODE) == 0 || this.getBlockState().getValue(SageBurningPlate.MODE) == 1)) {
                    BlockPos pos = new BlockPos(worldPosition.getX() + 0.5f + vec.x(), worldPosition.getY() + 0.25f + vec.y(), worldPosition.getZ() + 0.5f + vec.z());

                    if ((!level.getBlockState(pos.below()).isAir() || !level.getBlockState(pos.below().below()).isAir()) && level.getBlockState(pos).isAir())
                        level.addParticle(ModParticleTypes.FOG.get(), pos.getX(), pos.getY(), pos.getZ(), (rand.nextDouble() - 0.5d) / 15d, (rand.nextDouble() + 0.5d) * 0.015d, (rand.nextDouble() - 0.5d) / 15d);

                }
                if (rand.nextInt(160) == 0 && (this.getBlockState().getValue(SageBurningPlate.MODE) == 1 || this.getBlockState().getValue(SageBurningPlate.MODE) == 2))
                    level.addParticle(ModParticleTypes.FOG.get(), pos2.getX(), pos2.getY(), pos2.getZ(), (rand.nextDouble() - 0.5d) / 15d, (rand.nextDouble() + 0.5d) * 0.015d, (rand.nextDouble() - 0.5d) / 15d);

            }
        }
    }

//    @Override
    public void tick() {

        if(this.getBlockState().getValue(SageBurningPlate.LIT)){
            if (this.burnTime <= 0) {
                if(!level.isClientSide){
                    this.items.get(0).hurt(1, new Random(), null);

                    if (this.items.get(0).getDamageValue() >= this.items.get(0).getMaxDamage()) {
                        removeItem(0, 1);
                    } else
                        sync();
                    this.burnTime = this.burnTimeMax;
                }
                else {


                }

            } else {
                this.burnTime--;
                emitParticles();
            }
        }

    }

    @Override
    public int[] getSlotsForFace(Direction p_19238_) {
        return new int[]{0};
    }

    public boolean canPlaceItemThroughFace(int index, ItemStack itemStackIn, @Nullable Direction direction) {
        return this.canPlaceItem(index, itemStackIn);
    }

    public boolean canPlaceItem(int index, ItemStack stack) {
        return this.items.get(index).isEmpty() && stack.is(ModItems.DRIED_SAGE_BUNDLE.get());
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack p_19240_, Direction p_19241_) {
        return true;
    }

    @Override
    public int getContainerSize() {
        return this.items.size();
    }

}
