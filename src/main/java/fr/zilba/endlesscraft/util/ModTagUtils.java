package fr.zilba.endlesscraft.util;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModTagUtils {

  public static void setTag(ItemStack stack, String key, String value) {
    if (!stack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      stack.getOrCreateTag().put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    stack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).putString(key, value);
  }
  public static void setTag(ItemStack stack, String key, int value) {
    if (!stack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      stack.getOrCreateTag().put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    stack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).putInt(key, value);
  }
  public static void setTag(ItemStack stack, String key, List<Integer> value) {
    if (!stack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      stack.getOrCreateTag().put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    stack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).putIntArray(key, value);
  }

  public static float getTagFloat(ItemStack pStack, String element) {
    if (pStack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      return pStack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).getFloat(element);
    }
    return -1;
  }

  public static String getTag(ItemStack pStack, String element) {
    if (pStack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      return pStack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).getString(element);
    }
    return null;
  }

  public static int getTagInt(ItemStack pStack, String element) {
    if (pStack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      return pStack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).getInt(element);
    }
    return 0;
  }

  public static boolean getTagBoolean(ItemStack pStack, String element) {
    if (pStack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      return pStack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).getBoolean(element);
    }
    return false;
  }

  public static List<Integer> getTagList(ItemStack pStack, String element) {
    if (pStack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      int[] tab = pStack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).getIntArray(element);
      return Arrays.stream(tab)
          .boxed()
          .collect(Collectors.toList());
    }
    return List.of();
  }

  public static void setTag(ItemStack stack, String key, boolean value) {
    if (!stack.getOrCreateTag().contains(EndlessCraft.MOD_ID)) {
      stack.getOrCreateTag().put(EndlessCraft.MOD_ID, new CompoundTag());
    }
    stack.getOrCreateTag().getCompound(EndlessCraft.MOD_ID).putBoolean(key, value);
  }


}
