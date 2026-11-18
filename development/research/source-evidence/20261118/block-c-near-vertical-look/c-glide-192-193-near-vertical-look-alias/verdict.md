# Independent audit verdict — C, Elytra near-vertical look alias

## Verdict

**Split verdict.** The bounded source claim is confirmed: no 1.9.2→1.9.3 difference is present in this `moveRelative` Elytra/look-vector/trig slice. The report's static witness has an incorrect lookup index and horizontal-look magnitude and should be corrected before relying on those numeric details. The correction does not change the report's gate conclusion: for the stated pitch, `j > 0.0` and `i < 0.0F` are true. This is not full-pair coverage or runtime-parity evidence.

## Bound input and exact local source identity

Input report: `c-glide-192-193-near-vertical-look-alias/report.md`, SHA-256 `238a53ad9128473f7dca443011f5c1c96361de566bd8d6913734a0ac69beb53b`.

Read-only canonical sources: `decompiled_minecraft/1.9.2/ornithe-feather/` and `decompiled_minecraft/1.9.3/ornithe-feather/`. The local provenance manifests match the input identities stated in the report:

| Release | Manifest SHA-256 | Feather artifact SHA-256 | Client SHA-256 |
|---|---|---|---|
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` | `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65` |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` | `ab8fdde789c5b4614ffc98249e04fdcce7dfce320b55215bfe5418608af6a63b` |

For both manifests, on-disk SHA-256 values of `LivingEntity.java`, `Entity.java`, and `MathHelper.java` match their provenance `sources` entries and the hashes in the input report. The compared `moveRelative` method bodies (1.9.2 lines 1293–1341; 1.9.3 lines 1302–1350), `Entity.getRotationVector(float,float)` bodies (1120–1125; 1124–1129), and complete `MathHelper.java` contents are identical across the pair.

## Independent witness check

For `pitch = -89.999F`, `headYaw = 0.0F`, binary32 evaluation of the source expressions gives `i = -1.5707788467F`. `MathHelper.cos(i)` indexes 0 and returns `0.0F`, as the input report states. But the look-vector vertical angle is `-pitch * (float)(PI/180) = +1.5707788467F`; `MathHelper.cos` computes lookup index 32767, whose table value is about `9.58738e-5F`, not index 32768 / `1.22464685e-16F`. With yaw zero, the resulting look vector has approximately `x = -1.17412e-20`, `z = +9.58738e-5`, hence `j = sqrt(x*x+z*z) ≈ 9.58738e-5 > 0.0`.

The near-vertical view is finite and within the pitch range. For an ordinary active local player in dry conditions, with `isFallFlying()` true from valid chest-slot Elytra state, and a finite horizontal speed (for example `k = 0.1` and initial vertical velocity zero), the source reaches the gliding branch; the `-0.08` vertical update leaves `velocityY < 0.0`. The later raw-pitch gate also passes because `i < 0.0F`. Since `o = k * -MathHelper.sin(i) * 0.04` is positive (the sine lookup is `-1.0F` here), the correction before `move(...)` adds `o * 3.2` to vertical velocity and subtracts `look.x * o / j` and `look.z * o / j` from horizontal velocity. Subsequent `j > 0.0` steering and drag still precede the first move. Thus the sign/gate conclusion survives, while the reported exact `j` value does not.

## Limits

This verdict audits only the assigned source slice and its stated witness. No other campaign outputs, wiki material, product history, network, regeneration, build, tests, or launches were consulted or used.