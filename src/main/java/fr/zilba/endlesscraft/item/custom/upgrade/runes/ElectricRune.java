package fr.zilba.endlesscraft.item.custom.upgrade.runes;

import fr.zilba.endlesscraft.item.custom.ArcaneGauntletElement;

public class ElectricRune extends Rune {
  public ElectricRune(Properties pProperties) {
    super(pProperties);
  }

  @Override
  public ArcaneGauntletElement getElement() {
    return ArcaneGauntletElement.ELECTRIC;
  }
}
