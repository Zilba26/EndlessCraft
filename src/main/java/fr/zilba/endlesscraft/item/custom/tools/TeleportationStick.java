package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TeleportationStick extends Item implements EndlessCraftToolsItem {

  public static final int BASE_COOLDOWN = 20 * 60 * 5;

  public TeleportationStick(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    ItemStack pStack = pPlayer.getItemInHand(pUsedHand);
    BlockPos teleportPos = getBlockPos(pStack);
    if (Screen.hasShiftDown()) {
      setBlockPos(pStack, pPlayer.blockPosition());
    } else if (teleportPos != null && canTeleport(pPlayer)) {
      teleportPlayer(pPlayer, teleportPos, pStack);
    }
    return super.use(pLevel, pPlayer, pUsedHand);
  }

  private void teleportPlayer(Player pPlayer, BlockPos pPos, ItemStack pStack) {
    pPlayer.teleportTo(pPos.getX(), pPos.getY(), pPos.getZ());
    pPlayer.getCooldowns().addCooldown(this, getCooldown(pStack));
  }

  private boolean canTeleport(Player entity) {
    return !entity.getCooldowns().isOnCooldown(this);
  }

  private int getCooldown(ItemStack pStack) {
    int timeUpgraderLevel = EndlessCraftUpgrade.getItemLevel(pStack, EndlessCraftUpgrade.TIME);
    if (timeUpgraderLevel > 0) {
      return BASE_COOLDOWN / timeUpgraderLevel;
    }
    return BASE_COOLDOWN;
  }

  private BlockPos getBlockPos(ItemStack pStack) {
    if (pStack.hasTag()) {
      if (pStack.getTag().contains(EndlessCraft.MOD_ID)) {
        if (pStack.getTag().getCompound(EndlessCraft.MOD_ID).contains("teleportPos")) {
          return BlockPos.of(pStack.getTag().getCompound(EndlessCraft.MOD_ID).getLong("teleportPos"));
        }
      }
    }
    return null;
  }

  private void setBlockPos(ItemStack pStack, BlockPos pPos) {
    if (!pStack.hasTag()) {
      pStack.setTag(new CompoundTag());
    }
    if (!pStack.getTag().contains(EndlessCraft.MOD_ID)) {
      pStack.getTag().put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    pStack.getTag().getCompound(EndlessCraft.MOD_ID).putLong("teleportPos", pPos.asLong());
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    BlockPos teleportPos = getBlockPos(pStack);
    if (teleportPos != null) {
      pTooltipComponents.add(Component.nullToEmpty("Teleportation position: " + teleportPos.getX() + " " + teleportPos.getY() + " " + teleportPos.getZ()));
    }
    else {
      pTooltipComponents.add(Component.translatable("tooltip.endless_craft.teleport_stick.instructions"));
    }
    this.appendUpgradeHoverText(pStack, pTooltipComponents);
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}
