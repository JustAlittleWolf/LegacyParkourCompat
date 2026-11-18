# COMPLETED — 1.15.1 → 1.15.2 stationary block slice

## Exact source identity

Both version parents report `status: ready`, exact IDs `1.15.1` / `1.15.2`, and `mapping: mojmap`. The SHA-256 of `1.15.1/mojmap.sources.sha256` is `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; the `1.15.2` manifest hash is `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`. Each ready marker's `sourceManifestSha256` matches its parent manifest hash.

Compared source-member hashes match their pinned manifest entries on both sides:

- `net/minecraft/world/level/block/Blocks.java`: `0cef66feacbf9d7d5bd38ac1d2065e71384a73043b0956eeaf314fedbf5cc7d9` (identical in both releases).
- `net/minecraft/world/level/block/Block.java`: `a5819a4c676d2b7e08cce7f80ae80a13726dd17b15e0b29efdce7e37efa2bf0d` (identical).
- `net/minecraft/world/level/block/state/BlockState.java`: `77320fd1e50e58d7a3ed593ccbcf21853c97d125fccebde2e0e305dc47524f21` (identical).
- `net/minecraft/world/level/block/BaseEntityBlock.java`: `f81e9144ab102e1228f09d868331549377de2083f1c160ba6c65ec42b173abdd` (identical).
- `net/minecraft/world/level/block/FallingBlock.java`: `ce6f8a737b49dad8ffcfc0adad11e1103250bcae5b4902e436bbd3b8686ad701` (identical).
- `net/minecraft/world/level/block/BeehiveBlock.java`: 1.15.1 `bb33cb2317d9b18f9183e772d6a9d26243b069b3f087f2d88f30c93a28dabe08`; 1.15.2 `c144e102beb2305e85d7bc6245c193cc7b24d15ddd478e2db15c9e0cb2ce8ad2`.
- `net/minecraft/world/level/block/ConcretePowderBlock.java`: 1.15.1 `ee3710039b1e1e0aa9ddc0931c19c21236fdc3426603285efa9c69d81a2075d1`; 1.15.2 `9f42ff3c725b87c4f4813eea471e7f44646067592f5e737adf151229194679f7`.

## Family verdicts

### Beehive and bee nest — no eligible finding

`Blocks.java:2116-2117` registers both as the same `BeehiveBlock` with `Material.WOOD`; only strength differs (`0.3F` for nest, `0.6F` for hive). The whole registration file is byte-identical across the pair. `BeehiveBlock.java:51-56, 221-228` defines only `FACING` and `HONEY_LEVEL`, defaults to north / zero, and preserves those properties in both releases. There is no shape override in `BeehiveBlock`; it inherits the `BaseEntityBlock` → `Block` route. `BlockState.java:184-197` delegates shapes to the block; `Block.java:386-392` supplies `Shapes.block()` and, with collision enabled, returns the state's shape. `Block.Properties` defaults `hasCollision=true` (`Block.java:852`); neither registration opts out with `noCollission()`. The honey-level/facing values do not select another collision shape. The only `BeehiveBlock` body delta is honeycomb-drop bookkeeping in the honey-harvest path, outside player movement.

### Concrete powder and cured concrete — no eligible fixed-state finding

`Blocks.java:1692-1714` registers cured concrete as ordinary `Block` and powder as `ConcretePowderBlock(WHITE_CONCRETE, ...)`; the full registration source is identical. Both inherit collision-enabled, full-block defaults; powder extends `FallingBlock` → `Block` and declares no shape provider. The cured block is ordinary `Block`, with the same full-cube shape. No legal held-state, collision flag, or shape-provider difference was found.

`ConcretePowderBlock` does have 1.15.2 changes in `onLand`, `getStateForPlacement`, and the `shouldSolidify`/water-contact helpers. These methods select or produce a different block state during landing, placement, or water/neighbor contact. They are excluded by the assigned fixed-state boundary and explicit exclusions for curing/falling and environmental state production, so they are not admitted as movement findings here.

## Coverage and limits

Compared: pinned pair readiness/mapping identity; source hashes for registration, bee/powder classes, shared shape defaults and BlockState delegation; bee legal/default state properties; collision-enabled registration defaults; stationary shapes; changed method disposition. No implementation, tests, game launch, or runtime parity claim. No eligible finding and no unresolved source-integrity gap for this bounded slice.
