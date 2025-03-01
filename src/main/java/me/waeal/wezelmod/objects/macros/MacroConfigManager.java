package me.waeal.wezelmod.objects.macros;

import java.io.File;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class MacroConfigManager {
    private static Configuration config;

    public static MacroConfig loadConfig(FMLPreInitializationEvent event) {
        File configFile = new File(event.getModConfigurationDirectory(), "WezelModMacros.cfg");
        config = new Configuration(configFile);

        try {
            config.load();
        } catch (Exception e) {
            // Handle error loading config
            System.err.println("Error loading config: " + e.getMessage());
        } finally {
            if (config.hasChanged()) {
                config.save(); // Save changes if any
            }
        }

        return new MacroConfig();
    }

    public static Configuration getConfig() {
        return config;
    }

    public static void saveConfig(MacroConfig macroConfig) {
        macroConfig.save();

        if (config.hasChanged()) {
            config.save();
        }
    }
}
