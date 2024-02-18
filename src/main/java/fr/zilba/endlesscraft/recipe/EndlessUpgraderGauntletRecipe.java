package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntlet;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import fr.zilba.endlesscraft.item.custom.upgrade.runes.Rune;
import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EndlessUpgraderGauntletRecipe extends EndlessUpgraderRecipe {
  public EndlessUpgraderGauntletRecipe(NonNullList<Ingredient> inputItems, ResourceLocation id) {
    super(inputItems, id);
  }

  @Override
  public ItemStack getResultByBase(ItemStack base, ItemStack addition) {
    if (!(base.getItem() instanceof ArcaneGauntlet) || !(addition.getItem() instanceof Rune rune)) {
      return ItemStack.EMPTY;
    }
    if (!rune.isActivated(addition)) {
      return ItemStack.EMPTY;
    }
    List<ArcaneGauntletElement> elements = ArcaneGauntlet.getElements(base);
    if (elements.contains(rune.getElement())) {
      return ItemStack.EMPTY;
    }
    ItemStack result = base.copy();
    elements.add(rune.getElement());
    ModTagUtils.setTag(result, "elements", elements.stream().map(Enum::ordinal).toList());
    return result;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return EndlessUpgraderGauntletRecipe.Serializer.INSTANCE;
  }

  public static class Serializer implements RecipeSerializer<EndlessUpgraderGauntletRecipe> {

    public static final EndlessUpgraderGauntletRecipe.Serializer INSTANCE = new EndlessUpgraderGauntletRecipe.Serializer();
    public static final ResourceLocation ID = new ResourceLocation(EndlessCraft.MOD_ID, "endless_upgrader.json");

    @Override
    public EndlessUpgraderGauntletRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

      inputs.set(0, Ingredient.of(ModItems.ARCANE_GAUNTLET.get()));
      inputs.set(1, Ingredient.fromJson(pSerializedRecipe.get("rune")));

      return new EndlessUpgraderGauntletRecipe(inputs, pRecipeId);
    }

    @Override
    public @Nullable EndlessUpgraderGauntletRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

      inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

      return new EndlessUpgraderGauntletRecipe(inputs, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, EndlessUpgraderGauntletRecipe pRecipe) {
      pBuffer.writeInt(pRecipe.inputItems.size());

      for (Ingredient ingredient : pRecipe.getIngredients()) {
        ingredient.toNetwork(pBuffer);
      }
    }
  }
}
