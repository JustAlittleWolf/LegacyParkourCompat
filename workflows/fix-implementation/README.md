# Implementing a movement finding

## Inputs and acceptance

Implement one independently scoped, source-confirmed finding as a minimal historical delta. Runtime comparison belongs to separately authorized validation.

Before editing, read the root [README](../../README.md), [AGENTS.md](../../AGENTS.md), the finding and its discovery manifest. Required inputs are the immutable finding commit/path/hash, independent blind acceptance, intended historical behavior, exact release-boundary evidence, closed finding-specific dependencies, artifact publication identities, code base SHA and build target. Use the [campaign snapshot gate](../source-campaign-2026-10-07/README.md#incremental-finding-to-implementation-handoff) for early handoffs from partial pairs.

Candidates, unresolved dependencies and uncertain required version boundaries block implementation. Endpoints A and B locate a difference within `(A, B]`; inspect intervening exact sources before registering a specific boundary or applying it to a whole `ParkourVersion` group. An accepted finding does not complete its pair. Do not send implementation-derived feedback to source-only owners before full-pair freeze.

## 1. Isolate and reconcile

Use a dedicated branch/worktree and assigned writable paths. No concurrent writers to the same checkout. Inspect the current code and record the finding as implemented, partially implemented, intentionally excluded or open, with exact code/hook/registration evidence. Preserve existing mechanics; add only the missing delta. A correct no-code disposition is a valid result.

Keep an implementation record using [templates/fix.md](templates/fix.md), including accepted inputs, boundary, current hook, disposition, checks, integration conflicts and unresolved runtime cases. Generated sources, jars and raw diffs stay outside Git.

## 2. Locate the current vanilla operation

Resolve `minecraft_version` from `gradle.properties` and confirm the build configuration. Read the assignment's canonical source roots/readiness manifests and verify the exact target, mapping namespace, cited hashes and method bodies. Missing artifacts go to the preparation owner; workers must not decompile shared sources, create local fallback trees or add junctions.

Preparation owners follow [source preparation](../movement-discovery/source-preparation.md) and [buildSrc](../../buildSrc/README.md). Use the repository decompiler, never online mappings. Do not use source paths from historical campaign records as current readiness inputs.

Trace the finding's old/new chains into the current player path through callers, inheritance, block/fluid callbacks and side-specific code. Select the smallest operation, return value, field access or method boundary exposing the delta. Verify the descriptor, guards and execution order. Prefer a reusable shared hook without changing required timing. Put client-only injections in `src/client/` and shared injections in `src/main/`.

## 3. Separate bridge and historical behavior

The mixin exposes inputs/result or an original call, identifies the player, resolves the hook through `MovementRuntime` and dispatches. Name it for its Minecraft location/operation, such as `PhysicsTickMixin`. Extend an existing injection at the correct point rather than adding a parallel one. Historical version checks, block selection and formulas belong in the change class.

Each interface in `mechanic/hook/` represents one independently resolved operation and its vanilla fallback. A change may implement several narrow interfaces when one historical behavior owns those operations; register each interface independently.

Put historical behavior in a plain Java class under `src/main/java/me/wolfii/legacyparkourcompat/change/v<emulated-version>/`, named for the behavior and annotated `@MovementChange(emulates = ParkourVersion.<version>)`. No injection annotations or behavior inherited from another release's change class. Share neutral helpers only when historical operations are identical. Vanilla accessors/invokers must preserve the exact math and gates.

Register every implemented interface in the matching explicit per-version method of the single static `MovementChangeCatalog`; add new mixins to the correct config. Do not create per-version providers or movement entrypoints.

`emulates` identifies the reproduced behavior, not automatically the newer endpoint. Check `ParkourVersion`, `ChangeResolver`, variants and existing changes for that key: the closest applicable historical delta must win. Avoid exact-profile guards that defeat resolver applicability. Keep vanilla block states and limit historical content to era-existing features and player movement.

## 4. Preserve exact behavior and fallback

Match literal types, casts, evaluation order, intermediate rounding, `Math`/`Mth` choice, comparisons, branch/callback order, state writes and persistence. Verify helpers, attributes, tags, defaults and synchronized inputs. If the hook cannot preserve timing/math, refine its bridge. Do not duplicate whole movement loops or smooth out historical quirks.

Preserve vanilla behavior when emulation is disabled, no hook resolves or the entity is not a player. Check historical applicability, concrete preconditions, adjacent-version overrides and interactions with other mechanics. Broken movement invariants must fail visibly. Do not add movement-loop unit/mock tests.

## 5. Review, build and integrate

Commit the candidate and obtain independent technical review of the exact code: source fidelity, hook descriptor/timing, resolver direction, catalog registration, interactions and vanilla fallback. Hand accepted candidates to the serial integration/build owner with their source and review identities.

The owner merges the default branch, checks new commits for semantic conflicts and reconciles overlapping injections, keys and registrations. Shared bridges dispatch through separate hooks; release implementations remain independent. Review affected code after merge corrections.

Build the integrated code with every Gradle `Test` task disabled, including subprojects, plus `-x test`. A command such as `./gradlew build -x test --init-script <disable-all-tests-script>` (`.\gradlew.bat` on Windows) uses the build owner's verified disabling script. Do not assume `-x test` alone excludes every test task. Record the exact integrated code/artifact identities and result; docs-only changes do not require another build.

Tests, clients, TAS, Gym, servers and Docker require explicit user authorization. Compilation/static review do not establish runtime movement parity. Commit the integrated result and hand off its disposition and unresolved runtime cases.
