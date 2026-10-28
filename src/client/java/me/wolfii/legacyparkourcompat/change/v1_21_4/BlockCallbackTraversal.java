package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackDda;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * Selects the source-audited swept block traversal for the 1.21.4 callback
 * checker. Enumeration, clipping, tie, epsilon and integer-bound rules are
 * shared with the accepted 1.21.6 implementation through the neutral helper.
 * Source: 1.21.4/mojmap/net/minecraft/world/level/BlockGetter.java,
 * boxTraverseBlocks and addCollisionsAlongTravel.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class BlockCallbackTraversal implements BlockCallbackTraversalHook {
    @Override
    public boolean appliesTo(Entity entity) {
        return entity instanceof LocalPlayer && entity.level().isClientSide()
            && !entity.isRemoved() && !entity.noPhysics;
    }

    @Override
    public Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        return BlockCallbackDda.visitSegment(from, to, target, visitor);
    }
}
