# Independent verdict: ordinary wooden fence sibling bridge

**Verdict: ACCEPT** for each named dry sibling at every inspected stable release from 1.9 through 1.11.1: spruce, birch, jungle, acacia, and dark oak. Each registration selects the ordinary `FenceBlock` with `Material.WOOD`; the only wood-specific constructor value is planks map color. `FenceBlock` does not override the virtual `Block.isFaceSolid` predicate. Its default connection bits are all false, and its shape calculation uses state/world connections rather than material or map color. Thus the same-height and above-side horizontal face probes in the already accepted falling-water `LEVEL >= 8` cue have the same result as oak. This is sibling applicability only, not a new flow-scan or trajectory claim.

## Immutable source inputs

All sources are the canonical read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather` trees. Mapping is Ornithe Feather generation 2 build 2 in every row. Earlier releases have an inner `.decompile-provenance.json`; later rows have the ready marker and root source manifest. The digest below is the full source manifest digest where a ready marker exists, otherwise the raw SHA-256 of the inner provenance manifest. The older inner manifests identify the exact release-specific Feather build.2 artifact URL. For every release, the per-file pins in the next section matched the named provenance/source manifest.

| Release(s) | Source identity digest |
|---|---|
| 1.9 | inner provenance `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` |
| 1.9.1 | inner provenance `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` |
| 1.9.2 | inner provenance `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` |
| 1.9.3 | inner provenance `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` |
| 1.9.4 | source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` |
| 1.10 | inner provenance `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | inner provenance `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |
| 1.10.2 | source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | source manifest `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |

## Pinned source files and hash deduplication

Each row gives exact raw SHA-256 pins for the named source file; equal hashes were deduplicated before tracing. These five files were individually checked against every release's source manifest/provenance.

- `net/minecraft/block/Block.java`
  - `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2`: 1.9–1.9.2
  - `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6`: 1.9.3–1.9.4
  - `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01`: 1.10–1.10.2
  - `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488`: 1.11
  - `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a`: 1.11.1
- `net/minecraft/block/Blocks.java`
  - `ae6e28482364703673c89e33d885ef30d7c7421ab5a0e0a0c2d4e656600bb352`: 1.9–1.9.2
  - `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7`: 1.9.3–1.9.4
  - `40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9`: 1.10–1.10.2
  - `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d`: 1.11–1.11.1
- `net/minecraft/block/FenceBlock.java`
  - `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761`: 1.9–1.9.2
  - `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909`: 1.9.3–1.10.2
  - `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5`: 1.11
  - `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7`: 1.11.1
- `net/minecraft/block/material/Material.java`
  - `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19`: 1.9–1.9.4
  - `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d`: 1.10–1.11.1
- `net/minecraft/block/LiquidBlock.java`
  - `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8`: 1.9–1.9.2
  - `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`: 1.9.3–1.10.2
  - `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244`: 1.11–1.11.1

## Exact source trace

For every release, `Block.java` registers all five siblings with `new FenceBlock(Material.WOOD, PlanksBlock.Variant.<wood>.getColor())`; the oak registration has the same constructor with `Variant.OAK.getColor()`. The registration rows are 1.9–1.9.2: spruce 1130–1131, birch 1139–1140, jungle 1148–1149, dark oak 1157–1158, acacia 1166–1167; 1.9.3–1.9.4: 1175–1176, 1184–1185, 1193–1194, 1202–1203, 1211–1212; 1.10–1.10.2: 1180–1181, 1189–1190, 1198–1199, 1207–1208, 1216–1217; 1.11–1.11.1: 1185–1186, 1194–1195, 1203–1204, 1212–1213, 1221–1222. Their `Blocks.java` aliases resolve the matching registry names: 1.9–1.9.2 lines 333–337; 1.9.3–1.9.4 lines 335–339; 1.10–1.10.2 lines 340–344; 1.11–1.11.1 lines 357–361.

`FenceBlock` constructor establishes NORTH/EAST/SOUTH/WEST false for the default state. Its `getShape` calls `resolveVirtualProperties` and indexes the shape table from state bits; neither material nor map color selects a shape. It has no `isFaceSolid` override. The base `Block.isFaceSolid` returns the block state's `Material.isSolid()`. `Material.WOOD` is constructed as the wood material in each distinct `Material.java` version and `isSolid()` returns true. Therefore horizontal fence faces pass the inherited predicate regardless of species, default/connected fence shape, or map color.

The exact `LiquidBlock` implementations retain the falling-level gate and the ordered same-height/above horizontal probes: 1.9–1.9.2 `getFlow` lines 157–160; 1.9.3–1.10.2 lines 160–163; 1.11–1.11.1 lines 160–163. The face predicate uses the liquid-material, UP, and ICE exclusions before delegating to the base predicate, so these dry WOOD siblings take the same base-material path as oak. This retraces only the alias applicability leg; the accepted oak cue and its side-caller scope are reused from the authorized prior evidence.

## Per-name outcomes

- **SPRUCE_FENCE:** accepted in all ten releases; `Material.WOOD`, spruce planks map color.
- **BIRCH_FENCE:** accepted in all ten releases; `Material.WOOD`, birch planks map color.
- **JUNGLE_FENCE:** accepted in all ten releases; `Material.WOOD`, jungle planks map color.
- **DARK_OAK_FENCE:** accepted in all ten releases; `Material.WOOD`, dark-oak planks map color.
- **ACACIA_FENCE:** accepted in all ten releases; `Material.WOOD`, acacia planks map color.

The terminal per-release outcomes are therefore ACCEPT for 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.1, 1.10.2, 1.11, and 1.11.1. Combined with the independently accepted exact 1.11.2 → 1.12.0 sibling verdict, this closes the requested bridge through the 1.12 boundary for these five names. It does not revise that verdict or claim any intermediate patch cutover beyond the individually inspected releases.

## Reused evidence and limits

Reused only the permitted accepted oak caller evidence in `development/research/source-evidence/20261117/block-a-oak-fence-pre112-verified-b/lane-falling-water-fence-prior/comparison.corrected-r1.md` (worktree SHA `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`) and the assigned sibling verdict Git blob `ffa3b453cffa6018dff873f53c67e5365a93d722:sibling-audit-verdict.md` (raw blob SHA-256 `6073aab8a2e71b0b84388d691b340ddbec5f5239ee7972c062637ed9162f4ff7`). No oak re-audit, new flow scan, source generation, shared source write, build, test, or launch was performed. No other fence families, fence gates, 1.8 siblings, modern blocks, runtime reachability, or trajectory equivalence are covered.
