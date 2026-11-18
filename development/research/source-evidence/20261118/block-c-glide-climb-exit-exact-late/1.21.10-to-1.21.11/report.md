# Elytra climbable exit predicate

## Bounded comparison

Exact client pair: Minecraft 1.21.10 → 1.21.11, Mojmap, from the canonical local source trees `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`.

The bounded operation is the player-visible travel dispatch while fall flying is already active, for a dry, ordinary on-foot local player with no abilities flight and no gear/start/final-tick transition. The supplied ladder/vine feet condition enters the climbable predicate; membership in any additional environmental tag is left to the environmental lane.

## Finding

1.21.10, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `onClimbable()` lines 1662–1678; `travel(Vec3)` lines 2275–2284; `travelFallFlying(Vec3)` and `stopFallFlying()` lines 2383–2402.

1.21.11, Mojmap: `net/minecraft/world/entity/LivingEntity.java`, `onClimbable()` lines 1669–1687; `travel(Vec3)` lines 2309–2321; `travelFallFlying(Vec3)` and `stopFallFlying()` lines 2441–2460.

Difference: `LivingEntity.onClimbable()` now returns false when the entity is fall flying and its current `getInBlockState()` belongs to `BlockTags.CAN_GLIDE_THROUGH`; this guard precedes the existing `BlockTags.CLIMBABLE` and usable-trapdoor checks. In 1.21.10 the climbable tag check had no fall-flying exception. This adds a state-tag predicate dependency to the movement query; tag contents and block shape/state behavior are outside this lane.

Movement meaning: With fall flying active, 1.21.10 takes `travelFallFlying`'s climbable arm when `onClimbable()` is true: it runs ordinary `travelInAir(input)` first, then calls `stopFallFlying()`, whose ordered writes set shared flag 7 true and then false. That ordinary-air move therefore occurs while the flight flag is still active; the flag is cleared only afterward. In 1.21.11, if the in-block state is `CAN_GLIDE_THROUGH`, `onClimbable()` returns false before the climbable arm, so the same first move instead applies `updateFallFlyingMovement`, moves with the resulting velocity, and does not call `stopFallFlying`. If the supplied ladder/vine state is not in `CAN_GLIDE_THROUGH`, the existing climbable exit path remains. Whether the supplied state overlaps that new tag is an environmental-lane dependency, not established here.

`LivingEntity.aiStep()` still checks `isFallFlying()` and calls `updateFallFlying()` immediately before building travel input and calling `travel()`: 1.21.10 lines 2834–2849; 1.21.11 lines 2960–2975. `updateFallFlying()` still performs fall-distance accumulation, then gates its eligibility cancellation and subsequent server-side work behind `!level().isClientSide()`: 1.21.10 lines 2901–2920; 1.21.11 lines 3027–3046. Thus that pre-travel update does not cancel the already-active local-client glide on this path. `Player.onClimbable()` is unchanged in logic in both releases (1.21.10 lines 1955–1957; 1.21.11 lines 2043–2045): abilities flight returns false, otherwise it delegates to `LivingEntity.onClimbable()`.

## Evidence identity and bounds

The adjacent provenance records identify exact releases 1.21.10 and 1.21.11, `mojmap`, a shared exact run with batch IDs `1.21.8,1.21.10,1.21.11`, Vineflower 1.12.0, mapping-io 0.9.1, Java 25.0.3+9-LTS, Gradle 9.7.1 and Tiny Remapper 0.14.1. Their ready markers verify exact metadata IDs and match the published source-manifest SHA-256 values. The generated ready-output paths named in the markers are no longer present; no hidden `.decompile-provenance.json` or retained `.*previous*` directory was present beside either canonical version tree. The source manifest entries match the actual local source file hashes below.

| Release | Local provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 | `LivingEntity.java` SHA-256 | `Player.java` SHA-256 |
|---|---|---|---|---|---|
| 1.21.10 Mojmap | `9a1db4c050c3caa28a1313f89daeda9b28feaa5f8afba42cab798b47d7ad77c` | `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0` | `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| 1.21.11 Mojmap | `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` | `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b` | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |

This is one bounded source finding, not a full pair inventory or independent audit. It does not establish the exact introduction point inside 1.21.10–1.21.11, runtime parity, `CAN_GLIDE_THROUGH` membership for the supplied blocks, or changes to environmental shapes, fluid routing, input/start logic, gear, or glide-final-tick behavior.
