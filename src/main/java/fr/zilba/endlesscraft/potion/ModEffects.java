package fr.zilba.endlesscraft.potion;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.potion.effect.InvulnerableEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

  public static final DeferredRegister<MobEffect> MOB_EFFECT
      = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, EndlessCraft.MOD_ID);

  public static final RegistryObject<MobEffect> INVULNERABLE_EFFECT = MOB_EFFECT.register("invulnerable",
      () -> new InvulnerableEffect(MobEffectCategory.BENEFICIAL, 0x000000));

  public static void register(IEventBus eventBus) {
    MOB_EFFECT.register(eventBus);
  }
}
