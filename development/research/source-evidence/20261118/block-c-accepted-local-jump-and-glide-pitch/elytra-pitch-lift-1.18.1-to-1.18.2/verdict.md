# Independent source audit verdict: 1.18.1→1.18.2 Elytra pitch-cosine factor

## Bound review identity

- Candidate: `c-glide-pitch-cos-1181-1182-source/report.md`
- Candidate SHA-256: `4e5094d335e3f6082cba82ca2a7d9987d48662390a97f4368699b8a6cf56b3ea`
- Scope: exact local Mojmap source comparison of the dry, active, local ordinary-player Elytra travel cosine factor, including reachability, precision, calls and numeric witness. No product implementation or runtime parity review.
- Verdict: **ACCEPT the source-difference claim; do not accept the report unchanged.** Its central finding and real movement reachability are source-supported. Correct the witness arithmetic and the stated downstream use before treating this report text as accepted evidence.

## Frozen source identity and bytes

Read-only local exact-release Mojmap trees were checked before opening the candidate report. The per-release manifests and actual source bytes agree for the examined files.

- 1.18.1: exact version `1.18.1`, mapping `mojmap`, Vineflower 1.12.0; run ID `prep-1.18-1.18.1-mojmap-2026-10-09-r1`. `mojmap.sources.sha256` SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`; `mojmap.provenance.json` SHA-256 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`.
- 1.18.2: exact version `1.18.2`, mapping `mojmap`, Vineflower 1.12.0. `mojmap.sources.sha256` SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`; `mojmap.provenance.json` SHA-256 `ce14d5dfc2d51cffa7b1512878d908e37c930d414147be24e69439a7f1061ca9`; ready marker records client SHA-256 `1d09e3639644b6b2254499469d0765cc005a286d19f3fa595b0ed8fb07971ec7`.

Actual SHA-256s:

| Source member | 1.18.1 | 1.18.2 |
|---|---|---|
| `net/minecraft/world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` | `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a` |
| `net/minecraft/client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `99c2d18bcd23243afbf8b95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` |
| `net/minecraft/util/Mth.java` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` | `32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e` |

## Independently checked source result

In `LivingEntity.travel(Vec3)`, 1.18.1 lines 2081–2082 assign `float $$19 = Mth.cos($$15)` then cast the complete product to float: `$$19 = (float)($$19 * ($$19 * Math.min(1.0, $$18 / 0.4)))`. Here `$$15` is a float angle, `$$18` is a double length, and the `Math.min` overload and product before the cast are double. `Mth.cos(float)` in both releases indexes the 65,536-entry `float[] SIN` using `(int)(angle * 10430.378F + 16384.0F) & 65535`.

In 1.18.2 `LivingEntity.travel(Vec3)` lines 2085–2086 instead assign `double $$19 = Math.cos($$15)` then retain `$$19 * $$19 * Math.min(1.0, $$18 / 0.4)` as double. The old table lookup and float-rounded factor are therefore a real source difference. The separate later negative-pitch term still computes `$$21 = $$17 * -Mth.sin($$15) * 0.04` in both versions; the cosine change does **not** feed that pitch-up term. The factor does affect the gravity expression and the downward-velocity correction `$$20`.

Reachability is supported for a legal active glide: player pitch is clamped to `[-90F, 90F]` by `Entity.turn`, and +45° is valid. `LivingEntity.updateFallFlying()` permits active fall flying when the shared flag is set, the player is airborne, not a passenger or levitating, and the chest Elytra is enabled. `Player.travel` delegates to `LivingEntity.travel` when the player is not swimming or ability-flying. `LivingEntity.travel` runs for the controlled local player; the active branch stores the resulting velocity with drag `(0.99F, 0.98F, 0.99F)` and calls `move(MoverType.SELF, this.getDeltaMovement())`. This is the actual movement operation. In an unobstructed dry state the Y move difference is not clipped.

## Witness correction required

For the report's +45° pitch, yaw 0°, velocity `(0, -0.1, 0.3)`, normal gravity and unobstructed dry state, independent source-order arithmetic gives the reported cosine-table index `24576`, cosine `0.7071067690849304F`, old factor `0.4999999701976776F`, new factor `0.49999997814430497`, and factor delta `7.946627356147928E-9`. The view length is approximately `0.999999982885729`, so `min(1.0, length / 0.4)` is 1.0.

The report's absolute move-Y values apply a double `0.98`; source line 2098/2102 passes the float literal `0.98F` to `Vec3.multiply(double, double, double)`, widening the rounded float. Using that exact source operand, resulting unobstructed move Y is approximately `-0.1396500048208237` for 1.18.1 and `-0.1396500042601097` for 1.18.2; the same delta, approximately `5.60714003E-10` blocks, remains. The report should replace its absolute values and remove the claim that the factor changes the separate pitch-up contribution.

## Signature

Independent reviewer: `/root/c_glide_pitch_cos_1181_1182_audit`  
Signed scope: exact local 1.18.1→1.18.2 Mojmap source audit bound to candidate SHA-256 above.  
Verdict: source finding accepted; candidate numeric witness requires the two corrections stated here.
