package net.joefoxe.hexerei.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraftforge.client.IItemRenderProperties;

public class BroomRenderProperties implements IItemRenderProperties {

    @Override
    public ItemStackTileEntityRenderer getItemStackRenderer() {
        return new BroomItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }
}
