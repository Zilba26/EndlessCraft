package fr.zilba.endlesscraft.item.custom.upgrade;

import fr.zilba.endlesscraft.item.custom.tools.EndlessCraftToolsItem;
import net.minecraft.world.item.ItemStack;

public enum EndlessCraftUpgrade {

  ARMY("army", 5),
  PROTECTION("protection", 4),
  HEALTH("health", 5),
  LIFE_STEAL("life_steal", 5),
  INFINITE_DURABILITY("infinite_durability", 1);

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
      if (stack.hasTag()) {
        if (stack.getTag().contains(upgrade.getKey())) {
          return stack.getTag().getInt(upgrade.getKey());
        }
      }
    }
    return 0;
  }
}
