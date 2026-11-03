package me.wolfii.legacyparkourcompat.change.v1_13_1.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PushAwayProbeRangeHook;

import java.util.Optional;

/**
 * Preserves the 1.13 sampled row and, where that call site checks it, the row above.
 * 1.13.2/ornithe-feather,
 * {@code net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java}:
 * {@code tick} calls {@code pushAwayFrom} at four corners; {@code pushAwayFrom}
 * constructs the sample from {@code box.minY + 0.5}, and {@code wouldCollideAt}
 * checks only that row while swimming. Cardinal candidates use
 * {@code doesNotSuffocate}, which also reads the cell above (1.13.2/ornithe-feather,
 * {@code net/minecraft/entity/living/player/PlayerEntity.java},
 * {@code doesNotSuffocate} / {@code shouldSuffocate}).
 * 1.14/ornithe-feather,
 * {@code net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java}:
 * {@code pushAwayFrom}/{@code wouldCollideAt} scan from floor(box.minY) to
 * ceil(box.maxY). The 1.14.1, 1.14.2, and 1.14.3 Yarn aliases use
 * {@code net/minecraft/client/network/ClientPlayerEntity.java},
 * {@code pushOutOfBlocks}/{@code cannotFitAt}, with the same whole-column scan.
 * 1.14.4/ornithe-feather retains that scan. The suffocation predicate remains
 * resolved independently by SuffocationProbeHook; this range only applies to
 * proven stone/air/source-water witness columns. Push-away calls retain their
 * native order and closest-space comparisons.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class PushAwayProbeRange implements PushAwayProbeRangeHook {
    @Override
    public Optional<ProbeRange> probeRange(double boxMinY, ProbeRole role, boolean swimming) {
        int minY = (int) Math.floor(boxMinY + 0.5D);
        int maxYExclusive = swimming && role == ProbeRole.ORIGIN ? minY + 1 : minY + 2;
        return Optional.of(new ProbeRange(minY, maxYExclusive));
    }
}
