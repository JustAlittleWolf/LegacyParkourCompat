# F002: Pose selection and collision-aware resize

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose`, lines 334–360.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose`, lines 343–370, 109–117.

Difference: 1.13.2 derives dimensions directly from fall-flying/sleeping/swimming-or-spin/sneaking/standing predicates; a requested box is applied only if collision-free, otherwise the existing size remains. 1.14.4 chooses a Pose, requires the swimming pose to fit before entering the selection, then tries the requested pose and falls back to sneaking or swimming. 1.13.2 passes `null` as collision-query entity; 1.14.4's pose fit calls the entity-aware `hasNoCollisions(this, box)` path.

Movement meaning: Player state predicates -> `updatePlayerPose` -> pose/dimensions -> collision, eye height and fluid checks. 1.14.4 pose/dimension mutation and world collision query/provider behavior need full paired closure.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md).
