# 1.21.1–1.21.3 external correction boundary

## Scope and verdict

This is a source-only boundary check of two related operations: the `ServerPlayer.teleportTo(double, double, double)` correction writer's relative-delta flags, and the client `ClientPacketListener.handleMovePlayer` receiver's treatment of the local player while riding. The reported 1.21.1→1.21.3 endpoint report was used only as a lead (SHA-256 `763e286dd4bc968827f8078ed3849c1f98cf27812687979b735b515a39db5291`); all three exact source trees and relevant source bodies were checked independently.

**Boundary verdict:** both changes are already present in 1.21.2. They are absent in 1.21.1 and remain present in 1.21.3. Thus 1.21.1→1.21.2 is the first checked adjacent cutover for both operations, and 1.21.2→1.21.3 shows no further change in the inspected source. This establishes the boundary among these three exact releases, not the patch or commit that first introduced it.

## Source identity

All paths are Mojmap source paths relative to `decompiled_minecraft/<version>/mojmap/`.

| Release | Identity evidence checked |
| --- | --- |
| 1.21.1 | `mojmap.ready.json` SHA-256 `988b38ac59491f9430d5bd1968db0fdaacb777a63fef745ca3e20342237a0310` says `status=ready`, `versionId=1.21.1`, `mapping=mojmap`, 5,363 source files. Its source-manifest SHA `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48` and artifact-manifest SHA `09ced418cbc7530a1d6d8802ee10c05cd576b217a2129655a71f30a2ae38f486` both match the hashes bound in the ready marker; sidecars contain 5,363 and 75 entries respectively. Provenance SHA-256: `6f84a596ea944a1d40c79b53ead9d2cdd2b3c726d710a977758d15b5578ce7a9`. |
| 1.21.2 | Mapping-local `.decompile-provenance.json` SHA-256 `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` matches the assigned identity. Its `sources` object has 5,654 Java-path entries; each relevant file hash below was checked against its path entry and actual bytes. The metadata input includes the exact `.../1.21.2.json` resource with SHA-256 `793e3fd0451354a26aaa00253dcb6600add11f9d2a90f7bdef75ddf5b38c2b45`. This snapshot has no separate ready marker, root source-manifest sidecar, or artifact-manifest sidecar. The provenance JSON itself has no top-level `versionId` or `mapping` property; version/mapping identity is therefore bound by the assigned hash, its 1.21.2 mapping-local location and the version metadata input, rather than a ready marker. |
| 1.21.3 | `mojmap.ready.json` SHA-256 `72385aa99d249a56d742b9c07823c1155c74c39dda1fbc6eacc12c980cd12e02` says `status=ready`, `versionId=1.21.3`, `mapping=mojmap`, 5,655 source files. Its source-manifest SHA `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` and artifact-manifest SHA `0587668e5c70bb06dacf3496a4442f66dc9cdcab23f2350b844f14259cc40dcf` both match the hashes bound in the ready marker; sidecars contain 5,655 and 89 entries respectively. Provenance SHA-256: `24093437c0cd41d807ab0829068ef97828b65bbb95b38dfdc4f706883a8483d7`. |

Actual relevant source hashes match their source-manifest/`sources` entries:

| Source file | 1.21.1 | 1.21.2 | 1.21.3 |
| --- | --- | --- | --- |
| `net/minecraft/server/level/ServerPlayer.java` | `ccdac581288507aae64bd844beefe9ac2e174f316458e181f23ce5631a4b1ba7` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` | `f6c58a668916857a2aa28f27b4b20d711b5f703aa19fad24875c129ae9ca712c` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `822e00d89e8def4cf7a8c22fdfefba3b9dc2135a7630e4ebe561a9d547d7f79d` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` | `169e1edaf666ebeb4ad736323565e28935a247748fecb67333dd5db53f24fb8a` |
| `net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `03f4c58cb1f801cb62fe48a207a575b7bfd2a9fdcb5a0683bef3c0c9c75bed69` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` | `658e5968d7c83bd6b176e7b061078e0def68abcc2558f7d9ffd59640a88f317f` |
| `net/minecraft/world/entity/RelativeMovement.java` (1.21.1) / `Relative.java` (1.21.2–1.21.3) | `7b12db600cde348858fe5835d7618a59b22ad44ed147351fc79b83b7ecaced4f` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` | `292b6d9f3f28dcd69ba60d02e218158bf92b61464597dd21529b0e841c54c0d6` |
| `net/minecraft/world/entity/PositionMoveRotation.java` | absent | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` | `74b39fb0907043885e7cfeb6376c8d39eba7131ee6befa2df57a08ea53571436` |

## Paired operations

### Server correction producer and client velocity response

- **1.21.1 Mojmap**, `ServerPlayer.java:1400–1402`: `teleportTo(double, double, double)` calls `connection.teleport(x, y, z, currentYaw, currentPitch, RelativeMovement.ROTATION)`. The position is absolute and the legacy relative set contains only rotation flags.
- **1.21.2 Mojmap**, `ServerPlayer.java:1594–1596`: it calls `connection.teleport(new PositionMoveRotation(new Vec3(x, y, z), Vec3.ZERO, 0.0F, 0.0F), Relative.union(Relative.DELTA, Relative.ROTATION)`. `Relative.DELTA` expands to `DELTA_X`, `DELTA_Y`, `DELTA_Z`, and `ROTATE_DELTA` (`Relative.java:10–24`). The 1.21.3 body has the same lines and the same complete source-file hash as 1.21.2.
- **Client consumption:** 1.21.1 `ClientPacketListener.handleMovePlayer` (`:620–691`) has no passenger guard. For absolute XYZ it sets the player position and the matching old-position fields from the packet, and sets each delta component to `0.0`; relative position bits instead add packet position deltas and preserve current delta components. In 1.21.2 and 1.21.3 `handleMovePlayer` (`:706–715`) calls `setValuesFromPositionPacket` only when `!player.isPassenger()`. That call passes `false`, so this player correction uses the direct branch rather than the helper's interpolation branch. `PositionMoveRotation.calculateAbsolute` (`:35–62`) adds packet delta to existing delta only for `DELTA_X/Y/Z`; otherwise it replaces it. With this writer's zero delta and all three delta-relative bits, the ordinary non-passenger local-player response preserves current velocity. `ROTATE_DELTA` rotates movement by the difference between base and resulting rotations; this writer also marks rotation relative and supplies zero rotation, so the rotation delta is zero.

The producer is a server-side teleport API and does not itself run historical movement. Its effect in scope is the subsequent client-side local-player correction. The source slice does not establish which gameplay/event callers trigger this API or every other teleport producer.

### Local-player riding gate

- **1.21.1 Mojmap**, `ClientPacketListener.handleMovePlayer` (`:620–691`): after same-client-thread enforcement, it obtains `minecraft.player` and applies the packet's position, velocity, and rotations unconditionally; there is no `isPassenger()` branch. It then sends teleport acceptance followed by a `PosRot` movement packet.
- **1.21.2 Mojmap**, `ClientPacketListener.java:706–715`: after the same thread gate and obtaining `minecraft.player`, it applies the correction only inside `if (!player.isPassenger())`. It still sends `PosRot` and accepts the teleport after the branch. 1.21.3 has a byte-identical `ClientPacketListener.java`, so the gate and responses persist unchanged.
- The handler is on `ClientPacketListener`, not overridden in `LocalPlayer`; searches of the three hash-verified `LocalPlayer.java` files found no `handleMovePlayer`, packet type, or correction-helper references. The `Player` read by the client listener is the local `minecraft.player`; while it is riding, the correction state write is skipped, but the acknowledgement/response packets are still sent from its current state.

## Boundary and remaining dependencies

The riding gate and the relative-delta producer/client interpretation are absent in 1.21.1 and present in 1.21.2. The 1.21.2 and 1.21.3 hashes are equal for `ServerPlayer.java`, `ClientPacketListener.java`, `LocalPlayer.java`, `ClientboundPlayerPositionPacket.java`, `Relative.java`, and `PositionMoveRotation.java`, so these inspected implementations do not change again at 1.21.3.

Remaining dependencies: full server-side call sites, event conditions and packet-send eligibility were not audited; riding-state transition paths and downstream snapshot consumers were not audited. This is a bounded boundary verdict, not full-pair discovery acceptance, implementation status, or runtime parity. No test, build, decompile, game launch, or online mapping lookup was run.
