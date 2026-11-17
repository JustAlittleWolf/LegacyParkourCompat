# Independent wall face-shape census audit

## Input and verdict

Audited `build/source-residual-20261116/1.13.2--1.14.4/wall_remaining_face_shape_census/report.md`, SHA-256 `f1a12c7564f14c91c652bea6603e8ada8350b98c2892df05c591c0161d4e041d`.

**ACCEPT for the bounded census and Beacon result.** The two exact Feather Gen2 build 2 source-manifest identities match the assigned releases: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`. All seven source-file digests cited for the Beacon comparison match local source bytes and their manifest entries.

## Beacon source trace

- Both registration sites bind `minecraft:beacon` to `BeaconBlock` (`1.13.2 Block.java:1239`; `1.14.4 Blocks.java:795`). `BeaconBlock` declares no block-state properties and does not override the inherited shape provider.
- Old `WallBlock.shouldConnectTo` accepts a generic arm only when `!isExceptionForConnection(block) && shape == SOLID` (`WallBlock.java:51–65`). `isExceptionForConnection` delegates through `Block.isExceptionForAttachment` to `isExceptionForPlacement`; the placement exception includes `Blocks.BEACON` (`Block.java:241–256`). The old horizontal result is therefore false even though the inherited face shape is `SOLID`.
- Target `WallBlock` uses `!isExceptionForAttachment(block) && faceSolid` for the generic arm (`WallBlock.java:48–52`). Target `Block.isExceptionForAttachment` does not include Beacon (`Block.java:260–267`). `Block.isFaceSolid` checks the `LEAVES` tag and the full directional projection of the collision shape (`Block.java:402–408`). Beacon is absent from the independently verified target LEAVES values; the exact payload SHA is `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991`, bound by extraction record SHA `f26f84c7221f667cd84ff4e048d06b0fd3c3aec52779f3fbd0a8f4e2afe64fea` and independent raw-resource verdict SHA `6402c6af692b3f198c853d3558b91d4de7c215db793e8847e66e45f08f4d8898`.
- Target `Block.Properties.hasCollision` defaults to true, `Block.getCollisionShape` delegates to the state shape when collision is enabled, and the default `Block.getShape` is a full cube (`Block.java:811, 853–854, 377, 372`). The Beacon registration does not call `noCollision`; `BeaconBlock` does not override the shape. The resulting target projection is full on all four horizontal faces.

This confirms the bounded source result: Beacon wall bits change false → true in NORTH, EAST, SOUTH, and WEST for its sole registered state. Wall placement/update paths consume these bits and select wall shape. This is a source predicate/shape result, not a measured player trajectory.

## Census and scope checks

A direct-method scan of top-level old `net/minecraft/block/*.java` files finds exactly 65 files declaring `getFaceShape`; excluding the separate `block/shape/VoxelShape.java` utility matches the report's stated count. The report correctly labels this a declaration census rather than a registered-key census. Its long residual list is explicitly a lower bound, with unresolved aliases, inherited subclasses, state domains, resource gates, and full registration crosswalk left open. I verified that it does not use those unknowns as parity/no-difference claims and does not assert pair freeze.

The referenced generic-neighbor report identity matches its file (SHA `1dc175827e5b8c7cbaecb20fc13dc2f55a8cf3450be8c76106ff855d56efcdbc`), as does its independent verdict (`e4a35e33d288d1ae9afa1818764ee5b7bd53c223bf30ddaf32c2bbd8c7292bcc`). The later raw-resource extraction and r2 audit are the appropriate membership evidence for this report; the older resource-availability verdict predates that extraction and is not reused as a current blocker.

The unknown rows remain open as stated in the audited report. This acceptance covers the source census count, bounded Beacon wall-bit comparison, and explicit limits only; it does not close the full wall/provider crosswalk or either pair.
