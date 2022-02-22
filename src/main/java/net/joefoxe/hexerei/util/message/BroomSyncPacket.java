package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroomSyncPacket {
    int sourceId;
    CompoundNBT tag;

    public BroomSyncPacket(Entity entity, CompoundNBT tag) {
        this.sourceId = entity.getId();
        this.tag = tag;
    }
    public BroomSyncPacket(PacketBuffer buf) {
        this.sourceId = buf.readInt();
        this.tag = buf.readNbt();
    }

    public static void encode(BroomSyncPacket object, PacketBuffer buffer) {
        buffer.writeInt(object.sourceId);
        buffer.writeNbt(object.tag);
    }

    public static BroomSyncPacket decode(PacketBuffer buffer) {
        return new BroomSyncPacket(buffer);
    }

    public static void consume(BroomSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            if(world.getEntity(packet.sourceId) != null) {
                world.getEntity(packet.sourceId).load(packet.tag);
                ((BroomEntity) world.getEntity(packet.sourceId)).setChanged();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}