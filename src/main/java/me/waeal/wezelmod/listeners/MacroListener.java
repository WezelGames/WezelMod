package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.objects.macros.Macro;
import me.waeal.wezelmod.objects.macros.MacroCategory;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class MacroListener {
    @SubscribeEvent
    public void chatReceivedEvent(ClientChatReceivedEvent event) {
        if (!Main.settings.macroToggle)
            MinecraftForge.EVENT_BUS.unregister(this);

        for (MacroCategory category : Main.macroConfig.getCategories().values()) {
            if (!category.isEnabled())
                continue;

            for (Macro macro : category.getMacros().values()) {
                if (macro.getRequirement().isMet(event.message.getUnformattedText()) && macro.isEnabled())
                    macro.execute();
            }
        }
    }

    @SubscribeEvent
    public void tickEvent(TickEvent.PlayerTickEvent event) {
        if (!Main.settings.macroToggle)
            MinecraftForge.EVENT_BUS.unregister(this);

        if (event.player == null || event.phase != TickEvent.Phase.START) {
            return;
        }

        for (MacroCategory category : Main.macroConfig.getCategories().values()) {
            if (!category.isEnabled())
                continue;

            for (Macro macro : category.getMacros().values()) {
                if (macro.getRequirement().isMet(event.player.posX, event.player.posY, event.player.posZ) && macro.isEnabled())
                    macro.execute();
            }
        }
    }
}
