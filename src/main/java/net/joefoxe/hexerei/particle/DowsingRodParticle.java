package net.joefoxe.hexerei.particle;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.tileentity.MixingCauldronTile;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.core.BlockPos;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Random;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

@OnlyIn(Dist.CLIENT)
public class DowsingRodParticle extends SpriteTexturedParticle {

    private final ResourceLocation TEXTURE = new ResourceLocation(Hexerei.MOD_ID,
            "textures/particle/cauldron_boil_particle.png");

    // thanks to understanding simibubi's code from the Create mod for rendering particles I was able to render my own :D
    public static final Vector3d[] CUBE = {
            // top render
            new Vector3d(0.5, 0.5, -0.5),
            new Vector3d(0.5, 0.5, 0.5),
            new Vector3d(-0.5, 0.5, 0.5),
            new Vector3d(-0.5, 0.5, -0.5),

            // bottom render
            new Vector3d(-0.5, -0.5, -0.5),
            new Vector3d(-0.5, -0.5, 0.5),
            new Vector3d(0.5, -0.5, 0.5),
            new Vector3d(0.5, -0.5, -0.5),

            // front render
            new Vector3d(-0.5, -0.5, 0.5),
            new Vector3d(-0.5, 0.5, 0.5),
            new Vector3d(0.5, 0.5, 0.5),
            new Vector3d(0.5, -0.5, 0.5),

            // back render
            new Vector3d(0.5, -0.5, -0.5),
            new Vector3d(0.5, 0.5, -0.5),
            new Vector3d(-0.5, 0.5, -0.5),
            new Vector3d(-0.5, -0.5, -0.5),

            // left render
            new Vector3d(-0.5, -0.5, -0.5),
            new Vector3d(-0.5, 0.5, -0.5),
            new Vector3d(-0.5, 0.5, 0.5),
            new Vector3d(-0.5, -0.5, 0.5),

            // right render
            new Vector3d(0.5, -0.5, 0.5),
            new Vector3d(0.5, 0.5, 0.5),
            new Vector3d(0.5, 0.5, -0.5),
            new Vector3d(0.5, -0.5, -0.5)
    };

    public static final Vector3d[] CUBE_NORMALS = {
            // modified normals for the sides
            new Vector3d(0, 0.5, 0),
            new Vector3d(0, -0.5, 0),
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

    public DowsingRodParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
        super(world, x, y, z);
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.rotation = 0;
        Random random = new Random();
        setScale(0.2F);
        setRotationDirection(random.nextFloat() - 0.5f);
    }

    public void setScale(float scale) {
        this.scale = scale;
        this.setSize(scale * 0.5f, scale * 0.5f);
    }

    public void setRotationDirection(float rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    @Override
    public void tick() {
        this.rotation = (this.rotationDirection * 0.1f) + this.rotation;
        super.tick();
    }

    @Override
    public void render(IVertexBuilder builder, ActiveRenderInfo renderInfo, float p_225606_3_) {
        Vector3d projectedView = renderInfo.getPosition();
        float lerpX = (float) (MathHelper.lerp(p_225606_3_, this.xo, this.x) - projectedView.x());
        float lerpY = (float) (MathHelper.lerp(p_225606_3_, this.yo, this.y) - projectedView.y());
        float lerpZ = (float) (MathHelper.lerp(p_225606_3_, this.zo, this.z) - projectedView.z());

        int light = 15728880;
        double ageMultiplier = 1 - (Math.pow(MathHelper.clamp(age + p_225606_3_, 0, lifetime), 3) / Math.pow(lifetime, 3))/2f;

        RenderSystem._setShaderTexture(0, TEXTURE);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                Vector3d vec = CUBE[i * 4 + j];
                vec = vec
                        .yRot(this.rotation)
                        .scale(scale * ageMultiplier)
                        .add(lerpX, lerpY, lerpZ);

                Vector3d normal = CUBE_NORMALS[i];

                if(i == 0) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(MathHelper.clamp(rCol * 1.25f, 0, 1.0f), MathHelper.clamp(gCol * 1.25f, 0, 1.0f), MathHelper.clamp(bCol * 1.25f, 0, 1.0f), alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 1) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.55f, gCol * 0.55f, bCol * 0.55f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 2) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.95f, gCol * 0.95f, bCol * 0.95f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 3) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.75f, gCol * 0.75f, bCol * 0.75f, alpha)
                            .normal((float) normal.x, (float) normal.y, (float) normal.z)
                            .uv2(light)
                            .endVertex();
                }else if(i == 4) {
                    builder.vertex(vec.x, vec.y, vec.z)
                            .uv(0, 0)
                            .color(rCol * 0.9f, gCol * 0.9f, bCol * 0.9f, alpha)
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
            DowsingRodParticle cauldronParticle = new DowsingRodParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            Random random = new Random();

            float colorOffset = (random.nextFloat() * 0.25f);

            cauldronParticle.setColor(colorOffset + 0.30f,colorOffset + 0.55f,colorOffset + 0.30f);

            cauldronParticle.setAlpha(1.0f);
            cauldronParticle.setScale(0.5f);
            cauldronParticle.setLifetime(1);


            cauldronParticle.pickSprite(this.spriteSet);
            return cauldronParticle;
        }
    }


}
