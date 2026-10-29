# DDA torch family 50, 75, 76: provider and alias closure

## Scope and source identity

This source-only report compares 1.8.9 (Ornithe-Feather) with 26.2 (unobfuscated) for old registered block IDs 50, 75, and 76. Exact inspected file hashes are in `source-manifest.json`; paths are relative to the indicated canonical roots.

## 1.8.9 registration, state and movement collision

`net/minecraft/block/Block.java:938` registers ID 50 as `torch`; lines 1002–1006 register ID 75 as `unlit_redstone_torch` and ID 76 as `redstone_torch`. IDs 75 and 76 are separate `RedstoneTorchBlock` instances with constructor `lit=false` / `lit=true` respectively. They inherit `TorchBlock` state and collision behavior.

`TorchBlock.java:35–37` returns `null` from `getCollisionShape`. The common `Block.addCollisions` path (`Block.java:340–344`) adds a shape only when this value is non-null and the query box intersects it. Therefore all three registered IDs contribute no movement collision box, regardless of facing. This finding concerns the legacy movement-collision provider only.

`TorchBlock.java:22–25` defines `FACING` as every direction except DOWN and initializes the default state to UP. Its metadata decoder (`:181–202`) maps metadata 1/2/3/4 to EAST/WEST/SOUTH/NORTH; 5 and the default branch map to UP. Encoder (`:205–227`) maps those horizontal directions back to 1–4 and UP/DOWN/default to 5. Thus 0, 5, and unsupported values decode UP; there is no valid DOWN torch state. `RedstoneTorchBlock` inherits these methods without override, so the same face mapping applies to IDs 75 and 76; litness is fixed by block identity, not metadata.

## 1.8.9 ray bounds are a separate mutable provider

`TorchBlock.java:138–154` overrides `rayTrace`: it reads the world state facing and calls `setShape` with a small facing-dependent box (horizontal post for EAST/WEST/SOUTH/NORTH; upright post for UP/default), then delegates to `Block.rayTrace`. `Block.java:292–299` shows `setShape` writes the block instance's `min/max` fields. `Block.java:456+` begins the superclass ray calculation, and `Block.java:596–597` shows its `updateShape` is empty, so the newly written bounds remain on that block instance after ray tracing. Redstone torches inherit this ray provider. This documents possible mutable **ray/outline bounds** writes; it does not turn these boxes into movement collision shapes.

## 26.2 current identity and state aliases

`EntityBlockStateFix.java` maps old numeric names `minecraft:torch` to 50, `minecraft:unlit_redstone_torch` to 75, and `minecraft:redstone_torch` to 76. `Blocks.java:1223–1231` registers current standing `torch` and separate `wall_torch`, both `.noCollision()`; `:1945–1954` registers standing `redstone_torch` and separate `redstone_wall_torch`, also `.noCollision()`. The standing redstone block uses `litBlockEmission(7)`.

`BlockStateData.java:1353–1357` gives the legacy property conversion: old `torch` with EAST/WEST/SOUTH/NORTH facing becomes current `wall_torch` with the same horizontal `facing`; old `torch` with UP becomes current `torch` (the current standing block has no facing property). The fixed legacy metadata decode above means horizontal metadata 1–4 takes the wall alias, and 0/5/invalid takes standing torch. The current wall-torch variant has its own facing property and is registered `.noCollision()` in `Blocks.java`.

For old redstone torch, `BlockStateData.java:3786–3799` explicitly maps old `unlit_redstone_torch` horizontal states to current `redstone_wall_torch` with the same facing and `lit=false`; its UP state maps to current standing `redstone_torch` with `lit=false`. Old lit `redstone_torch` horizontal states map to current `redstone_wall_torch` with the same facing and `lit=true`; UP maps to current standing `redstone_torch` with `lit=true`. Current `RedstoneTorchBlock.java:40` defaults `LIT=true`, and its state definition adds `LIT` (`:149–151`); the wall variant defines both `FACING` and `LIT` (`RedstoneWallTorchBlock.java:103–104`). Accordingly, old ID 75 aliases to the same current registry block as ID 76, distinguished by `LIT=false`, not a current `unlit_redstone_torch` block. Current wall redstone torch is likewise `.noCollision()`.

`BaseTorchBlock.java:15–25` supplies a fixed upright 4-by-10 pixel outline for standing variants. `WallTorchBlock.java` and `RedstoneWallTorchBlock.java` supply facing-specific wall outlines. These are outline shapes, while the `.noCollision()` registration makes current movement collision empty. `BlockBehaviour.java:327–329` defines the collision gate: `hasCollision=false` yields `Shapes.empty()` regardless of outline shape.

## Result and limits

For the old IDs 50/75/76, exact source supports an empty movement-collision provider at every decoded facing. It also supports aliasing to current standing or wall torch identities as detailed above, with old redstone litness represented by current `LIT`. The legacy rayTrace callback mutates shared bounds; the source supports tracing this state-dependent writer separately from collision. This report does not establish traversal order, query visitation, DDA equivalence, cache/observer behavior, or any end-to-end ray result.
