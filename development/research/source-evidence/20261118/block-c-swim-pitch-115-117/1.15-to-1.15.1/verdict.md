# Blind source audit: local swim pitch-to-vertical correction, 1.15 → 1.15.1

## Source-only freeze (written before opening candidate report)

Candidate report identity supplied by assignment: `c-swim-pitch-vertical-115-1151-source/report.md`, expected SHA-256 `480353DDBFFB8B54C26049A6BA7FA05D0D9CEE3BE227A47C113AB1AA2AD91130`. This section records my independent source conclusion before inspecting that report.

### Canonical source-set readiness and provenance

Both exact release source sets are canonical read-only Mojmap decompilations in `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/` and are marked `status: ready`, mapping `mojmap`, decompiler Vineflower, Java runtime 25, 3,329 source files each. The readiness records point to the same relative ready roots under the pair publication (`build/movement-campaign-2026-10-07/ready/<version>/mojmap`).

| Exact release | Readiness record SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Client JAR SHA-256 | Source files / bytes |
|---|---|---|---|---|---|---|
| 1.15 | `C6B66B63BAFAF2096AD7606111F1D0FAE1F2178D624D6A8C9DED5AD3AC1AAA31` | `DE4A538EBBAF82D1615683B08ECAE3CD76553345F3079436764EB095BE343949` | `251A3019CCC3FD067F66365F3545B69A983383A28B19A1038DEBF1791BE29340` | `ACC29974470E4F4864C695E1F1D084E746874663C47A365CA1FA41FF9FCC0CAE` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` | 3,329 / 17,522,461 |
| 1.15.1 | `AFBB16C3CB832FFEE8E4763BF9933D9635ECD811C9FBD7166554B78E59788504` | `6A7AF1F5D65D7AF165C2AB9A90C1FAE20169B38E70C8E92DBC5F59D94EFE9461` | `C8FDCE2F9432B72FC13C19A48F9AB3BE203CAC41A617139E81BAC3552245912D` | `D2C40F0770820752FE879670B35BD718B87E4F14491B3EC782E2E74DF6B38BBB` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` | 3,329 / 17,524,592 |

The ready-record source-manifest and provenance digests match the actual bytes of those named files. Exact version IDs and mapping are 1.15/Mojmap and 1.15.1/Mojmap; both provenance records identify the same prepared pair run `prep-1.15-1.15.1-mojmap-2026-10-09-r1`. The source manifest SHA checks below were rechecked against each canonical source file, not inferred from the ready marker.

### Frozen source trace and comparison

The local-player tick path reaches `LivingEntity.aiStep`, which calls virtual `this.travel(new Vec3(this.xxa, this.yya, this.zza))` at line 2287. `LocalPlayer.aiStep` calls `super.aiStep()` at line 778. Thus the dynamic player implementation is `Player.travel(Vec3)`; this trace is present in both releases.

In both `Player.travel` implementations (1.15 and 1.15.1, lines 1456–1486):

1. Save starting X/Y/Z.
2. Gate the correction on `isSwimming() && !isPassenger()`.
3. Read `g` as the `double` Y component of `getLookAngle()`; select `double h = g < -0.2 ? 0.085 : 0.06` (strict less-than, both decimal literals are `double`).
4. Apply only if `g <= 0.0`, or `jumping`, or the fluid state of the block at `(getX(), getY() + 1.0 - 0.1, getZ())` is nonempty. The queried Y expression is evaluated as doubles (`1.0 - 0.1`); `new BlockPos(...)` performs the block-position conversion before the state lookup.
5. Read the existing `Vec3` delta and call `setDeltaMovement(vec32.add(0.0, (g - vec32.y) * h, 0.0))`. The correction is added to the current vertical component, with no horizontal component change and no assignment replacing `vec32.y` directly. Evaluation order is exactly `(g - currentY) * h`, then `Vec3.add`.
6. Then the flying-and-not-passenger branch is tested; otherwise dispatch to `super.travel(vec3)`. End with movement-statistic deltas. For ordinary swimming, the first correction precedes the inherited travel call.

`Entity.getLookAngle()` delegates to `calculateViewVector(xRot, yRot)`. In both releases that method computes float radians `h = xRot * (float)(Math.PI / 180.0)`, float `i = -yRot * (float)(Math.PI / 180.0)`, then `j=Mth.cos(i)`, `k=Mth.sin(i)`, `l=Mth.cos(h)`, `m=Mth.sin(h)`, returning `new Vec3(k*l, -m, j*l)`. Therefore the correction's `g` is the float LUT-derived `-Mth.sin(pitchRadians)` widened to double by the `Vec3` component, not a double-precision sine or a normalized/recomputed pitch value.

Both releases' `Mth.SIN` is a 65,536-element float table initialized as `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `Mth.sin(float f)` returns `SIN[(int)(f * 10430.378F) & 65535]`; the float multiplication, Java int truncation and 16-bit mask are part of the pitch path. `cos` uses the same table with `+16384.0F`. Relevant helper sources are byte-identical across the pair.

For an ordinary player with swimming shared flag set, `Player.isSwimming()` further requires not flying and not spectator. Passenger state independently suppresses the correction. If the correction gate passes, `Player.travel` delegates to `LivingEntity.travel`; with the ordinary player in water and not flying, the inherited `!isInWater() || (this instanceof Player && abilities.flying)` condition is false and the inherited water branch runs. Its subsequent order in both releases is water slowdown/depth-strider setup, `moveRelative`, `move(SELF, deltaMovement)`, optional ladder Y adjustment, multiply X/Z by slowdown and Y by `0.8F`, optional gravity adjustment when gravity applies and not sprinting (including the `bl`/`0.005`/`d/16` threshold special case), then the horizontal-collision step-up check. The pre-travel pitch correction therefore affects the starting vertical velocity consumed by this branch. All these inherited water-branch lines are identical between the releases.

### Exact cited source bytes

Every listed file's actual SHA-256 equals its entry in that release's `mojmap.sources.sha256`; for each file, the 1.15 and 1.15.1 hashes are also equal. Thus the complete containing source files, not just selected lines, are byte-identical for the traced path.

| Source file, relative to `<release>/mojmap/` | SHA-256 in each release manifest and actual file |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/util/Mth.java` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| `net/minecraft/world/phys/Vec3.java` | `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909` |

### Independent source-only conclusion (frozen)

There is no 1.15→1.15.1 source difference in the local ordinary-swim `Player.travel` pitch-directed vertical velocity correction, its pitch-to-look-vector math/LUT, its gates, or the inherited ordinary-water travel trace. The paired containing Java files are byte-identical and their hashes validate against each release's exact source manifest. The source-only result is a no-difference for this bounded operation; it does not establish behavior for intermediate releases or other swimming mechanics.

**Freeze point:** this source-only conclusion is frozen before reading the candidate report. Candidate report's declared input SHA-256 is the identity above; the independent comparison is not based on that report.

## Independent audit of the frozen source report

Candidate input: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-swim-pitch-vertical-115-1151-source/report.md`, actual SHA-256 `480353DDBFFB8B54C26049A6BA7FA05D0D9CEE3BE227A47C113AB1AA2AD91130` (matches the assignment's immutable identity).

The source report's claims were checked against the frozen source set. The ordinary-swim gate, strict `g < -0.2` coefficient threshold, three-way short-circuit write gate, fluid lookup coordinates, `(g - vec32.y) * h` delta addition before superclass travel, float pitch/LUT conversion, inherited in-water `moveRelative` / `move(SELF, delta)` / vertical damping order, and stated no-difference result all match the exact Mojmap sources. The report's note that `g` is a widened float LUT-derived component while the adjustment arithmetic is double is accurate.

For the actual local-player call chain, both `LocalPlayer.aiStep` implementations call `super.aiStep()` at line 778; `LivingEntity.aiStep` calls virtual `this.travel(new Vec3(...))` at line 2287; `LocalPlayer.isEffectiveAi()` returns `true` at line 490–492, satisfying the inherited `LivingEntity.travel` entry gate. Its water branch calls `move(MoverType.SELF, this.getDeltaMovement())` at lines 1949–1950. The inherited `Entity.move` at lines 450 onward runs the collision calculation (`collide(vec3)`), updates the bounding box and position from the collision-adjusted vector, then updates collision flags and relevant movement state. These called code files are byte-identical across 1.15 and 1.15.1 and match the canonical source manifests.

**Verdict:** independently accepted as a source-confirmed no-difference for this bounded operation and release pair. The report does not overstate an exact intermediate introduction boundary or claim runtime parity. No discrepancy or unresolved source dependency remains within the assigned scope.

The source-only freeze artifact, exactly as saved before reading the report, had SHA-256 `C63A06F3496CC80911B82D689F5D6BA1911066D83667FD28AE48D02BCE654944`. This final verdict appends only the post-freeze report integrity check and independent claim audit; it leaves the frozen source conclusion intact.
