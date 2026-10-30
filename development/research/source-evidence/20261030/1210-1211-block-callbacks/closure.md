# 1.21.0 → 1.21.1 changed block callbacks and block entity validation

## Bounded closure

For ordinary valid block/entity state transitions, these six changed paths do not add a direct local-player movement response when the client has the same received block states and valid block entities. The four `onRemove` reorderings execute only on the logical server. They can change authoritative neighbor/comparator processing and thus can participate in production of later block states delivered to the client; that is a server-produced world-state difference, not a changed client callback or collision procedure. The block-entity validation addition is also reached on clients, but normal transitions keep the existing entity valid or remove it on a block-type change before validation. Shulker support/collision shapes read its block entity, so the invalid-entity edge is explicitly bounded below rather than generalized away.

This closes only the four callback and two block-entity paths against ordinary local-player collision/support consumers. It does not close other changed paths or the 1.21.0–1.21.1 movement pair.

## Callback order and side

The compared differences are confined to `onRemove` in the four block classes (as recorded by the accepted source audit):

| Class | 1.21.0 order | 1.21.1 order |
|---|---|---|
| `ChiseledBookShelfBlock` | On actual block replacement, drop/clear shelf contents, call `updateNeighbourForOutputSignal` when the shelf entity was nonempty, then `super.onRemove`. | Drop/clear, remember whether it was nonempty, call `super.onRemove`, then update output neighbors if it was nonempty. |
| `LecternBlock` | On actual block replacement, pop the book, update neighbors below if old `POWERED` was true, then `super.onRemove`. | Pop the book, call `super.onRemove`, then update neighbors below if old `POWERED` was true. |
| `SculkSensorBlock` | On actual block replacement, if old phase was `ACTIVE`, notify neighbors at the sensor and below; then `super.onRemove`. | Call `super.onRemove`, then perform the same active-phase neighbor notifications. |
| `ShulkerBoxBlock` | On actual block replacement, capture the old entity, update output neighbors if it was a shulker entity, then `super.onRemove`. | Capture the old entity, call `super.onRemove`, then perform the same output-neighbor update. |

The enclosing `LevelChunk.setBlockState` is decisive: both releases call the removed state's `onRemove` only in `!level.isClientSide` (`LevelChunk.java:273–278`). The client branch instead removes an old block entity when the old state has one and the new block differs; it never calls the changed `onRemove`. The callback methods' `!oldState.is(newState.getBlock())` condition further limits them to actual block replacement, not property changes within one block. The moved notifications can change server-side comparator/neighbor decisions. A resulting changed block state can subsequently be sent to the client, but that is an input-state difference; with the same received states, the client collision/support consumers see the same state and the callback diff contributes no direct local response.

## Block-entity validation and movement consumers

In 1.21.1 `BlockEntity` validates the constructor state and validates every later `setBlockState` assignment with `BlockEntityType.isValid`. That predicate is simply membership of `state.getBlock()` in the type's registered valid-block set (`BlockEntityType.java:300–302`); it does not inspect block properties. `LevelChunk.setBlockState` adds a both-sides check for an existing entity when the new state has a block entity: if invalid, remove it and create the new state's entity (`LevelChunk.java:288–302`). The 1.21.0 path directly calls `setBlockState` on an existing entity.

For ordinary writes, the changed validation path is not a reachable mismatch for these classes. A property change preserves the block identity used by the validity predicate. On a block-identity change, the server's inherited `BlockBehaviour.onRemove` removes the old entity; on a client update, `LevelChunk`'s client branch removes it before reaching new-entity validation. Shulker boxes are one relevant multi-block exception to check: `BlockEntityType.SHULKER_BOX` explicitly accepts all 17 color/default shulker blocks in both versions, so a color-block replacement remains valid even though the block singleton changes.

Three of the four block classes do not read their block entity from collision/support shape methods. `ShulkerBoxBlock` does: support uses `ShulkerBoxBlockEntity.isClosed`, and shape uses its animation-derived bounding box (`getBlockSupportShape` and `getShape`). Those method bodies are unchanged between the endpoints. The added invalid-entity cleanup could therefore alter the shape only if a mismatched/stale entity is already present at a shulker state (or loading malformed state fails the new constructor validation). The ordinary state transitions above do not create that condition. Malformed externally injected/chunk data is not claimed closed by this ordinary-path result.

For client state delivery, `ClientPacketListener.handleBlockUpdate` calls `ClientLevel.setServerVerifiedBlockState`; that calls `Level.setBlock` and reaches `LevelChunk.setBlockState`. The client-side removed-block callback is still skipped in that route. `ClientLevel.syncBlockState` also has an existing prediction-correction path that can call `player.absMoveTo` if the corrected state collides with the player; it is not changed in either release and consumes the state supplied to it.

## Exact source identity and hashes

Canonical read-only sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`, Mojmap, folder `1.21` for Minecraft 1.21.0 and folder `1.21.1` for Minecraft 1.21.1. The accepted `.native-source/20261030/1210-1211-audit/source-audit.md` pins release publication evidence and independently verifies all 5,363 Java files on each side, all 17 changed paths, and the exact source manifests. Hashes below bind this closure to the relevant source files; identical hashes are byte-identical.

| Relative source path below `decompiled_minecraft/` | SHA-256 |
|---|---|
| `1.21/mojmap/net/minecraft/world/level/block/ChiseledBookShelfBlock.java` | `e1099b42015d86dc1186e77a12634f66a1bf3da031bdfd1762dc2f4416d24d16` |
| `1.21.1/mojmap/net/minecraft/world/level/block/ChiseledBookShelfBlock.java` | `a098b823a3cd19189564b4c284d9b705a7bdfc7c337b2195f06689b5955b86d2` |
| `1.21/mojmap/net/minecraft/world/level/block/LecternBlock.java` | `95b43efb57c792420532cbe6c6182f2196ced6ef4854d478240a9f7b729c3f33` |
| `1.21.1/mojmap/net/minecraft/world/level/block/LecternBlock.java` | `cf46fbcb2b644a4f8081cc834a542e069e748541071ce859ee44f586805e801d` |
| `1.21/mojmap/net/minecraft/world/level/block/SculkSensorBlock.java` | `24413bb5e7860e6de0203c3d87be19354880e35ddca938079e61cf586c9b51d5` |
| `1.21.1/mojmap/net/minecraft/world/level/block/SculkSensorBlock.java` | `a19c286a36c0e7c2b5689c6495aadcc519826cc0f1b949aab129f435a4109c42` |
| `1.21/mojmap/net/minecraft/world/level/block/ShulkerBoxBlock.java` | `3b782565707ee431aa2843c772d494147cc37fde61eeca50902aa710dbb8c227` |
| `1.21.1/mojmap/net/minecraft/world/level/block/ShulkerBoxBlock.java` | `c7921f13e26d3ce152f551d2e50e6e35d9cf8dc900503da4fb7730a85a717489` |
| `1.21/mojmap/net/minecraft/world/level/block/entity/BlockEntity.java` | `579463eb222b03d3fb89aacfa49a0d1963df3f47fe5f6bcdbc61c208b3f5207d` |
| `1.21.1/mojmap/net/minecraft/world/level/block/entity/BlockEntity.java` | `b16e3efc8f959372433f17f71bd7836f3d75f6f9c7e608d7184ab779d6370f0b` |
| `1.21/mojmap/net/minecraft/world/level/chunk/LevelChunk.java` | `4912413546274e0eb231bc36e4537a4ce243b41b46c83c89bb64dd59cad7072d` |
| `1.21.1/mojmap/net/minecraft/world/level/chunk/LevelChunk.java` | `435db581b3d397edd88dd19f051f7f6adb8fcf8acacdc42680339815f5f05591` |
| `1.21/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java` | `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` |
| `1.21.1/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java` | `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` |
| `1.21/mojmap/net/minecraft/world/level/block/entity/BlockEntityType.java` | `9432c68f12bd79761167129ad5def3cafc9516513a38f4beaa9d992ff6d6cfe1` |
| `1.21.1/mojmap/net/minecraft/world/level/block/entity/BlockEntityType.java` | `9432c68f12bd79761167129ad5def3cafc9516513a38f4beaa9d992ff6d6cfe1` |
| `1.21/mojmap/net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java` | `4779b4301b2cdd90ac39e9abb2e4f5e47b07869f592028e1f085ad2be7c697b8` |
| `1.21.1/mojmap/net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java` | `4779b4301b2cdd90ac39e9abb2e4f5e47b07869f592028e1f085ad2be7c697b8` |
| `1.21/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| `1.21.1/mojmap/net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| `1.21/mojmap/net/minecraft/client/multiplayer/ClientLevel.java` | `cefc6ffb53ad508052a7a0a96e21ecfd6d2cf471d920c7e91f1911c61fb911e6` |
| `1.21.1/mojmap/net/minecraft/client/multiplayer/ClientLevel.java` | `cefc6ffb53ad508052a7a0a96e21ecfd6d2cf471d920c7e91f1911c61fb911e6` |

## Static source anchors

- Four `onRemove` differences: the callback methods in the four block files listed above; exact method starts are line 188 (`ChiseledBookShelfBlock`), 208 (`LecternBlock`), 121 (`SculkSensorBlock`), and 177 (`ShulkerBoxBlock`) in 1.21.0/1.21.1.
- Side gate and entity cleanup: both `LevelChunk.java` versions, `setBlockState`, lines 273–299; the new validity removal/recreation is 1.21.1 lines 288–302.
- Inherited entity removal: both `BlockBehaviour.java` versions, `onRemove`, lines 160–164.
- New validation: 1.21.1 `BlockEntity.java`, constructor/validator lines 39–53 and setter lines 248–252; `BlockEntityType.isValid`, lines 300–302.
- Client block update path: 1.21.1 `ClientPacketListener.java`, `handleBlockUpdate`, lines 780–784; `ClientLevel.java`, `setServerVerifiedBlockState`, lines 123–126, and `syncBlockState`, lines 129–137.
- Shulker direct shape dependency: both `ShulkerBoxBlock.java` versions, `getBlockSupportShape` and `getShape`, lines 211–222; its entity animation/bounding-box source is unchanged `ShulkerBoxBlockEntity.java`, `tick`/`updateAnimation`/`getBoundingBox`, lines 62–109.
