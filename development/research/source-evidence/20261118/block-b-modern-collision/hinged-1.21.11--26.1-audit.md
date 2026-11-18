# COMPLETED — blind source audit: hinged fixed-state collision

## Scope

Exact pair: Minecraft 1.21.11 Mojmap → 26.1 unobfuscated. Read-only, static source audit of the ordinary solid, dry collision shapes for SlabBlock, StairBlock, DoorBlock, and TrapDoorBlock. Included selector domains, exact Blocks registrations, local player collision-context provider, fixed shape construction/cache and dispatch. Excluded state production, fluids/effects, AutoJump/support, and all other block families, movement edges, or providers. No product, wiki, backlog, history, game, or test sources were inspected.

## Evidence identity and integrity

The source manifests match the assigned pins, and the ready markers identify 1.21.11/mojmap and 26.1/unobfuscated:

- `decompiled_minecraft/1.21.11/mojmap.sources.sha256`: `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`
- `decompiled_minecraft/26.1/unobfuscated.sources.sha256`: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`

All compared class bodies were read intact. Pinned member SHA-256 values:

| Source member | 1.21.11 Mojmap | 26.1 unobfuscated |
|---|---|---|
| `net/minecraft/world/level/block/SlabBlock.java` | `016ad58b741e761af8e44e881abc91fbfd16ddca8b83b980cd369ca98fa156bf` | `883ac7561b118096d215def3e92bcc56336f4527d2a2587f2f4f9e948e162c8e` |
| `net/minecraft/world/level/block/StairBlock.java` | `ca07df20d87cd5e06c4c5939f4ce828d3b52ccd9460103fd566eca6e23dc749d` | `2a8ad6eedc5a151af7a739ccd476f18cab4fe78e9df7c1ec601cb9687a77630b` |
| `net/minecraft/world/level/block/DoorBlock.java` | `5d0b6099e56f04946da7ba269e28e52d616fe8b25138f7da9d831b294f3cb05e` | `e97e2857c3afedd0162a93b6ceed5cea6ad13303951930f703e864c070ec0dad` |
| `net/minecraft/world/level/block/TrapDoorBlock.java` | `80fc4831ce3759894eaef67180d5f31b064449d0db4d61b05e348327c754ca8b` | `893754c8f7f127d311f7ccd5de8fea104fd377f59a086fe703dcc1aadb0cd2ef` |
| `net/minecraft/world/level/block/Blocks.java` | `cc6636f6ace603b32edd3db4c0cfcda60be08e2c6b5def783a698f105b8f7f23` | `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `cf7eff07efb53d95c8ffa633ff38c7d3f454e185f197de49eb4da279a8da9d17` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` |
| `net/minecraft/world/phys/shapes/CollisionContext.java` | `6f1ade5fa21f38d217a6c766ac34a4d2ddf54798320486ea33a7f676abfdc640` | `8702d23bea0f119e0d63ce15ff59088559a319283186372fd69adebeed4dcd69` |
| `net/minecraft/world/phys/shapes/EntityCollisionContext.java` | `f5e66f9b4d3689993b402a51a3b00b91cee82befdae02ef28945b68109091ea1` | `2f0a2bfd72a3d35121e3ef4ab80755f04cefaa7dd933988e2246b39ff5c39291` |

## Findings

**No fixed-state solid dry collision-shape difference was found in the four assigned families.** The block-local shape selectors and calculations match across the pair; the 26.1 source only changes decompiler parameter/local names and formatting in these operations.

- **Slabs:** Legal selector is `TYPE = BOTTOM | TOP | DOUBLE`; `WATERLOGGED` is held false for dry states. Bottom/top are the cached `Block.column(16, 0, 8)` / `Block.column(16, 8, 16)` shapes; double returns `Shapes.block()`. `getShape` dispatches only on `TYPE`, not context or position. Sources: `SlabBlock.java`, fields and `getShape` (lines 33–64 both sides).
- **Stairs:** Selectors are horizontal `FACING`, `HALF = TOP | BOTTOM`, `SHAPE = STRAIGHT | OUTER_LEFT | OUTER_RIGHT | INNER_LEFT | INNER_RIGHT`; `WATERLOGGED` held false. The shared cached base voxel unions and six direction maps (bottom/top × outer/straight/inner) are constructed identically. Shape dispatch chooses the map from half/shape and the direction from facing/left/right rotation, identically. Sources: `StairBlock.java`, fields/cache and `getShape` (lines 33–45, 74–90 both sides).
- **Doors:** Selectors are horizontal `FACING`, `HALF = LOWER | UPPER`, `HINGE = LEFT | RIGHT`, `OPEN`, and `POWERED`. Collision shape depends only on facing/open/hinge; half and powered do not affect it. Both versions cache `Shapes.rotateHorizontal(Block.boxZ(16, 13, 16))`, then choose facing when closed and hinge-adjusted clockwise/counterclockwise direction when open. Sources: `DoorBlock.java`, shape cache and `getShape` (lines 52, 79–85 both sides).
- **Trapdoors:** Selectors are horizontal `FACING`, `OPEN`, `HALF = TOP | BOTTOM`, `POWERED`, and dry `WATERLOGGED = false`. Both cache `Shapes.rotateAll(Block.boxZ(16, 13, 16))`. Shape selection is facing when open, otherwise `DOWN` for top-half or `UP` for bottom-half; context is ignored. Sources: `TrapDoorBlock.java`, shape cache and `getShape` (lines 48, 71–73 both sides).

The static per-state collision cache and dynamic-context route remain the same in `BlockBehaviour`: the context-free `BlockState.getCollisionShape(level,pos)` returns its cached collision shape when a cache exists; the context overload invokes the block collision-shape method with the supplied context. Cache construction calls the block with `EmptyBlockGetter`, origin and `CollisionContext.empty()`. For an entity request, `CollisionContext.of(entity)` uses `EntityCollisionContext` except for the minecart-specific branch; a local non-riding player therefore uses the generic entity context, which forwards to the state collision method and supplies entity bottom/descending/held-item data. The four audited shape bodies ignore that context, so it does not alter these shapes. Neither dispatch nor provider adds a special riding path for these blocks. Sources: `BlockBehaviour.java` state collision/cache methods; `CollisionContext.java` `of`; `EntityCollisionContext.java` constructor and forwarding method, both versions.

## Registered aliases

`Blocks.java` declarations were enumerated and cross-compared by owning field plus registry identifier. All entries below are present on both ends with the same alias-to-class assignment (58 slabs, 7 stairs, 17 doors, 17 trapdoors). Stair helper-backed entries are included.

- **Slabs (58):** `resin_brick_slab`, `prismarine_slab`, `prismarine_brick_slab`, `dark_prismarine_slab`, `oak_slab`, `spruce_slab`, `birch_slab`, `jungle_slab`, `acacia_slab`, `cherry_slab`, `dark_oak_slab`, `pale_oak_slab`, `mangrove_slab`, `bamboo_slab`, `bamboo_mosaic_slab`, `stone_slab`, `smooth_stone_slab`, `sandstone_slab`, `cut_sandstone_slab`, `petrified_oak_slab`, `cobblestone_slab`, `brick_slab`, `stone_brick_slab`, `mud_brick_slab`, `nether_brick_slab`, `quartz_slab`, `red_sandstone_slab`, `cut_red_sandstone_slab`, `purpur_slab`, `polished_granite_slab`, `smooth_red_sandstone_slab`, `mossy_stone_brick_slab`, `polished_diorite_slab`, `mossy_cobblestone_slab`, `end_stone_brick_slab`, `smooth_sandstone_slab`, `smooth_quartz_slab`, `granite_slab`, `andesite_slab`, `red_nether_brick_slab`, `polished_andesite_slab`, `diorite_slab`, `crimson_slab`, `warped_slab`, `blackstone_slab`, `polished_blackstone_brick_slab`, `polished_blackstone_slab`, `tuff_slab`, `polished_tuff_slab`, `tuff_brick_slab`, `waxed_oxidized_cut_copper_slab`, `waxed_weathered_cut_copper_slab`, `waxed_exposed_cut_copper_slab`, `waxed_cut_copper_slab`, `cobbled_deepslate_slab`, `polished_deepslate_slab`, `deepslate_tile_slab`, `deepslate_brick_slab`.
- **Stairs (7):** `tuff_stairs`, `polished_tuff_stairs`, `tuff_brick_stairs`, `waxed_oxidized_cut_copper_stairs`, `waxed_weathered_cut_copper_stairs`, `waxed_exposed_cut_copper_stairs`, `waxed_cut_copper_stairs`.
- **Doors (17):** `oak_door`, `iron_door`, `spruce_door`, `birch_door`, `jungle_door`, `acacia_door`, `cherry_door`, `dark_oak_door`, `pale_oak_door`, `mangrove_door`, `bamboo_door`, `crimson_door`, `warped_door`, `waxed_copper_door`, `waxed_exposed_copper_door`, `waxed_oxidized_copper_door`, `waxed_weathered_copper_door`.
- **Trapdoors (17):** `oak_trapdoor`, `spruce_trapdoor`, `birch_trapdoor`, `jungle_trapdoor`, `acacia_trapdoor`, `cherry_trapdoor`, `dark_oak_trapdoor`, `pale_oak_trapdoor`, `mangrove_trapdoor`, `bamboo_trapdoor`, `iron_trapdoor`, `crimson_trapdoor`, `warped_trapdoor`, `waxed_copper_trapdoor`, `waxed_exposed_copper_trapdoor`, `waxed_oxidized_copper_trapdoor`, `waxed_weathered_copper_trapdoor`.

No alias was added, removed, or retargeted within the audited families across the pair. The registry enumeration closes only these assigned class families.
