package fr.zilba.endlesscraft.particle;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.particle.custom.arcane_gauntlet_projectile_particle.ArcaneGauntletProjectileParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModParticles {

  public static final DeferredRegister<ParticleType<?>> PARTICLES_TYPES =
      DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, EndlessCraft.MOD_ID);

  public static final RegistryObject<ArcaneGauntletProjectileParticleType> ARCANE_GAUNTLET_PROJECTILE_PARTICLE =
      PARTICLES_TYPES.register("arcane_gauntlet_projectile",
          ArcaneGauntletProjectileParticleType::new);

  public static void register(IEventBus eventBus) {
    PARTICLES_TYPES.register(eventBus);
  }
}
