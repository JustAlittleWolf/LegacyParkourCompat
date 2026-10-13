# Swimming eye height

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 291-315.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1756-1767.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `protected void updatePlayerPose() {`, lines 334-361.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public float getEyeHeight() {`, lines 1859-1870.

Difference: 1.13.2 returns 0.4F eye height while swimming; 1.12.2 has no swimming eye-height branch. This reader is separate from collision-box resizing.

Movement meaning: Eye-position water/submersion predicates can sample a different block/fluid height. Rendering effects are excluded.
