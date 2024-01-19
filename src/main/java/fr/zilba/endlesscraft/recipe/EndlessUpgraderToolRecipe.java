package fr.zilba.endlesscraft.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.tools.EndlessCraftToolsItem;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgradeItem;
import fr.zilba.endlesscraft.item.custom.upgrade.infinity_sword.InfiniteDurabilityUpgrade;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.Nullable;

public class EndlessUpgraderToolRecipe extends EndlessUpgraderRecipe {
  public EndlessUpgraderToolRecipe(NonNullList<Ingredient> inputItems, ResourceLocation id) {
    super(inputItems, id);
  }

  public ItemStack getResultByBase(ItemStack tool, ItemStack upgrade) {
    ItemStack result = tool.copy();
    if (!(upgrade.getItem() instanceof EndlessCraftUpgradeItem
        && tool.getItem() instanceof EndlessCraftToolsItem)) {
      return ItemStack.EMPTY;
    }

    EndlessCraftUpgradeItem upgradeItem = (EndlessCraftUpgradeItem) upgrade.getItem();
    if (tool.hasTag() && tool.getTag().contains(EndlessCraft.MOD_ID)) {
      if (tool.getTag().getCompound(EndlessCraft.MOD_ID).contains(upgradeItem.getKeyName())) {
        if (tool.getTag().getCompound(EndlessCraft.MOD_ID).getInt(upgradeItem.getKeyName())
            >= EndlessCraftUpgrade.getLevel(upgrade)) {
          return ItemStack.EMPTY;
        }
      }
    }

    CompoundTag tag = result.getOrCreateTag();
    if (!tag.contains(EndlessCraft.MOD_ID)) {
      tag.put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    tag.getCompound(EndlessCraft.MOD_ID).putInt(upgradeItem.getKeyName(),
        EndlessCraftUpgrade.getLevel(upgrade));

    if (this.getAddition().getItem() instanceof InfiniteDurabilityUpgrade) {
      result.getTag().putBoolean("Unbreakable", true);
    }
    return result;
  }

  @Override
  public RecipeSerializer<?> getSerializer() {
    return Serializer.INSTANCE;
  }

  public static class Serializer implements RecipeSerializer<EndlessUpgraderToolRecipe> {

    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation ID = new ResourceLocation(EndlessCraft.MOD_ID, "endless_upgrader");

    @Override
    public EndlessUpgraderToolRecipe fromJson(ResourceLocation pRecipeId, JsonObject pSerializedRecipe) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

      inputs.set(0, Ingredient.fromJson(pSerializedRecipe.get("base")));
      inputs.set(1, Ingredient.fromJson(pSerializedRecipe.get("addition")));

      return new EndlessUpgraderToolRecipe(inputs, pRecipeId);
    }

    @Override
    public @Nullable EndlessUpgraderToolRecipe fromNetwork(ResourceLocation pRecipeId, FriendlyByteBuf pBuffer) {
      NonNullList<Ingredient> inputs = NonNullList.withSize(pBuffer.readInt(), Ingredient.EMPTY);

      inputs.replaceAll(ignored -> Ingredient.fromNetwork(pBuffer));

      return new EndlessUpgraderToolRecipe(inputs, pRecipeId);
    }

    @Override
    public void toNetwork(FriendlyByteBuf pBuffer, EndlessUpgraderToolRecipe pRecipe) {
      pBuffer.writeInt(pRecipe.inputItems.size());

      for (Ingredient ingredient : pRecipe.getIngredients()) {
        ingredient.toNetwork(pBuffer);
      }
    }
  }
}
