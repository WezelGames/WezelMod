package me.waeal.wezelmod.commands;

import me.waeal.wezelmod.objects.Command;
import me.waeal.wezelmod.services.WezelServices;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WezelCommand {
    boolean open = false;
    @Autowired
    WezelServices services;

    private final Command command = new Command ("wezel", "wezel", (sender, args) -> {
        open = true;
        MinecraftForge.EVENT_BUS.register(this);
    });

    public Command getCommand() {
        return command;
    }

    @SubscribeEvent
    public void tick(TickEvent e) {
        MinecraftForge.EVENT_BUS.unregister(this);
        if (!open)
            return;

        open = false;
        services.openSettingsGui();
    }
}
