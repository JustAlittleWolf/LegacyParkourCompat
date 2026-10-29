# DDA source closure: 1.8.9 wool and carpet provider families

## Scope and evidence identity

This source-only note closes the 1.8.9 registry/color/state-to-current-block mapping and collision-shape provider behavior for old block IDs 35 (`wool`) and 171 (`carpet`) against exact 26.2 unobfuscated source. It does not establish ray traversal, visited-cell sequence, callback ordering, DDA first-hit parity, nor runtime reachability. Exact per-file hashes and source-manifest comparisons are in `source-hashes.json`; every listed source file matched its canonical `*.sources.sha256` manifest.

Older source: `decompiled_minecraft/1.8.9/ornithe-feather`, mapping family Ornithe Feather. Relevant source: `net/minecraft/block/Block.java` registry lines 888 and 1195, generic block bounds/collision/ray tracing members; `net/minecraft/block/ColoredBlock.java`; `net/minecraft/block/CarpetBlock.java`; `net/minecraft/item/DyeColor.java`.

Current source: `decompiled_minecraft/26.2/unobfuscated`, published unobfuscated names. Relevant source: `net/minecraft/world/item/DyeColor.java`; `net/minecraft/world/level/block/Blocks.java`; `net/minecraft/references/BlockItemIds.java`; `net/minecraft/world/level/block/ColorCollection.java`; `net/minecraft/util/datafix/fixes/BlockStateData.java`; `net/minecraft/world/level/block/CarpetBlock.java`; `net/minecraft/world/level/block/WoolCarpetBlock.java`; `net/minecraft/world/level/block/Block.java`; `net/minecraft/world/level/block/state/BlockBehaviour.java`.

## Old metadata and current aliases

The 1.8.9 registry installs one `ColoredBlock` at ID 35/key `wool` and one `CarpetBlock` at ID 171/key `carpet`. Both use a `color` enum property. Both state decoders call `DyeColor.byId(metadata)`; the enum's `BY_ID` table indexes `getId()`, while its separate `getMetadata()` values are reversed for the legacy dye-item convention and are not used by these block-state decoders. IDs 0–15 therefore map directly to the canonical 16 color IDs below. `byId` maps out-of-range values to 0/WHITE. Both block classes default their `COLOR` property to WHITE.

| Old metadata / DyeColor ID | Current wool block | Current carpet block |
|---:|---|---|
| 0 | `minecraft:white_wool` | `minecraft:white_carpet` |
| 1 | `minecraft:orange_wool` | `minecraft:orange_carpet` |
| 2 | `minecraft:magenta_wool` | `minecraft:magenta_carpet` |
| 3 | `minecraft:light_blue_wool` | `minecraft:light_blue_carpet` |
| 4 | `minecraft:yellow_wool` | `minecraft:yellow_carpet` |
| 5 | `minecraft:lime_wool` | `minecraft:lime_carpet` |
| 6 | `minecraft:pink_wool` | `minecraft:pink_carpet` |
| 7 | `minecraft:gray_wool` | `minecraft:gray_carpet` |
| 8 | `minecraft:light_gray_wool` | `minecraft:light_gray_carpet` |
| 9 | `minecraft:cyan_wool` | `minecraft:cyan_carpet` |
| 10 | `minecraft:purple_wool` | `minecraft:purple_carpet` |
| 11 | `minecraft:blue_wool` | `minecraft:blue_carpet` |
| 12 | `minecraft:brown_wool` | `minecraft:brown_carpet` |
| 13 | `minecraft:green_wool` | `minecraft:green_carpet` |
| 14 | `minecraft:red_wool` | `minecraft:red_carpet` |
| 15 | `minecraft:black_wool` | `minecraft:black_carpet` |

This mapping is source-backed in two independent current structures. `ColorCollection.VALUES` and `net/minecraft/world/item/DyeColor.java` retain the same IDs/order 0–15. `BlockItemIds` derives wool/carpet IDs by prefixing every name in that ordered collection with `wool` or `carpet`, then `Blocks.WOOL` registers a plain `Block` per ID while `Blocks.CARPET` registers `WoolCarpetBlock(color, properties)` per ID. The `BlockStateData` tables explicitly translate old `wool{color:...}` to each named wool block (state IDs 560–575), and old `carpet{color:...}` to each named carpet block (IDs 2736–2751). In both tables the former `silver` property for metadata 8 becomes `light_gray`; no current block retains the old single colored-block state representation. Current carpet blocks retain color as a per-block `DyeColor` field rather than a block-state property.

## Collision provider behavior

**Wool (ID 35):** the old `ColoredBlock` does not override collision bounds or collision insertion. It inherits the base `Block` full unit bounds (`0,0,0` through `1,1,1`), and `addCollisions` constructs that world-offset box and appends it when the query box intersects it. Color does not change the shape. Current `Blocks.WOOL` creates vanilla `Block` instances with ordinary properties and no `noCollision()` call; base `BlockBehaviour.getShape` is `Shapes.block()`, and `getCollisionShape` returns that shape when `hasCollision` is true. This is the same full-cube provider geometry for all color variants.

**Carpet (ID 171):** old `CarpetBlock` sets bounds to `(0,0,0)`–`(1,1/16,1)` in its constructor and resets them to the same values through `updateShape(0)`; its `updateShape(WorldView,pos)` ignores world/state and delegates to the constant reset. The base collision provider offsets those bounds to the block position and adds the resulting box only on strict `Box.intersects` overlap. The shared old `Block.rayTrace` first calls `updateShape(world,pos)` and then uses the mutable min/max fields. Thus carpet ray tracing observes the same fixed 1/16 bound, with no color or neighbor-dependent geometry. Current `CarpetBlock.getShape` returns the static `Block.column(16,0,1)` shape, which the current helper converts to x/z 0–16 and y 0–1 in 0–16 block units, i.e. a full footprint of 1/16 block height. `BlockBehaviour.getCollisionShape` forwards the state outline/shape when collision is enabled; `Blocks.CARPET` properties do not disable collision. Each color-specific `WoolCarpetBlock` inherits this provider; its stored color is not consulted by `getShape`.

The wool full-cube provider and carpet 1/16-height provider are intentionally distinct. This closure supplies exact provider identities/shapes and old collision-box intersection semantics for these two families. It is not a generalized DDA adapter, and it does not prove the modern collector visits the same coordinates or invokes these providers in the same order as the old ray traversal.

## Immutable source hashes

`source-hashes.json` records SHA-256 for 14 exact source files and the corresponding canonical manifest digest. The verifier found 14/14 exact matches.
