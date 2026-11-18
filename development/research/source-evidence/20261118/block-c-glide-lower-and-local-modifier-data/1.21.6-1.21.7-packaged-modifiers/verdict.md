# Independent audit verdict: 1.21.6–1.21.7 packaged modifier bytes

**Verdict: ACCEPTED within the stated bounds.**

Audited report: `c-queue-1216-1217-packaged-modifier-bytes/report.md`  
Audited report SHA-256: `e53dbac9f90967a87743ce0e61db2db916191b12fae8863877ba3dcbd3bc7be5`

## Evidence retraced

- The canonical cached client inputs are `.minecraft-decompile-cache/1.21.6/client.jar` (29,516,555 bytes; SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a`) and `.minecraft-decompile-cache/1.21.7/client.jar` (29,522,178 bytes; SHA-256 `6d0aa64c6a0aee3270253317f51e9e816f94cd3b16b44f5d605615c39fc62fb9`). Each digest matches its cache pin and decompile provenance input. For both sides, pinned `version.json` hashes match the local metadata; metadata IDs are the exact releases; the metadata client SHA-1 equals the object hash embedded in the client URL; and that URL equals the `client.jar` pin URL. The exact Mojang `client.txt` mapping URLs, hashes, and bytes also match the cache pins and provenance manifests.
- The packaged entries were opened directly through .NET `ZipArchive` streams; no archive was extracted. Streamed uncompressed lengths and SHA-256 values match the report on both sides, and the payload text compares byte-for-byte equal:

| Entry | Bytes | SHA-256 |
|---|---:|---|
| `depth_strider.json` | 774 | `a19a75083ed4229e1890333b5bfb71639e81c2fda5dbe80aee43251f05d5a934` |
| `soul_speed.json` | 7,312 | `01a3752231e3bacf7d65627f3c7b48349446d35c527a58347e7f28456a247e07` |
| `swift_sneak.json` | 693 | `b5417e7c4e8705c897751ae6d0ed377030216af39c788519aaf00be8d3865956` |
| `frost_walker.json` | 3,096 | `2a3c9fd51dea59e196f4288df4cc27a83dc80cb4724898e6ea95bd806996d954` |

- In the pinned Mojmap source, actual `Enchantments.java` and `LivingEntity.java` hashes match both provenance manifests and are identical between releases. The registered definitions bind Depth Strider to `WATER_MOVEMENT_EFFICIENCY` (0.33333334 per level), Soul Speed to conditional `MOVEMENT_SPEED` (0.0405 base, 0.0105 per level above first) and `MOVEMENT_EFFICIENCY` (1.0), and Swift Sneak to `SNEAKING_SPEED` (0.15 per level). `LivingEntity` consumes water movement efficiency in water travel and movement efficiency in block speed; `Player` owns the sneaking-speed attribute. These establish bounded movement relevance for the three attribute entries. Soul Speed's serialized requirements include its active/inactive branches, flying and ground flags, and soul-speed-block predicates; the exact payloads match on both versions.
- Frost Walker has no movement attribute entry: its location-changed effect replaces a disk of blocks with frosted ice, and its other checked entry is damage immunity. The report correctly treats it as a boundary case, not a direct player movement-modifier default.

## Limits

This accepts only the canonical cached inputs, metadata and mapping pins, the four named packaged entries, and their stated movement relevance. It does not establish complete pair coverage, other enchantment or world-effect behavior, intermediate patch boundaries, or runtime parity. No cache or source files were changed.