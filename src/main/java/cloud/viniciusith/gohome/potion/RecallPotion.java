package cloud.viniciusith.gohome.potion;

import cloud.viniciusith.gohome.config.ModConfig;
import cloud.viniciusith.gohome.effect.RecallEffect;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.SetPotionLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class RecallPotion {
    public static RegistryEntry.Reference<Potion> RECALL_POTION;

    public static void registerRecallPotion() {
        RECALL_POTION = Registry.registerReference(
                Registries.POTION,
                new Identifier("gohome", "recall_potion"),
                new Potion(new StatusEffectInstance(RecallEffect.RECALL))
        );

        FabricBrewingRecipeRegistryBuilder.BUILD.register((l) -> l.registerPotionRecipe(Potions.AWKWARD, Items.ENDER_PEARL, RECALL_POTION));
    }

    public static void addLootTable(LootTable.Builder tableBuilder, float minSpawn, float maxSpawn) {
        if (!ModConfig.ENABLE_RECALL_POTION || !ModConfig.ENABLE_NATURAL_RECALL_POTION) {
            return;
        }

        ItemStack potionStack = PotionContentsComponent.createStack(Items.POTION, RecallPotion.RECALL_POTION);

        LootPoolEntry recallPotionPool = ItemEntry.builder(potionStack.getItem()).build();
        LootPool.Builder builder = LootPool.builder()
                .rolls(UniformLootNumberProvider.create(minSpawn, maxSpawn))
                .with(recallPotionPool)
                .apply(SetPotionLootFunction.builder(RecallPotion.RECALL_POTION));

        tableBuilder.pool(builder);
    }
}
