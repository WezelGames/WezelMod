package me.waeal.wezelmod.commands;

import me.waeal.wezelmod.services.ProfileServices;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PVCommand extends CommandBase {
    boolean open = false;
    @Autowired
    ProfileServices services;
    String player;

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return "pv";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "pv";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        open = true;
        MinecraftForge.EVENT_BUS.register(this);
        if (args.length >= 1)
            player = args[0];
        else
            player = Minecraft.getMinecraft().thePlayer.getName();
    }

    @SubscribeEvent
    public void tick(TickEvent e) {
        MinecraftForge.EVENT_BUS.unregister(this);
        if (!open)
            return;

        open = false;
        services.showInventory(player);
    }
}
