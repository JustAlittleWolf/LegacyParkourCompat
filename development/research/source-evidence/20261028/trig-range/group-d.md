# Group D: relative input trig aliases

Scope checked against exact Mojmap source trees: 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10 (anchor). Canonical root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. This is a trig/input-rotation slice only, not complete tick coverage.

## Behavior result

No behavior changes were found in the assigned releases. All nine use the same `float[65536]` SIN table initialized by `(float)Math.sin(ix * Math.PI * 2.0 / 65536.0)`, with `int` index truncation and `& 65535`; sin multiplies the float angle by `10430.378F`, cos adds `16384.0F` before masking. All nine `Entity#moveRelative` paths pass `getYRot()` to `getInputVector`; there it computes `Mth.sin/cos(yaw * (float)(Math.PI / 180.0))` as floats and rotates X/Z in the same order. The zero-length threshold, conditional normalization, scale and delta-velocity addition are also textually the same in the covered bodies.

`1.21.9` was inspected directly: it is in the same behavior class as 1.21.8 and 1.21.10. Therefore no unknown introduction boundary remains within 1.21.8–1.21.10 for this operation. No statement is made about versions outside the assignment.

## Exact source locations

All paths below are relative to `<version>/mojmap/`.

| Version(s) | Mth lookup init | Mth sin/cos | Entity moveRelative/getInputVector |
|---|---:|---:|---:|
| 1.21.2, 1.21.3 | `net/minecraft/util/Mth.java:32-36` | `:48-53` | `net/minecraft/world/entity/Entity.java:1403-1418` |
| 1.21.4 | `Mth.java:32-36` | `:48-53` | `Entity.java:1424-1439` |
| 1.21.5 | `Mth.java:32-36` | `:48-53` | `Entity.java:1453-1468` |
| 1.21.6, 1.21.7, 1.21.8 | `Mth.java:32-36` | `:48-53` | `Entity.java:1530-1545` |
| 1.21.9 | `Mth.java:32-36` | `:48-53` | `Entity.java:1585-1600` |
| 1.21.10 | `Mth.java:32-36` | `:48-53` | `Entity.java:1590-1605` |

## Identity verification

Root ready/provenance markers were checked when present. Where absent, the hidden `mojmap/.decompile-provenance.json` was checked; its embedded identities for `Mth.java` and `Entity.java` match the source files byte-for-byte. Root ready manifests (where present) match the actual `mojmap.sources.sha256` digest, and the manifest entries for both files match computed file hashes. Mapping family is Mojmap for all checked releases.

| Version | Manifest SHA-256 or hidden provenance SHA-256 | `Mth.java` SHA-256 | `Entity.java` SHA-256 |
|---|---|---|---|
| 1.21.2 | hidden provenance `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.3 | manifest `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9` |
| 1.21.4 | manifest `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad` |
| 1.21.5 | manifest `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9` | `98dfb57e239348cf71bf00695b84299232bd5e0cfe206ee8c23a69b72f1099da` | `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4` |
| 1.21.6 | hidden provenance `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| 1.21.7 | hidden provenance `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| 1.21.8 | manifest `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` | `962c7439a4d0eacc23965d67af13a03e2d95f703247dce19074c2ae134a1cf9b` | `c403e6176d27b5bfd6aaa0dea3735ffa4fcf80dbae58766661dd84453b7e9704` |
| 1.21.9 | hidden provenance `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` |
| 1.21.10 | manifest `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `d2a84fa876d78eb9693b2625ff5dc2eb8568ea33a974b4368effb03b4390e7f1` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |

For embedded-only identities, provenance inputs were confirmed as version-specific client JAR, `client.txt` mapping and version metadata. Their hashes are retained in the supplied comparison input; the hidden record’s embedded source hashes match the listed source hashes. No root manifest is present for those four releases.
