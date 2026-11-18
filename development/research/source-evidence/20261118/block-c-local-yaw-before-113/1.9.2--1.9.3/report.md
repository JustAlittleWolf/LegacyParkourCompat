# 1.9.2 → 1.9.3 — blind yaw slice

Result: **No change in the assigned ordinary LocalPlayer yaw slice.** This is bounded source coverage, not a full movement-pair freeze. The enclosing source files differ, so this verdict is based on the inspected methods and their gates, not file-level identity.

## Exact source identities

Both sides are local exact-version decompilations under `decompiled_minecraft/<version>/ornithe-feather/`; Feather is the only mapping family used.

- 1.9.2: `ornithe-feather/.decompile-provenance.json`, SHA-256 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`. Its pinned manifest names client `1.9.2` and Feather Gen2 `1.9.2+build.2`; inspected source hashes match it.
- 1.9.3: `ornithe-feather/.decompile-provenance.json`, SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`. Its pinned manifest names client `1.9.3` and Feather Gen2 `1.9.3+build.2`; inspected source hashes match it.

## Compared operation path

For local, dry, non-fall-flying movement, `LivingEntity.moveRelative` (1.9.2 line 1293; 1.9.3 line 1302) selects the existing ground/air input scale and calls `Entity.updateVelocity`. The compared `updateVelocity` bodies (1.9.2 line 943; 1.9.3 line 947) are statement-for-statement identical in the yaw-relative path: float input norm and threshold, float radians from current `yaw`, sine/cosine, then X `sideways*cos - forwards*sin` and Z `forwards*cos + sideways*sin`.

`LivingEntity.jump` (1.9.2 line 1270; 1.9.3 line 1279) is also unchanged in this path. A grounded sprinting player receives the same `-sin(yaw)*0.2F` X and `+cos(yaw)*0.2F` Z impulse. This impulse predates both sides of this pair. Table-backed `MathHelper.sin/cos` is unchanged. Ground and air scale constants remain separate physics context.

The yaw contract remains `float yaw` plus `float lastYaw`. `setRotation` still uses `% 360.0F`, with no finite guard; `updatePositionAndAngles` assigns the incoming yaw to both fields before computing their difference, making its ±180-degree branches inert for finite input. The later `setRotation` applies `% 360.0F` to current yaw only; prior yaw keeps the supplied value. The local-camera method adds `yawDelta * 0.15` to current yaw (double intermediate, then float cast), then advances `lastYaw` by the current-yaw delta; input-device production is outside this slice. Neither path checks finiteness. The local tick still snapshots yaw before movement. Input-device production is excluded.

## Exact source payload hashes

| File | 1.9.2 SHA-256 | 1.9.3 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `d8824f670037a88753c428c27213989fc5173b1d7c638f498f73395c145b6fd9` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |


