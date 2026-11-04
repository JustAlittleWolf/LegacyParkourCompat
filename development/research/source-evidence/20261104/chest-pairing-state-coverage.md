# Chest pairing state producers and persistence (1.12.2 → 1.13.2)

## Scope and accepted input

This is a source-only supplement for chest pairing state production and persistence. It does not re-audit collision geometry or player trajectory. Accepted provider context: `build/source-drafts/residual-common-collision-a.md`, SHA-256 `4f822062f3e07e4d79e5a5fb976ccd1573d3036ee3f734504d6389845770a668` (E1 A). That review reports old `ChestBlock.getShape` reading live neighboring block identities, while new shape selection consumes stored `ChestType`; it explicitly leaves valid, updated pairing state separate from stale/malformed or multiply-adjacent arrangements. This note checks only how placement, neighbor updates, and saves create or preserve that state.

## Exact source identities

Both endpoints are local Ornithe Feather Gen2 build.2 decompilations; each listed source hash was checked against the endpoint source manifest.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.12.2 | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13.2 | `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |

| Version | Relevant source file | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/block/ChestBlock.java` | `cc63486b970a8a7670910b9d4e24c8de4b616dc53c9ce2f01260cfad53a5e178` |
| 1.12.2 | `net/minecraft/block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12.2 | `net/minecraft/block/entity/ChestBlockEntity.java` | `22a516dea6302bccb8cdf6487cb44875aacef2af1f4a0254735d80c28a87be7c` |
| 1.12.2 | `net/minecraft/world/World.java` | `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594` |
| 1.12.2 | `net/minecraft/world/chunk/WorldChunk.java` | `b80c4962661b31c9015faa9bb3d42e390528b59473956ba20e424c76625b844f` |
| 1.12.2 | `net/minecraft/world/chunk/PalettedContainer.java` | `c0d1f6ab76522018d3b4544104a16f11c76678c0f448d772cc8d163516fb33e2` |
| 1.12.2 | `net/minecraft/world/chunk/storage/AnvilChunkStorage.java` | `057c620b6156a3c860065122c1cef42e44917422662599cc18000efdfea98c60` |
| 1.13.2 | `net/minecraft/block/ChestBlock.java` | `483ed7dc673f8acb33fbac6d5d883c652db05de2314931c522e0bee482792541` |
| 1.13.2 | `net/minecraft/block/TrappedChestBlock.java` | `ce2db33ed4b4e5dec67d757ad0211b78c9f92d406bee833485961a3a3ff611e8` |
| 1.13.2 | `net/minecraft/block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.2 | `net/minecraft/block/entity/ChestBlockEntity.java` | `5dd060e056a73ef07e8d9d3696af78425687876a8eacf1862bb3c79330fa0d8f` |
| 1.13.2 | `net/minecraft/world/World.java` | `0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunk.java` | `d800fac11aa2672a4dd69cb5a599e5f84bb6e2c71cfc8080fa972acdabfb4914` |
| 1.13.2 | `net/minecraft/nbt/NbtUtils.java` | `a228be0c95de96bdb29a19b3f2ee9cd925233bae395fc0a3a0c71e8f123f9110` |
| 1.13.2 | `net/minecraft/world/chunk/WorldChunkSection.java` | `df960f6239f7413dea7882775b9693a37c5f3650eabd0e5bf70f1504a0094e7e` |
| 1.13.2 | `net/minecraft/world/chunk/storage/AnvilChunkStorage.java` | `9037625793eb4b046d4c80ebf352d73a0b750f969dfaa1fc2a53b35467dc5031` |

## Placement and pairing-state producers

### 1.12.2

- Registration creates separate `ChestBlock` instances for `minecraft:chest` (`Block.java:865`, `Type.BASIC`) and `minecraft:trapped_chest` (`:1086`, `Type.TRAP`). Each instance has a single blockstate property, `FACING` (`ChestBlock.java:31–43, 469–471`); there is no persistent left/right/single property.
- `getPlacementState` initially sets facing from `entity.getHorizontalFacing()` (`ChestBlock.java:94–96`). `onPlaced` then recomputes facing from quantized entity yaw (`:99–103`). It inspects the four horizontal cells for the same block instance and, in its axis-specific branches, writes the facing state to the new chest and a selected adjacent chest (`:104–132`). The ordinary placeability gate rejects a placement if any adjacent same-instance chest is already paired and returns false once more than one same-instance neighbor is found (`:247–286`).
- `onAdded` invokes `updateState` for the new block and then each horizontal same-instance neighbor (`:81–88`). `updateState` is server-only and recomputes `FACING` from same-instance chest neighbors plus opaque neighboring cells, then writes the updated state with flags `3` (`:140–204`). Thus ordinary pairing is inferred from neighboring block identities and facing adjustments; pair identity is not stored as a blockstate value.
- Neighbor notifications reach `ChestBlock.neighborChanged` through `World.updateNeighbors` (`World.java:351–406`). Chest's override delegates to the base and clears the `ChestBlockEntity` block cache when present (`ChestBlock.java:303–310`); it does not write chest blockstate. The old cached `doubleChest`/neighbor references are derived by `ChestBlockEntity.updateShape` (`ChestBlockEntity.java:104–155`, called from `:173` and `:285`), not persisted fields.

### 1.13.2

- `minecraft:chest` registers a `ChestBlock`; `minecraft:trapped_chest` registers a `TrappedChestBlock` subclass (`Block.java:994,1287`; `TrappedChestBlock.java:15–17`). The latter does not override pairing-state methods, so both use the inherited `ChestBlock` logic.
- The state definition contains `FACING`, `TYPE`, and `WATERLOGGED`; default `TYPE` is `SINGLE` (`ChestBlock.java:42–53,313–315`). `getPlacementState` derives facing from `BlockPlaceContext`, checks sneaking and the clicked face, and checks adjacent same-block `SINGLE` states with matching facing to choose `LEFT` or `RIGHT`; it returns all three properties together (`:117–140,177–180`). `onPlaced` only transfers a custom name to the block entity (`:183–191`), with no later pair-state write there.
- On neighboring-state changes, `updateShape` first schedules a water tick when `WATERLOGGED`. For a horizontal neighbor of the same block instance, it changes a current `SINGLE` to the opposite of the neighbor's non-single `TYPE` only when both facings match and `getConnectedDirection(neighborState)` points back toward this cell (`:72–90,111–114`). When the neighbor is not the same block instance (or the direction is vertical), it resets to `SINGLE` if the changed direction equals this state's connected direction. A same-instance horizontal neighbor that fails the pairing guard does not enter that reset branch. This source fact leaves malformed/stale arrangements outside a general paired-state invariant.
- When flag bit 16 is clear, `World.setBlockState` triggers neighbor-shape updates (`World.java:201–248`); `Block.updateNeighborShapes` passes the changed state and direction to adjacent states' `updateShape` and writes any returned replacement (`Block.java:166–202`). Ordinary `neighborChanged` notifications are a separate path (`World.java:317–356`) and are not the chest pairing-state producer described above.

## Explicit state persistence

- **1.12.2 blockstate:** `ChestBlock.getMetadataFromState` serializes `FACING` as the direction ID; `getStateFromMetadata` reconstructs it and maps vertical IDs to north (`ChestBlock.java:444–457`). `Block.serialize` combines registry ID and metadata (`Block.java:79–91`). Chunk palette IDs are written/read through the legacy `Blocks`, `Data`, and optional `Add` arrays (`PalettedContainer.java:95–128`; `AnvilChunkStorage.java:228–244, 310–320`). Consequently chest pairing is not separately stored in the blockstate; only facing survives this representation.
- **1.13.2 blockstate:** the chest state definition includes `FACING`, `TYPE`, and `WATERLOGGED` (`ChestBlock.java:313–315`). Chunk `PalettedContainer` is wired to `NbtUtils.readBlockState`/`writeBlockState` (`WorldChunkSection.java:20–25`); `NbtUtils` writes the registry `Name` and every state property and reads recognized properties back (`NbtUtils.java:191–241`). `AnvilChunkStorage` persists each section's `Palette` and `BlockStates` and reloads them (`:649–685`). `TYPE` therefore is explicitly persistent as a blockstate property in this endpoint.
- In both versions, `ChestBlockEntity.readNbt`/`writeNbt` persist inventory or loot-table data and custom name, not chest pairing type (`1.12.2 ChestBlockEntity.java:72–98`; `1.13.2 ChestBlockEntity.java:66–91`). Old `doubleChest` and neighbor references are transient cache fields, not written by these methods. New pairing lives in the blockstate, not in chest block-entity NBT. This describes each version's own save encoding; no old-world-to-new-world migration or datafix behavior was audited here.

## Boundary and open points

- Covered: ordinary/trapped chest registration relation; placement-produced `FACING`/`TYPE`; the old `onAdded`/neighbor-notification paths; the new `updateShape` neighbor path; and blockstate versus block-entity persistence.
- Not covered: collision geometry, player movement/trajectory, chest interaction/menu eligibility, chunk-load re-pairing beyond the explicit save encoding, structure placement/mirroring, datafix migration between versions, or arbitrary command-modified malformed states.
- The accepted E1 A collision review remains unchanged. This supplement supports only a source-level account of pairing state production and storage; it does not establish collision-output equivalence for every reachable or malformed arrangement.


