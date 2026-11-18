# 1.14.2 → 1.14.3 portal-related passenger operations

## Scope and verdicts

This is a bounded source comparison of four assigned operations. The checked evidence shows **no movement-behavior difference in this pair** for these operations. Names, enclosing source layout, box classes and mapped symbols changed; the compared branch conditions, numeric expressions, write order and packet response remain equivalent. No nested canonical finding is warranted from this comparison.

| Operation | Scoped verdict |
|---|---|
| Generic passenger stand-up position on the logical server | Same `LivingEntity.method_6038(Entity)` search order, collision/support predicates and chosen coordinates. It is reached after a living entity actually leaves a vehicle, guarded by `!world.isClient`. |
| Server virtual teleport correction and movement-packet gate | Same post-super correction in `ServerPlayerEntity.stopRiding`; same handler `requestTeleport` relative-coordinate construction, requested-position bookkeeping and resend/ordinary-movement gate. |
| Boat and horse stand-up contract | Same shared `LivingEntity.method_6038` type split and boat/horse offset calculations. These types are special cases in the shared method, not overrides of `method_6038` in the inspected `HorseBaseEntity` or `BoatEntity`. |
| Client local application of absolute XYZ / relative flags | Same X, Y, Z order; relative coordinates are added to current position and preserve velocity, absolute coordinates replace render history and zero that velocity component. Rotation, position write, confirm and follow-up movement packet order match. Only flag names differ. |

The operation remains limited to source-confirmed behavior. This does not claim that a portal launch occurs, that a network packet arrives, or that asynchronous/event timing is equivalent. The endpoint comparison does not establish an intermediate patch boundary or full-pair movement coverage.

## Exact inputs and provenance

Host and shell: Windows 11, PowerShell. Canonical source directories were consumed read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.14.2/yarn/` and `.../1.14.3/yarn/`. Both contain `.decompile-provenance.json`; recorded source hashes for every relied-on file below were recomputed with SHA-256 and matched the manifest. Provenance was inspected in the hidden local cache under `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/`.

| Side | Exact game/client identity | Yarn mapping and local pin | Intermediary pin | Yarn metadata pin |
|---|---|---|---|---|
| Older | `1.14.2`; client `piston-data` object `ca6c5a139045967229975c0c0b7f93e78b4314c2`; SHA-256 `9b740c9fd4955981125ebd3cd086baa8b4392963d043a3480b5b942a96637c25` | `1.14.2+build.7`, `yarn-1.14.2+build.7-v2.jar`; SHA-256 `3c91c928d10797ba688b1f7e61063fd4bc56a3ee0f66fa2609ef960ec08b239b` | `intermediary-1.14.2-v2.jar`; SHA-256 `f73883d007c64858ae0f26533fe47ec5d3a051cede6892c8d6531fc5122e459e` | cached `yarn-1.14.2.json`; SHA-256 `a65478802722f65b4dfec15e8a732872f2836e1e9b7ae95d7086c601b07f1d81` |
| Newer | `1.14.3`; client `piston-data` object `af100b34ec7ef2b8b9cf7775b544d21d690dddec`; SHA-256 `4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07` | `1.14.3+build.13`, `yarn-1.14.3+build.13-v2.jar`; SHA-256 `ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e` | `intermediary-1.14.3-v2.jar`; SHA-256 `f2e9990c00fa87ddc7158a95afb735a947ae26d1997b52916226e11c612542a9` | cached `yarn-1.14.3.json`; SHA-256 `85ad2c9e3ff892f5a6d5b774b869ed6b6dbdd26de9a46d1a37b5c739e73c8766` |

The local mapping metadata records game versions and highest available builds 7 and 13 for the respective versions. Each mapping artifact's hidden `.pin.json` URL and digest matched the provenance manifest. The client hashes and exact version metadata hashes likewise match each manifest. No online mappings were used. Decompiler provenance records Vineflower `1.12.0`, pipeline `lpc-decompile-v1`, and Eclipse Adoptium Java `25.0.3+9-LTS` on Windows 11 amd64.

## Compared source and operation traces

Ranges are inclusive and refer to the canonical Yarn trees above.

| Source member | 1.14.2 | 1.14.3 | Evidence SHA-256 (1.14.2 / 1.14.3) |
|---|---:|---:|---|
| `net/minecraft/entity/LivingEntity.java`, `method_6038(Entity)` and `stopRiding()` | 1656–1728; 2312–2318 | 1667–1738; 2322–2328 | `e8224e7df98ccf0986bb390f35e64293263d17b9ab1dbdfeb661c65d28b6ee78` / `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| `net/minecraft/entity/Entity.java`, `stopRiding()` / `requestTeleport(...)` / teleport-request consume | 1591–1597; 2181–2187; 2352–2355 | 1631–1637; 2222–2228; 2392–2396 | `7c0e9cab0842a45e42fa9f27c9661de3fd0fd076c35ff989aa0477198eb38edc` / `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14` |
| `net/minecraft/server/network/ServerPlayerEntity.java`, `stopRiding()` | 759–766 | 760–767 | `5dd585383d9cb52bf1db7304fc755ae392d48a6d1513ff9143e3b49934340381` / `45b2ea744bf16153c51e3cd31719e746f6e6a821616383f9a4e683b1046a8f3a` |
| `net/minecraft/server/network/ServerPlayNetworkHandler.java`, `onPlayerMove(...)`, `requestTeleport(...)`, `teleportRequest(...)` | 678–822 | 679–826 | `554278bbc6bed6b776312d35d1e4c964288cb349f4c06b2e9ff8f01fabc8c20a` / `0467b26f218892f6ed2851688cc20ecde313fa76f6d84419af775c849eb568c3` |
| `net/minecraft/entity/passive/HorseBaseEntity.java`, passenger-position method | 914–932 | 914–932 | `317ec0256a38c0f35b3dd20e8a03b17a7819bb903ad41c77f95cde6e78e47387` / `df26ff8249c826512ac49846c7cbba342fb4ca3c2ea6dc4927b75a62aa5bfd61` |
| `net/minecraft/entity/vehicle/BoatEntity.java`, passenger-position method | 627–655 | 626–654 | `9b639ab039d20b4f224be4f7b15dbee77e4e7bfba11c64e92060fe97c06e7725` / `b2156dce2d641188d793ef83327fa1abd385ceb8e29c6050ef34f522f0938101` |
| `net/minecraft/client/network/ClientPlayNetworkHandler.java`, `onPlayerPositionLook(...)` | 642–695 | 641–694 | `5055088f2e944c50be032cce0a921054eba2c13a022f60306e5e9411c400697b` / `5dab323ebb5147be91219928928abaadaa320a3281fad8f6957d3d96d5eeb4a7` |
| `net/minecraft/client/network/packet/PlayerPositionLookS2CPacket.java`, `Flag` and accessors | 82–126 | 82–126 | `9303cb6dfa7f4db4b775d6e57304ebaf58790dcea915cdb89564edd05a9308e2` / `f2650155fc20362b51de1e8eaed6fc28a60796846d366a7e7dbac9bcd5ab0fe3` |

### Stand-up selection, boat and horse branches

`LivingEntity.stopRiding()` snapshots the old vehicle, calls `Entity.stopRiding()`, then invokes `method_6038(oldVehicle)` only if the old vehicle differs from the now-current vehicle and `!world.isClient`. A `ServerPlayerEntity` follows this same inherited path. This is the logical-server guard for the generic stand-up search; it is not historical client movement running on a server.

For non-boat and non-horse vehicles, `method_6038` starts with `(vehicle.x, vehicle.boundingBox.minY + vehicle.height, vehicle.z)`. If the vehicle has a movement direction, it tests the fixed nine-entry offset array in order. It derives the passenger-centered block coordinates with `floor(this.x/z) + 0.5`, builds the passenger-sized candidate box using the vehicle's minY and `floor(vehicle.minY) + this.height`, then checks collision and solid-top support. The first collision-free supported candidate at the passenger's current Y calls `requestTeleport(u, this.y + 1.0, v)` and returns. Otherwise the scan may update the fallback to `y + 1.0` for support below or `y + 2.0` for a clear raised box. After the scan, the fallback is passed to `requestTeleport`.

Boat and horse are explicitly excluded from that generic directional search and enter the special branch in the same method. For a boat, the passenger's horizontal offset angle is `0.0F`; for a horse it is `(float)(Math.PI / 2) * (mainArm == RIGHT ? -1 : 1)`. Both then use the same width sum plus `0.4`, sine/cosine and vehicle yaw to derive the side position. The passenger is first placed at `vehicle.y + vehicle.height + 0.001`; if the union box is not collision-free it is retried at `+1.001`, and if that also collides it falls back to `(vehicle.x, vehicle.y + passenger.height + 0.001, vehicle.z)`. These special branches call `setPosition`, not `requestTeleport`. The pair retains this branch contract and ordering. The enclosing `ServerPlayerEntity.stopRiding` correction subsequently requests the current resulting coordinates from the network handler. The inspected horse and boat classes implement passenger *riding-position* updates, not an override of the dismount selector.

The compared formulas match. Visible source differences are `BoundingBox` → `Box`, water tag symbol `FluidTags.field_15517` → `FluidTags.WATER`, changed method offsets from other version source edits, and decompiler local identifiers. These do not change the operations evidenced here.

### Server correction and enclosing packet gate

After inherited stand-up positioning, `ServerPlayerEntity.stopRiding()` compares the old and new vehicle. When they differ and `networkHandler != null`, it calls `networkHandler.requestTeleport(this.x, this.y, this.z, this.yaw, this.pitch)`. Since this is an instance call, the network handler's normal implementation is dynamically used. That method delegates to `teleportRequest(..., Collections.emptySet())`.

The handler computes relative-coordinate correction terms in X, Y, Z then yaw and pitch order (current value when the corresponding relative flag is set, otherwise zero), stores `requestedTeleportPos = new Vec3d(d,e,f)`, increments/wraps the request ID, records the tick, applies `player.setPositionAnglesAndUpdate(d,e,f,g,h)`, then sends `PlayerPositionLookS2CPacket(d-i,e-j,f-k,g-l,h-m,flags,id)`. The paired code has the same ordering and casts/literals. The packet gate in `onPlayerMove` first handles a non-null outstanding `requestedTeleportPos` by resending only after more than 20 ticks; its `else` is the ordinary vehicle/non-vehicle movement path. The `isInTeleportationState()` checks surrounding speed and wrong-position corrections also occur in the same positions in both versions. Thus the correction remains in force before ordinary movement processing while a request is outstanding.

### Client recipient application

`ClientPlayNetworkHandler.onPlayerPositionLook` first forces main-thread handling, snapshots the client player's velocity, and reads packet XYZ and angles. For each position axis in X/Y/Z order, a relative flag adds the packet delta to the current position and retains the velocity component; otherwise it sets previous render position to the packet absolute value and sets that velocity component to `0.0`. It then writes velocity, applies relative pitch then yaw, updates player position/angles, sends teleport confirmation, and sends a `PlayerMoveC2SPacket.Both` using current position/rotation and `false`. The two methods are operation-for-operation equal in the checked section; enum names changed from intermediary field names to `X/Y/Z/X_ROT/Y_ROT`.

## Residuals

- This is not a complete 1.14.2/1.14.3 player-tick inventory or independent coverage audit.
- Source establishes the send call and pending-request logic, not transport delivery or client event arrival.
- The introduction point for any behavior not inspected at intermediate patch versions remains unknown.

## Terminal digest

SHA-256 of this exact UTF-8 LF file (no BOM) is reported in the handoff.