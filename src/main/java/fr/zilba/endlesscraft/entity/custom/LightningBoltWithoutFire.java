package fr.zilba.endlesscraft.entity.custom;

import com.google.common.collect.Sets;
import fr.zilba.endlesscraft.entity.ModEntities;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class LightningBoltWithoutFire extends LightningBolt {
  private int life;
  private ServerPlayer cause;
  private final Set<Entity> hitEntities = Sets.newHashSet();

  public LightningBoltWithoutFire(EntityType<? extends LightningBolt> pEntityType, Level pLevel) {
    super(pEntityType, pLevel);
    super.setVisualOnly(true);
  }

  @Override
  public void tick() {
    super.tick();
    if (this.life >= 0 && !this.level().isClientSide) {
      List<Entity> list1 = this.level().getEntities(this, new AABB(this.getX() - 3.0D, this.getY() - 3.0D, this.getZ() - 3.0D, this.getX() + 3.0D, this.getY() + 6.0D + 3.0D, this.getZ() + 3.0D), Entity::isAlive);

      for(Entity entity : list1) {
        if (!net.minecraftforge.event.ForgeEventFactory.onEntityStruckByLightning(entity, this))
          entity.thunderHit((ServerLevel)this.level(), this);
      }

      this.hitEntities.addAll(list1);
      if (this.cause != null) {
        CriteriaTriggers.CHANNELED_LIGHTNING.trigger(this.cause, list1);
      }
    }
  }

  @Override
  public Stream<Entity> getHitEntities() {
    return this.hitEntities.stream().filter(Entity::isAlive);
  }

  @Override
  public void setCause(ServerPlayer cause) {
    this.cause = cause;
    super.setCause(cause);
  }
}
