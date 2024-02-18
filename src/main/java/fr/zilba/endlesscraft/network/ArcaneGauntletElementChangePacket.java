package fr.zilba.endlesscraft.network;

import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ArcaneGauntletElementChangePacket {

  private final int elementOrdinal;
  private final InteractionHand hand;

  public ArcaneGauntletElementChangePacket(int elementOrdinal, InteractionHand hand) {
    this.elementOrdinal = elementOrdinal;
    this.hand = hand;
  }

  public ArcaneGauntletElementChangePacket(FriendlyByteBuf buffer) {
    this(buffer.readInt(), buffer.readEnum(InteractionHand.class));
  }

  public void encode(FriendlyByteBuf buffer) {
    buffer.writeInt(this.elementOrdinal);
    buffer.writeEnum(this.hand);
  }

  public void handle(Supplier<NetworkEvent.Context> context) {
    ServerPlayer player = context.get().getSender();
    if (player != null) {
      ItemStack stack = player.getItemInHand(this.hand);
      ModTagUtils.setTag(stack, "element", this.elementOrdinal);
    }
  }
}
