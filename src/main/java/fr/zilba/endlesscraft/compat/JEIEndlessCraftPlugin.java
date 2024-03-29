package fr.zilba.endlesscraft.compat;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderRecipe;
import fr.zilba.endlesscraft.screen.EndlessUpgraderScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIEndlessCraftPlugin implements IModPlugin {
  @Override
  public ResourceLocation getPluginUid() {
    return new ResourceLocation(EndlessCraft.MOD_ID, "jei_plugin");
  }

  @Override
  public void registerCategories(IRecipeCategoryRegistration registration) {
    registration.addRecipeCategories(new EndlessUpgraderCategory(registration.getJeiHelpers().getGuiHelper()));
  }

  @Override
  public void registerRecipes(IRecipeRegistration registration) {
    RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

    List<EndlessUpgraderRecipe> polishingRecipes = recipeManager
        .getAllRecipesFor(EndlessUpgraderRecipe.Type.INSTANCE);
    registration.addRecipes(EndlessUpgraderCategory.ENDLESS_UPGRADER_TYPE, polishingRecipes);
  }

  @Override
  public void registerGuiHandlers(IGuiHandlerRegistration registration) {
    registration.addRecipeClickArea(EndlessUpgraderScreen.class, 60, 30, 20, 30,
        EndlessUpgraderCategory.ENDLESS_UPGRADER_TYPE);
  }
}
