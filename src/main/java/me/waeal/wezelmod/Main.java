package me.waeal.wezelmod;

import me.waeal.wezelmod.commands.*;
import me.waeal.wezelmod.handlers.MacroAreaHandler;
import me.waeal.wezelmod.handlers.PacketEventHandler;
import me.waeal.wezelmod.listeners.*;
import me.waeal.wezelmod.listeners.esps.*;
import me.waeal.wezelmod.listeners.songs.*;
import me.waeal.wezelmod.objects.macros.*;
import me.waeal.wezelmod.objects.Settings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "wem", name = "WezelMod", version = "1.8.9-MOD_ALPHA")
public class Main {
	public static Settings settings;
	public static MacroConfig macroConfig;

	public static KeyBinding freeMouseBind = new KeyBinding("Free Mouse", 0, "WezelMod");

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception {
		macroConfig = MacroConfigManager.loadConfig(event);
		MacroConfigManager.saveConfig(macroConfig);

		settings = new Settings(event.getModConfigurationDirectory().getPath());
		settings.initialize();
		settings.markDirty();
		settings.loadData();

		initSettings();

		Launch.classLoader.registerTransformer("me.waeal.wezelmod.objects.ClassTransformer");
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ClientRegistry.registerKeyBinding(freeMouseBind);
		MinecraftForge.EVENT_BUS.register(freeMouseKeybindListener);
		MinecraftForge.EVENT_BUS.register(macroAreaListener);

		ClientCommandHandler.instance.registerCommand(wezelCommand.getCommand());
		ClientCommandHandler.instance.registerCommand(wezelMacroCommand.getCommand());

		MinecraftForge.EVENT_BUS.register(packetEventHandler);
	}

	private void initSettings() throws Exception {
		settings.regBooleanListener("copyChat" , chatGuiListener);
		settings.regBooleanListener("pickPing" , pickPingListener);
		settings.regBooleanListener("harp" , harpGuiListener);
		settings.regBooleanListener("melody" , melodyGuiListener);

		settings.regBooleanListener("thickOfIt" , thickOfItListener);
		settings.regBooleanListener("sigmaBoy" , sigmaBoyListener);

		settings.regBooleanListener("macroToggle" , macroListener);

		settings.regMultipleChoiceListener("mobEsp" , mobESPListener, 1);
		settings.regMultipleChoiceListener("chestEsp" , chestESPListener, 1);
		settings.regMultipleChoiceListener("corpseEsp" , corpseESPListener, 1);
		settings.regMultipleChoiceListener("witherEsp" , witherESPListener, 1);
	}

	public static WezelCommand wezelCommand = new WezelCommand();
	public static WezelMacroCommand wezelMacroCommand = new WezelMacroCommand();
	public static PacketEventHandler packetEventHandler = new PacketEventHandler();
	public static MacroAreaHandler macroAreaHandler = new MacroAreaHandler();
	public static MacroAreaListener macroAreaListener = new MacroAreaListener(macroAreaHandler);

	public static MacroListener macroListener = new MacroListener();
	public static FreeMouseKeybindingListener freeMouseKeybindListener = new FreeMouseKeybindingListener();

	public static PickPingListener pickPingListener = new PickPingListener();

	public static ThickOfItListener thickOfItListener = new ThickOfItListener();
	public static SigmaBoyListener sigmaBoyListener = new SigmaBoyListener();

	public static ChatGuiListener chatGuiListener = new ChatGuiListener();
	public static HarpGuiListener harpGuiListener = new HarpGuiListener();
	public static MelodyGuiListener melodyGuiListener = new MelodyGuiListener();

	public static MobESPListener mobESPListener = new MobESPListener();
	public static ChestESPListener chestESPListener = new ChestESPListener();
	public static CorpseESPListener corpseESPListener = new CorpseESPListener();
	public static WitherESPListener witherESPListener = new WitherESPListener();
}
