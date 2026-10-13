# Agent guidance

Read [README.md](README.md) for project scope, architecture and commands. Before working in a development module, read its guide: [TAS](development/tas-client/README.md), [Gym](development/parkourgym-server/README.md), [test coordination](development/testing/README.md), or [buildSrc](buildSrc/README.md).

## Invariants

- Emulate historical client **player movement** for old parkour maps with deterministic tick-level parity, including bugs. Keep vanilla block states. Historical shapes of era-existing blocks and direct player responses/predicates reading vanilla state are in scope. Rendering, vehicle/non-player physics, modern-only features, health/food production, damage calculation and bed-entry eligibility are excluded. Profile switching is undefined behavior.
- Historical movement must not run on logical servers, including integrated servers. Servers enforce the selected profile and relax conflicting movement checks only for eligible connections; they do not replay historical physics.
- Implement independent minimal deltas in `change/`. `ChangeResolver` selects the closest applicable change from the selected version or later, per interface. One topic-organized `*Hook` interface represents one independently resolved technical operation. Register every implemented interface in the single static `MovementChangeCatalog`; no per-version providers, duplicated loops or cross-release change inheritance.
- Keep client mixins thin and general; dispatch through `MovementRuntime`. Share only neutral helpers with identical historical behavior. Vanilla accessors/invokers must match the historical math and gates. Preserve native fallback.
- Exact-version decompiled source is primary evidence. Preserve operation order, casts, rounding and gates. Use `decompileMinecraft` for mappings, never online mapping lookups. Shared sources live in the primary local project and have one writer; consumers are read-only. Never swallow errors: crash on broken movement invariants and log expected failures with `LegacyParkourCompat.LOGGER`.
- Do not preserve compatibility with older releases of this mod. Ask before heavy Java/physics libraries or core version-resolution refactors.

## Working procedures

Before performing or reviewing a phase, read its procedure in full: **coordinate work:** [orchestration](development/workflows/orchestration/WORKFLOW.md); **discover differences:** [discovery](development/workflows/discovery/WORKFLOW.md) and the assigned lane; **implement or integrate:** [implementation](development/workflows/implementation/WORKFLOW.md); **plan or execute verification:** [testing](development/workflows/testing/WORKFLOW.md). Read all matching procedures once per task; reread when changed. These are agent instructions loaded by task, not automatically discovered skills. Current procedures belong in `development/workflows/`; findings and state belong in `development/research/`. Implemented behavior and exact source references belong in Change source comments; research retains permanent findings and current relevant state.

Keep source discovery blind to concrete implementations and wiki lanes until the pair's source coverage is frozen. Independently accepted findings with closed dependencies may enter implementation early. Pair completion additionally requires complete coverage and independent audit. Keep acceptance, implementation and runtime parity separate. Preserve existing implementations; use a sanitized export for independent candidates and reconcile in the product checkout.

An agent is a worker without delegation rights unless explicitly assigned as an under-orchestrator. Every delegation states scope, inputs, owned paths, output and delegation limits. The master's global concurrency pool counts its direct workers and under-orchestrators, not the under-orchestrators' subworkers. Each under-orchestrator manages a separately assigned worker limit within actual host capacity. Overall time and resource budgets still apply to all descendants. Use model capabilities/cost appropriate to the task without hardcoding model names into procedures.

## Verification

After code changes, the integration/build owner builds with every Gradle `Test` task disabled, including subprojects, and `-x test`: `gradlew.bat build -x test --init-script development/disable-tests.gradle` on Windows, `./gradlew` on Linux/macOS. Tests, game/TAS/Gym/server launches and Docker restarts require explicit authorization. No movement-loop unit/mock tests. Documentation-only changes need static checks, not a build. Compilation does not prove movement parity.
