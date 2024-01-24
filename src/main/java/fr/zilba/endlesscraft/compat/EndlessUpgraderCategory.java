package fr.zilba.endlesscraft.compat;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.ModBlocks;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class EndlessUpgraderCategory implements IRecipeCategory<EndlessUpgraderRecipe> {

  public static final ResourceLocation UID = new ResourceLocation(EndlessCraft.MOD_ID, "endless_upgrader.json");
  public static final ResourceLocation TEXTURE = new ResourceLocation(EndlessCraft.MOD_ID,
      "textures/gui/endless_upgrader_gui.png");
  public static final RecipeType<EndlessUpgraderRecipe> ENDLESS_UPGRADER_TYPE =
      new RecipeType<>(UID, EndlessUpgraderRecipe.class);
  private final IDrawable background;
  private final IDrawable icon;

  public EndlessUpgraderCategory(IGuiHelper helper) {
    this.background = helper.createDrawable(TEXTURE, 25, 47, 90, 18);
    this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK,
        new ItemStack(ModBlocks.ENDLESS_UPGRADER.get()));
  }

  @Override
  public RecipeType<EndlessUpgraderRecipe> getRecipeType() {
    return ENDLESS_UPGRADER_TYPE;
  }

  @Override
  public Component getTitle() {
    return Component.translatable("block.endless_craft.endless_upgrader");
  }

  @Override
  public IDrawable getBackground() {
    return background;
  }

  @Override
  public IDrawable getIcon() {
    return icon;
  }

  @Override
  public void setRecipe(IRecipeLayoutBuilder builder, EndlessUpgraderRecipe recipe, IFocusGroup focuses) {
    builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addIngredients(recipe.getIngredients().get(0));
    builder.addSlot(RecipeIngredientRole.INPUT, 19, 1).addIngredients(recipe.getIngredients().get(1));

    builder.addSlot(RecipeIngredientRole.OUTPUT, 73, 1).addItemStack(recipe.getResultItem(null));
  }
}
