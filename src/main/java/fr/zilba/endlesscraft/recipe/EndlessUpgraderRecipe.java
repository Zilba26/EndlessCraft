package fr.zilba.endlesscraft.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public abstract class EndlessUpgraderRecipe implements Recipe<SimpleContainer> {

  protected final NonNullList<Ingredient> inputItems;
  private final ResourceLocation id;

  public EndlessUpgraderRecipe(NonNullList<Ingredient> inputItems, ResourceLocation id) {
    this.inputItems = inputItems;
    this.id = id;
  }

  public ItemStack getBase() {
    return inputItems.get(0).getItems()[0].copy();
  }

  public ItemStack getAddition() {
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
    return this.getResultByBase(this.getBase(), this.getAddition());
  }

  public abstract ItemStack getResultByBase(ItemStack base, ItemStack addition);

  @Override
  public ResourceLocation getId() {
    return id;
  }

  @Override
  public abstract RecipeSerializer<?> getSerializer();

  @Override
  public RecipeType<?> getType() {
    return Type.INSTANCE;
  }

  public static class Type implements RecipeType<EndlessUpgraderRecipe> {

    public static final Type INSTANCE = new Type();
    public static final String ID = "endless_upgrader.json";
  }

}
