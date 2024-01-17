package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public interface EndlessCraftToolsItem {

  default void appendUpgradeHoverText(ItemStack pStack, List<Component> pTooltipComponents) {
    if (pStack.hasTag()) {
      List<Component> finalPTooltipComponents = new ArrayList<>();
      AtomicBoolean hasUpgrade = new AtomicBoolean(false);
      if (!pStack.hasTag() || !pStack.getTag().contains(EndlessCraft.MOD_ID)) return;
      pStack.getTag().getCompound(EndlessCraft.MOD_ID).getAllKeys().forEach(key -> {
        if (Arrays.stream(EndlessCraftUpgrade.values()).map(EndlessCraftUpgrade::getKey).anyMatch(key::equals)) {
          finalPTooltipComponents.add(
              Component.translatable("item.endless_craft.upgrade." + key,
                  pStack.getTag().getCompound(EndlessCraft.MOD_ID).getInt(key)).withStyle(ChatFormatting.YELLOW));
          hasUpgrade.set(true);
        }
      });
      if (hasUpgrade.get()) {
        if (Screen.hasShiftDown()) {
          pTooltipComponents.addAll(finalPTooltipComponents);
        } else {
          pTooltipComponents.add(Component.translatable("item.endless_craft.upgrade.shift")
              .withStyle(ChatFormatting.DARK_GRAY));
        }
      }
    }
  }
}
