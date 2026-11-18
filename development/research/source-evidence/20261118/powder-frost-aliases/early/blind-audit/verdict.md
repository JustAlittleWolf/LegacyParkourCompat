# Final author review: powder-snow frost gate

## Identity and disposition

The supplied `source/comparison.md` SHA-256 (`ae8388c786d8f78199566eab0bb5f9c3e2858ee7cbfc4db4a110674a6a93f9ef`), `source/findings/1.19.4--1.20/powder-snow-frost-gate.md` SHA-256 (`08134c5b1c8efc185f598d01a46c71788b376c5b542eebd38fce31570a5df795`), and frozen blind-audit SHA-256 (`60f9d845fcf259eb10fdeacec6e8a1f57b7868358ba41a95a495481f4ec35164`) all match the requested bytes. The review leaves those files and the shared decompiled sources untouched.

The author's central counterclaim is confirmed: the 1.20 frost non-air gate is not equivalent to 1.19.4 merely because both use a 0.2 Y offset. The main-support branch changes which X/Z block is sampled. The 1.20.2 source continues the same support-aware logic. The finding is directionally correct but needs an additional no-support case and source-dependency corrections before acceptance.

## Gate behavior

In 1.19.4, `Entity.getBlockStateOnLegacy()` calls `getOnPosLegacy()`, which calls the shared position helper with `0.2F`. That helper first constructs `(floor(x), floor(y - 0.2F), floor(z))`. If that block is air, it reads one block below and returns that lower position when its state is a fence, wall, or fence gate; otherwise it returns the initial position. `LivingEntity.tryAddFrost()` tests this selected state's `isAir()` result.

In 1.20, and identically in 1.20.2, `getBlockStateOnLegacy()` still calls `getOnPosLegacy()` with `0.2F`, but `getOnPos(float)` first branches on `mainSupportingBlockPos`:

- If support is present, the 0.2 offset selects the support block's X/Z and normally replaces Y with `floor(position.y - 0.2F)`. If the support block itself is a fence, wall, or fence gate, the method returns the support position directly. This preserves the special support-block treatment while selecting a support-derived X/Z cell.
- If support is absent, it returns `(floor(x), floor(y - 0.2F), floor(z))` directly. Unlike the 1.19.4 helper, this branch does not perform the old “if air, inspect one block below for fence/wall/gate” fallback.

Therefore there are two source-defined gate differences: the support-present sample can select a different X/Z block; and the support-absent sample omits the legacy one-below fence/wall/gate fallback. The second difference is reachable during local player movement after leaving support: `Entity.move()` calls `setOnGroundWithKnownMovement(verticalCollisionBelow, clippedMovement)`, and the false-ground branch clears `mainSupportingBlockPos`. While airborne with positive frozen ticks, a position for which the `y - 0.2F` cell is air and the cell below is a fence, wall, or gate passes the old non-air test but fails the 1.20/1.20.2 no-support test. The finding should state this condition explicitly; it is part of the same `tryAddFrost()` gate operation.

A support-present difference is also a precise reachable conditional: after local movement reports `verticalCollisionBelow`, `checkSupportingBlock` queries the block-collision set under the entity AABB. A block supporting the edge of that AABB can have different X/Z from `floor(player.x/z)`. `getOnPos(float)` then samples the support-derived position. This establishes a reachable support input without implying every edge position differs.

## Support writers and order

The new field starts as `Optional.empty()`. In both 1.20 and 1.20.2, `setOnGround(boolean)` and `setOnGroundWithKnownMovement(boolean, Vec3)` call `checkSupportingBlock`. On grounded movement, `checkSupportingBlock` queries a thin AABB below the post-move bounding box through `Level.findSupportingBlock`; if needed it retries a box translated back by the movement vector. The resulting optional is assigned to `mainSupportingBlockPos`. When not grounded, it clears the field. In `Entity.move()`, the movement-collision result is passed to `setOnGroundWithKnownMovement` after collision resolution and before the later LivingEntity frost update consumes the block state. The ordered writers and reader are present in 1.20.2 as well.

The support lookup itself is `CollisionGetter.findSupportingBlock`: it iterates block collisions, selects the nearest candidate block position to the entity position (with a deterministic tie break), and returns that position. This is the dependency that explains how the new support input can differ from the former center-floor lookup.

## Shared operations and required corrections

The UUID, name, transient `ADDITION` operation, non-air / positive-frozen-count / non-null-attribute gates, and `-0.05F * getPercentFrozen()` formula remain the same across the nine named anchors. The post-travel `travel(...)`, frozen-counter update, `removeFrost()`, then `tryAddFrost()` ordering also remains the same. The method holding that sequence is `LivingEntity.aiStep()`, not `LivingEntity.tick()`; correct the comparison's method name. The counter update is separately server-gated, while `tryAddFrost()` consumes the available frozen ticks on the client.

The comparison's speed-read explanation names `LivingEntity.getSpeed()` as the attribute getter. That is inaccurate: `LivingEntity.getSpeed()` returns its cached `this.speed` field. `Player` overrides `getSpeed()` in every named anchor to return `(float)getAttributeValue(Attributes.MOVEMENT_SPEED)`, and `LocalPlayer` inherits through `AbstractClientPlayer extends Player`. Thus the LocalPlayer `updateAutoJump()` call dynamically reads the movement-speed attribute directly during `move()`, before the post-travel frost update; a later call can read the modifier after it is installed. Correct the declaring type and include `Player.java` and `AbstractClientPlayer.java` source pins for this dispatch claim. The existing comparison pin table covers only `LivingEntity.java`, `Entity.java`, and `LocalPlayer.java`.

For the support-writer/selection dependency, add `CollisionGetter.java` pins for 1.20 and 1.20.2 (and its `findSupportingBlock` source anchor). The current finding's `Entity.java` and `LivingEntity.java` anchors support the core difference, but they do not alone establish the block-collision selection behind the reachability explanation.

The frozen blind-audit file also describes the LocalPlayer read as a cached-speed read and treats `Player.aiStep()`'s later `setSpeed(...)` cache write as the local receipt. Its call-order conclusion remains correct, but those speed claims need a separately recorded correction before relying on them. The freeze was preserved as instructed.

## Source anchors

- 1.19.4 `Entity.java`: `getOnPosLegacy` / private `getOnPos` lines 720–742; `getBlockStateOnLegacy` lines 1140–1142. `LivingEntity.java`: `tryAddFrost` line 510.
- 1.20 `Entity.java`: `setOnGroundWithKnownMovement` lines 558–560; `checkSupportingBlock` lines 567–587; `Entity.move` call lines 640–643; `getOnPosLegacy` / support-aware `getOnPos` lines 786–815. `LivingEntity.java`: `tryAddFrost` line 525. `CollisionGetter.java`: `findSupportingBlock` lines 95–109.
- 1.20.2 `Entity.java`: `setOnGroundWithKnownMovement` lines 560–562; `checkSupportingBlock` lines 569–589; `Entity.move` call lines 642–645; `getOnPosLegacy` / support-aware `getOnPos` lines 788–817. `LivingEntity.java`: `tryAddFrost` line 526. `CollisionGetter.java`: `findSupportingBlock` begins at line 105.
- The named older anchors' common frost operation and counter formula remain as recorded in the frozen source audit; this review does not infer uninspected intermediate releases.

Verdict: accept the core 1.19.4–1.20 `tryAddFrost()` gate discontinuity, bound to the named sources. Amend the finding to include the no-support fence/wall/gate fallback difference and correct its dependency evidence. Correct the comparison's `aiStep` and `Player.getSpeed()` attribution; add the missing pinned source files. No exact patch boundary beyond 1.19.4–1.20 is established.