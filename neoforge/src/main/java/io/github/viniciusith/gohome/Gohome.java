package io.github.viniciusith.gohome;


import io.github.viniciusith.gohome.commands.ReloadConfigCommand;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

import static io.github.viniciusith.gohome.registration.ModRegistry.RECALL_POTION;

@Mod(Constants.MOD_ID)
public class Gohome {
    public Gohome(IEventBus eventBus) {
        Constants.LOG.info("Hello NeoForge world!");
        CommonClass.init();

        NeoForge.EVENT_BUS.addListener(Gohome::registerBrewingRecipes);
        NeoForge.EVENT_BUS.addListener(Gohome::registerCommands);
    }

    public static void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
                Potions.AWKWARD, Items.ENDER_PEARL, RECALL_POTION.asHolder()
        );
    }

    public static void registerCommands(RegisterCommandsEvent event) {
        ReloadConfigCommand.register(event.getDispatcher());
    }
}
