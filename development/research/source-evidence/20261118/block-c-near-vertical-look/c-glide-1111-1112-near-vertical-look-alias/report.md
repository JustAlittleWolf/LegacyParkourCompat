# 1.11.1 to 1.11.2 gliding look-vector slice

## Result

No source difference found in the assigned ordinary local-player gliding slice for a finite, negative, near-vertical pitch with a finite positive horizontal look magnitude. This closes only this bounded operation; it is not a full-pair coverage or acceptance verdict.

## Exact local evidence

Both inputs are the adjacent local Feather trees `decompiled_minecraft/1.11.1/ornithe-feather/` and `decompiled_minecraft/1.11.2/ornithe-feather/`.

- **1.11.1, Ornithe Feather Gen2 build 2.** `ornithe-feather.provenance.json` records resolved version `1.11.1`, the exact client SHA-256 `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6`, mapping artifacts `feather-gen2-1.11.1+build.2-mergedv2.jar` and `feather-gen2-1.11.1+build.2.tiny`, and Vineflower 1.12.0. Local provenance-file SHA-256: `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055`. The ready marker binds the source manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`; the local manifest's actual SHA-256 matches. Ready-marker SHA-256: `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf`.
- **1.11.2, Ornithe Feather Gen2 build 2.** `ornithe-feather.provenance.json` is a compact record (not a full run transcript); it names both build.2 mapping artifacts and Vineflower 1.12.0. The ready marker records exact version `1.11.2`, client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`, and source manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; the local manifest's actual SHA-256 matches. Its adjacent verification record says 1,921 source files verified, zero source-file differences and zero missing files. It records one derived remapped-jar difference and identifies it as a regenerated intermediate, with raw input differences empty and published source files matching. Local verification-record SHA-256: `1ff7d450309db002c7b9e42319f933ea6db75f0622d45996b3674ecb7e5c8fb4`. Ready-marker SHA-256: `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`.

Actual local source-file SHA-256 values agree with each release's own `ornithe-feather.sources.sha256` entry:

| Source file | 1.11.1 SHA-256 | 1.11.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/entity/Entity.java` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `net/minecraft/util/math/Vec3d.java` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` |
| `net/minecraft/util/math/MathHelper.java` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` |

The complete `LivingEntity`, `Vec3d` and `MathHelper` file hashes are identical across the pair. `Entity.java` differs elsewhere; the relevant `getRotationVector(float pitch, float yaw)` method was checked directly on both sides (1.11.1 line 1229; 1.11.2 line 1216) and has identical expressions and ordering.

## Operation comparison

`LivingEntity.moveRelative(float sideways, float forwards)` is at lines 1388–1431 on both sides and the whole enclosing file hash is identical. Its relevant gates and order are unchanged: local/logical-side update eligibility; water and lava exclusions (both exempt a flying player); active `isFallFlying`; fall-distance write; `getLookVector`; pitch-radian calculation; horizontal look magnitude `j`; horizontal velocity magnitude `k`; full look length `l`; cosine and float cast; gravity term; descending-look correction guarded by `velocityY < 0.0 && j > 0.0`; negative-pitch term guarded by `i < 0.0F`; horizontal speed alignment guarded by `j > 0.0`; drag; then `move(MoverType.SELF, ...)`.

`LivingEntity.getLookVector()` at lines 1916–1918 calls `getRotationVector(1.0F)`. That overload at lines 1921–1929 takes the current `pitch` and `headYaw` for exactly `1.0F`; otherwise it interpolates previous/current pitch and head yaw. The inherited `Entity.getRotationVector(float pitch, float yaw)` produces float trig results from the float degree-to-radian expressions and constructs `Vec3d` from those floats (widened to double). `Vec3d.length()` calls `MathHelper.sqrt(double)`, which returns a float widened to double.

In `moveRelative`, `i` is a float computed as `pitch * (float)(Math.PI / 180.0)`. `j` is a double from `Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)`; `k` is likewise a double from horizontal velocity; `l` is `vec3d.length()`. The multiplier expression is explicitly cast back to float after double participation in `Math.min(1.0, l / 0.4)`. For the bounded finite near-vertical case with `i < 0.0F` and `j > 0.0`, the pitch-down/up contribution branch is taken, the look-vector divisions use that positive finite `j`, the later stabilization branch is also taken, and both occur before position movement. These are source-level implications only; no trajectory was simulated.

**Difference:** none in the requested lookup, horizontal-magnitude computation, branch signs, casts or call order. The identical `LivingEntity`, `Vec3d` and `MathHelper` payload hashes substantiate these paths; the changed `Entity.java` payload was narrowed to the relevant unchanged overload rather than treated as globally identical.
