# Independent audit: 1.16.4→1.16.5 fluid tag resources

Verdict: **ACCEPT — bounded water/lava resource membership only.** This verdict is bound to `build/source-mid-patches-20261117/1.16.4--1.16.5/fluid_travel/resource-gate/input-reference.json` SHA-256 `b5e946b902d686ec785bd4fcfd3b13e80bd5d51033cc22df914285c890b21bde` and source report `build/source-mid-patches-20261117/1.16.4--1.16.5/fluid_travel/fluid-travel-dependency-report.md` SHA-256 `3D99D173E66DCEFD6B6EE00028099B41CDFA9F81EFA1F8F9AE31BB4A79B2661F`.

## Artifact identity and archive check

- The input reference SHA matches disk. The stable release inventory manifest SHA-256 is `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa` and matches the local copy. Its 1.16.5 entry binds the exact metadata URL and SHA-1 declared in the input reference.
- The 1.16.4 hidden provenance sidecar SHA-256 `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c` matches disk and records the metadata and client URLs with their declared SHA-256 values.
- The 1.16.5 ready marker SHA-256 `e9dd4397c6ade5f68d27f50baf285086dc367b56d2119b03a116cf3cf3b23fee` and artifact-manifest SHA-256 `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` match disk. The ready marker identifies version 1.16.5/Mojmap and pins client JAR SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`; the artifact manifest contains the same client-JAR hash.
- Independently fetched official metadata matched SHA-256 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5` (1.16.4) and `3eeeab7b3165cc5263dc26ff8fe114fdb718b75a0244efead9d19635d090ba72` (1.16.5). Both metadata documents declared the exact client URLs and SHA-1s in the input reference.
- Independently fetched client JARs matched both checksums: 1.16.4 SHA-1 `1952d94a0784e7abda230aae6a1e8fc0522dba99`, SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`; 1.16.5 SHA-1 `37fd3c903861eeff3bc24b71eed48f828b5269c8`, SHA-256 `00b5ebbc33e95ea88c1ab80601599c9827e9aa861d93ccc2cfcbbfd996e86263`.

## Allowlisted entries

I read only these four archive entries and compared their exact bytes with the packet files:

| Version | Archive entry | Bytes | SHA-256 | Values | `replace` |
|---|---|---:|---|---|---|
| 1.16.4 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `minecraft:water`, `minecraft:flowing_water` | false |
| 1.16.4 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `minecraft:lava`, `minecraft:flowing_lava` | false |
| 1.16.5 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `minecraft:water`, `minecraft:flowing_water` | false |
| 1.16.5 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `minecraft:lava`, `minecraft:flowing_lava` | false |

All four archived byte sequences, extracted packet files, sizes, SHA-256 values, and parsed JSON values matched. Temporary metadata/JAR downloads were removed after verification. No other archive entries were read.

The provenance boundary is preserved: 1.16.4 provenance directly records its metadata/client URL and hashes; for 1.16.5, the stable manifest pins metadata, metadata supplies the client URL/SHA-1, and the ready marker/artifact manifest pin client SHA-256. No stronger provenance relationship is claimed.

This accepts only the packaged water/lava tag membership input for this pair. It does not accept full pair coverage, a movement difference, runtime behavior, or a pair freeze; the source report remains partial.
