# Directional machine provider closure: 1.8.9 to 26.2

## Bounded result

This report closes the explicit aliases, state projections and individual movement/ray-shape providers for old block IDs 23 (dispenser), 61 (unlit furnace), 62 (lit furnace), and 158 (dropper). Exact source hashes for every cited file, their canonical source-manifest entries, and both release identity files are recorded in `source-hashes.json`.

All four historical block instances inherit the 1.8.9 `Block` unit bounds initialized by `Block.<init>` (`net/minecraft/block/Block.java:201`). None of the examined `DispenserBlock`, `DropperBlock`, or `FurnaceBlock` classes overrides `rayTrace`, `updateShape`, or `getCollisionShape`; `DropperBlock` extends `DispenserBlock` (`DropperBlock.java:16`). Thus the legacy ray provider invokes the inherited no-op `Block.updateShape` (`Block.java:596`) and clips against the unchanged unit bounds (`Block.java:456–516`). The default movement provider returns a box from those same instance bounds (`Block.java:346–350`). Direction, trigger, lit state, and their block entities do not enter these shape-provider methods.

In 26.2, `BlockBehaviour.getShape` returns `Shapes.block()` by default (`world/level/block/state/BlockBehaviour.java:323–325`), while default `getCollisionShape` returns the state shape when `hasCollision` is true (`:327–328`); new properties default `hasCollision` to true (`:985`). The `Blocks` registrations for Dispenser, Furnace, and Dropper use ordinary `Properties.of()` and do not disable collision (`world/level/block/Blocks.java:674–677, 1323–1331, 2948–2952`). The current block classes add no shape/ray overrides for this family. Therefore the individual current outline and movement-collision providers are also unit cubes for every state in these mappings. This establishes local provider equivalence only; it does not establish traversal, first-hit, or whole-adapter equivalence.

## Identity and state mapping

| Old ID | 1.8.9 registration and state | 26.2 key/state mapping |
|---|---|---|
| 23 | `Block.java:875`: `dispenser`, `DispenserBlock`. `DispenserBlock.getStateFromMetadata` uses `getDirection(metadata)` and `(metadata & 8) > 0` for `TRIGGERED`; `getDirection` masks with `& 7` (`DispenserBlock.java:198–205`). | `BlockItemIds.DISPENSER` is `dispenser` (`references/BlockItemIds.java:562`). Current `DispenserBlock` has `FACING` and `TRIGGERED` (`world/level/block/DispenserBlock.java:48–50, 201–203`). The built-in state table contains false/true trigger states for all six facings (`util/datafix/fixes/BlockStateData.java:968–979`). |
| 61 | `Block.java:960`: `furnace`, `new FurnaceBlock(false)`. Its metadata decoder maps `Direction.byId(metadata)` to `FACING` and normalizes vertical-axis values to NORTH (`FurnaceBlock.java:187–194`). | `BlockItemIds.FURNACE` is `furnace` (`references/BlockItemIds.java:315`). Current `FurnaceBlock` inherits `FACING` and `LIT` from `AbstractFurnaceBlock` (`world/level/block/AbstractFurnaceBlock.java:26–29, 82–84`); the current state table pairs the unlit historical furnace states with current `furnace` states having `lit=false` (`BlockStateData.java:3393–3396`). |
| 62 | `Block.java:962`: `lit_furnace`, a distinct `FurnaceBlock(true)` instance. It decodes the same horizontal facing metadata as ID 61. `ItemIdFix` preserves the old numeric identity/name pair 61→`furnace`, 62→`lit_furnace` (`ItemIdFix.java:73–74`). | The current `BlockItemIds` key is still `furnace`; `BlockStateData` maps old `lit_furnace` facing states to current `furnace` states with `lit=true` (`BlockStateData.java:3397–3400`). Thus old instance identity becomes the current `LIT` property for this source mapping; no separate current furnace shape provider is introduced. |
| 158 | `Block.java:1174`: `dropper`, `DropperBlock`. It inherits the dispenser facing/triggered decoder and metadata encoder (`DropperBlock.java:16`; `DispenserBlock.java:202–215`). | `BlockItemIds.DROPPER` is `dropper` (`references/BlockItemIds.java:563`). Current Dropper extends Dispenser and retains its state definition (`world/level/block/DropperBlock.java:23–35`; `DispenserBlock.java:48–50, 201–203`). The built-in state table contains false/true trigger states for all six facings (`BlockStateData.java:7075–7086`). |

The exact old direction order is DOWN, UP, NORTH, SOUTH, WEST, EAST (`util/math/Direction.java:12–17`); `Direction.byId` wraps by six (`:168–169`). Dispenser/dropper additionally mask the metadata direction bits to three bits before lookup and store trigger in bit 3. Furnace metadata is not a direction bitfield with an independent lit bit: the lit value is carried by the separate old block registration (61 vs. 62), and vertical decoded directions are changed to NORTH. The report does not generalize malformed state handling beyond these source decoders.

`DispenserBlock` and `FurnaceBlock` do read neighboring block states during placement/onAdded orientation correction, and these blocks have block entities for inventory behavior. Those routines do not feed `rayTrace`, `updateShape`, or `getCollisionShape`; the shape providers above are state-independent unit bounds. This finding therefore needs no neighbor or block-entity projection for the bounded shape-provider operation.

## Applicability limits

- Closed: these four legacy identities; their cited facing/trigger/lit state encodings; their default legacy ray and movement shape providers; and current aliases/state shape providers.
- Not closed: the camera DDA cell visitation/order, custom providers elsewhere, shared-bound history effects from other block classes, whole-operation native fallback, runtime reachability, or player trajectory effects.
- No tests, builds, game/runtime launches, or product inspection were performed.

## Exact sources

The read-only canonical sources are `decompiled_minecraft/1.8.9/ornithe-feather` and `decompiled_minecraft/26.2/unobfuscated`. `source-hashes.json` binds 16 selected Java files to their canonical `.sources.sha256` rows and records the ready/provenance/source-manifest identities for both releases. Every selected Java hash matched its canonical manifest.
