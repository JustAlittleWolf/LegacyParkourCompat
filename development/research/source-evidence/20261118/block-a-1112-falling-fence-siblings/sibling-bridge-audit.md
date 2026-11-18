# Independent verdict: ordinary wooden fence siblings, 1.9–1.11.1

**Verdict: ACCEPT** the bounded applicability claim for `SPRUCE_FENCE`, `BIRCH_FENCE`, `JUNGLE_FENCE`, `DARK_OAK_FENCE`, and `ACACIA_FENCE` in each of the ten exact releases listed below. Each alias registers an ordinary `FenceBlock(Material.WOOD, <plank variant>.getColor())`. The dry default and connected states use the common `FenceBlock` shape provider, whose shape bits depend on connections; the fence does not override `Block.isFaceSolid`. The base face predicate reads `Material.isSolid()`, and `Material.WOOD` takes the solid path. In the accepted falling-water caller context, the horizontal `side` then `side.up()` probes therefore give these siblings the same result as oak. This accepts alias applicability only; it adds no flow-scan or trajectory claim.

## Immutable source identities

Canonical sources are read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather`. Every mapping is Feather generation 2 build 2 for that exact release. Digests were recomputed from the raw inner provenance manifest on legacy trees, or the root `.sources.sha256` manifest where the ready marker is present. File hashes below were recomputed from the source bytes and match the per-release source manifests.

| Release | Source identity SHA-256 | Exact mapping artifact |
|---|---|---|
| 1.9 | `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | `feather-gen2-1.9+build.2` |
| 1.9.1 | `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | `feather-gen2-1.9.1+build.2` |
| 1.9.2 | `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | `feather-gen2-1.9.2+build.2` |
| 1.9.3 | `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | `feather-gen2-1.9.3+build.2` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `feather-gen2-1.9.4+build.2` |
| 1.10 | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | `feather-gen2-1.10+build.2` |
| 1.10.1 | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | `feather-gen2-1.10.1+build.2` |
| 1.10.2 | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | `feather-gen2-1.10.2+build.2` |
| 1.11 | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `feather-gen2-1.11+build.2` |
| 1.11.1 | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `feather-gen2-1.11.1+build.2` |

## Retrace and hash deduplication

Each pin below was verified against all ten release manifests. Equal hashes were deduplicated before source tracing; the listed ranges identify the exact releases sharing each byte-identical file.

- `net/minecraft/block/Block.java`: `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` (1.9–1.9.2); `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` (1.9.3–1.9.4); `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` (1.10–1.10.2); `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` (1.11); `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` (1.11.1).
- `net/minecraft/block/Blocks.java`: `ae6e28482364703673c89e33d885ef30d7c7421ab5a0e0a0c2d4e656600bb352` (1.9–1.9.2); `dc561d17e9e12767774d9ef01057f0c981e5a9bb666e2d11cba3517062cc8dd7` (1.9.3–1.9.4); `40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9` (1.10–1.10.2); `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d` (1.11–1.11.1).
- `net/minecraft/block/FenceBlock.java`: `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` (1.9–1.9.2); `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` (1.9.3–1.10.2); `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5` (1.11); `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` (1.11.1).
- `net/minecraft/block/material/Material.java`: `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` (1.9–1.9.4); `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` (1.10–1.11.1).
- `net/minecraft/block/LiquidBlock.java`: `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` (1.9–1.9.2); `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` (1.9.3–1.10.2); `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` (1.11–1.11.1).

`Block.java` registers the five species with the same `FenceBlock(Material.WOOD, …getColor())` constructor; only the planks variant differs. Registration line groups are 1130–1131, 1139–1140, 1148–1149, 1157–1158, and 1166–1167 for 1.9–1.9.2; 1175–1176, 1184–1185, 1193–1194, 1202–1203, and 1211–1212 for 1.9.3–1.9.4; 1180–1181, 1189–1190, 1198–1199, 1207–1208, and 1216–1217 for 1.10–1.10.2; 1185–1186, 1194–1195, 1203–1204, 1212–1213, and 1221–1222 for 1.11–1.11.1. In `Blocks.java`, their aliases resolve the corresponding lower-case registry names at lines 333–337, 335–339, 340–344, and 357–361 for those same groups.

The common `FenceBlock` constructor sets all four connection bits false in its default state. `getShape` resolves virtual connection properties and indexes `SHAPES` only from those bits. `FenceBlock` declares no `isFaceSolid` override. `Block.isFaceSolid` returns the selected state's material `isSolid()` result. In both distinct `Material.java` bodies, `WOOD` is an ordinary `Material` and its inherited `isSolid()` returns true. Neither the wood's plank map color, nor fence connection bits or shape, enter that face predicate.

The exact `LiquidBlock` bodies preserve the accepted falling-level gate and ordered horizontal probes: `getFlow` checks `side` then `side.up()` after `LEVEL >= 8`; `isFaceSolid` rejects same-liquid material, treats UP specially, rejects ICE, then delegates to `Block.isFaceSolid`. Thus an air same-height side followed by one of these dry WOOD fences above it reaches the same true base-material predicate as the oak caller. The reused caller scope and exclusions are from the accepted prior source review, not re-audited here: `development/research/source-evidence/20261117/block-a-oak-fence-pre112-verified-b/lane-falling-water-fence-prior/comparison.corrected-r1.md`, raw worktree SHA-256 `0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`.

## Per-release result and scope

ACCEPT independently for each of 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, 1.10, 1.10.1, 1.10.2, 1.11, and 1.11.1, and for each of the five named wood aliases. This verdict binds to the exact source identities above and closes only ordinary dry/default non-oak sibling applicability to the already accepted falling-water fence face cue. No 1.8 aliases, 1.11.2–1.12 aliases, other fence families, additional flow providers, caller-range changes, runtime reachability, or trajectory equivalence are claimed.