package me.waeal.wezelmod.objects;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class Command extends CommandBase {
    private String name, usage;

    public void setName(String name) {
        this.name = name;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public void setProcessCommand(Runnable processCommand) {
        this.processCommand = processCommand;
    }

    private Runnable processCommand;

    public Command(String name, String usage, Runnable processCommand) {
        this.name = name;
        this.usage = usage;
        this.processCommand = processCommand;
    }


    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return usage;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        processCommand.run(sender,args);
    }
}
