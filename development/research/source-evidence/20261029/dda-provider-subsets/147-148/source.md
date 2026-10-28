# 1.8.9 weighted pressure-plate provider and alias closure

## Result

This source-only closure covers legacy block IDs 147 and 148, the old and current state aliases, and the movement collision provider. It makes no DDA visitation, player reachability, or runtime-parity claim.

The old registrations map ID 147 to `minecraft:light_weighted_pressure_plate` and ID 148 to `minecraft:heavy_weighted_pressure_plate`. The current registry has the same two keys; current block/item data-fix tables also associate IDs 147 and 148 with those keys. Each block's sole state property is `power`, with permitted values 0 through 15 and default 0. Old metadata 0–15 directly selects that power value. The current `POWER` property has the same name, range, and default. The old per-block weights (15 and 150) are retained by current registrations; weight affects signal production, not the provider geometry described here.

## Movement collision provider

In 1.8.9, `AbstractPressurePlateBlock.getCollisionShape(World, BlockPos, BlockState)` always returns `null`. The old block collector's provider therefore adds no movement-collision box for either weighted plate at any power value. This method does not call the shared-bounds shape updater.

In 26.2, both registrations set `.noCollision()`. `BlockBehaviour.getCollisionShape` returns `Shapes.empty()` when that flag is false, so neither weighted plate contributes movement collision at any `POWER` state. This matches the old empty movement-provider result. It is specifically a collision-shape claim; the blocks still have outline shapes.

## Mutable outline / ray-trace distinction

The old `AbstractPressurePlateBlock.updateShape(WorldView, BlockPos)` reads the block state and mutates the shared block bounds according to `POWER > 0`: unpressed bounds are `[1/16,0,1/16]–[15/16,1/16,15/16]`; pressed bounds are `[1/16,0,1/16]–[15/16,1/32,15/16]`. `Block.rayTrace` calls `updateShape` before reading `minX/minY/minZ/maxX/maxY/maxZ`; the method does not restore these shared fields on its return paths. Thus this is a ray-trace outline write and residual shared-state possibility, not a movement collision-provider write.

The current `BasePressurePlateBlock` returns immutable outline shapes selected by `getSignalForState(state) > 0`. Its 14/16-wide unpressed and pressed columns use heights 1/16 and 1/32 respectively, matching the old dimensions exactly. Current movement collision is still empty because registration `.noCollision()` gates `BlockBehaviour.getCollisionShape` independently of this outline shape. No claim is made about camera/ray visitation, sequencing, or whether the residual old bounds are read elsewhere.

## State and numeric limits

- Legacy `WeightedPressurePlateBlock.POWER` is `IntegerProperty.of("power", 0, 15)`; `getStateFromMetadata(metadata)` stores the metadata directly, and the default is 0. Values 0–15 are all valid; this closure does not characterize out-of-range metadata.
- Current weighted-plate `POWER` aliases `BlockStateProperties.POWER`, defined as integer 0–15; both current blocks default it to 0.
- Both old and current outline geometry changes only on the zero/nonzero boundary. Weight values 15 and 150 affect their signal calculations and are not part of the collision-provider equivalence.
- This result covers only vanilla collision and outline providers for these two IDs. It does not establish power-signal behavior, interaction reach, neighbor updates, the old mutable-bounds observer path, or the DDA provider visitation sequence.

## Source references

| Release | Source | References |
|---|---|---|
| 1.8.9, `ornithe-feather` | `net/minecraft/block/Block.java` | Static ID registration at 1144–1152; default bounds, `getCollisionShape` and `rayTrace` at 200–208, 340–349, 456–535 |
| 1.8.9, `ornithe-feather` | `net/minecraft/block/Blocks.java` | Weighted-plate registrations at 170–171, resolved keys at 378–379 |
| 1.8.9, `ornithe-feather` | `net/minecraft/block/WeightedPressurePlateBlock.java` | `POWER`, default, metadata decode, and property definition at 13–15, 20–24, 53–65 |
| 1.8.9, `ornithe-feather` | `net/minecraft/block/AbstractPressurePlateBlock.java` | State-based shared bounds at 26–39; null collision provider at 47–49; reset method at 169–174 |
| 1.8.9, `ornithe-feather` | `net/minecraft/block/state/property/IntegerProperty.java`, `BlockState.java` | Property value bounds and state assignment validation |
| 26.2, `unobfuscated` | `net/minecraft/references/BlockItemIds.java`, `net/minecraft/world/level/block/Blocks.java` | Weighted-plate key aliases and registrations at `Blocks.java:2888–2896` |
| 26.2, `unobfuscated` | `net/minecraft/world/level/block/WeightedPressurePlateBlock.java` | `POWER` alias, default 0, and state definition at 26, 36–37, 65–68 |
| 26.2, `unobfuscated` | `net/minecraft/world/level/block/BasePressurePlateBlock.java` | Immutable pressed/unpressed outline shapes and state selector at 26–41 |
| 26.2, `unobfuscated` | `net/minecraft/world/level/block/Block.java`, `net/minecraft/world/level/block/state/BlockBehaviour.java` | `column`/box coordinate scaling at 160–184; collision-shape `hasCollision` gate at 327–328 |
| 26.2, `unobfuscated` | `net/minecraft/world/level/block/state/properties/BlockStateProperties.java` | `POWER` integer property 0–15 at 120 |
| 26.2, `unobfuscated` | `net/minecraft/util/datafix/fixes/ItemIdFix.java`, `EntityBlockStateFix.java`, `BlockStateData.java` | Numeric key aliases and registered power-state rows at 140–141, 169–170, 6698–6729 |

Canonical source identities and selected-file hashes, verified against both ready markers' source-manifest digests and exact source manifests, are recorded in `sources.sha256`.
