package fr.zilba.endlesscraft.client.renderer;

import fr.zilba.endlesscraft.entity.custom.TemporalArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class TemporalArrowRenderer extends ArrowRenderer<TemporalArrow> {

  public TemporalArrowRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public ResourceLocation getTextureLocation(TemporalArrow pEntity) {
    return new ResourceLocation("textures/entity/projectiles/arrow.png");
  }
}
