# 1.10 → 1.10.1 near-vertical Elytra look trace

## Scope and verdict

Bounded trace: an ordinary dry local player whose Elytra fall-flying flag is already active, with a finite negative pitch near its vertical limit. The trace stops at the first `move(...)` call. It excludes startup/acquisition, malformed inputs, server interaction, and the rest of the movement inventory.

No 1.10 → 1.10.1 source change was found in the look-vector lookup, its Elytra movement consumers, or their sign/zero gates. The relevant source files are byte-identical across this pair; the inspected `PlayerEntity.moveRelative` body is also unchanged, though its enclosing file differs elsewhere. This is a bounded no-difference result, not full-pair coverage.

## Exact source identity

Canonical local Feather source roots:

- `decompiled_minecraft/1.10/ornithe-feather/`
- `decompiled_minecraft/1.10.1/ornithe-feather/`

Each `.decompile-provenance.json` identifies the exact release through its Mojang metadata/client URLs and the Feather mapping input. Both report pipeline `lpc-decompile-v1` and the same recorded Java 25 / Windows 11 runtime. The manifest source hashes below were recomputed from the source bytes and all matched.

| Input | 1.10 SHA-256 | 1.10.1 SHA-256 |
|---|---|---|
| Mojang client artifact | `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35` | `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72` |
| Feather mapping artifact (`1.10` / `1.10.1`, each `build.2`) | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |

Verified source SHA-256 (identical on both releases):

| Relative source path | SHA-256 |
|---|---|
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| `net/minecraft/util/math/Vec3d.java` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |

`net/minecraft/entity/living/player/PlayerEntity.java` differs as a whole: 1.10 `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b`; 1.10.1 `020370cffde2d1fe9f1b63fa5d51f1af2fab095908b19e79f88a7bcac7dfd501`. Its inspected `moveRelative` body at lines 1391–1409 is identical in both releases.

## Trace and numeric behavior

1. `LocalClientPlayerEntity.isLocallyControlled()` returns `true` (1.10 lines 458–460). `LivingEntity` reaches its travel phase at lines 1742–1747: it calls `flyingTick()` and then dynamically dispatches `moveRelative(sidewaysSpeed, forwardSpeed)`. For a player without ability flight, `PlayerEntity.moveRelative` calls `super.moveRelative` (1.10 lines 1391–1409; same body in 1.10.1). In the stated dry state the water/lava gates pass, and the active `isFallFlying()` branch runs in `LivingEntity.moveRelative` (lines 1332–1371).
2. The branch obtains `vec3d = getLookVector()`. `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, which uses current `pitch` and `headYaw` (lines 1853–1865). `Entity.getRotationVector(pitch,yaw)` computes float trig arguments and components in this order: yaw cosine, yaw sine, `h = -cos(-pitch * (float)(PI/180))`, `i = sin(-pitch * (float)(PI/180))`, then `new Vec3d(g*h, i, f*h)` (`Entity.java` lines 1138–1153). No normalization is applied.
3. The old lookup is significant: `MathHelper.sin/cos` use a 65,536-entry float sine table, indexing with `(int)(x * 10430.378F)` (cos adds `16384.0F`) and masking with `65535` (`MathHelper.java` lines 8–20). Initialization stores `(float)Math.sin(i * PI * 2.0 / 65536.0)` (line 424). The integer cast truncates before the mask. At finite `pitch = -90.0F`, the float conversion and lookup make the horizontal factor `h` a tiny nonzero residual (about `-1.2246469E-16` at the vertical table index); with ordinary finite yaw, `j = sqrt(vec3d.x² + vec3d.z²)` remains positive, though extremely small. This is a lookup-table alias/residual, not a mathematically exact horizontal zero. The stated pitch is finite and ordinary; no malformed input is involved.
4. Before the first move, operations are ordered: `i = pitch * (float)(PI/180)`; `j = Math.sqrt(vec3d.x*vec3d.x + vec3d.z*vec3d.z)`; `k = Math.sqrt(velocityX² + velocityZ²)`; `l = vec3d.length()`; `m = MathHelper.cos(i)`; then `m = (float)(m * (m * Math.min(1.0, l / 0.4)))` (LivingEntity lines 1341–1348). `Vec3d.length()` calls `MathHelper.sqrt(double)`, whose return type is `float`, widened back to `double` by `length()`; the horizontal magnitudes `j` and `k` instead use `Math.sqrt` and remain double.
5. If `velocityY < 0.0 && j > 0.0`, the glide descent correction computes `n = velocityY * -0.1 * m`, updates vertical velocity, then adds `vec3d.x*n/j` and `vec3d.z*n/j` horizontally (lines 1349–1354). Separately, the negative-pitch gate `i < 0.0F` computes the pull-up term from `k * -sin(i) * 0.04`, adds `o*3.2` vertically, and subtracts `vec3d.x*o/j` and `vec3d.z*o/j` horizontally (lines 1356–1361); this branch itself has no `j > 0` guard. Then `j > 0.0` gates horizontal speed alignment using `vec3d.x/j` and `vec3d.z/j` (lines 1363–1366). The tiny positive lookup residual therefore passes these gates at the finite near-vertical limit. The residual, division order, and each gate must be retained as written.
6. The code applies X/Z `*= 0.99F`, Y `*= 0.98F`, then first calls `move(velocityX, velocityY, velocityZ)` at line 1371. Collision response is after that call and outside this trace.

## Compared bounds

Compared and hash-verified: the look vector construction and trig lookup; Elytra branch math through the first move; local-control and player-dispatch gates; and `Vec3d.length`/`MathHelper.sqrt` return types. Exact movement ranges: `Entity.java` 1138–1153; `LivingEntity.java` 1332–1371 and 1742–1747; `LocalClientPlayerEntity.java` 458–460; `PlayerEntity.java` 1391–1409; `MathHelper.java` 8–20 and 424; `Vec3d.java` 91–92. No startup, server, general pair-inventory, or runtime-parity claim is made.
