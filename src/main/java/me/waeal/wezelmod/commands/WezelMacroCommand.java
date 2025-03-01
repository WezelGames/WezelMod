package me.waeal.wezelmod.commands;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.objects.Command;
import me.waeal.wezelmod.objects.macros.MacroConfigManager;
import me.waeal.wezelmod.services.WezelServices;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class WezelMacroCommand {
    boolean open = false;

    private final Command command = new Command ("wezelmacro", "wezelmacro", (sender, args) -> {
        if (args.length == 0) {
            if (!Main.settings.macroToggle) {
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enable " + EnumChatFormatting.RESET + "the macro toggle in the general settings menu first! (/wezel -> Macro)"));
                return;
            }

            open = true;
            MinecraftForge.EVENT_BUS.register(this);
            return;
        }

        if (!(args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("toggle"))) {
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Incorrect usage of the command /wezelmacro (Optional: toggle|enable|disable) (Optional: <Macro Category>) (Optional: <Macro Name>)"));
            return;
        }

        boolean disable = !args[0].equalsIgnoreCase("disable");
        boolean enable = args[0].equalsIgnoreCase("enable");

        if (args.length == 1) {
            Main.settings.macroToggle = (!Main.settings.macroToggle && disable) || enable;
            Main.settings.markDirty();
            if (Main.settings.macroToggle)
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled " + EnumChatFormatting.RESET + "all macros"));
            else
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Disabled " + EnumChatFormatting.RESET + "all macros"));
        } else if (args.length == 2
                && Main.macroConfig.getCategories().containsKey(args[1])) {
            Main.macroConfig.getCategory(args[1]).setEnabled((!Main.macroConfig.getCategory(args[1]).isEnabled() && disable) || enable);
            MacroConfigManager.saveConfig(Main.macroConfig);
            if (Main.macroConfig.getCategory(args[1]).isEnabled())
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled " + EnumChatFormatting.RESET + "all macros in the " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET + " category"));
            else
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Disabled " + EnumChatFormatting.RESET + "all macros in the " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET + " category"));
        } else if (args.length == 3
                && Main.macroConfig.getCategories().containsKey(args[1])
                && Main.macroConfig.getCategory(args[1]).getMacros().containsKey(args[2])) {
            Main.macroConfig.getCategory(args[1]).getMacro(args[2]).setEnabled((!Main.macroConfig.getCategory(args[1]).getMacro(args[2]).isEnabled() && disable) || enable);
            MacroConfigManager.saveConfig(Main.macroConfig);
            if (Main.macroConfig.getCategory(args[1]).getMacro(args[2]).isEnabled())
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enabled " + EnumChatFormatting.RESET + "the " + EnumChatFormatting.AQUA + args[2] + EnumChatFormatting.RESET + " macro in the " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET + " category"));
            else
                Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Disabled " + EnumChatFormatting.RESET + "the " + EnumChatFormatting.AQUA + args[2] + EnumChatFormatting.RESET + " macro in the " + EnumChatFormatting.GOLD + args[1] + EnumChatFormatting.RESET + " category"));
        } else
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.RED + "Incorrect usage of the command /wezelmacro (Optional: toggle|enable|disable) (Optional: <Macro Category>) (Optional: <Macro Name>)" + EnumChatFormatting.RESET + "\nPlease check the spelling of your Macro Category and/or Macro Name"));
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
        WezelServices.openMacroNavGui();
    }
}
