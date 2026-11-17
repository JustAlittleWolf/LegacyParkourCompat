# Independent audit: 1.15 → 1.15.1 movement-tag resource packet

Verdict: **ACCEPT — bounded packaged resource membership only.** This verdict is bound to `build/source-mid-patches-20261117/1.15--1.15.1/resource_gate/input-reference.json`, SHA-256 `7dfc948b8b96eac2598855b320e1f97cd0cc796c83a955aaa13c5c3820baa692`.

## Input and source identity

The JSON input reference parses successfully. Its seven-entry allowlist is exactly `water`, `lava`, `fences`, `walls`, `leaves`, `portals`, and `shulker_boxes` under the corresponding `data/minecraft/tags/{fluids,blocks}/` paths, with seven entries declared for each endpoint and no out-of-allowlist extraction reported. The source manifests and provenance sidecars match their declared identities:

- 1.15 Mojmap: manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; provenance SHA-256 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`.
- 1.15.1 Mojmap: manifest SHA-256 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; provenance SHA-256 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`.

The paired source maps declare these default-namespace keys through `BlockTags.bind(new ResourceLocation(string))` and `FluidTags.bind(new ResourceLocation(string))`. `BlockTags.java` hash `2e6248215ac4075d899c71b419c1c81a1c868b7d0725b06052c92644004fa97f` and `FluidTags.java` hash `7b75994500c5286295f17227cd89ce0c42ffe829af57e6a5ff3415e41342953c` match the manifests on both sides. This confirms the resource-path mapping for the seven source identifiers.

## Independent archive verification

Fetched the exact official version metadata documents and verified their recorded SHA-256 values and client descriptors. The metadata-selected client URLs, SHA-1s and sizes matched the input reference; downloaded client archives also matched its SHA-1, SHA-256 and size pins before archive inspection:

| Version | Metadata SHA-256 | Client JAR size | Client JAR SHA-1 | Client JAR SHA-256 |
|---|---|---:|---|---|
| 1.15 | `d154eaf07885a7fd6dfc6014bb9caf08fecc19ccd9a0862b31b1c7d1639e7785` | 15,517,346 | `7b07fd09d1e3aae1bc7a1304fedc73bfe5d81800` | `41eee66b4c8e76c018745a63a46a15097cc1c6482c94b38801054938a010b21b` |
| 1.15.1 | `dda0859bca7447ff5f071643006b35a51f54e2fbef851a6665b424cf5743c598` | 15,518,397 | `8b11614bea9293592a947ea8f4fd72981ea66677` | `6e28ecd008d0ca5e1dcd346dbc2882430f02a77a78864d57acc49fc8bc1b5358` |

Independently read only the fourteen allowlisted archive entries and compared each byte-for-byte with its extracted payload file. Every entry's size, SHA-256 and JSON payload matched the input reference. Both endpoints contain identical payloads:

| Resource | Bytes | SHA-256 | Payload (`replace=false`) |
|---|---:|---|---|
| `fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `minecraft:water`, `minecraft:flowing_water` |
| `fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `minecraft:lava`, `minecraft:flowing_lava` |
| `blocks/fences.json` | 108 | `8c4ebabbf6d088340c70c29091eadfd94d11cd551b193af52ea06cc073bc7483` | `#minecraft:wooden_fences`, `minecraft:nether_brick_fence` |
| `blocks/walls.json` | 520 | `f20174f51f66d27681fd9d469a77c86cae82012c0d9125e7d246c426b772391c` | 14 wall blocks as listed in input reference |
| `blocks/leaves.json` | 224 | `c09bb9af30197720b232b9c84079039d4f344bfc2970fa0d02a1b43bc091b991` | Six leaves blocks as listed in input reference |
| `blocks/portals.json` | 128 | `fecdf2f3a61c8521647bb59802f4ecc18569d4b86786700499ac11e5718f80a9` | `minecraft:nether_portal`, `minecraft:end_portal`, `minecraft:end_gateway` |
| `blocks/shulker_boxes.json` | 637 | `c854bef377f69d8bbc88d37c641533ae7adae0f6999f72da2fea3e6e98fc9df3` | 17 shulker-box blocks as listed in input reference |

Temporary metadata and client JAR downloads were removed after verification. The audit confirms the declared official metadata/client artifact chain and archive contents; it does **not** claim a source-manifest-to-client-JAR hash binding beyond the records present in the canonical provenance and the verified artifact pins.

This accepts only the bounded packaged-resource membership packet. It does not close source-pair coverage, establish datapack override behavior, movement applicability, or runtime parity, and does not freeze the pair.
