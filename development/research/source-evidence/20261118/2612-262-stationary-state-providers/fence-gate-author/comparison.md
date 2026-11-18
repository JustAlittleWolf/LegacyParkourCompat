# Fence gate stationary collision comparison

Pair: Minecraft 26.1.2 unobfuscated -> 26.2 unobfuscated. Scope is registered `FenceGateBlock` variants and dry, lawful stationary block states. Source files and sidecars are enumerated in `source-roster.md` and `provenance.md`.

## Disposition

No source-confirmed collision delta for this family. No canonical finding draft is warranted. `FenceGateBlock` differs only in the unrelated `isPathfindable` switch expression (`26.1.2` lines 117–127; `26.2` lines 117–123). The state selectors, collision shape, shape lookup, and block registrations are unchanged in movement-relevant behavior. The `Blocks` registration call syntax changes to `BlockItemIds` in 26.2; it registers the same twelve registered variants.

## Legal stationary state matrix

`HorizontalDirectionalBlock.FACING` is `BlockStateProperties.HORIZONTAL_FACING` (`HorizontalDirectionalBlock.java:10–12`), whose values come from `Direction.Plane.HORIZONTAL` (`BlockStateProperties.java:57`). The gate adds `OPEN`, `POWERED`, and `IN_WALL` (`FenceGateBlock.java:44–46, 210` in 26.1.2 and `44–46, 206` in 26.2). `StateDefinition.createMultiPropertyStates` forms the Cartesian product of each property’s possible values (`StateDefinition.java:103–126`): 4 facing values × 2 open × 2 powered × 2 in-wall = 32 legal states for each registered gate. This is a selector matrix only; no placement or neighboring-state production is included. Both endpoints register the same twelve variants: oak, spruce, birch, jungle, acacia, cherry, dark oak, pale oak, mangrove, bamboo, crimson and warped (`Blocks.java`, gate registration declarations; line inventory in source roster).

| Selector | Legal values | Closed (`OPEN=false`) collision | Open (`OPEN=true`) collision |
|---|---|---|---|
| `FACING` | north/south (axis Z) | Full X width; Z thickness 4/16 centered; Y 0..24/16 | Empty |
| `FACING` | east/west (axis X) | X thickness 4/16 centered; full Z width; Y 0..24/16 | Empty |
| `POWERED` | false / true | No effect | No effect |
| `IN_WALL` | false / true | No effect | No effect |

Those bounds follow from `SHAPE_COLLISION = rotateHorizontalAxis(Block.column(16, 4, 0, 24))` (`FenceGateBlock.java:51`), `Block.column` constructing the centered X/Z extents (`Block.java:185–189` in 26.1.2; `181–185` in 26.2), and `Shapes.rotateHorizontalAxis` mapping the supplied Z-axis shape and its 90-degree X-axis rotation (`Shapes.java:378–384`). The collision callback indexes only the facing axis and returns `Shapes.empty()` iff `OPEN` (`FenceGateBlock.java:105–109`). It reads neither the level, position nor context, and does not read `POWERED` or `IN_WALL`.

## Distinguish the other shapes

The selection above is the occupied collision shape, not the outline or occlusion shape. `getShape` selects full-gate `SHAPES` versus `SHAPES_WALL` by `IN_WALL`; that wall variant is a Boolean `ONLY_FIRST` subtraction (`FenceGateBlock.java:47–49, 73–77`). `getOcclusionShape` separately indexes wall/non-wall occlusion maps (`:53–61, 111–115`). Neither is the collision callback. Shape maps are static class-level fields shared by all gate instances; the collision callback does not read the instance wood type. Support shape is another operation: it is empty while open and uses the separate y=5..24 column while closed (`:52, 99–103`). Collision itself uses one column shape; it has no union, wall subtraction, outline or context-dependent branch. Its 24/16 maximum Y extends 8/16 above its cell.

## State-to-collision interface dependency

The ordinary block-collision iterator asks its `CollisionContext` for the candidate state's collision shape (`BlockCollisions.java:93`); the `BlockState` path dispatches to the block's `getCollisionShape` (`BlockBehaviour.java:680–685` in 26.1.2; `686–691` in 26.2). For this gate, that reaches the axis/OPEN-only method above. The no-context overload can use the per-state collision cache (`BlockBehaviour.java:680–682` in 26.1.2; `686–688` in 26.2); cache construction invokes the block callback with `EmptyBlockGetter`, origin and empty context (`:924–926` in 26.1.2; `930–932` in 26.2), which is sufficient because this gate callback ignores those inputs. The same callback remains callable with a context. Cache metadata detects shapes beyond the unit cube (`:937–938` in 26.1.2; `943–944` in 26.2), relevant to this gate's y=1.5 maximum. This records only the gate-family interface dependency, not the generic iterator's broader movement behavior.

`CollisionGetter.getBlockCollisionsFromContext` changes visibility from private in 26.1.2 to default in 26.2 (`CollisionGetter.java:104–105`). That is a generic consumer/API change outside this family slice; the iterator's gate-state shape request remains the same. No gate-specific consequence is established here.