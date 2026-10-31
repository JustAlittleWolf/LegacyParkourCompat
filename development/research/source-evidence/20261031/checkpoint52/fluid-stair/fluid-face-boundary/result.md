# Fluid stair face boundary

**Result: earliest stable release in the checked range is 1.14.0.** The candidate is bound to SHA-256 `A4227DFC907B8B195DB475EFAC66FFFBD7CA8024E7AE1A8CA77CF8C94E61ECEB`; the computed digest matches, and the bound review renews acceptance for that revision.

## Exact source crosswalk

| Release / mapping | Falling-flow branch and face predicate | Default LEAVES recipe | Integrity |
|---|---|---|---|
| 1.13.2 / Ornithe Feather | `FlowingFluid.getFlow`, lines 87–93, calls `isSolidFace`; lines 105–123 reject `StairsBlock` before checking `FaceShape.SOLID`. | `data/tag/BlockTagsProvider.java:239–240` lists six leaf blocks, no stairs. | Ready marker is exact `1.13.2` / Ornithe Feather; source-manifest SHA matches its ready marker. Candidate-cited `FlowingFluid.java` and `Entity.java` hashes match the source manifest. |
| 1.14 / Yarn build.21 | `fluid/BaseFluid.java:87–97` runs the falling branch and calls `method_15749`; lines 105–114 reject same-fluid, accept UP, then use `Block.isSolidFullSquare`. `block/Block.java:388–395` excludes `BlockTags.field_15503` (registered as `"leaves"` in `tag/BlockTags.java:39`) and tests whether the collision shape fills the requested face. | `data/server/BlockTagsProvider.java:288–289` fills `field_15503` with six block entries. `Blocks.java:191–207` maps them to the six vanilla `LeavesBlock` registrations. Stairs are absent. | Provenance inputs identify Minecraft `1.14.json` and Yarn `1.14+build.21`; each relevant source SHA matches the provenance `sources` map. |
| 1.14.1 / Yarn build.10 | Same branch and predicate: `BaseFluid.java:87–97,105–114`; `Block.java:399–405`. | Same six-entry `field_15503` recipe at `BlockTagsProvider.java:288–289`; the leaf registrations match 1.14. | Provenance inputs identify `1.14.1.json` and Yarn `1.14.1+build.10`; relevant source SHAs match its `sources` map. |
| 1.14.2 / Yarn build.7 | Same branch and predicate: `BaseFluid.java:87–97,105–114`; `Block.java:398–404`. | Six-entry `field_15503` recipe at `BlockTagsProvider.java:290–291`; `Blocks.java` confirms the same leaf registrations. No stair entry. | Provenance inputs identify `1.14.2.json` and Yarn `1.14.2+build.7`; relevant source SHAs match its `sources` map. |
| 1.14.3 / Yarn build.13 | `BaseFluid.java:87–97` retains the falling branch; lines 105–114 call `Block.isSolidFullSquare`. `Block.java:395–402` explicitly names `BlockTags.LEAVES` and checks the collision face. | `BlockTagsProvider.java:300–301` lists six leaves and no stairs. | Provenance inputs identify `1.14.3.json` and Yarn `1.14.3+build.13`; relevant source SHAs match its `sources` map. |
| 1.14.4 / Ornithe Feather | Candidate-cited `FlowingFluid.getFlow` / `isSolidFace` retains the face test using collision-face solidity. | `data/tag/BlockTagsProvider.java:299–300` lists the six leaves and omits stairs. | Ready marker is exact `1.14.4` / Ornithe Feather; source-manifest SHA matches its ready marker. Candidate-cited `FlowingFluid.java`, `Entity.java`, and `BlockTagsProvider.java` hashes match the manifest. |

For each 1.14 Yarn root, `StairsBlock.getOutlineShape` selects the state-indexed bottom stair shape (`StairsBlock.java:102–107`) and retains the same bottom-shape composition (`StairsBlock.java:37–53`). The inherited `Block.getCollisionShape` returns that outline shape when collidable (`Block.java:360–362` in 1.14, `371–373` in 1.14.1, `370–372` in 1.14.2, and `369–371` in 1.14.3). Thus the south-facing, bottom, straight stone-brick stair contributes a full SOUTH collision face in the queried state. Its exclusion from default `LEAVES` means the new predicate returns true for that face. The intervening releases 1.14.1–1.14.3 retain the operation; the earliest checked stable release is 1.14.0.

For the Yarn roots, each listed source digest was recomputed from disk and matched the `sources` map in the adjacent `.decompile-provenance.json`. The principal face-test source digests are:

| Root | `.decompile-provenance.json` SHA-256 | `BaseFluid.java` SHA-256 | `Block.java` SHA-256 |
|---|---|---|---|
| 1.14 / Yarn | `ea70a955a12f377cd3b9e8d5f346f4625b044d55da726b6bc472405ae91f4341` | `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` | `799e08e9631c0a6cdb74be2faeacb054f3c638e1b35c13863eeeba96417b2f2c` |
| 1.14.1 / Yarn | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` | `c596bd4fa6aaf4f176c4be18d9e3125ff8b3e47846cf5a99253cccc9ba7bf5b5` | `f5ef3ec7cecd508d948ac5f4a09ce04a4882da000e70a518a1882a00946d56fe` |
| 1.14.2 / Yarn | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` | `a2509164dc7d354fdef23957f96d2d05c0f6bff73446f8362c6acff8f5a81e1d` | `e90cdc7b0b78310c59eb5a96cfbfed0f0ec8aaf7156d20f0876e4a04711c2d1f` |
| 1.14.3 / Yarn | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` | `7a178ec425b1103d1231387e4bb1c88ee67babeb288822fb0e9f7a2aa854776a` | `d8728c19555875aee8f47ea138bf836d02acf9076d69fce289baad93c60e715f` |

## Boundary interpretation

The stable-release boundary is directly bracketed: 1.13.2 rejects stairs categorically, while 1.14.0 uses tagged-leaf exclusion followed by the collision-face test. The requested 1.14.1, 1.14.2, 1.14.3 and 1.14.4 sources confirm retention, so there is no unresolved patch gap inside 1.14. The candidate's paired fixture assumptions and review remain necessary for its direct player-velocity claim; this check establishes the earliest release of the face-predicate behavior, not fixture provenance or a later trajectory.
