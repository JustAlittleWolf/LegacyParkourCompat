# Independent source audit: pressure-plate wall-face projection

Verdict: **ACCEPT, bounded to this nine-key family and the vanilla 1.14.4 wall-tag resource.**

Bound report SHA-256: `0bc397037efbe0f1059148d4bf12709078a96a1fa50e3f748dadd8f578d63729`.

## Identity and registration

The ready Ornithe Feather source identities are 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. The ready markers identify these exact source manifests. I independently checked the cited AbstractPressurePlateBlock, PressurePlateBlock, WeightedPressurePlateBlock, WallBlock, and Properties file bytes against both source manifests; all match. The 1.13.2 Block.java registry and 1.14.4 Blocks.java registrations also match their cited hashes.

Both registrations bind stone and the six wood keys (`stone`, `oak`, `spruce`, `birch`, `jungle`, `acacia`, `dark_oak` pressure plates) to PressurePlateBlock; light and heavy weighted keys bind to WeightedPressurePlateBlock. All nine set `noCollision()` at both endpoints. The generic POWERED domain is Boolean false/true and weighted POWER is integer 0–15 at both endpoints. These domains match the state builders and Properties declarations; no additional state property is added by either class.

## Wall predicate result

Old 1.13.2 AbstractPressurePlateBlock.getFaceShape returns FaceShape.UNDEFINED unconditionally. Old WallBlock.shouldConnectTo accepts its generic face route only for SOLID (with the separate middle-pole fence-gate case), so these pressure plates do not set a horizontal arm in any legal state/direction.

At 1.14.4, Block.Properties.noCollision sets `hasCollision=false`; Block.getCollisionShape then returns VoxelShapes.empty rather than the outline shape. BlockState.m_87223014 delegates to Block.isFaceSolid when uncached, and Block.isFaceSolid applies isFaceFull to that collision shape. Thus the generic full-face boolean is false for every one of the nine keys, all legal states, and all horizontal directions. WallBlock's additional route is membership in `BlockTags.WALLS` or a direction-matching FenceGateBlock. None of these registered classes is a FenceGateBlock. The pinned vanilla 1.14.4 `data/minecraft/tags/blocks/walls.json` payload lists 14 wall keys and contains none of the nine pressure-plate keys (payload SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`). Its extraction record SHA-256 is `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea`; the independent raw-resource audit is `wall_resource_membership_audit/verdict-r2.md`, SHA-256 `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.

The outline shape changes only between the same pressed/unpressed inset boxes at these endpoints; it does not affect this result because target wall projection consumes effective collision shape. The old AbstractPressurePlateBlock collision box used for entity activation is not the wall FaceShape result and does not change the conclusion.

## Limits

Accepted claim: no generic horizontal wall arm for these exact nine vanilla keys across these endpoints, over their legal listed states. This does not cover modded tag edits, other block keys/providers, unrelated neighbor predicates, trajectory, or pair completion.

No corrections or audit addenda were required.
