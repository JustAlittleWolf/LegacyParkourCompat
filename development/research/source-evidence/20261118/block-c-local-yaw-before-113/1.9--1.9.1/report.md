# 1.9 → 1.9.1 — blind yaw slice

Result: **No change in the assigned ordinary LocalPlayer yaw slice.** This is bounded source coverage, not a full movement-pair freeze.

## Exact source identities

Both sides are local exact-version decompilations under `decompiled_minecraft/<version>/ornithe-feather/`; Feather is the only mapping family used.

- 1.9: `ornithe-feather/.decompile-provenance.json`, SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`. The pinned manifest names client `1.9` and Feather Gen2 `1.9+build.2`; the four inspected Java files match the manifest's per-source hashes.
- 1.9.1: `ornithe-feather/.decompile-provenance.json`, SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`. The pinned manifest names client `1.9.1` and Feather Gen2 `1.9.1+build.2`; the four inspected Java files match the manifest's per-source hashes.

## Compared operation path

`LocalClientPlayerEntity` supplies `sidewaysSpeed` and `forwardSpeed` during its local-player tick. `LivingEntity.moveRelative` (1.9 line 1289; 1.9.1 line 1293) admits locally controlled movement, then its dry, non-fall-flying branch chooses the ground or air input scale and calls `Entity.updateVelocity`. Ground scale uses `getSpeed() * (0.16277136F / (s * s * s))`; air scale uses `speedInAir`. Those are separate physics constants and are recorded only as branch context.

`Entity.updateVelocity` (both versions line 943) normalizes nontrivial float inputs, computes `float` radians from the **current** `this.yaw`, calls the same table-backed `MathHelper.sin/cos`, and applies X as `sideways*cos - forwards*sin` and Z as `forwards*cos + sideways*sin`. The exact statement order, casts, sign and `< 1.0E-4F` input gate match. `LivingEntity.jump` (1.9 line 1266; 1.9.1 line 1270) also has the same sprint-only current-yaw impulse: X subtracts `sin(yaw)*0.2F`, Z adds `cos(yaw)*0.2F`. This sprint jump path already exists in 1.9.

`Entity.yaw` and `lastYaw` remain `float`. `setRotation` applies `% 360.0F` without a finite-value guard; `updatePositionAndAngles` first assigns the incoming value to both `lastYaw` and `yaw`, then computes `lastYaw - yaw`; for finite input this is zero, so the ±180-degree branches do not adjust it. The later `setRotation` applies `yaw % 360.0F` to current yaw only, leaving prior yaw at the supplied value. The ordinary local-camera method instead updates current yaw by `yawDelta * 0.15` (double intermediate, then float cast), then adds the current-yaw delta to `lastYaw`; that input-device producer is outside this slice. Neither path has a finite-value check. The ordinary entity tick copies current yaw to prior yaw before movement. None of these scoped statements changed across this pair. Mouse/input-device production is outside this slice.

## Exact source payload hashes

| File | 1.9 SHA-256 | 1.9.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |


