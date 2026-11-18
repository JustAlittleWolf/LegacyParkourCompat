# Independent audit: stationary block collision providers, 1.16.1 → 1.16.2

**Producer revision audited:** `b-mid-04/report.md`, SHA-256 `366dddc1158f331d1c13e3b18048264813945f0804084413fc7b1e57ec2bd495` (verified).

## Evidence integrity

The exact Mojmap source manifest hashes and provenance hashes match the assigned pins for both releases. I recomputed the source hashes for all five target block classes, `Blocks`, `BlockBehaviour`, `Block`, `Shapes`, and `FallingBlock`; every file digest matches its entry in the corresponding `mojmap.sources.sha256` manifest. The provider, registration, shared dispatch/default/cache, shape construction and inheritance slices were inspected in both versions. The paired relevant source comparison reveals no behavior-changing difference in this scope.

## Provider verdicts

| Provider / operation | Verdict | Independent result |
|---|---|---|
| Anvil `getShape` and inherited ordinary collision provider | **ACCEPT** | Same X-axis vs Z-axis union selection from `FACING`; inherited `BlockBehaviour.getCollisionShape` still gates on `hasCollision` and otherwise dispatches to the state shape. Anvil registrations use collision-enabled defaults. `FallingBlock` adds no collision provider override. |
| Bell `getCollisionShape` and `getShape` | **ACCEPT** | Both call the same state-based helper; `FACING` and `ATTACHMENT` choose equivalent floor, ceiling, double-wall, or single-wall shapes in both versions. `POWERED` does not select geometry. |
| Lectern `getCollisionShape` and `getShape` | **ACCEPT** | Collision always returns the same `SHAPE_COMMON ∪ SHAPE_TOP_PLATE`; facing only selects outline shape. The top plate remains the degenerate `Block.box(0,15,0,16,15,16)` in both. `Block.box` scaling and `Shapes.create` behavior are byte-identical (`Shapes.java` has the same manifest-matched digest), so no hidden construction delta is present. |
| Grindstone `getCollisionShape` and `getShape` | **ACCEPT** | Both dispatch through the same `getVoxelShape`; `FACE` and `FACING` branch selection and all referenced floor/wall/ceiling shape constants are unchanged. |
| Stonecutter `getShape` and inherited ordinary collision provider | **ACCEPT** | Same fixed `Block.box(0,0,0,16,9,16)`, independent of `FACING`; inherited collision dispatch and registration defaults are unchanged. |

Shared closure also agrees: `BlockStateBase` resolves shape/collision calls to the owning block, the collision cache computes that provider with `EmptyBlockGetter`, origin and empty context, and `BlockBehaviour` retains `hasCollision = true` by default and returns an empty shape only when that flag is false. The five registrations do not disable collision.

## Scope boundary

These verdicts cover only stationary ordinary collision/outline provider geometry and the relevant vanilla dispatch, cache, default and registration inputs for the named blocks. They do not audit contact/support-specific predicates, neighboring-block dependencies, callbacks, pose or edge behavior, falling/contact behavior, shape-production beyond the cited degenerate lectern plate path, or other movement tick areas. No whole-pair coverage or freeze is claimed. No implementation, tests or runtime parity were reviewed.