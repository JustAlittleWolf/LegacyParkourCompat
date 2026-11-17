# Independent blind audit: 1.21.10 → 1.21.11 tick and pose

## Verdict

**COMPLETED — bounded claims upheld.** I independently inspected the exact local Mojmap sources for the assigned grouped no-diff claims and both reported source deltas. No in-scope player position, velocity, or pose difference is established in these slices. This is not a full-pair freeze.

## Immutable input and source identity

The required queue report was verified as SHA-256 `9d077edf2db34568fed6d6460a46e59d1c757979b0683f1b572a3880b12823c1` before use. Its claims were treated as claims to audit, not accepted as proof. Sources were read-only from `decompiled_minecraft/1.21.10/mojmap/` and `decompiled_minecraft/1.21.11/mojmap/`.

Both ready markers identify the exact release and `mojmap`; both provenance records identify the same exact batch (`1.21.8,1.21.10,1.21.11`), Mojmap, Vineflower 1.12.0, mapping-io 0.9.1, and Java 25. The local source-manifest SHA-256 values match the ready markers and report: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`; 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`. Direct source-file SHA-256 values match each source manifest for `LivingEntity.java`, `Player.java`, and `Entity.java` on both sides.

## Independently reviewed claims

1. **Jump admission, cutoffs and travel/fall order — no difference in the inspected paths.** Compared `LivingEntity.aiStep` (1.21.10 lines 2752–2860; 1.21.11 lines 2878–3008). Both retain the player horizontal `horizontalDistanceSqr() < 9.0E-6` cutoff, separate vertical `abs(y) < 0.003` cutoff, input/immobility handling, the same local jump/fluid gates and `noJumpDelay` write, fall-flying update before travel, fall-distance reset check, and travel dispatch order. The checked `jumpFromGround` early return remains `getJumpPower() <= 1.0E-5F`; velocity and sprint vector expressions are unchanged. This verifies only the stated local slice; fluid height/sensing and travel internals were not audited.

2. **Pose writer, dimensions and old-position writers — no difference in the inspected paths.** `Player.tick` calls `updatePlayerPose` in the same post-`super.tick()` position (both files around lines 238–294). `updatePlayerPose`/`getDesiredPose` retain the same priority, fit fallback selection and final `setPose` call (`Player.java:352–384` both versions). `Entity.setPose`, `DATA_POSE` refresh callback, and `refreshDimensions` retain the same writer sequence: capture old dimensions, derive pose dimensions and eye height, reapply position, then apply the same guarded server size-growth handling with the player exclusion (`Entity.java:412–417, 3193–3222` vs `413–418, 3207–3241`). `setOldPos` still copies the same position coordinates into old-position fields. The pose-fit collision geometry itself was not reviewed as a no-diff claim.

3. **Jump impulse bookkeeping delta — confirmed, no demonstrated in-scope vector change.** `LivingEntity.jumpFromGround` changes the final marker from `hasImpulse = true` (1.21.10:2235–2246) to `needsSync = true` (1.21.11:2269–2280). The jump-power gate, vertical max write, sprint-angle calculation and horizontal addition match. On this evidence the delta is synchronization/impulse bookkeeping; no changed player position or velocity result is established.

4. **Position-derived sample delta — confirmed, outside assigned movement consequence.** 1.21.11 adds `lastKnownPosition`/`lastKnownSpeed`; `reapplyPosition` invalidates the sample, `baseTick` calls `computeSpeed`, and that method records the position difference before capturing the current position (Entity lines 458–461, 484–487, 540–546). The 1.21.10 source has no sampler. The sample itself does not write position, velocity, or pose. Checked references lead to `KineticWeapon` attack scaling (damage calculation, excluded) and `FollowBoatGoal` (non-player/vehicle behavior, excluded); they do not establish altered local player movement in this assignment.

## Scope boundary and coverage limits

Covered: the assigned jump admission/cutoff/early-return and travel/fall ordering slice; player pose-selection and pose/dimensions/old-position writers. Confirmed but dispositioned: the jump marker swap and derived speed sample as described above.

Not covered: glide trigonometry and the known glide delta; item input (already handled by separate pretravel evidence); collision response/shape providers; fluid sensing/contact and fluid travel math; full travel-world dependencies; external direct movement responses; movement changes in other classes or callers not named above; intermediate releases and exact patch introduction boundaries. The new sampler's full attack/damage path was not audited beyond the direct usage and scope classification described above. No claim of complete 1.21.10→1.21.11 movement coverage or runtime parity is made.

No product, wiki, backlog, or Git history was read. No code, tests, launches, or source generation were performed.
