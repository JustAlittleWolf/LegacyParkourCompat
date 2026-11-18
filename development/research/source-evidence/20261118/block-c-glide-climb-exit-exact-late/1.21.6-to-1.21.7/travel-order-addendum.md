# Travel-order addendum: 1.21.6–1.21.7 climb-exit route

This addendum corrects the position-move statement in the prior verdict. It is bound to:

- Frozen input report `c-glide-1216-1217-actual-climb-exit/report.md`, SHA-256 `FDA2FA518B4D52D43B54E93269B3DE864824E2C18CE3A75D26FB40242CCEF43D`.
- Prior `verdict.md` in this directory, SHA-256 `1596173E4D703F133ED5193433BF371314EAE1A16CC60DE12247F091A53596EA`.
- Exact sides A/B remain those in the prior verdict. The cited `LivingEntity.java`, `Entity.java`, and `LocalPlayer.java` are byte-identical across A/B, match their manifest source hashes, and use matching line locations.

## Corrected source order

For the bounded dry, ordinary local-player path, `LivingEntity.travel` selects `travelFallFlying` when already fall-flying (`LivingEntity.java:2245–2253`). Its `onClimbable()` branch calls `travelInAir` and only after it returns calls `stopFallFlying` (`2353–2356`). `travelInAir` unconditionally invokes `handleRelativeFrictionAndCalculateMovement` (`2276–2281`). That private helper performs, in order: `moveRelative`, `handleOnClimbable` and `setDeltaMovement`, then `this.move(MoverType.SELF, this.getDeltaMovement())` (`2446–2455`). Thus this path does invoke the ordinary-air position-movement operation before the later `stopFallFlying` flag writes at `2369–2372`.

On the climbable predicate, `handleOnClimbable` calls `resetFallDistance()` and clamps the movement vector before the helper's `move` call (`2473–2487`). After the helper's move returns, `travelInAir` performs its gravity/friction velocity write (`2281–2298`); then the climb-exit branch calls `stopFallFlying`. The pretravel cap remains earlier: `LivingEntity.aiStep` calls `updateFallFlying` before travel (`2805–2807`), and that updater calls `checkFallDistanceAccumulation` before its server-only cancellation gate (`2871–2877`; `Entity.java:2674–2677`). On the selected client climbable path the later `handleOnClimbable` reset therefore follows that cap.

`this.move` is virtual. The effective local-player override is `LocalPlayer.move` (`LocalPlayer.java:916–924`), which calls `super.move` before its AutoJump/walk-distance bookkeeping. `Entity.move` handles `MoverType.SELF` through collision resolution, then conditionally records a movement and calls `setPos` (`Entity.java:663–700`). The piston-only early return does not apply to `SELF`; however, the internal collision-result predicate at `685` can omit the position write when the resolved displacement is zero and the requested displacement was not within its near-zero tolerance. This is a condition on the resulting positional write, not a guard that skips the helper's `move` invocation. `Player` has no overriding `move` in this inheritance path.

The earlier verdict's claim that `travelInAir` contains no `move` call was wrong because it failed to follow the private helper. The bounded no-difference result between A and B remains supported: the relevant source files and operation order are byte-identical. This correction does not expand the audited start/final, applicability, equipment, predicate, shape, or intermediate-release bounds.