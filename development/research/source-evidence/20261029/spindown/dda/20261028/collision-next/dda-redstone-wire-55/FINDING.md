# DDA redstone wire source closure (1.8.9 → 26.2)

## Bounded result

This closes the local source facts for historical registry ID 55 (`redstone_wire`) and the current `minecraft:redstone_wire` block. It covers old metadata/state and neighbor inputs, old movement collision and inherited ray-box behavior, and current collision versus outline providers. It does not establish a ray-traversal, first-hit, interaction, or player-trajectory equivalence.

## Historical provider and state

- Exact 1.8.9 `net/minecraft/block/Block.java:943` registers ID 55 as `redstone_wire`, with one `RedstoneWireBlock` instance.
- `RedstoneWireBlock.java:35–46` defines NORTH/EAST/SOUTH/WEST connection properties plus POWER 0–15, defaults the four directions to NONE and POWER to 0, and initializes the shared bounds to `[0,0,0]–[1,1/16,1]`.
- `getStateFromMetadata` and `getMetadataFromState` at `RedstoneWireBlock.java:375–381` encode/decode POWER. Directional connection properties are resolved separately: `resolveVirtualProperties` at `:50–54` recomputes all four sides from world neighbors. `getConnection` at `:57–68` examines horizontal neighbors and their below/above states, plus the block above the wire; `shouldConnectTo` at `:305–318` considers wire, repeater direction, and signal-source neighbors. A source-compatible state view therefore needs the wire's POWER and those neighboring block identities/states to reconstruct the virtual direction properties.
- The old movement provider is unconditionally empty: `RedstoneWireBlock.getCollisionShape` at `:71–73` returns null, so `Block.addCollisions` at `Block.java:339–344` contributes no box.
- The old block inherits `Block.canRayTrace`, whose default returns true (`Block.java:356–362`), and inherits default `Block.rayTrace` (`:456–459`) plus the no-op `Block.updateShape` (`:596–598`). No RedstoneWireBlock ray or updateShape override writes the instance bounds. When the inherited provider is invoked, it clips against the constructor-set full-footprint, 1/16-high box; the connection and POWER state do not reshape those bounds.

## Current provider and mapping

- `BlockItemIds.java:736` explicitly assigns `REDSTONE_DUST` the key `minecraft:redstone_wire`; `Blocks.java:1279–1281` registers that key to `RedStoneWireBlock` with `.noCollision()`.
- `BlockBehaviour.Properties.noCollision()` clears `hasCollision` (`BlockBehaviour.java:1094–1097`), and base `getCollisionShape` returns `Shapes.empty()` when that flag is false (`:327–328`). Thus the modern movement collision provider is empty, matching the old no-box result.
- Current `RedStoneWireBlock` keeps POWER 0–15 and four directional properties (`:54–61`, `:72–88`). `makeShapes` (`:94–114`) builds a dot and directional side/up arms from the four connection properties; `getShape` returns the precomputed state-dependent shape (`:116–118`). This is outline geometry; it is not movement collision. Unlike the old shared ray bounds, the current outline shape varies with the stored connection properties.
- Current placement and neighbor updates derive connections from the surrounding world (`:121–167`, `:171–193`, `:242–273`), including the above conductor and horizontal neighbors with relevant above/below cells. The 26.2 `EntityBlockStateFix` map explicitly associates `minecraft:redstone_wire` with legacy ID 55 (`:77`). `RedstoneWireConnectionsFix` is registered at `DataFixers.java:954`; its source (`:25–43`) normalizes isolated connection properties to retain a side in a cross-shaped state. This documents a data-fix rule; it does not prove every live state was created through that migration.

## Applicability limits

- Closed: exact ID/key mapping, valid POWER range, separate neighbor-derived connection state, null/empty movement collision, old fixed ray bounds with no wire-specific ray-time writer, and current connection-dependent outline shape.
- Not closed: the runtime path that constructs a compatibility-era state view, all data-fixer execution paths, which shape channel a particular modern picking caller uses, DDA visitation/order, first-hit parity, or any movement consequence from a player-accessible ray.
- Do not use the modern redstone-wire outline shape as a replacement for the old provider box, and do not attribute an old movement-cache write to this block: its old collision getter is always null and its `updateShape` is inherited no-op.

## Exact source references

Canonical read-only exports are 1.8.9 / `ornithe-feather` and 26.2 / `unobfuscated`. The selected file hashes and ready-marker identities are recorded in `source-hashes.json`.
