package fr.zilba.endlesscraft.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

import java.util.EnumSet;

public class TameMonsterFollowOwnerGoal extends Goal {

  private final Monster monster;
  private final LivingEntity owner;
  private final LevelReader level;
  private final PathNavigation navigation;
  private int timeToRecalcPath;
  private final float stopDistance;
  private final float startDistance;
  private float oldWaterCost;

  public TameMonsterFollowOwnerGoal(Monster pMonster, float pStartDistance, float pStopDistance, Player owner) {
    this.monster = pMonster;
    this.level = pMonster.level();
    this.navigation = pMonster.getNavigation();
    this.startDistance = pStartDistance;
    this.stopDistance = pStopDistance;
    this.owner = owner;
    this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    if (!(pMonster.getNavigation() instanceof GroundPathNavigation) && !(pMonster.getNavigation() instanceof FlyingPathNavigation)) {
      throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
    }
  }

  /**
   * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
   * method as well.
   */
  public boolean canUse() {
    if (monster.getPersistentData().contains("tamed")) {
      return false;
    } else if (owner.isSpectator()) {
      return false;
    } else if (this.unableToMove()) {
      return false;
    } else if (this.monster.distanceToSqr(owner) < (this.startDistance * this.startDistance)) {
      return false;
    } else {
      return true;
    }
  }

  /**
   * Returns whether an in-progress EntityAIBase should continue executing
   */
  public boolean canContinueToUse() {
    if (this.navigation.isDone()) {
      return false;
    } else if (this.unableToMove()) {
      return false;
    } else {
      return !(this.monster.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
    }
  }

  private boolean unableToMove() {
    return this.monster.isPassenger() || this.monster.isLeashed();
  }

  /**
   * Execute a one shot task or start executing a continuous task
   */
  public void start() {
    this.timeToRecalcPath = 0;
    this.oldWaterCost = this.monster.getPathfindingMalus(BlockPathTypes.WATER);
    this.monster.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
  }

  /**
   * Reset the task's internal state. Called when this task is interrupted by another one
   */
  public void stop() {
    this.navigation.stop();
    this.monster.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
  }

  /**
   * Keep ticking a continuous task that has already been started
   */
  public void tick() {
    this.monster.getLookControl().setLookAt(this.owner, 10.0F, (float)this.monster.getMaxHeadXRot());
    if (--this.timeToRecalcPath <= 0) {
      this.timeToRecalcPath = this.adjustedTickDelay(10);
      if (this.monster.distanceToSqr(this.owner) >= 196.0D) {
        this.teleportToOwner();
      } else {
        this.navigation.moveTo(this.owner, 1.0D);
      }
    }
  }

  private void teleportToOwner() {
    BlockPos blockpos = this.owner.blockPosition();

    for(int i = 0; i < 10; ++i) {
      int j = this.randomIntInclusive(-3, 3);
      int k = this.randomIntInclusive(-1, 1);
      int l = this.randomIntInclusive(-3, 3);
      boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
      if (flag) {
        return;
      }
    }

  }

  private boolean maybeTeleportTo(int pX, int pY, int pZ) {
    if (Math.abs((double)pX - this.owner.getX()) < 2.0D && Math.abs((double)pZ - this.owner.getZ()) < 2.0D) {
      return false;
    } else if (!this.canTeleportTo(new BlockPos(pX, pY, pZ))) {
      return false;
    } else {
      this.monster.moveTo(pX + 0.5D, pY, pZ + 0.5D, this.monster.getYRot(), this.monster.getXRot());
      this.navigation.stop();
      return true;
    }
  }

  private boolean canTeleportTo(BlockPos pPos) {
    BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pPos.mutable());
    if (blockpathtypes != BlockPathTypes.WALKABLE) {
      return false;
    } else {
      BlockState blockstate = this.level.getBlockState(pPos.below());
      if (blockstate.getBlock() instanceof LeavesBlock) {
        return false;
      } else {
        BlockPos blockpos = pPos.subtract(this.monster.blockPosition());
        return this.level.noCollision(this.monster, this.monster.getBoundingBox().move(blockpos));
      }
    }
  }

  private int randomIntInclusive(int pMin, int pMax) {
    return this.monster.getRandom().nextInt(pMax - pMin + 1) + pMin;
  }
}