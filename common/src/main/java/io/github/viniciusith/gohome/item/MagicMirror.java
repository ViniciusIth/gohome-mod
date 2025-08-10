package io.github.viniciusith.gohome.item;

import io.github.viniciusith.gohome.Utilities;
import io.github.viniciusith.gohome.config.Config;
import io.github.viniciusith.gohome.registration.ModRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;


public class MagicMirror extends Item {
    public static Properties PROPERTIES = new Properties().stacksTo(1).rarity(Rarity.RARE);

    public MagicMirror(Properties properties) {
        super(properties);
    }

    public static float getMagicMirrorUsageDisplay(ItemStack stack, LivingEntity entity) {
        if (entity == null) {
            return 0f;
        }
        float maxUseTime = stack.getUseDuration(entity);
        float elapsed = entity.getTicksUsingItem();

        return elapsed / maxUseTime;
    }

    public static void addToLootTable(LootTable.Builder tableBuilder, float minRolls, float maxRolls, float chance, int minCount, int maxCount) {
        if (!Config.ENABLE_MIRROR || !Config.ENABLE_NATURAL_MIRROR) {
            return;
        }

        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(UniformGenerator.between(minRolls, maxRolls))
                .when(LootItemRandomChanceCondition.randomChance(chance))
                .add(LootItem.lootTableItem(ModRegistry.MAGIC_MIRROR.get()))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minCount, maxCount)));

        tableBuilder.withPool(builder).build();
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand usedHand) {
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return Config.MIRROR_USE_TIME;
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, Level level, @NotNull LivingEntity livingEntity) {
        if (level.isClientSide()) {
            return stack;
        }

        ServerPlayer serverPlayer = (ServerPlayer) livingEntity;
        Vec3 spawnPos = Utilities.getPlayerSpawnPos(serverPlayer).orElse(Utilities.getWorldSpawnPos(serverPlayer));
        ResourceKey<Level> destinationDim = serverPlayer.getRespawnDimension();

        if (!destinationDim.equals(serverPlayer.serverLevel().dimension())) {
            if (!Config.TRANS_DIM) {
                serverPlayer.displayClientMessage(Component.translatable("teleport.gohome.teleport.error"), true);
                return stack;
            }
        }

        Utilities.teleportPlayerTo(serverPlayer, spawnPos, destinationDim);
        serverPlayer.serverLevel().playSound(null, spawnPos.x(), spawnPos.y(), spawnPos.z(), SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1f, 1f);

        serverPlayer.getCooldowns().addCooldown(this, Config.MIRROR_RELOADING_TIME);

        return stack;
    }
}
