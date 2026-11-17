# Provider inventory source audit: 1.10.2 → 1.11

**Verdict: ACCEPT (bounded provider inventory only).** The audited input is `build/source-early-patches-20261117/1.10.2--1.11/dependencies/provider-inventory.md`, SHA-256 `559cfc38964235b8c436cbda1e908637578580c0a766bb655e632db63eeffd96`.

## Checks

- The report SHA is exact. Root readiness marker and source-manifest hashes match the report for both releases: 1.10.2 `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` / `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; 1.11 `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` / `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca`.
- Independently checked all 18 unique Java source filenames cited in the report against local source bytes and the corresponding manifest rows at each endpoint: 36/36 match. The report’s boundary and registry hash rows for `World`, `Block`, `StateDefinition`, `BlockProperties`, and `Blocks` also match local files.
- `StateDefinition` forwards collision-shape, callback, outline-shape, full-block, and virtual-property queries to their block implementations. `Block.addCollisions` appends the translated collision shape only when nonempty and intersecting. `World.getCollisions` passes the queried entity through the state callback; entity-vs-entity boxes are appended separately.
- Cocoa’s state-selected collision boxes change at the later ages in 1.11, including the minimum height changing from 0.3125 to 0.1875. This is a real provider geometry change, with unchanged age/facing dispatch.
- `PistonHeadBlock.addCollisions` preserves plate-then-arm order. In 1.10.2 the arm depends on facing; in 1.11 it also reads `SHORT` and selects six short-arm boxes. Production of a live `SHORT` state remains unverified by this inventory.
- `MovingBlock` in 1.11 adds an entity-aware callback into the position’s `MovingBlockEntity`; the old version uses the inherited single-shape callback. The payload and eligible local-player caller remain unresolved, so this conditional route is not promoted to a closed movement candidate.
- `SnowLayerBlock` collision construction is unchanged; `isFullBlock` changes from layer count 7 to 8. No local-player caller for that predicate is established here.
- The Flower change offsets `getShape`, while `PlantBlock.getCollisionShape` remains explicitly empty. Rails likewise return empty collision; their ascending outline changes from the full block to a half-height shape. Neither outline difference is treated as emitted player collision geometry.
- The registry aliases show Shulker boxes only in 1.11; treating them as new-only and excluding them from the 1.10.2 profile is consistent. Flower registrations exist at both endpoints.

## Limits and state

This ACCEPT covers the report’s provider inventory and its cited source/hash bindings. It does not close the cached/dynamic `MovingBlockEntity` payload and player-query boundary, production of `PistonHeadBlock.SHORT`, or a local-player caller for SnowLayer `isFullBlock`. It does not establish pair-wide coverage or a movement outcome. **The exact pair remains partial.**
