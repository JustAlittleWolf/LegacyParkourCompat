# 1.8.3 → 1.8.4 cached-resource correction supplement

This supplement corrects the stale availability statement in `.native-source/20261030/ordinary-pool/183-184-same-input-closure/report.md`. The report, independent review, and prior evidence were preserved. This is a bounded same-input resource check, not a pair-wide source audit.

## Cached artifacts and provenance reconciliation

Both local cached `client.jar` archives and `version.json` metadata files were hashed before any archive entries were read. The corresponding embedded `.decompile-provenance.json` records were inspected to reconcile those identities.

| Release | Cached metadata | Metadata SHA-256 | Cached client archive | Client SHA-256 | Provenance reconciliation |
|---|---|---|---|---|---|
| 1.8.3 | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.3/version.json` (`id=1.8.3`) | `4afafb220ce18063d3aec5f8bab178a5192d84ca2d63b358fee7d369aea96938` | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.3/client.jar` | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` | Both digests match the 1.8.3 embedded provenance `inputs`. Its metadata input URL is `https://piston-meta.mojang.com/v1/packages/413ad8fdcf8f29f4f1c2b4425aa0dade00f75dd4/1.8.3.json`; the metadata's `downloads.client.url` is the same URL as the provenance-pinned client input. |
| 1.8.4 | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.4/version.json` (`id=1.8.4`) | `c7220eabc36645636c8597b167d9ca67d16580611ed2d7d8cd7a3c0aec904587` | `D:/Javastuff/LegacyParkourCompat/.minecraft-decompile-cache/1.8.4/client.jar` | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | Both digests match the 1.8.4 embedded provenance `inputs`. Its metadata input URL is `https://piston-meta.mojang.com/v1/packages/043b84efde9fc25d849e979329c03101ac9b7795/1.8.4.json`; the metadata's `downloads.client.url` is the same URL as the provenance-pinned client input. |

## Read-only archive inventory and movement scope

Only after confirming both cached client digests against their provenance inputs, I inventoried each JAR and hashed its `assets/` entries.

| Release | Total entries | Unique archive paths | Asset entries and unique asset paths | `data/` entries |
|---|---:|---:|---:|---:|
| 1.8.3 | 5,598 | 5,598 | 3,085 | 0 |
| 1.8.4 | 5,597 | 5,597 | 3,085 | 0 |

The asset path sets are identical. They consist of `assets/minecraft/blockstates`, `font`, `lang`, `models`, `shaders`, `texts`, and `textures`. Exactly one asset path has different content: `assets/minecraft/lang/en_US.lang` (1.8.3: 121,090 bytes, SHA-256 `35a432c4cd0bb57186b2dc91bc4d4827c57897954f808293b8ab2c30a6118ca3`; 1.8.4: 121,209 bytes, SHA-256 `ff0d64150ebe70e332026ccdb9f3d4f14fc4365363630db85879aff98014f6ee`). No asset path was added or removed. The differing English language file contains presentation text; no packaged resource delta in this pair changes movement registration, collision shape, or the vine-climbing predicate.

## Correction and bounded disposition

Replace the report's statement that no cached 1.8.3 or 1.8.4 client JAR was present and that no archive inspection was performed. Provenance-matching cached archives were present and were inspected as recorded above. The source-defined default `portal`/`vine` registrations and the equal-effective-input movement conclusion remain supported.

The bounded same-input source-freeze recommendation remains appropriate subject to its existing equal movement-relevant received-state and default-registration assumptions. This supplement does not establish compatibility with custom or modded registries/resources, re-audit reused 1.8.2 → 1.8.3 coverage, establish runtime parity, or provide pair-wide acceptance.
