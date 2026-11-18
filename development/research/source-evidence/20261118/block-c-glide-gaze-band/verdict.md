# Independent source audit: glide near-vertical gaze

**Terminal status: COMPLETED**  
**Verdict: SUBSTANTIVE CLAIM ACCEPTED WITH A BOUNDARY CORRECTION**

## Immutable input binding

Audited frozen input `c-glide-gaze-source/comparison.md`, SHA-256 `2dcb0b22b9a64e875e85b356145febe95832b83b486e0ad77ba6d63da235a6d0`; the locally computed SHA-256 matches the assigned value.

Sources were read-only Ornithe Feather decompilations for exact releases 1.12, 1.12.1, 1.12.2, 1.13, 1.13.1 and 1.13.2. The 1.12/1.12.1/1.12.2 files for `LivingEntity.java`, `Entity.java`, and `MathHelper.java` have identical SHA-256 values respectively (`190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`, `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`, `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80`). The 1.13.1/1.13.2 files likewise match each other (`bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c`, `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69`). The 1.13.0 corresponding hashes are `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`, `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`, and `ad7615e7acf9d5ebe1d7cfc236a7b2dc7726c42a32bd46254ba29f9fc68c567f`.

## Independently checked claim

The source formulas and guard difference are present in all three releases on each side. In 1.12.x, `LivingEntity.moveRelative` uses `j = sqrt(look.x² + look.z²)`, enters the pitch-up block on `i < 0.0F`, and divides the horizontal updates by `j` without a `j > 0` guard (1.12.2 lines 1434–1453). `Entity.getRotationVector` uses `h = -cos(-pitch * degrees)` for the horizontal scale (1.12.2 lines 1239–1245). In 1.13.x, `getRotationVector` multiplies both horizontal components by `cos(pitch * degrees)` (1.13.2 lines 1251–1259), and `moveRelative` requires `j < 0.0F && k > 0.0` before the pitch-up correction (1.13.2 lines 1493–1513). The naming changed, but the 1.13 `k` is the horizontal look length and `l` is horizontal velocity length.

Both mapping families use the same table-index operation: sine truncates `x * 10430.378F`; cosine truncates `x * 10430.378F + 16384.0F`; both mask with `65535`. The table initializes each entry as `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`, so entry zero is exactly zero and entry 32767 is a positive `0.0000958738019107841F`.

I replayed each intermediate operation as binary32, including the degree conversion `(float)(Math.PI / 180.0)`, multiplication by that constant, the `10430.378F` product, cosine offset addition, truncation, and mask. The exact zero-cosine band contains **718** representable pitches:

- First: `-89.99999237060547F` (`0xC2B3FFFF`), angle `-1.5707961320877075F` (`0xBFC90FD9`), table index 0.
- Last: `-89.99452209472656F` (`0xC2B3FD32`), angle `-1.570700764656067F`, table index 0.
- First inward nonzero sample: `-89.99451446533203F` (`0xC2B3FD31`), angle `-1.5707005262374878F`, table index 1 and cosine `0.0000958738019107841F`.

**Correction to frozen comparison:** it gives the last in-band pitch as `-89.9945068359375F`. That is `0xC2B3FD30`, two representable-float steps inward from the actual last zero-index value; it does not match the stated 718-value count. The count and the stated offset-719 first-nonzero sample are otherwise consistent. Update the upper endpoint of the zero band to `-89.99452209472656F` if reusing that text.

For the same first interior pitch, the 1.12 source computes the positive angle `1.5707961320877075F`; its cosine index is 32767, and the horizontal scale is nonzero. Replaying the exact 1.12 rotation-vector float operations gives horizontal look lengths about `9.58738e-5` at yaws 0, 37, 90 and 123.456 degrees. Thus the old denominator is positive for these finite-yaw samples. For an illustrative nonzero horizontal velocity `(0.3, 0.4)`, the source's `k = sqrt(vx² + vz²)` is `0.5`; `sin(i)` is `-1.0F`, so the old pitch-up coefficient `o = k * -sin(i) * 0.04` is `0.02` and adds `0.064` to vertical velocity before later movement operations. This checks the local source arithmetic only; it makes no trajectory or terrain claim.

The 1.13 pitch-derived horizontal cosine is exactly zero throughout the band, making both horizontal look components zero for finite yaw and its horizontal-look `k` exactly zero. The added `k > 0.0` predicate therefore bypasses the pitch-up correction over these strictly interior angles. `Entity.updateLocalPlayerCamera` clamps pitch to `[-90.0F, 90.0F]` in all six releases; its float/double update and cast path admits interior float values adjacent to the lower clamp. This establishes source-level reachability. The audit did not rerun the exact `-90` endpoint case; that prior accepted evidence remains separate.

## Limits respected

This audit is limited to the frozen comparison, the assigned exact Feather sources, and the assigned prior endpoint context. It did not consult wiki material, product implementations or history, other discovery lanes, or broader movement evidence, and did not run tests, builds, gameplay, TAS/Gym/server sessions, or runtime checks. No terrain, trajectory, or exact-endpoint conclusion is made here.
