package fr.zilba.endlesscraft.item;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

  public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
      DeferredRegister.create(Registries.CREATIVE_MODE_TAB, EndlessCraft.MOD_ID);

  public static final RegistryObject<CreativeModeTab> ENDLESS_CRAFT_TAB
      = CREATIVE_MODE_TABS.register("endless_craft_tab",
      () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CONTROL_STICK.get()))
          .title(Component.translatable("creativetab.endless_craft_tab"))
          .displayItems(((itemDisplayParameters, output) -> {
            output.accept(ModItems.CONTROL_STICK.get());
            output.accept(ModItems.ARMY_UPGRADE.get());
            output.accept(ModItems.PROTECTION_UPGRADE.get());
            output.accept(ModItems.HEALTH_UPGRADE.get());
            output.accept(ModItems.LIFE_STEAL_UPGRADE.get());
            output.accept(ModItems.INFINITE_DURABILITY_UPGRADE.get());
            output.accept(ModItems.STAR_FRAGMENT.get());
            output.accept(ModItems.INFINITY_SWORD.get());

            output.accept(ModBlocks.ENDLESS_UPGRADER.get());
            output.accept(ModBlocks.METEORITE.get());
            output.accept(ModBlocks.STAR.get());
          }))
          .build());

  public static void register(IEventBus eventBus) {
    CREATIVE_MODE_TABS.register(eventBus);
  }
}