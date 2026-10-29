# Correction supplement 1 — `CAN_GLIDE_THROUGH` movement reachability

This supplement corrects the caller-reachability statement in `closure.md`. It does not replace or modify that report.

## Immutable inputs

- Original report: `.native-source/20261028/frontier-c/12111-2612-glide-tag-consumer-closure/closure.md`
  - SHA-256: `a91a30510983bb500329b3b593cd52e59ce297061a8e9f8b1bf202a3d3fbbff6`
- Allowed pair-audit context: `.native-source/20261028/frontier-c/12111-2612-pair-coverage-audit/audit.md`
  - SHA-256: `24af4f87694b702d8f35b362744cd7eb1434934a8a1d5674180187eae2731a33`
- Canonical endpoint identities and source-manifest hashes remain as stated in `closure.md`.

## Corrected reachability finding

The original report listed `LivingEntity` ordinary-air climb lift, climb movement handling, and jump lift among reachable effects of the false return from `onClimbable()`. That was an overstatement. Those operations are in ordinary travel branches; when the entity is fall-flying, `LivingEntity.travel` dispatches to `travelFallFlying`, so those ordinary-air/water/climb branches are not reached as a consequence of `CAN_GLIDE_THROUGH` returning false during fall flight. Remove those claimed movement effects from this tag closure.

A positive local-player movement path remains, conditional on effective tag membership and state:

- `LocalPlayer` inherits `Player.onClimbable()`; its wrapper returns false only for creative ability flight, otherwise it delegates to `LivingEntity.onClimbable()`.
- In fall-flying travel, `LivingEntity.travelFallFlying` tests `onClimbable()`. A true result calls `travelInAir(input)` and then `stopFallFlying()` (1.21.11 Mojmap `LivingEntity.java:2441–2445`; 26.1.2 unobfuscated `LivingEntity.java:2528–2532`). The shared predicate returns false when the entity is fall-flying and its in-block state is in `CAN_GLIDE_THROUGH` (old `LivingEntity.java:1675`; new `:1695`). Thus, for a tagged in-block state, the client local player remains on the fall-flying travel branch instead of taking the ordinary-air fallback and stopping flight. This is a source-confirmed conditional movement dependency in both endpoints; no pair delta is found in these operations.
- The `LocalPlayer.aiStep` attempt-to-start-gliding condition also calls `onClimbable()` (old `LocalPlayer.java:815–816`; new `:854–855`), but the `CAN_GLIDE_THROUGH` condition is gated by `isFallFlying()`. On this attempt path before flight starts, the tag does not decide the predicate. It is not a separate positive tag-triggered path.

The report's other listed `onClimbable()` consumers, including `isPushable()`, are not expanded here into additional positive movement claims. This correction is limited to the fall-flying travel path requested and does not reopen or duplicate the already-covered entity-push slice.

## Remaining limits

The built-in source provider declares the same default entries in both endpoints, but effective loaded tag membership is still unresolved: no generated payload, datapack merge result, or client reload result is bound by these sources. Accordingly, the movement dependency is conditional; no particular runtime block or trajectory is asserted. Server producer timing remains unresolved and was not used. This correction does not close the broader source/resource pair audit or establish runtime parity.
