# 1.21.1–1.21.3 ServerPlayer teleport and correction call-site inventory

## Scope

Source-only inventory of direct ServerPlayer position-correction writers, paired overloads, and direct call routes that can apply to a player. It reuses the accepted endpoint comparison and 1.21.2 boundary packet; it does not reopen the accepted relative-delta interpretation or client receiver. This inventory does not claim full-pair coverage, runtime behavior, or that each event occurs during ordinary gameplay.

## Exact source identity

Read-only Mojmap roots: `decompiled_minecraft/1.21.1/mojmap/` and `decompiled_minecraft/1.21.3/mojmap/`. Both ready markers state the exact version, `mapping=mojmap`, and `status=ready`. At each endpoint, the ready marker's source-manifest digest matches the adjacent `mojmap.sources.sha256`; the provenance and artifact manifest hashes are also bound by the ready marker. Each cited Java source below was hashed from the canonical tree and matched its relative path entry in that source manifest.

| Endpoint | Ready marker SHA-256 | Source manifest SHA-256 | Provenance SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 1.21.1 Mojmap | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` | `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` |
| 1.21.3 Mojmap | `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` |

The already accepted 1.21.2 boundary input binds mapping-local `decompiled_minecraft/1.21.2/mojmap/.decompile-provenance.json` (SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`). I did not re-inspect 1.21.2 or renew that boundary verdict.

## Paired ServerPlayer packet writers

`ServerPlayer.java` hashes are `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` (1.21.1) and `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` (1.21.3); both match their endpoint source manifests.

| Route in ServerPlayer | 1.21.1 | 1.21.3 | Call/eligibility boundary |
| --- | --- | --- | --- |
| Dimension transition | `changeDimension(DimensionTransition)`, lines 840–877: `connection.teleport` at 853 for same-dimension transition and 875 after cross-dimension placement; both followed by `resetPosition`. | `teleport(TeleportTransition)`, lines 1029–1071: packet at 1046 for same dimension and 1069 after cross-dimension placement; both followed by `resetPosition`. Unless `asPassenger`, the newer path first stops riding. | Rejects removed player. Cross-dimension path sends respawn data and moves the player before correction. This comparison does not enumerate every portal/event producer of these APIs. |
| Sleep stop | `stopSleepInBed`, lines 991–999; after `super`, sends current position/rotation if `connection != null`. | `stopSleepInBed`, lines 1191–1199; same gate and packet payload. | Reached by player wake/sleep-stop paths; server-level wake and packet-listener wake call this method. No endpoint difference claimed here. |
| Absolute coordinate teleport | `teleportTo(double,double,double)`, lines 1400–1402; sends absolute target and `RelativeMovement.ROTATION`. | `teleportTo(double,double,double)`, lines 1594–1596; sends target, zero delta/rotations, and `Relative.union(Relative.DELTA, Relative.ROTATION)`. | Existing accepted source report establishes the endpoint response difference, and the accepted boundary packet places it in 1.21.2. This report only inventories routes into the writer. |
| Relative coordinate teleport | `teleportRelative`, lines 1404–1406; sends current position plus offset with `RelativeMovement.ALL`. | `teleportRelative`, lines 1598–1601; sends offset and zero delta/rotation with `Relative.ALL`. | `Block.pushEntitiesUp` invokes this virtually for entities intersecting a nonempty block-shape difference; a player reaches the ServerPlayer override. This packet-format route is an unreviewed direct-response dependency; no changed consequence is asserted. |
| Same/cross-level flagged teleport | `teleportTo(ServerLevel,...,Set<RelativeMovement>,yaw,pitch)`, lines 1410–1425: adds a post-teleport chunk ticket, stops riding and sleep, uses `connection.teleport` on same level, otherwise delegates to the six-argument player overload at 1421; sets head rotation. | `teleportTo(ServerLevel,...,Set<Relative>,yaw,pitch,boolean)`, lines 1604–1620: tickets destination, stops sleep, optionally resets camera when boolean is true, calls `super.teleportTo`, then updates head rotation on success. | Old same-level packet is direct. New super route uses the newer teleport transition path. Commands and spectator/camera paths below reach these overloads; other callers are not closed here. |
| Convenience level teleport (old signature) | `teleportTo(ServerLevel,x,y,z,yaw,pitch)`, lines 1661–1669: resets camera, stops riding, sends a same-level correction; otherwise calls `changeDimension`. | This overload is absent; callers use flagged overload / `TeleportTransition` APIs. | Old overload is invoked by `setCamera` and the old flagged overload's cross-level path. |
| Successful mount correction | `startRiding`, lines 1858–1870: after `super.startRiding` succeeds, zeroes known movement, positions on vehicle, and sends current absolute position/rotation. | `startRiding`, lines 2115–2127: after success, positions on vehicle and sends structured packet with absolute position and relative rotation. | Only successful mount. This is a direct position correction not routed through `teleportTo`; downstream riding transitions remain outside this inventory. |

`rg` across both complete endpoint trees found no additional `ServerPlayer.java` `connection.teleport(...)` expressions beyond those listed. `connection.resetPosition()` in `moveTo`, `setCamera`, and level-transition paths resets the server movement baseline but is not itself an outgoing position packet.

## Direct callers and conditions

- **Spectator target teleport:** `ServerGamePacketListenerImpl.handleTeleportToEntityPacket`, 1.21.1 lines 1131–1141 and 1.21.3 lines 1144–1155. After packet thread enforcement, only a spectator enters the search; the API call occurs only when the entity is found in a server level. It calls the old six-argument player overload and new flagged overload (with `true`).
- **Teleport command:** `TeleportCommand.performTeleport`, 1.21.1 lines 258–282 and 1.21.3 lines 252–274. The receiver is an `Entity`, so ServerPlayer behavior applies when selected subject is a player; it is not player-exclusive. Both reject out-of-spawnable-bounds positions first. The new path derives relative position/rotation offsets before calling the new overload. Post-success look-at, fall-flying check and movement/on-ground handling are command follow-up.
- **Spread players command:** `SpreadPlayersCommand` lines 226–240 in both endpoints iterates an `Entity` list and calls the level teleport overload; a player subject uses ServerPlayer behavior, but the operation is not player-exclusive. The newer call adds the boolean `true` argument.
- **Camera routing:** `ServerPlayer.setCamera` acts only when camera entity changes. If the selected camera is in a `ServerLevel`, it teleports the player to that entity's position; old calls the convenience level overload (1.21.1 lines 1601–1615), new calls flagged overload with `false` (1.21.3 lines 1822–1836), avoiding camera-reset recursion. Direct callers include spectator attack target selection, game-mode changes, and player tick dead-camera / wants-to-stop-riding branches (1.21.1 lines 503–513, 1456–1471, 1625–1630; 1.21.3 lines 663–674 and corresponding game-mode/attack methods). Also, `SpectateCommand.spectate` calls `setCamera(target)` at line 59 in both endpoints, after rejecting self-targeting and requiring the executor player to already have `GameType.SPECTATOR` (lines 50–59); its nullable target can clear the camera. New flagged overload resets camera only when its boolean is true.
- **Random teleport:** `LivingEntity.randomTeleport` invokes virtual `teleportTo(x,y,z)` for a candidate and, if validation fails, to restore original coordinates (1.21.1 lines 3180–3213; 1.21.3 lines 3279–3311). It requires a loaded chunk, searches down to a motion-blocking block, and accepts only a no-collision/no-liquid destination. In 1.21.1 the direct caller found is `ChorusFruitItem.finishUsingItem` (lines 21–38), server-side and up to 16 attempts after passenger dismount. In 1.21.3 the direct caller is `TeleportRandomlyConsumeEffect.apply` (lines 42–57), up to 16 attempts after passenger dismount; its target type is `LivingEntity`, so a player can be targeted, but item-component assignment is outside this slice. Enderman/cat random-teleport callers are non-player routes and excluded.
- **Block-shape push-up:** `Block.pushEntitiesUp` (1.21.1 lines 130–140; 1.21.3 lines 128–138) computes added collision-shape region and calls `Entity.teleportRelative(0,1+dy,0)` on intersecting entities when nonempty. A player in that list dispatches to `ServerPlayer.teleportRelative`; specific block-state writers invoking the helper are not inventoried here.

The full-tree `teleportTo` search also found inherited Entity/LivingEntity implementations plus the command, spectator, ServerPlayer, and random-teleport sites described above. Non-player Enderman/Cat random-teleport routes and generic entity teleports do not establish further player callers. `ServerPlayer.dismountTo` removes the vehicle and calls `setPos` rather than sending a packet, so is not a correction-writer route here.

## Source file identities checked

Each listed Java source was hash-checked against its endpoint `mojmap.sources.sha256`; hashes below are exact source-byte SHA-256 values.

| Relative path | 1.21.1 SHA-256 | 1.21.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/server/level/ServerPlayer.java` | `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |
| `net/minecraft/server/commands/TeleportCommand.java` | `176096e6b7477da7a411d076f748c13531f0647e547f580474b3fc36a755afac` | `33e4741d89446ad3fae4991858a23a69780177fa649764dc39cf49e7a8ec2b5e` |
| `net/minecraft/server/commands/SpreadPlayersCommand.java` | `eed8ed9b1c129a46ea6620ca612c0a199c31321fc6b27e3afb728e7bf9f895e4` | `92a15a27bdabc9943632789c440900197c4ab13783e97f3de49d7461c8236f55` |
| `net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/world/level/block/Block.java` | `6502b61ce4d667501b4e4889eff3aa337e3f66c0891232d5b679b181782acd0f` | `4ba7c916ae7c4ee9174c468e9a1f56218d2bcac16f74dd6080c561f945ea4d3a` |
| `net/minecraft/server/level/ServerLevel.java` | `a87ef1125c53a0ada419f583d17e841402235ca569994220ec8a63baa5457181` | `c664c8ff5807eed64b5f627afd6908f5baccf4acab504ef4dab6e42ad2fef00d` |
| `net/minecraft/server/commands/SpectateCommand.java` | `b1166db677fcc3dd9542fdce6acf54b99c0636dd06332617e0e7cd53a239efb0` | `b1166db677fcc3dd9542fdce6acf54b99c0636dd06332617e0e7cd53a239efb0` |
| `net/minecraft/world/item/ChorusFruitItem.java` / `net/minecraft/world/item/consume_effects/TeleportRandomlyConsumeEffect.java` | `5c26e5a0eac8e6bf962ac0e24efd3c9caa3f572d335b5a1dfaae09810d127ff7` | `cc305f084afdffe844378505e3be439739328f83dee9c0c7d1ab521bf8146916` |

## Remaining gates

This closes only endpoint caller inventory around ServerPlayer teleport and direct correction writers. It does not inspect every portal, item-component, block-state or riding-state producer that can reach these calls, every transition/correction payload dependency, or ordinary player tick/travel. The accepted 1.21.1→1.21.2 boundary for relative-delta flags was not reopened. An independent reviewer should audit this immutable inventory for caller completeness and exact per-call eligibility. Pair coverage remains partial.
