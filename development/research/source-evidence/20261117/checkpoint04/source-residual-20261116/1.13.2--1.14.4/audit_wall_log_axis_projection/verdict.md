# Independent source audit: Axis/log generic wall-face projection

Verdict: ACCEPT, bounded to the report's selected wall-neighbor predicate rows.

Reviewed packet: `build/source-residual-20261116/1.13.2--1.14.4/wall_log_axis_projection/report.md` SHA-256 `c23f6e4767fddc73a41727952c7be7a56ad374a1414ab464c3c579c7f20176b0`.

## Audit checks

- Ready markers bind 1.13.2 Feather Gen2 build 2 to source manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` and 1.14.4 Feather Gen2 build 2 to `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.
- Recomputed every cited Java source SHA-256 and matched it to that endpoint's source manifest. The supplied residual TSV hash is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; filtering its exact rows by both concrete classes (`AxisBlock` or `AbstractLogBlock`) and old `Block` face-shape owner yields 27 rows, matching the report's key list.
- In both endpoints, `AxisBlock` declares the `AXIS` property and adds it to the state definition; `Properties.AXIS` is an enum property of `Direction.Axis`, whose legal values are X, Y, Z. `AbstractLogBlock` extends `AxisBlock` and its only own override is map-color selection. Hence these rows cover 27 × 3 = 81 registered key/state combinations.
- In 1.13.2, `Block.getFaceShape` defaults to `SOLID`, and `Block.getCollisionShape` returns the state shape when `hasCollision` is true. `AxisBlock` and `AbstractLogBlock` do not override face-shape or collision-shape behavior. Old `WallBlock.shouldConnectTo` accepts SOLID unless the block is in the attachment exception set; neither class is such an exception.
- In 1.14.4, the selected registrations construct the corresponding classes without `.noCollision()`. `Block.Properties.hasCollision` defaults true; inherited `Block.getShape` is the full cube; inherited `getCollisionShape` follows the enabled collision gate. Target `Block.isFaceSolid` therefore reports full horizontal faces for these states, and target `WallBlock` passes that result into its generic connection arm. The classes are not leaves and are absent from the attachment exception set and WALLS tag.
- The resulting wall neighbor bit is true for every selected key, all three axes, and four horizontal directions at both endpoints (324 checked cases). The bounded no-difference verdict follows from the cited source routes and registrations.

## Scope and limitation

This accepts only the 27 AxisBlock/AbstractLogBlock rows from the supplied residual crosswalk. It establishes no movement trajectory, no other provider-family parity, no external-state reachability, no intermediate patch boundary, and no pair freeze. The source packet preserves the crosswalk r2 audit limitation: the prior r1 TSV had been overwritten, so unchanged rows could not all be byte-diffed against r1; this audit does not claim or repeat a global crosswalk audit.
