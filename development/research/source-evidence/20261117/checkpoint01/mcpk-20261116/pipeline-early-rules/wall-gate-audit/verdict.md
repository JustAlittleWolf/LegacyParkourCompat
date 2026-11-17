# Independent source audit: wall perpendicular fence-gate connection

**Verdict: ACCEPT**

Audited candidate bytes: `f5593511ef9f5a859aac97a0192b6564d5407261cca98d574ec80f7d87815730` (SHA-256), at `build/mcpk-20261116/pipeline-early-rules/wall-gate-author/wall-perpendicular-fence-gate-connection.md`.

## Findings

- The supplied 1.12 wiki sentence states that walls and fences no longer connect to perpendicular fence gates. This candidate deliberately narrows its finding to walls.
- In exact 1.11.2 source, `WallBlock.shouldConnectTo(WorldView, BlockPos)` rejects barriers and then accepts this wall or any `FenceGateBlock`, without consulting gate facing or the adjacent side. Its four connection checks inspect north, east, south, and west.
- In exact 1.12, 1.12.1, and 1.12.2 source, `WallBlock.shouldConnectTo(WorldView, BlockPos, Direction)` obtains the neighboring state’s face shape. It accepts `MIDDLE_POLE_THICK`, or `MIDDLE_POLE` only for a `FenceGateBlock`; the regular `SOLID` face path is separately gated by `isExceptionForConnection`. `resolveVirtualProperties` supplies the wall-facing face for each adjacent position. `FenceGateBlock.getFaceShape` returns `MIDDLE_POLE` only for horizontal faces matching the gate facing axis test, and `UNDEFINED` for vertical or other horizontal faces. Thus the candidate’s directional explanation is supported; the method does not read `OPEN`.
- Wall collision generation resolves the virtual connection properties before selecting the collision shape when `forceShape` is false. `Entity.move` requests expanded-shape world collisions, and the world scan delegates to each encountered block state’s `addCollisions`; the `PlayerEntity` → `LivingEntity` → `Entity` inheritance path supports the stated player movement consumer.
- The relevant wall and gate implementations are byte-identical in 1.12, 1.12.1, and 1.12.2. The sources support 1.12 as the first observed cutover relative to 1.11.2, and support the behavior across all three inspected 1.12.x releases. They do not establish behavior before 1.11.2; the candidate explicitly limits its conclusion accordingly.

## Integrity checks

Recomputed SHA-256 values for every source file listed in the candidate table match the candidate’s values and the corresponding exact-version source identity entry. For 1.11.2 and 1.12.2, the entries were checked in `ornithe-feather.sources.sha256`; for 1.12 and 1.12.1, they were checked in the mapping-local `.decompile-provenance.json` `sources` map. All six cited source files per release matched. The provenance descriptor and separate source-list identity hashes also match the values recorded in the candidate:

- 1.11.2 provenance `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; source list `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`.
- 1.12 provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`.
- 1.12.1 provenance `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`.
- 1.12.2 provenance `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; source list `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.

No blocking factual or source-integrity issue was found. This verdict audits the immutable candidate only; it does not modify or move the candidate into its canonical intended path.