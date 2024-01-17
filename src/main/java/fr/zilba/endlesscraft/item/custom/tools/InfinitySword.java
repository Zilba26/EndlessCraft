package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.item.ModToolTiers;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
  public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
    this.healEntity(pStack, pAttacker);
    this.giveFireResistanceEffect(pStack, pAttacker);
    pStack.hurtAndBreak(10, pAttacker, (p_43296_) -> {
      p_43296_.broadcastBreakEvent(EquipmentSlot.MAINHAND);
    });
    return true;
  }

  private void healEntity(ItemStack stack, LivingEntity entity) {
    int lifeSteal = EndlessCraftUpgrade.getItemLevel(stack, EndlessCraftUpgrade.LIFE_STEAL);
    if (lifeSteal > 0) {
      entity.heal(lifeSteal);
    }
  }

  private void giveFireResistanceEffect(ItemStack stack, LivingEntity entity) {
    int fireResistance = EndlessCraftUpgrade.getItemLevel(stack, EndlessCraftUpgrade.FIRE_RESISTANCE);
    if (fireResistance > 0) {
      entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 20 * 10));
    }
  }
}
