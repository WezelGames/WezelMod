package me.waeal.bootymod.listeners;

import me.waeal.bootymod.Booty;
import me.waeal.bootymod.services.ChatServices;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ChatGuiListener {
    @Autowired
    private ChatServices services;

    @SubscribeEvent
    public void chatGuiClickEvent(GuiScreenEvent.MouseInputEvent.Pre event) {
        if (!Booty.settings.copyChat)
            MinecraftForge.EVENT_BUS.unregister(this);
        if (!(event.gui instanceof GuiChat && Mouse.getEventButtonState() && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) || Mouse.getEventButton() < 0)
            return;

        services.copyChat();
    }
}
