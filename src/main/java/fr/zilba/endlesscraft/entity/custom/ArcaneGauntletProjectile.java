package fr.zilba.endlesscraft.entity.custom;

import fr.zilba.endlesscraft.entity.ModEntities;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import fr.zilba.endlesscraft.particle.custom.Color;
import fr.zilba.endlesscraft.particle.custom.arcane_gauntlet_projectile_particle.ArcaneGauntletProjectileParticleType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FrostedIceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.entity.IEntityAdditionalSpawnData;
import net.minecraftforge.network.NetworkHooks;

public class ArcaneGauntletProjectile extends AbstractArrow implements IEntityAdditionalSpawnData {

  private int age = 0;
  private ArcaneGauntletElement element;

  public ArcaneGauntletProjectile(Level pLevel, LivingEntity pShooter, ArcaneGauntletElement element) {
    super(ModEntities.ARCANE_GAUNTLET_PROJECTILE.get(), pShooter, pLevel);
    this.element = element;
    this.setNoGravity(true);
    super.setBaseDamage(0.0D);
  }

  public ArcaneGauntletProjectile(EntityType<ArcaneGauntletProjectile> entityType, Level level) {
    super(entityType, level);
  }

  @Override
  public void tick() {
    super.tick();
    if (this.age++ > 100 || this.inGround) {
      this.discard();
    }
    if (this.level().isClientSide && this.age > 5) {
      switch (this.element) {
        case FIRE:
          //this.playParticles(Color.from(255, 0, 0));
          //this.spawnParticles(ParticleTypes.FLAME);
          this.playParticles2(255, 0, 0);
          break;
        case ICE:
          //this.playParticles(Color.from(0, 255, 255));
          //this.spawnParticles(ParticleTypes.ITEM_SNOWBALL);
          this.playParticles2(0, 255, 255);
          break;
        case ELECTRIC:
          //this.playParticles(Color.from(255, 255, 0));
          //this.spawnParticles(ParticleTypes.FLASH);
          this.playParticles2(255, 255, 0);
          break;
        case WATER:
          //this.playParticles(Color.from(0, 0, 255));
          //this.spawnParticles(ParticleTypes.BUBBLE);
          this.playParticles2(0, 0, 255);
          break;
        default:
          break;
      }
    }
    if (this.level().getBlockState(this.blockPosition()) == Blocks.WATER.defaultBlockState() && this.element == ArcaneGauntletElement.ICE) {
      this.level().setBlock(this.blockPosition(), Blocks.FROSTED_ICE.defaultBlockState(), 11);
      this.discard();
    }
  }

  public void playParticles2(int red, int green, int blue) {
    double deltaX = getX() - xOld;
    double deltaY = getY() - yOld;
    double deltaZ = getZ() - zOld;
    double dist = Math.ceil(Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 10);
    for (double j = 0; j < dist; j++) {
      double coeff = j / dist;
      int colorRgb24 = (red << 16) | (green << 8) | blue;
      DustParticleOptions particle = new DustParticleOptions(
          Vec3.fromRGB24(colorRgb24).toVector3f(), 1.0F);
      this.level().addParticle(particle,
          (float) (xo + deltaX * coeff),
          (float) (yo + deltaY * coeff) + 0.1, (float)
              (zo + deltaZ * coeff),
          0.005f * (random.nextFloat() - 0.5f),
          0.005f * (random.nextFloat() - 0.5f),
          0.005f * (random.nextFloat() - 0.5f));
    }
  }

  @Override
  protected void onHitEntity(EntityHitResult pResult) {
    if (!this.level().isClientSide) {
      switch (this.element) {
        case FIRE:
          pResult.getEntity().setSecondsOnFire(5);
          this.hurtEntity(pResult.getEntity(), 5);
          this.discard();
          break;
        case ICE:
          if (pResult.getEntity() instanceof LivingEntity entity) {
            this.hurtEntity(entity, 2);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 100));
            this.discard();
          }
          break;
        case ELECTRIC:
          if (pResult.getEntity() instanceof LivingEntity source) {
            this.hurtEntity(source, 4);
            LivingEntity target = this.level().getNearestEntity(
                LivingEntity.class, // Type d'entité que vous recherchez
                TargetingConditions.forNonCombat(), // Conditions de ciblage
                source, // Entité source
                source.getX(), source.getY(), source.getZ(), // Coordonnées de l'entité source
                source.getBoundingBox().inflate(5, 5, 5) // Zone de recherche
            );
            System.out.println("Electric arc- " + source + " to " + target);
            ElectricArc arc = new ElectricArc(this.level(), source.blockPosition(), target.blockPosition(), 0);
            arc.moveTo(Vec3.atBottomCenterOf(pResult.getEntity().getOnPos()));
            this.level().addFreshEntity(arc);
          }
//          LightningBoltWithoutFire lightningBolt = ModEntities.LIGHTNING_BOLT_WITHOUT_FIRE.get().create(this.level());
//          if (lightningBolt != null) {
//            lightningBolt.moveTo(Vec3.atBottomCenterOf(pResult.getEntity().getOnPos()));
//            this.level().addFreshEntity(lightningBolt);
//          }
          this.discard();
          break;
        default:
          break;
      }
    }
  }

  private void hurtEntity(Entity entity, int damage) {
    Entity shooter = this.getOwner() == null ? this : this.getOwner();
    DamageSource source = this.damageSources().arrow(this, shooter);
    entity.hurt(source, damage);
  }

  @Override
  protected void onHitBlock(BlockHitResult pResult) {
    BlockPos relativePos = pResult.getBlockPos().relative(pResult.getDirection());
    switch (this.element) {
      case FIRE:
        BlockState blockState = BaseFireBlock.getState(this.level(), relativePos);
        if (this.level().getBlockState(relativePos).isAir()) {
          this.level().setBlock(relativePos, blockState, 11);
        }
        this.discard();
        break;
      case ICE:
        if (this.level().getBlockState(relativePos) == FrostedIceBlock.meltsInto()) {
          this.level().setBlock(relativePos, Blocks.FROSTED_ICE.defaultBlockState(), 11);
        }
        this.discard();
        break;
      case WATER:
        if (this.level().getBlockState(relativePos).isAir()) {
          this.level().setBlock(relativePos, Blocks.WATER.defaultBlockState(), 11);
        }
        this.discard();
        break;
      default:
        break;
    }
  }

  @Override
  protected ItemStack getPickupItem() {
    return null;
  }

  @Override
  protected float getWaterInertia() {
    return 1.0F;
  }

  @Override
  public double getBaseDamage() {
    return 0;
  }

  @Override
  public void writeSpawnData(FriendlyByteBuf buffer) {
    buffer.writeEnum(this.element);
    //if (this.getOwner() != null) buffer.writeUUID(this.getOwner().getUUID());
  }

  @Override
  public void readSpawnData(FriendlyByteBuf additionalData) {
    this.element = additionalData.readEnum(ArcaneGauntletElement.class);
    //this.setOwner(this.level().getPlayerByUUID(additionalData.readUUID()));
  }

  @Override
  public Packet<ClientGamePacketListener> getAddEntityPacket() {
    return NetworkHooks.getEntitySpawningPacket(this);
  }
}
