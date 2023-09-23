package me.waeal.bootymod.services;

import me.waeal.bootymod.Booty;
import net.minecraft.client.Minecraft;
import org.springframework.stereotype.Service;

@Service
public class BootyServices {
    public void openSettingsGui() {
        Minecraft.getMinecraft().displayGuiScreen(Booty.settings.gui());
    }
}
