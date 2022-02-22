package net.joefoxe.hexerei.tileentity.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.joefoxe.hexerei.Hexerei;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.block.custom.Coffer;
import net.joefoxe.hexerei.tileentity.CofferTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.model.data.EmptyModelData;

public class CofferRenderer implements TileEntityRenderer<CofferTile> {

    @Override
    public void render(CofferTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                       IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {

        if(!tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).hasBlockEntity())
            return;

        matrixStackIn.pushPose();
        if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.NORTH) {
            matrixStackIn.translate(8D / 16D, 4D / 16D, 4D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        } else if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.SOUTH) {
            matrixStackIn.translate(8D / 16D, 4D / 16D, 12D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(0));
        } else if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.EAST) {
            matrixStackIn.translate(12D / 16D, 4D / 16D, 8D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        } else if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.WEST) {
            matrixStackIn.translate(4D / 16D, 4D / 16D, 8D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        }
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE)));
        if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_black"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_BLACK.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_blue"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_BLUE.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_cyan"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_CYAN.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_gray"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_GRAY.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_green"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_GREEN.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_light_blue"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_LIGHT_BLUE.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_light_gray"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_LIGHT_GRAY.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_lime"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_LIME.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_magenta"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_MAGENTA.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_orange"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_ORANGE.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_pink"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_PINK.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_purple"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_PURPLE.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_red"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_RED.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_white"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_WHITE.get().defaultBlockState());
        else if(tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getBlock().getRegistryName().toString().equals(Hexerei.MOD_ID + ":coffer_yellow"))
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID_YELLOW.get().defaultBlockState());
        else
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_LID.get().defaultBlockState());
        matrixStackIn.popPose();

        float sideRotation = (((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135);

        if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.NORTH || tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.SOUTH) {

            matrixStackIn.pushPose();
            matrixStackIn.translate(11.7299D / 16D, 2.4772D / 16D, 5.475D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(11.7299D / 16D, 2.4772D / 16D, 10.525D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(4.2701/ 16D, 2.4772D / 16D, 5.475D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(4.2701 / 16D, 2.4772D / 16D, 10.525D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.translate(1D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 1.75D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 5D / 16D);
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_CONTAINER.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(11D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 1.75D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 5D / 16D);
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_CONTAINER.get().defaultBlockState());
            matrixStackIn.popPose();
        }

        if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.EAST || tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.WEST) {

            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(11.7299D / 16D, 2.4772D / 16D, 5.475D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(11.7299D / 16D, 2.4772D / 16D, 10.525D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(-((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(4.2701/ 16D, 2.4772D / 16D, 5.475D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(4.2701 / 16D, 2.4772D / 16D, 10.525D / 16D);
            matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135));
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_HINGE.get().defaultBlockState());
            matrixStackIn.popPose();

            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(1D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 1.75D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 5D / 16D);
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_CONTAINER.get().defaultBlockState());
            matrixStackIn.popPose();
            matrixStackIn.pushPose();
            matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
            matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
            matrixStackIn.translate(11D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 1.75D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 5D / 16D);
            renderBlock(matrixStackIn, bufferIn, combinedLightIn, ModBlocks.COFFER_CONTAINER.get().defaultBlockState());
            matrixStackIn.popPose();
        }

        //render items only if its at least slightly opened
        if((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) > 2)
        {
            if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.NORTH)
                renderItemsNorth(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
            if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.WEST)
                renderItemsWest(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
            if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.SOUTH)
                renderItemsSouth(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
            if (tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(HorizontalBlock.FACING) == Direction.EAST)
                renderItemsEast(tileEntityIn, partialTicks, matrixStackIn, bufferIn, combinedLightIn);

        }


    }

    private void renderItemsNorth(CofferTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                                  IRenderTypeBuffer bufferIn, int combinedLightIn)
    {
        float sideRotation = (((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135);

        // item row 1
        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(35), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(34), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(33), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(32), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(31), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(30), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(29), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(28), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(27), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 2

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(26), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(25), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(24), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(23), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(22), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(21), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 3

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(20), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(19), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(18), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(17), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(16), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(15), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 4

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(14), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(13), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(12), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(11), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(10), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(9), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 5
        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(8), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(7), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(6), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(5), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(4), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(3), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(2), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(1), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();

        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(0), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

    }

    private void renderItemsSouth(CofferTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                                 IRenderTypeBuffer bufferIn, int combinedLightIn)
    {
        float sideRotation = (((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135);

        // item row 1
        matrixStackIn.pushPose();
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(35), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(34), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(33), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(32), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(31), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(30), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(29), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(28), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(27), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 2

        matrixStackIn.pushPose();
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(26), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(25), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(24), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(23), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(22), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(21), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 3

        matrixStackIn.pushPose();
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(20), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(19), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(18), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(17), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(16), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(15), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 4

        matrixStackIn.pushPose();
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(14), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(13), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(12), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(11), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(10), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(9), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 5
        matrixStackIn.pushPose();
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(8), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(7), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(6), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(5), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(4), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(3), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();


        matrixStackIn.pushPose();
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(2), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(1), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(0), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

    }

    private void renderItemsWest(CofferTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                                 IRenderTypeBuffer bufferIn, int combinedLightIn)
    {
        float sideRotation = (((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135);

        // item row 1
        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(35), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(34), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(33), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(32), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(31), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(30), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(29), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(28), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(27), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 2

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(26), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(25), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(24), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(23), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(22), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(21), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 3

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(20), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(19), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(18), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(17), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(16), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(15), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 4

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(14), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(13), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(12), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(11), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(10), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(9), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 5
        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(8), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(7), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(6), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(5), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(4), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(3), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();


        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(2), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(1), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(0D / 16D, 0D / 16D, 16D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(0), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

    }

    private void renderItemsEast(CofferTile tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
                                 IRenderTypeBuffer bufferIn, int combinedLightIn)
    {
        float sideRotation = (((float)tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos()).getValue(Coffer.ANGLE) / (float)tileEntityIn.lidOpenAmount) * 135);

        // item row 1
        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(35), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(34), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(33), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(32), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(31), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(3D / 16D - Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(30), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(29), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(28), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 6.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(27), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 2

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(26), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(25), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(24), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(23), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(22), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 7.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(21), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 3

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(20), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(19), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(18), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(17), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(16), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 8.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(15), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 4

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(14), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(13), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(12), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(11), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(10), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 9.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(9), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        // item row 5
        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(4.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(8), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(6D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(7), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(7.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(6), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 7D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(5), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 8D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(4), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(13D / 16D + Math.sin(((sideRotation - 90)/180f)*Math.PI) * 3D/16D, 4D / 16D-(Math.cos(((sideRotation + 90)/180f)*Math.PI) * 3D/16D), 9D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(3), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();


        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(9.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(2), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(11D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(1), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

        matrixStackIn.pushPose();
        matrixStackIn.translate(16D / 16D, 0D / 16D, 0D / 16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270));
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        matrixStackIn.translate(-1,0,-1);
        matrixStackIn.translate(12.5D/16D-0.5D/16D, 0.15D, 10.5D/16D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        matrixStackIn.mulPose(Vector3f.XP.rotationDegrees(15f));
        matrixStackIn.mulPose(Vector3f.ZP.rotationDegrees(2.5f));
        matrixStackIn.scale(0.20f, 0.20f, 0.20f);
        renderItem(tileEntityIn.getItemStackInSlot(0), partialTicks, matrixStackIn, bufferIn, combinedLightIn);
        matrixStackIn.popPose();

    }

    private void renderItem(ItemStack stack, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn,
                            int combinedLightIn) {
        Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemCameraTransforms.TransformType.FIXED, combinedLightIn,
                OverlayTexture.NO_OVERLAY, matrixStackIn, bufferIn, 1);
    }




    private void renderBlock(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, BlockState state) {
        Minecraft.getInstance().getBlockRenderer().renderSingleBlock(state, matrixStackIn, bufferIn, combinedLightIn, OverlayTexture.NO_OVERLAY, EmptyModelData.INSTANCE);

    }


}
