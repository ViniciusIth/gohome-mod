package io.github.viniciusith.gohome;

import io.github.viniciusith.gohome.item.MagicMirror;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

public class GohomeClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Constants.LOG.info("Hello from Fabric Client");

        ItemProperties.register(
                ModRegistry.MAGIC_MIRROR.get(),
                ResourceLocation.withDefaultNamespace("recalling"),
                (itemStack, clientLevel, livingEntity, i) -> MagicMirror.getMagicMirrorUsageDisplay(itemStack, livingEntity)
        );

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> content.accept(ModRegistry.MAGIC_MIRROR.get()));
    }
}
