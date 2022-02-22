package net.joefoxe.hexerei.world.processor;


import com.mojang.serialization.Codec;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import t;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.block.material.Material;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

/**
 * Dynamically generates support legs below small dungeons.
 * Yellow stained glass is used to mark the corner positions where the legs will spawn for simplicity.
 */
@t
public class WitchHutLegProcessor extends StructureProcessor {
    public static final WitchHutLegProcessor INSTANCE = new WitchHutLegProcessor();
    public static final Codec<WitchHutLegProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @ParametersAreNonnullByDefault
    @Override
    public Template.BlockInfo process(IWorldReader worldReader, BlockPos jigsawPiecePos, BlockPos jigsawPieceBottomCenterPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoGlobal, PlacementSettings structurePlacementData, @Nullable Template template) {
        if (blockInfoGlobal.state.getBlock() == Blocks.WHITE_STAINED_GLASS_PANE) {
            ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
            IChunk currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
            Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);

            // Always replace the glass itself with mossy cobble
            currentChunk.setBlockState(blockInfoGlobal.pos, Blocks.SPRUCE_LOG.defaultBlockState(), false);
            blockInfoGlobal = new Template.BlockInfo(blockInfoGlobal.pos, Blocks.SPRUCE_LOG.defaultBlockState(), blockInfoGlobal.nbt);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfoGlobal.pos.below().mutable();
            BlockState currBlock = worldReader.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                currentChunk.setBlockState(mutable, Blocks.SPRUCE_LOG.defaultBlockState(), false);
                mutable.move(Direction.DOWN);
                currBlock = worldReader.getBlockState(mutable);
            }
        } else if (blockInfoGlobal.state.getBlock() == Blocks.RED_STAINED_GLASS_PANE) {
            ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
            IChunk currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
            Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);

            // Always replace the glass itself with mossy cobble
            currentChunk.setBlockState(blockInfoGlobal.pos, ModBlocks.MAHOGANY_LOG.get().defaultBlockState(), false);
            blockInfoGlobal = new Template.BlockInfo(blockInfoGlobal.pos, ModBlocks.MAHOGANY_LOG.get().defaultBlockState(), blockInfoGlobal.nbt);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfoGlobal.pos.below().mutable();
            BlockState currBlock = worldReader.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                currentChunk.setBlockState(mutable, ModBlocks.MAHOGANY_LOG.get().defaultBlockState(), false);
                mutable.move(Direction.DOWN);
                currBlock = worldReader.getBlockState(mutable);
            }
        } else if (blockInfoGlobal.state.getBlock() == Blocks.YELLOW_STAINED_GLASS_PANE) {
            ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
            IChunk currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
            Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);

            // Always replace the glass itself with mossy cobble
            currentChunk.setBlockState(blockInfoGlobal.pos, Blocks.AIR.defaultBlockState(), false);
            blockInfoGlobal = new Template.BlockInfo(blockInfoGlobal.pos, Blocks.AIR.defaultBlockState(), blockInfoGlobal.nbt);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfoGlobal.pos.below().mutable();
            BlockState currBlock = worldReader.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                currentChunk.setBlockState(mutable, Blocks.OAK_LOG.defaultBlockState(), false);
                mutable.move(Direction.DOWN);
                currBlock = worldReader.getBlockState(mutable);
            }
        } else if (blockInfoGlobal.state.getBlock() == Blocks.PURPLE_STAINED_GLASS_PANE) {
            ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
            IChunk currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
            Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);

            // Always replace the glass itself with mossy cobble
            currentChunk.setBlockState(blockInfoGlobal.pos, ModBlocks.WILLOW_LOG.get().defaultBlockState(), false);
            blockInfoGlobal = new Template.BlockInfo(blockInfoGlobal.pos, ModBlocks.WILLOW_LOG.get().defaultBlockState(), blockInfoGlobal.nbt);

            // Generate vertical pillar down
            BlockPos.Mutable mutable = blockInfoGlobal.pos.below().mutable();
            BlockState currBlock = worldReader.getBlockState(mutable);
            while (mutable.getY() > 0 && (currBlock.getMaterial() == Material.AIR || currBlock.getMaterial() == Material.WATER || currBlock.getMaterial() == Material.LAVA)) {
                currentChunk.setBlockState(mutable, ModBlocks.WILLOW_LOG.get().defaultBlockState(), false);
                mutable.move(Direction.DOWN);
                currBlock = worldReader.getBlockState(mutable);
            }
        }

        return blockInfoGlobal;
    }

    protected IStructureProcessorType<?> getType() {
        return Hexerei.WITCH_HUT_LEG_PROCESSOR;
    }
}

