package me.waeal.wezelmod.listeners.esps;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CorpseESPListener {
    @SubscribeEvent
    public void renderEntity(RenderWorldLastEvent event) {
        if (Main.settings.corpseEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        Minecraft.getMinecraft().theWorld.loadedEntityList.forEach(e -> {
            if (e instanceof EntityArmorStand
                    && ((EntityArmorStand) e).getShowArms()
                    && e.isInvisible()
                    && ((EntityArmorStand) e).getCurrentArmor(3) != null
                    && ((EntityArmorStand) e).getCurrentArmor(3).getItem() == Item.getItemById(169)) // Sea lantern
                ESPServices.drawEntityBox(e, event.partialTicks, Main.settings.corpseEsp, Main.settings.corpseEspColor);
        });
    }
}
