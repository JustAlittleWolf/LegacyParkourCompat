# X1 direct external player movement responses

Scope: Mojmap `ClientPacketListener` routes that directly update the current local player or movement state from external packet input, comparing 1.20.6 → 1.21 and 1.21 → 1.21.1. Same valid raw packet/state inputs are assumed; server production and trajectory reachability are not required. This is a bounded worker report, not a canonical finding or pair coverage freeze.

## Evidence identity and integrity

The exact read-only trees inspected were `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.20.6,1.21,1.21.1}/mojmap`, all Mojmap. The 1.21 `.decompile-provenance.json` source hashes were checked against the local files for `ClientPacketListener.java`, `LocalPlayer.java`, `Entity.java`, `ClientboundSetEntityMotionPacket.java`, `ClientboundExplodePacket.java`, and `PlayerInfo.java`; all matched. The 1.20.6 and 1.21.1 trees contain no `.decompile-provenance.json` or endpoint manifest, so their listed local SHA-256 values are recorded as observed file identities, not provenance-verified hashes. No source was regenerated.

| Version | File | SHA-256 |
|---|---|---|
| 1.20.6 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `e121e998ec25211aaecf91fffd0ea699cebd47eddea9134efd3f2ffbef8eb7cd` |
| 1.20.6 | `net/minecraft/client/player/LocalPlayer.java` | `6b429dfa6e0681251ec985dda1627f808652a7bbe5b70dc85c8fa0fe0ed46ffa` |
| 1.20.6 | `net/minecraft/world/entity/Entity.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3` |
| 1.20.6 | `net/minecraft/world/entity/LivingEntity.java` | `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.20.6 | `net/minecraft/world/entity/player/Player.java` | `785d93ccc94e1f912e545b2b0c355edeb352b44ee8e83a69364daec35266dbe2` |
| 1.20.6 | `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `34e141d58c2d3585ef6a4b00ed4819c42fa11301ba8428ca06c5379fb7ecdae9` |
| 1.20.6 | `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` |
| 1.21 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| 1.21 | `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21 | `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21 | `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21 | `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21 | `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` |
| 1.21 | `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` |
| 1.21.1 | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` |
| 1.21.1 | `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21.1 | `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` |
| 1.21.1 | `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.1 | `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` |
| 1.21.1 | `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` |
| 1.21.1 | `net/minecraft/network/protocol/game/ClientboundExplodePacket.java` | `41b8d60fd85e4811bbc0b7b6bc623825f7824ec8266bb00ab4e2fb371438038c` |

## Compared routes

### Entity motion packet

`ClientPacketListener.handleSetEntityMotion` (1.20.6 lines 515–521; 1.21 and 1.21.1 lines 517–523) resolves the packet entity by ID, checks non-null, and calls `lerpMotion`. At 1.20.6 the handler passes each raw short-backed integer divided by `8000.0`; at 1.21 the getters perform that same integer-to-double division and the handler passes their returned doubles. `Entity.lerpMotion` delegates to `setDeltaMovement` in the compared sources. For equal raw packet values, each component is therefore the same double; no movement delta found. The getter return type/source location changed, but not the effective operation for this route.

### Explosion packet knockback

`ClientPacketListener.handleExplosion` (1.20.6 lines 1166–1185; 1.21 lines 1171–1190; 1.21.1 lines 1171–1190) constructs/finalizes the client explosion and adds the packet's three knockback floats to the current player's delta movement. The direct knockback operation and order are identical across both pairs. `ClientboundExplodePacket` is byte-for-byte identical across these source trees by SHA-256. Explosion visuals/block interaction are not treated as movement findings here.

### Authoritative local-player position/rotation correction

`handleMovePlayer(ClientboundPlayerPositionPacket)` (1.20.6 lines 618–686; 1.21 and 1.21.1 lines 620–688) resolves `this.minecraft.player` directly with no entity-ID/local-authority condition. For relative X/Y/Z it preserves that velocity component, adds the packet offset to position and previous-position fields; for absolute axes it zeros that velocity component and replaces position and previous-position fields. It then sets position and velocity, applies relative or absolute rotations, acknowledges teleport ID and sends a position/rotation response. This code is unchanged across both pairs, including arithmetic order and packet feedback.

### Entity relative move / teleport

`handleMoveEntity` (1.20.6 lines 577–600; 1.21 and 1.21.1 lines 579–602) resolves the entity and applies position/rotation interpolation and packet on-ground only when `!isControlledByLocalInstance()`. Position deltas decode through that entity's `VecDeltaCodec`, whose base is updated before `lerpTo`; rotation-only packets use existing lerp targets. `handleTeleportEntity` (1.20.6 lines 532–549; 1.21 and 1.21.1 lines 534–551) always syncs the packet-position codec for a resolved entity, then only interpolates pose/position and writes on-ground when the same local-control predicate is false. Both routes and gates are unchanged across both pairs. For the current LocalPlayer, the normal locally controlled predicate suppresses remote movement application; if the predicate is false, the same packet route applies unchanged.

### Respawn and dimension transition

`handleRespawn` (1.20.6 lines 1073–1151; 1.21 and 1.21.1 lines 1076–1154) replaces the `LocalPlayer`, transfers its ID, resets its position/pose, adds it to the selected level and sets its yaw, input and game mode. These movement-relevant steps and dimension gates match. In 1.20.6, attributes are copied only when keep-attributes is requested; in 1.21/1.21.1 the false branch additionally calls `assignBaseValues` from the old player. This is attribute restoration, excluded by the assignment's attribute/input-dependency boundary; it may affect later movement attributes, so it is recorded as an excluded/adjacent respawn behavior, not accepted here as a response finding. At 1.21→1.21.1 the entire `ClientPacketListener.java` hash is identical.

### Abilities, player-info game mode and game-state packets

`handlePlayerAbilities` (1.20.6 lines 1796–1803; 1.21 and 1.21.1 lines 1799–1806) writes flying, instabuild, invulnerability, mayfly, flying speed and walking speed to the current player. The handler is unchanged across both pairs. `handlePlayerInfoUpdate` / `applyPlayerInfoUpdate` (1.20.6 lines 1720–1767 and 1.21/1.21.1 lines 1723–1770) calls `onGameModeChanged` only for an actual game-mode change matching the local player's UUID; `LocalPlayer.onGameModeChanged` (1.20.6 lines 1059–1065; 1.21/1.21.1 lines 1062–1068) clears vertical velocity only for SPECTATOR, and both gate/effect are unchanged. `handleGameEvent`'s CHANGE_GAME_MODE route calls local game-mode state; IMMEDIATE_RESPAWN and LIMITED_CRAFTING only affect death-screen/crafting policy. The current-player movement path is unchanged. The 1.21 change to WIN_GAME's respawn-screen routing is presentation/command behavior, not a direct movement update.

### Entity metadata and entity events

`handleSetEntityData` (1.20.6 lines 524–530; 1.21/1.21.1 lines 526–531) assigns packet metadata to a resolved entity. For a matching player pose accessor, inherited `Entity.onSyncedDataUpdated` (1.20.6 lines 2719–2721; 1.21/1.21.1 lines 2748–2750) refreshes dimensions after pose metadata changes; the pose accessor, refresh and handler route show no change in the compared endpoints. LocalPlayer's own synced-data callback updates hand-use state/sound only. `handleEntityEvent` routes unhandled event IDs to `Entity.handleEntityEvent`; LocalPlayer only handles permission-level IDs 24–28, while the Player/LivingEntity/Entity overrides handle item, sound, particles, death presentation and honey particles/jump particles. No compared event branch directly changes current local-player position, velocity, on-ground state, pose or movement-driving abilities. These dispatch and callback slices show no relevant change across either pair.

`handlePlayerInfoRemove` removes social/tab-list state only. `handlePlayerInfoUpdate` actions other than UPDATE_GAME_MODE update chat, listing, latency or display-name state only. No movement effect found.

## Coverage status

- **Compared and closed for this bounded lane:** motion packet, explosion knockback, authoritative position correction, relative entity move/teleport with local-control predicates, respawn/dimension replacement, abilities, player-info/game-mode and game-event movement-driving state, entity metadata pose path, and entity-event inheritance/callback routes.
- **No movement difference accepted from this pair comparison.** The `/8000.0` entity-motion conversion moved from listener to packet getters but yields the same values for the same raw packet state.
- **Excluded adjacent change:** respawn's new base-attribute restoration on the non-keep path, under the separately assigned attribute scope.
- **Not examined:** effects/attributes/equipment packet handlers and their inputs; block/level events and piston callbacks; vehicles; damage/attack resolution; health/food; rendering; other packet handlers without a direct movement state write. No claim of full-pair tick coverage or independent audit.
- **Historical snapshot caveat (superseded below):** the initial pass did not locate the endpoint manifests and described the 1.20.6/1.21.1 hashes as local checksums only. See the appended evidence correction; all listed endpoint source hashes are now manifest-verified.

## Evidence correction (2026-10-31)

The endpoint manifests were located in the version roots as `1.20.6/mojmap.sources.sha256` and `1.21.1/mojmap.sources.sha256`, with matching `mojmap.ready.json` metadata. The ready metadata records client SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` for 1.20.6 and `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99` for 1.21.1. The actual source-manifest file hashes match the ready records: 1.20.6 `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; 1.21.1 `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`.

Every 1.20.6 and 1.21.1 source-file SHA-256 listed in the evidence table above was checked against its respective `mojmap.sources.sha256` entry and the current file; all matched. Thus endpoint source integrity is verified for the listed files. This corrects only the previous provenance caveat; the scope and movement findings remain unchanged.
