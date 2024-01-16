package fr.zilba.endlesscraft.events;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.block.entity.ModBlocksEntities;
import fr.zilba.endlesscraft.client.StarRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = EndlessCraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventClientBusEvents {

  @SubscribeEvent
  public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
    event.registerBlockEntityRenderer(ModBlocksEntities.STAR_BE.get(), context -> new StarRenderer());
  }
}
