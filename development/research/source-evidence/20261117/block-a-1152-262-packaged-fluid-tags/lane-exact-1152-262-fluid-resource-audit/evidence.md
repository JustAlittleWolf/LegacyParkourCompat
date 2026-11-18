# Independent fluid resource provenance audit

Verdict: **VERIFIED** for the four packaged vanilla builtin water/lava tag resources in 1.15.2 and 26.2. The author comparison's membership claim and entry roster are independently reproduced from exact client archive bytes. Scope is builtin defaults only, with no custom datapacks, pack priority/reload merge, travel behavior, or full movement parity claim.

## Frozen pair and entry roster

Before opening the author comparison, froze the pair as `1.15.2/mojmap` and `26.2/unobfuscated`, pinned client identities from the captured official metadata plus local ready/artifact manifests, and these four resource entries:

- 1.15.2: `data/minecraft/tags/fluids/water.json` and `data/minecraft/tags/fluids/lava.json`.
- 26.2: `data/minecraft/tags/fluid/water.json` and `data/minecraft/tags/fluid/lava.json`.

The authorized identity manifest SHA-256 is `237af4da29ec6f89a9d296d4ec4297d338db424a21a51538101771c9c26c2d76`.

## Local pins and archive provenance

- 1.15.2 ready marker SHA-256: `64a0e40b784d525ce50937c63e465fc4f564efb1f7af17f2bac3c77a36907be0`; artifact manifest SHA-256: `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`. Captured version metadata SHA-256: `e974511243e845b2427ab635eba5612481cb420a70c0630aa041eab3eeb590c5`; metadata client SHA-1 `e3f78cd16f9eb9a52307ed96ebec64241cc5b32d`, size `15531492`. Published client ZIP SHA-1 and SHA-256 rehash to `e3f78cd16f9eb9a52307ed96ebec64241cc5b32d` and `4a73008a73f3824b7c711750a5a37556df8614f193c0a531e292dad159a73a7c`; the latter matches ready and artifact pins.
- 26.2 ready marker SHA-256: `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f`; artifact manifest SHA-256: `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`. Captured version metadata SHA-256: `8d009c6ed0b7b8c65bfff515a1ff8b12cc8df72627bb0e355ecbce9f7bc34f9c`; metadata client SHA-1 `2dc72797acbc1b63fc16a11c4ac393605f453754`, size `39193383`. The local exact client archive at `.gradle/caches/fabric-loom/26.2/minecraft-client.jar` rehashes to that SHA-1 and SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`, matching the ready/artifact source pin.

The captured current catalog snapshot SHA-256 is `4be3f97bcbd4b803d5645eb5ac56aacb1b45a64d0493a5d45091c6fad1be9f41`. Its 26.2 index record advertises metadata URL package SHA-1 `f76b7f3dc4df9778a004baa5097dc556474b3c8f`, while the pinned metadata URL package SHA-1 is `91ef51b7d36ca092c5c02aba42f0f40c7cc1db84`. This records a catalog pointer change only; no network fetch or claim about the unprovided live metadata payload was used. The captured pinned metadata, archive SHA-1, client SHA-256 source pins, and archive contents are mutually consistent.

## Raw entries and default-only membership

SHA-256 values below were computed over the uncompressed raw entry bytes from the rehashed client ZIPs and match the identity manifest. Each parsed resource has no tag-reference includes, so its builtin default membership is the listed values.

| Version | Entry | Bytes | Raw SHA-256 | `replace` | Builtin default values |
|---|---|---:|---|---|---|
| 1.15.2 | `data/minecraft/tags/fluids/water.json` | 94 | `698e1662335b6241879b380a58d478ee019a1e789362b004050b5ccac421ad18` | `false` | `minecraft:water`, `minecraft:flowing_water` |
| 1.15.2 | `data/minecraft/tags/fluids/lava.json` | 92 | `f3a67622f1f6a4c69e7792f1e3731b04236962b5be1821671aca0d1b59c316dd` | `false` | `minecraft:lava`, `minecraft:flowing_lava` |
| 26.2 | `data/minecraft/tags/fluid/water.json` | 74 | `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004` | absent | `minecraft:water`, `minecraft:flowing_water` |
| 26.2 | `data/minecraft/tags/fluid/lava.json` | 72 | `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b` | absent | `minecraft:lava`, `minecraft:flowing_lava` |

The entry directory changes from `fluids` to `fluid`; membership IDs are unchanged. The author comparison itself hashes to the assigned SHA-256 `05815f03ea980dbd4a66b539ed44399a4ca2321d8dd293297088c62f8937bdd0`, matching its sidecar. The comparison's bounded claim is supported.
