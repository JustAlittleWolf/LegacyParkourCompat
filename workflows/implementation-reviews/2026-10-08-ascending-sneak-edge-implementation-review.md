# Implementation review — 1.18.2 ascending sneak edge

**Decision: ACCEPT** — static implementation correspondence for commit `14a6e23432ab6a885b5c5ce265cf2404283756f6`, checked in an isolated review worktree. No implementation files were changed. No build, tests, client, server, TAS, Gym, or Docker were run.

## Evidence identities

- Accepted source finding: `workflows/source-campaign-2026-10-07/1.18.2--1.19.2/findings/F-002.md`; immutable source snapshot commit `1d5f18176eccc5103c08bd1807b2f2c32f2b3376`; finding SHA-256 `4a4a23d071397f4cd3a1c82ef4e4ff59885df9b63c79b425c927c310bd1dbbb9`; blind finding acceptance `0648b843fecf2358165a7c387cf348b02c66396b`.
- Exact A source: `build/movement-campaign-2026-10-07/ready/1.18.2/mojmap/net/minecraft/world/entity/player/Player.java`, SHA-256 `bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a`.
- Exact B source: `build/movement-campaign-2026-10-07/ready/1.19.2/mojmap/net/minecraft/world/entity/player/Player.java`, SHA-256 `155c5fcfba322d968f3180383e7d283ddeb5edee4e04314906310d4e3ce0ccc1`.
- Current runtime source: `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/net/minecraft/world/entity/player/Player.java`, SHA-256 `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`.
- Implementation files reviewed at the code commit: `change/v1_18_2/SneakEdge.java`, `change/common/SneakEdgeBackoff.java`, refactored `change/v1_16/SneakEdge.java`, provider registration in `change/v1_18_2/MovementChanges.java`, and dispatch in `mixin/PlayerMixin.java`.

## Review

The accepted delta is reproduced at the correct boundary. 1.18.2's `Player#maybeBackOffFromEdge` guards on not flying, SELF/PLAYER movement, the shift-key predicate, and `isAboveGround()`, then performs the X-only, Z-only, and combined X/Z backoff loops regardless of requested Y. 1.19.2 adds the `requestedDelta.y <= 0.0` guard. The implementation registers its replacement as `emulates = V1_18_2`, so the existing resolver selects it for 1.18.2 and older profiles until a closer registered behavior applies; 1.19 profiles do not resolve it and retain the vanilla method.

`change/v1_18_2/SneakEdge.java` carries the 1.18.2 flying, mover, shift, and near-ground gates. `isAboveGround` retains the source's short-circuit order and float probe-distance operations: grounded first, then `fallDistance < probeDistance`, then the translated-box `noCollision` query. It intentionally has no Y-sign test. `SneakEdgeBackoff.apply` retains source X/Z initialization, X then Z then combined loop order, exact 0.05 threshold and signed reductions, full translated-box queries, and the original Y component in the returned vector. The refactor of the earlier 1.16 behavior delegates to the same unchanged loop implementation and preserves its existing on-ground/mover/shift guards.

The dispatch hook runs at the return of `Player#maybeBackOffFromEdge`, which is called virtually by `Entity#move` before `collide` in the exact 1.18.2 and 1.19.2 sources and in current 26.2. On 26.2, the native player method contains the positive-Y veto; when a historical 1.18.2 behavior resolves, the return hook receives the requested vector and applies the historical probe on rising movement. For non-positive-Y movement the native operation already uses the selected historical `EdgeBackoffProbeBounds` probe behavior; applying the historical loops again cannot restore any component already reduced. The 26.2 `Player#maybeBackOffFromEdge(Vec3,MoverType)` and `Entity#maybeBackOffFromEdge(Vec3,MoverType)` declarations match the mixin target and inherited dispatch; the `isStayingOnGroundSurface` invoker target also exists in current `Player`.

For `CURRENT`, `ChangeResolver.resolve` returns no movement changes. `PlayerMixin` only replaces the callback value inside the present-behavior branch, so the historical behavior and distance hooks leave the native return value intact. Current 26.2's own positive-Y guard remains active.

## Limits and prior-review claim

This accepts implementation-to-source correspondence only. It does not complete either endpoint's movement discovery, establish the first changed release within `(1.18.2, 1.19.2]`, or validate final trajectories at runtime.

The claimed independent review evidence is limited: commit `0648b843` is a blind review of the **source finding snapshot** F-002 and supports its bounded implementation handoff. It does not review code commit `14a6e234`. The repository's earlier implementation-review report (`workflows/implementation-reviews/historical-movement-implementation-review-2026-10-08.md`, commit `fddb263`) covers four different patches and does not include this implementation. This report is the independent static review of `14a6e234`.

Reviewer: isolated static implementation review. Decision date: 2026-10-08.
