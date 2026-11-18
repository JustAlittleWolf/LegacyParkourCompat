# 1.11.2 → 1.12: near-vertical Elytra look vector

**Result:** No source difference found for the assigned ordinary, dry, already-active local chest-Elytra glide path. The 1.12 `moveRelative` signature adds an upwards argument, but its fall-flight math, operation order, float casts, branch conditions, and pre-move velocity scaling match 1.11.2. No evidence supports a changed near-−90° pitch alias or a newly reachable zero denominator for lawful finite camera rotations in this slice.

## Exact source identity

Both inputs are canonical local `ornithe-feather` decompilations:

- 1.11.2: Feather Gen2 `1.11.2+build.2`; raw client SHA-256 `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2`; mapped archive SHA-256 `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`. The source tree hash entries are in `decompiled_minecraft/1.11.2/ornithe-feather.sources.sha256`.
- Initial 1.12: Feather Gen2 `1.12+build.2`; raw client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; mapped archive SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`. Source hashes and inputs are recorded in `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json`.

Full SHA-256 for every source file used:

| Source, relative to each `ornithe-feather/` root | 1.11.2 | 1.12 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/util/math/MathHelper.java` | `97aa2499681fdd8cee6564f777310ee396bc447fef78c2273a990e9202d9641b` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |

## Paired source evidence

- 1.11.2 `LivingEntity.moveRelative(float sideways, float forwards)`, lines 1388–1427; 1.12 `LivingEntity.moveRelative(float forwards, float sideways, float upwards)`, lines 1425–1464. Inside the fall-flight branch, both call `getLookVector()`, form the pitch radians in a `float`, calculate horizontal look magnitude `j`, horizontal speed `k`, and look length `l`, then apply the same lift, dive, pitch-up, steering and drag operations before their first `move(SELF, velocityX, velocityY, velocityZ)` call. The glide branch reads no `sideways`, `forwards`, or `upwards` argument before that move.
- 1.11.2 `LivingEntity.getLookVector` and `getRotationVector(float)`, lines 1915–1929; 1.12 equivalents, lines 1959–1967. Both select the full-tick path and delegate using `pitch` and `headYaw`, preserving the player view alias.
- 1.11.2 `Entity.getRotationVector(float pitch, float yaw)`, lines 1216–1221; 1.12 lines 1239–1244. The same float conversion, negations, `MathHelper.cos/sin` calls, multiplication order, and `Vec3d` construction occur in both.
- `MathHelper.sin(float)` / `cos(float)` use the same 65,536-entry table lookup and float scale/offset in both files (lines 8, 15–21); the table initialization expression also matches (lines 426–429).

The branch tests `j > 0.0` before the falling-energy and horizontal-steering divisions. Its negative-pitch correction is guarded only by `i < 0.0F` and divides by `j` without a zero check. For a normal finite camera pitch in the lawful range near −90°, the shared float conversion and table math keep the horizontal look magnitude positive: at exactly −90°, the converted angle is `-1.5707964F`; `MathHelper.cos(+1.5707964F)` selects table index 32768, whose table entry is approximately `1.2246469e-16F`, not zero. At least one yaw sine/cosine component is nonzero, so the resulting horizontal vector and `j` are nonzero. The identical source sequence therefore has the same small-denominator behavior on both versions; it is not an adjacent-version delta. Invalid or non-finite inputs are outside this finding.

## Bounds

This closes only the requested source slice: the local player is already fall-flying with a chest Elytra, the area is dry, and the look is negative and near vertical, through the first glide movement call. It excludes glide startup/finalization, post-move collision handling, other environment branches, invalid inputs, broader tick inventory, and runtime parity. No tests, game launches, or code changes were made.
