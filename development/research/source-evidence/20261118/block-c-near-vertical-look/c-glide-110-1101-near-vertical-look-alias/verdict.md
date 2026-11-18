# Independent source audit: 1.10 → 1.10.1 near-vertical Elytra look trace

## Verdict

**ACCEPT — bounded no-difference claim.** The report's stated local, dry, already-fall-flying path and its look-vector/LUT and horizontal correction-order account are supported by the exact paired sources. The five identified shared source files are byte-identical across these releases. `PlayerEntity.java` differs as a whole, while its examined `moveRelative` body is unchanged. This accepts only the reported trace through the first `move(...)`; it does not establish pair coverage or runtime parity.

## Immutable input

- Draft: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-110-1101-near-vertical-look-alias/report.md`
- Draft SHA-256 at audit: `15B8A50BD8F11C2E6399570C676E23381DB4E28D18C8D62C205999B58ABEA205`

## Exact paired-source identity checked

Roots: `decompiled_minecraft/1.10/ornithe-feather/` and `decompiled_minecraft/1.10.1/ornithe-feather/`. Both provenance manifests identify `lpc-decompile-v1`, Java 25 / Windows 11. The exact release-bearing input URLs and recorded artifact hashes agree with the draft:

- 1.10 client `.../objects/ba038efbc6d9e4a046927a7658413d0276895739/client.jar`: `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`; Feather `1.10+build.2`: `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`.
- 1.10.1 client `.../objects/44b389fff90324c4ca18796d4428a7b8ec6c2eb0/client.jar`: `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`; Feather `1.10.1+build.2`: `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`.

Recomputed SHA-256 for `Entity.java`, `LivingEntity.java`, `MathHelper.java`, `Vec3d.java`, and `LocalClientPlayerEntity.java` matched the report on both sides. The recomputed `PlayerEntity.java` hashes also matched its separately reported differing values.

## Trace audit

- `LocalClientPlayerEntity.isLocallyControlled()` is unconditionally true (1.10 `LocalClientPlayerEntity.java:458–460`). `LivingEntity.moveRelative` admits this path, then its dry-state water/lava guards and active `isFallFlying()` branch (`LivingEntity.java:1332–1341`). The active flag predicate reads flag 7 (`LivingEntity.java:2088–2090`). The already-active state is an input assumption; no activation or chest-equipment eligibility is inferred.
- `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, which passes current `pitch` and `headYaw` to `Entity.getRotationVector(float,float)` (`LivingEntity.java:1853–1865`; `Entity.java:1148–1153`). The helper evaluates float yaw cosine/sine first, then `h = -cos(-pitch * (float)(PI/180))`, `i = sin(-pitch * (float)(PI/180))`, and constructs `(g*h, i, f*h)`, without normalization. Thus yaw here is the live head-yaw input.
- `MathHelper.sin/cos` index the float sine table using `(int)(x * 10430.378F)` (plus `16384.0F` for cosine), then `& 65535`; the cast truncates toward zero. The table stores `(float)Math.sin(i * PI * 2.0 / 65536.0)` (`MathHelper.java:15–20, 422–425`). At pitch `-90.0F`, the cosine index is 32768 and its float table value is the small nonzero `sin(PI)` residual; negation gives `h` about `-1.2246469E-16`. With ordinary finite `headYaw`, this produces a positive, very small `j = sqrt(x*x + z*z)`, so the stated `j > 0.0` gates pass. This is a source-derived lookup residual, not an exact horizontal zero.
- Before the first move, `LivingEntity.java:1341–1371` has the reported order and types: compute `i` as float; horizontal look magnitude `j` and horizontal velocity magnitude `k` with `Math.sqrt` (double); full look length `l` via `Vec3d.length()` and `MathHelper.sqrt(double)` returning float, widened to double; compute float `m` and its float-cast product; update baseline vertical velocity; apply descent correction only for `velocityY < 0.0 && j > 0.0`; apply the negative-pitch pull-up term for `i < 0.0F` (with no local `j` guard); then align horizontal speed only for `j > 0.0`. The divisions and add/subtract order match the draft. Damping is X/Z by `0.99F`, Y by `0.98F`, followed by the first `move(...)` at line 1371.
- The 1.10 and 1.10.1 `PlayerEntity.moveRelative` bodies at lines 1391–1409 match textually: non-ability-flight players call `super.moveRelative`, then update movement stats. This closes the relevant dispatch wrapper despite the surrounding file hash difference.

## Boundaries

Reviewed only the reported local-player dispatch, look-vector/table math, Elytra travel branch numeric sequence and first-move cutoff. Startup/acquisition, malformed values, server interaction, collision response after `move`, the broader release-pair inventory and runtime parity remain outside this verdict.
