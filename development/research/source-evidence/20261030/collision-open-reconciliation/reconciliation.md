# Collision findings: product reconciliation

Read-only product reconciliation for the four open geometry findings named in this note. This is not new source acceptance. Primary exact sources remain `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft`; the preserved 20261028 notes cited below carry prior source review and bounded acceptance.

## Product coverage observed

- `BlockCollisionCollectionHook` and `MovementRuntime.collectHistoricalMovementBlocks` already provide a collection seam. The runtime adapter retains the primary movement request, reconstructs the 1.13 query shapes, checks entity/border/step-up admission, scans the complete old envelope, and falls back to native when a gate fails.
- `change/v1_13/BlockCollisionCollection` (`@MovementChange(V1_13)`) reconstructs the 1.13.0 aggregate. `change/v1_13_1/BlockCollisionCollection` (`@MovementChange(V1_13_1)`) reconstructs the 1.13.1/.2 partial-shape stream and full-cell tail. Both only claim the mapped slab set plus three proven empty-air keys. `change/v1_12/NativeBlockCollisionCollection` is an explicit lower native fence so the 1.13 collectors do not resolve to 1.12 profiles.
- `change/v1_13/AxisClipping` uses the single 1.13.0 aggregate only when the scoped collision context contains exactly that shape. The current axis resolver otherwise keeps native clipping. For 1.13.1/.2, current axis clipping retains the strict sub-epsilon cutoff for the bounded grid-aligned slab stream.
- `BlockCollisionScanHook` and `BlockCollisionsMixin` can change the modern cursor envelope and perimeter cell admission. Existing scan providers are only `change/v1_8/NeighborCollisionScan` and `change/v1_9/NeighborCollisionScan`; neither resolves to 1.12 profiles. `change/v1_11/LowerRowMovingFenceCollision` is a separate narrow lower-row moving-fence candidate filter and does not cover the 1.12.2 query-face omission.
- Profile declarations split `V1_13` (1.13.0) from `V1_13_1` (1.13.1–1.13.2), matching the collector and epsilon-witness boundary. The 1.13 collector changes do not resolve into 1.14 profiles.

## Findings, dispositions, and smallest next gates

### Collision representation: 1.12.2 to 1.13.2

**Uncovered as a general collection operation.** 1.12.2 `World.getCollisions` returns translated block `Box` candidates through dynamic `addCollisions`, then appends eligible entity boxes. 1.13.2 `WorldView.getBlockCollisions` emits individually ordered partial voxel shapes plus one aggregated canonical-full-cell shape; concrete `World.getCollisions` appends entity shapes. The extant collection code only reconstructs the 1.13 side for its slab/empty-state domain; `V1_12` deliberately takes the native provider path. The accepted collector closure confirms the 1.13 provider distinction and entity composition, not equivalence of the two collectors or a general block-state adapter.

**Minimal candidate:** reuse the `world.block-collision-collection` operation for bounded movement queries, with separately annotated 1.12.2 and 1.13 providers. Preserve the old X/Z/Y box-candidate sequence versus the later partial stream/full-cell tail; do not combine either with entity candidates. If the hook’s `StreamOrder` cannot express 1.12's X-outer/Z-middle/Y-inner visitation, extend that neutral contract only as far as needed. Keep native fallback for entities, border-state behavior, unsupported block providers, or an unproved historical/current shape mapping. Before general implementation, close an exact state-provider domain or narrow it to a finding with a common-era witness. Source-only references in `collision-representation.md`: 1.12.2 `Entity.move`, `World.getCollisions`; 1.13.2 `Entity.move`, `WorldView.getBlockCollisions`, concrete `World.getCollisions`.

### Repeated voxel offset epsilon

**No 1.12.2-to-1.13.2 change for the corrected two-full-stone witness.** The 1.13.2 collector aggregates both full cubes into one final `IntVoxelShape`; it does not invoke the between-shape `1.0E-7` cutoff after the first cube. 1.12.2 retains the same approximately `5e-8` residual. This is independently audited in `.native-source/20261028/collision-dependencies/voxel-repeat-audit/audit.md` and `.native-source/20261028/collision-dependencies/voxel-repeat-axis/voxel-repeat-axis.md`.

The accepted bounded two-bottom-slab witness is instead a 1.13.0 vs 1.13.1/.2 collector split. The product's profile split and paired `V1_13` aggregate / `V1_13_1` stream providers cover that slab-only source calculation under their runtime gates. This is not whole-collector coverage, an input-only trajectory, or runtime parity. Keep the finding as a bounded witness/correction; do not add a separate epsilon hook or claim the original 1.12.2-to-1.13.2 stone difference.

### Moving fence collision at a query-region edge

**Uncovered direct player-response witness.** In 1.12.2 `World.getCollisions`, an X-side cell is still visited at minimum Y when Z is interior; in 1.13.2 `WorldView.getBlockCollisions`, a cell on faces for any two axes is pruned. A translated, connected fence arm can cross those query faces and clip the supplied player's movement. The existing 1.8/1.9 scan providers do not apply to a 1.12 profile, and the 1.11 lower-row fence hook handles a different cell/era gate.

**Minimal candidate:** a `BlockCollisionScanHook` provider annotated at the bounded 1.12.2 cutover can reuse the cursor-bound seam, but `BlockCollisionScan` needs a small neutral predicate extension first: its current `pruneHorizontalVerticalEdges` flag excludes both Y edge rows next to a horizontal perimeter, whereas 1.12.2 excludes only the upper Y row (`q != l - 1`). Preserve the old X/Z/Y envelope/order and that asymmetric upper-row rule. Also prove that the current non-loading chunk lookup admits the same scanned columns as historical `isChunkLoaded`; otherwise retain native scanning. Gate use to the exact historical scan semantics and source-closed moved-fence geometry/state mapping. The accepted witness supports the specific piston-translated fence setup, not all moving blocks or all 1.12.2 provider geometry. No additional axis operation is indicated.

### World block-collision enumeration: 1.13.2 to 1.14.4

**Source algorithm difference only; movement consequence is not yet established.** The finding records 1.13.2 floor/ceil bounds, outer-corner pruning, chunk-loaded checks and full-cell aggregation against 1.14.4 epsilon-adjusted bounds, inclusive traversal, block flags/context and per-shape emission. The 1.13.0/.1 providers do not resolve to 1.14 profiles; there is no current 1.14 historical collector provider. The preserved slab contract separately says collector differences alone do not prove a common-state movement result.

**Smallest remaining gate:** either produce an exact 1.13.2/1.14.4 common-state query where a cell admitted/omitted or differently represented by these enumerators changes the player collision result, or close a relevant common-state domain as equivalent. Only then select an applicability cutover and consider a `world.block-collision-collection` provider for the exact 1.14.4 range. The 1.14.4 source alone must drive that provider; reusing a 1.13 provider would be incorrect. Preserve the existing native fallback outside a proven domain.

## Evidence consulted

- The four assigned findings under `development/research/findings/`.
- `.native-source/20261028/collision-next/collector-113/collector-113-closure.md`.
- `.native-source/20261028/collision-next/slab-113-product-seam-reconciliation/report.md` and `slab113-contracts-review/review.md`.
- `.native-source/20261028/collision-next/slab-1130-adapter-source-closure/report.md`, `slab-113-stream-general-equivalence/report.md`, `slab-1130-all-slab-state-aliases/closure.md`, `slab-1130-union-math-audit/report.md`, and `slab-pane-1130/WITNESS.md`.
- `.native-source/20261028/collision-dependencies/voxel-repeat-axis/voxel-repeat-axis.md` and `voxel-repeat-audit/audit.md`.
- Current product hooks/providers named above; canonical 1.12.2 `World.getCollisions`, 1.13.2 `WorldView.getBlockCollisions` / `World.getCollisions`, 1.12.2 moving block/fence providers, and 1.14.4 `WorldView.getBlockCollisions`.

No product files were edited. No build, tests, or game/runtime launches were performed.
