# Falling-water wall-face boundary, r1

## Finding

The era-existing dry oak-fence side witness first stops producing the falling-water side cue at **1.12.0**, after the inspected 1.11.2 behavior. The 1.12 change is a face-provider gate: the liquid query now requires `FaceShape.SOLID`, while `FenceBlock.getFaceShape` returns `MIDDLE_POLE` on every horizontal direction. The fence remains registered as a wood-material block, so this is not a fence registration/material cutover. The first observed boundary is 1.12.0; the later inspected exact releases through 1.14.4 preserve rejection of the same dry-fence witness.

This is an exact-source endpoint comparison, not proof that every intervening snapshot, pre-release or uninspected patch has identical behavior. The accepted 1.11.2 source and new exact sources are hash-identified below. The earlier lane files remain byte-preserved.

## Operation, gates and normalizations

The old and examined new falling-water paths preserve the same relevant outer operation: at the falling-level gate (`LEVEL >= 8` in 1.12/1.13; `FALLING` in 1.14.4), examine each horizontal `direction`, query the neighbor at `pos.offset(direction)` and the cell above it with that same direction, then on the first side hit add the normalized horizontal vector to `(0,-6,0)` and normalize the final vector. In 1.12 `LiquidBlock.getFlow` is `LiquidBlock.java:139-178`; 1.13 `FlowingFluid.getFlow` is `FlowingFluid.java:39-83`; 1.14.4 `FlowingFluid.getFlow` is `FlowingFluid.java:52-100`. The face direction is not inverted.

The helper retains these branch orders at the sampled versions:

| Exact release(s) | Same liquid/material | `UP` | Ice | Remaining predicate |
| --- | --- | --- | --- | --- |
| 1.11.2 baseline | same material => false | true | `Material.ICE` => false | base `Material.isSolid()`; face ignored |
| 1.12, 1.12.1, 1.12.2 | same material => false | true | `Material.ICE` => false | reject attachment exceptions and stairs; require `getFaceShape(...) == SOLID` |
| 1.13, 1.13.1, 1.13.2 | same fluid type => false | true | state material `Material.ICE` => false | reject attachment exceptions and stairs; require `getFaceShape(...) == SOLID` |
| 1.14 Feather | same fluid type => false | true | `Material.ICE` => false | `Block.isFaceSolid`, which uses the full face of the collision shape |
| 1.14.1, 1.14.2 Yarn | matching fluid => false | `Direction.field_11036` => true (`Direction.java:20` identifies it as UP) | `Material.ICE` => false | `Block.isSolidFullSquare`, using the collision shape's queried face |
| 1.14.3 Yarn | matching fluid => false | `Direction.UP` => true | `Material.ICE` => false | `Block.isSolidFullSquare`, using the collision shape's queried face |
| 1.14.4 Mojmap | same fluid type => false | `Direction.UP` => true | `IceBlock` => false | `BlockState.isFaceSturdy`, resolving to `Block.isFaceSturdy` and full collision-face shape |
| 26.2 baseline | same fluid type => false | `Direction.UP` => true | `IceBlock` => false | full `SupportType.FULL` face-sturdy check |

Exact helper locations: 1.12 `LiquidBlock.java:82-100`; 1.13 `FlowingFluid.java:90-108` (1.13.1/.2 carry the same source hash); 1.14 Feather `FlowingFluid.java:106-116`; 1.14.1/.2 Yarn `BaseFluid.java:105-114`; 1.14.3 Yarn `BaseFluid.java:105-114`; 1.14.4 Mojmap `FlowingFluid.java:105-115`; current 26.2 `FlowingFluid.java:106-115`. In 1.12+ the explicit same-fluid/UP/ice ordering therefore remains, though the same-liquid test moves from material equality to fluid type as the code is reorganized.

## Why the fence witness changes at 1.12

1.11.2 delegates the non-special side check to `Block.isFaceSolid`, which reads the neighbor material's solidity and ignores the requested face. Oak fence is registered with `Material.WOOD`; the old query therefore accepts its north side. In 1.12 the query still rejects equal fluid material, accepts UP and rejects ICE, but the ordinary branch requires `FaceShape.SOLID`. The exact 1.12 `FenceBlock.getFaceShape` returns `MIDDLE_POLE` for horizontal faces and `CENTER` vertically (`FenceBlock.java:202-204`), so a horizontal fence candidate fails. The same face provider and query are present in 1.12.1 and 1.12.2.

The 1.12 registry still creates numeric block ID 85, `fence`, using `FenceBlock(Material.WOOD, ...)` (`Block.java:946-954`). In 1.13 the registered `oak_fence` remains a `FenceBlock` with `Material.WOOD` (`Block.java:1094`; 1.13.1/.2 `Block.java:1075`). Thus the new false result comes from face-shape semantics, not from the wooden fence changing identity or material. In 1.13 the fence keeps horizontal `MIDDLE_POLE` (`FenceBlock.java:116-118`), so its query still fails.

By 1.14, the full-face predicate uses collision shape instead of the older `FaceShape` enum. A dry unconnected oak-fence state is still not a full horizontal face: the 1.14 Feather fence uses a narrow post plus optional arms (`FenceBlock.java:27-30` and its shape builder); 1.14.1-.3 Yarn use the same shape dimensions (`FenceBlock.java:25-35`); 1.14.4 Mojmap initializes all four connection properties false (`FenceBlock.java:27-31`), and its `CrossCollisionBlock` supplies a narrow post plus optional arms (`CrossCollisionBlock.java:41-86`). `Block.isFaceSturdy` tests the queried full face of this collision shape (`Block.java:403-410`). The oak fence remains registered (`Blocks.java`, the `oak_fence` entry). The 26.2 baseline likewise uses the full support face, as recorded in the original comparison.

## Concrete ordinary-player witness

Use one water cell with falling level (`LEVEL=8` in 1.11.2/1.12 era; falling state in later sources), no horizontal liquid-depth gradient, and a dry oak fence north of it, with no other side or above-side candidate qualifying. The inspected 1.11.2 material predicate treats the wood fence as solid and contributes the downward-biased vector. In 1.12.0 and the sampled later releases, the fence's horizontal face is partial and contributes no falling-side cue; with no horizontal gradient this witness leaves the cell's flow vector zero. Same-liquid, UP and ice cases are held aside by their explicit branches and are not the source of this difference.

The original comparison establishes the end-to-end ordinary LocalPlayer path at both endpoints (1.8 and 26.2). This boundary addendum is limited to the source operation and the era-existing fence dependency; it does not extend the generic water upper-bound or glass analysis.

## Source identity and immutable routing

Historical sources are from the workspace's exact-release Feather decompilation roots. The 1.12 and 1.12.1 source roots carry hidden `.decompile-provenance.json`; 1.12.2, 1.13/.1/.2 and 1.14 Feather carry version-root provenance and `ornithe-feather.sources.sha256` sidecars. Yarn 1.14.1/.2/.3 carry per-root hidden provenance; 1.14.4 Mojmap carries version-root provenance and `mojmap.sources.sha256`. The exact mapped releases/builds are pinned by those provenance records. Each source hash listed below was recomputed and matched the indicated exact source manifest/sidecar. No source was modified.

Key source SHA-256 (full values; identical hash means identical source bytes across listed roots):

| Exact release(s) / namespace | Source | SHA-256 |
| --- | --- | --- |
| 1.12, 1.12.1, 1.12.2 / Feather | `block/LiquidBlock.java` | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` |
| 1.12, 1.12.1, 1.12.2 / Feather | `block/FenceBlock.java` | `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` |
| 1.12, 1.12.1, 1.12.2 / Feather | `block/Block.java` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` |
| 1.12, 1.12.1, 1.12.2 / Feather | `block/Blocks.java` | `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e` |
| 1.13 / Feather | `fluid/FlowingFluid.java` | `da81f379a77ef5da26e87503a1f605df4efbf5d5c02e6b6f68a916dc327df5dd` |
| 1.13 / Feather | `block/FenceBlock.java` | `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` |
| 1.13 / Feather | `block/Block.java` | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` |
| 1.13 / Feather | `block/Blocks.java` | `8e66b1ad391d61f4e42fecb313f99299810493cb87525493f9ace8a1eb7cd000` |
| 1.13.1, 1.13.2 / Feather | `fluid/FlowingFluid.java` | `2398d109ddc5785cb1bc8872b8c157319137df8957faf8a03581a8f68afb1253` |
| 1.13.1, 1.13.2 / Feather | `block/FenceBlock.java` | `abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2` |
| 1.13.1, 1.13.2 / Feather | `block/Block.java` | `735030e8bb5fc7ead2d6dbcb1b262a36a414b4349daf197456337f460b444cd4` |
| 1.13.1, 1.13.2 / Feather | `block/Blocks.java` | `3e128c9a4f6b53e1037fb85269c4d00bc85112ae52fe48f9534232e13ee2a303` |
| 1.14 / Feather | `fluid/FlowingFluid.java` | `a92395f2e8219aaf7b2c80444dd14ba5427577a7f1d2027c780e822eb8793a15` |
| 1.14 / Feather | `block/FenceBlock.java` | `99bf66fc30039a136940b25a3d05c5e9035b857f26e4884f68c7b237f54ec8d7` |
| 1.14 / Feather | `block/Block.java` | `0308b22d47062863ee36db68b907d42c7a95e060d5841f4d6e9cf67eda66fd6a` |
| 1.14 / Feather | `block/Blocks.java` | `b568c300287713c9b5ba59c6382f60014830163008fcd3613fbe54373ed43042` |
| 1.14.1 / Yarn | `fluid/BaseFluid.java` | `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` |
| 1.14.1 / Yarn | `block/FenceBlock.java` | `541fc19f45d44bf38c4ee98c9842444e7ef6110833cf582254a94497fd67287c` |
| 1.14.1 / Yarn | `block/Block.java` | `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe` |
| 1.14.1 / Yarn | `block/Blocks.java` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |
| 1.14.1 / Yarn | `util/math/Direction.java` | `7d6b8dff3edf46d50b0384ca0515b482095bfcd56c7cf00fa1aa5bff94a57f66` |
| 1.14.2 / Yarn | `fluid/BaseFluid.java` | `a2509164dc7d354fdef23957f96d2d05c0f6bff73446f8362c6acff8f5a81e1d` |
| 1.14.2 / Yarn | `block/FenceBlock.java` | `d3bc15b3ed57f3c0c1852c23bef7582f6b67b961a27939c84bde2681d842c022` |
| 1.14.2 / Yarn | `block/Block.java` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` |
| 1.14.2 / Yarn | `block/Blocks.java` | `f5ba7169237468c68c085ecbf101e0e1c620013c00744a20dfae7c8af97c74dd` |
| 1.14.2 / Yarn | `util/math/Direction.java` | `7d6b8dff3edf46d50b0384ca0515b482095bfcd56c7cf00fa1aa5bff94a57f66` |
| 1.14.3 / Yarn | `fluid/BaseFluid.java` | `7a178ec425b1103d1231387e4bb1c88ee67babeb288822fb0e9f7a2aa854776a` |
| 1.14.3 / Yarn | `block/FenceBlock.java` | `aed6b128eb58984376e2ad3c7fbcc92f5a47bc22769c9abc51d876d2a7635a43` |
| 1.14.3 / Yarn | `block/Block.java` | `d8728c19555875aee8f47ea138bf836d02acf9076d69fce289baad93c60e715f` |
| 1.14.3 / Yarn | `block/Blocks.java` | `d003f43e26e26190ed29b17cd3a0f16b0473b5b4b58d4dbd4a247974af8b0f71` |
| 1.14.3 / Yarn | `util/math/Direction.java` | `319758bd2eb12d3862c8c9b967dae40321c3149dee8a63b4d61391799ce96335` |
| 1.14.4 / Mojmap | `material/FlowingFluid.java` | `e8a395552e53f33bcb5648325615d31bc1796ce2a8aeb501bdda3127622dccdf` |
| 1.14.4 / Mojmap | `block/FenceBlock.java` | `d45b20c3b1280285b823a5a8bdab8cfb526fd5cda41c33d83fee75dbb17f6f3e` |
| 1.14.4 / Mojmap | `block/CrossCollisionBlock.java` | `5088eb166ca1ca36eb496ab120338d713c30bec84ff5815ec266a52ea1444ecd` |
| 1.14.4 / Mojmap | `block/Block.java` | `276022cfc5bc00437fe65a23bbcdc9c078026e68d88930eb3244581f6dbfcf5f` |
| 1.14.4 / Mojmap | `block/Blocks.java` | `983d0cde25f55ddb055015b682bbdf3b131394208561805f26d9b2a240dee9a9` |

New provenance identity hashes: 1.12 hidden Feather manifest `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; 1.12.1 hidden Feather manifest `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; 1.12.2 Feather provenance `747425a26da363d1abc8a2cecd83a74504aeb1f58be93aa53f48c690e35318ce`, source sidecar `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13 Feather provenance/sidecar `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` / `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`; 1.13.1 `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904` / `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; 1.13.2 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` / `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; 1.14 Feather provenance/sidecar `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` / `68bc39e38bc501e7c9af315de4678263e1a64606b0b9780099187c7984ccbc98`; 1.14.1 Yarn hidden manifest `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907`; 1.14.2 Yarn hidden manifest `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a`; 1.14.3 Yarn hidden manifest `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`; 1.14.4 Mojmap provenance/sidecar `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` / `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.

The original source files listed in `comparison.md` were rehashed on this continuation and retain their recorded values. The two original lane artifact hashes are `comparison.md` `1a75e279c01a7c78e9d5ca4d6d888ae66b2821742cc6006735545275ff69aec0` and `falling-water-wall-face-solidity.md` `8d8ffe81b4978535649fde3556381ce87b52d9fe2275a9603e42d1d2c9507676`.