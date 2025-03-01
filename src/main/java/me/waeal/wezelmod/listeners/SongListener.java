package me.waeal.wezelmod.listeners;

import java.util.ArrayList;
import net.minecraft.client.Minecraft;

public class SongListener {
    protected final String trigger;
    protected final String[] response;
    private final ArrayList<String> responseTriggers = new ArrayList<>(); // response elements that contain trigger.

    protected SongListener(String trigger, String... response) {
        this.trigger = trigger;
        this.response = response;

        for (String str : response) {
            if (str.toLowerCase().contains(trigger.toLowerCase()))
                responseTriggers.add(str);
        }
    }

    private static Thread songThread;

    protected void play(String unformattedText) {
        if (!unformattedText.toLowerCase().contains(trigger.toLowerCase())
                || isPartOfResponse(unformattedText))
            return;

        if (songThread != null)
            songThread.interrupt();

        songThread = new Thread(() -> {
            try {
                for (String str : response) {
                    Thread.sleep(1700);
                    if (!str.isEmpty())
                        Minecraft.getMinecraft().thePlayer.sendChatMessage("/pc " + str);
                }
            } catch (InterruptedException e) {
                // killed thread
            }
        });

        songThread.start();
    }

    private boolean isPartOfResponse(String unformattedText) {
        for (String str : responseTriggers) {
            if (unformattedText.contains(str))
                return true;
        }
        return false;
    }
}
