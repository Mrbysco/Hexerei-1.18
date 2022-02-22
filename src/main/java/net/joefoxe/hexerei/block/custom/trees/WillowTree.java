package net.joefoxe.hexerei.block.custom.trees;


import net.joefoxe.hexerei.world.gen.ModConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.block.trees.Tree;

import java.util.Random;

public class WillowTree extends Tree {


    @org.jetbrains.annotations.Nullable
    @Override
    protected ConfiguredFeature<?, ?> getConfiguredFeature(Random p_60014_, boolean p_60015_) {
        return ModConfiguredFeatures.WILLOW;
    }
}
