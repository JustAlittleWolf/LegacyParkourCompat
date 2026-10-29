# Independent review: 1.8.9 terrain solids IDs 1, 2, 3, 7, 12, 13

Verdict: **ACCEPT** for the bounded identity, metadata normalization, and per-block collision-provider/bounds closure stated in the reviewed finding. This does not establish DDA visitation, ordering, first-hit, movement reachability, or parity.

## Reviewed inputs

- Finding: `.native-source/20261028/collision-next/dda-terrain-solids-1-2-3-7-12-13/FINDING.md`
  - SHA-256: `F75301F1E150D3B3E1EF6C3F43D96905F319A6BEDC14847D6553C05E1CB88FAE`
- Exact-source ledger: `.native-source/20261028/collision-next/dda-terrain-solids-1-2-3-7-12-13/source-hashes.json`
  - SHA-256: `EA031A67CBBD950ED3FE9EB30D605323E17479FEEF1AA2874706D252623CC55A`
- Canonical 1.8.9 mapping/source identity: `ornithe-feather`, ready marker reports `ready`; ready-marker file SHA-256 `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; `.sources.sha256` SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.
- Canonical 26.2 mapping/source identity: `unobfuscated`, ready marker reports `ready`; ready-marker file SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; `.sources.sha256` SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

## Independent checks

- Recomputed all 17 Java file hashes in the source ledger against `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; all match. Independently checked each ledger entry against the corresponding canonical `.sources.sha256` file; 17/17 match. Both ready-marker file hashes, `status: ready`, and source-manifest hashes match the ledger.
- Verified old IDs and keys in 1.8.9 `Block.init`: 1 stone, 2 grass, 3 dirt, 7 bedrock, 12 sand, 13 gravel. `StoneBlock.Variant.byId`, `DirtBlock.Variant.byId`, and `SandBlock.Variant.byId` map out-of-range values to variant 0, so valid legacy metadata 7–15 for stone and 3–15 for dirt / 2–15 for sand normalize to their defaults. Grass, bedrock, and gravel use inherited metadata decoding. The report correctly excludes negative values and values above 15.
- Verified the 26.2 `BlockStateData` aliases and state projections: old stone variants resolve to their seven current block keys; grass to `grass_block`; dirt/coarse dirt/podzol to distinct keys; bedrock, sand/red sand, and gravel to their named keys. The report correctly does not confuse legacy stone's enum strings with current block IDs.
- Verified the old base constructor initializes bounds to `[0,0,0]–[1,1,1]`; `Block.getCollisionShape` offsets those bounds by block position, and `addCollisions` adds that box only on query intersection. The scoped old subclasses do not override `setShape` or `updateShape`; `rayTrace` calls the empty base `updateShape` for these instances before using the stored bounds.
- Verified the 26.2 ordinary collision route: `BlockBehaviour.Properties.hasCollision` defaults true; the scoped current registrations do not opt out with `noCollision`; default state outline shape is `Shapes.block()` and the default collision shape returns that state shape when collision is enabled. The inspected `GrassBlock`/snowy, sand, colored falling block, and falling block classes add no shape/collision override; their update hooks do not change this geometry. The bounded unit-cube provider conclusion follows for the listed mappings.

## Limits

The acceptance is limited to the stated six IDs, source-mapped aliases and ordinary collision route. It supplies provider inputs for a ray adapter but does not close traversal cells, visitation order, callback ordering, early-hit semantics, or full-registry provider coverage.