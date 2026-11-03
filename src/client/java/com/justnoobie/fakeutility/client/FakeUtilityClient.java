package com.justnoobie.fakeutility.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class FakeUtilityClient implements ClientModInitializer {
    private static KeyBinding openMenu;
    @Override public void onInitializeClient() {
        DisguiseConfig.load();
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.fakeutility.open_menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_K, "category.fakeutility"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenu.wasPressed()) if (client.currentScreen == null) client.setScreen(new LootdropScreen());
        });
    }
}
