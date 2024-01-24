package fr.zilba.endlesscraft.datagen;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
  public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
    super(output, EndlessCraft.MOD_ID, exFileHelper);
  }

  @Override
  protected void registerStatesAndModels() {
    //blockWithItem(ModBlocks.ENDLESS_UPGRADER);
    blockWithItem(ModBlocks.METEORITE);
    //blockWithItem(ModBlocks.STAR);
  }

  private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
    simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
  }
}
