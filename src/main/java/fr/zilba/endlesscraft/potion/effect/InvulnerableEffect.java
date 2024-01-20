package fr.zilba.endlesscraft.potion.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class InvulnerableEffect extends MobEffect {
  public InvulnerableEffect(MobEffectCategory pCategory, int pColor) {
    super(pCategory, pColor);
  }

  @Override
  public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
    pLivingEntity.invulnerableTime = 20;
  }

  @Override
  public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
    return true;
  }
}
