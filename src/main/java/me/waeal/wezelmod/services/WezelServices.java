package me.waeal.wezelmod.services;

import me.waeal.wezelmod.Main;
import net.minecraft.client.Minecraft;
import org.springframework.stereotype.Service;

@Service
public class WezelServices {
    public void openSettingsGui() {
        Minecraft.getMinecraft().displayGuiScreen(Main.settings.gui());
    }
}
