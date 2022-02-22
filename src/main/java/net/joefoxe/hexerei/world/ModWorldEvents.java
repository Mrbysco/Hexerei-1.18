package net.joefoxe.hexerei.world;

import com.mojang.serialization.Codec;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.world.gen.ModFlowerGeneration;
import net.joefoxe.hexerei.world.gen.ModPlacements;
import net.joefoxe.hexerei.world.gen.ModStructureGeneration;
import net.joefoxe.hexerei.world.gen.ModTreeGeneration;
import net.joefoxe.hexerei.world.structure.ModStructures;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import org.apache.logging.log4j.LogManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Hexerei.MOD_ID)
public class ModWorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
//        ModOreGeneration.generateOres(event);
//        ModStructureGeneration.generateStructures(event);
//        ModTreeGeneration.generateTrees(event);
//        ModFlowerGeneration.generateFlowers(event);

        if (event.getCategory() == Biome.Category.SWAMP) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModPlacements.WILLOW_TREE);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModPlacements.FLOWERING_LILYPAD);
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModPlacements.SWAMP_FLOWERS);
            event.getGeneration().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ModPlacements.SELENITE_GEODE);
        }
        if (event.getCategory() == Biome.Category.JUNGLE) {
            event.getGeneration().addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModPlacements.MAHOGANY_TREE);
            event.getGeneration().addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, ModPlacements.SELENITE_GEODE);
        }

    }


    @SubscribeEvent
    public static void addDimensionalSpacing(final WorldEvent.Load event) {
        if(event.getWorld() instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) event.getWorld();

            try {
                Method GETCODEC_METHOD =
                        ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "func_230347_a_");
                ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(
                        (Codec<? extends ChunkGenerator>)GETCODEC_METHOD.invoke(serverWorld.getChunkSource().getGenerator()));

                if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                    return;
                }
            } catch (Exception e) {
                LogManager.getLogger().error("Was unable to check if " + serverWorld.dimension().location()
                        + " is using Terraforged's ChunkGenerator.");
            }

            // Prevent spawning our structure in Vanilla's superflat world
            if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator &&
                    serverWorld.dimension().equals(World.OVERWORLD)) {
                return;
            }

            // Adding our StructureFeature to the Map
//            Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap =
//                    new HashMap<>(serverWorld.getChunkSource().getGenerator().getSettings().structureConfig());
//            tempMap.putIfAbsent(ModStructures.WITCH_HUT.get(),
//                    StructureSettings.DEFAULTS.get(ModStructures.WITCH_HUT.get()));
//            serverWorld.getChunkSource().getGenerator().getSettings().structureConfig = tempMap;
        }
    }
}