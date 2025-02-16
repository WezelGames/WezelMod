package me.waeal.wezelmod.listeners;

import me.waeal.wezelmod.Main;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ThickOfItListener {
    private static final String[] response = {"I'm in the thick of it, everybody knows",
            "They know me where it snows, I skied in and they froze",
            "I don't know no nothin' 'bout no ice, I'm just cold",
            "Forty somethin' milli' subs or so, I've been told",
            "",
            "From the screen to the ring, to the pen, to the king",
            "Where's my crown? That's my bling",
            "Always drama when I ring",
            "See, I believe that if I see it in my heart",
            "Smash through the ceiling 'cause I'm reaching for the stars"};

    private Thread toiThread;

    @SubscribeEvent
    public void chatReceivedEvent(ClientChatReceivedEvent event) {
        if (!Main.settings.thickOfIt)
            MinecraftForge.EVENT_BUS.unregister(this);
        if (event.message.getUnformattedText().toLowerCase().contains("thick of it")
                && !event.message.getUnformattedText().contains("I'm in the thick of it, everybody knows")) {
            toiThread.interrupt();
            toiThread = new Thread(() -> {
                try {
                    for (String str : response) {
                        Thread.sleep(1700);
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc " + str);
                    }
                } catch (InterruptedException e) {
                    // killed thread
                }
            });
            toiThread.start();
        }
    }
}
