# Provenance path claim rebind

**Verdict: ACCEPT** for revised report SHA-256 `0d82c3e2c968d05f95b91be26d5fe2ee48ab1607d6ced68e97d60e76af196229` (`.native-source/20261028/jump-amp-admission-120-121/report-revised.md`), limited to the revised provenance statement. The earlier source-behavior audit remains separately bound to the prior immutable report revision.

The revised path claim is correct: `D:\Javastuff\LegacyParkourCompat\.minecraft-decompile-cache` exists; its 1.20 directory contains the three stated `client.jar.pin.json`, `client_mappings.txt.pin.json`, and `version.json.pin.json` files. Their SHA-256 values match the 1.20 client jar, mapping file, and version metadata hashes recorded in the canonical 1.20 provenance. Cache version directories for 1.20.1, 1.20.2, and 1.20.5 are absent.

The canonical decompiled version directories for 1.20.1, 1.20.2, and 1.20.5 each contain `artifacts.sha256`; those manifests include the respective client jar, client mappings, and version metadata hashes reported in the revision. Their SHA-256 values are respectively `5f1fc7dcd4ee82ddb7ea0c3be6fd21ceb1910cc691594bda163bfa19b01c7db2`, `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`, and `f2dbd7a2e6c3fb452b0c28c93c9815e43ca24c179815f3d4fca34e484d465bb6`. The stated distinction between 1.20 cache pins and later per-version artifact manifests/provenance is properly scoped.

No movement behavior was re-audited in this rebind.
