# Bubble-column contact discovery — corrected revision 1

## Scope and verdicts

Compared exact source for 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10, 1.21.11 Mojmap and published 26.2 unobfuscated. The scope is ordinary non-passenger LocalPlayer contact admission and direct bubble-column vertical velocity responses.

Two independent changes remain supported:

1. **1.21.4→1.21.5 above-contact classification:** a dry above block with empty collision shape can select the stronger above response even when it is non-air.
2. **1.21.9→1.21.10 short-contact precision gate:** swept candidate visitation can still invoke `state.entityInside(...)`, but BubbleColumnBlock skips its above and inside response when movement is short and the final deflated player box misses the bubble cell.

Candidate traversal changed earlier, from 1.21.8 to 1.21.9: the short-motion cutoff fell from roughly one block of displacement to `1.0E-5F`. That makes short swept candidates available in 1.21.9 and is a prerequisite for the short-exit witness; it is distinct from the bubble response gate added in 1.21.10.

## Local-player tick and authority path

In 1.21.10, a loaded `LocalPlayer.tick` invokes `super.tick()` (`client/player/LocalPlayer.java:195-200`). For a non-passenger, `LivingEntity.aiStep` calls travel when `canSimulateMovement()` and `isEffectiveAi()` are true, then calls `applyEffectsFromBlocks()` only when `!level.isClientSide() || isLocalInstanceAuthoritative()` (`world/entity/LivingEntity.java:2845-2853`). Client-side local authority comes from `Entity.isLocalInstanceAuthoritative()` (`Entity.java:3406-3408`) and `Player.isLocalClientAuthoritative()`, which returns `isLocalPlayer()` (`Player.java:1177-1179`). The path therefore applies to the local player's own ordinary movement; it does not imply server-side replay.

## Separate earlier change: above vs inside

In 1.21.4, `BubbleColumnBlock.entityInside` selects `onAboveBubbleCol` only if `stateAbove.isAir()`; otherwise it calls `onInsideBubbleColumn` (`1.21.4/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java:50-76`). From 1.21.5 it instead tests `stateAbove.getCollisionShape(level, bubblePos).isEmpty() && stateAbove.getFluidState().isEmpty()` and chooses the above response when true (`1.21.5/mojmap/net/minecraft/world/level/block/BubbleColumnBlock.java:51-59`). Note that the collision-shape query receives the **bubble cell's position**, not `bubblePos.above()`; the concrete `SHORT_GRASS` witness is position-independent because its registration uses `.noCollission()` (`1.21.4/mojmap/net/minecraft/world/level/block/Blocks.java:698-710`).

With dry no-collision short grass above, the 1.21.4 state is non-air and takes the inside branch; the 1.21.5 predicate selects above. The direct response formulas already existed on both sides: for `dragDown=false`, above gives `min(1.8, y+0.1)` and inside gives `min(0.7, y+0.06)` plus fall-distance reset (`1.21.4 Entity.java:2483-2505`; `1.21.5 Entity.java:2459-2500`). At callback-entry `y=0.65`, these are `0.75` and `0.70`. The 1.21.5 predicate remains through 1.21.11 and 26.2. This classification difference is not the later short-sweep gate.

## Candidate enumeration and short-sweep gate

The relevant cutovers are separate:

- **Through 1.21.8:** `BlockGetter` uses a short-motion branch when `delta.lengthSqr() < Mth.square(0.99999F)`. That branch visits only blocks in the final box (`1.21.4 BlockGetter.java:172-178`; `1.21.5 and 1.21.8 BlockGetter.java:172-189`). A short path that ends fully beyond a bubble cell does not enumerate that starting cell.
- **From 1.21.9:** the short-motion branch becomes `delta.lengthSqr() < Mth.square(1.0E-5F)`. Otherwise `forEachBlockIntersectedBetween` visits cells in the target deflated box translated back by `-delta`, cells along travel, and then cells in the target box (`1.21.9 BlockGetter.java:171-203`). The 1.21.10 caller continues to use this iterator (`BlockGetter.java:171-203`).
- **From 1.21.10:** `Entity.checkInsideBlocks` computes `movedFar = from.distanceToSqr(to) > Mth.square(0.9999900000002526)` and, once a block contact is admitted, passes `movedFar || deflatedTargetBox.intersects(blockPos)` to `state.entityInside(...)` (`Entity.java:1167-1200`). The squared threshold is `0.9999800001005053` (the literal is a `double`, selecting `Mth.square(double)`; `Mth.java:603-609`).

The 1.21.10 `state.entityInside(...)` call still occurs for an admitted candidate even when that boolean is false. `BubbleColumnBlock.entityInside` receives the flag, then encloses both above and inside response branches in `if (isPrecise)` (`BubbleColumnBlock.java:51-60`). `Entity.onInsideBlock(state)` follows the callback at `Entity.java:1195-1200`; this finding concerns only whether BubbleColumnBlock dispatches its direct vertical response.

BubbleColumnBlock's ordinary collision shape is empty, but its **entity-inside collision shape** is the default full block `Shapes.block()` (`BlockBehaviour.java:317-319`; BubbleColumnBlock normal shape override at `BubbleColumnBlock.java:162-165`). In Entity's admission expression, identity with `Shapes.block()` short-circuits the alternative call to `collidedWithShapeMovingFrom` (`Entity.java:1191-1194`). For this block, do not describe the AABB shape-sweep method as having run: the swept *candidate enumerator* supplies the candidate, then the full-shape identity condition admits it.

### Independently checked vertical-exit witness

Use a standard player AABB sized `0.6 × 1.8` (`1.21.10/mojmap/net/minecraft/world/entity/EntityType.java:1164-1172`), centered at `x=z=0.5`, with feet at `y=0.98`, bubble column `(0,0,0)`, air above, `dragDown=false`, no other colliding blocks, ordinary water travel, gravity `0.08`, no slow-falling effect, and no input. The player box begins at x/z `[0.2,0.8]` and y `[0.98,2.78]`; it overlaps the bubble cell by `0.02` in y and is in water above the `0.014` fluid-height threshold (`Entity.java:1491-1503`).

First, at this stationary position, both releases can admit the bubble callback because the target box intersects cell `(0,0,0)`. Water travel from zero vertical velocity moves by zero; `getFluidFallingAdjustedMovement` changes the post-travel y velocity to `-0.005` (gravity/16, `LivingEntity.java:2488-2500`). The above response then sets it to `min(1.8, -0.005+0.1) = 0.095`. The current position remains at feet `0.98`; the final box intersects the cell, so the 1.21.10 precision boolean is true for that contact too.

On the next local tick, the shared starting y velocity `0.095` moves the player vertically from feet `0.98` to `1.075`. The movement length squared is `0.009025`: it is greater than the 1.21.9 iterator's `Mth.square(1.0E-5F)` cutoff, but below the 1.21.10 precise-distance threshold `0.9999800001005053`. The target deflated box has minY `1.07499`, so it does not intersect the bubble cell under AABB's strict `min < otherMax && max > otherMin` predicate (`AABB.java:239-250`). Yet the 1.21.9+ candidate iterator first checks that final box translated back by `-delta`; its minY is `0.97999`, whose floored cell coordinate is y=0 (`BlockGetter.java:184-190`; `BlockPos.java:491-500`). The bubble cell is therefore visited. Its entity-inside shape equals `Shapes.block()`, so `insideBlock` is admitted by identity and `state.entityInside(...)` is invoked.

Before the post-move block callback, water travel scales the movement y velocity by `0.8F` and subtracts gravity/16: `(0.095 × 0.8F) - (0.08/16) ≈ 0.0710000011` (`LivingEntity.java:2331-2360`, `Attributes.java:44-46`). In 1.21.9 the unguarded open-above branch adds `0.1`, yielding approximately `0.1710000011` (below the `1.8` upward cap). In 1.21.10 the callback is still invoked but receives `isPrecise=false`; the guard skips both above and inside responses, leaving approximately `0.0710000011`. This sequence makes the exit state reachable from the prior admitted bubble response while accounting for actual water travel and callback order.

For the direct downward branch, the same gate suppresses it too. Its above response is `max(-0.9, y-0.03)` for `dragDown=true`; the inside response is `max(-0.3, y-0.03)`. For upward motion they are `min(1.8, y+0.1)` and `min(0.7, y+0.06)`. The gate's changed result is branch suppression; the formulas and caps themselves are not changed here (`1.21.10 Entity.java:2700-2739`).

## Release progression and exact checked boundary

| Release | Relevant result |
|---|---|
| 1.21.4 | Above only for air; short iterator cutoff is near one block. |
| 1.21.5 | Above predicate expands to empty collision shape + empty fluid; iterator still uses near-one-block cutoff. |
| 1.21.6–1.21.8 | No precision callback flag; short iterator cutoff remains near one block. |
| 1.21.9 | First checked iterator with `1.0E-5F` trivial threshold; no precision callback flag or BubbleColumn guard. |
| 1.21.10 | First checked `movedFar || finalBox.intersects(cell)` argument and `if (isPrecise)` BubbleColumn guard. |
| 1.21.11 | Same precision expression and guard. |
| 26.2 published unobfuscated | Same precision expression and guard. |

The observed release boundary for the response gate is exactly 1.21.9→1.21.10: 1.21.9 lacks both the precision argument and bubble guard; 1.21.10 has both. This states the release boundary established by exact checked endpoints, not a commit timestamp.

## Provenance and source hashes

All listed primary files were matched to the per-release source SHA-256 manifest or hidden `.decompile-provenance.json` source map. The 1.21.6/.7/.9 hidden manifests record version-specific Mojang client and official client-mapping-text inputs. Root ready markers identify versions and source-manifest hashes; 26.2 is the published unobfuscated tree.

The primary Entity/BubbleColumn hashes and the checked provenance types remain as in the immutable previous comparison. Additional corrected-witness files also hash-match their manifests: 1.21.4 `BlockGetter.java` `8bd936e3ecac5720a36b675f4910801e9d201e899b34c316c6d0be9a12d4da20`; 1.21.5 `BlockGetter.java` `8494b6a560adcd84dec9340cae99a937fa98a5153fa02cf8764348c94c266d76`; 1.21.8 `BlockGetter.java` `d113fbe2638dea44595ed924d540859d9240a3e4633f9298cf35dcb6bdfec18f`; 1.21.9 `BlockGetter.java` `a9e5a28474bd036451daa7fa1500d37a0a2e0f7bee37bac6765ac4d58571d83c` and `BlockPos.java` `f2597185a8cfd7bf20ab5c6e29039977a35ca0b3083154815c21fabb49ef58f4`; 1.21.10 `EntityType.java` `11bc0dcc9a447bcc4fb369a29f78f76cbe9cd5b4eb5f05c8a0fa520c9ac66bab`, `BlockPos.java` `f2597185a8cfd7bf20ab5c6e29039977a35ca0b3083154815c21fabb49ef58f4`, `Mth.java` `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1`, and `Attributes.java` `b941c0556d9ed3c3ce1ffd4c993c9902287a690230e1510696ff13f9efdf2020`.