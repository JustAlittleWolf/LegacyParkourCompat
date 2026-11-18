# 26.1.2 → 26.2: active Elytra glide at climbable feet

## Bounded result

For a dry, on-foot local player with the default chest Elytra already active, the inspected climb predicate, local activation guard, pre-move climb clamp, glide/ordinary-air route choice, and stop writes are unchanged between 26.1.2 and 26.2. At ordinary feet the active flag stays set and glide math runs. At ladder/vine feet the active flag remains set through the pre-travel client update, then the glide travel method selects ordinary air travel; its first `move` and post-move velocity calculation complete before `stopFallFlying` writes shared flag 7 true and then false.

The adjacent 26.2 change to ordinary-air drag uses the new `AIR_DRAG_MODIFIER` and `FRICTION_MODIFIER` attributes. Their registered default is `1.0`; `computeModifiedFriction(f, 1.0F)` reduces to the original `f`, so the bounded default-player air path retains the old `0.91F` horizontal and `0.98F` vertical drag values. This is not evidence for non-default attribute/equipment combinations.

## Source trace

| Operation | 26.1.2 (`unobfuscated`) | 26.2 (`unobfuscated`) | Comparison |
|---|---|---|---|
| Climbable predicate | `LivingEntity.onClimbable`, 1689–1706 | `LivingEntity.onClimbable`, 1722–1739 | Same spectator rejection; reads `blockPosition()` / `getInBlockState()`; active glide plus `CAN_GLIDE_THROUGH` returns false; otherwise `CLIMBABLE` tag returns true and records the climbable position; usable trapdoor ladder remains the fallback. Ordinary feet without those states do not take the climb branch. |
| Local start request | `LocalPlayer.aiStep`, 854–858; `Player.tryToStartFallFlying` / `startFallFlying`, 1442–1453 | `LocalPlayer.aiStep`, 854–858; `Player.tryToStartFallFlying` / `startFallFlying`, 1463–1474 | Same jump-edge, not-creative-flight-toggle, not-already-jumping, and `!onClimbable()` guard before the local attempt. The attempt requires not already gliding, `canGlide()`, and not in water; it sets flag 7 locally before sending `START_FALL_FLYING`. `canGlide()` rejects ground, passenger, or levitation and scans equipment slots for a valid glider. No custom-item variant was followed. |
| Active tick upkeep | `LivingEntity.tick`, 3060–3062; `updateFallFlying` / `canGlide`, 3126–3160 | `LivingEntity.tick`, 3127–3129; `updateFallFlying` / `canGlide`, 3193–3226 | The `isFallFlying()` check precedes travel in both. `updateFallFlying()` always calls fall-distance accumulation, but its `canGlide()` clearing/durability/event branch is guarded by `!level().isClientSide()`. Thus the local client does not clear an already-active flag there merely because the player is at ladder/vine feet. No `isLocalInstanceAuthoritative()` gate appears in this flag upkeep or stop path. |
| Dry travel dispatch | `LivingEntity.travel`, 2392–2399 | `LivingEntity.travel`, 2430–2437 | Same order: affected water/lava first, else active glide to `travelFallFlying`, else `travelInAir`. The assignment is dry, so the active flag selects the glide method. |
| Glide versus climb exit | `LivingEntity.travelFallFlying` / `stopFallFlying`, 2528–2547 | `LivingEntity.travelFallFlying` / `stopFallFlying`, 2581–2600 | Same predicate and order. `onClimbable()==false`: update glide velocity, then move; client skips server-only collision damage. `onClimbable()==true`: call ordinary `travelInAir(input)` first, then `stopFallFlying()`, which writes flag 7 `true` followed by `false`. |
| First ordinary-air move clamp | `LivingEntity.handleRelativeFrictionAndCalculateMovement` / `handleOnClimbable`, 2623–2664 | `LivingEntity.handleRelativeFrictionAndCalculateMovement` / `handleOnClimbable`, 2676–2717 | Same order: relative input, climb clamp, `move`, then the climb/jump Y response (`0.2`) is returned for subsequent velocity work. On climbable states, the pre-move delta clamps X/Z to `[-0.15F, 0.15F]` and lower-bounds Y at `-0.15F`; ladder sneaking/scaffolding exception remains identical. |
| Ordinary-air drag after first move | `LivingEntity.travelInAir`, 2430–2453 | `LivingEntity.travelInAir` / `computeModifiedFriction`, 2468–2502; `Attributes`, 10–12 and 52–54 | 26.1.2 uses `blockFriction * 0.91F`, vertical `0.98F` except flying animals. 26.2 applies registered modifier defaults of `1.0` through `1.0F - (1.0F - friction) * modifier`, yielding the same constants for this default-player case. It can differ for modified attributes, outside this bounded default case. |

`LocalPlayer.aiStep` stores `wasFallFlying = isFallFlying()` immediately after the jump-attempt block on both sides. That local bookkeeping does not add a climb-exit write; the actual exit writes remain after `travelInAir` returns.

## Source identity and integrity

Both exact source sets are published as `unobfuscated`; each adjacent `unobfuscated.provenance.json` and `unobfuscated.ready.json` names the exact release and mapping. In each ready marker, the recorded SHA-256 of `unobfuscated.sources.sha256` matches the file on disk. The source-file SHA-256 values below match their entries in those manifests.

- 26.1.2 ready marker source-manifest digest: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` (6,882 source files); provenance run is exact version `26.1.2`, mapping option `unobfuscated`, Vineflower `1.12.0`, Java `25.0.3+9-LTS`.
- 26.2 ready marker source-manifest digest: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; provenance run is exact version `26.2`, mapping option `unobfuscated`, Vineflower `1.12.0`, Java `25.0.3+9-LTS`.

| Release | Relative source | SHA-256 |
|---|---|---|
| 26.1.2 | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1.2 | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1.2 | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `net/minecraft/client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/Attributes.java` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |

## Bounds / open coverage

This report is limited to exact 26.1.2→26.2 sources and the described dry, standard local-player path. It does not establish an intermediate patch boundary. Startup, later ticks, custom glider/item behavior, water/lava precedence, environmental interactions, and A/B trajectory behavior were not reviewed. No runtime or test verification was performed.
