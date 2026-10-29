# 1.20.5 → 1.20.6 packaged movement-relevant resource inventory

Static ZIP inventory only. No archive payload was executed, extracted to source/cache, or decompiled. Hashes are SHA-256 over uncompressed resource bytes.

## Provenance and identity

- 1.20.5 version JSON: provenance-pinned URL `https://piston-meta.mojang.com/v1/packages/90a26a9473aa77e384beadcc6c88a00edd4c9a8d/1.20.5.json`; ID `1.20.5`; SHA-256 `d4e0df98325bc46ee023141b21b1230ad6c9266745835867bf6ab3eb1051a9b7`; matches the surviving 1.20.5 provenance pin. Its historical URL differs from the currently cached version-manifest entry; that current entry was not substituted.
- 1.20.6 version JSON: cached official-manifest URL `https://piston-meta.mojang.com/v1/packages/876ef010b51e9e646712d8322e383a84859d3b39/1.20.6.json`; ID `1.20.6`; raw-byte SHA-1 `876ef010b51e9e646712d8322e383a84859d3b39` matches manifest; SHA-256 `e25adfd8fe13c23465518b3059b8e44257f3f057425c61cbee5cf1bcd27f53a5`.
- 1.20.5 client URL: `https://piston-data.mojang.com/v1/objects/c6b92b2374a629f20802bb284f98a4ee790e950a/client.jar`; size 26,565,623; JSON SHA-1 `c6b92b2374a629f20802bb284f98a4ee790e950a`; local SHA-1 verified; local SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510` matches ready/artifact records.
- 1.20.6 client URL: `https://piston-data.mojang.com/v1/objects/05b6f1c6b46a29d6ea82b4e0d42190e42402030f/client.jar`; size 26,565,641; JSON SHA-1 `05b6f1c6b46a29d6ea82b4e0d42190e42402030f`; local SHA-1 verified; local SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2` matches the surviving 1.20.6 ready/artifact records.

## Inventory scope

The TSV inventories every JSON file in `data/minecraft/tags/**` and the nested `data/minecraft/datapacks/update_1_21/data/minecraft/tags/**` bundle. Rows distinguish active root tags from bundled future-update tags and classify block, fluid, item, entity-type, enchantment, and other tag namespaces. The paired TSV records every path status and old/new content hash.

Engine collision shapes, block-state definitions, and status-effect behavior are code-defined in this client archive; this inventory does not derive them from class files. `assets/minecraft/blockstates/**` is render-state model selection, not physics state. Damage and damage-effect behavior is outside movement scope. The bundled `update_1_21` datapack is not the active 1.20.5/1.20.6 root tag set.

## Pair summary

- Inventory rows: 1072 (536 for 1.20.5; 536 for 1.20.6).
- Union paths: 536; changed/added/removed: 0/0/0.
- Unchanged paths: 536.

Per-file hashes and classifications: `resource-inventory.tsv`; pair path/hash comparison: `resource-diff.tsv`.
