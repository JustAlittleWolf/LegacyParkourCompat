# Lava-presence source lifecycle and client movement consumers

## Scope and evidence

This supplement closes the operational source trace for `Entity.isInLava()` in Mojmap 1.16.1 → 1.16.2. It is distinct from the accepted water-first mixed-current finding: it does not make claims about lava-current application, eyes-fluid state, damage behavior, or server-side state production.

Inputs rebound by SHA-256:

- AF author source, `frontier-c/AF/water-lava-fluid-current.md`: `e931848c053565352d3eda9f262dd230fef78765ecc29c822a48a3bfef9eee66`.
- AF independent source audit, `frontier-c/AF-audit/audit.md`: `1a249eca3e95ba2c5cb8934962eb7c34073de43ad9d7a41db4a916c48136ca88`.
- Patch-alias closure, `frontier-c/AF-alias/AF-patch-alias-closure.md`: `3c64bc4d50fb0bef75822f03fed9fb739cf9691df37804853e6c5b065ca63dd5`.
- Patch-alias audit, `frontier-c/AF-alias-audit/audit.md`: `83f2c218fc05377300a9b235df6eac845f283fa11656aa5d3222bef1012524c5`.

Exact canonical Mojmap trees are under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap`. The accepted source-identity record reports the ready-marker and manifest hashes for 1.16.1 and 1.16.2; it also records that the original binary artifact cache is unavailable for rehashing. I independently rehashed the selected source files in the canonical tree. The relevant hashes are:

| Release slice | `Entity.java` | `LiquidBlock.java` | `LivingEntity.java` |
|---|---|---|---|
| 1.16 and 1.16.1 | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `e7b138668f426eaf7c2f89b34dd0448f6ba0eb133963929ebf12d64840f22fb3` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16.2 through 1.16.5 | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `24f1cfbd036cca77ec99d855a028194ed39f583699ce91bc2a4c61957ff22fb7` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |

`LocalPlayer.java` is `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` in 1.16.1 and `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` in 1.16.2. These agree with the accepted source-identity table. The alias audit directly compared 1.16.3 and 1.16.4; their operation-file hashes match 1.16.2, as do 1.16.5. This is only the bounded lava-presence operation, not a full-pair freeze.

## 1.16.1 producer, reset and lifetime

`Entity` owns `protected boolean isTouchingLava`, initially false (`Entity.java:157`). `Entity.isInLava()` returns that field, and its only source writer is `Entity.setInLava()`, which sets it to true (`Entity.java:1065–1071`). A full exact-source search found the only 1.16.1 caller of that setter in `LiquidBlock.entityInside`.

The reset is not in `baseTick`. In the collision-rest portion of each `Entity.move`, after collision resolution and fall-damage handling, the method clears `isTouchingLava` and calls `checkInsideBlocks` (`Entity.java:551–559`). `checkInsideBlocks` forms inclusive block-position bounds from the current bounding box inset by `0.001` on each face; only if `level.hasChunksAt(minPos,maxPos)` is true does it scan those positions and call `blockState.entityInside(level,pos,this)` (`Entity.java:803–828`). If that admission fails, the newly cleared flag remains false. The value otherwise remains latched until the next `Entity.move` reaches this reset-and-callback phase.

`LiquidBlock.entityInside` sets the flag only when its `fluid` is in `FluidTags.LAVA`. It computes `surfaceY = blockPos.y + blockState.getFluidState().getHeight(level,blockPos)` and sets lava if `box.minY < surfaceY || surfaceY > box.maxY` (`LiquidBlock.java:178–185`). For an ordinary ordered AABB, the second disjunct implies the first and the predicate reduces to `box.minY < surfaceY`; the callback contains no separate upper-face intersection test. The callback runs for the scanned block positions after the movement operation, so the old `isInLava()` value consumed before a player's current travel/move can reflect the preceding movement's callback result.

## 1.16.2 cached query and phase

The `isTouchingLava` field, `setInLava()` method and `LiquidBlock.entityInside` override are absent. `Entity.isInLava()` instead returns `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0`.

`Entity.baseTick` calls `updateInWaterStateAndDoFluidPushing()` before its lava-related checks, and sets `firstTick=false` near the end (`Entity.java:362–415`). The 1.16.2 wrapper clears `fluidHeight`, updates water, then scans lava before returning; it no longer returns early on positive water (`Entity.java:957–963`). The scan uses `getBoundingBox().deflate(0.001)`, requires `level.hasChunksAt` for its computed bounds, visits the fluid cells, and stores the maximum positive overlap under the fluid tag (`Entity.java:2641–2705`). Therefore `isInLava()` during the early `baseTick` check is false on the first tick; after that check, the newly cached value is visible to the subsequent AI step of that same `LivingEntity.tick`. Later `LivingEntity.checkFallDamage` may rescan water when needed, but that is not the lava-height refresh; the lava value comes from the base-tick wrapper. The query is at `Entity.java:1071–1073`.

## Client player movement reachability

`LocalPlayer.tick` delegates to `Player.tick` (`LocalPlayer.java:183–186`); `Player.tick` delegates through `LivingEntity.tick`, whose `super.tick` reaches `Entity.tick`/`baseTick` before it invokes `aiStep` (`Player.java:207–234`; `LivingEntity.java:2122–2169`). `LocalPlayer.aiStep` delegates to the player/living AI path (`LocalPlayer.java:652–813`). Thus the cached query and its timing are read on the ordinary client-player tick path.

Two direct movement consumers are present in `LivingEntity`:

1. `travel` selects the lava movement branch with `isInLava() && isAffectedByFluids() && !canStandOnFluid(...)` (1.16.1 `LivingEntity.java:1957`, 1.16.2 `:1959`). That branch applies lava-specific input scaling, movement, drag and gravity. The local player's normal travel path is controlled by `isControlledByLocalInstance`.
2. In `aiStep`, the jumping-fluid branch tests `isInLava()` to select the lava height and then decide between ground-jump and `jumpInLiquid(LAVA)` (1.16.1 `LivingEntity.java:2427–2446`, 1.16.2 `:2429–2447`).

Also, `Entity.baseTick` halves `fallDistance` whenever `isInLava()` is true (`Entity.java:392–395` in 1.16.1; `:400–403` in 1.16.2); that accumulator feeds later movement/fall handling. The same base-tick condition also calls `lavaHurt`, but its damage/fire effects are excluded here. `canSpawnSprintParticle` reads the predicate too, but that use is visual and excluded. Other entity and AI callers found by source search are outside this local-player movement conclusion.

The source establishes that the predicate change reaches client-player movement decisions, independently of damage or server-only behavior. It does not establish a runtime trajectory. A specific transition's result depends on the movement box and callback/scan phases described above.

## Bounded conclusion

The old predicate is a movement-time callback latch, cleared and recomputed by `Entity.move` after collision. The new predicate is a first-tick-gated positive cached fluid-height query refreshed in `baseTick` before local-player AI/travel. The exact accepted alias interval is old behavior for 1.16 and 1.16.1, new behavior for 1.16.2–1.16.5. This closes the producer, reset, phase, client movement-consumer and alias trace for the `isInLava()` operation only. It does not merge the independent lava-current operation into this finding or close the broader 1.16 movement pair.
