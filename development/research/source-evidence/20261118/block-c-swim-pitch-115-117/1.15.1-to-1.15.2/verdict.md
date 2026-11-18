# Independent source audit verdict: swim pitch vertical correction

## Frozen source review

Audit scope: ordinary active local player swim-pitch vertical correction in exact Minecraft 1.15.1 → 1.15.2 Mojmap source; complete `Player.travel` override, angle/LUT and sample math, swim gates, and route through inherited movement. Source review only. No product, backlog, wiki, Git, runtime, build, test, or generation inputs used.

Frozen before reading `c-swim-pitch-vertical-1151-1152-source/report.md`.

Verdict: **No 1.15.1 → 1.15.2 source change in this operation.** The complete `Player.travel(Vec3)` body is identical (31 lines; 1.15.1 `Player.java:1456–1486`, 1.15.2 `Player.java:1448–1478`). The complete `LivingEntity.travel(Vec3)` body is identical (157 lines; both `LivingEntity.java:1831–1987`). The source files for `Entity.java` and `Mth.java` are byte-identical between releases. The swim-pitch correction is present on both sides, with the same order, gates, sample and arithmetic.

### Behavior traced on both sides

- `LocalPlayer` inherits `Player.travel`; it does not supply a separate travel override in the reviewed source. Its `isEffectiveAi()` returns `true`, so the inherited `LivingEntity.travel` movement body runs for the active local player.
- `Player.travel` first snapshots x/y/z, then enters the direct correction only when `isSwimming() && !isPassenger()`. `Player.isSwimming()` excludes flying and spectators before delegating to the inherited swimming state.
- The code samples `g = this.getLookAngle().y` once. `Entity.getLookAngle()` uses current `xRot` and `yRot`; `calculateViewVector` computes float radians as `f * (float)(Math.PI / 180.0)` and `-g * (float)(Math.PI / 180.0)`, evaluates `cos(i), sin(i), cos(h), sin(h)` in that order, then returns `(k*l, -m, j*l)`. Thus the y component used by the correction is a float result widened into `Vec3.y`'s double.
- It selects double `h = g < -0.2 ? 0.085 : 0.06`. The vertical addition is gated by the ordered short-circuit condition `g <= 0.0 || jumping || !fluidAtSample.isEmpty()`. The sampled position is `new BlockPos(getX(), getY() + 1.0 - 0.1, getZ())`; the double constructor floors each coordinate through `Vec3i(double,double,double)` / `Mth.floor`. The fluid lookup occurs only when the first two disjuncts are false. On success it reads the then-current delta movement and replaces it with `vec32.add(0.0, (g - vec32.y) * h, 0.0)`, before calling `super.travel`.
- For ordinary non-flying swimming, `Player.travel` calls `super.travel(vec3)`. `LivingEntity.travel` is gated by `isEffectiveAi() || isControlledByLocalInstance()`, selects its water branch when in water, applies depth-strider / sprint / Dolphin's Grace parameters, calls `moveRelative`, then `move(MoverType.SELF, deltaMovement)`. The water branch damps the moved y velocity by `0.8F` and, absent no-gravity/sprinting, applies its existing gravity correction; after return, `Player.travel` records movement statistics. These downstream operations are identical across the pair.
- `Mth` constructs a 65,536-float sine table with `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`. `sin` indexes `SIN[(int)(f * 10430.378F) & 65535]`; `cos` indexes `SIN[(int)(f * 10430.378F + 16384.0F) & 65535]`. The casts, float constants and lookup order are identical in both releases.

### Canonical source readiness and relied-byte identities

Both exact source sets report `status: ready`, mapping `mojmap`, Java runtime 25, and Vineflower. The manifests report 3,329 files / 17,524,592 bytes for 1.15.1 and 3,332 files / 17,543,871 bytes for 1.15.2. The canonical trees contain those respective file counts. I verified every file against the 1.15.1 source manifest (3,329/3,329, no mismatches); for 1.15.2 I verified each relied-on source byte against its manifest entry and recorded the full manifest identity below.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 | Diagnostics SHA-256 |
| --- | --- | --- | --- | --- | --- |
| 1.15.1 | `afbb16c3cb832ffee8e4763bf9933d9635ecd811c9fbd7166554b78e59788504` | `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461` | `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d` | `d2c40f0770820752fe879670b35bd718b87e4f14491b3ec782e2e74df6b38bbb` | `d4af93fa2ceff5aa5db0611fd0f5e11f70de30dff1f44edb4e2e43b942f1dda2` |
| 1.15.2 | `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0` | `d4802d35ee2927a44d753871f184c3255c060eb94457a5c65a8bc121a087954e` | `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` | `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406` | `1ab6c473dbbdf2c590a7a8870befbd404e634fe7c1b0ae6a796b7b5f9c63d4d5` |

Source-relative SHA-256 entries, matching the files actually read:

| Source file | 1.15.1 | 1.15.2 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `6985e11ad9be553e9a2f25e5decc20a9da5c3b59b68f4e83a5b9f448cd2e48a0` | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| `net/minecraft/world/entity/LivingEntity.java` | `139c3866ad2c6a1ad73496f22ff1a4b5259998442092ffbafb8664428e28bf79` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` |
| `net/minecraft/util/Mth.java` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` | `a842059e498147d368126c896f0036916a90bdd125a09b09f9fe6658f2aaf0a4` |
| `net/minecraft/client/player/LocalPlayer.java` | `7227da5233e3c72ced008187b92a6a86842f84778eaa9f40fa1eb117286af823` | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| `net/minecraft/core/BlockPos.java` | `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950` | `c71cc6f248a5c929879dd22a818d270dc6c586900e3dce57301ba86aa3115950` |
| `net/minecraft/core/Vec3i.java` | `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d` | `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d` |
| `net/minecraft/world/phys/Vec3.java` | `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909` | `5c3c36231efe06c3c0bf70dcf2c2ec2f2b79089cb2dad530db1b3b7e1a5ec909` |

Pre-report source freeze: independent comparison established the no-difference result for the complete Player-level override, inherited LivingEntity travel method, LUT/casts and sample/order before opening any paired report. LocalPlayer virtual move dispatch was checked in the addendum after the report was opened. Auditor: Codex source-audit worker. Date: 2026-11-18.
## Dispatch addendum after paired-report opening

The frozen comparison covered the Player and LivingEntity methods and their operation sequence, but a follow-up dispatch check found the local-player virtual `move` wrapper that should be named explicitly. For an ordinary `LocalPlayer`, `LivingEntity.travel`'s `this.move(MoverType.SELF, ...)` dispatches to `LocalPlayer.move` (`LocalPlayer.java:851–856`), which snapshots x/z, calls `super.move(moverType, vec3)` into `Entity.move`, then calls `updateAutoJump` with float-cast x/z displacement. Both the complete six-line `LocalPlayer.move` method and the complete 99-line `updateAutoJump` method are text-identical across 1.15.1 and 1.15.2. `Entity.java` is byte-identical; its base movement body remains the same. This closes the local-player dispatch detail without changing the no-difference verdict.

The paired report itself was opened only after the pre-report Player/LivingEntity/LUT/sample comparison had been recorded. Its supplied SHA-256 was verified as `649d0e171712abfd00f6fe5719e33938e2a464da531df7ecf2566d0cbd31bac8`. It agrees with the no-difference result and source hashes. Its statement that the water branch's move dispatches directly to inherited `Entity.move` omits the `LocalPlayer.move` wrapper; the exact path is LocalPlayer.move → `super.move` / Entity.move → LocalPlayer.updateAutoJump. Both wrapper steps are identical in the pair. The report's separate statement that swim correction precedes inherited movement remains correct.

Final audit verdict: **No source change in the assigned swim-pitch vertical correction or its local-player inherited-move route.** This closes only the bounded operation, not full-pair coverage or runtime parity.

Auditor: Codex source-audit worker. Finalized: 2026-11-18.
