package me.waeal.wezelmod.listeners.esps;

import java.util.HashSet;
import java.util.List;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobESPListener {
    private final HashSet<Entity> starMobs = new HashSet<>();
    private final HashSet<Entity> checked = new HashSet<>();

    public void checkForMobs(Entity e) {
        List<Entity> found = e.getEntityWorld().getEntitiesInAABBexcluding(
                e, e.getEntityBoundingBox().offset(0d, -1d, 0d),
                entity -> !(entity instanceof EntityArmorStand)
                            && !starMobs.contains(entity)
                            && entity != Minecraft.getMinecraft().thePlayer);

        if (found.isEmpty())
            return;

        starMobs.addAll(found);
        checked.add(e);
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
            onWorldLoad(null);

        Minecraft.getMinecraft().theWorld.loadedEntityList.forEach(e -> {
            if (e instanceof EntityPlayer
                    && !starMobs.contains(e)
                    && e.isInvisible()
                    && e.getName().contains("Shadow Assassin")
                    && Minecraft.getMinecraft().thePlayer != e)
                starMobs.add(e);

            if (e instanceof EntityArmorStand
                    && !checked.contains(e)
                    && e.hasCustomName()
                    && (e.getCustomNameTag().matches(".*✯.*❤")
                    || e.getCustomNameTag().contains("Shadow Assassin")
                    || (e.getCustomNameTag().contains("Adventurer")
                    && (!e.getCustomNameTag().contains("Saul") || e.getCustomNameTag().equals("bAdventurer")))
                    || e.getCustomNameTag().contains("Archeologist")))
                checkForMobs(e);

            if (!starMobs.contains(e))
                return;

            ESPServices.drawEntityBox(e, event.partialTicks, Main.settings.mobEsp, Main.settings.mobEspColor);
            if (e.isInvisible())
                e.setInvisible(false);
        });
    }
}
