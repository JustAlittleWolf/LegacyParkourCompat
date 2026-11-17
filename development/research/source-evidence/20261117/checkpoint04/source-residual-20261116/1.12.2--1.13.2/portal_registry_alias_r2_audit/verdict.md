# Independent source audit: portal registry alias r2

**Terminal result: SUCCESS**

## Bound artifact and source identities

Audited only `build/source-residual-20261116/1.12.2--1.13.2/portal_registry_alias_r2/report.md`, exact byte SHA-256 `4389089049c47f017e72fe5a911cde1bb2e58f8984d9928d5dd31279b85588ba`. The adjacent `report.sha256` contains the same digest.

Both endpoint ready markers independently identify the stated versions and `ornithe-feather` mapping (`status: ready`): 1.12.2 has mapping jar `feather-gen2-1.12.2+build.2-mergedv2.jar` / `...build.2.tiny`, and 1.13.2 has the corresponding `1.13.2+build.2` jars. The ready markers' `sourceManifestSha256` values match SHA-256 of the present manifests:

- `decompiled_minecraft/1.12.2/ornithe-feather.sources.sha256`: `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`.
- `decompiled_minecraft/1.13.2/ornithe-feather.sources.sha256`: `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`.

All twelve source-file SHA-256 values listed in the report were recomputed from the exact source files and matched both their manifest entries and the report values.

## Independent claim review

The source supports the stated legacy block key alias. In 1.12.2, `Block.java:960` registers numeric block ID 90 under `portal`, and `Blocks.java:384` resolves `NETHER_PORTAL` from that key. In 1.13.2, `Block.java:1081-1084` registers `nether_portal`, and `Blocks.java:840` resolves `NETHER_PORTAL` from that key.

The two listed legacy palette records are correctly bounded to IDs 1441 and 1442 in 1.13.2 `C_80754635.java:3603-3604`, each carrying `Name:'minecraft:portal'` with axis `x` and `z`, respectively. These IDs follow the old ID/metadata encoding: 90 shifted left four bits plus metadata 1 or 2. The 1.12.2 decoder at `PortalBlock.java:217-219` selects Z only when `(metadata & 3) == 2`, and otherwise X; its metadata encoder at `:80-85` maps X to 1 and Z to 2. `C_29689164.java:329-336` reconstructs the flattened palette ID from the block ID and low four metadata bits.

The schema-1510 block renaming fixer is installed at 1.13.2 `DataFixes.java:406-408`, using `C_00119465.f_76383611`; that map explicitly has `minecraft:portal` → `minecraft:nether_portal` at `C_00119465.java:22-24`. `C_81767953.java:31-40` separately rewrites `BLOCK_NAME` and `BLOCK_STATE`; for block states it updates only the `Name` field on the dynamic, retaining the other state fields. The target `PortalBlock` uses `Properties.HORIZONTAL_AXIS` at `PortalBlock.java:26`, defaults to X, and selects X/Z shapes at `:35-43`; the property is defined as exactly X/Z in `state/property/Properties.java:45`. Thus the report's stated two state mappings preserve axis and resolve to target-supported states.

The item-fixer boundary is accurately distinguished. `DataFixes.java:408` installs the item renaming fixer from `f_00266730`; `C_88295388.java:22-30` applies it to `TypeReferences.ITEM_NAME`. Separately, `ItemIdFix.java:95` maps numeric item ID 90 to the item name `minecraft:portal`, and its rule at `:346-354` is over `ITEM_STACK`. Those item operations are not the evidence for the separately typed `BLOCK_NAME` / `BLOCK_STATE` transform. The report does not overclaim arbitrary NBT fields or general alias behavior.

## Scope of verdict

The corrected report's bounded old registry-key and listed legacy palette-state claims are supported by the pinned endpoint sources. Its exclusions and limits are appropriate. This verdict accepts only those reviewed claims and the cited evidence identity; it does not claim complete 1.12.2→1.13.2 pair coverage, other aliases, malformed states, intermediate patch boundaries, implementation acceptance, or runtime parity.