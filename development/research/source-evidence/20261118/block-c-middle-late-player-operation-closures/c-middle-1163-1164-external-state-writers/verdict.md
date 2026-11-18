# Independent verdict: Mojmap 1.16.3 → 1.16.4 external-state writers

**Verdict: ACCEPT the bounded claim.** The frozen report accurately concludes that the changed-method slice in `ServerPlayer`, `ServerGamePacketListenerImpl`, and `ServerLevel` does not add a producer of client-local movement state. The only changed inventory producer is the book-edit/sign path; its client-visible result is a book stack slot update, not movement state. This verdict is limited to those changed methods and the traced book route. It is not full pair coverage or runtime parity.

## Immutable inputs and provenance

- Frozen input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-middle-1163-1164-external-state-writers/report.md`
- Input SHA-256 (verified from bytes): `30549876caeb6c6089a1a4fa2003a4bb977944a111740a7cc009b641596d9124`
- Canonical local exact-source roots: `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`.
- Hidden `.decompile-provenance.json` SHA-256: 1.16.3 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; 1.16.4 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`.
- Each manifest identifies the `lpc-decompile-v1` pipeline and the same recorded Eclipse Adoptium 25.0.3+9-LTS / Windows 11 / amd64 runtime. Manifest client-input SHA-256 values match: 1.16.3 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; 1.16.4 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`. Official Mojang client-mapping (`client.txt`) SHA-256 values match: 1.16.3 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`; 1.16.4 `93f188a0038fec8c2402d78e7acca983680978d6a9237c1fab80f22caddb00fe`.
- For each assigned class, the bytes independently hash to both the frozen report value and the corresponding manifest `sources` entry:

| Source | 1.16.3 SHA-256 | 1.16.4 SHA-256 |
|---|---|---|
| `net/minecraft/server/level/ServerPlayer.java` | `bead85f57f7726d66040a9d3075cef2f28fe136bfe4bc098698f2bbea70255a9` | `9fad233370c04ab17f973bc12476466d824e3c1ba59a400542b073380bf550c2` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `1837e3179a34c4905462e8bc3b881b2795b43cc945369225b64c48f5a2aabf05` | `3db41202adf5f9ef3626c63843eda39b9476c65ce86a2c7c5c6044fdb519ea80` |
| `net/minecraft/server/level/ServerLevel.java` | `0f73194bb3c93fd179a19386597461b9ea6436c06c80a82613d2f15e2aaab2d2` | `c3d3ccc873efb17048613f1b465401b139f05955cc2b1e1d1bdd99d0744d629f` |

## Independent changed-method inventory

I compared the complete exact source files with Windows `fc /n`, checking every changed hunk in the assigned classes (imports/line movement excluded as behavior changes). The 1.16.4 additions and body changes are:

- `ServerPlayer`: `textFilter` field, constructor initialization and `getTextFilter()`; no movement-field or movement-packet write.
- `ServerGamePacketListenerImpl`: text-filter join/leave lifecycle; three `filterTextPacket` overloads; changed `handleChat` and its new string overload; changed `handleEditBook` plus `updateBookContents` / `signBook`; changed `handleSignUpdate` plus `updateSignText`. These write/filter chat or sign text and book inventory contents/item identity. `handlePlayerInput` and `handleMovePlayer` appear in shifted diff hunks but their method bodies are unchanged. The changed `containsInvalidValues` and vehicle handling bodies are unchanged as well.
- `ServerLevel`: added `getWatchdogStats()` and `getTypeCount(...)`, which construct diagnostic strings from counts. They do not write player state.

No changed producer in this slice writes or emits position, velocity, bounding-box/pose, movement effects, sprint, or player-input state.

## Book write reachability

In 1.16.4 `ServerGamePacketListenerImpl.handleEditBook` accepts a writable book, validates its tag, and restricts the selected inventory slot to the hotbar or slot 40. `updateBookContents` changes only the pages tag on a writable book. `signBook` creates a `WRITTEN_BOOK` and writes it to the addressed inventory slot via `player.inventory.setItem` (1.16.4 lines 758–816). This can be observed by the ordinary inventory-menu listener: `AbstractContainerMenu.broadcastChanges()` detects changed `ItemStack`s and calls `ContainerListener.slotChanged` (lines 113–135); `ServerPlayer.slotChanged` sends `ClientboundContainerSetSlotPacket` for the applicable slot (lines 981–990). The latter packet transports container ID, slot and item stack; client `ClientPacketListener.handleContainerSetSlot` stores the stack into inventory/menu slots (1.16.4 lines 1166–1198). The item update therefore reaches client inventory state only.

The exact 1.16.4 `WritableBookItem.use` and `WrittenBookItem.use` methods call `player.openItemGui(...)` and return; they do not write player movement state (`WritableBookItem.java:38–43`, `WrittenBookItem.java:95–100`). The separately handled `ClientboundOpenBookPacket` opens a `BookViewScreen` (`ClientPacketListener.java:1893–1899`); that is GUI state, not the normal local movement-state writer. I verified these dependency source bytes: `AbstractContainerMenu.java` SHA-256 `8b32e0dab8da7acfdb000ab6edca47a06dadc2dc640b333548882dda28248524`; `ServerPlayer.java` as above; `WritableBookItem.java` SHA-256 `61f76bdb2cc1ffb7fdc81d777a76aac4b5df068dc2b0a2bac5364e7c6d19e95b`; `WrittenBookItem.java` SHA-256 `9e98411eff892a2207019191b628607b280b749dec0162fc7ecb78060e373d8a`; `Inventory.java` SHA-256 `4cb10f1b789e5cc054f3103d049b9a5f0294476b92a75edde109ae813be99bc6`; `ClientPacketListener.java` SHA-256 `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e`; and `ClientboundContainerSetSlotPacket.java` SHA-256 `e3f3277a18fcfbb28f4afd32bb12f14256b3f511e815ebcd3ac694a1b065921`.

## Explicit bounds

- **Correction route:** the clientbound correction receiver (`ClientPacketListener.handleMovePlayer(ClientboundPlayerPositionPacket)`) directly writes local position and velocity, but it is a separate client method and was not audited as a changed method in this assigned server-class slice. `ServerGamePacketListenerImpl.handleMovePlayer` is the serverbound processing/correction route; its method body is unchanged here and its arithmetic/call route was not re-audited. Do not use this verdict to accept or reject correction math.
- **Server movement replay:** no historical replay or server-side movement is established by this audit. Server replay and tick physics remain outside the examined changed-method claim.
- **Health/food:** health and food production/state are excluded from the movement scope and are not claimed unchanged globally; no changed method inventoried here adds a movement-state writer through them.
- **A/B and runtime:** no A/B comparison, tests, gameplay/TAS/Gym/server launch or runtime parity check was performed. This is a static source verdict only.
- Unchanged methods, other packet producers/consumers, entity/world replication and other classes remain outside the source slice. The report correctly states this limitation.

## Output identity

- Verdict path: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-middle-1163-1164-external-state-writers-audit/verdict.md`
- Output SHA-256: recorded in the handoff after writing; it cannot be embedded in this file without changing the bytes and invalidating that hash.