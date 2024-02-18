package fr.zilba.endlesscraft.network;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {

  private static final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder
      .named(new ResourceLocation(EndlessCraft.MOD_ID, "main"))
      .serverAcceptedVersions((s) -> true)
      .clientAcceptedVersions((s) -> true)
      .networkProtocolVersion(() -> "1")
      .simpleChannel();

  public static void register() {
    INSTANCE.messageBuilder(ArcaneGauntletElementChangePacket.class,0, NetworkDirection.PLAY_TO_SERVER)
        .encoder(ArcaneGauntletElementChangePacket::encode)
        .decoder(ArcaneGauntletElementChangePacket::new)
        .consumerMainThread(ArcaneGauntletElementChangePacket::handle)
        .add();
  }

  public static void sendToServer(Object msg) {
    INSTANCE.send(PacketDistributor.SERVER.noArg(), msg);
  }
}
