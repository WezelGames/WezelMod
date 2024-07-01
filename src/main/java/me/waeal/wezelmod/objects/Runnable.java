package me.waeal.wezelmod.objects;

import net.minecraft.command.ICommandSender;

public interface Runnable {
    void run(ICommandSender sender, String[] args);
}
