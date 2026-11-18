# Independent source audit: 1.11.1–1.11.2 gliding look slice

## Verdict

**Confirmed: no difference in the assigned operation.** The immutable input report has SHA-256 `42cf9929340fd9b6fdecc7e24eb405fe5f57e9df3277b6cf079d80a85dfab8ed`; the bytes at its stated path match that hash. This verdict is limited to the ordinary dry local-player path with fall-flying already active, a finite negative near-vertical pitch, and finite positive horizontal look magnitude. It is not a full-pair coverage or acceptance verdict.

## Exact source identity

Both inputs are local canonical Ornithe Feather Gen2 build 2 / Vineflower 1.12.0 trees:

- `decompiled_minecraft/1.11.1/ornithe-feather/`, exact version 1.11.1, client SHA-256 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`, mappings `feather-gen2-1.11.1+build.2-mergedv2.jar` and `feather-gen2-1.11.1+build.2.tiny`. Ready-marker SHA-256 `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`; it binds manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`, confirmed against the local manifest. Provenance SHA-256 `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`.
- `decompiled_minecraft/1.11.2/ornithe-feather/`, exact version 1.11.2, client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`, mappings `feather-gen2-1.11.2+build.2-mergedv2.jar` and `feather-gen2-1.11.2+build.2.tiny`. Ready-marker SHA-256 `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`; it binds manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, confirmed against the local manifest. Verification record SHA-256 `1ff7d450309db002c7b9e42319f933ea6db75f0622d45996b3674ecb7e5c8fb4`; it records 1,921 source files checked, no source differences or missing files, and only a regenerated derived remapped-jar difference.

The local source-file SHA-256 values were checked directly and match the manifests: `LivingEntity.java` is `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` on both sides; `Entity.java` is `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` (1.11.1) and `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` (1.11.2); `Vec3d.java` is `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` on both sides; `MathHelper.java` is `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` on both sides.

## Independent trace

In each `LivingEntity.java`, `moveRelative(float,float)` is at lines 1388–1431 and the full source-file hashes are identical. The enclosing gates require local control or logical-side movement update, dry conditions (water and lava checks both exempt a flying player), and `isFallFlying()`. Once these hold, the gliding calculation runs before the first `move(MoverType.SELF, ...)` call at line 1425.

The calculation obtains `getLookVector()` at line 1397. `LivingEntity.getLookVector()` (lines 1916–1918) calls `getRotationVector(1.0F)`; the tick-delta overload (lines 1921–1929) uses current pitch and head yaw for exactly `1.0F`. The inherited `Entity.getRotationVector(float pitch, float yaw)` body is identical at 1.11.1 lines 1229–1234 and 1.11.2 lines 1216–1221, despite the changed enclosing `Entity.java` hashes. It computes the trig components as floats and constructs the vector from those float results.

Within `moveRelative`, `i` is a float from `pitch * (float)(Math.PI / 180.0)`. `j` is a double from `Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)`; `k` is the corresponding horizontal velocity magnitude; `l` is `vec3d.length()`. `Vec3d.length()` at line 92 calls `MathHelper.sqrt(double)`, whose line 28 casts `Math.sqrt` to float (widened back to double by the return type); these files have identical hashes across the pair.

For the stated finite near-vertical case with negative pitch and stipulated positive finite horizontal magnitude, the source takes `i < 0.0F`; the horizontal-magnitude stabilization gate `j > 0.0` is true. The descending-look correction additionally requires post-gravity `velocityY < 0.0` and `j > 0.0`; that first condition depends on state and is not inferred from pitch. Both gated calculations precede drag and the first `move` call. The lookup, magnitudes, signs, casts, gates and order show no adjacent-version difference. No trajectory was simulated.

## Bounds and output identity

Read-only exact-source audit only. No product files, reports beyond the assigned frozen input, wiki material, Git history, network, source generation, build, tests, or game/runtime launch were used. Output: `verdict.md` in the sibling audit directory. Its SHA-256 is recorded by the file hash after writing.

