package fr.zilba.endlesscraft.entity.custom;

import fr.zilba.endlesscraft.entity.ModEntities;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class ElectricArc extends Entity implements IEntityAdditionalSpawnData {

  private BlockPos source;
  private BlockPos target;
  private int bounceCount;
  private int age;
  public long seed;


  public ElectricArc(EntityType<?> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
    this.age = 0;
    this.bounceCount = 0;
    this.seed = this.random.nextLong();
  }

  public ElectricArc(Level pLevel, BlockPos source, BlockPos target, int bounceCount) {
    this(ModEntities.ELECTRIC_ARC.get(), pLevel);
    this.source = source;
    this.target = target;
    this.bounceCount = bounceCount;
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
          Mob.class, TargetingConditions.forNonCombat(), null, target.getX(), target.getY(),
          target.getZ(), AABB.ofSize(target.getCenter(), 3, 3, 3));
      if (nextTarget != null) {
        ElectricArc arc = new ElectricArc(level(), target, nextTarget.getOnPos(), bounceCount - 1);
        arc.moveTo(target.getX(), target.getY(), target.getZ());
        level().addFreshEntity(arc);
      }
    }
  }

  public boolean shouldRenderAtSqrDistance(double pDistance) {
    double d0 = 16.0D * getViewScale();
    return pDistance < d0 * d0;
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

  @Override
  public void writeSpawnData(FriendlyByteBuf buffer) {
    buffer.writeInt(age);
    buffer.writeInt(bounceCount);
    if (source != null && target != null) {
      buffer.writeBlockPos(source);
      buffer.writeBlockPos(target);
    }

  }

  @Override
  public void readSpawnData(FriendlyByteBuf additionalData) {
    age = additionalData.readInt();
    bounceCount = additionalData.readInt();
    source = additionalData.readBlockPos();
    target = additionalData.readBlockPos();
  }

  @Override
  public Packet<ClientGamePacketListener> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }

  public BlockPos getSource() {
    return source;
  }

  public BlockPos getTarget() {
    return target;
  }
}
