package fr.zilba.endlesscraft.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.entity.custom.TemporalArrow;
import net.minecraft.client.renderer.MultiBufferSource;
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
  public void render(TemporalArrow pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
    //super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
  }

  @Override
  public ResourceLocation getTextureLocation(TemporalArrow pEntity) {
    return new ResourceLocation(EndlessCraft.MOD_ID, "textures/entity/projectiles/arcane_gauntlet_projectile.png");
  }
}
