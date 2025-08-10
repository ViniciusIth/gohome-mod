package io.github.viniciusith.gohome.effect;

import io.github.viniciusith.gohome.Utilities;
import io.github.viniciusith.gohome.config.Config;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.functions.SetPotionFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RecallEffect extends InstantenousMobEffect {
    public RecallEffect(MobEffectCategory category, int colour) {
        super(category, colour);
    }

    public static void addToLootTable(LootTable.Builder tableBuilder, float minRolls, float maxRolls, float chance, int minCount, int maxCount) {
        if (!Config.ENABLE_RECALL_POTION || !Config.ENABLE_NATURAL_RECALL_POTION) {
            return;
        }

        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(UniformGenerator.between(minRolls, maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(Items.POTION))
                .apply(SetPotionFunction.setPotion(ModRegistry.RECALL_POTION.asHolder()))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));

        tableBuilder.withPool(builder).build();
    }

    @Override
    public void applyInstantenousEffect(
            @Nullable Entity source,
            @Nullable Entity attacker,
            @NotNull LivingEntity target,
            int amplifier,
            double effectiveness
    ) {
        if (!(target instanceof ServerPlayer player)) return;
        teleportToSpawn(player);
    }

    private void teleportToSpawn(ServerPlayer serverPlayer) {
        Vec3 spawnPos = Utilities.getPlayerSpawnPos(serverPlayer).orElse(Utilities.getWorldSpawnPos(serverPlayer));
        ResourceKey<Level> destinationDim = serverPlayer.getRespawnDimension();

        if (!destinationDim.equals(serverPlayer.serverLevel().dimension())) {
            if (!Config.TRANS_DIM) {
                serverPlayer.displayClientMessage(Component.translatable("teleport.gohome.teleport.error"), true);
                return;
            }
        }

        Utilities.teleportPlayerTo(serverPlayer, spawnPos, destinationDim);
        serverPlayer.serverLevel().playSound(null, spawnPos.x(), spawnPos.y(), spawnPos.z(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
    }
}
