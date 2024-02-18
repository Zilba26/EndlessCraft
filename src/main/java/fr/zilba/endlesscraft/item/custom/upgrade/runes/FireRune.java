package fr.zilba.endlesscraft.item.custom.upgrade.runes;

import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import fr.zilba.endlesscraft.util.ModTagUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FireRune extends Rune{
  public FireRune(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public ArcaneGauntletElement getElement() {
    return ArcaneGauntletElement.FIRE;
  }

  @Override
  public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
    super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    if (!super.isActivated(pStack) && !pLevel.isClientSide()) {
      if (pEntity.isInLava()) {
        int cooldown = ModTagUtils.getTagInt(pStack, "cooldown");
        if (cooldown >= 100) { //TODO: Change this to 200
          super.activate(pStack);
        }
        ModTagUtils.setTag(pStack, "cooldown", cooldown + 1);
      } else {
        ModTagUtils.setTag(pStack, "cooldown", 0);
      }
    }
  }

  @Override
  public boolean onDroppedByPlayer(ItemStack item, Player player) {
    ModTagUtils.setTag(item, "cooldown", 0);
    return super.onDroppedByPlayer(item, player);
  }
}
