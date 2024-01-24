package fr.zilba.endlesscraft.item;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {
  INFINITY("infinity", 50, new int[]{4, 9, 7, 4}, 15,
      SoundEvents.ARMOR_EQUIP_NETHERITE, 5.0F, 0.5F,
      () -> Ingredient.of(Items.NETHER_STAR));

  private final String name;
  private final int durabilityMultiplier;
  private final int[] protectionAmounts;
  private final int enchantmentValue;
  private final SoundEvent equipSound;
  private final float toughness;
  private final float knockbackResistance;
  private final Supplier<Ingredient> repairIngredient;

  private static final int[] BASE_DURABILITY = new int[]{11, 16, 16, 13};

  ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
    this.name = name;
    this.durabilityMultiplier = durabilityMultiplier;
    this.protectionAmounts = protectionAmounts;
    this.enchantmentValue = enchantmentValue;
    this.equipSound = equipSound;
    this.toughness = toughness;
    this.knockbackResistance = knockbackResistance;
    this.repairIngredient = repairIngredient;
  }

  @Override
  public int getDurabilityForType(ArmorItem.Type pType) {
    return BASE_DURABILITY[pType.ordinal()] * this.durabilityMultiplier;
  }

  @Override
  public int getDefenseForType(ArmorItem.Type pType) {
    return this.protectionAmounts[pType.ordinal()];
  }

  @Override
  public int getEnchantmentValue() {
    return this.enchantmentValue;
  }

  @Override
  public SoundEvent getEquipSound() {
    return this.equipSound;
  }

  @Override
  public Ingredient getRepairIngredient() {
    return this.repairIngredient.get();
  }

  @Override
  public String getName() {
    return EndlessCraft.MOD_ID + ":" + this.name;
  }

  @Override
  public float getToughness() {
    return this.toughness;
  }

  @Override
  public float getKnockbackResistance() {
    return this.knockbackResistance;
  }
}
