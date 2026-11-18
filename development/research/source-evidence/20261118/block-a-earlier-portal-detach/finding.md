# Earlier-profile portal dismount route

## Scope and verdict

Bounded comparison: exact Minecraft 1.13.2 to exact 1.14, Ornithe Feather mapping family. Question: can a portal-tagged riding player detach locally and have ordinary dismount placement itself write client player XYZ, including boat/horse cases?

**Verdict: no, for both examined releases.** Portal handling is server-world-only and advances to `changeDimension` only when `!isRiding()`. It does not detach a rider. An ordinary sneak dismount is also server-only. `LivingEntity.stopRiding` computes dismount placement only when `!world.isClient`; its general branch calls `teleport`, and its boat/horse special branch directly calls `setPosition`, under that same server-only guard. The client-side local `stopRiding` override only clears rowing state after delegating. A client passenger update can invoke the stop callback, but that local callback has no XYZ placement write. This conclusion excludes separate server-to-client absolute position corrections.

Still mounted is distinct: `Entity.rideTick` calls the mount's `updateRiderPositon` while still riding. Generic, boat and horse implementations write rider XYZ and have no client-side guard; the client passenger tick therefore remains a possible XYZ writer while mounted. This is not a dismount-placement write.

## Exact source evidence

All paths below are relative to `decompiled_minecraft/<release>/ornithe-feather/`.

### 1.13.2

- `net/minecraft/entity/Entity.java:357-376`: portal logic is guarded by `!world.isClient && world instanceof ServerWorld`; it increments portal time and calls `changeDimension` only under `!isRiding()`; no detach call.
- `net/minecraft/entity/Entity.java:1575-1593`: still-mounted `rideTick` calls `entity.updateRiderPositon(this)` when still riding; generic helper writes rider XYZ.
- `net/minecraft/entity/Entity.java:1645-1650`: `stopRiding` only clears the mount/passenger link.
- `net/minecraft/entity/living/player/PlayerEntity.java:426-430`: sneak-triggered `stopRiding` is gated by `!world.isClient`.
- `net/minecraft/entity/living/LivingEntity.java:1375-1440`: generic dismount placement and boat/horse offset placement. The special boat/horse branch makes direct `setPosition` writes; it is reached only through the server-gated override below.
- `net/minecraft/entity/living/LivingEntity.java:2030-2036`: after `super.stopRiding`, invokes `dismountRider` only if the mount changed and `!world.isClient`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:165-168`: local override delegates to `super.stopRiding()` and clears `rowing`; it has no placement write.
- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:880-888`: passenger packet route; client passenger links are reconciled by `yeetPassengers` / `startRiding`.
- `net/minecraft/entity/vehicle/BoatEntity.java:629-648` and `net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:938-951`: mounted rider-position helpers write rider XYZ; distinct from detach.

### 1.14

- `net/minecraft/entity/Entity.java:1668-1680`: portal transfer runs in server world; `changeDimension` is reached only under `!isRiding()`; it does not detach a rider.
- `net/minecraft/entity/Entity.java:1519-1530`: still-mounted `rideTick` calls `updateRiderPositon`; generic helper writes rider XYZ.
- `net/minecraft/entity/Entity.java:1586-1591`: `stopRiding` only clears the mount/passenger link.
- `net/minecraft/entity/living/player/PlayerEntity.java:435-439`: sneak-triggered `stopRiding` is gated by `!world.isClient`.
- `net/minecraft/entity/living/LivingEntity.java:1666-1734`: general dismount placement plus boat/horse direct `setPosition` branch.
- `net/minecraft/entity/living/LivingEntity.java:2322-2328`: the dismount placement callback is gated by `!world.isClient`.
- `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:162-165`: local override only delegates and clears rowing.
- `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java:913-931`: passenger packet route reconciles links with `yeetPassengers` / `startRiding`.
- `net/minecraft/entity/vehicle/BoatEntity.java:625-644` and `net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:921-934`: mounted rider-position helpers write rider XYZ; distinct from detach.

## Source and mapping identity

Exact source roots: `decompiled_minecraft/1.13.2/ornithe-feather` and `decompiled_minecraft/1.14/ornithe-feather`.

- 1.13.2 provenance identifies `versionId=1.13.2`, `namespace=ornithe-feather`, mapping artifacts Feather Gen2 `1.13.2+build.2`. Mapping artifact SHA-256 values in `decompiled_minecraft/1.13.2/artifacts.sha256`: mergedv2 JAR `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`; Tiny `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`. Source manifest SHA-256: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- 1.14 provenance identifies exact `versionId=1.14`, `namespace=ornithe-feather`, mapping coordinate `net.ornithemc:feather-gen2:1.14+build.2`. Mapping mergedv2 JAR SHA-256 `57f0e3227099d010c4a0c7f2fea03dbd665bc7e612ad204db80fa5b5728e65a3`; Tiny SHA-256 `d865d01921c115a1c20dfdc1b7cdca1c59ff1694cad54bd79e146d553417d31f`. Raw client SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`; mapped client SHA-256 `ae171032ec6ad71505488c9c5578c5c04168c74ee2cd822177c24e79d45834d8`. Source manifest SHA-256: `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.
- Every compared Java file's content SHA-256 matched its release's `ornithe-feather.sources.sha256` entry at review time.

This is a bounded 1.13.2/1.14 result only; it does not establish earlier release behavior or an implementation boundary.
