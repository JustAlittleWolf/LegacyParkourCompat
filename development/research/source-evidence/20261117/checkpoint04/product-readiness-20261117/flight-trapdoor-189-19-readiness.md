# Product readiness: accepted 1.8.9→1.9 flight-input and trapdoor-climbing operations

Read-only correspondence only. Flight input is a concrete product gap for 1.9 selections; trapdoor climbing is covered for the bounded claims. No runtime-parity or full-pair claim.

## Immutable accepted inputs

Flight input:
- Candidate: `build/source-189-19-20261117/input-travel/sneaking-flight-input-rescaling-r2.md` — SHA-256 `4116f0bcbeab6343c9cb8df34a6de335afae7cb3cc897da60e5e23b130e008d2`.
- Independent ACCEPT: `build/source-189-19-20261117/audit-flight-r2/verdict.md` — SHA-256 `654254F567817B3667442A013A7A447564EEDFAD6B17CDDD67D5A2EA2D0DBB71`.
- Existing canonical finding: `development/research/findings/1.8.9--1.9.4/flight-sneak-input-rescaling.md`; not edited.

Trapdoor climbing:
- Candidate: `build/source-189-19-20261117/collision-effects/trapdoor-ladder-climbing.md` — SHA-256 `C2986394FAAFE72C02684CADA9987767E0301F1C849B96B2C1E8414C47A7170C`.
- Independent ACCEPT: `build/source-189-19-20261117/audit-trapdoor/verdict.md` — SHA-256 `C08DBC8BBE742CB2C45DCBA8011849AB69A90CB41E321FD1EAA22A3D7AF63781`.
- Existing canonical finding: `development/research/findings/1.8.9--1.9.4/trapdoor-ladder-climbing.md`; not edited.

## Selected-profile mapping

`ParkourVersion` aliases are V1_8_9 = 1.8.9; V1_9 = 1.9, 1.9.1, 1.9.2; V1_9_3 = 1.9.3, 1.9.4. Resolver chooses the closest provider annotated at or after the selected profile, independently per hook interface.

## Flight/sneak input rescaling — GAP for V1_9 and V1_9_3

`FlightSneakCompensationV1_14_2` is the sole `FlightSneakInputHook` provider and is annotated V1_14_2. It divides stored X/Z input by 0.3 for selected versions at or after V1_9, so it would express the accepted 1.9 operation. However, the only call site in `LocalPlayerMixin` invokes `FlightSneakInputHook` inside `controlled camera && shift held && legacyparkourcompat$inputSlowdown != null`. That field is populated from the sole `InputSlowdownHook` provider only when `change.supports(player, selected)` is true. The interface default support window starts at V1_14 and ends before V1_15; thus for V1_9 and V1_9_3 the field is null and the flight compensation provider is never called. Result: selected V1_9 and V1_9_3 retain native current input without the accepted 1.9 divide-by-0.3 operation. This is a bounded product gap. The accepted item-use ordering remains part of the source claim; no separate item-use behavior is asserted here.

For selected V1_8_9, the source requires no horizontal divide in this flight branch. The later compensation provider is not reached because the same input-slowdown support gate is false, preserving the no-op behavior for this accepted operation.

## Open trapdoor over matching-facing ladder — COVERED_NO_CODE

`OpenTrapdoorClimbing` is annotated V1_8_9 and returns false from `TrapdoorClimbingHook`, suppressing the modern native open-trapdoor predicate for selected V1_8_9. `TrapdoorClimbingNativeFence` is annotated V1_8_5 and returns the native result, but it is older than selected V1_8_9 and therefore does not override the V1_8_9 provider. For selected V1_9 and V1_9_3, both providers are older and no later `TrapdoorClimbingHook` provider exists; native fallback remains active. The current `LivingEntityClimbingMixin` wraps `LivingEntity.trapdoorUsableAsLadder(BlockPos, BlockState)` in `onClimbable`, so native behavior accepts the same open-trapdoor/same-facing-ladder condition described by the accepted 1.9 source. Thus V1_8_9 suppresses the later behavior and V1_9/V1_9_3 retain it through native fallback.

No product files or Git state were changed for this check.