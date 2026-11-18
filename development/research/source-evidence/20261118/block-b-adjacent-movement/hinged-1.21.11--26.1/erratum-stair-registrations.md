# Erratum — StairBlock registrations and inherited dispatch

## Scope and evidence

This correction is limited to the Blocks stair aliases/factory helpers and inherited shape dispatch in the same pinned source pair: 1.21.11 Mojmap and 26.1 unobfuscated. It supplements the original report without modifying it. Both `Blocks.java` member hashes are those recorded in the original report and are pinned by the assigned source manifests.

Sources: `net/minecraft/world/level/block/Blocks.java` in both roots; `StairBlock.java`; `WeatheringCopperStairBlock.java`.

## Correction

The original report's “Stairs (7)” count/list was incomplete. `Blocks.java` has **58 registered `_STAIRS` aliases** in both versions, with the same field names, registry IDs, and registration paths. This includes aliases omitted from the initial direct-constructor scan, including `oak_stairs`.

Registration path counts on each side:

- **47 `registerLegacyStair(id, base)` aliases.** The helper constructs `new StairBlock(base.defaultBlockState(), properties)` using `Properties.ofLegacyCopy(base)`.
- **4 `registerStair(id, base)` aliases.** The helper constructs `new StairBlock(base.defaultBlockState(), properties)` using `Properties.ofFullCopy(base)`.
- **3 inline `new StairBlock(...)` registrations:** `tuff_stairs`, `polished_tuff_stairs`, `tuff_brick_stairs`.
- **4 inline `new WeatheringCopperStairBlock(...)` registrations:** `oxidized_cut_copper_stairs`, `weathered_cut_copper_stairs`, `exposed_cut_copper_stairs`, `cut_copper_stairs`.

Total: 47 + 4 + 3 + 4 = 58. `WeatheringCopperStairBlock extends StairBlock` and defines no `getShape` override; these four registrations inherit `StairBlock.getShape` and its shape selector/cache implementation. The waxed copper stairs use the `registerStair` helper and construct `StairBlock` directly.

The exact shared alias set is:

`oak_stairs`, `cobblestone_stairs`, `brick_stairs`, `stone_brick_stairs`, `mud_brick_stairs`, `resin_brick_stairs`, `nether_brick_stairs`, `sandstone_stairs`, `spruce_stairs`, `birch_stairs`, `jungle_stairs`, `quartz_stairs`, `acacia_stairs`, `cherry_stairs`, `dark_oak_stairs`, `pale_oak_stairs`, `mangrove_stairs`, `bamboo_stairs`, `bamboo_mosaic_stairs`, `prismarine_stairs`, `prismarine_brick_stairs`, `dark_prismarine_stairs`, `red_sandstone_stairs`, `purpur_stairs`, `polished_granite_stairs`, `smooth_red_sandstone_stairs`, `mossy_stone_brick_stairs`, `polished_diorite_stairs`, `mossy_cobblestone_stairs`, `end_stone_brick_stairs`, `stone_stairs`, `smooth_sandstone_stairs`, `smooth_quartz_stairs`, `granite_stairs`, `andesite_stairs`, `red_nether_brick_stairs`, `polished_andesite_stairs`, `diorite_stairs`, `crimson_stairs`, `warped_stairs`, `blackstone_stairs`, `polished_blackstone_brick_stairs`, `polished_blackstone_stairs`, `tuff_stairs`, `polished_tuff_stairs`, `tuff_brick_stairs`, `oxidized_cut_copper_stairs`, `weathered_cut_copper_stairs`, `exposed_cut_copper_stairs`, `cut_copper_stairs`, `waxed_oxidized_cut_copper_stairs`, `waxed_weathered_cut_copper_stairs`, `waxed_exposed_cut_copper_stairs`, `waxed_cut_copper_stairs`, `cobbled_deepslate_stairs`, `polished_deepslate_stairs`, `deepslate_tile_stairs`, `deepslate_brick_stairs`.

The earlier statement that the stair list included the complete helper-backed entries was inaccurate. This correction replaces only that stair-registration count/list. The fixed-state `StairBlock.getShape` comparison and no-shape-difference verdict remain unchanged; the newly accounted weathering copper stair subclass inherits that same method.
