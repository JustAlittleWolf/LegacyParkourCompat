# Product integration receipt: early oak-boat rider

## Immutable product and build identities

- Clean product base: current primary `main` `aae9c86464638cb5ed7faa535c92569b89484906`.
- Source-evidence merge: `1aae7a9f16eb4ed5160477d0ff8f3d96eadb3f98`, with common ancestor `3719d09435e7014e38fa072bf75c1ffe08daccfa`.
- Integrated code commit: `6dc71f6cf199bbc6fcaba160f500be3cfe82cb27` (follow-up type cleanup: `3e9ba0f4eca81e104c8b105a6eff4a549e388111`, `c5da0405b978a22fd93c0c060e244f7c79fe8e40`).
- Compiled code commit: `c5da0405b978a22fd93c0c060e244f7c79fe8e40`.
- Compiled Git tree: `40b35e4c0258c3143813793cc7c039bb9db3464b`.
- Code paths: `change/v1_8/BoatRiderPlacement.java`, `change/v1_8_9/BoatRiderPlacement.java`, `change/v1_8_9/BoatRiderOrientation.java`, shared `change/boat/` witness/seat helper, `BoatRiderOrientationHook`, and `AbstractBoatPassengerOrientationMixin` registration.
- The implementation reuses existing `PlayerPassengerPlacementHook` and `EntityPassengerPlacementMixin`. Candidate-only history is not included in product ancestry.

Build command on the compiled tree:

```text
gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` on commit `c5da0405b978a22fd93c0c060e244f7c79fe8e40` and tree `40b35e4c0258c3143813793cc7c039bb9db3464b`; 19 actionable tasks, 3 executed and 16 up-to-date. The init script disables every Gradle `Test` task and `-x test` excludes the test task. The compiled orientation mixin class SHA-256 is `1E09198984FC78F2B0B60CA8F46CE7132577A90A8D41E3FB1C6C7ABF1458AA9D`; the 1.8 and 1.8.9 placement provider class hashes are `B2996356997B97ACB1F66817A4F18E58E0B0D885DB72D8E6B5C53791D5910B77` and `BE383F4202870CEDE49A537641437554AA104CC342ED65BE8C6005E59E670586`. No tests, runtime, game, TAS, Gym, server or Docker launch was performed. Runtime parity remains unverified.

## Accepted source evidence

The byte-bound source comparisons and independent verdicts are in the current-main ancestry and the exact evidence commit above.

| Evidence slice | Comparison SHA-256 | Verdict SHA-256 | Accepted scope |
|---|---|---|---|
| 1.8.9–1.9.4 boat rider transform | `2e120c8d3517ad4d229a82464397623cc7ae5601a3e9a77196c2a903adb4e3b4` | `b10c9857e6689e8bc95ea4f255911bcc317f175611c13dd688d3aae2260484fd` | The bounded local sole-rider position and orientation change at the 1.9 boundary. |
| 1.8–1.8.9 boat placement alias | `984e133434562f5b3c7f063c0684dfe9bee022b112313dc02b7762af59799314` | `3b79bdd8bd7b45cfcc9acdc82de03e02d9fcb33cbc00a59e99ae759250672f8d` | The placement body is stable through 1.8.9; only the mount-height constant changes from float-rounded `-0.3F` in 1.8 to double `-0.3` beginning in 1.8.1. The callback writes position only. |

## Integrated operation and limits

For an exact vanilla `minecraft:oak_boat` and the sole direct exact-class client `LocalPlayer` passenger, the 1.8 provider preserves the promoted float `-0.3F` mount-height term. The 1.8.9 provider uses the double `-0.3` term supported by the 1.8.1–1.8.9 source alias audit. Both retain the source order `boatY + mountHeight + -0.35` and the yaw-derived X/Z offsets. Other boat types, subclasses, passengers, remote players and server execution retain native placement.

The separate orientation hook only matches the same oak-boat rider witness. On the 26.2 `AbstractBoat.positionRider` `CAN_TURN_IN_BOATS` check, it returns true for that historical local rider, so the native `if (!passenger.is(CAN_TURN_IN_BOATS))` yaw/head-yaw adjustments and clamp are skipped. Other riders use the native tag query. There is no orientation provider at 1.9 or later, so resolver absence keeps the native operation. The placement hook's existing `V1_19_4` native provider bounds the earlier operation before its existing `V1_20` implementation.

## Intended central finding association

- Associate this bounded 1.8–1.8.9 placement implementation with `development/research/findings/1.8.9--1.9.4/boat-rider-transform.md`.
- Keep boat motion, steering, collision, and non-player passenger behavior outside this association. Compilation establishes no runtime parity.

This receipt records product integration and build identity only; source acceptance remains bound to the comparison and verdict identities above.
