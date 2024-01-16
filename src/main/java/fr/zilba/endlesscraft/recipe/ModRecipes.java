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

  public static final RegistryObject<RecipeSerializer<EndlessUpgraderRecipe>> ENDLESS_UPGRADER_SERIALIZER =
      SERIALIZERS.register("endless_upgrader", () -> EndlessUpgraderRecipe.Serializer.INSTANCE);

  public static void register(IEventBus eventBus) {
    SERIALIZERS.register(eventBus);
  }
}
