# Frozen source roster and scoped comparison: 1.15 to 26.2

## Inputs and provenance

- Host and shell: Windows, PowerShell; primary checkout `D:\Javastuff\LegacyParkourCompat`.
- Older endpoint: Minecraft `1.15`, `mojmap` source tree at `decompiled_minecraft/1.15/mojmap/`.
- Newer endpoint: Minecraft `26.2`, published `unobfuscated` source tree at `decompiled_minecraft/26.2/unobfuscated/`.
- Both trees have `ready` markers. The 1.15 ready record names client input SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b`; the 26.2 ready record names version metadata ID `26.2`.
- Provenance records exact version IDs and namespace/mapping (`1.15` / `mojmap`; `26.2` / `unobfuscated`), Java 25 and Vineflower 1.12.0. The 1.15 tree was published in the 2026-10-09 exact run batch `1.15,1.15.1`; 26.2 was published in its own exact run. No source regeneration was performed. The exact 1.15 source root is `decompiled_minecraft/1.15/mojmap/`; its ready marker is `decompiled_minecraft/1.15/mojmap.ready.json` and its provenance file is `decompiled_minecraft/1.15/mojmap.provenance.json`.
- Ready source counts: 3,329 files / 17,522,461 bytes for 1.15; 7,055 files / 33,813,717 bytes for 26.2.
- Source manifest SHA-256 (also the ready marker's `sourceManifestSha256`): 1.15 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.
- Each listed file's SHA-256 was recomputed and matched its source manifest entry. Hashes below are the terminal file identities for this bounded comparison.

## Identity clarification

The earlier sibling artifact `source-roster-comparison.md` binds to `decompiled_minecraft/1.15.2/mojmap/` and its 1.15.2 ready/source-manifest identities. This file instead binds to the exact `decompiled_minecraft/1.15/mojmap/` tree, whose ready marker and source manifest name version `1.15`. The earlier artifact is preserved and is not the source evidence for the 1.15 assignment. The comparison below was checked against the exact 1.15 source files and independently verified hashes.

## Frozen source roster and terminal hashes

| Endpoint | Relative source path | SHA-256 |
|---|---|---|
| 1.15 mojmap | 
et/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 mojmap | 
et/minecraft/server/level/ServerPlayer.java` | `719416651f82ff78e9990a77e5bea0998938bf8070898865c37ae2df6e3b22da` |
| 1.15 mojmap | 
et/minecraft/server/network/ServerGamePacketListenerImpl.java` | `3d46e455509891a3e0ef50472610d565255f5991987e971ba00af2140d25982c` |
| 1.15 mojmap | 
et/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` |
| 26.2 unobfuscated | 
et/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 unobfuscated | 
et/minecraft/server/level/ServerPlayer.java` | `37e617a5beccbf3558c8cf8f6624ba9d704593ccc5d6633bedf71cf4f792e82e` |
| 26.2 unobfuscated | 
et/minecraft/server/network/ServerGamePacketListenerImpl.java` | `2015d9e3c8d3e277bc2ef9e709ad4c991894df1deaa2eaca3a5a5dcc29fb51d2` |
| 26.2 unobfuscated | 
et/minecraft/client/multiplayer/ClientPacketListener.java` | `9cb0cc8afebae9f4e42f428a52719c645817d03893dc371e6711c7bd16eac1b6` |
| 26.2 unobfuscated | 
et/minecraft/world/entity/Relative.java` | `e4a9488f6e6703e184fc62f0e85006e06a6e30d7475c892542db3abbe6a45ed3` |
| 26.2 unobfuscated | 
et/minecraft/world/entity/PositionMoveRotation.java` | `a5f0cf32266502dd2c9250d28dfc7bc85cfcf30247e91e3311018af15be319f8` |
| 26.2 unobfuscated | 
et/minecraft/network/protocol/game/ClientboundPlayerPositionPacket.java` | `8da72408493b7279713f134bb2a60c39763e89ce6ceb043eece306c040eaaa5a` |

## 1. Server-side portal vehicle placement on ordinary dismount

**1.15:** `LivingEntity.stopRiding()` (lines 2389–2395) saves the old vehicle, calls `super.stopRiding()`, then calls private `findStandUpPosition(oldVehicle)` only when the vehicle changed and `!level.isClientSide`. In `findStandUpPosition` (lines 1697–1700), the first branch tests `level.getBlockState(new BlockPos(vehicle)).getBlock().is(BlockTags.PORTALS)`. When true, it sets the player position to `(vehicle.getX(), vehicle.getY(1.0) + 0.001, vehicle.getZ())`. This branch precedes and bypasses the boat/horse and ordinary stand-up search branches.

**26.2:** `LivingEntity.stopRiding()` (lines 3293–3298) retains the changed-vehicle and logical-server gate, then calls `dismountVehicle(oldVehicle)`. That method (lines 2347–2368) uses the vehicle's standard passenger dismount location only if the rider is not removed, the vehicle is not removed, and the vehicle block state is not in `BlockTags.PORTALS`. For an ordinary extant rider and an extant portal-tagged vehicle, it takes the fallback: `maxY = Math.max(rider.getY(), vehicle.getY())`, initial target `(rider.getX(), maxY, rider.getZ())`; if both rider width and height are `<= 4.0F`, it creates a center at `target + (0, height / 2, 0)`, queries `level.findFreePosition(rider, Shapes.create(AABB.ofSize(center, width, height, width)), center, width, height, width)`, and, if present, subtracts half-height from the returned Y. The initial target is retained if no free position is returned. It then calls `dismountTo(target.x, target.y, target.z)`.

**Difference and player meaning:** For the concrete ordinary-player portal case, 1.15 places the player directly above the vehicle using vehicle X/Z and vehicle top plus `0.001`; 26.2 starts from the player's X/Z and the greater of player/vehicle Y, then may use the bounded free-position query. The portal block-state predicate remains on the vehicle's block position. This is a server-side dismount response, not historical physics replay. No conclusion is made about any intermediate release.

## 2. Server position packet production and locally owned client consumer

**1.15 call and payload:** The portal branch above invokes `setPos`, not `teleportTo`. The ordinary player packet is produced by the enclosing override `ServerPlayer.stopRiding()` (lines 751–758): after `super.stopRiding()`, if the vehicle changed and `connection != null`, it invokes `connection.teleport(player X, Y, Z, yRot, xRot)`. `ServerGamePacketListenerImpl.teleport(x,y,z,yRot,xRot)` (lines 872–874) passes `Collections.emptySet()` to its overload. That overload (lines 876–889) updates awaiting-position state and sends `ClientboundPlayerPositionPacket(d,e,f,g,h,emptySet,id)`, so XYZ and rotations are absolute fields with no relative arguments. The source call is on the server player dismount path; the portal-specific placement itself does not produce the packet.

**26.2 call and payload:** `LivingEntity.dismountVehicle` ends with `dismountTo(x,y,z)` (line 2368). `Entity.dismountTo` (Entity.java lines 3347–3349) calls virtual `teleportTo(x,y,z)`. The ordinary server player's override `ServerPlayer.teleportTo(double,double,double)` (lines 1688–1691) calls `connection.teleport(new PositionMoveRotation(new Vec3(x,y,z), Vec3.ZERO, 0.0F, 0.0F), Relative.union(Relative.DELTA, Relative.ROTATION))`. `Relative.DELTA` is `{DELTA_X, DELTA_Y, DELTA_Z, ROTATE_DELTA}`; `Relative.ROTATION` is `{X_ROT, Y_ROT}`; neither set contains positional `X`, `Y`, or `Z`. `ServerGamePacketListenerImpl.teleport(destination, relatives)` (lines 1261–1269) applies the server position and sends `ClientboundPlayerPositionPacket.of(id, destination, relatives)`. The packet factory stores the supplied change and relatives unchanged. Thus the position fields are absolute XYZ, the rotation fields are relative zero changes, and the delta-motion components are zero changes under the relative delta flags.

**Client consumer:** In 1.15, `ClientPacketListener.handleMovePlayer` (lines 669 onward) always consumes the packet for `minecraft.player`: X/Y/Z are added to current coordinates only when their respective relative arguments are set; otherwise each coordinate and old-position value is assigned from the packet. It updates delta movement to the current movement component for relative position axes and zero for absolute axes, applies relative yaw/pitch flags, calls `absMoveTo`, acknowledges the teleport, and sends a position/rotation response.

In 26.2, the locally owned player consumer `ClientPacketListener.handleMovePlayer` (lines 795–805) calls `setValuesFromPositionPacket` only when `!player.isPassenger()`. It sends teleport acknowledgement and a move packet either way. For the applied case, `PositionMoveRotation.calculateAbsolute` (PositionMoveRotation.java lines 40–62) adds current XYZ only for positional relative flags, current yaw/pitch only for rotational relative flags, and current delta components only for relative delta flags. The dismount payload's positional flags are absent, so the packet's XYZ are interpreted as absolute if this client-side gate permits application. The source does not establish message arrival order or whether an in-flight client state is still a passenger when the handler runs.

**Difference and movement meaning:** The old dismount placement path has a direct server position assignment followed by `ServerPlayer.stopRiding` sending an all-absolute position/rotation correction. The newer dismount placement path calls the player's teleport override and sends absolute XYZ with relative zero rotation/delta changes; the new locally owned client handler additionally gates applying that packet on `!isPassenger()`. These source facts identify the changed server call/payload and the client gate without asserting packet ordering, wire timing, or representability during a transient state.

## Scope closure

Only the assigned portal-tagged ridden-vehicle dismount placement and its server position-packet/client consumer path were compared. Called predicates and packet-relative helpers were read only as needed to establish those operations. This is an endpoint comparison of 1.15 and 26.2; it does not establish when the change first appeared or coverage of other movement operations.