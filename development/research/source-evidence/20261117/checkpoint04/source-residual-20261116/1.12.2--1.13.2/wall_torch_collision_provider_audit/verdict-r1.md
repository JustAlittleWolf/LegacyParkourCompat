# Independent audit: wall-torch collision provider

## Verdict

**REQUEST CHANGES** for `wall_torch_collision_provider/report.md` (SHA-256 `515e5e0e99b5f9ffef23cab5acb8d3979cbe32fc4de14102c7daf7334ffdcd3d`). The reported source identities and all 14 Java source hashes in its table were verified against canonical source bytes and endpoint manifests. The bounded key mapping, target collision gate, inherited `FaceShape.UNDEFINED`, and collision-route claims are supported.

One scope sentence is factually incorrect: the report states that no call site for `C_80754635.m_11299400` appears in the inspected source tree. `net/minecraft/unmapped/C_15874933.java:16–20` calls it as the remainder transformation for `TypeReferences.BLOCK_STATE`, and `net/minecraft/util/datafix/DataFixes.java:228` registers `C_15874933`. These sources do not prove that every external/custom importer applies the data fixer, so the external migration-applicability limit may remain, but the report must acknowledge this registered fixer call path.

The two additional 1.13.2 source hashes, checked against both local bytes and the ready-bound source manifest, are:

- `net/minecraft/unmapped/C_15874933.java`: `8d62c370f468ca9534a4db65338dacc69a0f416079d4c499cc8d0e9250c3ae71`.
- `net/minecraft/util/datafix/DataFixes.java`: `eb0e351b692e79b3dda9c04afe75fb242ba7d7ff9c789a04ee8b18ac13f6b1e8`.

## Verified source identities and claims

- 1.12.2 ready marker source-manifest digest is `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 digest is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The on-disk sidecar hashes match both ready markers.
- All 14 report-table file hashes match their canonical Java file bytes and the corresponding manifest entries.
- 1.12.2 registers numeric block ID 50 as `minecraft:torch`; `TorchBlock.FACING` excludes DOWN, leaving four horizontal states in this scope. `C_80754635` contains four horizontal input entries mapping to `minecraft:wall_torch` with the same facing and a separate up-facing torch entry. Its helper places the first state as output and maps the following state inputs to that output.
- The old `TorchBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE`. Target `wall_torch` registration calls `.noCollision()`, and target base `getCollisionShape` returns `VoxelShapes.empty()` when `hasCollision` is false. The report is correct that these provider outputs are empty across the bounded states.
- Both old and target `TorchBlock.getFaceShape` return `UNDEFINED`; target `WallTorchBlock` inherits this method. The report does not assert that the orientation-specific outline boxes are equal.
- Old world collision dispatch reads the state collision callback; target world collision dispatch reads `BlockState.getCollisionShape` and filters empty shapes. No collision-provider delta is established for this bounded slice.

The audit does not establish outline equality, external/custom migration applicability, movement trajectory, or pair completion. Correct the fixer-call-path statement and bind the two cited source files, then request a narrow re-audit; no geometry re-comparison is needed.
