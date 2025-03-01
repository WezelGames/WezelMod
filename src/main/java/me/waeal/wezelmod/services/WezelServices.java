package me.waeal.wezelmod.services;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.objects.macros.Macro;
import me.waeal.wezelmod.objects.macros.MacroEditGuiScreen;
import me.waeal.wezelmod.objects.macros.MacroNavGuiScreen;
import net.minecraft.client.Minecraft;

public class WezelServices {
    public static void openSettingsGui() {
        Minecraft.getMinecraft().displayGuiScreen(Main.settings.gui());
    }

    public static void openMacroNavGui() {
        Minecraft.getMinecraft().displayGuiScreen(new MacroNavGuiScreen());
    }

    public static void openMacroEditGui(Macro macro) {
        Minecraft.getMinecraft().displayGuiScreen(new MacroEditGuiScreen(macro));
    }
}
