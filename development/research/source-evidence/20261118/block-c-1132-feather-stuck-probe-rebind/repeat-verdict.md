# Complementary repeat artifact verification

**Terminal result: COMPLETED**

**Repeat receipt SHA-256:** `8efeca8c42c3e046447b45f4b01e12b6aee4ccb248fce2cf51700a24d3a27ba0`.

## Exact mapped outputs

The first sealed mapped JAR has raw ZIP SHA-256 `847244506ec99f4dac6b816c4acf11e6d0f4f88e0fb3976388661847c679dbc0`. The repeated isolated-cache JAR has raw ZIP SHA-256 `3fe62de0eb2d8cb7ebcfb429e9a526809c547eb7284073880b7cbfb3ed643738`, matching `repeat-bindings.json`. Raw ZIP bytes differ. Both contain 4,265 distinct entry paths and 4,027 class entries.

I independently recomputed the content digest on both archives using the framed digest function in the isolated receipt-side `verify-publication.py` (prefix `LPC-JAR-CONTENT-v1\0`; names sorted by UTF-16BE/ordinal order; UTF-8 name framed with a big-endian 32-bit length; uncompressed entry bytes framed with a big-endian 64-bit length). Both produce canonical content SHA-256 `38a6f3fc763e73bff1cf121f7b09c7508af0fb2e381b70d3754b6aa1c90aca6d`, matching both receipts. The repeated reviewed-class digest is also the declared `bb18f4cc6a86a850eaddcecfaadcef97c4ce9a20006fc66a1f979f78d2acdab3`.

## Pinned input and source checks

The isolated cache bytes match the hashes in the source provenance input map and pin sidecars:

- Mojang 1.13.2 `version.json`: `26ddcd28289a44d4ae4179307b7193ce0227d2da57350e717ff1d8c6e27e8c95`. Its `id` is `1.13.2`; its client URL/object hash is the pinned `https://piston-data.mojang.com/v1/objects/30bfe37a8db404db11c7edf02cb5165817afb4d9/client.jar`.
- Raw official client JAR: `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`, equal to the client pin and the version metadata's client size of 16,089,640 bytes / SHA-1 object identity.
- Feather metadata: `6110b2fe29f2a7386cdee42effba7d5696bd3a883d4b7bd9fa3b572b6aaa8f3a`; its stable selected entry is `1.13.2+build.2`, game version `1.13.2`, Maven coordinate `net.ornithemc:feather-gen2:1.13.2+build.2`.
- Merged-v2 mapping artifact: `317384d4faccc2939c3b14252993d31745eb42aad59f2f0ea78893cbe4e7283b`, matching its `.pin.json` URL/hash and provenance input. Its `mappings/mappings.tiny` entry hashes to `b3fd787448aed2c6e115d965d9edbb437ce47794ba63c7883e9014fd41262f71`, exactly matching the isolated extracted Tiny file.
- Release catalog: `ac37f92737df1a9d7660047136fedd3edebd90bd6e552f53ddc3f222079bc8fa`, matching the repeat receipt.

The final isolated output's `LocalClientPlayerEntity.java` SHA-256 is `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`, matching the prior frozen probe source and both receipt bindings. Its `.decompile-provenance.json` SHA-256 is `cc4ef194e3d7ca445cb4403fdf1311cc8ddbd1412b922f78726c8b1bcfb9c291`, identical byte-for-byte to the first-run provenance receipt and the repeated provenance binding.

## Limits

This independently establishes the repeated output's exact input pins, mapping/Tiny relation, raw ZIP identity, normalized archive content, and reviewed source identity. It does not establish equivalence to the unavailable original mapped client hash `d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5`; the repeat receipt itself records `repeatMatchesFirstMappedZipBytes: false`. Full 2,711-file equality with the canonical tree remains the repeat receipt's recorded result; this check independently revalidated the reviewed source member only. No new movement claim, coverage claim, implementation claim, or runtime-parity claim is made.
