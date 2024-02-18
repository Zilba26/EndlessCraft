package fr.zilba.endlesscraft.particle.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.registries.ForgeRegistries;

public class Color {
  public float red;
  public float green;
  public float blue;

  public Color(float red, float green, float blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }
  public Color(int red, int green, int blue) {
    this.red = (float) red /255;
    this.green = (float) green /255;
    this.blue = (float) blue /255;
  }

  public static Color fromString(String string) {
    if (string == null || string.isEmpty())
      return defaultParticleColor();
    String[] arr = string.split(",");
    return new Color(
        (float) Integer.parseInt(arr[0].trim()),
        (float) Integer.parseInt(arr[1].trim()),
        (float) Integer.parseInt(arr[2].trim()));
  }

  private static Color defaultParticleColor() {
    return new Color(1, 1, 1);
  }

  public static Color from(CompoundTag compoundTag) {
    return new Color(
        compoundTag.getFloat("red"),
        compoundTag.getFloat("green"),
        compoundTag.getFloat("blue"));
  }

  public static Color from(int red, int green, int blue) {
    return new Color(
        (float) red / 255,
        (float) green / 255,
        (float) blue / 255);
  }

  public CompoundTag serialize() {
    CompoundTag tag = new CompoundTag();
    tag.putFloat("r", red);
    tag.putFloat("g", green);
    tag.putFloat("b", blue);
    return tag;
  }
}
