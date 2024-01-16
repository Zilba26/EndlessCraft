package fr.zilba.endlesscraft.item.custom.upgrade;

import net.minecraft.world.item.Item;

public abstract class EndlessCraftUpgradeItem extends Item {

  public EndlessCraftUpgradeItem() {
    super(new Properties().stacksTo(8));
  }

  public abstract EndlessCraftUpgrade getUpgrade();
  public String getKeyName() {
    return getUpgrade().getKey();
  }
  public int getMaxLevel() {
    return getUpgrade().getMaxLevel();
  };
}
