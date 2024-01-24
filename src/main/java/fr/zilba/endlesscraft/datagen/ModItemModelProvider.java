package fr.zilba.endlesscraft.datagen;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
  public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
    super(output, EndlessCraft.MOD_ID, existingFileHelper);
  }

  @Override
  protected void registerModels() {
    simpleItem(ModItems.CONTROL_STICK);
    simpleItem(ModItems.INFINITY_ARMOR);
    simpleItem(ModItems.INFINITY_BOOTS);
    simpleItem(ModItems.INFINITY_CHESTPLATE);
    simpleItem(ModItems.INFINITY_HELMET);
    simpleItem(ModItems.INFINITY_LEGGINGS);
    simpleItem(ModItems.INFINITY_SWORD);
    simpleItem(ModItems.ARMY_UPGRADE);
    simpleItem(ModItems.HEALTH_UPGRADE);
    simpleItem(ModItems.FIRE_RESISTANCE_UPGRADE);
    simpleItem(ModItems.LIFE_STEAL_UPGRADE);
    simpleItem(ModItems.PROTECTION_UPGRADE);
    simpleItem(ModItems.INFINITE_DURABILITY_UPGRADE);
    simpleItem(ModItems.NIGHT_VISION_UPGRADE);
    simpleItem(ModItems.FLY_UPGRADE);
    simpleItem(ModItems.STAR_FRAGMENT);
  }

  private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
    return withExistingParent(item.getId().getPath(),
        new ResourceLocation("item/generated")).texture("layer0",
        new ResourceLocation(EndlessCraft.MOD_ID, "item/" + item.getId().getPath()));
  }
}
