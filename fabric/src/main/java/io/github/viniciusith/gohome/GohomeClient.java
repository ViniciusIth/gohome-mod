package io.github.viniciusith.gohome;

import io.github.viniciusith.gohome.item.MagicMirror;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class GohomeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOG.info("Hello from Fabric Client");

        ItemProperties.register(
                ModRegistry.MAGIC_MIRROR.get(),
                ResourceLocation.withDefaultNamespace("recalling"),
                (itemStack, clientLevel, livingEntity, i) -> MagicMirror.getMagicMirrorUsageDisplay(itemStack, livingEntity)
        );
    }
}
