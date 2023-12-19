package cloud.viniciusith.gohome.config;

import cloud.viniciusith.gohome.GoHomeMod;
import com.mojang.datafixers.util.Pair;

public class ModConfig {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean TRANS_DIM;

    public static boolean RECALL_POTION;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();
        CONFIG = SimpleConfig.of(GoHomeMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    public static void reloadConfigs() {
        CONFIG = SimpleConfig.of(GoHomeMod.MOD_ID + "config").provider(configs).request();
        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("teleport.transdim", true), "Allow trans-dimensional teleport");

        // Items
        configs.addKeyValuePair(new Pair<>("item.recall_potion", true), "Enables recall potion");
    }

    private static void assignConfigs() {
        TRANS_DIM = CONFIG.getOrDefault("teleport.transdim", true);
        RECALL_POTION = CONFIG.getOrDefault("item.recall_potion", true);

        GoHomeMod.LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
    }

}
