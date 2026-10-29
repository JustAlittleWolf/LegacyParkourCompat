# Independent review: wool and carpet source closure

## Verdict

**ACCEPT**, limited to the old/current state aliases and collision-provider facts for 1.8.9 block IDs 35 (`wool`) and 171 (`carpet`). The report does not claim DDA visitation, first-hit parity, or runtime reachability, and this review accepts none of those claims.

## Input identity and source integrity

- `finding.md` SHA-256: `43e1b41f9b7b9eb470314c2ab13c0fae5de790e099879522ae6f754ba449db79`.
- `source-hashes.json` SHA-256: `b68b62092c3f9eefc1497436ef85c25b9aea4b878cdaab04bde8a48cafb060ee`.
- Independently recomputed every listed source-file digest and matched each against both its JSON value and the canonical version manifest: **14/14 match**.

Canonical source roots used were `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather` and `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`.

## Findings checked

1. **Legacy identity and color decoding:** `Block.java` registers ID 35 as `wool` with `ColoredBlock` (line 888) and ID 171 as `carpet` with `CarpetBlock` (line 1195). Both classes define a `COLOR` property, default WHITE, and decode block metadata through `DyeColor.byId`. In `DyeColor`, `BY_ID` is populated from `getId()`, while `getMetadata()` populates a separate reversed item-metadata table. Out-of-range IDs select 0/WHITE. The report correctly distinguishes the block-state ID order from dye-item metadata.
2. **Current alias mapping:** the old ID order maps to the same 0–15 color order in current `DyeColor` and `ColorCollection`. `BlockItemIds` derives the wool and carpet ID collections from these color names; current `Blocks.WOOL` registers ordinary `Block` values and `Blocks.CARPET` registers `WoolCarpetBlock` values. `BlockStateData` contains the legacy colored wool/carpet conversions; the old `silver` name is normalized to `light_gray`. The listed 16 aliases are consistent with these sources.
3. **Wool provider:** old `ColoredBlock` has no collision override and inherits unit bounds and the base collision insertion/intersection behavior. Current wool uses ordinary collidable `Block` behavior; base outline shape is `Shapes.block()` and collision shape forwards that shape while collision is enabled. Color has no geometry effect. The report's bounded full-cube equivalence is supported.
4. **Carpet provider and mutable bounds:** old `CarpetBlock` initializes and resets bounds to x/z `[0,1]`, y `[0,1/16]`; its world/state `updateShape` delegates to that fixed reset. Base `Block.addCollisions` offsets the mutable bounds and uses strict box intersection. Base `Block.rayTrace` calls `updateShape(world,pos)` before reading min/max fields, so carpet's ray query refreshes the shared mutable bounds first. Current `CarpetBlock` returns `Block.column(16, 0, 1)`, the same full-footprint 1/16-height shape; `WoolCarpetBlock` inherits it for every dye. The report correctly treats these providers separately from wool.
5. **Scope:** the report explicitly leaves traversal visitation, provider-query ordering, and first-hit parity open. Nothing reviewed here closes those dependencies.

## Source anchors

- 1.8.9 `net/minecraft/block/Block.java`: registry lines 888/1195, base unit-bound initialization, `addCollisions`, `getCollisionShape`, and `rayTrace` calling `updateShape`.
- 1.8.9 `net/minecraft/block/ColoredBlock.java`: `COLOR`, WHITE default, `getStateFromMetadata`.
- 1.8.9 `net/minecraft/block/CarpetBlock.java`: fixed bounds and `updateShape` methods.
- 1.8.9 `net/minecraft/item/DyeColor.java`: enum IDs, separate metadata table, and out-of-range fallback.
- 26.2 `net/minecraft/world/item/DyeColor.java`, `net/minecraft/world/level/block/ColorCollection.java`, `net/minecraft/references/BlockItemIds.java`, `net/minecraft/util/datafix/fixes/BlockStateData.java`: color order, generated IDs, legacy aliases.
- 26.2 `net/minecraft/world/level/block/Blocks.java`, `CarpetBlock.java`, `WoolCarpetBlock.java`, `Block.java`, `state/BlockBehaviour.java`: provider registration and geometry.

No implementation, product checkout, build, test, or runtime was used in this review.
