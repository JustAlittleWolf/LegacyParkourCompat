# Independent source-only audit: 1.8.3 → 1.8.4 residual movement claims

## Scope and limits

This audit independently checked the assigned residual report and its inventories, then re-traced the changed client source paths, `VineBlock.tick`, and the portal relocation-to-client packet path against the canonical source tree at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`. It did not inspect product code, Git history, wiki material, prior-pair audit files, or other campaign/backlog records. It is not a full-pair coverage audit, source acceptance, parity result, or freeze.

## Identity and inventory verification

The audited report is `.native-source/20261028/frontier-c/183-184-residual-movement/report.md`; its on-disk SHA-256 is `f2f85733cfb97f091ae417bf3c7d6060f25ec8b04d1940fcb0885fc9c58d4f84`, matching the assigned identity and its `.sha256` sidecar.

The two source roots are `1.8.3/ornithe-feather/` and `1.8.4/ornithe-feather/`. Their `.decompile-provenance.json` hashes independently recalculate to:

- 1.8.3: `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed`
- 1.8.4: `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`

Each provenance record identifies Ornithe Feather Gen2 build 2 for its named release. The input maps name the `1.8.3+build.2-mergedv2.jar` and `1.8.4+build.2-mergedv2.jar` artifacts and the corresponding Feather metadata URLs; their embedded SHA-256 values match the report. Each manifest lists 1,612 Java sources. The report's CSV inventories each contain 1,612 rows, the disk trees each contain 1,612 Java files, all listed byte lengths and SHA-256 values match disk, and the inventory hashes match the corresponding provenance source entries. The source path sets are identical. Recomputing hash differences yields exactly 45 paths, matching all 45 data rows in `changed-source-paths.csv`; the listing matches the recomputed differences exactly. No report, manifest, or per-file inventory hash/size mismatch was found.

## Changed client sources

All nine changed `net/minecraft/client/` paths in the inventory were diffed directly:

- `Minecraft.java`, `DebugOverlay.java`, `TitleScreen.java`, and `ServerListEntry.java` change displayed/reported version strings.
- `Screen.java` restricts the Ctrl cut/copy/paste/select-all predicates to cases without Shift or Alt.
- `MultiplayerServerListPinger.java` adds duplicate-status handling and rearranges server status, player-list, favicon and ping bookkeeping.
- `ItemRenderer.java` adds front-face culling for model transformations with an odd number of negative scale components; `ModelTransformation.java` permits negative scale values in its clamp.
- `TextureAtlas.java` changes a mip-level diagnostic from debug to warning.

These are the complete changed client-path list from the independently verified 45-path inventory. None changes local-player tick/input/travel, a movement predicate, player position/velocity/box writes, collision queries, or a client-side response to a movement event. The network-pinger edit processes server-list status packets, not the play-handler movement packet. The report's “no changed client-local movement path” conclusion is confirmed for the changed client files.

## Vine growth candidate

`net/minecraft/block/VineBlock.java` is changed only within `tick` (method starts at line 198 in both versions). Both versions enclose the scheduled growth logic in `if (!world.isClient)` (line 199), and block-state writes in this method therefore occur only in a logical server world. The changed code is not only temporary-name cleanup: in the branch where the block below is already a vine, 1.8.3 conditionally clears that vine's horizontal properties (`1.8.3` lines 254–269), while 1.8.4 conditionally sets properties true using a different `random.nextBoolean() && state-property` gate (`1.8.4` lines 255–270). The side-growth block is also re-expressed with adjusted local positions (`1.8.3` lines 272–292; `1.8.4` lines 273–293).

The unchanged `LivingEntity.isClimbing` reads the block identity at the player's feet and treats a vine as climbable subject to the spectator gate (`LivingEntity.java:794–800` in both releases); it does not inspect vine face properties. `VineBlock.getCollisionShape` returns `null` (`VineBlock.java:127–128` in both releases), and the full file diff has no change to that method or `updateShape`. Thus the changed operation is server-side world growth. A server-generated change to whether a vine block exists can later be observed by the client and can affect the unchanged climbing predicate; the report's phrasing that no direct client movement consequence is established is appropriately cautious. This review does not establish a reachable movement divergence from growth, nor does this server-only producer become a historical client movement implementation point under the project invariant that historical movement does not run on logical servers.

## Portal placement and received client response

The exact changed producer is `net/minecraft/server/world/PortalForcer.java`, not a client class. In the found-portal branch (`1.8.3` lines 100–123; `1.8.4` lines 100–121), both versions begin from the portal block center and calculate the same portal-relative position. 1.8.3 additionally applies `match.getForward().getOffsetX()` to `f` for an X-axis-forward portal (line 118), or `getOffsetZ()` to `h` for the other axis (line 122); 1.8.4 omits both additions. The chosen position is written to the entity at `1.8.3:148` / `1.8.4:146`.

The server-side provenance of this write is explicit: `Entity.changeDimension` is guarded by `!this.world.isClient` (`Entity.java:1657–1673`), then calls `PlayerManager.changeDimension`. The player-manager path calls the destination world's `PortalForcer.onDimensionChanged` (`PlayerManager.java:498–513`) and later calls `player.networkHandler.teleport(player.x, player.y, player.z, ...)` (`PlayerManager.java:435–451`). `ServerPlayNetworkHandler.teleport` updates the server player and sends those coordinates in `PlayerMoveS2CPacket` (`ServerPlayNetworkHandler.java:353–381`). On receipt, the unchanged client `ClientPlayNetworkHandler.handlePlayerMove` applies the absolute/relative values to `minecraft.player` through `updatePositionAndAngles` (`ClientPlayNetworkHandler.java:517–556`). Therefore the server-produced portal placement does reach and update the local client player's position. The handler and packet are unchanged between 1.8.3 and 1.8.4.

Applicability has two separate layers: the *client receipt/application* is a direct player-state response, while the *release delta being audited* is calculated by server portal-placement code and carried as authoritative coordinates. The client does not recompute the portal placement. The packet path confirms an observable local-player position difference when an older versus newer server sends its respective target, but it does not establish a changed client movement rule that should override server coordinates. Under the project's server/client invariant, this source delta should remain recorded as a server-authoritative placement boundary; whether the broad discovery ledger tracks that state outcome as an in-scope direct response remains a scope decision, not evidence of client parity or an implementation-ready client hook.

## Omissions and verdict

No hashes, inventory rows, or source identities are missing or inconsistent. The residual report did not name the transport continuation (`ServerPlayNetworkHandler.teleport` → `PlayerMoveS2CPacket` → `ClientPlayNetworkHandler.handlePlayerMove`); this audit supplies those exact source references and confirms the consumer is unchanged. Its Vine summary also did not spell out the property-write gate change; this audit records it above. The report's references to the previous 1.8.2 → 1.8.3 audits were not reopened here, and the other 34 non-client changed paths were not method-by-method retraced because they were outside this bounded assignment. These limits keep this verdict at independent residual-claim verification only. No pair freeze, source acceptance, runtime-parity claim, or implementation-readiness claim follows.
