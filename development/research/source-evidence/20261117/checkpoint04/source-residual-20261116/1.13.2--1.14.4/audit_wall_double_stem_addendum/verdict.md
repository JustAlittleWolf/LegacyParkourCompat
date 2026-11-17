# Independent source audit verdict — evidence addendum

Verdict: **ACCEPT — narrow evidence-binding addendum**

Addendum: `build/source-residual-20261116/1.13.2--1.14.4/wall_double_stem_plant_projection/report-addendum-evidence-bindings.md`

Addendum SHA-256: `391c8ba29439517e689914e4c5d02335d47556b0cfb9bc10eac6e0b378be940e`

Bound original report SHA-256: `59c078e34b1d9df85ae7e913a94914d9c0bcd4b102599e712eaf389439ba7f8c`

The prior REQUEST CHANGES verdict is the expected file at `audit_wall_double_stem_plant/verdict.md`, SHA-256 `c8857b4a72f2fa3aab083e320786ce0f20934288666cc1a1b811ab59aee5cb95`.

## Verified

- Both endpoint ready markers match the stated 1.13.2 and 1.14.4 Feather source-manifest digests.
- All newly added `BlockState.java`, `Half.java`, `Direction.java`, `DirectionProperty.java`, `Properties.java`, and `HorizontalFacingBlock.java` hashes match the canonical read-only files and their `.sources.sha256` entries at both endpoints.
- The cited old `BlockState.getFaceShape` delegation at lines 285–286 and target `m_87223014` delegation to `Block.isFaceSolid` at lines 326–327 are present.
- `Half` contains exactly UPPER and LOWER; `Direction.Plane.HORIZONTAL` lists NORTH, EAST, SOUTH, WEST at the cited endpoint lines; `Properties.HORIZONTAL_FACING` uses that plane and `HorizontalFacingBlock.FACING` aliases it. The added evidence supports the corrected four-way horizontal cardinality.
- The original report and prior audit verdict still match their bound hashes. The addendum supplements the original evidence without changing its bytes.

This resolves only the requested evidence-binding omission. It does not repeat the geometry comparison or broaden the bounded predicate claim; the pair remains partial.
