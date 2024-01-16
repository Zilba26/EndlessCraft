package fr.zilba.endlesscraft.structure;

import com.mojang.serialization.Codec;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.structure.custom.MeteoriteStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

  public static final DeferredRegister<StructureType<?>> STRUCTURES =
      DeferredRegister.create(Registries.STRUCTURE_TYPE, EndlessCraft.MOD_ID);

  public static final RegistryObject<StructureType<MeteoriteStructure>> METEORITE_STRUCTURE =
      STRUCTURES.register("meteorite_structure", () -> explicitStructureTypeTyping(MeteoriteStructure.CODEC));

  private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(Codec<T> structureCodec) {
    return () -> structureCodec;
  }

  public static void register(IEventBus eventBus) {
    STRUCTURES.register(eventBus);
  }
}
