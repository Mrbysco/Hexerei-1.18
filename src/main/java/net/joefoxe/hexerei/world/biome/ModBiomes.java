package net.joefoxe.hexerei.world.biome;

import net.joefoxe.hexerei.Hexerei;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.data.worldgen.StructureFeatures;
import net.minecraft.data.worldgen.placement.AquaticPlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;


import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;

public class ModBiomes {
    public static final DeferredRegister<Biome> BIOMES
            = DeferredRegister.create(ForgeRegistries.BIOMES, Hexerei.MOD_ID);

    public static final RegistryObject<Biome> WILLOW_SWAMP = BIOMES.register("willow_swamp",
            () -> makeWillowSwampBiome());


    public static Biome makeWillowSwampBiome() {
        MobSpawnInfo.Builder mobspawninfo$builder = new MobSpawnInfo.Builder();
        BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder());


        DefaultBiomeFeatures.farmAnimals(mobspawninfo$builder);
        DefaultBiomeFeatures.commonSpawns(mobspawninfo$builder);
        DefaultBiomeFeatures.caveSpawns(mobspawninfo$builder);
        mobspawninfo$builder.addSpawn(EntityClassification.MONSTER, new MobSpawnInfo.Spawners(EntityType.SLIME, 1, 1, 1));


        DefaultBiomeFeatures.addLushCavesVegetationFeatures(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addLushCavesSpecialOres(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addWaterTrees(biomegenerationsettings$builder);
        DefaultBiomeFeatures.addDefaultOres(biomegenerationsettings$builder);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, AquaticPlacements.SEAGRASS_SWAMP);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, MiscOverworldPlacements.DISK_CLAY);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_NORMAL);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_NORMAL);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_SUGAR_CANE_SWAMP);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_WATER);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MiscOverworldPlacements.SPRING_LAVA);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.FLOWER_SWAMP);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_GRASS_NORMAL);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_DEAD_BUSH);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.PATCH_WATERLILY);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.BROWN_MUSHROOM_SWAMP);
        biomegenerationsettings$builder.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, VegetationPlacements.RED_MUSHROOM_SWAMP);
//        biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModConfiguredFeatures.WILLOW);

        DefaultBiomeFeatures.addJungleVines(biomegenerationsettings$builder);
        return (new Biome.Builder()).precipitation(Biome.RainType.RAIN).biomeCategory(Biome.Category.SWAMP).temperature(0.8F).downfall(0.9F).specialEffects((new BiomeAmbience.Builder()).waterColor(6388580).waterFogColor(2302743).fogColor(12638463).skyColor(getSkyColorWithTemperatureModifier(0.8F)).foliageColorOverride(6975545).grassColorModifier(BiomeAmbience.GrassColorModifier.SWAMP).ambientMoodSound(MoodSoundAmbience.LEGACY_CAVE_SETTINGS).build()).mobSpawnSettings(mobspawninfo$builder.build()).generationSettings(biomegenerationsettings$builder.build()).build();
    }


    private static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }

    public static void register(IEventBus eventBus) {
        BIOMES.register(eventBus);
    }
}