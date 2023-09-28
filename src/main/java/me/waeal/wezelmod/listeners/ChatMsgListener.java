package me.waeal.wezelmod.listeners;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.ChatServices;
import net.minecraft.nbt.NBTException;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatMsgListener {
    @Autowired
    private ChatServices service;

    private final Pattern joinMsgPattern = Pattern.compile("Dungeon Finder > (.[^ ]+) joined the dungeon group! .+");

    @SubscribeEvent
    public void chatReceivedEvent(ClientChatReceivedEvent event) throws NBTException, IOException {
        if (!Main.settings.displayDungeonStats) {
            MinecraftForge.EVENT_BUS.unregister(this);
            return;
        }
        String msg = EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getUnformattedText());

        Matcher m = joinMsgPattern.matcher(msg);
        if (m.find())
            service.displayUserInfo(m.group(1));
    }
}
