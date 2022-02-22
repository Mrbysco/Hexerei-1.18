package net.joefoxe.hexerei.item.custom;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.joefoxe.hexerei.particle.ModParticleTypes;
import net.joefoxe.hexerei.util.HexereiPacketHandler;
import net.joefoxe.hexerei.util.message.DowsingRodUpdatePositionPacket;
import net.joefoxe.hexerei.util.message.EmitExtinguishParticlesPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.util.Mth;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import net.minecraft.item.Item.Properties;

public class DowsingRodItem extends Item {

    public BlockPos nearestPos = null;
    public boolean swampMode = true;

    public DowsingRodItem(Properties properties) {
        super(properties);
    }


    public static double angleDifference( double angle1, double angle2 )
    {
        double diff = ( angle2 - angle1 + 180 ) % 360 - 180;
        return diff < -180 ? diff + 360 : diff;
    }


    @Override
    public void inventoryTick(ItemStack p_41404_, World world, Entity entity, int p_41407_, boolean p_41408_) {
        super.inventoryTick(p_41404_, world, entity, p_41407_, p_41408_);

        if(entity instanceof PlayerEntity){
            if(this.nearestPos== null && ((PlayerEntity) entity).getMainHandItem() == p_41404_ || ((PlayerEntity) entity).getOffhandItem() == p_41404_) {
                if (this.swampMode)
                    findSwamp(world, entity);
                else
                    findJungle(world, entity);
            }
        }

    }


    public static final DynamicCommandExceptionType ERROR_INVALID_BIOME = new DynamicCommandExceptionType((p_137850_) -> {
        return new TranslationTextComponent("commands.locatebiome.invalid", p_137850_);
    });

    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);


        if(playerIn.isSecondaryUseActive()) {
            if(!worldIn.isClientSide){
                playerIn.getCooldowns().addCooldown(this, 20);
                this.swampMode = !this.swampMode;

                String s = "display.hexerei.dowsing_rod_swamp";
                if (!this.swampMode)
                    s = "display.hexerei.dowsing_rod_jungle";

                if (this.swampMode)
                    findSwamp(worldIn, playerIn);
                else
                    findJungle(worldIn, playerIn);


                playerIn.displayClientMessage(new TranslationTextComponent(s), true);
            }
        }
        else
        {
            if (this.swampMode)
                findSwamp(worldIn, playerIn);
            else
                findJungle(worldIn, playerIn);

        }
        if(!worldIn.isClientSide)
            HexereiPacketHandler.instance.send(PacketDistributor.TRACKING_CHUNK.with(() -> worldIn.getChunkAt(playerIn.blockPosition())), new DowsingRodUpdatePositionPacket(itemstack, this.nearestPos, this.swampMode));

        return ActionResult.pass(itemstack);
    }

    public void findSwamp(World worldIn, Entity entity)
    {
        if(!worldIn.isClientSide){
            Biome biome = null;
            try {
                biome = entity.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getOptional(Biomes.SWAMP).orElseThrow(() -> {
                    return ERROR_INVALID_BIOME.create(Biomes.SWAMP);
                });
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }


            this.nearestPos = ((ServerWorld) worldIn).findNearestBiome(biome, entity.blockPosition(), 6400, 8);

        }
    }



    public void findJungle(World worldIn, Entity entity)
    {
        if(!worldIn.isClientSide){
            Biome biome = null;
            try {
                biome = entity.getServer().registryAccess().registryOrThrow(Registry.BIOME_REGISTRY).getOptional(Biomes.JUNGLE).orElseThrow(() -> {
                    return ERROR_INVALID_BIOME.create(Biomes.SWAMP);
                });
            } catch (CommandSyntaxException e) {
                e.printStackTrace();
            }


            this.nearestPos = ((ServerWorld) worldIn).findNearestBiome(biome, entity.blockPosition(), 6400, 8);

        }
    }

    //BlockPos blockpos1 = p_137843_.getLevel().findNearestBiome(biome, blockpos, 6400, 8);
    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(Screen.hasShiftDown()) {
            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.dowsing_rod_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.dowsing_rod_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.dowsing_rod_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.dowsing_rod_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        } else {
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.dowsing_rod").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
        }


        super.appendHoverText(stack, world, tooltip, flagIn);
    }
}