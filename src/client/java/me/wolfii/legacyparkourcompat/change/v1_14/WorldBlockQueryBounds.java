package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.BlockQueryBounds;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WorldBlockQueryBoundsHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Restores 1.14's auto-jump world-query candidate range.
 *
 * <p>Minecraft 1.14 (Yarn build 21), {@code ViewableWorld#getCollisionShapes(Entity, BoundingBox, Set)}
 * floors each minimum and subtracts one, ceils each maximum and adds one, then
 * traverses to one less than those upper bounds. Minecraft 1.14.1 (Yarn build
 * 10), the same method epsilon-adjusts both bounds and traverses both iterator
 * endpoints. The range here represents the 1.14 traversal's inclusive
 * coordinates. The corresponding query is made by the auto-jump scan in
 * {@code ClientPlayerEntity#method_3148} in both releases. The hook is intended
 * only for that local-player consumer; other world queries and the separate
 * per-axis movement clipping scan keep their native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class WorldBlockQueryBounds implements WorldBlockQueryBoundsHook {
    @Override
    public Optional<BlockQueryBounds> bounds(AABB queryBox) {
        return Optional.of(new BlockQueryBounds(
            Mth.floor(queryBox.minX) - 1,
            Mth.floor(queryBox.minY) - 1,
            Mth.floor(queryBox.minZ) - 1,
            Mth.ceil(queryBox.maxX),
            Mth.ceil(queryBox.maxY),
            Mth.ceil(queryBox.maxZ)
        ));
    }
}
