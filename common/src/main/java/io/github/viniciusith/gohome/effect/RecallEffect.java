package io.github.viniciusith.gohome.effect;

import io.github.viniciusith.gohome.Utilities;
import io.github.viniciusith.gohome.config.Config;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSystemChatPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
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

import java.util.Optional;

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

    private void teleportToSpawn(ServerPlayer player) {
        BlockPos fallback = player.level().getSharedSpawnPos();
        var dim = player.getRespawnDimension();

        // TODO: Maybe we could also bring the vehicle with the player if configured
        player.removeVehicle();
        player.fallDistance = 0f;

        Optional<BlockPos> spawn = Optional.ofNullable(player.getRespawnPosition());
        if (spawn.isEmpty()) {
            boolean ok = Utilities.teleportPlayerTo(player, Vec3.atLowerCornerOf(fallback), ServerLevel.OVERWORLD);
            if (!ok) {
                player.connection.send(new ClientboundSystemChatPacket(
                        Component.translatable("teleport.gohome.teleport.error"), true
                ));
            }
            ServerLevel world = player.serverLevel();
            world.playSound(
                    null,
                    fallback,
                    SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS,
                    1.0f, 1.0f
            );
            player.connection.send(new ClientboundSystemChatPacket(
                    Component.translatable("block.minecraft.spawn.not_valid"), true
            ));

            return;
        }

        Utilities.teleportPlayerTo(player, Vec3.atLowerCornerOf(spawn.get()), dim);
        player.serverLevel().playSound(null, spawn.get().getX(), spawn.get().getY(), spawn.get().getZ(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1f, 1f);
    }
}
