package net.joefoxe.hexerei.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.joefoxe.hexerei.Hexerei;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Random;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

@OnlyIn(Dist.CLIENT)
public class BloodParticle extends SpriteTexturedParticle {

    private final ResourceLocation TEXTURE = new ResourceLocation(Hexerei.MOD_ID,
            "textures/particle/cauldron_boil_particle.png");
    // thanks to understanding simibubi's code from the Create mod for rendering particles I was able to render my own :D
    public static final Vector3d[] CUBE = {
            // bottom render
            //right
            new Vector3d(0.5, -0.1, -0.5), // >^
            new Vector3d(0.5, -0.1, 0.5),  // >V
            new Vector3d(0.25, -0.1, 0.5), // <V
            new Vector3d(0.25, -0.1, -0.5),// <^
            //left
            new Vector3d(-0.25, -0.1, -0.5), // >^
            new Vector3d(-0.25, -0.1, 0.5),  // >V
            new Vector3d(-0.5, -0.1, 0.5), // <V
            new Vector3d(-0.5, -0.1, -0.5),// <^
            //front
            new Vector3d(0.25, -0.1, -0.5), // >^
            new Vector3d(0.25, -0.1, -0.25),  // >V
            new Vector3d(-0.25, -0.1, -0.25), // <V
            new Vector3d(-0.25, -0.1, -0.5),// <^
            //back
            new Vector3d(-0.25, -0.1, 0.5), // >^
            new Vector3d(-0.25, -0.1, 0.25),  // >V
            new Vector3d(0.25, -0.1, 0.25), // <V
            new Vector3d(0.25, -0.1, 0.5),// <^


            // top render
            new Vector3d(0.25, 0.1, -0.5), // >^
            new Vector3d(0.25, 0.1, 0.5),  // >V
            new Vector3d(0.5, 0.1, 0.5), // <V
            new Vector3d(0.5, 0.1, -0.5),// <^
            //left
            new Vector3d(-0.5, 0.1, -0.5), // >^
            new Vector3d(-0.5, 0.1, 0.5),  // >V
            new Vector3d(-0.25, 0.1, 0.5), // <V
            new Vector3d(-0.25, 0.1, -0.5),// <^
            //front
            new Vector3d(-0.25, 0.1, -0.5), // >^
            new Vector3d(-0.25, 0.1, -0.25),  // >V
            new Vector3d(0.25, 0.1, -0.25), // <V
            new Vector3d(0.25, 0.1, -0.5),// <^
            //back
            new Vector3d(0.25, 0.1, 0.5), // >^
            new Vector3d(0.25, 0.1, 0.25),  // >V
            new Vector3d(-0.25, 0.1, 0.25), // <V
            new Vector3d(-0.25, 0.1, 0.5),// <^


            // front render
            new Vector3d(-0.5, -0.1, -0.5),
            new Vector3d(-0.5, 0.1, -0.5),
            new Vector3d(0.5, 0.1, -0.5),
            new Vector3d(0.5, -0.1, -0.5),

            // back render
            new Vector3d(0.5, -0.1, 0.5),
            new Vector3d(0.5, 0.1, 0.5),
            new Vector3d(-0.5, 0.1, 0.5),
            new Vector3d(-0.5, -0.1, 0.5),

            // left render
            new Vector3d(0.5, -0.1, -0.5),
            new Vector3d(0.5, 0.1, -0.5),
            new Vector3d(0.5, 0.1, 0.5),
            new Vector3d(0.5, -0.1, 0.5),

            // right render
            new Vector3d(-0.5, -0.1, 0.5),
            new Vector3d(-0.5, 0.1, 0.5),
            new Vector3d(-0.5, 0.1, -0.5),
            new Vector3d(-0.5, -0.1, -0.5),


            // inside
            // front render
            new Vector3d(-0.25, -0.1, 0.25),
            new Vector3d(-0.25, 0.1, 0.25),
            new Vector3d(0.25, 0.1, 0.25),
            new Vector3d(0.25, -0.1, 0.25),

            // back render
            new Vector3d(0.25, -0.1, -0.25),
            new Vector3d(0.25, 0.1, -0.25),
            new Vector3d(-0.25, 0.1, -0.25),
            new Vector3d(-0.25, -0.1, -0.25),

            // left render
            new Vector3d(-0.25, -0.1, -0.25),
            new Vector3d(-0.25, 0.1, -0.25),
            new Vector3d(-0.25, 0.1, 0.25),
            new Vector3d(-0.25, -0.1, 0.25),

            // right render
            new Vector3d(0.25, -0.1, 0.25),
            new Vector3d(0.25, 0.1, 0.25),
            new Vector3d(0.25, 0.1, -0.25),
            new Vector3d(0.25, -0.1, -0.25),

            //middle top inside
            new Vector3d(0.25, -0.01, -0.25),
            new Vector3d(0.25, -0.01, 0.25),
            new Vector3d(-0.25, -0.01, 0.25),
            new Vector3d(-0.25, -0.01, -0.25),
            // middle bottom render
            new Vector3d(-0.25, 0.01, -0.25),
            new Vector3d(-0.25, 0.01, 0.25),
            new Vector3d(0.25, 0.01, 0.25),
            new Vector3d(0.25, 0.01, -0.25),


    };

    public static final Vector3d[] CUBE_NORMALS = {
            // modified normals for the sides
            new Vector3d(0, 0.1, 0),
            new Vector3d(0, 0.1, 0),
            new Vector3d(0, 0.1, 0),
            new Vector3d(0, 0.1, 0),
            new Vector3d(0, -0.5, 0),
            new Vector3d(0, -0.5, 0),
            new Vector3d(0, -0.5, 0),
            new Vector3d(0, -0.5, 0),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
            new Vector3d(0, 0, 0.5),
    };

    private static final IParticleRenderType renderType = new IParticleRenderType() {
        @Override
        public void begin(BufferBuilder bufferBuilder, TextureManager textureManager) {
            RenderSystem.disableTexture();

            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);

            bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormats.PARTICLE);
        }

        @Override
        public void end(Tessellator tesselator) {
            tesselator.end();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        }
    };

    protected float scale;
    protected float rotationDirection;
    protected float rotation;
    protected float rotationOffsetYaw;
    protected float rotationOffsetPitch;
    protected float rotationOffsetRoll;
    protected float colorOffset;


    public BloodParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z);
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.rotation = 0;

        averageAge(80);

        Random random = new Random();

        this.colorOffset = (random.nextFloat() * 0.25f);
        this.rotationOffsetYaw = random.nextFloat();
        this.rotationOffsetPitch = random.nextFloat();
        this.rotationOffsetRoll = random.nextFloat();

        setScale(0.2F);
        setRotationDirection(random.nextFloat() - 0.5f);
    }

    public void setScale(float scale) {
        this.scale = scale;
        this.setSize(scale * 0.5f, scale * 0.5f);
    }

    public void averageAge(int age) {
        Random random = new Random();
        this.lifetime = (int) (age + (random.nextDouble() * 2D - 1D) * 8);
    }

    public void setRotationDirection(float rotationDirection) {
        this.rotationDirection = rotationDirection;
    }


    @Override
    public void tick() {

        this.rotation = (this.rotationDirection * 0.1f) + this.rotation;

        super.tick();
    }

//    @Override
//    public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
//
//
//        Vec3 projectedView = renderInfo.getPosition();
//        float lerpedX = (float) (Mth.lerp(partialTicks, this.xo, this.x) - projectedView.x());
//        float lerpedY = (float) (Mth.lerp(partialTicks, this.yo, this.y) - projectedView.y());
//        float lerpedZ = (float) (Mth.lerp(partialTicks, this.zo, this.z) - projectedView.z());
//
//        int light = 15728880;// 15<<20 && 15<<4
//        double ageMultiplier = 1 - Math.pow(age, 3) / Math.pow(this.lifetime, 3);
//
//        for (int i = 0; i < CUBE.length / 4; i++) {
//            // 10 faces to a blood particle
//            for (int j = 0; j < 4; j++) {
//                Vec3 vec = CUBE[i * 4 + j];
//                vec = vec
//                        .yRot(this.rotation + this.rotationOffsetYaw)
//                        .xRot(this.rotation + this.rotationOffsetPitch)
//                        .zRot(this.rotation + this.rotationOffsetRoll)
//                        .scale(scale * ageMultiplier)
//                        //.mul(1, 0.25 + 0.55 * (age/4f), 1) //scale non uniform based off age (maybe)
//                        .add(lerpedX, lerpedY, lerpedZ);
//
//                Vec3 normal = CUBE_NORMALS[i];
//                buffer.vertex((float) vec.x, (float) vec.y, (float) vec.z, this.rCol + this.colorOffset, this.gCol, this.bCol, this.alpha, 0, 0, 0, light,(float) normal.x, (float) normal.y, (float) normal.z);
//
//
//            }
//        }
//
//    }

    @Override
    public void render(IVertexBuilder builder, ActiveRenderInfo renderInfo, float p_225606_3_) {
        Vector3d projectedView = renderInfo.getPosition();
        float lerpX = (float) (MathHelper.lerp(p_225606_3_, this.xo, this.x) - projectedView.x());
        float lerpY = (float) (MathHelper.lerp(p_225606_3_, this.yo, this.y) - projectedView.y());
        float lerpZ = (float) (MathHelper.lerp(p_225606_3_, this.zo, this.z) - projectedView.z());

        int light = 15728880;
        double ageMultiplier = 1 - Math.pow(MathHelper.clamp(age + p_225606_3_, 0, lifetime), 3) / Math.pow(lifetime, 3);

        RenderSystem._setShaderTexture(0, TEXTURE);

        for (int i = 0; i < CUBE.length / 4; i++) {
            for (int j = 0; j < 4; j++) {
                Vector3d vec = CUBE[i * 4 + j];
                vec = vec
                        .yRot(this.rotation + this.rotationOffsetYaw)
                        .xRot(this.rotation + this.rotationOffsetPitch)
                        .zRot(this.rotation + this.rotationOffsetRoll)
                        .scale(scale * ageMultiplier)
                        .add(lerpX, lerpY, lerpZ);

                Vector3d normal = CUBE_NORMALS[i];

                if(i == 0 || i == 1 || i == 2 || i == 3) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(MathHelper.clamp(rCol * 1.25f, 0, 1.0f), MathHelper.clamp(gCol * 1.25f, 0, 1.0f), MathHelper.clamp(bCol * 1.25f, 0, 1.0f), alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 4 || i == 5 || i == 6 || i == 7) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.55f, gCol * 0.55f, bCol * 0.55f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 8) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.75f, gCol * 0.95f, bCol * 0.95f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 9) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.85f, gCol * 0.75f, bCol * 0.75f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 10) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.95f, gCol * 0.9f, bCol * 0.9f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 11) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 1.05f, gCol * 1.05f, bCol * 1.05f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 12 || i == 13 || i == 14 || i == 15) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.4f, gCol * 0.4f, bCol * 0.4f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 17 || i == 16) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.4f, gCol * 0.4f, bCol * 0.4f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.85f, gCol * 0.85f, bCol * 0.85f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }
            }
        }
    }



    @Override
    public IParticleRenderType getRenderType() {
        return renderType;
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
            BloodParticle cauldronParticle = new BloodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            Random random = new Random();

            float colorOffset = (random.nextFloat() * 0.20f);
            cauldronParticle.setColor(0.05f + colorOffset, 0.05f, 0.05f);

            cauldronParticle.setAlpha(1.0f);


            cauldronParticle.pickSprite(this.spriteSet);
            return cauldronParticle;

        }
    }


}
