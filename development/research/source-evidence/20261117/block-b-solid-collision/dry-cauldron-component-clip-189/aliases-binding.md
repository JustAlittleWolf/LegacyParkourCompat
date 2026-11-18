# Dry cauldron component clipping across the 1.8 releases

The [command-created canopy fixture](binding.md) yields the same bounded dry-cauldron result from exact 1.8 through 1.8.9. The 0.6F-width box at center Z 0.500000011920929 reaches the south-wall plane at +0.07499997615814202 Z. The sub-epsilon X overlap is positive, so each release's ordered strict component clip sees it. The occupied-union 26.2 control still preserves +0.2.

The canopy clips both step ascents to about 0.2, below the 0.5 needed to clear the wall. Exact 1.8 requests the full step-height descent on the rejected candidate. Exact 1.8.1 through 1.8.9 descend by the selected clipped ascent. The equal horizontal candidate gain restores the ordinary clipped move for all ten releases. The reviewed authorized placement and immediate move establish the source setup. Natural reachability and runtime parity remain unverified.

The independent verdict describes the exact 1.8 candidate as stopping at the bottom slab during descent. Exact 1.8 Entity.move lines 468 and 526-535 reuse the upward-expanded candidate list. SlabBlock.addCollisions lines 63-65 delegates to Block.addCollisions lines 292-297, which requires strict Box.intersects (lines 153-157). The bottom slab touches the lower query face and is omitted, so this rejected candidate can descend below the support height. The same >= comparison still restores the ordinary saved box and zero Y, preserving the accepted final Z result.

The existing V1_8_9 dry keyed collector is the closest applicable collector for the four earlier-line profile groups once its empty V1_8_5 collector is retired. This extends only the certified fixture. The native scan used in 1.8 through 1.8.8 still visits the cauldron and canopy as interior cells, and the wrapper performs strict component/query intersection. The 1.8.9 profile keeps its historical scan bounds. The ordinary occupied geometry and water cauldron route retain their previous providers.

All seven consumed fixture members matched each exact Feather source manifest or hidden provenance map. The historical aliases retain their existing source identities.

| Exact release | Source identity | SHA-256 |
|---|---|---|
| 1.8 | Mapping-root hidden provenance | 52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05 |
| 1.8.1 | Mapping-root hidden provenance | f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca |
| 1.8.2 | Mapping-root hidden provenance | 8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472 |
| 1.8.3 | Mapping-root hidden provenance | 7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed |
| 1.8.4 | Mapping-root hidden provenance | b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00 |
| 1.8.5 | Mapping-root hidden provenance | 986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce |
| 1.8.6 | Mapping-root hidden provenance | c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9 |
| 1.8.7 | Mapping-root hidden provenance | 6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef |
| 1.8.8 | Mapping-root hidden provenance | 588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1 |
| 1.8.9 | Release-root source manifest | 9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004 |

- [Alias source report](aliases-author.md), SHA-256 73fcae8acab9265ed2be0bbcf17a4a24aaff7b935aa25cd3f8fd64090819b099.
- [Independent corrected verdict](aliases-verdict-r2.md), SHA-256 24dc27b87443dd7eb09b1477025ec1562b36d7d7e725304d04857290d3327480.
