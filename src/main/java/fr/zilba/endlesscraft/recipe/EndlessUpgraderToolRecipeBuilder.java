package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
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

public class EndlessUpgraderToolRecipeBuilder implements RecipeBuilder {
  private final Ingredient upgrade;
  private final Ingredient tool;
  private final RecipeCategory category;
  private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
  private final RecipeSerializer<?> type;

  public EndlessUpgraderToolRecipeBuilder(RecipeSerializer<?> pType, Ingredient pUpgrade, Ingredient pTool, RecipeCategory pCategory) {
    this.category = pCategory;
    this.type = pType;
    this.upgrade = pUpgrade;
    this.tool = pTool;
  }

  public static EndlessUpgraderToolRecipeBuilder craft(Ingredient pUpgrade, Ingredient pTool, RecipeCategory pCategory) {
    return new EndlessUpgraderToolRecipeBuilder(ModRecipes.ENDLESS_UPGRADER_TOOL_SERIALIZER.get(), pUpgrade, pTool, pCategory);
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
    return this.tool.getItems()[0].getItem();
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
    System.out.println("Saving recipe " + pLocation);
    this.save(pRecipeConsumer, new ResourceLocation(pLocation));
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer) {
    String s = RecipeBuilder.getDefaultRecipeId(this.tool.getItems()[0].getItem())
        + "_with_" + this.upgrade.getItems()[0].getItem();
    this.save(pRecipeConsumer, s);
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
    this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pLocation))
        .rewards(AdvancementRewards.Builder.recipe(pLocation)).requirements(RequirementsStrategy.OR);
    pRecipeConsumer.accept(new EndlessUpgraderToolRecipeBuilder.Result(pLocation, this.type, this.upgrade, this.tool, this.advancement, pLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
  }

  public record Result(ResourceLocation id, RecipeSerializer<?> type, Ingredient upgrade, Ingredient tool, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
    public void serializeRecipeData(JsonObject jsonObject) {
      jsonObject.add("base", this.tool.toJson());
      jsonObject.add("addition", this.upgrade.toJson());
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
