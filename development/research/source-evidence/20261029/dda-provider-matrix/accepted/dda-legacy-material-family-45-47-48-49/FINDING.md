# Legacy structural materials: IDs 45, 47, 48, 49

## Exact sources

Older endpoint: Minecraft 1.8.9, `ornithe-feather` (Feather Gen2 1.8.9 build 2; Vineflower). Canonical ready marker: `decompiled_minecraft/1.8.9/ornithe-feather.ready.json`; source-manifest SHA-256: `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

Newer endpoint: Minecraft 26.2, `unobfuscated` (Vineflower). Canonical ready marker: `decompiled_minecraft/26.2/unobfuscated.ready.json`; source-manifest SHA-256: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

The selected source digests and their canonical manifest entries are recorded in `source-hashes.json`; every selected file matched its canonical entry.

## Identity and state projection

The 1.8.9 block registry in `net/minecraft/block/Block.java:924–937` assigns ID 45 to `brick_block`, ID 47 to `bookshelf`, ID 48 to `mossy_cobblestone`, and ID 49 to `obsidian`. ID 45 is a generic `Block`; ID 48 is also generic. IDs 47 and 49 instantiate `BookshelfBlock` and `ObsidianBlock`, respectively. Neither subclass overrides state or shape methods.

For these blocks the inherited `Block.getStateFromMetadata(int)` returns the default state, and `getMetadataFromState` returns 0 for propertyless states (`Block.java:182–188`). The base state definition is empty for these concrete blocks. Thus their supported metadata projection is metadata 0; invalid raw metadata does not introduce a distinct shape state.

The 26.2 datafix state table records these legacy mappings at the 16-wide state IDs: `720` (`45×16`) maps old `minecraft:brick_block` to current `minecraft:bricks`; `752` maps bookshelf to bookshelf; `768` maps mossy cobblestone to mossy cobblestone; and `784` maps obsidian to obsidian (`net/minecraft/util/datafix/fixes/BlockStateData.java:1344, 1347, 1351–1352`). Current registry keys are confirmed in `BlockItemIds.java:285, 298, 301–302` and registered in `Blocks.java:1068–1079, 1211–1222`. The brick block rename is the only name change in this bounded family.

## Collision and ray-bound providers

In 1.8.9 the base `Block` constructor initializes mutable bounds to `[0,1]` on all axes (`Block.java:198–209`). Its default movement collision provider returns that full-cell AABB and `addCollisions` appends it when the query intersects (`Block.java:339–348`). `Block.rayTrace` calls `updateShape` before clipping against those six mutable bounds (`Block.java:456–461`); the base `updateShape` is empty (`Block.java:596`). None of the four registry entries or the two concrete subclasses overrides these methods, so their local movement collision shape and ray provider are full-cube with no per-query bounds mutation.

In 26.2 all four registry declarations use the ordinary `Block` registration and default `BlockBehaviour.Properties`, without `noCollision` or a shape-producing subclass. `BlockBehaviour.getShape` returns `Shapes.block()`; `getCollisionShape` returns the state shape when `hasCollision` is enabled (`net/minecraft/world/level/block/state/BlockBehaviour.java:323–328`). The property default for `hasCollision` is true (`BlockBehaviour.java:985`). Thus each current block has a full-cube collision shape in this bounded provider path.

## Result and limit

For this four-ID family, local movement collision geometry is the same full cube on both endpoints, and the old shape provider has no state, neighbor, or block-entity input. The required historical identity map must project ID 45 to current `bricks` (not same-name `brick_block`); the other three keys preserve their names. These facts close only the local provider inputs for this family. They do not establish DDA visitation, provider-call order across cells, first-hit behavior, a complete legacy-state adapter, or end-to-end ray-trace parity.
