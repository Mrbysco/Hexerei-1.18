package net.joefoxe.hexerei.tileentity;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModTileEntities {

    public static DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Hexerei.MOD_ID);


    public static final RegistryObject<TileEntityType<MixingCauldronTile>> MIXING_CAULDRON_TILE = TILE_ENTITIES.register(
            "mixing_cauldron_entity", () -> TileEntityType.Builder.of(MixingCauldronTile::new, ModBlocks.MIXING_CAULDRON.get()).build(null));

    public static final RegistryObject<TileEntityType<CofferTile>> COFFER_TILE = TILE_ENTITIES.register(
            "coffer_entity", () -> TileEntityType.Builder.of(CofferTile::new, ModBlocks.COFFER.get(), ModBlocks.COFFER_BLACK.get(), ModBlocks.COFFER_BLUE.get(), ModBlocks.COFFER_CYAN.get(), ModBlocks.COFFER_GRAY.get(), ModBlocks.COFFER_GREEN.get(), ModBlocks.COFFER_LIGHT_BLUE.get(), ModBlocks.COFFER_LIGHT_GRAY.get(), ModBlocks.COFFER_LIME.get(), ModBlocks.COFFER_MAGENTA.get(), ModBlocks.COFFER_ORANGE.get(), ModBlocks.COFFER_PINK.get(), ModBlocks.COFFER_PURPLE.get(), ModBlocks.COFFER_RED.get(), ModBlocks.COFFER_WHITE.get(), ModBlocks.COFFER_YELLOW.get()).build(null));


    public static final RegistryObject<TileEntityType<HerbJarTile>> HERB_JAR_TILE = TILE_ENTITIES.register(
            "herb_jar_entity", () -> TileEntityType.Builder.of(HerbJarTile::new, ModBlocks.HERB_JAR.get()).build(null));

    public static final RegistryObject<TileEntityType<CrystalBallTile>> CRYSTAL_BALL_TILE = TILE_ENTITIES.register(
            "crystal_ball_entity", () -> TileEntityType.Builder.of(CrystalBallTile::new, ModBlocks.CRYSTAL_BALL.get()).build(null));

    public static final RegistryObject<TileEntityType<BookOfShadowsAltarTile>> BOOK_OF_SHADOWS_ALTAR_TILE = TILE_ENTITIES.register(
            "book_of_shadows_altar_entity", () -> TileEntityType.Builder.of(BookOfShadowsAltarTile::new, ModBlocks.BOOK_OF_SHADOWS_ALTAR.get()).build(null));

    public static final RegistryObject<TileEntityType<CandleTile>> CANDLE_TILE = TILE_ENTITIES.register(
            "candle_entity", () -> TileEntityType.Builder.of(CandleTile::new, ModBlocks.CANDLE.get(), ModBlocks.CANDLE_BLUE.get(), ModBlocks.CANDLE_BLACK.get(), ModBlocks.CANDLE_LIME.get(), ModBlocks.CANDLE_ORANGE.get(), ModBlocks.CANDLE_PINK.get(), ModBlocks.CANDLE_PURPLE.get(), ModBlocks.CANDLE_RED.get(), ModBlocks.CANDLE_CYAN.get(), ModBlocks.CANDLE_YELLOW.get()).build(null));

    public static final RegistryObject<TileEntityType<CandleDipperTile>> CANDLE_DIPPER_TILE = TILE_ENTITIES.register(
            "candle_dipper_entity", () -> TileEntityType.Builder.of(CandleDipperTile::new, ModBlocks.CANDLE_DIPPER.get()).build(null));

    public static final RegistryObject<TileEntityType<DryingRackTile>> DRYING_RACK_TILE = TILE_ENTITIES.register(
            "drying_rack_entity", () -> TileEntityType.Builder.of(DryingRackTile::new, ModBlocks.HERB_DRYING_RACK.get()).build(null));

    public static final RegistryObject<TileEntityType<PestleAndMortarTile>> PESTLE_AND_MORTAR_TILE = TILE_ENTITIES.register(
            "pestle_and_mortar_entity", () -> TileEntityType.Builder.of(PestleAndMortarTile::new, ModBlocks.PESTLE_AND_MORTAR.get()).build(null));

    public static final RegistryObject<TileEntityType<SageBurningPlateTile>> SAGE_BURNING_PLATE_TILE = TILE_ENTITIES.register(
            "sage_burning_plate_entity", () -> TileEntityType.Builder.of(SageBurningPlateTile::new, ModBlocks.SAGE_BURNING_PLATE.get()).build(null));


    public static void register(IEventBus eventBus) {
        TILE_ENTITIES.register(eventBus);
    }
}
