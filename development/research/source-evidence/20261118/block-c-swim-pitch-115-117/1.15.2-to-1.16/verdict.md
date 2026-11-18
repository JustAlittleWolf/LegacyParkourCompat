# Independent source verdict: swim pitch vertical correction, 1.15.2 → 1.16.0

## Blind-source freeze

This technical conclusion and source identity were frozen before reading the assigned lab report. Scope is limited to the ordinary player swim-pitch direct vertical-velocity correction in `Player.travel`, its pitch-derived `Entity.getLookAngle` input, `Mth` lookup procedure, enclosing gates and order, and the subsequent inherited movement call. No wiki, product implementation, backlog, Git history, or network evidence was consulted.

Host/environment: Windows PowerShell 7.6.9; canonical read-only source root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\`.

| Exact source | Ready identity | Provenance / readiness | Source manifest SHA-256 |
|---|---|---|---|
| 1.15.2 Mojmap | `versionId=1.15.2`, `mapping=mojmap`, status `ready`; 3,332 files; client SHA-256 `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c` | `mojmap.ready.json` SHA-256 `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; `mojmap.provenance.json` SHA-256 `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e`; ready marker cites matching source manifest; recorded toolchain Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0. | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` |
| 1.16.0 Mojmap (`versionId=1.16`) | `versionId=1.16`, `mapping=mojmap`, status `ready`; 3,572 files; client SHA-256 `00740ef4475d4cd0a1d7064f89602f759c45ff9afe29da9b0ee0b9037ebb2a71` | `mojmap.ready.json` SHA-256 `5b6cb24f95e83b856c53f646d6477f2e46dae2276737dee56dd07c5bd745e4a2`; ready marker cites `mojmap.provenance.json` SHA-256 `371d50521a183f4a3f42fac2d18021f228e0a4e6b2e8a9838c2805391709f2a9` and source manifest; recorded toolchain Java 25.0.3, Gradle 9.7.1, Vineflower 1.12.0. | `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a` |

The 1.15.2 ready record is the older schema and does not carry a provenance hash field; the provenance file exists and its hash above was computed directly. The ready record cites source-manifest hash `cb7fd93...`, matching the actual manifest. The 1.16 ready record cites both source and provenance hashes, matching the bytes on disk. These are the canonical exact-version trees; I did not regenerate them.

Relevant canonical source paths, paired line ranges, and SHA-256 identities (also matched against each version's source manifest):

| Member | 1.15.2 range / SHA-256 | 1.16 range / SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` — `travel(Vec3)` | 1448–1478 / `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` | 1380–1410 / `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| `net/minecraft/world/entity/LivingEntity.java` — inherited `travel(Vec3)` | 1831–1919 inspected / `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | 1912–1976 inspected / `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| `net/minecraft/world/entity/Entity.java` — `move`, `calculateViewVector`, `getLookAngle` | 450–530, 1207–1227, 1661–1663 / `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | 469–530, 1225–1245, 1680–1682 / `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| `net/minecraft/util/Mth.java` — sine table and accessors | 10–35 / `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` | 10–35 / `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |

Readiness/source-manifest hash pairs are 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and 1.16 `e3c23e94ac985c25dec0c768aed17b443ba4823662cf1e152235c99756ae5d4a`; each marker's byte hash and each cited source entry matched the canonical files.

## Frozen technical inference

**Verdict for this bounded operation: no source difference. Do not accept a 1.15.2→1.16.0 change for the ordinary swim-pitch direct Y correction.**

The two `Player.travel(Vec3)` bodies have the same relevant operation, expression, constants, gates, and order (1.15.2 lines 1448–1478; 1.16 lines 1380–1410): they save starting coordinates; when `isSwimming() && !isPassenger()`, read `g = getLookAngle().y`; select `h = g < -0.2 ? 0.085 : 0.06`; and, when `g <= 0.0 || jumping || the fluid state at (x, y + 1.0 - 0.1, z) is nonempty`, read current delta movement and write `delta + (0.0, (g - delta.y) * h, 0.0)`. Thus the direct Y correction is gated to swimming, non-passengers and the stated sub-gates; upward look (`g > 0`) without jumping or fluid immediately above skips it. The calculation uses the pre-super-travel vertical velocity and is executed before `super.travel(vec3)` in both versions. Flight handling and movement-stat bookkeeping follow the superclass call in the same order in both versions.

Both `Entity.getLookAngle()` implementations pass current raw entity rotations, `xRot` and `yRot`, into the same `calculateViewVector` procedure (1.15.2 lines 1661–1663 and 1219–1226; 1.16 lines 1680–1682 and 1237–1244). It forms float radians `h = pitch * (float)(Math.PI / 180.0)`, computes yaw-side values, and returns Y as `-Mth.sin(h)`. This is current pitch, not the interpolated `getViewXRot(partialTick)` path. Both `Mth` implementations initialize the same 65,536-entry float `SIN` table from `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin(float)` uses `SIN[(int)(f * 10430.378F) & 65535]`; Java's float multiplication then cast-to-int truncation and 16-bit mask are identical. `cos(float)` uses the same operation with `+16384.0F`. No numeric, cast, rounding, table or indexing difference was found in the pitch-to-Y path.

After this correction, both Player overrides call inherited `LivingEntity.travel`. For an ordinary active water branch, the inherited movement path reaches `Entity.move(MoverType.SELF, getDeltaMovement())` (1.15.2 `LivingEntity` line 1891; 1.16 line 1945), which then performs collision resolution through `collide`, applies the resolved displacement to the bounding box/location, and derives collision/on-ground state from requested versus resolved movement (`Entity.move`, 1.15.2 lines 450–481; 1.16 lines 469–500). The inherited water travel implementation itself was reorganized between releases: 1.15.2 dispatches through its `!isInWater()` / fall-flying split, while 1.16 has an explicit water/affected-by-fluids/can-stand-on-fluid branch with water slowdown and fluid-falling adjustment. Those are outside this verdict's narrowly assigned direct Player swim-pitch correction; their existence does not change the identical pre-super direct correction or establish a difference in it.

Source-confirmed facts are the identical source expressions, gates, call order, shared pitch-to-look math and subsequent inherited `move` call. Any resulting path divergence from downstream `LivingEntity.travel`, collision conditions or environment is not evidence of a change in this bounded correction. Intermediate patch introduction is not established by this endpoint pair.

## Post-freeze comparison

The assigned lab report was not opened until the source identities and conclusion above had been written. Comparison disposition: to be recorded after report review.

Post-freeze comparison: the report SHA-256 exactly matched the assigned identity `537c3d21a3ce495b45560453420e7ca44fbbec4734e7e5ac6edd285dd7fb6f57`. Its bounded no-difference conclusion agrees with the independent source conclusion. Its readiness and source-manifest hashes match the canonical files documented above. The report additionally calls out `Entity.getLookAngle().y` widening: `Mth.sin(h)` returns float, unary `-m` remains float as the `Vec3` constructor argument and is widened to the vector's double Y coordinate. Inspection confirms this and adds no difference to the direct correction.

**Post-freeze source-line correction:** the pre-report frozen paragraph cited 1.15.2 `LivingEntity.travel:1891` for the ordinary active water branch. That line is the separate non-water branch. Exact source inspection after the freeze confirms the water branch calls `this.move(MoverType.SELF, this.getDeltaMovement())` at 1.15.2 `LivingEntity.java:1950`; 1.16 does so at `LivingEntity.java:1945`. These resolve to inherited `Entity.move` because neither `Player` nor `LivingEntity` declares `move(MoverType, Vec3)`. This corrects only the call-site line attribution in the earlier trace; the no-difference verdict and described shared dispatch remain unchanged. The 1.15.2 water branch then applies water drag, gravity/falling behavior; 1.16 applies water slowdown, fluid-falling adjustment and a collision jump check. These remain outside this bounded direct-correction verdict.
