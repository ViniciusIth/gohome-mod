package cloud.viniciusith.gohome.item;

import cloud.viniciusith.gohome.GoHomeMod;
import cloud.viniciusith.gohome.Utilities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

public class MagicMirror extends Item {
    public static Item MAGIC_MIRROR = new MagicMirror(new FabricItemSettings().maxCount(1));

    public MagicMirror(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BRUSH;
    }

    @Override
    public Rarity getRarity(ItemStack stack) {
        return Rarity.RARE;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (world.isClient()) {
            return stack;
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;

        teleportToSpawn(serverPlayer);
        serverPlayer.getItemCooldownManager().set(this, 60);

        return stack;
    }

    void teleportToSpawn(ServerPlayerEntity playerEntity) {
        Optional<Vec3d> spawn = Utilities.getPlayerSpawn(playerEntity);
        RegistryKey<World> spawnDimension = playerEntity.getSpawnPointDimension();

        playerEntity.stopRiding();
        playerEntity.fallDistance = 0;

        if (spawn.isEmpty()) {
            Vec3d worldSpawn = Utilities.getWorldSpawnPos(playerEntity);
            boolean teleportResult = Utilities.teleportPlayerTo(playerEntity, worldSpawn, ServerWorld.OVERWORLD);
            if (!teleportResult) {
                playerEntity.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable(
                        "teleport.gohome.interdimension.error")));
                return;
            }

            playerEntity.getWorld().playSound(null,
                                              worldSpawn.getX(),
                                              worldSpawn.getY(),
                                              worldSpawn.getZ(),
                                              SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
                                              SoundCategory.PLAYERS,
                                              1f,
                                              1f
            );
            playerEntity.networkHandler.sendPacket(new OverlayMessageS2CPacket(Text.translatable(
                    "block.minecraft.spawn.not_valid")));
            return;
        }

        Utilities.teleportPlayerTo(playerEntity, spawn.get(), spawnDimension);
        playerEntity.getWorld().playSound(null,
                                          spawn.get().getX(),
                                          spawn.get().getY(),
                                          spawn.get().getZ(),
                                          SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT,
                                          SoundCategory.PLAYERS,
                                          1f,
                                          1f
        );
    }

    public static void registerMagicMirror() {
        Registry.register(
                Registries.ITEM,
                new Identifier(GoHomeMod.MOD_ID, "magic_mirror"),
                MAGIC_MIRROR
        );
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(MAGIC_MIRROR);
        });
    }
}
