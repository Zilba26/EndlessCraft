package fr.zilba.endlesscraft.util;

import fr.zilba.endlesscraft.EndlessCraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class ModTags {

  public static class Blocks {

    public static final TagKey<Block> NEEDS_INFINITY_TOOL = tag("needs_infinity_tool");

    public static TagKey<Block> tag(String name) {
      return BlockTags.create(new ResourceLocation(EndlessCraft.MOD_ID, name));
    }
  }
}
