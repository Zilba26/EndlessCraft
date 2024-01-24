package fr.zilba.endlesscraft.datagen;

import fr.zilba.endlesscraft.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

  public ModRecipeProvider(PackOutput pOutput) {
    super(pOutput);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
    ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CONTROL_STICK.get())
        .pattern("  *")
        .pattern(" / ")
        .pattern("/  ")
        .define('/', Items.BLAZE_ROD)
        .define('*', Items.NETHER_STAR)
        .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.BLAZE_ROD))
        .save(pWriter);
  }
}
