package net.joefoxe.hexerei.particle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.*;
import net.minecraft.particles.BasicParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class FogParticle extends SpriteTexturedParticle {

    public FogParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z);
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.lifetime = 200;
        this.quadSize = 2;

    }

    @Override
    public void tick() {

//        this.oRoll = this.roll;
//        if(Math.abs(this.yd) > 0 && this.y != this.yo)
//            this.roll += 0.3f * rotationDir;
//        this.yd -= 0.005f * fallingSpeed;

        this.alpha = Math.min(1, (this.lifetime - this.age) / (float)this.lifetime);
        super.tick();
    }

    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite spriteSet;

        public Factory(IAnimatedSprite sprite) {
            this.spriteSet = sprite;
        }

        @Nullable
        @Override
        public Particle createParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            Random rand = new Random();
            float colorOffset = (rand.nextFloat() * 0.4f);
            FogParticle fogParticle = new FogParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            fogParticle.pickSprite(this.spriteSet);

//            fogParticle.sprite.u1
            fogParticle.setColor(0.6f + colorOffset,0.6f + colorOffset,0.6f + colorOffset);
//            if(this.spriteSet.get(0,1).getName().getPath().toString().matches("particle/fog_particle_4") ||
//                  this.spriteSet.get(0,1).getName().getPath().toString().matches("particle/fog_particle_5") ||
//                      this.spriteSet.get(0,1).getName().getPath().toString().matches("particle/fog_particle_6")) {
//                fogParticle.lifetime += fogParticle.lifetime * 3 + 30;
//            }

            return fogParticle;
        }
    }
}
