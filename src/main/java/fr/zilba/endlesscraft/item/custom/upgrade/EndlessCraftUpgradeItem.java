package fr.zilba.endlesscraft.item.custom.upgrade;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class EndlessCraftUpgradeItem extends Item {

  public EndlessCraftUpgradeItem() {
    super(new Properties().stacksTo(8));
  }

  public abstract EndlessCraftUpgrade getUpgrade();
  public String getKeyName() {
    return getUpgrade().getKey();
  }
  public int getMaxLevel() {
    return getUpgrade().getMaxLevel();
  }

  @Override
  public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
    if (!pStack.hasTag()) {
      pStack.setTag(new CompoundTag());
    }
    CompoundTag tag = pStack.getTag();
    if (!tag.contains(EndlessCraft.MOD_ID)) {
      tag.put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    CompoundTag endlessCraftTag = tag.getCompound(EndlessCraft.MOD_ID);
    if (!endlessCraftTag.contains("level")) {
      endlessCraftTag.putInt("level", 1);
    }

    pTooltipComponents.add(Component.translatable("item.endless_craft.upgrade.level",
        endlessCraftTag.getInt("level")).withStyle(ChatFormatting.GOLD));
    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}
