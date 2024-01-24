package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.item.ModArmorMaterials;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;

public class InfinityArmorEquipItem extends Item implements ICurioItem, EndlessCraftToolsItem {
  public InfinityArmorEquipItem(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public void curioTick(SlotContext slotContext, ItemStack stack) {
    if (!slotContext.entity().level().isClientSide()) {
      if (slotContext.entity() instanceof Player player) {
        if (hasInfinityArmor(player)) {
          if (EndlessCraftUpgrade.getItemLevel(stack, EndlessCraftUpgrade.NIGHT_VISION) > 0) {
            if (!player.hasEffect(MobEffects.NIGHT_VISION) || player.getEffect(MobEffects.NIGHT_VISION).getDuration() < 220) {
              player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 300, 0, false, false, true));
            }
          }
        }
      }
    }
    ICurioItem.super.curioTick(slotContext, stack);
  }

  @Override
  public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
    if (!slotContext.entity().level().isClientSide()) {
      if (slotContext.entity() instanceof Player player) {
        if (hasInfinityArmor(player)) {
          if (EndlessCraftUpgrade.getItemLevel(stack, EndlessCraftUpgrade.FLY) > 0) {
            player.getAbilities().mayfly = true;
            player.getAbilities().setFlyingSpeed(getMaxFlyingSpeed(stack));
            player.onUpdateAbilities();
          }
        }
      }
    }
    ICurioItem.super.onEquip(slotContext, prevStack, stack);
  }

  @Override
  public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
    if (!slotContext.entity().level().isClientSide()) {
      if (slotContext.entity() instanceof Player player) {
        player.getAbilities().mayfly = false;
        player.getAbilities().flying = false;
        player.getAbilities().setFlyingSpeed(0.05f);
        player.onUpdateAbilities();
      }
    }
    ICurioItem.super.onUnequip(slotContext, newStack, stack);
  }

  private boolean hasInfinityArmor(Player player) {
    ItemStack boots = player.getInventory().getArmor(0);
    ItemStack leggings = player.getInventory().getArmor(1);
    ItemStack breastplate = player.getInventory().getArmor(2);
    ItemStack helmet = player.getInventory().getArmor(3);

    return isCorrectArmor(boots) && isCorrectArmor(leggings)
        && isCorrectArmor(breastplate) && isCorrectArmor(helmet);
  }

  private boolean isCorrectArmor(ItemStack stack) {
    if (!stack.isEmpty()) {
      if (stack.getItem() instanceof ArmorItem armor) {
        return armor.getMaterial() == ModArmorMaterials.INFINITY;
      }
    }
    return false;
  }

  private float getMaxFlyingSpeed(ItemStack stack) {
    return 0.01f * (float) Math.pow(EndlessCraftUpgrade.getItemLevel(stack, EndlessCraftUpgrade.FLY), 2);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    this.appendUpgradeHoverText(pStack, pTooltipComponents);
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}
