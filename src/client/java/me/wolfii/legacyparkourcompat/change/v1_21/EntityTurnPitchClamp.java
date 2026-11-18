package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.EntityTurnPitchHook;
import net.minecraft.world.entity.Entity;

/** Restores the old finite first-setter clamp for ordinary local mouse turns. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class EntityTurnPitchClamp implements EntityTurnPitchHook {
    /**
     * Mojmap 1.21.1 Entity.turn(double,double) sends its raw float pitch sum to
     * Entity.setXRot(float), whose first write stores it before the later turn
     * clamp. 26.2 unobfuscated Entity.turn(double,double) preserves that first
     * setter order, while Entity.setXRot(float) applies remainder by 360 before
     * clamping. Clamp only this first setter argument to recover the bounded
     * historical result. Non-finite inputs remain on vanilla's rejection path;
     * unchanged in-range floats retain their exact bits, including signed zero.
     * The accepted source comparisons cover 1.20.5–1.21.1 and the 1.21.1→1.21.2
     * cutoff (1.21.1 Entity.turn 401–413, setXRot 3359–3369; 26.2 Entity.turn
     * 489–501, setXRot 3906–3916).
     */
    @Override
    public float transformFirstPitchArgument(Entity entity, float vanillaArgument) {
        if (!Float.isFinite(vanillaArgument)) {
            return vanillaArgument;
        }
        if (vanillaArgument < -90.0F) {
            return -90.0F;
        }
        if (vanillaArgument > 90.0F) {
            return 90.0F;
        }
        return vanillaArgument;
    }
}
