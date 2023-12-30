package cloud.viniciusith.gohome;

import cloud.viniciusith.gohome.command.ReloadConfigCommand;
import cloud.viniciusith.gohome.config.ModConfig;
import cloud.viniciusith.gohome.effect.RecallEffect;
import cloud.viniciusith.gohome.item.MagicMirror;
import cloud.viniciusith.gohome.potion.RecallPotion;
import cloud.viniciusith.gohome.registry.LootTables;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoHomeMod implements ModInitializer {
    public static final String MOD_ID = "gohome";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ModConfig.registerConfigs();

        if (ModConfig.ENABLE_RECALL_POTION) {
            RecallEffect.registerEffect();
            RecallPotion.registerRecallPotion();
        }

        if (ModConfig.ENABLE_MIRROR) MagicMirror.registerMagicMirror();

        CommandRegistrationCallback.EVENT.register(ReloadConfigCommand::register);

        LootTables.modifyLootTables();
    }
}
