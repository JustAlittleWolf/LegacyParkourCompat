# Water sprint cancellation predicates

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.

Difference: 1.12.2 stops sprint for low forward input, horizontal collision or insufficient food. 1.13.2 separates already-swimming from ordinary sprint: ordinary sprint additionally cancels on water contact without eye submersion; swimming uses ((!onGround && !sneaking && (forward<0.8F || !eligibleFood)) || !isInWater()).

Movement meaning: Swimming state is sampled before the local sprint decision, so this is an independent stop operation. Surface-water cancellation must not be generalized to already-swimming players.

Original evidence: [F-WATER-SPRINT.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT.md).
