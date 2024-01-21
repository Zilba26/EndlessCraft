package fr.zilba.endlesscraft.entity.custom;

import fr.zilba.endlesscraft.entity.ModEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class TemporalArrow extends AbstractArrow {
  public TemporalArrow(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
  }

  public TemporalArrow(Level pLevel, double pX, double pY, double pZ) {
    super(ModEntities.TEMPORAL_ARROW.get(), pX, pY, pZ, pLevel);
  }

  public TemporalArrow(Level pLevel, LivingEntity pShooter) {
    super(ModEntities.TEMPORAL_ARROW.get(), pShooter, pLevel);
  }

  @Override
  protected void onHit(HitResult pResult) {
    if (!this.level().isClientSide()) {
      Vec3 hitPos = pResult.getLocation();
      List<Entity> entities = this.level().getEntities(this,
          this.getBoundingBox().inflate(3.0D), (entity -> entity instanceof LivingEntity));
      for (int x = -3; x <= 3; x++) {
        for (int z = -1; z <= 1; z++) {
          //add particles
          this.level().addFreshEntity(new TemporalArrow(this.level(), hitPos.x() + x, hitPos.y(), hitPos.z() + z));
        }
      }
    }
    super.onHit(pResult);
  }

  @Override
  public ItemStack getPickupItem() {
    return new ItemStack(Items.ARROW);
  }
}
