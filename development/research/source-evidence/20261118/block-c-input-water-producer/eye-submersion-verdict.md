# Independent source verdict: local sprint eye-submersion reader

**Terminal: COMPLETED.** Verdict bound to immutable comparison `c-input-submersion-source/comparison.md`; raw SHA-256: `08f044eb70baedf3635f32e8c6f598b1778fe7d0e1d75bd806e9e8dd7d10b72a`.

## Verdict

**ACCEPT — bounded submersion-predicate equivalence.** For the same player position, cached pose-derived eye height, vehicle state, eye-containing fluid cell and water state, with that cell's chunk loaded, the exact 1.14.3 Yarn and 1.14.4 Mojmap predicates have matching gates and threshold math. No reachable divergence was found in this reader dependency.

Both methods return false when the player is riding a boat. Otherwise each computes eye Y as entity Y plus the cached eye-height value, constructs the eye-cell position, optionally returns false when that cell's chunk is not loaded, and then requires matching water plus the strict test `eyeY < cellY + (fluidHeight + 0.11111111F)`. 1.14.3 uses `Entity.isSubmergedIn` (`Entity.java:1032–1045`) and `getStandingEyeHeight`; 1.14.4 uses `Entity.isUnderLiquid` (`Entity.java:1057–1069`) and cached `getEyeHeight()`. Their boat type checks, water test, constant, float addition, strict comparison, and loaded-chunk early return match for the assigned loaded eye-cell case.

The cached values are selected from the current pose and dimensions during `calculateDimensions` / `refreshDimensions` (1.14.3 `Entity.java:2240–2245`; 1.14.4 `Entity.java:2265–2270`), and tracked pose changes refresh that cache. Living-entity sleeping eye height is `0.2F` in both (1.14.3 `LivingEntity.java:2758–2764`; 1.14.4 `LivingEntity.java:2764–2771`). Player values also match: swimming, fall-flying and spin attack use `0.4F`; sneaking uses `1.27F`; other poses use `1.62F` (1.14.3 `PlayerEntity.java:1781–1791`; 1.14.4 `Player.java:1801–1811`).

The local-player update order exposes the submersion bits before these sprint reads. 1.14.3 `PlayerEntity.tick()` calls `updateInWater()` before `super.tick()` (`PlayerEntity.java:218–219`); its `ClientPlayerEntity` override delegates to `super.updateInWater()` before returning (`ClientPlayerEntity.java:976–978`). The inherited living tick later reaches `tickMovement()` (`LivingEntity.java:2039`), whose local-player override contains the sprint gate. 1.14.4 follows the same sequence: `Player.tick()` calls `updateIsUnderwater()` before `super.tick()` (`Player.java:235–236`); `LocalPlayer.updateIsUnderwater()` delegates to the player writer (`LocalPlayer.java:987–989`); then `LivingEntity.aiStep()` is reached (`LivingEntity.java:2042`), with the local sprint reader in its override. The bounded sprint readers use corresponding submersion results in the same water-dependent start and maintenance positions: 1.14.3 `ClientPlayerEntity.tickMovement()` at `:657–675` reads `!isInsideWater() || isInWater()`, plus `isInsideWater() && !isInWater()` for maintenance cancellation; 1.14.4 `LocalPlayer.aiStep()` at `:668–686` reads `!isInWater() || isUnderWater()`, plus `isInWater() && !isUnderWater()`. The contact bits remain a separate producer dependency: the accepted prior contact verdict below is used only as an external condition for relating those expressions. This audit does not re-accept or broaden the contact-producer claim.

## Limits and rejected extensions

The loaded case is conditional: both methods guard the eye-containing chunk with version-specific APIs (`World.isChunkLoaded` / `Level.hasChunk`). No verdict is made for unavailable or partially loaded chunks, differing eye cells or chunk-load boundaries, fluid-current/callback behavior, arbitrary modded overrides, unrelated sprint gates, or the whole sprint routine. The verdict covers this eye-point reader dependency only; it does not establish the underlying contact producer or runtime parity.

## Bound prior contact-producer evidence

The comparison explicitly depends on the following exact prior artifacts, each independently hash-checked against its raw bytes:
- `c-input-water-gate-source\comparison.md` — `3a2bce15a218d961779a90c78eeca0d482ca99e8294265e20301f5e39a58f75c`
- `c-input-water-gate-audit\verdict.md` — `5980a123d975e140bcacc2b032b4d165fa407be1674cec44108d95b350b38f0e`
- `c-input-water-gate-audit\input-identity-correction.md` — `d4219de6e07a2ab582d9b22b59fb7357f6d48fecb791385f373d9e53abde69cb`

## Exact source identity checks

All eight comparison-listed Java digests were recomputed from the read-only canonical source files and matched their provenance/source-manifest entries. 1.14.3 provenance identifies Yarn `1.14.3+build.13`; 1.14.4 provenance identifies version `1.14.4` namespace `mojmap`.

- 1.14.3 Yarn, `net/minecraft/entity/Entity.java` — `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14`
- 1.14.3 Yarn, `net/minecraft/entity/LivingEntity.java` — `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b`
- 1.14.3 Yarn, `net/minecraft/entity/player/PlayerEntity.java` — `e3f361ac4e369d01473f3d84044ee33f9685d182c4485e2e80b7f62756950963`
- 1.14.3 Yarn, `net/minecraft/client/network/ClientPlayerEntity.java` — `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`
- 1.14.4 Mojmap, `net/minecraft/world/entity/Entity.java` — `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`
- 1.14.4 Mojmap, `net/minecraft/world/entity/LivingEntity.java` — `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`
- 1.14.4 Mojmap, `net/minecraft/world/entity/player/Player.java` — `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`
- 1.14.4 Mojmap, `net/minecraft/client/player/LocalPlayer.java` — `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`

No product files were inspected or changed. No tests, build, game, TAS, Gym or runtime checks were performed.
