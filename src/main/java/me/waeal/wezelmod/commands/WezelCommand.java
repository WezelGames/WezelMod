package me.waeal.wezelmod.commands;

import me.waeal.wezelmod.services.WezelServices;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WezelCommand extends CommandBase {
    boolean open = false;
    @Autowired
    WezelServices services;

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "wezel";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "wezel";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        open = true;
        MinecraftForge.EVENT_BUS.register(this);
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
