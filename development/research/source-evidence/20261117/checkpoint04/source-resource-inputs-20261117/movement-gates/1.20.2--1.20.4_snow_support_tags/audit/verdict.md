# Independent audit: 1.20.2–1.20.4 snow support tag packet

**Verdict: ACCEPT.** This verdict binds input reference SHA-256 `505efc5f34050a4e1a45031eade9d54b1051ed2271839092c50216ab55da57f4` at `build/source-resource-inputs-20261117/movement-gates/1.20.2--1.20.4_snow_support_tags/input-reference.json`.

## Identity and pin checks

For both 1.20.2 and 1.20.4, the ready marker and provenance identify the exact release with Mojmap. Each ready marker's source and artifact manifest digests match the referenced on-disk manifests. The listed `BlockTags.java` SHA-256 (`c40cebc2b59225e4cba18f057d519fbd00abed513091ef98610c0a9a0d993d22`) matches the local file and each source-manifest row. Both versions declare `SNOW_LAYER_CANNOT_SURVIVE_ON` and `SNOW_LAYER_CAN_SURVIVE_ON` using `create("snow_layer_...")`; `create(String)` constructs a `ResourceLocation(String)`, whose no-namespace default is `minecraft`. This supports the tag identifier crosswalk.

The local client archives exist at the reference paths. Their computed SHA-256 values match both `clientSha256` in each ready marker and the `client.jar` row in the exact root `artifacts.sha256` file: 1.20.2 `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`; 1.20.4 `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838`. Computed SHA-1 values match the referenced local official metadata JSON `downloads.client.sha1` and URL object: 1.20.2 `82d1974e75fc984c5ed4b038e764e50958ac61a0`; 1.20.4 `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a`. Each metadata file's computed SHA-256 matches the reference. The provenance sidecars bind version and namespace and source-run details but do not record a client-jar digest or artifact path; the packet states this limitation accurately.

## Allowlisted archive entries

I opened each pinned client archive and checked only the four assigned snow-tag entries. Each archive entry's decompressed bytes match the extracted output path byte-for-byte, including reported size and SHA-256. Parsed JSON has no `replace` field.

| Tag pair | Archive path(s) | Size | SHA-256 | Values |
| --- | --- | ---: | --- | --- |
| `SNOW_LAYER_CANNOT_SURVIVE_ON` at both releases | `data/minecraft/tags/blocks/snow_layer_cannot_survive_on.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` |
| `SNOW_LAYER_CAN_SURVIVE_ON` at both releases | `data/minecraft/tags/blocks/snow_layer_can_survive_on.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` |

Corresponding payloads are byte-identical across the 1.20.2 and 1.20.4 archives. No extra archive entries were extracted or checked.

## Limits

This accepts only the four packaged payloads and their stated pinned-client-archive identity evidence. It does not prove that provenance itself recorded the JAR digest, that resource overrides/reloads are absent, that a runtime registry uses these exact values, or that any world state or player can reach a tagged block.
