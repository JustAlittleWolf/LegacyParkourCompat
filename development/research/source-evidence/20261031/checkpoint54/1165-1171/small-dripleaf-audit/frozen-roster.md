# Frozen source roster: Small Dripleaf item-use state path

## Scope and boundary
Compare the exact 1.17 → 1.17.1 Mojmap path for placing the double-height Small Dripleaf with an item. Check 1.16.5 registration absence only to state that this is a modern-only feature relative to that older profile. Same local-player item-use input, same initial block/fluid states and same captured placement context are the comparison assumptions. The question is whether the local client item-use path itself produces a different final block/fluid/collision/climbable state that downstream LocalPlayer travel or collision reads. Server authority/state production and unrelated item placement are excluded.

## Frozen candidate files and terminal members
- `net/minecraft/world/item/Items.java`: `SMALL_DRIPLEAF` item construction and block/item registration (1.16.5 presence/absence; 1.17 and 1.17.1 exact item factory).
- `net/minecraft/world/item/DoubleHighBlockItem.java`: 1.17.1 double-height placement override (`placeBlock`), support/upper-cell handling.
- `net/minecraft/world/item/BlockItem.java`: `place`, `placeBlock`, placement state, replaceability and neighbor-update sequence.
- `net/minecraft/world/item/context/BlockPlaceContext.java`: captured position, clicked state and placement-context reads.
- `net/minecraft/world/level/block/SmallDripleafBlock.java`: placement state, `setPlacedBy`, upper-half property/state and shape/collision/fluid/climbable reads.
- `net/minecraft/world/level/block/DoublePlantBlock.java`: relevant double-height state helper only if directly called by the selected item/block route.
- `net/minecraft/world/level/Level.java` and `net/minecraft/world/level/LevelWriter.java`: directly used client placement writes and update flags, only if the selected methods call them.
- Directly called state/property methods for waterlogging and placement replaceability, limited to exact callees discovered from the frozen methods above.

## Planned movement-consumer terminal
Check the resulting Small Dripleaf `BlockState` fluid, collision and climbable/speed properties against the direct LocalPlayer readers that consume those properties. Compare local placement updates as a direct item-use response under equal raw item-use and world state. Do not infer server placement, later server correction, or custom resource/tag behavior from Java alone. Do not claim runtime trajectory.

This is the pre-report roster. No prior discovery report, product code, wiki or backlog has been read. Any added dependency discovered during source tracing will be recorded as an addendum without editing this frozen roster.
