# 1.9.3 → 1.9.4 — blind yaw slice

Result: **No change in the assigned ordinary LocalPlayer yaw slice.** This is bounded source coverage, not a full movement-pair freeze.

## Exact source identities

- 1.9.3: `ornithe-feather/.decompile-provenance.json`, SHA-256 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`; local source hashes below match its manifest, which pins client `1.9.3` and Feather Gen2 `1.9.3+build.2`.
- 1.9.4: the version-root `ornithe-feather.ready.json` SHA-256 is `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`; it identifies exact version `1.9.4`, mapping `ornithe-feather`, status `ready`, and the source manifest SHA-256 `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. The local `ornithe-feather.sources.sha256` matches that digest, and each inspected source hash below matches its manifest entry. The separate verification record says 1,819 published source files matched with zero differences and all raw source inputs matched. The regenerated intermediate mapped jar had a recorded derived-artifact difference; source files and raw inputs did not.

## Compared operation path

`LocalClientPlayerEntity` feeds local movement inputs to `LivingEntity.moveRelative` (both line 1302). Its local-control and dry/non-fall-flying gates, ground/air scale selection, and call to `Entity.updateVelocity` are unchanged. `Entity.updateVelocity` (both line 947) retains the same float normalization and current-yaw trig/order/sign: X `sideways*cos - forwards*sin`; Z `forwards*cos + sideways*sin`. `LivingEntity.jump` (both line 1279) retains the same sprint-only current-yaw impulse, already present in 1.9.

The current/prior yaw storage remains two floats. `setRotation` remains `% 360.0F` without a finite guard. The position-and-angles setter assigns incoming yaw to both fields before computing their difference, so its ±180-degree branches are inert for finite input; its subsequent `setRotation` changes current yaw only, leaving prior yaw at the supplied value. The local-camera method adds `yawDelta * 0.15` to current yaw (double intermediate, then float cast), then advances `lastYaw` by the current-yaw delta; input-device production is outside this slice. Neither path checks finiteness. Tick still snapshots yaw before movement. `MathHelper.sin/cos` is unchanged. Ground and air acceleration scales are separate physics constants; input-device production is excluded.

## Exact source payload hashes

| File | 1.9.3 SHA-256 | 1.9.4 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` |
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |



