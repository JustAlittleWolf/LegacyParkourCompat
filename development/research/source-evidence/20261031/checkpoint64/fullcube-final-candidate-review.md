# Full-cube collision candidate final review

## Verdict

**ACCEPT** candidate commit `eb9b2834dd787bd86c6944f2d8aa6fdafbd2ab21` against neutral base `1cc7cfc4ca2e942cd5e8e0e771abd9335a506dd0` for the assigned full-cube overlap operation.

## Correction verified

The only diff from the previously reviewed commit `46ec84d4604c226349904200c067567cf362cba2` changes `snappedOverlap` to require `snappedMin < snappedMax` before testing strict overlap. This closes the finite-positive, grid-collapsed interval counterexample: 1.15.2 `Shapes.create` rounds endpoints to voxel indices and fills cells only while the lower index is less than the upper index; `DiscreteVoxelShape.isEmpty` rejects a shape with no filled extent on an axis. The candidate now likewise returns false for that snapped axis, so the three-axis conjunction rejects the cube.

The correction preserves the accepted nonzero-grid witness: when snapping leaves positive width and crosses a block face to tangency, strict overlap still returns false as required by the old query shape. The all-zero special case continues to model `Shapes.create`'s origin `Shapes.block()`/empty selection. The array fallback continues to mirror the two-coordinate `IndirectMerger` merge order and `1e-7` coalescing for each axis.

## Routing and version selection

The `V1_15` raw-strict entry and `V1_15_2` historical entry align with the resolver's closest `emulates >= selected` rule: earlier selections resolve the strict-native baseline, 1.15.2 resolves the old predicate, and 1.16+ has no applicable hook and retains native strict behavior. The mixin obtains an entity from the collision context and delegates only when `MovementRuntime.find` accepts a client-side Player with an enabled profile; otherwise it calls the native strict AABB predicate. Logical servers and non-player contexts therefore retain native behavior.

No material blocker remains within the assigned normal collision-query domain. This review establishes source/implementation agreement only; it does not claim runtime parity.

## Scope and process

Reviewed the exact correction diff and immutable candidate commit, with the previously reviewed candidate, neutral resolver/runtime, accepted finding, and canonical read-only 1.15.2/1.16.5/26.2 sources as context. No product implementation was inspected. No files beyond this report were written; no build, tests, or runtime launch was performed.
