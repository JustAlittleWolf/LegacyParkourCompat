# Source-only movement campaign roster and handoff protocol

Campaign scope: player movement from Java 1.8.9 through the native 26.2 source release. New source-only reports belong under this directory as `<A>--<B>/run.md` and optional `findings/`; prior catalogs remain untouched. Wiki research has a separate directory and owner. Normal source workers may read prior discovery reports as navigation, but must not inspect old mod implementation, browse either wiki, or read wiki-audit reports until the full pair is source-frozen. Accepted finding snapshots do not release this isolation.

## Exact 26-pair endpoint roster

The coordinator and source owner validated this endpoint sequence; use each adjacent pair as one independent source-only assignment. Do not merge intervals or claim coverage between nonadjacent endpoints.

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

This chain retains each named content update/gamedrop endpoint and the conservative 1.19.3, 1.19.4 and 1.20.2 checkpoints. An endpoint comparison establishes a difference somewhere in that interval; establish a first changed version only by inspecting intervening exact sources. A run must not claim a whole release line from a single interval.

## Shared source preparation and build admission

1. Name one preparation owner for shared Gradle decompilation/cache writes. That owner alone runs the decompiler, serially, for exact release/mapping pairs. No source worker invokes `decompileMinecraft`, creates a worktree-local fallback, changes the shared cache, or removes/replaces generated output.
2. After each successful task, the owner publishes a readiness record with exact requested/resolved release IDs, command and exit status, mapping family/build, source root, original/mapped jar and mapping hashes, relevant tool versions, and any decompiler warnings. The owner writes a temporary record and atomically renames it to `ready-<version>-<mapping>.json` only after validation. The marker includes the source file inventory/hash manifest.
3. A worker reads the exact pair's readiness markers and verifies source roots and cited hashes before starting. It uses the shared trees read-only. Missing/stale marker or hash mismatch means stop that slice and report the precise dependency; never “fix” it by launching another decompile.
4. One named owner serializes repository-wide Gradle build/static work when shared `build/` state could overlap. Workers do source reads and isolated text/static checks only. No tests, Minecraft clients, TAS, Gym, server, Docker, or runtime validation until the coordinator authorizes it. Any allowed build must explicitly exclude tests.
5. Workers own only their assigned new report folder and its `run.md`/`findings/`; no shared roster, source cache, prior report, or Java implementation edits. They may publish independently accepted immutable finding snapshots while the pair remains partial, but may not inspect implementation or receive implementation-derived feedback until full-pair freeze. Partial reports are requeued by exact open slice/dependency; completed ancestry and accepted snapshots are not accepted as pair coverage.

## Pasteable worker checklist

- Exact endpoints, mapping pair, source owner and readiness hashes verified; report goes in this campaign directory.
- Fresh source-only discovery; prior discovery reports are navigation only; no old mod implementation, no wiki browsing, no wiki-audit findings before freeze.
- Inventory full player tick call graph: input, pre-travel, every travel branch, post-travel; exact method/body ranges and producer → consumer dependencies.
- Explicit inventories: pose/dimensions/eye height and writers; collision shapes/providers, registrations and neighboring blocks; fluids, block callbacks/data; movement attributes/effects/enchantments/equipment; external player movement influences.
- Exclude health/food state production, attack/damage resolution, non-player movement and vehicle physics. Predicates may read vanilla values without emulating their producer systems. Direct player velocity/impulse/knockback application remains in scope when reachable, even if combat can trigger it; do not inspect or emulate the combat cause or damage calculation.
- Keep every slice pending/in-progress/terminal/blocked with both-side evidence. Pending, in-progress, blocked, dependencies, or unreviewed misses forbid `complete`.
- Freeze each implementation handoff as an exact finding snapshot with independent blind source-review acceptance; this does not freeze the pair. Keep source discovery, implementation disposition, and runtime validation separate.
- Independent reviewers record finding-snapshot acceptance separately from the full-pair audit; only the latter can support pair completion after every slice closes. Build/static only; no tests or runtime launches without coordinator authorization.

## Incremental finding-to-implementation handoff

This gate allows implementation to proceed from findings whose evidence is complete while source discovery continues elsewhere in the pair. It does not relax any source-coverage or pair-completion criterion.

1. The source author marks a finding `source-confirmed`, closes that finding's dependencies, cites paired exact-version source ranges/absence paths, and records source/resource SHA-256 values plus the artifact-manifest identity. Verify the implementation emulation boundary from exact versions when implementation needs one; endpoints alone do not establish the first changed release. Candidate findings, unresolved evidence/dependencies, or an unresolved required implementation boundary cannot be dispatched for code implementation.
2. A reviewer who did not author the finding and has not inspected implementation or wiki-audit information reviews its reachability/preconditions, both-side evidence, hashes, dependency closure, and finding scope. Record a decision (`accepted`, `rejected`, or `revision required`), reviewer/date, and concise source-only basis.
3. For acceptance, commit an immutable snapshot. In the pair's `run.md` snapshot log record: snapshot ID; finding ID(s); snapshot commit; finding-file SHA-256; exact A/B artifact-manifest/source hashes; verified implementation boundary/evidence; closed dependency IDs; blind reviewer and decision; timestamp; pair run status/commit at handoff; and `pair complete: no`. Record whether implementation handoff is `ready` or `blocked` with its reason. The commit/hash, not a mutable working-tree path, is the implementation input.
4. A separate implementation chat receives only the accepted snapshot commit/hash, finding file, relevant exact source evidence, and current code checkout. Record its implementation status (`not started`, `active`, `implemented`, `intentionally excluded`, or `open`) and implementation commit separately in that implementation task. Do not send implementation-derived observations/status/feedback to the source-only owner before full-pair source freeze. The pair's source report records snapshot acceptance only; it does not record implementation results before freeze.
5. If the finding/source evidence changes, append a snapshot event marking the old snapshot `invalidated` or `superseded`, identify its prior ID/hash and reason, and create a new snapshot ID/commit/hash. Preserve the old entry in Git history. Obtain a fresh independent source review before further implementation relies on the replacement snapshot.
6. Full-pair source freeze remains a distinct status and requires all inventories/slices terminal, closed dependencies, frozen pair manifest and independent complete call-graph audit. Only then may the source owner receive implementation reconciliation or isolated wiki-audit information. Neither a snapshot acceptance nor one or more implementations can mark the pair complete.

The pair completion checker remains strict and unchanged by snapshot acceptance: for a `complete` run it requires closed slices/dependencies, full-pair freeze, reconciliation and passed independent audit. A partial report may contain accepted snapshots and still exits as `partial`; its completion status stays open.

## Rollout instructions

- **Coordinator:** assign one source worker to each exact adjacent pair above, give each a separate report directory, assign blind finding-snapshot reviewers and separate full-pair coverage reviewers. Requeue any partial report by its exact open slice/dependency; a finished worker turn is not a coverage result.
- **Source-preparation owner:** publish and maintain readiness markers for the exact source/mapping pair of every endpoint. Workers are read-only consumers. Serialize shared decompile/cache writes and any shared Gradle build/static check.
- **Source worker:** copy `workflows/movement-discovery/templates/run.md` into `workflows/source-campaign-2026-10-07/<A>--<B>/run.md`, then create `findings/` only when findings exist. Prior discovery reports are permitted as navigation. Do not inspect old mod implementation or wiki-audit output, and do not browse either wiki. Keep the pair open while any slice/dependency remains; source-confirmed findings may be submitted for the separately gated snapshot review above.
- **Finding-snapshot reviewer:** independently review only the exact finding snapshot's source proof, reachability, artifact/evidence hashes and dependency closure. Stay blind to implementation and wiki reports. Acceptance releases only that snapshot for separate implementation work.
- **Full-pair reviewer:** remain blind to mod implementation and wiki-audit information through the audit; independently re-walk both exact source trees and the entire player tick path, including pre-travel, all travel branches, and post-travel. Verify each named inventory has bounded method-range slices, state producer/consumer links, and evidence-backed dispositions. Route every miss to a concrete slice and owner; do not accept a stage summary or a narrow unchanged travel method as full-tick parity. Snapshot reviews do not replace this audit.
- **Completion gate:** from the repository root run `python workflows/movement-discovery/check_completion.py workflows/source-campaign-2026-10-07/<A>--<B>/`. Replace `<A>--<B>` with the exact directory, for example `1.18.2--1.19.2`. A structurally valid `active`, `partial`, or `blocked` report exits 0 with closure explicitly unclaimed. `complete` exits 0 only when the full schema/status gate passes; incomplete slices, open dependencies, or an unpassed audit make a `complete` report fail. A pass does **not** prove source truth; reviewers still perform and record the independent audit.
- **All owners:** no tests, game clients, TAS, Gym, server, or Docker launches. Keep discovery, implementation disposition, and runtime validation separate. Builds/static checks only, coordinated by the shared build owner.
