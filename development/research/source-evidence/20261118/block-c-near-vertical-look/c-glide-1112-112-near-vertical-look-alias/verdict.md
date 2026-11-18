# Independent audit: 1.11.2 → initial 1.12 Elytra near-vertical look

## Verdict

**Core no-difference claim accepted for the assigned slice.** The local, already-active fall-flight branch in dry conditions has the same look-vector computation, float conversions, flight math and operation order through the first `move(SELF, ...)` call. For finite lawful negative pitch near −90°, this source pair does not establish a newly reachable zero horizontal denominator or an adjacent-version alias change.

**Evidence correction:** the input report describes `LivingEntity.getRotationVector(1.0F)` as supplying `headYaw` to this local-player glide path. That is the base implementation, but it is not the effective local-player dispatch. In 1.11.2, `LivingEntity.getLookVector()` calls virtual `getRotationVector(1.0F)` and `LocalClientPlayerEntity.getRotationVector(float)` overrides it to use `pitch, yaw`. In initial 1.12, `Entity.getLookVector()` directly uses `pitch, yaw`. Both local paths therefore feed yaw, with the same resulting vector math. Add the local override to the evidence chain; this correction does not change the no-difference verdict.

## Frozen input and source identity

- Audited input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-1112-112-near-vertical-look-alias/report.md`
- Input SHA-256: `E81591BD6B77F6AAA4F4A9F4773459E16E9812A3F04EA252B5DB6EF397DEB6C2`
- Host: Windows 11 PowerShell; canonical local Feather decompilations, read-only.
- 1.11.2 mapping: Feather Gen2 `1.11.2+build.2`; exact-run provenance `decompiled_minecraft/1.11.2/ornithe-feather.provenance.json` SHA-256 `DCDB65E1361297A584C5B93F581B1B539911911C6CEC1A203740BAA5379732C6`. The exact source inventory is `decompiled_minecraft/1.11.2/ornithe-feather.sources.sha256` SHA-256 `D908C2AF76598AD26999563BFEF0089F1D3C8C32483EEE6B108F0DEFA2773DF0`. `artifacts.sha256` pins client bytes `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` and Feather mapped archive `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757`.
- Initial 1.12 mapping: Feather Gen2 `1.12+build.2`; `.decompile-provenance.json` SHA-256 `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`; pinned client SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`; mapped Feather archive SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`.

Actual SHA-256 for the relevant decompiled source files, verified against the 1.11.2 source inventory and 1.12 provenance `sources` entries:

| Relative source | 1.11.2 | Initial 1.12 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `BB7DC6C9E423A9568D6433D51BBA12E7AEE4555FBF3FB3E2B87F618382279F2F` | `190E9AC551538E015D9E4D6C42856E5BA32B593131CF6D93895E7B29533F1EE6` |
| `net/minecraft/entity/Entity.java` | `CE8104A17CE783DF639CF9726E7B1CD0936563EAA7BA308603335BBE05D49440` | `80F091BF32166C88CF8BBD31CAF72D84FA16224410733C7D2A0F00563F294A0A` |
| `net/minecraft/util/math/MathHelper.java` | `97AA2499681FDD8CEE6564F777310EE396BC447FEF78C2273A990E9202D9641B` | `579E05D110460F444BCAC86729141545095CB5932C0F8D34C9A4DCFCD9A35E80` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65C2747BD8C70DEF6BE7F41F624D4C9493342B39AE7BED7967F9FF63608F59ED` | `D7237B8B9A68525BBFEC6AF1C234D2AAFB1E4AA7D52059331D1D243313D6D7D1` |

## Bounded source comparison

- `LivingEntity.moveRelative`: 1.11.2 lines 1388–1427; initial 1.12 lines 1425–1464. The client/local-control and water/lava gates precede the active fall-flight branch. The only signature change in the assigned operation is the new `upwards` parameter; this branch does not read its movement parameters. It obtains the look vector, casts pitch-radians to `float`, computes `j = sqrt(x*x + z*z)`, horizontal speed `k`, and look length `l`, then applies lift, conditional falling-energy correction, negative-pitch correction, conditional steering, and `0.99F/0.98F/0.99F` velocity drag in the same order. Both then make the first glide move with `MoverType.SELF` and current velocity.
- Local look dispatch: 1.11.2 `LivingEntity.getLookVector()` lines 1916–1918 delegates to `getRotationVector(1.0F)`; the local override at `LocalClientPlayerEntity` lines 153–156 resolves this to `getRotationVector(this.pitch, this.yaw)`. Initial 1.12 `Entity.getLookVector()` lines 1690–1692 directly returns `getRotationVector(this.pitch, this.yaw)`. The base `LivingEntity` method at 1.11.2 lines 1921–1928 and initial 1.12 lines 1960–1967 uses `headYaw`, but is overridden/inherited around by the effective local path as described above.
- Rotation conversion: `Entity.getRotationVector(float pitch, float yaw)` is the same at 1.11.2 lines 1216–1221 and initial 1.12 lines 1239–1244: `float` angle scaling, sign/π operations, `MathHelper.cos/sin` calls, multiplication order, and `Vec3d` component construction match.
- Lookup math: both `MathHelper` files use `(int)(x * 10430.378F) & 65535` for sine and `(int)(x * 10430.378F + 16384.0F) & 65535` for cosine; table initialization is `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)` over 65,536 entries. Thus at pitch −90 degrees the look-vector horizontal factor uses the cosine lookup at positive converted π/2, index 32768, whose float table value is about `1.2246469e-16F`, not zero. At least one yaw sine/cosine component is nonzero, so `j > 0` for that finite orientation. The negative-pitch correction divides by `j` without its own guard, while the downward-flight and steering divisions are guarded by `j > 0`; the same small-denominator behavior exists on both versions.

## Limits

Only ordinary dry, already-active local chest-Elytra glide behavior through the first glide move was audited. This does not cover glide startup/finalization, post-move collision response, other environment branches, invalid/non-finite rotations, full movement-tick coverage, or runtime parity. No code, product, wiki, Git, source generation, build, test, or game/runtime launch was used.
