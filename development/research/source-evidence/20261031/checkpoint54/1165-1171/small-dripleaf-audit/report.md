# Small Dripleaf item-use state audit (1.17 → 1.17.1)

## Disposition

There are two exact local state-production differences on the Small Dripleaf item-use path. Both are conditional consequences of client item placement, a world-state producer, rather than accepted movement-operation deltas under the parent scope. That scope compares movement under equal raw logical inputs with the same pre-move block/fluid state. Small Dripleaf is also absent in 1.16.5, so it has no older-profile behavior to emulate there. The findings below do not assert runtime trajectories or server-authoritative outcomes.

## Frozen inputs, client route, and admission

The pre-report source roster is `frozen-roster.md`; the directly discovered state/consumer dependencies are listed without changing it in `roster-addendum.md`. Sources are the exact Mojmap publications under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`. The selected files’ actual hashes and matching declared hashes are recorded in `source-hashes.tsv`; publication identities are recorded in `publication-identities.tsv`.

`MultiPlayerGameMode.useItemOn(LocalPlayer, ClientLevel, ...)` in both 1.17 and 1.17.1 reaches `ItemStack.useOn` for the normal eligible local interaction (not spectator and not item-cooldown-blocked), after sending the use-on packet. `BlockItem.useOn` constructs `BlockPlaceContext`; `BlockItem.place` first checks `context.canPlace()`, then obtains the placement state, calls virtual `placeBlock`, and writes the block. Thus this state calculation/write occurs on the client prediction path as well as in the authoritative logical level’s corresponding item-use path. Server authority and server-world production remain out of this audit’s compatibility acceptance scope.

For the flowing-water witness, the context clicks a replaceable water cell, so `BlockPlaceContext.canPlace()` is true: its exact predicate is `replaceClicked || clickedState.canBeReplaced(context)`. A valid supported lower placement is admitted by `DoublePlantBlock.getStateForPlacement` when the above cell can be replaced; Clay is in `SMALL_DRIPLEAF_PLACEABLE`. For the upper-Vine witness, use an air lower clicked cell over Clay and a replaceable Vine in the above cell; the same lower context passes `canPlace`, while DoublePlant placement checks the above cell. This is an ordinary valid replaceable upper-state case, not an arbitrary state forced into the world.

## Changed members and concrete state consequences

1. **Item factory — `Items.SMALL_DRIPLEAF`.** In 1.17 it is registered through `registerBlock(Blocks.SMALL_DRIPLEAF, ...)`, which constructs an ordinary `BlockItem`. In 1.17.1 it is registered as `new DoubleHighBlockItem(Blocks.SMALL_DRIPLEAF, ...)`.

2. **Double-height preclear — `DoubleHighBlockItem.placeBlock`.** The 1.17.1 override clears the cell above the clicked lower position to `Blocks.WATER.defaultBlockState()` if `level.isWaterAt(above)`, otherwise to AIR, using flags 27, then delegates to `BlockItem.placeBlock`. The same class/member in 1.17 has a different generic behavior for other double-height items, but Small Dripleaf does not use that class in 1.17. Consequently its relevant 1.17 ordinary-BlockItem route does not run a preclear. The client `Level` write happens before server correction.

   A valid fixture with a replaceable, attached Vine in the upper cell gives a conditional prediction difference: in 1.17 ordinary `BlockItem.placeBlock` leaves that upper Vine intact because `SmallDripleafBlock.setPlacedBy` returns immediately on `level.isClientSide()`; in 1.17.1 the double-height item preclear changes the upper Vine to AIR (unless it is water). Both server paths can add the upper Dripleaf through `setPlacedBy` because that method’s double-half write is guarded to non-client levels. Do not conflate the client prediction transient with a changed server-produced final structure. `VineBlock` is tagged `CLIMBABLE`; `LivingEntity.onClimbable()` reads the feet block state and tests that tag, and climbing handling consults that predicate. If the player is positioned so the Vine cell is the feet cell before correction, the predicted climbable read differs. This is only a conditional state-producer consequence, not an accepted direct movement delta.

3. **Placement water predicate — `SmallDripleafBlock.getStateForPlacement`.** In 1.17, after the shared DoublePlant lower-state calculation, the method sets `WATERLOGGED` from `level.getFluidState(clickedPos).getType() == Fluids.WATER`. In 1.17.1 it calls `DoublePlantBlock.copyWaterloggedFrom`, which sets the property from `LevelReader.isWaterAt(clickedPos)`. In both versions `isWaterAt` is `getFluidState(pos).is(FluidTags.WATER)`; the exact built-in fluid tag contains both `Fluids.WATER` and `Fluids.FLOWING_WATER`.

   Therefore, with the same lower clicked cell containing a non-source `Fluids.FLOWING_WATER` state and a valid support/upper placement, 1.17 computes lower Small Dripleaf `WATERLOGGED=false`, while 1.17.1 computes `WATERLOGGED=true`. The change is not merely upper-cell cleanup: the lower placement state itself diverges. `SmallDripleafBlock.getFluidState` returns the superclass fluid for false and source water for true. Since the block has no collision shape, this witness affects fluid state/contact rather than its own collision. Entity fluid scanning reads local block fluid states; `LivingEntity` movement selects its water branch from `isInWater()`, and travel consumes that branch. This yields a concrete conditional fluid/travel consequence if the placement-produced state is present at the movement scan. It remains excluded as direct movement emulation because the changed value is produced by item placement. The same distinction applies to authoritative server placement; it is outside accepted scope.

## No-diff members / controls

- `BlockItem.place`’s relevant ordering and eligibility gate are equivalent for the pair; its call is dispatched with different registered item subclasses only in 1.17.1.
- `BlockPlaceContext.canPlace` has the same clicked-cell acceptance predicate for the pair.
- `SmallDripleafBlock.setPlacedBy` has the same client-side early return and non-client upper-half write in both versions. It does not itself create a client-predicted upper Dripleaf.
- The adjacent ordinary state and fluid helpers used to explain the witness are unchanged where called (`LevelReader.isWaterAt`, `FluidTagsProvider` water membership, and direct fluid/block state getters). The changed interpretation is the caller’s type-equality versus tag-membership expression.
- Small Dripleaf is not registered in 1.16.5 `Items` and its block class is absent from that source tree. The item/state path is modern-only relative to the 1.16.5 profile.

## Acceptance boundary and remaining context

The concrete flowing-water and upper-Vine cases close whether the placement difference is reachable by ordinary client item use: both are admitted by the local context and placement-state gates under the described initial state. The local client can predict both differences before correction. They are retained as source-proven state-production findings only. Under the assigned equal-pre-move-world-state rule, neither is an accepted movement delta; if callers supply identical captured pre-move states, the movement comparison does not include the differing item-use producer. There is no unresolved Small Dripleaf placement admission question within this roster. No runtime parity claim is made.

## Source references

Line numbers refer to the exact Mojmap files whose SHA-256 values are in `source-hashes.tsv`.

| File/member | 1.17 | 1.17.1 | Relevant exact behavior |
|---|---:|---:|---|
| `world/item/Items.java`, `SMALL_DRIPLEAF` | 224 | 224–226 | ordinary BlockItem vs DoubleHighBlockItem |
| `world/item/DoubleHighBlockItem.java`, `placeBlock` | 16–21 | 16–22 | 1.17.1 above-cell water-or-air preclear before super placement |
| `world/item/BlockItem.java`, `useOn`, `place` | 44–75 | 44–75 | context and place ordering/gate |
| `world/item/context/BlockPlaceContext.java`, `canPlace` | 55–57 | 55–57 | clicked-cell acceptance predicate |
| `world/level/block/SmallDripleafBlock.java`, `getStateForPlacement` | 54–63 | 54–63 | exact type equality vs shared copy-waterlogged helper |
| `world/level/block/SmallDripleafBlock.java`, `setPlacedBy` | 65–73 | 65–73 | same client early return; upper half is non-client only |
| `world/level/block/SmallDripleafBlock.java`, `getFluidState` | 81–83 | 77–79 | waterlogged → water source; otherwise superclass fluid |
| `world/level/block/DoublePlantBlock.java`, `getStateForPlacement` | 45–53 | 45–54 | valid replaceable above cell gate |
| `world/level/block/DoublePlantBlock.java`, `copyWaterloggedFrom` | n/a | 78–82 | WATERLOGGED from `isWaterAt` |
| `world/level/LevelReader.java`, `isWaterAt` | 132–134 | 132–134 | fluid tag membership |
| `data/tags/FluidTagsProvider.java`, water tag | 18 | 18 | source and flowing water are both tagged |
| `data/tags/BlockTagsProvider.java`, `SMALL_DRIPLEAF_PLACEABLE` | 706–707 | 706–707 | Clay and Moss Block support |
| `world/level/block/VineBlock.java`, replacement | 294–303 | 294–303 | replaceable Vine allowed by context |
| `data/tags/BlockTagsProvider.java`, `CLIMBABLE` | 566+ | 566+ | Vine is included in climbable tag |
| `world/entity/LivingEntity.java`, `onClimbable` | 1423–1442 | 1427–1446 | feet-state climbability read |
| `world/entity/Entity.java`, fluid scan / `isInWater` | 580–610; 1027+ | 580–610; 1031+ | block-fluid contact and water-contact reader |
| `client/multiplayer/MultiPlayerGameMode.java`, `useItemOn` | 271–305 | 272–306 | local prediction invokes `ItemStack.useOn` after packet send |

No source generation, runtime, build, test, game launch, or product-file edit was performed.
