package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HarpGuiListener {

    private int lastPressed = 0;

    @SubscribeEvent
    public void harpSolver(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Main.settings.harp)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!GuiServices.isChest(event.gui))
            return;

        GuiChest gui = (GuiChest) event.gui;

        if (!GuiServices.getName(gui).contains("Harp") || !GuiServices.updatedInv(gui))
            return;

        boolean pressed = false;
        for (int i = 36; i < 45; i++) {
            if (!GuiServices.itemEquals(gui, i, 155) || i == lastPressed) // quartz
                continue;

            if (GuiServices.itemEquals(gui, i-9, 160)) // glass pane
                lastPressed = i;

            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(Main.settings.harpDelay);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                GuiServices.click(finalI);
            }).start();
            pressed = true;
            break;
        }
        if (!pressed)
            lastPressed = 0;
    }
}
