# Resource-gap reassessment supplement: 1.20.5 → 1.20.6

This supplement is bound to the original pair coverage audit at `1205-1206-pair-coverage-audit/audit.md` (SHA-256 `71caecc5e9ee29102234dbd537b9ffe2bf3db2462ddbe426bf94b7c2c7b56dc3`). It supersedes that audit's specific open statement that no packaged movement tag/resource inventory was available. The original file and its hash are unchanged.

## Bounded coverage verdict

**Yes: the bounded Java + packaged client tag-resource source coverage is complete for the audited vanilla player-movement dependency inventory.** The resource recovery independently enumerated all JSON files under `data/minecraft/tags/**` and the nested `data/minecraft/datapacks/update_1_21/data/minecraft/tags/**` in both exact client archives. It found 536 paths per release, the same 536-path union, and zero added, removed, or changed paths. The independent resource audit re-opened each ZIP member in place, matched all 1,072 path/hash/byte-count rows, and independently confirmed the pair diff. This closes the source/resource gap recorded in the original audit for those packaged tag inputs.

The direct movement-source tag gates visible in the canonical Java roots are represented by the inventory, including block tags for climbability, fence/wall clearance and step/soul-speed behavior, fluid tags for water and lava, and entity/equipment tags used by underwater, fall and freezing predicates. Examples rechecked in the paired table include:

- `data/minecraft/tags/blocks/climbable.json`, `fences.json`, `walls.json`, `fall_damage_resetting.json`, `portals.json`, and `soul_speed_blocks.json`;
- `data/minecraft/tags/fluids/water.json` and `lava.json`;
- `data/minecraft/tags/entity_types/fall_damage_immune.json`, `freeze_immune_entity_types.json`, `dismounts_underwater.json`, and `powder_snow_walkable_mobs.json`;
- `data/minecraft/tags/items/freeze_immune_wearables.json`.

Each example is present on both sides and marked unchanged with identical SHA-256 and byte count in `resource-diff.tsv`. Since the inventory is exhaustive for both declared tag roots, there is no specific vanilla client movement tag file or tag gate identified by the audited Java source roots that remains absent from it.

Together with the original audit's complete Java-manifest verification and hash-equal movement, response, collision/query, shape and callback implementation sources, there is no remaining identified client Java or packaged tag-resource dependency in this bounded inventory. The resource evidence says block collision shape definitions, block-state definitions and status-effect behavior are code-defined; those source classes were covered by the original Java audit. `assets/minecraft/blockstates/**` selects render models and is not a physical block-state or collision-shape input. No resource-level claim is made for those code-defined behaviors.

## Evidence identities and corrected metadata

- Resource inventory evidence: `1205-1206-resource-evidence/resource-evidence.md`, SHA-256 `d9c9686b6fae752f3559483b44338b1b8b50db856c49fee74d660e73d49c7ceb`.
- Resource inventory: `resource-inventory.tsv`, SHA-256 `08593c7f880645332ac66343297905bc4ffd1d8ac44e2f8ea9c5c379b8e51ce7`.
- Pair path/hash comparison: `resource-diff.tsv`, SHA-256 `03b69d30ef5d31f1cf96cf67eaa6f18109d5446023b634a3d555e234b38e6eb7`.
- Independent archive/resource audit: `1205-1206-resource-evidence-audit/audit.md`, SHA-256 `bb7d164b9fd5976d3f7f63ab9c13e87018bec83498b8f30688437fd7fd01564e`.
- Metadata correction: `1205-1206-resource-evidence/correction-01.md`, SHA-256 `962497d3ee2515501c59c5842a8fd91527800918dd150ebad0acd5d1a68bd042`; its sidecar hash matches.

Both recovered client archives match their exact metadata client-object SHA-1 values and the surviving ready/artifact SHA-256 pins. The corrected actual unique ZIP entry counts are **23,402 for 1.20.5** and **23,403 for 1.20.6**. The original `archive-provenance.json` says 23,402 for 1.20.6; that entry count is wrong and correction-01 binds the corrected count. For 1.20.6, the old provenance object's version-JSON URL was null. The exact URL is supplied by the hash-verified cached official version manifest (SHA-256 `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa`): `https://piston-meta.mojang.com/v1/packages/876ef010b51e9e646712d8322e383a84859d3b39/1.20.6.json`, bound to SHA-1 `876ef010b51e9e646712d8322e383a84859d3b39` and raw JSON SHA-256 `e25adfd8fe13c23465518b3059b8e44257f3f057425c61cbee5cf1bcd27f53a5`. For 1.20.5, the historical provenance-pinned URL is `https://piston-meta.mojang.com/v1/packages/90a26a9473aa77e384beadcc6c88a00edd4c9a8d/1.20.5.json`; the different URL currently listed for 1.20.5 in the cached manifest was not substituted.

## Limits

“Complete” above is limited to the Java movement partitions and vanilla client tag-resource paths in the reviewed inventory. It does not claim that every entry in the 23,402/23,403-entry archives was pair-diffed, nor does it compare rendering assets, damage resources, arbitrary external datapacks or server-sent tag/world data. The bundled `update_1_21` tags are inventoried but are not the active root tag set for these two releases. Server-produced environment inputs and runtime path/trajectory parity remain outside this source-coverage verdict; no runtime parity or separate pair-freeze decision is asserted here.
