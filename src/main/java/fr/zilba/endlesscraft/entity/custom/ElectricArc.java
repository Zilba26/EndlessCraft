package fr.zilba.endlesscraft.entity.custom;

import fr.zilba.endlesscraft.entity.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

public class ElectricArc extends Entity {

  private LivingEntity source;
  private LivingEntity target;
  private int bounceCount;
  private int age;

  public ElectricArc(EntityType<?> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
  }

  public ElectricArc(Level pLevel, LivingEntity source, LivingEntity target, int bounceCount) {
    super(ModEntities.ELECTRIC_ARC.get(), pLevel);
    this.source = source;
    this.target = target;
    this.bounceCount = bounceCount;
    this.age = 0;
  }

  @Override
  public void tick() {
    super.tick();
    age++;
    if (age > 200) {
      discard();
    }
    if (age == 50 && bounceCount > 0) {
      LivingEntity nextTarget = level().getNearestEntity(
          Mob.class, TargetingConditions.forNonCombat(), target, target.getX(), target.getY(),
          target.getZ(), target.getBoundingBox().inflate(5, 5, 5));
      System.out.println("nextTarget");
      System.out.println(nextTarget);
      if (nextTarget != null) {
        ElectricArc arc = new ElectricArc(level(), target, nextTarget, bounceCount - 1);
        level().addFreshEntity(arc);
      }
    }
  }

  @Override
  protected void defineSynchedData() {
  }

  @Override
  protected void readAdditionalSaveData(CompoundTag pCompound) {
  }

  @Override
  protected void addAdditionalSaveData(CompoundTag pCompound) {
  }

  public LivingEntity getSource() {
    return source;
  }

  public LivingEntity getTarget() {
    return target;
  }
}
