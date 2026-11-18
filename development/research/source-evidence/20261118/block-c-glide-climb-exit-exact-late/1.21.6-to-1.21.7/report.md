# Source comparison: 1.21.6 → 1.21.7 Elytra climb exit

## Scope and result

Compared the exact Mojmap sources for an already-active fall-flying local player, dry and on foot, with a default chest Elytra, at a climbable ladder/vine versus ordinary feet. Start/final ticks, custom gear, fluid behavior, the underlying climbable predicate, block shapes/geometry, and rendering are excluded. **No difference was found in the requested movement path.** The relevant source files have identical SHA-256 hashes across the pair, and each file matches its own provenance manifest.

## Ordered path

The `LivingEntity.aiStep` travel section (1.21.6 `LivingEntity.java:2805–2821`; identical 1.21.7 lines) checks `isFallFlying()` and calls `updateFallFlying()` before the movement call. `updateFallFlying` first calls `checkFallDistanceAccumulation()` (`LivingEntity.java:2871–2873`). That helper caps `fallDistance` to `1.0` only when current vertical velocity is greater than `-0.5` and the distance exceeds `1.0` (`Entity.java:2674–2677`). Its position and condition are unchanged.

In the dry case, `LivingEntity.travel` skips its fluid branch and selects `travelFallFlying` when `isFallFlying()` is true; otherwise it selects `travelInAir` (`LivingEntity.java:2245–2253`). For the active glide at a climbable surface, `travelFallFlying` takes its `onClimbable()` branch, calls `travelInAir($$0)`, then calls `stopFallFlying()` (`LivingEntity.java:2353–2361`). Thus ordinary-air movement happens before the stop writes. `stopFallFlying()` writes shared flag 7 as `true`, then `false` (`LivingEntity.java:2369–2372`). In the ordinary-feet branch, the glide route updates velocity and calls `move` without this climb-exit stop (`LivingEntity.java:2362–2367`).

The active updater's cancellation path is server-only: after the fall-distance check, `updateFallFlying` tests `!level().isClientSide`; only there can failed `canGlide()` write flag 7 false and return (`LivingEntity.java:2871–2878`). Therefore that updater does not cancel an already-active local client glide before travel. In `aiStep`, the call to `travel` itself is in the normal controlling-passenger / `canSimulateMovement() && isEffectiveAi()` dispatch (`LivingEntity.java:2809–2817`). The separate local-authority check guards `applyEffectsFromBlocks()` after travel, not this travel route (`LivingEntity.java:2821`).

All of these statements are identical in 1.21.6 and 1.21.7. This establishes no behavioral delta for the bounded operation; it does not establish coverage of other Elytra paths or intermediate releases.

## Exact source provenance and hashes

Canonical source roots: `decompiled_minecraft/1.21.6/mojmap/` and `decompiled_minecraft/1.21.7/mojmap/`. Each contains `.decompile-provenance.json`; the version directories contain only the `mojmap` output directory (no retained previous/alternate mapping tree was present). The manifests identify the mapping inputs as Mojang `client.txt` and exact client JAR inputs. Manifest `sources` hashes below were recomputed from the corresponding files and match.

| Version | Client JAR input SHA-256 | Mojang client mappings input SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.21.6 | `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` | `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261` | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` |
| 1.21.7 | `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9` | `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50` | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` |

| Source file | 1.21.6 SHA-256 | 1.21.7 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
