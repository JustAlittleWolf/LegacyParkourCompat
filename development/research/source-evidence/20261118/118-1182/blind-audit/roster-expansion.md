# Post-freeze dependency expansion

The frozen roster remains unchanged. A source retrace exposed these additional dependent operations that must be checked to close the assigned state/current path:

- `LivingEntity.checkFallDamage`: after movement, if `!isInWater()`, calls `updateInWaterStateAndDoWaterCurrentPushing()` again. This is a second possible water scan/current push on the same entity tick and is relevant to current state and post-move flow.
- `Entity.move` → `checkFallDamage`: direct caller boundary for that post-move scan.
- `Player.tick` calls `updateIsUnderwater()` before `super.tick()`; `Player.updateIsUnderwater` writes the player eye-water state from `isEyeInFluid(WATER)`.
- `LocalPlayer.isUnderWater` reads its `wasUnderwater` state; `LocalPlayer.updateIsUnderwater` calls the parent and preserves that state around the local player update. These methods connect eye-sample timing to movement consumers.
- `Entity.updateSwimming` reads water and underwater predicates after `updateFluidOnEyes` in `Entity.baseTick`.

This is dependency closure, not a rewrite of the frozen source roster. These members are checked on both adjacent release edges and treated as source coverage.
A second closure check records the exact state and world readers underlying the scan:

- `Level.getFluidState(BlockPos)` returns the empty fluid state before chunk lookup when `isOutsideBuildHeight`; otherwise it reads the chunk. This supplies both the fluid scan and the eye sample.
- `LevelChunk.getFluidState(BlockPos/int,int,int)` indexes its section and returns the empty fluid state for an out-of-range or all-air section; `LevelChunkSection.getFluidState` reads the current block state's fluid state.
- `Entity.touchingUnloadedChunk` calls the four-coordinate `LevelReader.hasChunksAt` overload, not the six-coordinate height-aware overload. That horizontal overload maps the inflated AABB bounds to section coordinates and requires each chunk; the out-of-build-height fallback is on `Level.getFluidState` for each sampled cell. These sources are textually unchanged across both edges.
- The adjacent 1.18.2 signature migration has only `LivingEntity` default and `Strider` overrides for `canStandOnFluid`; `Player`/`LocalPlayer` have no override. `Strider` is outside player movement scope.

Tag-membership closure on the `Tag`→`TagKey` API migration:

- 1.18.1 `Fluid.is(Tag)` delegates to `Tag.contains(fluid)`; `StaticTagHelper`/`Tag.Named` resolve that named key to the currently bound `TagCollection`.
- 1.18.2 `Fluid.is(TagKey)` delegates to its built-in registry holder's tag-key membership; `Holder.Reference` stores a set of bound keys. `FluidState.getTags` streams this complete key set.
- Both versions load tag definitions through `TagLoader` and bind/rebind them from runtime tag data. Client tag packets update these assignments (old `TagContainer`/`StaticTags` path; new `TagKey`/registry-holder binding path). The source default `FluidTagsProvider` assigns water to `{WATER,FLOWING_WATER}` and lava to `{LAVA,FLOWING_LAVA}` in each release. These are source defaults; external data-pack/server updates remain conditional.
