# Independent source verdict: flower wall-neighbor projection

Verdict: ACCEPT, bounded to the report's 14 registered flower/tall-flower keys and their wall-neighbor predicate.

Reviewed report SHA-256: `661a73a9c77aa5ed6d498b8c3a834b2d0a5376bcc1da52ac9c0e1368c68d0f9e`.

## Identity and evidence checks

The 1.13.2 and 1.14.4 ready markers identify Ornithe Feather Gen2 build 2 and bind source manifests `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`, respectively. All 14 cited Java-file hashes in the report match both canonical files and their source-manifest entries. The report itself matches the reviewed SHA above.

The crosswalk selection contains ten singleton `FlowerBlock` keys and four `TallFlowerBlock` keys with `HALF={LOWER,UPPER}`, for 18 states total. This audit confirms that the stated classes and state domains agree with the referenced source declarations and registrations.

## Predicate verification

In 1.13.2, `PlantBlock.getFaceShape` returns `FaceShape.UNDEFINED`; `FlowerBlock` inherits it, while `TallFlowerBlock` inherits through `BushBlock` from `PlantBlock`. `WallBlock.shouldConnectTo` accepts the generic branch only for `FaceShape.SOLID`, and the separate middle-pole branch is limited to fence gates. These flowers therefore yield false neighbor bits.

In 1.14.4, `WallBlock` queries the face-solid predicate and separately checks wall-tag and fence-gate routes. `Block.isFaceSolid` uses the collision shape, and base `Block.getCollisionShape` returns empty when `hasCollision` is false. The 14 registrations in `Blocks.java` use `.noCollision()`; none is a fence gate, and the reused audited `WALLS` payload excludes these keys. The target bits are therefore also false for all 18 states and four horizontal directions.

## Limits

The no-difference result covers only these 72 key/state/direction cases. It makes no claim about other block providers, movement trajectories, lifecycle or migration paths, or pair completion. The crosswalk r2 correction records that its earlier r1 TSV was overwritten, preventing a full byte-diff of unchanged r1 rows; this focused audit relies on the corrected TSV selection and does not renew that global crosswalk audit.
