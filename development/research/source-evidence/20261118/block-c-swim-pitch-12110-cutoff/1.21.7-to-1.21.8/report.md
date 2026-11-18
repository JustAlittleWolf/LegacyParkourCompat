# 1.21.7 → 1.21.8 swim-pitch vertical correction

## Scope and evidence

Bounded slice: ordinary active, nonpassenger `Player.travel(Vec3)` while `isSwimming()` is true; the direct pitch-derived Y-velocity correction and its path into the inherited `travel` call. This is not a full-pair movement inventory.

Exact Mojmap decompiled sources were read from `decompiled_minecraft/1.21.7/mojmap/` and `decompiled_minecraft/1.21.8/mojmap/`. For 1.21.7, `.decompile-provenance.json` pins the client JAR (`6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`) and source hashes. For 1.21.8, `mojmap.ready.json` identifies version `1.21.8`, Mojmap, client JAR (`ea74e9c5e92d01f95f3d39196ddb734e19a077a58b4c433c34109487d600276f`), and source-manifest digest; the manifest, artifact manifest, and diagnostics digests match their files. The relevant per-file SHA-256 values match their declared source manifests:

| Source | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/Entity.java` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| `net/minecraft/util/Mth.java` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |

## Compared source path

In both releases, `Player.travel(Vec3)` (`Player.java:1438–1459`) first routes passengers straight to `super.travel`. Otherwise, while swimming, it samples `getLookAngle().y` once into a `double`, selects `0.085` when that value is below `-0.2` and `0.06` otherwise, then updates vertical velocity only when the sampled value is `<= 0.0`, `jumping` is true, or the fluid at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is empty. On that gate it reads the current movement vector and writes `movement.add(0.0, (lookY - movement.y) * scale, 0.0)`. No re-sampling occurs between the gate and write.

The correction precedes inherited movement. `Player` extends `LivingEntity` (`Player.java:128`), so the nonflying branch's `super.travel(input)` enters `LivingEntity.travel` (`LivingEntity.java:2245–2254`): that method samples the fluid state at `blockPosition()` and selects `travelInFluid`, `travelFallFlying`, or `travelInAir` under its shown gates. The flying branch samples the corrected Y velocity first, calls `super.travel(input)`, then writes Y as that saved value times `0.6`. Passenger travel bypasses the correction entirely. The inherited method and its dispatch gates are byte-identical in both source trees.

The look value is based on the current raw entity rotation: `Entity.getLookAngle()` calls `calculateViewVector(this.getXRot(), this.getYRot())` (`Entity.java:2356–2358`), and `getXRot()` returns the `float xRot` field directly (`Entity.java:204, 3705–3707`), without interpolation. The field has Java's default `0.0F` initialization; `setXRot` rejects nonfinite values and stores `Math.clamp(value % 360.0F, -90.0F, 90.0F)` (`Entity.java:3709–3714`). In `calculateViewVector` (`Entity.java:1746–1753`), pitch radians are evaluated first as `pitch * (float)(Math.PI / 180.0)` in `float`; the pitch component is `-Mth.sin(pitchRadians)`. That `float` is widened when passed into the `Vec3` double component, then read as `double` by `Player.travel`.

`Mth` uses the same table and lookup in both releases (`Mth.java:32–53`): a `float[65536]` is filled in ascending index order with `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)` (double arithmetic before the cast). `sin(float)` computes `(int)(angle * 10430.378F) & 65535`: multiplication is `float`, conversion truncates toward zero to `int`, then a bitmask selects the table index. `cos` uses the same process with `+16384.0F` before the cast. For the swim correction's pitch-derived Y component, only `sin` is used.

## Result

No difference found for this bounded operation between 1.21.7 and 1.21.8. The `Player.travel`, `Entity` raw-pitch/view-vector path, and `Mth` table/lookup sources are byte-identical and their sampled gates, numeric types, evaluation order, and writes are unchanged. This closes only the assigned direct correction slice; it does not establish full-pair coverage or an exact introduction boundary.
