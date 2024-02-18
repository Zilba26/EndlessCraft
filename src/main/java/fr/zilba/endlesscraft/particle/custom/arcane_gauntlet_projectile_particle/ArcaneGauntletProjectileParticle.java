package fr.zilba.endlesscraft.particle.custom.arcane_gauntlet_projectile_particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ArcaneGauntletProjectileParticle extends TextureSheetParticle {

  private float initAlpha;
  private final SpriteSet sprites;


  protected ArcaneGauntletProjectileParticle(ClientLevel pLevel, double pX, double pY, double pZ,
                                             float r, float g, float b, SpriteSet sprites,
                                             double pXSpeed, double pYSpeed, double pZSpeed) {
    super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);
    this.hasPhysics = false;
    this.xd = pXSpeed;
    this.yd = pYSpeed;
    this.zd = pZSpeed;
    this.sprites = sprites;
    this.lifetime = 100;
    this.quadSize = this.random.nextFloat() * 0.15F + 0.1F;

    this.rCol = r;
    this.gCol = g;
    this.bCol = b;
    this.alpha = 0.11f;
    this.initAlpha = (float) (this.random.nextInt(100) + 155)/255;
    this.setSpriteFromAge(sprites);
  }

  @Override
  public void tick() {
    super.tick();
    //this.setSpriteFromAge(this.sprites);
    // Fade out
    float lifeCoeff = (float) this.age / (float) this.lifetime;
    this.alpha = 1f;
  }

  @Override
  public ParticleRenderType getRenderType() {
    return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
  }

  @OnlyIn(Dist.CLIENT)
  public static class Provider implements ParticleProvider<ArcaneGauntletProjectileParticleType> {
    private final SpriteSet spriteSet;

    public Provider(SpriteSet spriteSet) {
      this.spriteSet = spriteSet;
    }

    @Override
    public Particle createParticle(ArcaneGauntletProjectileParticleType data, ClientLevel level,
                                   double x, double y, double z,
                                   double xSpeed, double ySpeed, double zSpeed) {
      return new ArcaneGauntletProjectileParticle(level, x, y, z,
          data.color.red, data.color.green, data.color.blue, spriteSet, xSpeed, ySpeed, zSpeed);
    }
  }
}
