# 1.14.1 → 1.14.2: portal-adjacent passenger dismount and XYZ correction

## Source identity

Both exact-version source trees exist locally and contain `.decompile-provenance.json`. The only common supported mapping family available on both sides is Yarn: 1.14.1+build.10 and 1.14.2+build.7. No Mojmap tree is published for either exact version in this checkout. The manifest pins the exact Mojang client artifact and mapping artifact inputs. Every examined source file hash in `source-roster.txt` matches its version manifest's `sources` entry. The roster also records both manifest hashes and pinned client/mapping input hashes.

## Bounded comparison

**Ordinary dismount and placement.** In both releases, `net/minecraft/entity/player/PlayerEntity.java`, `tickRiding()` (1.14.1 lines 437–459; 1.14.2 lines 437–459), takes the detach branch only on `!world.isClient && isSneaking() && hasVehicle()`. It calls `stopRiding()` and clears sneaking; otherwise it retains the ride tick and passenger update path. `Entity.updatePassengerPosition()` (1.14.1 lines 1530–1533; 1.14.2 lines 1532–1535) writes the passenger's mounted position while attached. `Entity.stopRiding()` (1.14.1 lines 1589–1595; 1.14.2 lines 1591–1597) only clears the vehicle relation and asks the old vehicle to remove the passenger. No ordinary dismount location search/placement routine is present in these Entity or BoatEntity classes, and the dismount path does not choose a new XYZ position. The corresponding code is unchanged in behavior between the pair.

**Portal gate.** In both versions, `PortalBlock.onEntityCollision()` (lines 101–104) calls `setInPortal` only when the entity has neither vehicle nor passengers and `canUsePortals()` is true. `Entity.setInPortal()` and `tickPortal()` (1.14.1 lines 1647–1695; 1.14.2 lines 1649–1697) retain the same portal-time logic; the actual transfer remains gated by the logical-server `ServerWorld`, Nether permission, no current vehicle, and elapsed portal time. No `PORTALS` block-tag predicate appears in the exact `BlockTags.java` pair; the portal collision gate is the direct passenger/vehicle predicate above. This means an ordinary server dismount can make the entity eligible for a later portal collision callback, but it does not reposition it. No release difference was found in this gate.

**Server correction emitted by player dismount.** In both versions, `ServerPlayerEntity.stopRiding()` (lines 759–765) snapshots the old vehicle, calls the superclass detach, then, only when the vehicle changed and `networkHandler != null`, calls `requestTeleport(this.x, this.y, this.z, this.yaw, this.pitch)`. It re-synchronizes current authoritative coordinates; it does not calculate a dismount location. `ServerPlayNetworkHandler.requestTeleport/teleportRequest()` (1.14.1 lines 799–817; 1.14.2 lines 804–822) retains the same absolute-coordinate path: records requested XYZ, increments/rolls the teleport ID, calls `player.setPositionAnglesAndUpdate(d,e,f,g,h)`, and sends a `PlayerPositionLookS2CPacket` with the selected relative flags and XYZ deltas. The call and write gates are unchanged.

**Client packet handling.** `ClientPlayNetworkHandler.onPlayerPositionLook()` (both versions lines 642–690) applies relative offsets or absolute XYZ per packet flags, zeros velocity on absolute axes, invokes `setPositionAnglesAndUpdate`, then sends teleport confirmation and a `PlayerMoveC2SPacket.Both` from the applied XYZ. The reachable XYZ writes and flag gates are identical. Passenger attachment reconciliation (`onEntityPassengersSet`, both lines 899–915) also has no dismount-specific XYZ placement.

## Result

No source-confirmed difference / no finding for the assigned dismount placement, portal gate, or dismount-triggered authoritative XYZ correction operation. Exact claim is limited to 1.14.1→1.14.2; this does not prove neighboring patch boundaries or runtime parity.

A separate 1.14.2 server movement edit was observed in `ServerPlayNetworkHandler`: a new `method_20630(ViewableWorld)` checks a contracted player box at `1.0E-5F` and replaces the prior direct `doesNotCollide(...contract(0.0625))` calls inside general `onPlayerMove`. That broad inbound movement collision-correction path is not initiated by ordinary dismount and is excluded from this bounded no-difference claim.

## Examined exact source locations

All source paths are rooted at `decompiled_minecraft/<version>/yarn/`; full-file SHA-256 values and manifest matches are in `source-roster.txt`.

- `net/minecraft/entity/player/PlayerEntity.java`: `tickRiding`, `stopRiding`.
- `net/minecraft/entity/Entity.java`: `tickRiding`, `updatePassengerPosition`, `stopRiding`, `removePassenger`, `setInPortal`, `tickPortal`.
- `net/minecraft/block/PortalBlock.java`: `onEntityCollision`.
- `net/minecraft/server/network/ServerPlayerEntity.java`: `startRiding`, `stopRiding`.
- `net/minecraft/server/network/ServerPlayNetworkHandler.java`: `onPlayerMove`, `requestTeleport`, `teleportRequest`.
- `net/minecraft/client/network/ClientPlayNetworkHandler.java`: `onPlayerPositionLook`, `onEntityPassengersSet`.
- `net/minecraft/client/network/ClientPlayerEntity.java`: `stopRiding`, riding packet path.
- `net/minecraft/entity/vehicle/BoatEntity.java`: passenger position update; no ordinary dismount placement override.
- `net/minecraft/tag/BlockTags.java`: no portal-tag predicate in either mapping output.
