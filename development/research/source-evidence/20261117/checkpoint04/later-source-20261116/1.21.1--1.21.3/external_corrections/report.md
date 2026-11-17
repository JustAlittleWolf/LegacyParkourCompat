# S-EXTERNAL source review: received corrections

## Scope and verdict

Pair: Minecraft **1.21.1 Mojmap → 1.21.3 Mojmap**. This is a bounded source-only review of server-to-client entity-motion packets and player position-correction packets, their client dispatch/handlers, directly written movement state, and the packet-side server writers. No intermediate release was inspected, so the introduction boundary is unknown within the endpoint interval. This slice is not a pair freeze or runtime-parity claim.

Coverage verdict for the assigned slice: **examined with confirmed endpoint differences and bounded no-difference facts; producer-trigger and full external-event dependencies remain partial**. The broader pair remains partial as described by the supplied source-only projection.

## Exact source identity

Read-only source roots:

- `decompiled_minecraft/1.21.1/mojmap/`
- `decompiled_minecraft/1.21.3/mojmap/`

Both `mojmap.ready.json` markers report `status=ready`, the exact version and `mapping=mojmap`. The 1.21.1 marker reports 5,363 source files / 25,773,546 bytes and client SHA-256 `499f6897d1837516680f3114072d8106e11c9adcd933fe5cf051b551089b0c99`; the 1.21.3 marker reports 5,655 / 26,544,115 and client SHA-256 `25190c556bc56d11070638ccab3b3c6efd76bb9684c92a2680a4de01c3c7138b`.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 (actual = marker) | Artifact manifest SHA-256 (actual = marker) | Diagnostics SHA-256 (actual = marker) |
| --- | --- | --- | --- | --- | --- |
| 1.21.1 Mojmap | `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` | `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9` | `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` | `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` | `44c184cd1385e5b23991b2698ff0ae9f1e62a9491de8dcac27e5a3f9af88cd4f` |
| 1.21.3 Mojmap | `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` | `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7` | `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` | `065c0644d14a4ba2001713ba9a72470b414f01ab327aec772924709f3118f08f` |

The provenance markers identify Mojmap, Vineflower 1.12.0, Mapping-IO 0.9.1, Tiny Remapper 0.14.1, ASM 9.10.1, Gson 2.14.0, Gradle 9.7.1 and Java 25.0.3+9-LTS. Both markers record the same exact batched 1.21.1/1.21.3/1.21.4/1.21.5 Mojmap decompile run. Their recorded `outputPath` is under `build/movement-campaign-2026-10-07/ready/`; the current canonical source directories were checked against the ready-bound source-manifest hashes. Every source file listed below exists, and its computed SHA-256 matches its entry in the corresponding source manifest. Manifest entry counts match the ready markers. The ready markers also bind the artifact manifests and movement diagnostics to the hashes shown above.

## Ordered coverage and source facts

1. **Packet dispatch and client listener entry points.** In both versions, `ClientboundSetEntityMotionPacket.handle` dispatches to `ClientGamePacketListener.handleSetEntityMotion`; `ClientboundPlayerPositionPacket.handle` dispatches to `handleMovePlayer`. The interface declarations are at `ClientGamePacketListener.java:81,105` in 1.21.1 and `:87,113` in 1.21.3. `LocalPlayer.java` and `Player.java` contain no packet-handler override for either operation; the actual handlers are in `ClientPacketListener.java`.

2. **Received velocity packet — bounded no difference.** `ClientPacketListener.handleSetEntityMotion` (1.21.1 lines 517–523; 1.21.3 lines 537–543) performs the same order: ensure execution on the client thread, look up the packet entity by ID in `level`, and if it exists call `lerpMotion(packet Xa, Ya, Za)`. There is no local-player-only gate. `Entity.lerpMotion` in both versions forwards the three doubles to `setDeltaMovement`; the setter stores the `Vec3` directly. The client therefore replaces, rather than adds to, that entity's current motion.

   The packet class is byte-for-byte identical across the pair (same SHA-256 in both source manifests). Its writer clamps each source component to `[-3.9, 3.9]`, multiplies by `8000.0`, narrows to `int` (Java truncation toward zero), writes three signed shorts, and the getters divide each signed short by `8000.0`. `ServerEntity` and `MaceItem` are the observed server-side construction sites in both endpoint trees. This review checked their call-site presence and packet payload path, not every producer trigger, event formula, or entity-tracking eligibility condition.

3. **Received player position correction — changed gate and state calculation.** The 1.21.1 handler `ClientPacketListener.handleMovePlayer` (lines 620–691) always operates on `minecraft.player` after same-thread enforcement. For each position axis, the old `RelativeMovement.X/Y/Z` bit controls both the position and velocity rule: a relative position adds the packet component to current position, increments both the corresponding `xOld`/`xo` (and y/z equivalents), and preserves that component of the current `deltaMovement`; an absolute position assigns the packet coordinate to both snapshots and sets that velocity component to `0.0`. It then applies `setPos`, writes the three-component delta, applies absolute or relative pitch and yaw in that order, updates `xRotO`/`yRotO`, sends the movement acknowledgement, and sends a `PosRot` player movement packet.

   The 1.21.3 handler (lines 706–735) now skips applying the correction when `player.isPassenger()` is true, while still sending the `PosRot` response and teleport acknowledgement (lines 713–714). Otherwise it calls `setValuesFromPositionPacket(change, relatives, player, false)`. The helper (lines 717–735) derives a base `PositionMoveRotation` from the entity's lerp target, known movement and current rotations; calculates absolute values; sets current position, delta movement, yaw, then pitch; and reconstructs old position/rotation using `oldPosition()`, zero delta and old rotations before calling `setOldPosAndRot`. Because the local-player call passes `false`, its interpolation branch is unreachable; the helper's `>4096.0` position-distance threshold does not change this local correction into a lerp. The old handler has no passenger gate and no distance-based lerp branch.

   The new `Relative` enum (lines 10–24, 50–70) adds `DELTA_X/Y/Z` and `ROTATE_DELTA`; these are distinct from position `X/Y/Z`. `PositionMoveRotation.calculateAbsolute` (lines 35–63) independently adds relative position and rotation components, optionally rotates the base delta by pitch then yaw deltas (float radians passed to `Vec3.xRot` then `yRot`), and for each velocity axis either adds the packet delta to the base when its `DELTA_*` bit is set or replaces it otherwise. Consequently, endpoint source behavior differs for player corrections: passenger corrections no longer apply client-side in 1.21.3, and position-relative bits no longer implicitly preserve movement. Velocity preservation/addition is now selected by delta-relative flags, while absolute delta components come from the packet's `PositionMoveRotation`.

4. **Packet representation and server position writer dependency.** The 1.21.1 `ClientboundPlayerPositionPacket` (lines 10–88) stores separate double x/y/z, float yaw/pitch, a byte-packed five-value `RelativeMovement` set, and teleport ID. The 1.21.3 record packet carries ID, `PositionMoveRotation`, and a `Relative` set encoded through the new codec (the record and codec are lines 10–26); `PositionMoveRotation` adds a serialized `Vec3 deltaMovement` alongside position and rotations. These are not a wire-compatible packet representation across versions; the comparison concerns each version's native client behavior.

   The server correction writer also changes. In 1.21.1 `ServerGamePacketListenerImpl.teleport` (lines 989–1006) calculates relative position/rotation offsets, records the target, increments/wraps teleport ID, calls `player.absMoveTo`, and sends the adjusted primitive packet. In 1.21.3 its default overload and `teleport(PositionMoveRotation, Set<Relative>)` (lines 1007–1019) call `player.teleportSetPosition`, record the resulting position, and send the structured values/relative set. `Entity.teleportSetPosition` (1.21.3 lines 2721–2732) resolves relative state, writes position/rotations, resets old-position and rotation snapshots, writes delta movement, and clears `movementThisTick`.

   A concrete player teleport writer changes the velocity-relative flags: `ServerPlayer.teleportTo(double,double,double)` in 1.21.1 (lines 1400–1406) sends absolute position with `RelativeMovement.ROTATION`, which makes the old client handler zero all three delta components. In 1.21.3 (lines 1593–1600) it sends a zero delta payload with `Relative.union(Relative.DELTA, Relative.ROTATION)`. The new client helper adds that zero delta to current movement on all three axes, and the relative rotation is zero; thus this writer preserves the client's current velocity across the absolute-position teleport at the newer endpoint. This is a confirmed server-writer/client-handler endpoint difference, not evidence of the exact patch that introduced it.

5. **Direct entity state APIs and player inheritance.** The paired `Entity.setPos(Vec3)` overloads (1.21.1 lines 384–391; 1.21.3 lines 395–402) both unpack coordinates and update raw position/bounding box through the same operation sequence. `Entity.setDeltaMovement(Vec3)` and `lerpMotion` are identical for the examined bodies. `Entity.isPassenger()` (1.21.1 lines 2184–2186; 1.21.3 lines 2278–2280) remains `getVehicle() != null`; only 1.21.3's handler adds the gate. Old-position helpers differ in organization: 1.21.1 has `setOldPosAndRot()` directly copy position/rotation to snapshot fields (lines 1419–1431); 1.21.3 has overloads that copy an explicit `oldPosition()`/rotation tuple and set both `xo` and `xOld` to the chosen coordinates (lines 1472–1500). No `LocalPlayer` or `Player` correction-specific override was found in the manifest-verified files.

## Identity-verified source files

All paths below are relative to each version's `mojmap/` root. Actual content SHA-256 matched the same path's entry in `mojmap.sources.sha256` for each version.

| Source path | 1.21.1 SHA-256 | 1.21.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/world/entity/player/Player.java` | `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` | `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` |
| `net/minecraft/world/entity/Entity.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `03f4c58cb1f801cb62fe48a207a575b7bfd2a9fdcb5a0683bef3c0c9c75bed69` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` |
| `net/minecraft/network/protocol/game/ClientboundSetEntityMotionPacket.java` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` | `bc6c957cb33fd07b3b2b6e22f62aa15f9f68872e44d67b172bb0249f81950d91` |
| `net/minecraft/network/protocol/game/ClientGamePacketListener.java` | `3ffa78e79c23460b88a75d02cdecc96b18ebb70f35668530eaf258830455b8ca` | `abede3d8934bf5c2644e28585a435ae58fa41c832bdd5e5e8f94de8adcb20f98` |
| `net/minecraft/world/entity/RelativeMovement.java` / `Relative.java` | `7b12db600cde348858fe5835d7618a59b22ad44ed147351fc79b83b7ecaced4f` (`RelativeMovement`) | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` (`Relative`) |
| `net/minecraft/world/entity/PositionMoveRotation.java` | absent | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` |
| `net/minecraft/server/network/ServerGamePacketListenerImpl.java` | `cc73bb8fab95346aea10f7018ba7de6c723cde8a52a4ae40629b5370b6ff6d10` | `e4ad1ac2fa3236f659220c44dd4d5f63d9ef2ab5791bdabbfa2d229b14452a38` |
| `net/minecraft/server/level/ServerPlayer.java` | `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` |
| `net/minecraft/server/level/ServerEntity.java` | `0994216505c8df7cc67a7a068ae8e71d1f023dbef5dc1dc31360e3e5bdc2ce14` | `c45400e92eb0ad334212bda63549050259ca1cab74dd9a50ec2f2738f86fa3a6` |
| `net/minecraft/world/item/MaceItem.java` | `27e460170ddc3a9131bf10ef9449f2afe22116929607acaaa08523b97aa6aa01` | `3a5e624f9d1cec8aab92d1f107d518ab1219e4be1f3bdd197680e5c62b915a4c` |

## Partial and unexamined dependencies

- This review does not establish the whole-pair reachable movement tick, packet/send eligibility, or a full audit of every server path that can call a teleport or produce a velocity update. ServerEntity and MaceItem packet construction sites were identified, but their event conditions and physics preceding packet creation were not compared as complete mechanics.
- Entity's `oldPosition`, known-movement/lerp-target consumers beyond those directly reached by the local position handler, riding transitions and later tick consumers of the written snapshots remain open. The correction writer's passenger case is captured, but all passenger/vehicle transition paths are not.
- No block contact, input, ordinary travel, abilities, local tick kernel, collision, or intermediate-patch boundary was reopened. Those subjects remain exactly at the partial status supplied in the assignment projection.
- No implementation, test, game launch, build, runtime comparison or online mapping lookup was performed.

## Next gate

An independent source reviewer should retrace this bounded terminal slice against both hash-identified trees, with special attention to all `ServerPlayer` teleport call paths and the distinction between position-relative and delta-relative flags. The master can then add only this source-side coverage to the pair record; full-pair freeze still requires the remaining inventory and independent full-pair audit.
