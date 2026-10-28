package me.wolfii.legacyparkourcompat.change.v1_12.movingpiston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.movingpiston.ShortArmThresholdHook;

/** Historical 1.12-family source-head short-arm threshold. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class ShortArmThreshold implements ShortArmThresholdHook {
    /**
     * In 1.12, 1.12.1, and 1.12.2 (Ornithe Feather),
     * {@code net.minecraft.block.entity.MovingBlockEntity.addCollisions} sets
     * source-head SHORT from {@code 1.0F - this.progress < 0.25F};
     * {@code net.minecraft.block.PistonHeadBlock.getArmShape} consumes it.
     * The normal tick progression is 0.0F, 0.5F, 1.0F, so the predicate is
     * false at the first two values and true at 1.0F. {@code readNbt} assigns
     * the stored float without clamping, so this live-progression result does
     * not cover arbitrary loaded values. This predicate source does not establish
     * player-query reachability or tick-level movement parity.
     */
    @Override
    public boolean isShort(float progress, boolean nativeResult) {
        return 1.0F - progress < 0.25F;
    }
}