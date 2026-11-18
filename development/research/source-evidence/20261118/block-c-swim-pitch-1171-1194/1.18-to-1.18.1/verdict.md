# Independent audit — 1.18 to 1.18.1 swim-pitch vertical correction

## Frozen independent source trace (pre-author-report)

Scope: the `Player.travel(Vec3)` swim-pitch direct Y-velocity correction before `super.travel`, its authoritative look-Y calculation, numeric lookup behavior, eligibility gates and order through the first real player movement call. This is a bounded operation audit, not full pair/tick coverage.

Canonical inputs: exact Minecraft `1.18` and `1.18.1`, Mojmap, published in `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. Both ready manifests identify 4,203 source files and Vineflower/Java 25. Their shared exact preparation run is `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, mapping option `mojmap`, pinned Java 25.0.3+9-LTS / Vineflower 1.12.0 / Gradle 9.7.1 / ASM 9.10.1 / Mapping IO 0.9.1 / Tiny Remapper 0.14.1. The 1.18 client input SHA-256 is `381e090ef22584526113bcccf26ae38a310a22094d14c2d0dec72b71fdf65aaa`, version metadata SHA-256 `40e076b63d116d21b8eaf7f9d3e09651e4bbc5853cde47edc13b4d5ea0b6631b`; 1.18.1 client input SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`, version metadata SHA-256 `b854990db7e1948661c0f0359e3b2dbf32610a7a61b4efa4d5471399bcf68fd1`. This records the canonical local evidence; no source generation or network lookup was performed.

Canonical manifest byte SHA-256s:

| Version | ready manifest | provenance manifest | source manifest |
|---|---|---|---|
| 1.18 | `4537edf0e88c88631215beb1c4f1d67f5a5d6d021c982c0af549b7b27856e5f` | `e642f075b25d1b6e74bad05edacd740421c70f5d8e0c2a38767523cf864124c5` | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.1 | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |

The ready manifests carry matching provenance and source-manifest hashes; actual provenance bytes hash to the ready-manifest `provenanceSha256`, and actual source-manifest bytes hash to the provenance/ready `sourceManifestSha256`. The source manifests list the same digest for each cited Java file on both sides. Direct SHA-256 checks also agree:

- `net/minecraft/world/entity/player/Player.java`: `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`
- `net/minecraft/world/entity/Entity.java`: `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`
- `net/minecraft/util/Mth.java`: `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f`
- `net/minecraft/world/entity/LivingEntity.java`: `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`

Source trace:

1. `Player.travel(Vec3)` (both: lines 1409–1439) snapshots x/y/z, then gates its direct pre-super correction on `isSwimming() && !isPassenger()`.
2. It obtains `double lookY = getLookAngle().y`, chooses `double factor = lookY < -0.2 ? 0.085 : 0.06`, then corrects only if `lookY <= 0.0 || jumping || !fluidState(block at (x, y + 1.0 - 0.1, z)).isEmpty()`. When this inner gate passes, it reads current movement and calls `setDeltaMovement(old.add(0.0, (lookY - old.y) * factor, 0.0))`. The correction is before both branches' `super.travel`; it does not directly change position.
3. `Entity.getLookAngle()` returns `calculateViewVector(getXRot(), getYRot())` (both: lines 1813–1815). `calculateViewVector` (both: lines 1311–1319) computes pitch radians as `float pitchRad = xRot * (float)(Math.PI / 180.0)`; yaw is likewise float. It calls `Mth.cos/sin` with float arguments and constructs `Vec3(sinYaw * cosPitch, -sinPitch, cosYaw * cosPitch)`. The middle component is a float result widened into Vec3's double Y; `getLookAngle().y` and the later blend are double.
4. `Mth` (both: lines 26–30, 43–49) has a `float[65536]` sine table populated by `(float)Math.sin(index * Math.PI * 2.0 / 65536.0)`. `sin(float)` indexes `SIN[(int)(angle * 10430.378F) & 65535]`; `cos(float)` indexes `SIN[(int)(angle * 10430.378F + 16384.0F) & 65535]`. Multiplication/addition before cast use float operands; `(int)` truncates toward zero, then `& 65535` wraps the 16-bit table index. The loaded table float is widened only where consumed in double-valued Vec3 construction.
5. The subsequent `super.travel(Vec3)` enters `LivingEntity.travel` (both: lines 2005 onward). Under its effective-AI/local-control gate, its in-water + fluid-affected + cannot-stand-on-fluid branch calls `moveRelative` then `move(MoverType.SELF, getDeltaMovement())` (lines 2014–2038), so the pre-super Y correction reaches the actual move input before later water drag/fall adjustment. Other reachable travel branches use their own existing movement paths; none changes the pre-super order.
6. `Entity.move(MoverType, Vec3)` (both: lines 541 onward) preserves native no-physics fallback; otherwise it applies piston limiting when applicable, stuck multiplier, edge backoff and collision, then writes position by the resolved collided vector and derives collision/on-ground state. Thus the corrected Y participates in the requested input to the native collision resolution when the caller reaches movement.

Independent comparison result before consulting the author report: no 1.18→1.18.1 source difference in this bounded operation, gates, ordering, pitch calculation, LUT width/index/cast/widening, or route into movement. All four Java source files have identical SHA-256 bytes across the pair. This conclusion makes no claim about other movement slices, intermediate patch boundaries, or runtime parity.


## Post-freeze independent comparison and audit

The author report was opened only after the pre-report trace above was frozen at SHA-256 `4b6c4db22b4771b65e19956ecc3de0f738403669a7f9ad25dc62e3af2a5b2709`. The report reviewed was `c-swim-pitch-vertical-118-1181-source/report.md`, SHA-256 `b95b8a20749c96014df37998293ec741a0f48ed183e9642834cd46604c010368`. Its bounded result agrees with the independent result: no 1.18→1.18.1 difference in the scoped direct swim-pitch Y correction or dependencies.

Coverage addendum: both `Player.travel` sources also contain the flying/non-passenger branch at lines 1424–1434. It snapshots current Y velocity before `super.travel`, then sets movement Y to that snapshot times `0.6` after the parent returns. The ordinary `else` invokes `super.travel` at lines 1435–1436. This branch is identical in both source files (same Player.java hash listed above); it qualifies the general route statement in the frozen trace. The author report accurately records this branch. Overall independent verdict: **concur, no scoped source difference**. The review is operation-bounded and does not independently certify full-pair coverage, implementation acceptance, or runtime parity.
