# Door companion-state collision-shape applicability

## Scope and source identity

Exact pair: 1.8.9 → 1.9, Ornithe Feather. Source roots and publication identities are:

- 1.8.9: `decompiled_minecraft/1.8.9/ornithe-feather/`; `ornithe-feather.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; `ornithe-feather.provenance.json` SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9: `decompiled_minecraft/1.9/ornithe-feather/`; mapping-local `.decompile-provenance.json` SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.

All listed Java hashes below were recomputed from file bytes and matched the corresponding 1.8.9 source-manifest row or 1.9 provenance source row.

## Source comparison

The conditional delta is in how DoorBlock derives shape inputs when its companion half is not the same door:

- 1.8.9 `net/minecraft/block/DoorBlock.java` SHA-256 `51b93fcb01758e1b809a503a9e63aab6d781dbd9a0e4d3b95823168e8958128d`, `getCollisionShape` / `updateShape(WorldView, BlockPos)` (lines 71–82) calls `getCombinedMetadata` (222–241). That method reads metadata below and above without checking either block identity. It combines facing/open bits from the lower-position metadata with hinge/powered bits from the upper-position metadata, whichever blocks occupy those positions.
- 1.9 `net/minecraft/block/DoorBlock.java` SHA-256 `13b045e7241341a7f50a25f42f0134df9ecf2d856f074a084c7677cc0946b004`, `getShape` (49–66) resolves virtual properties before selecting a box. `resolveVirtualProperties` (278–294) copies upper hinge/powered fields into a lower half, or lower facing/open fields into an upper half, only when the adjacent block is this DoorBlock.
- With a matching lower/upper pair, these inputs correspond: both releases use lower facing/open and upper hinge/powered. This review found no Door shape difference for those valid paired states.
- If a door half instead has a non-door block in the companion position, 1.8.9 can interpret that block’s metadata as door fields; 1.9 leaves the door state’s own fields in place. That is a conditional malformed/missing-companion shape input, not by itself evidence of an eligible player movement difference.

## Ordinary state construction and query reachability

The ordinary construction routes inspected create paired halves:

- 1.8.9 `net/minecraft/item/DoorItem.java` SHA-256 `6dc00a0314ca7bc1fa5b4d160cda18c17e8c18a63d7b02e25a1a52339aff5f6b`, `place` (44–61), writes lower and upper from the same base state before notifying neighbors. Its `StructurePiece.placeWoodenDoor` caller delegates to this paired placement (`net/minecraft/world/gen/structure/StructurePiece.java`, SHA-256 `01f0ee0b46bec9c1059b7cfac58e77261b521a059ac06faa74ae7f12783b7737`, lines 600–604).
- 1.9 `net/minecraft/item/DoorItem.java` SHA-256 `12268d913ca8d81a1cd17fa2b30d15066b2ea1e661a7979e95cab6b2b619b0d1`, `place` (54–79), likewise writes both halves before neighbor notifications. Its `StructurePiece.placeWoodenDoor` writes lower and upper at the same x/z from corresponding states (`net/minecraft/world/gen/structure/StructurePiece.java`, SHA-256 `34a88f8d02f71bc89f403fd2049fa938dfc5308c0e0d327b954f3fe78e04e904`, lines 373–378).
- The checked stronghold generation call sites also write both door positions: 1.8.9 `StrongholdPieces.java` SHA-256 `5f41411bd8c17fb3ac0f0abe941dcc120034cb6294b1e255da93f8b79ccc3b81` (lines 835–838, 1313–1336); 1.9 `StrongholdPieces.java` SHA-256 `59143955ffde91488d09ccb567317d05e7d395222bfd1e6221bed23a45b830be` (lines 814–819, 1278–1302).
- In both releases, DoorBlock.neighborChanged removes an upper half whose lower position is not the same door and removes a lower half whose upper position is not the same door (1.8.9 DoorBlock lines 157–184; 1.9 lines 160–187). The paired World.neighborChanged methods invoke this only on the non-client world (1.8.9 `World.java` SHA-256 `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee`, lines 371–378; 1.9 `World.java` SHA-256 `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98`, lines 373–380).
- A local-player collision query can reach block collision shapes through `World.getCollisions` (1.8.9 World lines 891 onward; 1.9 lines 893 onward). Reachability of the query does not establish that its Door has mismatched companion state.

The inspected placement and structure-generation paths therefore do not establish the mismatched state as an ordinary stable map/player state. This bounded check does not certify every chunk-load or client block-update timing path. Since client-side neighbor validation is skipped and block updates can be applied separately, a transient client mirror mismatch is not ruled out; no source evidence here establishes a deterministic local-player tick occurring in such a mismatch window. I did not use an arbitrary malformed snapshot as an applicability witness.

## Disposition

**Applicability remains unestablished.** The old unchecked companion-metadata read is real, but no ordinary deterministic player/map lifecycle for a mismatched Door half was established. Retain it as a conditional malformed-state source observation only; do not promote it to an accepted movement finding. The next useful source gate would be exact chunk-load/client-update lifecycle evidence showing whether a mismatched pair can persist through a local-player collision tick. No trajectory, implementation, or pair-freeze claim is made.
