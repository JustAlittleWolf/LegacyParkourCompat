

- **1.19.4**, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`; lines 585-599.
- **1.20.1**, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/Entity.java`; lines 629-643.

## Difference

1.19.4 derives the movement-factor block position from the entity's center X/Z and bounding-box minimum Y minus 0.5000001. 1.20.1 updates mainSupportingBlockPos after grounded collision using a thin AABB below the entity, then getBlockPosBelowThatAffectsMyMovement uses getOnPos(0.500001F), which takes the stored supporting block's X/Z when present.

If no support position is stored, 1.20.1 instead floors position.x, position.y - 0.500001F, and position.z; 1.19.4 still floors position.x, boundingBox.minY - 0.5000001, and position.z.

The changed spectator tick path in S1-06 calls setOnGround(false), which clears mainSupportingBlockPos before no-physics movement.

Thus the block position feeding jump and speed factor reads can differ due to a selected support block, the empty-support fallback offset, or a state-clear path; the factor formulas themselves retain the same current-block then supporting-block fallback order.

## Movement meaning

Entity.move performs the support update after collision on a downward contact.

In 1.20.1, Player#tick also reaches setOnGround(false) on the spectator branch; checkSupportingBlock(false, null) clears a previously recorded support position, and the following empty-support lookup uses 1.20.1's position-based fallback.

On later movement math, getBlockJumpFactor contributes to the player's ground-jump power, while getBlockSpeedFactor contributes to speed and post-move damping.

CollisionGetter#findSupportingBlock enumerates blocks through BlockCollisions under the support AABB.

S4-02 must finish the shape/query path and S5-01 must inventory block registrations and factors before naming concrete blocks or terrain outcomes.

The source confirms that 1.20.1 can provide a different block-position input to player movement-factor reads when its selected supporting block differs from 1.19.4's center-column position.

This finding does not assert a particular block layout, factor value, or observed trajectory; those concrete consequences depend on shape and block-property closure.

[Archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.19.4--1.20.1/findings/F-02-supporting-block-factor-position.md).
