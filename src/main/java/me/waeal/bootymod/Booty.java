package me.waeal.bootymod;

import me.waeal.bootymod.commands.*;
import me.waeal.bootymod.listeners.*;
import me.waeal.bootymod.objects.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Mod(modid = "bbm", name = "BootyMod", version = "1.8.9-MOD_ALPHA")
public class Booty {
	public static Settings settings;
	private static String dir;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception {
		dir = event.getModConfigurationDirectory().getPath();
		initSettings();
	}

	private void initSettings() throws Exception {
		if (settings != null)
			return;

		settings = new Settings(dir);
		settings.initialize();
		settings.markDirty();
		settings.loadData();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		SpringApplication.run(Booty.class, "Booty");
	}

	@PostConstruct
	public void register() throws Exception {
		initSettings();

		ClientCommandHandler.instance.registerCommand(pvCmd);
		ClientCommandHandler.instance.registerCommand(bootyCmd);

		if (settings.copyChat)
			MinecraftForge.EVENT_BUS.register(cgListener);
		settings.setCgListener(cgListener);
		if (settings.displayDungeonStats)
			MinecraftForge.EVENT_BUS.register(cmListener);
		settings.setCmListener(cmListener);
	}

	@Autowired
	public PVCommand pvCmd;
	@Autowired
	public BootyCommand bootyCmd;
	@Autowired
	public ChatGuiListener cgListener;
	@Autowired
	public ChatMsgListener cmListener;

}
