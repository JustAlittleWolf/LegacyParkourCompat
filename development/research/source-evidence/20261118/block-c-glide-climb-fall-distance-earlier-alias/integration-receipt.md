# Earlier climbable Elytra fall-distance integration receipt

## Product, review, and build identities

- Product branch: `codex/c-glide-climb-earlier-alias`
- Clean corrected product base: `82845bd462dd63c56708f2ff68584a070378fc6f`, with primary `main` ancestor `aae9c86464638cb5ed7faa535c92569b89484906`.
- Integrated code commit: `9c665ab5544e1afedf70c9c8c4db1ed0087245ea`
- Compiled Git tree: `85b4abd3ba717a3d8841d23495f4ee38065ca38a`
- Independent product static review: signed PASS by `c_glide_climb_integration_bound_review` for that exact commit and tree.
- The withdrawn unsafe commit `8dad6ae18d6706e5c964aaed885e26ea59f66969` is not an ancestor.

## Integrated operation and scope

The integration changes only the climbability eligibility predicate in the existing `GlideFallDistance` assignment and `GlideFallDistanceLimiterStage` scheduler. For selected profile groups V1_15 through V1_19, both checks now short-circuit profile eligibility before calling `onClimbable()`. The source-bounded already-active dry Elytra path does not query climbability before its strict incoming-Y `> -0.5` check and `fallDistance = 1.0F` write. Avoiding the modern predicate also prevents its `lastClimbablePos` cache side effect for this historical witness.

The existing scheduler remains the sole owner of the native limiter callback phase. All prior local-client, local-player, active-flight, airborne, not-flying, not-passenger, vanilla chest-Elytra, and equipment gates remain. Other profile predicates, the strict assignment threshold, write value/order, non-climbable behavior, native fallback, hooks, mixins, and travel dispatch remain unchanged. The newly enabled interval is V1_15, V1_15_2, V1_16, V1_16_2, V1_17, V1_17_1, V1_18, and V1_18_1; the existing V1_18_2 and V1_19 groups retain the previously accepted climbable path. Profiles before V1_15 and after V1_19 remain outside the new range.

This receipt covers already-active dry travel only. The accepted 1.15.2 `LocalPlayer.tick()` comparison finds a separate `!onLadder` Elytra start condition; the integration does not alter startup eligibility, same-tick activation, or the `ElytraClimbableStartGate`. It makes no claim about the final tick value, later collision/landing effects, or runtime parity.

## Bound source evidence

- Earlier adjacent Mojmap aliases 1.15→1.16.5, including the bounded already-active subcase correction for 1.15.1→1.15.2: bundle SHA-256 `537840cf3aa497d4eba6b0f37aae0101cc30e50bddf82a36a35c618d73e1f96c`, commit `1422aa70dc505104e95156562052880f3a8d334c`; README SHA-256 `8bc03310f768984af65f651cda9685050653914c211742011513fe97b7b2114a`.
- Exact 1.16.5→1.17 route/report and audit: first artifact commit `9799e43bd1dbf97031f89bc45cdd43e73258ead4`; report SHA-256 `2e9b9df4afc8292c20ad39a485c595f0777a803d2917617e63e19d62e655b684`; verdict SHA-256 `d17ed5786fe0ec305fd5f8709fdf0344b90faa9ebb04913b59474452867d1e35`.
- Exact 1.17→1.17.1 alias: commit `5499504691579996c7d9edddb1079b64b536937b`; report SHA-256 `9203e6324d346f13debb95a34038bb88fcf86941aaa98b1026656539ddfcda36`; verdict SHA-256 `6ebde7ed279d25067680e81a4a8a7e2f17a1efc8946100249c5cc3e8f505834b`.
- Exact 1.17.1→1.18, 1.18→1.18.1, and 1.18.1→1.18.2 aliases: commit `ae77924340bef9a41f76903f61ce533d7602eefd`; README SHA-256 `c681f44022ef6000c724d55a9e978dc51265d656f7a901aa7b601f613e2a3d4e`.
- The existing 1.18.2/1.19.2 climbable applicability comparison and audit remain bound at SHA-256 `7d5f95456c9f1f7d2373b9a08aae27e99d1059efe0c06a679126f4da89aeac21` / `128b50a9f2227f2e568a76e654f34267cc5d15860bfbcc72f4b89afd9934b0d8`. Existing 1.19 alias evidence commit `cf5d89aefd1078e6e71425e1f5b702409c46000a` binds the 1.19/1.19.1/1.19.2 active dry branch; the 1.19→1.19.1 audit separately records the start-gate distinction.
- Stage-source route evidence includes the 1.21.1→1.21.2 active dry cap report/verdict at `c3148cfc94158e786ae6ddf85268df8a995c4ef5c268290633642c2a2664bdd6` / `53e9730787825417192ec341f74fb7ade48c6e5a12687d9adf4b8330ad2a7aa0`; archive commit `8f161c8c0e153b9228ad1e293df28e9c83452e3f`.

Change comments cite Mojmap `LivingEntity.travel(Vec3)` for the release-specific dry travel operation and `LocalPlayer.tick()` for the separate 1.15.2 startup gate. The 1.15.2 audit rejects broad startup no-difference but accepts the already-active travel subcase. Source acceptance, static integration review, compilation, and runtime parity remain separate.

## Test-disabled build

- Exact command: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`
- Result: exit code `0`, `BUILD SUCCESSFUL`; 19 actionable tasks, 3 executed and 16 up-to-date. `compileClientJava` ran. Every Gradle `Test` task was disabled by the init script and `-x test` was supplied.
- The build compiled exact code commit `9c665ab5544e1afedf70c9c8c4db1ed0087245ea` and tree `85b4abd3ba717a3d8841d23495f4ee38065ca38a`.
- Compiled class SHA-256: `v1_19/GlideFallDistance.class` `0900c3bc16da34baaca6fd9813f9e58ec2c1e95700c9a7c87590fbeef22b7edd`; `v1_21/GlideFallDistanceLimiterStage.class` `738e4d3df21af3ae88a9221154f9b4a4d23dd4098c08674592847ce3bf26e6b6`.
- No tests or runtime launches were performed. Compilation and static review do not prove movement parity.

Intended central finding association: `development/research/findings/1.18.2--1.19.4/glide-fall-distance.md`.
