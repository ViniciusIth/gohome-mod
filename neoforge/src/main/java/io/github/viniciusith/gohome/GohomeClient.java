package io.github.viniciusith.gohome;

import io.github.viniciusith.gohome.item.MagicMirror;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
public class GohomeClient {
    public GohomeClient(IEventBus modBus) {
        modBus.addListener(GohomeClient::onClientSetup);
        modBus.addListener(GohomeClient::addToCreativeTab);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> ItemProperties.register(
                ModRegistry.MAGIC_MIRROR.get(),
                ResourceLocation.withDefaultNamespace("recalling"),
                (stack, level, player, seed) -> MagicMirror.getMagicMirrorUsageDisplay(stack, player)
        ));
    }

    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(ModRegistry.MAGIC_MIRROR.get());
        }
    }
}
