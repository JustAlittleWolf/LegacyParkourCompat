# Blind source audit roster freeze

## Bounded assignment

Pair: Minecraft 1.10 → 1.10.1; canonical source publication: Ornithe Feather. Audit only (1) ordinary dry oak fence horizontal connection/collision behavior when an adjacent wall is at the same height or one block above, and (2) the local client player water-current path, including flow accumulation/normalization and loaded-area/chunk gates. Vanilla block states and direct player movement responses are in scope. No wiki, product implementation/index/history, other lane, runtime, tests, build, network, or source writes are used.

## Evidence identity

- 1.10 client: Mojang client artifact `ba038efbc6d9e4a046927a7658413d0276895739`, SHA-256 `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`.
- 1.10 mapping: Ornithe Feather gen2 `1.10+build.2`, mergedv2 artifact SHA-256 `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`; mapping metadata SHA-256 `a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`.
- 1.10.1 client: Mojang client artifact `44b389fff90324c4ca18796d4428a7b8ec6c2eb0`, SHA-256 `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`.
- 1.10.1 mapping: Ornithe Feather gen2 `1.10.1+build.2`, mergedv2 artifact SHA-256 `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37`; mapping metadata SHA-256 `8648e97ee398b11151cb2d441161629abde561afed9c933c18fbc622e2c9904a`.
- The matching decompile-provenance manifests are under `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json`; manifest SHA-256: 1.10 `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`; 1.10.1 `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f`. Relevant file hashes were checked against each manifest before freeze.

## Frozen paired source roster

1. Fence's direct neighbor decision and virtual-property/shape/collision path: `net/minecraft/block/FenceBlock.java` (`shouldConnectTo`, `resolveVirtualProperties`, `getShape`, `addCollisions`, shape tables); `net/minecraft/block/Block.java` and `net/minecraft/block/state/BlockState.java` for connection predicates, block state and shape delegation; supporting `net/minecraft/block/FenceGateBlock.java`, `net/minecraft/block/Blocks.java`, and block material classes only where a concrete predicate requires them. Trace same-level horizontal neighbor positions and independently inspect whether an upper neighbor is read by this operation. Scope is ordinary oak, dry world, no gate/barrier/fence neighbor except as needed to establish exclusions.
2. Local-player water-current caller path: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` and `ClientPlayerEntity.java` tick dispatch/loading guard; inherited player/living movement entry points in `net/minecraft/entity/living/player/PlayerEntity.java` and `net/minecraft/entity/living/LivingEntity.java`; `net/minecraft/entity/Entity.java` (`isInWater`, liquid-collision eligibility); `net/minecraft/world/World.java` (`applyLiquidDrag`, loaded-area gate, block scan, liquid-height gate, accumulation/normalization and velocity writes); `net/minecraft/block/LiquidBlock.java` (`getFlow`, `applyMaterialDrag`, flow direction/obstruction/normalization); `net/minecraft/block/FlowingLiquidBlock.java`, `net/minecraft/block/state/BlockState.java`, `net/minecraft/world/WorldView.java`, and chunk/load accessors only as needed to close called gates. Follow exact control flow and preserve loop order, numeric types/constants, normalization count, return value and client/server predicates.

## Freeze boundary

This is the complete roster for this bounded audit; the comparison artifact has not been opened. No difference or acceptance claim is frozen here. If paired source reveals a required dependency outside the listed path, record the dependency and blocker before broadening the audit; do not silently widen the roster.