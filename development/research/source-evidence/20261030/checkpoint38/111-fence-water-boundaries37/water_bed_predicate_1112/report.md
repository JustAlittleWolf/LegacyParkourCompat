# Level-8 water current against a bed: patch applicability

## Scope and conclusion

Compared the exact `ornithe-feather` sources for 1.10.2, 1.11, 1.11.1, 1.11.2, 1.12, 1.12.1, and 1.12.2. The old material-based side-face predicate is present in **1.10.2, 1.11, 1.11.1, and 1.11.2**. The `FaceShape` predicate and the bed's `UNDEFINED` face result are present in **1.12, 1.12.1, and 1.12.2**. Thus this witness is old through 1.11.2 and new beginning with exact 1.12.0. The 1.10.2 comparison confirms the old behavior predates 1.11. It does not establish behavior for releases earlier than 1.10.2 or uninspected patches outside the listed versions.

The difference is limited to whether a horizontal bed face supplies the level-8 downward-flow wall input. The surrounding `getFlow` arithmetic, its `LEVEL >= 8` gate, and the liquid-drag velocity writer remain present in both groups. This is a direct player fluid response on received world state; it makes no claim about server-side historical physics or producing the received block states.

## Exact source identities

All seven sources use the Ornithe Feather Gen2 `build.2` naming family and Vineflower 1.12.0. Exact version-scoped artifacts are:

| Version | Mapping artifact identity | Source identity evidence |
|---|---|---|
| 1.10.2 | `feather-gen2-1.10.2+build.2-mergedv2.jar` and `feather-gen2-1.10.2+build.2.tiny` | ready source-manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `feather-gen2-1.11+build.2-mergedv2.jar` and `feather-gen2-1.11+build.2.tiny` | ready source-manifest SHA-256 `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |
| 1.11.1 | `feather-gen2-1.11.1+build.2-mergedv2.jar` and `feather-gen2-1.11.1+build.2.tiny` | ready source-manifest SHA-256 `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` |
| 1.11.2 | `feather-gen2-1.11.2+build.2-mergedv2.jar` and `feather-gen2-1.11.2+build.2.tiny` | ready source-manifest SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.12 | `feather-gen2-1.12+build.2-mergedv2.jar` | embedded provenance SHA-256 `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`; mapping jar SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` |
| 1.12.1 | `feather-gen2-1.12.1+build.2-mergedv2.jar` | embedded provenance SHA-256 `0ff5246e8d29c04554cc9ec93ae9b4abef539652325b6c0ac822851d7186a9c8`; mapping jar SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7` |
| 1.12.2 | `feather-gen2-1.12.2+build.2-mergedv2.jar` and `feather-gen2-1.12.2+build.2.tiny` | ready source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |

Relevant Java-file SHA-256 values were recomputed from the read-only source trees:

| Versions | `block/LiquidBlock.java` | `block/BedBlock.java` | `block/Block.java` | `block/FlowingLiquidBlock.java` |
|---|---|---|---|---|
| 1.10.2 | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `7893d2ceb9e572562018b7e55d8fecdc33e0445be89c39522075a833ccd1e463` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79` |
| 1.11 | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2` |
| 1.11.1, 1.11.2 | same as 1.11 | same as 1.11 | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` | same as 1.11 |
| 1.12, 1.12.1, 1.12.2 | `00f7995dd49a48bf97316e30b3d6e41d8ee1e65dd212f3dc64891883621c4260` | `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369` | `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1` | same as 1.11 |

`block/material/Material.java` has SHA-256 `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` in every listed version. `block/Blocks.java` hashes: 1.10.2 `40ed1ada6eb5a36221b3b576a7eec21824911c92131d54d4f620615e1e71d1c9`; 1.11/1.11.1/1.11.2 `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d`; 1.12/1.12.1/1.12.2 `8d34c5e531407689108668a84bb8fe705bf987b1481ae8b6d3a6e39424243e2e`. New state dispatch is in `block/state/StateDefinition.java`, hash `934493deb8dad66936edf94264bda735ae8062c6ef091b1ff6a1501eed2b8d37` for all three 1.12 sources.

## Predicate and witness

- **1.10.2:** `LiquidBlock.isFaceSolid` (`block/LiquidBlock.java:82–90`) rejects same-material liquid, accepts `UP`, rejects ice, then falls back to `Block.isFaceSolid`; `Block.isFaceSolid` (`block/Block.java:332–334`) returns the neighboring state's material `isSolid()`. `BedBlock` constructs with `Material.WOOL` (`block/BedBlock.java:31–33`); `Material.WOOL` is the ordinary `new Material(...)` entry (`block/material/Material.java:19`) and base `Material.isSolid()` returns `true` (`:63–65`). `Block.java:776` registers the `bed` instance as `new BedBlock(...)`.
- **1.11 / 1.11.1 / 1.11.2:** the same old route is at `LiquidBlock.java:83–91`, base `Block.isFaceSolid` at `Block.java:343–345`, bed construction at `BedBlock.java:31–33`, and the `bed` instance registration at `Block.java:781`. The `LiquidBlock.java` hash is identical across these three exact versions.
- **1.12 / 1.12.1 / 1.12.2:** `LiquidBlock.isFaceSolid` is now private (`block/LiquidBlock.java:82–100`); after liquid, UP, and ice checks it rejects the attachment-exception/stairs cases and returns whether the neighboring `BlockState.getFaceShape(...)` is `SOLID`. Bed's constructor still uses `Material.WOOL` (`BedBlock.java:39–42`), but `BedBlock.getFaceShape` returns `UNDEFINED` for every face and state (`BedBlock.java:326–329`). State dispatch forwards the full state to its owning block (`state/StateDefinition.java:415–416`); bed head/foot are `PART` variants of the same registered `BedBlock`, and the override ignores `state`, position, and face. `Blocks.BED` resolves the `bed` registry entry (`Blocks.java:310`), registered as a `new BedBlock(...)` in `Block.java:800`. The attachment-exception set is the placement exceptions plus piston, sticky piston and piston head (`Block.java:219–234`); it does not include bed.

The movement-affecting caller remains `LiquidBlock.getFlow`: 1.10.2 and 1.11.x `block/LiquidBlock.java:131–177`; 1.12.x `:139–185`. It forms the horizontal depth-gradient vector, then only when `(Integer)state.get(LEVEL) >= 8` scans horizontal neighbors and each neighbor's block above. On the first solid side it does `vec3d.normalize().add(0.0, -6.0, 0.0)`, then normalizes the result. With an otherwise zero-gradient level-8 water cell, bed as the only horizontal side candidate, and air at the corresponding above-side and other candidates, the old predicate yields a downward normalized flow; the new bed face does not add the downward term and the result remains zero. `FlowingLiquidBlock.tick` can produce such a level-8 state by spreading a level 0–7 fluid downward with `i + 8` (`block/FlowingLiquidBlock.java:92–96` in 1.10.2/1.11.2 and 1.12.2; same file hash across 1.11.x and 1.12.x).

## Player caller and gates

The fixed-world fluid response path is present in each listed version. `Entity.checkWaterCollisions` skips liquid drag for a boat mount and otherwise calls `World.applyLiquidDrag` with `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` and `Material.WATER`. `World.applyLiquidDrag` enumerates intersecting fluid states and aggregates `Block.applyMaterialDrag`; when the vector is nonzero and `entity.hasLiquidCollision()` it normalizes and adds `0.014` times each component to velocity. `PlayerEntity.hasLiquidCollision()` is `!abilities.flying`. Therefore a local-player witness requires water overlap, an unmounted player, and non-flying abilities. The flow predicate reads the received neighboring `BlockState`s; no server replay or state-production claim is made.

Exact line anchors for the key call/gates (minor releases were individually inspected):

| Version | `Entity.checkWaterCollisions` boat/drag gate | `World.applyLiquidDrag` aggregate velocity writer | player `hasLiquidCollision` |
|---|---|---|---|
| 1.10.2 | `entity/Entity.java:862–865` | `world/World.java:1603–1608` | `entity/living/player/PlayerEntity.java:1736–1738` |
| 1.11 | `entity/Entity.java:884–887` | `world/World.java:1625–1630` | `entity/living/player/PlayerEntity.java:1729–1731` |
| 1.11.1 | `entity/Entity.java:934–937` | `world/World.java:1604–1609` | `entity/living/player/PlayerEntity.java:1736–1738` |
| 1.11.2 | `entity/Entity.java:927–930` | `world/World.java:1604–1609` | `entity/living/player/PlayerEntity.java:1736–1738` |
| 1.12, 1.12.1, 1.12.2 | `entity/Entity.java:948–951` | `world/World.java:1612–1617` | `entity/living/player/PlayerEntity.java:1733–1735` |

### Applicability boundary

For this accepted bed-side witness, preserve the 1.11.x behavior through **1.11.2** and apply the `FaceShape.UNDEFINED` behavior beginning at **1.12.0**, including 1.12.1 and 1.12.2. The 1.10.2 check proves the old-face behavior is also present there; it does not justify applying a 1.11-specific change backward before 1.10.2. No claim is made about unlisted releases, profiles older than 1.10.2, unrelated level/current cases, or historical server physics.
