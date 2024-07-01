package me.waeal.wezelmod.listeners;

import java.util.Random;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MelodyGuiListener {
    @Autowired
    GuiServices service;

    @SubscribeEvent
    public void melodyGUI(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Main.settings.melody)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!service.isChest(event.gui))
            return;

        GuiChest gui = (GuiChest) event.gui;

        if (!service.getName(gui).equals("Click the button on time!") || !service.updatedInv(gui))
            return;

        for (int i = 1; i < 6; i++) {
            if (!service.paneEquals(gui, i, 2))
                continue;

            for (int j = 4; j > 0; j--) {
                if (service.paneEquals(gui, i + j*9, 14)) // Red
                    break;

                if (!service.paneEquals(gui, i + j*9, 5)) // Lime
                    continue;

                int finalJ = j;

                if (!Main.settings.melodySkip) {
                    service.click(7 + finalJ *9);
                    break;
                }

                new Thread(() -> {
                    service.click(7 + finalJ *9);
                    try {
                        Thread.sleep(Main.settings.melodySkipDelay + (int) (Math.random()*Main.settings.melodyRandomSkipDelay));
                        service.click(16 + finalJ *9);
                        Thread.sleep(Main.settings.melodySkipDelay + (int) (Math.random()*Main.settings.melodyRandomSkipDelay));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    service.click(16 + finalJ *9);
                }).start();

                break;
            }
            break;
        }
    }
}
