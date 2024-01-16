package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public abstract class TameMonsterTargetGoal extends TargetGoal {

  protected final Player owner;
  protected final Monster monster;


  public TameMonsterTargetGoal(Monster monster, boolean pMustSee, Player pOwner) {
    super(monster, pMustSee);
    this.owner = pOwner;
    this.monster = monster;
    this.setFlags(EnumSet.of(Goal.Flag.TARGET));
  }

  @Override
  public boolean canUse() {
    if (owner == null || !monster.getPersistentData().contains("tamed"))
      return false;
    return canUseAfterTamedCheck();
  }

  abstract public boolean canUseAfterTamedCheck();
}
