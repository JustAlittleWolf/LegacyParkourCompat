package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackDda;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.BlockCallbackTraversalHook;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/**
 * 1.21.6/1.21.7/1.21.8 Mojmap BlockGetter.forEachBlockIntersectedBetween and
 * addCollisionsAlongTravel (the complete Entity and BlockGetter files are identical).
 * Compared with 1.21.9 Mojmap and 26.2 unobfuscated same members: each component
 * owns a 17-attempt DDA cap and always completes its endpoint.
 * No initial box is emitted for a long segment. Empty clips consume attempts too.
 * The post-increment on the rejected eighteenth attempt makes endpoint index 19.
 * Record construction, axis ordering, record budget and Entity dedup/contact
 * sequencing are independently resolved; this operation enumerates one segment.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class BlockCallbackTraversal implements BlockCallbackTraversalHook {
    @Override
    public Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        return BlockCallbackDda.visitSegment(from, to, target, visitor);
    }
}
