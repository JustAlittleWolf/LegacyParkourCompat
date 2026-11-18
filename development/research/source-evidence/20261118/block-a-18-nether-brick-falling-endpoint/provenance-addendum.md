# Provenance and release-span correction

This addendum supersedes the earlier report's line-wide readiness blocker and boundary-commit discrepancy. Hidden mapping-local `.decompile-provenance.json` files are present under each `decompiled_minecraft/<release>/ornithe-feather/` directory for 1.8 through 1.8.8; the exact boundary-witness repository HEAD is the supplied `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d`.

## Exact release pins and source binding

For each release 1.8 through 1.8.8, its nested provenance `inputs` contains that release's Feather gen2 build.2 mergedv2 artifact URL and SHA-256. Its nested `sources` map matches the local complete-file SHA-256 for `Block.java`, `FenceBlock.java`, `LiquidBlock.java`, and `material/Material.java`. 1.8.9 has the corresponding root-level provenance/ready/source-manifest files; the ready marker binds the source manifest and those four source files match their manifest entries. Exact mapping artifact pins are:

| Release | Feather gen2 mergedv2 artifact | Artifact SHA-256 |
| --- | --- | --- |
| 1.8 | `feather-gen2-1.8+build.2-mergedv2.jar` | `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe` |
| 1.8.1 | `feather-gen2-1.8.1+build.2-mergedv2.jar` | `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` |
| 1.8.2 | `feather-gen2-1.8.2+build.2-mergedv2.jar` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` |
| 1.8.3 | `feather-gen2-1.8.3+build.2-mergedv2.jar` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` |
| 1.8.4 | `feather-gen2-1.8.4+build.2-mergedv2.jar` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` |
| 1.8.5 | `feather-gen2-1.8.5+build.2-mergedv2.jar` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` |
| 1.8.6 | `feather-gen2-1.8.6+build.2-mergedv2.jar` | `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81` |
| 1.8.7 | `feather-gen2-1.8.7+build.2-mergedv2.jar` | `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b` |
| 1.8.8 | `feather-gen2-1.8.8+build.2-mergedv2.jar` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` |
| 1.8.9 | `feather-gen2-1.8.9+build.2-mergedv2.jar` and `feather-gen2-1.8.9+build.2.tiny` | `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` (jar), `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63` (Tiny) |

The exact source hashes are distinct for 1.8 and 1.8.1. Their `(Block.java, FenceBlock.java, LiquidBlock.java, Material.java)` SHA-256 tuples are:

- 1.8: `(4a4e6dfde24a135ecf87c359fd98f0380664e4bacaf679815c9016cb1ba0d814, 2e75c087705278524a3a54acf3b56c4a476294357a11741640b457cea4e781e1, e4ef9385d025aa4672d4b6a55afc72803267d0721469bf036217ab30a7760fe3, a27bcc70e2712f150d8428773f8c2aafe98af9100a4fd05653057044d7d248a9)`.
- 1.8.1: `(4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c, 0a4b6623a3b5d59b521f082676b2a7400d40d696ee239da7f74c146487637271, 36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a, c05f393fe2b5e77b96dac85250751bed03cad2be6377fc5c10209904c1f185bf)`.
- 1.8.2–1.8.9: `(ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528, 8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9, 1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5, 017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2)`.

## Earlier release gate trace and terminal result

In 1.8 and 1.8.1, `Block.java` registers ID 113 / key `nether_brick_fence` as `FenceBlock(Material.STONE)` (1.8 lines 978–979; 1.8.1 lines 1027–1028). `Blocks.NETHER_BRICK_FENCE` resolves that key. Oak is the same fence class with `Material.WOOD` (1.8 line 936; 1.8.1 line 975); the 1.8.1 map-color constructor does not replace its material. `FenceBlock` inherits `Block.isFaceSolid`, which reads the neighbor's material `isSolid`; `Material.isSolid` returns true. `LiquidBlock.getFlow` applies the `LEVEL >= 8` wall cue and probes each horizontal side then its above cell, short-circuiting in that order (1.8 lines 150–154; 1.8.1 lines 151–155). With side air and the fence at Y=65, the material cue matches oak. Both releases' default disconnected fence collision shape uses local minimum Y 0 (1.8 `FenceBlock.java` lines 49–64; 1.8.1 lines 54–69), so its world minimum Y is 65, above the fixture player-box maximum near 64.401.

**Revised terminal verdict: ACCEPT** for NETHER_BRICK_FENCE substitution in the already accepted exact 1.8.x upper-Y fixture, including the independently pinned 1.8, 1.8.1, 1.8.2–1.8.9 source sets. The boundary witness raw blob SHA-256 is the supplied `7f5f2c31a35121c1acdef172f069e8cd28c0fba4c6618562286c184af0b25e7c`; its actual commit is the supplied `...76d`. The 1.8 alias audit commit/blob identities also match. Scope remains only this known fence applicability; no new provider inventory or runtime claim is added.