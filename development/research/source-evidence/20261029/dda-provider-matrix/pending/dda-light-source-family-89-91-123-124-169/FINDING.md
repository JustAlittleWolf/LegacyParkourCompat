# Legacy light-source block providers: IDs 89, 91, 123, 124, 169

## Scope and result

This source-only closure compares selected 1.8.9 block identities and their mapped 26.2 states, limited to registry identity, metadata/state mapping, ordinary movement collision, and default ray/outline bounds. It excludes light propagation, rendering, redstone response behavior, golem spawning, and DDA visitation/hit ordering. Exact source identities and Java hashes are in `source-hashes.json`.

**Result:** All five old IDs use the legacy unit-cube `Block` collision and ray bounds. None of their old block classes overrides `getCollisionShape`, `rayTrace`, or `updateShape`; the pumpkin's facing state does not affect those bounds. The mapped 26.2 registrations also use the default full-block shape and collision provider. The one-to-one legacy identity distinction for redstone lamp lit/unlit is represented by the modern `LIT` property. The old `lit_pumpkin` identity maps to `jack_o_lantern` with the same four valid horizontal facing states. This is local provider/state evidence only; it does not close the full camera adapter or prove overall ray-trace parity.

## Old registrations and local providers (1.8.9, Ornithe Feather)

`net/minecraft/block/Block.java:1025` registers ID 89 as `glowstone` with `GlowstoneBlock`; `:1027` registers ID 91 as `lit_pumpkin` with a second `PumpkinBlock` instance; `:1103–1107` registers ID 123 `redstone_lamp` with `RedstoneLampBlock(false)` and ID 124 `lit_redstone_lamp` with `RedstoneLampBlock(true)`; `:1191` registers ID 169 `sea_lantern` with `SeaLanternBlock`.

`Block` initializes each instance's bounds to `[0,1]` on all three axes (`Block.java:198–205`, especially `:201`). Its default `getCollisionShape` returns a box from those bounds (`:346–350`). Its default `rayTrace` calls virtual `updateShape` and clips against the same six fields (`:456–466`); base `updateShape` is empty (`:596–597`). Source inspection of `GlowstoneBlock`, `PumpkinBlock`, `RedstoneLampBlock`, and `SeaLanternBlock` found no overrides of these shape/provider methods. `PumpkinBlock` defines only horizontal `FACING` for these old states; metadata decode calls `Direction.byIdHorizontal(metadata)` (`PumpkinBlock.java:122–135`). `Direction.byIdHorizontal` indexes `abs(id % 4)` (`Direction.java:172–174`), with horizontal IDs 0=SOUTH, 1=WEST, 2=NORTH, 3=EAST (`Direction.java:12–16`). Therefore all five valid facing states of ID 91's direction property retain the same unit movement collision box and unit ray bounds.

`RedstoneLampBlock`'s `lit` field differentiates the two registry instances, but its methods concern redstone updates and item drops; it does not override the collision or ray shape methods. This report makes no claim about those redstone event paths.

## Current aliases and state mapping (26.2, unobfuscated)

The exact current `BlockItemIds` declarations are `glowstone` (`:347`), `jack_o_lantern` (`:338`), `redstone_lamp` (`:573`), and `sea_lantern` (`:467`). `Blocks.java` registers those keys at `:2100–2109`, `:2131–2141`, `:2626–635`, and `:3047–3056`; Jack O'Lantern uses `CarvedPumpkinBlock`, Redstone Lamp uses `RedstoneLampBlock`, and Glowstone/Sea Lantern use the plain `Block` registration overload.

`BlockStateData` provides explicit old-to-current state rows:

- Legacy ID 89 at data 0 (`89*16 = 1424`) maps `glowstone` to `glowstone` (`BlockStateData.java:3886`). There are no shape state properties.
- Legacy ID 91 data 0–3 (`1456–1459`) maps `lit_pumpkin` facing SOUTH/WEST/NORTH/EAST to `jack_o_lantern` with the same facing (`:3889–3892`). These are the four valid old horizontal facing values described above. For unspecified metadata indices, `finalizeMaps` fills in the first registered state for that block (`:700–705`); do not treat malformed metadata 4–15 as a proven orientation-preserving mapping. Old `byIdHorizontal` wraps such values modulo four, whereas the datafix table's default is the first registered (south-facing) state.
- Legacy ID 123 data 0 (`1968`) maps `redstone_lamp` to `redstone_lamp` with `LIT=false`; legacy ID 124 data 0 (`1984`) maps `lit_redstone_lamp` to the same current key with `LIT=true` (`:4986–4987`). Thus the old distinct block-instance identities are represented by the current state bit, while both provider shapes are identical.
- Legacy ID 169 data 0 (`2704`) maps `sea_lantern` to `sea_lantern` (`:7659`). It has no shape state properties.

The mapping direction is established by `BlockStateData.register` storing the new tag at its numeric state index and binding each supplied legacy tag to that index (`:686–697`). These rows establish datafix mappings, not a claim that every live chunk or runtime state necessarily passes through this migration.

## Current collision and ray/outline providers

`BlockBehaviour.getShape` defaults to `Shapes.block()` (`:323–325`), and its default collision method returns the state's shape when `hasCollision` is true (`:327–328`). `Properties.hasCollision` defaults to true (`:985`); none of the four current registrations opts into `noCollision()` (`:1094–1095`). Source inspection found no `getShape`, `getCollisionShape`, or outline-shape override in `CarvedPumpkinBlock` or `RedstoneLampBlock`. The two plain-Block registrations likewise use the base behavior. Consequently these current states expose the default full-block outline and movement collision shape; pumpkin FACING and lamp LIT do not change that provider.

## Bounded conclusion and limits

For valid old facing values on ID 91, and for old registered states of IDs 89/123/124/169, the local ordinary movement collision provider and old ray bounds are unit cubes. The corresponding 26.2 mapped states also have default full-block movement collision and outline providers. Redstone lamp state identity and pumpkin facing projection are explicitly source-mapped as above.

This does not cover malformed pumpkin metadata, every other legacy ID that may be encountered by a camera traversal, historical visitation/first-hit order, light behavior, or gameplay/event semantics. It does not establish a usable historical DDA adapter or full-operation parity.
