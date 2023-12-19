package cloud.viniciusith.gohome;

import cloud.viniciusith.gohome.config.ModConfig;
import cloud.viniciusith.gohome.effect.RecallEffect;
import cloud.viniciusith.gohome.potion.RecallPotion;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoHomeMod implements ModInitializer {
    public static final String MOD_ID = "gohome";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.registerConfigs();

        RecallEffect.registerEffect();
        RecallPotion.registerRecallPotion();
    }
}
