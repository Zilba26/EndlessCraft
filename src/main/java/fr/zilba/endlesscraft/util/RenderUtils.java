package fr.zilba.endlesscraft.util;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fr.zilba.endlesscraft.item.custom.upgrade.runes.Rune;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix4f;

public class RenderUtils {

  private static final RenderType TRANSLUCENT = RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS);

  public static void drawRuneItem(Rune objectToBeDrawn, GuiGraphics graphics, int positionX, int positionY, int size, boolean renderTransparent) {
    renderFakeItemTransparent(graphics.pose(), objectToBeDrawn.getDefaultInstance(), positionX, positionY, size, 0, renderTransparent,150);
  }

  public static void drawItemAsIcon(ItemStack itemStack, GuiGraphics graphics, int positionX, int positionY, int size, boolean renderTransparent) {
    renderFakeItemTransparent(graphics.pose(), itemStack, positionX, positionY, size, 0, renderTransparent,150);
  }

  public static void renderFakeItemTransparent(PoseStack poseStack, ItemStack stack, int x, int y,int scale, int alpha, boolean transparent, int zIndex) {
    if (stack.isEmpty()) {
      return;
    }

    ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

    BakedModel model = renderer.getModel(stack, null, Minecraft.getInstance().player, 0);
    renderItemModel(poseStack, stack, x, y, scale, alpha, model, renderer, transparent, zIndex);
  }

  private static final Matrix4f SCALE_INVERT_Y = new Matrix4f().scaling(1F, -1F, 1F);

  public static void renderItemModel(PoseStack poseStack, ItemStack stack, int x, int y, int scale, int alpha, BakedModel model, ItemRenderer renderer, boolean transparent, int zIndex) {
    poseStack.pushPose();
    poseStack.translate(x + 8F, y + 8F, zIndex);
    poseStack.mulPoseMatrix(SCALE_INVERT_Y);
    poseStack.scale(scale, scale, scale);

    boolean flatLight = !model.usesBlockLight();
    if (flatLight) {
      Lighting.setupForFlatItems();
    }

    MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
    renderer.render(
        stack,
        ItemDisplayContext.GUI,
        false,
        poseStack,
        buffer,
        LightTexture.FULL_BRIGHT,
        OverlayTexture.NO_OVERLAY,
        model
    );
    buffer.endBatch();

    RenderSystem.enableDepthTest();

    if (flatLight) {
      Lighting.setupFor3DItems();
    }

    poseStack.popPose();
  }
}
