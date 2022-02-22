package net.joefoxe.hexerei.client.renderer.entity.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraftforge.client.IItemRenderProperties;

public class ArmorModel <T extends LivingEntity> extends BipedModel<T> implements IItemRenderProperties {

    public EquipmentSlotType slot;
    ModelRenderer root, modelHead, modelBody, modelLeft_arm, modelRight_arm, modelBelt, modelLeft_leg, modelRight_leg, modelLeft_foot, modelRight_foot;

    public ArmorModel(ModelRenderer root) {
        super(root);
        this.root = root;
        this.modelBelt = root.getChild("Belt");
        this.modelBody = root.getChild("Body");
        this.modelRight_foot = root.getChild("RightBoot");
        this.modelLeft_foot = root.getChild("LeftBoot");
        this.modelLeft_arm = root.getChild("LeftArm");
        this.modelRight_arm = root.getChild("RightArm");
        this.modelRight_leg = root.getChild("LeftLeg");
        this.modelLeft_leg = root.getChild("RightLeg");
        this.modelHead = root.getChild("Head");

    }

    public static PartDefinition createHumanoidAlias(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        root.addOrReplaceChild("Body", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("Belt", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("Head", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("LeftLeg", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("LeftBoot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("RightLeg", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("RightBoot", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("LeftArm", new CubeListBuilder(), PartPose.ZERO);
        root.addOrReplaceChild("RightArm", new CubeListBuilder(), PartPose.ZERO);

        return root;
    }

    @Override
    protected Iterable<ModelRenderer> headParts() {
        return slot == EquipmentSlotType.HEAD ? ImmutableList.of(modelHead) : ImmutableList.of();
    }

    @Override
    protected Iterable<ModelRenderer> bodyParts() {
        if (slot == EquipmentSlotType.CHEST) {
            return ImmutableList.<ModelRenderer>of(modelBody, modelLeft_arm, modelRight_arm);
        }
        else if (slot == EquipmentSlotType.LEGS) {
            return ImmutableList.<ModelRenderer>of(modelLeft_leg, modelRight_leg, modelBelt);
        }
        else if (slot == EquipmentSlotType.FEET) {
            return ImmutableList.<ModelRenderer>of(modelLeft_foot, modelRight_foot);
        }
        else return ImmutableList.of();
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
//        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        matrixStack.pushPose();
        if (this.slot == EquipmentSlotType.HEAD) {
            this.modelHead.copyFrom(this.head);
            this.modelHead.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        else if (this.slot == EquipmentSlotType.CHEST) {
            this.modelBody.copyFrom(this.body);
            this.modelBody.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.modelRight_arm.copyFrom(this.rightArm);
            this.modelRight_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.modelLeft_arm.copyFrom(this.leftArm);
            this.modelLeft_arm.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        } else if (this.slot == EquipmentSlotType.LEGS) {
            this.modelBelt.copyFrom(this.body);
            this.modelBelt.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.modelRight_leg.copyFrom(this.rightLeg);
            this.modelRight_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.modelLeft_leg.copyFrom(this.leftLeg);
            this.modelLeft_leg.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

        } else if (this.slot == EquipmentSlotType.FEET) {
            this.modelRight_foot.copyFrom(this.rightLeg);
            this.modelRight_foot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
            this.modelLeft_foot.copyFrom(this.leftLeg);
            this.modelLeft_foot.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
        }
        matrixStack.popPose();
    }

    public void copyFromDefault(BipedModel model) {
        modelBody.copyFrom(model.body);
        modelBelt.copyFrom(model.body);
        modelHead.copyFrom(model.head);
        modelLeft_arm.copyFrom(model.leftArm);
        modelRight_arm.copyFrom(model.rightArm);
        modelLeft_leg.copyFrom(leftLeg);
        modelRight_leg.copyFrom(rightLeg);
        modelLeft_foot.copyFrom(leftLeg);
        modelRight_foot.copyFrom(rightLeg);
    }

}