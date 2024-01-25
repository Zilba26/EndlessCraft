package fr.zilba.endlesscraft.block;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.custom.EndlessUpgrader;
import fr.zilba.endlesscraft.block.custom.Star;
import fr.zilba.endlesscraft.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

  public static final DeferredRegister<Block> BLOCKS =
      DeferredRegister.create(ForgeRegistries.BLOCKS, EndlessCraft.MOD_ID);

  public static final RegistryObject<Block> ENDLESS_UPGRADER = registerBlock("endless_upgrader",
      () -> new EndlessUpgrader(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

  public static final RegistryObject<Block> METEORITE = registerBlock("meteorite",
      () -> new Block(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE)));

  public static final RegistryObject<Block> STAR = BLOCKS.register("star",
      () -> new Star(BlockBehaviour.Properties.copy(Blocks.BLACKSTONE).noOcclusion()
          .noParticlesOnBreak().strength(-1.0F, 3600000.0F)));

  private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
    RegistryObject<T> toReturn = BLOCKS.register(name, block);
    registryBlockItems(name, toReturn);
    return toReturn;
  }

  private static <T extends Block> RegistryObject<Item> registryBlockItems(String name, RegistryObject<T> block) {
    return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
  }

  public static void register(IEventBus eventBus) {
    BLOCKS.register(eventBus);
  }
}
