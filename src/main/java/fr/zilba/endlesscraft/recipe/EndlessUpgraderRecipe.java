package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgradeItem;
import fr.zilba.endlesscraft.item.custom.upgrade.infinity_sword.InfiniteDurabilityUpgrade;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class EndlessUpgraderRecipe implements Recipe<SimpleContainer> {

  private final NonNullList<Ingredient> inputItems;
  private final ResourceLocation id;

  public EndlessUpgraderRecipe(NonNullList<Ingredient> inputItems, ResourceLocation id) {
    this.inputItems = inputItems;
    this.id = id;
  }

  public ItemStack getTool() {
    return inputItems.get(0).getItems()[0].copy();
  }

  public ItemStack getUpgrade() {
    return inputItems.get(1).getItems()[0].copy();
  }

  @Override
  public boolean matches(SimpleContainer pContainer, Level pLevel) {
    if (pLevel.isClientSide()) {
      return false;
    }
    return inputItems.get(0).test(pContainer.getItem(0))
        && inputItems.get(1).test(pContainer.getItem(1));
  }

  @Override
  public NonNullList<Ingredient> getIngredients() {
    return inputItems;
  }

  @Override
  public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
    return null;
  }

  @Override
  public boolean canCraftInDimensions(int pWidth, int pHeight) {
    return true;
  }

  @Override
  public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
    return getResultByTool(getTool());
  }

  public ItemStack getResultByTool(ItemStack tool) {
    ItemStack result = tool.copy();
    EndlessCraftUpgradeItem upgrade = (EndlessCraftUpgradeItem) getUpgrade().getItem();

    CompoundTag tag = result.getOrCreateTag();
    if (!tag.contains(EndlessCraft.MOD_ID)) {
      tag.put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    tag.getCompound(EndlessCraft.MOD_ID).putInt(upgrade.getKeyName(),
        tag.getCompound(EndlessCraft.MOD_ID).getInt(upgrade.getKeyName()) + 1);

    if (this.getUpgrade().getItem() instanceof InfiniteDurabilityUpgrade) {
      result.getTag().putBoolean("Unbreakable", true);
    }
    return result;
  }

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Serializer.INSTANCE;
  }

  @Override
  public RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  public static class Type implements RecipeType<EndlessUpgraderRecipe> {

    public static final Type INSTANCE = new Type();
    public static final String ID = "endless_upgrader";
  }

  public static class Serializer implements RecipeSerializer<EndlessUpgraderRecipe> {

    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = new ResourceLocation(EndlessCraft.MOD_ID, "endless_upgrader");

    @Override
    public EndlessUpgraderRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
      JsonObject ingredients = GsonHelper.getAsJsonObject(pSerializedRecipe, "ingredients");

      NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

      inputs.set(0, Ingredient.fromJson(ingredients.get("tool")));
      inputs.set(1, Ingredient.fromJson(ingredients.get("upgrade")));

      return new EndlessUpgraderRecipe(inputs, pRecipeId);
    }

    @Override
    public @Nullable EndlessUpgraderRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

      inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

      return new EndlessUpgraderRecipe(inputs, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, EndlessUpgraderRecipe pRecipe) {
      pBuffer.writeInt(pRecipe.inputItems.size());

      for (Ingredient ingredient : pRecipe.getIngredients()) {
        ingredient.toNetwork(pBuffer);
      }
    }
  }
}
