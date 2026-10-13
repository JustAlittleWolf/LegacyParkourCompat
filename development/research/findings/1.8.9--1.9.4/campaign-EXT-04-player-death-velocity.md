# EXT-04: player death velocity uses a different angle conversion

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void die(DamageSource source) {`, lines 513-535.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `public void die(DamageSource source) {`, lines 484-506.

Difference: The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

Movement meaning: The degree-to-radian conversion has a different floating-point operation order: 1.8.9 converts `Math.PI` to float before multiplication/division; 1.9.4 divides in double precision and casts the quotient to float before multiplying.

The formula feeds the direct X/Z velocity assignment.

Both endpoints preserve the same vertical `0.1F` write and the same null-source horizontal-zero branch.

This records only a player velocity assignment in the death handler.

Health and damage production, death/respawn lifecycle, and subsequent motion are not evaluated.

Original evidence: [EXT-04-player-death-velocity.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-04-player-death-velocity.md).
