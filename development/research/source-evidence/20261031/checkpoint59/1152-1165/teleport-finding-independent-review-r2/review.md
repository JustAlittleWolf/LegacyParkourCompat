# Independent source review: client teleport near-coordinate fallback

Verdict: **ACCEPTED** for the exact final draft SHA-256 `F700013A645964DA3D9477B4E6D165B59E9F9069F50CDF65F464EF9D1B0EFD47` at `.native-source/20261030/1152-1165-finish43/canonical-findings/1.16.1--1.16.2/client-teleport-near-coordinate-fallback.md`.

The reviewed boundary erratum is SHA-256 `860C0BAF2A748E874A221DFE853B24E1F9C6916F375F6A1A06261A47FDF4280B`. The older boundary scan SHA-256 `B735812955161438D8C988DBDA8D8E8F3D18FB5E43F18DBB65F45792B15602BC` contains false absence/provenance claims; the final draft identifies that error and cites the correction.

## Source checks

- Exact Mojmap `ClientPacketListener.java` bytes and the `mojmap.sources.sha256` entry agree for 1.16.1 (`466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d`) and 1.16.2 (`4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9`). Each `mojmap.ready.json` names the corresponding exact version and Mojmap mapping; its source-manifest digest matches the actual manifest. I also checked the source, source manifest, ready record, and provenance record for exact ID `1.16` and 1.16.1/1.16.2. The `1.16` folder is the exact release ID; it is not a missing `1.16.0` source. Its handler and 1.16.1 retain the fallback; 1.16.2 removes it. The boundary erratum is correct.
- In 1.16.1, the handler enters the `!entity.isControlledByLocalInstance()` branch, then uses three negated `>=` checks against X/Z `0.03125` and Y `0.015625`; the current-position target is used when all raw `>=` checks are false. In 1.16.2 the same gated branch passes the packet coordinates unconditionally. `setPacketCoordinates` precedes this branch in both versions.
- `LocalPlayer` inherits through `AbstractClientPlayer` and `Player` from `LivingEntity`; none of those intervening classes overrides `lerpTo`. `LivingEntity.lerpTo` therefore receives the virtual call and assigns `lerpX/Y/Z` and `lerpSteps` without immediately calling `setPos`. `Entity.getControllingPassenger()` returns null, and the inherited `isControlledByLocalInstance()` uses `!level.isClientSide` when there is no controlling passenger, so the registered LocalPlayer on the client takes the handler branch described by the draft.
- The later `LivingEntity.aiStep` interpolation is gated by `lerpSteps > 0` and advances position via `setPos`. LocalPlayer's `aiStep` calls `super.aiStep` in both reviewed releases. The draft appropriately qualifies the eventual positional consequence on that interpolation step occurring and does not assert tick order or runtime parity.
- The finite-coordinate condition is sufficient: with at least one nonzero coordinate delta and every absolute delta below its threshold, the old target is the current position and the new target is the packet position; zero deltas yield the same target. The negated comparisons also preserve the source's NaN comparison behavior.
- The final draft's 1.16.1 `LivingEntity.java` hash is now correct (`b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` as checked from the file and source manifest); 1.16.2's listed hash `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` also matches. The preceding draft had accidentally listed the 1.15.2 LivingEntity hash; that source-reference error was corrected before this verdict.

## Wording note

For maximum boolean clarity, “If all three comparisons are false” could say “If each raw absolute-delta `>=` comparison is false.” The next sentence correctly states the finite-value condition (all deltas strictly below threshold), so this is a nonblocking wording clarification and does not change the accepted claim.

No source/ref error remains in the reviewed revision. This is a source finding review only; it does not establish full-pair coverage or runtime parity.
