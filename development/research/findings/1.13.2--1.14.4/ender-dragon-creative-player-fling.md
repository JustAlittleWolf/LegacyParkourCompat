# Ender Dragon wing fling excludes creative players in 1.14.4

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java`, `mobTick, flingMobs`, lines 304–305, 402–414.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/mob/monster/boss/EnderDragonEntity.java`, `mobTick, flingMobs`, lines 293–300, 397–409.

Difference: 1.13.2's two-argument wing query resolves through `EntityView.getEntities(exclude,bounds)`, whose default passes `EntityFilter.NOT_SPECTATOR`. That predicate rejects spectator players but allows non-spectator creative players. 1.14.4 passes `EntityFilter.NOT_SPECTATOR_NOT_CREATIVE` explicitly to each wing query. Its player branch requires both `!isSpectator()` and `!isCreative()`, so a creative player is removed before `flingMobs` receives the list. The paired `flingMobs` writer is the same: for each living entity it computes the x/z offset from the dragon body center, computes `h = f*f + g*g`, and calls `addVelocity(f/h*4.0, 0.2F, g/h*4.0)`. The separate damage condition follows the impulse in both versions and is excluded from this movement finding.

Movement meaning: The wing query runs server-side when `damagedTimer == 0`.
