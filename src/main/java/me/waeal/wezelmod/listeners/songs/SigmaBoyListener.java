package me.waeal.wezelmod.listeners.songs;

import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.listeners.SongListener;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SigmaBoyListener extends SongListener {
    public SigmaBoyListener() {
        super("sigma boy",

              "Sigma, sigma boy, sigma boy, sigma boy!",
              "Каждая девчонка хочет танцевать с тобой!",
              "Sigma, sigma boy, sigma boy, sigma boy!!",
              "Я такая вся, что добиваться будешь год!!",
              "Sigma, sigma boy, sigma boy, sigma boy!!!",
              "Каждая девчонка хочет танцевать с тобой!!!",
              "Sigma, sigma boy, sigma boy, sigma boy!!!!",
              "Я такая вся, что добиваться будешь год!!!!");
    }

    @SubscribeEvent
    public void chatReceivedEvent(ClientChatReceivedEvent event) {
        if (!Main.settings.sigmaBoy)
            MinecraftForge.EVENT_BUS.unregister(this);
        play(event.message.getUnformattedText());
    }
}
