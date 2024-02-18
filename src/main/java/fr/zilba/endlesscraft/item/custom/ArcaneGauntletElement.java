package fr.zilba.endlesscraft.item.custom;

import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.item.custom.upgrade.runes.Rune;
import fr.zilba.endlesscraft.screen.radial.RadialMenuSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.List;

public enum ArcaneGauntletElement {
  FIRE,
  WATER,
  ELECTRIC,
  ICE;

  public static List<RadialMenuSlot<Rune>> getRadialMenuSlots() {
    return Arrays.stream(ArcaneGauntletElement.values()).map(
        element -> new RadialMenuSlot<>(element.name(), element.getRune())
    ).toList();
  }

  public ChatFormatting getChatColor() {
    return switch (this) {
      case FIRE -> ChatFormatting.RED;
      case WATER -> ChatFormatting.AQUA;
      case ELECTRIC -> ChatFormatting.YELLOW;
      case ICE -> ChatFormatting.WHITE;
    };
  }

  public Rune getRune() {
    Item rune = switch (this) {
      case FIRE -> ModItems.FIRE_RUNE.get();
      case WATER -> ModItems.WATER_RUNE.get();
      case ELECTRIC -> ModItems.ELECTRIC_RUNE.get();
      case ICE -> ModItems.ICE_RUNE.get();
    };
    return (Rune) rune;
  }
}
