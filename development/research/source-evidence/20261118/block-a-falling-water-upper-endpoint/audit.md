# Independent source audit: falling-water upper endpoint

## Verdict

**ACCEPT**, limited to the stated exact call-boundary fixture in 1.8.9 → 1.9, Ornithe Feather build.2. The source trace supports an old-only falling-water current contribution while both versions admit water contact through the lower level-0 source. It does not establish natural ordinary-tick reachability, a stable full tick, or sustained multi-tick propagation. The oak-fence cue applies only to the dry default oak fence in this fixture.

Target author commit: `4cd8c9d010a04b52d7d66c672295f1bc7cf0f76d` (`Record bounded falling-water current witness`). Target: `legacy-fluid/falling-current-boundary/evidence.md`. Recomputed raw SHA-256: `595a2a01ebe0e87320815fc2b7d23043d10b74ee1fa7971c44cda9bacf75e795`.

The three assigned companion files match their specified SHA-256 identities: `water-upper-bound-liquid-admission.md` (`a82ce04764f3d0c5f2155c0fda86b0cf29aa460d39be2ac3bd296baef32cd330`), `comparison.corrected-r1.md` (`0e9bebe9aba84ae1b6d4fc8f9e319b064092eb727e4e808cbaddc4229df67846`), and `falling-water-wall-face-solidity-boundary.md` (`f7a69e9ac847e7a9e844fa685424d839eb4741c83bfc1984b6dade021cdaa7e1`). Relevant 1.8.9 files match the flat source manifest; relevant 1.9 files match `.decompile-provenance.json`.

## Frozen source roster and dependency closure

Before opening the target witness, the bounded pair roster was frozen to these files in each exact `decompiled_minecraft/<version>/ornithe-feather/` tree: `net/minecraft/world/World.java`, `net/minecraft/entity/Entity.java`, `net/minecraft/entity/living/LivingEntity.java`, `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `net/minecraft/block/LiquidBlock.java`, `net/minecraft/block/FenceBlock.java`, `net/minecraft/block/material/Material.java`, and `net/minecraft/util/math/Box.java`.

After opening the target, dependency closure required read-only checks of `Block.java`, `Vec3d.java`, `PlayerEntity.java`, `Direction.java`, and `MathHelper.java` in both versions. No product implementation, wiki inventory, backlog, or product Git history was consulted.

## Independent trace

The player shape uses source width `0.6F`, height `1.8F`. At lower Y `62.60100005364418`, source-ordered Java double operations yield `y + (double)1.8F = 64.40100000596046…`, then adding `(double)-0.4F` gives `64.001`, and subtracting `0.001` gives exactly `64.0`. The float widening is material: this result is for `-0.4F`, as passed to `Box.grown`, not a double `-0.4` literal. Contracted X is approximately `[-0.09900001192, 0.49900001192]`; contracted Z is approximately `[0.20099998808, 0.79900001192]`.

The 1.8.9 World loop uses `floor(max + 1.0)` and therefore has exclusive upper X/Y/Z indices `(1, 65, 1)`, with lower indices `(-1, 63, 0)`. It visits X `-1,0`, Y `63,64`, Z `0`. The 1.9 loop uses `ceil(max)` and has exclusive upper indices `(1,64,1)`, visiting X `-1,0`, Y `63`, Z `0`. Thus `(0,64,0)` is old-only and `(-1,63,0)` is scanned by both.

At the old-only falling water cell, level 8 maps to liquid depth 0. Direction order is NORTH, EAST, SOUTH, WEST. Only WEST has an air side whose lower neighbor is level-0 water; `k = 0 - (0 - 8) = 8`, producing raw vector `(-8,0,0)`. The falling-level face loop short-circuits after WEST: same-height side air is false, then the dry default oak fence above it is true through the WOOD material face predicate. Fence geometry and connection bits do not enter this query.

Both versions' `MathHelper.sqrt(double)` returns a float that `Vec3d.normalize` widens back to double. Consequently the cell normalization is source-float-rounded: `d1 = (double)(float)Math.sqrt(37.0) = 6.0827627182006836`; the vector after adding downward flow is `(-1/d1, -6/d1, 0)`. The World aggregate is then normalized again; its float-rounded length is `d2 = 0.9999999403953552`, giving `(-0.164398992025856, -0.986393952155136, 0)` for this source/tool result. This numeric trace records float rounding; `(-1,-6,0)/sqrt(37)` is only the idealized explanation.

For the lower source, each horizontal side at Y=63 and each corresponding cell below is air. Its flow is zero. Its liquid-height gate passes on both versions: old exclusive upper Y `l=65`, new `l=64`, while source height is `d=64-getHeightLoss(0)`. It makes `applyLiquidDrag` return true on both, and does not query the falling cell from its own flow calculation. The falling cell also passes the old gate (`l=65`, `d=65-getHeightLoss(8)`), but is not visited in 1.9. With a non-flying player, `hasLiquidCollision()` is true on both; the old aggregate adds its components times `0.014` to velocity.

The local-client tick reaches `super.tick()` only after its center-chunk `isChunkLoaded(BlockPos(x,0,z))` gate. Here that gate requires chunk `(0,0)`. The World loaded-area checks use X/Z ranges spanning chunk coordinates `-1` and `0` and Y indices within `0..255`; loading chunks `(-1,0)` and `(0,0)` satisfies both. The area-check Y maximum differs between releases but does not introduce another chunk requirement.

## Source bindings

- 1.8.9: `Entity.checkWaterCollisions` 746–760; `World.applyLiquidDrag` 1486–1526; `LiquidBlock.getHeightLoss` 41–47, `getLiquidDepth` 53–57, `getFlow` 129–165; `Box.contract` 176–184; `LocalClientPlayerEntity.tick` 104–115; `PlayerEntity.hasLiquidCollision` 1617–1619; `Block.isFaceSolid` 329–331; oak fence registration in `Block.java` 1016–1020; `Material.isSolid` 60–62; `Vec3d.normalize` 34–37; `MathHelper.sqrt(double)` 26–27; `Direction.Plane.HORIZONTAL` 314–315.
- 1.9: `Entity.checkWaterCollisions` 845–861; `World.applyLiquidDrag` 1554–1596; `LiquidBlock.getHeightLoss` 47–53, `getLiquidDepth` 59–63, `getFlow` 128–173; `Box.grown` 100–105, `expand` 108–110, `contract` 220–222; `LocalClientPlayerEntity.tick` 140–155; `PlayerEntity.hasLiquidCollision` 1710–1712; `Block.isFaceSolid` 305–307; oak fence registration in `Block.java` 872–880; `Material.isSolid` 62–64; `Vec3d.normalize` 35–40; `MathHelper.sqrt(double)` 27–28; `Direction.Plane.HORIZONTAL` 318–319.

No source was generated or modified. No tests, builds, game launch, runtime simulation, backlog write, or push was performed.
