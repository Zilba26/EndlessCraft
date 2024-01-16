package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class MonsterOwnerHurtTargetTargetGoal extends TameMonsterTargetGoal {
  private LivingEntity ownerLastHurt;
  private int timestamp;
  public MonsterOwnerHurtTargetTargetGoal(Monster pMonster, Player pOwner) {
    super(pMonster, false, pOwner);
  }

  @Override
  public boolean canUseAfterTamedCheck() {
    if (owner == null) {
      return false;
    } else {
      this.ownerLastHurt = owner.getLastHurtMob();
      int i = owner.getLastHurtMobTimestamp();
      return i != this.timestamp && this.canAttack(this.ownerLastHurt, TargetingConditions.DEFAULT);
    }
  }

  @Override
  public void start() {
    this.monster.setTarget(this.ownerLastHurt);
    if (owner != null) {
      this.timestamp = owner.getLastHurtByMobTimestamp();
    }

    super.start();
  }
}
