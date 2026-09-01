package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BlockBounceBehavior;

/**
 * Beds did not bounce until 1.12. That release converts 66% of downward speed
 * ({@code velocityY = -velocityY * 0.66F} in 1.12.2 {@code BedBlock#setEntityVelocity}).
 * 26.2 stores the same factor on {@code Block#getBounceRestitution}; returning
 * {@code 0} disables that restitution path. Fall damage and slime are unchanged.
 *
 * <p>{@link ParkourVersion#V1_11_2} is the last selectable version before 1.12,
 * so 1.8 through 1.11.2 get no bounce and 1.12+ keep vanilla 66%.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class NoBedBounce implements BlockBounceBehavior {
    private final String blockId;

    public NoBedBounce(String blockId) {
        this.blockId = blockId;
    }

    @Override
    public String blockId() {
        return this.blockId;
    }

    @Override
    public float bounceRestitution(float vanilla) {
        return 0.0F;
    }
}
