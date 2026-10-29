# Trapdoor collision provider closure: old 1.8.9 IDs 96 and 167

## Result

For the bounded block-collision provider, old 1.8.9 wooden trapdoor ID 96 maps to current `minecraft:oak_trapdoor`, and iron trapdoor ID 167 maps to `minecraft:iron_trapdoor`. The old provider shape and the current collision shape are geometrically equal for every one of the 16 legacy metadata values, subject to the equivalent facing/open/half state. This closes these two families as default block collision providers for a DDA implementation; it does not establish DDA visitation order, ray hit ordering, or runtime parity.

## Legacy registration and state decoding

Old `Block.java` registers ID 96 as `trapdoor` and ID 167 as `iron_trapdoor`; both instantiate `TrapdoorBlock` with wood or iron material. `TrapdoorBlock` defines only `FACING`, `OPEN`, and `HALF`; the default is north, closed, bottom. `getStateFromMetadata` decodes facing from `metadata & 3` (0 north, 1 south, 2 west, 3 east), open from bit 2, and top half from bit 3. Therefore all sixteen 0–15 metadata values decode as the full Cartesian product of four horizontal facings, two open values, and two halves. For an open state, facing determines the panel plane and half is ignored by the shape formula.

## Historical collision provider and cached bounds

`TrapdoorBlock.getCollisionShape(world,pos,state)` first calls `updateShape(world,pos)`, which fetches the current block state at that position and writes the block instance's cached bounds, then delegates to `Block.getCollisionShape`, which copies those bounds into a world-coordinate `Box`. Closed bottom is `[0,0,0]..[1,3/16,1]`; closed top is `[0,13/16,0]..[1,1,1]`. Open panels are full-height and 3/16 thick: north `[0,0,13/16]..[1,1,1]`, south `[0,0,0]..[1,1,3/16]`, west `[13/16,0,0]..[1,1,1]`, east `[0,0,0]..[3/16,1,1]`.

The bounds are mutable per block instance, but the collision provider refreshes them from the queried position immediately before copying them. `rayTrace` also calls `updateShape` before delegating; the base ray-trace path refreshes shape bounds again. The reset shape is a centered 3/16-high strip, but neither collision nor raytrace uses it without first refreshing from state. The provider therefore has a mutable write on each query, but this source does not support a stale-trapdoor-bounds claim. No DDA/provider-visit behavior is inferred here.

## Current aliases and shape

26.2 `BlockStateData` explicitly registers the 16 legacy `minecraft:trapdoor` state variants against `minecraft:oak_trapdoor` variants. It also registers `minecraft:iron_trapdoor` state variants under the same iron-trapdoor key. This is state-alias evidence, not a claim about all item or world conversion paths.

Current `TrapDoorBlock.getShape` returns `SHAPES[FACING]` when open; when closed it returns `SHAPES[DOWN]` for top and `SHAPES[UP]` for bottom. `SHAPES` is built by `Shapes.rotateAll(Block.boxZ(16,13,16))`. The seed box is the north-facing vertical panel with z from 13/16 to 1; the rotation table supplies the four horizontal directions. Thus its four open panels and the closed top/bottom panels have the same coordinates as the legacy formulas above. `POWERED` and `WATERLOGGED` are additional current properties, but `getShape` does not read them, so they do not alter this collision geometry.

`BlockBehaviour` defaults `Properties.hasCollision` to true and returns the state's shape as collision shape when that flag is true. Both current oak and iron trapdoor registrations omit `noCollision()`, so the normal collision path is enabled. This comparison concerns `getCollisionShape` geometry and gates; it does not equate outline, placement, redstone, fluid, raytrace, or other modern-only behavior.

## Bounded applicability

- Old ID 96, any metadata 0–15: current key `minecraft:oak_trapdoor`; map legacy facing/open/half and use the provider geometry above.
- Old ID 167, any metadata 0–15: current key `minecraft:iron_trapdoor`; map legacy facing/open/half and use the same provider geometry.
- Current-only `POWERED`/`WATERLOGGED` values do not affect this shape; they are outside the legacy three-property model.
- Result is provider-family closure only. It does not close the overall DDA operation, source visitation, early hit behavior, or mutable-bound observation by an emulator.

## Source references

Canonical 1.8.9 source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.9/ornithe-feather`.
Canonical 26.2 source root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/26.2/unobfuscated`.
The accompanying `sources.sha256` records each source file hash and verifies every file against its canonical source manifest. The ready marker's source-manifest digest is verified for both roots.
