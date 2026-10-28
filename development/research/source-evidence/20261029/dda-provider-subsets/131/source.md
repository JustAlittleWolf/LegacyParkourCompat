# 1.8.9 Tripwire Hook provider and alias closure

## Scope

This is a source-only closure for old block ID 131 and the current Tripwire Hook registration. It covers state decoding, movement-collision provider behavior, the mutable bounds used by the old block ray method, and the current state/shape providers. It does not close DDA traversal order, the profile's ray producer, which current `ClipContext` mode is reached, a concrete movement consequence, runtime reachability, or tick-level parity.

Canonical sources: Minecraft 1.8.9 / `ornithe-feather` (ready marker says Feather Gen2 `1.8.9+build.2`) and Minecraft 26.2 / `unobfuscated`. Every used Java file was rehashed and matched the respective canonical source manifest; full identities and hashes are in `source-hashes.sha256`.

## Old ID and state

The old `Block` registry explicitly registers ID 131 as key `tripwire_hook` with a `TripwireHookBlock` instance (`net/minecraft/block/Block.java:1118`). `Blocks.TRIPWIRE_HOOK` resolves that same key (`Blocks.java:362`). The old constructor defaults to north, unpowered, unattached, and unsuspended (`TripwireHookBlock.java:20-31`). Its metadata decoder accepts all low four-bit values: facing is `Direction.byIdHorizontal(metadata & 3)`, attached is bit 2 (`metadata & 4`), and powered is bit 3 (`metadata & 8`) (`TripwireHookBlock.java:263-265`). Thus metadata 0–15 each maps to one of the four horizontal facings and both boolean combinations; none is an invalid-state case. The `SUSPENDED` property is not encoded in metadata; `resolveVirtualProperties` derives it from whether the block below has a solid top (`:34-36`). The shape update below does not read `SUSPENDED`, `ATTACHED`, or `POWERED`.

The 26.2 registration uses `BlockItemIds.TRIPWIRE_HOOK`, whose key is `tripwire_hook`, and constructs `TripWireHookBlock` with `Properties.noCollision()` (`net/minecraft/world/level/block/Blocks.java:2663-2667`, `net/minecraft/references/BlockItemIds.java:570`). The current state has the matching `FACING`, `POWERED`, and `ATTACHED` properties and defaults north/false/false (`TripWireHookBlock.java:31-39,275-277`). It has no `SUSPENDED` property. For the geometry operation covered here, this missing property is immaterial because both old and current providers select geometry only from `FACING`.

## Collision and ray-shape lifecycle

The old movement-collision provider is unambiguously empty: `TripwireHookBlock.getCollisionShape(World, BlockPos, BlockState)` returns `null` (`TripwireHookBlock.java:38-41`). `Block.addCollisions` adds a box only when the provider result is non-null and intersects the requested query (`Block.java:339-344`). Therefore this block contributes no old player movement-collision box in any state.

The old block nevertheless has a ray-test shape. `Block.rayTrace` first calls `updateShape(world,pos)`, then clips against the shared `minX/minY/minZ/maxX/maxY/maxZ` fields (`Block.java:456-469`). `TripwireHookBlock.updateShape` reads the current state's facing and writes the fields through `setShape` on the singleton block (`TripwireHookBlock.java:204-220; Block.java:292-299`). The four returned local bounds are:

| Facing | min X | min Y | min Z | max X | max Y | max Z |
|---|---:|---:|---:|---:|---:|---:|
| EAST | 0 | `0.2F` | `0.3125F` | `0.375F` | `0.8F` | `0.6875F` |
| WEST | `0.625F` | `0.2F` | `0.3125F` | 1 | `0.8F` | `0.6875F` |
| SOUTH | `0.3125F` | `0.2F` | 0 | `0.6875F` | `0.8F` | `0.375F` |
| NORTH | `0.3125F` | `0.2F` | `0.625F` | `0.6875F` | `0.8F` | 1 |

These values come from `f=0.1875F`; the end thickness is `2*f=0.375F` and the centered cross-section is `0.5F±f`. In particular, the old ray shape is recomputed from facing at each `Block.rayTrace` call; this class does not rely on stale prior bounds for that call. The write is to mutable fields on the shared block instance, so outside observers must still be analyzed with actual caller ordering before drawing a cache/observer conclusion. `TripwireHookBlock` does not override the base no-op `resetShape` (`Block.java:654-655`).

The 26.2 class holds a state-indexed immutable shape map. Its `getShape` returns the map entry for `FACING` (`TripWireHookBlock.java:27-29,46-48`). The north base is `Block.boxZ(6.0, 0.0, 10.0, 10.0, 16.0)` (`TripWireHookBlock.java:26`), which constructs X `[5/16,11/16]`, Y `[0,10/16]`, and Z `[10/16,1]` (`Block.java:187-198`). `Shapes.rotateHorizontal` rotates that base around block center to EAST/SOUTH/WEST (`Shapes.java:402-424`). The horizontal footprint follows the old six-sixteenths cross-section and end arm, but the vertical bounds do not match: old Y is `[0.2F,0.8F]`, while current outline `getShape` Y is `[0,10/16]`. Consequently the current outline provider is not an exact replacement for the old ray-test bounds, even though it is state-based rather than mutable.

Separately, current collision is disabled by registration: `BlockBehaviour.Properties.noCollision()` sets `hasCollision=false` (`BlockBehaviour.java:1094-1097`), and the base `getCollisionShape` then returns `Shapes.empty()` when `hasCollision` is false (`:327-329`). Thus old movement collision (null/no box) and modern movement collision (empty shape) agree for this block. Do not conflate this with the outline/ray shape: `ClipContext.Block.COLLIDER` selects `getCollisionShape`, while `OUTLINE` selects `getShape` (`ClipContext.java:56-59`); this closure does not establish which mode a particular camera or movement ray producer uses.

## Applicability boundary

For a future source-backed old-state provider, the safe alias is old `tripwire_hook` ID 131 with metadata 0–15 decoded as above to current `minecraft:tripwire_hook` `FACING`, `POWERED`, and `ATTACHED`; no geometry-dependent decision may use old `SUSPENDED`. The bounded movement-collision operation can use native empty collision for all these states. For a ray operation that uses the old `Block.rayTrace` bounds, implement the old facing box with its float constants or preserve native behavior until that operation is separately closed. The current OUTLINE shape differs vertically. Nothing here establishes the concrete ray query or DDA traversal/cache effects.

## Exact source locations

- 1.8.9 `net/minecraft/block/Block.java`: registry ID 131 at 1118; collision append at 339–344; state metadata dispatch at 124–134; ray test and shape read at 456–469; mutable `setShape` at 292–299; base `resetShape` at 654–655.
- 1.8.9 `net/minecraft/block/TripwireHookBlock.java`: properties/defaults 20–31; virtual suspension 34–36; empty collision provider 38–41; facing-dependent `updateShape` 204–220; metadata decode 263–265; state definition 282–285.
- 1.8.9 `net/minecraft/block/Blocks.java`: key registration field 154 and key lookup 362.
- 26.2 `net/minecraft/world/level/block/Blocks.java`: hook registration 2663–2667.
- 26.2 `net/minecraft/references/BlockItemIds.java`: `tripwire_hook` key at 570.
- 26.2 `net/minecraft/world/level/block/TripWireHookBlock.java`: immutable shape map 26; defaults 31–39; facing shape selection 46–48; state fields 275–277.
- 26.2 `net/minecraft/world/level/block/Block.java`: `boxZ` construction 187–198.
- 26.2 `net/minecraft/world/phys/shapes/Shapes.java`: horizontal rotation map 402–424.
- 26.2 `net/minecraft/world/level/block/state/BlockBehaviour.java`: base collision shape 327–329; `noCollision` 1094–1097.
- 26.2 `net/minecraft/world/level/ClipContext.java`: `COLLIDER` vs `OUTLINE` selector 56–59.
- 26.2 `com/mojang/math/OctahedralGroup.java`: horizontal block-rotation identities at lines 68–70; direction transforms are used by `Shapes.rotateHorizontal`.

