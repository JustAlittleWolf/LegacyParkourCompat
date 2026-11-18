# 1.14 → 1.14.1 ordinary local yaw precision

## Exact source identities

- `decompiled_minecraft/1.14/yarn/`: release 1.14, Yarn `1.14+build.21`; manifest pins client SHA-256 `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a`, mapping artifact SHA-256 `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64`, and `net/minecraft/entity/Entity.java` SHA-256 `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a`.
- `decompiled_minecraft/1.14.1/yarn/`: release 1.14.1, Yarn `1.14.1+build.10`; manifest pins client SHA-256 `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814`, mapping artifact SHA-256 `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630`, and `net/minecraft/entity/Entity.java` SHA-256 `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b`.

The source file hashes above match each tree's `.decompile-provenance.json` `sources` entry and the on-disk SHA-256. These are distinct exact release/mapping trees; this report compares their Yarn sources only.

## Compared source slices

- `net/minecraft/client/Mouse.java`, `onMouse` update, lines 195–220 (both): smoothing/raw cursor deltas are selected, cursor deltas are reset, Y inversion is applied, then a non-null client player receives `changeLookDirection(j, k * n)`. This caller flow and operand order are identical.
- `net/minecraft/entity/Entity.java`, `changeLookDirection`, lines 313–325 (both): `e * 0.15` and `d * 0.15` are computed as doubles; current pitch/yaw are each added before casting to float; current pitch is clamped; previous pitch/yaw are incremented with the same double deltas and cast; previous pitch is clamped; passenger callback follows. No yaw clamp, wrap, or finite check was added to this ordinary local input path.
- `net/minecraft/entity/Entity.java`, `updateVelocity` / `movementInputToVelocity`, lines 1009–1024 (both): acceleration uses `this.yaw`; the helper keeps the squared-length `< 1.0E-7` zero gate, normalization only above `1.0`, float angle and trig values, and the same X/Z rotation expression and order.
- `net/minecraft/entity/LivingEntity.java`, `travel` and `method_18802`, lines 1763–1823 and 1938–1940 (both): the ordinary non-fall-flying land path computes the first ground/air relative acceleration with `method_18802(slipperiness)`, which returns `onGround ? getMovementSpeed() * (0.21600002F / (slipperiness * slipperiness * slipperiness)) : field_6281`, then passes that and input to inherited `updateVelocity`. The first ground/air relative-acceleration use and surrounding order are unchanged. Separate lava and water branches also call inherited `updateVelocity` later in `travel` (lines 1847 and 1881). The fall-flying branch instead uses the rotation vector and is outside this ordinary relative-acceleration slice.
- `net/minecraft/entity/Entity.java`, `setPositionAndAngles`, lines 1048–1068 (both): current yaw is assigned from `g`, previous yaw is then copied from current yaw, and the same `double i = prevYaw - g` / `< -180.0` / `>= 180.0` checks adjust previous yaw before position and `setRotation` calls. Order and comparisons match.
- `net/minecraft/entity/Entity.java`, `getYaw(float)`, lines 1176–1178 (both): exact `f == 1.0F` current-yaw selection, otherwise `MathHelper.lerp(f, prevYaw, yaw)`, unchanged. In `readNbt`, lines 1381–1387 (both), position finiteness still throws on invalid coordinates while yaw/pitch are accepted only when both `Double.isFinite`; accepted rotation is passed to `setRotation` with no added yaw wrap.

## Difference and movement meaning

No difference found in the assigned 1.14→1.14.1 ordinary local yaw precision and first ground/air relative-acceleration slices. In both releases, the mouse delta is multiplied as a double and added to current and previous float yaw with explicit casts in the same order; ordinary ground/air relative acceleration reads current yaw through the identical rotation helper. This bounded comparison supports no source-backed yaw precision behavior change for this patch pair. It does not establish full player-movement pair coverage.

## Evidence hashes

SHA-256 of cited decompiled source files:

| Source | 1.14 Yarn | 1.14.1 Yarn |
| --- | --- | --- |
| `client/Mouse.java` | `8e9595a7306be13b81de1960e68f3ad883d7529fe1739b10248b0afd6413b2de` | `dedc0f972addc92ad335ea86210f71c4f3de56dcbebb81958cc509189b94d742` |
| `entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
