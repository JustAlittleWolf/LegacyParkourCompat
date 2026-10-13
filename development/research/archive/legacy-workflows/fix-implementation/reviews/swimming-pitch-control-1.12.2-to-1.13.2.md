# Implementation review: swimming pitch control — ACCEPT

## Review identity

- Reviewed implementation commit: `f24fdcd2dc82906f4e111cf804de6bdb199f7f54` (`fix/swimming-pitch-control-1-13`).
- Review branch: `fix/review-swimming-pitch-control-1-13`.
- Accepted source snapshot: `b20730f64ed07090b618f6101467f8bde07e0024`, `workflows/wiki-audit-2026-10-07/swimming-pitch-control-1.12.2-to-1.13.2.md`, SHA-256 `dd0abd5a90ac1813197e4c09ab97ed0e0027c48e237a148bbf95bde2f64e6a89`.
- Wiki-aware review: `c80b31ff94b4264194d2a08d168cca81c45d5f09`, which accepts this bounded finding.
- Current `main` merged before handoff: `6e0803b3fb17eeb8a3a9861ac2638828ab3200ea`; resulting review merge commit: `4a88b8b47122f62fbc79b585f00fc2498cce3dbf`.

## Findings

The implementation reproduces the identified 1.13.2 swimming look-Y input without copying or replacing the native travel loop. In the accepted 1.13.2 `PlayerEntity.moveRelative` body (lines 1442–1465; source SHA-256 `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633`), the value comes from the look vector's Y component. `Entity.getRotationVector` computes it from `pitch * (float)(Math.PI / 180.0)` and `MathHelper.sin(float)`; the table is initialized with `(float)Math.sin(i * Math.PI * 2.0 / 65536.0)`. `LegacyTrig` reproduces that exact table expression, float radians conversion, float multiplication by `10430.378F`, truncating cast, and 16-bit mask. Its returned float is widened to double after negation, as in the old look-vector component.

The native 26.2 `Player.travel` path retains the same swimming and passenger gates, `< -0.2` threshold, `0.085`/`0.06` factors, `<= 0.0`/jump/fluid predicate, fluid lookup position, and `(lookY - movement.y) * multiplier` update order (lines 1402–1422; source SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`). `Entity.calculateViewVector` also converts pitch degrees to radians in float before passing `realXRot` to `Mth.sin(double)` (lines 1966–1977; source SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`). The difference is the table-index multiplication: the old path multiplies in float; 26.2 widens the float radians value and multiplies in double. At `-84.375F`, the old calculation selects index `50177`; the current calculation selects `50176`. The player pitch setters clamp to `[-90, 90]` in both cited source trees, so the witness is in the reachable range. The source hashes match the canonical ready tree.

`PlayerMixin` redirects only the `Player.getLookAngle()` invocation in `Player.travel(Vec3)V`; in the native body it occurs inside the swimming branch. It preserves vanilla X/Z and substitutes only Y when `SwimmingPitchBehavior` resolves. The 1.13 implementation is registered from `change.v1_13.MovementChanges` and annotated `emulates = V1_13`. The resolver excludes it for later selections; `CURRENT` resolves no historical changes. Although a V1_13 delta is eligible as a candidate for older selections, `change.v1_12.SwimmingState` resolves for V1_12 and older and returns false, preventing those profiles from entering the swimming travel branch.

No mechanic-key collision exists with `VerticalPitchGlideLook`: that hook covers fall-flying look behavior under a distinct mechanic key. The new helper's reconstructed lookup table is supported by the exact 1.13.2 `MathHelper` initializer, not by an approximation. The implementation is a narrow delta and does not alter unrelated math hooks.

## Main integration and limits

The intervening `main` commits add the 1.17.1 boat passenger yaw refresh and its documentation. They do not touch `PlayerMixin`, the swimming pitch mechanic, or its resolver behavior. The merge completed cleanly, with no semantic conflict found.

This ACCEPT applies to the reviewed implementation and the bounded `(1.12.2, 1.13.2]` source finding. The original revised Feather-derived JAR equivalence remains unproven as recorded in the source snapshot. No build, tests, game client, TAS, gym, server, or Docker validation was run; runtime validation remains open.
