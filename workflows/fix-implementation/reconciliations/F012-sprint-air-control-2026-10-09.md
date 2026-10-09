# F-012 sprint air-control implementation reconciliation

- Discovery finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md`.
- Immutable accepted author snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, finding blob `947ca63e44ec3f8ecaa710f4168c95bf66e5402a`, raw SHA-256 `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff`.
- Independent blind source acceptance: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; F-012 is bounded ACCEPT for the 1.17.1 to 1.18.2 coefficient difference and consumer, without a first-release boundary.
- Independent bounded source-boundary acceptance: commit `ee586d98cbd02df932f6f076daa56a9d2176557b`, `workflows/source-boundary-reviews/2026-10-09-F012-independent-boundary-review.md`, blob `7a8315f0e5f253ef8c02a7e373524b3f8859080b`. It accepts the coefficient and consumer claim for the four sampled releases only; it expressly makes no claim about unexamined releases.
- Pair discovery status: partial; runtime validation not performed.
- Code base: `cb11257e593987c68be98a7dbda8bbea7d5e77fd`; current target `minecraft_version=26.2`, ready unobfuscated sources under `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`.
- Implementation commit: `e386e31f7b55d584461469f20752eefd5bfde955`; new class blob `a42b6a0b71ea176c8fb8f93def5b2dd1d1de44d9`; catalog blob `b26ec31d09a40c9f970659676904b6b198f02a6a`.
- Implementation status: **implemented for the independently accepted four-release evidence boundary; implementation review ACCEPT; campaign-owner build pending.** No tests, build, game, TAS, Gym, server, Docker, or runtime validation were performed.

## Existing mechanism and static trace

The accepted-finding backlog records F-012 as source ACCEPT, but no F-012 implementation proof or reconciliation exists in `workflows/fix-implementation/`. A pre-central-catalog implementation commit `cefcc1ccb4c8c6fac5ce18b2c4d4d499b0a630bd` introduced the delayed sprint air-speed mechanism with separate double/float variants. The current static catalog implementation is the code being reconciled here; the older commit is history, not proof that current routing remains correct. The existing current mechanism is preserved unchanged.

Relevant current code blobs at the base commit:

- `src/main/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java` — `c036a3499741474fbd517e457c583e1659abc938`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/AirSpeed.java` — `78bca57bab35fcfa71d5ed9487bc2d3bd047f9be`.
- `src/main/java/me/wolfii/legacyparkourcompat/change/MovementChangeCatalog.java` — `051a0f94cdfc9aeebd86174e7e645f9140fbd681`.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/ChangeResolver.java` — `9a6a57efd94aad45c2cd62ff452650528664577c`.
- `src/main/java/me/wolfii/legacyparkourcompat/mechanic/MovementRuntime.java` — `3911ac5967890a166f23198eb00e39da5213022f`.
- `src/main/java/me/wolfii/legacyparkourcompat/impl/MovementControllerImpl.java` — `0279e86a7350575e8a8d0124bdf9c630ed04972c`.
- `src/main/java/me/wolfii/legacyparkourcompat/api/ParkourVersion.java` — `955e535e08ba4c4896b44601cd9b40714f7c15354b7321047a281ea4af47960e`.
- `src/main/java/me/wolfii/legacyparkourcompat/api/ActiveMovementProfile.java` — `7099d545c0d381aed8bdc6ff66007ff321afaf84`.

`PlayerMixin` stores an `AirSpeedUpdateBehavior` result at the return from `Avatar.aiStep()` inside `Player.aiStep()V`, and injects at RETURN of `Player.getFlyingSpeed()F`. It changes the target result only when `MovementRuntime.find(AirSpeedBehavior.class, player)` resolves. `AirSpeed.speed` preserves vanilla for ability flight while not a passenger, otherwise returns the stored historical value. `AirSpeed.afterAiStep` resets to `0.02F` and, when sprinting, adds float literal `0.006F`.

The original catalog registered both `AirSpeedBehavior` and `AirSpeedUpdateBehavior` only at `V1_18_2`, causing its float expression to resolve for the sampled older profiles too. Following independent acceptance of the four-release source boundary, `change/v1_18/DoubleSprintAirSpeed.java` now supplies the older operation and `MovementChangeCatalog.registerV1_18` registers that one instance for both interfaces. The 1.18.2 class and registrations remain unchanged.

The old sampled operation is preserved explicitly: start with `0.02F`, and only when sprinting assign `(float) (speed + 0.005999999865889549)`. The unsuffixed decimal is a double, so the float value is promoted for double addition, then cast back to float. No compound float addition or algebraic rewrite is used. Both implementations retain the existing `speed` gate: return vanilla speed for ability flight when not a passenger; otherwise return the delayed stored coefficient. The `afterAiStep` hooks reset the coefficient to `0.02F` and only add the sprint increment when `player.isSprinting()`.

`ChangeResolver` in `impl/ChangeResolver.java` returns no changes for CURRENT. For historical selections it considers registrations whose emulated version is at least the selection, then chooses the closest qualifying version. The resulting AirSpeed routes are:

| Selected profile | Resolved registration | Source evidence status |
|---|---|---|
| 1.17.1 (`V1_17_1`) | V1_18 double-literal behavior | Exact source accepted |
| 1.18 / 1.18.1 (`V1_18`) | V1_18 double-literal behavior | Exact sources accepted |
| 1.18.2 (`V1_18_2`) | V1_18_2 float behavior | Exact source accepted |
| CURRENT / native | no historical behavior | Native mixin fallback |

This registration also mechanically supplies the V1_18 behavior to any older selectable profile that lacks a closer AirSpeed registration. The source review explicitly did not establish coefficient fidelity outside the four sampled releases; no claim is made here that the AirSpeed operation is historically faithful for profiles earlier than 1.17.1. Adding a special profile gate or changing version resolution to infer otherwise would exceed the evidence and requested minimal delta. The bounded evidence and the resolver's wider mechanically affected range remain distinct facts; broader source coverage is still unresolved.

`MovementRuntime.find` returns empty unless the entity is a player and its selected profile is enabled. Selecting CURRENT or the running version disables historical changes; with no resolved behavior, the injected methods leave vanilla values unchanged. The target 26.2 ready source confirms `LivingEntity#getFrictionInfluencedSpeed` delegates airborne speed to `getFlyingSpeed()` and `Player#getFlyingSpeed` supplies the native sprint/flight alternatives. Static target Player.java SHA-256 is `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; target LivingEntity.java SHA-256 is `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. The exact 26.2 ready-marker, source-manifest and artifact-manifest hashes are `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` and `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.

## Next action and checks

1. Independent read-only implementation review by a separate worker ACCEPTED commits `e386e31f7b55d584461469f20752eefd5bfde955` and `cc3ee3c8f3239e894923a1f23810c3a9d3130d31`, with no implementation or proof defects. The reviewer confirmed the expression/cast order, both catalog hooks, closest resolver selection, unchanged V1_18_2 implementation, flight/passenger gate, CURRENT fallback, and the explicit limit on unexamined older profiles.
2. The campaign build owner may now compile with every Gradle Test task disabled and `-x test`, per the repository verification protocol. No test, runtime, client, server, TAS, Gym, or Docker run belongs to this checkpoint.

Static verification performed: inspected the exact registration and resolver code paths; confirmed the incoming `main` commits changed no `src` paths before merging `main` into the task branch; `git diff --check` passed after the implementation edits. No build or tests were run.

Static checks only: immutable input identity, ready source identities, four-release source comparison, current hook/catalog/resolver/toggle and target fallback. No implementation-derived result was sent to source-only owners.
