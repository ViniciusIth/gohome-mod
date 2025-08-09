package io.github.viniciusith.gohome;

import io.github.viniciusith.gohome.item.MagicMirror;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class GohomeClient {
    public GohomeClient(IEventBus modBus) {
        modBus.addListener(GohomeClient::onClientSetup);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        Constants.LOG.info("Hello from Neoforge Client");

        event.enqueueWork(() -> ItemProperties.register(
                ModRegistry.MAGIC_MIRROR.get(),
                ResourceLocation.withDefaultNamespace("recalling"),
                (stack, level, player, seed) -> MagicMirror.getMagicMirrorUsageDisplay(stack, player)
        ));
    }
}
