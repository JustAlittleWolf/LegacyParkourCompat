# Independent source audit: Cocoa collision-shape provider (1.12.2 → 1.13.2)

Verdict: **ACCEPT for the bounded Cocoa provider slice.** This is not pair-wide provider closure or a pair freeze.

## Identity and method checks

- Coverage report SHA-256 verified: `2aa3b3ce481b940d2ee39a96b20a5f739f36e83f374703ac863b841f718e7d87`.
- Both ready-marker source-manifest digests matched their `ornithe-feather.sources.sha256` sidecars: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.
- All 14 source file hashes cited by the report (7 per endpoint) matched both local source bytes and the corresponding sidecar entries.
- Registration agrees: 1.12.2 `Block.java:1054` registers id 127/key `cocoa` as `new CocoaBlock`; 1.13.2 `Block.java:1227` registers key `cocoa` as `new CocoaBlock`; each `Blocks.COCOA` alias resolves that key (`Blocks.java:425`, `:894`). The target constructor call does not apply `.noCollision()`.
- Both state definitions include horizontal `FACING` and `AGE`; the old `AGE` range is 0–2 and the target aliases `Properties.AGE_2`, also 0–2. Each `getShape` indexes by AGE then selects the direction array using FACING. Independent comparison of all 12 table boxes confirmed exact equality after multiplying old normalized coordinates by 16. No world/neighbor/context reads occur in either provider method.
- Collision path is real for local-player movement: LocalClientPlayerEntity.move calls `super.move` at old `:849` and target `:874`; Entity.move queries World collisions (old Entity `:567`, `:613`; target `:580`, `:612`). Old World block enumeration dispatches `BlockState.addCollisions` and default Block.addCollisions requests `state.getCollisionShape`, which delegates to `state.getShape`. Target `BlockState.getCollisionShape` delegates to Block; default `Block.getCollisionShape` returns `state.getShape` when `hasCollision` is true. Cocoa's registration leaves the default collision flag enabled. The collision route therefore consumes the compared Cocoa shape tables.

## Scope boundary

This accepts only Cocoa registration, AGE/FACING state-to-shape mapping, and the direct local-player collision-query route. It does not establish the complete block/state/provider inventory, neighbor-dependent providers, or pair-level source coverage. No unrelated behavior was assessed.
