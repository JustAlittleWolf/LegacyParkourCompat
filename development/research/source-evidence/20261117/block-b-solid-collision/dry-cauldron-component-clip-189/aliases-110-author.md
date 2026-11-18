# Exact 1.10 alias source check: cauldron component clipping

## Scope and verdict

Checked only exact 1.10, 1.10.1, and 1.10.2 Ornithe Feather sources against the supplied 1.8.9-source audit (`review-cauldron-component-clip-observability/verdict.md`, SHA-256 `51456db253a63c257acfb32bf1156d4f1ddb9721dde5164a30533193c5aa05c8`). The approved claim remains applicable to all three exact aliases: the cauldron provider, strict axis-overlap clip predicate, entity collision query and move/step route, and command placement route are byte-identical across them. I found no alias-specific gap that changes the stipulated supported, stationary, command-created low-canopy setup. This is a bounded source check, not runtime verification and not a claim about other patches.

## Exact source identity

All three trees identify the `ornithe-feather` mapping family. The 1.10 and 1.10.1 `.decompile-provenance.json` manifests identify their exact client metadata/artifact and Feather Gen2 build.2 mapping artifact. The 1.10.2 ready marker identifies exact version `1.10.2`, mapping `ornithe-feather`, Feather Gen2 build.2 artifacts, and source-manifest SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; that matches the present `ornithe-feather.sources.sha256` file. Hidden `.decompile-provenance.json` files for 1.10 and 1.10.1 were included in the inspection.

The five relevant source files have matching manifest hashes in each tree; 1.10.2 was checked against its source sidecar and 1.10/1.10.1 against their embedded `sources` entries. Their identical hashes are:

| Source file (relative to `ornithe-feather/`) | SHA-256 in 1.10, 1.10.1, and 1.10.2 |
|---|---|
| `net/minecraft/block/CauldronBlock.java` | `84b346d09f7badf7821a43ef8a6a16fbcd161972ad67a8030c7574780ca6b205` |
| `net/minecraft/util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/server/command/SetBlockCommand.java` | `8773b5abd2711199e772a667dc1c11bfdfced2b8d533a4ecc306dbd5e3517d85` |

Because each relevant file is byte-identical across aliases, the line references below apply to all three.

## Checked behavior

- **Collision provider and order:** `CauldronBlock.addCollisions` (lines 45–51) always appends the base/floor box, then west, north, east, and south wall boxes. No cauldron-level or water-state gate occurs in this method. The south wall is `[0,1] × [0,1] × [0.875,1]`; the east wall is `[0.875,1] × [0,1] × [0,1]`. The empty default cauldron state therefore yields the same five-box union and order used by the fixture.
- **Strict predicate:** `Box.intersectZ` (lines 183–200) first rejects when `box.maxX <= this.minX`, `box.minX >= this.maxX`, `box.maxY <= this.minY`, or `box.minY >= this.maxY`; only strict orthogonal overlap proceeds to the Z-axis distance clip. `intersectX` and `intersectY` use the corresponding strict predicates. The 5e-8 X overlap specified in the reviewed fixture is therefore a real overlap in these sources.
- **Reachable query and move/step routes:** `World.getCollisions` (lines 903–942) scans the queried bounds and calls each encountered block state's `addCollisions` at line 937. `Entity.move` obtains this query for the swept box (line 514), clips Y then X then Z through the returned list (lines 518–537), and enters the step alternative only under `stepHeight > 0`, grounded/falling eligibility, and a horizontal clip (line 538). `LivingEntity` initializes `stepHeight = 0.6F` (line 165), and its ordinary travel path calls `move(this.velocityX, this.velocityY, this.velocityZ)` at line 1371. `Entity.move` sets `onGround` from downward vertical clipping (line 633). These are live player movement paths in the exact source, rather than isolated helper code.
- **Low ceiling and normal-step fallback:** The exact `Entity.move` step path at lines 538–625 retains the 0.6F rise request, clips both horizontal legs against the same queried collision list, compares squared horizontal progress, and restores the normal clipped box when `q*q + s*s >= dx*dx + dz*dz` (line 620). This agrees with the supplied verdict's roofed-case tie analysis. No alias changes that gate or fallback.
- **Supported command placement:** `SetBlockCommand` is named `setblock` and requires permission level 2 (lines 22–31). Its normal placement path resolves state metadata (line 91) and calls `world.setBlockState(blockPos, blockState, 2)` directly (line 92); it has no entity-collision validation. The default metadata is zero when omitted. In `World.setBlockState` (line 247 onward), source checks world-height/debug constraints and writes into the chunk; no collision rejection or player push-out is called. Thus a command can install the dry cauldron around a stationary player after the bottom slab has established support.
- **Push-out/tick check:** The exact entity sources contain no `moveTowardsClosestSpace` or `pushOutOfBlocks` path. The only nearby `getCollisions(this, this.getShape()).isEmpty()` use in `Entity` is the vertical position adjustment loop in `setPosition` (around line 243), not an entity-tick reaction to a block being placed. `Entity.tick` itself contains no collision push-out. This supports the immediate post-command query assumed in the verdict; do not insert a player-motion tick between placement and the measured move.

## Conclusion and limits

For the supplied corrected float-width endpoints, dry cauldron state, bottom-slab support, command placement, and top-slab ceiling, the 1.10, 1.10.1, and 1.10.2 source aliases preserve all old-side conditions needed for the stipulated direct +Z clip to affect the selected full-move result. The alias check does not revisit or expand the reviewed modern-side equal-union claim, establish runtime parity, establish command-free reachability, or infer a cutover for any uninspected release.
