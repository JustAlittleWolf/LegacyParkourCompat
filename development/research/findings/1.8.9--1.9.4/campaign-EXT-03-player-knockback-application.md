# EXT-03: player knockback application changes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {`, lines 763-778.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public void applyKnockback(Entity entity, float amount, double velocityX, double velocityZ) {`, lines 870-886.

Difference: For the same knockback-resistance outcome and direction inputs, 1.8.9 always halves and adds to Y and uses fixed `0.4F` for horizontal and vertical response. 1.9.4 leaves Y unchanged in air, applies its vertical half/add only on ground, and uses the passed `amount` for the horizontal and grounded vertical response, subject to the same `0.4F` upward cap.

Both set the dirty flag before applying the response. 1.9.4 additionally routes a shield-bypass recoil response to a living attacker under its source guard.

These are direct velocity writes on a player instance when the described call receiver is a player.

No damage amount, attack outcome, collision result, or subsequent player trajectory is inferred.

Movement meaning: For the same knockback-resistance outcome and direction inputs, 1.8.9 always halves and adds to Y and uses fixed `0.4F` for horizontal and vertical response. 1.9.4 leaves Y unchanged in air, applies its vertical half/add only on ground, and uses the passed `amount` for the horizontal and grounded vertical response, subject to the same `0.4F` upward cap.

Both set the dirty flag before applying the response. 1.9.4 additionally routes a shield-bypass recoil response to a living attacker under its source guard.

These are direct velocity writes on a player instance when the described call receiver is a player.

No damage amount, attack outcome, collision result, or subsequent player trajectory is inferred.

Original evidence: [EXT-03-player-knockback-application.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/EXT-03-player-knockback-application.md).
