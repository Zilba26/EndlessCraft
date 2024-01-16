package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.item.ModToolTiers;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinitySword extends SwordItem implements EndlessCraftToolsItem {
  public InfinitySword(Properties pProperties) {
    super(ModToolTiers.INFINITY, 3, -2.4F, pProperties);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    this.appendUpgradeHoverText(pStack, pTooltipComponents);
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    System.out.println("infinity dura " + this.getDamage(pPlayer.getItemInHand(pUsedHand)));
    return super.use(pLevel, pPlayer, pUsedHand);
  }
}
