# Independent audit: 1.21.3–1.21.4 block callback segment closure

## Binding and verdict

Audited input: `.native-source/20261028/frontier-c/1213-1214-block-segment-callback-closure/report.md`, SHA-256 `11c1e42f76911b4f527e3dc360e24834d81ea15374a12f6e41fc90a25a5b0c50` (matches its `report.sha256`).

**Verdict: PASS for the bounded source closure.** I independently checked the cited exact Mojmap sources and provenance for the scoped callback, traversal, local-player, piston and recorder claims. I found no material factual errors. This verdict accepts only this bounded dependency bundle for exact versions 1.21.3 and 1.21.4; it does not freeze the full pair or establish runtime parity, trajectory effects, or every segment/state combination.

## Reproducibility and source identity

Canonical read-only sources were available at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\net\minecraft`.

For both releases, ready markers identify the matching version and Mojmap mapping, report `status=ready`, name Vineflower, and agree with actual source file counts and byte totals. Ready-marker source-manifest and artifact-manifest SHA-256 values match the actual files. Provenance identifies one Mojmap decompile run for `1.21.1,1.21.3,1.21.4,1.21.5`, with Vineflower 1.12.0 and mapping-io 0.9.1; each release marker's metadata ID matches its release. Client jar hashes match their artifact-manifest entries.

| Release | Source files / bytes | Source manifest SHA-256 | Artifact manifest SHA-256 | Client jar SHA-256 |
| --- | ---: | --- | --- | --- |
| 1.21.3 | 5,655 / 26,544,115 | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` | `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b` |
| 1.21.4 | 5,744 / 26,784,732 | `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841` | `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` |

Every source-file hash listed in the closure report was independently recomputed and matches both the file and its source-manifest entry, including `Entity`, `LivingEntity`, `LocalPlayer`, `BlockGetter`, `BlockBehaviour`, the three block classes, `PistonMovingBlockEntity`, and `ServerGamePacketListenerImpl` on both sides.

## Independently verified claims

- **Callback admission:** `Entity.checkInsideBlocks` iterates each recorded movement in list order, traverses candidate positions, reads the current state, skips air, and de-duplicates packed positions through `visitedBlocks`. It asks the state for `getEntityInsideCollisionShape`; the base `BlockBehaviour` provider returns `Shapes.block()`. None of `WebBlock`, `SweetBerryBushBlock`, or `PowderSnowBlock` overrides this provider. The full-block sentinel bypasses the segment/shape intersection predicate and reaches `entityInside`; non-sentinel shapes use the swept segment predicate. `visitedBlocks` clears after a normal traversal, while an alive-check early return exits before that clear.
- **Traversal and bounds:** both `BlockGetter.boxTraverseBlocks` implementations keep ordered unique candidates from a linked set and the AABB's block range. In 1.21.3 the traversed endpoints are offset using a normalized delta scaled by `1.0E-7`; in 1.21.4 the traversal starts at the AABB minimum and its subtraction by the full delta has no epsilon offset. `Entity.checkInsideBlocks` in 1.21.3 calculates one deflated current bounding box before iterating movement entries. In 1.21.4 it calculates a deflated `makeBoundingBox(to)` inside the movement-entry loop. These are source-level admission differences; their outcomes depend on concrete coordinates and are not a trajectory claim.
- **List lifecycle:** both no-argument `applyEffectsFromBlocks()` methods pass `(oldPosition(), position)`. The 1.21.3 overload appends that movement, checks the live list, then clears `movementThisTick`; the 1.21.4 overload snapshots the list and clears the live list before checking the snapshot. Both process `blocksInside` and clear that set after the check. On the normal path, this makes each invocation consume its movement entries; an early return from the inner checker does not execute its `visitedBlocks.clear()`.
- **Callback bodies:** `WebBlock` applies `(0.25, 0.05F, 0.25)`, using `(0.5, 0.25, 0.5)` with Weaving. `SweetBerryBushBlock` applies `(0.8F, 0.75, 0.8)` only to living entities other than foxes and bees; its damage branch is guarded by `ServerLevel`. `PowderSnowBlock` applies its multiplier under the stated living-entity / `getInBlockState()` condition, then sets the powder-snow flag. Callback code is the same across these two releases; `SweetBerryBushBlock` differs elsewhere in the file. These checks do not extend the historical emulation scope to damage or particles.
- **Ordinary local-player path:** `LocalPlayer.tick()` invokes `super.tick()` in both releases, and `LocalPlayer.move` delegates to `super.move`. `LivingEntity` invokes `applyEffectsFromBlocks()` after travel under `!isClientSide || isControlledByLocalInstance`. `LocalPlayer` returns true for `isLocalPlayer()` and `isEffectiveAi()`, while `Entity.isControlledByLocalInstance()` consults a controlling player's local-player predicate or falls back to effective AI. This verifies local callback reachability on the ordinary client path and does not imply remote-client living entities pass the same gate.
- **Client piston path:** `PistonMovingBlockEntity.tick` calls `moveCollidedEntities` while progress is below 1 without a logical-side guard at that branch. The push helper calls `entity.move(MoverType.PISTON, delta)` and then the no-argument `entity.applyEffectsFromBlocks()`. Thus a local player selected for a piston push can enter the callback path on the client. The supplied segment remains the entity's `oldPosition()` to its current position; the report correctly avoids asserting concrete duplicate callback outcomes.
- **Recorder exclusion:** whole-source searches in each release found `recordMovementThroughBlocks` declared in `Entity` and exactly two call sites, both in `ServerGamePacketListenerImpl` (1.21.3 lines 452 and 930; 1.21.4 lines 454 and 990). These are server packet-handler paths; they do not provide a LocalPlayer accumulation path.

## Boundaries

The source confirms which callback code can be reached when the relevant gates and candidate positions admit it. It does not establish how a concrete movement trajectory changes, whether callbacks occur for every possible block position, patch-introduction boundaries, or complete player movement inventory coverage. No tests, builds, or launches were run.
