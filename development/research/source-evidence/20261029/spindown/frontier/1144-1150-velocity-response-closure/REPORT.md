# 1.14.4 → 1.15 clientbound entity velocity response closure

## Scope and verdict

Fresh source-only trace of `ClientboundSetEntityMotionPacket` from packet decode/dispatch through the client listener and entity velocity write, limited to clientbound velocity response and immediately reachable paths. No server packet production or network scheduling/delivery timing was analyzed. The packet layout/dispatch, local-player ID lookup, listener gate, and velocity write are unchanged. The handler has no local-player fallback when its ID lookup misses. A separate explosion response directly adds knockback to `minecraft.player` in both versions. The subsequent 1.15 movement consumer has a new block speed factor operation reachable for a local player under its gates; that is a next movement operation, not an extra action performed by the velocity handler.

## Source identity and exact provenance

Canonical source roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap`

| Version | Provenance SHA-256 | Ready marker SHA-256 | Source manifest SHA-256 (also declared in ready marker) | Ready source count / bytes | Client input SHA-256 |
|---|---|---|---|---:|---|
| 1.14.4 Mojmap | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | 3,250 / 17,080,207 | `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` |
| 1.15 Mojmap | `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` | `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` | `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` | 3,329 / 17,522,461 | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |

The 1.14.4 provenance records requested version 1.14.4 and mappings `feather,mojmap`; the 1.15 provenance records requested/version ID 1.15 and official Mojmap. The source-manifest files were SHA-256 checked directly against the ready marker declarations. For every source file cited below, the manifest entry agrees with a direct SHA-256 of the canonical source file.

## Findings

### Packet representation and dispatch

`net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` is byte-for-byte identical in the two source trees (SHA-256 `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762`). It stores the entity ID and three integer velocity components. `read` decodes a VarInt ID followed by three signed shorts; `write` writes those same fields in that order; getters expose the values. `handle` invokes `ClientGamePacketListener.handleSetEntityMotion`. Thus wire layout and dispatch have no version delta. This report does not assess the packet producer.

### Client listener and entity response

`ClientPacketListener.handleSetEntityMotion` is the same operation in both versions (the containing listener files differ elsewhere). It first calls `PacketUtils.ensureRunningOnSameThread`, then queries `this.level.getEntity(packet.getId())`. If an entity exists, it calls `lerpMotion(xa / 8000.0, ya / 8000.0, za / 8000.0)`; if not, it does nothing. There is no fallback to `this.minecraft.player` on a miss, nor any call here to move/tick the entity.

The inherited `Entity.lerpMotion` body is the same: it delegates directly to `setDeltaMovement(d, e, f)`, whose `Vec3` overload assigns the entity's `deltaMovement` field. `LocalPlayer` overrides neither method in either version. The response therefore replaces the local player's velocity state immediately when lookup succeeds; it does not synchronously apply displacement before the next tick.

The local player is in the lookup map under the packet's exact integer entity ID. On initial login both versions call `level.addPlayer(i, minecraft.player)` using `ClientboundLoginPacket.getPlayerId()` (and later assign that same ID to the player object); on respawn they preserve `localPlayer.getId()`, assign it to the replacement local player, then add the replacement under that ID. The 1.14.4 map lives in `MultiPlayerLevel`; in 1.15 it lives in renamed `ClientLevel`. Each `addPlayer(i, player)` inserts into `entitiesById` by key `i`; `getEntity(i)` returns that same map entry. The handler does not use a special local-connection lookup route.

### Separate immediate local-player response

`handleExplosion(ClientboundExplodePacket)` is a distinct packet path in both versions. After finalizing the client explosion, it reads `this.minecraft.player` directly and sets its velocity to its current delta movement plus the packet's knockback vector. This path is not a fallback in `handleSetEntityMotion`, and its relevant operation is textually the same in both versions.

### Changed downstream movement consumer

A separate operation can consume the received velocity on a later movement tick: 1.15 `Entity.move` scales X/Z delta movement by `getBlockSpeedFactor()` after block-contact processing. Its base getter chooses the current block's speed factor or, when that factor is 1 (except water/bubble column), the supporting block's factor. `Player.getBlockSpeedFactor()` bypasses this factor while abilities-flying or fall-flying; otherwise the base getter applies. This 1.15 operation has no 1.14.4 counterpart and can affect a local player after a velocity response, depending on position and flight state. It is downstream movement behavior, not a changed packet-response write or an immediately invoked fallback.

## Source anchors and hashes

Line references are one-based in the canonical decompiled files.

| Source | Relevant lines | SHA-256 |
|---|---:|---|
| 1.14.4 `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | 10–69 | `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762` |
| 1.15 same packet path | 10–69 | `753fb60e5ea296a64cfdf96a58bbc254f056b6d8dbcb19619ffaec2a83848762` |
| 1.14.4 `net/minecraft/client/multiplayer/ClientPacketListener.java` | login 334–365; velocity 542–552; explosion 1067–1085; respawn local player 1041–1053 | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` |
| 1.15 same listener path | login 339–376; velocity 554–564; explosion 1095–1115; respawn local player 1042–1082 | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| 1.14.4 `net/minecraft/client/multiplayer/MultiPlayerLevel.java` | add player/map/getEntity 268–280, 311–315 | `3d31b9e969da5a4c79c49987648c921ddd30da1946e4fae5a3dec7f62252bf10` |
| 1.15 `net/minecraft/client/multiplayer/ClientLevel.java` | add player/map/getEntity 282–295, 325–329 | `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` |
| 1.14.4 `net/minecraft/world/entity/Entity.java` | `lerpMotion` 1770–1772; setter 2675–2680 | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.15 same Entity | `lerpMotion` 1733–1735; setter 2680–2685; move consumer 543; base factor 590–602 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.14.4 `net/minecraft/client/player/LocalPlayer.java` | no `lerpMotion` or `setDeltaMovement` declaration | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.15 `net/minecraft/client/player/LocalPlayer.java` | no `lerpMotion` or `setDeltaMovement` declaration | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| 1.15 `net/minecraft/world/entity/player/Player.java` | factor override 2036–2038 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |

## Scope limits

No server packet construction/send path was traced. No scheduling or delivery timing was used to infer behavior. The trace ends at direct velocity assignment plus the identified separate direct explosion response and the named later movement consumer; it is not a complete movement-tick dependency audit.
