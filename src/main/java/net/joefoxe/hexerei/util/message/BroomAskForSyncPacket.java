package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroomAskForSyncPacket {
    int sourceId;

    public BroomAskForSyncPacket(Entity entity) {
        this.sourceId = entity.getId();
    }
    public BroomAskForSyncPacket(PacketBuffer buf) {
        this.sourceId = buf.readInt();

    }

    public static void encode(BroomAskForSyncPacket object, PacketBuffer buffer) {
        buffer.writeInt(object.sourceId);
    }

    public static BroomAskForSyncPacket decode(PacketBuffer buffer) {
        return new BroomAskForSyncPacket(buffer);
    }

    public static void consume(BroomAskForSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            ((BroomEntity)world.getEntity(packet.sourceId)).sync();
        });
        ctx.get().setPacketHandled(true);
    }
}