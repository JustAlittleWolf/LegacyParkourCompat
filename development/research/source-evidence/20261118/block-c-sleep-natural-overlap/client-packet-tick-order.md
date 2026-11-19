# Local packet and tick ordering for natural sleep/fall-flying overlap

Status: COMPLETED. This closes the prior report's bounded synchronization gate at source level. It establishes a **conditional ordinary local-tick overlap path**, not guaranteed occurrence for every timing, server configuration or world. Exact canonical sources were read-only. No product, Git, wiki, runtime, tests, live packets, artificial state, source regeneration or primary research writes.

## Verdict

In every assigned stable alias from 1.10 through 1.12.2, vanilla requeues off-thread play-packet handlers to the client main thread, drains that task queue before running game ticks, and then ticks the client world/player. The queue preserves task insertion order. A flag-7 metadata packet and a successful sleep packet on the same inbound connection therefore update local state in wire order, and the player pose selector runs on a later local-player tick after the scheduled tasks processed before that tick.

A normal two-step path can deliver flag 7 before sleep: while descending with a usable Elytra, the local jump-edge code sends `START_FALL_FLYING`; the server accepts it and sets shared flag 7. The server's dirty tracker sends the synchronized data packet to the player. After that ordinary update, the player can use a nearby eligible bed while still descending. The successful bed response sets the local sleeping field but does not clear flag 7. If no server-originated clear arrives before the next local-player tick, that tick reads both true predicates and reaches `updatePlayerPose`; fall-flying wins its existing first branch. A one-tick overlap is therefore **source-supported and possible with ordinary input/state**, conditional on the player remaining airborne with a usable Elytra long enough for the server to retain flag 7 and the local client receiving no clear before its next tick.

It is not guaranteed. A server tick may clear flag 7 if the player is grounded, riding, or lacks a usable Elytra; its later metadata update may arrive before a local tick. A client tick may also occur before the sleep packet. These timing paths produce a non-overlap tick or no overlap. Source proves the ordered code paths and conditional overlap opportunity, not that a particular network run observes it.

## Bounded ordinary witness

Use a normal world state with an eligible bed in a natural dimension at night, no nearby hostile mob that makes sleeping unsafe, and a descending player close enough to interact with it. In 1.10, `trySleep` checks not already sleeping/alive, natural dimension, daylight, coordinates (horizontal within 3 and vertical within 2), and nearby monsters; it does not require `onGround` or reject fall-flying. In 1.12.2 the same broad checks use `isBedWithinRange` (including the bed's opposite/facing tile); there is still no `onGround` or fall-flying rejection. These are the minimum ordinary admission conditions; no change to bed eligibility is proposed.

A feasible source-level sequence is:

1. On one local player tick while airborne and descending, a normal jump edge with a usable chest Elytra meets the local request guard and sends `START_FALL_FLYING`.
2. The server handles that C2S action and sets shared flag 7. `TrackedEntity` sends dirty synchronized data, including the shared flags, to the tracking set and the server player's own handler. The local `EntityDataS2CPacket` handler updates `SyncedData`; no local gameplay setter is needed.
3. After that state update, the player performs a normal bed interaction while still within the admission range and descending. On success, the server directly sends `PlayerSleepS2CPacket`; the client handler calls `trySleep`, which sets `sleeping=true`, zeroes velocity and does not clear shared flag 7.
4. Client `runGame` drains queued inbound packet tasks before ticks. Once the sleep response task has run, the next `world.tickEntities()` includes the local player; `PlayerEntity.tick()` calls `updatePlayerPose()`. If flag 7 has not been cleared by a later server update, both predicates are true at selector entry.

This route requires an eligible bed world state and a continuing airborne Elytra state, but it does not require reordered/forged packets, an injected flag, a runtime race, or simultaneous jump/use input. When the conditions are not present (daytime, unsafe bed, wrong dimension, out of range, no Elytra, grounded/landing before the response), this particular witness does not apply.

## Local writer audit

For the relevant local player, `isSleeping()` returns `PlayerEntity.sleeping`. The successful sleep packet handler is the ordinary setter path to true; `PlayerEntity.tick()` does not locally clear sleeping during ordinary sleep ticking. `isFallFlying()` reads shared flag 7. `ClientPlayNetworkHandler.handleEntityData` applies server-supplied entries to `SyncedData`; `LocalClientPlayerEntity.onDataValueChanged` only plays the Elytra sound when the shared flag becomes true and does not write the flag. `LivingEntity.flyingTick` writes flag 7 only on the logical server. `PlayerEntity.moveRelative` can clear flag 7 for ability flight, but that branch is conditioned on `abilities.flying`, excluded by the ordinary survival witness. Other local movement checks read `isFallFlying()` but do not write flag 7. Thus no ordinary local pre-selector writer forces either predicate false after the two authoritative/local updates described above.

## Exact evidence identity

All cited Java sources are exact canonical `decompiled_minecraft/<version>/ornithe-feather/` Feather Gen2 build.2 output. Each table row is the SHA-256 of the per-version `.decompile-provenance.json` when present, otherwise the published `ornithe-feather.sources.sha256` manifest. The prior immutable `source-gate.md` records the corresponding pose-selector and synchronization class hashes; this report's additional scheduling slices are in `network/PacketUtils.java`, `network/Connection.java`, `client/Minecraft.java`, and `client/network/handler/ClientPlayNetworkHandler.java`.

| Version | Provenance/source-manifest SHA-256 |
|---|---|
| 1.10 | `DF266E2C3009DD1A873C1665C30FE388078ED1C6227668E10CDE87409A18C6DA` |
| 1.10.1 | `A9A50497930848BF0DDEC732B6D424E947F643A8FA6290E01425AC2BBFCE219F` |
| 1.10.2 | `91B0F478ACB7B6F13463C35B268A30D2806F583402631A56F54CE2EB70D1EC71` |
| 1.11 | `8B3E5DA58494F1DBA77410960753B0BF646425A1ADA491D8242CC71E6C7A59CA` |
| 1.11.1 | `0D5F4697D4E4CE53B36D0DBFEC89D44830EB76753A6643B49E42F0857753066D` |
| 1.11.2 | `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0` |
| 1.12 | `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467` |
| 1.12.1 | `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8` |
| 1.12.2 | `B8A37CCFCCD2AAC5F40F5E34FEC85873DBDFA93A266043C103E592E4A8C949DA` |

Exact `client/Minecraft.java` source-file SHA-256s for the tick/task schedule, one per assigned alias:

| Version | `Minecraft.java` SHA-256 |
|---|---|
| 1.10 | `FD524C1902C75F5982B91ECF8EF361796EC72644A2AE11C046EE6CB78A75FAF1` |
| 1.10.1 | `1C6EEAB468D1CE60CA989BAD5813CF8EC02FFD5BCAC1AF330ACDA1DD38E63545` |
| 1.10.2 | `0979D770E8742A07BB8C54CF7FD3449A8C43A4F48910600FFABF75F1F67BA2D9` |
| 1.11 | `1889E0F8D212D696798C5345BEC6A0FEA7AE0B576C7BEEB96C3DE04EF0ACEA30` |
| 1.11.1 | `1EC8B2805E45145A9B96D37C928F875DE62011C449AEF802FCF0F920B8C821D5` |
| 1.11.2 | `3CA93038ADF4CEA512C21AE86D653376E963396640A02EB50CB42A67302F6236` |
| 1.12 | `308EA5B049C7410D03039A12ACDE604B8F6F184710CBC8EC53240524CB5E795A` |
| 1.12.1 | `0291015504D83C68BC25F3C6D26D51F317454692FCB04DC844164467CB25E893` |
| 1.12.2 | `8AF833A5D03A6F6516A7EFF0AB463A96BFEC64AAC31FC80656F37D193B1C1310` |
Additional source-file SHA-256s bind representative exact scheduling and packet paths:

- 1.10 `PacketUtils.java` `12757B150C21F193BF4458CF22002574AAA8FB593BAC0E83F1970FDB22742605`; `Connection.java` `DDD6E2861843C4D2C069FD923D3EDFC6ECBAD4D70EAE01CFD3D5C3CF1AE6CF74`; `Minecraft.java` `FD524C1902C75F5982B91ECF8EF361796EC72644A2AE11C046EE6CB78A75FAF1`.
- 1.12.2 `PacketUtils.java` `12757B150C21F193BF4458CF22002574AAA8FB593BAC0E83F1970FDB22742605`; `Connection.java` `502700F46DDD40822EBE4DC93D4A0B2B1EB610699875CEAD50FE202070370ACC`; `Minecraft.java` `8AF833A5D03A6F6516A7EFF0AB463A96BFEC64AAC31FC80656F37D193B1C1310`.
- For 1.10, the client packet handler SHA-256 is `AF3EFBA0A1D5A0824C29C4AF4AC7F8E4484D1787A82208E4D72BCC65F957E656`; the local-player class SHA-256 is `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`.
- For 1.12.2, the client packet handler SHA-256 is `FCE21D9902E555FD46545FB04BDB6C4A912EEF6398790776AD1A122E09960C7C`; the local-player class SHA-256 is `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC`.
- `TrackedEntity.java` is identical across the assigned aliases within the tracker class family: 1.10 digest `842CF1310C7C0A57E63C1A82D0DDDC3BFB96913DAC684EA5340A32CF26051090`; 1.11–1.12.2 digest `1BA9B26E88E9FC278C667B4311105BBFE45D1B58D6FFA015E02A56AD1C3E2CDC`.

The source sidecars authenticate each listed class within its exact source tree. Mapping identity is per-version Feather Gen2 `<version>+build.2`; no names were inferred from online mappings.

## Slices supporting the order

- `PacketUtils.ensureOnSameThread`: all aliases, `network/PacketUtils.java:8–16`. On Netty's non-client thread, it enqueues `packet.handle(listener)` through `runner.executeTask` and throws the handoff sentinel. `Connection.channelRead0` invokes packet handlers serially for each inbound channel and catches that sentinel, so packet handling itself is deferred rather than applied on Netty.
- `Minecraft.executeTask`: 1.10 `client/Minecraft.java:2510–2534`; 1.12.2 `:2565–2585`. Off-thread tasks are appended to the synchronized FIFO task queue. `runGame`: 1.10 `:831–859`; 1.12.2 `:876–897`; it drains that queue before entering the game tick loop. If both packet tasks are queued at drain time, both execute in inbound order before the following tick. If the later packet queues after a drain, it runs before a subsequent tick; that distinction is why no exact tick guarantee is claimed for all delivery schedules.
- World/local tick: 1.10 `Minecraft.tick():1413–1439` and 1.12.2 `:1443–1469` call `world.tickEntities()` after scheduled task draining. The local player delegates through `LocalClientPlayerEntity.tick()` to player tick; player tick later calls the selector (see `source-gate.md`, 1.10 line 250 and 1.12.2 line 245).
- Ordered local data application: 1.10 `ClientPlayNetworkHandler.handleEntityData():447–452`, `handlePlayerSleep():748–752`; 1.12.2 `:473–478`, `:775–778`. Both handlers use the same-thread handoff. Entity data updates `SyncedData`; player sleep calls local `trySleep`.
- Dirty authoritative data: 1.10 `TrackedEntity.sendDataAndAttributes():269–283`, `sendPacketToAll():286–297`; 1.12.2 same methods at the same ranges. Dirty synced data is emitted as `EntityDataS2CPacket`; `sendPacketToAll` includes `ServerPlayerEntity.networkHandler`, so the player's own client receives flag 7. The successful-bed `ServerPlayerEntity.trySleep` sends `PlayerSleepS2CPacket` directly after setting server sleep state. A previous tick's metadata therefore can precede a later successful-bed response on the same ordered connection.
- Selector entry / writes: 1.10 `PlayerEntity.updatePlayerPose():296–320`; 1.12.2 `:291–315`. `LocalClientPlayerEntity.onDataValueChanged` contains no flag writer (1.10 `:489–504`; 1.12.2 corresponding method); the sleep handler sets `sleeping`, and common living `flyingTick` changes flag 7 only on server. The ordinary survival witness does not set ability flight.
