# Independent dry-land travel audit

**Verdict: ACCEPT — bounded source comparison.**

Audited immutable report `build/later-source-20261116/1.21.11--26.1.2/dry_travel/coverage.md`, SHA-256 `B13E292A564EB94D1037F1E627F19B01CB3659E7859B82211F9B2254A7D4B39D`.

## Identity verification

The listed ready markers, provenance records, source manifests, and artifact manifests exist at the stated version-root paths. Their SHA-256 values match the report. The ready records identify exactly `1.21.11`/`mojmap` and `26.1.2`/`unobfuscated`; each ready `sourceManifestSha256` binds the listed source manifest and each ready `artifactManifestSha256` binds the common root `artifacts.sha256`. Provenance records independently identify the same version and namespace. All six cited Java file hashes match both the local files and corresponding manifest rows.

## Source review

The compared `Player.travel(Vec3)` wrappers preserve passenger dispatch, the swimming-only adjustment, and the flying-only Y reset around `super.travel`; for the report's non-passenger, non-swimming, non-flying case each calls the inherited travel path without those branches. `LivingEntity.travel` selects fluid, fall-flying, then air in the same order. Given the stated false fluid gate and non-fall-flying condition, both versions call `travelInAir`.

The two `travelInAir` bodies match in operation order and gates: select the supporting position, choose block friction only while grounded (otherwise `1.0F`), multiply by `0.91F`, compute relative movement, apply levitation or gravity/client missing-chunk Y handling, then either preserve movement under `shouldDiscardFriction` or apply horizontal friction plus the `FlyingAnimal`/`0.98F` vertical factor. The referenced `getBlockPosBelowThatAffectsMyMovement` route and the `getOnPos(0.500001F)` supporting-position selector have matching expressions and thresholds. The selector's state-population inputs remain outside this comparison, as the report says.

The helper `handleRelativeFrictionAndCalculateMovement` matches in order: friction-influenced speed, `moveRelative`, `handleOnClimbable`, `move(SELF, delta)`, then the horizontal-collision/jumping climb-or-powder-snow vertical correction. `getFrictionInfluencedSpeed` uses the same grounded `getSpeed() * (0.21600002F / friction³)` branch and airborne `getFlyingSpeed()` branch. `Player.getSpeed()` returns the movement-speed attribute cast to `float` in both versions. `Entity.moveRelative` and `getInputVector` match the same small-input cutoff, normalize-if-length-squared-over-one condition, scale, yaw rotation, and delta-movement addition. I found no changed constant, cast, type, branch, expression order, or write in these bounded operations.

## Limits

This ACCEPT covers the source statements and conditional travel path only. It does not establish support-position population equivalence, friction/resource values, input sampling or modifiers, climb-specific outcomes, collision/step-up consequences, effects, other tick paths, trajectory parity, or pair-wide coverage. No runtime or build evidence was produced.