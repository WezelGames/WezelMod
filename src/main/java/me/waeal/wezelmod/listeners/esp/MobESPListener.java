package me.waeal.wezelmod.listeners.esp;

import java.util.HashSet;
import java.util.List;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MobESPListener {
    @Autowired
    ESPServices service;

    private final HashSet<Entity> checked = new HashSet<>();
    private final HashSet<Entity> starMobs = new HashSet<>();

    public void checkForMobs(Entity entity) {
        System.out.println("LF Mobs");
        List<Entity> possibleStars = entity.getEntityWorld().getEntitiesInAABBexcluding(
                entity,
                entity.getEntityBoundingBox().offset(0d, -1d, 0d),
                e -> !(e instanceof EntityArmorStand));

        for (Entity e : possibleStars) {
            System.out.println("Possible star mob at " + e.getPosition().getX() + " - " + e.getPosition().getY() + " - " + e.getPosition().getZ());
            if (!(!starMobs.contains(e) &&
                    ((e instanceof EntityPlayer && !e.isInvisible() && e.getUniqueID().version() == 2 && e != Minecraft.getMinecraft().thePlayer)
                    || !(e instanceof EntityWither))))
                continue;

            starMobs.add(e);
            break;
        }

        checked.add(entity);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (Main.settings.mobEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        starMobs.clear();
        checked.clear();
    }

    @SubscribeEvent
    public void renderEntity(RenderWorldLastEvent event) {
        if (Main.settings.mobEsp == 0)
            onWorldLoad(null); // Clears the mob sets and unregisters the event listeners

        Minecraft.getMinecraft().theWorld.loadedEntityList.forEach(e -> {
            if (e instanceof EntityArmorStand
                    && e.hasCustomName()
                    && e.getCustomNameTag().contains("✯")
                    && !checked.contains(e))
                checkForMobs(e);

            if (starMobs.contains(e))
                service.drawEntityBox(e, event.partialTicks);
        });
    }
}
