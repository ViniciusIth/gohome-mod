package io.github.viniciusith.gohome;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RespawnAnchorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

public class Utilities {
    public static Vec3 getWorldSpawnPos(ServerPlayer playerEntity) {
        ServerLevel overworld = playerEntity.getServer().getLevel(ServerLevel.OVERWORLD);
        BlockPos worldSpawn = overworld.getSharedSpawnPos();
        return new Vec3(worldSpawn.getX(), worldSpawn.getY(), worldSpawn.getZ());
    }

    public static Optional<Vec3> getPlayerSpawn(ServerPlayer serverPlayerEntity) {
        ServerLevel targetWorld = serverPlayerEntity.server.getLevel(serverPlayerEntity.getRespawnDimension());
        BlockPos spawnpoint = serverPlayerEntity.getRespawnPosition();

        if (spawnpoint == null) {
            return Optional.empty();
        }

        BlockState respawnBlockState = targetWorld.getBlockState(spawnpoint);
        Block respawnBlock = respawnBlockState.getBlock();

        if (respawnBlock instanceof RespawnAnchorBlock) {
            return RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, targetWorld, spawnpoint);
        } else if (respawnBlock instanceof BedBlock) {
            return BedBlock.findStandUpPosition(
                    EntityType.PLAYER,
                    targetWorld,
                    spawnpoint,
                    respawnBlockState.getValue(BedBlock.FACING),
                    serverPlayerEntity.getRespawnAngle()
            );
        } else if (serverPlayerEntity.isRespawnForced()) {
            boolean footBlockClear = respawnBlock.isPossibleToRespawnInThis(respawnBlockState);
            boolean headBlockClear = targetWorld.getBlockState(spawnpoint.above()).getBlock().isPossibleToRespawnInThis(respawnBlockState);

            if (footBlockClear && headBlockClear) {
                return Optional.of(new Vec3((double) spawnpoint.getX() + 0.5D, (double) spawnpoint.getY() + 0.1D, (double) spawnpoint.getZ() + 0.5D));
            }
        }

        return Optional.empty();
    }

    public static boolean teleportPlayerTo(ServerPlayer playerEntity, Vec3 targetPos, ResourceKey<Level> destination) {
        ServerLevel destinationDim = playerEntity.getServer().getLevel(destination);

        if (!destination.equals(playerEntity.serverLevel().dimension())) {
//            if (!ModConfig.TRANS_DIM) {
//                return false;
//            }
//            FabricDimensions.teleport(playerEntity, destinationDim, new TeleportTarget(targetPos, Vec3d.ZERO, 0, 0));
        }

        playerEntity.teleportTo(destinationDim, targetPos.x(), targetPos.y(), targetPos.z(), playerEntity.getYRot(), playerEntity.getXRot());

        return true;
    }


}
