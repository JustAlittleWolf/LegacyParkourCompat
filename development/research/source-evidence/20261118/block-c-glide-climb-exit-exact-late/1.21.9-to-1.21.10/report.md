# 1.21.9 → 1.21.10: active Elytra travel at climbable feet

## Result

No source difference found in the assigned already-active glide/climb-exit slice. The exact `LivingEntity`, `Player`, and `LocalPlayer` decompiled source files are byte-identical between these versions, and their per-file source hashes match each side's source record. No change was found to active-state writes, updater cancellation, the glide/air route, its ordering, local eligibility, or glide movement math. This is a bounded no-difference report, not full-pair coverage.

## Exact source identity and hashes

Both sides use Mojmap (official Mojang mappings). 1.21.9 is identified by the version-metadata input URL `https://piston-meta.mojang.com/v1/packages/91f383c21d8cd06abc9a569f2976b58da192a642/1.21.9.json`; its embedded `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` records official mapping input `client.txt` SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`. The hidden provenance file itself hashes to `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` (SHA-256). The 1.21.10 adjacent `mojmap.provenance.json` names `versionId: 1.21.10`, `namespace: mojmap`, and a decompile batch containing 1.21.10; its SHA-256 is `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`. Its adjacent source manifest hash is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching `mojmap.ready.json`.

| Mojmap source | 1.21.9 SHA-256 (actual = embedded record) | 1.21.10 SHA-256 (actual = adjacent source manifest) |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |

## Paired slices checked

- `LivingEntity.java`, both versions, `aiStep()` around 2833–2837: if `isFallFlying()`, `updateFallFlying()` runs before the later travel phase. `updateFallFlying()` around 2901–2935 always checks fall-distance accumulation, then only on the logical server tests `canGlide()`, clears shared flag 7 and returns when that check fails, or handles the existing tick-based durability/event branch. The paired code is byte-identical. For the local logical client, this server-only cancellation/durability/event body is skipped.
- `Player.java`, both versions, `canGlide()` override 1320–1323: ability-flying is excluded by `!abilities.flying`; this separately owned behavior is recorded only as a boundary. `tryToStartFallFlying()` 1376–1383 checks inactive state, `canGlide()`, and water; `startFallFlying()` 1385–1387 sets shared flag 7 true. These writes and gates are identical. Flight-start eligibility is outside the already-active slice.
- `LivingEntity.java`, both versions, `travel()` 2275–2284 and `travelFallFlying()` 2383–2402: fluid routing precedes the active-fall-flying check; otherwise the active flag selects `travelFallFlying`, ahead of `travelInAir`. If `onClimbable()`, the method runs `travelInAir($$0)` first, then calls `stopFallFlying()`. Otherwise it computes the glide vector, writes it, moves, and runs fall-flying collision handling only when not client-side. `stopFallFlying()` writes flag 7 true and then false in that order. No paired order/write change exists.
- `LivingEntity.java`, both versions, `updateFallFlyingMovement()` 2404–2427: glide acceleration/order and terminal `multiply(0.99F, 0.98F, 0.99F)` are identical. No changed cap target or cap literal appears in this method; the climbable branch does not call it. No claim is made about the separate collision/geometry inputs.
- `LocalPlayer.java`, both versions, `aiStep()` 774–778: the jump-triggered request is gated by `!onClimbable()` before `tryToStartFallFlying()`, then `wasFallFlying` is updated. This gate and order are identical, but the start/request tick is excluded. The local updater and ordinary on-foot path are otherwise byte-identical in the inspected files.

## Bounds

Assignment covered a dry, already-active, ordinary local player with normal abilities and an ordinary chest Elytra, comparing ordinary feet to ladder/vine climbable feet; default-Elytra eligibility was read only as context for the `canGlide()` gate. Ability-flying and sprint behavior are excluded. Flight start/final ticks are excluded. Underlying ladder/vine contacts, block geometry, fluid paths, and A/B-owned operations are excluded. No cross-pair or intermediate-patch conclusion follows.



