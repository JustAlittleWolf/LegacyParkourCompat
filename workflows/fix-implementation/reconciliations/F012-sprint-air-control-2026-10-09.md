# F-012 sprint air-control implementation reconciliation

- Discovery finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-012-sprint-air-control-float.md`.
- Immutable accepted author snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, finding blob `947ca63e44ec3f8ecaa710f4168c95bf66e5402a`, raw SHA-256 `b089246435bb50d410fe8dbe5502fbcd2245a4aa72f6e896c1040eecb47c6bff`.
- Independent blind source acceptance: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; F-012 is bounded ACCEPT for the 1.17.1 to 1.18.2 coefficient difference and consumer, without a first-release boundary.
- New source-boundary evidence memo: `workflows/source-boundary-reviews/F012-sprint-air-control-boundary-evidence-2026-10-09.md`, blob `ebd4cb218453a9b2ce7afbe15932d2668dc6b742`, raw SHA-256 `477812d4bd08dcb3841b902b311899ee51c2f72d9ad76e323cb4a206dee01a06`. It compares exact 1.17.1, 1.18, 1.18.1 and 1.18.2 ready Mojmap sources and is pending independent source-only acceptance. This implementation reconciliation does not self-accept that boundary.
- Pair discovery status: partial; runtime validation not performed.
- Code base: `cb11257e593987c68be98a7dbda8bbea7d5e77fd`; current target `minecraft_version=26.2`, ready unobfuscated sources under `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`.
- Implementation status: **open / incomplete for the accepted release range; correction gated on independent acceptance of the exact boundary memo.** No Java changes are made in this checkpoint.

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

The single catalog registration for both `AirSpeedBehavior` and `AirSpeedUpdateBehavior` is at `V1_18_2`. There is no V1_18 registration for either interface. `ChangeResolver` yields no changes for CURRENT; otherwise it filters to registrations whose emulated version is at least the selected profile and chooses the closest qualifying registration. Consequently, the current V1_18_2 implementation is selected for V1_17_1, V1_18 (1.18/1.18.1) and V1_18_2 profiles. It uses the 1.18.2 float expression for all of them. The pending four-source comparison finds that V1_17_1, 1.18 and 1.18.1 use the older double-literal expression, while 1.18.2 uses the float expression; this boundary is not accepted yet. If independently accepted, the current class matches the coefficient for 1.18.2 but not the accepted earlier source behavior for V1_17_1 or V1_18 profiles. This is a bounded implementation gap, not a reason to replace the shared delayed-air-speed bridge.

`MovementRuntime.find` returns empty unless the entity is a player and its selected profile is enabled. Selecting CURRENT or the running version disables historical changes; with no resolved behavior, the injected methods leave vanilla values unchanged. The target 26.2 ready source confirms `LivingEntity#getFrictionInfluencedSpeed` delegates airborne speed to `getFlyingSpeed()` and `Player#getFlyingSpeed` supplies the native sprint/flight alternatives. Static target Player.java SHA-256 is `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; target LivingEntity.java SHA-256 is `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. The exact 26.2 ready-marker, source-manifest and artifact-manifest hashes are `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`, `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` and `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.

## Next action and checks

1. Obtain independent source-only acceptance of the boundary evidence memo, or preserve the reviewer’s requested corrections without changing the accepted F-012 snapshot.
2. After acceptance, add the minimal earlier double-literal resolver change for the confirmed V1_17_1/V1_18 applicability while retaining the existing V1_18_2 float implementation and shared hook/state bridge. Then record exact registration, resolver and fallback behavior for independent technical review.
3. Build only after code changes, with all Gradle Test tasks disabled and `-x test`, through the campaign build owner. No test, runtime, client, server, TAS, Gym, Docker, push, or build is authorized/performed in this checkpoint.

Static checks only: immutable input identity, ready source identities, four-release source comparison, current hook/catalog/resolver/toggle and target fallback. No implementation-derived result was sent to source-only owners.
