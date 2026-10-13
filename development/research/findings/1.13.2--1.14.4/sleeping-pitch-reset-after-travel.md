# Sleeping pitch reset after travel

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick`, lines 1689–1825.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `tick`, lines 1979–2123.

Difference: Both local-player `tick()` methods call their superclass when the player's chunk is loaded. `LivingEntity.tick()` calls `mobTick()` virtually, so the local player's input processing runs before `LocalClientPlayerEntity.mobTick()` delegates to `LivingEntity.mobTick()` for travel. In 1.14.4, after that movement call returns and after walk/fall-flying bookkeeping, `LivingEntity.tick()` executes `if (this.isSleeping()) this.pitch = 0.0F;`. 1.13.2 has no corresponding client-side post-tick pitch reset. 1.14.4 also has a server-only sleeping cleanup in the `!world.isClient` block; that branch is not reachable in this local-client path.

Movement meaning: The differing write is reachable on the local client when the player is sleeping.
