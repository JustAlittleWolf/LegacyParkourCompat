# F-09-R1: Withdraw the unsupported trapdoor-signal movement topology

- Older version A: 1.19.4
- Newer version B: 1.20.1
- Mechanic / coverage slice IDs: D3, S5-02, S4-02
- Classification: changed behavior
- Confidence: candidate (the source branch differs, but no reachable B-only trapdoor signal topology is established)
- Applicability: unresolved
- First changed release: unknown within (1.19.4, 1.20.1]
- Runtime validation: not performed
- Disposition: withdraw the player-movement consequence from confirmed findings pending a reachable source trace.
- Supersedes: the concrete topology in original F-09; the original finding remains immutable. Its independent blind review is preserved at commit `88102131e2a6c50ea291b4a80c0475b517de069a`, path `workflows/source-campaign-2026-10-07/1.19.4--1.20.1/reviews/F-09-independent-blind-review-2026-10-10.md`, file blob `aac005af17f657ddf72f9377cf89a60f17694bf4` (verdict: revision-required).

## Paired evidence

The exact original artifact and manifest identities are `A-1.19.4-MOJMAP-ORIGINAL` and `B-1.20.1-MOJMAP-ORIGINAL` in the pair's `run.md`; paths below are relative to those immutable Mojmap source roots. The source publication paths, endpoint manifests, client inputs, mappings, and provenance are recorded there. All cited source files match the corresponding published source manifest.

### A artifact identity

- Evidence artifact record ID: `A-1.19.4-MOJMAP-ORIGINAL`
- Publication status: original-verified
- Revision ID: none
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap/`
- Evidence artifact SHA-256: source manifest `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.19.4/mojmap.sources.sha256` / `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`
- Original artifact-manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.19.4/artifacts.sha256` / `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`
- Original derived-artifact availability and SHA-256: `../../../build/movement-campaign-2026-10-07/artifacts/1.19.4/client-mojmap.jar` / `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`
- Source/raw-input hash relation and verification reference: exact client jar, official Mojang mappings and derived Mojmap tree are recorded in run.md; cited source files match the immutable source manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original-verified publication.
- Provenance limitations: evidence is bounded to endpoint 1.19.4 and makes no claim about earlier releases.
- Relative source paths; members; source hashes:
  - `net/minecraft/world/level/block/RedStoneWireBlock.java`, `getConnectingSide(...)` lines 244-260, `updateShape(...)` lines 184-199, `neighborChanged(...)` lines 374-383, `getSignal(...)` lines 391-406; SHA-256 `9cb901b000b60746263b50f3ec4c70218ee3546645d8959ddb4eff29aa224e77`.
  - `net/minecraft/world/level/block/TrapDoorBlock.java`, `use(...)` lines 92-105, `neighborChanged(...)` lines 113-128, `getShape(...)` lines 59-76; SHA-256 `fae6479ab458d35b7c5b7a6588c28f8a6f7a3569b8abab96179ae86ac37904c4`.
  - `net/minecraft/world/level/block/state/BlockBehaviour.java`, `getBlockSupportShape(...)` line 250 and `isFaceSturdy(...)` lines 758-763; SHA-256 `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d`.
  - `net/minecraft/world/level/block/SupportType.java`, `FULL` face test; SHA-256 `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6`.
  - `net/minecraft/world/level/block/Block.java`, `isFaceFull(...)`; SHA-256 `440b55e32f468572b4c078ab0ce5bb9b94f1c7abcaa03b03a9ce7faccb646857`.
  - `net/minecraft/world/phys/shapes/Shapes.java` and `SliceShape.java`, face extraction; SHA-256 `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` / `168cceaa0d1471f6998037792c6e2cce9f1b0a9b34e4266a971f4d1c8a3a0b6f` (both files byte-identical between endpoints).
  - `net/minecraft/world/level/Level.java`, `setBlock(...)` lines 204-267; SHA-256 `a6d0feba4b033e36dfa54214e1c9ca08a812f60efc97a5906f5e9cb13cd22dfe`.
  - `net/minecraft/server/level/ServerLevel.java`, `updateNeighborsAt(...)` and neighbor updater dispatch lines 962-978; SHA-256 `10651d46d2548f14ac0a0382b233311facbaa05790965d1e94019c0fe248fba0`.

### B artifact identity

- Evidence artifact record ID: `B-1.20.1-MOJMAP-ORIGINAL`
- Publication status: original-verified
- Revision ID: none
- Immutable evidence path: `../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap/`
- Evidence artifact SHA-256: source manifest `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`
- Evidence manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.20.1/mojmap.sources.sha256` / `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`
- Original artifact-manifest path / SHA-256: `../../../build/movement-campaign-2026-10-07/ready/1.20.1/artifacts.sha256` / `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`
- Original derived-artifact availability and SHA-256: `../../../build/movement-campaign-2026-10-07/artifacts/1.20.1/client-mojmap.jar` / `221d543b9f80ca42faaaa8e3f7cebc4fe1879b8f7ba954bb6ca62f8250be8b77`
- Source/raw-input hash relation and verification reference: exact client jar, official Mojang mappings and derived Mojmap tree are recorded in run.md; cited source files match the immutable source manifest.
- Revised-to-original derived-artifact equivalence and evidence reference: not applicable; original-verified publication.
- Provenance limitations: evidence is bounded to endpoint 1.20.1 and makes no claim about later releases.
- Relative source paths; members; source hashes:
  - `net/minecraft/world/level/block/RedStoneWireBlock.java`, `getConnectingSide(...)` lines 244-260, `updateShape(...)` lines 184-199, `neighborChanged(...)` lines 374-383, `getSignal(...)` lines 391-406; SHA-256 `db9be63a8fe2a90ef72c6321346224c4b564d9f7c445a008f04b84881c7910d3`.
  - `net/minecraft/world/level/block/TrapDoorBlock.java`, `use(...)` lines 92-104, `neighborChanged(...)` lines 113-127, `getShape(...)` lines 59-75; SHA-256 `2151e4f2c3ea35c3dceab7dcc78e53c0d7e0357f4fbd1197f54c6d772fd6cabb`.
  - `net/minecraft/world/level/block/state/BlockBehaviour.java`, `getBlockSupportShape(...)` line 250 and `isFaceSturdy(...)` lines 758-763; SHA-256 `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d`.
  - `net/minecraft/world/level/block/SupportType.java`, `FULL` face test; SHA-256 `2604faefc3d4732eafe16363f4e9db68f7a3772e81e062e7ef4441a54709f5c6`.
  - `net/minecraft/world/level/block/Block.java`, `isFaceFull(...)`; SHA-256 `0c2df3d958078929c4cc2985e0d6a6a766cbbbb02066e6c9ac471981aa858656`.
  - `net/minecraft/world/phys/shapes/Shapes.java` and `SliceShape.java`, face extraction; SHA-256 `ec207e73b8e9de4b1234132f81126973f0cb2bb37646b070d273259f2e91311a` / `168cceaa0d1471f6998037792c6e2cce9f1b0a9b34e4266a971f4d1c8a3a0b6f` (both files byte-identical between endpoints).
  - `net/minecraft/world/level/redstone/SignalGetter.java`, `getSignal(...)` / `hasNeighborSignal(...)` lines 65-83; SHA-256 `387c52b2812332787fbf16c4cca41f120d1afcd9d3130cffd9736689e0eeb86b`.
  - `net/minecraft/world/level/Level.java`, `setBlock(...)` lines 202-257; SHA-256 `c13ead6630a8f77529574afcd6009b0395698489ff8ad6fdf75b0e745c65f61c`.
  - `net/minecraft/server/level/ServerLevel.java`, `updateNeighborsAt(...)` and neighbor updater dispatch lines 976-992; SHA-256 `e1b59acb9d7d52259123dc8244480f8ce66e91e868d3510faefa95ced721a855`.

## Source-level difference

B's `RedStoneWireBlock#getConnectingSide` adds an adjacent `TrapDoorBlock` exception before the existing `canSurviveOn` check. This source branch change is real. The old F-09 example does not distinguish A and B: the closed bottom trapdoor's upward face covers the full 16×16 footprint, so A's `canSurviveOn` check succeeds too. Both versions can take the upper-neighbor branch when a redstone wire is directly above that closed trapdoor.

A candidate replacement requires an **open** adjacent trapdoor and a redstone-wire state directly above it. The directional connection helper's `shouldConnectTo(BlockState)` overload accepts that redstone wire; it does not accept an arbitrary connectable source in this call. The trapdoor's open shape lacks a full upward support face, so A fails `canSurviveOn` while B's class exception can pass it. However, the candidate does not establish that this unsupported upper wire remains through the state and neighbor callbacks needed for a trapdoor signal difference. `TrapDoorBlock#use` writes the manually toggled state with flag 2; `Level#setBlock` still performs neighbor shape updates, and `RedStoneWireBlock#updateShape` returns the existing state for `Direction.DOWN`. That local shape response alone does not close persistence: `RedStoneWireBlock#neighborChanged` separately checks `canSurvive` and removes wire that is no longer supported. Redstone power changes call `updateNeighborsAt` for the wire and neighboring positions, and `ServerLevel#updateNeighborsAt` fans those notifications to their neighbors, including the trapdoor's unsupported upper wire. The callback ordering and wire lifetime through that fan-out have not been proven for a topology that reaches the changed signal query. Therefore the source snapshot does not establish a replacement A/B signal outcome.

`RedStoneWireBlock#getSignal` itself is unchanged and recomputes connection state for directional output. A's `Level` and B's `SignalGetter` implement the same six-direction `hasNeighborSignal` check; the consumer is not the changed operation. Trapdoor placement and `neighborChanged` read that query, and the fixed-state `TrapDoorBlock#getShape` geometry is unchanged at both endpoints.

## Reachability and dependencies

The bounded player route remains the ordinary state-shape consumer: `Entity#move` -> `collide` -> `BlockCollisions#computeNext` -> current block state's collision shape. But no source-closed producer-to-consumer sequence proves that this redstone-wire branch creates a different reachable trapdoor state in A and B. The candidate's support face, required upper-wire state, trapdoor writers, signal query, wire survival callback, power-change notification fan-out, and collision consumer are identified; the upper-wire lifetime and callback order across the fan-out remain open dependencies. Do not use this candidate as an implementation handoff or as evidence of an eligible historical shape delta.

## Consequence and uncertainty

The source proves a changed connection predicate and proves that a closed bottom trapdoor passes A's support-face test. It does not prove a reachable A/B difference in trapdoor `OPEN` state, player collision shape, or movement. Redstone/block-state lifecycle production remains excluded from movement emulation, and fixed-state trapdoor geometry is unchanged. Runtime validation was not performed. First changed release remains unknown within `(1.19.4, 1.20.1]`.

## Handoff

F-09-R1 withdraws the unsupported player-movement consequence from original F-09 while preserving the real B source branch as an open candidate. The original finding remains at its original path and blob for auditability. Prior independent blind review: commit `88102131e2a6c50ea291b4a80c0475b517de069a`, file blob `aac005af17f657ddf72f9377cf89a60f17694bf4`, verdict `revision-required`, bound to original F-09 blob `06b71e080a2b28db9ef83badd4a498eaa672e4c3`. This withdrawal snapshot needs a fresh independent blind source review; the reviewer should validate the corrected support-face analysis and the open upper-wire persistence/callback-order blocker. No implementation, wiki-audit material, or runtime testing was consulted or performed.
