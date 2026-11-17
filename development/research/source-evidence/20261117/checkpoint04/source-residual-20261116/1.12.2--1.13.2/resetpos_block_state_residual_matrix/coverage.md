# ResetPos block-state residual inventory: 1.12.2 → 1.13.2

## Scope and identity

This is a source-only key/provider residual inventory for the accepted `Entity.resetPos` block collision query. It does not establish old/new block shape equivalence or a player-position difference. Exact endpoints are Ornithe Feather Gen2 build 2: 1.12.2 source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 source manifest `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

The resetPos query uses the normal player box (`0.6 × 1.8`) and retries at integer Y. The accepted block-region note establishes padded candidate positions, border/chunk gates, and selected air/base-cube representatives only. At arbitrary reachable world positions, any exact common registered key may occupy a candidate cell; this inventory therefore keeps the full 184-key exact intersection rather than guessing map-specific block contents. Target-only keys and unresolved aliases without a proven old counterpart are not treated as historical 1.12 blocks.

## Immutable inputs checked

- `build/source-drafts/registry-collision-1122.md` SHA-256 `29278a0c2b0899866f444296bae8575bc6e039c3bbe419d0c51051077c1624a2`.
- `build/source-drafts/registry-collision-1132.md` SHA-256 `d9bae34a1b4f02a5ab00f7ce7909f420663625c145c328fc5f332a9957a2b139`.
- `build/source-drafts/registry-collision-crosswalk.md` SHA-256 `bbb1c099dc384489dbb219daa90f797649fc0c8094e5931a68a0c2997f32280a`.
- `build/source-residual-20261116/1.12.2--1.13.2/has_collision_key_join/key-join.csv` SHA-256 `c6bd748d0863ea4bc7f6098d0268d579b8da22216be23086400ffcd45ce608f5`.
- `provider_override_roster/provider-override-roster.md` SHA-256 `524e27cf0618ea8942a961f27835fabf7091248a0629b9f03f4bd4d39c05a979`.
- `default_block_collision_route/coverage-r2.md` SHA-256 `3d2ecb822cde9aa8fa6592aca23687c01840479913409b6c32b83452b252381e`, independently ACCEPTed verdict `default_block_collision_route_audit/verdict-r2.md` SHA-256 `6309ee8ddca94643c97914af7ed57819e0e3641b20c1dea4a333d4303c02741d`.
- ResetPos query/block-region inputs: `resetpos_collision_dependency/coverage.md` SHA-256 `d2a4c6d260c05f5c108ad0f1ceae51254db64227085985d9a6022d66925ec7e7`; `resetpos_block_region/note.md` SHA-256 `8183cc6d247d0e236287d2b1f5bd9cddfa477fd373cb99d6ced9bc6cccaafe95`.

## Matrix files

`resetpos-common-key-provider-matrix.csv` (SHA-256 `b7294302cd98bc923f543608ee9f5935a24a02033ac6033828c339dc702a2974`) contains one row per exact common registry key, with the old ID/class/effective `addCollisions` and `getCollisionShape` owners, the new class/effective collision route, and endpoint class-source locations for state-domain resolution. `resetpos-provider-owner-groups.csv` (SHA-256 `e5bb405a1939d78218e6c28ec8d5b2e968dcde672e6dd530e0b8817c3d0704a7`) groups the rows by exact effective old/new provider tuple and lists member keys. `state-domain-source-index.csv` (SHA-256 `42c24d1fd02b63c02c4d3548dfb9d0533c92c3bd8553ce130c1e4e08d07a1023`) binds all registered class names in the matrix to source file hashes and direct state-definition/property anchors (203 endpoint/class rows, including nested `PurpurSlabBlock.Single` resolved to `PurpurSlabBlock.java`).

Counts are 184 exact-key intersections: 76 keys in the ACCEPTed state-independent base Block unit-cube provider slice; 39 other keys inside the 115-key base-owner/target-base-route join, whose target shape dispatch is specialized; and 69 remaining shared-key rows with other effective owner/collision routes. There are 57 distinct old/new effective-owner route groups.

## State-domain and coverage gate

The two accepted registry inventories and crosswalk establish key/class/provider dispatch, not the full Cartesian legal state domain for every class. The source-domain index pins the source files and direct declaration anchors, but it deliberately leaves state property ranges that live in inherited owners unresolved. Thus this packet is an exact key/provider roster with explicit state-domain source locations, not a completed per-state matrix. The exact domain for each key must be expanded from the registered class's `StateDefinition`/state builder and inherited property sources before a query-specific provider verdict is claimed.

The 76-key default provider verdict is state-independent and can be reused only for the unit-cube provider claim; it does not by itself prove full ResetPos query equivalence. Specialized/stateful provider reports in the accepted coverage ledger are reusable for only the states and dependencies they reviewed. The block-side ResetPos gate remains open across the exact rows in the CSV until each reachable state/context is joined to accepted provider evidence or a new bounded source comparison. Do not infer shape or displacement from registry identity, owner name, or `hasCollision` alone.

The immediate next author action is to use the CSV plus state-domain source index to expand state domains for the residual owner groups, then assign only key/state groups not already closed by an immutable accepted provider packet. Preserve accepted entity-candidate, block-region, default-route, pane, EndPortal, and other owner verdicts; do not repeat their unchanged claims. No code/review/build/test/runtime or pair-freeze claim is included.
