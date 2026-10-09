# Source campaign protocol

## Scope and reports

Compare player movement across the exact Java 1.8.9–26.2 intervals below using the [discovery workflow](../movement-discovery/README.md). Own `<A>--<B>/run.md` and optional `findings/` under this directory, using the [run template](../movement-discovery/templates/run.md). Keep prior reports and immutable snapshots intact.

Source workers and blind reviewers may use prior source reports as navigation, but must not read mod implementation, either wiki or wiki-audit results before full-pair freeze. Wiki research has separate owners and reports. Accepted finding snapshots do not release source isolation.

## Exact endpoint roster

Assign each adjacent pair independently. Do not merge intervals or infer coverage between nonadjacent endpoints.

| # | Older A | Newer B |
|---:|---|---|
| 1 | 1.8.9 | 1.9.4 |
| 2 | 1.9.4 | 1.10.2 |
| 3 | 1.10.2 | 1.11.2 |
| 4 | 1.11.2 | 1.12.2 |
| 5 | 1.12.2 | 1.13.2 |
| 6 | 1.13.2 | 1.14.4 |
| 7 | 1.14.4 | 1.15.2 |
| 8 | 1.15.2 | 1.16.5 |
| 9 | 1.16.5 | 1.17.1 |
| 10 | 1.17.1 | 1.18.2 |
| 11 | 1.18.2 | 1.19.2 |
| 12 | 1.19.2 | 1.19.3 |
| 13 | 1.19.3 | 1.19.4 |
| 14 | 1.19.4 | 1.20.1 |
| 15 | 1.20.1 | 1.20.2 |
| 16 | 1.20.2 | 1.20.4 |
| 17 | 1.20.4 | 1.20.6 |
| 18 | 1.20.6 | 1.21.1 |
| 19 | 1.21.1 | 1.21.3 |
| 20 | 1.21.3 | 1.21.4 |
| 21 | 1.21.4 | 1.21.5 |
| 22 | 1.21.5 | 1.21.8 |
| 23 | 1.21.8 | 1.21.10 |
| 24 | 1.21.10 | 1.21.11 |
| 25 | 1.21.11 | 26.1.2 |
| 26 | 26.1.2 | 26.2 |

Endpoints locate a difference within an interval, not its first changed release. Inspect intervening exact sources when an implementation requires a specific boundary. Do not claim a whole release line from one comparison.

## Ownership and admission

- **Coordinator:** use the [orchestration workflow](../orchestration/README.md) to assign disjoint pair/report ownership, blind finding reviewers and separate full-pair audits. Requeue partial work by exact open slice/dependency.
- **Preparation owner:** follow [source preparation](../movement-discovery/source-preparation.md) to serialize shared decompilation/cache writes and publish verified readiness manifests. Workers consume the manifest's exact paths read-only.
- **Source worker:** verify exact endpoint/namespace readiness and cited hashes, then continue the assigned inventory/dependency queue. Missing artifacts block dependent slices; report the precise identity/path to the preparation owner. Do not decompile, create fallback sources, write the shared roster/cache, modify other reports or edit Java implementation.
- **Integration/build owner:** serialize shared build state and default-branch integration. Blind source owners do not inspect implementation diffs during main merges; integrate their report-only commits separately.
- **All roles:** tests, clients, TAS, Gym, servers and Docker require explicit user authorization. Builds must disable every Gradle Test task, including subprojects, plus `-x test`.

Source workers follow the [navigation stages](../movement-discovery/source-navigation.md) and template inventories through the full input/pre-travel/travel/post-travel path, state writers, collision providers/registrations/neighbors, resource-backed movement inputs and external player influences. Preserve exact math/order and vanilla block states. Health/food production, attack/damage resolution, non-player movement and vehicle physics are excluded; direct player motion responses and predicates reading vanilla state remain eligible.

## Incremental finding-to-implementation handoff

1. **Source readiness:** mark the finding `source-confirmed`; close its own dependencies; cite paired exact source ranges or checked absence paths, reachable player path/preconditions, source/resource hashes and artifact identities. Establish historical phase/operation and release applicability from vanilla source. Candidates, unresolved evidence or a missing required version boundary cannot enter implementation. Source owners do not design mod hooks.
2. **Blind finding review:** a reviewer who did not author the finding and has not inspected implementation/wiki information verifies the exact snapshot's scope, reachability, both-side evidence, hashes and dependency closure. Record `accepted`, `rejected` or `revision-required`, with reviewer/date and source-only basis.
3. **Immutable acceptance:** commit the finding snapshot and append its [snapshot event](../movement-discovery/templates/run.md#finding-snapshots-not-pair-freeze). Record finding ID(s), commit/path/file SHA-256, artifact-manifest/publication identities, cited source/resource hashes, verified boundary evidence, closed dependency IDs, reviewer/decision/date, pair status/commit and handoff `ready` or `blocked`. Include revised-artifact lineage and provenance limitations where applicable. Pair completion remains `no`.
4. **Separate implementation:** give the implementation chat the accepted finding/review identities, exact source/boundary evidence and current code checkout. Track implementation status/commit in its own record. Before full-pair freeze, source reports record source acceptance only; no implementation results or feedback return to the source owner.
5. **Revisions:** changed finding/source/artifact bytes, changed publication identity or challenged finding-specific evidence require an invalidation/supersession event identifying the prior snapshot, reason and replacement commit/hash. Preserve old records and verdicts; obtain fresh blind acceptance before relying on replacement evidence.

A committed blind acceptance bound to exact unchanged bytes and closed dependencies takes precedence over an older pending/provisional label. Mirror the exact decision and verification references in the current source-review ledger and integration index; preserve earlier entries. Correct status metadata without changing accepted evidence or requiring a status-only resnapshot. Unrelated open pair slices do not block an accepted finding's dispatch.

For revised artifacts, follow the [revision rules](../movement-discovery/source-preparation.md#revised-derived-artifacts-and-handoff-identity). Record independently resolved repair conditions for their exact publication and verification reference. Keep unavailable originals and unproven equivalence explicit; any claim depending on that equivalence remains unresolved.

## Full-pair audit and completion

A full-pair reviewer who did not author the discovery remains blind to implementation/wiki information and independently re-walks both exact sources and the complete player tick graph. Check every required inventory, paired method/body range, state producer/consumer edge, shape provider/registration/neighbor, direct predicate and excluded system. Route each missed behavior to a concrete coverage slice/dependency and owner before passing. Finding reviews do not replace this audit.

Full-pair freeze requires all source inventories/slices terminal, closed dependencies, a frozen pair manifest and the independent coverage audit. Only then may the source owner receive implementation reconciliation or wiki information. Finding acceptance, implementation and runtime validation remain separate.

From the repository root, run `python workflows/movement-discovery/check_completion.py workflows/source-campaign-2026-10-07/<A>--<B>/` (`py -3` on Windows). The checker validates schema/status declarations, not source truth or artifact bytes. Structurally valid active/partial/blocked reports exit 0 without claiming closure. A complete report must satisfy terminal slice/dependency states, full-pair freeze, implementation reconciliation and passed audit. Do not change an open pair's status merely because a snapshot, worker or build completed.
