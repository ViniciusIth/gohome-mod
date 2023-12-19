package cloud.viniciusith.gohome.effect;

import cloud.viniciusith.gohome.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RecallEffect extends InstantStatusEffect {
    public static StatusEffect RECALL;

    public RecallEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x15A5C1);
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        if (target instanceof PlayerEntity) {
            teleportToSpawn((ServerPlayerEntity) target);
        }
    }

    void teleportToSpawn(ServerPlayerEntity playerEntity) {
        Optional<Vec3d> spawn = Utilities.getPlayerSpawn(playerEntity);
        RegistryKey<World> spawnDimension = playerEntity.getSpawnPointDimension();

        playerEntity.stopRiding();
        playerEntity.fallDistance = 0;

        if (spawn.isEmpty()) {
            Vec3d worldSpawn = Utilities.getWorldSpawnPos(playerEntity);
            Utilities.teleportPlayerTo(playerEntity, worldSpawn, ServerWorld.OVERWORLD);
            playerEntity.getWorld().playSound(null, worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
            playerEntity.sendMessage(Text.translatable("block.minecraft.spawn.not_valid"));
            return;
        }

        Utilities.teleportPlayerTo(playerEntity, spawn.get(), spawnDimension);
        playerEntity.getWorld().playSound(null, spawn.get().getX(), spawn.get().getY(), spawn.get().getZ(), SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
    }

    public static void registerEffect() {
        RECALL = Registry.register(Registries.STATUS_EFFECT, new Identifier("gohome", "recall"), new RecallEffect());
    }
}
