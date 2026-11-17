# Packaged fluid-tag comparison: 1.16.3 → 1.16.4

**Terminal verdict: CLOSED — no difference in the packaged default WATER/LAVA tag resources only.** In both exact Mojmap client archives, `data/minecraft/tags/fluids/water.json` and `data/minecraft/tags/fluids/lava.json` are byte-for-byte equal. This closes only this bounded resource gate; it says nothing about fluid travel, datapack overrides, other resources, or the full movement pair.

## Evidence and identities

- The 1.16.3 input reference parses as JSON (`lpc-pinned-fluid-tag-resource-input-v1`), SHA-256 `b8d8b2c7d92201579042d0ff3da7d59c3aadf5ea2ac37b0ef500a73002ad0d09`. Its prior independent audit verdict SHA-256 is `363c2ea1de191f28cc9428da1b38a09037f5908c218eecf73af44204447c14c7` and accepts only 1.16.3 packaged tag membership.
- The accepted 1.16.4 counterpart input reference parses as JSON (schema `1`), SHA-256 `b5e946b902d686ec785bd4fcfd3b13e80bd5d51033cc22df914285c890b21bde`. Its prior independent audit verdict SHA-256 is `aa7f7dbae3ba543874642ce1c536831c100ba758e4b2fd7f0205c0062b290a92` and accepts only packaged water/lava membership for 1.16.4→1.16.5. That verdict is reused for its bounded 1.16.4 claim; its 1.16.5 claim is outside this comparison. These are prior independent ACCEPT verdicts; no cryptographic signature is asserted.
- Canonical source roots are `decompiled_minecraft/1.16.3/mojmap/` and `decompiled_minecraft/1.16.4/mojmap/`. Their `.decompile-provenance.json` SHA-256 values are respectively `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498` and `1e04555c915527a398a2c7150679fb24d056f54bd680261e3c1dd55da821147c`, matching the input identities. Each provenance record pins that version's metadata and client URLs and hashes. The cached stable release manifest raw SHA-256 is `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa`.
- Local pinned artifacts are available, so no missing-JAR or missing-artifact blocker applies. The 1.16.3 client JAR is 17,505,781 bytes, SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; the 1.16.4 JAR is 17,546,582 bytes, SHA-256 `d6740464ee2bdf37d285c5d368d2e16497492427fb5783e2db3482bbf0079ed0`. Both match the canonical roots' provenance, local client pin sidecars and accepted input declarations. The local version metadata hashes also match their pins: 1.16.3 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; 1.16.4 `667fff4ca7fd0f56ab78b757224515e8e64375868e8d1ce6040091d7b63a0cb5`. The 1.16.3 packet says no separate `artifacts.sha256` exists; its exact JAR and pin are present, and the provenance directly records the input URL/hash.

## Exact archive entries

| Tag | Bytes per version | SHA-256 per version | JSON membership | Comparison |
|---|---:|---|---|---|
| WATER (`water.json`) | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `replace: false`; `minecraft:water`, `minecraft:flowing_water` | Byte-for-byte equal |
| LAVA (`lava.json`) | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `replace: false`; `minecraft:lava`, `minecraft:flowing_lava` | Byte-for-byte equal |

Only these two named ZIP entries were read from each local JAR. Their archive payloads match the declared sizes and SHA-256 values in both packet inputs, and direct byte-array comparisons across versions returned equal for both entries. The JSON structure and values agree in the 1.16.3 input and the 1.16.4 endpoint of the accepted counterpart input.

## Boundary

This is a resource-membership equality result for the official packaged defaults at 1.16.3 and 1.16.4. It does not establish a datapack-effective tag result, movement applicability, full-pair tick coverage, source-pair freeze, or runtime parity.