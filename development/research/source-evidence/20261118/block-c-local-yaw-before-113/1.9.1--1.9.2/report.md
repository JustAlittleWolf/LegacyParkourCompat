# 1.9.1 → 1.9.2 — blind yaw slice

Result: **No change in the assigned ordinary LocalPlayer yaw slice.** This is bounded source coverage, not a full movement-pair freeze.

## Exact source identities

Both sides are local exact-version decompilations under `decompiled_minecraft/<version>/ornithe-feather/`; Feather is the only mapping family used.

- 1.9.1: `ornithe-feather/.decompile-provenance.json`, SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`. Its pinned manifest names client `1.9.1` and Feather Gen2 `1.9.1+build.2`; the four inspected Java files match the per-source hashes.
- 1.9.2: `ornithe-feather/.decompile-provenance.json`, SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`. Its pinned manifest names client `1.9.2` and Feather Gen2 `1.9.2+build.2`; the four inspected Java files match the per-source hashes.

## Compared operation path

`LocalClientPlayerEntity` passes the current movement inputs into `LivingEntity.moveRelative` (both line 1293). For a locally controlled player in dry, non-fall-flying movement, the method selects ground scale `getSpeed() * (0.16277136F / (s * s * s))` or air scale `speedInAir`, then calls `Entity.updateVelocity`. The separate scales are context, not part of this yaw comparison.

`Entity.updateVelocity` (both line 943) has the same float normalization threshold, normalization order, current `float yaw` to float-radians expression, `MathHelper.sin/cos` calls and signed X/Z additions. `LivingEntity.jump` (both line 1270) retains the same grounded sprint-jump yaw impulse. The corresponding local-player tick and `MathHelper` source hashes are identical across these releases.

`yaw` and `lastYaw` are `float`. `setRotation` uses `% 360.0F` with no finite check. `updatePositionAndAngles` assigns incoming yaw to both fields before computing their difference, so its ±180-degree branches are inert for finite input; its later `setRotation` changes current yaw only, leaving prior yaw at the supplied value. The local-camera method adds `yawDelta * 0.15` to current yaw (double intermediate, then float cast) and then advances `lastYaw` by the current-yaw delta; input-device production is outside this slice. Neither path checks finiteness. Tick order still snapshots current yaw as prior before movement. No scoped source difference was found.

## Exact source payload hashes

| File | 1.9.1 SHA-256 | 1.9.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |



