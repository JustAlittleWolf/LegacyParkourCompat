# Independent audit: 1.12.1–1.12.2 Elytra near-vertical look slice

## Verdict

**Accept the bounded no-difference movement claim, with an evidence-identity correction.** The three complete relevant source files are byte-identical between the canonical mapped directories, and the reported order, casts, trig-table lookup, gates, and pre-move denominator use match source. The report incorrectly says 1.12.1 exact decompile/mapping inputs are unverified: its canonical hidden provenance file is present inside `decompiled_minecraft/1.12.1/ornithe-feather/` and its declared hashes for all three relevant sources match the files.

## Immutable input

Frozen candidate: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-1121-1122-near-vertical-look-alias/report.md`

SHA-256: `ac9b2bc553da669aaeb18fca8dd6205aee693b85c6a028a58e7c1d68f5c6ad83`.

## Exact source identity

The mapped source directories are literally `decompiled_minecraft/1.12.1/ornithe-feather/` and `decompiled_minecraft/1.12.2/ornithe-feather/`; both name the Feather namespace. Each of these complete files has the same actual SHA-256 on both sides:

- `net/minecraft/entity/living/LivingEntity.java`: `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6`
- `net/minecraft/entity/Entity.java`: `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`
- `net/minecraft/util/math/MathHelper.java`: `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80`

For 1.12.1, `decompiled_minecraft/1.12.1/ornithe-feather/.decompile-provenance.json` has SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`. Its `sources` entries match the actual hashes above. Its pinned inputs identify the 1.12.1 client (`19331fb29bf8b8d5ed764677c94cca9d66481e10104f660eb555894a4300cf22`), Feather metadata (`1893deb3a1a74c6f2054876e2b8512817f744188aa8a9d8aafdba8525c0e896b`), and Feather 1.12.1 build.2 mapping jar (`f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`). Thus the report's sentence that 1.12.1 inputs are unverified by this checkout is incorrect. The hidden manifest is inside the mapped source directory, not a sibling of the mapping directory.

For 1.12.2, `ornithe-feather.ready.json` declares version `1.12.2`, mapping `ornithe-feather`, and the Feather build.2 merged jar plus `.tiny`. Its source-manifest SHA-256 matches `ornithe-feather.sources.sha256` (`b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`), with 2,050 entries as declared. The actual relevant source hashes match that manifest. `artifacts.sha256` matches its ready-manifest digest (`8171a095a5ee74cebbd12b3d76983fc01796c0c23c962076d1e235cd0f80fa8c`) and pins the cited Feather inputs to `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` and `a3aa1c8e73e81bd09432ba1f4b2e88aaacbedb2d8fa3cbf797536d2bdf0d4e58`. The 1.12.2 provenance SHA-256 is `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`; ready-marker SHA-256 is `b0aeec721e5c0af02b33d9e217cb8272889fa139ded1ecc2a31e42b930138f7a`.

## Bounded source audit

In `LivingEntity.java`, `moveRelative` (both releases, lines 1425–1464), local/logical movement, dry/lava, and already-fall-flying gates precede the glide branch. At 1434–1439 it obtains the current raw look vector, computes the float pitch-radian value, the nonnegative horizontal magnitude `j`, horizontal speed `k`, vector length, and cosine term in the reported order. The negative-vertical-speed correction at 1442 is guarded by `velocityY < 0.0 && j > 0.0`. The next pitch correction (1449–1454) is guarded only by `i < 0.0F`; it uses `j` as divisor without a positive-magnitude guard. Its velocity writes precede the first movement call at 1464. The later horizontal-speed adjustment has its own `j > 0.0` gate at 1456. This confirms the report's narrow reachable source path for a dry local player with active Elytra and finite negative vertical look.

`Entity.java` lines 1239–1244 constructs the vector with float trig results and no normalization; lines 1690–1692 pass current pitch/yaw through `getLookVector`. `MathHelper.java` lines 15–20 use `(int)(x * 10430.378F) & 65535` for sine and the float `16384.0F` offset for cosine; line 428 fills the table with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. These casts/indexes yield quantized lookup aliases; at pitch `-90.0F`, the cosine-table residual at index 32768 is positive, so `j` remains finite and positive while the pitch-radian value is negative. That means the unguarded pitch correction is reached at that source input. This is arithmetic from the exact source, not a runtime trajectory claim.

The complete-file hashes establish no code difference in these dependencies across the adjacent releases. The verdict is limited to this Elytra look/math slice and the evidence identity above; it closes no other pair coverage or runtime-parity gate.