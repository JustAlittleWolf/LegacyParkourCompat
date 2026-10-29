# Legacy fluid family IDs 8–11: aliases, collision, and ray-bound provider

Source comparison: Minecraft 1.8.9 / Ornithe Feather (`1.8.9/ornithe-feather`) to Minecraft 26.2 / published unobfuscated (`26.2/unobfuscated`). The canonical shared sources are read-only at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. Provenance identities are recorded in `source-manifest.json`.

## Legacy identities and state encoding

`net/minecraft/block/Block.java:851–854` registers ID 8 `flowing_water` and ID 9 `water` as distinct `FlowingLiquidBlock` and `LiquidSourceBlock` instances with `Material.WATER`; IDs 10 `flowing_lava` and 11 `lava` are the corresponding lava classes. Each registration uses the legacy `water` or `lava` block key, but the four registry IDs remain distinct.

`net/minecraft/block/LiquidBlock.java:22, 299–306` defines `LEVEL` from 0 through 15 and maps metadata directly to the state property (`getStateFromMetadata` sets `LEVEL=metadata`; `getMetadataFromState` returns `LEVEL`). The base constructor (`:24–28`) sets default level 0 and initializes the block's stored shape bounds to the full unit cube. `FlowingLiquidBlock` and `LiquidSourceBlock` inherit the ray and collision methods from `LiquidBlock`; neither declares a rayTrace, updateShape, or getCollisionShape override.

## Legacy movement collision provider

`LiquidBlock.getCollisionShape` (`:110–112`) returns `null` for all four IDs and every level. `Block.addCollisions` (`Block.java:340–344`) only appends a box when that return is non-null and intersects the query. Thus these liquid block states contribute no block AABB through this legacy provider. This is a provider fact; it does not cover fluid movement, drag, or other player-fluid operations.

## Legacy ray bounds and eligibility

`LiquidBlock.canRayTrace` (`:69–70`) accepts a state only when the caller allows liquids and `LEVEL == 0`. That is the source/flowing-state eligibility gate; it does not depend on which of IDs 8/9 or 10/11 owns the state.

The inherited `Block.rayTrace` (`Block.java:456 onward`) first calls `updateShape(world,pos)`, then intersects the segment against the block instance's stored min/max bounds. `Block.updateShape` (`:596–597`) is empty, and `LiquidBlock` initialized the instance bounds to `[0,1]` on every axis in its constructor (`:24–28`). Neither flowing nor source subclasses override those methods. Consequently the legacy direct ray provider has a full-cube stored ray box with no per-query liquid bound write, while caller eligibility suppresses nonzero levels when the liquid flag is false or level is not zero. This does not establish the old traversal's cell visitation or the exact caller's flag at a movement-relevant callsite.

## Current aliases and providers

`26.2/unobfuscated/net/minecraft/util/datafix/DataFixers.java:588` renames `minecraft:flowing_water` to `minecraft:water` and `minecraft:flowing_lava` to `minecraft:lava`. Current `references/BlockIds.java` exposes only `WATER` and `LAVA`; `world/level/block/Blocks.java:296–318` registers each as one `LiquidBlock` backed by the corresponding `Fluids` value, with `.noCollision()`. Current `BlockStateProperties.LEVEL` is also 0–15 (`:116`), and `LiquidBlock` uses that property with default 0 (`:53, 77`). The cited flowing-name fix proves the registry-name merge, not a separate metadata-to-state datafix rule; no claim about an unexamined serialized-state migration is made.

The modern `LiquidBlock.getCollisionShape` (`:81–95`) is context-dependent despite the registration's `.noCollision()`: an `alwaysCollideWithFluid` context gets a full block shape; otherwise nonzero levels are empty, and level 0 may return a living entity's liquid collision shape only when the context is above it and `canStandOnFluid` accepts the fluid. `EntityCollisionContext` (`:39–45, 56–65`) shows ordinary `CollisionContext.of(entity)` passes `alwaysCollideWithFluid=false`; standing-on-fluid still depends on the entity and fluid. The modern outline `getShape` (`LiquidBlock.java:145–147`) is empty. Thus the current API has no legacy mutable AABB cache: it computes a voxel shape from state and context, and its ordinary outline and collision queries are distinct providers.

## Bounded conclusion and open gates

The closed facts are the 1.8.9 registry identities/state encoding, null movement-collision box, stored full-cube ray bounds with the source-only caller gate, the 26.2 flowing-name aliases, and the modern context-sensitive collision/empty outline providers. This report does not claim old/new ray visitation, first-hit, query-flag, DDA, end-to-end parity, or ordinary-player reachability of the modern fluid-standing branch. Those require separate caller/traversal and context evidence. No product code, tests, builds, or runtime were inspected or changed.
