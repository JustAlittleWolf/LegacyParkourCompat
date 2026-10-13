# Swimming collision dimensions

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 291-315.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1756-1767.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 334-361.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1859-1870.

Difference: 1.13.2 adds swimming pose dimensions 0.6F by 0.6F behind its collision-fit guard; 1.12.2 has no swimming dimensions branch. The separate spin-attack trigger is a modern-only disposition.

Movement meaning: The established water movement state can choose a shorter player collision box; exact fit depends on the vanilla collision candidates.
