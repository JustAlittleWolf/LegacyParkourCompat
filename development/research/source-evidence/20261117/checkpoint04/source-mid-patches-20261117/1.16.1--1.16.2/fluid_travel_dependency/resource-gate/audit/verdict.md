# Independent resource-gate audit: 1.16.1 → 1.16.2 water/lava tags

Verdict: **ACCEPT for the bounded packaged-resource membership gate.** This does not accept the full fluid source report, establish player-state applicability, or freeze the release pair.

## Reviewed immutable inputs

- Fluid dependency report: `build/source-mid-patches-20261117/1.16.1--1.16.2/fluid_travel_dependency/report.md`, SHA-256 `BA441B366E3E79BE1DC7AF7B17F7FA987CC03F53E55DD0AA4A5582F3B94EF292`.
- Resource input contract: `build/source-mid-patches-20261117/1.16.1--1.16.2/fluid_travel_dependency/resource-gate/input-reference.json`, SHA-256 `527C182B62211E234A2A320C4372155BBF5C2599154671B5E1F106FC2A3E1F87`.

## Independent artifact and payload verification

Independently downloaded the two official client JARs from the exact URLs in the input contract and verified their SHA-256 and SHA-1 object hashes before opening either archive:

- 1.16.1: SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; SHA-1 `c9abbe8ee4fa490751ca70635340b7cf00db83ff`.
- 1.16.2: SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`; SHA-1 `653e97a2d1d76f87653f02242d243cdee48a5144`.

Read only the four allowlisted archive entries. Each archived entry exactly matches its extracted file and input-contract payload:

| Version | Entry | Bytes | SHA-256 | Payload |
|---|---|---:|---|---|
| 1.16.1 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `replace: false`; `minecraft:water`, `minecraft:flowing_water` |
| 1.16.1 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `replace: false`; `minecraft:lava`, `minecraft:flowing_lava` |
| 1.16.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `replace: false`; `minecraft:water`, `minecraft:flowing_water` |
| 1.16.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `replace: false`; `minecraft:lava`, `minecraft:flowing_lava` |

The independently computed payload hashes and bytes matched all four local extracted files and their input-reference declarations. Temporary JARs were removed after verification.

## Scope and limits

This accepts that the exact pinned client archives contain those exact tag payloads, and that water/lava fluid-tag membership is identical at these endpoints for the listed entries. It does not assert a source-provenance-to-client-JAR binding beyond the separately recorded artifact pins. It does not prove a separating fluid arrangement, runtime effect, complete tick/dependency coverage, or pair freeze. The source report's other open gates remain open.
