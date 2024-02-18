package fr.zilba.endlesscraft.client.renderer.block;

import fr.zilba.endlesscraft.block.entity.StarEntity;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class StarRenderer extends GeoBlockRenderer<StarEntity> {
  public StarRenderer() {
    super(new StarModel());
  }

//  @Override
//  public RenderType getRenderType(StarEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
//    return RenderType.entityTranslucent(getTextureLocation(animatable));
//  }
}
