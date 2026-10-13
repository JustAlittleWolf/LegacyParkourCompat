package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import java.util.Optional;

/**
 * 1.17.1 mojmap Entity#collide samples the border against the current player
 * box, deflated by 1.0E-7, and omits it on overlap. 1.18 mojmap
 * Entity#collideBoundingBox instead calls WorldBorder#isInsideCloseToBorder
 * with the swept box. 26.2 unobfuscated Entity#collectCollidersIgnoringWorldBorder
 * retains that newer call. This delta changes only border shape admission;
 * the source witness establishes the supplied one-axis result. Runtime parity
 * remains unverified.
 * 1.14 ornithe-feather Entity#m_72373519 introduces this shape-overlap gate;
 * 1.13.2 WorldView#getCollisions still uses cached outside-border state. That
 * earlier operation is unresolved, so this delta retains its native fallback.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class BorderCollisionAdmission implements BorderCollisionAdmissionHook {
    @Override
    public Optional<Boolean> includeBorder(ParkourVersion selected, WorldBorder border, AABB currentBox) {
        if (selected.olderThan(ParkourVersion.V1_14)) {
            return Optional.empty();
        }
        return Optional.of(!Shapes.joinIsNotEmpty(border.getCollisionShape(),
            Shapes.create(currentBox.deflate(1.0E-7)), BooleanOp.AND));
    }
}
