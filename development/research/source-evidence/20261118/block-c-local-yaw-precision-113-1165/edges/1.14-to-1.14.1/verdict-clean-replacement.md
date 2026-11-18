# Independent source audit: 1.14 → 1.14.1 yaw and ordinary acceleration

## Phase-one evidence freeze (before method inspection)

Canonical source roots: `decompiled_minecraft/1.14/yarn/` and `decompiled_minecraft/1.14.1/yarn/`.
Both roots use Yarn. Their manifest-listed source hashes match the files read-only in those roots.

| Release | Provenance manifest SHA-256 | Version metadata input | Client artifact input | Yarn mapping artifact input |
|---|---|---|---|
| 1.14 | `EA70A955A12F377CD3B9E8D5F346F4625B044D55DA726B6BC472405AE91F4341` | `https://piston-meta.mojang.com/v1/packages/0fd01dd81eaa451d3130b1cf025a10f129585b10/1.14.json` — `3d05fc911c8559105767713365ea1c29bbbeccf57214f286c86a598ce51f22a6` | `https://piston-data.mojang.com/v1/objects/7a762a59345c13af7d87111207a93f5a8607f6c0/client.jar` — `93907cbf0655aa5f10d049efa4d745f4250eecc5d0d5d1268046eec3144f323a` | `https://maven.fabricmc.net/net/fabricmc/yarn/1.14+build.21/yarn-1.14+build.21-v2.jar` — `0233edadf36a28099f6bb86e920c423b728ab5d75618da9046d92d287f12de64` |
| 1.14.1 | `308537C5ADEC3104C5CA243EB5010554F00978635ED74F0D7801C804DD4D4907` | `https://piston-meta.mojang.com/v1/packages/183e49ba0fabb5ef8a304f8f5907fa887f40c466/1.14.1.json` — `71c39840d012fcbe8fedb7be876bd008365b62ec7b1c9c5c7bd34d9a29c8ca22` | `https://piston-data.mojang.com/v1/objects/55ba86ddcbc3579397f41910463ffd4056e1e523/client.jar` — `7194d1326cf796f62ac55f3cb56f851c232895639ae27510f3207d5a73f91814` | `https://maven.fabricmc.net/net/fabricmc/yarn/1.14.1+build.10/yarn-1.14.1+build.10-v2.jar` — `f9462af25135abeb22cbdec829cc8618b1a8dd902f17259abfbe7399a222a630` |

The exact manifests also pin the version metadata, toolchain, remapper/decompiler and all generated source files. Hashes below are manifest values and were checked against the corresponding file bytes.

| Relative source path under `decompiled_minecraft/<release>/yarn/` | 1.14 SHA-256 | 1.14.1 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `f52483d5e1434114b81b1c353e89f246d1557bda7a95400832f5650b89cc744a` | `ce429699f2d8f0a54f4be516783a474c6c7808572950164508ceed0baf68ed5b` |
| `net/minecraft/entity/LivingEntity.java` | `8889e22779b9124998931bc97ce8b0d183d335c32a9bcbb089e49de65882bae6` | `331e2d7dfa936a6f7197910ba374c4a60b479c8565ff7c64a4d6a9e1e8b52dda` |
| `net/minecraft/entity/player/PlayerEntity.java` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` | `ebda4ad25a265f8c85e88345efcce753b78e248a29a9b910c5cfd311a4a69319` |
| `net/minecraft/client/network/AbstractClientPlayerEntity.java` | `b220f4851a6d07eb0880c78f080a47afbcb3af0e2ea40401f9df758d97e53bc4` | `b220f4851a6d07eb0880c78f080a47afbcb3af0e2ea40401f9df758d97e53bc4` |
| `net/minecraft/client/network/ClientPlayerEntity.java` | `92d0f97a9293f7bd57d49af00b70f13d78fc8b2202e7ed67edcfed917e0071be` | `6af7e75e80619ad77ce2c84692136b0a1d562a44d0e7910dcc3f6040b2dc9933` |
| `net/minecraft/client/input/Input.java` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` | `abb37170563ca28cafcf20b27b75036b4ee99cb0ec9775702beef6f97cebc351` |

Phase-one provenance and source hashes were frozen before method inspection. The following is the independent source-body trace.
## Additional helper-source freeze (before helper-body inspection)

The ordinary velocity transform calls math/vector helpers. These were added to the evidence boundary and hashed against each manifest before reading their method bodies; both releases have identical pinned bytes:

| Relative source path | Manifest and actual SHA-256 in both releases |
|---|---|
| `net/minecraft/util/math/MathHelper.java` | `21ed846ac48a5a2fbcdb2ac31fd5b74a461485effd881112c903c09d9297504a` |
| `net/minecraft/util/math/Vec3d.java` | `c56acd6b7b03e1a05d01687c490b5533c53c1b97ba637f7492a2d6d1744b55dc` |

No methods in these helper files had been read at the time of this freeze.
## Independent bounded verdict

**Result: no source difference found for the assigned operation.** In the inspected exact Yarn sources, current/previous yaw handling, casts, finite gate, and the ordinary dry ground/air player acceleration path are the same in 1.14 and 1.14.1. This says nothing about other movement operations or an exact change boundary inside this interval.

### Current and previous yaw

In both `Entity.java`, `setRotation` stores `yaw = f % 360.0F` (`:299-302`). `changeLookDirection` computes `e * 0.15` and `d * 0.15` as doubles, adds each to current pitch/yaw with explicit casts to float, clamps only current pitch, then adds the same double deltas to previous pitch/yaw with float casts and clamps only previous pitch (`:313-325`). It does not wrap yaw or apply a finite check. `baseTick` snapshots `prevYaw = yaw` after copying previous pitch and position (`:345-351`). The expressions and order match in both releases.

`Entity.getYaw(f)` returns current `yaw` only when `f == 1.0F`; otherwise it interpolates `prevYaw` to `yaw` (`Entity.java:1168-1178`, both). The unmounted `ClientPlayerEntity.getYaw(f)` returns current `yaw` directly (`:167-169`, both). Most decisively for this operation, `Entity.updateVelocity` passes raw current `this.yaw` to the movement transform (`Entity.java:1009-1012`, both); `prevYaw` is not read by this acceleration.

`setRotation` and `lookAt` retain their respective `% 360.0F` and `MathHelper.wrapDegrees` paths (`Entity.java:299-302`, `2489-2500`, both). In the inspected entity-load path, the float yaw/pitch must pass `Double.isFinite(this.yaw) && Double.isFinite(this.pitch)` before position/rotation setup runs (`Entity.java:1381-1387`, both). This finite gate applies there; the live `changeLookDirection` path above has none. No gate or precision change is present between releases.

### Ordinary client-player acceleration path

The client player tick movement calls `input.tick(...)`; its `tickNewAi` copies `input.movementSideways` and `input.movementForward` into `sidewaysSpeed` and `forwardSpeed` for the camera player (`ClientPlayerEntity.java:607-620, 588-599`, both). `ClientPlayerEntity.tickMovement` then delegates to `PlayerEntity.tickMovement` (`:762-763`, both), which delegates to `LivingEntity.tickMovement` (`PlayerEntity.java:475-493`, both). That living tick constructs the travel vector from those speed fields and dispatches `travel(...)` (`LivingEntity.java:2198-2205`, both). `PlayerEntity.travel` delegates to `super.travel(arg)` when the player is not in the flying branch (`PlayerEntity.java:1362-1387`, both). For ordinary dry movement, `LivingEntity.travel` enters the non-water/non-lava, non-fall-flying branch and calls `updateVelocity(this.method_18802(v), arg)` before moving (`LivingEntity.java:1763-1765, 1772-1775, 1817-1824`, both). The same call serves grounded and airborne movement; no separate horizontal speed/friction conclusion is made here.

### Numeric transform and ordering

Both `Entity.java:1009-1024` implement the same order:

1. `updateVelocity(float f, Vec3d arg)` computes `movementInputToVelocity(arg, f, this.yaw)` and adds the result to the existing velocity.
2. The helper computes `d = arg.lengthSquared()` and returns `Vec3d.ZERO` only for `d < 1.0E-7`.
3. Otherwise it uses `arg.normalize()` only for `d > 1.0`, keeps `arg` at or below that threshold, then multiplies by `f`.
4. It computes `h = MathHelper.sin(g * (float)(Math.PI / 180.0))` followed by `i = MathHelper.cos(...)`, then returns `(lv.x * i - lv.z * h, lv.y, lv.z * i + lv.x * h)`.

The exact helper implementations also match byte-for-byte in the two source sets. `MathHelper.sin` and `cos` use the 65,536-entry sine table; each index expression multiplies the float angle by `10430.378F`, with cosine adding `16384.0F`, then casts to `int` and masks with `65535` (`MathHelper.java:11-29`). `Vec3d.lengthSquared`, `normalize`, and scalar `multiply` preserve the same double products and order; normalization uses `MathHelper.sqrt` and returns `ZERO` below `1.0E-4` (`Vec3d.java:25-28, 75-97`). These inputs, thresholds, casts, table lookup, and expression order are unchanged across 1.14→1.14.1.

### Evidence ranges compared

All compared Java files are the exact canonical paths under the two frozen Yarn roots. Relevant body ranges were read on both sides and matched line-for-line for the operation: `Entity.java:299-321, 1009-1024, 1168-1178, 1381-1387`; `LivingEntity.java:1763-1844, 2127-2205`; `PlayerEntity.java:1362-1387`; `ClientPlayerEntity.java:167-169, 588-599, 607-620, 762-763`; `MathHelper.java:11-29`; `Vec3d.java:25-28, 75-97`. (The client call path and load finite gate are relevant context; this verdict is bounded to yaw and the first ordinary yaw-relative acceleration.)

## Phase-two comparison against the authorized author report

Compared only with `c-yaw-precision-114-1141-source/report.md`, whose pre-read SHA-256 was verified as `ca4a4af2f76a1e5b8887c08c0373e127a21b166dad26cf0662c87b9050ef1eb1`.

**Agreement on the overlapping bounded verdict.** The author report's conclusion of no 1.14→1.14.1 source difference agrees with this independent trace for `Entity.changeLookDirection`, `updateVelocity`/`movementInputToVelocity`, the raw-current-yaw input, the ordinary dry travel call into the yaw transform, `getYaw(float)`, and the `readNbt` finite gate. The casts, branches, thresholds and transform order in those overlapping claims match my independently inspected slices.

**Additional author claims remain outside this independent verification.** The author report also cites the `Mouse.onMouse` caller, `Entity.setPositionAndAngles`, and the numeric body of `LivingEntity.method_18802`. I did not independently inspect those bodies in this bounded audit, so this verdict neither accepts nor disputes those added details. I confirmed only the ordinary dry `travel` call passes `method_18802(v)` and input to `updateVelocity`; its coefficient is not part of this yaw-transform finding. No separate horizontal speed/friction stream is asserted here.
