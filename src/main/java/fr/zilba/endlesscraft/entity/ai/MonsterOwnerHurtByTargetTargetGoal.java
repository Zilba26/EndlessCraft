package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class MonsterOwnerHurtByTargetTargetGoal extends TameMonsterTargetGoal {
  private LivingEntity ownerLastHurtBy;
  private int timestamp;
  public MonsterOwnerHurtByTargetTargetGoal(Monster pMonster, Player pOwner) {
    super(pMonster, false, pOwner);
  }

  @Override
  public boolean canUseAfterTamedCheck() {
    if (owner == null) {
      return false;
    } else {
      this.ownerLastHurtBy = owner.getLastHurtByMob();
      int i = owner.getLastHurtByMobTimestamp();
      return i != this.timestamp && this.canAttack(this.ownerLastHurtBy, TargetingConditions.DEFAULT);
    }
  }

  @Override
  public void start() {
    this.monster.setTarget(this.ownerLastHurtBy);
    if (owner != null) {
      this.timestamp = owner.getLastHurtByMobTimestamp();
    }

    super.start();
  }
}
