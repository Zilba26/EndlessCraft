package fr.zilba.endlesscraft.entity;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.entity.custom.TemporalArrow;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {

  public static final DeferredRegister<EntityType<?>> ENTITY_TYPE =
      DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EndlessCraft.MOD_ID);

  public static final RegistryObject<EntityType<TemporalArrow>> TEMPORAL_ARROW =
      ENTITY_TYPE.register("temporal_arrow",
          () -> EntityType.Builder.<TemporalArrow>of(TemporalArrow::new, MobCategory.MISC)
              .sized(0.5F, 0.5F).clientTrackingRange(4)
              .updateInterval(20).build("temporal_arrow"));

  public static void register(IEventBus eventBus) {
    ENTITY_TYPE.register(eventBus);
  }
}
