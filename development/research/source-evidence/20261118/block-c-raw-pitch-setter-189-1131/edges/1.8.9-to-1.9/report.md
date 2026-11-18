# 1.8.9 → 1.9.0 direct local-player pitch response

## Bounded result

The `Entity.updateLocalPlayerCamera(float yaw, float pitch)` pitch write and terminal clamp are textually identical in the inspected 1.8.9 and 1.9 sources. There is no setter-delta finding for this pair. The ordinary local-player movement consumer changes in 1.9: `LivingEntity.moveRelative` adds an `isFallFlying()` branch that reads the player's live `pitch` and alters velocity before the ordinary walking branch. In this pair, that is the new movement consequence of the same direct pitch response.

This is bounded to local player view pitch and its direct movement use. It does not cover general pair tick inventory, renderer behavior, input scheduling, remote entities, projectiles, server behavior, or other 1.9 movement differences. `1.9` is the exact available release ID; this establishes only the 1.8.9-to-1.9 boundary.

## Exact source identity and readiness

- Older: Minecraft `1.8.9`, `ornithe-feather`, under `decompiled_minecraft/1.8.9/ornithe-feather/`.
- Newer: Minecraft `1.9` (1.9.0), `ornithe-feather`, under `decompiled_minecraft/1.9/ornithe-feather/`.
- The 1.9 `.decompile-provenance.json` binds the source to the Minecraft client input SHA-256 `0273b5c79d6b96799c6480ae61366d6b1d042655970de87f77f1a3f1ebea9307`, Feather metadata `https://meta.ornithemc.net/v3/versions/feather/1.9` SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`, and Feather Gen2 mapping artifact `1.9+build.2` SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`. Its toolchain/pipeline and source-file digests are in that manifest.
- The 1.8.9 tree has no `.decompile-provenance.json` at the expected root. Its source has been read directly and hashed below, but exact input/mapping provenance and canonical source readiness cannot be verified from this tree. No source was generated or modified.

## Pitch setter and ordinary mouse direction

Older source: `decompiled_minecraft/1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `setRotation` and `updateLocalPlayerCamera`, lines 221–242. Newer source: `decompiled_minecraft/1.9/ornithe-feather/net/minecraft/entity/Entity.java`, same members, lines 269–290. In both, `setRotation` stores `pitch % 360.0F`; ordinary mouse camera updates do not call that setter. `updateLocalPlayerCamera` captures old pitch in a `float`, writes yaw first, then first writes pitch as:

```java
this.pitch = (float)(this.pitch - pitch * 0.15);
this.pitch = MathHelper.clamp(this.pitch, -90.0F, 90.0F);
this.lastPitch = this.lastPitch + (this.pitch - f);
```

The argument and field are `float`. The literal `0.15` is `double`, so the multiplication/subtraction occur in double precision and the explicit cast rounds once to float before the clamp. The clamp is inclusive at `-90.0F` and `90.0F`; no modulo follows it. `lastPitch` receives the post-clamp float difference. These operations and their order match across the pair.

For ordinary non-smooth mouse handling, the caller in each `GameRenderer` computes `g = f * f * f * 8.0F`, `i = mouse.y * g`, sets `j` to `1` unless invert-Y is enabled (then `-1`), and passes `i * j` as the method's `pitch` argument. `Mouse.y` is an integer delta assigned from `Mouse.getDY()` in `client/Mouse.java` (1.9 lines 6–22). Thus, absent invert-Y, positive raw mouse Y becomes a positive pitch argument and the setter subtracts it; with invert-Y, caller sign is reversed. This caller trace does not assume raw mouse direction equals stored-pitch direction. The smoothing path adjusts the delta first but has the same sign multiplication and setter destination.

A finite post-conversion argument greater than 360 is reachable from an ordinary finite integer mouse delta: the source multiplies that delta by a positive sensitivity gain and forwards the resulting float; it does not cap the value at one revolution. For example, a raw Y delta of 400 with a legal setting yielding gain 1.0 passes `400.0F` (or `-400.0F` when inverted). The setter then applies the exact `0.15` scale and final clamp above. This is argument context, not a claim that the stored pitch can exceed its clamp.

Caller-only references: 1.8.9 `GameRenderer.java` lines 848–868; 1.9 `GameRenderer.java` lines 833–853. These are cited only to establish mouse-Y sign and float conversion.

## Direct movement consumer

In 1.8.9, `LivingEntity.moveRelative` (lines 1119–1217) has no fall-flying test or pitch-dependent flight branch; its locally controlled movement path uses ordinary movement and `updateVelocity`. A source search of the 1.8.9 entity tree found no `isFallFlying`/Elytra movement branch.

In 1.9, `LivingEntity.tickMovement` calls `this.moveRelative(this.sidewaysSpeed, this.forwardSpeed)` at line 1698. `PlayerEntity.moveRelative` delegates to `super.moveRelative` in its ordinary non-creative-flight branches (lines 1366–1381). The superclass method is gated by local-control/logical-side movement and checks `isFallFlying()` at lines 1289–1299 before the ordinary walking branch. When true, it derives `i` from the live field `this.pitch * (float)(Math.PI / 180.0)`, obtains a look vector, and changes vertical and horizontal velocity from pitch-dependent terms (`cos(i)`, `sin(i)`, and the look vector) before moving. `isFallFlying()` reads flag 7 at line 2038. With fall-flying active and ordinary locally controlled player movement, camera pitch from the setter therefore reaches a direct movement calculation in 1.9; the old pair member has no such branch.

The concrete movement consequence is that changing pitch while fall-flying changes the next movement velocity response, including climb/dive behavior. This statement follows directly from the inspected expressions; no runtime trajectory was tested.

## Exact cited-byte digests

SHA-256 of source files (raw file bytes):

- 1.8.9 `net/minecraft/entity/Entity.java`: `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`
- 1.8.9 `net/minecraft/entity/living/LivingEntity.java`: `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`
- 1.8.9 `net/minecraft/client/render/GameRenderer.java`: `69b0007074901e5bd617f35039f122f21c67402340fe08b3daea1b316c6e7940`
- 1.9 `net/minecraft/entity/Entity.java`: `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a`
- 1.9 `net/minecraft/entity/living/LivingEntity.java`: `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`
- 1.9 `net/minecraft/entity/living/player/PlayerEntity.java`: `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1`
- 1.9 `net/minecraft/client/render/GameRenderer.java`: `d03fb41524c7b0ecf92696ae6aa8144fdce7a449f5e670a59b210d8f6b06a709`
- 1.9 `net/minecraft/client/Mouse.java`: `b6c9132938c64f2b79138e87de60cee4b965e6c28d485798d7131ec2bf0fd058`

For 1.9, those source digests are present in `.decompile-provenance.json`; the manifest itself is the identity record. The 1.8.9 digests provide content identity only and do not replace the missing provenance manifest.

