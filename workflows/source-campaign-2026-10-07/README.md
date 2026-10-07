# Source-only movement campaign roster and handoff protocol

Campaign scope: player movement from Java 1.8.9 through the native 26.2 source release. New source-only reports belong under this directory as `<A>--<B>/run.md` and optional `findings/`; prior catalogs remain untouched. Wiki research has a separate directory and owner. Normal source workers may read prior discovery reports as navigation, but must not inspect old mod implementation, browse either wiki, or read wiki-audit reports until their source-only report is frozen.

## Remaining endpoint roster

Ten workers already cover the non-overlapping chain from 1.8.9 through 1.18.2. Queue the following remaining intervals. Each interval is a fresh vanilla source comparison; the final hotfix endpoint closes that content window. The human-specified 1.21 checkpoints are kept separate. The independent release-taxonomy audit may adjust the “latest hotfix” endpoint before dispatch, but its findings stay isolated until source reports are frozen.

| Older A | Newer B | Boundary label |
|---|---|---|
| 1.18.2 | 1.19.4 | Wild Update line, latest listed hotfix |
| 1.19.4 | 1.20.6 | Trails & Tales line, latest listed hotfix |
| 1.20.6 | 1.21.1 | Tricky Trials line, latest listed hotfix |
| 1.21.1 | 1.21.2 | Bundles of Bravery checkpoint |
| 1.21.2 | 1.21.4 | The Garden Awakens checkpoint |
| 1.21.4 | 1.21.5 | Spring to Life checkpoint |
| 1.21.5 | 1.21.6 | Chase the Skies checkpoint |
| 1.21.6 | 1.21.9 | The Copper Age checkpoint |
| 1.21.9 | 1.21.11 | Mounts of Mayhem checkpoint |
| 1.21.11 | 26.1.2 | 26.1 line, latest listed hotfix |
| 26.1.2 | 26.2 | Native 26.2 endpoint |

The listed 1.21 versions must not be collapsed into a single 1.21.x interval. A worker reports only its exact interval; it must not claim a whole major line. Endpoints prove a difference somewhere in that interval unless intervening exact sources establish the first changed release. The independent taxonomy owner should confirm whether a patch such as 1.21.3 is the latest hotfix for a named content update before work is queued; do not silently replace the human-listed checkpoints.

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
