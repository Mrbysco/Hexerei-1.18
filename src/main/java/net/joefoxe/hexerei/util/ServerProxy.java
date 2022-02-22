package net.joefoxe.hexerei.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class ServerProxy implements SidedProxy {
    @Override
    public PlayerEntity getPlayer() {
        return null;
    }

    @Override
    public World getLevel() {
        return null;
    }

    @Override
    public void init() {
        //
    }

    @Override
    public void openCodexGui() {
        //
    }
}