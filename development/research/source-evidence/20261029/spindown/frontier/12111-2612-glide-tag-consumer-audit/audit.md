# Independent audit: 1.21.11 → 26.1.2 `CAN_GLIDE_THROUGH` closure

## Verdict

The reported endpoint identities, cited file hashes, declaration/provider inventory, `LivingEntity.onClimbable()` predicate order, `Player` wrapper, and the principal reachable local-player fall-flying dependency are confirmed against the canonical read-only source trees and their manifests. The bounded result is that, while fall-flying and when `getInBlockState()` is a tag member, `onClimbable()` returns false before the climbable/trapdoor checks; through the ordinary client travel path this prevents `travelFallFlying()` from falling back to `travelInAir()` and stopping flight. No paired difference was found in these slices.

One reachability sentence in the closure overstates the consequences: the tag-gated false result cannot also suppress the `travelInAir()` climb handling and jump-lift checks in that same fall-flying state, because the dispatcher does not enter `travelInAir()` when fall-flying, and `travelFallFlying()` enters it only when `onClimbable()` is true. This narrows that sentence but does not affect the primary `travelFallFlying()` dependency or the source-equality result. The water-travel lift and `isPushable()` are separate callers; the former is selected before the fall-flying branch only when the block-position fluid gate succeeds, while the latter is independently callable.

## Identity and manifest checks

I read the two canonical source roots named in the closure, their ready/provenance sidecars, and their source/artifact manifests. Ready status, `versionId`, mapping namespace and source-manifest SHA-256 match the claims and provenance identity. Recomputed manifest digests also match the ready fields:

| Endpoint | Source-manifest digest (recomputed) | Artifact-manifest digest (recomputed) | Manifest rows |
|---|---|---|---|
| 1.21.11 Mojmap | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | 6,622 source rows |
| 26.1.2 unobfuscated | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | 6,882 source rows |

Every source file cited by the closure (including its `Player` and `LocalPlayer` caller references) has a matching release source-manifest row, and a direct SHA-256 recomputation of the extracted file matches that row:

| Release / mapping | Relative source file | SHA-256 |
|---|---|---|
| 1.21.11 Mojmap | `net/minecraft/tags/BlockTags.java` | `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3` |
| 1.21.11 Mojmap | `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 26.1.2 unobfuscated | `net/minecraft/tags/BlockTags.java` | `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f` |
| 26.1.2 unobfuscated | `net/minecraft/data/tags/VanillaBlockTagsProvider.java` | `e34485a342b377f530a28e4eb81c40163d69a0697acf7cfd19ee13baaab7ffbe` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1.2 unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |

The client artifact SHA-256 values stated by the permitted pair-coverage audit also match the respective `client.jar` entries in the artifact manifests and the ready markers. I did not need to reopen or hash the original jars to verify source-file provenance.

## Claim checks

### Key, provider, and consumer inventory

A case-insensitive search across each full extracted source tree found exactly three `CAN_GLIDE_THROUGH` / `can_glide_through` occurrences: the declaration, the built-in provider, and the `LivingEntity` read. The declarations are `BlockTags.CAN_GLIDE_THROUGH = create("can_glide_through")` (1.21.11 `BlockTags.java:125`; 26.1.2 `:127`). The provider chains are equivalent at 1.21.11 `VanillaBlockTagsProvider.java:320–324` and 26.1.2 `:319–323`: `VINE`, both twisting-vine blocks, both weeping-vine blocks, and the `CAVE_VINES` tag. This verifies source declarations only; it says nothing about emitted or effective resources.

### Predicate and player dispatch

The two complete `LivingEntity.onClimbable()` bodies have the same order:

1. Return false for spectators.
2. Read `blockPosition()` and `getInBlockState()`.
3. If `isFallFlying()` and that state is in `CAN_GLIDE_THROUGH`, return false.
4. Otherwise test `CLIMBABLE`, update `lastClimbablePos`, and return true; next test the usable trapdoor case and update the same position; otherwise return false.

The source lines are 1.21.11 `LivingEntity.java:1669–1685` and 26.1.2 `:1689–1705`; only local-variable names differ. In both releases `Player.onClimbable()` is `abilities.flying ? false : super.onClimbable()` (old `Player.java:2043–2044`; new `:2003–2004`). `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player` in both endpoints, and neither local-player class declares an `onClimbable()` override.

### Reachable local-player paths and qualification

In `LocalPlayer.aiStep`, jump input and creative-flight/jump gates precede `!onClimbable() && tryToStartFallFlying()`; a successful request sends `START_FALL_FLYING` (old `LocalPlayer.java:815–816`; new `:854–855`). The method then reaches `Player.aiStep` and `LivingEntity.aiStep`; the inherited living step calls `this.travel(...)`. The tag predicate's fall-flying gate is inactive before flight has started, and `tryToStartFallFlying()` itself rejects a player already fall-flying. Thus the pre-start `onClimbable()` check is a reachable caller but does not activate the tag-specific branch for starting flight.

For an already fall-flying local player, the inherited `LivingEntity.travel()` dispatch checks the fluid gate first, then selects `travelFallFlying()` when `isFallFlying()` is true; otherwise it selects `travelInAir()` (old `LivingEntity.java:2309–2316`; new `:2392–2400`). In the non-fluid, fall-flying path, `travelFallFlying()` calls `onClimbable()` and only calls `travelInAir()` plus `stopFallFlying()` when the predicate is true (old `:2441–2445`; new `:2528–2532`). Under the tag-and-fall-flying predicate condition, it is false, so the specialized fall-flying branch continues. This confirms the closure's principal movement-reachable dependency.

The closure also groups ordinary-air climb handling and jump lift (`handleOnClimbable` and `handleRelativeFrictionAndCalculateMovement`; old `LivingEntity.java:2534–2543,2561–2569`; new `:2623–2633,2650–2659`) with responses suppressed by a fall-flying tag state. That inference does not follow from the caller graph: `travel()` routes a fall-flying entity away from `travelInAir()`, and `travelFallFlying()`'s only `travelInAir()` call is guarded by `onClimbable() == true`, contrary to the tag predicate result. These particular ordinary-air checks therefore are not reached while that predicate returns false because of the tag. `travelInWater()` contains a separate horizontal-collision check (old `:2400–2402`; new `:2487–2489`) but is reached only when the earlier fluid gate selects it; `isPushable()` is another separate use (old `:3202–3205`; new `:3309–3312`). Their mere call sites do not establish the ordinary-air reachability claim.

The distinction does not undermine the finding that the tag gates `onClimbable()` during fall-flight travel. Nor does this audit establish effective tag membership, loaded resource composition, server producer timing, a specific trajectory, or runtime parity.
