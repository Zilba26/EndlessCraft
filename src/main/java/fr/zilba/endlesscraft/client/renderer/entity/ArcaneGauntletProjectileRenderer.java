package fr.zilba.endlesscraft.client.renderer.entity;

import fr.zilba.endlesscraft.entity.custom.ArcaneGauntletProjectile;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ArcaneGauntletProjectileRenderer extends EntityRenderer<ArcaneGauntletProjectile> {
  public ArcaneGauntletProjectileRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public ResourceLocation getTextureLocation(ArcaneGauntletProjectile pEntity) {
    return new ResourceLocation("textures/entity/projectiles/arrow.png");
  }
}
