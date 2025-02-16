package me.waeal.wezelmod.listeners.esp;

import java.util.HashSet;
import java.util.List;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MobESPListener {

    private final HashSet<Entity> checked = new HashSet<>();
    private final HashSet<Entity> starMobs = new HashSet<>();

    public void checkForMobs(Entity entity) {
        List<Entity> possibleStars = entity.getEntityWorld().getEntitiesInAABBexcluding(
                entity,
                entity.getEntityBoundingBox().offset(0d, -1d, 0d),
                e -> !(e instanceof EntityArmorStand));

        for (Entity e : possibleStars) {
            if (starMobs.contains(e) || e == Minecraft.getMinecraft().thePlayer)
                continue;

            starMobs.add(e);
            checked.add(entity);
            break;
        }

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
                    && !checked.contains(e)
                    && e.hasCustomName()
                    && (e.getCustomNameTag().matches(".*✯.*❤")
                    || e.getCustomNameTag().contains("Shadow Assassin")
                    || e.getCustomNameTag().contains("Adventurer")
                    || e.getCustomNameTag().contains("Archeologist")))
                checkForMobs(e);

            if (e instanceof EntityPlayer
                    && !starMobs.contains(e)
                    && e.isInvisible()
                    && ((EntityPlayer) e).getCurrentArmor(0) != null
                    && ((EntityPlayer) e).getCurrentArmor(0).getItem() == Item.getItemById(301))
                starMobs.add(e);

            if (!starMobs.contains(e))
                return;

            ESPServices.drawEntityBox(e, event.partialTicks, Main.settings.mobEsp, Main.settings.mobEspColor);
            if (e.isInvisible())
                e.setInvisible(false);
        });
    }
}
