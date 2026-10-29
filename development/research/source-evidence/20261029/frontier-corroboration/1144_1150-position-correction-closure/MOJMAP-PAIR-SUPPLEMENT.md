# Paired Mojmap supplement: direct position correction, 1.14.4 → 1.15

This is an immutable supplement to `POSITION-CORRECTION.md`. The original requested `1.14.4 Yarn → 1.15 Mojmap` identity gap remains recorded there. The coordinator authorized a bounded comparison using the exact published Mojmap roots on both endpoints. All behavioral claims below are Mojmap → Mojmap; none is attributed to Yarn.

## Exact source identity

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

- `1.14.4/mojmap.ready.json` identifies release `1.14.4`, Mojmap, 3,250 files / 17,080,207 bytes, source manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`, client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a`. `1.14.4/mojmap.provenance.json` identifies namespace `mojmap`, requested mapping option `feather,mojmap`, batch `1.14.4`, toolchain and exact Feather artifacts used for this dual-mapping preparation. The mapping-root contents and relevant file hashes match the parent source manifest; the ready and provenance metadata identify this published root.
- `1.15/mojmap.ready.json` identifies release `1.15`, Mojmap, 3,329 files / 17,522,461 bytes, source manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`, client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`. `1.15/mojmap.provenance.json` identifies namespace `mojmap`, exact version metadata ID `1.15`, official client mapping artifact SHA-256 `11415bc25024658f0ef1c1a0028df282135299e526a689de2f29c4662edf931a`, batch `1.15,1.15.1`, and source root/count/bytes/manifest matching the ready metadata. The manifest SHA-256 was recomputed and matches the ready marker.

The source manifest hash was recomputed for every file below and agrees with its corresponding manifest row:

| Source relative to each version's `mojmap/` | 1.14.4 SHA-256 | 1.15 SHA-256 |
|---|---|---|
| `net/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` | `893a920a818dfa91a977a367403f310811767998f4cd816f6a35d48f35c33e9d` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `6dddf37b79830d5413d91675e0bed8b4a3f0a179ac4535616dc57b510b778b45` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| `net/minecraft/world/entity/Entity.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/player/Player.java` | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |

## Packet and dispatch: unchanged

In both releases, `ClientboundPlayerPositionPacket.java:9-55` carries three doubles (`x/y/z`), two floats (`yRot/xRot`), one packed relative-argument byte, and a VarInt teleport id; `read` consumes these in that order and `write` emits them in that order. The enum at `:89-128` assigns X/Y/Z/Y_ROT/X_ROT bits 0 through 4 and packs/unpacks those same masks. The entire source file is byte-identical (matching SHA-256 above). Its `handle` dispatches to `ClientGamePacketListener.handleMovePlayer` in both.

Both client handlers begin with `PacketUtils.ensureRunningOnSameThread(packet, this, this.minecraft)` and then read `this.minecraft.player`; this is the direct clientbound correction response. This report does not infer behavior from a server-side packet producer.

## Handler procedure and position/velocity state: semantically unchanged for finite packet values

Compare `ClientPacketListener.java:659-711` (1.14.4) with `:669-731` (1.15):

- Each position axis independently checks its matching relative flag. Relative position adds the packet delta to current position and adds it to that axis's `xOld/yOld/zOld`, preserving that axis's current velocity. Absolute position uses the packet coordinate, assigns the corresponding old-position field to it, and changes that axis's velocity to `0.0`. The same gate and result apply to X, Y and Z on both sides.
- Source expression order differs in relative branches: 1.14.4 starts the position local with the packet value and adds the player field (`d += player.x`); 1.15 forms `player.getX() + packet.getX()` first. The old-position addition is the same `old + packetDelta` expression on both sides. For finite coordinates, operand reversal in the position sum has the same IEEE-754 result; the two calculations write independent fields and no call occurs between them. No movement difference is established from this source-order refactor. Non-finite packet reachability is not established here.
- Relative pitch and yaw independently add the current player rotations; absolute rotations use packet rotations. The relative flags and branch results are unchanged.
- `Entity.setDeltaMovement(Vec3)` and the three-double overload simply assign a new vector in both releases; they contain no callbacks or normalization. In 1.14.4 the handler applies selected velocity before resolving relative rotations and before `absMoveTo`. In 1.15 it first calls `setPosRaw`, writes `xo/yo/zo`, applies the selected velocity, then resolves rotations and calls `absMoveTo`. `setPosRaw` itself writes only `x/y/z`. The subsequent `absMoveTo` sets the position/bounding box and overwrites `xo/yo/zo` from the final clamped position on both versions. No movement consumer or callback intervenes between these intermediate writes. Thus finite packet inputs leave the same current position, bounding box, velocity and position-history fields when the handler returns.

## Absolute position, rotation history and first-session gate

`Entity.absMoveTo` is the substantive source delta (`Entity.java:1116-1143` in 1.14.4; `:1094-1105` in 1.15):

- Both clamp X and Z to `[-3.0E7, 3.0E7]`, leave Y unclamped, copy the resulting position into `xo/yo/zo`, and update the bounding box through `setPos`.
- Both clamp pitch to `[-90.0F, 90.0F]` for current pitch. 1.14.4 sets current yaw to the raw input temporarily and `yRotO` to the same raw input, then calls `setRot`; `setRot` applies `% 360.0F` to current yaw. Its subsequent `this.yRotO - g` value is zero for finite inputs, so the two adjustment branches do not alter the stored raw old yaw. 1.15 directly sets current yaw to `g % 360.0F` and copies that wrapped yaw into `yRotO`. Therefore, after the handler returns, current yaw is modulo-360 on both sides, while old yaw differs for finite input yaw outside the normalized interval (for example, input `360.0F` leaves `1.14.4 yRotO=360.0F` and `1.15 yRotO=0.0F`). Pitch history/current pitch are equivalent because the clamp bounds are within one revolution. This is a real rotation-history state difference, but not a changed current facing.
- In the following `LocalPlayer.tick`, both versions pass through `Player.tick` → `LivingEntity.tick` → `Entity.tick` → `Entity.baseTick` before LocalPlayer movement. `baseTick` copies current yaw/pitch into their old-rotation fields before later LivingEntity work. The old-yaw difference is therefore overwritten before the next player movement path consumes current orientation. Current yaw (the value used by LocalPlayer movement) is the same modulo-360 result on both sides. No movement consequence is established for this rotation-history difference. Inter-tick rendering interpolation is outside scope.

The session-start gate is also reorganized. After `absMoveTo`, 1.14.4's `if (!started)` copies current `x/y/z` to `xo/yo/zo` again, sets `started`, and closes the screen. Those three writes duplicate values `absMoveTo` has just installed. In 1.15, `absMoveTo` still installs the same final values; the `if (!started)` block only sets `started` and closes the screen. The final movement state is unchanged; the started gate's remaining work is UI initialization.

The handler's response packet also changes syntax only: 1.14.4 sends `player.x`, `player.getBoundingBox().minY`, `player.z`; 1.15 sends `player.getX()`, `player.getY()`, `player.getZ()`. After `absMoveTo` calls `setPos`, bounding-box `minY` equals player Y, and the getters return the same fields. Both send `ServerboundAcceptTeleportationPacket(id)` and `ServerboundMovePlayerPacket.PosRot(..., false)`. This records the client response construction only and makes no packet-scheduling claim.

## Next tick gate and movement consumers

`LocalPlayer.tick` is at `LocalPlayer.java:178-197` (1.14.4) and `:179-198` (1.15). Both guard the body with `level.hasChunkAt(BlockPos(x, 0.0, z))`; 1.15 spells the coordinates as `getX()/getZ()`, which return those same fields. On passage, `LocalPlayer.tick` calls `super.tick()` before its local movement-send path. `Player.tick` calls `super.tick`, and `LivingEntity.tick` starts with `super.tick`, reaching `Entity.tick/baseTick`. Current position and delta movement delivered by the handler are the same, and current yaw/pitch used by player movement are equivalent. Position old-history `xo/yo/zo` is also the same after `absMoveTo`. `xOld/yOld/zOld` are updated with equivalent finite-axis arithmetic; source search shows their client consumers are interpolation/render paths, while the block `SweetBerryBushBlock` consumer is guarded to server execution, not this logical-client player path.

No trajectory result is claimed. The bounded comparison establishes no changed direct current position or velocity response for finite packet fields. The yaw-history write differs in `absMoveTo`, but the next LocalPlayer tick resets that history before the movement path; its remaining visible effect is outside movement scope. The source-only comparison does not close unrelated correction/respawn/velocity packet ingress or establish full-pair tick coverage.

## Compared source ranges

| Operation | 1.14.4 Mojmap | 1.15 Mojmap |
|---|---|---|
| Packet layout, encoding and relative mask | `ClientboundPlayerPositionPacket.java:9-128` | same path and lines |
| Direct client handler | `ClientPacketListener.java:659-711` | `ClientPacketListener.java:669-731` |
| Absolute assignment / rotation | `Entity.java:1116-1143`, `:305-307` (`setRot`) | `Entity.java:1094-1105` |
| Delta setter | `Entity.java:2675-2681` | `Entity.java:2680-2686` |
| Raw position setter | Not used by the correction handler | `Entity.java:2728-2732` |
| LocalPlayer tick gate | `LocalPlayer.java:178-197` | `LocalPlayer.java:179-198` |
| Inherited next-tick path | `Player.java:209-237`, `LivingEntity.java:1975-1977`, `Entity.java:333-355` | `Player.java:210-238`, `LivingEntity.java:2024-2026`, `Entity.java:340-362` |

## Verdict for this bounded slice

- **Unchanged:** packet wire layout and flags; client thread gate; relative/absolute position rules; absolute-axis velocity zeroing; current pitch/yaw values after normalization; final clamped current position and bounding box; final velocity; first-start final position history; response packet values for finite state.
- **Changed source state:** `Entity.absMoveTo` now wraps yaw before writing `yRotO`, so immediately after correction finite unnormalized packet yaw leaves a different old-yaw interpolation field. Next `LocalPlayer.tick` resets that history before movement; no changed player movement behavior is established for this difference.
- **Residual gate:** non-finite packet-value reachability and movement effects are not established; no direct movement difference is claimed for such values. Unrelated clientbound state writers remain outside this bounded correction path.
