# Source coverage: dry ordinary solid fixtures, 26.1 through 26.1.2

Verdict: **COMPLETED — no source difference in either assigned adjacent edge; no finding draft is eligible.** Scope is only stationary Cauldron varieties, Hopper, Brewing Stand, Anvil variants, Grindstone, Stonecutter, Lectern and Enchanting Table ordinary local non-riding solid collision, for 26.1→26.1.1 and 26.1.1→26.1.2.

## Evidence identity and integrity

Canonical read-only roots: `decompiled_minecraft/{26.1,26.1.1,26.1.2}/unobfuscated`.

Each `unobfuscated.ready.json` reports `status=ready`, the exact corresponding version ID and `mapping=unobfuscated`. The three `unobfuscated.sources.sha256` file SHA-256 values match the assignment exactly: 26.1 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`; 26.1.1 `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.

Checked each listed source file's SHA-256 against its own source-manifest entry. Every check passed. All 28 scoped and shared source files below have identical file SHA-256 across all three releases, so each intact source body is byte-for-byte equivalent at both edges.

## Paired coverage and witnesses

- **Registration joins:** `net/minecraft/world/level/block/Blocks.java` registrations at 2634–2662 (Enchanting Table, Brewing Stand, Cauldron, Water Cauldron, Lava Cauldron, Powder Snow Cauldron), 2906–2935 (Anvil, Chipped Anvil, Damaged Anvil), 2979–2983 (Hopper), 5490–5514 (Grindstone, Lectern, Stonecutter). Exact IDs, concrete block classes, constructors, and registration properties are identical across all releases.
- **Cauldron shell and selectors:** `AbstractCauldronBlock.java` 28–39, 69–76: fixed `SHAPE_INSIDE`, union/difference construction of the common shell, and `getShape` returning that shell. `CauldronBlock.java` is the plain cauldron; `LayeredCauldronBlock.java` 39–57, 79–82, 135–136 binds the valid 1–3 `LEVEL` state and derives filled interior shapes, while `LavaCauldronBlock.java` 19–20, 42–44 supplies its fixed filled interior shape. Filled/interior entity-contact shapes are distinct from ordinary block collision and are not movement-solid fixtures here. `BlockStateProperties.java` 108–111 defines cauldron level bounds 1–3.
- **Hopper:** `HopperBlock.java` 42–79, 157–158. Legal `FACING` excludes UP (the five directions DOWN/N/S/E/W); `ENABLED` is boolean. Ordinary shape is a precomputed full-state function: hollow body is `ONLY_FIRST` of its outline minus the basin interior, unioned with the facing-selected spout clipped by the unit block. `getShape` dispatches by state; the interaction shape is separate.
- **Brewing Stand:** `BrewingStandBlock.java` 34–62, 100–101. Three legal Boolean bottle flags select state, but ordinary shape is fixed union of two columns and ignores them.
- **Anvils:** `AnvilBlock.java` 35–37, 47–50, 76–79, 121–122. Three registered anvil IDs share this class. Four horizontal facings collapse to two axis-keyed shapes; the occupied shape is the same four-column union rotated per axis.
- **Grindstone:** `GrindstoneBlock.java` 42–66, 103–104. Legal horizontal facing × `AttachFace` (`WALL`, `FLOOR`, `CEILING`) select a cached full-state shape. The same selected shape is returned by both `getCollisionShape` and `getShape`; construction starts with the bar and joined legs, then applies face and orientation rotations.
- **Stonecutter:** `StonecutterBlock.java` 31, 38–41, 67–70, 88–89. Horizontal facing is legal but does not affect the fixed 16×16 column with Y range 0–9/16.
- **Lectern:** `LecternBlock.java` 46–68, 96–104, 117–118. Legal state is horizontal facing × powered Boolean × has-book Boolean. Ordinary collision is the fixed union of its 16×16 two-high column and centered 8×8 column from Y=2 to 14/16; facing only rotates the outline rendered by `getShape`, not the collision method.
- **Enchanting Table:** `EnchantingTableBlock.java` 36–60. State-independent fixed 16×16 column, Y=0–12/16.
- **Common shape and state machinery:** `Block.java` 164–187 provides `box` scaling by 1/16, `boxes`, and centered column arithmetic. `BlockBehaviour.java` 329–346 and 672–685 establish default `getCollisionShape`→state shape, no-collision empty behavior, cache lookup for context-free calls, and context-bearing dispatch. `BlockState.java` and `StateDefinition.java` are identical, preserving legal states and cache construction. The explicitly state-indexed Hopper/Grindstone functions are built in the block constructor and have no context/position predicates.
- **Ordinary intake:** `Entity.java` 1133–1158 collects entity and world-border colliders, then invokes `level.getBlockCollisions(source, boundingBox)`; this path is not bypassed for an ordinary local player that is not riding. `CollisionGetter.java` 96–105 builds a normal `CollisionContext.of(source)` and `BlockCollisions`. `CollisionContext.java` and `EntityCollisionContext.java` pass that context through to `BlockState.getCollisionShape`; no fixture overrides it with a context gate. `BlockCollisions.java` 31–55, 84–104 preserves cursor filtering and checks translated shape intersection via full-block fast path or `Shapes.joinIsNotEmpty(..., AND)` against the entity box. Those bodies are unchanged at both edges.

## Exact files checked

`net/minecraft/world/level/block/Blocks.java`; `Block.java`; `state/BlockBehaviour.java`; `state/BlockState.java`; `state/StateDefinition.java`; `state/properties/BlockStateProperties.java`; `state/properties/AttachFace.java`; `state/properties/IntegerProperty.java`; `state/properties/EnumProperty.java`; `AbstractCauldronBlock.java`; `CauldronBlock.java`; `LayeredCauldronBlock.java`; `LavaCauldronBlock.java`; `HopperBlock.java`; `BrewingStandBlock.java`; `AnvilBlock.java`; `GrindstoneBlock.java`; `StonecutterBlock.java`; `LecternBlock.java`; `EnchantingTableBlock.java`; `world/level/BlockCollisions.java`; `world/level/CollisionGetter.java`; `world/phys/shapes/CollisionContext.java`; `world/phys/shapes/EntityCollisionContext.java`; `world/phys/shapes/Shapes.java`; `world/entity/Entity.java`; `world/entity/player/Player.java`; `core/Direction.java`.

## Residuals and exclusions

None within the assigned dry ordinary solid fixture scope for these adjacent edges. This is source/static coverage only. It does not establish other movement areas, other release boundaries, runtime parity, or pair-wide freeze. Excluded by assignment: 1.21.11→26.1, 26.1.2→26.2, AutoJump, support/step logic, moving blocks, entity-inside/material/callback effects, and other providers.

