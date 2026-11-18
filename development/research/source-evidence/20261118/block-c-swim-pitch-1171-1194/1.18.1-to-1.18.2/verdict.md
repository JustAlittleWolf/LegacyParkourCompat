# Independent audit verdict — Player swim-pitch vertical adjustment, 1.18.1→1.18.2

## Verdict

**Upheld for the stated bounded operation: no source difference in Player.travel's swim-pitch direct Y adjustment or its pitch-to-look-vector computation.** This does not establish full pair coverage or an exact patch boundary.

Audited artifact: `c-swim-pitch-vertical-1181-1182-source/report.md`  
Audited artifact SHA-256: `579dc58bd68693a2a91f5f8ddeb80dff7500ed5c04d500df934b7010bc47dc7e`

## Independent source basis

Read-only canonical sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.18.1,1.18.2}/mojmap/`. The paired readiness/provenance/source-manifest SHA-256 identities in the report were verified; the relevant Player, Entity, LivingEntity, Mth, and Vec3 source hashes match their respective `mojmap.sources.sha256` entries. In addition, the readiness marker hashes are 1.18.1 `7a6f3d9d86776e5a95e4722fc2c36f41165c34177f2993978dfaab410641fc81` and 1.18.2 `d8057d47468c37880de38d658e95070ec3b750305b7856189997604822480946`.

The paired `Player.travel(Vec3)` bodies (`Player.java:1409` in 1.18.1; `:1422` in 1.18.2) use the same order and expressions: swimming/non-passenger gate; sample `getLookAngle().y` into `double`; strict `< -0.2` coefficient choice; `<= 0.0 || jumping || nonempty fluid above` write gate; and `delta.add(0.0, (lookY - delta.y) * coefficient, 0.0)`. Literal types, block-position sample, strictness, pre-super position snapshots, and subsequent flying/native travel dispatch are the same.

The operative look-vector path is also unchanged: `Entity.getLookAngle()` passes stored float `getXRot()` to `calculateViewVector`; pitch radians are computed in float as `pitch * (float)(Math.PI / 180.0)`; `Mth.sin(float)` indexes `SIN[(int)(radians * 10430.378F) & 65535]`; the negated float sine is passed to the `Vec3(double, double, double)` constructor and widened to double. Back in `Player.travel`, `lookY` and the subtraction/multiplication with `delta.y` and the double coefficient are double operations. The table initialization and `cos` implementation match. `Vec3.directionFromRotation` matches as well, but is not called by this operation.

For a locally controlled player, the inherited dispatch reaches `LivingEntity.travel`; in its water path the velocity is first passed through `moveRelative`, then to `this.move(MoverType.SELF, this.getDeltaMovement())` (1.18.1 `LivingEntity.java:2038`; 1.18.2 `:2042`). `Player` does not override `Entity.move`, so the real target is inherited `Entity.move`.

## Scope qualification

The report appropriately limits its no-difference claim to the writer and pitch dependency. `Entity.move` itself has a separate 1.18.2 change (including a fall-distance reset clip before applying sufficiently large movement), so this verdict does not claim inherited movement resolution is unchanged. The separate change was observed only to verify the actual inherited target and was not audited as a swim-pitch finding. The broader `LivingEntity.travel` fluid-processing deltas and all other pair coverage remain outside this verdict.

Signed: independent source auditor `/root/c_swim_pitch_vertical_1181_1182_audit`  
Reviewed report SHA-256: `579dc58bd68693a2a91f5f8ddeb80dff7500ed5c04d500df934b7010bc47dc7e`


