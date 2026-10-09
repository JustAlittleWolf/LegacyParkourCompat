# F-001 sprint collision reconciliation (2026-10-09)

## Inputs and acceptance

- Finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md`.
- Immutable author snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, path above, Git blob `9bc53d27195a6f89ddb19711ef40558d3c7bdcc8`, raw SHA-256 `77957564feaa103b7817055c71c0a9d9e31d7af4bacae5432b4b18d82c99e2fd`.
- Independent blind acceptance: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, Git blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; accepts the horizontal collision sprint gate only.
- Finding-specific dependencies: accepted for the reachable stone-wall case. The input-to-sprint gate, B collision classifier/state writer, relevant solver path and common full-cube stone path are bounded by the finding. This does not close the pair's remaining discovery work.
- Pair status at handoff: partial; pair freeze and runtime parity are not claimed.
- Base code SHA: `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`; build target Minecraft 26.2 (`gradle.properties`).

## Canonical source publication checks

Consumed the existing ready roots read-only under `D:/Javastuff/LegacyParkourCompat/build/movement-campaign-2026-10-07/ready/`.

| Side | Ready marker | Source manifest SHA-256 | Artifact manifest SHA-256 | Verified cited files |
|---|---|---|---|---|
| A: 1.17.1 Mojmap | `ready`, version ID 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` | `LocalPlayer.java` `c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812`; `Entity.java` `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| B: 1.18.2 Mojmap | `ready`, version ID 1.18.2 | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` | `a1507e4875faee892aca4c59686bbd68933db21a2274559e81eea8a32b021036` | `LocalPlayer.java` `99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095`; `Entity.java` `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |

For each side, the marker's manifest hashes matched the raw manifest files, and the targeted source hashes matched both direct file hashing and their manifest entries. The accepted endpoint evidence is reproduced: A's non-swimming sprint-stop expression stops on any `horizontalCollision`; B gates that term with `!minorHorizontalCollision`, and B's `Entity.move` sets the minor flag from its classifier after movement resolution.

The current published campaign roots contain 1.17.1 and 1.18.2, but no ready roots for 1.18 or 1.18.1. The immutable finding explicitly says the first changed release is unknown within `(1.17.1, 1.18.2]`. The endpoint pair therefore does not establish that `V1_18` is the first changed profile.

## Existing implementation reconciliation

- Current hook: `SprintCollisionBehavior` (`player.sprint.collision`) at `src/main/java/me/wolfii/legacyparkourcompat/mechanic/hook/SprintCollisionBehavior.java`.
- Current bridge: `src/client/java/me/wolfii/legacyparkourcompat/mixin/LocalPlayerMixin.java`, injection at return from `shouldStopRunSprinting()Z`. It supplies the player and vanilla result to `MovementRuntime`; an absent hook leaves the vanilla result unchanged.
- Current change and registration: `src/main/java/me/wolfii/legacyparkourcompat/change/v1_17_1/SprintCollision.java`, registered under `SprintCollisionBehavior` at `ParkourVersion.V1_17_1` in `MovementChangeCatalog.registerV1_17_1`. The change returns `vanilla || player.horizontalCollision`, restoring the older stop condition while retaining other vanilla stop gates.
- Resolution: `ChangeResolver` selects the closest registration at or after the selected profile; thus the V1_17_1 change is used for V1_17_1 and older profiles, while V1_18 and later have no sprint-collision override and retain the target's vanilla gate.
- Disposition: **partially covered; boundary verification open**. The accepted operation already has an independent hook, bridge, historical behavior and catalog registration. No production change is needed for the bounded endpoint claim. However, this reconciliation cannot independently certify the current `V1_17_1`/`V1_18` split without canonical 1.18 and 1.18.1 source identities and bodies. Do not move or duplicate the registration based only on the two endpoints.
- Next action: the source-preparation owner must publish/verify exact Mojmap ready artifacts for 1.18 and 1.18.1 (or a source-bound proof that the operation bodies are identical for those releases); then a separate implementation reviewer can confirm the existing boundary. No source-only owner was contacted and no shared source was changed.

## Checks and integration

- Static identity checks: accepted finding Git blob and raw SHA-256 matched; both endpoint ready markers, manifests and cited source-file hashes matched.
- Code check: the existing interface, client dispatch, V1_17_1 implementation, static registration and resolver direction were inspected. No implementation files were edited.
- Build/tests/runtime: not run. This is a documentation-only reconciliation; no build, tests, client, TAS, Gym, server or Docker action was authorized or needed.
- Main integration: task branch `fix/reconcile-sprint-collision-2026-10-09` starts at `e7f89348a619cc9feb2d47aab7a87fbefbe7649d`, equal to the requested main target. `main` is already an ancestor; there are no incoming commits to reconcile.
- Runtime validation questions: verify sprint state after the accepted minor-collision case once the separately authorized input-simulation lab is available; source review alone establishes no runtime parity.
- Feedback isolation: no implementation-derived result was sent to source-only owners before full-pair freeze.
