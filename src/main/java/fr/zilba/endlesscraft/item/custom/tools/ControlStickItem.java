package fr.zilba.endlesscraft.item.custom.tools;

import fr.zilba.endlesscraft.entity.ai.*;
import fr.zilba.endlesscraft.item.custom.upgrade.EndlessCraftUpgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.ElderGuardian;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ControlStickItem extends Item implements EndlessCraftToolsItem {

  private Monster currentMonster;
  public ControlStickItem(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    this.appendUpgradeHoverText(pStack, pTooltipComponents);
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }

  @Override
  public InteractionResult interactLivingEntity(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
    if (!pPlayer.level().isClientSide) {
      if (pInteractionTarget instanceof Monster monster) {
        if (this.currentMonster == monster) {
          return InteractionResult.PASS;
        }
        if (monster instanceof WitherBoss || monster instanceof Warden || monster instanceof ElderGuardian) {
          return InteractionResult.PASS;
        }
        if (monster.getPersistentData().contains("tamed")) {
          return InteractionResult.PASS;
        }
        if (this.currentMonster != null) {
          this.currentMonster.getPersistentData().remove("tamed");
        }
        this.currentMonster = monster;
        this.currentMonster.getPersistentData().putString("tamed", pPlayer.getUUID().toString());

        //add tamed monster ai
        monster.targetSelector.addGoal(-3, new MonsterOwnerHurtByTargetTargetGoal(monster, pPlayer));
        monster.targetSelector.addGoal(-3, new MonsterOwnerHurtTargetTargetGoal(monster, pPlayer));
        monster.targetSelector.addGoal(-2, new TameMonsterNearestAttackableTargetGoal(monster, pPlayer));
        monster.targetSelector.addGoal(-1, new TameMonsterResetTargetTargetGoal(monster, pPlayer));
        monster.goalSelector.addGoal(4, new TameMonsterFollowOwnerGoal(monster, 8.0F, 4.0F, pPlayer));

        //add health
        double healthLevel = EndlessCraftUpgrade.getItemLevel(pStack, EndlessCraftUpgrade.HEALTH);
        if (healthLevel > 0) {
          float currentMaxHealth = monster.getMaxHealth();
          monster.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(
              new AttributeModifier("Tamed Monster",
                  healthLevel / 2, AttributeModifier.Operation.MULTIPLY_BASE)
          );
          monster.setHealth(monster.getMaxHealth() * monster.getHealth()/currentMaxHealth);
        }

        //add protection
        int protectionLevel = EndlessCraftUpgrade.getItemLevel(pStack, EndlessCraftUpgrade.PROTECTION);
        if (protectionLevel > 0) {
          setArmorToMonster(EquipmentSlot.HEAD, protectionLevel);
          setArmorToMonster(EquipmentSlot.CHEST, protectionLevel);
          setArmorToMonster(EquipmentSlot.LEGS, protectionLevel);
          setArmorToMonster(EquipmentSlot.FEET, protectionLevel);
        }

        pStack.hurtAndBreak(1, pPlayer, (pPlayerEntity) -> pPlayerEntity.broadcastBreakEvent(pUsedHand));
      }
    }
    return InteractionResult.SUCCESS;
  }

  private void setArmorToMonster(EquipmentSlot slot, int protectionLevel) {
    ItemStack armor = currentMonster.getItemBySlot(slot);
    if (armor.isEmpty()) {
      currentMonster.setItemSlot(slot, new ItemStack(getArmorByProtectionUpgrade(slot, protectionLevel)));
      currentMonster.setDropChance(slot, 0.0F);
    }
  }

  private Item getArmorByProtectionUpgrade(EquipmentSlot slot, int protectionLevel) {
    return switch (slot) {
      case HEAD -> switch (protectionLevel) {
        case 1 -> Items.LEATHER_HELMET;
        case 2 -> Items.GOLDEN_HELMET;
        case 3 -> Items.IRON_HELMET;
        case 4 -> Items.DIAMOND_HELMET;
        case 5 -> Items.NETHERITE_HELMET;
        default -> null;
      };
      case CHEST -> switch (protectionLevel) {
        case 1 -> Items.LEATHER_CHESTPLATE;
        case 2 -> Items.GOLDEN_CHESTPLATE;
        case 3 -> Items.IRON_CHESTPLATE;
        case 4 -> Items.DIAMOND_CHESTPLATE;
        case 5 -> Items.NETHERITE_CHESTPLATE;
        default -> null;
      };
      case LEGS -> switch (protectionLevel) {
        case 1 -> Items.LEATHER_LEGGINGS;
        case 2 -> Items.GOLDEN_LEGGINGS;
        case 3 -> Items.IRON_LEGGINGS;
        case 4 -> Items.DIAMOND_LEGGINGS;
        case 5 -> Items.NETHERITE_LEGGINGS;
        default -> null;
      };
      case FEET -> switch (protectionLevel) {
        case 1 -> Items.LEATHER_BOOTS;
        case 2 -> Items.GOLDEN_BOOTS;
        case 3 -> Items.IRON_BOOTS;
        case 4 -> Items.DIAMOND_BOOTS;
        case 5 -> Items.NETHERITE_BOOTS;
        default -> null;
      };
      default -> null;
    };
  }

  private static class TameMonster {

    public Monster monster;
    public Player owner;
  }
}
