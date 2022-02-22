package net.joefoxe.hexerei.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public interface SidedProxy {
    PlayerEntity getPlayer();
    World getLevel();
    void init();

    void openCodexGui();
}