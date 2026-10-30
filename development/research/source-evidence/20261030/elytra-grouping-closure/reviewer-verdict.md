# Independent source closure: elytra glide vertical grouping

Verdict: **Accept the operation-order finding, with scope narrowed to the grouped default-gravity vertical acceleration.** The exact source supports the arithmetic change. The Slow Falling branch is a separate dependency and is outside the historical target scope. This verdict is bound to finding SHA-256 `ED7AC76568ADF100F48D92B27D42785AE703BFD0B7631E32D1850D4E50A1A44E` and the exact source files identified below.

## Exact source identity and evidence

All paired sources use Ornithe Feather mappings. Source paths below are in the read-only primary source tree `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`.

| Release | Mapping identity | `LivingEntity.java` SHA-256 | Method evidence |
|---|---|---|---|
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar`, `feather-gen2-1.12.2+build.2.tiny` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` (source manifest and file hash agree) | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1425–1441 |
| 1.13 | `net.ornithemc:feather-gen2:1.13+build.2`; merged jar SHA-256 `a7a61604c7b365db1f4250c4c0079172c3beff37f4e16f7a53a13d77f3976897`; Tiny SHA-256 `7fa6b27aab7245c2d47ecbfe86ad9178272d7b1fd9ba69a482a41659387ffd84` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` (source manifest and file hash agree) | `ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `moveRelative`, lines 1463–1481 |
| 1.13.1 | `net.ornithemc:feather-gen2:1.13.1+build.2`; merged jar SHA-256 `6737db17e8cc724a018aad93d7fe5051a50e77136848d78d79b62310846fa65d`; Tiny SHA-256 `236423be826fb19d08edb3bf7cad9100eab72c5ddbbed8337afcf6ae304c4d14` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` (source manifest and file hash agree) | Same method/expression as 1.13, lines 1478–1500 |
| 1.13.2 | `feather-gen2-1.13.2+build.2-mergedv2.jar`, `feather-gen2-1.13.2+build.2.tiny` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` (source manifest and file hash agree) | Same method/expression as 1.13.1, lines 1478–1500 |
| 26.2 (target check) | `unobfuscated` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` (source manifest and file hash agree) | `unobfuscated/net/minecraft/world/entity/LivingEntity.java`, `updateFallFlyingMovement`, lines 2602–2626 |

The 1.13 and 1.13.1 provenance records identify exact metadata versions and mapping coordinates. The 1.12.2 and 1.13.2 provenance records identify the Feather mapping artifacts and exact decompile runs; their checked-in source manifests bind the inspected class bodies. No source was regenerated.

## Difference and stable cutover

In 1.12.2, `moveRelative` first stores a float cosine-derived value in `m`, then narrows the squared/look-length-scaled product back to float. The vertical update is `velocityY += -0.08 + m * 0.06` (lines 1434–1441). Here `m * 0.06` is promoted to double because `0.06` is a double literal; the sum and compound assignment to double `velocityY` are also double operations.

The first checked stable release with the new grouping is **1.13.0**: its `moveRelative` uses a float `n` with the same float narrowing step and, for ordinary gravity, performs `velocityY += d * (-1.0 + n * 0.75)` with `d == 0.08` (lines 1463–1481). `d` is double; `n` is promoted to double in `n * 0.75`, and every enclosing operation remains double. Exact 1.13.1 and 1.13.2 checks show the same expression and float intermediate. Thus the stable-cutover interval from the 1.12.2/1.13 endpoints closes to 1.13.0; the regrouping is not a 1.13.2-only change.

The default-gravity expressions are algebraically equal over real numbers, but not bit-identical in Java floating-point arithmetic. A finite reachable witness under the inspected 1.13.2 `MathHelper` lookup and `Entity.getRotationVector` path is pitch `30F`, yaw `0F`, zero initial vertical velocity, and no Slow Falling effect. The source's table-based cosine and float narrowing produce `n = 0.7500276565551758F`; the old grouping yields `-0.03499834060668946`, while the 1.13+ grouping yields `-0.03499834060668945`. The distinct finite double results differ by one ULP at that magnitude. This is a static expression calculation, not a movement test.

## Reachability and narrow implementation contract

The player path reaches the inherited operation: `LivingEntity` invokes virtual `moveRelative(sidewaysSpeed, verticalSpeed, forwardSpeed)` during its movement tick (1.12.2 line 1847; 1.13.2 line 1926). `PlayerEntity.moveRelative` delegates to `super.moveRelative` both in its flying-special-case and ordinary branch (1.12.2 lines 1386–1401; 1.13.2 lines 1442–1465). The inherited method gates this branch on local/logical movement authority, not being in water (unless player flight applies), not being in lava (unless player flight applies), and `isFallFlying()` (1.12.2 lines 1425–1429; 1.13.2 lines 1478–1488). This closure does not revisit the separate glide-look guard.

For an independent implementation of this operation only, preserve the historical float intermediate used to form the glide lift factor and then preserve the selected version's exact double expression for the vertical acceleration before subsequent glide velocity adjustments. The 1.12.2 contract is `-0.08 + m * 0.06`; the 1.13+ ordinary-gravity contract is `d * (-1.0 + n * 0.75)` with `d == 0.08`. Keep the float cast in the lift-factor calculation, Java `MathHelper` trig behavior, literal types, operation grouping, and the existing compound update order. Do not simplify to one formula or fuse this arithmetic with subsequent velocity corrections.

In 1.13+, `d` changes to `0.01` when vertical velocity is nonpositive and Slow Falling is present, with fall distance reset (1.13.2 lines 1480–1484). This confirms the finding's shared-gravity-factor statement, but Slow Falling itself is modern-only for the requested one-way legacy scope; it is not part of the historical implementation contract. The 26.2 target retains the grouped algebraic form in `updateFallFlyingMovement` (`gravity * (-1.0 + liftForce * 0.75)`, lines 2602–2609), but its lift factor is computed as a double square of `Math.cos`; that is a distinct modern numeric pipeline and does not establish parity with 1.13 float intermediates.
