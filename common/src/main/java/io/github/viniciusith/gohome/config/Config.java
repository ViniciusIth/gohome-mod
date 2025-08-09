package io.github.viniciusith.gohome.config;

import io.github.viniciusith.gohome.Constants;

import java.util.AbstractMap.SimpleEntry;

public class Config {
    public static SimpleConfig CONFIG;
    public static boolean TRANS_DIM;
    public static boolean ENABLE_RECALL_POTION;
    public static boolean ENABLE_NATURAL_RECALL_POTION;
    public static boolean ENABLE_MIRROR;
    public static boolean ENABLE_NATURAL_MIRROR;
    public static int MIRROR_RELOADING_TIME;
    public static int MIRROR_USE_TIME;
    private static ConfigProvider configs;

    public static void registerConfigs() {
        configs = new ConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(Constants.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    public static void reloadConfigs() {
        CONFIG = SimpleConfig.of(Constants.MOD_ID + "config").provider(configs).request();
        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(
                new SimpleEntry<>("general.dimensional_teleport", true),
                "Allow inter-dimensional teleportation"
        );

        // Recall Potion
        configs.addKeyValuePair(new SimpleEntry<>("item.recall_potion", true), "Enables recall potion");
        configs.addKeyValuePair(
                new SimpleEntry<>("item.recall_potion.natural_generation", true),
                "Enables recall potion loot table generation"
        );

        // Magic Mirror
        configs.addKeyValuePair(new SimpleEntry<>("item.magic_mirror", true), "Enables magic mirror");
        configs.addKeyValuePair(
                new SimpleEntry<>("item.magic_mirror.natural_generation", true),
                "Enables magic mirror loot table generation"
        );
        configs.addKeyValuePair(
                new SimpleEntry<>("item.magic_mirror.use_time", 20),
                "Set mirror use time in ticks (1s = 20 ticks)"
        );
        configs.addKeyValuePair(
                new SimpleEntry<>("item.magic_mirror.reload_time", 120),
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

        Constants.LOG.info("All {} settings were loaded", configs.getConfigsList().size());
    }
}
