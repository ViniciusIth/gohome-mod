package cloud.viniciusith.gohome;

import cloud.viniciusith.gohome.config.ModConfig;
import cloud.viniciusith.gohome.item.MagicMirror;
import net.fabricmc.api.ClientModInitializer;

public class GoHomeModClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        if (ModConfig.ENABLE_MIRROR) MagicMirror.registerMagicMirrorClient();
    }
}
