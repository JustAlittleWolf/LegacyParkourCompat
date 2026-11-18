# 1.13 swimming-state writer source evidence

This archive preserves three exact accepted reports with independent verdicts: the 1.13→1.13.1 state-writer comparison, its 1.13→26.2 destination witness, and a separate ordinary 1.13 local-route source witness. These claims concern the `Player.updateSwimming` state writer and its exact eligibility gate. They do not establish runtime parity or general fluid/solid geometry behavior.

| Record | Bounded disposition | Report SHA-256 | Verdict SHA-256 |
|---|---|---|---|
| 1.13→1.13.1 | Accepted exact source difference in the already-swimming state update; no runtime claim. | `6197165f66f254c57ed900b8d2eec75f90fa35594d43a9d93ea48857a9fedb60` | `ba21b9998e9433896a19b41c3cd66d80893d707de2858e672b8827845542463f` |
| 1.13→26.2 | Accepted destination comparison: old player clearance condition is absent while the early flight branch remains. This is an exact endpoint comparison only. | `a19278a995a24f7d9c2a6c30bef5ed57420a854979ca4d8046a6806986297e20` | `c22225c458959a47674060e37cddba39038661d51c315003bdc2e98e7d5b2bd8` |
| 1.13 ordinary route witness | Source-predicted route uses a default full-cube stone roof over source water, an already-valid swim state, and a local sprint-stop input; the update reads the still-swimming state before same-tick travel. This supports the stated gate only. | `84f7ee2b9c864f0f73b24c9575f00ffeb52ff32874d744f425d2245b6a6848fa` | `8907af733db10133307031ee13c94b3c03b12b830333152a5e6dcd7f392df120` |

The route witness does not claim eye-height rules, generalized fluid/solid geometry, a measured trajectory, or runtime behavior. The independent 1.13 swim pitch source archive is separate; its writer is not included here.

## Read-only product disposition at base `aae9c864`

No equivalent delegate state-writer hook for this exact `Player.updateSwimming` eligibility operation was found in the baseline product tree. A narrow bridge preflight remains pending; this archive makes no implementation or product-completion claim. No product, backlog, or prior archive was changed.