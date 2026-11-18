# 1.20.2 → 1.20.3: ability flight during active Elytra glide

Scope: ordinary dry airborne local player, not a passenger, with the shared glide flag initially true, a working Elytra in the default chest slot, and lawful ability flight (`abilities.mayfly` and `abilities.flying`). This records only the real movement and the subsequent direct glide-flag and fall-distance writes.

## Exact-source comparison

- 1.20.2 Mojmap: `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1450–1474; `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 2038–2169; `Entity.checkFallDamage`, lines 1107–1123; `Player.causeFallDamage`, lines 1568–1578.
- 1.20.3 Mojmap: `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1451–1472; `net/minecraft/world/entity/LivingEntity.java`, `LivingEntity.travel(Vec3)`, lines 2035–2166; `Entity.checkFallDamage`, lines 1108–1124; `Player.causeFallDamage`, lines 1494–1504.

For this precondition, `Player.travel` enters the same `abilities.flying && !isPassenger()` branch in both releases. It saves the incoming vertical velocity, calls `super.travel` (the real move), restores horizontal velocity from the resulting vector while replacing vertical velocity with the saved value times `0.6`, then calls `resetFallDistance()` and `setSharedFlag(7, false)` in that order. The direct flag clear is unconditional within the ability-flight branch; it does not depend on landing or Elytra state.

`LivingEntity.travel` is also unchanged in the examined path: controlled-local-instance handling precedes the dry, non-lava fall-flying branch. With flag 7 set and active Elytra, it computes glide velocity and calls `move(MoverType.SELF, ...)`. Its own flag-7 clear is gated by both `onGround()` and `!level().isClientSide`, so it does not clear the flag for this airborne local player. The later unconditional `Player.travel` write is the relevant clear.

Fall distance is a separate state operation. `Entity.checkFallDamage` accumulates it for a non-ground downward move; the player-flight branch then zeros it after `super.travel` returns. Separately, `Player.causeFallDamage` returns false when `abilities.mayfly` is true. The latter is a distinct damage gate, not the source of either the fall-distance reset or the flag clear.

**Bounded result: no change** to the specified move → fall-distance reset → flag-7 clear behavior between 1.20.2 and 1.20.3. The enclosing `Player.travel` method has adjacent statistic bookkeeping outside this assignment; this result does not claim that the whole method is unchanged.

## Input and source pins

The exact source bytes below were independently hashed from the canonical read-only Mojmap trees and matched the available source manifests.

| Release | `Player.java` SHA-256 | `LivingEntity.java` SHA-256 | `Entity.java` SHA-256 |
|---|---|---|---|
| 1.20.2 | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` |
| 1.20.3 | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |

- 1.20.2 readiness marker pins version `1.20.2`, namespace `mojmap`, client SHA-256 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, and source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`. Its artifact manifest pins `client_mappings.txt` as `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd` and `client-mojmap.jar` as `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`. Provenance sidecar SHA-256: `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- 1.20.3 mapping-root `.decompile-provenance.json` identifies `versionId=1.20.3`, `namespace=mojmap`, and pins the client input as `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655` and Mojang client mappings as `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`. The examined `Player.java`, `LivingEntity.java`, and `Entity.java` hashes matched its embedded exact source manifest. Provenance-file SHA-256: `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`.