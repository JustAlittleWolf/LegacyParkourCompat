# F-SNEAK-WATER-DESCENT — 1.13.2 adds a local downward water input

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 649-817.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `public void mobTick() {`, lines 653-842.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `protected void knockDownwards() {`, lines 1466-1468.

Difference: sneaking in water has a new direct velocity-writing call in 1.13.2. `LivingEntity.knockDownwards()` 1.13.2 line 1466 subtracts `0.04F` from velocityY; 1.12.2 has no paired helper and its local-player mobTick has no corresponding branch.

Movement meaning: - Reachability and dependencies: local keyboard sneaking and sampled water-contact state feed this condition; the method runs before superclass LivingEntity.mobTick travel.
