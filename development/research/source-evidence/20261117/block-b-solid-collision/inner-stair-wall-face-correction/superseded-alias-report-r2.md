# Inner stair / Wall face-selector alias audit (r2)

This revision corrects the prior per-facing table. The earlier table misread `SHAPE_INDICES` bit masks and omitted the stair-facing face from some newer results. The corrected results below were recomputed from exact source indices, direction IDs, mask coordinates, and face-slice rules.

## Bounded verdict

For the stationary, dry, naturally reachable classic `StairsBlock` `INNER_LEFT`/`INNER_RIGHT` states queried by an adjacent `WallBlock`, the 1.13 selector and the 1.14 selector differ in which *second* horizontal face is solid. Both selectors include the stair's `FACING` face for all tested inner states and both `HALF` values.

- 1.13.2 Feather `StairsBlock.java:108-129`: `INNER_LEFT` reports `SOLID` for `FACING` and `FACING.clockwiseY()`; `INNER_RIGHT` reports `SOLID` for `FACING` and `FACING.counterClockwiseY()`. The horizontal branch does not read `HALF` or `WATERLOGGED`.
- 1.13.2 Feather `WallBlock.java:51-55, 81-84, 105-116`: wall placement and neighbor update query the neighboring state on the opposite horizontal face. A `SOLID` result is eligible for connection subject to the wall exception/pole gate.
- 1.14 Feather `StairsBlock.java:93-105, 172-225` and 1.14.4 Feather `:93-105, 172-232` have no stair-specific `FaceShape` override. `WallBlock.java:48-51, 67-70, 91-103` asks `Block.isFaceSolid`; `Block.java:402-409` checks whether the collision-shape boundary slice is a full face. This uses the classic stair collision shape.
- 1.14.1/1.14.2/1.14.3 Yarn `StairsBlock.java:96-108, 176-230` likewise has no stair-specific selector; their wall call sites use `Block.isSolidFullSquare` and the corresponding `Block` helper checks a full collision face (`Block.java` around lines 396-405). These exact source roots are present, but their mapping/source provenance is unavailable as detailed below.
- 1.14.4 Mojmap independently confirms the newer route: `WallBlock.java:60-82` asks `BlockState.isFaceSturdy` on the opposite direction; `Block.java:403-409` uses the full collision-face test. `StairBlock.java` has no face-selector override.

For naturally reachable inner shapes, placement/state recomputation checks an orthogonal same-half neighboring stair and a same-facing obstruction predicate. See 1.13.2 Feather `StairsBlock.java:224-231, 245-280`; 1.14 Feather `:172-229`; 1.14.1–1.14.3 Yarn `:176-230`; and 1.14.4 Feather `:172-232`. The `isStair` gate is class-based, so using the same registered block identity on both stair positions satisfies it. Both halves and `WATERLOGGED=false` are legal.

## Corrected selector results

For 1.14, `Direction` horizontal IDs are SOUTH=0, WEST=1, NORTH=2, EAST=3 (`Direction.java:16-22`); the shape ordinal order is STRAIGHT=0, INNER_LEFT=1, INNER_RIGHT=2 (`StairShape.java`); `StairsBlock.getShapeIndex` is `shape.ordinal()*4 + facing.getIdHorizontal()` (`StairsBlock.java:103-105`). The 1.14.4 `SHAPE_INDICES` values (`StairsBlock.java:47-53`) select four quarter-boxes with bit order NW=1, NE=2, SW=4, SE=8. `Block.isFaceFull` slices the collision shape at the queried boundary and requires that slice to be a full block (`Block.java:406-412`). The selected quadrant masks give the same horizontal full-face results for top and bottom stairs: the base fills one vertical half and the mask determines the other half's boundary coverage.

| Stair shape | `FACING` | 1.13 `FaceShape.SOLID` faces | 1.14 full collision-face results |
|---|---|---|---|
| INNER_LEFT | NORTH | NORTH, EAST | NORTH, WEST |
| INNER_LEFT | EAST | EAST, SOUTH | EAST, NORTH |
| INNER_LEFT | SOUTH | SOUTH, WEST | SOUTH, EAST |
| INNER_LEFT | WEST | WEST, NORTH | WEST, SOUTH |
| INNER_RIGHT | NORTH | NORTH, WEST | NORTH, EAST |
| INNER_RIGHT | EAST | EAST, NORTH | EAST, SOUTH |
| INNER_RIGHT | SOUTH | SOUTH, EAST | SOUTH, WEST |
| INNER_RIGHT | WEST | WEST, SOUTH | WEST, NORTH |

The 1.14 column includes `FACING` in every row. It derives from exact shape-mask arithmetic, not from an assumed rotation mapping. For the wall-side call argument, use the direction from the stair toward the wall; the wall's queried neighbor side is its opposite.

### Coordinate-level legal witness, both endpoints

Place a stair at `(0,0,0)` with `FACING=EAST`, `WATERLOGGED=false`, and either `HALF=BOTTOM` or `HALF=TOP`; put the adjacent `WallBlock` at `(1,0,0)`. For `INNER_LEFT`, place a same-identity same-half stair facing NORTH at `(-1,0,0)` and leave `(0,0,-1)` non-stair. For `INNER_RIGHT`, use SOUTH at `(-1,0,0)` and leave `(0,0,1)` non-stair. The east/west rear stair has orthogonal facing and matches the candidate half; the clear side position satisfies the same-facing obstruction predicate. Thus each `INNER_*` state is placement-reachable at either half without waterlogging.

The wall passes `Direction.EAST` to the stair selector. At 1.13, `INNER_LEFT` returns EAST+SOUTH and `INNER_RIGHT` returns EAST+NORTH. At 1.14.4, masks 11 (`NW+NE+SE`) for inner-left and 14 (`NE+SW+SE`) for inner-right both fill the east boundary face; inner-left also fills north, inner-right also fills south. The base half plus the selected east-side quadrants make the EAST face full for both top and bottom stairs. The same index and direction-ID source exists on the 1.14 endpoint; this coordinate witness checks the corrected endpoint interpretation independently.

## Exact-version roots, mapping identities, and recomputed hashes

Canonical source roots consumed: Feather for 1.13, 1.13.1, 1.13.2, 1.14, and 1.14.4; Yarn for 1.14.1–1.14.3. Source hashes were recomputed directly and matched each available `.sources.sha256` entry. Hashes are kept within their mapping namespace; matching text hashes across Feather releases are evidence of identical consumed files, not a claim that mapping namespaces are interchangeable.

| Exact source root | `StairsBlock.java` SHA-256 | `WallBlock.java` SHA-256 | `.sources.sha256` SHA-256 | `.provenance.json` SHA-256 |
|---|---|---|---|---|
| 1.13 Feather | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` |
| 1.13.1 Feather | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` | `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` |
| 1.13.2 Feather | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| 1.14 Feather | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` | `9e120e49c648ffaa8c120b262d783bf9dd457cd7ad1a4d9b037ddb2679c6130b` | `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98` | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` |
| 1.14.1 Yarn | `370dbbe6502e888c952c0104ce9b4cd49ee8a0c9d0149ca4394c695da48cbe3d` | `f26af6b0b8efdcbd1b1ecdce52ca87bb0ded2f9268dec41f5d060883ae8f3245` | unavailable | unavailable |
| 1.14.2 Yarn | `d69a9ebbda81ca8dcea9bfdf6a249343f5da60cc1e36003619c89a5e506d5838` | `6a383c7085498a257ce1652923f2b5f5ad06a6eba34fcd5ef3d98590047386f9` | unavailable | unavailable |
| 1.14.3 Yarn | `00b8d1a9a13b4441e10f9ad1f71b0d0de848a3e4f70e6841dca2a76272bc284d` | `e75639b10b71692d786b5f962b5e53fe1841b270898058f9825abb6142c53390` | unavailable | unavailable |
| 1.14.4 Feather | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` | `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc` | `7167b135e0222fcf58ff5f36a36fbb604d5068f63a59165c8c4d18ce7d9b21c9` |
| 1.14.4 Mojmap | `StairBlock.java` `b302c4e8e6abe8541051f09f4b502ce51927b5ad614fd214da6d30338331dac7` | `WallBlock.java` `4ad01bfd8d84b77abe161289294667e9f482cf9477cd81f725162aa9dc891850` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |

1.14.1–1.14.3 Yarn source directories are present and their consumed files were hashed above, but each lacks `yarn.sources.sha256`, `yarn.provenance.json`, and `yarn.ready.json`; exact mapping artifact/build identity is unavailable. 1.14 also contains an unmanifested Yarn root; canonical 1.14 evidence here uses the manifested Feather root. The 1.14.4 Feather and Mojmap roots each have separate manifests/provenance and were checked independently. A hidden `.yarn.staging-*` directory in 1.14.4 was not treated as a published root.

For block identity scope, 1.13.2 has Oak stairs but no `stone_stairs`/`STONE_STAIRS` registration. 1.14 and 1.14.4 Feather register both `OAK_STAIRS` and `STONE_STAIRS` as `new StairsBlock(...)` (`Blocks.java:410, 1861-62` in 1.14; `:410, 1871` in 1.14.4). Thus the 1.13 alias applicability is Oak-only; Stone stairs begin on the 1.14 side.

## Alias boundary

The recomputed Feather `StairsBlock` and `WallBlock` hashes are identical across 1.13, 1.13.1, and 1.13.2, and the exact selector result is unchanged in that group. All inspected 1.14 roots show the newer collision-full selector semantics; the exact .1–.3 raw sources support the same alias behavior, with their immutable provenance/mapping-build gate still open. For the bounded selector, group 1.13 aliases separately from 1.14 aliases. This conclusion does not establish other operations or runtime movement parity. No Git, build, test, decompile, game, TAS, Gym, or server command was run.
