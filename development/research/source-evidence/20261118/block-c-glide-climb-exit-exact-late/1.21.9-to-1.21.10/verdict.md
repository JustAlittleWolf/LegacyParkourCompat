# Independent source audit: active Elytra climb exit

## Verdict

**ACCEPTED — bounded no-difference claim.** The report's conclusion is supported for the specified already-active local glide/climb-exit slice. The three examined paired source files are byte-identical, and the checked call order agrees with the report. This is not a full-pair freeze and does not establish runtime parity.

## Frozen input

- Claim/evidence input: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-glide-1219-12110-actual-climb-exit\report.md`
- Independently computed SHA-256: `e93f47cb26cd036f9f26ebebccbedf7f930f86dbbd0ca886278102d64116625a`
- The value above was produced from the file bytes at audit time and matched the assignment's claimed identity.

## Exact source identity and integrity

Both sides are Mojmap. The 1.21.9 hidden source record `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` has SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its exact mapping input `client.txt` is SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`; its 1.21.9 version metadata input is SHA-256 `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`.

For 1.21.10, `decompiled_minecraft/1.21.10/mojmap.provenance.json` (SHA-256 `9a1db4c050c3caa28a1313f89dae9da3b28feaa5f8afba42cab798b47d7ad77c`) identifies `versionId: 1.21.10` and `namespace: mojmap`. Its adjacent `decompiled_minecraft/1.21.10/mojmap.ready.json` reports `status: ready`, `versionId: 1.21.10`, and has SHA-256 `2374d901074b72cd69f0229381d6a11edea7983613c538a2e2e18d9fc73dbeb0`. Its source-manifest digest is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, matching the SHA-256 of adjacent `decompiled_minecraft/1.21.10/mojmap.sources.sha256`.

| Source file | 1.21.9 actual = hidden record | 1.21.10 actual = adjacent manifest |
| --- | --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/world/entity/player/Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |

The actual file hashes, 1.21.9 embedded hashes, and 1.21.10 manifest entries agree for all three files. Because each whole Java file is identical across the pair, differences cannot be hidden elsewhere in those files.

## Independent trace of the assigned route

- `LivingEntity.aiStep()` (both releases, lines 2835–2849): when shared flag 7 says fall-flying, `updateFallFlying()` runs before travel input is constructed and before `travel($$10)`. Dry state bypasses the fluid branch in `travel()` (2275–2283); the still-active flag selects `travelFallFlying()`.
- `LocalPlayer.aiStep()` (774–778) keeps the start/request gate separate: a jump request is gated by `!onClimbable()` before `tryToStartFallFlying()`, then `wasFallFlying` is sampled. With an already-active glide this is not the start path. `super.aiStep()` follows at 834, so that local snapshot precedes the base movement step.
- `LivingEntity.updateFallFlying()` (2901–2921) checks fall-distance accumulation first. The eligibility/cancellation, durability and glide-event branch is guarded by `!level().isClientSide()`. Thus the local client does not revalidate or clear an already-active glide here, even with a default chest Elytra. `Player.canGlide()` (1320–1323) excludes ability-flying; ordinary abilities satisfy that contextual boundary. The vanilla equipment predicate (`LivingEntity.canGlideUsing()`, 3704–3710) requires the GLIDER component, a matching equippable slot and that the next damage does not break it; this is eligibility context, not a new-start claim.
- `LivingEntity.travelFallFlying()` (2383–2397) checks `onClimbable()` first. When true, it calls `travelInAir($$0)` and only after return calls `stopFallFlying()`. In `travelInAir()` (2306–2329), movement is delegated to `handleRelativeFrictionAndCalculateMovement()`; that helper's order (2476–2485) is relative input, climbable velocity handling, then `move(MoverType.SELF, ...)`. Consequently the first move at ladder/vine climbable feet follows the ordinary air route while flag 7 is still active. The subsequent `stopFallFlying()` writes shared flag 7 true then false (2399–2402).
- With no climbable contact, the other branch computes `updateFallFlyingMovement()`, writes its result, calls `move()`, and only then reaches the server-side collision handler. The glide math including the final `multiply(0.99F, 0.98F, 0.99F)` is identical (2404–2426). The climb branch does not execute that glide update or cap.
- `Player.onClimbable()` (1955–1957) returns false for ability-flying and otherwise delegates to the living-entity predicate. This audit assumes the ordinary non-flying player's vanilla predicate is true for the stated ladder/vine contact; it does not audit contact geometry or block state sources.

The report is correct on order and bounded behavior. No version difference was found in active-state reads/writes, updater server gate, climbable-versus-glide routing, first move order or glide movement math.

## Bounds

Only 1.21.9 versus 1.21.10, Mojmap, ordinary local player, dry state, already-active fall-flying with normal abilities and an ordinary chest Elytra, and the branch where vanilla `onClimbable()` is true (ladder/vine feet) were audited. Flight start/final ticks, ability-flying and sprint behavior, the climbable contact/shape implementation, fluid route, and A/B-owned behavior are outside this verdict. No intermediate-patch claim or full-pair coverage claim follows.