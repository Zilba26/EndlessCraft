package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

public class TameMonsterResetTargetTargetGoal extends TameMonsterTargetGoal {

  public TameMonsterResetTargetTargetGoal(Monster pMonster, Player pOwner) {
    super(pMonster, true, pOwner);
  }

  @Override
  public boolean canUseAfterTamedCheck() {
    return true;
  }

  @Override
  public void start() {
    this.monster.setTarget(null);
  }
}
