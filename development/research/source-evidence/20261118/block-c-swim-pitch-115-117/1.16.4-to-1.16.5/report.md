# 1.16.4–1.16.5 pitch-directed swim Y adjustment

## Scope and verdict

Bounded comparison: Mojmap `Player.travel(Vec3)`'s swimming pitch adjustment for an ordinary local-controlled player who is swimming and not riding, including its look-vector math, conditional state query, and the inherited movement call. **No source difference found** in this operation or the dependencies examined. This closes only this bounded slice; it is not a full-pair coverage or runtime-parity verdict.

## Exact source evidence

Both inputs are exact local Mojmap decompilations. The relied-on files below have the same SHA-256 in both releases, and the corresponding source inventories record those same hashes:

| Relative source | 1.16.4 and 1.16.5 SHA-256 | Relevant members |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | `travel(Vec3)`, lines 1387–1418 |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | `calculateViewVector(float,float)`, 1243–1252; `getLookAngle()`, 1700–1701; `move(MoverType,Vec3)`, from 485 |
| `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` | `sin(float)` and `cos(float)`, 25–30 |
| `net/minecraft/world/phys/Vec3.java` | `b889010c321c45b497e71ff782d108a718ed23a5f605d6162904045e1e01fd48` | double-component constructor, 39; `add(double,double,double)`, 78–80 |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | `travel(Vec3)`, from 1914; water branch's `move`, 1947; ordinary movement helper's `move`, 2065 |

The 1.16.4 mapping-root `.decompile-provenance.json` SHA-256 is `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`; its `sources` entries bind all four relied-on file hashes above. The 1.16.5 version-root ready marker identifies version `1.16.5` and mapping `mojmap`. Its source-manifest hash is `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`, matching the marker; the manifest binds all four file hashes above. The ready marker also binds artifact manifest SHA-256 `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442b8b370c`. Source files were read-only; no regeneration or raw-cache use was performed.

## Behavior and ordering

In both `Player.travel` bodies, the adjustment runs first when `isSwimming() && !isPassenger()`. It reads `getLookAngle().y` once into a `double`; then picks a `double h` of `0.085` only when look-Y is below `-0.2`, otherwise `0.06`. The nested OR is short-circuiting, in this order: look-Y `<= 0.0`, `jumping`, then a block-state query at `BlockPos(getX(), getY() + 1.0 - 0.1, getZ())` whose fluid state must be nonempty. If any term is true, the current movement vector is read and replaced with `vec32.add(0.0, (g - vec32.y) * h, 0.0)`. The expression uses the double look-Y and double constants; the Y component changes before the superclass travel call.

The inherited `Entity.getLookAngle()` passes the current float `xRot` and `yRot` to `calculateViewVector`. That method does float multiplication by `(float)(Math.PI / 180.0)`, negates yaw, and calls `Mth.cos(float)` / `Mth.sin(float)`. The 65,536-entry float sine table is initialized by `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)` (double expression then float cast). Each lookup indexes it using `(int)(f * 10430.378F) & 65535` (cos adds `16384.0F` before the cast). The resulting float Y, `-m`, widens to double in the `Vec3(double,double,double)` constructor and remains double when assigned to `g`. `Vec3.add(double,double,double)` then computes each component in double precision. These expressions and casts match exactly across the pair.

For the ordinary nonflying local path, `Player.travel` then calls `super.travel(vec3)`. `LivingEntity.travel` admits the local path through `isEffectiveAi() || isControlledByLocalInstance()`. When the water/fluids/not-standable-on-fluid branch is active, it performs `moveRelative` and then `move(MoverType.SELF, getDeltaMovement())`, before its water damping and falling adjustments. Otherwise the inherited normal movement helper also issues `move(MoverType.SELF, ...)`. The enclosing `Entity.move` handles actual movement/collision. The flying branch remains ordered after the swim adjustment in `Player.travel` and has its own saved-Y handling; it is unchanged in both versions.

## Result

No 1.16.4→1.16.5 difference for this pitch-directed swim-Y operation. The cited source/member bodies and source hashes are identical.

