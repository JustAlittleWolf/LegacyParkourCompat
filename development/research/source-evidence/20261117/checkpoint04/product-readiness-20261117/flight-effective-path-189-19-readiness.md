# Corrected product readiness: 1.8.9→1.9 flight/sneak input rescaling

Status: COVERED_NO_CODE for the bounded accepted operation. This report corrects the earlier incomplete readiness note `build/product-readiness-20261117/flight-trapdoor-189-19-readiness.md` (SHA-256 `ED2A446CF1E9DCD3D22804BC2B814F78007AE6E22DAF25F9E04B6B13E45B52CC`), which omitted the V1_13_1 input-slowdown provider from the effective resolver path. This is a source/product correspondence result, not runtime parity or full-pair coverage.

## Immutable source inputs

- Candidate: `build/source-189-19-20261117/input-travel/sneaking-flight-input-rescaling-r2.md` — SHA-256 `4116f0bcbeab6343c9cb8df34a6de335afae7cb3cc897da60e5e23b130e008d2`.
- Independent ACCEPT: `build/source-189-19-20261117/audit-flight-r2/verdict.md` — SHA-256 `654254F567817B3667442A013A7A447564EEDFAD6B17CDDD67D5A2EA2D0DBB71`.
- Existing canonical finding `development/research/findings/1.8.9--1.9.4/flight-sneak-input-rescaling.md`; not edited.

## Effective selected-profile path

Aliases are V1_8_9 = 1.8.9; V1_9 = 1.9/.1/.2; V1_9_3 = 1.9.3/.4. Resolver choice is independent per hook interface and selects the closest annotation at or after the selected profile.

For V1_9 and V1_9_3, `InputSlowdownV1_13 @V1_13_1` is the closest applicable `InputSlowdownHook` provider; its `supports` override admits selections from V1_8 through V1_13_1. The later V1_14_1 provider is not selected for these profiles. At the wrapped `ClientInput.tick()` point, `LocalPlayerMixin` reconstructs signed horizontal impulses from the polled key booleans and calls `InputSlowdownHook.keyboardInput`. The V1_13_1 provider applies the sneak multiplier as `(float)(impulse * 0.3)` when shift is held. The shared keyboard-input method then applies the item-use `0.2F` multiplier when using an item and not a passenger.

The flight compensation hook resolves to `FlightSneakCompensationV1_14_2 @V1_14_2` for both 1.9 alias groups. The existing `LocalPlayerMixin` call is guarded by controlled-camera, shift-key, and non-null supported input-slowdown state; those gates are satisfied by the V1_13_1 provider for these selected profiles. `FlightSneakCompensationV1_14_2.compensate` divides both stored axes by double literal `0.3` and casts to float for selections at or after V1_9. The later `modifyInput` return hook then sends the stored vector through `InputSlowdownHook.travelInput`, which applies `0.98F` after flight compensation. Thus the effective order is keyboard sneak `0.3` → item-use `0.2F` when applicable → flying/camera/sneak divide by double `0.3` → `0.98F` travel input, matching the accepted 1.9 operation and preserving the item-use factor.

For V1_8_9, the same V1_13_1 provider supplies the historical keyboard sneak scale, while the future flight compensation provider returns the stored input unchanged because the selected profile is older than V1_9. This preserves the accepted pre-1.9 no-division behavior.

## Bounded disposition

COVERED_NO_CODE for V1_8_9, V1_9 and V1_9_3 aliases under the accepted gates. The earlier gap classification arose from considering only the V1_14_1 provider and missing the nearer V1_13_1 provider. No candidate code is needed. No product files or Git state were changed.