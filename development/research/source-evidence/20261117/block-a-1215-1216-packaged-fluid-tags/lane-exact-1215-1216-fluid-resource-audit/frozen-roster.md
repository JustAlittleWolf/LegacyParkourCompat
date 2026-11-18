# Frozen independent resource roster

Pair: Minecraft 1.21.5 → 1.21.6. Host: Windows PowerShell. Inputs were inspected directly from exact client JAR ZIP entries; no comparison/manifest draft had been opened at freeze time.

## Bounded roster

- Exact candidate universe: every ZIP entry under `data/minecraft/tags/fluid/` in each client JAR.
- 1.21.5 paths: `data/minecraft/tags/fluid/lava.json`, `data/minecraft/tags/fluid/water.json`.
- 1.21.6 paths: `data/minecraft/tags/fluid/lava.json`, `data/minecraft/tags/fluid/water.json`.
- Negative path check: neither JAR contains entries under `data/minecraft/tags/fluids/` (plural), and the `fluid/` directory contains no additional tag files.
- Frozen result: both resource paths exist on both sides; both contents and SHA-256 hashes match byte-for-byte. No packaged fluid-tag delta is present in this bounded roster.

## Raw identities verified before freeze

- 1.21.5 `1.21.5-version.json`: id `1.21.5`; SHA-1 `b341cd0e91419ab72b5d9dadad8d86a045d6b9a7`; SHA-256 `1751e36c5dda2bbf847d543d4f9a59c51035fe474b8528aca7f76c688f32fe88`. The SHA-1 matches the exact entry in the locally captured Mojang release catalog. There is no local 1.21.5 `version.json.pin.json`.
- 1.21.5 `1.21.5-client.jar`: 28,984,409 bytes; metadata declared size matches; actual SHA-1 `b88808bbb3da8d9f453694b5d8f74a3396f1a533` matches metadata; SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`.
- 1.21.6 `version.json`: id `1.21.6`; SHA-1 `8dd125560431a618094435343acccd45191bb006` matches locally captured catalog entry; SHA-256 `ea1c91ccb9760c9f7e697c29ed679427dd20cdac9ce35937125e5690973df6dd` matches `.minecraft-decompile-cache/1.21.6/version.json.pin.json`.
- 1.21.6 `client.jar`: 29,516,555 bytes; metadata declared size matches; actual SHA-1 `740a125b83dd3447feaa3c5e891ead7fbb21ae28` matches metadata; SHA-256 `9592df8587d6d3ec0334c873ad432dc76006d6129afdce9a7eac52638339a24a` matches `.minecraft-decompile-cache/1.21.6/client.jar.pin.json`.

## Frozen per-entry roster

- `data/minecraft/tags/fluid/lava.json`: 1.21.5 SHA-256 `71f50fb9092d78260bc7434731fc5fd426a44e5284a6ad084ec71cb725630c6b`; 1.21.6 same SHA-256. Both contain ordered values `minecraft:lava`, `minecraft:flowing_lava`.
- `data/minecraft/tags/fluid/water.json`: 1.21.5 SHA-256 `dcfa69a748d03dbf788d8f7b0e5eb6c8de6355a527fcaf74b9210fe4be2a3004`; 1.21.6 same SHA-256. Both contain ordered values `minecraft:water`, `minecraft:flowing_water`.
