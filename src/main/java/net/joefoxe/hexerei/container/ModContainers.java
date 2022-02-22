package net.joefoxe.hexerei.container;

import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModContainers {

    public static DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, Hexerei.MOD_ID);

    public static final RegistryObject<ContainerType<MixingCauldronContainer>> MIXING_CAULDRON_CONTAINER
            = CONTAINERS.register("mixing_cauldron_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new MixingCauldronContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<CofferContainer>> COFFER_CONTAINER
            = CONTAINERS.register("coffer_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new CofferContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<HerbJarContainer>> HERB_JAR_CONTAINER
            = CONTAINERS.register("herb_jar_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new HerbJarContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<DipperContainer>> DIPPER_CONTAINER
            = CONTAINERS.register("dipper_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new DipperContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<DipperContainer>> DRYING_RACK_CONTAINER
            = CONTAINERS.register("drying_rack_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new DipperContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<PestleAndMortarContainer>> PESTLE_AND_MORTAR_CONTAINER
            = CONTAINERS.register("pestle_and_mortar_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {
                BlockPos pos = data.readBlockPos();
                World world = inv.player.getCommandSenderWorld();
                return new PestleAndMortarContainer(windowId, world, pos, inv, inv.player);
            })));

    public static final RegistryObject<ContainerType<BroomContainer>> BROOM_CONTAINER
            = CONTAINERS.register("broom_container",
            () -> IForgeContainerType.create(((windowId, inv, data) -> {

                World world = inv.player.getCommandSenderWorld();//new BroomEntity(world, pos.getX(), pos.getY(), pos.getZ())
                int id = data.readInt();
                if(world.getEntity(id) != null)
                    return new BroomContainer(windowId,(BroomEntity)world.getEntity(id), inv, inv.player);
                else
                    return new BroomContainer(windowId,new BroomEntity(world, 0, 0, 0), inv, inv.player);

            })));

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
