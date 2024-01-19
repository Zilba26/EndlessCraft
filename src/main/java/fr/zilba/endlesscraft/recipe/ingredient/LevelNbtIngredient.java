package fr.zilba.endlesscraft.recipe.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.crafting.AbstractIngredient;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

import static net.minecraftforge.common.crafting.CraftingHelper.getItem;

public class LevelNbtIngredient extends AbstractIngredient {

  private final ItemStack stack;
  protected LevelNbtIngredient(ItemStack stack)
  {
    super(Stream.of(new Ingredient.ItemValue(stack)));
    this.stack = stack;
  }

  @Override
  public boolean isSimple() {
    return false;
  }

  @Override
  public IIngredientSerializer<? extends Ingredient> getSerializer() {
    return Serializer.INSTANCE;
  }

  @Override
  public boolean test(@Nullable ItemStack pStack) {
    if (pStack == null)
      return false;
    if (this.stack.getItem() != pStack.getItem())
      return false;
    return sameLevelTag(this.stack.getTag(), pStack.getTag());
  }

  private boolean sameLevelTag(CompoundTag tag1, CompoundTag tag2) {
    if (tag1 == null && tag2 == null)
      return true;
    if (tag1 == null || tag2 == null)
      return false;
    if (!tag1.contains(EndlessCraft.MOD_ID)) {
      if (!tag2.contains(EndlessCraft.MOD_ID))
        return true;
      CompoundTag modTag2 = tag2.getCompound(EndlessCraft.MOD_ID);
      if (!modTag2.contains("level"))
        return true;
      return modTag2.getInt("level") == 1 || modTag2.getInt("level") == 0;
    }

    CompoundTag modTag1 = tag1.getCompound(EndlessCraft.MOD_ID);
    CompoundTag modTag2 = tag2.getCompound(EndlessCraft.MOD_ID);
    if (!modTag1.contains("level")) {
      if (!modTag2.contains("level"))
        return true;
      return modTag2.getInt("level") == 1 || modTag2.getInt("level") == 0;
    }
    if (!modTag2.contains("level"))
      return modTag1.getInt("level") == 1 || modTag1.getInt("level") == 0;
    return modTag1.getInt("level") == modTag2.getInt("level");
  }

  @Override
  public JsonElement toJson() {
    JsonObject json = new JsonObject();
    json.addProperty("type", CraftingHelper.getID(Serializer.INSTANCE).toString());
    json.addProperty("item", ForgeRegistries.ITEMS.getKey(stack.getItem()).toString());
    json.addProperty("count", stack.getCount());
    if (stack.hasTag())
      json.addProperty("level", stack.getTag().toString());
    return json;
  }

  public static class Serializer implements IIngredientSerializer<LevelNbtIngredient>
  {
    public static final Serializer INSTANCE = new Serializer();
    public static final ResourceLocation NAME = new ResourceLocation(EndlessCraft.MOD_ID, "level_nbt");

    @Override
    public LevelNbtIngredient parse(FriendlyByteBuf buffer) {
      return new LevelNbtIngredient(buffer.readItem());
    }

    @Override
    public LevelNbtIngredient parse(JsonObject json) {

      System.out.println("\nparse LevelNbtIngredient");
      System.out.println(json);
      String itemName = GsonHelper.getAsString(json, "item");
      Item item = getItem(itemName, false);
      if (json.has("level")) {
        CompoundTag nbt = new CompoundTag();
        CompoundTag modTag = new CompoundTag();
        modTag.putInt("level", GsonHelper.getAsInt(json, "level", 1));
        nbt.put(EndlessCraft.MOD_ID, modTag);

        CompoundTag tmp = new CompoundTag();
        tmp.put("tag", nbt);
        tmp.putString("id", itemName);
        tmp.putInt("Count", GsonHelper.getAsInt(json, "count", 1));

        System.out.println(tmp);
        System.out.println(ItemStack.of(tmp));
        return new LevelNbtIngredient(ItemStack.of(tmp));
      }

      ItemStack stack = new ItemStack(item, GsonHelper.getAsInt(json, "count", 1));
      System.out.println(stack);
      return new LevelNbtIngredient(stack);
    }

    @Override
    public void write(FriendlyByteBuf buffer, LevelNbtIngredient ingredient) {
      buffer.writeItem(ingredient.stack);
    }
  }
}
