package fr.zilba.endlesscraft.item;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.TemporalArc;
import fr.zilba.endlesscraft.item.custom.tools.InfinitySword;
import fr.zilba.endlesscraft.item.custom.tools.ControlStickItem;
import fr.zilba.endlesscraft.item.custom.upgrade.control_stick.ArmyUpgradeItem;
import fr.zilba.endlesscraft.item.custom.upgrade.control_stick.ProtectionUpgradeItem;
import fr.zilba.endlesscraft.item.custom.upgrade.infinity_sword.FireResistanceUpgrade;
import fr.zilba.endlesscraft.item.custom.upgrade.infinity_sword.InfiniteDurabilityUpgrade;
import fr.zilba.endlesscraft.item.custom.upgrade.infinity_sword.LifeStealUpgrade;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

  public static final DeferredRegister<Item> ITEMS =
      DeferredRegister.create(ForgeRegistries.ITEMS, EndlessCraft.MOD_ID);

  public static final RegistryObject<Item> CONTROL_STICK = ITEMS.register("control_stick",
          () -> new ControlStickItem(new Item.Properties().durability(50)));

  public static final RegistryObject<Item> ARMY_UPGRADE = ITEMS.register("army_upgrade", ArmyUpgradeItem::new);
  public static final RegistryObject<Item> PROTECTION_UPGRADE = ITEMS.register("protection_upgrade", ProtectionUpgradeItem::new);
  public static final RegistryObject<Item> HEALTH_UPGRADE = ITEMS.register("health_upgrade", ProtectionUpgradeItem::new);
  public static final RegistryObject<Item> LIFE_STEAL_UPGRADE = ITEMS.register("life_steal_upgrade", LifeStealUpgrade::new);
  public static final RegistryObject<Item> FIRE_RESISTANCE_UPGRADE = ITEMS.register("fire_resistance_upgrade", FireResistanceUpgrade::new);
  public static final RegistryObject<Item> INFINITE_DURABILITY_UPGRADE = ITEMS.register("infinite_durability_upgrade", InfiniteDurabilityUpgrade::new);
  public static final RegistryObject<Item> STAR_FRAGMENT = ITEMS.register("star_fragment", () -> new Item(new Item.Properties()));

  public static final RegistryObject<Item> INFINITY_SWORD = ITEMS.register("infinity_sword",
      () -> new InfinitySword(new Item.Properties()));

  public static final RegistryObject<Item> TEMPORAL_ARC = ITEMS.register("temporal_bow",
      () -> new TemporalArc(new Item.Properties().durability(1000)));

  public static void register(IEventBus eventBus) {
    ITEMS.register(eventBus);
  }
}
