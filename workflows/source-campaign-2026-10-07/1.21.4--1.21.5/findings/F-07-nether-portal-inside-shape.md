# F-07: Nether portal entry callbacks use the portal plane shape in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S4-CALLBACKS, S5-BLOCK-SHAPES, S5-NEIGHBORS
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `net/minecraft/world/level/block/NetherPortalBlock.java`; `NetherPortalBlock#entityInside(BlockState, Level, BlockPos, Entity)` lines 103-108 sets the entity's inside-portal state when `canUsePortal(false)`. This class has no entity-inside collision-shape override; inherited `BlockBehaviour#getEntityInsideCollisionShape(BlockState, Level, BlockPos)` lines 353-355 returns `Shapes.block()`. NetherPortalBlock SHA-256: `4c2271222823f571a099e558a3aa2c6c3ddcd95911f09f436ac4c68caa218bfe`; BlockBehaviour SHA-256: `a549c5dcf7768f89f18519bdd2f985ef498d1bd4f6b329a30e0088e437819ddf`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `NetherPortalBlock#getEntityInsideCollisionShape(BlockState, BlockGetter, BlockPos, Entity)` lines 66-69 returns `state.getShape(level, pos)`; `getShape` lines 61-64 returns the axis-oriented portal plane. The plane is built from `Block.column(4.0, 16.0, 0.0, 16.0)` and rotated by horizontal axis at line 49. `entityInside` lines 106-111 still sets inside-portal state for `canUsePortal(false)`. NetherPortalBlock SHA-256: `1cb1b0a796f491deb2317d8a2706442741a627582ca86e3cc7adb6345f975d1b`; BlockBehaviour SHA-256: `15418b0026919ef5ab97780a6ce8844677dc82d36cc4433a58f379b837d5cc50`.
- B: `ready/1.21.5/mojmap.sources.sha256`; `Entity#checkInsideBlocks(List<Movement>, StepBasedCollector)` lines 1086-1098 obtains the block's entity-inside collision shape and invokes its callback when the movement intersects that shape. Entity.java SHA-256: `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`.

## Source-level difference

The A movement-contact scan sees the default full-block entity-inside shape for a Nether portal block. B adds an override that returns the portal's axis-oriented shape, which is 4/16 of a block thick. Under the same portal state and player movement, B invokes the inside-portal callback only when the movement intersects that portal plane shape; A's full block shape can invoke it elsewhere in the same cell.

## Reachability and dependencies

Player movement -> `Entity#applyEffectsFromBlocks` -> `checkInsideBlocks` -> NetherPortalBlock entity-inside shape -> `entityInside` -> `Entity#setAsInsidePortal`. The state reaches portal transition processing on later player ticks. A's checked replacement path is the inherited BlockBehaviour default shape; B's shape uses the existing AXIS blockstate and portal column geometry. The game mode/portal cooldown gate remains part of the player callback's `canUsePortal(false)` condition.

## Consequence and uncertainty

The source proves a thinner contact volume for initiating inside-portal state. This changes where along movement through a portal cell the portal callback can be triggered, which may shift portal-processing timing. No teleport timing or trajectory was measured; cooldown, portal state and subsequent player ticks remain dependencies.

## Handoff

Independent delta: Nether portal player-contact shape. Related finding IDs: F-01. Applicability requires movement through a Nether portal block. Exact first release within the pair is unknown.
