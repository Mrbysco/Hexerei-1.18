package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroomDamageBrushToServer {
    int sourceId;
    float rotation;

    public BroomDamageBrushToServer(Entity entity) {
        this.sourceId = entity.getId();
    }
    public BroomDamageBrushToServer(PacketBuffer buf) {
        this.sourceId = buf.readInt();

    }

    public static void encode(BroomDamageBrushToServer object, PacketBuffer buffer) {
        buffer.writeInt(object.sourceId);
    }

    public static BroomDamageBrushToServer decode(PacketBuffer buffer) {
        return new BroomDamageBrushToServer(buffer);
    }

    public static void consume(BroomDamageBrushToServer packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            ((BroomEntity)world.getEntity(packet.sourceId)).damageBrush();
        });
        ctx.get().setPacketHandled(true);
    }
}