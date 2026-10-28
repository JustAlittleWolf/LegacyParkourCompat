package me.wolfii.legacyparkourcompat.change.v1_13.movingpiston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.movingpiston.ShortArmThresholdHook;

/** Historical 1.13-family source-head short-arm threshold. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class ShortArmThreshold implements ShortArmThresholdHook {
    /**
     * In 1.13, 1.13.1, and 1.13.2 (Ornithe Feather),
     * {@code net.minecraft.block.entity.MovingBlockEntity.getCollisionShape}
     * sets source-head SHORT from {@code 1.0F - this.progress < 4.0F};
     * {@code net.minecraft.block.PistonHeadBlock.getArmShape} consumes it.
     * The normal tick progression is 0.0F, 0.5F, 1.0F, for which the predicate
     * is true throughout. {@code readNbt} assigns the stored float without
     * clamping: for example, a loaded {@code -3.0F} makes the predicate false.
     * This predicate source does not establish player-query reachability or
     * tick-level movement parity.
     */
    @Override
    public boolean isShort(float progress, boolean nativeResult) {
        return 1.0F - progress < 4.0F;
    }
}