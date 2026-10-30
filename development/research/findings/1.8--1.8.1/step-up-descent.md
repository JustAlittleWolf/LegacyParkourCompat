# Step-up descent: 1.8 → 1.8.1

## Finding

`Entity.move` changes the second-pass step descent from unary-negated configured `stepHeight` in 1.8 (`-stepHeight`, default `-0.6F`) to unary-negated actual clipped rise in 1.8.1 (`-var33` or `-var81`). The accepted reachable witness selects the second candidate, so the 1.8.1 request is `-var81` (`-0.2F`). Both candidates in both versions execute Up → X → Z; equal horizontal scores select the second candidate. The float rise is negated directly, preserving the source operation and float value.

The bounded ordinary-player setup uses a one-sixteenth carpet at `(1,1,0)`, a ceiling underside at `y=3`, and a full floor whose top is `y=1`. From box `x=[.20,.80], y=[1,2.8], z=[-.59,.01]`, the ordinary pass clips `dx=.25` to `.20` at the carpet and then moves `dz=-.03`, scoring `.0409`. Both raised candidates clip ascent to `.20` at the ceiling, clear the carpet vertically, and complete X/Z for score `.0634`; fallback retains the raised route. At descent the endpoint has `maxZ=-.02`, outside the carpet, and strict intersection omits the floor from the raised query because its top equals query `minY`. Neither descent is clipped: feet end at `.6` in 1.8 and `1.0` in 1.8.1. Thus the 1.8 path penetrates the omitted floor by `.4` for this tick.

This configuration is reachable through ordinary grounded sprint travel: five same-heading moves followed by a yaw turn produce the target horizontal vector `(0.25,-0.03)`; the ground clips the initial downward request (about `-.0784`) to zero and the prior grounded state opens the step path. The approach remains clear of carpet and ceiling. This closes the bounded player-applicability witness; it is source-derived, not runtime parity evidence.

## Source anchors and evidence

- Read-only `Entity.java`: 1.8 source SHA-256 `2be081abe1720f89d7ab90f782b8411e71bc5b8a6b53966b49a2380e3c47ffd9`, `move` at 370 and step candidates/descent/fallback around 459–533; 1.8.1 SHA-256 `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c`, `move` at 375 and selected descents/fallback around 524–538. `Box.java` is identical in both, SHA-256 `2d270470a11082e1cf9f772c94ecff9ecbf5430be78947dd152e2e72c20bbc52`; strict intersection is at 153–159. Carpet shape is set to height `0.0625` in 1.8 `CarpetBlock.java` SHA-256 `75016cb9c1c7e237b2a09833d9ab9169c0a542f849d8f22adaa0e804b820b9cf` and 1.8.1 SHA-256 `991a6b1fce6e593803b73d44668091522551e3ea1443a8ab5d6685fa82b4ce15`.
- Reachability anchors: paired `LivingEntity` input scaling, ground friction, travel and gravity; paired `PlayerEntity` live `getSpeed()` override and sprint modifier; paired `Entity.updateVelocity`; 1.8.1 `LocalClientPlayerEntity.mobTick` sets sprint before `super.mobTick`. Provider and coverage closure is in the hash-bound assigned coverage reports.
- Accepted witness report SHA-256 `8384b9353b020a5a18d1756a9bf2d35eec6778aef292c9264bae5e9f3d65db15`; its axis-order correction SHA-256 `f29bf89898167f20b77fcef7ad160dfbf172cbb04a69856cc18904ed9f050fb5`; independent pair-auditor verdict SHA-256 `51138cc26bc47626d5664abe8885fecafcbdd749e3a320e442e831658585a090`. The rejected earlier witness is not evidence for this finding.

This finding closes only this bounded source difference and applicability witness; it does not assert pair freeze, implementation, or runtime parity.
