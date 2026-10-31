# Independent review — bed-wake BlockGetter route

## Verdict

**ACCEPT**, with the limitations below. The 1.16.5 shape/supplier `BlockGetter.getBlockFloorHeight` overload is reachable from a LocalPlayer bed-wake response and can contribute to the coordinates passed to `setPos`. The route claim and the bed-exit scope disposition are source-supported. The overload is absent in 1.15.2, whose bed stand-up path uses a different floor-height search.

This accepts the bounded route/paired-source claim only. It does not accept a new movement finding, establish an exact trajectory divergence, freeze the pair, or establish an intermediate patch boundary.

## Independent source checks

Exact local roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap` and `...\1.16.5\mojmap` (Mojmap). The `BlockGetter` hashes in the submitted `source-hashes.tsv` match both files. All entries in that source hash table recomputed successfully. The endpoint source-manifest hashes match the cited ready-record identities: 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

- Both versions have the wake packet route: `ServerPlayer.stopSleepInBed` sends `ClientboundAnimatePacket(this, 2)` while sleeping using `broadcastAndSend`; `ChunkMap.TrackedEntity.broadcastAndSend` sends to the entity's own connection. `ClientPacketListener.handleAnimate` resolves the packet ID and action 2 calls `Player.stopSleepInBed(false, false)` in both versions. The client entity corresponding to the originating server player is the local player. Thus this is a conditional client response route, not server-side historical movement.
- `Player.stopSleepInBed(false, false)` calls `super.stopSleeping()` in both endpoint sources. `LivingEntity.stopSleeping` gates on a present sleeping position in a loaded chunk and a `BedBlock`, then obtains a stand-up position and calls `setPos`. In 1.16.5 it also derives yaw from the selected position and reapplies the same position after switching to standing pose.
- In 1.16.5, bed candidate search calls `DismountHelper.findSafeDismountLocation`; that calls the audited overload with the current non-climbable collision shape and a lazy supplier for the shape below. The overload returns current-shape `max(Y)` when nonempty; otherwise it gets the fallback maximum and returns `max - 1` when that maximum is at least 1, or negative infinity. `findSafeDismountLocation` then validates the floor, constructs a candidate, and returns it only if the candidate entity box has no block collisions. Its result is therefore an input to the position committed by `LivingEntity.stopSleeping`.
- The overload does not exist in 1.15.2. The older bed candidate route uses `BedBlock.getStandingLocationAtOrBelow`: it rejects a current collision shape with max Y greater than `0.4375`, searches down through empty shapes by at most two blocks, computes support Y as block Y plus shape max Y plus `2.0E-7`, applies the two-block vertical limit, then uses `noCollision`. The paired bed algorithms are not one-for-one helper replacements; candidate order and surrounding checks are also different.
- The new route is bed **exit** position selection. Bed-entry eligibility remains out of scope. A bed wake can directly write a LocalPlayer position, so it is eligible for movement review under the project boundary even though the initiating server event and the route are not ordinary `Entity.move` collision iteration.

## Limitations to preserve

The same packet-to-`setPos` route already exists in 1.15.2; only the helper and bed candidate algorithm differ at these endpoints. The report should not imply that bed-wake position placement first became movement-reachable in 1.16.5. The API-level old/new comparison alone does not isolate the behavioral contribution of this overload from the accompanying `BedBlock` and `DismountHelper` changes, nor prove a concrete candidate/trajectory divergence for a reachable map state. Any eventual finding needs a concrete precondition and must attribute the difference to the paired logic accurately. No intermediate releases were inspected.

No implementation, product source, wiki inventory, backlog, tests, build, runtime, or decompilation was used or changed.
