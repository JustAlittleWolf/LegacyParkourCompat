# Independent review: DDA torch family 50, 75, 76

Verdict: **ACCEPT** for the report's bounded registry/state aliases, movement-collision provider, and separate mutable ray/outline provider claims. This review does not establish traversal, visitation, cache/observer, or end-to-end DDA parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-torch-family-50-75-76/FINDING.md`
  - SHA-256: `990EBF44C43D88486B03E9CAF17151BCD0512C33AC7BDB57CAE0E18345124FC1`
- Source manifest: `.native-source/20261028/collision-next/dda-torch-family-50-75-76/source-manifest.json`
  - SHA-256: `B750DE5957C54E01525FCDC7F25DBA7F449F01FDC51330B1A38F7B3E08C4F6EF`

All 12 manifest entries were recomputed against the read-only canonical roots and matched exactly: 1.8.9 `ornithe-feather` and 26.2 `unobfuscated`.

## Findings checked

- 1.8.9 registry entries assign ID 50 to `torch`, 75 to `unlit_redstone_torch`, and 76 to `redstone_torch` (Block.java registrations). IDs 75 and 76 construct `RedstoneTorchBlock(false/true)`. `RedstoneTorchBlock` extends `TorchBlock`, so it inherits the relevant facing and collision behavior.
- `TorchBlock.getCollisionShape` returns null; `Block.addCollisions` adds only a non-null shape. Thus each of these three blocks has no legacy movement-collision box for every valid facing.
- The legacy facing property excludes DOWN and defaults to UP. `getStateFromMetadata` maps 1–4 to E/W/S/N, with 5 and default to UP; therefore 0 and unsupported metadata also decode UP. Encoder behavior and the report's statement that redstone litness is fixed by block identity are consistent with source.
- `TorchBlock.rayTrace` reads the state's facing, writes the facing-dependent bounds with `setShape`, and delegates to `Block.rayTrace`. `setShape` writes instance fields; the superclass ray routine calls the empty `updateShape`, so these ray bounds remain a separate mutable provider effect. The report correctly does not treat them as movement collision.
- 26.2 `EntityBlockStateFix` maps the three legacy identifiers to 50/75/76. `BlockStateData` maps ordinary horizontal torches to `wall_torch`, ordinary UP to standing `torch`; redstone horizontal variants map to `redstone_wall_torch` with matching `LIT`, and UP to standing `redstone_torch` with matching `LIT`. Current standing/wall torch registrations use `.noCollision()`; `BlockBehaviour.getCollisionShape` gates collision to empty when `hasCollision` is false. Outline shapes remain distinct, as the report states.
- The cited current standing, wall, and redstone shape/property references were checked against the listed source files. No product implementation or Git history was inspected.

No material source, alias, metadata, or provider error found within this bounded review.
