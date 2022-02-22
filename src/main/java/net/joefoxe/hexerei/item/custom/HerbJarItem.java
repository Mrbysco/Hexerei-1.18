package net.joefoxe.hexerei.item.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.joefoxe.hexerei.block.ModBlocks;
import net.joefoxe.hexerei.tileentity.HerbJarTile;
import net.joefoxe.hexerei.util.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.ActionResultType;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.World;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.IItemRenderProperties;
import net.minecraftforge.common.util.NonNullLazy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import net.minecraft.item.Item.Properties;

public class HerbJarItem extends BlockItem {

    public HerbJarItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
//        if(Screen.hasShiftDown()) {
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.dowsing_rod"));
//        } else {
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.dowsing_rod"));
//        }


        super.appendHoverText(stack, world, tooltip, flagIn);
    }

    @Override
    public ActionResultType place(BlockItemUseContext context) {

        return super.place(context);
    }

    @Override
    public void registerBlocks(Map<Block, Item> pBlockToItemMap, Item pItem) {
        super.registerBlocks(pBlockToItemMap, pItem);
    }

//
//    @Override
//    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
//        consumer.accept(new IItemRenderProperties() {
//            @Override
//            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
//                return HerbJarItemRenderer.INSTANCE;
//            }
//        });
//    }

}