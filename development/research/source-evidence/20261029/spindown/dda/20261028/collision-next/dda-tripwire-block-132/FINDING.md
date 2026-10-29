# 1.8.9 Tripwire Block (ID 132) provider closure

## Scope and evidence

This source-only closure covers the old tripwire block's registration, metadata/state, mutable ray bounds, ordinary movement-collision provider, and the 26.2 registration and state/shape providers. It complements the accepted ID 131 Tripwire Hook closure without repeating hook geometry. It does not establish DDA visitation, first-hit parity, which ray mode a particular caller selects, player reachability, server/event behavior, or tick-level parity.

Canonical inputs are Minecraft 1.8.9 with `ornithe-feather` (Feather Gen2 `1.8.9+build.2`) and Minecraft 26.2 with `unobfuscated`. The ready markers and source-manifest digests were checked. Every selected Java file was rehashed and matched its canonical manifest entry; exact file, marker, provenance, and manifest hashes are in `source-hashes.json`.

## Legacy identity and state

1.8.9 `Block.java:1119` registers numeric ID 132 as `tripwire`, using `TripwireBlock`; `Blocks.java:155,363` resolves that key as `Blocks.TRIPWIRE`. 26.2 `BlockItemIds.java:737` defines the `tripwire` key and `Blocks.java:2668-2670` registers it as `TripWireBlock` with `noCollision()`. This establishes the same block key at the two endpoints; it does not claim a complete world-state migration mapping.

The legacy constructor defines `POWERED`, `SUSPENDED`, `ATTACHED`, `DISARMED`, and four directional connection properties. Metadata bits 0, 1, 2, and 3 map respectively to `POWERED`, `SUSPENDED`, `ATTACHED`, and `DISARMED` (`TripwireBlock.java:20-44,219-225,250-251`). Thus every low-nibble value 0–15 decodes to a combination of those four booleans; the directional properties are computed separately by `resolveVirtualProperties` (`:49-53`).

Legacy `SUSPENDED` is set on block addition from `!World.hasSolidTop(world, pos.down())` (`TripwireBlock.java:110-114`), and neighbor validation removes the block if that predicate changes (`:86-93`). `shouldConnectTo` uses hook facing for a hook neighbor; for another tripwire it connects only when both `SUSPENDED` values agree (`:202-215`). These connection bits do not feed the bounds formula below.

The current `TripWireBlock` state definition contains `POWERED`, `ATTACHED`, `DISARMED`, and `NORTH/EAST/SOUTH/WEST`, but no `SUSPENDED` (`TripWireBlock.java:31-42,240-242`). Its defaults are false (`:53-67`). Current horizontal neighbor updates write only the relevant connection bit, and `shouldConnectTo` accepts a matching-facing hook or any tripwire neighbor (`:75-99,204-205`); it has no suspended-state comparison. A current tripwire state therefore does not itself carry the legacy `SUSPENDED` input. A ray adapter needing legacy bounds must receive that old state explicitly or reconstruct it from the historical support rule with separately proven state inputs; this report does not close such reconstruction.

## Legacy ray-bound provider and movement collision

`TripwireBlock` does not override `canRayTrace` or `rayTrace`; an inherited `Block.rayTrace` calls virtual `updateShape(world,pos)` before clipping against the block instance's mutable bounds (`Block.java:456-469`). The tripwire update reads the state's `ATTACHED` and `SUSPENDED` fields and sets full-width X/Z bounds with these Y intervals (`TripwireBlock.java:97-108`):

| Legacy state | Old Y bounds |
| --- | --- |
| `SUSPENDED=false` (either `ATTACHED` value) | `[0, 0.09375]` = `[0, 1.5/16]` |
| `SUSPENDED=true, ATTACHED=false` | `[0, 0.5]` = `[0, 8/16]` |
| `SUSPENDED=true, ATTACHED=true` | `[0.0625, 0.15625]` = `[1/16, 2.5/16]` |

X and Z bounds are `[0,1]` in all three branches. The constructor's initial max Y is `0.15625` (`:30-45`), but the inherited ray path invokes `updateShape` before using the bounds. The mutable write is source-proven; this closure does not establish the caller ordering or its observer consequences.

The old movement-collision provider returns `null` in every state (`TripwireBlock.java:56-59`); `Block.addCollisions` adds a box only for a non-null provider (`Block.java:339-350`). Current registration uses `noCollision()` (`Blocks.java:2668-2670`), which disables the base movement collision shape (`BlockBehaviour.java:327-329,1094-1097`). Thus the individual ordinary movement-collision result is empty on both sides, independently of the ray/outline bounds.

## Current shape comparison

26.2 `TripWireBlock.getShape` selects only `ATTACHED` (`TripWireBlock.java:70-72`): attached uses `Block.column(16,1,2.5)`, and unattached uses `Block.column(16,0,8)` (`:43-44`). `Block.column` creates a centered X/Z footprint from sixteenth-based extents (`Block.java:177-185`). The current attached and unattached Y intervals are respectively `[1/16,2.5/16]` and `[0,8/16]`.

Those current shapes match the old two `SUSPENDED=true` branches. They do not match the old `SUSPENDED=false` interval `[0,1.5/16]`, for either value of `ATTACHED`. The current `getEntityInsideCollisionShape` also delegates to `state.getShape` (`TripWireBlock.java:144-147`); that is separate from the `noCollision` movement provider. `ClipContext` distinguishes collision from outline selection (`ClipContext.java:56-59`), but this closure does not determine which mode a specific caller uses.

## Bounded result and remaining adapter gate

- Closed: old ID 132/current key `tripwire`; old metadata bit meanings; the old support-derived `SUSPENDED` input and neighbor connection rule; old facing-independent tripwire ray-bound formulas; null old movement collision; current absent `SUSPENDED`, `ATTACHED`-selected shape formulas, and empty ordinary movement collision.
- Not closed: a lossless current-world-to-old-state conversion, especially reconstruction of `SUSPENDED`; the chosen ray mode/caller; DDA visitation or hit ordering; event producer behavior; and any movement trajectory consequence.

For a source-backed old-ray provider, do not substitute current `getShape` for the old mutable box on all tripwire states. The `[0,1.5/16]` non-suspended box has no corresponding current `getShape` branch. Ordinary player collision for this individual block needs no geometry override because both endpoints return no movement shape.

## Exact source references

- 1.8.9 `net/minecraft/block/Block.java`: ID 132 registration at 1119; collision append/getter at 339-350; inherited ray update-before-clip at 456-469.
- 1.8.9 `net/minecraft/block/TripwireBlock.java`: properties/defaults at 20-45; directional resolver at 49-53; null collision at 56-59; support-derived suspension at 86-114; update bounds at 97-108; neighbor connection rule at 202-215; metadata/state decoding at 219-251.
- 1.8.9 `net/minecraft/world/World.java`: `hasSolidTop` used by `TripwireBlock`.
- 26.2 `net/minecraft/references/BlockItemIds.java`: tripwire key at 737.
- 26.2 `net/minecraft/world/level/block/Blocks.java`: no-collision tripwire registration at 2668-2670.
- 26.2 `net/minecraft/world/level/block/TripWireBlock.java`: state fields/defaults at 31-67; shape formulas at 43-44,70-72; neighbor/property handling at 75-99,204-205; separate entity-inside shape at 144-147; state schema at 240-242.
- 26.2 `net/minecraft/world/level/block/Block.java`: column shape construction at 177-185.
- 26.2 `net/minecraft/world/level/block/state/BlockBehaviour.java`: collision shape and no-collision behavior at 327-329,1094-1097.
- 26.2 `net/minecraft/world/level/ClipContext.java`: `COLLIDER`/`OUTLINE` selection at 56-59.
