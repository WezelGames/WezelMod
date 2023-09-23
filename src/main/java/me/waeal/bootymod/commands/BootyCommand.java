package me.waeal.bootymod.commands;

import me.waeal.bootymod.services.BootyServices;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BootyCommand extends CommandBase {
    boolean open = false;
    @Autowired
    BootyServices services;

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "booty";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "booty";
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
