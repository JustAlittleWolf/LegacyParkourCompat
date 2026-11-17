# Independent falling-water glass audit verdict

**Disposition:** Source review completed. Both version-bound changes and the 26.2 native-control operation are accepted as source-supported. No code, tests, gameplay or runtime checks were performed.

## Frozen inputs

- Source roster: SHA-256 `7c6bde8cb22b0193bb4ac48bd61c4cc077551be9d57947f66e0a139cd0b7ccb1`; 197 canonical UTF-8/LF `ROOT` and `FILE` rows, covering 14 source roots and 183 movement/glass/player source files. The same digest was recomputed after handoff with zero manifest or source-file mismatches.
- Roots: Feather generation 2 sources for 1.8.9, 1.9.4, 1.10.2, 1.11.2, 1.12, 1.12.1, 1.12.2, 1.13, 1.13.1, 1.13.2, 1.14, and 1.14.4; 1.14.4 Mojmap publication; and native unobfuscated 26.2. The 1.12 and 1.12.1 source hashes are in their in-tree `.decompile-provenance.json` files, which have no separate ready marker.
- Exact source-only handoff payloads were rehashed before reading: `comparison.md` `3af49a348f6490bfb731bf40a8e508302e75caaa497579c9167914e2aa6764f3`; `findings/1.11.2--1.12/falling-water-glass-side-current.md` `369d2a7ade573dce0bd85d3abeda2d44b3eba6bde21d3678b8ffc99ec8105c28`; `findings/1.13.2--1.14/falling-water-glass-side-current.md` `833b75b716aad53ac48189349e5fad21933f37635e634fda2d7fdaaa8d826bde`. All three were valid UTF-8 without CRLF and matched the declared hashes.

## Verdict: 1.11.2–1.12 suppression

**Accept.** In 1.11.2, `LiquidBlock.isFaceSolid` excludes the current liquid and ice, treats `UP` as solid, and delegates remaining faces to `Block.isFaceSolid`, which reads `Material.isSolid`. Both ordinary and stained glass are constructed with `Material.GLASS`; its default `isSolid()` is true. They therefore trigger the falling-water down-current check.

In 1.12, `LiquidBlock.isFaceSolid` first rejects `isExceptionForAttachment(block)`. `Block.isExceptionForPlacement` includes both `Blocks.GLASS` and `Blocks.STAINED_GLASS`, and `isExceptionForAttachment` includes that placement predicate. The falling-state gate and neighbor/neighbor-above probes remain; either glass position now returns false. For an immersed non-flying local player, the legacy `World.applyLiquidDrag` accumulation normalizes nonzero current and adds it at `0.014` scale. `PlayerEntity.hasLiquidCollision()` is `!abilities.flying`, so the cited loss of downward current applies to the ordinary non-flying player path.

The exact source pair supports only a boundary after 1.11.2 and at or before 1.12; it does not establish the introduction patch within that interval.

Evidence: `1.11.2/ornithe-feather/net/minecraft/block/LiquidBlock.java` lines 82–94; `Block.java` lines 329–331 and glass registrations; `Material.java` default solid predicate. `1.12/ornithe-feather/net/minecraft/block/LiquidBlock.java` lines 82–99 and 139–179; `Block.java` attachment/placement exception predicates; `PlayerEntity.java` `hasLiquidCollision`; `World.java` `applyLiquidDrag`.

## Verdict: 1.13.2–1.14 restoration

**Accept.** In 1.13.2, `FlowingFluid.isSolidFace` retains the attachment-exception rejection. The 1.13.2 exception predicate covers ordinary glass by `Blocks.GLASS` and stained glass by `instanceof StainedGlassBlock`, so neither side qualifies for the falling-water acceleration.

In 1.14, that helper no longer asks `isExceptionForAttachment`; after the same-fluid, `UP`, and ice cases, it calls `Block.isFaceSolid`. That method checks whether the requested face of the block’s collision shape is full. Both ordinary and stained glass use the default full block collision shape, so either tested glass face triggers the downward contribution. The stated change is source-supported; its exact introduction is bounded only to after 1.13.2 and by 1.14.

Evidence: `1.13.2/ornithe-feather/net/minecraft/fluid/FlowingFluid.java` lines 54–98, 105–120; `block/Block.java` attachment/placement predicates; `block/Blocks` registration. `1.14/ornithe-feather/net/minecraft/fluid/FlowingFluid.java` lines 54–114; `block/Block.java` lines 391–400; registered glass classes. 1.14.4 Feather and Mojmap sources retain the admitting full-face predicate for the reviewed glass case.

## Verdict: 26.2 native control

**Accept.** Native `FlowingFluid.getFlow` still gates the vertical addition on `FALLING`, probes the horizontal neighbor and its block above in each horizontal direction, and on the first solid face computes `normalize(existingFlow) + (0,-6,0)` before its final normalization. Its dry-neighbor/down-fluid gradient is separately gated by `BlockState.blocksMotion()`.

Ordinary glass is registered as `Blocks.GLASS` using `TransparentBlock`; stained glass extends `TransparentBlock`. Their transparency/no-occlusion behavior does not remove collision. `blocksMotion()` derives from the collision shape, and the default block-support shape also delegates to collision shape. The glass blocks have a full collision shape, so both stop the dry-neighbor/down-fluid gradient lookup and provide full supporting side faces for the falling-water downward addition. This conclusion does not treat panes as full glass blocks.

The local-player witness is reachable without relying on a runtime test: `LocalPlayer.tick()` calls `super.tick()`, whose `Entity` base tick updates fluid interaction. `Player.isPushedByFluid()` returns `!abilities.flying`; an ordinary non-flying player therefore scans and receives water current. `EntityFluidInteraction.update` scans the entity fluid box and accumulates flow vectors, scaling by immersion height below `0.4`. `applyCurrentTo` averages the accumulated vector for `Player` and normalizes only non-player entities, then scales by `0.014` (with the native small-impulse adjustment). Each `FlowingFluid.getFlow` vector itself is normalized; its horizontal terms point along neighbor offsets and the falling-wall term points downward.

Evidence: `26.2/unobfuscated/net/minecraft/world/level/material/FlowingFluid.java` lines 55–116; `world/level/block/Blocks.java` glass registrations; `world/level/block/TransparentBlock.java`, `world/level/block/StainedGlassBlock.java`, `world/level/block/state/BlockBehaviour.java` support-shape and `blocksMotion` behavior; `world/entity/EntityFluidInteraction.java` scan and `Tracker.applyCurrentTo`; `world/entity/Entity.java` base tick, `updateFluidInteraction`, and `isPushedByFluid`; `client/player/LocalPlayer.java` tick; `world/entity/player/Player.java` fluid-push gate.

## Chronology and scope limits

Both ordinary glass and stained glass are registered in the earliest exact source snapshot reviewed, 1.8.9. This confirms they are in scope by that endpoint; the reviewed roster does not establish their original introduction before 1.8.9. It also does not prove a finer patch boundary inside either accepted comparison interval. Stairs, panes, lava, generic fluid-spread behavior and runtime parity were outside this verdict.