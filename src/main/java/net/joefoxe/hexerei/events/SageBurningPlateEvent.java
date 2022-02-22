package net.joefoxe.hexerei.events;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.block.custom.Candle;
import net.joefoxe.hexerei.block.custom.SageBurningPlate;
import net.joefoxe.hexerei.config.HexConfig;
import net.joefoxe.hexerei.tileentity.ModTileEntities;
import net.joefoxe.hexerei.tileentity.SageBurningPlateTile;
import net.joefoxe.hexerei.util.HexereiTags;
import net.joefoxe.hexerei.util.HexereiUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.entity.SpawnReason;
import net.minecraft.world.World;
import net.minecraft.block.Block;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.List;

@EventBusSubscriber
public class SageBurningPlateEvent {


    @SubscribeEvent
    public void onEntityJoin(LivingSpawnEvent.CheckSpawn e) {
        World world = e.getWorld().isClientSide() ? null : e.getWorld() instanceof World ? (World)e.getWorld() : null;

        if (world == null) {
            return;
        }

        //TODO Add server config check here

        if(e.getSpawnReason() != SpawnReason.NATURAL)
            return;

        if(HexConfig.SAGE_BURNING_PLATE_RANGE.get()==0)return;

        Entity entity = e.getEntity();

        if (entity.getTags().contains(Hexerei.MOD_ID + ".checked" )) {

            return;
        }
        entity.addTag(Hexerei.MOD_ID + ".checked");

        if (!HexereiUtil.entityIsHostile(entity)) {
            return;
        }

        List<BlockPos> nearbySageBurningPlates = HexereiUtil.getAllTileEntityPositionsNearby(ModTileEntities.SAGE_BURNING_PLATE_TILE.get(), HexConfig.SAGE_BURNING_PLATE_RANGE.get() + 1, world, entity);

        if (nearbySageBurningPlates.size() == 0) {
            return;
        }

        BlockPos burning_plate = null;
        for (BlockPos nearbySageBurningPlate : nearbySageBurningPlates) {
            BlockState burning_platestate = world.getBlockState(nearbySageBurningPlate);
            Block block = burning_platestate.getBlock();
            TileEntity blockEntity = world.getBlockEntity(nearbySageBurningPlate);
            if(!burning_platestate.getValue(SageBurningPlate.LIT)) {
                continue;
            }
            if (!(block instanceof SageBurningPlate)) {
                continue;
            }

            burning_plate = nearbySageBurningPlate.immutable();
            break;
        }

        if (burning_plate == null) {
            return;
        }

        List<Entity> passengers = entity.getPassengers();
        if (passengers.size() > 0) {
            for (Entity passenger : passengers) {
                passenger.remove(RemovalReason.DISCARDED);
            }
        }

        e.setResult(Result.DENY);
    }
}