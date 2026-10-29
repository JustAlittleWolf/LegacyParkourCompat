# Correction 01: 1.20.5 → 1.20.6 packaged resource evidence

This immutable supplement is bound to the independent audit `1205-1206-resource-evidence-audit/audit.md`, SHA-256 `bb7d164b9fd5976d3f7f63ab9c13e87018bec83498b8f30688437fd7fd01564e`. It corrects provenance details without rewriting `archive-provenance.json`, `resource-inventory.tsv`, `resource-diff.tsv`, or `resource-evidence.md`.

## Corrections

- The 1.20.6 client JAR has **23,403 unique ZIP entries**. The prior `archive-provenance.json` value `zipEntryCount: 23402` is incorrect for 1.20.6. The 1.20.5 archive count remains 23,402.
- The exact 1.20.6 version JSON is pinned by the cached official manifest at `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache\version_manifest_v2.json`, whose SHA-256 is `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa`. Its `1.20.6` entry binds URL `https://piston-meta.mojang.com/v1/packages/876ef010b51e9e646712d8322e383a84859d3b39/1.20.6.json` to SHA-1 `876ef010b51e9e646712d8322e383a84859d3b39`. The recovered JSON has id `1.20.6`, that SHA-1, and SHA-256 `e25adfd8fe13c23465518b3059b8e44257f3f057425c61cbee5cf1bcd27f53a5`. This makes the exact 1.20.6 metadata URL and SHA-1 explicit; the earlier `archive-provenance.json` URL null was incomplete.
- 1.20.5 uses its historical version-specific provenance pin: URL `https://piston-meta.mojang.com/v1/packages/90a26a9473aa77e384beadcc6c88a00edd4c9a8d/1.20.5.json`, SHA-1 `90a26a9473aa77e384beadcc6c88a00edd4c9a8d`, SHA-256 `d4e0df98325bc46ee023141b21b1230ad6c9266745835867bf6ab3eb1051a9b7`. The current cached manifest instead lists URL `https://piston-meta.mojang.com/v1/packages/01f5f81adb74fc36f378c49cf0d6b70a60b139bb/1.20.5.json` and SHA-1 `01f5f81adb74fc36f378c49cf0d6b70a60b139bb`; that current entry is not the source used here and was not substituted for the historical provenance pin.

## Retained archive and resource result

The archive identities and scope remain bound to the prior evidence: 1.20.5 client JAR SHA-1 `c6b92b2374a629f20802bb284f98a4ee790e950a`, SHA-256 `49d5949adbb1021a7eb00c1b949f9e39934b10249cbf86d25004774d1b3ff510`; 1.20.6 client JAR SHA-1 `05b6f1c6b46a29d6ea82b4e0d42190e42402030f`, SHA-256 `02dfd345ac1ad55692d5dbc8486ac7e4fea72cd54ac494a79cd48963048e56b2`. Both SHA-256 values match the surviving ready/client pins and both JSON client-object SHA-1 values.

The declared resource scope is unchanged: all JSON tags under `data/minecraft/tags/**` and bundled `data/minecraft/datapacks/update_1_21/data/minecraft/tags/**`. It remains **536 paths per release**, **536 unchanged pair paths**, and **zero changed, added, or removed**. This correction does not claim a resource-level collision-shape or status-effect behavior comparison.