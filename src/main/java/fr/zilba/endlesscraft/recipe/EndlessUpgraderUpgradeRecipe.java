package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.common.crafting.StrictNBTIngredient;
import org.jetbrains.annotations.Nullable;

public class EndlessUpgraderUpgradeRecipe extends EndlessUpgraderRecipe {

  private final ItemStack result;
  public EndlessUpgraderUpgradeRecipe(NonNullList<Ingredient> inputItems, ItemStack result, ResourceLocation id) {
    super(inputItems, id);
    this.result = result;
  }

  public ItemStack getResultByBase(ItemStack upgrade, ItemStack addition) {
    return result.copy();
  }

  @Override
  public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
    return result.copy();
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Serializer.INSTANCE;
  }

  public static class Serializer implements RecipeSerializer<EndlessUpgraderUpgradeRecipe> {
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = new ResourceLocation(EndlessCraft.MOD_ID, "endless_upgrader_upgrade");

    @Override
    public EndlessUpgraderUpgradeRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
      ItemStack result = Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe,
          "result")).getItems()[0];

      NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

      inputs.set(0, Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "base")));
      inputs.set(1, Ingredient.fromJson(GsonHelper.getAsJsonObject(pSerializedRecipe, "addition")));

      return new EndlessUpgraderUpgradeRecipe(inputs, result, pRecipeId);
    }

    @Override
    public @Nullable EndlessUpgraderUpgradeRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

      inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

      ItemStack output = pBuffer.readItem();
      return new EndlessUpgraderUpgradeRecipe(inputs, output, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, EndlessUpgraderUpgradeRecipe pRecipe) {
      pBuffer.writeInt(pRecipe.inputItems.size());

      for (Ingredient ingredient : pRecipe.getIngredients()) {
        ingredient.toNetwork(pBuffer);
      }

      pBuffer.writeItemStack(pRecipe.getResultItem(null), false);
    }
  }
}
