# Source identity attestation: 1.17 to 1.17.1 swim-pitch slice

This sidecar binds the original source-discovery artifact to the exact Mojmap sources and records a verified correction to one transcribed input digest. The original report is immutable and has not been edited.

## Artifact identities

- Initial source report: `report.md`, SHA-256 `0d36637149f9f0249d7889467d3055795c338665aa6bcb20aeb48e2c9668d4f3` (recomputed from the current file).
- Independent audit verdict: `c-swim-pitch-vertical-117-1171-audit/verdict.md`, SHA-256 `451b4c1cb3ae4e9cbeaf45400e6de1316683a0c58afd84c9ee3fe6d04e4fbb18` (recomputed from the supplied verdict file).
- This attestation's whole-file SHA-256 is supplied out-of-band with the completion message.

## Exact source binding

Canonical source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap`.

| Java source, relative to `mojmap` | 1.17 SHA-256 | 1.17.1 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/entity/player/Player.java` | `9ad4c439814caf5f51cf6da9a86c5d22906b0a28b5b606bdeb1b770617d90516` | `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481` |
| `net/minecraft/world/entity/LivingEntity.java` | `8e81e5f1317d154fa41b676721ff581badd46296f77bcebcadb3c63448e528ed` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` |
| `net/minecraft/world/entity/Entity.java` | `81231ad8ac1b9a7a9f7ee186352c7e73c0c5211a3034c3a35c04badc6914c8ef` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` |
| `net/minecraft/util/Mth.java` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` | `24515c4549e01e985017227dccf7159166a675b232be9135d5f896022a9cb113` |

The 1.17 mapping-root `.decompile-provenance.json` SHA-256 is `afc9dc30cd8f6406d8ea306e64cc0b3601cc8d7c7bcd314f737573d830a79357`; its embedded source digests match the listed 1.17 files. The 1.17.1 `mojmap.sources.sha256` SHA-256 is `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; its entries match the listed 1.17.1 files.

## Corrected 1.17 metadata identity

The pinned local file `.minecraft-decompile-cache/1.17/version.json` has URL `https://piston-meta.mojang.com/v1/packages/0d9ace8a2ecfd1f4c782786f4b985a499240ff12/1.17.json`. Its `.pin.json` gives SHA-256 `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`; a fresh SHA-256 calculation of the cached file produces the same full digest. The 1.17 `.decompile-provenance.json` embeds that same input URL and digest.

The initial report transcribed this digest as `537c6fa0...`, substituting `0` for the correct seventh hex digit `7`. The correct whole digest is `537c6fa7e6ab13caa76ce2e84e4748882d36fcd5a076fa6903f894880e1df0f6`. This is a metadata citation transcription defect only; the original report bytes and their initial SHA above remain unchanged. The independent audit accepts the bounded no-difference movement claim and calls for a corrected immutable identity, which this sidecar supplies.

## Attestation

I rehashed the original report and independent verdict, compared the cached version metadata against its pin and the decompile provenance input, and recomputed the relied Java source hashes. This sidecar is the separate corrected identity artifact; the source report remains preserved byte-for-byte.

