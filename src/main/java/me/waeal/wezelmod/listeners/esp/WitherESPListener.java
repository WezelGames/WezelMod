package me.waeal.wezelmod.listeners.esp;

import java.util.HashSet;
import java.util.List;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WitherESPListener {
    @SubscribeEvent
    public void renderEntity(RenderWorldLastEvent event) {
        if (Main.settings.witherEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        Minecraft.getMinecraft().theWorld.loadedEntityList.forEach(e -> {
            if (e instanceof EntityWither
                    && !e.isInvisible())
                ESPServices.drawEntityBox(e, event.partialTicks, Main.settings.witherEsp, Main.settings.witherEspColor);
        });
    }
}
