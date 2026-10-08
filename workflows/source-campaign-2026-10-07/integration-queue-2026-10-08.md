# Movement campaign integration queue — 2026-10-08

Integration branch: `feat/movement-campaign-integration-2026-10-08`.
Baseline: `457b350fa5490b90b08de4f97033718c77efec1d` (`feat/movement-completeness-final`; final 2026-10-07 checkpoint). This queue records incremental handoffs from that baseline. Existing code coverage, new implementation, static review, integration/build, and runtime validation are separate statuses.

| Finding/work item | Source evidence / disposition | Baseline or incoming code | Static review | Integration / build | Runtime |
|---|---|---|---|---|---|
| 1.15 sprint reset + water descent | Owner verified both are already present in baseline; bytecode ordinal and `isAffectedByFluids` call/profile fallback traced. No new commit needed. | Existing coverage at baseline; no duplicate patch. | Static binding verified by owner; not runtime proof. | No integration needed; new campaign build pending. | Not performed. |
| F041, 1.19 fall-flying sprint gate | Owner verified existing baseline coverage in `v1_19SprintFallFlyingGate` / `SprintStart`, `v1_19_4SprintStart`, and `LocalPlayerMixin.canStartSprinting`. | Existing coverage at baseline; not a new fix. | Static binding verified by owner. | No integration needed; new campaign build pending. | Not performed. |
| TICK01 sprint duration | Owner is tracing the existing `SprintDuration` path. | Existing baseline code under review; no new patch reported. | Pending owner trace. | Pending disposition. | Not performed. |
| F002 fixed-profile pose selection / collision-fit | Accepted source snapshot `448934e826fb41266dc79a313ef1187899d76382`; blind acceptance `10aa24e1114200397ed2d042a33f269113faf179`. | Implementation underway. | Pending exact-code review. | Pending. | Not performed. |
| Sprint timeout ordering | Incoming code commit `3c531180287b3cecff49e557083db73352b258f7` moves existing sprint-duration timer dispatch from `aiStep` tail to head and updates hook documentation. | Patch received; not merged. | Assigned, decision pending. | Hold merge/build for review. | Not performed. |
| 1.14 Soul Sand / friction sample / Elytra start | Accepted source snapshot `4a0c35f`; blind review `960ce564`. | Implementation underway in separate owner task. | Source snapshot accepted; exact-code review pending. | Pending. | Not performed. |
| Passenger yaw | Implementation underway in separate owner task. | Code pending. | Pending. | Pending. | Not performed. |

The baseline contains yesterday's integrated fixes and its successful test-disabled build. Build status for any new integrated code remains pending. When a patch is accepted, record its exact code and review commits, integrate it, then use the exclusive compile slot with every Gradle Test task explicitly disabled and `-x test`. No tests, client/TAS, Gym/server, Docker, or runtime simulations are authorized. This queue does not close any source pair or claim runtime parity. Cross-version switching remains outside the correctness guarantee.

## Integration review update — 2026-10-08

Independent exact-patch review is recorded at review commit `fddb263`.

- TICK-01 is accepted and integrated as `3699009`; test-disabled compile/build remains pending.
- Passenger yaw and 1.13 pose fit are held for the requested corrections in `integration-review-2026-10-08.md`.
- Elytra comparator commit `a5d07341d941ce2c0b9df1e392af9a40b1fbb9e9` was verified, but the full patch is held because the newer callsite/helper gates remain. The correction and original code are preserved in branch history and reverted from the active diff.
- No build runs until accepted review scope is recorded. Runtime validation remains unperformed.

## Build result — 2026-10-08

- TICK-01-only batch: `BUILD SUCCESSFUL` with `gradlew.bat build -x test --init-script build/no-tests.init.gradle`; every Gradle `Test` task type was disabled and no tests ran.
- Packaged JAR SHA-256: `1C3198B1E05AC5FD4CE4D84683060576CA34ED636CA81E88728FF9F6A07BCBD1`.
- Runtime parity remains unverified. Passenger yaw, pose fit, and Elytra remain held for correction and renewed review.
## Passenger yaw integration update — 2026-10-08

- Independent review 7c40951938a925fbb6fae541a95b462006113c09 ACCEPTED corrected code tip 32c8b0a7e089e32d2b755a513241ac0e58d1d562; correction 5c2cc08f6b8eaccb02f89a80de4823a33ac37503 tracks a successful direct player remount to the captured boat.
- Integrated net diff against main base ea812dc9171fdf41e02b3a2292db7d40b105c9c4: 7 files, 193 insertions. TICK-01 remains integrated.
- Build succeeded with every Gradle Test task type disabled and -x test; 18 tasks, 5 executed and 13 up-to-date. No tests or runtime checks ran.
- JAR SHA-256: 0EFADAB8C4FC87ABE8137C3E1F3E6A57CBAC15BB470E1048B739422839A7CB93.
- Pose fit and Elytra remain held for correction and renewed review.
## Ascending sneak edge integration — 2026-10-08

- F-002 accepted source snapshot `1d5f18176eccc5103c08bd1807b2f2c32f2b3376`; finding SHA-256 `4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9`; blind acceptance `0648b843fecf2358165a7c387cf348b02c66396b`.
- Exact code `14a6e23432ab6a885b5c5ce265cf2404283756f6`; independent exact-code review `c24391cc31a774da64b5d3e6d319a56a4a01e9a3` ACCEPTED. Integrated and built; see `integration-review-2026-10-08.md` for build and JAR evidence.
- Runtime validation not performed. The corrected Elytra start patch is accepted and queued as the next serial integration batch; this build contains only the ascending edge patch. Pose resize, swimming pitch, and WORLD03 remain pending.
## Full Elytra start integration — 2026-10-08

- Code commit `361c15c687a16bbbaaef2525d27a4d2277b33c3a`, reviewed code tip `bb8484823980d8f44c33c712b964275917cc93bb`; independent review tip `b8dfbc3e4d092141861759cab36272ea223a4d17` ACCEPTED.
- Integrated and built against local main `b0e7300a46aeb3fe4087bfafbcf1bcc49c76542a`; see `integration-review-2026-10-08.md` for identities, source-digest discrepancy, and JAR hash.
- Swimming pitch is the next accepted serial batch. Pose resize and WORLD03 remain pending; no runtime validation was performed.