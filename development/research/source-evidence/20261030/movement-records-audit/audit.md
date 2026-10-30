# Independent audit: movement records source sweep

Audit date: 2026-10-30. Scope: ordinary player movement-record production, queue transfer/lifetime, axis order and LocalPlayer tick reachability in the bounded source sweep. This is a source-only audit, not a pair freeze, implementation acceptance, or runtime-parity verdict.

## Immutable input and evidence identity

Audited report: `.native-source/20261029/partial-sweep-movement-records-source/report.md`
SHA-256: `A075D735E81FC8136B151653EEC2A3F741529F2B7DDD73D46E91F3805D441457`

Canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release | Mapping root | Provenance SHA-256 | Audit check |
| --- | --- | --- | --- |
| 1.21.2 | `1.21.2/mojmap/` | `00F399407DC447737EA83E3E46562418F8C712F66E3F51CB85D46AFA2ECAC575` | Older `.decompile-provenance.json` embeds per-source hashes. Entity, LivingEntity, Player, LocalPlayer and ServerGamePacketListenerImpl hashes match those embedded entries. Its `inputs` includes the Mojang 1.21.2 version metadata, client jar and `client.txt` mapping input; the mapping-text input hash is `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`. |
| 1.21.3 | `1.21.3/mojmap/` | `24093437C0CD41D807AB0829068EF97828B65BBB95B38DFDC4F706883A8483D7` | Inspected Entity, LivingEntity, Player, LocalPlayer and packet-listener files match the source manifest. |
| 1.21.4 | `1.21.4/mojmap/` | `AB0439864C89F4E0DAB09DAAE7B57AB973368EEB0320626822E06F5A9BCE9FB9` | Same five-file check passes against source manifest. |
| 26.2 | `26.2/unobfuscated/` | `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1` | Entity, LivingEntity, Player and LocalPlayer files match the source manifest. |

**Identity verdict:** the report's 1.21.2 mapping identity is evidenced by the Mojmap root and the embedded `client.txt` input/hash. Its provenance format lacks a top-level mapping-option field and a separate ready/manifest envelope, but that does not make mapping identity absent or block these source comparisons. A preparation-owner acceptance policy may still govern source acceptance; the claimed missing-mapping evidence is not supported.

## Accepted source claims

1. **1.21.2 and 1.21.3 ordinary move does not produce a movement record; block-effect finalization appends the supplied old-to-current segment.** `Entity.move(MoverType, Vec3)` at `world/entity/Entity.java:619–701` (same body in both releases) resolves and applies movement without adding to `movementThisTick`. `applyEffectsFromBlocks(Vec3, Vec3)` at `:740–752` appends `(from,to)` only under `isAffectedByBlocks()`, passes the mutable list to `checkInsideBlocks`, and clears it after the consumer and fire/lava query. The movement record is only `(from,to)` (`:3608`). There is no zero-length test around the appended segment.

2. **1.21.4 preserves ordinary move behavior but changes transfer timing.** `Entity.move` at `:627–712` still does not record ordinary calls. `applyEffectsFromBlocks(Vec3, Vec3)` at `:751–765` appends the supplied segment under the same gate, snapshots with `List.copyOf`, clears `movementThisTick`, then invokes `checkInsideBlocks` with the copy. This establishes a live-list versus detached-snapshot lifetime difference.

3. **The callback timing difference is real, but a vanilla ordinary-player callback consequence is not established by the report.** In 1.21.2/1.21.3, `checkInsideBlocks` enhanced-for iterates its list (`Entity.java:1038–1078` in 1.21.2; same lines in 1.21.3), and `BlockState.entityInside` is invoked inside that loop (`:1060`). If a callback appends through the public `recordMovementThroughBlocks`, the live ArrayList is mutated while being iterated. In 1.21.4, callbacks receive the detached copy, so such a new record would remain in the raw queue for a later finalization rather than extend the current pass. However, a full source-tree call-site search finds only the two `ServerGamePacketListenerImpl` calls in each 1.21.x release (vehicle accepted movement and accepted player movement packet); it finds no ordinary LocalPlayer block-callback call to this method. Therefore accept the lifetime fact, not an unconditional gameplay effect or crash claim for the ordinary LocalPlayer path.

4. **26.2 moves record production into ordinary Entity.move and stores request metadata.** `Entity.move` at `world/entity/Entity.java:739–755` computes resolved movement, applies the exact `movementLength > 1.0E-7 || delta.lengthSqr() - movementLength < 1.0E-7` gate, constructs one record `(pos, pos + movement, delta)` before `setPos`, and records nothing on the no-physics path. The gate retains zero displacement in the stated cases. This is one record per qualifying call, not one record per axis.

5. **26.2 finalization order, compaction and zero-length fallback are stated correctly.** `Entity.applyEffectsFromBlocks()` at `:903–918` clears the final list, copies the deque in iteration order, drains the raw queue, adds `(oldPosition, position)` if empty (including equality), otherwise appends a connector only above the stated squared-distance threshold, and dispatches the finalized list. `addMovementThisTick` at `:920–929` combines the first two when size is already at least 100, then appends the new record; the combined record has no axis metadata. `applyEffectsFromBlocksForLastMovements()` reuses the finalized list (`:916–918`).

6. **Axis order and its scope are represented accurately.** In 1.21.2/1.21.3, `collideWithShapes` at `Entity.java:981–1015` resolves Y first, then compares absolute X/Z and resolves one horizontal component before the other. 1.21.4 retains that sequence at `:993–1027`. This is collision-resolution order, not per-axis movement-record creation. In 26.2, `collideWithShapes` at `:1244–1260` iterates `Direction.axisStepOrder(movement)` and shifts the box by prior resolved components. `checkInsideBlocks` at `:1278–1305` interprets metadata only when present and the record's from-to delta is nonzero, using the request's axis order to form nonzero component segments; otherwise it checks one segment. Raw record order remains outermost. No trajectory delta follows from these source facts alone.

7. **The ordinary LocalPlayer path reaches the described finalizer after travel.** The audited call chain is `LocalPlayer.tick` → `LivingEntity.tick` → virtual `aiStep` → `travel` → `applyEffectsFromBlocks`, guarded by `!level().isClientSide() || isControlledByLocalInstance` in 1.21.2/1.21.3 and the corresponding local-authority condition in 1.21.4/26.2. Relevant report references are adequate: 1.21.x `LocalPlayer.java:189–192`, `LivingEntity.java:2418, 2457–2459, 2680, 2771–2776`; 1.21.4 `LocalPlayer.java:191–196`, `LivingEntity.java:2782–2787`; 26.2 `LocalPlayer.java:227–234`, `LivingEntity.java:2765, 2804–2806, 3043, 3140–3145`. This supports reachability, not runtime parity.

8. **Explicit 1.21.x record call sites do not establish ordinary client-tick production.** The source-wide search finds only the accepted-movement packet call sites in `ServerGamePacketListenerImpl.java:452,930` (1.21.2/1.21.3) and `:454,990` (1.21.4), with the first call pertaining to a vehicle. The report correctly separates those from LocalPlayer tick movement.

## Rejected or narrowed claims

- Reject any conclusion that 1.21.2 lacks mapping identity or that the comparison must wait for a ready marker: embedded source hashes and the hashed Mojang client mapping input are present and match.
- Do not turn the 1.21.2/1.21.3 live-list detail into a confirmed vanilla callback mutation, ConcurrentModificationException, or movement consequence. The report itself leaves callback behavior excluded; no ordinary-player source call to the recorder was found. A narrower conditional statement about what happens if a callback appends is source-supported.
- Keep “one accepted record per 26.2 move call” scoped to the quoted gate and normal-physics branch. A call rejected by that gate or the no-physics path records nothing.
- Keep 1.21.x collision-axis ordering separate from movement-record splitting. The former alone does not show that records split by axis.
- Treat all claimed version differences as endpoint comparisons. These sources do not establish the first patch release where a behavior changed.

## Remaining precise gates

- To accept a concrete callback-driven ordinary-player consequence, identify a reachable vanilla `stepOn` or `entityInside` path that calls `recordMovementThroughBlocks` or otherwise mutates `movementThisTick` during traversal; none appears in the checked source call sites. The 1.21.x packet-listener calls are outside ordinary LocalPlayer tick.
- The supplied report explicitly excludes piston origin/cleanup, contact visitation/budgets/fluid callbacks, and shared-query-box geometry. Those dependencies remain outside this bounded audit and must close elsewhere before any full-pair freeze.
- No intermediate releases were inspected here, so the 1.21.2/1.21.3 to 1.21.4 transition interval remains the only supported boundary statement.
- No trajectory prediction, implementation acceptance, or runtime parity was reviewed.
