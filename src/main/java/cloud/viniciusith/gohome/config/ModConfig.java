package cloud.viniciusith.gohome.config;

import cloud.viniciusith.gohome.GoHomeMod;
import com.mojang.datafixers.util.Pair;

public class ModConfig {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;


    //region General Config
    public static boolean TRANS_DIM;
    //endregion

    //region Recall Potion Config
    public static boolean ENABLE_RECALL_POTION;
    public static boolean ENABLE_NATURAL_RECALL_POTION;
    //endregion

    //region Magic Mirror Configs
    public static boolean ENABLE_MIRROR;
    public static boolean ENABLE_NATURAL_MIRROR;
    public static int MIRROR_RELOADING_TIME;
    public static int MIRROR_USE_TIME;
    //endregion

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
        configs.addKeyValuePair(
                new Pair<>("general.dimensional_teleport", true),
                "Allow inter-dimensional teleportation"
        );

        // Recall Potion
        configs.addKeyValuePair(new Pair<>("item.recall_potion", true), "Enables recall potion");
        configs.addKeyValuePair(
                new Pair<>("item.recall_potion.natural_generation", true),
                "Enables recall potion loot table generation"
        );

        // Magic Mirror
        configs.addKeyValuePair(new Pair<>("item.magic_mirror", true), "Enables magic mirror");
        configs.addKeyValuePair(
                new Pair<>("item.magic_mirror.natural_generation", true),
                "Enables magic mirror loot table generation"
        );
        configs.addKeyValuePair(
                new Pair<>("item.magic_mirror.use_time", 20),
                "Set mirror use time in ticks (1s = 20 ticks)"
        );
        configs.addKeyValuePair(
                new Pair<>("item.magic_mirror.reload_time", 120),
                "Set reloading time in ticks (1s = 20 ticks)"
        );
    }

    private static void assignConfigs() {
        TRANS_DIM = CONFIG.getOrDefault("teleport.transdim", true);

        // Recall Potion
        ENABLE_RECALL_POTION = CONFIG.getOrDefault("item.recall_potion", true);
        ENABLE_NATURAL_RECALL_POTION = CONFIG.getOrDefault("item.recall_potion.natural_generation", true);

        // Magic Mirror
        ENABLE_MIRROR = CONFIG.getOrDefault("item.magic_mirror", true);
        ENABLE_NATURAL_MIRROR = CONFIG.getOrDefault("item.magic_mirror.natural_generation", true);
        MIRROR_USE_TIME = CONFIG.getOrDefault("item.magic_mirror.use_time", 20);
        MIRROR_RELOADING_TIME = CONFIG.getOrDefault("item.magic_mirror.reload_time", 120);

        GoHomeMod.LOGGER.info("All " + configs.getConfigsList().size() + "settings were loaded");
    }
}
