package me.waeal.wezelmod.objects;

import gg.essential.vigilance.Vigilant;
import gg.essential.vigilance.data.Property;
import gg.essential.vigilance.data.PropertyType;
import java.awt.*;
import me.waeal.wezelmod.Main;
import me.waeal.wezelmod.services.WezelServices;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.File;

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

        this.registerListener(getClass().getField("macroToggle"), cc -> {
            if (!((boolean) cc))
                Main.macroConfig.interrupt();
        });

        this.addDependency(getClass().getField("pickDelay"), getClass().getField("pickPing"));

        this.addDependency(getClass().getField("harpDelay"), getClass().getField("harp"));

        this.addDependency(getClass().getField("melodySkip"), getClass().getField("melody"));
        this.addDependency(getClass().getField("melodyOnlySides"), getClass().getField("melodySkip"));
        this.addDependency(getClass().getField("melodySkipDelay"), getClass().getField("melodySkip"));
        this.addDependency(getClass().getField("melodyRandomSkipDelay"), getClass().getField("melodySkip"));
    }

    @Property(type = PropertyType.PARAGRAPH,
            name = "Mod ID Hider (comma separated)",
            description = "Will hide mod ID's, comma separated",
            category = "Other Mods")
    public String hiddenModIds = "wem";

    @Property(type = PropertyType.SWITCH,
            name = "In the thick of it",
            description = "Will respond to anyone saying the thick of it",
            category = "Songs")
    public boolean thickOfIt = false;

    @Property(type = PropertyType.SWITCH,
            name = "Sigma sigma boy",
            description = "Will respond to anyone saying sigma",
            category = "Songs")
    public boolean sigmaBoy = false;

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
            max = 150)
    public int melodySkipDelay = 80;

    @Property(type = PropertyType.SLIDER,
            name = "Auto Melody - Random skip delay (ms)",
            description = "Adds a layer of randomness to the delay between clicks (higher is safer, but also less likely to skip)",
            category = "QOL",
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

    @Property(type = PropertyType.SWITCH,
            name = "Proxy(SOCKS5 only) Toggle",
            description = "Proxy toggle",
            category = "Proxy")
    public boolean proxyEnabled = false;

    @Property(type = PropertyType.TEXT,
            name = "IP",
            description = "IP of the proxy to connect to",
            category = "Proxy")
    public String proxyIP = "";

    @Property(type = PropertyType.TEXT,
            name = "Port",
            description = "Port of the proxy to connect to",
            category = "Proxy")
    public String proxyPort = "8080";

    @Property(type = PropertyType.TEXT,
            name = "Username",
            description = "Username, in case it's needed for the proxy",
            category = "Proxy")
    public String proxyUser = "";

    @Property(type = PropertyType.TEXT,
            name = "Password",
            description = "Password, in case it's needed for the proxy",
            category = "Proxy")
    public String proxyPass = "";

    @Property(type = PropertyType.SWITCH,
            name = "Macro General Toggle",
            description = "General macro toggle",
            category = "Macro")
    public boolean macroToggle = false;

    @Property(type = PropertyType.COLOR,
            name = "Menu Background Color",
            description = "Color picker to choose the background color of the macro menu",
            category = "Macro")
    public Color macroBackgroundColor = new Color(0, 0, 0, 50);

    @Property(type = PropertyType.COLOR,
            name = "Area Highlight Color",
            description = "Color picker to choose the color of a highlighted area",
            category = "Macro")
    public Color macroAreaColor = new Color(50, 0, 50, 50);

    @Property(type = PropertyType.COLOR,
            name = "Position Highlight Color",
            description = "Color picker to choose the color of a position",
            category = "Macro")
    public Color macroPosColor = new Color(0, 0, 255, 50);

    @Property(type = PropertyType.SWITCH,
            name = "Copy Chat - The same way it looks for messages",
            description = "Ctrl + click to copy chat messages",
            category = "Macro")
    public boolean copyChat = false;

    @Property(type = PropertyType.BUTTON,
            name = "Open Menu - /wezelmacro",
            description = "Button to open the menu",
            category = "Macro")
    public void openMacroMenu() {
        if (!macroToggle)
            Minecraft.getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(EnumChatFormatting.GREEN + "Enable " + EnumChatFormatting.RESET + "the macro toggle in the general settings menu first! (/wezel -> Macro)"));
        else
            WezelServices.openMacroNavGui();
    }
}