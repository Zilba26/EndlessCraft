package fr.zilba.endlesscraft.item.custom.upgrade;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.tools.EndlessCraftToolsItem;
import net.minecraft.world.item.ItemStack;

public enum EndlessCraftUpgrade {

  ARMY("army", 5),
  PROTECTION("protection", 4),
  HEALTH("health", 5),
  LIFE_STEAL("life_steal", 5),
  FIRE_RESISTANCE("fire_resistance", 1),
  INFINITE_DURABILITY("infinite_durability", 1),
  NIGHT_VISION("night_vision", 1),
  FLY("fly", 1);

  private final String key;
  private final int maxLevel;

  EndlessCraftUpgrade(String key, int maxLevel) {
    this.key = key;
    this.maxLevel = maxLevel;
  }

  public String getKey() {
    return key;
  }

  public int getMaxLevel() {
    return maxLevel;
  }

  public static int getItemLevel(ItemStack stack, EndlessCraftUpgrade upgrade) {
    if (stack.getItem() instanceof EndlessCraftToolsItem) {
      if (stack.hasTag() && stack.getTag().contains(EndlessCraft.MOD_ID)) {
        if (stack.getTag().getCompound(EndlessCraft.MOD_ID).contains(upgrade.getKey())) {
          return stack.getTag().getCompound(EndlessCraft.MOD_ID).getInt(upgrade.getKey());
        }
      }
    }
    return 0;
  }

  public static int getLevel(ItemStack stack) {
    if (stack.getItem() instanceof EndlessCraftUpgradeItem) {
      if (stack.hasTag() && stack.getTag().contains(EndlessCraft.MOD_ID)) {
        if (stack.getTag().getCompound(EndlessCraft.MOD_ID).contains("level")) {
          return stack.getTag().getCompound(EndlessCraft.MOD_ID).getInt("level");
        }
      }
    }
    return 0;
  }
}
