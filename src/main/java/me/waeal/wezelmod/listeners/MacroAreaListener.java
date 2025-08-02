package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.handlers.MacroAreaHandler;
import me.waeal.wezelmod.services.ESPServices;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MacroAreaListener {
    private final MacroAreaHandler handler;

    public MacroAreaListener(MacroAreaHandler handler) {
        this.handler = handler;
    }

    @SubscribeEvent
    public void renderArea(RenderWorldLastEvent event) {
        if (handler.getMacro() == null)
            return;

        ESPServices.drawArea(handler.getMacro().getRequirement().getMinX(),
                             handler.getMacro().getRequirement().getMinY(),
                             handler.getMacro().getRequirement().getMinZ(),
                             handler.getMacro().getRequirement().getMaxX(),
                             handler.getMacro().getRequirement().getMaxY(),
                             handler.getMacro().getRequirement().getMaxZ(), 2, Main.settings.macroAreaColor);

        if (handler.getPos1() == null)
            return;

        ESPServices.drawBlockBox(handler.getPos1(), 2, Main.settings.macroPosColor); // 2 is the ESP setting.
    }

    @SubscribeEvent
    public void playerInteract(PlayerInteractEvent event) {
        if (event.action == PlayerInteractEvent.Action.RIGHT_CLICK_AIR)
            return;

        handler.setPos(event.pos);
        event.setCanceled(true);
    }
}
