package fr.zilba.endlesscraft.item;

import fr.zilba.endlesscraft.EndlessCraft;
import fr.zilba.endlesscraft.util.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;

import java.util.List;

public class ModToolTiers {

  public static final Tier INFINITY = TierSortingRegistry.registerTier(
      new ForgeTier(10, 10000, 8.0F, 20.0F, 20,
          ModTags.Blocks.NEEDS_INFINITY_TOOL, () -> Ingredient.of(Items.NETHER_STAR)),
      new ResourceLocation(EndlessCraft.MOD_ID, "infinity"), List.of(Tiers.NETHERITE), List.of()
  );
}
