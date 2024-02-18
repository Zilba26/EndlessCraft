package fr.zilba.endlesscraft.events;

import com.mojang.blaze3d.platform.InputConstants;
import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.entity.ModBlocksEntities;
import fr.zilba.endlesscraft.client.renderer.block.StarRenderer;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntlet;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import fr.zilba.endlesscraft.item.custom.upgrade.runes.Rune;
import fr.zilba.endlesscraft.network.ArcaneGauntletElementChangePacket;
import fr.zilba.endlesscraft.network.PacketHandler;
import fr.zilba.endlesscraft.particle.ModParticles;
import fr.zilba.endlesscraft.particle.custom.arcane_gauntlet_projectile_particle.ArcaneGauntletProjectileParticle;
import fr.zilba.endlesscraft.screen.radial.GuiRadialMenu;
import fr.zilba.endlesscraft.screen.radial.RadialMenu;
import fr.zilba.endlesscraft.util.KeyBindings;
import fr.zilba.endlesscraft.util.ModTagUtils;
import fr.zilba.endlesscraft.util.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkHooks;

@Mod.EventBusSubscriber(modid = EndlessCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {
  
  private static final Minecraft MINECRAFT = Minecraft.getInstance();

  @Mod.EventBusSubscriber(modid = EndlessCraft.MOD_ID, value = Dist.CLIENT)
  public static class ForgeEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
      if (event.getKey() == KeyBindings.OPEN_GUI.getKey().getValue()
          && event.getAction() == InputConstants.PRESS && MINECRAFT.player != null) {
        if (MINECRAFT.screen instanceof GuiRadialMenu) {
          MINECRAFT.player.closeContainer();
        } else {
          ItemStack stack = MINECRAFT.player.getMainHandItem();
          InteractionHand hand = stack.getItem() instanceof ArcaneGauntlet
              ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
          if (MINECRAFT.player.getItemInHand(hand).getItem() instanceof ArcaneGauntlet) {
            RadialMenu<Rune> radialMenu = new RadialMenu<>(
                (int slot) -> PacketHandler.sendToServer(new ArcaneGauntletElementChangePacket(slot, hand)),
                //(int slot) -> ModTagUtils.setTag(MINECRAFT.player.getItemInHand(hand), "element", slot),
                ArcaneGauntletElement.getRadialMenuSlots(),
                RenderUtils::drawRuneItem,
                20
            );
            MINECRAFT.setScreen(new GuiRadialMenu<>(radialMenu));
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(ModBlocksEntities.STAR_BE.get(), context -> new StarRenderer());
  }

  @SubscribeEvent
  public static void registerParticleFactories(final RegisterParticleProvidersEvent event) {
    event.registerSpriteSet(ModParticles.ARCANE_GAUNTLET_PROJECTILE_PARTICLE.get(),
        ArcaneGauntletProjectileParticle.Provider::new);
  }

  @SubscribeEvent
  public static void registerKeyBindings(final RegisterKeyMappingsEvent event) {
    KeyBindings.register(event);
  }

}
