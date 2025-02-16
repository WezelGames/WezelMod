package me.waeal.wezelmod.listeners.esp;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ChestESPListener {

    @SubscribeEvent
    public void renderChest(RenderWorldLastEvent event) {
        if (Main.settings.chestEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        Minecraft.getMinecraft().theWorld.loadedTileEntityList.forEach(e -> {
            if (e instanceof TileEntityChest)
                ESPServices.drawBlockBox(e.getPos(), Main.settings.chestEsp, Main.settings.chestEspColor);
        });
    }
}
