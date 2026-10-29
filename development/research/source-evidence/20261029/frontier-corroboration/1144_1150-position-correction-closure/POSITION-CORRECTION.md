# 1.14.4 Yarn → 1.15 Mojmap: direct position-correction response

## Status

**Blocked before paired comparison.** The assigned exact 1.14.4 Yarn mapping root is not published in the canonical decompiled source tree. The only Yarn-named item under the 1.14.4 parent is `.yarn.staging-7231733284413474157/`; it has `com/` and `net/` directories but no ready marker, provenance, source manifest, or verification metadata. Its current directory contents are not canonical, identity-verified evidence. I did not use them to make behavioral claims.

The available 1.14.4 Mojmap source is not a substitute for the assigned Yarn mapping family. Therefore this report makes no paired claim about changed or unchanged handler procedure, packet layout, relative-axis behavior, old-position assignments, zeroing, first-spawn initialization, or pre-next-tick consumers. The prior `1144-115-full-audit/AUDIT.md` remains a scope/gap index only.

## Release and mapping-root identity

Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- **1.14.4 parent:** `1.14.4/` contains published `mojmap/` and `ornithe-feather/` roots and their respective ready, provenance, source-manifest and verification files. It contains no `yarn/` mapping root and no `yarn.ready.json`, `yarn.provenance.json`, `yarn.sources.sha256`, or `yarn.verification.json`. It also contains `.yarn.staging-7231733284413474157/`, which is not a published mapping root and has no identity metadata. The target Yarn source identity and mapping artifact/build cannot be established from these files.
- **1.15 parent:** `1.15/` contains the published `mojmap/` root and parent metadata. `mojmap.ready.json` identifies exact version `1.15`, mapping `mojmap`, 3,329 source files / 17,522,461 bytes, manifest `mojmap.sources.sha256` with SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`, and client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. `mojmap.provenance.json` agrees on release, source root/count/bytes/manifest, pins official client mappings SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`, and names batch `1.15,1.15.1`. The mapping root itself contains only the source package directories; source identity is bound by its parent ready/provenance/manifest metadata. The manifest has 3,329 lines and its recorded whole-file SHA matches the ready marker.

## Bounded one-sided source trace (1.15 Mojmap only)

These observations describe the available 1.15 side; with the 1.14.4 Yarn side unavailable they do **not** establish a release difference.

- `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java:9-129`: packet carries X/Y/Z doubles, Y/X rotation floats, one unsigned-byte packed relative-argument set, and a VarInt teleport id. The bits are X=0, Y=1, Z=2, Y_ROT=3, X_ROT=4. `handle` calls `ClientGamePacketListener.handleMovePlayer`.
- `net/minecraft/client/multiplayer/ClientPacketListener.java:669-731`: `handleMovePlayer` first schedules/validates execution via `PacketUtils.ensureRunningOnSameThread`, then reads `minecraft.player` and its delta movement. For each relative position axis it adds packet delta to current position and old position while preserving that velocity component; for each absolute axis it uses packet position, sets the matching old position directly, and supplies zero for that velocity component. It writes raw position, then `xo/yo/zo`, then the selected delta movement. Relative pitch/yaw add to current rotation. It calls `absMoveTo`, sends teleport acceptance and a position/rotation response with `onGround=false`, then `!started` only sets UI/start state and closes the screen; that block is not an observed first-spawn position initializer.
- `net/minecraft/world/entity/Entity.java:1094-1104`: 1.15 `absMoveTo` clamps X/Z to ±3.0E7, writes `xo/yo/zo`, calls `setPos`, sets yaw to `% 360.0F`, clamps pitch to [-90,90] before `% 360.0F`, then copies resulting rotations to `yRotO/xRotO`. `setPosRaw` at `2728-2732` writes `x/y/z` directly.
- `net/minecraft/client/player/LocalPlayer.java:179-195`: the next `tick()` gate is `level.hasChunkAt(new BlockPos(getX(), 0.0, getZ()))`; when true it calls `super.tick()` before the LocalPlayer send-position path. This identifies an immediate position-based tick gate but does not claim a trajectory or packet-scheduling effect.

No server producer path was used to infer the direct client response. Server producer identity and behavior are outside this assignment.

## Exact source hashes (available 1.15 side)

Each hash was recomputed from the canonical file and matched its line in the 1.15 source manifest (`mojmap.sources.sha256`):

| File relative to `1.15/mojmap/` | SHA-256 |
|---|---|
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |

## Completion gate

To resume this exact comparison, the preparation owner must publish the canonical `1.14.4/yarn/` source tree with parent and mapping-root metadata: exact Yarn artifact/build identity, ready marker, full source manifest, provenance and verification. Then retrace paired packet encoding/decoding and handler/state-writer slices, including first-spawn behavior and all direct state consumers before the next `LocalPlayer.tick()`. Until that evidence exists, changed/unchanged procedure and closed dependency status remain unknown.
