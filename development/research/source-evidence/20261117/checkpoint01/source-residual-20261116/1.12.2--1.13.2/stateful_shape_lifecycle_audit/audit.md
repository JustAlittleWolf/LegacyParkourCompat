# ACCEPT — stair/wall state lifecycle audit

## Audited input

- Note: `build/source-residual-20261116/1.12.2--1.13.2/stateful_shape_lifecycle/stair-wall-state-lifecycle.md`
- Required note SHA-256: `02836ADC143861993E1214FDC3EF3950E588E1BB20B7768258C63DA1F1242C0F`
- Verified note SHA-256: `02836ADC143861993E1214FDC3EF3950E588E1BB20B7768258C63DA1F1242C0F`
- Source-tree identities supplied for this audit: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All 15 consulted source-file hashes enumerated in the note were recomputed from the exact Feather paths and matched.

## Decision

**ACCEPT** the bounded lifecycle and reachability claims. No change is requested to the geometry/state-index table.

The 1.12.2 stair collision path resolves `SHAPE` from world neighbors in `StairsBlock.resolveVirtualProperties` before selecting collision boxes (`StairsBlock.java:66–74, 328–364`). The wall collision and collision-shape paths likewise resolve the cardinal flags and `UP` live (`WallBlock.java:83–103, 190–197`). The stair resolver reads its front/rear neighbors and a horizontal guard neighbor; wall derivation reads four horizontal neighbors plus the block above.

In 1.13.2, stair shape lookup uses stored state (`StairsBlock.java:98–105`); placement computes initial `SHAPE` (`224–232`) and each horizontal `updateShape` recomputes it (`235–243`). Wall placement computes the stored connection mask and `UP` (`WallBlock.java:68–93`); updates recompute the changed horizontal connection and `UP`, while `DOWN` is not an input (`96–119`). This matches the dependencies observed in the old live derivation.

Normal `World.setBlockState` calls neighboring shape updates unless flag 16 is set (`World.java:201–246`), and `Block.updateNeighborShapes` visits each of the six direct neighbors (`Block.java:166–175`). Thus the documented ordinary placement/edit lifecycle reaches the direct inputs before the next movement query. The identified repair routes are also real: chunk post-processing runs `UpgradeData.upgrade` (`WorldChunk.java:1062–1094`), whose inside and side passes invoke `updateShape` (`UpgradeData.java:66–111, 114–145`); structure-template placement explicitly updates boundary neighbors and calls `Block.updateFromNeighborShapes` for placed blocks (`StructureTemplate.java:280–304`).

The residual boundary is accurately stated. `StructurePiece.setBlockState` passes flag 2 (`StructurePiece.java:200–219`); with a `WorldGenRegion`, the write goes to `ChunkAccess.setBlockState` and returns without the `World.setBlockState` neighbor-update dispatch (`WorldGenRegion.java:206–230`). This proves a raw generation bypass, not by itself a built-in stale instance. The visible explicit nonstraight stair states in `SwapHutPiece` are supplied at its four roof corners (`SwapHutPiece.java:69–80`), and no built-in wall placement was found in the structure-piece tree. The note appropriately leaves arbitrary raw callers open while avoiding a claim that a vanilla mismatch or movement trajectory has been demonstrated.

## Scope and limits

This is a source-only lifecycle/reachability audit for the supplied endpoint sources. It does not establish runtime parity, inspect intermediate releases, or make claims about all arbitrary callers beyond the demonstrated raw setter boundary.
