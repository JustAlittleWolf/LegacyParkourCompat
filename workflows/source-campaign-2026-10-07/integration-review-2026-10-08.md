# Integration review — 2026-10-08

Integration branch: `feat/movement-campaign-integration-2026-10-08`, based at `958b0916f08cba25e3be86d67d48a9d1e8651d1f`.

## Accepted and integrated

- **TICK-01 sprint timeout** — ACCEPT. Source review: `fddb263`, section “TICK-01 sprint timeout — ACCEPT”. Code commit: `3c531180287b3cecff49e557083db73352b258f7`, integrated as `3699009`. Moving the callback to `LocalPlayer.aiStep` HEAD places the 600-tick stop before local sprint eligibility and travel, matching the 1.8.9 timeout phase. First changed release remains unknown within `(1.8.9, 1.9.4]`. Runtime parity is unverified.

## Held for correction and renewed review

- **Boat passenger yaw refresh** — REQUEST CHANGES in `fddb263`. The implementation must restore all historical yaw writes and restrict the path to direct player mount transitions; indirect/nested passengers do not run the old local-player mount callback. The current code and its direct-passenger interim edit were reverted from the active diff.
- **1.13 pose resize fit** — REQUEST CHANGES in `fddb263`. Compare requested float dimensions to the entity's float width/height using historical comparison semantics, not double AABB extents. The implementation was reverted from the active diff.
- **Elytra jump start** — REQUEST CHANGES in `fddb263`. The exact comparator correction `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` correctly preserves strict `y < 0.0` including NaN, but the implementation still delegates to the newer helper and sits under newer caller gates. It needs a hook that can reproduce the accepted 1.14.4 eligibility/request behavior. The implementation and comparator fix were reverted from the active diff.

## Verification

No tests, game clients, TAS, Gym/server, Docker, or runtime simulations were run.

## Build checkpoint

- Accepted batch: TICK-01 only.
- Command: `gradlew.bat build -x test --init-script build/no-tests.init.gradle`.
- Result: `BUILD SUCCESSFUL`; 18 actionable tasks executed. The init script disabled every Gradle `Test` task type across all projects; `-x test` also excluded the root test task. No test task executed.
- JAR: `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `1C3198B1E05AC5FD4CE4D84683060576CA34ED636CA81E88728FF9F6A07BCBD1`.
- Build verifies compilation/packaging only; runtime movement and Mixin application remain unverified.
## Passenger yaw re-review — 2026-10-08

This update supersedes the earlier REQUEST CHANGES disposition for the first passenger-yaw patch.

- Independent review commit 7c40951938a925fbb6fae541a95b462006113c09 accepted corrected code tip 32c8b0a7e089e32d2b755a513241ac0e58d1d562; correction commit 5c2cc08f6b8eaccb02f89a80de4823a33ac37503.
- The accepted wrapper records only a successful direct local-player remount to the captured boat. It preserves the pre-packet indirect-membership condition for nested-to-direct changes and excludes nested-to-nested, failed, removed, initial-mount, and CURRENT cases.
- Base main before integration: ea812dc9171fdf41e02b3a2292db7d40b105c9c4. Net patch: 7 files, 193 insertions. The earlier TICK-01 remains integrated.
- Test-disabled build succeeded: 18 tasks (5 executed, 13 up-to-date), with all Gradle Test task types disabled and -x test. No tests or runtime programs ran.
- JAR SHA-256: 0EFADAB8C4FC87ABE8137C3E1F3E6A57CBAC15BB470E1048B739422839A7CB93.
## 1.18.2 ascending sneak edge — 2026-10-08

- Independent exact-code review: `c24391cc31a774da64b5d3e6d319a56a4a01e9a3`, ACCEPT for code commit `14a6e23432ab6a885b5c5ce265cf2404283756f6`; report: `workflows/implementation-reviews/2026-10-08-ascending-sneak-edge-implementation-review.md`.
- Source finding F-002 snapshot: `1d5f18176eccc5103c08bd1807b2f2c32f2b3376`; finding SHA-256 `4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9`; blind acceptance `0648b843fecf2358165a7c387cf348b02c66396b`.
- Integrated on current main base `6e0803b3fb17eeb8a3a9861ac2638828ab3200ea`. Net incoming patch is limited to shared X/Z backoff factoring, the 1.18.2 historical hook/provider, and the Player mixin dispatch. The existing passenger-yaw path remains present and registered.
- Test-disabled build succeeded with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; all Gradle `Test` task types were disabled. 18 actionable tasks: 3 executed, 15 up-to-date. No tests or runtime programs ran.
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `193877F2902C2D375A2F3F1C7439EDD5CAFD92F884ED3134C24747261689A1A0`.
- Static compilation/packaging only. Runtime movement parity and Mixin application remain unverified. No source pair is completed by this integration.
