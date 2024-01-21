package fr.zilba.endlesscraft.item.custom;

import fr.zilba.endlesscraft.entity.custom.TemporalArrow;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.BowItem;

public class TemporalArc extends BowItem {
  public TemporalArc(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public AbstractArrow customArrow(AbstractArrow arrow) {
    return new TemporalArrow(arrow.level(), (LivingEntity) arrow.getOwner());
  }
}