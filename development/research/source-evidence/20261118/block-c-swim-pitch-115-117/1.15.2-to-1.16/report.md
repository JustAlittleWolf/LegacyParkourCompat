# 1.15.2 → 1.16.0 pitch-directed swimming correction

## Result

No source difference was found in the bounded `Player.travel` pitch-directed swimming correction or its look-vector Y calculation. The ordinary nonpassenger path still conditionally adjusts vertical velocity before delegating to `LivingEntity.travel`; the correction's operation order, constants, tests, sample position, and Java numeric types match. The inherited movement dispatch remains `Entity.move(MoverType.SELF, this.getDeltaMovement())` from `LivingEntity.travel`; neither `Player` nor `LivingEntity` declares a `move` override.

This is a bounded no-difference result, not evidence that every movement slice in the release pair is covered.

## Exact source identity and intact-file checks

Both sides are the canonical local Mojmap decompilations; the `1.16` source tree is the exact 1.16.0 release. Ready marker and source-manifest SHA-256 values:

- 1.15.2: `decompiled_minecraft/1.15.2/mojmap.ready.json` SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; marker names version `1.15.2`, mapping `mojmap`, and source manifest `mojmap.sources.sha256` SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.
- 1.16.0: `decompiled_minecraft/1.16/mojmap.ready.json` SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; marker names version `1.16`, mapping `mojmap`, and source manifest `mojmap.sources.sha256` SHA-256 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`.

The exact per-file hashes below match their corresponding entries in those source manifests:

| Relative source path | 1.15.2 SHA-256 | 1.16.0 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |

The source file compared on each side is under `decompiled_minecraft/<version>/mojmap/`. Provenance manifests are `decompiled_minecraft/1.15.2/mojmap.provenance.json` (SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`) and `decompiled_minecraft/1.16/mojmap.provenance.json` (SHA-256 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9`).

## Compared operation

- 1.15.2 Mojmap `Player.travel(Vec3)`, `Player.java:1448–1478`.
- 1.16.0 Mojmap `Player.travel(Vec3)`, `Player.java:1380–1410`.

Both snapshot position first, then enter the correction only when `isSwimming() && !isPassenger()`. They assign `g = getLookAngle().y` (a `double`), choose `h = g < -0.2 ? 0.085 : 0.06` (double literals), and correct only if `g <= 0.0`, `jumping`, or the sampled block's fluid state is nonempty. The sample is `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`; its Y expression is double arithmetic in the written left-to-right order. If gated in, they read the current delta and set it to `vec32.add(0.0, (g - vec32.y) * h, 0.0)`. The subtraction and multiplication are double operations and the correction is added before either flight handling or `super.travel(vec3)`. The initial position snapshot is later used for movement statistics.

### Look-vector Y value and widening

Both sides' `Entity.getLookAngle()` returns `calculateViewVector(this.xRot, this.yRot)` (`Entity.java:1661–1663` and `1680–1682`). In both `Entity.calculateViewVector(float f, float g)`, `f` is pitch: `h = f * (float)(Math.PI / 180.0)` is a float multiplication; `m = Mth.sin(h)` is a float; the Y argument passed to `new Vec3(...)` is unary-negated `-m`, still float, then widened to the vector's double coordinate. The `getLookAngle().y` read therefore yields the exact double representation of that float result, rather than a double-precision sine. Yaw is the second parameter and only contributes to the X/Z components. The methods and expression order match across the pair (`Entity.java:1219–1227` vs `1237–1245`).

### Inherited move reached

Neither `Player.java` nor `LivingEntity.java` declares `move(MoverType, Vec3)` on either side; the only matching declaration in the entity hierarchy files checked is `Entity.move` (`Entity.java:450` in 1.15.2; `:469` in 1.16.0). `super.travel(vec3)` dispatches to `LivingEntity.travel`; on its ordinary water movement path, that method calls `this.move(MoverType.SELF, this.getDeltaMovement())` (1.15.2 `LivingEntity.java:1950`; 1.16.0 `:1945`). Since `SELF` is used, the `PISTON`-only limiter does not apply. The called method is therefore the inherited `Entity.move`, which handles the resulting displacement and collision. This comparison records the dispatch and does not claim full collision-provider or fluid-mechanics coverage.

## Evidence locations

- 1.15.2 `Player.travel`: `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/entity/player/Player.java:1448–1478`.
- 1.16.0 `Player.travel`: `decompiled_minecraft/1.16/mojmap/net/minecraft/world/entity/player/Player.java:1380–1410`.
- 1.15.2 `Entity` look vector: `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/entity/Entity.java:1219–1227`, `1661–1663`.
- 1.16.0 `Entity` look vector: `decompiled_minecraft/1.16/mojmap/net/minecraft/world/entity/Entity.java:1237–1245`, `1680–1682`.
- Inherited fluid-path move call: `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/entity/LivingEntity.java:1950`; `decompiled_minecraft/1.16/mojmap/net/minecraft/world/entity/LivingEntity.java:1945`.
- `Entity.move` declarations: `decompiled_minecraft/1.15.2/mojmap/net/minecraft/world/entity/Entity.java:450`; `decompiled_minecraft/1.16/mojmap/net/minecraft/world/entity/Entity.java:469`.


