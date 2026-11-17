# Corrected stair wall-face projection recomputation: 1.13.2 → 1.14.4

## Scope and exact inputs

This is a new source-only recomputation of the ten-key row covered by `wall_stair_face_projection/report.md`; it does not alter that report or its audit. The older report SHA-256 is `03380e2cd9b2c4c29a49ad018586fa0e14777a5904f396e7b4fe8b78f8e2254d`; the independent verdict SHA-256 is `a38c5470c25fbc39266e52e21cf15c4f0dc7b6dc419dae087fe8b18a3988829d`.

Compared exact releases 1.13.2 and 1.14.4 using Ornithe Feather Gen2 build 2. The local ready markers and source-manifest bytes agree on these manifest SHA-256 values:

- 1.13.2: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14.4: `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdbb5ea3c9f75bc`.

Relevant source bytes agree with manifest entries:

| Source | 1.13.2 SHA-256 | 1.14.4 SHA-256 |
|---|---|---|
| `net/minecraft/block/StairsBlock.java` | `b226eb255a3a1b0a69e137c15024a3b0a48f488e7205174dd476ffd64ca82761` | `b49cbd43a9be5e848623f25769dede5795f50435ba0e82bbdbbf571527396b9f` |
| `net/minecraft/block/WallBlock.java` | `aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf` | `033da2cef0973f5d380a81b53ee76e23324fffece72edc74e12fcee0d8764be7` |
| `net/minecraft/block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` | `9c0323497b31f8a284456e5692898897b92b6f0817d0371fae9a8afb54c638f8` |
| Target `net/minecraft/block/Block.java` | — | `1f8ec3628f08e196c2793e91ca0b94feacea6bc3939e7fb770f0682a6e66f2c5` |
| Target `net/minecraft/block/state/BlockState.java` | — | `6c6703c7c2f7203a7507d8a621bc83e1dad613b32089394f5af4fbd8636b165f` |
| 1.13.2 `net/minecraft/util/math/Direction.java` | `6d5c496ff65377fee9f00c826e48c7be14d3889e5ae5191e110a464007549082` | — |

Both registration files instantiate all ten assigned IDs as `StairsBlock`: oak, spruce, birch, jungle, brick, sandstone, quartz, acacia, prismarine, and purpur stairs. Their state properties are `FACING`, `HALF`, `SHAPE`, and `WATERLOGGED`; the requested comparison covers every value of the first three and all four horizontal wall-query faces. Neither old nor target stair face result reads `WATERLOGGED`.

## Predicates compared

In 1.13.2, `StairsBlock.getFaceShape` (lines 108–130) handles horizontal faces using this exact rule:

- `STRAIGHT`: solid iff `face == facing`.
- `INNER_LEFT`: solid iff `face == facing` or `facing == face.clockwiseY()`.
- `INNER_RIGHT`: solid iff `face == facing` or `facing == face.counterClockwiseY()`.
- `OUTER_LEFT` and `OUTER_RIGHT`: never solid.

The important orientation step is solving the source condition for the queried `face`: `facing == face.clockwiseY()` means `face == facing.counterClockwiseY()`. Likewise `facing == face.counterClockwiseY()` means `face == facing.clockwiseY()`. The old sets are therefore INNER_LEFT = facing plus counterclockwise-facing, and INNER_RIGHT = facing plus clockwise-facing. Reversing those relations is the specific error behind the 320-difference audit count.

The old wall generic arm (`WallBlock.shouldConnectTo`, lines 51–65) accepts an unexcluded stair when its queried `FaceShape` is `SOLID`; the pole arm cannot accept stairs because it requires a pole face shape and a fence-gate block. Stairs are not in the old attachment exception set.

In 1.14.4, `WallBlock`'s generic arm (lines 48–52) uses the `bl` face-solid boolean. Its placement and update paths pass the neighboring block's opposite horizontal face to `BlockState.m_87223014` (lines 55–93). `BlockState.m_87223014` dispatches to `Block.isFaceSolid` (lines 326–328); `Block.isFaceSolid` calls `isFaceFull` on the directional collision shape (Block.java lines 402–408). The target wall tag and fence-gate arms do not add these ten keys: none is a gate, none is an attachment exception, and the independently checked raw 1.14.4 client tag payload has SHA-256 `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` (raw client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`); its 14 `walls` entries are wall IDs, not any of these stair IDs.

The target stair `getShape` (lines 47–105) indexes the common base and corner-cell cuboids using `SHAPE_INDICES` and horizontal direction IDs. `makeShape` adds corner cuboids for bits 1=NW, 2=NE, 4=SW, 8=SE. For a horizontal face to be full, both corner cells along that edge must be present: NORTH=NW|NE, EAST=NE|SE, SOUTH=SW|SE, WEST=NW|SW. The base cuboid supplies the opposite vertical half of the face; the selected edge corners supply the remaining half. `HALF` swaps which vertical half is base versus corners without changing horizontal edge coverage. The target collision context is not read by the stair shape selector. The shared same-state cuboid evidence is `stair_shape_provider/coverage.md`, SHA-256 `7c45510fc88f2c49d003c040619bdaf74a9a878fba460a33163c793450859753`.

## Complete recomputation

I recomputed all 10 keys × 4 `FACING` × 2 `HALF` × 5 `SHAPE` × 4 horizontal query faces = **1,600 comparisons**. The calculation applied the old boolean rule directly from its source expression, independently selected the target `SHAPE_INDICES` value using the source enum order and `Direction.getIdHorizontal`, then intersected its bits with each edge mask above. Each face was counted as full only when both bits in that queried edge mask were present.

| Shape | Facing | Old solid horizontal faces | Target full collision faces | Differences |
|---|---|---|---|---|
| STRAIGHT | NORTH / EAST / SOUTH / WEST | NORTH / EAST / SOUTH / WEST respectively | NORTH / EAST / SOUTH / WEST respectively | none |
| INNER_LEFT | NORTH | NORTH, WEST | NORTH, WEST | none |
| INNER_LEFT | EAST | EAST, NORTH | EAST, NORTH | none |
| INNER_LEFT | SOUTH | SOUTH, EAST | SOUTH, EAST | none |
| INNER_LEFT | WEST | WEST, SOUTH | WEST, SOUTH | none |
| INNER_RIGHT | NORTH | NORTH, EAST | NORTH, EAST | none |
| INNER_RIGHT | EAST | EAST, SOUTH | EAST, SOUTH | none |
| INNER_RIGHT | SOUTH | SOUTH, WEST | SOUTH, WEST | none |
| INNER_RIGHT | WEST | WEST, NORTH | WEST, NORTH | none |
| OUTER_LEFT / OUTER_RIGHT | any | none | none | none |

The table applies identically to `BOTTOM` and `TOP`, and to each of the ten registered keys. Exact old/target mismatching pairs: **∅ (0/1,600)**. For example, with `FACING=NORTH`, old `INNER_LEFT` admits NORTH and WEST because the source condition is `NORTH == face.clockwiseY()` at `face=WEST`; the target's INNER_LEFT source index is 7 (bits NW+NE+SW), whose complete edges are NORTH and WEST. Old and target INNER_RIGHT likewise both admit NORTH and EAST (target index 11, bits NW+NE+SE). Thus the audit's 320 count comes from reading the queried-face inversion backwards, not from a target/old geometry discrepancy.

For these keys and stored states, wall placement and horizontal neighbor updates consume the same stair-derived directional boolean at both endpoints, so the bounded stair-derived wall arm bits do not change. This is only a source predicate/state result, not measured movement trajectory parity. It establishes no intermediate release boundary and does not claim full-pair coverage or freeze.

No source generation, product/wiki/backlog/history edits, tests, build, game launch, or network access were performed.
