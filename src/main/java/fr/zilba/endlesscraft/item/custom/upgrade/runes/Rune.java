package fr.zilba.endlesscraft.item.custom.upgrade.runes;

import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class Rune extends Item {
  public Rune(Properties pProperties) {
    super(pProperties);
  }

  public abstract ArcaneGauntletElement getElement();

  @Override
  public boolean isFoil(ItemStack pStack) {
    return super.isFoil(pStack) || isActivated(pStack);
  }

  public boolean isActivated(ItemStack pStack) {
    return ModTagUtils.getTagBoolean(pStack, "activated");
  }

  protected void activate(ItemStack pStack) {
    System.out.println("Activating rune");
    ModTagUtils.setTag(pStack, "activated", true);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    if (isActivated(pStack)) {
      pTooltipComponents.add(Component.translatable("tooltip.endless_craft.rune.activated").withStyle(
          ChatFormatting.ITALIC, ChatFormatting.GRAY
      ));
    } else {
      pTooltipComponents.add(Component.translatable("tooltip.endless_craft."
          + ForgeRegistries.ITEMS.getKey(this).getPath() + ".information").withStyle(
          ChatFormatting.ITALIC, ChatFormatting.GRAY
      ));
    }
  }
}
