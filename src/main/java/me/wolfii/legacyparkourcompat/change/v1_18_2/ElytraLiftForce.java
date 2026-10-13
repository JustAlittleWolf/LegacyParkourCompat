package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ElytraLiftForceHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Uses the double cosine coefficient introduced for 1.18.2.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[2074, 2090]].
 *   travel; lines [[2077, 2081]].
 *   Source SHA-256: 33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel; lines [[2080, 2099]].
 *   travel; lines [[2085, 2086]].
 *   Source SHA-256: db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision-artifact-correction-2026-10-09.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F002-artifact-identity-review.md; ACCEPT, artifact identity correction.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md; ACCEPT, exact sampled boundaries.
 *   Reviewed snapshot 14443f43fbcc30152916ac75796846d796cf0268.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-002-elytra-cosine-precision.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class ElytraLiftForce implements ElytraLiftForceHook {
    @Override
    public double liftForce(Player player, ParkourVersion selected, Vec3 lookAngle, double vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        float leanAngle = player.getXRot() * (float) (Math.PI / 180.0);
        double coefficient = Math.cos(leanAngle);
        coefficient = coefficient * coefficient * Math.min(1.0, lookAngle.length() / 0.4);
        return coefficient;
    }
}
