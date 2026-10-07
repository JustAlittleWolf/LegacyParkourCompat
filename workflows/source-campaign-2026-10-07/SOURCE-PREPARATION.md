# Movement source campaign preparation

## Shared source store

Canonical store: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\`

- `artifacts/` holds Mojang version metadata, exact client jars, mappings, and decompiler dependencies.
- `staging/<unique-run-id>/<version>/<namespace>/` is a fresh destination for each decompile run.
- `ready/<version>/<namespace>/` contains the published, immutable Java source tree.
- `ready/<version>/<namespace>.ready.json` records the exact release ID and namespace, source and artifact manifest hashes, source counts, and reviewed movement-method diagnostics. A marker is written last.
- `decompile.lock` is the shared exclusive admission lock. Hold an open file handle with `FileShare.None` for the entire decompile and publish operation. A file's mere existence does not mean the lock is held.

Run decompiles serially with `GRADLE_USER_HOME=C:\Users\Wolfi\.gradle`, a 4G decompiler heap, and explicit absolute `--output-root` and `--cache-directory` paths. Never point a task at a ready tree: the Gradle task clears its selected output directory before writing. Check that a ready target does not exist, generate into a fresh staging root, confirm the exact ID and mapping namespace, inspect required player movement sources, and hash the complete source tree and relevant input artifacts before moving that one tree into `ready/`. Write the marker only after those checks. Do not replace published trees or markers; use a new versioned namespace/run if a correction is needed.

The current namespace plan is Feather for older releases where available, both Feather and Mojmap for 1.14.4, Mojmap for newer releases that publish official mappings, and `unobfuscated` for native 26.x. Source-only researchers read `ready/` and never write to `artifacts/`, `staging/`, or `ready/`.

## Ready versions

- The latest shared-store snapshot has readiness records through `1.20.6`, plus the boundary sources `1.16.1` and `1.16.2`, and `26.2` (including `1.8.9/ornithe-feather`, `1.9.4/ornithe-feather`, `1.14.4` in Feather and Mojmap, and `26.2/unobfuscated`). The `1.21.x` batch remains in progress or queued.
- Before comparing a pair, each worker reads both actual readiness JSON files and verifies exact requested/resolved IDs, namespace, source/artifact/diagnostics hashes, and the cited method bodies. A directory listing or a different pair's marker is not evidence that the requested pair is ready.

See each `ready/<version>/` directory for its marker, source manifest, artifact manifest, and movement diagnostics.

## Source-only report protocol

Write each report under `workflows/source-campaign-2026-10-07/<A>--<B>/` as `run.md` plus optional `findings/<finding-id>.md` files, following the canonical [movement discovery workflow](../movement-discovery/README.md) and its current template.

1. Compare the exact adjacent endpoint IDs assigned to the worker. Record both source-tree paths and namespace names.
2. Use only exact decompiled Minecraft source and bytecode as evidence for movement behavior. Cite source file and line for each difference; inspect bytecode when decompilation obscures operation order, casts, or overloads.
3. Limit findings to player movement and historical block collision shapes; exclude non-player movement. Do not emulate health, hunger/food, regeneration, saturation, exhaustion, damage, or combat systems, even when those systems can affect sprinting. Keep a separately evidenced movement-state predicate that reads vanilla state in scope when it directly implements a player movement operation.
4. Report a candidate only when both endpoints and the source evidence are confirmed. Include the relevant condition, calculations and operation order, affected movement operation, and whether the result is a new mechanic or a change to an existing one. Record checked areas with no movement difference briefly.
5. Do not read existing or old mod implementation code. Previous discovery reports may be read. Freeze the source-only reports before consulting any release-taxonomy or MCPK wiki audit.
6. Do not run tests, clients, Minecraft, TAS, Gym, servers, or Docker. Source decompilation and static source/bytecode inspection are the authorized workflow.
