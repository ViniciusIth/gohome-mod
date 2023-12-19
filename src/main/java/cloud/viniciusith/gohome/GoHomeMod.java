package cloud.viniciusith.gohome;

import cloud.viniciusith.gohome.effect.RecallEffect;
import cloud.viniciusith.gohome.potion.RecallPotion;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoHomeMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("gohome");

    @Override
    public void onInitialize() {
        RecallEffect.registerEffect();
        RecallPotion.registerRecallPotion();
    }
}
