# Local sprint-input submersion gate — narrow source comparison

## Assignment and conclusion

This note closes only the reader dependency left open by `c-input-water-gate-source/comparison.md` and its independent verdict: the separate player-eye submersion bit used by local sprint input. It does not re-audit the accepted water-contact producer, evaluate A-owned fluid-contact implementation, claim callback/current parity, or establish the entire sprint routine.

**Conclusion:** For the same player position, pose/eye-height cache, vehicle state, and fully loaded eye-containing chunk with the same water state, the 1.14.3 and 1.14.4 submersion predicates have matching gates and threshold math. The player's pose-dependent eye-height values are identical. Together with the separately accepted loaded-AABB contact-bit correspondence, this closes the local water-threshold reader dependency for corresponding ordinary fully loaded states: the sprint-start and sprint-maintenance booleans receive equivalent water predicates. No reachable difference in this bounded gate was found. Unloaded or partially loaded chunk cases remain outside this conclusion.

## Exact source identities

The canonical source trees are `decompiled_minecraft/1.14.3/yarn/` (Yarn `1.14.3+build.13`) and `decompiled_minecraft/1.14.4/mojmap/` (Mojmap 1.14.4). The prior comparison records the provenance identities and machine-generated provenance hashes; its signed correction resolves the malformed 1.14.3 client-source digest. The accepted input records are pinned here by raw SHA-256: contact producer draft `3a2bce15a218d961779a90c78eeca0d482ca99e8294265e20301f5e39a58f75c`, independent verdict `5980a123d975e140bcacc2b032b4d165fa407be1674cec44108d95b350b38f0e`, signed identity correction `d4219de6e07a2ab582d9b22b59fb7357f6d48fecb791385f373d9e53abde69cb`. Hashes below were freshly computed from the canonical source files for this review.

| Release / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.14.3 Yarn | `net/minecraft/entity/Entity.java` | `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.3 Yarn | `net/minecraft/entity/player/PlayerEntity.java` | `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963` |
| 1.14.3 Yarn | `net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |

## Predicate and input comparison

### Eye-height source and pose inputs

- In 1.14.3, `Entity.isSubmergedIn(Tag, boolean)` (`Entity.java:1032–1045`) returns false for a `BoatEntity` vehicle, then sets `d = y + getStandingEyeHeight()`. `getStandingEyeHeight()` returns the cached value written by `calculateDimensions()` for the current pose (`Entity.java:2240–2245`; accessor at 2311); synced pose changes invoke that calculation (`Entity.java:2235–2237`). `LivingEntity.getEyeHeight` applies `0.2F` for sleeping and otherwise delegates to the active-pose eye-height method. `PlayerEntity.getActiveEyeHeight` (`PlayerEntity.java:1781–1791`) returns `0.4F` for swimming, fall-flying, or spin attack; `1.27F` for sneaking; and `1.62F` otherwise.
- In 1.14.4, `Entity.isUnderLiquid(Tag, boolean)` (`Entity.java:1057–1069`) returns false for a `Boat` vehicle, then sets `d = y + getEyeHeight()`. `getEyeHeight()` returns the cache written during `refreshDimensions` (`Entity.java:2265–2270`, accessor at 2345). `LivingEntity.getEyeHeight` applies `0.2F` for sleeping and otherwise delegates to the pose eye-height method. `Player.getStandingEyeHeight` (`Player.java:1801–1811`) returns the same `0.4F`, `1.27F`, and `1.62F` values for the corresponding poses.

The field/accessor names and pose enum names changed, but both versions use the current cached player eye height, with matching player pose cases and constants. The sleeping override also matches. No stale-standing-versus-current-pose delta is supported by these cached-value update paths.

### Fluid threshold and loaded control

Both methods construct the fluid lookup position from `(x, y + eyeHeight, z)`, then test matching water and the strict comparison `eyeY < blockY + (fluidHeight + 0.11111111F)`. Both return false for a boat before lookup. With the eye-containing chunk loaded, neither loaded guard changes the predicate: Yarn `world.isChunkLoaded(chunkX, chunkZ)` and Mojmap `level.hasChunk(chunkX, chunkZ)` both permit the normal loaded case. The APIs differ by mapping/version; this review makes no claim about their behavior for unavailable chunks.

`updateWetState()` writes `inWater = isSubmergedIn(WATER, true)` (1.14.3 `Entity.java:967–969`); `updateUnderWaterState()` writes `wasUnderWater = isUnderLiquid(WATER, true)` (1.14.4 `Entity.java:992–994`). In both versions, the base entity tick performs the water-state update before the local-player sprint routine. The local readers use matching structure: 1.14.3 `ClientPlayerEntity.tickMovement()` (`:657–675`) checks `!isInsideWater() || isInWater()` for start and `isInsideWater()` / `isInWater()` for maintenance; 1.14.4 `LocalPlayer.aiStep()` (`:668–686`) checks `!isInWater() || isUnderWater()` and the matching maintenance predicates. Since 1.14.3 `isInWater()` is submerged-and-contact and 1.14.4 `isUnderWater()` is submerged-and-contact, these reader expressions agree when the independently accepted contact bit and this submersion bit agree.

## Boundaries

This is a source comparison of the reader dependency, not a new contact-bit verdict. It relies on the immutable bounded contact producer acceptance in `c-input-water-gate-audit/verdict.md` for corresponding fully loaded contracted player AABBs. The historical contact result controls `isInWater()` in 1.14.3 and is the analogous predicate in 1.14.4. This review covers only the additional eye-point producer and its local sprint consumers.

No claim is made for unavailable chunks, different worlds/blocks, callback or fluid-current effects, arbitrary modded overrides, or unrelated sprint gates. No product files were inspected or changed; no tests, build, game, TAS, Gym or runtime checks were performed.


