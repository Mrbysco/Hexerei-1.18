package net.joefoxe.hexerei.util;

import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.tileentity.TileEntity;

public class WorldUtil {
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T extends TileEntity> T getBlockEntity(IBlockReader worldIn, BlockPos posIn, Class<T> type) {
        TileEntity tileEntity = worldIn.getBlockEntity(posIn);
        if (tileEntity != null && tileEntity.getClass().isAssignableFrom(type)) {
            return (T) tileEntity;
        }
        return null;
    }

    @SuppressWarnings({ "unchecked", "deprecation" })
    @Nullable
    public static <T extends Entity> T getCachedEntity(@Nullable World worldIn, Class<T> type, @Nullable T cached, @Nullable UUID uuid) {
        if ((cached == null || cached.isRemoved()) && uuid != null && worldIn instanceof ServerWorld) {
            Entity entity = ((ServerWorld) worldIn).getPlayerByUUID(uuid);
            if (entity != null && entity.getClass().isAssignableFrom(type)) {
                return (T) entity;
            } else {
                return null;
            }
        }
        return cached;
    }
    public static Optional<BlockPos> immutable(BlockPos pos) {return pos != null ? Optional.of(pos.immutable()) : Optional.empty();}
    public static Optional<BlockPos> immutable(Optional<BlockPos> pos) {
        return pos.map(BlockPos::immutable);
    }
}