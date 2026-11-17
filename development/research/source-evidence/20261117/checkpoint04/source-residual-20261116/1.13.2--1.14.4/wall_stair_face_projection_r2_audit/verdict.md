# Independent source audit: wall stair face projection r2

**Verdict: ACCEPTED. Terminal status: SUCCESS.**

This fresh audit binds to `wall_stair_face_projection_r2/report.md`, SHA-256 `ae25ccd06649fb2eb9e65d0f00243957d258d6d081884b388f477305897b8a67`. I independently checked the corrected face-direction arithmetic and recomputed the bounded state matrix from the decompiled source. The report's result of **0 mismatches / 1,600 comparisons** is correct. This does not establish full-pair coverage, runtime movement parity, or an intermediate patch boundary.

## Evidence identity

The report bytes match the assigned SHA. Both ready markers identify Ornithe Feather Gen2 build 2 for the requested exact versions. Their source-manifest bytes hash to the supplied values:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

I recomputed SHA-256 for each cited source and confirmed it matches both the report and that version's `ornithe-feather.sources.sha256` entry. The source hashes are:

| Source | 1.13.2 | 1.14.4 |
|---|---|---|
| `net/minecraft/block/StairsBlock.java` | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | — |
| `net/minecraft/block/Block.java` | — | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| `net/minecraft/block/state/BlockState.java` | — | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |

## Recalculation

In 1.13.2 `StairsBlock.getFaceShape`, the horizontal INNER_LEFT condition returns SOLID when `facing == face` or `facing == face.clockwiseY()`. Since `clockwiseY` is a four-direction bijection, the second relation is equivalently `face == facing.counterClockwiseY()`. INNER_RIGHT similarly gives `face == facing.clockwiseY()`. The implementation's direction methods confirm NORTH→EAST→SOUTH→WEST→NORTH for clockwise and the reverse for counterclockwise. Thus for FACING=NORTH, INNER_LEFT is NORTH+WEST and INNER_RIGHT is NORTH+EAST. STRAIGHT is just FACING; both OUTER shapes return no horizontal solid faces.

The 1.14.4 target `StairsBlock.getShape` selects `SHAPE_INDICES[shape.ordinal()*4 + facing.getIdHorizontal()]`; the source enum order is STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT. `Direction` supplies horizontal IDs NORTH=2, EAST=3, SOUTH=0, WEST=1. The selected index bits add the named corner cuboids in `makeShape` (1=NW, 2=NE, 4=SW, 8=SE). For a horizontal collision face to be full, its edge pair must both be present: NORTH=3 (NW|NE), EAST=10 (NE|SE), SOUTH=12 (SW|SE), WEST=5 (NW|SW). The base cuboid fills the other vertical half; TOP/BOTTOM only swaps vertical placement and cannot change horizontal edge coverage. `Block.isFaceFull` projects the collision shape onto the requested face and tests for a full face; `Block.isFaceSolid` obtains that collision shape, and `BlockState.m_87223014` dispatches to it. The stair shape has no context-dependent selection.

I evaluated each old predicate directly against the target selected index and edge-pair predicate across all ten registered stair keys, four facings, both halves, five shapes, and four horizontal faces. Result: **1,600 cases; 0 mismatches**. The row-level face sets in r2 follow from this calculation. The corrected direction inversion is material: reading the source relation as the same-sense adjacent face would produce the previously disputed 320 count, but it does not follow from `facing == face.clockwiseY()` / `counterClockwiseY()`.

The old `WallBlock` generic arm accepts the stair when its queried face shape is SOLID; the target wall generic arm consumes the directional `m_87223014` result. For these stair cases, the compared directional booleans agree. No additional implementation recommendation follows from this bounded source result.

## Scope limits

The assignment requested only a source audit of the corrected r2 report. No product implementation, wiki material, backlog, campaign history, tests, build, game launch, source generation, or network access was used or changed. The audit output is this verdict only.
