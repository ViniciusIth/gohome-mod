package cloud.viniciusith.gohome;

import cloud.viniciusith.gohome.effect.RecallEffect;
import cloud.viniciusith.gohome.potion.RecallPotion;
import net.fabricmc.api.ModInitializer;

public class GoHomeMod implements ModInitializer {
    @Override
    public void onInitialize() {
        RecallEffect.registerEffect();
        RecallPotion.registerRecallPotion();
    }
}
