package cloud.viniciusith.gohome.config;

import cloud.viniciusith.gohome.GoHomeMod;
import com.mojang.datafixers.util.Pair;

public class ModConfig {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static String TEST;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(GoHomeMod.MOD_ID + "config").provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("key.test.value1", "Just a Testing string!"), "String");
    }

    private static void assignConfigs() {
        TEST = CONFIG.getOrDefault("key.test.value1", "Nothing");

        GoHomeMod.LOGGER.info("All " + configs.getConfigsList().size() + " have been set properly");
    }
}
