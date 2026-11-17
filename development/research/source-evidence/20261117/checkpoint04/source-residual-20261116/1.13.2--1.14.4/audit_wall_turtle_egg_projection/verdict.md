# Independent audit: turtle-egg wall predicate

**Verdict: ACCEPT**, bounded to the `minecraft:turtle_egg` horizontal wall-neighbor predicate for 1.13.2→1.14.4.

- Audited report: `build/source-residual-20261116/1.13.2--1.14.4/wall_turtle_egg_projection/report.md`
- Report SHA-256: `2e9812592edac77052a1133d11a148518768a450462ee60ef3f6723d1beda34b`
- Endpoint source manifests: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`. Ready markers and on-disk source-manifest hashes agree at both endpoints.
- Independently checked all six cited Java file hashes against canonical source bytes and endpoint sidecars: TurtleEggBlock, WallBlock, Block, Blocks, BlockState, and Properties at both versions. Every hash matches the report and manifest.
- Crosswalk input `unexamined-wall-projection-keys.tsv` has SHA-256 `8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`; its exact turtle-egg row identifies TurtleEggBlock at both endpoints and leaves both state cardinalities unresolved for this report to establish.
- Both TurtleEggBlock state definitions register `HATCH` and `EGGS`; Properties sets HATCH to 0–2 and EGGS to 1–4. The exact key has 12 states at each endpoint. Old `getFaceShape` returns `FaceShape.UNDEFINED` unconditionally, so the old WallBlock solid-face path is false. TurtleEggBlock extends Block, so the middle-pole fence-gate alternative does not apply.
- Target `BlockState.m_87223014` delegates to `Block.isFaceSolid`; target collision shape is the egg-count-selected one- or multiple-egg shape. The exact `Block.block` helper scales coordinates by 1/16; both shapes are inset from all horizontal cell faces, so none has a full N/E/S/W face. Default `hasCollision=true` is confirmed; the target shape path is not disabled by `noCollision`.
- The target WALLS membership and fence-gate alternatives are false for this key, consistent with the cited accepted resource-membership audit. Thus the target connection bit is false for all 12 states × four horizontal directions.

The report's bounded no-difference conclusion is supported. This audit does not repeat the separate shape-provider review and does not establish movement impact, full wall-provider coverage, or pair completion. The r1 crosswalk overwrite limitation remains attached to the broader crosswalk.
