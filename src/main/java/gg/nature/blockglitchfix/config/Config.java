package gg.nature.blockglitchfix.config;

import gg.nature.blockglitchfix.BlockGlitchFix;

public class Config {

    public static String DENY_MESSAGE;

    public static boolean ENTITY_TYPE_ENABLED;
    public static boolean SIGN_TYPE_ENABLED;
    public static boolean MINECART_TYPE_ENABLED;
    public static boolean PHASE_TYPE_ENABLED;
    
    public Config() {
        ConfigFile config = BlockGlitchFix.getInstance().getConfig();

        DENY_MESSAGE = config.getString("DENY_MESSAGE");

        ENTITY_TYPE_ENABLED = config.getBoolean("TYPE.ENTITY_FIX_ENABLED");
        SIGN_TYPE_ENABLED = config.getBoolean("TYPE.SIGN_FIX_ENABLED");
        MINECART_TYPE_ENABLED = config.getBoolean("TYPE.MINECART_FIX_ENABLED");
        PHASE_TYPE_ENABLED = config.getBoolean("TYPE.PHASE_FIX_ENABLED");
    }
}
