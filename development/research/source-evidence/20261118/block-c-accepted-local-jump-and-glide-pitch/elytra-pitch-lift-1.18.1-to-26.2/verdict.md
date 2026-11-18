# Independent verdict: 1.18.1 → 26.2 glide pitch cosine witness

## Verdict

**Accepted for the bounded claim.** The supplied report accurately identifies a source-level difference in the pitch-derived Elytra lift factor for an already-active local player's ordinary dry fall-flying path. The evidence establishes this difference between the two exact endpoints only. It does not establish a patch introduction boundary, full-pair source coverage, or runtime parity.

The report under audit is `c-glide-pitch-cos-1181-262-witness/report.md`, SHA-256 `0300468D76447B7DED545AB512C14F5FEE46CCDCBDE6FFD0132A6EF3EF075327` (verified before reading).

## Independent source trace

- **1.18.1, Mojmap:** `LivingEntity.travel` admits travel for effective AI or local control; water and lava cases precede fall-flying. For a dry already-active glide, lines 2076–2083 get the look vector, form a `float` pitch-radian value from raw `getXRot()` (`float degrees * (float)(Math.PI / 180.0)`), obtain a `float` from `Mth.cos(float)`, and assign `(float)(cos * (cos * Math.min(1.0, look.length() / 0.4)))`. `Mth.cos` indexes the float sine table using `(int)(angle * 10430.378F + 16384.0F) & 65535`. `Vec3.length()` is double math. The factor participates in the gravity adjustment and the downward look-conversion term. After the other branches, the updated vector is multiplied by `(0.99F, 0.98F, 0.99F)` and passed to the first `move(MoverType.SELF, getDeltaMovement())` at lines 2098–2099.
- **1.18.1 reachability:** `LocalPlayer.isEffectiveAi()` returns true. Base `Entity.isControlledByLocalInstance()` accepts a local `Player` controller. The stated local-player case therefore reaches the ordinary travel body; the dry and already-active conditions select the cited branch.
- **26.2, published unobfuscated:** `LivingEntity.aiStep` calls `travel(input)` on the ordinary simulation path when `canSimulateMovement()` and `isEffectiveAi()` hold. Both default predicates use `isLocalInstanceAuthoritative()`; for a client player, that is overridden through `Player.isLocalClientAuthoritative()` → `isLocalPlayer()`, and `LocalPlayer.isLocalPlayer()` returns true. Ordinary `Player.travel` forwards to `super.travel(input)` when not a passenger and not using the flying-ability branch. `LivingEntity.travel` selects fluid before `isFallFlying()`. Under the bounded dry, already-active conditions, `travelFallFlying` calls `updateFallFlyingMovement`, sets its result, then makes the first real self move (lines 2581–2589).
- **26.2 factor:** `updateFallFlyingMovement` forms `float leanAngle` from the same raw float pitch and float degree multiplier. `Math.cos(leanAngle)` widens the float to the `double` overload; `Mth.square(double)` returns the squared cosine as a `double`. The lift expression therefore has neither the old lookup-table float path nor the old look-vector-length cap. This double lift factor enters the gravity adjustment and the downward look-conversion term before the first move.

## Numeric check and first-move consequence

For raw pitch `30.0F`, yaw `0.0F`, and the ordinary unit look vector, the horizontal look length is `cos(30°) ≈ 0.866`, so the case is nonvertical and the old length multiplier is capped at one. Float operations give pitch radians `0.5235987901687622F` (bits `3F060A92`); the old table index is `21845`; its cosine is `0.866041362285614F` (bits `3F5DB4E3`). The old narrowed factor is `0.7500276565551758F`. The newer factor, `Math.cos((double)0.5235987901687622F)^2`, is `0.7499999873816084` as a double. New minus old is approximately `-0.0000276691735673`.

To show the factor reaching the first move, take a controlled arithmetic illustration with no effects, gravity `0.08`, zero initial velocity, and the same pitch/look vector. Applying the cited gravity term, then the downward look-conversion term and the final vertical `0.98F` multiplier yields first-move Y of about `-0.0317259015199503` on the old path and `-0.0317275013470995` on the new path (new minus old about `-1.5998271493e-6`). This illustration isolates the factor while holding gravity and starting velocity equal; it is not a general trajectory or runtime claim.

The remaining look-horizontal calculations, pitch-negative acceleration branch, velocity alignment, friction multiplier, gravity/effect selection, fluid/climbable dispatch, the 26.2 pre-travel `updateFallFlying()` phase, collision handling, and Elytra activation eligibility are separate operations. They are not evidence for or against this bounded factor claim.

## Canonical evidence identity and relied-on hashes

All source paths below are relative to `decompiled_minecraft/<release>/<mapping>/net/minecraft/`. I read the canonical existing decompilation read-only. Direct source SHA-256 values below agree with the corresponding records in each `*.sources.sha256` manifest.

| Side | Exact source identity | Provenance SHA-256 | Ready marker SHA-256 | Source-manifest SHA-256 |
|---|---|---|---|---|
| Older | `1.18.1`, Mojmap; run `prep-1.18-1.18.1-mojmap-2026-10-09-r1`; metadata ID `1.18.1`; client JAR SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`; official client mappings SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`. | `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139` | `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` |
| Newer | `26.2`, published unobfuscated sources; provenance and ready marker both identify exact version `26.2`. | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

| Side | Relied-on source path | SHA-256 |
|---|---|---|
| 1.18.1 | `world/entity/LivingEntity.java` | `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7` |
| 1.18.1 | `world/entity/Entity.java` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.1 | `world/entity/player/Player.java` | `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2` |
| 1.18.1 | `client/player/LocalPlayer.java` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` |
| 1.18.1 | `world/phys/Vec3.java` | `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c` |
| 1.18.1 | `util/Mth.java` | `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f` |
| 26.2 | `world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `world/entity/player/Player.java` | `8decc71b9c780664578dddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| 26.2 | `util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |

## Scope and signature

This verdict accepts only the exact 1.18.1 → 26.2 local ordinary dry active-fall-flying pitch-cosine/squared-lift witness and its path to the first real self-move. No wiki, product implementation/history, source generation, network, build, tests, game/runtime launch, or delegation was used.

Signed: `/root/c_glide_pitch_cos_1181_262_witness_audit` — independent exact-source witness audit; verdict `ACCEPTED`, scope as stated above.
