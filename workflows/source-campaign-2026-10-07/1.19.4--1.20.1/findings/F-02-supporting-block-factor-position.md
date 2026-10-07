
# F-02: Movement factors use the tracked supporting block in 1.20.1

- Older version A: Minecraft 1.19.4
- Newer version B: Minecraft 1.20.1
- Mechanic / coverage slice IDs: ground support and block jump/speed factor lookup; S4-01, S1-04, S3-01
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed

## Paired evidence

- A artifact reference: ../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/net/minecraft/world/entity/Entity.java; move lines 585-599 sets onGround from verticalCollision and requested Y; getBlockPosBelowThatAffectsMyMovement / getBlockJumpFactor / getBlockSpeedFactor lines 744-762; SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b.
- B artifact reference: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/entity/Entity.java; move lines 629-643 calls setOnGroundWithKnownMovement; checkSupportingBlock lines 567-585; getOnPos/getBlockPosBelowThatAffectsMyMovement lines 786-815; factor readers lines 817-831; SHA-256 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759.
- B selection evidence: ../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java#findSupportingBlock, lines 95-110; SHA-256 8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130. The scan selects the candidate block position with the smallest squared distance from the entity position, with a deterministic BlockPos tie-break.
- Player movement consumers: Entity#getBlockJumpFactor is read by LivingEntity#getJumpPower; Entity#getBlockSpeedFactor is read by movement code. The paired Entity and LivingEntity hashes are recorded in the run artifact manifest.

## Source-level difference

A derives the movement-factor block position from the entity's center X/Z and bounding-box minimum Y minus 0.5000001. B updates mainSupportingBlockPos after grounded collision using a thin AABB below the entity, then getBlockPosBelowThatAffectsMyMovement uses getOnPos(0.500001F), which takes the stored supporting block's X/Z when present. Thus the block position feeding both jump factor and speed factor can differ around partial or multi-block support; the factor formulas themselves have the same default/current-block then supporting-block fallback order.

## Reachability and dependencies

Entity.move performs the support update after collision on a downward contact. On later movement math, getBlockJumpFactor contributes to the player's ground-jump power, while getBlockSpeedFactor contributes to speed and post-move damping. CollisionGetter#findSupportingBlock enumerates blocks through BlockCollisions under the support AABB. S4-02 must finish the shape/query path and S5-01 must inventory block registrations and factors before naming concrete blocks or terrain outcomes.

## Consequence and uncertainty

The source confirms that B can provide a different block-position input to player movement-factor reads when its selected supporting block differs from A's center-column position. This finding does not assert a particular block layout, factor value, or observed trajectory; those concrete consequences depend on shape and block-property closure.

## Handoff

Preserve version-specific support-position selection before block jump/speed factor reads. First changed release inside this interval and concrete affected block arrangements remain unresolved.
