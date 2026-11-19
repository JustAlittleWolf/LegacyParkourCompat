package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.EmptyShapeMovementHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * Lower evidence boundary for the empty-shape movement shortcut. The exact
 * evidence starts at 1.17.1 Mojmap Entity.collide, compared with 1.18, 1.18.1,
 * and 1.18.2 Mojmap counterparts. Keep the earlier product fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class UnprovedEarlierCollisionBoundary implements EmptyShapeMovementHook {
    @Override
    public Optional<Vec3> resolve(Vec3 requested) {
        return Optional.empty();
    }
}
