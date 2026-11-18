# Final claim review: 1.9.1 to 1.9.2 moving-piston response

## Reviewed immutable inputs

- Blind source freeze: `blind-audit/frozen.md`, SHA-256 `1e4e2d3266d1e7cd09bffbc92ba9cd7fbbe79b8914da2c6eac4d9724d66d7f6a`.
- Source comparison: `source/comparison.md`, SHA-256 `90ef428831b2a9608e3af76e9091c82cb12d7c73dfff773de81f941579131867`.

The reviewed comparison's SHA-256 matches the assigned identity. The freeze's SHA-256 matches the earlier terminal identity. This verdict is bound to those exact payloads and does not revise the freeze.

## Separate bounded verdicts

### Callback admission — accepted, bounded

The comparison's admission claim is supported by the freeze: `MovingBlockEntity` queries entities for the translated moving-shape box; the world query applies `NOT_SPECTATOR`, the chunk query checks entity-shape intersection, and the callback skips only `IGNORE` piston behavior. An ordinary local player inherits `NORMAL`, so it can proceed when it satisfies that query. The selector contains no rider exclusion. The world, entity, and box inputs remain applicability dependencies; this verdict does not establish a concrete overlap witness or final geometry.

### Player response — accepted, bounded

The comparison's response claim matches the frozen trace. For a slime moved state, the callback writes the movement-axis velocity component to that axis direction's offset. It then computes the direction-axis displacement, adds `0.01` on that axis, and calls `entity.move(...)`; the same displacement request applies to other non-ignored moved states, without the slime velocity assignment. This accepts the velocity write and requested move only, not the resulting clipped movement or trajectory.

### Extension, retraction, and progress order — accepted, bounded

The comparison's tick-order claim matches the frozen trace: `lastProgress` receives the prior `progress`; an unfinished tick advances and clamps `progress` before calling `moveEntities()`; a tick entering at progress at least `1.0F` calls `moveEntities()` before teardown and state replacement. The facing direction is used during extension and its opposite during retraction. This verdict covers the paired callback order only. It does not infer an exact intermediate-release cutover.

## Residual limits

The reviewed no-difference conclusion is limited to callback admission, response writes and move request, and extension/retraction progress order for the stated 1.9.1–1.9.2 source pair. The final piston shape, concrete overlap conditions, clipping, downstream trajectory, runtime parity, and full-pair coverage remain unreviewed or unestablished. No tests or launches were performed.
