# Ordinary Nether respawn-transfer applicability audit

Status: COMPLETED

## Scope and conclusion

This closes only the earlier-client applicability prerequisite for the accepted `KEEP_ENTITY_DATA` respawn velocity-copy behavior. Exact source representatives cover ordinary Overworld ↔ Nether transfer in 1.8.9 and 1.12.2 (Ornithe Feather), 1.14.4, 1.16.5, 1.18.2, 1.20.6, and 1.21.1 (Mojmap), plus the 26.2 unobfuscated comparison. Both directions pass: the ordinary Nether route reaches a dimension respawn transition, and in 26.2 the client can observe the destination dimension key and the keep-entity-data bit.

The bounded applicability condition is the observable dimension-key pair `{minecraft:overworld, minecraft:the_nether}` in either direction, with `KEEP_ENTITY_DATA` bit 2 set for the modern copy path. This identifies packet-visible transfer state, not a physical cause. It must not claim a Nether portal as the cause, infer a causal category, or cover custom destinations, End/credits, death, arbitrary transfers, portal geometry, collision, or server physics.

The independently accepted historical-receiver audit states that 1.8–1.21.1 replacement/reset receivers start with zero velocity. The sampled 1.8.9/1.12.2 handlers make the result visible directly: each calls `Minecraft.respawnPlayer`, which constructs a replacement local player and does not copy motion. The 1.14.4+ audited receiver family likewise creates a replacement player without restoring the old delta movement. Thus ordinary Nether transfer is an applicable earlier-client route to the already accepted historical reset.

## Route and receiver evidence

- **1.8.9, Ornithe Feather:** `net/minecraft/entity/Entity.java:267-280` reaches the ordinary portal dimension-change route (vanilla dimension IDs, Nether `-1`, Overworld `0`); `net/minecraft/server/PlayerManager.java:435-458` sends `PlayerRespawnS2CPacket` before the position teleport; `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:798-817` handles it and calls `Minecraft.respawnPlayer`; `net/minecraft/client/Minecraft.java:1799-1823` constructs a new local player and does not copy velocity.
- **1.12.2, Ornithe Feather:** `net/minecraft/entity/Entity.java:351-364` reaches portal dimension change; `net/minecraft/server/PlayerManager.java:487-512` sends `PlayerRespawnS2CPacket` then teleports; client handler `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:905-924` calls `Minecraft.respawnPlayer`; `net/minecraft/client/Minecraft.java:1965-1994` creates a replacement and copies synced data, but not motion.
- **1.14.4, Mojmap:** `net/minecraft/world/entity/Entity.java:1738-1750` selects Nether when source is not Nether and Overworld when source is Nether. `net/minecraft/server/level/ServerPlayer.java:585-604` emits `ClientboundRespawnPacket` on dimension change; its End-credit return is a separate branch. `net/minecraft/client/multiplayer/ClientPacketListener.java:1014-1056` creates a replacement `LocalPlayer` without copying delta movement.
- **1.16.5, Mojmap:** `net/minecraft/world/entity/Entity.java:1725-1739` selects the opposite Nether/Overworld `ResourceKey`; `net/minecraft/server/level/ServerPlayer.java:642-663` emits the respawn packet in the cross-dimension route. `ClientPacketListener.handleRespawn` at `:1055+` creates a replacement player; no old delta movement is copied.
- **1.18.2, Mojmap:** `net/minecraft/world/entity/Entity.java:1850-1865` selects the opposite Nether/Overworld key; `net/minecraft/server/level/ServerPlayer.java:706-727` sends the respawn packet on transfer. `ClientPacketListener.handleRespawn` at `:910+` replaces the local player without copying its delta movement.
- **1.20.6, Mojmap:** `net/minecraft/world/entity/Entity.java:2061-2076` selects the opposite Nether/Overworld key; `net/minecraft/server/level/ServerPlayer.java:788-808` emits `ClientboundRespawnPacket` with data-to-keep mask `3`. The 1.20.6 client handler creates a new player from the keep flags but does not copy old delta movement (`ClientPacketListener.java:1073-1134`); this pre-26.2 receiver is consistent with the accepted reset audit.
- **1.21.1, Mojmap:** `net/minecraft/world/level/block/NetherPortalBlock.java:119-129` constructs the opposite `Level.NETHER`/`Level.OVERWORLD` target in both directions. `net/minecraft/server/level/ServerPlayer.java:840-861` emits the respawn packet with mask `3` in the cross-dimension transition. `ClientPacketListener.handleRespawn` (`:1076-1143`) creates a replacement and does not restore old delta movement.
- **26.2, unobfuscated:** `net/minecraft/world/level/block/NetherPortalBlock.java:137-148` chooses the opposite vanilla dimension key. `getExitPortal` returns a transition with `Vec3.ZERO` delta and `Relative.union(Relative.DELTA, Relative.ROTATION)` (`:232-240`). `net/minecraft/server/level/ServerPlayer.java:1112-1137` sends `ClientboundRespawnPacket(..., (byte)3)` before teleporting with that transition. The mask is `KEEP_ATTRIBUTE_MODIFIERS | KEEP_ENTITY_DATA`; packet bit 2 is directly testable (`ClientboundRespawnPacket.java:12-14,34-35`). `ClientPacketListener.handleRespawn` reads old/new dimension keys and copies `oldPlayer.getDeltaMovement()` only when bit 2 is set (`:1251-1318`).

## Concrete retained-input versus historical result

Let the outgoing player velocity be any nonzero vector `v`, for example `(0.2, 0.3, -0.1)`. On the 26.2 ordinary portal path, mask `3` makes the respawn handler seed the replacement with `v`. The following portal position correction carries the transition's `Vec3.ZERO` delta under `Relative.DELTA`; `PositionMoveRotation.calculateAbsolute` computes each relative delta component as current plus change, so `v + 0 = v`. `Relative.ROTATION` adjusts orientation, while the exit position is supplied as an absolute coordinate because X/Y/Z position-relative flags are absent. Therefore velocity remains `v` through the native transfer correction. Under the historical replacement behavior, the new local player starts with zero velocity; the same later zero-relative-delta correction produces `0 + 0 = 0`. The observable difference is the velocity entering the next movement tick, with the portal's resolved exit position in both cases.

This is not a new finding about portal destination math or transfer geometry. It isolates why the already accepted respawn reset applies to ordinary Nether dimension changes even when 26.2's native portal transition supplies a zero delta: the DELTA flags add that zero to the velocity retained by the respawn handler.

## Source identities and integrity

Exact source trees were consumed read-only. Each mapped/unobfuscated source tree has a matching sibling `.ready.json`, `.provenance.json`, and source SHA manifest. The listed method files were verified byte-for-byte against their source manifests. Ready/provenance identity pairs (SHA-256) are:

| Exact version / mapping | ready.json SHA-256 | provenance.json SHA-256 |
|---|---|---|
| 1.8.9 / ornithe-feather | `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95` | `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185` |
| 1.12.2 / ornithe-feather | `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a` | `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce` |
| 1.14.4 / mojmap | `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |
| 1.16.5 / mojmap | `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` | `b71df61eb775134119881915198f87c4337bae409e1f0de8d8d6c007e062902d` |
| 1.18.2 / mojmap | `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946` | `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9` |
| 1.20.6 / mojmap | `1ec1dc773ea2a8a3ef48a9fc868893bd2f83d6b13b057d75b151b711788c3d41` | `ee53e67e7ab51069a0eaae5a0ce13d49a9f972bdc13e3c31e041afa77f4d26e8` |
| 1.21.1 / mojmap | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` |
| 26.2 / unobfuscated | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |

For the 26.2 endpoint, ready metadata's source-manifest SHA-256 is `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` and the exact client source set is tagged `26.2` / `unobfuscated`. The 26.2 source hashes for `NetherPortalBlock.java`, `ServerPlayer.java`, `ClientPacketListener.java`, `PositionMoveRotation.java`, and `ClientboundRespawnPacket.java` are respectively `749fa4f33bf71d9758404778c92e88782e510a9e0868c970c18aa81a00db70e3`, `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e`, `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6`, `a5f0cf32266502dd2c9250d28dfc7bc85cfcf30247e91e3311018af15be319f8`, and `b2997582cb12fae6923d0d7c02ef864444ab7a6f476cbebfe8a225ebff4d3b03`.

## Binding to prior accepted work

The separate accepted OW → End applicability verdict at `c-external-respawn-transfer-audit/verdict.md` remains SHA-256 `a6e5ad0864a499eb47975261ef4ac0de95f26ba00123631da56a9b4515a99820`. This audit adds only the ordinary OW ↔ Nether prerequisite; it neither changes nor re-audits that verdict or the historical client reset finding.

No tests, build, game launch, generation, or product edits were performed.
