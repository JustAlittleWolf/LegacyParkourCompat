# C source witness: `Entity.turn` pitch precision to Elytra movement

## Scope and result

This is a blind, source-only comparison of ordinary in-range pitch arithmetic in `Entity.turn` and its first active nonpassenger Elytra movement consumer. The compared source trees are exact 1.16.5 Mojmap and 26.2 published unobfuscated source. No product source, wiki material, other report, generated source, runtime, test, or build was used. No input-device code was changed.

**Result:** a reachable finite `turn(double, double)` call can produce adjacent but different pitch floats between these versions; either version's active Elytra branch then produces a different move request when the remaining branch inputs are held fixed. The pitch effect is demonstrated within each branch, so the changed glide formula and cross-version trig lookup behavior are not attributed to `turn`.

## Exact source identities and file hashes

- `1.16.5`, namespace `mojmap`, ready source manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`. The ready marker identifies release `1.16.5`, namespace `mojmap`, and 3,523 source files; provenance identifies Vineflower 1.12.0 and Java 25.0.3+9-LTS.
- `26.2`, namespace `unobfuscated` (the exact published source tree for this release), ready source manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. The ready marker identifies release `26.2`, namespace `unobfuscated`, and 7,055 source files; provenance identifies Vineflower 1.12.0 and Java 25.0.3+9-LTS.

The relevant complete source-file SHA-256 values are:

| Source file | SHA-256 |
| --- | --- |
| 1.16.5 `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| 1.16.5 `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.5 `net/minecraft/util/Mth.java` | `0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290` |
| 26.2 `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 `net/minecraft/util/Mth.java` | `30455c684f2401c79290847822bca82e77162c4a2bcf8618e85cac9898a2dc17` |
| 1.16.5 `net/minecraft/client/MouseHandler.java` | `2611dceb1078a63a33d621bf02f466ae70f6d015c902813935b9836f39415949` |
| 26.2 `net/minecraft/client/MouseHandler.java` | `049c4f21b6e1b724e1f0ed8b6784e55eae11c50ccd88c2a483a7253429ef8c15` |

The per-file hashes were checked against the files in the ready source trees. Ready-marker manifest digests bind the complete trees; the table binds the source members cited below.

## `turn` arithmetic

1.16.5 Mojmap `Entity.turn(double d, double e)` at `net/minecraft/world/entity/Entity.java:340-352` computes `double f = e * 0.15`, then stores `(float)(this.xRot + f)` before clamping. Thus the input multiply and addition are binary64, followed by a single binary32 cast. The local client call path is `MouseHandler.turnPlayer` at `net/minecraft/client/MouseHandler.java:214-243`, which passes the accumulated vertical mouse delta as a `double` to `player.turn` at line 242.

26.2 `Entity.turn(double xo, double yo)` at `net/minecraft/world/entity/Entity.java:489-501` first computes `float xDelta = (float)yo * 0.15F`, then adds that float to `getXRot()` as binary32 and calls `setXRot`. `setXRot` at lines `3906-3915` applies `% 360.0F` and clamps to `[-90.0F, 90.0F]`; neither operation changes the witness below. The client call path is `MouseHandler.turnPlayer` at `net/minecraft/client/MouseHandler.java:347-375`, passing its accumulated vertical mouse delta as a `double` at line 375. These are the existing call paths; this finding does not change mouse accumulation, sensitivity, or other input plumbing.

### Finite witness

Hold the current pitch at the finite, in-range float `-15.801899909973145F` and call `turn` with vertical delta `yo/e = 5.7D`:

- 1.16.5: `(float)(-15.801899909973145F + 5.7D * 0.15D)` is `-14.946900367736816F`.
- 26.2: `(float)(-15.801899909973145F + ((float)5.7D * 0.15F))` is `-14.9468994140625F`.

Both results remain strictly inside the normal pitch bounds, so the clamp is inactive. The results differ by one binary32 step at this magnitude (`9.5367431640625E-7` degrees). This uses moderate finite inputs, not an overflow, NaN, infinity, or clamp-edge case. A direct local-player route to `turn` exists in both exact sources; the calculation is at the public method boundary and makes no claim about a particular physical device event encoding the decimal delta.

## First active nonpassenger Elytra consumer

In 1.16.5, `LivingEntity.updateFallFlying` at `LivingEntity.java:2478-2495` keeps fall-flying active only when the player is airborne, not a passenger, not affected by levitation, and has a fly-enabled chest Elytra. `LivingEntity.travel` at lines `1914-2020` gives fluid handling precedence and then enters the `isFallFlying()` branch; the branch reads `getLookAngle()` and current `xRot` at `1985-1991`, applies Elytra steering at `1992-2007`, writes the resulting velocity, and passes it to `move(MoverType.SELF, ...)` at line 2008. The witness uses the reachable no-fluid, no-levitation, nonpassenger, active-glide path.

In 26.2, `LivingEntity.canGlide` at `LivingEntity.java:3215-3223` requires airborne, nonpassenger, non-levitating state and an eligible glider; `travel` at `2430-2437` selects `travelFallFlying` after the fluid gate. With no climbable block, `travelFallFlying` at `2581-2590` stores `updateFallFlyingMovement(lastMovement)` and submits that movement to `move(MoverType.SELF, ...)` at line 2589. `updateFallFlyingMovement` reads look angle and pitch at `2602-2626`.

Both releases convert current pitch to the same kind of float lean angle, `pitch * (float)(Math.PI / 180.0)`. The source-level numeric witness yields lean angles `-0.26087263226509094F` (1.16.5 turn result) and `-0.26087260246276855F` (26.2 turn result). On both exact versions, `Mth.sin(leanAngle)` maps these angles to different sine-table indices: `62815` and `62816`. The table is initialized from sine in 1.16.5 `Mth.java:12-16`, with float-indexing methods at `25-30`; in 26.2 it is initialized at `35-38`, with the double-indexing methods at `50-55`. The adjacent table entries are distinct (approximately `-0.25792372F` and `-0.25783110F`).

To isolate causality, hold the branch's other movement inputs fixed within each release: yaw `0`, no fluid/climbable/levitation, ordinary gravity `0.08`, active fall-flying, and initial delta movement `(0, 0, 1)`. This gives nonzero horizontal speed (`l` / `moveHorLength = 1`) and keeps the negative-lean branch active. In 1.16.5, `p = l * -Mth.sin(j) * 0.04` at lines `1999-2000` changes, is added to vertical movement as `p * 3.2`, then remains in the velocity passed to `move` at line 2008. In 26.2, the corresponding `convert = moveHorLength * -Mth.sin(leanAngle) * 0.04` at lines `2616-2617` changes and is likewise added to vertical movement as `convert * 3.2`; after the final drag at line 2626, that result is passed to `move` at line 2589. No later vertical term in either selected path cancels this nonzero difference. Thus the `turn` precision change reaches a different actual Elytra move request in both branches when unrelated inputs are held constant.

As a static arithmetic check of that request, the Y component passed to `move` is approximately `0.01103705822` versus `0.01102543926` in the 1.16.5 branch, and `0.01103705602` versus `0.01102543789` in the 26.2 branch, when each branch is evaluated at the older versus newer turn-produced pitch with the fixed inputs above. These are source-expression calculations, not game/runtime observations.

## Concurrent mechanics kept separate

The two Elytra implementations are not otherwise identical. 1.16.5 derives lift through quantized `Mth.cos(j)` and its `n` expression at `LivingEntity.java:1990-1992`; 26.2 uses continuous `Math.cos(leanAngle)^2` at `2604-2608`, and adds other intervening logic. I did not attribute those differences to `turn`.

The trig lookup itself also changed. 1.16.5 indexes with a rounded float multiplication (`f * 10430.378F`, plus a float cosine offset), while 26.2 indexes with a double scale (`i * 10430.378350470453`, plus a double offset). For the same old pitch witness, the pitch sine index agrees but the cosine index used for `getLookAngle` differs across releases (`13663` in 1.16.5 versus `13662` in 26.2); after the newer turn result the newer cosine index advances to `13663`. This is a concurrent LUT-indexing effect. The causal witness above instead compares each version's own branch at its two turn-produced pitches and uses the independently varying `Mth.sin(leanAngle)` steering term.

This closes only the assigned `turn`-to-active-Elytra-consumer witness. It is not complete 1.16.5--26.2 movement-pair coverage, an intermediate-version boundary, acceptance, implementation, or runtime parity evidence.
