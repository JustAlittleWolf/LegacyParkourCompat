# Independent audit — 1.8.9 → 1.9 pose-provider dependency inventory

Verdict: **ACCEPT**, bounded to the pose-query caller, dispatch, provider-dependency inventory, and explicit open-gate boundaries in the immutable report below.

- Report: `build/source-189-19-20261117/tick-state/pose-provider-dependencies.md`
- Report SHA-256: `65d3084d1fc3ae2e3a63de6323ce9163fad9d52e0367f8957862540317238294`

## Independent checks

- Recomputed the report digest and the bound source identities. The cited `World.java`, `Block.java`, `StateDefinition.java`, and paired `PlayerEntity.java` hashes match local source bytes. The 1.8.9 root provenance/manifest hashes and 1.9 mapping-local provenance hash also match. The 1.8.9 sidecars are at the release root, outside the `ornithe-feather` namespace; the report's source namespace and sidecar identities are consistent with that publication layout.
- In 1.9 `PlayerEntity.tick()`, `updatePlayerPose()` is called after superclass tick at the cited location. The method selects fall-flying, sleeping, sneaking, or ordinary dimensions; it forms a proposed box from the current minimum corner and only calls `setSize` when the box-only `World.getCollisions(Box)` query is false. There is no `onGround` condition in the source method.
- The 1.8.9 `PlayerEntity` has no `updatePlayerPose()` method or tick-driven sneaking size change. Its `setSize` sites in this class are reset/death/wake-up paths, not ordinary pose resizing. Its movement collision method is the entity-aware list query and is not equivalent to 1.9's new Boolean pose query.
- The 1.9 Boolean `World.getCollisions(Box)` scans the proposed AABB's expanded integer envelope, skips unloaded chunk columns, returns collision at the world border, and dispatches each admitted `BlockState` with `entity = null`. It returns true on the first nonempty collision list. `StateDefinition.State.addCollisions` forwards the exact state to the block; base `Block.addCollisions` gets the state's collision shape, translates it by block position, and adds it only when it intersects the proposed box. Thus the report correctly separates the Boolean pose gate from entity-response collection and sequential movement clipping.
- Rechecked the cited accepted pose, state-provider, Door, stair clipping, collision-scan, and provider-residual packet digests. Their scopes remain bounded as the report describes: existing method/provider observations are not promoted into a complete per-state pose-query matrix. Anvil/Chest remain excluded/failed lifecycle candidates; malformed Door applicability remains unestablished; frozen pane-connection and piston-head-arm operations stay excluded.

## Limits and current evidence state

The report correctly leaves obstructed pose-query results open across the finite but incompletely audited state/provider matrix. In particular, it does not infer that every listed provider combination is collision-free or equivalent, and it does not transfer entity-aware scan-membership or sequential-clip results to this Boolean early-exit query. Its unobstructed pose witness remains bounded to the already accepted pose finding.

The report's stair-coordinate row cites the earlier r1 observation and says its interval correction was pending. That statement is a historical boundary on the report's own evidence; the separate r2 correction is now independently accepted at `providers/stair-state-coordinate-reachability-r2.md` (SHA-256 `3e7438882405a235db17378c6476af0abb0b7044fa34182bf96416c8b2ebf73f`) with correction verdict `providers/stair-state-coordinate-reachability-audit-r2/verdict.md` (SHA-256 `7bb562af0482a7c39f01bea345dfebae9aecb3b3deb68e501cf6a572654ea0a8`). This later correction does not make the pose report's stated partial row or its pose-query limitation false.

This verdict accepts only the bounded dependency inventory and its exclusions. It does not close all pose providers, the full player tick/provider inventory, or the pair-wide independent audit; it does not claim a new movement finding, implementation readiness, pair freeze, or runtime parity.
