# TICK-07 independent review — 2026-10-08

## Snapshot identity

- Owner finding snapshot: commit `1dbafbb7899c57ddf1718e421aa2c32e283fc7c7`, path `workflows/source-campaign-2026-10-07/1.8.9--1.9.4/findings/TICK-07-client-player-push.md`, SHA-256 `8a8aadfe4ddb982d03acd317b13d8dbea0ce3ef39d2e15b2ad3936196b188a77`.
- No TICK-07 immutable finding-snapshot event appears in the owner run at that revision. The pair run remains partial; this decision does not infer pair completeness.
- Source manifests: A `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; B `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`. Independently checked cited source hashes match: A `LivingEntity.java` `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e`, A `Entity.java` `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b`; B `LivingEntity.java` `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`, B `Entity.java` `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`, and B `EntityFilter.java` `91158a5477935c911178433e1b2628d1d063604a23b08baee4706dd286e7e813`.
- Artifact identity is limited to immutable `feather-r1-2026-10-07` snapshots: A `5c4cff3e4ac10ea1e1da166279133801ad304b4557d5ee4a77a2430977afb6a5`; B `fbcf50795566e12b8eab0e733b136ed562c4009d707ef4a7a4994936491816a3`. The original derived mapped JARs are unavailable, so their identity/equivalence is unproven.

## Decision: accept, as a conditional local-player velocity writer

In 1.8.9, `LivingEntity.mobTick` reaches the post-travel push phase but calls `pushAwayCollidingEntities` only when `!world.isClient`. In 1.9.4 it calls that method on both sides. The newer query uses the current shape and `EntityFilter.canBePushedBy(this)`; on a client, that filter excludes ordinary candidates but permits a locally controlled `PlayerEntity`, subject to spectator, pushability, and team-collision rules. `pushAway` invokes `candidate.push(owner)`.

When the selected candidate is the local player, it is the receiver (`this`) in `Entity#push`. That method adds a horizontal velocity delta to the local player, subject to same-vehicle, no-clip, separation (`absMax(dx,dz) >= 0.01F`), and local-player passenger guards; `addVelocity` marks velocity dirty. A has no corresponding client-side query call. The source therefore supports an additional conditional B-side route that writes local-player horizontal velocity.

## Limits

- This accepts the existence and reachability of the direct local-player velocity write, not a concrete world-state occurrence or measured trajectory.
- The owner may be another client-side living entity; its independent movement is not treated as the endpoint or as a finding here.
- The query shape and candidate filters also differ; this review does not claim which candidates overlap in a particular world.
- First changed release remains unknown within `(1.8.9, 1.9.4]`; runtime validation was not performed.
- Original mapped-JAR identity/equivalence remains unproven, as stated above.
