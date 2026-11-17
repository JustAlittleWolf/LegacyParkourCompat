# Pinned movement resource packets: independent audit r1

Verdict: ACCEPTED for the bounded resource-input claims below. Static byte/archive review only; this is not a movement-behavior or runtime verdict.

## Immutable inputs reviewed

- `1.20.2--1.20.4_soul_speed_resource_tags/input-reference.json` SHA-256 `d3e72d938e977e67a6ce24236d49df819c605d52f8c739bc1288f5fca09fe791`
- `1.21.11--26.1.2_fluid_tags/input-reference.json` SHA-256 `38791eb393da8cd5f9d9bb8766607a9dd2a80201c71a1d98b05102cf4b858731`
- `26.1.2--26.2_attack_knockback_json_scan/input-reference.json` SHA-256 `e48c7360374f9dc287ae8b9847ef18f21bd2f4f5eb4fb5d645976871ea2ec457`

All three hashes matched the assigned bytes. For each identity below, the declared `artifacts.sha256` and provenance file hashes matched the local files, and the client JAR’s actual byte size and SHA-256 matched both the input reference and the corresponding `client.jar` row in that artifact manifest. The JAR paths are local paths supplied by the input references; the references explicitly say they are not provenance-recorded paths.

| Version | Namespace | JAR bytes | Client JAR SHA-256 | `artifacts.sha256` SHA-256 | Provenance path / SHA-256 |
|---|---|---:|---|---|---|
| 1.20.2 | mojmap | 23,186,213 | `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda` | `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54` | `decompiled_minecraft/1.20.2/mojmap.provenance.json` / `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05` |
| 1.20.4 | mojmap | 24,445,539 | `9221ab461a491bf9661cd8e773a5e662aaa43d600fa7970b8c12bbfb0431b838` | `ee3efc771d264c0bc49d5472abb9763a646f8d221841d0a61201ecd991cb3948` | `decompiled_minecraft/1.20.4/mojmap.provenance.json` / `10914d9b7b3fc29de066ca24f7d905e4d4f0a451a6c81729a2d1981addfc1385` |
| 1.21.11 | mojmap | 31,152,600 | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `decompiled_minecraft/1.21.11/mojmap.provenance.json` / `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72` |
| 26.1.2 | unobfuscated | 38,113,927 | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `decompiled_minecraft/26.1.2/unobfuscated.provenance.json` / `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` |
| 26.2 | unobfuscated | 39,193,383 | `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `decompiled_minecraft/26.2/unobfuscated.provenance.json` / `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` |

The four downloaded JARs for 1.20.2, 1.20.4, 1.21.11 and 26.1.2 were also checked against the local official metadata files named `<version>.metadata.json`: metadata SHA-256 matched each reference, JAR byte size matched metadata, and the actual JAR SHA-1 matched `downloads.client.sha1` and the reference’s `metadataClientSha1`:

- 1.20.2 metadata `1befa18c7cc3d33e7b3e2ed91b71a8aeda1f81dd6855f72d6524bdac1c9052c6`; JAR SHA-1 `82d1974e75fc984c5ed4b038e764e50958ac61a0`.
- 1.20.4 metadata `cc81633f25e8e412a94dfb953a7b91702714e1e9ed1b680f520495d56d1ea301`; JAR SHA-1 `fd19469fed4a4b4c15b2d5133985f0e3e7816a8a`.
- 1.21.11 metadata `471a6eeadf1c74f84ed5d4290fb7234b746373478a4d6ac651962b93f0cb36d9`; JAR SHA-1 `ba2df812c2d12e0219c489c4cd9a5e1f0760f5bd`.
- 26.1.2 metadata `eda7bac4a0a1ff847efccf5a51f66e990521b2c09320fecd3679bc6921c7623e`; JAR SHA-1 `4e618f09a0c649dde3fdf829df443ce0b8831e65`.

26.2’s pinned cached client JAR matched its manifest SHA-256 and size. No local official 26.2 metadata file was supplied in this packet, so no metadata SHA-1 check is asserted for 26.2.

## Extracted tag archive entries

Every listed entry was read from the exact pinned client JAR, and compared by decompressed byte count, SHA-256, parsed `values`, and bytes of the isolated output file. All checks matched. Paired endpoint payloads are byte-identical for every listed logical tag. Values are:

| Archive entry (scope uses the path shown per version) | Bytes | SHA-256 | `values` |
|---|---:|---|---|
| 1.20.2 and 1.20.4 `data/minecraft/tags/blocks/soul_speed_blocks.json` | 74 | `8ba7eac6f7c74d25601ef4455b71e3947fdb8e51b8fafd55dddee48397b144b6` | `minecraft:soul_sand`, `minecraft:soul_soil` |
| 1.20.2 and 1.20.4 `data/minecraft/tags/blocks/climbable.json` | 295 | `d0e3e76d7457f3f3f3d7219fe218c7746e4b2e7626d5c388fcf193089069e365` | ladder, vine, scaffolding, weeping_vines, weeping_vines_plant, twisting_vines, twisting_vines_plant, cave_vines, cave_vines_plant (all `minecraft:`) |
| 1.20.2 and 1.20.4 `data/minecraft/tags/fluids/water.json`; 1.21.11 and 26.1.2 `data/minecraft/tags/fluid/water.json` | 74 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | `minecraft:water`, `minecraft:flowing_water` |
| 1.20.2 and 1.20.4 `data/minecraft/tags/fluids/lava.json`; 1.21.11 and 26.1.2 `data/minecraft/tags/fluid/lava.json` | 72 | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` | `minecraft:lava`, `minecraft:flowing_lava` |

The output directory contains exactly the input reference plus the eight allowlisted 1.20.x tag files, and exactly the input reference plus the four allowlisted 1.21.11/26.1.2 fluid tag files. No unrelated archive tree or scan-hit payload was copied. The JSON-scan scope contains only its input reference.

## Bounded 26.1.2 / 26.2 JSON scan

I independently enumerated all `data/**/*.json` archive entries and searched each exact entry’s UTF-8 text case-insensitively for `attack_knockback` and `attribute_modifiers`. Entry counts matched the references: 7,077 for 26.1.2 and 7,310 for 26.2. Neither JAR contains a JSON item-definition path matching `data/<namespace>/items/**/*.json`. There were zero hits for either literal in 26.1.2; in 26.2 there were zero `attack_knockback` hits and exactly 12 `attribute_modifiers` hits. The 12 paths, byte counts, and SHA-256 hashes match the reference’s list exactly; all are `data/minecraft/sulfur_cube_archetype/*.json` (bouncy, explosive, fast_flat, fast_sliding, high_resistance, hot, light, regular, slow_bouncy, slow_flat, slow_sliding, sticky). None is an item-definition file. This is only a bounded client-JAR data-JSON literal scan; it makes no claim about Java registration/defaults, non-JSON item components, datapack overrides, or effective client attributes.

## Limits

These checks establish pinned archive/resource bytes and the stated bounded JSON scan only. They do not establish runtime tag registries, datapack overrides, behavior, or movement parity. No source/product/backlog/wiki/Git history was consulted, and no build, test, or runtime was invoked.