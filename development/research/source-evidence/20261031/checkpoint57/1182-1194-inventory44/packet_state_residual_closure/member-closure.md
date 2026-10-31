# Clientbound packet handler residual closure

Scope: terminal dispositions for every changed `Clientbound*.java` path in the frozen 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4 path roster. This closes the packet-handler residual only; it is not a pair-freeze claim.

## Roster and hash coverage

The frozen roster contains **88 changed Clientbound edge/path rows across 57 unique paths**: 85 `network/protocol/game` rows (54 paths), plus two login-protocol paths and one status-protocol path. The five adjacent-edge row counts are 36, 6, 0, 32 and 11 respectively. The 1.19.1→1.19.2 edge contributes no changed Clientbound paths. All 85 game-packet rows’ old/new file hashes recompute to the frozen roster (`member-dispositions.tsv`: both `*MatchesRoster` fields are true on every row). The three login/status packet rows also match the roster and their dispatch/callback source hashes are recorded in `non_game_clientbound_hashes.tsv`.

The game packet ledger records each packet’s listener dispatch member, terminal disposition, old/new packet hashes, full `ClientPacketListener.java` hashes and extracted old/new handler-member body hashes when that member exists on the side. Additions/removals and handler renames have a missing-side member hash, explicitly distinguishable from a hash mismatch. Two `ClientboundLevelChunkPacketData` rows are payload helpers with no `handle` member; the caller is the chunk-with-light packet and `handleLevelChunkWithLight`, so these are classified as world/chunk data production.

All compared sources are exact local Mojmap roots under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The existing `source-hashes.tsv` ClientPacketListener hashes were independently recomputed and match on all six versions.

## Terminal handler dispositions

For the 85 game-protocol rows, the member ledger assigns every changed Clientbound path to one of these terminal categories:

- 24 chat, command, server-description and resource-pack metadata rows.
- 15 UI, player-list, stats, recipe, feature and item-control rows.
- 10 movement-relevant entity/player state input or initialization rows.
- 10 presentation, animation, sound, particle or map rows.
- 9 direct response rows already closed in the accepted packet-state work: block acknowledgement, player correction, respawn, explosion, and remote-entity move/teleport.
- 9 fixed-world block/chunk data production rows.
- 5 spawn/remote-entity rows.
- 2 damage/hurt rows.
- 1 other metadata/state-delivery row.

No additional changed handler reads or writes local-player movement state beyond the previously closed direct response paths and the state-input routes below. The old/new `ClientPacketListener` methods retain the `!entity.isControlledByLocalInstance()` gate on `handleMoveEntity` and `handleTeleportEntity`, so those packets do not apply position/rotation updates to the controlled local player.

### Movement-relevant state-input routes

- `ClientboundSetEntityDataPacket.handleSetEntityData` is a direct synchronized-data writer for the packet-addressed entity, which can be the LocalPlayer; its packed entries can include shared movement flags. From 1.19.2 to 1.19.3, the handler changes from a nullable `getUnpackedData()` plus null guard to `packedItems()` without the guard. The older decoder returns null for the empty terminator; the newer decoder returns an empty list. `SynchedEntityData.assignValues` loops over entries, so the empty-list route makes no state assignment. Nonempty entries still assign the values and invoke the same entity data update callback. No changed LocalPlayer flag value, selector or movement calculation was found. The exact packet/listener/synched-data files are hash-bound in `member-dispositions.tsv` and the existing source ledger.
- `ClientboundUpdateAttributesPacket.handleUpdateAttributes` can update movement attributes when its entity id identifies the LocalPlayer. Across 1.19.2→1.19.3 the lookup changes from `Registry.ATTRIBUTE` to `BuiltInRegistries.ATTRIBUTE`; the receiver still sets the base value, removes existing modifiers and re-adds the packet modifiers in that order. This is server/state input, not changed historical movement arithmetic.
- `ClientboundUpdateMobEffectPacket.handleUpdateMobEffect` and `ClientboundRemoveMobEffectPacket.handleRemoveMobEffect` target a `LivingEntity`, including the LocalPlayer. They install/remove effect state; no receiver movement formula changes. The removed `setNoCounter(isSuperLongDuration())` affects duration display only, as established in the previous packet-state review. Factor data/infinite-duration behavior is treated as state input, not as a legacy movement producer.
- `ClientboundLoginPacket.handleLogin` initializes the client player/game state from server-provided data. This is connection initialization rather than an ordinary movement-tick consumer; no new direct historical movement operation is identified.

The remaining three non-game Clientbound rows are also terminal: `ClientboundGameProfilePacket.handleGameProfile` and `ClientboundHelloPacket.handleHello` dispatch to the handshake/login implementation; `ClientboundStatusResponsePacket.handleStatusResponse` updates server-list status display. None is a LocalPlayer movement path.

## Closure boundary

Every changed Clientbound path in the frozen roster has a terminal disposition. The rest of the 254-row `network-protocol` role bucket—chat component/helper classes, serializers, connection/bundling support, serverbound request producers, and other non-Clientbound paths—remains outside this member-level packet-response subtask. This report does not close those rows or the full pair inventory. No source-preparation blocker remains for this assigned Clientbound slice.
