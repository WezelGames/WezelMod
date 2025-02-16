package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MelodyGuiListener {

    @SubscribeEvent
    public void melodyGUI(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Main.settings.melody)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!GuiServices.isChest(event.gui))
            return;

        GuiChest gui = (GuiChest) event.gui;

        if (!GuiServices.getName(gui).equals("Click the button on time!") || !GuiServices.updatedInv(gui))
            return;

        for (int i = 1; i < 6; i++) {
            if (!GuiServices.paneEquals(gui, i, 2))
                continue;

            for (int j = 4; j > 0; j--) {
                if (GuiServices.paneEquals(gui, i + j*9, 14)) // Red
                    break;

                if (!GuiServices.paneEquals(gui, i + j*9, 5)) // Lime
                    continue;


                if (!Main.settings.melodySkip || (Main.settings.melodyOnlySides && !(i == 1 || i == 5))) {
                    GuiServices.click(7 + j * 9);
                    break;
                }

                int finalJ = j;

                new Thread(() -> {
                    GuiServices.click(7 + finalJ * 9);
                    try {
                        for (int k = 1; finalJ + k <= 4; k++) {
                            Thread.sleep(Main.settings.melodySkipDelay + (int) (Math.random() * Main.settings.melodyRandomSkipDelay));
                            GuiServices.click(7 + (finalJ + k) * 9);
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

                break;
            }

            break;
        }
    }
}
