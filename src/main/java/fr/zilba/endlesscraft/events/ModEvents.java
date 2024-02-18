package fr.zilba.endlesscraft.events;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.entity.ai.*;
import fr.zilba.endlesscraft.item.ModItems;
import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.EntityLeaveLevelEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

public class ModEvents {

  @Mod.EventBusSubscriber(modid = EndlessCraft.MOD_ID)
  public static class ForgeEvents {

    @SubscribeEvent
    public static void onTamedMonsterJoinWorld(EntityJoinLevelEvent event) {
      if(!event.getLevel().isClientSide()) {
        if (event.getEntity() instanceof Monster monster) {
          if (monster.getPersistentData().contains("tamed")) {
            Player player = event.getLevel().players().stream().filter(
                p -> p.getUUID().toString().equals(monster.getPersistentData().getString("tamed"))
            ).findFirst().orElse(null);

            monster.targetSelector.addGoal(-3, new MonsterOwnerHurtByTargetTargetGoal(monster, player));
            monster.targetSelector.addGoal(-3, new MonsterOwnerHurtTargetTargetGoal(monster, player));
            monster.targetSelector.addGoal(-2, new TameMonsterNearestAttackableTargetGoal(monster, player));
            monster.targetSelector.addGoal(-1, new TameMonsterResetTargetTargetGoal(monster, player));
            monster.goalSelector.addGoal(4, new TameMonsterFollowOwnerGoal(monster, 8.0F, 4.0F, player));
          }
        }
      }
    }

    @SubscribeEvent
    public static void onTamedMonsterKilled(EntityLeaveLevelEvent event) {
      if(!event.getLevel().isClientSide()) {
        if (event.getEntity() instanceof Monster monster) {
          if (monster.getPersistentData().contains("tamed")) {
            Player player = event.getLevel().players().stream().filter(
                p -> p.getUUID().toString().equals(monster.getPersistentData().getString("tamed"))
            ).findFirst().orElse(null);
            //TODO: remove tamed monster ai
          }
        }
      }
    }
  }

  @SubscribeEvent
  public static void onPlayerHustByLightning(EntityStruckByLightningEvent event) {
    if (event.getEntity() instanceof Player player) {
      for (ItemStack stack : player.getInventory().items) {
        if (stack.getItem() == ModItems.ELECTRIC_RUNE.get()) {
          ModTagUtils.setTag(stack, "activated", true);
        }
      }
    }
  }

}
