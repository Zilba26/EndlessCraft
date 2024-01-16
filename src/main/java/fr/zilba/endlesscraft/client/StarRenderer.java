package fr.zilba.endlesscraft.client;

import fr.zilba.endlesscraft.block.entity.StarEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
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
