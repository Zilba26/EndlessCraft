package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class TameMonsterNearestAttackableTargetGoal extends NearestAttackableTargetGoal<LivingEntity> {

  private final Monster monster;
  private final Player owner;

  public TameMonsterNearestAttackableTargetGoal(Monster pMonster, Player pOwner) {
    super(pMonster, LivingEntity.class, true);
    this.monster = pMonster;
    this.owner = pOwner;
    this.targetConditions = TargetingConditions.forCombat().selector((pEntity) -> {
      if (!pMonster.getPersistentData().contains("tamed")) {
        return false;
      }
      if (pEntity.getUUID().equals(pOwner.getUUID())) {
        return false;
      }
      return pEntity instanceof Monster;
    });
  }

  @Override
  public boolean canUse() {
    if (owner == null || !monster.getPersistentData().contains("tamed")) {
      return false;
    }
    return super.canUse();
  }
}
