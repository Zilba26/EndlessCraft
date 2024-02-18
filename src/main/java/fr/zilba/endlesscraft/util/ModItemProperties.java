package fr.zilba.endlesscraft.util;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModItemProperties {

  public static void addCustomItemProperties() {
    ItemProperties.register(ModItems.ARCANE_GAUNTLET.get(), new ResourceLocation(EndlessCraft.MOD_ID, "element"),
        (pStack, pLevel, pEntity, pSeed) -> ModTagUtils.getTagFloat(pStack, "element"));

    makeBow(ModItems.TEMPORAL_ARC.get());
  }

  private static void makeBow(Item item) {
    ItemProperties.register(item, new ResourceLocation("pull"), (p_174635_, p_174636_, p_174637_, p_174638_) -> {
      if (p_174637_ == null) {
        return 0.0F;
      } else {
        return p_174637_.getUseItem() != p_174635_ ? 0.0F : (float)(p_174635_.getUseDuration() - p_174637_.getUseItemRemainingTicks()) / 20.0F;
      }
    });
    ItemProperties.register(item, new ResourceLocation("pulling"), (p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F);
  }
}
