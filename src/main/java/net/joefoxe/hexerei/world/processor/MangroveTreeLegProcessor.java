package net.joefoxe.hexerei.world.processor;


import com.mojang.serialization.Codec;
import net.joefoxe.hexerei.Hexerei;
import t;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
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
public class MangroveTreeLegProcessor extends StructureProcessor {
    public static final MangroveTreeLegProcessor INSTANCE = new MangroveTreeLegProcessor();
    public static final Codec<MangroveTreeLegProcessor> CODEC = Codec.unit(() -> INSTANCE);

    @ParametersAreNonnullByDefault
    @Override
    public Template.BlockInfo process(IWorldReader worldReader, BlockPos jigsawPiecePos, BlockPos jigsawPieceBottomCenterPos, Template.BlockInfo blockInfoLocal, Template.BlockInfo blockInfoGlobal, PlacementSettings structurePlacementData, @Nullable Template template) {
        ChunkPos currentChunkPos = new ChunkPos(blockInfoGlobal.pos);
        IChunk currentChunk = worldReader.getChunk(currentChunkPos.x, currentChunkPos.z);
        Random random = structurePlacementData.getRandom(blockInfoGlobal.pos);

        if(!(currentChunk.getBlockState(blockInfoGlobal.pos).getBlock() instanceof LeavesBlock || currentChunk.getBlockState(blockInfoGlobal.pos).getBlock() == Blocks.AIR))
        {
            return null;
        }

        if (blockInfoGlobal.state.getBlock() == Blocks.YELLOW_STAINED_GLASS_PANE) {

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
        }

        return blockInfoGlobal;
    }

    protected IStructureProcessorType<?> getType() {
        return Hexerei.MANGROVE_TREE_LEG_PROCESSOR;
    }
}

