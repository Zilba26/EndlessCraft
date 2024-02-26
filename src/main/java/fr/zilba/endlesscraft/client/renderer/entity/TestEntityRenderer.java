package fr.zilba.endlesscraft.client.renderer.entity;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import fr.zilba.endlesscraft.entity.custom.TestEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.platform.GlStateManager;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class TestEntityRenderer extends EntityRenderer<TestEntity> {
  public TestEntityRenderer(EntityRendererProvider.Context pContext) {
    super(pContext);
  }

  @Override
  public void render(TestEntity pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
    //this.renderLighning(pEntity, pPoseStack, pBuffer);
    VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.lightning());
    Matrix4f matrix4f = pPoseStack.last().pose();
    //TextureAtlasSprite sprite = Minecraft.getInstance().getTextureAtlas(TextureAtlas.LOCATION_BLOCKS).apply(TextureAtlas.LOCATION_BLOCKS);
    //quad3(matrix4f, vertexconsumer, 1, 1, 3, 1, 1, 0.45F, 0.45F, 0.5F, 3, 3);
    renderLine(pPoseStack, pEntity);
  }

  public void renderLighning(TestEntity pEntity, PoseStack pMatrixStack, MultiBufferSource pBuffer) {
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

    VertexConsumer vertexconsumer = pBuffer.getBuffer(RenderType.lightning());
    Matrix4f matrix4f = pMatrixStack.last().pose();

    for(int j = 0; j < 4; ++j) {
      RandomSource randomsource1 = RandomSource.create(pEntity.seed);

      for(int k = 0; k < 3; ++k) {
        int l = 7;
        int i1 = 0;
        if (k > 0) {
          l = 7 - k;
        }

        if (k > 0) {
          i1 = l - 2;
        }

        float f2 = afloat[l] - f;
        float f3 = afloat1[l] - f1;

        for(int j1 = l; j1 >= i1; --j1) {
          float f4 = f2;
          float f5 = f3;
          if (k == 0) {
            f2 += (float)(randomsource1.nextInt(11) - 5);
            f3 += (float)(randomsource1.nextInt(11) - 5);
          } else {
            f2 += (float)(randomsource1.nextInt(31) - 15);
            f3 += (float)(randomsource1.nextInt(31) - 15);
          }

          float f10 = 0.1F + (float)j * 0.2F;
          if (k == 0) {
            f10 *= (float)j1 * 0.1F + 1.0F;
          }

          float f11 = 0.1F + (float)j * 0.2F;
          if (k == 0) {
            f11 *= ((float)j1 - 1.0F) * 0.1F + 1.0F;
          }

          quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.45F, 0.45F, 0.5F, f10, f11, false, false, true, false);
          quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.45F, 0.45F, 0.5F, f10, f11, true, false, true, true);
          quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.45F, 0.45F, 0.5F, f10, f11, true, true, false, true);
          quad(matrix4f, vertexconsumer, f2, f3, j1, f4, f5, 0.45F, 0.45F, 0.5F, f10, f11, false, true, false, false);
        }
      }
    }

  }

  private static void quad(Matrix4f pMatrix, VertexConsumer pConsumer, float p_115275_, float p_115276_, int p_115277_, float p_115278_, float p_115279_, float pRed, float pGreen, float pBlue, float p_115283_, float p_115284_, boolean p_115285_, boolean p_115286_, boolean p_115287_, boolean p_115288_) {
    pConsumer.vertex(pMatrix, p_115275_ + (p_115285_ ? p_115284_ : -p_115284_), (float)(p_115277_ * 16), p_115276_ + (p_115286_ ? p_115284_ : -p_115284_)).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, p_115278_ + (p_115285_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * 16), p_115279_ + (p_115286_ ? p_115283_ : -p_115283_)).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, p_115278_ + (p_115287_ ? p_115283_ : -p_115283_), (float)((p_115277_ + 1) * 16), p_115279_ + (p_115288_ ? p_115283_ : -p_115283_)).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, p_115275_ + (p_115287_ ? p_115284_ : -p_115284_), (float)(p_115277_ * 16), p_115276_ + (p_115288_ ? p_115284_ : -p_115284_)).color(pRed, pGreen, pBlue, 0.3F).endVertex();
  }

  private static void quad2(Matrix4f pMatrix, VertexConsumer pConsumer, float x1, float z1, int y, float x2, float z2, float pRed, float pGreen, float pBlue, float m, float p) {
    pConsumer.vertex(pMatrix, x1 - p, (float)(y * 16), z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x2 - m, (float)((y + 1) * 16), z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x2 + m, (float)((y + 1) * 16), z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x1 + p, (float)(y * 16), z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
  }

  private static void quad3(Matrix4f pMatrix, VertexConsumer pConsumer, float x1, float z1, int y, float x2, float z2, float pRed, float pGreen, float pBlue, float m, float p) {
    pConsumer.vertex(pMatrix, x1 - p, y, z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x2 - m, y, z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x2 + m, y, z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    pConsumer.vertex(pMatrix, x1 + p, y, z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
  }

  private static void renderLine(PoseStack matrixStackIn, TestEntity tileEntityIn) {
    Tesselator tesselator = Tesselator.getInstance();
    BufferBuilder bufferbuilder = tesselator.getBuilder();
    Vec3 projectedView = Minecraft.getInstance().gameRenderer.getMainCamera().getPosition();

    matrixStackIn.pushPose();
    matrixStackIn.translate(-projectedView.x, -projectedView.y, -projectedView.z);
    RenderSystem.lineWidth(2);

    Matrix4f matrix = matrixStackIn.last().pose();
    Color color = new Color(255, 0, 0, 255);

    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
    quad3(matrix, bufferbuilder, 1, 1, 3, 1, 1, 0.45F, 0.45F, 0.5F, 3, 3);

    drawLine(matrix, bufferbuilder,
        new Vector3d(tileEntityIn.getOnPos().getX() + 1f, 0.7f, 1f),
        new Vector3d(tileEntityIn.getOnPos().getX() + 2f, 1.7f, 2f), color);
    tesselator.end();

    GlStateManager._disableDepthTest();
    matrixStackIn.popPose();
  }

  private static void drawLine(Matrix4f matrix, BufferBuilder buffer, Vector3d p1, Vector3d p2, Color color)
  {
    buffer.vertex(matrix, (float)p1.x, (float)p1.y, (float)p1.z)
        .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
        .endVertex();
    buffer.vertex(matrix, (float)p2.x, (float)p2.y, (float)p2.z)
        .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())
        .endVertex();
  }

  private static void quad4(Matrix4f pMatrix, BufferBuilder buffer, float x1, float z1, int y, float x2, float z2, float pRed, float pGreen, float pBlue, float m, float p) {
    buffer.vertex(pMatrix, x1 - p, y, z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    buffer.vertex(pMatrix, x2 - m, y, z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    buffer.vertex(pMatrix, x2 + m, y, z2 - m).color(pRed, pGreen, pBlue, 0.3F).endVertex();
    buffer.vertex(pMatrix, x1 + p, y, z1 - p).color(pRed, pGreen, pBlue, 0.3F).endVertex();
  }

  @Override
  public ResourceLocation getTextureLocation(TestEntity pEntity) {
    return TextureAtlas.LOCATION_BLOCKS;
  }
}
