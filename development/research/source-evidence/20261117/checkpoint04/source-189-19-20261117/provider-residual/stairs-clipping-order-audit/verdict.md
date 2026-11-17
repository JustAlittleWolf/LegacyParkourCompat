# Independent bounded audit: stairs clipping order

**Verdict: ACCEPT** for the bounded per-StairsBlock ordering and clipping-order observation only.

## Immutable input and source identity

- Input report: `build/source-189-19-20261117/provider-residual/stairs-clipping-order.md`
- Input SHA-256: `459D7278DE596837FB96F5E447517B6C21CE754172943679424DCBDD1C4A92D6`
- 1.8.9 source manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local provenance SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- All five paired source files cited by the report matched their on-disk SHA-256 values and the exact release sidecars: `StairsBlock.java`, `Block.java`, `World.java`, `Entity.java`, and `LivingEntity.java`.

## Audited claims

- 1.8.9 `StairsBlock.addCollisions` emits the base box, then the main or outer-quarter box, then an additional corner-quarter box only when the inner-stair helper and corner-shape helper both admit it. The 1.9 provider emits the base first, emits the main step for straight/inner shapes, and emits the quarter box for non-straight shapes. For the report’s stated condition that both versions resolve the same valid local shape and coordinates, the resulting subsequences agree: straight is base→main; outer is base→quarter; inner is base→main→corner-quarter.
- The base block collision collection preserves provider order. 1.8.9 adds intersecting world-positioned bounds; 1.9 translates each local bound by the block position, intersects it, and appends it. This preserves the order of the stair-local subsequence for the same query and coordinates.
- The report’s enclosing `World.getCollisions` caveat is correct: scan bounds and corner pruning changed, so this audit does not equate complete collision-list membership or order.
- Both `Entity.move` implementations process collision-list entries sequentially in Y, then X, then Z order. Their step-up alternatives also clip in Y/X/Z order before selecting a path and downward-clipping. Although the full `Box.java` hashes differ, the exact `intersectX`, `intersectY`, and `intersectZ` method bodies are identical in the two releases (1.8.9 SHA-256 `8FC7BB57B1132AFC7AE9539C30FF9C2585F0E36C2B0E59C60946CB1E3CEE9EEA`; 1.9 SHA-256 `CB0F30C72B7925FA0C0B133DFFD051CF8260F4321F7C4A093C64EF5A5F500E62`), and their sidecar entries match.

## Limits

This verdict accepts only the per-stair ordered-emission and sequential-clipping subdependency under matching valid local shapes, coordinates, and query. The all-direction/half state-neighbor crosswalk, other providers, global world-scan membership/order, and full-pair inventory/audit remain open. No trajectory, runtime, implementation, or pair-freeze claim is made.
