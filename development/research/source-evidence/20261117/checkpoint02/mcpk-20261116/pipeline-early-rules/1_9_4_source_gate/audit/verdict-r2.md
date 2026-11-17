# Independent 1.9.4 source audit verdict — r2

**Verdict: ACCEPT**

Audited corrected packet SHA-256 `209178a80021e29cc09c8600fbc27ff8fb41be226f3ec427f4c7052ec8076bec` against the assigned exact sources and identity records. This is a bounded source verdict, not full-pair coverage.

## Result

The correction to `World.getCollisions(Box)` is accurate in all five releases. The exact overload returns `boolean`; it accumulates block collision boxes through `BlockState.addCollisions(..., candidate, list, null)`, returns `true` when the list becomes nonempty, and has an explicit out-of-world **X/Z** bounds guard that also returns `true`. It returns `false` after the scan otherwise. It does not query entity collisions; the entity-taking overload is separate. The `World.getCollisions(Box)` normalized method-body digest independently recomputed to `d63400a365e5b4bb28893057c3238f3f249209a54d0ab4cd5b3facba6e2d4869` in each of 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4.

The packet's normalized digests for `PlayerEntity.updatePlayerPose()`, `Entity.setSize(float,float)` and `LivingEntity.mobTick()` also independently match in all five exact sources. Their operation order and gates are unchanged. The exact 1.9.4 behavior therefore equals the already authenticated 1.9–1.9.3 behavior for both assigned operations; no difference was found in these bounded slices.

## Identity checks

The packet SHA matches. Each scoped Java file hash, including `World.java`, matches its release's source manifest. For 1.9.4, the ready marker is `ready` for version `1.9.4` in `ornithe-feather`; its source and artifact manifest byte hashes match the marker, and its client hash matches the `1.9.4/client.jar` artifact row. Both Feather mapping artifacts named by the 1.9.4 provenance sidecar have matching entries in the artifact manifest. The 1.9–1.9.3 client, mapping and provenance identities and the 1.9.4 ready/provenance/verification hashes were unchanged from and match the packet's listed values.

## Limit

This accepts only the exact bounded claims above. It does not establish complete 1.9–1.9.4 pair coverage, other movement-operation equivalence or a patch-introduction boundary. No product code, candidate implementation, backlog, source findings, wiki material, Git history, build, test or runtime was inspected.
