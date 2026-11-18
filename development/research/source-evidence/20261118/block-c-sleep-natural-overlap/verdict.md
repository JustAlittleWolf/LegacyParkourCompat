# Natural sleep / fall-flying overlap: independent source audit

Status: **COMPLETED**. Fresh, read-only source audit across the stable 1.10–1.12.2 aliases. Primary source claims below are supported by exact canonical Feather Gen2 build.2 output. No product source, Git, wiki, implementation, tests, runtime/game, build, network experiment, source regeneration, or primary research area was read or changed.

## Bounded verdict

| Subclaim | Verdict | Finding |
|---|---|---|
| A normal descending jump edge can start authoritative fall-flying | **ACCEPT** | The local-player guard requires a jump edge, airborne state, negative vertical velocity, no current fall-flying, no ability flight, and a usable chest Elytra; it sends `START_FALL_FLYING` without setting flag 7 locally. The server action accepts the corresponding airborne/descending/not-already-flying/not-in-water state with a usable Elytra and calls `ServerPlayerEntity.setFlying()`, which sets shared flag 7 (1.10 `ServerPlayerEntity.java:1042–1047`; 1.12.2 `:1188–1193`). The action case has no sleeping gate. |
| Successful ordinary bed use can set sleeping without clearing flag 7 or requiring on-ground state | **ACCEPT** | `BedBlock.use` calls `PlayerEntity.trySleep`. The server first admits ordinary block use within its squared-distance `< 64.0` bound (plus the usual pending-teleport, spawn-protection, and world-border checks); the bed-specific distance gate is narrower. Neither stage requires `onGround`. The server-side sleep-success gates are environment, time, bed range, and nearby hostile-mob checks; there is no fall-flying rejection. Success sets `sleeping=true`, moves/resizes the player as part of sleeping, and zeroes velocity without clearing flag 7. |
| Exact packet/main-thread ordering permits a local player tick to sample both predicates | **ACCEPT, conditional** | After flag 7 has been delivered, a later ordinary successful bed response can set local sleeping. Both inbound handlers use `ensureOnSameThread`; off-thread packets become main-thread tasks. The client drains its FIFO task queue before its tick loop, and player tick reaches `updatePlayerPose`. If the sleep task runs before the next player tick and no later flag-clear update runs first, that tick sees `isFallFlying()` and `isSleeping()` both true; the fall-flying selector branch wins. |
| Every ordinary run must produce that overlap | **REJECT** | A tick can happen before the sleep response. Server `flyingTick` clears flag 7 when grounded, riding, or lacking a usable Elytra; the resulting metadata clear can be processed before the pose selector. The source proves an available ordering, not a universal delivery/tick schedule. |
| A specific physical trajectory/network run was demonstrated | **PARTIAL** | Ordinary use is source-permitted while airborne when the player is in range and the bed is otherwise eligible. No physics simulation or runtime/network trace was authorized or performed, so the exact fall path and timing remain conditional. |

No source-level exclusion was found. The conclusion is a **conditional ordinary source path**; do not report it as guaranteed runtime occurrence.

## Readback and identity checks

I read the three permitted immutable source notes as UTF-8 and programmatically checked their raw SHA-256 and title readback before using them:

| Read-only input | SHA-256 | Check |
|---|---|---|
| `c-pose-sleep-natural-overlap-source/source-gate.md` | `B734B066452710220E43F41FF5EFB9E1A2CB362F7356502AD61E8BFDE1DF813E` | Exact expected digest and title readback matched |
| `c-pose-sleep-natural-overlap-source/client-packet-tick-order.md` | `94F66037B7CEF39E47BE232290760E365B2330D2CF8CA04E099D2600B1A5CBEB` | Exact expected digest and title readback matched |
| `c-pose-sleep-priority-bound/stable-aliases.md` | `A7447EB210121A4DD4659F1D58DCCF1F154B459C4F7FC32B86D7AC124EA6A534` | Exact observed digest and title readback matched |

For each of the nine exact `decompiled_minecraft/<version>/ornithe-feather/` trees, the applicable nested provenance file or published source sidecar matched its recorded SHA-256. I parsed that identity's source-hash inventory and verified the current hashes of the eleven relevant classes (`BedBlock`, `PlayerEntity`, `LivingEntity`, `LocalClientPlayerEntity`, both play-network handlers, `ServerPlayerEntity`, `TrackedEntity`, `PacketUtils`, `Connection`, and client `Minecraft`) against it. All nine identities and all relevant class entries matched. Mapping identity is Feather Gen2 `<version>+build.2` for each exact release.

| Exact version | Provenance / source-sidecar SHA-256 |
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

Fresh method-text extraction (UTF-8 SHA-256 from the signature through its matching closing brace) also confirmed the key slices across all aliases:

- `PlayerEntity.updatePlayerPose`: `3CD15442FFAD29CCBA3822896DAAF498A1B098FF710399B0F4BF51D7C22A5EF4` for all nine aliases.
- `BedBlock.use`: `9DA0370549C202BD5FD0555FBC4B0CC58AEF1FEF3E9F12220B39E742F4326761` for 1.10–1.10.2; `12E4911EB935D7C7302DF63283A791287AEF8DC049F0C17A865FBF55941EE84B` for 1.11–1.12.2.
- `PlayerEntity.trySleep`: `2C0261FF7E420E7F30F86FE813711B19602663C8F38E3CCDC9F9D3869AA30F2A` for 1.10–1.10.2; `AEAF1933ABADAF6A5D91AA6B5EBF4CE35482541D998590078906C96194E7E006` for 1.11–1.11.2; `876A99033E9C0FE73B3E98AFACABC04174131BF121BC9C16B8E40F3C003C7930` for 1.12–1.12.2.
- `LivingEntity.flyingTick`: `A6B70CF51E2A4F3E4BD14CE0189793795AD590A73A4BDACC4CE0DA11992F1FE0` for 1.10–1.10.2; `0D7580A470858A9FA8A7E1EB3BF75816AAF9310D0BFAD1D1ADA3952389A4019F` for 1.11–1.12.2.
- ServerPlayNetworkHandler.handlePlayerUseBlock: 53FFF0FB505B7E0CDAD1C6D8E911A81C0A0F17DFE351E8B39176C020FF0D2C8A for 1.10–1.10.2; A1A1DB7EB9605E1091DABE89588ABEE9E96EFA88DCE89EB7C8CF7B7A3EFB6539 for 1.11–1.11.2; C5F8695CB6EF71CCDD487365B032B9EDA2E501414457BFA019E5F0D2FE37F1 for 1.12–1.12.2.
- `ClientPlayNetworkHandler.handlePlayerSleep` and `handleEntityData`: respectively `766385E6F5B62F74FC452AA10917E4260C8D8C39D98EDA3900F8F30FC854B981` and `D3B210200B63B9423EAC76F29075AA93FF940200799066F627BC1852FB2809AC` for all nine aliases.

## Source trace

1. **Jump edge to authoritative flag.** `LocalClientPlayerEntity` sends `START_FALL_FLYING` only after the jump-edge/airborne/descending/usable-Elytra checks. Representative slices: 1.10 `LocalClientPlayerEntity.java:730–737`; 1.12.2 `:764–771`. The server handler validates airborne, descending, not already fall-flying, not in water, and usable Elytra before invoking `ServerPlayerEntity.setFlying()`: 1.10 `ServerPlayNetworkHandler.java:845–854`; 1.12.2 `:877–886`. `ServerPlayerEntity.setFlying()` sets shared flag 7. The handler's `START_FALL_FLYING` case has no sleeping predicate.

2. **Ordinary successful bed path.** `ServerPlayNetworkHandler.handlePlayerUseBlock` forwards an ordinary block-use packet when distance squared is `< 64.0`, the player is not awaiting a teleport, and spawn-protection/world-border checks pass: 1.10 `ServerPlayNetworkHandler.java:591–608`; 1.12.2 `:630–648`. This method has no `onGround` gate; an otherwise eligible bed's narrower range fits within that normal interaction bound. `BedBlock.use` then calls `trySleep` on the ordinary bed-use branch: 1.10 `BedBlock.java:61–77`; 1.12.2 `:74–90`. `PlayerEntity.trySleep` checks the natural dimension, daytime, bed distance, and nearby hostile mobs. It never checks `onGround` or `isFallFlying`; after admission it sets `sleeping=true` and zeroes all velocity components, with no flag-7 clear: 1.10 `PlayerEntity.java:1180–1245`; 1.12.2 `:1185–1242`. The 1.12 range helper accepts either the clicked bed tile or the facing/opposite tile within the distance bounds (`:1245–1252`). This verifies only the admission conditions needed here, not a wider bed-eligibility audit.

3. **Server retention and later pose sample.** `LivingEntity.flyingTick` retains a true flag while airborne, unmounted, and wearing a usable Elytra; it writes the recomputed flag only on the server and has no sleeping test: 1.10 `LivingEntity.java:1754–1772`; 1.12.2 `:1854–1872`. The player sleep-time branch does not return before `super.tick()`, and `PlayerEntity.tick()` calls `updatePlayerPose()` afterward: 1.10 `PlayerEntity.java:185–205, 250`; 1.12.2 `:184–204, 245`. Thus sleeping itself does not bypass the later flight-flag maintenance or selector call. Grounding, mounting, or losing a usable Elytra remains a server-side clear path.

4. **Delivery and main-thread order.** Dirty synced data is sent as `EntityDataS2CPacket`; `TrackedEntity.sendPacketToAll` also sends to the tracked server player's own network handler (1.10 `TrackedEntity.java:264–297`; 1.12.2 `:264–299`). The sleep response is sent after successful `ServerPlayerEntity.trySleep` (1.10 `:515–523`; 1.12.2 `:600–608`). On the client, metadata updates `SyncedData` (1.10 `ClientPlayNetworkHandler.java:447–452`; 1.12.2 `:473–479`), while the sleep handler calls local `trySleep` (1.10 `:749–752`; 1.12.2 `:775–778`). Both handlers call `PacketUtils.ensureOnSameThread`.

   In `PacketUtils.ensureOnSameThread`, a non-client-thread packet handler is enqueued and the handoff sentinel is thrown; `Connection.channelRead0` catches that sentinel after invoking the packet. `Minecraft.executeTask` appends to its `ArrayDeque` task queue (`1.10 Minecraft.java:2510–2535`; `1.12.2 :2565–2590`). `runGame` polls that queue to empty before entering the tick loop (`1.10 :831–859`; `1.12.2 :876–897`), and `Minecraft.tick()` calls `world.tickEntities()` (`1.10 :1413–1439`; `1.12.2 :1443–1469`).

5. **Local clear audit.** `LocalClientPlayerEntity.onDataValueChanged` reacts to flag 7 by playing the Elytra sound; it does not write the flag (1.10 `LocalClientPlayerEntity.java:501`; 1.12.2 `:528–530`). `LivingEntity.flyingTick` writes flag 7 only when `!world.isClient`. The local `PlayerEntity.moveRelative` clear is guarded by `abilities.flying && !isRiding()` (`1.12.2 PlayerEntity.java:1386–1401`); it does not apply to the ordinary survival witness with ability flight off. A server-synchronized clear remains possible and is covered by the conditional verdict.

## Remaining gap

No runtime result is claimed. A specific physical fall/use window and the arrival time of any later server clear relative to the next client tick are conditional; source alone cannot establish that a given session realizes the opportunity. Close that only with separately authorized gameplay/network verification if a runtime-occurrence claim is required. The bounded source question is complete.