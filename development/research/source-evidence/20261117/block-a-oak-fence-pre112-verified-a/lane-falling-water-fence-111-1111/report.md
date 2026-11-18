# 1.11 → 1.11.1 falling-water oak-fence probe

## Scope and source readiness

Bounded source-only comparison: 1.11 to 1.11.1, `ornithe-feather` on both sides. Checked (a) a dry ordinary oak fence as a horizontal-face neighbor at the flowing-water cell and at the cell above it, and (b) direct local-player reachability plus current/velocity component order. This is not full-pair movement coverage and makes no claim about 1.11.2, intervening aliases, or other mapping/source pairs.

The exact `1.11` and `1.11.1` ready markers identify those exact release IDs and `ornithe-feather`; each marker's declared source-manifest SHA-256 matches its manifest bytes. The relevant Java files' SHA-256 values below also match their respective source manifests. Ready/provenance identities:

| release | ready marker SHA-256 | source-manifest SHA-256 | provenance SHA-256 | exact client SHA-256 | Feather mapping build |
|---|---|---|---|---|---|
| 1.11 | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` | `6f775c806fd1401a4d1756e9ef88da8d73185fe1d13598235f85c424405f4c40` | `f0d11987ea743f55a2c03bf77216e6dc9baea7201cc7bf498ceb18043e984634` | `feather-gen2-1.11+build.2` |
| 1.11.1 | `454036a978074d3a34359dfebf03d6ef3e3335d1318b88d6b17e4f1f5ab566cf` | `0d5f4697d4e4ce53b36d0dbfec89d44830eb76753a6643b49e42f0857753066d` | `0f6c92b8fbf4b1053a26a4cb61dc8f20c695e74a1963b49d911b8c7fbf01a055` | `eff72a31339cd9a96a61092eed2d95b95e4cc91516bea4e9105c3d73574147f6` | `feather-gen2-1.11.1+build.2` |

## Roster of examined exact source files

Paths are relative to `decompiled_minecraft/<release>/ornithe-feather/`. Each digest is the full file SHA-256 and was checked against that release's `.sources.sha256` manifest.

| path | 1.11 SHA-256 | 1.11.1 SHA-256 |
|---|---|---|
| `net/minecraft/block/LiquidBlock.java` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` |
| `net/minecraft/block/FenceBlock.java` | `790f3f58a2902a8f2ade860221c4bf1161ba9e82e92cd6ba4596b3b4002794e5` | `856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7` |
| `net/minecraft/block/Block.java` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` | `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` |
| `net/minecraft/block/material/Material.java` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` |
| `net/minecraft/block/Blocks.java` | `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d` | `69e47950c2d9761deff0ce1e1c46fefb2472bf61cb3d6c155fcb15457d03e64d` |
| `net/minecraft/world/World.java` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |
| `net/minecraft/entity/Entity.java` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` | `5a906de1d40a4a5ba8aedc9a6a9dc704e0ed862df177cf967fb1a9454765fa96` |
| `net/minecraft/entity/living/LivingEntity.java` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/util/math/Direction.java` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` | `116a61f67e93f895d29c71128b392fbde5f1ca85f1b51d9f7fce55a0dd5960bf` |

## Findings for the bounded probes

**Oak fence identity and face rule.** In both `net/minecraft/block/Block.java:927-935`, registry ID 85 / key `fence` constructs `FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())`; this is the legacy ordinary oak fence. `FenceBlock.java:106-114` (1.11) / `109-117` (1.11.1) says it is not a solid render and not a cube, and does not override `isFaceSolid`. `Material.java:63-65` returns `true` from `isSolid()`. Thus a dry fence's horizontal face counts as solid via `Block.isFaceSolid` (`Block.java:343-345`) even though the fence is non-cube.

**Falling-water probe.** `LiquidBlock.java:131-171` is byte-identical across the pair. It first gathers horizontal liquid-depth contributions in `Direction.Plane.HORIZONTAL` order (north, east, south, west; `Direction.java:336-340`). For state level >= 8, it checks each neighboring horizontal position and that position's `up` cell (`LiquidBlock.java:159-167`). On the first solid face, either probe can match the dry oak fence: its material differs from water, and its inherited solid-face result is true for horizontal faces. The current becomes `normalize(previousCurrent).add(0.0, -6.0, 0.0)`, exits the neighbor loop, then is normalized at return (`LiquidBlock.java:163-171`). This adds the downward current component for both a wall-adjacent and a wall-above fence; when both are present, the single first match causes one downward injection. The ordinary neighbor-depth branch does not sample water below the fence because `Material.WOOD.blocksMovement()` gates that path.

**Player reachability and velocity write order.** `LocalClientPlayerEntity.java:77,159-162` identifies the local client player and calls `super.tick()`; it inherits through `ClientPlayerEntity`/`PlayerEntity`/`LivingEntity` to `Entity.tick()`, which calls `checkWaterCollisions()` (`Entity.java:317-380`, both releases). `checkWaterCollisions()` passes the entity's contracted, vertically grown shape into `World.applyLiquidDrag` (`Entity.java:934-947` in 1.11.1; 1.11: 884-899). The world method loops block coordinates x (`o`), y (`p`), z (`q`), accumulates each matching liquid block's `applyMaterialDrag` result in that order, normalizes the accumulated vector once, then adds `x`, `y`, `z` components sequentially to `velocityX`, `velocityY`, `velocityZ` with scale `0.014` (`World.java:1571-1613`, 1.11.1; 1592-1634, 1.11). `Entity.hasLiquidCollision()` returns true on both releases (`Entity.java:2069-2071` / `2128-2130`), so this direct player path reaches the current-to-velocity write. `Entity.checkWaterCollisions()` also sets `inWater` from the `applyLiquidDrag` result, independent of whether the current vector is nonzero.

## Pair result and bounds

No difference was found in these two bounded probes from 1.11 to 1.11.1. The liquid-current implementation is exactly identical by whole-file hash, and checked registry, material solidity, player reachability, fluid collection and velocity component order agree in the cited slices. This is a checked no-difference result for the two assigned probes only; no full-pair freeze, implementation claim, or intermediate-patch boundary follows.