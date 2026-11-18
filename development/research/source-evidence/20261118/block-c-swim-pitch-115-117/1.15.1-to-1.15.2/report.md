# 1.15.1 → 1.15.2: player pitch-directed swim correction

## Result

No source change was found in the requested ordinary, active, nonpassenger player swim-correction operation or its following inherited movement path. The extracted `Player.travel(Vec3)` method body is byte-for-byte equal across the two Mojmap sources (UTF-8 body SHA-256 `cd4385e5f6986c16dedd181882c022c8acf8339538e3fb455761d24b64dfe60c`). The complete `Entity.java` source is also identical across the pair. This closes only this bounded operation; it does not establish whole-pair movement coverage.

## Exact behavior and ordering

In both releases, `Player.travel(Vec3)` first saves the current x/y/z positions. It then gates the correction on `isSwimming() && !isPassenger()`. This gate is evaluated before the later flying branch and does not itself test `abilities.flying`.

The pitch-directed value is `double g = this.getLookAngle().y`. `getLookAngle()` calls `calculateViewVector(this.xRot, this.yRot)` using current rotation, without interpolation. In that method, pitch `xRot` is a `float`; `h = xRot * (float)(Math.PI / 180.0)` is computed as a float, then `m = Mth.sin(h)` is float. `Mth.sin` indexes its float sine table at `(int)(h * 10430.378F) & 65535`. The y component passed to `new Vec3(k * l, -m, j * l)` is `-m`, still float; the `Vec3(double, double, double)` constructor widens it to the `double` field `y`. Thus `g` is a widened, table-sampled float derived from pitch, not a double-precision trig result. Yaw does not enter this y component.

The coefficient is selected next: `0.085` when `g < -0.2`, otherwise `0.06`; both are double literals. The correction is applied only if `g <= 0.0`, or `jumping`, or the short-circuited block/fluid sample is nonempty. That sample is constructed from `(getX(), getY() + 1.0 - 0.1, getZ())`; `BlockPos(double, double, double)` delegates to `Vec3i`, which floors each coordinate with `Mth.floor`. The sample tests `getFluidState().isEmpty()` with no water-only tag check. When admitted, the current velocity is fetched and its y component is updated as `oldY + (g - oldY) * coefficient`; x and z are unchanged.

Only after this correction does the player enter the flying/nonpassenger branch or call `super.travel(vec3)`. In the ordinary nonflying water path, inherited `LivingEntity.travel` applies `moveRelative(g, vec3)` and then calls `move(MoverType.SELF, this.getDeltaMovement())`; this dispatches to inherited `Entity.move`. The move receives the already corrected velocity (plus the inherited relative-input change), before water drag and the later gravity adjustment. The water branch itself is selected by `isInWater()` and is bypassed for a flying Player by the parent condition. No part of this ordering differs between the two releases.

## Exact source references

All references below are exact Mojmap decompilations. The line offsets differ in `Player.java`; operation text is equal.

- 1.15.1 `Player.travel`: `decompiled_minecraft/1.15.1/mojmap/net/minecraft/world/entity/player/Player.java`, lines 1456–1486. 1.15.2: corresponding file, lines 1448–1478.
- `LivingEntity.travel`: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/LivingEntity.java`, method begins at line 1831; the ordinary water path's `moveRelative` and inherited move are lines 1949–1950.
- `Entity.calculateViewVector`: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/entity/Entity.java`, lines 1219–1227; `getLookAngle` at lines 1661–1663; inherited `move(MoverType, Vec3)` begins at line 450. The entire `Entity.java` file is identical across the pair.
- `Mth.sin`: `decompiled_minecraft/<version>/mojmap/net/minecraft/util/Mth.java`, lines 25–27; `Vec3(double, double, double)`: `decompiled_minecraft/<version>/mojmap/net/minecraft/world/phys/Vec3.java`, lines 16–20.
- `BlockPos(double, double, double)`: `decompiled_minecraft/<version>/mojmap/net/minecraft/core/BlockPos.java`, lines 41–43; floor conversion: `Vec3i.java`, lines 23–25.

## Source identity and cited-byte verification

Both ready markers declare `status: ready`, exact version IDs 1.15.1 and 1.15.2, and mapping `mojmap`. Each marker's declared source-manifest SHA-256 matched the local `mojmap.sources.sha256` bytes, and its provenance SHA-256 matched the local provenance bytes. Each cited Java file's computed SHA-256 matched its entry in that release's source manifest.

- 1.15.1 ready marker SHA-256: `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504`; provenance SHA-256: `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`; source manifest SHA-256: `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`.
- 1.15.2 ready marker SHA-256: `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; provenance SHA-256: `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; source manifest SHA-256: `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.
- Cited source SHA-256 values that differ by release: `Player.java` 1.15.1 `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0`; 1.15.2 `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`. `LivingEntity.java` 1.15.1 `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79`; 1.15.2 `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`.
- Identical cited source bytes in both releases: `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `Mth.java` `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4`; `Vec3.java` `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909`; `BlockPos.java` `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950`; `Vec3i.java` `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`.