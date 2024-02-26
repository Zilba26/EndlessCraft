package fr.zilba.endlesscraft.client.renderer.entity;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import fr.zilba.endlesscraft.entity.custom.ElectricArc;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class ElectricArcRenderer extends EntityRenderer<ElectricArc> {
  RenderStateShard.ShaderStateShard RENDERTYPE_LIGHTNING_SHADER =
      new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeLightningShader);
  RenderStateShard.WriteMaskStateShard COLOR_DEPTH_WRITE =
      new RenderStateShard.WriteMaskStateShard(true, true);
  RenderStateShard.OutputStateShard WEATHER_TARGET = new RenderStateShard.OutputStateShard("weather_target", () -> {
    if (Minecraft.useShaderTransparency()) {
      Minecraft.getInstance().levelRenderer.getWeatherTarget().bindWrite(false);
    }

  }, () -> {
    if (Minecraft.useShaderTransparency()) {
      Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
    }
  });


  RenderType LIGHTNING = RenderType.create("electric_arc",
      DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS,
      256, false, true,
      RenderType.CompositeState.builder()
          .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
          .setWriteMaskState(COLOR_DEPTH_WRITE)
          .setOutputState(WEATHER_TARGET)
          .createCompositeState(false));

  public ElectricArcRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(ElectricArc pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    VertexConsumer vertexconsumer = pBuffer.getBuffer(LIGHTNING);
    Matrix4f matrix4f = pPoseStack.last().pose();
    BlockPos target = pEntity.getTarget();

    basicQuad(matrix4f, vertexconsumer, 0, 0, 0, (float) (target.getX() - pEntity.getX()),
        (float) (target.getY() - pEntity.getY()), (float) (target.getZ() - pEntity.getZ()));
  }

  public void render2(ElectricArc pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    if (pEntity.getTarget() != null && pEntity.getSource() != null) {
      BlockPos target = pEntity.getTarget();

      float[] afloat = new float[8];
      float[] afloat1 = new float[8];
      float f = 0.0F;
      float f1 = 0.0F;
      RandomSource randomsource = RandomSource.create(pEntity.seed);

      for(int i = 7; i >= 0; --i) {
        afloat[i] = f;
        afloat1[i] = f1;
        f += (float)(randomsource.nextInt(11) - 5);
        f1 += (float)(randomsource.nextInt(11) - 5);
      }

      VertexConsumer vertexconsumer = pBuffer.getBuffer(LIGHTNING);
      Matrix4f matrix4f = pPoseStack.last().pose();

      int j = 0;
      RandomSource randomsource1 = RandomSource.create(pEntity.seed);

      int l = 7;
      int i1 = 0;

      float f2 = afloat[l] - f;
      float f3 = afloat1[l] - f1;

      for(int j1 = l; j1 >= i1; --j1) {
        float f4 = f2;
        float f5 = f3;
        f2 += (float) (randomsource1.nextInt(11) - 5);
        f3 += (float)(randomsource1.nextInt(11) - 5);

        float f10 = 0.1F + (float)j * 0.2F;
        f10 *= (float) j1 * 0.1F + 1.0F;

        float f11 = 0.1F + (float)j * 0.2F;
        f11 *= ((float) j1 - 1.0F) * 0.1F + 1.0F;

        quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, f10, f11, false, false, true, false);
        quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, f10, f11, true, false, true, true);
        quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, f10, f11, true, true, false, true);
        quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, f10, f11, false, true, false, false);
      }
    }
  }

  private static void quad(Matrix4f pMatrix, VertexConsumer pConsumer, float x1, float z1, int y, float x2, float z2, float o1, float o2, boolean x1b, boolean z1b, boolean x2b, boolean z2b) {
    float red = 1F;
    float green = 1F;
    float blue = 0F;
    float alpha = 1F;
    pConsumer.vertex(pMatrix, x1 + (x1b ? o2 : -o2), y * 16, z1 + (z1b ? o2 : -o2)).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x2 + (x1b ? o1 : -o1), (y + 1) * 16, z2 + (z1b ? o1 : -o1)).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x2 + (x2b ? o1 : -o1), (y + 1) * 16, z2 + (z2b ? o1 : -o1)).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x1 + (x2b ? o2 : -o2), y * 16, z1 + (z2b ? o2 : -o2)).color(red, green, blue, alpha).endVertex();
  }

  private static void basicQuad(Matrix4f pMatrix, VertexConsumer pConsumer, float x1, float y1, float z1, float x2, float y2, float z2) {
    float red = 1F;
    float green = 1F;
    float blue = 0F;
    float alpha = 1F;
    pConsumer.vertex(pMatrix, x1, y1, z1).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x1, y2, z2).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x2, y2, z2).color(red, green, blue, alpha).endVertex();
    pConsumer.vertex(pMatrix, x2, y1, z1).color(red, green, blue, alpha).endVertex();
  }

  @Override
  public ResourceLocation getTextureLocation(ElectricArc pEntity) {
    return TextureAtlas.LOCATION_BLOCKS;
  }
}
