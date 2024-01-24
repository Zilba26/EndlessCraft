package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class EndlessUpgraderRecipeBuilder implements RecipeBuilder {
  private final Item upgrade;
  private final Ingredient addition;
  private final int level;
  private final RecipeCategory category;
  private final Advancement.Builder advancement = Advancement.Builder.recipeAdvancement();
  private final RecipeSerializer<?> type;

  public EndlessUpgraderRecipeBuilder(RecipeSerializer<?> pType, Item pUpgrade, Ingredient pAddition, int pLevel, RecipeCategory pCategory) {
    this.category = pCategory;
    this.type = pType;
    this.upgrade = pUpgrade;
    this.addition = pAddition;
    this.level = pLevel;
  }

  public static EndlessUpgraderRecipeBuilder upgrade(Item pUpgrade, Ingredient pAddition, int pLevel, RecipeCategory pCategory) {
    return new EndlessUpgraderRecipeBuilder(ModRecipes.ENDLESS_UPGRADER_UPGRADE_SERIALIZER.get(), pUpgrade, pAddition, pLevel, pCategory);
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, String pLocation) {
    this.save(pRecipeConsumer, new ResourceLocation(pLocation));
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer) {
    String s = RecipeBuilder.getDefaultRecipeId(upgrade) + "_" + level + "_upgrade";
    this.save(pRecipeConsumer, s);
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
    return this.upgrade;
  }

  public void save(Consumer<FinishedRecipe> pRecipeConsumer, ResourceLocation pLocation) {
    this.advancement.parent(RecipeBuilder.ROOT_RECIPE_ADVANCEMENT)
        .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(pLocation))
        .rewards(AdvancementRewards.Builder.recipe(pLocation)).requirements(RequirementsStrategy.OR);
    pRecipeConsumer.accept(new EndlessUpgraderRecipeBuilder.Result(pLocation, this.type, this.upgrade, this.addition, this.level, this.advancement, pLocation.withPrefix("recipes/" + this.category.getFolderName() + "/")));
  }

  public record Result(ResourceLocation id, RecipeSerializer<?> type, Item upgrade, Ingredient addition, int level, Advancement.Builder advancement, ResourceLocation advancementId) implements FinishedRecipe {
    public void serializeRecipeData(JsonObject jsonObject) {
      JsonObject base = new JsonObject();
      base.addProperty("type", "endless_craft:level_nbt");
      base.addProperty("item", BuiltInRegistries.ITEM.getKey(this.upgrade).toString());
      base.addProperty("level", this.level - 1);
      jsonObject.add("base", base);

      jsonObject.add("addition", this.addition.toJson());

      JsonObject result = new JsonObject();
      result.addProperty("type", "endless_craft:level_nbt");
      result.addProperty("item", BuiltInRegistries.ITEM.getKey(this.upgrade).toString());
      result.addProperty("level", this.level);
      jsonObject.add("result", result);
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