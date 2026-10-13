# F-BLOCK-CONTACT-TRAVERSE — swept player block contact

- **1.21.1**, mojmap: `net/minecraft/world/entity/Entity.java`; lines 598-727.
- **1.21.3**, mojmap: `net/minecraft/world/entity/LivingEntity.java`; lines 2690-2779.

## Difference

For a movement whose player bounding box traverses a cobweb block's inside shape but ends with no overlap, 1.21.1's post-move scan visits only the final bounding-box cells and does not call the callback for that cobweb. 1.21.3's post-travel scan traverses cells along the old-position-to-current-position movement and can call it.

The ordinary local player reaches 1.21.3's scan because `LocalPlayer.isEffectiveAi()` returns true.

The unchanged cobweb callback then writes a movement multiplier which is consumed by the next `Entity.move`; this is conditional on the path geometry, callback guards and a movement component affected by that multiplier.

## Movement meaning

On both sides the local player tick reaches `Player.aiStep`, `LivingEntity.aiStep`, travel and `Entity.move`. 1.21.1 invokes the inside-block callback from each eligible move and scans the post-move box when the current box's chunks are available. 1.21.3 replaces that per-move callback with a post-travel pass from `oldPosition()` to `position`; the server-or-locally-controlled guard passes for `LocalPlayer` because its `isEffectiveAi()` override returns true. 1.21.3's path checker uses the current entity box, path-traversed block positions and each state's inside-collision shape.

For COBWEB, the unchanged `WebBlock` callback writes `stuckSpeedMultiplier`, which both versions consume on the next move.

This finding is limited to client local-player movement; it makes no claim about server movement equivalence.

Source-proven: with the stated path and shape preconditions, 1.21.1's final-box scan omits an intermediate-only cobweb callback while 1.21.3's path traversal can invoke it, and the callback's multiplier is read by the next move.

It is a source-level prediction that this changes the next client-predicted movement vector when the multiplier affects a nonzero component.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.1--1.21.3/findings/F-BLOCK-CONTACT-TRAVERSE.md).
