# Source-only movement campaign roster and handoff protocol

Campaign scope: player movement from Java 1.8.9 through the native 26.2 source release. New source-only reports belong under this directory as `<A>--<B>/run.md` and optional `findings/`; prior catalogs remain untouched. Wiki research has a separate directory and owner. Normal source workers may read prior discovery reports as navigation, but must not inspect old mod implementation, browse either wiki, or read wiki-audit reports until their source-only report is frozen.

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
5. Workers own only their assigned new report folder and its `run.md`/`findings/`; no shared roster, source cache, prior report, or Java implementation edits. Freeze the source-only report before any implementation reconciliation. Partial reports are requeued by exact open slice/dependency; completed ancestry is not accepted as coverage.

## Pasteable worker checklist

- Exact endpoints, mapping pair, source owner and readiness hashes verified; report goes in this campaign directory.
- Fresh source-only discovery; prior discovery reports are navigation only; no old mod implementation, no wiki browsing, no wiki-audit findings before freeze.
- Inventory full player tick call graph: input, pre-travel, every travel branch, post-travel; exact method/body ranges and producer → consumer dependencies.
- Explicit inventories: pose/dimensions/eye height and writers; collision shapes/providers, registrations and neighboring blocks; fluids, block callbacks/data; movement attributes/effects/enchantments/equipment; external player movement influences.
- Explicitly exclude health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement. Predicates may read vanilla values without emulating their producer systems.
- Keep every slice pending/in-progress/terminal/blocked with both-side evidence. Pending, in-progress, blocked, dependencies, or unreviewed misses forbid `complete`.
- Freeze source-only findings/coverage, then reconcile against current implementation. Keep source discovery, implementation disposition, and runtime validation separate.
- Independent reviewer re-walks all inventories and routes concrete missed slices. Build/static only; no tests or runtime launches without coordinator authorization.

## Rollout instructions

- **Coordinator:** assign one source worker to each exact adjacent pair above, give each a separate report directory, and assign one of the independent coverage reviewers. Requeue any partial report by its exact open slice/dependency; a finished worker turn is not a coverage result.
- **Source-preparation owner:** publish and maintain readiness markers for the exact source/mapping pair of every endpoint. Workers are read-only consumers. Serialize shared decompile/cache writes and any shared Gradle build/static check.
- **Source worker:** copy `workflows/movement-discovery/templates/run.md` into `workflows/source-campaign-2026-10-07/<A>--<B>/run.md`, then create `findings/` only when findings exist. Prior discovery reports are permitted as navigation. Do not inspect old mod implementation or wiki-audit output, and do not browse either wiki. Freeze the vanilla-source report before any implementation reconciliation.
- **Coverage reviewer:** independently re-walk both exact source trees and the entire player tick path, including pre-travel, all travel branches, and post-travel. Verify each named inventory has bounded method-range slices, state producer/consumer links, and evidence-backed dispositions. Route every miss to a concrete slice and owner; do not accept a stage summary or a narrow unchanged travel method as full-tick parity.
- **Completion gate:** from the repository root run `python workflows/movement-discovery/check_completion.py workflows/source-campaign-2026-10-07/<A>--<B>/`. Replace `<A>--<B>` with the exact directory, for example `1.18.2--1.19.2`. A structurally valid `active`, `partial`, or `blocked` report exits 0 with closure explicitly unclaimed. `complete` exits 0 only when the full schema/status gate passes; incomplete slices, open dependencies, or an unpassed audit make a `complete` report fail. A pass does **not** prove source truth; reviewers still perform and record the independent audit.
- **All owners:** no tests, game clients, TAS, Gym, server, or Docker launches. Keep discovery, implementation disposition, and runtime validation separate. Builds/static checks only, coordinated by the shared build owner.
