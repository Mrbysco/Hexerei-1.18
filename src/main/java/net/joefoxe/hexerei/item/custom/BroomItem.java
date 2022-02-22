package net.joefoxe.hexerei.item.custom;

import java.util.List;
import java.util.function.Predicate;

import net.joefoxe.hexerei.client.renderer.entity.custom.BroomEntity;
import net.joefoxe.hexerei.config.ModKeyBindings;
import net.joefoxe.hexerei.item.ModItems;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.stats.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.ActionResult;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntityPredicates;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.world.World;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

import javax.annotation.Nullable;

public class BroomItem extends Item {
    private static final Predicate<Entity> field_219989_a = EntityPredicates.NO_SPECTATORS.and(Entity::canBeCollidedWith);
    private final BroomEntity.Type type;

    public BroomItem(BroomEntity.Type broomType, Item.Properties properties) {
        super(properties);
        this.type = broomType;
    }

//    @Override
//    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
//        consumer.accept(new IItemRenderProperties() {
//            private final NonNullLazy<BlockEntityWithoutLevelRenderer> ister = NonNullLazy.of(
//                    () -> new BroomItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels()));
//
//            @Override
//            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
//                return ister.get();
//            }
//        });
//    }


    @Override
    public Object getRenderPropertiesInternal() {
        return new BroomRenderProperties();
    }

    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #use}.
     */
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        RayTraceResult raytraceresult = getPlayerPOVHitResult(worldIn, playerIn, RayTraceContext.FluidMode.ANY);
        if (raytraceresult.getType() == RayTraceResult.Type.MISS) {
            return ActionResult.pass(itemstack);
        } else {
            Vector3d vector3d = playerIn.getLookAngle();
            double d0 = 5.0D;
            List<Entity> list = worldIn.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), field_219989_a);
            if (!list.isEmpty()) {
                Vector3d vector3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AxisAlignedBB axisalignedbb = entity.getBoundingBox().inflate((double)entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return ActionResult.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == RayTraceResult.Type.BLOCK) {
                BroomEntity boatentity = new BroomEntity(worldIn, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boatentity.setBroomType(this.type);
                boatentity.setYRot(playerIn.getYRot());
                boatentity.itemHandler.deserializeNBT(itemstack.getOrCreateTag().getCompound("Inventory"));
                if(!itemstack.getOrCreateTag().contains("floatMode")) {
                    boatentity.itemHandler.setStackInSlot(2, new ItemStack(ModItems.BROOM_BRUSH.get()));
                    boatentity.sync();
                }
                boatentity.floatMode = (itemstack.getOrCreateTag().getBoolean("floatMode"));

                if (!worldIn.noCollision(boatentity, boatentity.getBoundingBox().inflate(-0.1D))) {
                    return ActionResult.fail(itemstack);
                } else {
                    if (!worldIn.isClientSide) {

                        worldIn.addFreshEntity(boatentity);
                        if (!playerIn.getAbilities().instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return ActionResult.sidedSuccess(itemstack, worldIn.isClientSide());
                }
            } else {
                return ActionResult.pass(itemstack);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        CompoundNBT inv = stack.getOrCreateTag().getCompound("Inventory");
        ListNBT tagList = inv.getList("Items", INBT.TAG_COMPOUND);

        if(Screen.hasShiftDown()) {

            tooltip.add(new TranslationTextComponent("<%s>", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA6600)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_shift_2", new TranslationTextComponent(ModKeyBindings.broomDescend.getKey().getName()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0xCCCC00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_shift_3").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_shift_4").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            if(stack.is(ModItems.MAHOGANY_BROOM.get())) {
                tooltip.add(new TranslationTextComponent(""));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.mahogany_broom_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.mahogany_broom_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            }
            else {
                tooltip.add(new TranslationTextComponent(""));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.willow_broom_shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.willow_broom_shift_2").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            }
            if(stack.hasTag()) {
                tooltip.add(new TranslationTextComponent(""));
                CompoundNBT brushTags = null;
                CompoundNBT miscTags = null;
                CompoundNBT satchelTags = null;

                for(int i = 0; i < 3; i++){
                    if (tagList.getCompound(i).getInt("Slot") == 0 && miscTags == null)
                        miscTags = tagList.getCompound(i);
                    if (tagList.getCompound(i).getInt("Slot") == 1 && satchelTags == null)
                        satchelTags = tagList.getCompound(i);
                    if (tagList.getCompound(i).getInt("Slot") == 2 && brushTags == null)
                        brushTags = tagList.getCompound(i);
                }
                ItemStack miscItem = miscTags == null ? ItemStack.EMPTY : ItemStack.of(miscTags);
                ItemStack brushItem = brushTags == null ? ItemStack.EMPTY : ItemStack.of(brushTags);

                if(tagList.size() > (miscItem.isEmpty() ? 0 : 1) + (brushItem.isEmpty() ? 0 : 1)) {

                    tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_5").withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                    for (int i = 0; i < tagList.size(); i++)
                    {
                        CompoundNBT itemTags = tagList.getCompound(i);

                        TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(itemTags).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));
                        TranslationTextComponent itemText2 = (TranslationTextComponent) new TranslationTextComponent(" - %s", itemText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999)));
                        int countText = ItemStack.of(itemTags).getCount();
                        itemText2.append(" x" + countText);

                        if(itemTags.getInt("Slot") != 0 && itemTags.getInt("Slot") != 1 && itemTags.getInt("Slot") != 2)
                            tooltip.add(itemText2);
                    }
                }
            }
        } else {
//            tooltip.add(new TranslatableComponent("tooltip.hexerei.broom"));
            tooltip.add(new TranslationTextComponent("[%s]", new TranslationTextComponent("tooltip.hexerei.shift").withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAAAA00)))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
            if(stack.hasTag())
            {
                CompoundNBT brushTags = null;
                CompoundNBT miscTags = null;
                CompoundNBT satchelTags = null;

                for(int i = 0; i < 3; i++){
                    if (tagList.getCompound(i).getInt("Slot") == 0 && miscTags == null)
                        miscTags = tagList.getCompound(i);
                    if (tagList.getCompound(i).getInt("Slot") == 1 && satchelTags == null)
                        satchelTags = tagList.getCompound(i);
                    if (tagList.getCompound(i).getInt("Slot") == 2 && brushTags == null)
                        brushTags = tagList.getCompound(i);
                }
                ItemStack miscItem = miscTags == null ? ItemStack.EMPTY : ItemStack.of(miscTags);
                ItemStack brushItem = brushTags == null ? ItemStack.EMPTY : ItemStack.of(brushTags);


                TranslationTextComponent miscText = (TranslationTextComponent) new TranslationTextComponent(miscTags == null ? "" : ItemStack.of(miscTags).isEmpty() ? "" : ((ItemStack.of(miscTags).getDescriptionId()))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA9900)));

                TranslationTextComponent satchelText = (TranslationTextComponent) new TranslationTextComponent((satchelTags == null ? "" : ItemStack.of(satchelTags).isEmpty() ? "" : ((ItemStack.of(satchelTags).getDescriptionId())))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA9900)));

                TranslationTextComponent brushText = (TranslationTextComponent) new TranslationTextComponent((brushTags == null ? "" : brushItem.isEmpty() ? "" : ((brushItem.getDescriptionId())))).withStyle(Style.EMPTY.withColor(Color.fromRgb(0xAA9900)));

                if(!brushItem.isEmpty())
                    brushText.append(new TranslationTextComponent(" - %s/%s", brushItem.getMaxDamage() - brushItem.getDamageValue(), brushItem.getMaxDamage()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                if(miscItem.is(ModItems.BROOM_KEYCHAIN.get()))
                {
                    CompoundNBT inv2 = miscItem.getOrCreateTag();
                    ListNBT tagList2 = inv2.getList("Items", INBT.TAG_COMPOUND);
                    CompoundNBT compoundtag = tagList2.getCompound(0);
                    CompoundNBT itemTags = tagList2.getCompound(0);

                    TranslationTextComponent itemText = (TranslationTextComponent) new TranslationTextComponent(ItemStack.of(compoundtag).getDescriptionId()).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x998800)));

                    miscText.append(new TranslationTextComponent(" - %s", itemText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));

                }

                tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_2", miscText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_3", satchelText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));
                tooltip.add(new TranslationTextComponent("tooltip.hexerei.broom_4", brushText).withStyle(Style.EMPTY.withColor(Color.fromRgb(0x999999))));


            }
        }


        super.appendHoverText(stack, world, tooltip, flagIn);
    }
}