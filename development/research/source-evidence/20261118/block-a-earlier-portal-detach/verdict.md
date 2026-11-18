# Independent scoped verdict

Reviewed author input only after freezing roster commit `70ead27f1820905b9805bed0282a3f8b4f306e26`.

Input: `portal-earlier-profile-route` commit `4a400f38e8e423fe3630778c2a9d0575bb98238a`, `finding.md`. Committed blob SHA-256 verified directly from `git cat-file blob`: `4799296f44b73e30165f8ba3b999395c7961930968fe7ec52b00d354b71b5dc0`.

## Verdict

**Accept the narrow claim for exact 1.13.2 and 1.14:** ordinary player detach does not locally apply dismount placement, whether the mount is an ordinary entity or boat/horse. It does not show that there are no other local XYZ writers. The finding correctly excludes still-mounted `rideTick` updates and server-authoritative corrections; keep those exclusions explicit.

The route is:

- `PlayerEntity.tick` only performs sneak-triggered stop when `!world.isClient` (1.13.2 `PlayerEntity.java:426-430`; 1.14 `PlayerEntity.java:435-439`).
- `LivingEntity.stopRiding` captures the mount, delegates to `Entity.stopRiding`, then calls `dismountRider` only if the mount changed **and `!world.isClient`** (1.13.2 `LivingEntity.java:2030-2036`; 1.14 `LivingEntity.java:2322-2328`). `Entity.stopRiding` only clears the rider's mount link and removes it from the vehicle passenger list (1.13.2 `Entity.java:1645-1650`; 1.14 `Entity.java:1586-1591`).
- The server-only `dismountRider` callback has local XYZ writers: generic branch uses `teleport`; boat/horse branch uses `setPosition` (1.13.2 `LivingEntity.java:1375-1440`; 1.14 `LivingEntity.java:1666-1734`). The `BoatEntity` and `HorseBaseEntity` rider-position overrides are a different mounted update route, not dismount placement.
- Portal block collision only forwards to `onPortalCollision` when `!entity.isRiding() && !entity.hasPassengers() && entity.canUsePortals()` (both versions `PortalBlock.java:101-105`/`112-115`). `Entity.baseTick`'s portal transfer is server-world-only and only reaches `changeDimension` when not riding (1.13.2 `Entity.java:356-378`; 1.14 `Entity.java:1675-1684`). No portal path in the reviewed methods detaches a rider.
- The actual client passenger route in both versions is `ClientPlayNetworkHandler.handleEntityPassengers`: it calls `entity.yeetPassengers()`, then `startRiding(entity, true)` for packet IDs (1.13.2 `ClientPlayNetworkHandler.java:874-888`; 1.14 `:913-931`). `yeetPassengers` calls each passenger's `stopRiding`. On the client, that reaches the `LivingEntity.stopRiding` mount-link cleanup but fails the `!world.isClient` callback gate, so no local generic or boat/horse dismount-placement write occurs. `LocalClientPlayerEntity.stopRiding` only delegates and clears rowing (1.13.2 `:165-168`; 1.14 `:162-165`); no XYZ write.

## Narrow correction / exclusions

The author's phrase “A client passenger update can invoke the stop callback” is imprecise: it invokes `stopRiding`, but the client-side guard prevents `dismountRider` itself from running. Suggested wording: “A client passenger packet can call the player's `stopRiding`, which clears the mount link; because `world.isClient` is true, it does not invoke `dismountRider` or perform dismount placement.” This is a precision fix, not a change to the verdict.

Still-mounted position writes remain: `Entity.rideTick` invokes the vehicle's `updateRiderPositon`; generic, boat, and horse helpers can set the passenger position on the client. That is expressly outside this finding's dismount-placement conclusion. Packet reconciliation can also reattach a player through `startRiding`; no detach placement occurs there.

Disconnect/reconnect is a separate lifecycle route. In both versions `ClientPlayNetworkHandler.onDisconnect` ultimately closes/clears the client world; 1.13.2 `Minecraft.setWorld(null)` calls `closeWorld`, sets connection/world state to null, but retains its player reference in the reviewed method; 1.14 `Minecraft.disconnect` additionally sets `player = null`. A subsequent login/reconnect can create/reset/reposition a player by login/respawn handling. Such lifecycle or authoritative positioning is not evidence that ordinary detach itself writes XYZ. The finding's correction exclusion should remain; it should not be expanded into a claim that reconnect cannot write player coordinates.

No alternate local dismount-placement writer was found in this bounded route. **Not reviewed:** other disconnect-time side effects, all player XYZ writers, other release pairs, or intermediate patches. Vehicle/horse helper details used to distinguish mounted motion were cross-checked in `BoatEntity.java` and `HorseBaseEntity.java` on both sides.

## Exact source identity and hash checks

1.13.2: `decompiled_minecraft/1.13.2/ornithe-feather`, Feather Gen2 `1.13.2+build.2`; ready marker records version/mapping, 2,711 source files, and source manifest SHA-256 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The actual source manifest hash matched the ready marker.

1.14: `decompiled_minecraft/1.14/ornithe-feather`, `net.ornithemc:feather-gen2:1.14+build.2`; provenance records raw client SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`, mapped client SHA-256 `ae171032ec6ad71505488c9c5578c5c04168c74ee2cd822177c24e79d45834d8`, and 3,152 source files. Actual source manifest hash matched provenance/ready marker `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`.

For both releases, every reviewed Java source file's SHA-256 matched its line in `ornithe-feather.sources.sha256`. The frozen roster is in `roster.md`; added follow-through files were only `LivingEntity.java`, `PlayerEntity.java`, `HorseBaseEntity.java`, and `Minecraft.java` to close the assigned callback and reconnect route.

This verdict is limited to 1.13.2/1.14 and the supplied local-detach claim. It makes no earlier-profile or patch-introduction claim.

