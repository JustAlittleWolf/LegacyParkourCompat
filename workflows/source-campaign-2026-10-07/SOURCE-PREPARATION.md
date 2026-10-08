# Movement source campaign preparation

## Shared source store

Canonical store: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\`

- `artifacts/` holds Mojang version metadata, exact client jars, mappings, and decompiler dependencies.
- `staging/<unique-run-id>/<version>/<namespace>/` is a fresh destination for each decompile run.
- `ready/<version>/<namespace>/` contains the published, immutable Java source tree.
- `ready/<version>/<namespace>.ready.json` records the exact release ID and namespace, source and artifact manifest hashes, source counts, and movement-method diagnostic status. A marker is written last.
- `ready/<version>/<namespace>.provenance.json` records the exact Gradle invocation, staging/cache roots, and tool versions. New runs retain the complete Gradle stream under their unique staging root; older runs may have only a concise success excerpt because their original streams were displayed in Codex but not persisted.
- `decompile.lock` is the shared exclusive admission lock. Hold an open file handle with `FileShare.None` for the entire decompile and publish operation. A file's mere existence does not mean the lock is held.
- `revisions/derived-artifact-snapshots/feather-r1-2026-10-07/` contains the revised immutable artifact snapshots for six early Feather runs. Read `ARTIFACT-REVISION-2026-10-07.md` before relying on any artifact provenance.

Run decompiles serially with `GRADLE_USER_HOME=C:\Users\Wolfi\.gradle`, a 4G decompiler heap, and explicit absolute `--output-root` and `--cache-directory` paths. Use a fresh isolated cache for every run; do not overwrite artifact paths referenced by a published manifest. Persist the complete stdout/stderr for new runs under the unique staging run root. Snapshot derived mapped artifacts into a distinct versioned path before publication and hash them there. Never point a task at a ready tree: the Gradle task clears its selected output directory before writing. Check that a ready target does not exist, generate into a fresh staging root, confirm the exact ID and mapping namespace, inspect required player movement sources, and hash the complete source tree and relevant input artifacts before moving that one tree into `ready/`. Write the marker only after those checks. Do not replace published trees or markers; use a new versioned namespace/run if a correction is needed.

The current namespace plan is Feather for older releases where available, both Feather and Mojmap for 1.14.4, Mojmap for newer releases that publish official mappings, and `unobfuscated` for native 26.x. Source-only researchers read `ready/` and never write to `artifacts/`, `staging/`, or `ready/`.

## Ready versions

- The 31 ready marker sets are: Feather `1.8.9`, `1.9.4`, `1.10.2`, `1.11.2`, `1.12.2`, `1.13.2`, and `1.14.4`; Mojmap `1.14.4`, `1.15.2`, `1.16.1`, `1.16.2`, `1.16.5`, `1.17.1`, `1.18.2`, `1.19.2`, `1.19.3`, `1.19.4`, `1.20.1`, `1.20.2`, `1.20.4`, `1.20.5`, `1.20.6`, `1.21.1`, `1.21.3`, `1.21.4`, `1.21.5`, `1.21.8`, `1.21.10`, and `1.21.11`; unobfuscated `26.1.2` and `26.2`. The exact requested endpoint chain and supplemental versions are listed in [the release roster](../../source-inventory-2026-10-07/RELEASE-ROSTER.md).
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
