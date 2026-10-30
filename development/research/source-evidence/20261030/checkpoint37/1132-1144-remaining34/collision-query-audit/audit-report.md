# Independent source audit: collision-query witnesses, 1.13.2 → 1.14.4

## Verdict

Both explicit full-cube resolver witnesses are supported by the exact endpoint sources, subject to their stated static preconditions. The diagonal axis-order result and the strict resolved-vector squared cutoff are correctly derived. These checks close only these two resolver/query witnesses; they do not close the broader pair inventory, so the full 1.13.2 → 1.14.4 pair remains open.

The input closure report and its adjacent SHA-256 match (`30670ce15cba31f5dc7ced918ae9911dd68ace3e9b5fcb1b7b2230d91a872aa8`). All endpoint Java source hashes in the input's table were independently recomputed and match. The additional `net/minecraft/block/shape/VoxelShapes.java` files used here hash to `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95` (1.13.2) and `51a09c297c7f01c8bad9b66af2a80900e95f33c6e3a250ce599f4817433587d9` (1.14.4). Source roots are the assigned read-only Feather Gen2 build 2 directories.

## Witness A — diagonal axis-order result

**Verdict: confirmed.** For the stated standing box `[0,0.6] × [0.25,2.05] × [0,0.6]`, the cube at `(1,0,1)`, and delta `(0.5,0.01,0.6)`, both requested horizontal axes have positive-volume overlap with the cube only after the other axis moves. The player dimensions and centered X/Z box construction are consistent with the source: 1.13.2 `PlayerEntity.java:344-351,450`, `Entity.java:473,676`; 1.14.4 `PlayerEntity.java:109`, `Entity.java:308-315`.

- 1.13.2 applies Y, then X, then Z in `Entity.java:578-598`. The Y move remains inside the block's vertical span. X can therefore take `0.5` while Z has no overlap; Z is then limited by the `0.4` initial gap. Result: `(0.5,0.01,0.4)`.
- 1.14.4 enters the diagonal path for at least two nonzero components (`Entity.java:692-705`). With `abs(0.5) < abs(0.6)`, `m_79801352` clips Z before X (`Entity.java:708-738`); Z takes `0.6`, then X is limited to `0.4`. Result: `(0.4,0.01,0.6)`.
- Airborne state suppresses the step-up branch: 1.13.2 `Entity.java:601-603`; 1.14.4 `Entity.java:666-681`. Y is positive and unblocked, so the descending-clipped condition is false in either version.
- The full cube is a valid ordinary base-block collision provider: 1.13.2 `Block.java:388-395,1901`; 1.14.4 `Block.java:371-378,811`; state dispatch is 1.13.2 `BlockState.java:209-210` and 1.14.4 `BlockState.java:197-202`. The 1.14.4 stone registration uses the collision-enabled default (`Blocks.java:22`).

The route is statically available through ordinary dry travel: 1.13.2 `LivingEntity.java:1553-1575`; 1.14.4 `LivingEntity.java:1840-1846`. Neither source establishes that this exact delta arises from a specified input trajectory, and the witness does not claim one.

## Witness B — resolved squared-distance cutoff

**Verdict: confirmed with explicit zero residual-cobweb-motion precondition.** At the stated box `[0.3999,0.9999] × [1.1,2.9] × [0.3999,0.9999]`, each wall starts `0.0001` (approximately, in binary floating-point) beyond the relevant positive face. The tiled full cubes cover the player's orthogonal footprint. X clips to approximately `1.0 - 0.9999 = 0.0001`; after that X translation, the Z wall still overlaps the box in X and Z clips to approximately the same gap. The unobstructed Y component remains `0.00001`. The resulting squared length is approximately `0.0001² + 0.00001² + 0.0001² = 2.01e-8`, strictly below `1.0e-7`.

- The diagonal helper is selected in 1.14.4 because X, Y, and Z are nonzero; equal horizontal magnitudes make `abs(dx) < abs(dz)` false, so X is clipped before Z (`Entity.java:692-738`). The corresponding 1.13.2 order is unconditionally Y→X→Z (`Entity.java:581-598`). The `calculateMaxOffset` implementations retain these ~`1e-4` results; their early-zero threshold is only `1.0e-7` (`VoxelShapes.java:194-205` in 1.13.2 and `:213-225` in 1.14.4).
- 1.13.2 applies each nonzero resolved component and calls `setPositionFromShape()` (`Entity.java:586-597,674-680`). 1.14.4 obtains the resolved vector from `m_72373519`, then moves the shape and recomputes position only when its squared length is strictly greater than `1.0E-7` (`Entity.java:463-468`). The calculated vector therefore crosses the stated behavioral gate exactly as claimed.
- Airborne state and unchanged positive Y suppress the 1.14.4 step branch (`Entity.java:666-681`); the 1.13.2 step candidate is also suppressed (`Entity.java:601-603`).

The extra 1.14.4 pre-resolution residual-motion field is initialized to `Vec3d.ZERO` (`Entity.java:134`) and may be set by cobweb collision (`:1964-1966`). To make the witness fully explicit, require `f_74350999 == Vec3d.ZERO` at entry to `move`. It is initialized to zero, but a previous cobweb collision can set it; the source confirms zero initialization and the cobweb setter, not the absence of all prior cobweb history. As with A, the source confirms the ordinary dry SELF call site but not a keyboard-to-velocity history producing the exact test vector.

## Query and exclusion assumptions checked

The 1.13.2 swept block query is constructed at `WorldView.java:154-163`, then enumerates the padded bounds, skips designated boundary triples, checks chunk-loaded positions and translates the collision shape at `:112-151`; its `World` method appends entity collisions (`World.java:1755-1757`). The 1.14.4 resolver concatenates prior candidates with a fresh block-collision scan of the swept box (`Entity.java:692-705`); `WorldView.java:133-185` applies chunk lookup with `load=false`, state filters, translated collision shapes and overlap. The collision chunk status is `FULL_CHUNK` (`World.java:875-877`); the witness must retain the stated loaded-chunk precondition. Entity candidates are absent under the witness's no-intersecting-entities assumption (1.13.2 `EntityView.java:21-35`; 1.14.4 `:53-66`).

**Correction to the input's border wording:** 1.14.4 `Entity.m_72373519` does not offer the world-border shape only at a crossing. At an interior position, the outside-border complement shape is included in the initial stream when the contracted box does not overlap it (`Entity.java:659-665`). The shape is inert for these witnesses because their swept boxes are separated from that complement. The separate `WorldView.getBlockCollisions` scan emits its border shape on the expanded-versus-contracted crossing predicate (`WorldView.java:150-156`). The complement geometry is visible in `WorldBorder.java:299-311` and `:406-417`. This distinction corrects the description but does not change either resolver result.

## Scope conclusion

The two witness verdicts are positive, with the residual-cobweb-motion precondition added to B and the border-stream wording corrected. They establish neither exhaustive collision-provider coverage nor full reachable-movement inventory, and they do not imply source acceptance or runtime parity for the pair. The pair remains open.
