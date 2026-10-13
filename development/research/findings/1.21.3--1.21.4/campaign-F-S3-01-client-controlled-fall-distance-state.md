# F-S3-01: 1.21.4 leaves client-controlled player fall distance unchanged on landing

1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, move 619-702; checkFallDamage 1216-1231.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/Entity.java`, move 627-713; checkFallDamage 1237-1252.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, checkFallDamage 341-371.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, checkFallDamage 339-369.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, isLocalPlayer 347-349; isEffectiveAi 478-480.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, isControlledByClient 2146-2148; maybeBackOffFromEdge 1066-1123.
1.21.4, mojmap: `1.21.4/mojmap/net/minecraft/world/entity/player/Player.java`, isControlledByClient 2166-2168.

Difference: On 1.21.3's client-local movement path, a grounded collision invokes `LivingEntity.checkFallDamage()` and then `Entity.checkFallDamage()`, which resets the player's fall distance. On 1.21.4, `Player.isControlledByClient()` is true, so the new negative guard skips the whole callback for the local player. 1.21.4 therefore retains the previous fall-distance value after landing. This is a local movement-state change distinct from whether fall damage is calculated or applied.

Movement meaning: The ordinary player travel path calls `Entity.move()` after movement collision resolution. On 1.21.3, LocalPlayer's `isEffectiveAi()` makes it controlled by the local instance; on 1.21.4 the added controlled-by-client guard excludes the Player. The reset's state is read by `Player.maybeBackOffFromEdge()`. 1.21.3 reachable contrast is a player that lands after accumulating fall distance greater than its `maxUpStep`, then is airborne again while holding shift during a non-positive-Y movement: 1.21.3 starts the new descent with the landing reset, while 1.21.4 still has the prior larger value. That value can make `isAboveGround()` false before the support-geometry check, selecting a different edge-backoff branch. The source proves that 1.21.4 does not clear this client-side player movement state through the landing collision callback and that the state feeds a crouch-edge movement guard. Fall-damage calculation, attack/damage effects, and any health outcome are outside this finding.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.3--1.21.4/findings/F-S3-01-client-controlled-fall-distance-state.md).
