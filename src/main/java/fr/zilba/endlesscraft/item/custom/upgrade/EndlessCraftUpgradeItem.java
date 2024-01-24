package fr.zilba.endlesscraft.item.custom.upgrade;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderRecipe;
import fr.zilba.endlesscraft.recipe.EndlessUpgraderToolRecipe;
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
    pTooltipComponents.add(Component.translatable("tooltip.endless_craft.upgrade.level",
        endlessCraftTag.getInt("level")).withStyle(ChatFormatting.GOLD));

    if (pLevel != null) {
      List<EndlessUpgraderRecipe> recipes = pLevel.getRecipeManager()
          .getAllRecipesFor(EndlessUpgraderRecipe.Type.INSTANCE);
      StringBuilder toolsTooltip = new StringBuilder(Component.translatable("tooltip.endless_craft.upgrade.tools").getString());
      boolean showToolsTooltip = false;
      for (EndlessUpgraderRecipe recipe : recipes) {
        if (recipe instanceof EndlessUpgraderToolRecipe toolRecipe) {
          if (toolRecipe.getAddition().getItem() == this) {
            if (showToolsTooltip) toolsTooltip.append(", ");
            toolsTooltip.append(toolRecipe.getBase().getHoverName().getString());
            showToolsTooltip = true;
          }
        }
      }
      if (showToolsTooltip) pTooltipComponents.add(
          Component.literal(toolsTooltip.toString()).withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
    }

    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
  }
}
