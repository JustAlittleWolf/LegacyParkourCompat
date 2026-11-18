# 1.21.11 → 26.1: Elytra climbable exit

## Scope and result

Bounded blind-source slice: dry, on-foot local client player, already in fall-flying state with the ordinary chest-slot Elytra, no passenger/fluid/effect interference, same input and velocity, and feet at ordinary blocks versus a climbable ladder/vine state. Block/tag and collision environment is otherwise held constant. This is not full-pair movement coverage or an intermediate-patch boundary claim.

**Result: no movement difference found in this slice.** Both versions retain the same ordered client update, small-velocity deadzone, fall-flying dispatch, and climbable exit behavior. The selected source identities are exact `1.21.11` Mojmap and `26.1` published unobfuscated sources. The canonical 26.1 metadata/source ID is `26.1` (the initial stable 26.1 release); no separate `26.1.0` directory or metadata ID was present. Adjacent root entries `26.1.1`, `26.1.2`, and `26.2` were not used as substitutes.

## Readiness and source identity

Readiness markers say `ready` and their source/artifact manifest digests were recomputed and matched:

- `decompiled_minecraft/1.21.11/mojmap.ready.json`: version and metadata ID `1.21.11`, mapping `mojmap`, 6,622 source files, source manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, artifact manifest SHA-256 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`, client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`.
- `decompiled_minecraft/26.1/unobfuscated.ready.json`: version and metadata ID `26.1`, mapping `unobfuscated`, 6,882 source files, source manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`, artifact manifest SHA-256 `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0`, client SHA-256 `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`.

Exact source class IDs (same package/class paths in both mappings) and verified source-file SHA-256:

| Source class | 1.21.11 Mojmap | 26.1 unobfuscated |
|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |

Both class hashes match their entries in the corresponding source manifests. The 26.1 provenance resolves to metadata ID `26.1`; its published mapping field is not applicable. Its pinned client digest agrees with the ready marker. No source generation or mutation was performed.

## Ordered comparison

1. **Local-player state snapshot:** `LocalPlayer.aiStep` stores `wasFallFlying = isFallFlying()` before `super.aiStep()` in both versions (1.21.11 line 819; 26.1 line 858). `onSyncedDataUpdated` retains the same Elytra-sound gate: shared flag changed, currently fall-flying, and `!wasFallFlying` (1.21.11 lines 546–558; 26.1 lines 584–596).
2. **Pre-travel velocity deadzone:** early in `LivingEntity.aiStep`, players zero horizontal components when horizontal-distance-squared is `< 9.0E-6`; vertical velocity is zeroed when `abs(y) < 0.003` (1.21.11 lines 2899–2914; 26.1 lines 2998–3013). The check precedes the travel profiler section and is unchanged.
3. **Active update before travel:** immediately before reading input and dispatching movement, `aiStep` calls `updateFallFlying()` when the flag is active (1.21.11 lines 2960–2963; 26.1 lines 3059–3062). On the logical client, that method performs fall-distance accumulation, while the `!level().isClientSide()` equipment/can-glide validation and flag-clear path does not run. That ordering and gate are unchanged.
4. **Travel dispatch and branch:** for the dry baseline, `travel` dispatches to `travelFallFlying` while the active flag remains set (1.21.11 lines 2309–2316; 26.1 lines 2392–2399). `travelFallFlying` first calls `onClimbable()`. If true, it runs `travelInAir(input)` and then `stopFallFlying()`; otherwise it applies the Elytra movement update, writes that velocity, and calls `move(MoverType.SELF, ...)` (1.21.11 lines 2441–2455; 26.1 lines 2528–2542).
5. **Actual movement and exit write:** `travelInAir` reaches `handleRelativeFrictionAndCalculateMovement`, which applies relative input, clamps/resets climbable movement via `handleOnClimbable`, and calls `move(MoverType.SELF, ...)` before gravity/friction produces the next delta movement. Thus the climbable exit does move the player that tick, but through the ordinary air/climbable route rather than `updateFallFlyingMovement`. Only after that route does `stopFallFlying()` set shared flag 7 true and then false (1.21.11 lines 2343–2366, 2442–2460, 2534 onward (movement helper) and 2561 onward (climbable clamp); 26.1 lines 2430–2453, 2529–2547, 2623 onward (movement helper) and 2650 onward (climbable clamp)). Both versions preserve this ordering and the two flag writes.
6. **Contact predicate:** `onClimbable()` excludes spectators; while fall-flying it returns false first for `CAN_GLIDE_THROUGH`, then recognizes `CLIMBABLE` or the usable-trapdoor ladder case (1.21.11 lines 1669–1685; 26.1 lines 1689–1705). The same condition order is present on both sides. Therefore ordinary feet that do not satisfy the predicate use the glide route; ladder/vine feet satisfying `CLIMBABLE` and not `CAN_GLIDE_THROUGH` use the air/climbable route and clear the active flag after its move.

The method bodies differ in local names, formatting, and some parameter naming only in the examined logic. No changed gate, operation order, arithmetic, state write, or movement call was found. The comparison makes no trajectory claim beyond these source-level route consequences.

## Coverage boundary

Examined this assigned Elytra climbable-exit slice and its directly reached client tick, deadzone, tag predicate, travel helper and flag callback in both exact sources. This does not establish complete 1.21.11–26.1 player-tick inventory, independent audit, pair freeze, runtime parity, or exact introduction boundary. No implementation, tests, launches, network lookups, Git operations or wiki/product inspection were performed.