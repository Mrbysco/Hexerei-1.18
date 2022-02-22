package net.joefoxe.hexerei.block;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Nullable;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.IBlockReader;
import net.minecraft.tileentity.TileEntity;

public interface ITileEntity<T extends TileEntity> {

    Class<T> getTileEntityClass();

    default void withTileEntityDo(IBlockReader world, BlockPos pos, Consumer<T> action) {
        getTileEntityOptional(world, pos).ifPresent(action);
    }

    default ActionResultType onTileEntityUse(IBlockReader world, BlockPos pos, Function<T, ActionResultType> action) {
        return getTileEntityOptional(world, pos).map(action)
                .orElse(ActionResultType.PASS);
    }

    default Optional<T> getTileEntityOptional(IBlockReader world, BlockPos pos) {
        return Optional.ofNullable(getBlockEntity(world, pos));
    }

    @Nullable
    @SuppressWarnings("unchecked")
    default T getBlockEntity(IBlockReader worldIn, BlockPos pos) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        Class<T> expectedClass = getTileEntityClass();

        if (tileEntity == null)
            return null;
        if (!expectedClass.isInstance(tileEntity))
            return null;

        return (T) tileEntity;
    }

}