# Agent guidance

Read [README.md](README.md) for project scope, architecture and commands. Read the relevant module guide before work in [buildSrc](buildSrc/README.md), [parkourgym-server](parkourgym-server/README.md), [tas-client](tas-client/README.md) or [testing](testing/README.md).

## Invariants

- Emulate historical **player movement** on modern clients for old parkour maps, with deterministic tick-level parity, including historical bugs. Keep vanilla block states; historical collision shapes of era-existing blocks are in scope. Rendering, non-player/vehicle physics, modern-only features, health/food production and attack/damage resolution are excluded. Direct player movement responses and predicates reading vanilla state remain eligible. Profile switching is undefined behavior; correctness targets fixed profiles.
- Implement independent minimal deltas in `change/`. `ChangeResolver` selects the closest applicable change from the selected version or later. One hook represents one independently resolved technical operation. Register every implemented interface in the single static `MovementChangeCatalog`; no per-version providers, duplicated movement loops or cross-release change inheritance.
- Keep mixins thin and general; dispatch through `MovementRuntime`. Share only neutral helpers with identical historical behavior; use vanilla accessors/invokers only when their math and gates match exactly. Preserve native behavior when emulation is inactive.
- Exact-version decompiled source is primary evidence. Preserve operation order, casts, rounding, gates and quirks. Use `decompileMinecraft` for mappings, never online mapping lookups. Never swallow errors: crash on broken movement invariants; log expected failures with `LegacyParkourCompat.LOGGER` and handle them.
- Do not preserve compatibility with older releases of this mod. Ask before heavy Java libraries/physics engines or core version-resolution refactors.

## Research and orchestration

For campaign coordination, read [the orchestrator workflow](workflows/orchestration/README.md). Source workers and blind reviewers must read [discovery](workflows/movement-discovery/README.md) and [the campaign protocol](workflows/source-campaign-2026-10-07/README.md); implementers must read [implementation](workflows/fix-implementation/README.md).

Keep worker messages specific to their assignment: action, inputs, constraints and output. Write instructions and documentation without chat history, user-message attribution or unrelated campaign details.

Keep source discovery blind to implementation and both wiki lanes until full-pair freeze. Only independently accepted immutable findings may enter implementation early. Full tick/inventory coverage, dependency closure and independent coverage audit are required for discovery completion. Finding acceptance, implementation, build success and runtime parity are separate statuses. Shared sources have one writer and read-only consumers. Never erase existing implementations to obtain blind discovery; reconcile them separately after freeze.

## Verification

After code changes, build with every Gradle `Test` task disabled (including subprojects) and `-x test`, through the campaign build owner when applicable. Tests, game/TAS/Gym/server launches and Docker restarts require explicit user authorization. No movement-loop unit/mock tests; use the separately authorized input-simulation lab. Documentation-only work needs static checks, not a build. Compilation does not prove movement parity.
