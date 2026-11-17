# Independent source audit: coral wall-fan wall predicate

Verdict: **ACCEPT**, bounded to the explicit ten-key wall-neighbor predicate claim in the audited report.

- Author report: `build/source-residual-20261116/1.13.2--1.14.4/wall_coral_wall_fans_projection/report.md`
- Report SHA-256: `6751677394f1bf9491284ea677b4e24c5dc43ac968905c222633143747751b93`
- Audited endpoints: 1.13.2 and 1.14.4, Ornithe Feather Gen2 build 2.
- Ready-marker source-manifest SHA-256 values match the report: 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14.4 `717b468536348557f9dae8cf57edccf80ad16eb65e5bef6c60bdb5ea3c9f75bc`.

I verified the report's 25 Java-file hashes against both canonical source bytes and the matching per-version `.sources.sha256` entries. All 25 source bindings matched. The report's crosswalk input hash also matches the current `unexamined-wall-projection-keys.tsv` (`8a79ad3e851470a59976b9120d085ab2ec556d3cd61885205113dbd167712366`), and that roster contains exactly the ten listed coral wall-fan keys with the stated old/target concrete classes and old `BaseCoralPlantTypeBlock` face-shape owner.

The registrations in old `Block.java` and target `Blocks.java` map the five dead keys to `BaseCoralWallFanBlock` and five live keys to `CoralWallFanBlock`; every registration sets `.noCollision()`. `BaseCoralWallFanBlock` registers `FACING` and `WATERLOGGED`. `FACING` resolves through `HorizontalFacingBlock.FACING` to `Properties.HORIZONTAL_FACING`, whose `Direction.Plane.HORIZONTAL` values are NORTH/EAST/SOUTH/WEST; `WATERLOGGED` is boolean. Thus there are eight registered states per key, and 10 × 8 × 4 = 320 key/state/direction cases.

The old `BaseCoralPlantTypeBlock.getFaceShape` returns `FaceShape.UNDEFINED`, inherited by both wall-fan classes. Old `WallBlock.shouldConnectTo` only admits non-exception SOLID faces or the pole/fence-gate route, so these states do not connect. In the target, `WallBlock` evaluates `BlockState.m_87223014` and then the tag/fence-gate alternatives. `m_87223014` uses the cached face result when available or `Block.isFaceSolid`; the cache itself is computed from `Block.isFaceSolid`. With `.noCollision()`, `Block.getCollisionShape` returns empty, so all face-full checks are false. Direct target outline shape does not override this collision route. I independently checked the extracted 1.14.4 WALLS payload (`f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c`); its fourteen values contain no coral wall-fan key. The separate fence-gate alternative does not apply to these registrations.

Conclusion: all 320 cases are false at both endpoints. No correction addendum is required for this bounded claim. The r1 crosswalk TSV overwrite limitation remains attached to the crosswalk's broader historical audit; this verdict does not restore a byte-for-byte audit of unchanged r1 rows and does not establish movement trajectory, other coral families, or pair completion.
