# Independent blind source audit: portal/dismount placement route

## Scope and freeze correction

Exact sources reviewed read-only: 1.8.9, 1.9, and 1.12.2, all `ornithe-feather`. Bounded question: an ordinary client mount-removal route; 1.8.9 type-0 attach with attached entity id −1, including an old boat; then 1.9 and 1.12.2 ordinary empty passenger lists. Already-mounted tick, authoritative player-position correction, and generalized vehicle physics are outside this conclusion. No older/later alias expansion.

The initial pre-claim freeze omitted `LivingEntity.startRiding`; it is superseded by this corrected, fully closed virtual chain. The corrected final route is below.

## 1.8.9 exact route and verdict

`AttachEntityS2CPacket(int type, Entity entity, Entity attached)` writes `attached.getNetworkId()` or `−1` when attached is null (`net/minecraft/network/packet/s2c/play/AttachEntityS2CPacket.java:18–20`). `ClientPlayNetworkHandler.handleAttachEntity` resolves the attached id through the client world, replaces the entity with `minecraft.player` when the packet entity id is the local player's network id, and for type `0` calls `entity.startRiding(entity2)` (`net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:735–759`). The constructor's `−1` value resolves to no entity, so this is a virtual `startRiding(null)` call on the actual `LocalClientPlayerEntity`.

The complete class chain is `LocalClientPlayerEntity` → `ClientPlayerEntity` → `PlayerEntity` → `LivingEntity` → `Entity`. `LocalClientPlayerEntity.startRiding` (97–102) calls `super.startRiding(entity)` and optionally plays minecart sound. Neither `ClientPlayerEntity` nor `PlayerEntity` overrides this method. The invoked inherited override is `LivingEntity.startRiding` (1483–1499): when `vehicle != null && entity == null`, it checks `!world.isClient` before `dismountRider(vehicle)`, then clears `vehicle.rider` if still non-null and sets `vehicle = null`. On the logical client, that branch bypasses `Entity.startRiding(null)` entirely. No call to `setPositionAndAngles`, `setPosition`, or `setShape` occurs. This applies to a boat as to another old vehicle; there is no vehicle-class or portal-tag guard.

If the local player has no old vehicle, the `LivingEntity` branch condition is false and it delegates to `Entity.startRiding(null)`. The base null branch's own `if (vehicle != null)` is also false; it clears the already-null field and makes no position or AABB write. Thus both old-vehicle and no-old-vehicle states have no position writer on this local-client route. The base writer that would have snapped an entity to `vehicle.x`, `vehicle.shape.minY + vehicle.height`, `vehicle.z` is reachable only when `Entity.startRiding(null)` is reached with a non-null vehicle; the inherited client-side `LivingEntity` gate prevents that case here.

Writer roster: **none** on either actual local-client null-detach branch. The counterfactual base writer chain is `Entity.startRiding` (1343–1370) → `Entity.setPositionAndAngles` (899–907) → `Entity.setPosition` (226–233) → `Entity.setShape` (1820 onward), but the mounted local-client route does not enter it. There is no portal-tag or portal-state check in the packet handler, `LocalClientPlayerEntity.startRiding`, `LivingEntity.startRiding`, or the null branch of `Entity.startRiding`.

## 1.9 and 1.12.2 ordinary empty passenger list

In both releases, `ClientPlayNetworkHandler.handleEntityPassengers` gets the vehicle entity, calls `entity.yeetPassengers()`, then loops over the packet passenger ids. An ordinary empty list runs no `startRiding` call. If the local player is among the vehicle's existing passengers, `yeetPassengers` invokes virtual `stopRiding` on that rider.

In both `LocalClientPlayerEntity` classes, `stopRiding` calls `super.stopRiding()` and resets `rowing`; it does not write position or AABB. Base `Entity.yeetPassengers` iterates riders and calls `stopRiding`; base `Entity.stopRiding` clears the mount and removes the passenger. This route does not call `startRiding(null)`, `setPosition`, `setPositionAndAngles`, or `setShape`. The 1.9 and 1.12.2 finding is therefore a state-only passenger detach on an empty-list packet, unlike the 1.8.9 attach route, and makes no immediate placement change. No portal-tag check appears in the handling or detach path.

## Source identities

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`; direct SHA-256 values were compared with each available sidecar/index.

1.8.9 has `decompiled_minecraft/1.8.9/ornithe-feather.provenance.json` (SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`), ready marker `ornithe-feather.ready.json` (`fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`), and source index `ornithe-feather.sources.sha256` (`9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`). The indexed hashes match all reviewed source files.

1.9 has mapping-local `.decompile-provenance.json` (SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`). Its source hashes match the three reviewed files: handler `7f67bd2daa792cfc4aac1744d30f9cfdb28306ec55583d474ddc1a251561b1d6`, local player `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06`, entity `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`.

1.12.2 has `decompiled_minecraft/1.12.2/ornithe-feather.provenance.json` (SHA-256 `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`), ready marker `ornithe-feather.ready.json` (SHA-256 `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`), and source index `ornithe-feather.sources.sha256` (SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`). The indexed hashes match all reviewed source files.

Reviewed line anchors: 1.8.9 packet, handler, LocalClientPlayerEntity, LivingEntity and Entity above; 1.9 handler `787–808`, local player `135–139`, `154–157`, Entity `1455–1508`; 1.12.2 handler `833–850`, local player `135–158`, Entity `1614–1673`.