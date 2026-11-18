# 1.21.6 → 1.21.7 source slice: swimming pitch Y writer

## Verdict

No source difference in the assigned local `Player.travel` swimming pitch-to-vertical-velocity path. The exact source hashes for `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` match across both releases and match each side's embedded `.decompile-provenance.json` source pins. No numeric witness is applicable because the examined operation is unchanged.

## Evidence identity

Both sides are canonical local Mojmap decompilations. The hidden provenance manifests are present at `decompiled_minecraft/<version>/mojmap/.decompile-provenance.json`; recursive inspection found no additional version-root manifest. The manifest itself hashes are:

- 1.21.6 manifest SHA-256: `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e`.
- 1.21.7 manifest SHA-256: `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`.

Manifest input pins identify the exact client and Mojang client mappings for each version:

- 1.21.6: client `.../740a125b83dd3447feaa3c5e891ead7fbb21ae28/client.jar`, SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`; `client.txt` SHA-256 `2ff82b34ce5f849aaafdff51e23b9448c4263881238b780a441fe1061a6fc261`; exact version metadata SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd`.
- 1.21.7: client `.../a2db1ea98c37b2d00c83f6867fb8bb581a593e07/client.jar`, SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`; `client.txt` SHA-256 `328f2c5849c7c5fc4f2b1eae4f74a7cfe6bb337c7938242ac5c4f9eac89b1e50`; exact version metadata SHA-256 `b7ebf2fb9a9cc655f874386a55731e87fa20d35fcbae43bb92ddb4df081fc51a`.

Each listed source file was hashed from disk and matched its side's manifest source-map entry. Across both versions the identical source SHA-256 values are:

- `net/minecraft/world/entity/player/Player.java`: `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987`.
- `net/minecraft/world/entity/Entity.java`: `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704`.
- `net/minecraft/world/entity/LivingEntity.java`: `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8`.
- `net/minecraft/util/Mth.java`: `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b`.

## Traced operation (same in both releases)

- `Player.travel(Vec3)`, `Player.java:1438–1465`: passenger path calls `super.travel` before the swim block, so it skips this Player-specific pitch writer. On the nonpassenger path, `isSwimming()` gates the read of `getLookAngle().y`. It chooses a `double` coefficient of `0.085` when look Y `< -0.2`, otherwise `0.06`. It writes only when look Y `<= 0.0`, `jumping`, or the fluid at `BlockPos.containing(x, y + 1.0 - 0.1, z)` is nonempty. The write is `deltaMovement.add(0.0, (lookY - deltaY) * coefficient, 0.0)`. This precedes `super.travel`. If abilities are flying, the later post-super `with(Y, savedDeltaY * 0.6)` replaces that Y velocity; the ordinary nonflying case leaves the super travel result.
- `Entity.java:3705–3715`: `getXRot()` returns the stored `xRot` field directly (not an interpolated value). `setXRot` rejects non-finite input; otherwise stores `Math.clamp(value % 360.0F, -90.0F, 90.0F)`.
- `Entity.java:1746–1753, 2356–2358`: `getLookAngle()` passes current `getXRot()` and `getYRot()` to `calculateViewVector`. The pitch angle is formed in binary32 by `xRot * (float)(Math.PI / 180.0)`; its Y component is `-Mth.sin(pitchRadians)`, computed as float and widened to double when passed into `Vec3`.
- `Mth.java:32–36, 48–50`: `SIN` is a 65,536-element `float[]`; each element is `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` (double arithmetic, then float cast). `sin(float)` multiplies in float by `10430.378F`, truncates toward zero via `(int)`, masks with `65535`, then reads the float table. This table construction and lookup are identical in both versions.
- `LivingEntity.java:2245–2253, 2301–2342`: inherited travel chooses fluid travel when in water/lava, affected by fluids, and not standing on the sampled fluid. In water, `travelInFluid` calls `moveRelative`, then `move(MoverType.SELF, getDeltaMovement())`; only afterward it applies water drag and fluid-falling adjustment. For an active ordinary swimmer in water, the Player pre-super Y velocity therefore participates in the movement call.
- `Entity.java:663 onward`: `move(MoverType, Vec3)` is the inherited implementation reached by that call; neither `Player` nor `LivingEntity` declares an override. Its collision/position handling is unchanged because the pinned Entity source bytes match.

Compared ranges are identical byte-for-byte, so no historical movement difference or concrete changed consequence is established for this bounded operation.

