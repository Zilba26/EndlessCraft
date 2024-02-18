package fr.zilba.endlesscraft.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Axis;
import fr.zilba.endlesscraft.entity.custom.ElectricArc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.fluids.FluidStack;

@OnlyIn(Dist.CLIENT)
public class ElectricArcRenderer extends EntityRenderer<ElectricArc> {
  public ElectricArcRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(ElectricArc pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    Level level = pEntity.level();
    BlockPos pos = pEntity.getOnPos();

    IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(Fluids.WATER);
    ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture();
    if (stillTexture == null)
      return;

    FluidState state = Fluids.WATER.defaultFluidState();

    TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(stillTexture);
    int tintColor = fluidTypeExtensions.getTintColor(state, level, pos);

    float height = 0.5f;

    VertexConsumer builder = pBuffer.getBuffer(ItemBlockRenderTypes.getRenderLayer(state));

    drawQuad(builder, pPoseStack, 0.25f, height, 0.25f, 0.75f, height, 0.75f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);

    drawQuad(builder, pPoseStack, 0.25f, 0, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);

    pPoseStack.pushPose();
    pPoseStack.mulPose(Axis.YP.rotationDegrees(180));
    pPoseStack.translate(-1f, 0, -1.5f);
    drawQuad(builder, pPoseStack, 0.25f, 0, 0.75f, 0.75f, height, 0.75f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
    pPoseStack.popPose();

    pPoseStack.pushPose();
    pPoseStack.mulPose(Axis.YP.rotationDegrees(90));
    pPoseStack.translate(-1f, 0, 0);
    drawQuad(builder, pPoseStack, 0.25f, 0, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
    pPoseStack.popPose();

    pPoseStack.pushPose();
    pPoseStack.mulPose(Axis.YN.rotationDegrees(90));
    pPoseStack.translate(0, 0, -1f);
    drawQuad(builder, pPoseStack, 0.25f, 0, 0.25f, 0.75f, height, 0.25f, sprite.getU0(), sprite.getV0(), sprite.getU1(), sprite.getV1(), pPackedLight, tintColor);
    pPoseStack.popPose();
  }

  private static void drawVertex(VertexConsumer builder, PoseStack poseStack, float x, float y, float z, float u, float v, int packedLight, int color) {
    builder.vertex(poseStack.last().pose(), x, y, z)
        .color(color)
        .uv(u, v)
        .uv2(packedLight)
        .normal(1, 0, 0)
        .endVertex();
  }

  private static void drawQuad(VertexConsumer builder, PoseStack poseStack, float x0, float y0, float z0, float x1, float y1, float z1, float u0, float v0, float u1, float v1, int packedLight, int color) {
    drawVertex(builder, poseStack, x0, y0, z0, u0, v0, packedLight, color);
    drawVertex(builder, poseStack, x0, y1, z1, u0, v1, packedLight, color);
    drawVertex(builder, poseStack, x1, y1, z1, u1, v1, packedLight, color);
    drawVertex(builder, poseStack, x1, y0, z0, u1, v0, packedLight, color);
  }

  //  @Override
//  public void render(ElectricArc pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
//    LivingEntity source = pEntity.getSource();
//    LivingEntity target = pEntity.getTarget();
//    System.out.println("Rendering electric arc- " + source + " to " + target);
//
//    if (source != null && target != null) {
//      System.out.println(pEntityYaw + "-" + pPartialTick + "-" + pPackedLight);
//      Vec3 sourcePos = source.position();
//      Vec3 targetPos = target.position();
//
//      RenderType renderType = RenderType.create("electric_arc", DefaultVertexFormat.POSITION_COLOR,
//          VertexFormat.Mode.LINES, 256, false, true,
//          RenderType.CompositeState.builder()
//              .createCompositeState(false));
//      VertexConsumer vertexConsumer = pBuffer.getBuffer(renderType);
//      vertexConsumer.vertex(pPoseStack.last().pose(), (float) sourcePos.x, (float) sourcePos.y, (float) sourcePos.z)
//          .color(255, 255, 0, 255)
//          .endVertex();
//      vertexConsumer.vertex(pPoseStack.last().pose(), (float) targetPos.x, (float) targetPos.y, (float) targetPos.z)
//          .color(255, 255, 0, 255)
//          .endVertex();
//    }
//  }

  @Override
  public ResourceLocation getTextureLocation(ElectricArc pEntity) {
    return null;
  }
}
