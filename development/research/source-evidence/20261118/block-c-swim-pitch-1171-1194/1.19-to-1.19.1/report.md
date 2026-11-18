# 1.19 to 1.19.1 source trace: swimming pitch Y writer

## Result

No source difference was found in the active, nonpassenger `Player.travel` swimming pitch-to-vertical-velocity operation between Minecraft 1.19 and 1.19.1 (Mojmap). The pre-`super.travel` writer, its angle calculation, float sine lookup, threshold selection, short-circuit gate, and inherited water movement call are unchanged. This bounded result is not a claim of full-pair movement coverage.

## Exact source identity and readiness

Both canonical local trees are `decompiled_minecraft/<version>/mojmap/`, with exact release IDs verified by the ready marker and provenance. Their ready markers report status `ready`, Vineflower, Java runtime 25, and successful required-source diagnostics. The source manifest hashes match the ready-marker identities and each named file hash below matches its manifest entry.

- 1.19: ready marker SHA-256 `b00a68db8600ec8cdc3917357aff59eb0537be6f382e621e5ed3eeb900898b88`; provenance SHA-256 `833cf50d6eab0f24475dcc11085c1414c85eee0e708ff17c59bc8dcd2ae9c35a`; source manifest SHA-256 `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26`; artifact manifest SHA-256 `eee4a52f029446e075ff10ef761cea9a621b40df6e66240b6323d1596ca60ebf`; diagnostics SHA-256 `e666eb5dd7bdbea46599f85b5ae720077338255df0b9d0cc5b63fe6f292faa4c`.
- 1.19.1: ready marker SHA-256 `c7b5373078eb777895a4f4b10d8c99eb289cb14fa1835004c1eb4563b6a8a690`; provenance SHA-256 `de0de05299c59d3b05d96e744b27f5a5cef377880ae4a7b402337aa0ffe00245`; source manifest SHA-256 `25a8e35111971be21a85fefd567ada685098f45e35e431163afdbe0fa66dfde1`; artifact manifest SHA-256 `aae6327967e07f5cdd92ac9bfa6d9c606e1be9a96e24dd30acad07fd2425df41`; diagnostics SHA-256 `afd25c98efb5dbf3ae434f5c7be063a176f28c2ae42d37ec60db9979d5301163`.

Both provenance records name Mojmap client mappings, Vineflower 1.12.0, TinyRemapper 0.14.1, Mapping IO 0.9.1, Java 25.0.3+9-LTS, and Gradle 9.7.1. Per-file SHA-256 values:

| Source | 1.19 | 1.19.1 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `4b5d3d71ac1c4b64efdeb1ad86a4b6f84afffe210e272850e620339430942de1` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` |
| `net/minecraft/world/entity/Entity.java` | `3a8ce3090d29aad1bbfeca31de300d34b42a7e902f0e78b13efd5653cd588766` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` |
| `net/minecraft/util/Mth.java` | `710e1a1f4e262b610406c707d8fab245d518570dd88b96828ffa9aa5a6fcda65` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` |
| `net/minecraft/world/entity/LivingEntity.java` | `00c591dd2cac49ee5ccad8705f7dd879ad5d29f0360b98742166294989b3531c` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` |

## Trace and comparison

- `Player.travel(Vec3)`, 1.19 `Player.java:1458-1489`; 1.19.1 `Player.java:1463-1494`. Each saves initial coordinates, then checks `isSwimming() && !isPassenger()`. In that branch it reads `double lookY = getLookAngle().y`, selects `double factor = lookY < -0.2 ? 0.085 : 0.06`, then applies the writer only if `lookY <= 0.0 || jumping || !fluidStateAtHead.isEmpty()`. The head sample is block state at `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`. On the true branch it snapshots the velocity and calls `setDeltaMovement(old.add(0.0, (lookY - old.y) * factor, 0.0))`. Operand types, constants, order and short-circuit order match exactly in both versions. The two flight/super branches and post-travel movement-statistics call are also unchanged in this method.
- `Entity.getLookAngle()`, 1.19 and 1.19.1 `Entity.java:1841-1843`, returns `calculateViewVector(getXRot(), getYRot())`. The angle route does not use interpolated rotations. `calculateViewVector`, 1.19 `Entity.java:1339-1346`; 1.19.1 `Entity.java:1334-1341`, computes float radians with `rotation * (float)(Math.PI / 180.0)`, negating yaw before its conversion. It obtains pitch sine/cosine and yaw sine/cosine from `Mth`, then constructs `Vec3(yawSin * pitchCos, -pitchSin, yawCos * pitchCos)`. These expressions are unchanged. `pitchSin` is a float negated as float and widened when passed as the `Vec3` double Y argument; yaw does not contribute to the vertical component.
- `Mth.sin(float)` and `Mth.cos(float)`, 1.19 `Mth.java:26-48`; 1.19.1 `Mth.java:27-49`, use the same 65,536-entry float `SIN` table. Entries are `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. Sine indexes `SIN[(int)(angle * 10430.378F) & 65535]`; cosine uses `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. The float multiplication/addition, Java float-to-int truncation, mask, table initialization and constants are identical.
- `LivingEntity` calls `this.travel(new Vec3(this.xxa, this.yya, this.zza))` from its tick movement stage (1.19 `LivingEntity.java:2580-2586`; 1.19.1 `:2583-2589`); `Player.travel` therefore executes the pre-super writer before inherited travel. In `LivingEntity.travel`, an effective-AI or locally controlled entity takes the movement path. The water branch requires `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluidState)`, then calls `moveRelative`, followed by the real `move(MoverType.SELF, getDeltaMovement())` before post-move drag/falling adjustment (1.19 and 1.19.1 `LivingEntity.java:2042-2086`). This branch and call order are identical. The player writer itself has no logical-side check; no server behavior is inferred here.

The pitch-sensitive vertical term predicts convergence of velocity Y toward look-vector Y by the selected factor when its gate passes; this is source-level meaning only, not a measured trajectory result. Since the compared implementation is unchanged, this pair yields no finding for this operation.
