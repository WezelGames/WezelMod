package me.waeal.wezelmod.listeners.esps;

import java.util.HashSet;
import java.util.List;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WitherESPListener {
    private final HashSet<Entity> withers = new HashSet<>();
    private final HashSet<Entity> checked = new HashSet<>();

    public void checkForMobs(Entity e) {
        List<Entity> found = e.getEntityWorld().getEntitiesInAABBexcluding(
                e, e.getEntityBoundingBox().offset(0d, -1d, 0d),
                entity -> entity instanceof EntityWither
                        && !entity.isInvisible());

        if (found.isEmpty())
            return;

        withers.addAll(found);
        checked.add(e);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load event) {
        if (Main.settings.witherEsp == 0)
            MinecraftForge.EVENT_BUS.unregister(this);

        withers.clear();
        checked.clear();
    }

    @SubscribeEvent
    public void renderEntity(RenderWorldLastEvent event) {
        if (Main.settings.witherEsp == 0)
            onWorldLoad(null);

        Minecraft.getMinecraft().theWorld.loadedEntityList.forEach(e -> {
            if (!(e instanceof EntityArmorStand
                    && !checked.contains(e)
                    && e.hasCustomName()
                    && (e.getCustomNameTag().contains("Maxor")
                    || e.getCustomNameTag().contains("Storm")
                    || e.getCustomNameTag().contains("Goldor")
                    || e.getCustomNameTag().contains("Necron"))))
                checkForMobs(e);

            if (!withers.contains(e))
                return;

            ESPServices.drawEntityBox(e, event.partialTicks, Main.settings.witherEsp, Main.settings.witherEspColor);
        });
    }
}
