package fr.zilba.endlesscraft.potion;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModPotions {

  public static final DeferredRegister<Potion> POTIONS
      = DeferredRegister.create(ForgeRegistries.POTIONS, EndlessCraft.MOD_ID);

  public static final RegistryObject<Potion> ECLIPSE_POTION
      = POTIONS.register("eclipse_potion",
      () -> new Potion(new MobEffectInstance(ModEffects.INVULNERABLE_EFFECT.get(), 3600)));

  public static void register(IEventBus eventBus) {
    POTIONS.register(eventBus);
  }
}
