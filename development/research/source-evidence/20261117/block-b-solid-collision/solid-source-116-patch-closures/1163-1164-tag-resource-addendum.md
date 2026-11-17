# Addendum: exact client resource-tag inputs

This addendum supersedes the resource-absence statement in `verdict.md`. The source-only Java tree and the separate asset cache do not contain these resources, but the exact pinned raw client JARs do.

Before inspecting ZIP entries, I verified the actual cached `client.jar` SHA-256 against both `.decompile-provenance.json` and `client.jar.pin.json`:

- 1.16.3: `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa` (all three agree).
- 1.16.4: `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0` (all three agree).

I read the four requested entries directly from each verified raw JAR: `data/minecraft/tags/blocks/walls.json`, `wall_post_override.json`, `fences.json`, and `wooden_fences.json`. All four exist in both JARs and have identical raw byte lengths and SHA-256 values across the pair.

I recursively followed `#minecraft:<tag>` values from those four roots through tag JSON entries in the same ZIPs. The simple closure contains 11 entries, no referenced tag is missing, and every corresponding entry is byte-identical across the versions. In addition to the four roots, closure members are `signs`, `banners`, `pressure_plates`, `standing_signs`, `wall_signs`, `wooden_pressure_plates`, and `stone_pressure_plates`.

Thus the previously identified gap for these shipped vanilla tag inputs is closed: the Java `WallBlock` provider predicates and their four tag roots plus simple referenced-tag closure are unchanged. This check covers the requested shipped tag closure, not custom/resource-pack overrides or unrelated resource files.
