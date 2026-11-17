# Independent source audit: climbing push gate

**Terminal status: COMPLETED — core receiver-eligibility claim accepted with one correction.**

## Bound claim and evidence identity

Reviewed the frozen comparison artifact `climbing-push-gate-source/comparison.md`, SHA-256 `7d143e119cea2ef523b40a3cf6efc732ebac55608d85c9d04161f05f292dbcdd`.

Read-only evidence was limited to canonical `decompiled_minecraft/<version>/ornithe-feather/` sources and their version-level ready markers, source manifests, and provenance for 1.10.2, 1.11, 1.11.1, 1.11.2, and 1.12.2. Each ready marker names the exact version and mapping. Each ready marker's declared source-manifest SHA-256 matched the manifest bytes; each relevant Java file hash matched its source-manifest entry and the frozen comparison's listed hash. All five relevant source trees use Feather Gen2 build 2.

## Verdict

- **Cutoff:** `LivingEntity.isPushable()` returns `!this.removed` in 1.10.2 (line 1887). In 1.11 it returns `this.isAlive() && !this.isClimbing()` (line 1948), and the same expression appears in 1.11.1, 1.11.2, and 1.12.2. This establishes the change across the bounded 1.10.2 → 1.11 pair, with no reversion in the examined 1.11 patch releases or at 1.12.2. It does not locate an intermediate patch-level introduction more precisely.
- **Candidate and pusher roles:** `LivingEntity.pushAwayCollidingEntities()` calls `world.getEntities(this, this.getShape(), EntityFilter.canBePushedBy(this))`. In `canBePushedBy`, the captured `entity` is the query owner/pusher and the lambda's `entity` parameter is each candidate. The lambda checks candidate `isPushable()` before its client-side rule admits only a `PlayerEntity` whose `isLocal()` is true. `LocalClientPlayerEntity.isLocal()` returns true in both 1.10.2 and 1.11. Thus another living entity's query can select the local player as the candidate before the cutoff, while a climbing local-player candidate is rejected by the new predicate from 1.11 onward.
- **Reachable ladder witness:** For a non-spectator local player whose floored `(x, shape.minY, z)` block is an ordinary ladder, `isClimbing()` returns true in both versions. With an eligible nearby living query owner (e.g. ordinary collision rules), its candidate predicate accepts the local player in 1.10.2 and rejects that player in 1.11 because `!isClimbing()` fails. The gate therefore removes this specific pusher-query → local-player-candidate path.
- **No blanket push prohibition:** The change is in candidate eligibility. `Entity.push(Entity)` retains its response and has no climbing check; `PlayerEntity.push(Entity)` adds a sleeping check and delegates, with no climbing check. The result must remain scoped to this candidate-filtered query path and must not be described as a general rule that every push involving a climbing local player is blocked.
- **Correction to frozen comparison:** Its statement that the local player's own `pushAwayCollidingEntities()` path “may still write local-player velocity while climbing” is not established by that call chain and is contradicted for the ordinary logical-client query by `EntityFilter`: candidates must be the local player, while `getEntities(this, ...)` excludes the querying entity itself. The path should not be cited as a reachable opposite-direction witness. This does not change the accepted receiver-eligibility difference above.

## Bounds

This is an independent audit of the stated filter-to-push operation, its local-player identity gate, and the ordinary ladder witness. It does not establish the exact intermediate release where the predicate changed, broader collision-query coverage, the trapdoor ascent case, overall movement-pair freeze, or runtime parity. No product source, wiki, Git history, backlog, generation, tests, build, or game/runtime execution was used.

## Immutable identities

Frozen comparison SHA-256: `7d143e119cea2ef523b40a3cf6efc732ebac55608d85c9d04161f05f292dbcdd`

Source-manifest SHA-256 values verified against ready markers and bytes:

- 1.10.2: `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`
- 1.11: `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`
- 1.11.1: `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d`
- 1.11.2: `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`
- 1.12.2: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`