# Independent audit: 1.14.4 → 1.15 swim-pitch vertical adjustment

## Independent trace frozen before draft access

Freeze point: 2026-11-18, Windows PowerShell, primary checkout `D:\Javastuff\LegacyParkourCompat`. At this freeze, the author source report was not opened. This section records my source-derived trace and its exact identities before comparison with that report.

### Canonical exact-source identity

The prepared canonical source trees are `decompiled_minecraft/1.14.4/mojmap/` and `decompiled_minecraft/1.15/mojmap/`. The latter uses exact release ID `1.15`; the published release is not represented by a `1.15.0` directory.

| Identity | 1.14.4 Mojmap | 1.15 Mojmap |
|---|---|---|
| Ready marker, SHA-256 | `decompiled_minecraft/1.14.4/mojmap.ready.json` — `ec7b6a6d9ba72f8a19908527977c970a8e2b3007b0e0b4323a725fb5943d3043` | `decompiled_minecraft/1.15/mojmap.ready.json` — `c6b66b63bafaf2096ad7606111f1d0fae1f2178d624d6a8c9ded5ad3ac1aaa31` |
| Provenance, SHA-256 | `decompiled_minecraft/1.14.4/mojmap.provenance.json` — `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` | `decompiled_minecraft/1.15/mojmap.provenance.json` — `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` |
| Source manifest, SHA-256 | `decompiled_minecraft/1.14.4/mojmap.sources.sha256` — `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` | `decompiled_minecraft/1.15/mojmap.sources.sha256` — `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340` |
| Artifact manifest, SHA-256 | `decompiled_minecraft/1.14.4/mojmap.artifacts.sha256` — `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` | `decompiled_minecraft/1.15/artifacts.sha256` — `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae` |
| Prepared identity | ready marker says `status=ready`, `versionId=1.14.4`, `mapping=mojmap`, 3,250 sources; client SHA-256 `b3b2a798e2d67b566008fe4a03767ae2c7ff3f8c7ba6751e7b71fc7299672d0a` | ready marker says `status=ready`, `versionId=1.15`, `mapping=mojmap`, 3,329 sources; client SHA-256 `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |

The ready markers' source-manifest SHA-256 values match the actual manifest bytes. The four cited method-source files below match their per-file entries in those manifests; their SHA-256 values were also calculated directly from the canonical source files. Provenance identifies Mojang official mappings for 1.15 and the recorded exact preparation inputs/toolchain; the canonical artifact manifests are separately pinned above.

### Cited exact source bytes

| Release | Relative source and member | Source-file SHA-256 |
|---|---|---|
| 1.14.4 Mojmap | `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1380–1408 | `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java`, inherited `LivingEntity.travel(Vec3)`, lines 1782–1938 | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/Entity.java`, `getLookAngle`/`calculateViewVector`, `turn`, `isControlledByLocalInstance`, lines 1251–1259, 1697–1699, 319–327, 2516–2519 | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` |
| 1.14.4 Mojmap | `net/minecraft/util/Mth.java`, `SIN`, `sin(float)`, `cos(float)`, lines 12–30 | `57e0babfbc9ce864e539c7632ebb72c2e14a208c2db1c3a1bfc16ee0237ae576` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java`, `isLocalPlayer`, lines 329–331 | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.15 Mojmap | `net/minecraft/world/entity/player/Player.java`, `Player.travel(Vec3)`, lines 1456–1486 | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` |
| 1.15 Mojmap | `net/minecraft/world/entity/LivingEntity.java`, inherited `LivingEntity.travel(Vec3)`, lines 1831–1987 | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` |
| 1.15 Mojmap | `net/minecraft/world/entity/Entity.java`, `getLookAngle`/`calculateViewVector`, `turn`, `isControlledByLocalInstance`, lines 1219–1227, 1661–1663, 326–334, 2521–2524 | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| 1.15 Mojmap | `net/minecraft/util/Mth.java`, `SIN`, `sin(float)`, `cos(float)`, lines 12–30 | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| 1.15 Mojmap | `net/minecraft/client/player/LocalPlayer.java`, `isLocalPlayer`, lines 328–330 | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |

### Trace and legal witness

Both `Player.travel(Vec3)` methods have this order: snapshot position; if `isSwimming() && !isPassenger()`, read `g = getLookAngle().y`; choose `h = 0.085` when `g < -0.2`, otherwise `0.06`; if `g <= 0.0 || jumping || fluid-at-(x,y+0.9,z) is nonempty`, add `(0.0, (g - deltaY) * h, 0.0)` to delta movement; then run the flying/non-passenger branch or delegate to `super.travel`; finally update movement statistics. There is no vertical adjustment difference between these releases in this method.

`Entity.getLookAngle()` passes current `xRot` and `yRot` to `calculateViewVector`. Both versions compute float radians `h = xRot * (float)(PI/180)` and `i = -yRot * (float)(PI/180)`, then table-backed float `j=cos(i)`, `k=sin(i)`, `l=cos(h)`, `m=sin(h)` and return `(k*l, -m, j*l)`. `Mth` in both versions initializes `SIN[65536]` as `(float)Math.sin(ix * PI * 2.0 / 65536.0)` and implements `sin(f)` as `SIN[(int)(f * 10430.378F) & 65535]`; `cos(f)` is `SIN[(int)(f * 10430.378F + 16384.0F) & 65535]`. Thus the swim writer consumes the old float lookup-table look-Y, not `Math.sin` computed directly at the writer.

A legal witness is a local player in swimming state, not a passenger, not flying, with a downward pitch of `+30.0F` (within the `Entity.turn` clamp of `[-90,90]`) and `deltaY=0.0`. The source calculation yields look-Y `-Mth.sin(30.0F * (float)(PI/180))`, below `-0.2`; therefore `h=0.085`, and `g<=0` makes the vertical adjustment gate true regardless of jumping or the overhead-fluid test. `Player.travel` then delegates to `LivingEntity.travel`; its outer `isEffectiveAi() || isControlledByLocalInstance()` gate accepts the local player because `LocalPlayer.isLocalPlayer()` returns true and `Entity.isControlledByLocalInstance()` uses that predicate. With non-flying local player in water, inherited `LivingEntity.travel` takes its water branch: `moveRelative`, `move(SELF, deltaMovement)`, then applies water slowdown / gravity adjustment. This shows the writer feeds the actual inherited local movement path.

Cross-release conclusion from the frozen trace: no source difference is present in the swim pitch vertical writer, look-Y computation, lookup table, or relevant local-travel gate/order. The similar renamed-coordinate edits in `Player.travel` and `LivingEntity.travel` do not alter this operation.

## Author-report audit

Not yet performed at freeze. The report may be opened only after this pre-report section is saved and its SHA-256 recorded. Append the bounded comparison verdict here without altering the frozen trace above.


## Post-freeze correction and author-report verdict

Author input identity was checked before opening: `c-swim-pitch-vertical-1144-115-source/report.md`, SHA-256 `fd068ee6d8f8dff82e882783bda32a0e3ce473f5733ffe1a5ef38a80964f67b0`. The pre-report trace above was frozen at SHA-256 `8ed1d8709ba6cfd0cc54750a824de13d62b1036b3a4baf379405650de0805cba` before that read.

**Correction to the frozen trace:** its local movement-gate explanation incorrectly attributed acceptance to `Entity.isControlledByLocalInstance()` and `LocalPlayer.isLocalPlayer()`. For an unmounted local player, `Entity.getControllingPassenger()` returns `null`, so `isControlledByLocalInstance()` falls back to `!level.isClientSide` and is false on the client. The actual gate is `LocalPlayer.isEffectiveAi()`, overridden to return `true`: 1.14.4 `net/minecraft/client/player/LocalPlayer.java:483–485` and 1.15 `.../LocalPlayer.java:490–492`; the enclosing `LivingEntity.travel` gate is `isEffectiveAi() || isControlledByLocalInstance()` at 1.14.4 `LivingEntity.java:1782–1784` and 1.15 `LivingEntity.java:1831–1833`. The prior trace's conclusion that the ordinary local-player path reaches inherited movement remains true, but its stated reason is superseded by this correction. This is an audit correction, not a change to the frozen pre-report bytes.

### Verdict

**Accept the bounded no-difference finding.** The author report's source hashes and cited Player, Entity, Mth, LivingEntity and LocalPlayer files agree with the prepared canonical trees and their source-manifest entries. I independently confirm identical Player swim guard, `g`/`h` computation, short-circuit order, optional vertical write, and handoff to inherited movement. The 1.14.4 field reads versus 1.15 accessors are naming/API changes. In both releases, the same float LUT generates look-Y and the same non-flying water branch consumes the adjusted velocity. The report's scope disclaimer correctly avoids claiming fluid/collision coverage.

**Evidence gap for its local witness:** the report follows `aiStep` to virtual `travel` and names `LivingEntity.travel` as the consumer, but does not cite or state the exact `LivingEntity.travel` entry gate together with the `LocalPlayer.isEffectiveAi() == true` override. Those are necessary to prove the client-local witness enters the inherited body. The independent sources show this gate is identical in both releases. Add those exact members/lines and source hashes if the report is to stand as a complete gate trace.

**Witness wording:** state the angle numerically to avoid sign ambiguity. `xRot=+30.0F` is within the `Entity.turn` clamp `[-90F,90F]`, looks down, and produces `g=-Mth.sin(...) < -0.2`; `xRot=-30.0F` looks up and produces `g>0`. The report's “upward pitch” versus “horizontal/downward pitch (`g > 0`)” labels are easy to read as contradictory; the math and no-difference conclusion are right, but replace those directional labels with signed `xRot` and explicit look-Y. The `+30F` witness makes the swim adjustment gate true via `g <= 0` independent of jump or above-block fluid, choosing `h=0.085`.

Verdict classification: source claim accepted for this operation; independent bounded trace agrees. Pair freeze, intermediate-patch introduction, other movement coverage, implementation and runtime parity remain unassessed.

**Identity transcription correction:** the frozen table's 1.15 `LocalPlayer.java` row accidentally repeats the 1.14.4 digest. The correct 1.15 digest is `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823`, which matches the 1.15 source-manifest row and the SHA-256 calculated from the exact file. The author report records this correct digest. The independent freeze identity remains preserved above; this line corrects the transcription for downstream use.
