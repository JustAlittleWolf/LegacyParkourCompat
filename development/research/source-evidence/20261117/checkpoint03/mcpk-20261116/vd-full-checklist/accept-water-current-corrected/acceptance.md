# Independent source acceptance: corrected flowing-water current finding

Verdict: **ACCEPT** for corrected finding SHA-256 `242fdf2f38f1e9c7cce581ea7312ce8aea5bea18c8d05fcba81a29c90368def7` (UTF-8/LF). This is a new verdict bound to the corrected claim. The earlier acceptance bound to SHA-256 `01fac144d0c1977de5618b059072db9269690348105f32d2dc4fbcefd6027779` does not transfer.

## Independent checks

- Candidate bytes were hashed locally and match the assigned SHA.
- Exact 1.12.2 source confirms `Box.grown(dx,dy,dz)` computes `minY - dy` and `maxY + dy`; `contract(a)` calls `expand(-a)`. Thus `grown(0,-0.4F,0).contract(0.001)` yields `minY + 0.401` and `maxY - 0.401`, a vertical contraction. `Entity.checkWaterCollisions()` calls `World.applyLiquidDrag` with that query.
- 1.12.2 `World.applyLiquidDrag` floors/ceils the supplied bounds, returns false at `isAreaLoaded(..., true)` before reading cells when unavailable, scans cells of the requested material, accepts a liquid surface when `ceil(bounds.maxY) >= y + 1 - LiquidBlock.getHeightLoss(LEVEL)`, accumulates `applyMaterialDrag`/`getFlow`, normalizes a nonzero sum, and applies `0.014` to velocity when `entity.hasLiquidCollision()` is true.
- Exact 1.13 `Entity.m_69693160` independently has the same kind of loaded-area early return. It scans `getShape().contract(0.001)`, checks tagged `FluidState`s at `cellY + getHeight() >= box.minY`, updates running maximum immersion, scales each contributing flow by that depth below `0.4`, averages by count, preserves the averaged vector for `PlayerEntity` while normalizing non-player vectors, and adds `0.014` times the result to velocity. The candidate correctly does not call the loaded-area guard a 1.13 addition.
- 1.13 `FluidState.getHeight()` delegates to `Fluid.getHeight`; `FlowingFluid.getHeight` returns `state.getLevel()/9.0F`. `FlowingFluid.getFlow` derives and normalizes a direction from neighboring heights and falling/solid-face behavior. This supports the candidate's level, cell surface and flow-direction description.
- Reachability is supported: the 1.12.2 `Entity` tick calls `checkWaterCollisions`; the 1.13 `Entity` tick calls `baseTick`, which invokes its water-state update and current scan. Both `PlayerEntity.hasLiquidCollision()` overrides return `!abilities.flying`, so ordinary non-flying players receive the direct velocity response. No swimming/sprinting gate exists in the current scan. The difference is a reachable player movement operation.
- Consequence is stated carefully: changed query/surface eligibility can move boundary positions for current; shallow immersion and multiple-cell averaging/alignment can change player delta magnitude. A single full-depth cell can still produce a `0.014` magnitude. No trajectory outcome is asserted.

## Exact evidence identity

All source trees identify Ornithe Feather / Feather Gen2 build.2 in ready markers. Ready markers report exact versions and client artifact SHA; their source-manifest SHA values were independently checked against manifest bytes. Manifest-listed hashes match the cited source bytes.

- 1.12.2 client SHA `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; source manifest SHA `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. Relevant files: `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; `Box.java` `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `LiquidBlock.java` `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260`; `PlayerEntity.java` `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`.
- 1.13 client SHA `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`; source manifest SHA `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`. Relevant files: `Entity.java` `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; `FlowingFluid.java` `da81f379a77ef5da26e87503a1f605df4efbf5d5c02e6b6f68a916dc327df5dd`; `FluidState.java` `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6`.
- 1.13.1 manifest SHA `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; client SHA `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`.
- 1.13.2 manifest SHA `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; client SHA `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.

For 1.13.1 and 1.13.2, `Entity.java`, `FlowingFluid.java`, and `FluidState.java` have identical SHA-256s across both releases: respectively `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253`, and `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6`. The player override remains `!abilities.flying` in both; therefore the candidate's bounded unchanged claim is confirmed. Exact intermediate patch boundary before 1.13.0 remains unknown.

## Source paths/methods checked

`decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/{entity/Entity.java#checkWaterCollisions,world/World.java#applyLiquidDrag,util/math/Box.java#grown+contract,block/LiquidBlock.java#getFlow,entity/living/player/PlayerEntity.java#hasLiquidCollision}`; `decompiled_minecraft/1.13/ornithe-feather/net/minecraft/{entity/Entity.java#baseTick+m_03231680+checkWaterState+m_69693160,fluid/FlowingFluid.java#getFlow+getHeight,fluid/state/FluidState.java#getHeight,entity/living/player/PlayerEntity.java#hasLiquidCollision}`; identical checked scan/dependencies in 1.13.1 and 1.13.2.

