# Natural sleeping and fall-flying overlap: source gate

Status: COMPLETED. Read-only source review of ordinary local-player tick, successful bed response, fall-flying authority, and synchronization for the stable 1.10–1.12.2 aliases. Compared only the supplied alias and resize notes plus exact canonical decompiled Feather Gen2 build.2 sources. No tests, runtime, live packets, artificial data, product sources, Git, wiki, or primary research writes.

## Verdict

No source-level exclusion exists. Exact sources support an ordinary sequence that can put the authoritative player state into both predicates: a descending local player with a usable Elytra sends `START_FALL_FLYING`; the server handler accepts it when airborne, descending, not already fall-flying and not in water, and sets shared flag 7. A subsequent successful bed interaction calls `trySleep`, which sets `sleeping=true` and zeroes velocity without clearing flag 7. The server's `flyingTick` does not test sleeping; it preserves flag 7 while the player is still airborne, unmounted, and has a usable Elytra. The ordinary player tick continues through `updatePlayerPose`, whose first branch gives fall-flying precedence.

The client does not authoritatively set flag 7. Its matching local input condition sends a request; its `isFallFlying()` reads shared flag 7. The successful `PlayerSleepS2CPacket` handler calls `trySleep` on the local player, setting its local sleeping state. The tracked entity path sends dirty shared data including flag 7 back to tracked clients (including the player). Thus the two inputs have ordinary authoritative update paths. However, inspected source establishes no strict client tick boundary between processing the sleep response and processing the tracked flag update. Asynchronous delivery/main-thread task order leaves the exact first local tick that samples both predicates unproven. Result: **ordinary server overlap is source-supported; local tick observation is possible but not closed by these sources alone.** No evidence supports exclusion.

### Bounded sequence and gate

1. While descending with a usable Elytra and `onGround=false`, a normal local jump edge meets `LocalClientPlayerEntity`'s request condition. The request itself does not set flag 7 locally.
2. If the server handles that request before the bed interaction, `ServerPlayNetworkHandler` sets flag 7. The server's successful bed path has no on-ground eligibility check; once admitted, `PlayerEntity.trySleep` does not clear flag 7 and zeros velocity. This is a source-supported ordinary server order, not injected state.
3. `LivingEntity.flyingTick` runs during the ordinary entity travel step and retains flag 7 under the existing airborne/unmounted/usable-Elytra gates; there is no sleeping predicate in this gate. `PlayerEntity.tick` still calls `updatePlayerPose` afterward.
4. Server sleep response handling sets the local player's `sleeping` field. Server shared-data tracking synchronizes flag 7. `PlayerEntity.updatePlayerPose` reads the predicates separately and has no mutual-exclusion guard.

The open gate is narrow: establish the main-thread packet/tick ordering for the local player and the precise server tick/dirty-tracker flush order in each relevant alias. This source review does not establish whether a client tick can occur between those two state updates, or the first tick after both are present. Do not mark natural local overlap excluded or proven until that order is resolved. A source-only continuation should trace the authoritative server tick loop and client packet-task drain against these handlers; do not infer it from packet names or server-side state alone.

## Exact sources and identities

All source files are from `decompiled_minecraft/<version>/ornithe-feather/`, Feather Gen2 `<version>+build.2`, verified using each exact version's manifest/sidecar. The supplied earlier `stable-aliases.md` authenticates the pose-selector source hashes and exact release mapping artifacts. Here are the provenance file SHA-256s that bind the synchronization slices for every assigned stable alias:

| Version | Provenance/manifest SHA-256 |
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

The synchronization source classes are `entity/living/player/PlayerEntity.java`, `entity/living/LivingEntity.java`, `client/entity/living/player/LocalClientPlayerEntity.java`, `client/network/handler/ClientPlayNetworkHandler.java`, `server/network/handler/ServerPlayNetworkHandler.java`, `server/entity/living/player/ServerPlayerEntity.java`, and `server/TrackedEntity.java`. Across 1.10–1.10.2, the latter six synchronization files are byte-identical within their respective class families; 1.11–1.11.2 likewise; 1.12 and 1.12.1 are byte-identical, with 1.12.2 client-local-player and network handler class changes that retain the observed request, handler, and sleep-response operations. All nine `PlayerEntity.updatePlayerPose()` method bodies have the supplied stable-alias digest `848A1FE07E3D14340968AB4BA35455662DEE494E8ABB7FEA21D944E0555DEB31`.

Representative exact-file digests (full source-file SHA-256):

- 1.10 `LocalClientPlayerEntity.java`: `A9637065F21AD67464EB5C204C74EBF228C3BB0DA8A96DDF4AE73C0490FED443`; `LivingEntity.java`: `D40DD476B6B68C6CE45B4202823475DEB546ECDA2284DA330FF6724B33815E82`; `ServerPlayNetworkHandler.java`: `14B537A3F11E57D8364E90508F45B996B7E0593B741064167D85256166A287C6`; `ClientPlayNetworkHandler.java`: `AF3EFBA0A1D5A0824C29C4AF4AC7F8E4484D1787A82208E4D72BCC65F957E656`; `ServerPlayerEntity.java`: `DE500C8BF8E82A3F89D3A7F7208F3EED3D47F616344FB1E72AAACFD9E331A03F`; `TrackedEntity.java`: `842CF1310C7C0A57E63C1A82D0DDDC3BFB96913DAC684EA5340A32CF26051090`.
- 1.12.2 `LocalClientPlayerEntity.java`: `01A58E94D8C6FF98A8E3794227CDC76A5FCBDBAD795C70C9CF28854AFF9823CC`; `LivingEntity.java`: `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6`; `ServerPlayNetworkHandler.java`: `77BF65B2C48FF952713942E183AF1CD5FB243AD4F8FD2E53AA1B97272E7ACD7C`; `ClientPlayNetworkHandler.java`: `FCE21D9902E555FD46545FB04BDB6C4A912EEF6398790776AD1A122E09960C7C`; `ServerPlayerEntity.java`: `AD4EFFC65EDD98098D86AF8BA725F6D6C2C94068F996739CF2734D34ECF804A9`; `TrackedEntity.java`: `1BA9B26E88E9FC278C667B4311105BBFE45D1B58D6FFA015E02A56AD1C3E2CDC`.

## Key source slices

- Local request: 1.10 `LocalClientPlayerEntity.java:730–737`; 1.12.2 `:764–771`. `START_FALL_FLYING` requires jump edge, airborne, descending, currently not fall-flying, and not abilities-flying; it sends a C2S action and then reads `isFallFlying()` for local `falling` state.
- Server authority: 1.10 `ServerPlayNetworkHandler.java:845–854`; 1.12.2 `:877–886`. The start action gate is `!onGround`, `velocityY < 0`, `!isFallFlying()`, `!isInWater()`, plus valid Elytra; it calls `setFlying()`, which sets flag 7. No sleeping rejection appears in this action case.
- Sleep state: 1.10 `PlayerEntity.java:1180–1245`, `ServerPlayerEntity.java:515–523`, `ClientPlayNetworkHandler.java:748–752`; 1.12.2 `PlayerEntity.java:1185–1242`, `ServerPlayerEntity.java:600–608`, `ClientPlayNetworkHandler.java:774–777`. Successful server sleep directly emits `PlayerSleepS2CPacket`; the client handler calls `trySleep`, which sets `sleeping=true`, zeroes velocity, and does not clear flag 7. There is no on-ground test in successful bed admission path.
- Server retention/tick: 1.10 `LivingEntity.java:1754–1772`; 1.12.2 `:1854–1872`. `flyingTick` retains flag 7 for an airborne, unmounted entity with usable Elytra and writes flag 7 on the server; it does not check sleeping. `PlayerEntity.tick` calls `updatePlayerPose` at 1.10 line 250 and 1.12.2 line 245.
- Client desired selector: 1.10 `PlayerEntity.java:296–320`; 1.12.2 `:291–315`; both check `isFallFlying()` before `isSleeping()` with no mutual-exclusion guard. The supplied alias source records identical selector bodies across all nine aliases.
- Server data synchronization: 1.10 and 1.12.2 `TrackedEntity.java:159–174, 264–276` (line positions may differ by alias): dirty synced data triggers `EntityDataS2CPacket` broadcast. `sendPacketToAll` includes the server player’s own network handler. This establishes authoritative delivery, but not the relative client tick at which delivery is observed against the direct sleep response.
