# F-COLLISION-REPRESENTATION — 1.13.2 changes player collision resolution representation

- Older version A: 1.12.2
- Newer version B: 1.13.2
- Mechanic / coverage slice IDs: ENTITY-MOVE-AXES, WORLD-COLLISIONS.
- Classification: changed behavior.
- Applicability: unresolved until a reachable old-block case is proven.
- Confidence: candidate (full-pair dependencies and independent review are open).
- First changed release: unknown within (1.12.2, 1.13.2].
- Runtime validation: not performed.
- Artifact manifests: A original artifact-manifest SHA-256 8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c; B fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e. Source trees were rehashed against their ready manifests. Revised snapshot revision feather-r1-2026-10-07: A fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87; B b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c. Original mapped jars unavailable; snapshots not proven identical or metadata-only.
- Evidence artifact records: `EA-FEATHER-R1-A` and `EA-FEATHER-R1-B` (see `run.md`, Artifact evidence identities).

## Paired evidence

- A evidence: `ready/1.12.2/ornithe-feather/net/minecraft/entity/Entity.java`, move(MoverType,DDD)V lines 462-796; `World.java` collision collector lines 964-1063; Entity SHA-256 `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; World SHA-256 `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`.
- B evidence: corresponding Entity.move lines 473-782; `WorldView.getBlockCollisions` / `getCollisions` lines 112-178; `EntityView.getEntityCollisions` lines 21-36; and `World.getCollisions` lines 1755-1758. Entity SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; World SHA-256 `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a`; WorldView SHA-256 `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`; EntityView SHA-256 `2b5b59eef6efa93bb51c1d838c7a1cf64725be96c55c47f76894fde11e13d6de`.
## Source-level difference

- Difference: A collects translated Box candidates and clips each axis against the list; B collects VoxelShapes and uses VoxelShapes.calculateMaxOffset for axis clipping. Both use Y, X, then Z axis resolution and step alternatives, but candidate representation/helper semantics differ.
- Collector boundary: on the reachable movement path, A scans block positions and calls `BlockState.addCollisions` against the requested Box, then appends intersecting entity and entity-against-player Boxes except same-vehicle entities. B's displacement overload builds a swept VoxelShape query with `1.0E-7` offsets and subtracts the initial shape; `WorldView` gathers block shapes and merges out-of-border cells into a boundary shape, while `EntityView` appends entity shapes after exclusion-set and same-vehicle checks. Exact old-block consequences remain unresolved until paired shape providers and VoxelShape offset semantics are closed.
## Reachability and dependencies

- Reachability and dependencies: Entity.move is the player collision path for self/player moves. Sneak edge probes, step height, support lookup, border and block callbacks are within the caller's reachable path.
## Consequence and uncertainty

- Consequence and uncertainty: representation change alone does not prove a movement outcome differs. Every old block's effective collision shape, registration/defaults, neighbors, WorldView collision stream, entity exclusions, border and voxel offset/tie behavior must be compared with concrete reachable cases before this becomes a behavioral finding.

- Revised-artifact provenance: revision ID `feather-r1-2026-10-07`. A immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.12.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`; A `revision.json` SHA-256 `2be8645d57ca5c00411b037e7617f9860ac700f7fa8ef50d28220c2e1b0c60dc`; A source manifest `ready/1.12.2/ornithe-feather.sources.sha256` SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; A raw artifact manifest `ready/1.12.2/artifacts.sha256` SHA-256 `8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`.
- B immutable JAR `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.13.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `b28c33e023928045c8fd7ed7727a860a6820241e371ddb16108366c6192b718c`; B `revision.json` SHA-256 `a4dfb51e36d4e52e1fc26d96647942e3dfcc2302bb4568a0a50dd49acf91b8f5`; B source manifest `ready/1.13.2/ornithe-feather.sources.sha256` SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; B raw artifact manifest `ready/1.13.2/artifacts.sha256` SHA-256 `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`.
- Both revision records state `originalDerivedArtifactAvailable=false`, source trees and raw inputs identical, and original-derived-artifact equivalence unproven. This worker verified the snapshots and manifests; independent ops audit passed on 2026-10-07.
## Handoff
