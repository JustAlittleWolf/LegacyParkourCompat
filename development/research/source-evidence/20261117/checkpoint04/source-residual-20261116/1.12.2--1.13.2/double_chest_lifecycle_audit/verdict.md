# Independent source audit verdict: double-chest lifecycle

**Verdict: ACCEPT, with one route correction to the report.** The lifecycle conclusion is supported for legal 1.12.2 adjacent same-kind chest pairs migrated and delivered by 1.13.2. The report slightly overstates the direct world-chunk loading route for old chunks; this correction does not change the conclusion.

## Immutable inputs and hashes

- Audited report: `build/source-residual-20261116/1.12.2--1.13.2/double_chest_lifecycle/report.md`, SHA-256 `8f4ac240ca5f5c07fba5822a797404ed555294efafdecb1273da3c17169d84b2` (matches assignment).
- 1.12.2 Feather Gen2 source manifest SHA-256: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` (matches assignment).
- 1.13.2 Feather Gen2 source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` (matches assignment).
- All 21 source-file hashes listed in the report were independently recomputed and matched both the files and their manifest entries.
- Prior provider coverage SHA-256: `3ae1bc28a668f58f06a2ecb4338b93c35334853b956bcf1b861c9560123c8d52` (matches report).
- Additional status-migration evidence: 1.13.2 `net/minecraft/unmapped/C_51231156.java`, manifest and file SHA-256 `d1a6fb5940c85b9c5bac45176a94fcb50781a02043f37aa41a438169f022cf0e`.

## Audited gates

- `C_36170522.makeRule` routes chunk rewrites through `m_10744737` (lines 414–418; 407–412). During legacy palette traversal it checks legacy IDs 54 and 146 (lines 467–468), records interior positions in the section index and boundary side bits (621–627), and writes those into `UpgradeData.Indices` and `UpgradeData.Sides` (953–966). `C_80754635` maps both chest kinds' legacy facing states to `type:'single'` (chest lines 1604–1607; trapped chest 6033–6042).
- `UpgradeData.upgrade` runs `upgradeInside` then all eight side passes (66–71). `upgradeInside` visits the recorded indices and only processes neighbors remaining within that chunk (114–145). `upgradeSides` consumes marked side bits and scans the relevant boundary area against world neighbors (77–112). The registered chest fixer handles both `Blocks.CHEST` and `Blocks.TRAPPED_CHEST`; it requires identical block instances, horizontal adjacency, both states `SINGLE`, equal facing, and a perpendicular adjacency axis, then writes the complementary type to the neighbor and current state (242–265). These gates are consistent with legal old double-chest halves.
- `WorldChunk.doPostProcessing` requires `neighbors == 8` (1062–1064), does its queued postprocessing, sets `POST_PROCESSED`, then synchronously calls `upgradeData.upgrade(this)` (1091–1094). `addToCache` schedules that method on the world's event loop when the neighborhood is complete (1148–1155); `ChunkMap.put` maintains the 3×3 neighbor counts (17–29).
- `WorldChunk.isPopulated` is exactly the `POST_PROCESSED` status gate (804–805). `ChunkHolder.populate` checks that gate before setting its populated flag and constructing/sending the full chunk packet (84–107); later `sendChunk` also checks the holder's populated flag (112–116). Thus the normal chunk delivery path cannot transmit a `SINGLE` intermediate state created by the data fixer before this postprocessing task has completed. The `POST_PROCESSED` assignment occurs earlier than `upgrade`, but both occur in the same synchronous event-loop task, and no packet is sent from inside that method.

## Correction to report wording

Report section 1 says that after `upgradeChunkNbt` fixes a disk chunk, it then creates a `WorldChunk` from the fixed NBT. That is not the direct route for a legacy 1.12.2 chunk. The 1.13.2 `ChunkToProtoChunkFix` in `C_51231156` writes status `mobs_spawned` or `empty` from `TerrainPopulated` / `LightPopulated` (lines 34–50). `ChunkStatus` classifies those as `PROTO_CHUNK`; `AnvilChunkStorage.loadChunk` rejects non-world chunks (lines 163–169), and `loadProtoChunk` instead uses `createProtoChunkFromNbt` (201–214). `WorldChunk(World, ProtoChunk, ...)` carries the proto chunk's `UpgradeData` and section contents forward (WorldChunk lines 127–146), then assigns `FULL_CHUNK` (157–159). It later reaches the 8-neighbor postprocessing gate described above. Therefore the report should distinguish direct world-chunk loading for already-world-status chunks from this legacy proto-chunk migration path.

The report's conclusion that legal migrated old pairs are repaired before the server's full chunk packet reaches the logical client is accepted after that route qualification. This verdict does not extend to malformed or manually authored inconsistent modern states.
