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