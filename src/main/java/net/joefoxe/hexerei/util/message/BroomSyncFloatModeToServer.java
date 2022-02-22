package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class BroomSyncFloatModeToServer {
    int sourceId;
    boolean mode;

    public BroomSyncFloatModeToServer(Entity entity, boolean tag) {
        this.sourceId = entity.getId();
        this.mode = tag;
    }
    public BroomSyncFloatModeToServer(PacketBuffer buf) {
        this.sourceId = buf.readInt();
        this.mode = buf.readBoolean();

    }

    public static void encode(BroomSyncFloatModeToServer object, PacketBuffer buffer) {
        buffer.writeInt(object.sourceId);
        buffer.writeBoolean(object.mode);
    }

    public static BroomSyncFloatModeToServer decode(PacketBuffer buffer) {
        return new BroomSyncFloatModeToServer(buffer);
    }

    public static void consume(BroomSyncFloatModeToServer packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            ((BroomEntity)world.getEntity(packet.sourceId)).setFloatMode(packet.mode);
        });
        ctx.get().setPacketHandled(true);
    }
}