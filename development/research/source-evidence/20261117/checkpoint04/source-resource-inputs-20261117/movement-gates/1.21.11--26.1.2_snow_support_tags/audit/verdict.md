# Independent audit: snow-support resource packet

Verdict: **ACCEPT** for the exact four-entry resource packet and the bounded identity/pin/member claims below.

## Immutable input

- Input reference: `build/source-resource-inputs-20261117/movement-gates/1.21.11--26.1.2_snow_support_tags/input-reference.json`
- SHA-256: `18b9a5deb911956374f2d4086988e8daf471f7afea8d96a62bb79ec1c67baede`
- Referenced source report: `build/later-source-20261116/1.21.11--26.1.2/core_shape_providers/coverage.md`, SHA-256 `73684d58e3a36bf90457b784ac444c42f607ffb37865a430c052546b0e84309e`.

## Exact source and artifact identity

- 1.21.11 source identity is `versionId=1.21.11`, namespace/mapping `mojmap`. Ready SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99fafec3ae2f5b804b` declares source-manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`, which matches the root `mojmap.sources.sha256`; provenance SHA-256 is `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` and declares version/namespace consistently.
- 26.1.2 source identity is `versionId=26.1.2`, namespace/mapping `unobfuscated`. Ready SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` declares source-manifest SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, which matches the root `unobfuscated.sources.sha256`; provenance SHA-256 is `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` and declares version/namespace consistently.
- `BlockTags.java` local hashes match their respective source-manifest rows: 1.21.11 `9db30d02bbb896538a35267b2f0e9c957a00ffdbf20bcc0ea025b5a7bbec38e3`; 26.1.2 `edb77ca338c6e831d47b41d799b2f2cad432f34ee465be33a4d1e69edf0f8c1f`. The inspected constants use `Identifier.withDefaultNamespace`, so the resource namespace is `minecraft`; 1.21.11 keys are `snow_layer_cannot_survive_on` / `snow_layer_can_survive_on`, while 26.1.2 keys are `cannot_support_snow_layer` / `support_override_snow_layer`.
- Artifact-manifest SHA-256 values match the input reference: 1.21.11 `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c`; 26.1.2 `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`. In both manifests, the exact `client.jar` pin equals the downloaded JAR SHA-256: 1.21.11 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` (31,152,600 bytes); 26.1.2 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` (38,113,927 bytes).
- Downloaded official metadata SHA-256 values match the input reference; metadata IDs, client URLs, and client SHA-1 values were read and verified against the exact JARs. 1.21.11 client SHA-1 is `ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd`; 26.1.2 is `4e618f09a0c649dde3fdf829df443ce0b8831e65`.
- The provenance JSON files do not contain a client-JAR digest or artifact-path field. The packet correctly limits its claim to a local JAR whose SHA-256 matches the canonical `artifacts.sha256` pin and whose SHA-1 matches official version metadata; it does not claim the artifact path/hash was provenance-recorded.

## Allowlisted archive entries

Each exact allowlisted entry exists at the stated archive path. For every entry, archive size, SHA-256, parsed values, and `replace`-field absence match the reference and extracted output file; the archive bytes are byte-identical to the extracted file. The two corresponding before/after tag payloads are byte-identical as well.

| Release | Archive entry | Bytes | SHA-256 | Values |
| --- | --- | ---: | --- | --- |
| 1.21.11 | `data/minecraft/tags/block/snow_layer_cannot_survive_on.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` |
| 1.21.11 | `data/minecraft/tags/block/snow_layer_can_survive_on.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` |
| 26.1.2 | `data/minecraft/tags/block/cannot_support_snow_layer.json` | 94 | `282662c1139488dcc43e3bfe48a36a6c4a759e53cb6a4024454236b8f07384cb` | `minecraft:ice`, `minecraft:packed_ice`, `minecraft:barrier` |
| 26.1.2 | `data/minecraft/tags/block/support_override_snow_layer.json` | 97 | `c7491ae1bafae440421e90a1387ac87f46f7e003f37315f4acf5ea0d0ee8cd5a` | `minecraft:honey_block`, `minecraft:soul_sand`, `minecraft:mud` |

## Limits

Acceptance is limited to these four exact entries and stated archive/JAR/source identities. It does not establish datapack override behavior, reload state, effective membership in a specific world, player reachability of every tag member, runtime behavior, or pair-wide source coverage.
