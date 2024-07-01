package me.waeal.wezelmod.listeners.esp;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChestESPListener {
    @Autowired
    ESPServices service;

    @SubscribeEvent
    public void renderChest(RenderWorldLastEvent event) {
        if (Main.settings.chestEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        Minecraft.getMinecraft().theWorld.loadedTileEntityList.forEach(e -> {
            if (e instanceof TileEntityChest)
                service.drawBlockBox(e.getPos());
        });
    }
}
