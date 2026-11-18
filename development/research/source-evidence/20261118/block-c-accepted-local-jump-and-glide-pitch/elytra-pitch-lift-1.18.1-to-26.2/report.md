# 1.18.1 → 26.2 glide pitch cosine witness

## Scope and verdict

Bounded witness: ordinary, dry, already-active local-player Elytra fall-flying, with ordinary non-flying/non-passenger control and a standard chest Elytra. This compares only the pitch-derived lift factor through its first real movement call. It is not full-pair coverage and does not establish an exact intermediate patch boundary.

**Difference:** 1.18.1 multiplies its float lookup-table cosine by a second cosine and `min(1.0, lookAngle.length() / 0.4)`, then narrows the result to `float`. 26.2 computes `Math.cos(leanAngle)` and squares that `double`; it removes the view-length cap and changes the precision path. Both factors feed the vertical gravity adjustment before the first self movement.

## Reachability and first movement

- 1.18.1 `LocalPlayer.isEffectiveAi()` returns `true` (`client/player/LocalPlayer.java:497-499`). The base `Entity.isControlledByLocalInstance()` accepts a local `Player` controller (`world/entity/Entity.java:2672-2675`). In `LivingEntity.travel`, the ordinary local-controlled guard admits the body; water and lava dispatch precede the `isFallFlying()` branch (`world/entity/LivingEntity.java:2005-2016, 2050-2071`). In that branch the factor is calculated, delta movement is set, then the first real move is `move(MoverType.SELF, this.getDeltaMovement())` (`:2076-2099`).
- 26.2 `LivingEntity.aiStep` calls `travel(input)` when `canSimulateMovement()` and `isEffectiveAi()` both hold (`world/entity/LivingEntity.java:3126-3141`); both default predicates return `isLocalInstanceAuthoritative()` (`world/entity/Entity.java:3589-3609`), which follows the local client authority predicate; `Player.isLocalPlayer()` defaults false and is overridden by the actual `LocalPlayer` (`world/entity/player/Player.java:1280-1287`; `client/player/LocalPlayer.java:378`). `Player.travel` forwards to `super.travel(input)` for the ordinary non-passenger/non-flying case (`world/entity/player/Player.java:1402-1423`). `LivingEntity.travel` dispatches fluid first, then `isFallFlying()` (`world/entity/LivingEntity.java:2430-2437`). In clear dry air without `onClimbable()`, `travelFallFlying` computes and sets the updated velocity, then calls `move(MoverType.SELF, ...)` (`:2581-2594`); `updateFallFlyingMovement` returns that updated vector (`:2602-2627`).

This path is conditioned on fall-flying already being active. The chest item/start-glide eligibility operation is outside this factor witness.

## Exact arithmetic

**1.18.1, Mojmap** (`LivingEntity.java:2076-2083`):

1. `$$15` is a `float`: raw `getXRot()` (float degrees) multiplied by `(float)(Math.PI / 180.0)`; there is no extra clamp or conversion of the raw pitch before this multiplication.
2. `Mth.cos($$15)` returns `float` through the 65,536-entry sine table (`Mth.java:47-49`; lookup index expression is float arithmetic).
3. `$$19` is assigned as `(float)($$19 * ($$19 * Math.min(1.0, $$18 / 0.4)))`. `$$18` is the `double` result of `lookAngle.length()` (`LivingEntity.java:2078, 2081-2082`; `Vec3.length()` squares components, adds, then `Math.sqrt`, returning `double`, `Vec3.java:123-125`). The `Math.min` and inner/outer products therefore use double arithmetic before the final cast to float.
4. The vertical adjustment uses double gravity and promotes the float factor in `gravity * (-1.0 + factor * 0.75)` (`:2083`).

**26.2, unobfuscated** (`LivingEntity.java:2602-2609`):

1. `leanAngle` is a `float` formed from raw float `getXRot()` degrees times `(float)(Math.PI / 180.0)` (`:2604`), as before.
2. `Math.cos(leanAngle)` invokes the double overload after widening the float argument; `Mth.square(double)` squares and returns `double` (`:2608`; `Mth.java:642-644`). This is not `Mth.cos` and does not use its float lookup table.
3. The double `liftForce` is applied to double gravity in `gravity * (-1.0 + liftForce * 0.75)` (`:2608-2609`). There is no `lookAngle.length()` multiplier in this factor.

The look vector still controls separate downward-conversion and horizontal-alignment branches (`:2610-2623`), which are not part of this factor verdict.

## Safe numeric comparison

Use raw pitch `30.0F`, yaw `0.0F`, and a unit look vector. This is nonvertical: the horizontal look length is approximately `0.866`, so the later `lookHorLength > 0.0` guards are safely true; the view-length ratio caps at one. Simulating the source float operations and 1.18.1 LUT index gives:

- 1.18.1 table cosine `0.866041362285614F`; narrowed factor `0.7500276565551758F`.
- 26.2 `Math.cos((double)0.5235987901687622F)^2` = `0.7499999873816084` (double).
- Factor delta (new minus old) ≈ `-0.0000276691735673`.

This comparison isolates the angle/cosine precision path under the ordinary capped-length case. The source-level behavior also differs if a supplied look vector has length below `0.4`, because only the old factor scales by `length / 0.4`; the standard rotation-derived look vector in this scenario is well above that threshold.

## Adjacent slices and phase separation

Keep this as a pitch-lift-factor operation. Separate bounded checks are needed for the nearby dry/fluid dispatch, climbable fallback, the 26.2 pre-travel `updateFallFlying()` phase (`LivingEntity.java:3127-3129`), gravity selection/effects, and post-move collision handling. This endpoint comparison only bounds any change to the interval after 1.18.1 and through 26.2; it does not prove when the factor changed or that adjacent glide operations share that boundary.

## Canonical source identity and hashes

Sources were read from the canonical primary checkout, not generated or modified here. Full-file SHA-256 values below were checked against the respective `*.sources.sha256` manifests.

| Side | Exact release / mapping | Provenance and ready identity | Source file SHA-256 |
|---|---|---|---|
| Older | `1.18.1` / Mojmap | `mojmap.provenance.json`: run `prep-1.18-1.18.1-mojmap-2026-10-09-r1`, metadata ID `1.18.1`; client JAR SHA-256 `737c28a56c8980fcd8574465c61538ebde8f511bdd035c69d0cbe27953f892aa`; official mappings SHA-256 `a7a4109255dfb74b9552b339482f0714bb0e7e7143d1059b0bf62a526d2c2a84`. Provenance SHA-256 `4b181e586201aa2695835e31155c96d8ae3d05b00c9bbb83e99eaa5b2b9db139`; ready SHA-256 `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81`; source manifest SHA-256 `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`. | `world/entity/LivingEntity.java` `68fc9b15eb31690377afe92acfc783c86945fedec5739d28faacd96da4f9e0e7`; `world/entity/player/Player.java` `1fb9dfb9f700323d77f4196dbc3618d01a65e56438b9cce3ee38fa0b258bd7e2`; `client/player/LocalPlayer.java` `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996`; `world/phys/Vec3.java` `3695439a814bc61cf986d4f6b7164aa930176d196287a3eb2013b9f2e72acf2c`; `util/Mth.java` `10bd2dcc2b7084d0f9be24a3e54322510f1730e67fb84d35ec460c6953b3397f`; `world/entity/Entity.java` `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`. |
| Newer | `26.2` / published unobfuscated sources | `unobfuscated.provenance.json`: version ID `26.2`; ready marker version ID `26.2`. Provenance SHA-256 `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1`; ready SHA-256 `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. | `world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `world/entity/player/Player.java` `8decc71b9c780664578dddb14591db2a2f207c72c05b676edded6f8e964576531`; `util/Mth.java` `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17`; `world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. |

Evidence paths are relative to `decompiled_minecraft/<version>/<mapping>/net/minecraft/`. No source-generation, repository-history, wiki, implementation, build, test, or runtime checks were used.


