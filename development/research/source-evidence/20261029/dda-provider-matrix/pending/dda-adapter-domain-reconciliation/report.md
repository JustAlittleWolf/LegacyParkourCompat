# DDA adapter domain reconciliation

## Decision

A per-query bounded adapter is technically possible, but the current bounds branch does not provide a safe, recoverable supported-query domain yet. A ray may be executed with legacy semantics only while the whole old block-provider cache history is known valid and every old cell/provider operation that can affect that history is covered. On an unsupported state or unobserved write, native fallback can avoid committing a partial emulation, but the existing product has no operation that re-establishes the historical cache state in-process. A fail-closed implementation would therefore remain native for the rest of the process/session after invalidation. Closing only Anvil and Chest or only a subset of current cells is not enough to restore validity.

## Evidence boundary

Product inspection used the isolated product worktree at commit `993707517116faa21584be5bd101a53ebcf5cc66`. The accepted traversal/provider contracts are:

- `dda-legacy-provider-closure/provider-and-hit-contract.md` (source-closed callback, old provider semantics and eligibility limits)
- `dda-default-provider-shape-closure/closure.md` (provider-input/cache-history requirements)

The accepted callback contract requires exact old `canRayTrace(state,false)`, ordered calls to the old provider, old miss handling, and first non-null hit short-circuit. The old provider mutates registry-object bounds; default ray providers can read prior bounds on a miss. The caller contract states that current `BlockGetter.traverseBlocks` cannot choose which historical cells get queried.

## Current product path

At the inspected commit, `MovementRuntime.withClientTickPick` (MovementRuntime.java:189-209) opens a marker only when Anvil/Chest writer hooks are registered. `withLocalPlayerPick` (215-233) further limits capture to the local player's no-fluid tick pick. `recordLegacyPickShape` (235-245) dispatches only the Anvil and Chest writers. The generic `BlockStateOutlineShapeMixin` calls this method before the modern OUTLINE query, but the runtime returns immediately outside that narrow scope.

The Anvil change shares one change-owned shape cache across three current anvil IDs and writes only the facing axis (AnvilCollisionShapeNativeFence.java:62-80, 99-105). Chest keeps a per-variant cache and reconstructs its current four-direction neighbor priority (ChestCollisionBounds.java:49-60, 74-91). The collision read hooks expose those caches to movement without checking a cache-validity state (AnvilCollisionShapeNativeFence.java:87-97; ChestCollisionBounds.java:93-101). There is no generic old DDA/provider adapter or validity/invalidation/recovery mechanism at this commit.

## Sufficient contract for a bounded adapter

A candidate could support an individual operation only under all of these explicit preconditions:

1. A cache-valid flag starts only after historical constructor defaults are seeded before any relevant query.
2. Run the accepted old traversal and provider logic as a single operation, retaining exact cell order, old state/metadata, provider-specific bound writes, miss behavior, and first-hit stop.
3. Resolve every visited cell and every old neighbor/paired-cell/block-entity read through a source-closed mapping. Pre-stage shared bound state so an unsupported provider/state discovered mid-walk cannot partially commit earlier writer effects.
4. Commit the staged cache and result only if the entire operation is supported. On any unknown cell/context, abort the result to the native clip and invalidate the entire historical cache mirror; movement then uses native shapes while invalid.
5. Restore validity only after a source-defined complete replay from known constructor defaults. A normal next query, world reload, or a successful query over only its visited cells is not such a reset because untouched old block objects retain their previous bounds.

This is a sufficient design contract for a fail-closed bounded implementation. It does not yield a useful runtime domain in the current branch because precondition 1 cannot be maintained: the product observes only the scoped tick pick and only Anvil/Chest writers, while the accepted source closure leaves other provider families, other relevant bound writers, and their call sites open. An unknown encountered after earlier old writes must invalidate the mirror; no in-process resynchronization is established.

## Adapter gate

Before enabling legacy results or cache reads in ordinary sessions, close an audited 1.8.9-to-26.2 registry/state/provider-input crosswalk for every camera-eligible first-hit provider, including fixed constructor bounds, virtual and custom writers, old metadata/state projection, and neighbor/paired-cell/block-entity inputs. Also establish the lifecycle/caller coverage needed to seed and keep the shared bounds mirror valid across all relevant writes, or explicitly accept permanent native fallback from the first unobserved operation. Then integrate one generic resolved traversal/provider operation; current Anvil/Chest writer hooks can be adapters inside it, not substitutes for it.

Evidence does not support guessing a modern outline/collision shape as the old ray provider, skipping unknown cells, committing only a prefix of provider writes, or declaring cache valid again after one supported pick. No code, tests, builds, or runtime were changed or run.
