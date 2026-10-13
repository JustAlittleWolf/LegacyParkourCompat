# F-001 final no-code reconciliation review

**Verdict: ACCEPT, bounded to the independently source-reviewed release boundary.** The current hook, its collision gate, catalog registration and profile resolution agree with the accepted F-001 finding: the historical any-horizontal-collision stop rule applies through 1.17.1, and the native minor-collision exemption remains active from 1.18 in the releases examined. No code change is indicated for that boundary.

This verdict does **not** establish source fidelity for every profile routed through the 1.17.1 registration, all intervening releases, full movement parity, or runtime parity. The change is routed as far back as V1_8 by the generic resolver; that routing is not evidence that the source behavior was unchanged throughout those releases.

## Bound documents and accepted evidence

The reviewed final reconciliation is immutable commit `936bc7213916335962f638d53263280fbf061e29`, path `workflows/fix-implementation/reconciliations/F001-sprint-collision-2026-10-09.md`, blob `ad8f0c6c10555fd0c6db5b5e0320d2dbc7553318`, raw SHA-256 `0f54257decdafdf70b7cc0b73f0068ac271964bf53bc43aa7c3397a70b77cb4e` (verified against the author worktree).

The endpoint evidence it carries is the initial reconciliation at commit `bba3c42691c2bb1527ce98dc5ff942e35bb31690`, same path, blob `e1ed834519d5a50841fc1af361957da998716243`, raw SHA-256 `c0a6dff4225befb68c0f394aed9198e01c7fde50db00b6b8baa75d027f3ea367`, and its independent review at commit `d898d4c0ef42f0cdb065aacc36eb30d28dcc06a4`, path `workflows/implementation-reviews/2026-10-09-endpoint-reconciliation-review.md`, blob `95ea1be9272116a88af552fcaf64ff0570470b52`, raw SHA-256 `0b257ce7e7dd20d2a96a48a735a1584d7ed340033cae9ec6a496d269e1970dcf`. That review accepted endpoint correspondence and left the first-release boundary open.

The accepted boundary evidence is author commit `14443f43fbcc30152916ac75796846d796cf0268`, path `workflows/source-boundary-reviews/1171-1182-F001-F002-boundary-evidence-2026-10-09.md`, blob `8892a7533358c6e7053527c24dacd0a2acce5ae9`, raw SHA-256 `66f5659dd17ff8441d10a02d95920ecd5d4e190c53c2c0893eac79efec36c6cb`; and independent review commit `163ea4881b327a9316a266601705463784566e2c`, path `workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md`, blob `b505e009ad5726b7d7fd18c7bf35a38495003e28`, raw SHA-256 `bebbb4dc67323ece9615444d5d32ec56eba480683abb6a30891d18481b5ef124`. The accepted exact comparison is 1.17.1 (`horizontalCollision`) against 1.18, 1.18.1 and 1.18.2 (`horizontalCollision && !minorHorizontalCollision`), making 1.18 the first changed release among those sampled. The independent review corrected the 1.18 ready-marker digest to `4537edf0e88c88631215beb1c14f1d67f5a5d6d021c982c0af549b7b27856e5f`.

The accepted F-001 finding is in author snapshot commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`, blob `9bc53d27195a6f89ddb19711ef40558d3c7bdcc8`, raw SHA-256 `77957564feaa103b7817055c71c0a9d9e31d7af4bacae5432b4b18d82c99e2fd`; its blind source review is commit `2fd121e116d7185165c76aea1c3708273afc7ea1`, path `workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md`, blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`. This review relies on that accepted evidence and does not expand it.

## Independent implementation trace

I inspected the current implementation at main `cb11257e593987c68be98a7dbda8bbea7d5e77fd` (already an ancestor of this review branch). Relevant source blob identities in that snapshot:

- `LocalPlayerMixin.java`, blob `481cc05ad933ba3d43a742f3fb960a733f93a5a3`.
- `SprintCollision.java`, blob `f3d5b5d18dcf172ce2e70a393af8937e2322fc1d`.
- `ChangeResolver.java`, blob `9a6a57efd94aad45c2cd62ff452650528664577c`.
- `MovementChangeCatalog.java`, blob `051a0f94cdfc9aeebd86174e7e645f9140fbd681`.

`LocalPlayerMixin` injects at the RETURN of `shouldStopRunSprinting()Z` and replaces the return only if `MovementRuntime.find(SprintCollisionBehavior.class, player)` yields a behavior. `MovementRuntime.find` returns empty if movement emulation is disabled; the hook is on `LocalPlayer`, so it is player-only. `CURRENT` resolves to an empty map. In these cases the original vanilla return is preserved.

The single catalog entry for `SprintCollisionBehavior` is registered at `ParkourVersion.V1_17_1`; no V1_18 or later registration exists. `SprintCollision.shouldStopRunSprinting` returns `vanilla || player.horizontalCollision`. This OR restores stopping for any horizontal collision while retaining the other native stop predicates already represented by `vanilla` (sprint capability and forward input, among them).

`ChangeResolver` returns no historical changes for `CURRENT`; otherwise it keeps registrations whose emulated version is newer than or equal to the selected profile and chooses the closest qualifying release. Thus the V1_17_1 change resolves for declared profiles V1_8 through V1_17_1. For V1_18 (including 1.18.1), V1_18_2 and later, that entry is older than the selected profile; with no later sprint-collision entry, the hook does not override the target-native return. That is consistent with the independently accepted boundary for the four sampled exact releases.

The final reconciliation also binds ready source/artifact manifests and LocalPlayer/Entity hashes for 1.17.1, 1.18, 1.18.1 and 1.18.2. Those identities support the accepted sampled comparison; this verdict does not infer coverage of unsampled releases from matching hashes or resolver routing.

## Limits and checks

The final reconciliation's bounded status is accurate. Its statements about behavior through all of V1_8–V1_17_1 are resolver-routing facts, not proof that every such release has the historical 1.17.1 collision rule. The no-code conclusion is accepted only for the exact sampled boundary and the matching current routing. No build, tests, game/TAS/Gym/server launch, or runtime parity check was performed; only document/code identity checks and static source tracing were used.
