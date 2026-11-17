# Entity body-water sampler — corrected revision r1

This revision supersedes the analysis in `comparison.md` (SHA-256 `04c133996126a9248aca3c08c4f2a81498ff770574c32c9768bca66cd7444883`) and the draft in `findings/1.12.2--1.13/body-water-contact-and-immersion-height.md` (SHA-256 `4f56d17a688adb7ac8eb5d591d9f8f12a468935a6613e41b0e88fc40aedcc06a`). Those original files remain intact and recoverable. Corrective input independently authorized by the coordinator: `lane-water-body-audit/verdict.md`, SHA-256 `93043a9316dab0cfd3cb79d313af61aa7d93e1bf7f3a4875e62b29884a50c9b3`.

## Scope and source identity

This source-only comparison covers the entity body-water contact boolean and sampled immersion height through the ordinary local-player tick and movement consumers. Exact sources inspected: 1.12.2, 1.13, 1.13.1, 1.13.2, plus 1.14.4 as a later control. Mappings: Ornithe Feather for 1.12.2 through 1.13.2; Mojang mappings (`mojmap`) for 1.14.4. These are the pinned canonical `decompiled_minecraft/` outputs. Per-file hashes below were checked against the per-version `*.sources.sha256` entries; per-version ready-marker source-manifest SHA-256 is also listed.

| Release | Mapping identity | `Entity.java` SHA-256 | `LocalClientPlayerEntity.java` / `LocalPlayer.java` SHA-256 | ready source-manifest SHA-256 |
|---|---|---|---|---|
| 1.12.2 | Feather Gen2 1.12.2 build.2 | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` | `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` |
| 1.13 | Feather Gen2 1.13 build.2 | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 1.13.1 | Feather Gen2 1.13.1 build.2 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` | `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba` |
| 1.13.2 | Feather Gen2 1.13.2 build.2 | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29d8d9bf42bf` | `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211` |
| 1.14.4 | `mojmap` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` | `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b` |

The 1.12.2 entity and world helper hashes additionally verified for geometry/material details: `Entity.java` above; `World.java` `e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594`; `Box.java` `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a`; `ChestBlock.java` `cc63486b970a8a7670910b9d4e24c8de4b616dc53c9ce2f01260cfad53a5e178`; `Block.java` `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`. 1.13 fluid-helper hashes: `Entity.java` above; `FluidState.java` `05e2df9377a3bc1e91645ce4282bcaa4edebe93d7ef1907407961baaecff23a6`; `FlowingFluid.java` `da81f379a77ef5da26e87503a1f605df4efbf5d5c02e6b6f68a916dc327df5dd`; `WaterFluid.java` `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b`.

## Changed body-state producer

In 1.12.2, `Entity.tick` calls `checkWaterCollisions` (`net/minecraft/entity/Entity.java:382, 948-964`). Outside a boat, it passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` to `World.applyLiquidDrag` and uses its boolean to maintain `inWater`, reset fall distance, extinguish, and possibly splash. A boat mount explicitly clears `inWater`.

The transform’s sign matters. `Box.grown(dx,dy,dz)` returns `minY-dy` and `maxY+dy` (`net/minecraft/util/math/Box.java:132-139`); `contract(amount)` delegates to `expand(-amount)`, and `expand` delegates to `grown` (`Box.java:144-145, 279-281`). Thus `dy=-0.4F` **shrinks** the box vertically: before contraction, `minY` rises by 0.4 and `maxY` falls by 0.4. Contraction narrows it another 0.001 at each face. This removes a strip from both the bottom and top; it is not a downward extension.

`World.applyLiquidDrag` floors sampled minima, ceils maxima, and iterates half-open integer ranges (`net/minecraft/world/World.java:1579-1598`). For each cell whose block material equals `WATER`, it accepts when `ceil(bounds.maxY) >= cellY + 1 - LiquidBlock.getHeightLoss(level)` (`World.java:1599-1605`). It does not compare the fluid surface with `bounds.minY`, but cells below the narrowed box are never visited. If accepted, the same method gathers material drag and may apply its separate current impulse (`World.java:1604-1617`).

In 1.13, `Entity.tick` invokes its water-state updater (`net/minecraft/entity/Entity.java:393-394`); `checkWaterState` retains boat suppression and state writes but delegates to `m_69693160(FluidTags.WATER)` (`Entity.java:960-976`). The sampler contracts the unshrunk entity box by `0.001`, floors minima and ceils maxima, scans half-open ranges, and accepts only tagged fluid whose surface `cellY + fluidState.getHeight()` is at or above the contracted `minY`. It records maximum depth from that lower face in `f_85121000` (`Entity.java:2507-2571`). 1.13.1 and 1.13.2 retain the same sampler body; their mapped `Entity.java` source hashes match each other. 1.14.4’s control has the same unshrunk-then-deflated volume test, surface comparison and maximum-height write (`Entity.updateInWaterState` and `checkAndHandleWater`, `world/entity/Entity.java:974-989, 2588-2650`).

The first changed sampled release is 1.13. In the specified adjacent endpoint comparison, the transition is therefore 1.12.2 -> 1.13; no intermediate release claim is made beyond those exact sources.

## Independently recomputed reachable standing witness

The following source-derived setup reverses the earlier invalid witness and is geometrically feasible. It is not a runtime measurement.

- Put one ordinary chest at block `(0,0,0)`, facing north with no adjacent chest. Its default shape is `[0.0625,0,0.0625]` to `[0.9375,0.875,0.9375]` (`1.12.2 net/minecraft/block/ChestBlock.java:31-37, 67-78`). `Block.getCollisionShape` delegates to the block state shape (`Block.java:385-389`), so its top collision face is `y=0.875`.
- Use an unmounted player at position `(x,y,z)=(0.8,0.875,0.5)`. `Entity` defaults to width `0.6F` and height `1.8F` (`entity/Entity.java:178-179`), and `setPosition` builds an AABB centered on x/z with its lower face at y (`Entity.java:298-305`). The resulting body AABB is x `[0.5,1.1]`, y `[0.875,2.675]`, z `[0.2,0.8]`. It overlaps the chest top support shape in x `[0.5,0.9375]` and z `[0.2,0.8]`, leaving a reachable standing pose on that support.
- Place a water source at `(1,0,0)`, contain it with a solid floor at `y=-1` and solid east/north/south neighbors, and ensure the sampled area/chunk is loaded. The body box overlaps this water cell horizontally: x to `1.1` and z `[0.2,0.8]`. The source is ordinary water material in 1.12.2 and a water-tagged fluid state in 1.13.

Exact sampled vertical bounds and loop rows:

| Query | Sampled y interval | Half-open y cells | Candidate water cell `(1,0,0)` |
|---|---|---|---|
| 1.12.2: grow `dy=-0.4`, then contract `0.001` | `[1.276, 2.274]` | `1 <= y < 3` | Excluded; y=0 is never inspected, so old `inWater` remains false for this sole water cell. |
| 1.13: contract original box `0.001` | `[0.876, 2.674]` | `0 <= y < 3` | Inspected. |

The 1.13 source-water height calculation is source-backed: `FluidState.getHeight` delegates to its fluid (`fluid/state/FluidState.java:29-31`); `FlowingFluid.getHeight` returns `state.getLevel()/9.0F` (`fluid/FlowingFluid.java:373-376`); `WaterFluid.Source.getLevel` returns `8` (`fluid/WaterFluid.java:136-145`). For the candidate cell, `q=0`, so its fluid surface is `8/9 ≈ 0.8888889`, which is above `box.minY=0.876`. Therefore 1.13 body contact is true and stored depth is approximately `0.0128889`; the old sampler omits the cell and returns false. The older material predicate’s upper-surface check would pass if visited (`ceil(2.274) >= 0 + 1 - 1/9`), but it is not visited.

This is a reachable standing **body-contact** difference: the unmounted local player’s `Entity.tick` reaches the source producer when its chunk is loaded (`LocalClientPlayerEntity.tick`, 1.12.2:165-179; 1.13:179-197). `LivingEntity.moveRelative` reads `isInWater()` for locally controlled movement (1.12.2:1425-1428; 1.13:1459-1468), so the predicate switches from land to water movement. In 1.13, the stored immersion depth also feeds the water-jump/ground-jump selection in `LivingEntity.tick` (1.13:1884-1895; 1.13.2:1904-1914). These are source-derived consequences, not measured trajectories.

For the standard standing player in this pose, the eye is far above the source surface; the eye-position submersion query is false. The 1.13 swimming flag is recomputed separately after body and underwater state, and entry requires sprinting plus its underwater predicate and no mount; body contact alone does not make this player swim. The body predicate, eye query, and swimming flag must not be conflated.

## Current impulse is separate

The old `World.applyLiquidDrag` collects each accepted block’s material-drag vector and only adds current if the aggregate is nonzero and `entity.hasLiquidCollision()`; it normalizes before adding `0.014` times the vector (`World.java:1604-1617`). The 1.13 sampler separately captures `hasLiquidCollision()` as `bl`, adds fluid flow vectors for accepted samples, scales an added vector when maximum depth is `<0.4`, averages by sample count, normalizes only nonplayers, and adds `0.014` times the vector (`Entity.java:2518-2565`). These current-flow gates/math are not the body-contact boolean or the immersion-height output. The witness claim is limited to the boolean/depth result; it does not assert a current-impulse delta.

## Authority and remaining evidence limits

The base Entity sampler and state writes do not have a logical-side guard. This is a common entity state producer, not an exclusively client-authoritative operation. The reachable movement witness follows the local client player into locally controlled movement; it does not support running historical movement on a logical server. Mounted players are excluded from the witness and boat suppression is explicit on both compared paths.

No tests, game launch, build, network lookup, or runtime measurement was used. The water-cell arrangement, source math, bounds, and tag/material checks are supported by decompiled source; actual in-game persistence of the contained source and resulting tick trajectory remain runtime-unverified. Independent review must bind a fresh verdict to this corrected revision’s bytes.