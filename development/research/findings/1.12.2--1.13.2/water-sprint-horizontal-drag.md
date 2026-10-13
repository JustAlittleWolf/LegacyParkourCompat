# Water sprint horizontal drag

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1425-1599.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void moveRelative(float forwards, float sideways, float upwards) {`, lines 1478-1669.

Difference: For the bounded water-travel preconditions, 1.12.2 X/Z drag uses 0.8F while 1.13.2 sprinting X/Z drag uses 0.9F. Depth Strider blending remains a separate input to the ordered coefficient computation.

Movement meaning: The same water velocity/input can retain a different horizontal component after drag. The operation is separately registered through WaterSprintSlowdownHook.

Original evidence: [F-WATER-SPRINT-TRAVEL.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-WATER-SPRINT-TRAVEL.md).
