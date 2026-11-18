# Corrected source comparison: 1.14.1 → 1.14.2 dismount placement and XYZ correction

## Exact inputs

The comparison remains limited to 1.14.1 → 1.14.2. Both exact local source trees are Yarn-mapped: Yarn 1.14.1+build.10 and Yarn 1.14.2+build.7. No Mojmap tree is present for either side. Exact client and mapping artifact identities, the original source roster, and its checked file hashes are recorded in `source-roster.txt` (SHA-256 `ac604983703e5ed08ca6b1c0777e780084e2e36c2e8de766cf2dacc89737ada7`). The independent audit's added source inventory is `../lane-portal-passenger-position-1141-1142-audit/source-roster-supplement.txt`; its listed hashes were rechecked against the exact local provenance manifests.

## Ordinary dismount and placement

For players, `PlayerEntity.tickRiding()` (both releases, lines 437–459) enters the dismount branch only when `!world.isClient && isSneaking() && hasVehicle()`. It invokes `stopRiding()` and clears sneaking. `PlayerEntity` inherits the relevant `LivingEntity.stopRiding()` behavior. In both releases, `LivingEntity.stopRiding()` (1.14.1 lines 2306–2311; 1.14.2 lines 2312–2317) saves the old vehicle, detaches through `super.stopRiding()`, then calls `method_6038(oldVehicle)` only if the vehicle relation changed and `!world.isClient`.

`LivingEntity.method_6038(Entity)` is the ordinary dismount placement routine (1.14.1 lines 1650–1727; 1.14.2 lines 1656–1733). Its complete method body is byte-for-byte identical in the two sources. It has two geometry paths:

- For vehicles other than `BoatEntity` and `HorseBaseEntity`, it uses the vehicle movement direction and a fixed ordered set of offsets, rider and vehicle bounds, collision checks, solid-top checks, and a fluid check to select a candidate. Depending on the candidate, it calls `requestTeleport` with the selected XYZ or elevated fallback; with no accepted candidate it requests the initialized fallback coordinates.
- For boats and horse-base vehicles, it computes a side position from yaw, rider/vehicle widths, and the rider's main-hand side, calls `setPosition`, then tries a one-block raised position and finally a position above the vehicle if the union box still collides.

The direct dependencies used by this operation were also checked: `ViewableWorld.doesNotCollide/getCollisionShapes` (lines 117–195), `BlockState.getCollisionShape/hasSolidTopSurface` (lines 192–210), and `Block.isFaceFullSquare` (lines 399–405 in 1.14.1; 398–404 in 1.14.2) have identical compared source ranges. Their whole-file hashes and source-manifest matches are in the audit supplement. These checks use collision shape, solid top, and fluid behavior; neither the placement routine nor these predicates contain a `PORTALS`-specific branch.

## Server-authoritative XYZ and client handling

`ServerPlayerEntity.stopRiding()` (both releases, lines 759–765) snapshots the previous vehicle and calls `super.stopRiding()`, allowing the server-only `LivingEntity.method_6038` placement to run first. It then requests a teleport with the player's current XYZ and rotation only if the vehicle relation changed and `networkHandler != null`. Thus its outer correction synchronizes the authoritative post-placement position; it does not select the location.

On the generic non-boat/non-horse placement path, `method_6038` itself ends by calling `this.requestTeleport(l,m,n)`. For a server player this dispatches to `ServerPlayerEntity.requestTeleport()` (both releases, lines 1081–1083) and the network handler. After that call returns, the enclosing `ServerPlayerEntity.stopRiding()` also sends its current authoritative XYZ. On the boat/horse path the placement uses `setPosition`, after which the enclosing server-player override sends the correction. This operation order and the reachable gates are unchanged in the pair.

`ServerPlayNetworkHandler.requestTeleport/teleportRequest()` (1.14.1 lines 799–817; 1.14.2 lines 804–822) has the same XYZ/rotation recording, teleport-ID update, `setPositionAnglesAndUpdate`, and packet construction path. `ClientPlayNetworkHandler.onPlayerPositionLook()` (both releases, lines 642–690) applies relative offsets or absolute XYZ according to packet flags, zeros velocity on absolute axes, updates position/rotation, confirms the teleport, and sends a movement packet from the applied XYZ. `onEntityPassengersSet()` (both releases, lines 899–915) only reconciles passenger relations; it does not choose a dismount location.

## Portal gate and disposition

`PortalBlock.onEntityCollision()` (both releases, lines 101–104) calls `setInPortal` only when the entity has no vehicle, no passengers, and `canUsePortals()` is true. `Entity.setInPortal()` retains the same logical-server check for portal-position computation (1.14.1 lines 1647–1670; 1.14.2 lines 1649–1672). `Entity.tickPortal()` remains server-world gated and requires Nether permission, no vehicle, and elapsed portal time (1.14.1 lines 1672–1695; 1.14.2 lines 1674–1697). The portal interaction path contains no `PORTALS` block-tag test and no portal-specific dismount-placement rule. Dismounting can change later portal eligibility by removing the vehicle relation, but this is not a release difference.

**Revised result: no source-confirmed difference and no finding for these bounded operations in 1.14.1 → 1.14.2.** The dismount does choose new XYZ through `LivingEntity.method_6038`; the method, its checked collision/solid-top dependencies, server-side gates, outer authoritative correction, and client XYZ handling are behaviorally identical across this pair. This verdict does not establish other patch boundaries or runtime parity.

## Source identities for the omitted placement dependency

| Version | Exact source | SHA-256 |
|---|---|---|
| 1.14.1 | `net/minecraft/entity/LivingEntity.java` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| 1.14.2 | `net/minecraft/entity/LivingEntity.java` | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` |
| both | `net/minecraft/world/ViewableWorld.java` | `67e88826a77d5ed6dbdf7701acab287bd6b34f1e394625a6a701c1181556fd2e` |
| 1.14.1 | `net/minecraft/block/BlockState.java` | `9106f1649a2ac0965f6b3b13f2ec8437d7b16d2ce72e217c4f105b4524732238` |
| 1.14.2 | `net/minecraft/block/BlockState.java` | `c35e64104be023c3f7940dec7b459fb4252d552b6770baa45f4fb4c345dd7035` |
| 1.14.1 | `net/minecraft/block/Block.java` | `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe` |
| 1.14.2 | `net/minecraft/block/Block.java` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` |
