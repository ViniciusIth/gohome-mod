package cloud.viniciusith.gohome.effect;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
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
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class RecallEffect extends StatusEffect {
    public static StatusEffect RECALL;

    public RecallEffect() {
        super(StatusEffectCategory.BENEFICIAL, 0x15A5C1);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (entity instanceof PlayerEntity) {
            teleportToSpawn((ServerPlayerEntity) entity);
        }
    }

    void teleportToSpawn(ServerPlayerEntity player) {
        BlockPos spawn = player.getSpawnPointPosition();
        if (spawn == null) {
            spawn = player.getWorld().getSpawnPos();
        }

        RegistryKey<World> spawnDimension = player.getSpawnPointDimension();
        ServerWorld destination = ((ServerWorld) player.getWorld()).getServer().getWorld(spawnDimension);


        if (!spawnDimension.equals(player.getServerWorld().getRegistryKey())) {
            Vec3d spawn3d = new Vec3d(spawn.getX() + 0.5f, spawn.getY() + 0.5f, spawn.getZ() + 0.5f);
            FabricDimensions.teleport(player, destination, new TeleportTarget(spawn3d, Vec3d.ZERO, 0, 0));
        }


        player.stopRiding();
        player.fallDistance = 0;
        player.teleport(spawn.getX() + 0.5f, spawn.getY() + 0.5f, spawn.getZ() + 0.5f);
        player.getWorld().playSound(null, spawn.getX() + 0.5F, spawn.getY() + 0.5F, spawn.getZ() + 0.5F, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
    }

    public static void registerEffect() {
        RECALL = Registry.register(Registries.STATUS_EFFECT, new Identifier("gohome", "recall"), new RecallEffect());
    }
}
