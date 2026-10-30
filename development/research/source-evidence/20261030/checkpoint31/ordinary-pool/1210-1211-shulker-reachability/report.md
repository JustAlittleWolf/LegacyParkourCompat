# Shulker block-entity client reachability: 1.21.0 → 1.21.1

## Bounded verdict

For equal vanilla-received block states, neither release can leave a stale or mismatched `ShulkerBoxBlockEntity` in the client chunk through chunk load/replacement, block-state updates, block-entity updates, or chunk unload/reload. The 1.21.1 `LevelChunk#setBlockState` validity guard closes a broader stale-entity case, but it does not change a reachable Shulker case: Shulker block-entity validity is a predicate of the block identity, all Shulker variants share `BlockEntityType.SHULKER_BOX`, and property updates preserve the block identity. This is a source-derived reachability conclusion, not a runtime parity claim.

Malformed/custom packet contents and externally modified client state are outside that vanilla reachability conclusion. Chunk block-entity tags that do not match the block entity constructed from the received block state are ignored by the client application path; they do not install a mismatched Shulker entity.

## Exact-source basis

Canonical read-only Mojmap root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. Version directory `1.21` is the 1.21.0 source tree; `1.21.1` is the 1.21.1 tree. The preceding audit pins both source trees and their manifests. SHA-256 values below were recomputed from the cited files.

| Source path relative to `mojmap/` | 1.21.0 SHA-256 | 1.21.1 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` | same |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `cefc6ffb53ad508052a7a0a96e21ecfd6d2cf471d920c7e91f1911c61fb911e6` | same |
| `net/minecraft/client/multiplayer/ClientChunkCache.java` | `89abca5266fcf654a4cc54bbc21dbcb14a92e31e0aeff62febe68d72e6517d00` | same |
| `net/minecraft/world/level/chunk/LevelChunk.java` | `4912413546274e0eb231bc36e4537a4ce243b41b46c83c89bb64dd59cad7072d` | `435db581b3d397edd88dd19f051f7f6adb8fcf8acacdc42680339815f5f05591` |
| `net/minecraft/world/level/block/entity/BlockEntity.java` | `579463eb222b03d3fb89aacfa49a0d1963df3f47fe5f6bcdbc61c208b3f5207d` | `b16e3efc8f959372433f17f71bd7836f3d75f6f9c7e608d7184ab779d6370f0b` |
| `net/minecraft/world/level/block/entity/BlockEntityType.java` | `9432c68f12bd79761167129ad5def3cafc9516513a38f4beaa9d992ff6d6cfe1` | same |
| `net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java` | `4779b4301b2cdd90ac39e9abb2e4f5e47b07869f592028e1f085ad2be7c697b8` | same |
| `net/minecraft/world/level/block/ShulkerBoxBlock.java` | `3b782565707ee431aa2843c772d494147cc37fde61eeca50902aa710dbb8c227` | `c7921f13e26d3ce152f551d2e50e6e35d9cf8dc900503da4fb7730a85a717489` |
| `net/minecraft/world/level/block/BaseEntityBlock.java` | `c20747afb018dce428377966d7feaff9f919ea39f8ddf5166ecaa1e0f3e7788f` | same |
| `net/minecraft/world/level/Level.java` | `f698b412b7e849f5076ae268f7819247b623afc905b8d59de9eb60b414e11e96` | same |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `35420ee4fca120ccf1d1542a6b1746fcfdd1e9dafd125cf0b8193f9b8b406b292` | same |
| `net/minecraft/network/protocol/game/ClientboundLevelChunkPacketData.java` | `e0f1ab3bdfa53c5732140a6cdea90010267bd00b2436f903527de3899ee743e8` | same |
| `net/minecraft/network/protocol/game/ClientboundBlockEntityDataPacket.java` | `932ffccac5f8b7c9cab06a1096c9531598c2c9c8b2f9c049933aeb18abdfeff7` | same |

## Reachability trace

### Chunk load and replacement

`ClientPacketListener.handleLevelChunkWithLight` applies chunk packets through `updateLevelChunk` (1.21.0/1.21.1 `ClientPacketListener.java:700–712,738–740`). `ClientChunkCache.replaceWithPacketData` either creates a fresh `LevelChunk` or replaces packet data in the existing client chunk (`ClientChunkCache.java:98–118`). The client `LevelChunk(Level, ChunkPos)` constructor initializes an empty chunk rather than copying server pending block-entity NBT (`LevelChunk.java:86–114`; the server-only transfer constructor is at 116–134).

`LevelChunk.replaceWithPacketData` first marks all current block entities removed and clears their map and tickers, then reads section block states, then applies block-entity tags (`LevelChunk.java:479–499,604–609`). For each tag, the consumer asks for `getBlockEntity(pos, IMMEDIATE)`, which constructs from the *received block state*; it loads the tag only when the constructed entity's type equals the tag type (`LevelChunk.java:323–347,479–499`). Packet data supplies a position, registered entity type and nullable tag (`ClientboundLevelChunkPacketData.java:94–108,119–143`); a tag of the wrong entity type cannot replace the state-derived entity. This gates the malformed-chunk-data possibility from creating a Shulker BE against a different block type.

### Incremental block and block-entity updates

Single and section block updates both call `ClientLevel.setServerVerifiedBlockState` (`ClientPacketListener.java:694–697,781–784`). It applies the received state through `Level.setBlock` unless a prediction records the server state (`ClientLevel.java:123–127`; `Level.java:211–237`). In `LevelChunk.setBlockState`, the client path removes the current BE when the old block state has an entity and the block identity changes (`LevelChunk.java:273–281`). If the new state has a BE, it reuses the same-block entity after updating its stored block state or creates the new block's entity (`LevelChunk.java:288–304`). In 1.21.1, a reused BE that is invalid for the new state is removed and reconstructed first (`LevelChunk.java:288–303`); this five-line check is absent in 1.21.0. `BlockEntityType.isValid` is exactly `validBlocks.contains(state.getBlock())` in both releases (`BlockEntityType.java:300–302`), so a property-only state change on the same block cannot turn a valid Shulker BE invalid. Each colored Shulker block creates `ShulkerBoxBlockEntity` with the shared `BlockEntityType.SHULKER_BOX` (`ShulkerBoxBlock.java:86–94`; type's allowed block set starts at `BlockEntityType.java:184–191`).

Block-entity data packets do not create or replace entities: the client looks up an entity at the position using the packet's declared type, and loads nonempty data only if that typed lookup succeeds (`ClientPacketListener.java:1270–1282`; typed lookup contract is `BlockEntityType.java:310–312`). `loadWithComponents` loads entity-specific data and does not replace the entity's block state (`BlockEntity.java:73–78` and Shulker's `loadAdditional`, `ShulkerBoxBlockEntity.java:198–202`).

Block events are also routed through the current block state (`ClientPacketListener.java:1309–1312`; `Level.blockEvent`, `Level.java:782–783`). `BaseEntityBlock.triggerEvent` invokes the BE found at that position (`BaseEntityBlock.java:27–32`). This path can update a Shulker animation when the current state and stored Shulker BE are consistent; it does not construct or install a stale entity. On the reachable Shulker state, `ShulkerBoxBlockEntity.triggerEvent` changes only open-count/animation status (`ShulkerBoxBlockEntity.java:137–152`).

### Removal, unload and geometry consumer

The Shulker block's changed 1.21.1 `onRemove` ordering is server-only in the state-write path: `LevelChunk.setBlockState` calls `onRemove` only under `!level.isClientSide` (`LevelChunk.java:273–278`). The diff in `ShulkerBoxBlock.java:176–184` only moves `super.onRemove` before neighbor-output notification; it does not alter client block-entity cleanup or shape methods.

Forget-chunk packets drop the chunk (`ClientPacketListener.java:756–760`; `ClientChunkCache.java:56–64`). Cache replacement/unload calls `ClientLevel.unload`, which clears all block entities and tickers (`ClientChunkCache.java:189–207`; `ClientLevel.java:294–298`). A subsequent packet load therefore installs state-derived entities afresh.

The movement-relevant Shulker geometry consumer is `ShulkerBoxBlock.getShape`: it uses `getBoundingBox(currentBlockState)` only when the BE at the position is a `ShulkerBoxBlockEntity`, otherwise it returns the full-block shape (`ShulkerBoxBlock.java:217–221`; the open support shape also type-checks at 211–215). `getBoundingBox` consumes animation progress; `isClosed` reads the animation enum (`ShulkerBoxBlockEntity.java:103–105,244–259`). The geometry and BE implementation hashes are identical across this pair.

## Pair-specific disposition

`LevelChunk.java` and `BlockEntity.java` are the two relevant changed paths already classified by the paired audit as added BE validation/removal behavior. The constructor and `setBlockState` validation added in 1.21.1 reject mismatches if they are presented. For the client-reachable Shulker case, however, mismatches are already excluded by the 1.21.0 packet construction/type gates and block-identity replacement cleanup. The later guard has no reachable Shulker block-state transition on which to change entity reuse. `ShulkerBoxBlock.java` is the third applicable changed path from that audit, but its sole diff is `onRemove` callback order, and client state writes do not execute that callback.

**Bounded conclusion:** no valid vanilla client path found in these sources can preserve a mismatched/stale Shulker BE that changes Shulker movement geometry between 1.21.0 and 1.21.1. States created by malformed or external client chunk/entity data are not covered as vanilla-reachable; chunk tags with mismatched registered entity types are in fact ignored by the vanilla client path traced above. This closes only the requested Shulker BE reachability slice, not overall movement-pair coverage.
