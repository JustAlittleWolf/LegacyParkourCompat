# Respawn velocity copy applicability audit

Status: COMPLETED

## Scope and evidence boundary

This is a bounded applicability audit for `development/research/findings/1.21.1--1.21.2/keep-entity-data-respawn-velocity-copy.md`. It does not repeat the accepted historical client-handler comparison. The supplied finding records that prior source authors traced no historical velocity copy and zero delta at the exact replacement/reset receivers across 1.8–1.21.1. This worker checked the server-side ordinary End Portal route at representative exact releases/protocol body groups and audited every exact 26.2 `ClientboundRespawnPacket` constructor/send site. Exact source files below are canonical local decompiler output, read-only.

## Ordinary Overworld-to-End portal route

At sampled versions, an ordinary server-side End Portal transition goes through the server player's dimension-change path, emits that release's respawn packet, then follows with the position correction. It therefore reaches the historical client replacement/reset receiver previously traced for the exact handlers. The result relies on the supplied prior finding for the receiver's zero-velocity behavior; this audit establishes route applicability, not a fresh handler audit.

| Exact source sample | End Portal route and respawn emission | Coverage note |
|---|---|---|
| 1.8, Ornithe/Feather | `decompiled_minecraft/1.8/ornithe-feather/net/minecraft/block/EndPortalBlock.java:63` calls `entity.changeDimension(1)`. `net/minecraft/server/PlayerManager.java:386-401` changes the server player dimension, sends `PlayerRespawnS2CPacket`, performs dimension change and calls network teleport. | Legacy protocol/body group. |
| 1.12.2, Ornithe/Feather | `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/EndPortalBlock.java:69` calls `entity.changeDimension(1)`. `net/minecraft/server/PlayerManager.java:487-503` sends `PlayerRespawnS2CPacket`, transfers, then teleports. | Same named packet family, later server body. |
| 1.14.4, Mojmap | `decompiled_minecraft/1.14.4/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:47` selects THE_END from Overworld and calls `entity.changeDimension`. `net/minecraft/server/level/ServerPlayer.java:585-604` emits `ClientboundRespawnPacket` in the ordinary branch of `changeDimension`; the credits-return branch is separately handled. | Mojmap protocol body after packet/type transition. |
| 1.16.5, Mojmap | `decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:56` calls `entity.changeDimension(serverLevel)`. `net/minecraft/server/level/ServerPlayer.java:642-660` sends `ClientboundRespawnPacket` from dimension change before placement. | PortalInfo transition body. |
| 1.18.2, Mojmap | `decompiled_minecraft/1.18.2/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:52` calls the player/entity dimension-change route. `net/minecraft/server/level/ServerPlayer.java:706-724` sends `ClientboundRespawnPacket`. | Dimension-key/common spawn info body. |
| 1.20.6, Mojmap | `decompiled_minecraft/1.20.6/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:58` calls `changeDimension`. `net/minecraft/server/level/ServerPlayer.java:788-804` sends `ClientboundRespawnPacket(..., (byte)3)` for cross-dimension transfer. | Keep-mask body immediately preceding finding pair. |
| 1.21.1, Mojmap | `decompiled_minecraft/1.21.1/mojmap/net/minecraft/world/level/block/EndPortalBlock.java:54-84` gets the destination; the Overworld-to-End case returns a `DimensionTransition`. `net/minecraft/server/level/ServerPlayer.java:840-861` processes cross-dimension transition and sends `ClientboundRespawnPacket(..., (byte)3)`. | Same relevant keep-mask body as finding's newer endpoint. |
| 1.21.2, Mojmap | The accepted finding already records the exact End Portal destination and `ServerPlayer.teleport` transfer evidence, with keep byte 3. | Existing evidence; not re-audited here. |

These samples cover the legacy packet family (1.8/1.12.2), packet/type transition (1.14.4), and successive dimension-transfer bodies through the finding endpoints. They are not an exhaustive inspection of every patch or every release body from 1.8 through 1.21.1. That exhaustive route sweep is outside this bounded applicability check. The accepted exact-handler coverage remains the basis for saying ordinary transfers reach a zero receiver throughout its recorded range.

## 26.2 exact send-site audit

Source identity: `decompiled_minecraft/26.2/unobfuscated.ready.json` is ready for exact version `26.2`, mapping `unobfuscated`, from Vineflower 1.12.0 and Java 25; its source manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Relevant manifest file hashes:

- `ClientPacketListener.java`: `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`
- `ClientboundRespawnPacket.java`: `b2997582cb12fae6923d0d7c02ef864444ab7a6f476cbebfe8a225ebff4d3b03`
- `ServerPlayer.java`: `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`
- `PlayerList.java`: `f3060f313a2c9221c8fe953cba0f19f5179d579fc3b80598d68628fb712242ff`
- `ServerGamePacketListenerImpl.java`: `2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2`

A source-wide exact search finds two constructor/send sites:

1. `net/minecraft/server/level/ServerPlayer.java:1121` sends `new ClientboundRespawnPacket(..., (byte)3)` from `teleport(TeleportTransition)` only after the same-dimension early return, so it is a cross-dimension player transfer. `(byte)3` has bit 2 set, so the client `shouldKeep((byte)2)` branch copies the old LocalPlayer's delta movement. This is the ordinary portal/cross-dimension route (including End credits return only if represented as such a transition, which the separate win-game flow below is not).
2. `net/minecraft/server/players/PlayerList.java:420` sends `dataToKeep = (byte)(keepAllPlayerData ? 1 : 0)`. Its caller `ServerGamePacketListenerImpl.java:1897-1906` shows two reasons: `wonGame` End-credits respawn calls `respawn(..., true, CHANGED_DIMENSION)`, producing mask `1`; ordinary death calls `respawn(..., false, KILLED)`, producing mask `0`. Neither sets bit 2, so neither copies delta movement through the client's `shouldKeep((byte)2)` gate. The End credits completion path is thus a distinct modern keep-all-data reason, but not a keep-entity-data reason.

`ClientboundRespawnPacket.java:13,34-35` defines `KEEP_ENTITY_DATA = 2` and `shouldKeep(mask)` as `(dataToKeep & mask) != 0`. In `ClientPacketListener.java:1251-1321`, the client has the respawn packet's keep bits, common spawn info including the destination dimension, the old player's current dimension, and `oldPlayer.isDeadOrDying()` (used for loading reason). It can distinguish cross-dimension destination/context and whether the server marked entity data to keep. The packet does not carry a reason enum saying “ordinary portal” versus another transfer; bit 2 is the operative keep-data signal. The client also sees later position-correction packet values/relative flags, but those are separate from the respawn packet and are not needed to choose the immediate replacement-player velocity behavior.

## Applicability conclusion

The accepted historical zeroing behavior applies to a replaced player for the 1.21.1→1.21.2 operation regardless of whether the path was an ordinary dimension transfer or a death respawn: old handlers reset the new LocalPlayer's velocity, and the source claim is explicitly scoped to the respawn replacement-player operation. In 26.2, the modern copy gate is narrower than “every respawn”: bit 2 is sent by cross-dimension `ServerPlayer.teleport` as mask 3, while End-credits keep-all-data respawn sends mask 1 and death respawn sends mask 0. Any overwrite that intends to recover the historical zero receiver should key on the selected historical operation/respawn replacement path, and should run after the native copy when bit 2 is present. Do not infer from `shouldKeep((byte)1)` or `wonGame` that copied velocity occurred; neither sets the keep-entity-data bit. The packet/context is insufficient to label the exact cause among all possible server-defined cross-dimension transitions, but that cause distinction is not required to target the common historical replacement/reset operation.

### Sample source publication identities

The canonical ready markers identify these sampled source sets: 1.12.2 `ornithe-feather` manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.14.4 Mojmap `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; 1.16.5 Mojmap `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`; 1.18.2 Mojmap `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; 1.20.6 Mojmap `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311`; and 1.21.1 Mojmap `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`. The 1.8 source tree is present under `decompiled_minecraft/1.8/ornithe-feather/`, but has no ready/provenance marker alongside the directory, so it is used only as a body-shape sample and has no independently recorded source-set identity in this report.
