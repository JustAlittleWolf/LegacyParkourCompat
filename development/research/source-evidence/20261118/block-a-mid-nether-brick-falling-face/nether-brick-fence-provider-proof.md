# Netherit brick fence at the falling-water face boundary

## Bounded result

**ACCEPT** the exact `NETHER_BRICK_FENCE` dry/default provider at the existing falling-water `LEVEL >= 8` horizontal side / side-above face probe for each listed release. It follows the same old-material-solid → 1.12 horizontal `MIDDLE_POLE` transition as the accepted oak witness. The nether fence's own masonry material and registration were checked; this conclusion does not assume the wooden registration.

This proves provider applicability only. It adds no water-flow scan, player reachability, trajectory, or patch-onset claim beyond the already accepted common caller boundary. The first inspected stable release here with the new face-shape test is 1.12.

## Exact source identities and release status

Every source root is `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/ornithe-feather`, mapped with Feather generation 2 build 2 for that exact release. For legacy trees, the identity below is the raw SHA-256 of `.decompile-provenance.json`; for ready trees it is the `sourceManifestSha256` from `ornithe-feather.ready.json`. In both cases, the named source-file hashes were checked against that source identity and the current raw files. No sources were generated or modified.

| Release | Source readiness / source identity SHA-256 | Mapping |
|---|---|---|
| 1.9 | legacy provenance `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` | Feather gen2 build.2 |
| 1.9.1 | legacy provenance `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a` | Feather gen2 build.2 |
| 1.9.2 | legacy provenance `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510` | Feather gen2 build.2 |
| 1.9.3 | legacy provenance `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a` | Feather gen2 build.2 |
| 1.9.4 | ready source manifest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | Feather gen2 build.2 |
| 1.10 | legacy provenance `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` | Feather gen2 build.2 |
| 1.10.1 | legacy provenance `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` | Feather gen2 build.2 |
| 1.10.2 | ready source manifest `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` | Feather gen2 build.2 |
| 1.11 | ready source manifest `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | Feather gen2 build.2 |
| 1.11.1 | ready source manifest `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | Feather gen2 build.2 |
| 1.11.2 | ready source manifest `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` | Feather gen2 build.2 |
| 1.12 | legacy provenance `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` | Feather gen2 build.2 |

Relevant raw source-file hash groups (all hashes match each release's listed manifest):

- `block/LiquidBlock.java`: `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` (1.9–1.9.2); `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` (1.9.3–1.10.2); `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` (1.11–1.11.2); `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` (1.12).
- `block/Block.java`: `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` (1.9–1.9.2); `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` (1.9.3–1.9.4); `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` (1.10–1.10.2); `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` (1.11); `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` (1.11.1–1.11.2); `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` (1.12).
- `block/FenceBlock.java`: `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` (1.9–1.9.2); `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` (1.9.3–1.10.2); `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5` (1.11); `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` (1.11.1–1.11.2); `633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e` (1.12).
- `block/material/Material.java`: `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` (1.9–1.9.4); `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` (1.10–1.12).

## Provider trace

- The exact `Block.java` registry entry is ID 113, key `nether_brick_fence`, constructed as `new FenceBlock(Material.STONE, MapColor.NETHER)` in every release: lines 933–937 (1.9–1.9.2), 978–982 (1.9.3–1.9.4), 983–987 (1.10–1.10.2), 988–992 (1.11–1.11.2), and 1007–1011 (1.12). The corresponding `Blocks.NETHER_BRICK_FENCE` aliases resolve `nether_brick_fence` in every release. Registration is not an oak/wood assumption: this provider uses `Material.STONE` and `MapColor.NETHER` throughout.
- Before 1.12, `LiquidBlock.isFaceSolid` rejects the same liquid material, accepts UP, rejects ICE, then delegates to `Block.isFaceSolid`; the base predicate returns the queried state's `Material.isSolid()`. The default `Material.isSolid()` returns true, including for `Material.STONE`. `FenceBlock` does not override this predicate, so a dry default nether fence in either horizontal probe passes, independent of its partial collision shape.
- In 1.12, the exact helper keeps those initial branches, then rejects `isExceptionForAttachment(block)` or `block instanceof StairsBlock`, and accepts only `blockState.getFaceShape(...) == FaceShape.SOLID`. The nether-brick fence is neither a listed attachment exception (the list is explicit in `Block.java`) nor stairs. Its fixed default state sets NORTH/EAST/SOUTH/WEST false (`FenceBlock.java:52–56`); for every horizontal direction the common `FenceBlock.getFaceShape` returns `MIDDLE_POLE` (`FenceBlock.java:202–204`). Thus both the same-height side and same-direction side-above probes fail the new predicate for this dry/default provider.
- The reused caller checks `(Integer) state.get(LEVEL) >= 8`, iterates `Direction.Plane.HORIZONTAL`, and probes `pos + direction` then `pos + direction + UP` using that same direction as the face before applying the falling contribution. Caller body/reachability was not re-audited here.

## Caller evidence reuse and its verified bytes

The accepted oak boundary artifact and its verdict were read-only and their raw bytes match the assigned SHA-256 values: `falling-water-wall-face-solidity-boundary.md` `f7a69e9ac847e7a9e844fa685424d839eb4741c83bfc1984b6dade021cdaa7e1`; sibling `verdict.md` `4f64176409d0572a4a7fafcd5a6f91221134e42a486caa780c6b8ce3ff2035ae`. The sibling-independent mid-wood verdict is the requested Git blob `cd86c49cb74b577bae2d2eaa70acd7b74b04e77c:sibling-audit-verdict.md`; `git rev-parse` returned blob `707217203803d6449cead70b7c1c0671b2110156`, whose raw file SHA-256 is `8213e4e736b15765724f587d80776a67df9adb75e96ef5ae58e8255bf787a047`.

## Per-release terminal result

| Release | Result |
|---|---|
| 1.9 | ACCEPT: stone-material default fence passes both old horizontal face probes. |
| 1.9.1 | ACCEPT: same verified provider/caller bodies as 1.9. |
| 1.9.2 | ACCEPT: same verified provider/caller bodies as 1.9. |
| 1.9.3 | ACCEPT: stone-material default fence passes both old horizontal face probes. |
| 1.9.4 | ACCEPT: same verified provider/caller bodies as 1.9.3. |
| 1.10 | ACCEPT: stone-material default fence passes both old horizontal face probes. |
| 1.10.1 | ACCEPT: same verified provider/caller bodies as 1.10. |
| 1.10.2 | ACCEPT: same verified provider/caller bodies as 1.10. |
| 1.11 | ACCEPT: stone-material default fence passes both old horizontal face probes. |
| 1.11.1 | ACCEPT: same verified provider/caller bodies as 1.11. |
| 1.11.2 | ACCEPT: same verified provider/caller bodies as 1.11. |
| 1.12 | ACCEPT: stone-material default fence returns MIDDLE_POLE horizontally and fails both new SOLID-face probes. |

No blocker remains within the assigned exact release set.
