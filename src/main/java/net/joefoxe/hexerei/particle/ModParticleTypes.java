package net.joefoxe.hexerei.particle;

import net.joefoxe.hexerei.Hexerei;
import net.minecraft.particles.ParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticleTypes {
    public static ParticleType<CauldronParticleData> cauldronParticleType;

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Hexerei.MOD_ID);

    public static final RegistryObject<BasicParticleType> CAULDRON = PARTICLES.register("cauldron_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BLOOD = PARTICLES.register("blood_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BLOOD_BIT = PARTICLES.register("blood_bit_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM = PARTICLES.register("broom_particle_1", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM_2 = PARTICLES.register("broom_particle_2", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM_3 = PARTICLES.register("broom_particle_3", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM_4 = PARTICLES.register("broom_particle_4", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM_5 = PARTICLES.register("broom_particle_5", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> BROOM_6 = PARTICLES.register("broom_particle_6", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> FOG = PARTICLES.register("fog_particle", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DOWSING_ROD_1 = PARTICLES.register("dowsing_rod_1", () -> new BasicParticleType(true));
    public static final RegistryObject<BasicParticleType> DOWSING_ROD_2 = PARTICLES.register("dowsing_rod_2", () -> new BasicParticleType(true));

}