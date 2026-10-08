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
## Full 1.14 Elytra start request — 2026-10-08

- Implementation commit `361c15c687a16bbbaaef2525d27a4d2277b33c3a`, reviewed implementation tip `bb8484823980d8f44c33c712b964275917cc93bb`; exact independent review tip `b8dfbc3e4d092141861759cab36272ea223a4d17`, verdict ACCEPT. Review: `workflows/fix-implementation-review-2026-10-08/ELYTRA-START.md` (SHA-256 at review tip: `65d047fb306bdbc7a24c6aa14f91e1a83d4f15c0d9695b8fc8a579ec705e455c`; after this provenance-only correction: `ee9c731188e73b5cba3c4593e2161b2b74681b713077272199708ea06348f38d`).
- Source finding F-ELYTRA-START snapshot `4a0c35f2008d785867c00360a7b72726e3506435`, file `workflows/source-campaign-2026-10-07/1.14.4--1.15.2/findings/F-ELYTRA-START.md`. The raw Git blob is 3,868 bytes, Git blob `066856290714a826dffb44072609114389cf9d8c`, SHA-256 `b6d5091e95e7cc0d35eb696dea6d06f720aaad91540ad88915ddc61dad576e59`. The review report's earlier `content SHA` value was a malformed transcription; blind acceptance `960ce5644ceacd8e27bd3cb9145bd4eaf221379c` records the same correct SHA-256 and Git blob. This is not a source-byte mismatch: the ACCEPT remains bound to the exact accepted snapshot, and the implementation-review report now explicitly documents the correction.
- Integrated against current local main `b0e7300a46aeb3fe4087bfafbcf1bcc49c76542a`. The reviewed change is confined to the 1.14 `LocalPlayer.aiStep` Elytra request gate, its behavior/provider, and the `EntityInvoker` accessor. Passenger yaw and edge backoff hooks are untouched and remain in the tree. Swimming's later `Player.travel` hook is a separate call site.
- Test-disabled build succeeded with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; every Gradle `Test` task type was disabled. 18 actionable tasks: 4 executed, 14 up-to-date; no tests ran.
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `FEA5D9F547EC251FBAFC12C4257F9450167E6376C64153FA8C1BAA029CA3B09C`.
- Runtime movement parity and Mixin application remain unverified; this does not refine the historical release boundary.
## 1.13 swimming pitch control — 2026-10-08

- Code commit `f24fdcd2dc82906f4e111cf804de6bdb199f7f54`; independent review tip `90eaed02a299761f925f54d43a36759f26cdb650`, ACCEPT. Review report `workflows/fix-implementation/reviews/swimming-pitch-control-1.12.2-to-1.13.2.md`, SHA-256 `0ab635172f2058831fc76f7d154f968f8286c05090b637b8d9f534a99cd886d0`.
- Accepted source snapshot `b20730f64ed07090b618f6101467f8bde07e0024`, file `workflows/wiki-audit-2026-10-07/swimming-pitch-control-1.12.2-to-1.13.2.md`, SHA-256 `dd0abd5a90ac1813197e4c09ab97ed0e0027c48e237a148bbf95bde2f64e6a89`; this matches the report.
- Integrated against local main `d39482fcd0e3f82fac21d35207297a41f1dec78a` after the Elytra batch. Net code delta: `SwimmingPitch`, its versioned hook/provider, and one `Player.travel` look-angle redirect. Existing native swimming predicates/math remain in place. Separate from Elytra `LocalPlayer.aiStep`, passenger yaw, edge backoff, and fall-flying look hooks; all remain present.
- Test-disabled build succeeded with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; all Gradle `Test` task types disabled. 18 actionable tasks: 3 executed, 15 up-to-date; no tests ran.
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `C717F4F74B51D5F41A7D6B1A1034683377C6744478B41AC863E30DF9602ADA08`.
- Runtime movement parity and Mixin application remain unverified. The finding bounds the behavior to the reviewed 1.13 endpoint; the pair and exact first-change release remain unresolved.
## 1.8.9 fence arm beside End Portal Frame — 2026-10-08

- Implementation commit `51ecf89cfdb2d077a34a56e7e1c05a9d1236f46d`; handoff tip `44882452607d58457d44e1aa4940f15dca9e72c8`; exact reviewed code files match byte-for-byte at the final review tip.
- Independent implementation review tip `8838f92d46d064a7da314f4d0ca02be9ec3148bc`, ACCEPT. Report `workflows/fix-implementation/reviews/fence-end-portal-frame-1.8.9-to-1.9.4.md`, SHA-256 `bd272c43f137929d2988f6500ad65fdb210ca82dc9fce9024dffc13d070422de`.
- Accepted source snapshot `103786e872f13a44aa0232a562f2eaab8eb5e185`, file `workflows/wiki-audit-2026-10-07/fence-end-portal-frame-1.8.9-to-1.9.4.md`, SHA-256 `e5edb59eaa858f4905ec9f87862344bfbece0fe5e22315e86c531c071e638852`. Provenance re-review `0979bee9878fc1351a03c183a50ff96c28d79e21` ACCEPT, report SHA-256 `20df73aca35e20e16479eea7ca212ac902824ba27eed0042f2246fe1d8251dab`.
- Integrated against current local main `64895d266fcee5da774c3236295ba949045200c7`. Net patch adds the V1_8 `FencePortalFrameConnection` hook and registers exactly the seven 1.8.9 fences. It restores only missing End Portal Frame-side arms while preserving native collision when no such arm is missing; no block states are changed.
- Fence IDs are distinct from existing pane IDs; both register through the existing `BlockCollisionShape` provider. Existing `BlockStateCollisionShapeMixin` dispatch resolves player collision context through `MovementRuntime.playerFrom`, so the hook remains player-only. All previous accepted batches remain intact.
- Test-disabled build succeeded with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; all Gradle `Test` task types were disabled. The confirming run completed with 18 actionable tasks, all up-to-date; no tests ran.
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `91E9C2CA3883EB6F782B871AC9107F21143A8949B03B5094D9C591F40CB874E2`.
- Runtime collision parity remains unverified. Pose, F-005, WORLD03, and swimming-entry remain pending review; no changes to those items are included.
## F002 pose-fit float-state and block-only correction — 2026-10-08

- Corrected code commit `ea62550bbecc8233917fb293dbe3ee627529f621`; focused independent review tip `88792a06e72fb88f8841d8e413cdb6da026248b3`, ACCEPT. Review report `workflows/implementation-reviews/pose-fit-float-correction-r2-review-2026-10-08.md`, SHA-256 `fe2fe9b101818ebb52e9e480cfb3a29aca2958a72cc322db5fedbe110c4df26d`.
- Accepted source F002 snapshot `448934e826fb41266dc79a313ef1187899d76382`, file `workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md`, SHA-256 `baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e`; blind acceptance `10aa24e1114200397ed2d042a33f269113faf179`.
- Integrated against local main `b099aa02d82e57c1672f68375edcbcacdf8e3973`. Corrected V1_13 pose-fit compares retained float width/height, anchors the requested AABB at the current min coordinates, and uses the historical block-only `noBlockCollision(null, requested)` query. V1_8 and V1_12 register false-returning pose markers; V1_13 handles the update. Later/current profiles retain native behavior.
- The current-main fence addition since the reviewer base changes only `FencePortalFrameConnection` and its V1_8 provider. It does not overlap the pose feature files; the 3-way integration preserves both fence registrations and pose behavior. The new `updatePlayerPose` injection is separate from swimming-pitch and sneak-edge `PlayerMixin` hooks; Elytra remains in `LocalPlayer.aiStep`.
- Test-disabled build succeeded with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; all Gradle `Test` task types were disabled. 18 actionable tasks: 3 executed, 15 up-to-date; no tests ran.
- JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`, SHA-256 `59B6910B612092D3CE6566EBEBC2C5B4217BF1375EDDF471CD10210ED24F9805`.
- Runtime collision/pose behavior remains unverified. F-005 is next in the authorized serial queue; WORLD03 and swimming-entry remain pending review.