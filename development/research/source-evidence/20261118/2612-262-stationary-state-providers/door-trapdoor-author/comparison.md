# 26.1.2 → 26.2 stationary door and trapdoor collision

## Verdict

**No stationary dry collision-shape delta found** for `DoorBlock`, `TrapDoorBlock`, or their weathering-copper subclasses between exact unobfuscated 26.1.2 and 26.2. No canonical finding draft is warranted.

## DoorBlock: legal state → occupied box

Both versions define `FACING`, `HALF`, `HINGE`, `OPEN`, and `POWERED`; the default is north, lower, left hinge, closed, unpowered (`DoorBlock.java:47–52, 63–71`). The static cached shape map is `Shapes.rotateHorizontal(Block.boxZ(16.0, 13.0, 16.0))` and the same `getShape` body selects its key (`:52, 78–85`). `Block.boxZ(16,13,16)` yields the seed box `(x,y,z)=[0,16]×[0,16]×[13,16]` in 0–16 block units, i.e. a 3/16-thick full-height leaf.

The horizontal map rotates that leaf around block center. Closed state selects `FACING`; open state selects `FACING` rotated clockwise for LEFT hinge or counterclockwise for RIGHT hinge. Equivalently: closed north/east/south/west occupy respectively `z=13..16`, `x=0..3`, `z=0..3`, `x=13..16`, with the other horizontal axis and y spanning `0..16`; open states select the same four boxes through the hinge-adjusted direction. `HALF` (upper/lower) and `POWERED` are ignored; hinge is ignored while closed. All values are legal in the declared state definition (`:270–271`), and geometry does not inspect world, position, or context.

## TrapDoorBlock: legal state → occupied box

Both versions define `FACING`, `OPEN`, `HALF`, `POWERED`, and `WATERLOGGED`; the default is north, closed, bottom, unpowered, dry (`TrapDoorBlock.java:44–48, 59–67`). Their static map is `Shapes.rotateAll(Block.boxZ(16.0,13.0,16.0))`, selected by exactly the same expression at `:48, 70–73`: open selects `FACING`; closed selects `DOWN` for TOP half and `UP` for BOTTOM half. The base box is the same 3/16-thick full face. Rotating it to UP/DOWN gives the bottom/top horizontal slabs respectively, each spanning x,z `0..16` and occupying y `0..3` / `13..16`; open states use the four vertical 3/16 edge slabs from the horizontal map above. `POWERED` and `WATERLOGGED` are ignored. `FACING` is ignored when closed and `HALF` is ignored when open. The state definition permits these values (`:165–166`); geometry has no fluid, world, position, or collision-context branch.

## Collision, outline, cache, and registrations

For both releases `BlockBehaviour.getCollisionShape` uses `state.getShape(...)` when `hasCollision` is true; its default outline shape is the same state `getShape`, and default visual shape aliases collision shape (`BlockBehaviour.java:329–347`). The registered door/trapdoor base properties retain collision (default true); their registration uses `noOcclusion`, not `noCollision`. Consequently these families' stationary collision boxes equal their selected outline boxes. The state cache records the collision result from `getCollisionShape` using `EmptyBlockGetter`, zero position, and empty context, once per state (`BlockBehaviour.java:924–948`). Both maps are static enum maps of rotated `VoxelShape`s, so the same precomputed family boxes are reused.

`Blocks.java` registers the classic wood/nether/iron families and 26.x copper forms. Copper weathering doors/trapdoors subclass the base classes and do not override shape/collision; weathering tick methods do not affect stationary geometry (`WeatheringCopperDoorBlock.java`, `WeatheringCopperTrapDoorBlock.java`). Waxed forms instantiate the base classes and copy properties. Type (`BlockSetType`) and material/property copies therefore do not select another shape. Differences in registration inventory or weathering behavior are outside this geometric verdict.

## Pair comparison and limits

Compared paired `getShape`, defaults, state definitions, `Block.boxZ`, shape rotations, collision/visual forwarding, cache construction, registrations, and weathering subclasses. Door and trapdoor shape maps, selector branches, state properties, and box dimensions are unchanged. This closes only stationary dry shape-provider behavior and the specified family cache/registration dependencies. It does not cover door/trapdoor activation or placement, fluids/responses, stepping/support-only behavior, restitution, other block families, or shared `BlockCollisions` traversal and exceptional world/entity gates.

## Exact source references

- 26.1.2 and 26.2 unobfuscated sources: `net/minecraft/world/level/block/DoorBlock.java`; `TrapDoorBlock.java`; `WeatheringCopperDoorBlock.java`; `WeatheringCopperTrapDoorBlock.java`; `Blocks.java`; `Block.java`; `world/level/block/state/BlockBehaviour.java`; `world/phys/shapes/Shapes.java`.
- Shared rotation implementation: `com/mojang/math/OctahedralGroup.java`, `SymmetricGroup3.java`.
- Source manifest hashes and SHA-256 values for every relied-on source file: `source-roster.txt`.