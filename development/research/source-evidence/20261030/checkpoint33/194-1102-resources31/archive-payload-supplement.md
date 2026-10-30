# Immutable archive-payload supplement: 1.9.4–1.10.2

This supplement closes the archive payload portion left open in `report.md`; it does not alter that report or claim full-pair coverage.

## Exact inputs

- `.native-artifacts/pinned-clients/1.9.4.jar`: 8,736,083 bytes; SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`, matching the exact input identity recorded for the canonical 1.9.4 decompilation.
- `.native-artifacts/pinned-clients/1.10.2.jar`: 8,857,653 bytes; SHA-256 `7cdf7fcdc1c92584a233bf4c42bd7f0df1bdad3007d306831fe50410692be1e9`, matching the exact input identity recorded for the canonical 1.10.2 decompilation.
- Read each archive directly with .NET `System.IO.Compression`; calculated SHA-256 for every non-directory `assets/` and `data/` entry. No extraction or modification performed.

## Resource comparison

| Archive entries | 1.9.4 | 1.10.2 |
|---|---:|---:|
| Total non-directory entries | 6,205 | 6,290 |
| `assets/minecraft/` entries | 3,295 | 3,339 |
| `data/` entries | 0 | 0 |

Across `assets/` and `data/`, 44 entries are added in 1.10.2, zero removed, and 31 same-path entries have changed content. Additions: 5 blockstate JSONs, 10 model JSONs, 2 loot tables, 16 structure templates, and 11 texture/texture metadata files. Changed content: 24 structure templates, 5 textures, 1 language file, and 1 splash text file. The five new blockstate paths are `assets/minecraft/blockstates/{structure_void,magma,red_nether_brick,bone_block,nether_wart_block}.json`. No tags or data-driven block registries occur in either archive.

## Movement dependency closure

The changed blockstate/model/texture entries are render assets. In the paired Java source trees, block registration is fixed in `net/minecraft/block/Blocks.java`; collision shape methods live on `Block` and concrete block subclasses, and player collision/predicates consume vanilla block state and those Java methods. No examined movement or collision-shape consumer loads blockstate/model/texture JSON, so these archive changes do not define authoritative collision geometry. The `.json` model bounds are not collision shapes.

The new/changed `assets/minecraft/structures/*.nbt` files can affect structure-template availability and thus server-side world-generation output in worlds where those templates are placed. That is an upstream world-state producer effect; under the assigned equal-received-world-state assumption it is not a player movement-response difference. Loot tables, language/splash content, and textures have no player movement-state consumer. No tags exist in either archive. Thus no packaged archive resource is an additional player-physics input for this bounded pair slice, with server-produced world contents kept separate.

## Result

Archive-level packaged-resource inventory is now closed for the stated client jars and source/resource-to-player-physics dependency question. Fixed Java block registration and shape/state behavior remain authoritative. The report remains a bounded dependency inventory only; it is not a full tick graph, independent audit, or pair freeze.
