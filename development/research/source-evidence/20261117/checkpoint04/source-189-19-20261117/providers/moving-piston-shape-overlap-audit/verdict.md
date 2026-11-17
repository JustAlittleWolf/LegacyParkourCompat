# Independent source audit: moving-piston shape and overlap dependencies (1.8.9 → 1.9)

## Verdict

**ACCEPT — bounded source inventory only.** The report accurately binds the paired MovingBlockEntity/MovingBlock/PistonBaseBlock/resolver/Block/StateDefinition sources and states the raw-state capture difference, shape paths, direct overlap consumer, reused accepted evidence, and remaining gates. This does not establish a provider-specific moved-state outcome beyond the already accepted Stone witness, a general trajectory, complete pair coverage, or pair freeze.

## Identity verification

- Audited report: `build/source-189-19-20261117/providers/moving-piston-shape-overlap.md`
- Report SHA-256: `3BA538C9AF45920B25D4D290479BD146831556240A088FE7A5328A622A50CEE5`
- 1.8.9 `ornithe-feather.sources.sha256` SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; provenance SHA-256: `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`.
- 1.9 mapping-local `.decompile-provenance.json` SHA-256: `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Recomputed the six paired Java source hashes in the report table. All six 1.8.9 hashes match current bytes and the root source sidecar; all six 1.9 hashes match current bytes and the corresponding provenance `sources` rows. The three sidecar hashes above also match current bytes.
- Recomputed referenced accepted evidence hashes: piston response r2 report `AA25A8EC6767B9B0B443C8CADBD7FC3B818B212EF5E897EA7AE20CA5F5A82B61`, verdict `103EE71196F3D357053A6C1A6E1FBCFB7E3BA817F197F09610E2856D5364E7B8`; Stone sweep r2 report `05A2B44304FE83008B9896D7BB5BCF23B51EA85E3A4F38EE5A94174D87C6EC91`, verdict `37CD019F6D23BB0B83AAA94045594A71FAC3690E485E56D638F47B52B4E76063`. Every identity matches the referenced file bytes.

## Audited source bindings

- `PistonBaseBlock.move` captures the ordinary moved-state list before replacing source blocks. 1.8.9 passes each original `world.getBlockState` directly to the moving entity; 1.9 first maps those states through `resolveVirtualProperties(world,pos)`. The paired source therefore supports a concrete input-binding difference. The report correctly does not generalize a shape or movement result for arbitrary state-dependent providers.
- `StateDefinition.BlockState.resolveVirtualProperties` dispatches to the block's virtual-property resolver as cited. The two producer paths, including the distinct piston-base retraction source entity, are described within source bounds. `source` does not occur in the shape/response methods; the frozen piston-head arm path remains excluded.
- The 1.8.9 collision wrapper reads `getProgress(0.0F)`, applies extension conversion, requests one moved-state `getCollisionShape`, and shifts the returned world-space box. The 1.9 collision wrapper uses `MovingBlockEntity.getShape`, which samples both current and previous progress, translates each moved-state `getShape`, and unions them. The separate full-cube outline fallback when the block entity is absent is correctly excluded from the collision callback claim.
- The direct response path is accurately bounded: both versions query `World.getEntities(null, box)`; that overload defaults to `EntityFilter.NOT_SPECTATOR`. 1.9 additionally skips `PistonMoveBehavior.IGNORE`, while base Entity returns `NORMAL` and the paired PlayerEntity classes do not override it. With an intersecting player box in a loaded chunk, the query can include a non-spectator local player. The response proceeds through the previously accepted conditional direct-response and local `Entity.move` collision paths; no realized displacement is inferred here.
- The report correctly keeps the response procedure/Stone witness evidence separate from provider-family equivalence. It preserves the accepted witness only for one full-cube Stone state at `(progress,lastProgress)=(0.5,0.0)`, does not redo its scan/clip analysis, and excludes frozen pane-connection and piston-head-arm slices.
- NBT field descriptions and stated producer/serialization limitations are consistent with the cited paired MovingBlockEntity sources. Residual gates are appropriately limited to provider-specific moved-state shapes and virtual properties, any concrete additional overlap witness, and the separate client-side moving-block state/progress producer or serialization/order needed for pair-wide applicability.

## Scope limit

Acceptance is limited to the correctness and evidence binding of this raw-state/shape/overlap dependency report. Its listed residual dependencies remain open, and no full-pair inventory, movement trajectory, runtime result, or pair freeze is accepted.
