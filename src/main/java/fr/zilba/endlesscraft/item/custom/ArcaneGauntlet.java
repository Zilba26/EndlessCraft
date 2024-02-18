package fr.zilba.endlesscraft.item.custom;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.entity.custom.ArcaneGauntletProjectile;
import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArcaneGauntlet extends Item {
  public ArcaneGauntlet(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
    if (!pLevel.isClientSide()) {
      ItemStack itemStack = pPlayer.getItemInHand(pUsedHand);
      float elementOrdinal = ModTagUtils.getTagFloat(itemStack, "element");
      ArcaneGauntletElement element = ArcaneGauntletElement.values()[(int) elementOrdinal];
      ArcaneGauntletProjectile projectile = new ArcaneGauntletProjectile(pLevel, pPlayer, element);
      projectile.shootFromRotation(pPlayer, pPlayer.getXRot(), pPlayer.getYRot(), 0.0F, 0.8F, 1.0F);
      pLevel.addFreshEntity(projectile);
//      if (Screen.hasShiftDown()) {
//        ArcaneGauntletElement nextElement = element.ordinal() < ArcaneGauntletElement.values().length - 1
//            ? ArcaneGauntletElement.values()[element.ordinal() + 1] : ArcaneGauntletElement.values()[0];
//        ModTagUtils.setTag(itemStack, "element", nextElement.ordinal());
//        pPlayer.displayClientMessage(Component.literal(nextElement.name()), true);
//      } else {
//
//      }
    }

    return super.use(pLevel, pPlayer, pUsedHand);
  }

  public static List<ArcaneGauntletElement> getElements(ItemStack pStack) {
    return ModTagUtils.getTagList(pStack, "elements").stream().map(
        i -> ArcaneGauntletElement.values()[i]
    ).collect(Collectors.toList());
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    List<ArcaneGauntletElement> elements = getElements(pStack);
    if (elements.isEmpty()) {
      pTooltipComponents.add(Component.translatable("tooltip.endless_craft.arcane_gauntlet.no_element")
          .withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC));
    } else {
      for (ArcaneGauntletElement element : getElements(pStack)) {
        pTooltipComponents.add(Component.literal(element.name()).withStyle(element.getChatColor()));
      }
    }
  }
}
