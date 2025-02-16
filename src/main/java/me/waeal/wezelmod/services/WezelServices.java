package me.waeal.wezelmod.services;

import me.waeal.wezelmod.Main;
import net.minecraft.client.Minecraft;

public class WezelServices {
    public static void openSettingsGui() {
        Minecraft.getMinecraft().displayGuiScreen(Main.settings.gui());
    }
}
