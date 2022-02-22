package net.joefoxe.hexerei.util.message;

import net.joefoxe.hexerei.Hexerei;
import net.minecraft.util.math.BlockPos;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TESyncPacket {
    BlockPos pos;
    CompoundNBT tag;

    public TESyncPacket(BlockPos pos, CompoundNBT tag) {
        this.pos = pos;
        this.tag = tag;
    }

    public static void encode(TESyncPacket object, PacketBuffer buffer) {
        buffer.writeBlockPos(object.pos);
        buffer.writeNbt(object.tag);
    }

    public static TESyncPacket decode(PacketBuffer buffer) {
        return new TESyncPacket(buffer.readBlockPos(), buffer.readNbt());
    }

    public static void consume(TESyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            World world;
            if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
                world = Hexerei.proxy.getLevel();
            }
            else {
                if (ctx.get().getSender() == null) return;
                world = ctx.get().getSender().level;
            }

            if(world.getBlockEntity(packet.pos) != null){
                world.getBlockEntity(packet.pos).load(packet.tag);
                world.getBlockEntity(packet.pos).setChanged();
            }
        });
        ctx.get().setPacketHandled(true);
    }
}