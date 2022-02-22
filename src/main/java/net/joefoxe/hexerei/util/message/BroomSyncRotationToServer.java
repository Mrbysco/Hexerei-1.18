package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroomSyncRotationToServer {
    int sourceId;
    float rotation;

    public BroomSyncRotationToServer(Entity entity, float tag) {
        this.sourceId = entity.getId();
        this.rotation = tag;
    }
    public BroomSyncRotationToServer(PacketBuffer buf) {
        this.sourceId = buf.readInt();
        this.rotation = buf.readFloat();

    }

    public static void encode(BroomSyncRotationToServer object, PacketBuffer buffer) {
        buffer.writeInt(object.sourceId);
        buffer.writeFloat(object.rotation);
    }

    public static BroomSyncRotationToServer decode(PacketBuffer buffer) {
        return new BroomSyncRotationToServer(buffer);
    }

    public static void consume(BroomSyncRotationToServer packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            ((BroomEntity)world.getEntity(packet.sourceId)).setRotation(packet.rotation);
        });
        ctx.get().setPacketHandled(true);
    }
}