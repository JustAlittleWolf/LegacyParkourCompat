# Frozen blind source roster: 1.18 → 1.18.1 → 1.18.2

Scope: independently retrace body water/lava immersion (including unloaded-chunk preflight and state writes), eye water/lava sampling and previous/current state, and ordinary non-flight water/lava travel adjustment, drag, exit impulse, and existing-fluid dependency gates on both exact adjacent edges.

Source identity: Mojmap canonical decompilations `decompiled_minecraft/{1.18,1.18.1,1.18.2}/mojmap/`; exact per-version ready-marker SHA-256 values supplied in the assignment. Compare each adjacent edge only. No implementation, wiki, research, product history, or other worker material consulted.

## Primary entity operations and ordered callers

- `net/minecraft/world/entity/Entity.java`: `tick` → `baseTick`; `baseTick` call order for `updateInWaterStateAndDoFluidPushing`, `updateFluidOnEyes`, `updateSwimming`; ordinary predicates/consumers `isInWater`, `isInLava`, `isUnderWater`, `isEyeInFluid`; `updateInWaterStateAndDoFluidPushing`; `updateInWaterStateAndDoWaterCurrentPushing`; `updateFluidOnEyes`; `updateFluidHeightAndDoFluidPushing`; `touchingUnloadedChunk`; `getFluidHeight`; `getFluidJumpThreshold`; `isPushedByFluid`; `moveRelative` (movement input consumer boundary).
- `net/minecraft/world/entity/LivingEntity.java`: player-reachable tick caller `aiStep` / travel call site; `travel` water/lava and enclosing flight/ordinary branches; dependencies `isAffectedByFluids`, `canStandOnFluid`, `getWaterSlowDown`, `getFluidFallingAdjustedMovement`; `move` and `isFree` treated as downstream collision/movement writer boundary when reached by fluid branch.
- `net/minecraft/world/entity/player/Player.java`: `travel` dispatch/abilities and non-flight wrapper; overrides `isAffectedByFluids`, `isPushedByFluid`, `canStandOnFluid`, and any version-present `getWaterSlowDown` used by the above paths.
- `net/minecraft/client/player/LocalPlayer.java`: only if its travel override/call path changes or gates the assigned ordinary water/lava branch; record observed call path, otherwise mark unchanged/uninvolved.

## Fluid-height/current and input dependencies

- `net/minecraft/world/level/material/FluidState.java`: `is(Tag/TagKey)`, `getHeight`, `getFlow`, state/tag type as version-present and used by assigned entity methods.
- `net/minecraft/world/level/material/FlowingFluid.java` (and concrete `WaterFluid.java` / `LavaFluid.java` only if called by assigned methods): `getHeight`, `getOwnHeight`, `getFlow`; capture only dependencies reached by entity immersion/current/travel.
- `net/minecraft/tags/FluidTags.java`: water/lava/static-tag providers used in immersion and eye sampling.
- `net/minecraft/world/level/Level.java` or its exact implementation of `hasChunksAt`, dimension `ultraWarm` access: preflight/current scale defaults and caller behavior, only where directly called.
- Existing fluid-state source/amount/falling and upper-cell height provider: record exact path through `FluidState`/`FlowingFluid`; no fluid simulation outside this sampling/current dependency.

## Exclusions and closure boundary

- Exclude flight physics, jump input/liquid-jump response, drowning/fire/damage, boats/non-player behavior, fluid rendering/audio, block-state mutation, and fluid propagation apart from exact state height/current providers read above.
- Do not expand into generic collision algorithm, geometry, equipment enchantments/effects, abilities, sprint, or movement input semantics; retain their exact branch/gate/call positions as dependencies.
- External runtime world/chunk/block/tag/vehicle/equipment data is conditional evidence, not a presumed default. Record vanilla tag/dimension defaults only if available in the permitted exact source resources.

Roster frozen before paired comparison. Any later discovery that expands coverage is separately recorded and does not rewrite this roster.