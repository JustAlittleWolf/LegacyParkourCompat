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
