package fr.zilba.endlesscraft.datagen;

import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderToolRecipeBuilder;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderUpgradeRecipeBuilder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {

  public ModRecipeProvider(PackOutput pOutput) {
    super(pOutput);
  }

  @Override
  protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CONTROL_STICK.get())
        .pattern("  *")
        .pattern(" / ")
        .pattern("/  ")
        .define('/', Items.BLAZE_ROD)
        .define('*', Items.NETHER_STAR)
        .unlockedBy(getHasName(Items.NETHER_STAR), has(Items.BLAZE_ROD))
        .save(pWriter);

    ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.TELEPORTATION_STICK.get())
        .pattern("  *")
        .pattern(" / ")
        .pattern("/  ")
        .define('/', Items.BLAZE_ROD)
        .define('*', Items.ENDER_PEARL)
        .unlockedBy(getHasName(Items.ENDER_PEARL), has(Items.BLAZE_ROD))
        .save(pWriter);

    this.buildUpgradeRecipe(pWriter, ModItems.PROTECTION_UPGRADE.get(), List.of(Items.IRON_BLOCK, Items.GOLD_BLOCK, Items.DIAMOND_BLOCK, Items.NETHERITE_INGOT));
    this.buildUpgradeRecipe(pWriter, ModItems.LIFE_STEAL_UPGRADE.get(), List.of(Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND_BLOCK, Items.NETHERITE_INGOT));
    this.buildUpgradeRecipe(pWriter, ModItems.ARMY_UPGRADE.get(), List.of(Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND_BLOCK, Items.NETHERITE_INGOT));
    this.buildUpgradeRecipe(pWriter, ModItems.FLY_UPGRADE.get(), List.of(Items.DIAMOND_BLOCK, Items.NETHERITE_SCRAP, Items.NETHERITE_INGOT, Items.NETHERITE_BLOCK));
    this.buildUpgradeRecipe(pWriter, ModItems.TIME_UPGRADE.get(), List.of(Items.IRON_INGOT, Items.GOLD_INGOT, Items.DIAMOND_BLOCK, Items.NETHERITE_INGOT));

    this.buildToolRecipe(pWriter, ModItems.CONTROL_STICK.get(), ModItems.ARMY_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.CONTROL_STICK.get(), ModItems.PROTECTION_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_ARMOR.get(), ModItems.FLY_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_ARMOR.get(), ModItems.INFINITE_DURABILITY_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_ARMOR.get(), ModItems.NIGHT_VISION_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_SWORD.get(), ModItems.FIRE_RESISTANCE_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_SWORD.get(), ModItems.LIFE_STEAL_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.INFINITY_SWORD.get(), ModItems.INFINITE_DURABILITY_UPGRADE.get());
    this.buildToolRecipe(pWriter, ModItems.TELEPORTATION_STICK.get(), ModItems.TIME_UPGRADE.get());
  }

  private void buildUpgradeRecipe(Consumer<FinishedRecipe> pWriter, Item upgrade, List<ItemLike> levelsUpgrades) {
    for (ItemLike levelUpgrade : levelsUpgrades) {
      EndlessUpgraderUpgradeRecipeBuilder
          .upgrade(upgrade, Ingredient.of(levelUpgrade), levelsUpgrades.indexOf(levelUpgrade)+2, RecipeCategory.MISC)
          .unlockedBy("has_" + getItemName(upgrade), has(levelUpgrade))
          .save(pWriter);
    }
  }

  private void buildToolRecipe(Consumer<FinishedRecipe> pWriter, Item tool, Item upgrade) {
    EndlessUpgraderToolRecipeBuilder
        .craft(Ingredient.of(upgrade), Ingredient.of(tool), RecipeCategory.TOOLS)
        .unlockedBy("has_" + getItemName(upgrade), has(upgrade))
        .save(pWriter);
  }
}
