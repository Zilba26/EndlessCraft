package fr.zilba.endlesscraft.datagen;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class ModCuriosDataProvider extends CuriosDataProvider {
  public ModCuriosDataProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
    super(EndlessCraft.MOD_ID, output, fileHelper, registries);
  }

  @Override
  public void generate(HolderLookup.Provider provider, ExistingFileHelper existingFileHelper) {
    this.createSlot("infinity_armor_upgrades");

    this.createEntities("infinity_armor_upgrades_player")
        .addPlayer()
        .addSlots("infinity_armor_upgrades");
  }
}
