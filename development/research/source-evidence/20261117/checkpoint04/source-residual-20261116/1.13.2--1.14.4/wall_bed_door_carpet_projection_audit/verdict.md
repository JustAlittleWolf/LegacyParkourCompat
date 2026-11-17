# Independent source audit: wall bed/door/carpet face projection (1.13.2 → 1.14.4)

## Verdict

**ACCEPT WITH CORRECTION**, bound to `wall_bed_door_carpet_projection/report.md` SHA-256 `7e1fd65ad5d301a46e9df601d41e010117a4acb54857a4f865631cd616bd34ee` and the exact Feather Gen2 build 2 source manifests below. The assigned face-projection results, door direction table, key/state domains, and bounded conclusion are supported by source. The report needs one correction to its description of the old exception chain; this does not change the assigned-family outcomes.

## Identity and hash checks

The 1.13.2 and 1.14.4 ready markers are `ready`, identify the requested versions and `ornithe-feather`, and name Feather Gen2 build 2 artifacts. Their source manifest SHA-256 values match the assignment (`2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`); recomputed hashes of the on-disk manifest files match. I recomputed every source-file SHA-256 listed in the report (BedBlock, CarpetBlock, DoorBlock, Block, WallBlock on both sides; target Blocks and VoxelShape); all match both the report and manifest entries.

The report's own SHA-256 matches the assigned identity. The bed provider report exists at the stated path and its SHA-256 matches `509950547603b487a25cbc83435367bc2a2cde743a9442909e8d1d1dde5f17d11a`. The cited `door_shape_provider/report.md` is absent from the current workspace; the directory contains `coverage.md` and the audit verdict, not a file whose bytes can be checked against the report's cited `18ba45c9b4c44e258cd044bab46cb8aab3a43383649c7575162a20084aea0df5`. The door geometry/state claim is nevertheless directly source-verifiable and bounded reuse does not carry over a wall-projection conclusion. Treat that historical report-hash reference as unverified in this workspace.

## Claim audit

- The listed 16 bed keys and 16 carpet keys resolve to their respective classes in the old registration code and target `Blocks` registrations; the seven listed door keys likewise resolve to `DoorBlock`. State definitions establish beds' four facings × two parts × two occupied values (16/key), doors' four facings × two open × two hinges × two powered × two halves (64/key), and carpets' single default state. `HALF`, `POWERED`, and bed `OCCUPIED` are not read by the cited provider geometry branches.
- In 1.13.2, bed and door `getFaceShape` return `UNDEFINED` for every queried face. Carpet returns `UNDEFINED` for horizontal faces. None qualifies for the `MIDDLE_POLE`/`MIDDLE_POLE_THICK` special arm in the old wall predicate. Thus the old wall bits are false for these keys on N/E/S/W.
- In 1.14.4, the wall predicate's generic arm consumes `Block.isFaceSolid`; that projects the collision-shape exterior slice and tests it as full. The target bed top reaches only y=9/16 and its corner feet cover only parts of horizontal side slices, so no horizontal side is full. Carpet's 1/16-high collision slab likewise gives no full horizontal side. These results are invariant over the listed state domains.
- Door collision uses one of the four 3/16-thick full-height slabs. Reading the returned shape's actual occupied coordinate and the corresponding exterior slice confirms the report's outward-face labels (for example, `EAST_SHAPE` occupies x=0..3/16 and exposes WEST; `NORTH_SHAPE` occupies z=13/16..1 and exposes SOUTH). The `FACING × OPEN × HINGE` table is correct for all branches: for EAST, closed→WEST, open-left→NORTH, open-right→SOUTH; analogous branch inspection confirms all other rows. `HALF` and `POWERED` do not affect this selection. A true queried neighbor face contributes to the opposite named wall bit, as both wall-state construction and neighbor update code query the neighbor using the opposite direction.
- No `noCollision()` override/property is present for these registrations; target collision dispatch uses the collision-enabled state shape. The report appropriately limits its conclusion to wall connection inputs and does not claim player trajectory parity.

## Required correction

In the old-exception paragraph, `WallBlock.isExceptionForConnection` is described as only `Block.isExceptionForAttachment` (whose chain includes `isExceptionForPlacement` and piston, sticky piston, and piston head) plus that chain. The actual 1.13.2 method also adds wall-specific exceptions: barrier, melon block, pumpkin, carved pumpkin, lit pumpkin, frosted ice, and TNT. Amend the text to name that full method chain. These extra exceptions do not include beds, doors, or carpets, and their `UNDEFINED` old horizontal face result already makes the assigned old bits false, so the projection verdict remains unchanged.

This is a bounded source audit only. It does not establish complete pair coverage, a patch boundary, or runtime parity.

