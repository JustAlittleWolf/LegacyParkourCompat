# Fix: F-008 border edge gate

- Discovery finding: `workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-008-border-edge-gate.md`.
- Immutable finding snapshot: commit `e531086eecc778432b40b2b9ef39499b8a7f0dba`; Git blob `58ebc5b2dbff5f0e409fdceb38b0466e89322ce4`; raw SHA-256 `3a8a5258cb741497a52085f76688df7ca32161dcbd0e068ffe5ee70b3a271afd`.
- Accepted blind source review: commit `2fd121e116d7185165c76aea1c3708273afc7ea1`; review blob `871ad5bccde2194ccc733ed8fef92472a1c96c2a`; raw SHA-256 `ee96f6564b366b9622bfed23bb31331018613d64589ee1215d842a2165307d3e`.
- Pair status: accepted full-pair source freeze; discovery completion, implementation, build, and runtime parity remain separate.
- Implementation status: implemented in commit `2aede8384f0a9ea4ea8518b7ec8dcfedfcc12732`; independent static implementation review and post-merge review accepted on 2026-10-09.
- Source boundary memo: `workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-boundary.md`, commit `1bfc993c0602c00b22622d651c93fe640d4a4ac1`, Git blob `864c15ea896e688c339d31b054963a6d6ea76b9a`, raw SHA-256 `25cc8c489b5ddea46b07abae01db1bc5d444d28d38f7f075ad0631cc0f840040`.
- Accepted independent memo review: `workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-review.md`, commit `c578b68bbbef1442fd23367bfef5fe6dc9e39f89`, raw SHA-256 `6e4193cb85a66fd4ea289f79f34f0a2edf058423ef6ca5c31daffbc48ac49259`.

## Source behavior and resolution boundary

- 1.15, 1.15.1, and 1.15.2 `CollisionGetter.java` are byte-identical (SHA-256 `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd`). The edge probe passes a candidate AABB to block/entity collision lookup, but the border branch intersects the player's current box, deflated and inflated by `1.0E-7`, with the border shape. It has no strict rounded-border admission check.
- 1.16 and 1.16.1 `CollisionSpliterator.java` are byte-identical (SHA-256 `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297`); the 1.16.2 and 1.16.5 files have the same hash. `worldBorderCheck` first admits the border query only when the current player box is not strictly inside the floor/ceil rounded bounds, then uses that same current box for the inflated/deflated shape tests.
- 1.17.1 `CollisionSpliterator.java` has SHA-256 `c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0`; its query body has the same current-box gate. 1.18, 1.18.1, and 1.18.2 use the candidate AABB in `CollisionGetter.noCollision`'s border path instead (exact hashes and manifests are recorded in the source memo).
- The source-boundary memo is intentionally unchanged: its accepted claim is limited to the 1.17.1 → 1.18.2 pair. This implementation handoff records the additional resolver applicability check needed for supported 1.15.x and 1.16.x profiles.

## Implementation

- Current target: Minecraft `26.2`; Mojmap target source path and method descriptor were verified in the source memo.
- Hook: `SneakEdgeCollisionQueryBehavior` (`player.sneak.edge.collision-query`), invoked only by the existing Player `maybeBackOffFromEdge` mixin bridge.
- Historical query providers are independently registered at `V1_15_2`, `V1_16`, and `V1_17_1`. The 1.15.2 provider retains its no-gate current-box border predicate; 1.16 and 1.17.1 use the strict rounded-border admission gate. This prevents nearest-later resolution from applying the 1.17.1 gate to V1_15/V1_15_2, while V1_16/V1_16_2 and V1_17/1.17.1 resolve to the gated provider under the repository's closest-later rule. Exact 1.17.0 source was not available in the campaign source cache, so that profile's use of the V1_17_1 provider remains source-unverified. Pre-1.15 source was not checked; older profiles resolve the ungated V1_15_2 provider under the same rule. At V1_18 and later no historical query provider resolves, so the shared edge helper uses native `noCollision`.
- After the border predicate, each provider checks candidate block collision then candidate entity collision, preserving the existing edge-backoff axis loop and the source-relevant result order. The hook is player-only; vanilla block states and non-player movement are untouched.
- Disabled/current behavior: `MovementRuntime.find` resolves no historical hook, leaving native `Player.maybeBackOffFromEdge` active. Existing nearest-later `SneakEdgeBehavior` providers receive the query only when the query hook resolves for that profile.
- Static checks: `git diff --check` passed. No tests, game/TAS/Gym/server launches, or Docker work were run.
- Build: pending the exclusive campaign build owner; no Gradle build was run here.
- Runtime question: compare the edge-gate query at a border for V1_15_2, V1_16, V1_17_1, and V1_18 against exact clients. Tick trajectory parity remains unverified.
- Default-branch merge: merge commit `842eb77a044eec03dbc75a44d12b48dfc9757a11`, incoming parent `3467cc398a89bb86bfafa6eedb315e0a29e040de`. It was textually clean. Post-merge independent review accepted: the incoming V1_18 `PlayerFallDistanceResetBehavior` dispatch targets `Entity.move`'s fall-distance reset call, a separate catalog key and invocation from the Player edge query. It can influence the separate V1_18/1.18.1 `SneakEdgeBehavior.isAboveGround` path, which has no F-008 query provider, but does not change F-008 query resolution or its border admission.
- Feedback isolation: no implementation results were sent to source-only owners; pair source freeze was already accepted before implementation.
