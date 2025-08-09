package io.github.viniciusith.gohome.item;

import io.github.viniciusith.gohome.Utilities;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;


public class MagicMirror extends Item {
    public static Properties MAGIC_MIRROR_PROPERTIES = new Properties().stacksTo(1).rarity(Rarity.RARE);

    public MagicMirror(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 20;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BRUSH;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (level.isClientSide()) {
            return stack;
        }

        ServerPlayer serverPlayer = (ServerPlayer) livingEntity;

        Vec3 spawnPos = Utilities.getPlayerSpawnPos(serverPlayer).orElse(Utilities.getWorldSpawnPos(serverPlayer));

        boolean teleportResult = Utilities.teleportPlayerTo(serverPlayer, spawnPos, serverPlayer.getRespawnDimension());
        if (!teleportResult) {
            serverPlayer.displayClientMessage(Component.translatable("teleport.gohome.teleport.error"), true);
            return stack;
        }

        serverPlayer.getCooldowns().addCooldown(this, 20);

        return stack;
    }

//    void teleportToSpawn(ServerPlayerEntity playerEntity) {
//        Optional<Vec3d> spawn = Utilities.getPlayerSpawn(playerEntity);
//        RegistryKey<World> spawnDimension = playerEntity.getSpawnPointDimension();
//
//        playerEntity.stopRiding();
//        playerEntity.fallDistance = 0;
//
//        if (spawn.isEmpty()) {
//            Vec3d worldSpawn = Utilities.getWorldSpawnPos(playerEntity);
//            boolean teleportResult = Utilities.teleportPlayerTo(playerEntity, worldSpawn, ServerWorld.OVERWORLD);
//            if (!teleportResult) {
//                playerEntity.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable(
//                        "teleport.gohome.interdimension.error")));
//                return;
//            }
//
//            playerEntity.getWorld().playSound(
//                    null,
//                    worldSpawn.getX(),
//                    worldSpawn.getY(),
//                    worldSpawn.getZ(),
//                    SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
//                    SoundCategory.PLAYERS,
//                    1f,
//                    1f
//            );
//            playerEntity.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable(
//                    "block.minecraft.spawn.not_valid")));
//            return;
//        }
//
//        Utilities.teleportPlayerTo(playerEntity, spawn.get(), spawnDimension);
//        playerEntity.getWorld().playSound(
//                null,
//                spawn.get().getX(),
//                spawn.get().getY(),
//                spawn.get().getZ(),
//                SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
//                SoundCategory.PLAYERS,
//                1f,
//                1f
//        );
//    }
//
//    public static void registerMagicMirror() {
//        Registry.register(
//                Registries.ITEM,
//                new Identifier(GoHomeMod.MOD_ID, "magic_mirror"),
//                MAGIC_MIRROR
//        );
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> content.add(MAGIC_MIRROR));
//    }
//
//
//    public static void registerMagicMirrorClient() {
//        ModelPredicateProviderRegistry.register(
//                MAGIC_MIRROR,
//                new Identifier("recalling"),
//                (ItemStack itemStack, ClientWorld clientWorld, LivingEntity livingEntity, int seed) -> {
//                    if (livingEntity == null || livingEntity.getActiveItem() != itemStack) {
//                        return 0.0F;
//                    }
//
//                    return (float) (itemStack.getMaxUseTime() - livingEntity.getItemUseTimeLeft()) / ModConfig.MIRROR_USE_TIME;
//                }
//        );
//    }
//
//    public static void addLootTable(LootTable.Builder tableBuilder, float minSpawn, float maxSpawn) {
//        if (!ModConfig.ENABLE_MIRROR || !ModConfig.ENABLE_NATURAL_MIRROR) {
//            return;
//        }
//
//        LootPoolEntry magicMirrorPool = ItemEntry.builder(MAGIC_MIRROR).build();
//        LootPool.Builder builder = LootPool.builder()
//                .rolls(UniformLootNumberProvider.create(minSpawn, maxSpawn))
//                .with(magicMirrorPool);
//
//        tableBuilder.pool(builder);
//    }
}
