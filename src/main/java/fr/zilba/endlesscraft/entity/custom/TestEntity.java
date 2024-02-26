package fr.zilba.endlesscraft.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class TestEntity extends Entity {

  public long seed;

  public TestEntity(EntityType<?> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
    this.seed = this.random.nextLong();
  }

  public boolean shouldRenderAtSqrDistance(double pDistance) {
    return true;
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
}
