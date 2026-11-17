# Independent source audit: stair faces and pane/barrier

Verdict: **ACCEPT** for the bounded source-facts packet.

## Bound input

- Packet: `build/mcpk-20261116/pipeline-early-rules/stair-barrier-source/source-facts.md`
- Declared author SHA-256: `8ebc5dbfc6608f93bd8e7df1001473f5304275c464b770f4d2f91f4da5c1e5ca`
- Independently computed packet SHA-256: `8ebc5dbfc6608f93bd8e7df1001473f5304275c464b770f4d2f91f4da5c1e5ca` (match)

## Provenance and file integrity

Reviewed only the named packet and exact decompiled sources, manifests, ready markers and provenance for 1.11.2, 1.12, 1.12.1 and 1.12.2 in `ornithe-feather`.

- 1.11.2 and 1.12.2 ready markers identify the requested release/mapping and `status=ready`; both marker-declared source-manifest SHA-256 values match the manifest bytes. Their provenance identities and mapping artifact names match the packet. All seven listed source files per release (WallBlock, PaneBlock, StairsBlock, BarrierBlock, Block, World, Entity) match both packet hashes and source-manifest entries.
- 1.12 and 1.12.1 provenance identifies the precise Feather Gen2 build.2 mergedv2 mapping JAR, metadata, client inputs and local source hashes. The packet's five block-source hashes per release match provenance; World and Entity hashes match the packet. The five block files are byte-identical across 1.12, 1.12.1 and 1.12.2, as claimed.
- All 28 packet-listed source hashes across the four releases were recomputed and matched. The corresponding source-manifest/provenance hashes and exact release/mapping identities were checked. No source-generation or mutation was performed.

## Claim audit

- **1.11.2 stair faces:** `StairsBlock.isCube` returns false. Wall connection requires the relevant non-exception predicate and otherwise solid-blocking material plus cube state; pane connection accepts cube state or its explicit cases. Thus stairs do not provide wall/pane arms under these predicates.
- **1.12 family stair faces:** Wall and pane resolve each directional property from the neighbor's face shape, passing the face toward the wall/pane. Wall accepts unexcluded `SOLID` and qualifying pole faces; pane accepts unexcluded `SOLID` or `MIDDLE_POLE_THIN`. Stairs' face-shape method returns `SOLID` on the queried horizontal face for a straight stair exactly when `FACING` equals that face; the inner variants have their stated directional cases and outer variants return `UNDEFINED`. Stairs are absent from the wall attachment-exception helper. The packet's straight-stair selector and directional explanation are accurate.
- **Collision effect:** Wall and pane `addCollisions` resolve virtual properties when `forceShape` is false, then select wall collision shape / pane center-plus-enabled-arm boxes from those properties. `World.getCollisions` invokes block-state collision collection with `false`; `Entity.move` uses this list. This is a reachable ordinary entity/player movement collision path. The claim concerns collision geometry, not only render/outline geometry.
- **Pane/barrier:** In 1.11.2 barrier inherits `Block.isCube == true`; pane resolves adjacency through `shouldConnectTo`, so a barrier sets its direction true and adds that arm to collision. In 1.12, pane's predicate explicitly recognizes `Blocks.BARRIER`; the `SOLID` branch is excluded for it, and the separate thin-pole branch does not apply. The barrier inherits `Block.getFaceShape == SOLID`. Thus the pane arm is absent toward barrier from 1.12 through 1.12.2.
- **First checked release:** The checked endpoints support the packet's bounded statement that these behaviors differ between 1.11.2 and 1.12 and persist in 1.12.1/1.12.2. “First checked” is appropriately not an assertion about snapshots, unexamined patch releases or an exact historical introduction date.

## Limits

This accepts the packet's source facts only for the stated operations and four exact releases. It does not establish whole-pair inventory/coverage, acceptance of unrelated wall/fence/gate behavior, implementation correctness, trajectory magnitude, runtime parity, or any exact boundary outside the checked releases. No product code, findings/backlog, other source lanes, Git history, tests, builds or runtime behavior were reviewed.
