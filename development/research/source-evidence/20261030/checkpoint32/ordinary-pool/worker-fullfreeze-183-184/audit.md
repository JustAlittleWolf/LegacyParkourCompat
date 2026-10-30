# Full-pair source closure audit: 1.8.3 → 1.8.4

## Verdict

**Freeze is justified for the complete 1.8.3 → 1.8.4 pair under equal effective movement-relevant client/player/world inputs, default vanilla registrations, and the project boundary that excludes logical-server simulation and server-produced exit-position payloads.** The whole-tree source inventory has identical 1,612-path Java sets and 45 changed source files. The existing exact-source coverage for the ordinary player tick and movement graph is reused as requested; every changed path is accounted for below, the changed client paths were checked for movement operations, and residual Vine/Portal/resource dependencies now have independent closure evidence. No changed client movement terminal or changed same-input movement provider/consumer remains open.

This freeze does not assert parity when received player/world state differs, include server simulation or PortalForcer exit-coordinate production, cover custom/modded registries/resources, establish intermediate patch boundaries, or prove runtime parity. It records source-pair coverage only.

## Exact inputs and evidence identity

The exact read-only trees are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.3/ornithe-feather/` and `.../1.8.4/ornithe-feather/`, both Ornithe Feather Gen2 build 2. Provenance manifest SHA-256: 1.8.3 `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`; 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`. The manifests identify mapped artifact hashes `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` and `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c`. Client input JAR hashes are `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` and `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b`.

Reused source inventory artifacts:

- `.native-source/20261028/frontier-c/183-184-residual-movement/1.8.3-source-hashes.csv` — SHA-256 `0a7f78f35921f95c1858171a36a1f2dc730454216d8fab63a42c1ecaeb00fbe9`.
- `.native-source/20261028/frontier-c/183-184-residual-movement/1.8.4-source-hashes.csv` — SHA-256 `b326587565cb8eeddebab8a58f1e387c90168c0f4f590f77341b38b10a1529b8`.
- `.native-source/20261028/frontier-c/183-184-residual-movement/changed-source-paths.csv` — SHA-256 `beb915407587241b495609b04916f89907d8795136cc1e7c22f23b96866cd197`.

Report SHA-256 identities:

- Residual report `.../183-184-residual-movement/report.md`: `f2f85733cfb97f091ae417bf3c7d6060f25ec8b04d1940fcb0885fc9c58d4f84`.
- Independent residual audit `.../183-184-residual-movement-audit/audit.md`: `9a1ef8ae8010724c9591c10c000d93b4d9e8f4660f7245bfa3b17e9366e6c6ac`.
- Same-input closure report `.../183-184-same-input-closure/report.md`: `c0affec7df11b514b36c12f96322f756437713d656365b32946ef5d971b5910d`.
- Independent review `.../183-184-same-input-closure-review/review.md`: `e0faa42f2fac4a6ae9cff229553c72936ad6b9db3a07569b1e346dafa2ca74dc`.
- Vine/Portal/resource audit `.../worker-183-184-vine-portal-resources/audit.md`: `6187d6f704d3ce06557ded803a0ec611682f98a512e50cab37836cc463a8bcef`.

The initially named `resource-correction-supplement.md` is not present in the supplied closure directory. Its correction is instead recorded in the independent `same-input-closure-review/review.md`, and the more recent Vine/Portal/resource audit independently resolves it: provenance-matched 1.8.3/1.8.4 client archives were present and inspected; each has 3,085 asset entries and matching asset path sets, with only `assets/minecraft/lang/en_US.lang` content differing, and neither has `data/` entries. This corrects the earlier report's no-cache/no-archive-inspection statement. The resource audit verifies source default registrations and limits its resource conclusion to the packaged vanilla assets.

The cited prior full ordinary-movement inventory is the accepted 1.8.2 → 1.8.3 baseline identified in the residual report by audit SHA-256 `4876f16a74f682b3e1935568e3f7c88d365f58da9a59d9ac3fa11516e44e107c` and `c3c6a4c371868eff642ffd585b15dd7f7b6da43ae6255ca524d68f2c776f0bc6`. Those are reused as assigned; this audit did not reopen or re-hash that baseline.

## Complete changed-path disposition

The paired inventory records 45 changed Java paths and no added or removed Java path. All 45 are covered by these source-diff groups; the client-path details and the movement candidates are also independently covered by the residual audit and closure evidence.

| Changed paths | Movement/tick disposition |
|---|---|
| `block/DispenserBlock.java`, `block/DropperBlock.java` | Stack-size zero handling in item dispensing; no player position, velocity, collision, shape or player response operation. Any resulting world/entity outcome is externally produced state. |
| `block/spawner/MobSpawner.java` | Spawner NBT write guard for empty entity type; entity production/save behavior, not player movement. |
| `block/VineBlock.java` | Changed scheduled growth/property writes are guarded by `!world.isClient`; paired collision-shape and update-shape methods remain unchanged; identical `LivingEntity.isClimbing` tests vine block identity and spectator status, not vine face properties. Any changed received vine state is unequal input. Closed by exact dependency/resource audit. |
| `client/gui/overlay/DebugOverlay.java`, `client/gui/screen/menu/TitleScreen.java`, `client/Minecraft.java`, `client/options/ServerListEntry.java` | Displayed/reported release strings only. |
| `client/gui/screen/Screen.java` | Tightens Ctrl shortcut predicates for cut/copy/paste/select-all; UI input only. |
| `client/network/MultiplayerServerListPinger.java` | Duplicate server-list status rejection/bookkeeping; not play movement packet handling. |
| `client/render/entity/ItemRenderer.java`, `client/resource/model/ModelTransformation.java`, `client/render/texture/TextureAtlas.java` | Rendering/model scale/culling and diagnostic logging only; no collision or movement consumer. |
| `entity/ai/goal/FollowOwnerGoal.java`, `entity/living/mob/passive/VillagerEntity.java` | Non-player AI/spectator-following and trade-index bounds; no local-player movement operation. |
| `nbt/NbtByte.java`, `NbtByteArray.java`, `NbtCompound.java`, `NbtDouble.java`, `NbtEnd.java`, `NbtFloat.java`, `NbtInt.java`, `NbtIntArray.java`, `NbtList.java`, `NbtLong.java`, `NbtShort.java`, `NbtString.java` | Read-budget accounting and malformed-list validation. These affect deserialization acceptance/cost; no tick, movement math, collision or direct response member changes. A differing decoded/saved player state is not an equal effective player-state input. |
| `realms/DisconnectedRealmsScreen.java`, `realms/RealmsSharedConstants.java` | Disconnected screen layout and reported version constant. |
| `scoreboard/SavedScoreboardData.java`, `scoreboard/Scoreboard.java`, `server/command/ScoreboardCommand.java` | Scoreboard name bounds/normalization; no movement tick or player movement state writer. |
| `server/command/handler/CommandManager.java`, `server/GameProfileCache.java`, `server/OpEntry.java`, `server/Ops.java`, `server/PlayerManager.java`, `server/MinecraftServer.java`, `server/integrated/IntegratedServer.java`, `server/network/handler/RemoteHandshakeNetworkHandler.java`, `server/Snooper.java`, `util/crash/CrashReport.java` | Console/op/player-limit, profile-cache, broadcast, protocol/version and server metadata behavior. No client movement simulation. `PlayerManager`'s dimension-transfer continuation is addressed with PortalForcer below. |
| `server/network/handler/ServerPlayNetworkHandler.java` | Changed server-side movement validation arithmetic is explicitly excluded by the invariant against logical-server movement replay. Its teleport method and the client receive/application continuation are unchanged, as established by exact hashes in the closure report. |
| `server/world/PortalForcer.java` | Found-portal exit X/Z offset changed; server-authoritative exit-position production, excluded under the explicit equal received-position rule. `PlayerManager` sends the computed result through the unchanged packet and client handler. |
| `world/chunk/storage/AnvilChunkStorage.java` | Chunk NBT save queue/concurrency implementation. It does not add a local-player tick/response/shape operation. Saved or loaded chunk differences would be received world-state differences; equal client block/player input leaves no local movement delta. |

All nine changed `net/minecraft/client/` paths are therefore accounted for: seven are presentation/rendering or UI operations; `MultiplayerServerListPinger` is status-query handling; none changes local-player tick/input/travel, player movement predicates, position/velocity/box writes, collision queries, or play-handler movement response. The changed server movement validator is a server-only policy operation. The portal exit coordinates are an authoritative server payload producer and are excluded per assignment; the unchanged packet/application behavior remains eligible in principle and was checked. No source side changes that client operation.

## Ordered movement closure and dependencies

1. Reuse the exact accepted 1.8.2 → 1.8.3 negative coverage cited above for local-player input/tick, entity/living/player movement, jump/travel, position/velocity/box, collision/world queries, fluids, direct block responses, effects/equipment, and network state writers. The full-pair Java path/hash inventory says the core `Entity`, `LivingEntity`, `PlayerEntity`, `Block`, `World`, `ClientWorld`, and `LadderBlock` sources are unchanged from 1.8.3 to 1.8.4.
2. Census and diff all 45 changed files against exact paired Ornithe Feather sources. All changed client files are closed to player tick/response terminals. The remaining changes are server/entity AI, serialization/storage, UI/rendering or administrative/metadata operations, subject to the specific movement-adjacent edges below.
3. Close the changed Vine provider edge from vanilla default registration (`Block` → `Blocks`) through `VineBlock` collision/shape and `LivingEntity.isClimbing`. Registration aliases and `PortalBlock` are identical; packaged archives provide no movement registry/data replacement. Under equal effective received block state, neither shape nor climb predicate differs.
4. Close the changed PortalForcer producer edge through `Entity.changeDimension` server gate, `PlayerManager`, server `teleport`, `PlayerMoveS2CPacket`, and unchanged `ClientPlayNetworkHandler.handlePlayerMove`. The changed server-produced target is an unequal received position; when the effective received position is held equal, no client movement algorithm differs.
5. Close changed chunk/NBT storage and NBT parser edges at the input boundary: these can affect disk/server-produced contents or whether malformed NBT loads, but do not alter a movement terminal for an equal effective client/player/world state.

The closure review and resource audit independently retraced the Vine and Portal edges and confirmed hashes for the listed exact source members. The full-path hash comparison itself is reused from the verified prior inventory rather than repeated as a broad hash sweep.

## Freeze record and bounded next queue

Complete pair freeze: **yes, conditional on equal effective movement-relevant received client/player/world state, default vanilla resources/registrations, and the stated exclusion of logical-server simulation and authoritative portal exit-position production.** No source findings are implied for server-only outcomes. The accepted original player movement inventory supplies the unchanged-terminal baseline; all changed-source paths and the two newly closed dependencies are paired and classified.

There is no remaining 1.8.3 → 1.8.4 source-closure queue item under that boundary. The next bounded queue is a different adjacent release pair or, only if scope is broadened, an explicit custom/modded registry/resource or server-produced-state investigation. Intermediate patch cutovers, runtime parity and implementation remain separate work.
