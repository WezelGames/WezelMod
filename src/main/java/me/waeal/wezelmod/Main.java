package me.waeal.wezelmod;

import me.waeal.wezelmod.commands.*;
import me.waeal.wezelmod.listeners.*;
import me.waeal.wezelmod.listeners.esp.*;
import me.waeal.wezelmod.objects.Settings;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Mod(modid = "wem", name = "WezelMod", version = "1.8.9-MOD_ALPHA")
public class Main {
	public static Settings settings;
	private boolean constructed = false;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) throws Exception {
		settings = new Settings(event.getModConfigurationDirectory().getPath());
		settings.initialize();
		settings.markDirty();
		settings.loadData();
		if (constructed)
			initSettings();
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		SpringApplication.run(Main.class);
		/*
		ConfigurableApplicationContext ctx = SpringApplication.run(Main.class, "java.system.class.loader=gg.essential.loader.stage2.relaunch.RelaunchClassLoader");
		ctx.setClassLoader(SaxEventRecorder.class.getClassLoader());
		 */
	}

	@PostConstruct
	public void register() throws Exception {
		ClientCommandHandler.instance.registerCommand(wezelCmd.getCommand());
		constructed = true;

		if (settings != null)
			initSettings();
	}

	private void initSettings() throws Exception {
		settings.regBooleanListener("copyChat" , cgListener);
		settings.regBooleanListener("harp" , hgListener);
		settings.regBooleanListener("melody" , mgListener);
		settings.regMultipleChoiceListener("mobEsp" , meListener, 1);
		settings.regMultipleChoiceListener("chestEsp" , ceListener, 1);
	}

	@Autowired
	public WezelCommand wezelCmd;
	@Autowired
	public ChatGuiListener cgListener;
	@Autowired
	public HarpGuiListener hgListener;
	@Autowired
	public MelodyGuiListener mgListener;

	@Autowired
	public MobESPListener meListener;
	@Autowired
	public ChestESPListener ceListener;

}
