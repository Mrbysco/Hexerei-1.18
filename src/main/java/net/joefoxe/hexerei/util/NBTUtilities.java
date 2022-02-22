package net.joefoxe.hexerei.util;

import java.util.UUID;
import javax.annotation.Nullable;

import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;

public class NBTUtilities {
    public static void putUniqueId(CompoundNBT compound, String key, @Nullable UUID uuid) {
        if (uuid != null) {
            compound.putUUID(key, uuid);
        }
    }
    @Nullable
    public static UUID getUniqueId(CompoundNBT compound, String key) {
        if (compound.hasUUID(key)) {
            return compound.getUUID(key);
        } else if (NBTUtilities.hasOldUniqueId(compound, key)) {
            return NBTUtilities.getOldUniqueId(compound, key);
        }
        return null;
    }
    public static UUID getOldUniqueId(CompoundNBT compound, String key) {return new UUID(compound.getLong(key + "Most"), compound.getLong(key + "Least"));}
    public static boolean hasOldUniqueId(CompoundNBT compound, String key) {return compound.contains(key + "Most", INBT.TAG_ANY_NUMERIC) && compound.contains(key + "Least", INBT.TAG_ANY_NUMERIC);}
    public static void removeOldUniqueId(CompoundNBT compound, String key) {
        compound.remove(key + "Most");
        compound.remove(key + "Least");
    }
    public static void putResourceLocation(CompoundNBT compound, String key, @Nullable ResourceLocation rl) {
        if (rl != null) {compound.putString(key, rl.toString());}
    }

}