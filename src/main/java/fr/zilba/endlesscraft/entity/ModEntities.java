package fr.zilba.endlesscraft.entity;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.entity.custom.ArcaneGauntletProjectile;
import fr.zilba.endlesscraft.entity.custom.ElectricArc;
import fr.zilba.endlesscraft.entity.custom.LightningBoltWithoutFire;
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

  public static final RegistryObject<EntityType<ArcaneGauntletProjectile>> ARCANE_GAUNTLET_PROJECTILE =
      ENTITY_TYPE.register("arcane_gauntlet_projectile",
          () -> EntityType.Builder.<ArcaneGauntletProjectile>of(ArcaneGauntletProjectile::new, MobCategory.MISC)
              .noSave().sized(0.5F, 0.5F).clientTrackingRange(4)
              .updateInterval(20).build("arcane_gauntlet_projectile"));

  public static final RegistryObject<EntityType<ElectricArc>> ELECTRIC_ARC =
      ENTITY_TYPE.register("electric_arc",
          () -> EntityType.Builder.<ElectricArc>of(ElectricArc::new, MobCategory.MISC)
              .sized(0.0F, 0.0F).clientTrackingRange(16)
              .updateInterval(Integer.MAX_VALUE).build("electric_arc"));

  public static final RegistryObject<EntityType<LightningBoltWithoutFire>> LIGHTNING_BOLT_WITHOUT_FIRE =
      ENTITY_TYPE.register("lightning_bolt_without_fire",
          () -> EntityType.Builder.<LightningBoltWithoutFire>of(LightningBoltWithoutFire::new, MobCategory.MISC)
              .noSave().sized(0.0F, 0.0F).clientTrackingRange(16)
              .updateInterval(Integer.MAX_VALUE).build("lightning_bolt_without_fire"));

  public static void register(IEventBus eventBus) {
    ENTITY_TYPE.register(eventBus);
  }
}
