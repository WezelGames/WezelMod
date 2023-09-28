package me.waeal.wezelmod.objects;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import me.waeal.wezelmod.listeners.ChatGuiListener;
import me.waeal.wezelmod.listeners.ChatMsgListener;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Settings extends Vigilant {
    ChatGuiListener cgListener;
    public void setCgListener(ChatGuiListener cgListener) {
        this.cgListener = cgListener;
    }

    ChatMsgListener cmListener;
    public void setCmListener(ChatMsgListener cmListener) {
        this.cmListener = cmListener;
    }

    public Settings(String configDir) throws Exception {
        super(new File(configDir + "/WezelMod.toml"), "WezelMod");

        this.registerListener(getClass().getField("copyChat"), cc -> {
            if ((boolean) cc)
                MinecraftForge.EVENT_BUS.register(cgListener);
            else
                MinecraftForge.EVENT_BUS.unregister(cgListener);
        });

        this.registerListener(getClass().getField("displayDungeonStats"), cc -> {
            if ((boolean) cc)
                MinecraftForge.EVENT_BUS.register(cmListener);
            else
                MinecraftForge.EVENT_BUS.unregister(cmListener);
        });
    }

    @Property(type = PropertyType.SWITCH,
    name = "Copy Chat",
    description = "Ctrl + click to copy chat messages",
    category = "QOL")
    public boolean copyChat = false;

    @Property(type = PropertyType.SWITCH,
            name = "Display Dungeons Stats",
            description = "Displays dungeons statistics from a player whenever they join the party through party finder",
            category = "QOL")
    public boolean displayDungeonStats = false;
}

class Sorting extends SortingBehavior {
    @NotNull
    @Override
    public Comparator<? super Category> getCategoryComparator() {
        return (o1, o2) -> 0;
    }
    @NotNull
    @Override
    public Comparator<? super Map.Entry<String, ? extends List<PropertyData>>> getSubcategoryComparator() {
        return (o1, o2) -> 0;
    }
}