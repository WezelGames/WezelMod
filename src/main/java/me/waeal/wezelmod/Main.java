package me.waeal.wezelmod;

import me.waeal.wezelmod.commands.*;
import me.waeal.wezelmod.handlers.PacketEventHandler;
import me.waeal.wezelmod.listeners.*;
import me.waeal.wezelmod.listeners.esp.*;
import me.waeal.wezelmod.objects.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "wem", name = "WezelMod", version = "1.8.9-MOD_ALPHA")
public class Main {
	public static Settings settings;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception {
		settings = new Settings(event.getModConfigurationDirectory().getPath());
		settings.initialize();
		settings.markDirty();
		settings.loadData();
		initSettings();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(wezelCommand.getCommand());
		MinecraftForge.EVENT_BUS.register(packetEventHandler);
	}

	private void initSettings() throws Exception {
		settings.regBooleanListener("copyChat" , chatGuiListener);
		settings.regBooleanListener("pickPing" , pickPingListener);
		settings.regBooleanListener("harp" , harpGuiListener);
		settings.regBooleanListener("melody" , melodyGuiListener);
		settings.regBooleanListener("thickOfIt" , thickOfItListener);
		settings.regMultipleChoiceListener("mobEsp" , mobEspListener, 1);
		settings.regMultipleChoiceListener("chestEsp" , chestESPListener, 1);
		settings.regMultipleChoiceListener("corpseEsp" , corpseESPListener, 1);
		settings.regMultipleChoiceListener("witherEsp" , witherESPListener, 1);
	}

	public WezelCommand wezelCommand = new WezelCommand();
	public PacketEventHandler packetEventHandler = new PacketEventHandler();

	public ThickOfItListener thickOfItListener = new ThickOfItListener();
	public PickPingListener pickPingListener = new PickPingListener();

	public ChatGuiListener chatGuiListener = new ChatGuiListener();
	public HarpGuiListener harpGuiListener = new HarpGuiListener();
	public MelodyGuiListener melodyGuiListener = new MelodyGuiListener();

	public MobESPListener mobEspListener = new MobESPListener();
	public ChestESPListener chestESPListener = new ChestESPListener();
	public CorpseESPListener corpseESPListener = new CorpseESPListener();
	public WitherESPListener witherESPListener = new WitherESPListener();
}
