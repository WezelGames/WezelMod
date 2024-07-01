package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.GuiServices;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HarpGuiListener {
    @Autowired
    GuiServices service;

    private int lastPressed = 0;

    @SubscribeEvent
    public void harpSolver(GuiScreenEvent.BackgroundDrawnEvent event) {
        if (!Main.settings.harp)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (!service.isChest(event.gui))
            return;
        GuiChest gui = (GuiChest) event.gui;

        if (!service.updatedInv(gui))
            return;

        boolean pressed = false;
        for (int i = 36; i < 45; i++) {
            if (!service.itemEquals(gui, i, 155) || i == lastPressed) // quartz
                continue;

            if (service.itemEquals(gui, i-9, 160)) // glass pane
                lastPressed = i;

            service.click(i);
            pressed = true;
            break;
        }
        if (!pressed)
            lastPressed = 0;
    }
}
