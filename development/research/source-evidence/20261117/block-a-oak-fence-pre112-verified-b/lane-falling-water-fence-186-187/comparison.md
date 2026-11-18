# 1.8.6 → 1.8.7 falling-water comparison

## Evidence check

Canonical exact-version source trees are at `decompiled_minecraft/1.8.6/ornithe-feather/` and `decompiled_minecraft/1.8.7/ornithe-feather/`. The source-only roster records each hidden manifest identity and raw client/Feather artifact hashes. Both manifests list 1,612 source files; every listed SHA-256 matched the source tree. Required methods are present in intact hashed files.

The eleven complete files checked for this assignment are byte-for-byte identical across the pair: `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, `Material.java`, `Blocks.java`, `World.java`, `Entity.java`, `LivingEntity.java`, `PlayerEntity.java`, `ClientPlayerEntity.java`, and `LocalClientPlayerEntity.java`. (The roster and detailed path list are in `roster.md`; the source-set check found no mismatched hashes.)

## Operation 1: dry oak fence cue

Both releases register the plain oak `fence` block as a `FenceBlock(Material.WOOD, ...)`. `Material.isSolid()` returns true for `WOOD`. `FenceBlock` defines its collision shape and connections but does not override `isFaceSolid`. The base `Block.isFaceSolid` returns the block material's solid predicate. Thus, the dry wood fence is a solid-face cue despite its non-cube collision shape.

`LiquidBlock.getFlow` checks the flow-level branch (`LEVEL >= 8`) and, for each horizontal neighbor, checks `isFaceSolid` first at the same height and then at `blockPos.up()`. On a cue, it normalizes the accumulated vector, adds `(0, -6, 0)`, and exits that neighbor loop; the method normalizes again before returning. The same order, gates and predicates occur in both releases. **Result: no source difference for this operation.**

Evidence in both releases: `Block.java:329–331, 1017–1021`; `Material.java:7, 60–62`; `FenceBlock.java:36–82, 85–112, 128–136`; `LiquidBlock.java:74–82, 129–160`.

## Operation 2: current, loaded-area and player gates

`Entity.tick` invokes `checkWaterCollisions`. That method passes the entity shape grown downward by `0.4F` and contracted by `0.001` to `World.applyLiquidDrag`. `World.applyLiquidDrag` floors the bounds (using max coordinate plus `1.0`), returns false if the complete area is not loaded, accumulates per-liquid-block material flow where the height gate passes, then applies current only if the accumulated vector is nonzero and `entity.hasLiquidCollision()` is true. It normalizes that vector and adds each component times `0.014` to the current velocity. It returns the liquid-presence flag separately from whether current was applied.

`PlayerEntity.hasLiquidCollision()` returns `!this.abilities.flying`, so flying players do not receive the current application. `LocalClientPlayerEntity.tick` calls `super.tick()` only while its current chunk is loaded, reaching the inherited entity water-collision call in the normal local-player tick. `LivingEntity.checkFallDamage` also checks water collisions when the entity is not already in water. These caller paths and operation order are unchanged in the compared source files. **Result: no source difference for this operation.**

Evidence in both releases: `Entity.java:300, 746–759, 1753–1755`; `LivingEntity.java:151–171`; `World.java:179–215, 1486–1527`; `Block.java:592–594`; `PlayerEntity.java:1617–1619`; `LocalClientPlayerEntity.java:105–115`.

## Bounded conclusion

For the assigned operations, source comparison closes with no difference between 1.8.6 and 1.8.7. No finding is warranted from this pair for these operations. This result does not establish other movement coverage, exact intermediate patch boundaries, implementation acceptance or runtime parity.