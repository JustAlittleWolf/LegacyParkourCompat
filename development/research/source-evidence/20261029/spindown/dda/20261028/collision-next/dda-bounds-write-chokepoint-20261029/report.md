# 1.8.9 mutable Block bounds write choke-point audit

Snapshot: 2026-10-29. This is a source-only audit of whether the six mutable bounds on the canonical 1.8.9 `Block` instances can be monitored at one method boundary and whether the source defines a reliable recovery/reset operation. It does not inspect product code or prove a runtime observation implementation.

## Decision

`Block.setShape` is the main writer, but it is not a complete mutation choke point. Three other block classes write the fields directly: `MovingBlock` writes all six, `StemBlock` writes `maxY` before it calls `setShape`, and `WallBlock` writes `maxY` after calling `setShape`. A hook that observes `setShape` alone can miss the persistent `MovingBlock` and `WallBlock` values. The stem direct write is followed synchronously by a `setShape` write of the same maxY value on the normal return path, but it remains a separate field write and should not be generalized into a guarantee for other direct writers.

The base `Block.resetShape()` is empty. There are 15 override declarations in the block package, each applying only that class's own reset rule; the method is not a general reset for arbitrary instances or dynamic world-dependent shape state. Source evidence therefore supports a possible fail-closed observer only if it observes every write to the six fields (including direct stores), but does not provide a universal cache recovery operation. No ordinary-session adapter readiness follows from this audit.

## Bound fields and common method

`Block.java:102-108` declares six protected mutable instance fields. The base constructor (`Block.java:197-205`) initializes them to the full unit cube through `setShape`. `setShape` is `protected final` and writes all six fields in sequence (`Block.java:292-298`), so subclasses cannot replace the method implementation. Most provider and state-dependent block geometry calls use this shared setter.

The broad source scan over `net/minecraft/block/**/*.java` for assignment/update expressions on these six names returned exactly the 14 lines in `direct-field-writes.txt` and `all-bound-name-write-expressions.txt`: six assignments in `Block.setShape`, six in `MovingBlock`, one in `StemBlock`, and one in `WallBlock`. This scan also checked assignment forms without a `this.` qualifier. The whole-export reflective-field search found only two unrelated `Constructor.setAccessible(true)` uses in `RealmsBridge`; it found no `Field`, `getDeclaredField`, `getField`, or `setAccessible` path that targets these bounds. This is a source-text scan of the vanilla export; it does not cover transformed bytecode or third-party mods.

## Direct writer exceptions

| Source and line | Operation | Why a `setShape`-only observer misses it |
|---|---|---|
| `MovingBlock.java:153-158` | After asking the moved block to `updateShape`, computes and assigns all six `MovingBlock` bounds directly from the moved block's current values and movement progress. | There is no following `setShape`; these values remain on the registered moving-block instance. |
| `WallBlock.java:104-110` | `getCollisionShape` runs `updateShape`, then assigns `this.maxY = 1.5`, then returns the base collision shape. | The final `maxY` mutation occurs after the `updateShape`/`setShape` calls. |
| `StemBlock.java:112-117` | `updateShape` reads the world's AGE state into `maxY`, then passes that value to `setShape` for all six fields. | It is a direct store before the common setter; the setter repeats the final maxY value on normal completion. |

`Block.getCollisionShape` and `getOutlineShape` read the fields into world-space boxes (`Block.java:331-352`); the base `addCollisions` route uses `getCollisionShape`. Thus these are shared object values consumed by later calls, not per-call local shape variables.

## Reset and recovery boundary

`Block.resetShape()` at line 654 has an empty body. The source scan records 15 override declarations in `resetShape-overrides.txt` (plus the base declaration); some set a class-specific constructor-like default via `setShape`, but no single base operation reconstructs every registered block's construction-time values or the latest world/state-dependent values. `MovingBlock` and `WallBlock` have no reset override in the searched block package. In particular, `WallBlock.getCollisionShape` may leave its instance maxY at 1.5 after returning, and generic reset dispatch does not restore that field.

A complete observer can be described at source level as observing every write to all six fields. In vanilla source those writes are finite and identified above, but there is no single Java method through which they all pass. A method-level `setShape` hook is insufficient; a bytecode field-store observer or explicit hooks for each direct-write path would be required. This is a possible instrumentation design, not proof that the mod currently has such observation or that arbitrary transformed/modded callers are covered.

After an unobserved/unknown write, source-defined safe recovery is not available through `resetShape()` uniformly. Seeding from constructor defaults may establish an initial value only if performed for every instance after its full constructor chain; it cannot restore a previously mutated instance. Replaying the actual state- and world-dependent writer operations might reconstruct some values, but the source does not define one complete registry-wide replay that restores all six fields for all instances. Therefore the fail-closed rule remains: any write not included in the observer invalidates the historical mirror; recovery needs an independently proven complete reconstruction protocol. This report does not establish that one exists.

## Static evidence and limits

Exact source files and all 15 reset override files are hash-bound in `source-hashes.json` against canonical manifest `ornithe-feather.sources.sha256` (SHA-256 `9E75F46DC0ED43B6A355BD65DB8A92C93A4DFEAECFA92284187C6FE9410D8004`). Search queries and limits are recorded in `scan-method.md`. The accepted DDA writer/caller inventory is supporting context only; its bounded consumer findings do not prove whole-session ordering, all-player-read coverage, or cache validity.
