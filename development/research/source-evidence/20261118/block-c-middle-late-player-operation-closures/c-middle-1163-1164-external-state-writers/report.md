# Mojmap 1.16.3 → 1.16.4: external state writers

## Scope and result

Blind, passive exact-source comparison of `ServerPlayer`, `ServerGamePacketListenerImpl`, and `ServerLevel`. I inventoried every changed method/hunk in these three classes and traced any changed client-player state write far enough to determine whether it supplies normal client local movement.

**Result:** no changed method in these classes writes or transmits client-player position, velocity, pose, movement effects, sprint state, or input state. The only changed player-adjacent state writer is book editing: 1.16.4 validates and filters book text, updates a writable-book stack, or replaces a writable book with a written book in a hotbar/offhand inventory slot. This can reach the client as an inventory-slot update, but it is book contents/item identity, not a movement-state write. The changed chat/sign methods write text; the added ServerLevel methods only produce watchdog-stat strings.

This closes only the assigned changed-method slice. It does not audit unchanged movement producers, world/entity tracking, or the full clientbound packet surface.

## Exact source identity

Canonical local roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.3\mojmap` and `...\1.16.4\mojmap`. Both local `.decompile-provenance.json` manifests exist and their listed SHA-256 values match the three examined source files below.

| Release | Mojang client input SHA-256 | Official Mojang client mappings (`client.txt`) SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.16.3 | `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` | `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e` | `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` |
| 1.16.4 | `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` | `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe` | `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` |

Manifest input URLs are Mojang's exact `piston-data` client and mapping objects and `piston-meta` package JSON for each named release. Both manifests record the same `lpc-decompile-v1` pipeline and `Eclipse Adoptium 25.0.3+9-LTS / Windows 11 / amd64` runtime. The source-file SHA-256 pairs (1.16.3 / 1.16.4) are:

| Source | 1.16.3 SHA-256 | 1.16.4 SHA-256 |
|---|---|---|
| `net/minecraft/server/level/ServerPlayer.java` | `bead85f57f7726d66040a9d3075cef2f28fe136bfe4bc098698f2bbea70255a9` | `9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `1837e3179a34c4905462e8bc3b881b2795b43cc945369225b64c48f5a2aabf05` | `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80` |
| `net/minecraft/server/level/ServerLevel.java` | `0f73194bb3c93fd179a19386597461b9ea6436c06c80a82613d2f15e2aaab2d2` | `c3d3ccc873efb17048613f1b465401b139f05955cc2b1e1d1bdd99d0744d629f` |

## Changed-method inventory and movement trace

### `ServerPlayer`

- 1.16.4 adds `TextFilter` field initialization in the constructor (1.16.3 line 175; 1.16.4 lines 172, 178–187) and `getTextFilter()` (1.16.4 lines 1505–1507). The constructor addition creates per-player chat filtering state. It does not touch movement fields or emit a player-state packet.
- No other method-body changes were present in this class diff.

### `ServerGamePacketListenerImpl`

- Constructor (1.16.3 line 184; 1.16.4 lines 191–199) joins the player's text filter when present. `disconnect` (1.16.3 line 283; 1.16.4 lines 294–298 plus 1121–1127 in the disconnect flow) leaves it. These lifecycle changes affect filtering only.
- 1.16.4 adds `filterTextPacket` overloads (lines 300–323), dispatching approved text work onto the server event loop and ignoring callbacks after disconnect.
- `handleChat` (1.16.3 line 1113; 1.16.4 lines 1179–1187) now routes ordinary chat through filtering; slash commands remain on the packet thread. Its new `handleChat(String)` (line 1189 onward) retains the chat action. Chat text/action time is outside the listed movement state.
- `handleSignUpdate` (1.16.3 line 1409; 1.16.4 lines 1483–1486) filters sign text before the new `updateSignText` method (1488–1512) writes sign block-entity messages. It updates block text and not player movement state.
- `handleEditBook` (1.16.3 line 700; 1.16.4 lines 736–761) now filters text and validates the addressed slot as hotbar or slot 40. `updateBookContents` (763–770) writes page tags to the existing writable-book stack; `signBook` (772–794) builds a written-book stack and calls `player.inventory.setItem` for that slot.
- The latter write can reach the player's normal container listener: `AbstractContainerMenu.broadcastChanges` compares slots (1.16.4 `AbstractContainerMenu.java:113–135`), and `ServerPlayer.slotChanged` sends `ClientboundContainerSetSlotPacket` (`ServerPlayer.java:981–990`). This is an inventory update, not a position/velocity/pose/effect/sprint/input packet. The addressed item remains a book: writable and written book `use` methods open the item GUI (`WritableBookItem.java:38–43`; `WrittenBookItem.java:95–100`); these methods contain no movement-state write. Thus this changed inventory payload does not introduce a changed input to normal local movement.
- `handlePlayerInput` and `handleMovePlayer` are present in both versions (1.16.3 lines 290, 758; 1.16.4 lines 326, 819). Their method bodies are unchanged in this pair. `handleMovePlayer` is the ordinary serverbound movement/correction path and is not re-audited here, per assignment boundary.

### `ServerLevel`

- 1.16.4 adds `getWatchdogStats()` and `getTypeCount(...)` (lines 1542–1574); neither has a 1.16.3 counterpart. They aggregate entity/block-entity counts and chunk/tick diagnostics into strings. No player state or packet writer is involved.

## Boundaries

The whole-file diffs for the three assigned classes contain no other changed methods. This is not evidence that all 1.16.3→1.16.4 client-visible player state is unchanged: entity replication, other packet producers, unchanged methods, and the normal correction receiver/producer numeric route were outside this assignment. No runtime, tests, decompilation, network access, product source, wiki, or Git history was used.
