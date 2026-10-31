# Supplemental direct-source closure

Exact inputs are read-only Mojmap/Vineflower trees at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`. The six ready markers, source manifests, artifact sidecars, diagnostics, and source row contents were rehashed on 2026-10-31. Their values and row counts match `.native-source/20261030/1182-1194-inventory44/whole-source-path-inventory.md`. The 5,074 edge/path rows' 9,202 old/new file hash references also match the canonical sources.

The JOML dependency for `LocalPlayer.updateAutoJump` was independently verified from `.native-source/20261031/joml105-prep45/publication.json`: exact JOML 1.10.5 jar SHA-256 `cac9f22f83a7aa33eebda73c16ff5261e3cb4911b6bafcf4c79ea486099d0c9a` matches the 1.19.4 `artifacts.sha256` entry and ready sidecar. The published `Math.javap.txt` SHA-256 is `0347840f3dc8529f88cf786fd61c2d823f7c02f4bc32fa71ea327e4c448e4a8e`, matching publication metadata. This is static bytecode evidence only.

## Snow layer provider dependency

The 1.18.2 `SnowLayerBlock.canSurvive` explicitly rejects ice, packed ice, and barrier; it allows honey block and soul sand even without a full upper collision face. In 1.19+, the same logic is expressed via `SNOW_LAYER_CANNOT_SURVIVE_ON` and `SNOW_LAYER_CAN_SURVIVE_ON`.

Exact vanilla provider recipes in `BlockTagsProvider` (1.19–1.19.2) and `VanillaBlockTagsProvider` (1.19.3–1.19.4) place the same three old negative members and two old positive members in those tags. The only extra positive member is `Blocks.MUD`, introduced in the new release line. Under vanilla fixed old-era block states, this added member cannot alter an existing 1.18.2 state; the old movement-relevant survival memberships are unchanged. This closes the old world-collision inventory's data/tag-input dependency without relying on generated JSON or runtime overrides.

Source hashes:

| Version | Source | SHA-256 |
|---|---|---|
| 1.18.2 | `world/level/block/SnowLayerBlock.java` | `fe84be0a319262c1e87d16dea73c6ffaaecfad63f5e75e558bcf7461c63142ae` |
| 1.19 | `world/level/block/SnowLayerBlock.java` | `afdea90defd27e1e35e18f01c2c0d239336fe170f56ee915eaa4772797c52a47` |
| 1.19 | `data/tags/BlockTagsProvider.java` | `7386e263290f48276ad9ed1a099cb4cb630529ebac22e9dfd94d748007411ea0` |
| 1.19.4 | `data/tags/VanillaBlockTagsProvider.java` | `1213febfd8a452574cf35d789f727d6ba027aa7dd6837c707dd9f1be92fd30cc` |
| 1.19.4 | `world/level/block/SnowLayerBlock.java` | `afdea90defd27e1e35e18f01c2c0d239336fe170f56ee915eaa4772797c52a47` |
