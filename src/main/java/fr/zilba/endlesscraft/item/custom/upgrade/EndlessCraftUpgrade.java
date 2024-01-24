package fr.zilba.endlesscraft.item.custom.upgrade;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.tools.EndlessCraftToolsItem;
import net.minecraft.world.item.ItemStack;

public enum EndlessCraftUpgrade {

  ARMY("army"),
  PROTECTION("protection"),
  HEALTH("health"),
  LIFE_STEAL("life_steal"),
  FIRE_RESISTANCE("fire_resistance"),
  INFINITE_DURABILITY("infinite_durability"),
  NIGHT_VISION("night_vision"),
  FLY("fly");

  private final String key;

  EndlessCraftUpgrade(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
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
