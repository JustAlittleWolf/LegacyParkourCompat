package me.wolfii.legacyparkourcompat.change.v1_13_1.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PushAwayProbeRangeHook;

import java.util.Optional;

/**
 * Preserves the 1.13 sampled row and the row above it for a non-swimming player.
 * 1.13.2/ornithe-feather,
 * {@code net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java}:
 * {@code tick} calls {@code pushAwayFrom} at four corners; {@code pushAwayFrom}
 * constructs the sample from {@code box.minY + 0.5}, and {@code wouldCollideAt}
 * checks it. Its non-swimming {@code doesNotSuffocate} predicate also reads the
 * cell above (1.13.2/ornithe-feather,
 * {@code net/minecraft/entity/living/player/PlayerEntity.java},
 * {@code doesNotSuffocate} / {@code shouldSuffocate}).
 * 1.14.4/ornithe-feather,
 * {@code net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java}:
 * the same methods use the same four calls but {@code wouldCollideAt} scans
 * from floor(box.minY) to ceil(box.maxY). The suffocation predicate remains
 * resolved independently by SuffocationProbeHook; this range only applies to
 * proven stone/air witness columns. Push-away calls retain their native order
 * and closest-space comparisons.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PushAwayProbeRange implements PushAwayProbeRangeHook {
    @Override
    public Optional<ProbeRange> probeRange(double boxMinY) {
        int minY = (int) Math.floor(boxMinY + 0.5D);
        return Optional.of(new ProbeRange(minY, minY + 2));
    }
}
