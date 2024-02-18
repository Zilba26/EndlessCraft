package fr.zilba.endlesscraft.recipe.builder;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.recipe.ModRecipes;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EndlessUpgraderGauntletRecipeBuilder implements RecipeBuilder {

  private final Ingredient rune;
  private final RecipeCategory category;
  private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
  private final RecipeSerializer<?> type;

  public EndlessUpgraderGauntletRecipeBuilder(RecipeSerializer<?> pType, Ingredient pRune, RecipeCategory pCategory) {
    this.category = pCategory;
    this.type = pType;
    this.rune = pRune;
  }

  public static EndlessUpgraderGauntletRecipeBuilder craft(Ingredient pUpgrade, RecipeCategory pCategory) {
    return new EndlessUpgraderGauntletRecipeBuilder(ModRecipes.ENDLESS_UPGRADER_GAUNTLET_SERIALIZER.get(), pUpgrade, pCategory);
  }

  @Override
  public RecipeBuilder unlockedBy(String pCriterionName, CriterionTriggerInstance pCriterionTrigger) {
    this.advancement.addCriterion(pCriterionName, pCriterionTrigger);
    return this;
  }

  @Override
  public RecipeBuilder group(@org.jetbrains.annotations.Nullable String pGroupName) {
    return this;
  }

  @Override
  public Item getResult() {
    return ModItems.ARCANE_GAUNTLET.get();
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
    this.save(pRecipeConsumer, new ResourceLocation(pLocation));
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer) {
    String s = RecipeBuilder.getDefaultRecipeId(ModItems.ARCANE_GAUNTLET.get())
        + "_with_" + this.rune.getItems()[0].getItem();
    this.save(pRecipeConsumer, s);
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
    this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pLocation))
        .rewards(AdvancementRewards.Builder.recipe(pLocation)).requirements(RequirementsStrategy.OR);
    pRecipeConsumer.accept(new EndlessUpgraderGauntletRecipeBuilder.Result(pLocation, this.type, this.rune, this.advancement, pLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
  }

  public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient rune, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
    public void serializeRecipeData(JsonObject jsonObject) {
      jsonObject.add("rune", this.rune.toJson());
    }

    public ResourceLocation getId() {
      return new ResourceLocation(EndlessCraft.MOD_ID, this.id.getPath());
    }

    public RecipeSerializer<?> getType() {
      return this.type;
    }

    @Nullable
    public JsonObject serializeAdvancement() {
      return this.advancement.serializeToJson();
    }

    @Nullable
    public ResourceLocation getAdvancementId() {
      return this.advancementId;
    }
  }

}
