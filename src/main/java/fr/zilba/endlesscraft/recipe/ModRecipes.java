package fr.zilba.endlesscraft.recipe;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {

  public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
      DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, EndlessCraft.MOD_ID);

  public static final RegistryObject<RecipeSerializer<EndlessUpgraderToolRecipe>> ENDLESS_UPGRADER_TOOL_SERIALIZER =
      SERIALIZERS.register("endless_upgrader_tool", () -> EndlessUpgraderToolRecipe.Serializer.INSTANCE);

  public static final RegistryObject<RecipeSerializer<EndlessUpgraderUpgradeRecipe>> ENDLESS_UPGRADER_UPGRADE_SERIALIZER =
      SERIALIZERS.register("endless_upgrader_upgrade", () -> EndlessUpgraderUpgradeRecipe.Serializer.INSTANCE);

  public static void register(IEventBus eventBus) {
    SERIALIZERS.register(eventBus);
  }
}
