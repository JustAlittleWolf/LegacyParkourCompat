# Independent source audit: slime, soul sand, and magma wall predicates

Verdict: **ACCEPT**, bounded to the three wall-neighbor predicate rows in the author report below.

- Author report: `build/source-residual-20261116/1.13.2--1.14.4/wall_slime_soulsand_magma_projection/report.md`
- Audited report SHA-256: `78dcbc450091371d14e5802bc7becb290b09ff1964dd67ca1ed232ce2f4ec6c9`
- Exact source identities: 1.13.2 Ornithe Feather Gen2 build 2 manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 Ornithe Feather Gen2 build 2 manifest `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Both report identities match the ready markers.

I independently checked the report's listed Java hashes against canonical bytes and endpoint manifest entries: `Block.java`, `WallBlock.java`, `BlockState.java`, `SlimeBlock.java`, `TransparentBlock.java`, `MagmaBlock.java`, and `SoulSandBlock.java` on both endpoints, plus target `Blocks.java`. All matched. The old `Block.java` base horizontal `getFaceShape` is `SOLID`; `TransparentBlock` only changes render-face culling, while the assigned slime/magma subclasses do not override the shape methods. Base block shape is full cube and collision defaults on. SoulSand overrides collision shape on both sides with a 16×14×16 box. Target `BlockState.m_87223014` delegates to `Block.isFaceSolid`, which tests for a full collision-shape face. The two WallBlock predicates and their placement callers pass the neighbor face as described in the report.

The exact crosswalk input SHA is `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its rows identify these three keys and singleton state domains. I verified the 1.14.4 `WALLS` payload SHA `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`; it contains 14 wall keys and excludes all three. The referenced raw-resource extraction record and independent resource audit are present. The old explicit connection exceptions and target attachment exceptions also exclude none of the three; none is a fence gate.

The resulting bits are supported in all horizontal directions: slime block `true→true`, soul sand `true→false` (the 14/16-high collision face), magma block `true→true`. Thus the bounded source report correctly identifies one changed source-level neighbor bit for SoulSand while keeping the rest of the wall-state and player-trajectory dependencies open. This acceptance makes no claim about complete wall placement/update reachability, movement trajectory, or full-pair coverage/freeze.
