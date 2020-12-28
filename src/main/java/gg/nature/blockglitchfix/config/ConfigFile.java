package gg.nature.blockglitchfix.config;

import gg.nature.blockglitchfix.BlockGlitchFix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigFile extends YamlConfiguration {

    public ConfigFile(String name) {
        File file = new File(BlockGlitchFix.getInstance().getDataFolder(), name);

        if (!file.exists()) {
            BlockGlitchFix.getInstance().saveResource(name, false);
        }

        try {
            this.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    
    public boolean getBoolean(String path) {
        return super.getBoolean(path, false);
    }
    
    public String getString(String path) {
        return ChatColor.translateAlternateColorCodes('&', super.getString(path, ""));
    }
}
