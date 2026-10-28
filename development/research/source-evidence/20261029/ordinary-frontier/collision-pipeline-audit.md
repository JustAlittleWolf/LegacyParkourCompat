# Independent collision pipeline report audit

## Verdict

**Verified for the assigned bounded slice; no material factual corrections.** The report accurately describes the compared iterator replacement, `noCollision` API/order, entity clipping and step-up order, world-border gate relocation, and requested-versus-clipped movement flags. Its explicit exclusions and open dependencies remain necessary. This verdict does not establish complete pair coverage, geometry equivalence, a concrete trajectory, runtime parity, or implementation acceptance.

## Inputs and identity checks

- Reviewed report: `.native-source/20261028/frontier-c/1171-1182-collision-pipeline-closure/collision-pipeline-report.md`
- Reviewed report SHA-256: `4c8aeed13d490a3f9e6dac6421a2b9199949ee00e3cef039ff8cd04efe98d74d` (matches assignment).
- Read-only canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.
- Both ready markers confirm requested release IDs, Mojmap namespace, Vineflower and Java 25. For both versions, the ready marker's `sourceManifestSha256` matches the SHA-256 of `mojmap.sources.sha256`; `mojmap.provenance.json` records the shared four-version exact run with `--mappings=mojmap`, and the success excerpt identifies the release and mapping.
- All source hashes listed below match both the current file bytes and the corresponding manifest entry. The `Cursor3D` hashes are added because cursor order and type classification are part of traversal verification.

## Claim audit

- **Iterator traversal, admission, termination:** Verified. `CollisionSpliterator` (1.17.1) and `BlockCollisions` (1.18.2) construct the same inclusive X/Y/Z cursor bounds with the same epsilon and one-cell expansion. Their cursor consumers call `advance()`, examine coordinates/type in that order, skip corners (type 3), retrieve a collision chunk, skip missing chunks, read the state, apply the release-specific predicate/suffocation gate before type-1/type-2 gates, then evaluate the collision shape and intersection. Both yield one passing shape per demand and continue at the next cursor item; exhaustion returns false / `endOfData()`. `Cursor3D.advance()` and `getNextType()` implement equivalent traversal and classification in both versions. The arbitrary 1.17 predicate and 1.18 suffocating-only option differ as the report states. The 1.18 chunk cache avoids repeated lookup only for a non-null cached getter, also as stated.
- **`noCollision` and APIs:** Verified. 1.17.1's predicate overload delegates to `getCollisions(...).allMatch(VoxelShape::isEmpty)`; combined streams are block first, entity second. In 1.18.2, `noCollision` checks block shapes, then entity list, returns true early for a null entity, then checks the border shape/query AABB overlap. Entity collision shapes are a `List`; combined `getCollisions` orders entity shapes before block iteration. The report correctly bounds this as API and check-order evidence.
- **`Entity.collide`, clipping axes, and step-up:** Verified. 1.17.1 creates a rewindable entity-collision-then-border stream and uses the heuristic split; when at least two requested components are nonzero it uses the legacy shape-stream clipper, otherwise the `LevelReader`/context overload. 1.18.2 materializes entity shapes and each clip call builds entity shapes, conditional border shape, then blocks before a single list clipper. In both, clipping is Y first, then X/Z with Z first only if `abs(X) < abs(Z)`, shifting the box after nonzero Y and first horizontal clipping. The step gate, three candidates, strict horizontal-distance comparisons, and final vertical clip align with the report.
- **World-border gate relocation:** Verified. The 1.17 iterator's one-time border check is source-entity based and inspects the source's current bounding box, with the fully-within and inflate/deflate shape tests described. `Entity.collide` separately conditionally appends a border shape after entity shapes. In 1.18, block iteration has no border stage; movement clipping checks `isInsideCloseToBorder` for each swept box and appends border after entity shapes and before blocks. `noCollision` applies the border check after block/entity checks. The geometric `Shapes.INFINITY - interior-box` expression is unchanged in the compared border extents.
- **Requested versus clipped displacement flags:** Verified. In both `move` methods, after piston limiting, stuck-speed adjustment, and `maybeBackOffFromEdge`, the resulting vector is passed to `collide`; that vector is the correct meaning of requested displacement for the report. Horizontal flags use `!Mth.equal` on X/Z, vertical uses exact inequality, and `onGround` uses vertical collision plus negative requested Y. 1.17 independently zeros X/Z velocity only on exact component inequality. 1.18 zeros components under the horizontal flag branch using the Mth.equal-derived component booleans. The report records this distinction correctly.

## Open scope retained

The report appropriately leaves `VoxelShape`/`Shapes.collide` internals and shape providers, `Level` entity-collision production/order, and player-specific edge/minor-collision overrides open. These prevent geometry-equivalence or trajectory conclusions; no such conclusion is made here. No runtime or test/build/game/server action was performed.

## Source identities

All paths are relative to each release's `mojmap/` directory.

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.17.1 | `net/minecraft/world/level/CollisionGetter.java` | `eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747` |
| 1.17.1 | `net/minecraft/world/level/CollisionSpliterator.java` | `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0` |
| 1.17.1 | `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| 1.17.1 | `net/minecraft/world/level/border/WorldBorder.java` | `b848dd0dc6043d6c9012726844bea53cf4558c0135a08fa3be55960b0e557df9` |
| 1.17.1 | `net/minecraft/core/Cursor3D.java` | `e0bb657461a1e3a4dd1eb8b33f16f23bd2642e727e086c78867d488bbcc148d9` |
| 1.18.2 | `net/minecraft/world/level/CollisionGetter.java` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` |
| 1.18.2 | `net/minecraft/world/level/BlockCollisions.java` | `11fecadc012d0a544097bf0febfa1a83da5a534e6182ee7df7b2334b6ee80127` |
| 1.18.2 | `net/minecraft/world/entity/Entity.java` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| 1.18.2 | `net/minecraft/world/level/border/WorldBorder.java` | `ade6bcb18ae0b7a2c6e9978295288bfac6fbb759a0f8a2dfb9d3983788d332fb` |
| 1.18.2 | `net/minecraft/core/Cursor3D.java` | `9516429bc3db97b97a2e5dde5a3a7a18e7e0377e56393a6a8b5c480f742ebb2b` |
