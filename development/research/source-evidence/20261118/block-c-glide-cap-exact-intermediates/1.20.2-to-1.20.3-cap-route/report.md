# 1.20.2 → 1.20.3 active Elytra route and climbable cap

## Scope and result

Compared exact Mojmap decompiled sources for a local player on the logical client, ordinary dry movement, already-active glide with a standard enabled Elytra equipped in the chest slot, and either ordinary feet or ladder/vine feet. The bounded operations were glide-state update/cancellation, active-glide versus ordinary/climbable travel routing, and the climbable velocity cap's pre-move phase. Start eligibility, fluid/contact/collision internals, and final movement/tick accumulation are outside this report.

**No behavioral difference found in this slice.** `LivingEntity.updateFallFlying`, the travel branch predicates and ordering, the active-glide pre-move velocity update, and the ordinary branch's climbable cap have the same gates and expressions in both releases.

## Exact source evidence

All references below are relative to `decompiled_minecraft/<version>/mojmap/`.

- `net/minecraft/world/entity/LivingEntity.java`, 1.20.2 lines 2591–2599 and 2639–2665; 1.20.3 lines 2593–2601 and 2641–2667: `updateFallFlying()` runs before travel in the `travel` phase. It starts from shared flag 7; retains it only when airborne, not a passenger, not levitating, and the chest slot holds an enabled Elytra. For the client, the computed value is **not written back**: `setSharedFlag(7, value)` is guarded by `!level.isClientSide`. Thus the method's cancellation decision does not itself clear the client flag; authoritative flag synchronization remains relevant. The same predicates and server-only write gate occur in both releases. `Player.travel()` (1.20.2 lines 1450–1474; 1.20.3 lines 1451–1473) also has an earlier ability-flight branch: when `abilities.flying && !isPassenger()`, it runs `super.travel()` and then clears flag 7 on either side. The specified ordinary player path takes the `else` arm and delegates to the same superclass glide route in both versions.
- `LivingEntity.java`, 1.20.2 lines 2038–2166; 1.20.3 lines 2035–2163: `travel()` routes water, then lava, then `isFallFlying()`, then ordinary friction movement. In the specified dry, already-active case, the glide branch wins before the ordinary branch regardless of ladder/vine feet. That branch applies its glide math, multiplies velocity by `(0.99F, 0.98F, 0.99F)`, then calls `move`. Its later `onGround()` cancellation also writes flag 7 only on the logical server. Neither route nor gate differs.
- `LivingEntity.java`, 1.20.2 lines 1511–1525; 1.20.3 lines 1508–1522: `onClimbable()` reads the feet block state and returns true for `BlockTags.CLIMBABLE` (including ladder/vine states), subject to the same spectator and trapdoor checks.
- `LivingEntity.java`, 1.20.2 lines 2205–2247; 1.20.3 lines 2202–2244: only the ordinary `handleRelativeFrictionAndCalculateMovement()` path calls `handleOnClimbable()` before `move`. When feet are climbable, it clamps x/z to `[-0.15F, 0.15F]`, sets y to at least `-0.15F`, and suppresses negative y when `isSuppressingSlidingDownLadder()` and the entity is a `Player`, unless the feet are scaffolding. This remains a pre-move delta clamp after `moveRelative`; active glide bypasses it and uses the separate glide velocity update above. The helper body and call phase are unchanged.
- `net/minecraft/client/player/LocalPlayer.java` is byte-identical across these exact outputs (SHA-256 below); no LocalPlayer-specific change alters this route. `Player.travel` retains the same glide-related branch in the inspected ranges; the 1.20.2-only movement-statistics operation is outside scope.

## Source identity and hashes

The 1.20.2 tree uses the adjacent canonical source sidecars. `mojmap.ready.json` identifies version `1.20.2`, 4,904 source files, and source-manifest SHA-256 `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; the client artifact SHA-256 recorded there is `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`. The 1.20.3 tree has its full `.decompile-provenance.json`; it pins release metadata at `https://piston-meta.mojang.com/v1/packages/86c3a686b59ee1bcd70febee4de962ec3e1a5d87/1.20.3.json` (SHA-256 `05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`), official Mojang client SHA-256 `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`, and mapping file SHA-256 `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.

SHA-256 of the exact class files used (the 1.20.2 values also match its source-manifest entries; 1.20.3 values match `.decompile-provenance.json`):

| Source | 1.20.2 | 1.20.3 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |
| `net/minecraft/world/entity/player/Player.java` | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `218da60bc4f8c9279f56eb87e2cf2c0af79562fbe3d818a408429c656efb95dd` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |

Sidecar identities: 1.20.2 `mojmap.ready.json` SHA-256 `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; `mojmap.provenance.json` SHA-256 `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`. 1.20.3 `.decompile-provenance.json` SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`.



