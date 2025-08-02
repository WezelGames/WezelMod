package me.waeal.wezelmod.services;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.objects.macros.Macro;
import me.waeal.wezelmod.objects.macros.MacroEditGuiScreen;
import me.waeal.wezelmod.objects.macros.MacroNavGuiScreen;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WezelServices {
    private static boolean close;

    public static void openSettingsGui() {
        Minecraft.getMinecraft().displayGuiScreen(Main.settings.gui());
    }

    public static void openMacroNavGui() {
        Minecraft.getMinecraft().displayGuiScreen(new MacroNavGuiScreen());
    }

    public static void openMacroEditGui(Macro macro) {
        Minecraft.getMinecraft().displayGuiScreen(new MacroEditGuiScreen(macro));
    }

    public static void closeGui() {
        close = true;
        MinecraftForge.EVENT_BUS.register(new WezelServices());
    }

    @SubscribeEvent
    public void nextTick (TickEvent.ClientTickEvent event) {
        if (close)
            Minecraft.getMinecraft().displayGuiScreen(null);
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
