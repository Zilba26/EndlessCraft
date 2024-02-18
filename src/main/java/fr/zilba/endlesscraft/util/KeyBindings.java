package fr.zilba.endlesscraft.util;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class KeyBindings {

  public static final String KEY_CATEGORY = "key.category.endless_craft";

  public static final String KEY_OPEN_GUI = "key.endless_craft.open_gui";

  public static final KeyMapping OPEN_GUI = new KeyMapping(KEY_OPEN_GUI, GLFW.GLFW_KEY_J, KEY_CATEGORY);

  public static void register(RegisterKeyMappingsEvent event) {
    event.register(OPEN_GUI);
  }
}
