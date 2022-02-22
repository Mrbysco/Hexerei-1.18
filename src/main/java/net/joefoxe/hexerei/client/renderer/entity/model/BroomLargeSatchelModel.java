package net.joefoxe.hexerei.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)

public class BroomLargeSatchelModel extends SegmentedModel<BroomEntity> {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Hexerei.MOD_ID, "broom_large_satchel"), "main");
    private final ModelRenderer satchel;


    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Satchel = partdefinition.addOrReplaceChild("Satchel", CubeListBuilder.create().texOffs(0, 14).addBox(5.5F, -3.5F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-1.5F, -3.5F, -1.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(3.0F, -1.5F, 1.0F, 7.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 18).addBox(3.0F, -1.75F, 1.25F, 7.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(3.0F, -1.5F, -3.0F, 7.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(14, 25).addBox(3.0F, -1.75F, -3.25F, 7.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(47, 27).addBox(-3.0F, -1.75F, -3.25F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(33, 2).addBox(-3.0F, -1.5F, -3.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(50, 19).addBox(-3.0F, -1.75F, 1.25F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(33, 7).addBox(-3.0F, -1.5F, 1.0F, 5.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    public BroomLargeSatchelModel(ModelRenderer root) {
        this.satchel = root.getChild("Satchel");
    }


    @Override
    public void renderToBuffer(MatrixStack poseStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        satchel.render(poseStack, buffer, packedLight, packedOverlay);
    }

    @Override
    public Iterable<ModelRenderer> parts() {
        return ImmutableList.of(satchel);
    }

    @Override
    public void setupAnim(BroomEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }
}