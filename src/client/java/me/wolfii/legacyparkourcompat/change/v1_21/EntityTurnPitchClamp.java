package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.EntityTurnPitchHook;
import net.minecraft.world.entity.Entity;

/** Restores the old finite first-setter clamp for ordinary local mouse turns. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class EntityTurnPitchClamp implements EntityTurnPitchHook {
    /**
     * Mojmap 1.17 Entity.turn(double,double) narrows the pitch delta to float,
     * multiplies by 0.15F, adds it to the current pitch, and calls
     * Entity.setXRot(float) before its later clamp; the setter stores finite
     * values verbatim (Entity.java:377–388, 3021–3027). This direct operation is
     * accepted continuously through Mojmap 1.20.4 Entity.turn/setXRot
     * (Entity.java:398–410, 3309–3315) and 1.21.1 Entity.turn/setXRot
     * (Entity.java:401–413, 3363–3369). Mojmap 1.21.2 changes the setter to
     * remainder by 360.0F then clamp (Entity.java:3489–3494); 26.2 unobfuscated
     * Entity.turn/setXRot retains that later setter behavior (Entity.java:489–501,
     * 3906–3916). Clamp only the first setter argument. Non-finite inputs remain
     * on vanilla's rejection path; unchanged in-range floats retain their exact
     * bits, including signed zero.
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
