# Early water-flow drag discovery

## Bound and verified inputs

Compared the assigned exact Ornithe Feather sources in release order: 1.8.8, 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3, 1.9.4, and 1.10.2. The mapped artifact on each side is Feather Gen2 build.2. The 1.8.9, 1.9.4, and 1.10.2 sibling ready markers agree with their source-manifest and artifact-manifest SHA-256 values; the other five source trees have their decompile provenance manifests. For all eight releases, local SHA-256 values for `World.java`, `LiquidBlock.java`, `Entity.java`, and `Box.java` match their source manifest. I also checked the bounded method bodies and call sites cited below.

Mapping-artifact SHA-256 identities (merged artifact; the sibling campaign artifact manifests additionally pin the Tiny files for 1.8.9, 1.9.4, and 1.10.2):

| Release | Feather Gen2 build.2 merged artifact SHA-256 |
| --- | --- |
| 1.8.8 | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` |
| 1.8.9 | `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` |
| 1.9 | `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a` |
| 1.9.1 | `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3` |
| 1.9.2 | `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859` |
| 1.9.3 | `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3` |
| 1.9.4 | `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` |
| 1.10.2 | `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` |

The selected source-file SHA-256 values group as follows; each group was verified against every listed release's source manifest:

| Release(s) | `World.java` | `LiquidBlock.java` | `Entity.java` | `Box.java` |
| --- | --- | --- | --- | --- |
| 1.8.8, 1.8.9 | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` |
| 1.9 | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |
| 1.9.1, 1.9.2 | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` | `cb0f30c72b7925fa0c0b133dffd051cf8260f4321f7c4a093c64ef5a5f500e62` |
| 1.9.3, 1.9.4 | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `055d57e1e555cc378fd1d7ea14d57036190ae335238175d284b969f439abe198` |
| 1.10.2 | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |

## Covered operation and result

The ordinary player path is inherited `Entity.tick()` calling `checkWaterCollisions()` (1.8.8/1.8.9 `Entity.java:300`; 1.9–1.9.2 `:358`; 1.9.3/1.9.4 `:361`; 1.10.2 `:366`). For water, that method passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` in 1.8.x, and the equivalent uniform `.contract(0.001)` in 1.9 onward (`Entity.java:746–759` and `:845–861`, then `:849–865`, `:862–878`). `Box.contract(0.001)` delegates to `expand(-amount)`, while `expand` delegates to `grown(amount, amount, amount)`; the bounds are thus contracted by 0.001 on each face in both forms (`Box.java:176–184` in 1.8.x; `:98–110,220–222` in 1.9.2; corresponding methods in later versions).

`World.applyLiquidDrag` is also the flow-vector admission and addition path (`World.java:1486–1527` in 1.8.8/1.8.9; `:1554–1596` in 1.9–1.9.2; `:1560–1602` in 1.9.3/1.9.4; `:1570–1612` in 1.10.2). Before 1.9 it computes each maximum cell bound as `floor(bounds.max + 1.0)` and loops with an exclusive `< maxCell` bound. From 1.9 onward it computes `ceil(bounds.max)` and retains the exclusive loop. Minimum bounds remain `floor(bounds.min)`. Liquid vertical admission remains `l >= p + 1 - getHeightLoss(level)`. If the scan admits liquid, `LiquidBlock.applyMaterialDrag` adds that block's flow vector to the accumulator. When the resulting vector is nonzero and `entity.hasLiquidCollision()` is true, the world normalizes it and adds its components times `0.014` to entity velocity. `PlayerEntity.hasLiquidCollision()` is `!abilities.flying` in all checked versions (`PlayerEntity.java:1617` in 1.8.x, `:1710` in 1.9–1.9.2, `:1717` in 1.9.3/1.9.4, `:1736` in 1.10.2).

The upper-bound change is behaviorally observable at an exact integer maximum coordinate. For a contracted player box with `bounds.maxY == N`, 1.8.x sets `l = floor(N + 1) = N + 1` and scans cell `p == N`; 1.9 onward sets `l = ceil(N) = N` and does not scan that cell. With the horizontal footprint inside a water block at `(x, N, z)`, an admitted level-1 water block has top `d = N + 1 - ((1 + 1) / 9.0F)`, so the older `l >= d` gate passes. Arrange a neighboring level-0 water source to give the admitted level-1 cell a nonzero horizontal flow vector. A non-flying player then receives the normalized `0.014` velocity addition from that cell in 1.8.x; the 1.9+ scan omits it. `checkWaterCollisions()` also reports water contact and updates the water/fall state from the method's boolean result, but the claimed mechanical change here is the omitted water-cell contribution to flow and contact admission.

The cell-bound change applies to all three maximum axes. The vertical-face witness above holds X and Z in the interior, so it depends only on the exact upper-Y boundary and avoids a corner-only case. The player box's `grown(0,-0.4,0)` and epsilon contraction are included in the precondition; a player Y position for which the resulting upper bound is exactly `N` is reachable because entity positions are doubles.

## No additional water-flow-vector change found

`LiquidBlock.getFlow` is at `LiquidBlock.java:129–161` in 1.8.x, `:128–168` in 1.9–1.9.2, and `:131–172` in 1.9.3–1.10.2. Across these versions it accumulates horizontal-neighbor liquid-depth differences in `Direction.Plane.HORIZONTAL` order, includes the same downward-adjacent-liquid contribution when an open neighbor is empty, applies the same falling-liquid wall impulse (`normalize().add(0,-6,0)`), and normalizes before returning. The 1.8 form accumulates immutable `Vec3d.add` results using coordinate deltas; 1.9 changes that representation to scalar `double` accumulators using the equal direction offsets. The per-component additions remain in the same neighbor order. In 1.9.3, `getLiquidDepth` accepts `BlockState`; `applyMaterialDrag` supplies the current position state to `getFlow`. Neighbor depth lookups use `world.getBlockState`. This is a state-access refactor with the same values and arithmetic for a stable client-world snapshot, not another demonstrated movement difference. In 1.10.2, the final velocity multiply spells `0.014` inline instead of through a local `double e = 0.014`; the resulting scalar operation is unchanged.

## Excluded from this slice

The 1.9+ `getMount() instanceof BoatEntity` guard in `Entity.checkWaterCollisions()` is an on-boat transition and was not evaluated as part of this assignment. Lava-presence scanning and unrelated material tests were also not used as evidence.

## Scope limit

This report closes only the assigned water-flow/vector-addition and world-liquid-contact-admission slice. It does not establish complete player-tick coverage or pair freeze. No build, tests, runtime, gameplay, decompilation, or network access was performed.
