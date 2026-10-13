# F-001 sprint collision applicability reconciliation

**Status:** Implemented for bounded F-001; exact first-release boundary accepted; no Java change required.

## Accepted evidence

The immutable endpoint reconciliation is commit `bba3c42691c2bb1527ce98dc5ff942e35bb31690`. Its note is `workflows/fix-implementation/reconciliations/F001-sprint-collision-2026-10-09.md` at that commit, blob `e1ed834519d5a50841fc1af361957da998716243`, raw SHA-256 `c0a6dff4225befb68c0f394aed9198e01c7fde50db00b6b8baa75d027f3ea367`. The independent endpoint technical review is immutable commit `d898d4c0ef42f0cdb065aacc36eb30d28dcc06a4`, file `workflows/implementation-reviews/2026-10-09-endpoint-reconciliation-review.md`, blob `95ea1be9272116a88af552fcaf64ff0570470b52`. It accepted the endpoint correspondence and left the first-release boundary open.

The boundary candidate is immutable author commit `14443f43fbcc30152916ac75796846d796cf0268`, file `workflows/source-boundary-reviews/1171-1182-F001-F002-boundary-evidence-2026-10-09.md`, blob `8892a7533358c6e7053527c24dacd0a2acce5ae9`, raw SHA-256 `66f5659dd17ff8441d10a02d95920ecd5d4e190c53c2c0893eac79efec36c6cb`. Independent review is immutable commit `163ea4881b327a9316a266601705463784566e2c`, file `workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md`, blob `b505e009ad5726b7d7fd18c7bf35a38495003e28`. The review accepts 1.18 as the exact first changed release from the examined versions 1.17.1, 1.18, 1.18.1, and 1.18.2.

The candidate's 1.18 ready-marker SHA omitted a trailing `f`. The independent reviewer rehashed the live marker; its correct SHA-256 is `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`. This metadata correction does not alter the source evidence or decision. Relevant source-ready identities and hashes are:

| Release | Source manifest SHA-256 | Artifact manifest SHA-256 | `LocalPlayer` source SHA-256 | `Entity` source SHA-256 |
|---|---|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| 1.18 | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` | `7ca2b4da88c215e52924d277b257c4631f9664f5def2fb2b7ef25479ec330234` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.1 | `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d` | `b8237a01cdebe7d784caae113886f42565677ce6897ea960a49223cfec4c00ec` | `e38d0bbb6e5b9de2a698609b8491d0007406b3becd42ba9b95289f533788c996` | `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1` |
| 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

The 1.18 and 1.18.1 `LocalPlayer` and `Entity` source hashes match. The reviewed 1.18.2 sources retain the changed gate. These findings support the bounded first-release decision for F-001.

## Implementation applicability

The current implementation was inspected without modification:

- `ParkourVersion` declares `V1_17_1("1.17.1")`, `V1_18("1.18", "1.18.1")`, and `V1_18_2("1.18.2")`.
- `MovementChangeCatalog` has one `SprintCollisionBehavior` registration, in `registerV1_17_1`, backed by `change.v1_17_1.SprintCollision`.
- `SprintCollision` returns `vanilla || player.horizontalCollision`, restoring the historical any-horizontal-collision condition while retaining the other native stop-sprinting predicates.
- `LocalPlayerMixin` injects at the return of `shouldStopRunSprinting()Z`; it changes the return only when `MovementRuntime` finds an applicable behavior.
- `ChangeResolver` excludes a change when its `emulates()` version is older than the selected historical profile, and resolves the closest remaining applicable change. `CURRENT` resolves no historical changes.

Therefore the sole V1_17_1 registration resolves for every declared historical profile from V1_8 through V1_17_1. For selected V1_18 profiles (1.18 and 1.18.1) and later profiles, that registration is older than the selected profile, and there is no later sprint-collision registration. The mixin leaves the target's native return unchanged when no behavior resolves. This is the intended boundary behavior: historical behavior through 1.17.1 and the target's native gate beginning with 1.18.

The evidence establishes the exact first changed release among the reviewed versions and confirms that the existing registration boundary matches it. It does not claim an exhaustive source audit of every earlier release, full later-release historical parity, or runtime parity. The paired source campaign remains partial.

## Scope and verification

This is a no-code applicability reconciliation. The evidence was prepared against base code commit `e7` and target 26.2. The task branch was fast-forwarded to main at `cb11257e593987c68be98a7dbda8bbea7d5e77fd`; incoming changes outside this note were documentation-only, and the source tree had no diff. This final note records the accepted boundary and statically verified resolver mapping.

Static checks only: source identities, reviewed findings, implementation registration and resolution rules. No build, tests, game/TAS/Gym/server launch, or runtime parity check was performed.
