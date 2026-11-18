# Independent exact-source audit: 1.19.2 → 1.19.3

## Pre-report freeze

This trace and its source identities were frozen before opening `c-swim-pitch-vertical-1192-1193-source/report.md`. Scope is ordinary `Player.travel`'s own pre-`super.travel` swim-pitch Y correction, the authoritative pitch/look-vector and trigonometric table feeding it, the gate's block/fluid sample, and the inherited water-path movement which first consumes the corrected velocity. This is not a full-pair movement inventory or runtime parity claim.

### Canonical source readiness and provenance

Read-only canonical source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.
Both readiness markers say `status: ready`, `mapping: mojmap`, `decompiler: Vineflower`, Java runtime 25; each points at its exact version and its source manifest. The source-manifest SHA-256 below matches the marker's `sourceManifestSha256` and the computed manifest-file hash.

| Version | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Manifest / source count |
|---|---|---|---|---|
| 1.19.2 | `90f5a351c60a1aab160b640567b716bbc563e62d92e7e555df55c4ce7952492c` | `c0c148d3c3a0092adc59c29c25f2106f3a5398156a4f266882384804b3534280` | `95bc354ac219b9fe1ca8bf45053edad0119a75323d576902d59616a11647aac2` | 4,480 source files |
| 1.19.3 | `c1c6abc850d52b0dac399ef6ac68974fb15750cb3bdced2f947fad21c79289b6` | `f733352c574a2f70f9218f709dcaaf6b19a9d968d5fa19aad4e25c2eed25a9fe` | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` | 4,618 source files |

Each provenance record identifies an exact 1.19.2/1.19.3/1.19.4 batch, Mojmap mapping option, Vineflower 1.12.0, Tiny Remapper 0.14.1, Gradle 9.7.1, Java 25.0.3+9-LTS. The marker's `clientSha256` is `e1ac65de9b471b6916cc457fdcff00c1bafac17027aa79100c4df893b3d956db` for 1.19.2 and `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe0035b2f042652` for 1.19.3.

### Cited source-file hashes

For every row, the canonical `mojmap.sources.sha256` entry was checked against SHA-256 of the actual UTF-8 source file bytes.

| Source file (relative to version Mojmap root) | 1.19.2 SHA-256 | 1.19.3 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/player/Player.java` | `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1` | `02e64e197aec3f4f2a8abc9c5ce4545121ac5bcbbc71592ce478601a0db17203` |
| `net/minecraft/world/entity/Entity.java` | `759de9cded43bd882afcf5b7023bcf804d92419acb656b493f3b490c83eb18b6` | `8c35bdef3bf2de7b70ac76ad767ba2c194305e0dda2e03fdf6544983667adc32` |
| `net/minecraft/world/entity/LivingEntity.java` | `fee2df5155449098556a138d2530d06b7a35979e43c6aa0ae1fffd6251853e77` | `2b8befdc406176e01672175451dd480b14da0b52bfe11a982d368909c05e73db` |
| `net/minecraft/util/Mth.java` | `61727897223a78846a5f1fa270d57aa714c21b3c8d6f2b0e12fea4a07e6c327f` | `f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f` |
| `net/minecraft/core/BlockPos.java` | `f520f3056b16c033f28db64171471aa165537fc8a1504278a24a4760b3fcf0ec` | `19fd76058d98a5bc8f0dfd6aab03524c4941c0407660812d46a22f3cc05fc54a` |
| `net/minecraft/core/Vec3i.java` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` | `e48be042611e39e69be4d6858644209cd00dbaa8e389f7a3f92809b51c642d32` |
| `net/minecraft/world/phys/Vec3.java` | `1d46be5be0e4041bcd6f3bdb86edf8c22d0b4291c0d3e84bf66de311b4c6a886` | `ec99e432b690cd7db0f8bec30eb6bb51fcb13fa75e035522f6a65609974399af` |

### Frozen technical trace

- **Player-owned correction and order.** Mojmap `Player.travel(Vec3)` is `Player.java:1464–1491` in 1.19.2 and `Player.java:1448–1475` in 1.19.3. Both first cache `getX/Y/Z`, then gate the correction with `isSwimming() && !isPassenger()`. Inside the gate, in order, `$$4 = getLookAngle().y`; `$$5 = $$4 < -0.2 ? 0.085 : 0.06`; then correction is conditionally applied if `$$4 <= 0.0 || jumping || fluid-at-upper-sample-is-nonempty`. If admitted it samples the current delta once and writes `delta + (0.0, ($$4 - delta.y) * $$5, 0.0)`. The strict `< -0.2` coefficient threshold differs from the inclusive `<= 0.0` correction gate. No position or pitch write occurs here.
- **Sample and state read.** The fluid predicate constructs `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`, then reads that block's `FluidState.isEmpty()`. The expression is left-associated double arithmetic (`Y + 1.0`, then `- 0.1`) and is independent from the earlier cached `$$1/$$2/$$3`. `BlockPos(double, double, double)` delegates to `Vec3i(double, double, double)`, which applies `Mth.floor` separately to all three values (`BlockPos.java:46–48`, `Vec3i.java:39–41` on each release). Thus the tested cell is the floored current player X/Z and floored current Y+0.9, including negative-coordinate floor semantics.
- **Authoritative pitch / vector.** `Entity` owns private `float xRot` and public `getXRot()` returns it (`Entity.java:156–158, 3055–3063` in 1.19.2; `156–158, 3090–3098` in 1.19.3). `getLookAngle()` passes current `getXRot()` and `getYRot()` to `calculateViewVector` (`1841–1843`; `1850–1852`). That method first computes float radians `pitch * (float)(PI/180)` and `-yaw * (float)(PI/180)`, then float cos/sin values from `Mth`, and returns `new Vec3(yawSin * pitchCos, -pitchSin, yawCos * pitchCos)` (`Entity.java:1334–1342`; `1339–1347`). Therefore the correction's `$$4` is the double `Vec3.y` formed by unary negation/widening of the float pitch sine; it uses current entity pitch, not interpolated `xRotO`.
- **LUT identity and numeric procedure.** Both `Mth.java` files have private `float[65536] SIN`, initialized by casting `Math.sin(i * Math.PI * 2.0 / 65536.0)` to float for `i=0..65535`. Both `sin(float)` use `SIN[(int)(radians * 10430.378F) & 65535]`; casts truncate toward zero before mask. Both `cos(float)` use `SIN[(int)(radians * 10430.378F + 16384.0F) & 65535]`. Table width, initializer, scale, cast, offset, mask and access are identical. 1.19.3 adds `Mth.truncate` before these methods, shifting line numbers only.
- **Inherited consumption / real move.** After this prelude, ordinary player travel reaches `super.travel(input)` (`Player.java:1490` or `1474`); the flying/non-passenger alternative also calls `super.travel` after its own branch-local setup. For the applicable submerged fluid path, `LivingEntity.travel` requires `isEffectiveAi() || isControlledByLocalInstance()`, computes its common gravity/slow-fall state and current block fluid, then selects `isInWater() && isAffectedByFluids() && !canStandOnFluid(fluid)`. It calls `moveRelative(...)` and then the actual `move(MoverType.SELF, getDeltaMovement())` before subsequently scaling velocity, applying fluid-fall adjustment, and possibly setting a collision jump velocity (`LivingEntity.java:2042 onward`, 1.19.2; `2052 onward`, 1.19.3). `Entity.move` is the inherited collision-resolving writer (`Entity.java:547ff` both). This confirms corrected velocity can reach movement in that water path; the code path is broader than only players but Player's prelude supplies the corrected delta.

**Frozen comparison conclusion (before report):** the scoped `Player.travel` correction, its exact gates and sample expression, `Entity` pitch-to-look-vector dependency, `Mth` float sine table/index math, and the relevant inherited water move call have the same behavior in 1.19.2 and 1.19.3. The visible `Mth.truncate` addition is outside the cited methods and does not alter their LUT behavior. This is a bounded no-difference finding, not an assertion about other swimming or movement code in the pair.


## Post-freeze review verdict

**Verdict: ACCEPTED — scoped no-difference claim confirmed.** Reviewed only after the pre-report trace was frozen.

- Audited input: assigned source report `c-swim-pitch-vertical-1192-1193-source/report.md`, SHA-256 `384786241a2473c17372aeec0b112147e1626a4e23ed08d540dc5583ed65ae04` (matches the dispatch identity). Its source-manifest, ready-marker, artifact-manifest, and diagnostics hashes all match the canonical 1.19.2/1.19.3 files; its cited `Player.java`, `Entity.java`, `LivingEntity.java`, and `Mth.java` file hashes match each source manifest.
- Frozen reviewer input revision: this file's pre-report-freeze text had SHA-256 `4bc4717a739ea1f7fc73a2fd06c746e342ac142227d2c14eaeadf2ac0945a5e1`; the trace above predates opening the report.
- Exact body result: no behavior difference in the scoped `Player.travel` correction, its pitch-to-look-vector chain or `Mth` LUT, nor the `LivingEntity.travel` water branch that performs `move(SELF, deltaMovement)`. Line numbers shift. The 1.19.3 `Mth.truncate` insertion is outside the compared sine/cosine operations. The different Elytra branch remains outside this bounded audit.
- Sampling dependency confirmed: the report names the sample coordinates and exact short-circuit order. The double `BlockPos` constructor delegates to `Vec3i(double,double,double)`, which applies `Mth.floor` independently to X, Y and Z on both releases. This makes the actual fluid check the floored cell at current X/Z and current Y+0.9, including negative-coordinate floor behavior. The report does not spell out that conversion or cite `BlockPos.java`/`Vec3i.java`; this is a coverage detail worth carrying into any permanent finding, not a contradiction of the report's result.

Signed: independent blind source auditor, `c-swim-pitch-vertical-1192-1193-audit`.
