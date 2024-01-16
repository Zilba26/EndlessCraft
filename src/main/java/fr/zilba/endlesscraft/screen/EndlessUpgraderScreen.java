package fr.zilba.endlesscraft.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EndlessUpgraderScreen extends AbstractContainerScreen<EndlessUpgraderMenu> {

  private static final ResourceLocation TEXTURE =
    new ResourceLocation(EndlessCraft.MOD_ID, "textures/gui/endless_upgrader_gui.png");
  public EndlessUpgraderScreen(EndlessUpgraderMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
    super(pMenu, pPlayerInventory, pTitle);
  }

  @Override
  protected void init() {
    super.init();
    this.inventoryLabelY = 10000;
    this.titleLabelY = 10000;
  }

  @Override
  protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
    RenderSystem.setShader(GameRenderer::getPositionTexShader);
    RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    RenderSystem.setShaderTexture(0, TEXTURE);
    int x = (this.width - this.imageWidth) / 2;
    int y = (this.height - this.imageHeight) / 2;

    pGuiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);
  }

  @Override
  public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
    renderBackground(pGuiGraphics);
    super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    renderTooltip(pGuiGraphics, pMouseX, pMouseY);
  }
}
