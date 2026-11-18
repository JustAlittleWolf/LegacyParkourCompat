# Independent wall source audit: 1.12 through 1.12.2

COMPLETED — bounded source audit only.

## Evidence identity and integrity

- Canonical sources: `decompiled_minecraft/{1.12,1.12.1,1.12.2}/ornithe-feather`.
- 1.12 mapping-root `.decompile-provenance.json` SHA-256: `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` (matched assigned identity); Feather `1.12+build.2` artifact is identified in provenance.
- 1.12.1 mapping-root `.decompile-provenance.json` SHA-256: `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8` (matched assigned identity); Feather `1.12.1+build.2` artifact is identified in provenance.
- 1.12.2 readiness marker identifies `1.12.2`, Feather build 2, Vineflower, Java 25 and 2,050 source files. `ornithe-feather.sources.sha256` SHA-256 matched assigned identity `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- Relevant file hashes match the 1.12/1.12.1 provenance records and the 1.12.2 source manifest. The full 60-file block face-shape-provider inventory was checked against the 1.12.2 manifest (zero mismatches); the key state-dispatch files were checked too. Exact provider file set is the same across all three versions. The wall, generic block, fence-gate, face-shape and state-dispatch source bodies were intact.

## Per-edge verdicts

### 1.12 → 1.12.1: no wall movement delta found

`WallBlock.java` is byte-identical between these sources (SHA-256 `7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50`). `Block.java`, `FenceGateBlock.java`, `FaceShape.java`, `StateDefinition.java` and `BlockProperties.java` are also byte-identical. The 60 classes that provide `getFaceShape` have the same membership and contents on these two sides. Connection eligibility, exceptions, directional masks, cap/straight-run logic and local shape/collision selection therefore have no source change at this edge.

### 1.12.1 → 1.12.2: no wall movement delta found

The same six key files remain byte-identical. The 60-provider inventory remains unchanged. Three provider files differ at file level (`EndPortalBlock`, `FarmlandBlock`, `GrassPathBlock`), but their `getFaceShape` method bodies are identical across this edge; their differences do not affect horizontal wall connections. No directional provider or legal neighbor-rule change affecting this wall connection mask was found.

## Retraced operation

All versions use `WallBlock.resolveVirtualProperties` to query north/east/south/west neighbors, asking the opposite face on each neighbor. `shouldConnectTo` reads that state's directional face shape. It accepts a `SOLID` face unless `isExceptionForConnection` rejects the block; separately, it accepts `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` only for a `FenceGateBlock`. The exception set is `Block.isExceptionForAttachment` plus barrier, melon, pumpkin and lit pumpkin. Neighbor booleans become N/E/S/W bits in `getShapeIndex`; the alternating N+S-only or E+W-only arrangement removes the upper post only when the block above is air. The 16 local boxes are selected by this four-bit index. Collision uses the corresponding box with max Y extended to 1.5; `forceShape` bypasses virtual-property resolution only in `addCollisions`. These gates, orientations, dimensions, masks and array assembly are unchanged.

Source locations (same line ranges in all three files): `WallBlock.java` 28–66 (shape and collision arrays), 84–121 (shape query, collision dispatch, mask), 146–160 (neighbor rule and exceptions), 190–206 (resolved directional properties and wall face provider); `Block.java` 232–239 (attachment exception set) and 362–364 (default solid face); `FenceGateBlock.java` 175–177 (face provider); `StateDefinition.java` 415–416 and `BlockProperties.java` 96 (directional face dispatch contract).

## Scope and remaining gates

This result covers only vanilla-state, non-stair cobblestone-wall neighbor connection eligibility/mask and local collision box across these two edges. It excludes stairs and the fence/pane blocks' own geometry, state production, rendering, and unrelated movement. It is not complete pair movement coverage, a full-pair independent audit, implementation acceptance, or runtime parity. Those gates remain with the coordinator and their assigned owners.

