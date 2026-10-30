# Independent review: legacy liquid state projection and provider facts

Verdict: accept the 64-row numeric state projection and the bounded legacy collision/ray-provider facts. These claims do not establish a generic adapter, traversal, ray-hit, fluid-physics, or runtime-parity result.

Reviewed immutable inputs:

- `.native-source/20261030/collision-pool/fluid-author/FINDING.md`, SHA-256 `D487E8D2EAA67AFF2D44C73142AD20199669AF3E10309526E974381BCD6E237F`.
- `.native-source/20261030/collision-pool/fluid-author/fluid-state-projection.tsv`, SHA-256 `F38860D8F6C5FD91074B477B3924D6141E353350106CBA847106DB17D9561BA4`.
- Canonical source ledger: `canonical-source-hashes.sha256`, SHA-256 `542B8610D4507E26607C2E2AD82BFB662E5BA14BA22BE54CAF3A76EFA9CBEA75`.
- Permitted input ledger: `allowed-input-hashes.sha256`, SHA-256 `21AB3564A3CFF3F0CAF1E8337CB97D12B6AA850AE4777F9B20DF5AF31FBA950B`.
- Artifact ledger: `artifact-hashes.sha256`, SHA-256 `F62960A4DADA3B4E4CE826DB0F1F37B7F792918760D43F6DDEE2FDF709F55A4E`.

All canonical source, permitted-input, and output hashes in those ledgers matched. The table has 64 unique `(old ID, metadata)` rows, exactly 16 for each ID 8–11. Each old tuple is `LEVEL=n`, with valid property values 0–15; the registrations preserve separate old flowing and source instances. Every numeric DataFixer index is `id*16+n`, every row is explicit, and each current target is the matching `water` or `lava` key with `level=n`. The target names resolve to current registrations and the level property codec accepts all listed values. The direct numeric output already has the final water/lava names, so the later flowing-name rename has no row-local action; the `BLOCK_NAME` flattening route is separate. The projection retains the old instance distinction as an identity note and correctly avoids claiming current behavioral equivalence.

The old provider facts also match the canonical sources: liquid block collision shape is null, inherited `addCollisions` skips null boxes, constructor bounds are the unit cube, inherited `updateShape` is empty, and ray eligibility is `allowLiquids && LEVEL == 0`. The world ray path separately excludes a null-collision block when `ignoreBlocksWithoutCollision` is true. This is a provider and gate result, not ray traversal or caller-specific movement behavior. The modern context section remains separately bounded and does not claim parity.

**Evidence-identity correction:** the finding's final sentence leaves mapping artifact/build coordinates open because the supplied prior `source-manifest.json` contains only digests. Canonical provenance manifests, which are permitted exact-source evidence, close that gate: 1.8.9 is Ornithe Feather Gen2 `1.8.9+build.2` (`feather-gen2-1.8.9+build.2-mergedv2.jar` and `.tiny`); 26.2 is the published `unobfuscated` namespace with mapping option `unobfuscated` and no external mapping artifact. Their provenance hashes are the exact digests already recorded in the report. The pair identity is therefore complete for the named canonical source trees.

To make the `name-only` boundary explicit in any reconciliation, `BlockStateData.upgradeBlock(String)` resolves only a name; it cannot preserve metadata/`level`. Full state projection is bound to the per-row old alias plus numeric index and emitted `Name`/`Properties` tuple. No further source or tuple gate remains within IDs 8–11. Actual save migration-path execution, a movement-relevant caller's ray flags, ray visitation/hit selection, fluid movement equivalence, and runtime parity remain outside this closure.
