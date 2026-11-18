package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import java.util.List;
import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Supplies the entity-first candidates for the closed Stone floor and fence fixture. */
@MechanicType("player.auto_jump.entity_prefix")
public interface AutoJumpEntityPrefixHook extends VersionedMechanic {
    default boolean observesClippedGroundedTravel() {
        return false;
    }

    default Optional<List<VoxelShape>> candidates(Player player, Level level, AABB query, ProbeSegments probes,
                                                  Entity entity, BlockState state, BlockPos pos,
                                                  CollisionContext context) {
        return Optional.empty();
    }
}
