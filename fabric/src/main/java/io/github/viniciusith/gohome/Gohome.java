package io.github.viniciusith.gohome;

import io.github.viniciusith.gohome.commands.ReloadConfigCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

import static io.github.viniciusith.gohome.registration.ModRegistry.RECALL_POTION;

public class Gohome implements ModInitializer {
    @Override
    public void onInitialize() {
        // This method is invoked by the Fabric mod loader when it is ready
        // to load your mod. You can access Fabric and Common code in this
        // project.

        // Use Fabric to bootstrap the Common mod.
        Constants.LOG.info("Hello Fabric world!");
        CommonClass.init();

        FabricBrewingRecipeRegistryBuilder.BUILD.register((l) -> l.registerPotionRecipe(Potions.AWKWARD, Ingredient.of(Items.ENDER_PEARL), RECALL_POTION.asHolder()));
        CommandRegistrationCallback.EVENT.register(
                (dispatcher, ctx, environment) -> ReloadConfigCommand.register(dispatcher)
        );
//        LootTableEvents.MODIFY.register(((key, tableBuilder, source) -> {
//            if (BuiltInLootTables.ABANDONED_MINESHAFT.equals(key)) {
//                RecallEffect.addToLootTable(tableBuilder, 1, 1, .25f, 1, 2);
//            }
//            if (BuiltInLootTables.STRONGHOLD_LIBRARY.equals(key)) {
//                RecallEffect.addToLootTable(tableBuilder, 1, 1, .15f, 1, 1);
//                MagicMirror.addToLootTable(tableBuilder, 1, 1, .15f, 1, 1);
//            }
//            if (BuiltInLootTables.SHIPWRECK_TREASURE.equals(key)) {
//                RecallEffect.addToLootTable(tableBuilder, 1, 2, .35f, 1, 5);
//                MagicMirror.addToLootTable(tableBuilder, 1, 1, .10f, 1, 1);
//            }
//            if (BuiltInLootTables.TRIAL_CHAMBERS_REWARD.equals(key)) {
//                MagicMirror.addToLootTable(tableBuilder, 1, 1, .05f, 1, 1);
//            }
//            if (BuiltInLootTables.ANCIENT_CITY.equals(key)) {
//                MagicMirror.addToLootTable(tableBuilder, 1, 1, .05f, 1, 1);
//            }
//        }));
    }
}
