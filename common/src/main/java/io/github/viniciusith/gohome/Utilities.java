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
        return playerEntity.adjustSpawnLocation(overworld, overworld.getSharedSpawnPos()).getBottomCenter();
    }

    public static Optional<Vec3> getPlayerSpawnPos(ServerPlayer serverPlayerEntity) {
        BlockPos spawnpoint = serverPlayerEntity.getRespawnPosition();
        if (spawnpoint == null) {
            return Optional.empty();
        }

        ServerLevel targetDimension = serverPlayerEntity.server.getLevel(serverPlayerEntity.getRespawnDimension());

        BlockState respawnBlockState = targetDimension.getBlockState(spawnpoint);
        Block respawnBlock = respawnBlockState.getBlock();

        switch (respawnBlock) {
            case RespawnAnchorBlock s -> {
                return RespawnAnchorBlock.findStandUpPosition(EntityType.PLAYER, targetDimension, spawnpoint);
            }
            case BedBlock s -> {
                return BedBlock.findStandUpPosition(
                        EntityType.PLAYER,
                        targetDimension,
                        spawnpoint,
                        respawnBlockState.getValue(BedBlock.FACING),
                        serverPlayerEntity.getRespawnAngle()
                );
            }
            default -> {
            }
        }

        if (serverPlayerEntity.isRespawnForced()) {
            return Optional.of(serverPlayerEntity.adjustSpawnLocation(targetDimension, targetDimension.getSharedSpawnPos()).getBottomCenter());
        }

        return Optional.empty();
    }

    public static boolean teleportPlayerTo(ServerPlayer playerEntity, Vec3 targetPos, ResourceKey<Level> destination) {
        ServerLevel targetDimension = playerEntity.getServer().getLevel(destination);

        if (!destination.equals(playerEntity.serverLevel().dimension())) {
//            if (!ModConfig.TRANS_DIM) {
//                return false;
//            }
        }

        playerEntity.teleportTo(targetDimension, targetPos.x(), targetPos.y(), targetPos.z(), playerEntity.getYRot(), playerEntity.getXRot());

        return true;
    }


}
