package gg.nature.blockglitchfix;

import gg.nature.blockglitchfix.config.Config;
import gg.nature.blockglitchfix.config.ConfigFile;
import gg.nature.blockglitchfix.handler.BlockGlitchHandler;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BlockGlitchFix extends JavaPlugin {

    @Getter private static BlockGlitchFix instance;

    private ConfigFile config;
    private BlockGlitchHandler blockGlitchHandler;

    @Override
    public void onEnable() {
        instance = this;

        this.config = new ConfigFile("config.yml");

        new Config();

        this.blockGlitchHandler = new BlockGlitchHandler();
    }

    @Override
    public void onDisable() {
        this.blockGlitchHandler.disable();
    }
}
