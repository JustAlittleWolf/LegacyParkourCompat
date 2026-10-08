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