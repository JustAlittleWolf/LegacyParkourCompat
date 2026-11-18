# 1.13 / 1.13.1 AutoJump center-column source witness

## Witness

This is a bounded follow-up to the verified exact Feather Gen2 1.13 / 1.13.1 source pair. Keep the same supported standing east-step fixture: feet y=64, actual post-move `(8.99,64,0.5)`, actual delta `(0.10,0,0)`, Speed II, Jump Boost II (amplifier 1; cap `o=2.7F`), east border maxX=10, Stone support `(8,63,0)` and `(9,63,0)`, no other colliding entities, and AIR in the direct headroom cells except for the explicitly added block below. Add one stored full Stone block at `(11,66,0)`, beyond the static border. A block may remain in loaded world state outside a border after the border was moved inward; border collision synthesis is a query response and does not erase the stored block state.

## Why the query shape stays the same

Both provider chains compute active border collision for the stated positions. The outside-world-border flag does not turn off the collision query: `collidesWithBorder` is true for x=8.89 and 8.99 under the strict one-block-expanded center bounds, and `getBlockCollisions` receives that computed boolean.

In 1.13, `WorldView.getBlockCollisions` substitutes `VoxelShapes.block()` for cells outside the border when the collision flag is active (`WorldView.java:138–153`); it does not fetch their stored `BlockState` on that path. In 1.13.1 the streamed provider makes the same substitution (`WorldView.java:130–143`) and likewise skips `getBlockState` for those outside cells. The added `(11,66,0)` cell therefore cannot alter the block-collision mask: it is outside the border and its physical full-block shape is equivalent to the synthetic full cube in any case. The version-specific edge guards also omit this particular x=11 / top-Y scan-edge cell from the mask. No nearby entity adds a collision shape in this witness.

The previous combined-mask trace remains: the first raised-ray box in 1.13 is `[10,11] × [64,66] × [-2,3]`, with center floored to `(10,65,0)`; in 1.13.1 it is `[10,12] × [64,66] × [0,1]`, with center floored to `(11,65,0)`. Both begin with `s=66` from the aggregate box.

## Direct block-state sampling changes the outcome

The loop after the box hit reads real `BlockState` collision shapes by `blockPos2.up(t)`; those reads do not use the synthetic world-border collision provider (1.13 `LocalClientPlayerEntity.java:940–949`; 1.13.1 lines 945–954). At t=1:

- 1.13 samples `(10,66,0)`. That cell is AIR, so `s` stays 66. At t=2 it samples AIR at `(10,67,0)`, checks AIR at the player's head column `(8,66,0)`, and finishes with `u=66-64=2`. Since `0.5 < 2 <= 2.7`, it writes `ticksToNextAutojump=1`.
- 1.13.1 samples `(11,66,0)`. The physical Stone has a full collision shape, so source sets `s=66+1=67`. It then evaluates `s - getShape().minY = 67-64 = 3 > 2.7` and immediately returns from `autoJump` (lines 949–953), before the later clearance read and before the timer write. It therefore does not arm the timer.

This is a real source-predicted difference under the added raw block state: 1.13 arms, while 1.13.1 rejects the step at Jump Boost II. The collision provider's aggregate mask remains unchanged; the difference is which physical state the version-specific aggregate center selects for the later direct height scan. With Jump Boost III (`o=3.45F`), the same `s=67` does not exceed the cap, so this particular early return does not occur; this witness is intentionally the Jump Boost II case.

## Limits

This establishes a source-level counterexample for the fixed geometry and stated block states. It does not establish that a particular keyboard input always reaches the fixed displacement, runtime parity, a general border aggregation rule, or pair-wide coverage. The query assumes the surrounding region is loaded and contains no other colliding entities. No product, wiki, backlog, build, test, game, or decompiler work was performed.

