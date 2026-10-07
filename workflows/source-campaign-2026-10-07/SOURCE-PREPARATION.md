# Movement source campaign preparation

## Shared source store

Canonical store: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\`

- `artifacts/` holds Mojang version metadata, exact client jars, mappings, and decompiler dependencies.
- `staging/<unique-run-id>/<version>/<namespace>/` is a fresh destination for each decompile run.
- `ready/<version>/<namespace>/` contains the published, immutable Java source tree.
- `ready/<version>/<namespace>.ready.json` records the exact release ID and namespace, source and artifact manifest hashes, source counts, and reviewed movement-method diagnostics. A marker is written last.
- `ready/<version>/<namespace>.provenance.json` records the exact Gradle invocation, staging/cache roots, and tool versions. Its sibling `<namespace>.success.log` is a concise success excerpt for the initial batches; those original Gradle streams were displayed in Codex but not persisted as raw log files.
- `decompile.lock` is the shared exclusive admission lock. Hold an open file handle with `FileShare.None` for the entire decompile and publish operation. A file's mere existence does not mean the lock is held.

Run decompiles serially with `GRADLE_USER_HOME=C:\Users\Wolfi\.gradle`, a 4G decompiler heap, and explicit absolute `--output-root` and `--cache-directory` paths. Persist the complete stdout/stderr for new runs under the unique staging run root. Never point a task at a ready tree: the Gradle task clears its selected output directory before writing. Check that a ready target does not exist, generate into a fresh staging root, confirm the exact ID and mapping namespace, inspect required player movement sources, and hash the complete source tree and relevant input artifacts before moving that one tree into `ready/`. Write the marker only after those checks. Do not replace published trees or markers; use a new versioned namespace/run if a correction is needed.

The current namespace plan is Feather for older releases where available, both Feather and Mojmap for 1.14.4, Mojmap for newer releases that publish official mappings, and `unobfuscated` for native 26.x. Source-only researchers read `ready/` and never write to `artifacts/`, `staging/`, or `ready/`.

## Ready versions

- `1.8.9/ornithe-feather` — ready; 1,612 Java files.
- `1.9.4/ornithe-feather` — ready; 1,819 Java files.
- `1.10.2/ornithe-feather` — ready; 1,845 Java files.
- `1.11.2/ornithe-feather` — ready; 1,921 Java files.
- `1.12.2/ornithe-feather` — ready; 2,050 Java files.
- `1.13.2/ornithe-feather` — ready; 2,711 Java files.
- `1.14.4/ornithe-feather` and `1.14.4/mojmap` — ready; each 3,250 Java files.
- `1.15.2/mojmap` — ready; 3,332 Java files.
- `1.16.1/mojmap` — ready; 3,572 Java files (supplemental boundary source).
- `1.16.2/mojmap` — ready; 3,516 Java files (supplemental boundary source).
- `1.16.5/mojmap` — ready; 3,523 Java files.
- `1.17.1/mojmap` — ready; 4,142 Java files.
- `1.18.2/mojmap` — ready; 4,236 Java files.
- `1.19.2/mojmap` — ready; 4,480 Java files.
- `1.19.3/mojmap` — ready; 4,618 Java files.
- `1.19.4/mojmap` — ready; 4,740 Java files.
- `1.20.1/mojmap` — ready; 4,786 Java files.
- `1.20.2/mojmap` — ready; 4,904 Java files.
- `1.20.4/mojmap` — ready; 5,048 Java files.
- `1.20.6/mojmap` — ready; 5,329 Java files.
- `26.2/unobfuscated` — ready; 7,055 Java files.

The active preparation batch is `1.21.1/mojmap`, `1.21.3/mojmap`, `1.21.4/mojmap`, and `1.21.5/mojmap`.

See each `ready/<version>/` directory for its marker, source manifest, artifact manifest, and movement diagnostics.

## Source-only report protocol

Write each frozen report under `workflows/source-campaign-2026-10-07/<A>--<B>/` as `run.md` plus `findings.md`.

1. Compare the exact adjacent endpoint IDs assigned to the worker. Record both source-tree paths and namespace names.
2. Use only exact decompiled Minecraft source and bytecode as evidence for movement behavior. Cite source file and line for each difference; inspect bytecode when decompilation obscures operation order, casts, or overloads.
3. Limit findings to player movement and historical block collision shapes. Exclude entity movement and health, hunger, food, regeneration, saturation, exhaustion, and indirect sprint-related behavior.
4. Report a candidate only when both endpoints and the source evidence are confirmed. Include the relevant condition, calculations and operation order, affected movement operation, and whether the result is a new mechanic or a change to an existing one. Record checked areas with no movement difference briefly.
5. Do not read existing or old mod implementation code. Previous discovery reports may be read. Freeze the source-only reports before consulting any release-taxonomy or MCPK wiki audit.
6. Do not run tests, clients, Minecraft, TAS, Gym, servers, or Docker. Source decompilation and static source/bytecode inspection are the authorized workflow.

