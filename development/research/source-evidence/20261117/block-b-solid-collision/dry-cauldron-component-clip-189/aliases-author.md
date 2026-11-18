# 1.8.x cauldron component-clip alias audit

## Conclusion

For the bounded accepted 1.8.9 setup reviewed in `review-cauldron-component-clip-observability/verdict.md` (SHA-256 `51456db253a63c257acfb32bf1156d4f1ddb9721dde5164a30533193c5aa05c8`), I traced the same source path across exact 1.8 through 1.8.9 Feather trees. The horizontal clip-kernel distinction and lawful setup alias to every patch in the line. In the stipulated stationary, command-created setup, the old client finishes the roof-constrained move at +0.075 Z while the equal-union modern clip result is +0.2 Z; this carries the verdict's corrected float-width endpoints, bottom slab support, and top slab at `(1,2,0)`.

This is a bounded alias result, not a claim that all 1.8.x movement source is unchanged. `Entity.move` has step-down source changes in 1.8.1 and 1.8.2. The stipulated roof limits the alternate rise to 0.2, below the 0.5 needed to clear the wall from feet y=0.5; the old candidate horizontal legs tie, and the `>=` fallback restores the ordinary clipped move. No runtime comparison was performed.

## Exact source and integrity

All inspected files are from `decompiled_minecraft/<version>/ornithe-feather/`. For 1.8–1.8.8 I read each hidden `.decompile-provenance.json`; its `sources` map matched recomputed SHA-256 for these seven slices' enclosing source files: `block/CauldronBlock.java`, `util/math/Box.java`, `entity/Entity.java`, `entity/living/LivingEntity.java`, `entity/living/player/PlayerEntity.java`, `world/World.java`, and `server/command/SetBlockCommand.java` (7/7 for each release). The corresponding mapping input is the exact `feather-gen2-<version>+build.2-mergedv2.jar` artifact named in that sidecar.

For 1.8.9, the version-root `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, `ornithe-feather.sources.sha256`, and `artifacts.sha256` identify version `1.8.9`, namespace `ornithe-feather`, and Feather Gen2 build 2. The source-manifest SHA-256 is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`; the artifact-manifest SHA-256 is `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`. Recomputed hashes of the same seven source files match the manifest (7/7). Recomputed SHA-256 of `ornithe-feather.provenance.json` is `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, and of `ornithe-feather.sources.sha256` is `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`.

The rehashed 1.8.9 source-file identities are:

| Source file | SHA-256 |
|---|---|
| `block/CauldronBlock.java` | `cbc1724ea6707ce7f22981867cb62d4931240499291d18bd5e45a81d7a33836a` |
| `util/math/Box.java` | `8fc7bb57b1132afc7ae9539c30ff9c2585f0e36c2b0e59c60946cb1e3cee9eea` |
| `entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `server/command/SetBlockCommand.java` | `a5ea78b65ed02aa50b3474d7e6d126f60ca5f0f37493cf082570e83aa4fd93b6` |

## Direct trace

- `CauldronBlock.addCollisions` appends the floor, west wall, north wall, east wall, then south wall. The floor is `[0,1] × [0,5/16] × [0,1]`; each wall is 1/8 thick and extends to y=1. This order and geometry appear in 1.8 (`CauldronBlock.java:35–47`), 1.8.1 (`:35–47`), and 1.8.9 (`:36–48`). At 1.8.2 the local thickness variable changes from an inferred `var7` to `f`; the ordered box values are the same. Its default state is level 0, the dry cauldron state.
- `Box.intersectZ` tests strict positive overlap on X and Y using `box.maxX <= this.minX`, `box.minX >= this.maxX`, `box.maxY <= this.minY`, and `box.minY >= this.maxY` as rejection conditions, then clips only when the obstacle is ahead of the requested Z displacement (`1.8:133–151`; `1.8.9:133–151`). The operation and inequalities are identical in every patch; only local variable names change.
- `World.getCollisions(Entity, Box)` enumerates block positions covering the expanded move and calls each block's `addCollisions` in traversal order. Its source changes at 1.8.1 and 1.8.2 (mutable-position reuse and world-border caching), but the bounded block at x=0 remains in the query for the stipulated player box, and its five cauldron boxes are appended in the same internal order. The method's remaining entity-collision scan is not on the accepted isolated witness path.
- For x-min `0.99999995`, the east wall ends at x=1, so the positive 5e-8 X overlap passes `intersectZ`'s strict overlap gate. The east wall cannot clip +Z because its Z span already contains the player. The south wall begins at z=0.875; with player z-max 0.8 and requested +0.2, it clips to `0.875 - 0.8 = +0.075`. The floor does not overlap vertically at feet y=0.5. This local calculation holds for every 1.8.x version.
- `Entity.move` clips Y, then X, then Z across the returned list; its step alternative runs for positive `stepHeight` when grounded and the ordinary horizontal request clips. `LivingEntity` sets `stepHeight=0.6F` (`LivingEntity.java:133`). Its step-up calculation changed across this line: 1.8 descends by `-stepHeight`; 1.8.1 uses candidate-specific descent (`-var33` / `-var81`); 1.8.2 onward uses `-n` / `-q`. With the accepted roof, both candidate rises stop at +0.2. Their horizontal gains tie, so the source's `>=` fallback restores the ordinary clipped path. Thus these changes do not change the bounded final Z result. Without the roof, the accepted verdict expressly does not claim a final-move Z difference.
- `SetBlockCommand.getRequiredPermissionLevel()` returns 2 (`SetBlockCommand.java:24–28`). The `setblock` run path turns the requested block into its default metadata state and calls `World.setBlockState(blockPos, blockState, 2)` (`:55–100`). `World.setBlockState` checks world/debug bounds and the chunk write; it performs no entity-collision rejection (`World.java:238–292`). These command and write semantics match for the audited path throughout 1.8–1.8.9. The bottom slab may support the player before the command; the command then adds the dry cauldron without checking the existing player box.
- `Entity.setPosition` derives the box from `float width / 2.0F` and `height`; this operation is stable for all ten patches (1.8.9 `Entity.java:226–233`). The shape/position helper and `setShape` source slices are stable. `setShape`'s dimension-shrink correction is guarded by `!world.isClient`; it is not a client push-out on this path.
- `pushAwayFrom` is not a player-tick hook in these sources: call sites are `ExperienceOrbEntity` and `ItemEntity` only in 1.8, 1.8.1, and 1.8.2–1.8.9. The separate `LivingEntity.dismountRider` collision search is vehicle dismount behavior and is not entered by the bounded unmounted setup. `World.getBlockCollisions` and `Entity.pushAwayFrom` also have source-slice changes before 1.8.2, but the latter call sites are item and experience-orb code, and that query is not reached by this no-tick witness. The accepted path goes directly from the authorized command write to the immediate movement request, without an intervening entity tick. This confirms the verdict's no-push-out applicability for the bounded witness; it does not assert natural locomotion reachability.

## Patch-by-patch capability

| Exact source patch | Witness capability and source boundary |
|---|---|
| 1.8 | Five boxes, strict Z clip, collision enumeration, command write, and width/shape helpers support the bounded witness. `Entity.move` has the original `-stepHeight` descent expression; the roof and tie fallback preserve +0.075 Z. |
| 1.8.1 | Same witness geometry, strict clip, and command write. `Entity.move` changes the step candidate's descent values, but the roof-constrained tie still returns the ordinary clip. `World` also begins reusing mutable block positions; query coverage of the cauldron is unchanged for this setup. |
| 1.8.2 | Same witness and outcome. Step descent becomes candidate-specific for both alternatives. The seven inspected source files' relevant slices remain unchanged through 1.8.9, aside from no further method-source changes to the listed slices. |
| 1.8.3 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.4 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.5 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.6 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.7 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.8 | Same as 1.8.2; no changed reviewed slice. |
| 1.8.9 | Same as 1.8.2; this is the exact source revision bound by the independent verdict. |

## Method-slice rehash and true gaps

Method-slice hashes below were recomputed from exact source text. Different hashes can reflect Feather local-name/type rendering; the comparisons above distinguish those from behavioral edits.

| Method slice | Exact patch groups by computed SHA-256 |
|---|---|
| `CauldronBlock.addCollisions` | 1.8–1.8.1 `85bf46b642f57e017e058836db3015d3fbe2ef0e93f3482e3ce863c331a0747c`; 1.8.2–1.8.9 `aa9596596e84ce89f57603239454e05c3472d18df1cdeffed64abccb6aa56bb0` |
| `Box.intersectZ` | 1.8–1.8.1 `5671b7d3c2f81704a356507d84000cd81bda34326a4042203eabea20117b3a30`; 1.8.2–1.8.9 `41fb16134cc426942a116930c1b599b5439853ff58968a102c1d07efb98c37e7` |
| `Entity.move` | 1.8 `7ac929d0da05744094b53e0a6f92a5c0ce5545547036684c74dac2007a6eb94a`; 1.8.1 `884c757b7807d042a86b57eb5938442abb880ade105dcac1d6d55d3f28cb80a8`; 1.8.2–1.8.9 `31760c0cdec903263c45fabe12483f9c7507c7f7e5104a01930f23bcf66f5a1c` |
| `World.getCollisions(Entity, Box)` | 1.8 `d3197f4bed4410401ecfdf4ef210fe0464e5e8e98337b287ae0728034e3298da`; 1.8.1 `4687d55f5813c5bc66108db977e672cb9f6f65f6ca02d040b7dae4fb26ace67f`; 1.8.2–1.8.9 `8ae43bedf64e6e4210a136fb79f24d22faf40361b274ac5ddf673118ba11e933` |
| `World.setBlockState(pos,state,flags)` | 1.8–1.8.1 `bd1a9d41ab69ab74e805439e8dedc17ced407694bc82a5a41fb41ad174d79f76`; 1.8.2–1.8.9 `36282f281a98e87374f08f935da981de539ff3e6038b2cdeca639fb8730f9d66` |
| `SetBlockCommand.run` | 1.8–1.8.1 `337558416408cbac900cbe1226db6388e4c0a24f5142864ff36ad6c431b5af83`; 1.8.2–1.8.9 `258537a9b93173fa5d6d512cc0ea5866619ac570b40f6266180fb067831d5415` |
| `Entity.setPosition` | 1.8–1.8.1 `321d37e721b03bf2551136db5b22d8456dc4232f0681362e252c766877bbb300`; 1.8.2–1.8.9 `df95e97625af10b8030bb0cf82c486bacb4f31a015c6e78cddeac82e7d26e116` |
| `Entity.setPositionFromShape` | 1.8–1.8.9 `5d43f59f18b55227ee4f4dc8801e0f0e63ed65274da9daeb4c9a4cd0e308e134` |
| `Entity.pushAwayFrom` | 1.8–1.8.1 `f47b52e1ba276b53c07fc8504a989cb15a024d87ea23fe3d06cdd38f46d49a76`; 1.8.2–1.8.9 `f64d3d31e7cc90be0b828ed48fd75c5a2d0fa951e24b18ec0b5c01b097cf1f24` |
| `World.getBlockCollisions(Box)` | 1.8 `5ecb0d49eae115186120649e9189d45189f237020e25d52fa554762c4104bdaf`; 1.8.1 `ff72878cd2242139478bfef7cfbd264bcd1487615586c10e485b115d7ef26b21`; 1.8.2–1.8.9 `74165df2c425b61747d48df4497ccddc5c0d257d5bd67fa9290df1d300fe06fb` |
| `Entity.setShape` | 1.8–1.8.9 `3f6f08462e62faf124a2fe31d89d13854c207e9293be4d497084e1549a092284` |

Genuine limits: this does not inspect intermediate release boundaries within the broad 1.8.1→1.8.2 interval beyond the exact supplied patch trees; does not prove natural player entry into the micro-overlap; does not cover an intervening movement tick, vehicle dismount, outside-border/unloaded-chunk conditions, other cauldron states, or other movement inputs; and does not establish runtime parity. The accepted conclusion is limited to the exact stationary command-created setup and immediate request.


