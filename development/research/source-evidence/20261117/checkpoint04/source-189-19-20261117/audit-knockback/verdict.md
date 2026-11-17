# Independent source audit: knockback response

Verdict: **ACCEPT**

## Reviewed identities

- Immutable candidate `build/source-189-19-20261117/tick-state/knockback-response.md`, SHA-256 `1288399db66cec306eeaecf20e58a06052ded76e8014d0411753b58da96c774b`.
- 1.8.9 Ornithe Feather: source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 Ornithe Feather: mapping-local provenance SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Audited `LivingEntity.java` hashes: 1.8.9 `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`; 1.9 `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb`.

## Evidence and verdict basis

The candidate accurately isolates the vertical knockback gate and math. In 1.8.9 `LivingEntity.takeDamage` calls the victim's `applyKnockback` for a non-null attacker when its accepted-hit flag `bl` is true (lines 641-654, 656-694; call at 681-690). `applyKnockback` passes the resistance random gate, halves X/Y/Z, subtracts normalized horizontal direction times fixed `0.4F`, adds `0.4F` to Y and caps Y at `0.4F`; it does not read `onGround` and does not use its `amount` parameter (lines 763-778).

In 1.9 `takeDamage` calls the victim's `applyKnockback` when `bl2` is true and an attacker exists (lines 709-723, 725-770; call at 757-766). On the ordinary non-shield route this supplies `0.4F`. `applyKnockback` passes the same form of resistance random gate, always halves/adjusts X and Z using the supplied amount, and halves/adds/caps Y only under `if (this.onGround)` (lines 863-879). Therefore an airborne victim gets no Y mutation from this 1.9 response; a grounded victim gets the half, add-amount, cap sequence. For the ordinary route, this preserves a 0.4F addition, while 1.8.9 always adds 0.4F independent of ground state.

A `PlayerEntity` inherits this `LivingEntity` response, so an accepted entity hit with a non-null attacker can directly change player velocity. The call is server-side: both `takeDamage` methods return false on `world.isClient` (1.8.9 lines 621-623; 1.9 lines 675-677). Both implementations set `velocityDirty` when the resistance gate passes. `TrackedEntity` tracks dirty velocity and sends `EntityVelocityS2CPacket` (1.8.9 lines 152-204; 1.9 lines 159-215). The client handler consumes that packet through `entity.lerpVelocity(packet.getVelocityX()/8000.0, packet.getVelocityY()/8000.0, packet.getVelocityZ()/8000.0)` (1.8.9 `ClientPlayNetworkHandler.java` lines 402-407; 1.9 lines 434-439), which is the relevant client player velocity input to subsequent movement.

## Applicability and remaining gates

The finding applies to the 1.8.9 and 1.9 endpoints only; these endpoints do not establish the exact intermediate patch boundary. The victim must take a route that reaches the `takeDamage` knockback branch with a non-null attacker (`bl` in 1.8.9; `bl2` in 1.9). Knockback is skipped when `random.nextDouble() < KNOCKBACK_RESISTANCE`; successful mutation marks velocity dirty for tracking. Client-visible movement also depends on the authoritative velocity update being sent and consumed. Damage calculation, attacker eligibility and the event's broader resolution remain outside this finding. No product implementation, tests or runtime were inspected.