# F-BLOCK-CONTACT-TRAVERSE — swept player block contact

1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 598-727.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, isControlledByLocalInstance 3149-3151; applyEffectsFromBlocks 736-738; applyEffectsFromBlocks 736-771.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2586-2681.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2690-2779.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-210.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, isEffectiveAi 478-480.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 513-535.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java`, boxTraverseBlocks 172-190.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.

Difference: For a movement whose player bounding box traverses a cobweb block's inside shape but ends with no overlap, 1.21.1's post-move scan visits only the final bounding-box cells and does not call the callback for that cobweb. 1.21.3's post-travel scan traverses cells along the old-position-to-current-position movement and can call it. The ordinary local player reaches 1.21.3's scan because `LocalPlayer.isEffectiveAi()` returns true. The unchanged cobweb callback then writes a movement multiplier which is consumed by the next `Entity.move`; this is conditional on the path geometry, callback guards and a movement component affected by that multiplier.

Movement meaning: On both sides the local player tick reaches `Player.aiStep`, `LivingEntity.aiStep`, travel and `Entity.move`. 1.21.1 invokes the inside-block callback from each eligible move and scans the post-move box when the current box's chunks are available. 1.21.3 replaces that per-move callback with a post-travel pass from `oldPosition()` to `position`; the server-or-locally-controlled guard passes for `LocalPlayer` because its `isEffectiveAi()` override returns true. 1.21.3's path checker uses the current entity box, path-traversed block positions and each state's inside-collision shape. For COBWEB, the unchanged `WebBlock` callback writes `stuckSpeedMultiplier`, which both versions consume on the next move. This finding is limited to client local-player movement; it makes no claim about server movement equivalence. Source-proven: with the stated path and shape preconditions, 1.21.1's final-box scan omits an intermediate-only cobweb callback while 1.21.3's path traversal can invoke it, and the callback's multiplier is read by the next move. It is a source-level prediction that this changes the next client-predicted movement vector when the multiplier affects a nonzero component.

## Exact 1.21.2 boundary and vertical cobweb witness

Exact 1.21.2 already uses the post-travel traversal. In Mojmap `BlockGetter.boxTraverseBlocks`, squared displacement below `Mth.square(0.99999F)` uses final-box candidates. The binary32 threshold is `0.99997997283935546875`, and equality takes the swept branch. At most 17 DDA cells reach clipping before extent enumeration. Final-box cells are appended after swept cells with position de-duplication.

For ordinary standing free fall at `x=z=0.5` with default gravity and no collision, 72 gravity and damping updates from zero give vertical velocity `-3.004701526459729`. Starting the next move at feet Y `1.1` places the final head below Y `0`, so both endpoint boxes miss the cobweb at `(0,0,0)` while the swept box crosses it. The 1.21.2 callback stores the multiplier for the next travel move on the following tick in the ordinary local-player path. This witness leaves multi-segment and pose cases and 1.21.4 boundary-sensitive candidate differences outside its scope. [Exact finding](../../source-evidence/20261117/block-a-early-swept-contact/inside-block-path-order.corrected-r1.md), [source comparison](../../source-evidence/20261117/block-a-early-swept-contact/cobweb-comparison.corrected-r1.md), [independent verdict](../../source-evidence/20261117/block-a-early-swept-contact/cobweb-verdict-r1.md).
