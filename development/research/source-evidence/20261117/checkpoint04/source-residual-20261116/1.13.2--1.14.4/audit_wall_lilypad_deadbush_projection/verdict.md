# Independent source audit: lily pad and dead bush wall predicates

**Verdict: ACCEPT**, bounded to the two assigned registry rows and their wall-neighbor predicate. Audited report SHA-256: `a4306764f7dcb7e95d14381f5bff5a6d52697aed78f1237f89b806c9d9041b5a`.

## Identity and coverage

The endpoint ready markers bind the exact Feather source manifests: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. I recomputed the report's 14 cited Java hashes from canonical sources; each matches both the report and its endpoint manifest. The report hash matches the requested immutable identity.

The cited residual crosswalk hash is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its exact `minecraft:dead_bush` and `minecraft:lily_pad` rows name `DeadBushBlock`/`LilyPadBlock`, old `PlantBlock` face ownership, and singleton state cardinality at both endpoints. The cited accepted wall plant/fluid report hash `41c1ec692df824c1aea57c186a4ff5968cc72fd257d359ccfb0cf88cb8ea20ee` has an 11-key projection table containing neither key. The distinct `potted_dead_bush` registry entry is not part of this slice.

## Predicate verification

- **Old 1.13.2:** Both classes extend `PlantBlock`; `PlantBlock.getFaceShape` returns `FaceShape.UNDEFINED`, and `BlockState.getFaceShape` delegates to that override. `WallBlock.shouldConnectTo`'s generic branch requires `FaceShape.SOLID`; the alternative middle-pole branch requires the corresponding shape and a `FenceGateBlock`. Neither assigned block qualifies, so both horizontal wall bits are false.
- **Target 1.14.4:** `WallBlock.m_71853799` combines the collision-face generic branch with a `WALLS`-tag or direction-valid fence-gate alternative. The audited resource payloads (and already accepted resource-membership audit) exclude both keys from `WALLS`; neither is a gate or a literal attachment exception. Thus the generic collision-face result decides each bit.
- `dead_bush` is registered with `.noCollision()`. This sets `hasCollision=false`; inherited `Block.getCollisionShape` consequently returns an empty shape, regardless of `DeadBushBlock.getShape`. The collision face cannot be full, so the target bit is false.
- `lily_pad` is collision-enabled, but its fixed shape is inset to 1..15 horizontally and only 1.5/16 high. Each horizontal collision-face slice is short of a full unit face, so `Block.isFaceSolid` is false in every horizontal direction.
- Both classes inherit the empty base state definition at both endpoints, consistent with the crosswalk's singleton cardinalities.

The projection is therefore **false → false** for each key in all four horizontal directions. The assigned wall-predicate facts are supported for the registered singleton states.

## Limits

This acceptance covers only the assigned wall-neighbor predicate rows. It does not make a movement-trajectory claim and does not freeze the release pair. No correction addendum is needed.
