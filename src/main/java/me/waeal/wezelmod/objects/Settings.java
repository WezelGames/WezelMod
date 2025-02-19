package me.waeal.wezelmod.objects;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.*;
import java.awt.*;
import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommandSender;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;


import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class Settings extends Vigilant {
    public void regBooleanListener(@NotNull @NonNls String field, Object listener) throws Exception {
        this.registerListener(getClass().getField(field), cc -> {
            if ((boolean) cc)
                MinecraftForge.EVENT_BUS.register(listener);
            else
                MinecraftForge.EVENT_BUS.unregister(listener);
        });
        if (getClass().getField(field).getBoolean(this))
            MinecraftForge.EVENT_BUS.register(listener);
    }

    public void regMultipleChoiceListener(@NotNull @NonNls String field, Object listener, int threshold) throws Exception {
        this.registerListener(getClass().getField(field), cc -> {
            if ((int) cc >= threshold)
                MinecraftForge.EVENT_BUS.register(listener);
            else
                MinecraftForge.EVENT_BUS.unregister(listener);
        });
        if (getClass().getField(field).getInt(this) >= threshold)
            MinecraftForge.EVENT_BUS.register(listener);
    }

    public Settings(String configDir) throws Exception {
        super(new File(configDir + "/WezelMod.toml"), "WezelMod");

        this.addDependency(getClass().getField("pickDelay"), getClass().getField("pickPing"));

        this.addDependency(getClass().getField("harpDelay"), getClass().getField("harp"));

        this.addDependency(getClass().getField("melodySkip"), getClass().getField("melody"));
        this.addDependency(getClass().getField("melodyOnlySides"), getClass().getField("melodySkip"));
        this.addDependency(getClass().getField("melodySkipDelay"), getClass().getField("melodySkip"));
        this.addDependency(getClass().getField("melodyRandomSkipDelay"), getClass().getField("melodySkip"));
    }

    @Property(type = PropertyType.SWITCH,
            name = "Copy Chat",
            description = "Ctrl + click to copy chat messages",
            category = "Hidden",
            hidden = true)
    public boolean copyChat = false;

    @Property(type = PropertyType.SWITCH,
            name = "In the thick of it",
            description = "Will respond to anyone saying the thick of it",
            category = "QOL")
    public boolean thickOfIt = false;

    @Property(type = PropertyType.SWITCH,
            name = "Pick ping (M7 time Coop)",
            description = "Adds ping to pickaxes",
            category = "QOL")
    public boolean pickPing = false;

    @Property(type = PropertyType.SLIDER,
            name = "Pick ping - Delay time (ms)",
            description = "Set the delay before packets are sent to the server",
            category = "QOL",
            max = 1000)
    public int pickDelay = 150;

    @Property(type = PropertyType.SWITCH,
            name = "Auto Harp",
            description = "Automatically does the harp",
            category = "QOL")
    public boolean harp = false;

    @Property(type = PropertyType.SLIDER,
            name = "Auto Harp - Click delay (in case ur name is Coop)",
            description = "Set the delay for clicks",
            category = "QOL",
            min = 0,
            max = 200)
    public int harpDelay = 0;

    @Property(type = PropertyType.SWITCH,
            name = "Auto Melody",
            description = "Automatically does the melody",
            category = "QOL")
    public boolean melody = false;

    @Property(type = PropertyType.SWITCH,
            name = "Auto Melody - Skip",
            description = "Automatically does the melody, and attempts the skip",
            category = "QOL")
    public boolean melodySkip = false;

    @Property(type = PropertyType.SWITCH,
            name = "Auto Melody - Only Sides",
            description = "Automatically does the melody, and attempts the skip, but only on the sides",
            category = "QOL")
    public boolean melodyOnlySides = false;

    @Property(type = PropertyType.SLIDER,
            name = "Auto Melody - Skip delay (ms)",
            description = "Set the delay between clicks (higher is safer, but also less likely to skip)",
            category = "QOL",
            min = 0,
            max = 150)
    public int melodySkipDelay = 80;

    @Property(type = PropertyType.SLIDER,
            name = "Auto Melody - Random skip delay (ms)",
            description = "Adds a layer of randomness to the delay between clicks (higher is safer, but also less likely to skip)",
            category = "QOL",
            min = 0,
            max = 150)
    public int melodyRandomSkipDelay = 20;

    @Property(type = PropertyType.SELECTOR,
            name = "Mob ESP",
            description = "Highlights all star mobs with their bounding boxes",
            category = "ESP",
            options = {"Off", "Lines", "Filled", "Filled and Lines"})
    public int mobEsp = 0;

    @Property(type = PropertyType.COLOR,
            name = "Mob ESP Color",
            description = "Color picker to choose what color to highlight mobs with",
            category = "ESP")
    public Color mobEspColor = new Color(255, 0, 0, 10);

    @Property(type = PropertyType.SELECTOR,
            name = "Corpse ESP",
            description = "Highlights all mineshaft corpses with their bounding boxes",
            category = "ESP",
            options = {"Off", "Lines", "Filled", "Filled and Lines"})
    public int corpseEsp = 0;

    @Property(type = PropertyType.COLOR,
            name = "Corpse ESP Color",
            description = "Color picker to choose what color to highlight corpses with",
            category = "ESP")
    public Color corpseEspColor = new Color(255, 0, 0, 10);

    @Property(type = PropertyType.SELECTOR,
            name = "Chest ESP",
            description = "Highlights all chests with their bounding boxes",
            category = "ESP",
            options = {"Off", "Lines", "Filled", "Filled and Lines"})
    public int chestEsp = 0;

    @Property(type = PropertyType.COLOR,
            name = "Chest ESP Color",
            description = "Color picker to choose what color to highlight chests with",
            category = "ESP")
    public Color chestEspColor = new Color(0, 255, 0, 10);

    @Property(type = PropertyType.SELECTOR,
            name = "Wither ESP",
            description = "Highlights all withers with their bounding boxes",
            category = "ESP",
            options = {"Off", "Lines", "Filled", "Filled and Lines"})
    public int witherEsp = 0;

    @Property(type = PropertyType.COLOR,
            name = "Wither ESP Color",
            description = "Color picker to choose what color to highlight withers with",
            category = "ESP")
    public Color witherEspColor = new Color(9, 9, 9, 110);

    @Property(type = PropertyType.BUTTON,
            name = "White Space - Does nothing",
            description = "White Space for the color picker above",
            category = "ESP")
    public void whiteSpace() {
        Minecraft.getMinecraft().thePlayer.sendChatMessage("MEOW");
    }

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