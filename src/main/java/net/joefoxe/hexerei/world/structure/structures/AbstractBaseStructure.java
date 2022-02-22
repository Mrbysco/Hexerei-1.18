package net.joefoxe.hexerei.world.structure.structures;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class AbstractBaseStructure<C extends IFeatureConfig> extends Structure<C> {

    public AbstractBaseStructure(Codec<C> codec, Predicate<PieceGeneratorSupplier.Context<C>> locationCheckPredicate, Function<PieceGeneratorSupplier.Context<C>, Optional<PieceGenerator<C>>> pieceCreationPredicate) {
        this(codec, locationCheckPredicate, pieceCreationPredicate, PostPlacementProcessor.NONE);
    }

    public AbstractBaseStructure(Codec<C> codec, Predicate<PieceGeneratorSupplier.Context<C>> locationCheckPredicate, Function<PieceGeneratorSupplier.Context<C>, Optional<PieceGenerator<C>>> pieceCreationPredicate, PostPlacementProcessor postPlacementProcessor) {
        super(codec, (context) -> {
                    if (!locationCheckPredicate.test(context)) {
                        return Optional.empty();
                    }
                    else {
                        return pieceCreationPredicate.apply(context);
                    }
                },
                postPlacementProcessor);
    }
}