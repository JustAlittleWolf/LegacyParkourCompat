# Travel order addendum — Elytra climbable exit audit

## Binding and supersession

This addendum is bound to the exact supplied report SHA-256 `c06d237d049085aacd812f92f1d32bd4911bff05cfc3d3a676339b0b97f9d723` and the original frozen verdict SHA-256 `fc15ab13ebd27dfc331d2aabd8677a13d43dd4ed9953d7e88a4c42faceee6f08`. It corrects the original verdict's statement that the 1.21.10 climbable arm issues no `move()` call. The supplied report's move-before-flag ordering is supported after tracing the nested helper and local-player dispatch.

Supplementary exact-source SHA-256 identities, all matching their local manifest entries:

| Release | `Entity.java` | `LocalPlayer.java` |
| --- | --- | --- |
| 1.21.10 Mojmap | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| 1.21.11 Mojmap | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |

## Corrected exact order

For an already-active, dry, ordinary local player without abilities flight, the path is:

1. `LivingEntity.aiStep()` calls `updateFallFlying()` while `isFallFlying()` is true, before travel input is built and `travel()` is called (1.21.10 `LivingEntity.java:2835–2849`; 1.21.11 `LivingEntity.java:2961–2975`). `updateFallFlying()` calls `checkFallDistanceAccumulation()` first; `Entity.java:2747–2751` in .10 and `2769–2773` in .11 cap `fallDistance` to `1.0` only when `deltaMovement.y() > -0.5` and current fall distance is above `1.0`. Its following `canGlide()` cancellation is inside `!level().isClientSide()` (`LivingEntity.java:2901–2921` and `3027–3047` respectively), so this local-client update does not clear the active flag.
2. The separate `aiStep()` effect check calls `resetFallDistance()` for Slow Falling or Levitation before `travel()` (.10 `LivingEntity.java:2839–2848`; .11 `2965–2974`). If neither effect is present, that check does not reset it. The climbable-air helper has its own earlier reset described below.
3. In .10, `travelFallFlying()` selects its `onClimbable()` arm and calls `travelInAir(input)` before `stopFallFlying()` (`LivingEntity.java:2383–2387`). `travelInAir()` calls private `handleRelativeFrictionAndCalculateMovement()` at line 2310. That helper calls `moveRelative`, applies `handleOnClimbable`, then explicitly calls `this.move(MoverType.SELF, this.getDeltaMovement())` in that order (`LivingEntity.java:2476–2485`). `moveRelative` only adds input to delta movement (`Entity.java:1590–1593`). For the true climbable predicate, `handleOnClimbable` calls `resetFallDistance()` before the move and applies the historical velocity clamps (`LivingEntity.java:2503–2518`).
4. The call to `this.move()` is virtual. The exact `LocalPlayer` override is `LocalPlayer.java:906–914` (.10) / `947–955` (.11): it captures old X/Z, calls `super.move(...)`, then performs auto-jump and walked-distance updates. There is no `move(MoverType, Vec3)` override in `Player` or `AbstractClientPlayer` in either exact source tree, so this `super.move()` resolves to `Entity.move()`. In the ordinary non-`noPhysics`, non-piston path, `Entity.move()` applies collision, records movement and sets position (`Entity.java:670–714` in .10; `685–729` in .11). Its own fall-distance reset is conditional on nonzero accumulated fall distance, movement length squared at least `1.0`, and a non-miss fall-damage-resetting clip (.10 lines 699–707; .11 714–722).
5. After that explicit local-player move returns, `handleRelativeFrictionAndCalculateMovement()` can apply its collision/jumping climbable response, returns movement, and `travelInAir()` performs its gravity/friction delta write (`LivingEntity.java:2306–2329` in .10; `2343–2366` in .11). Only after this sequence does `stopFallFlying()` write shared flag 7 `true`, then `false` (.10 `2399–2402`; .11 `2457–2460`). Thus the .10 ordinary-air SELF move is issued while the glide flag is still active; the ordered late toggle follows the helper's move and travel-in-air velocity write.

If the new .11 `onClimbable()` early gate returns false, `travelFallFlying()` instead calls `updateFallFlyingMovement()`, writes that velocity, and issues its explicit SELF move (`LivingEntity.java:2441–2454`); that arm does not call `stopFallFlying()`. The predicate gate remains conditionally supported: for a state that satisfies both the prior climbable predicate and `CAN_GLIDE_THROUGH`, the gate precedes the climbable/trapdoor checks. This addendum does not establish that the supplied ladder/vine witness belongs to `CAN_GLIDE_THROUGH`; witness applicability remains unresolved.

## Bounds and signature

No block-tag inventory, shape claim, runtime trajectory, or full-pair coverage is added. This review only followed the scoped travel call chain, helper movement, local-player virtual dispatch, and fall-distance/flag ordering in the exact local sources.

Signed: `/root/c_glide_12110_12111_actual_climb_exit_audit` — 2026-11-18.