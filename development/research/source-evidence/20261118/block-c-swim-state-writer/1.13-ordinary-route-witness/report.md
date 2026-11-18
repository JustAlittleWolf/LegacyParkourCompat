# 1.13 Feather ordinary swimming route witness

## Scope and result

This read-only witness closes the physical-setup question for the accepted 1.13 `PlayerEntity.updateSwimming` state-writer difference. The setup uses only ordinary local-player input and vanilla source-water/stone cells. It demonstrates a source-legal route into a one-block-high water channel, then a normal local input change that clears sprint before the following tick's swimming update. It is a source-predicted reachability witness, not a gameplay run or measured trajectory.

## Exact source identity

Canonical reference: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.13\ornithe-feather` (Minecraft `1.13`, Ornithe Feather `net.ornithemc:feather-gen2:1.13+build.2`). The accepted paired source report and blind audit identify the canonical pair's source manifest SHA-256 as `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`, artifact manifest `a2c17075b601d9b83703f7ee188b04c6d9f68cac08d97b1a4f838fe8cd2e2c24`, and provenance JSON `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d`. The prior accepted report records that PlayerEntity, Entity, LivingEntity and LocalClientPlayerEntity match their source-manifest rows. Hashes below were recomputed from the read-only canonical files for this witness.

| File/member | Source evidence | SHA-256 |
|---|---|---|
| `entity/living/player/PlayerEntity.java` | `updateSwimming` 1464–1480; `updatePlayerPose` 333–357; `getEyeHeight` 1854–1865; `moveRelative` 1435–1460 | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| `entity/Entity.java` | `updateSwimming` 952–958; `isSubmergedIn` 1041–1049; sprint state 1781–1787 | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| `entity/living/LivingEntity.java` | tick/travel call ordering and `moveRelative` call at 1670+ and 1907 | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| `client/entity/living/player/LocalClientPlayerEntity.java` | `mobTick` input and sprint gates 649–754; local `tick` 179–191 | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |
| `block/Block.java` | stone and water registration 794–795, 845; cube/solid/shape/collision defaults 281–291, 404–410 | `e5be72b77a28e89595009289964bb031bbb187d15d5698570615e6fc9a21a476` |
| `block/LiquidBlock.java` | water default level and fluid-state / non-cube behavior 35–70 | `a2033da6ef3d2e1adc3b67c58b524949d458aa9992d8fad6c1c2596a01830626` |
| `block/material/Material.java` | water and stone material flags 27–34, 82 | `9bf4fc6893e07f15b7db2b03cba7cba7850370a2499870c621f422c356cf3b4f` |
| `fluid/WaterFluid.java`, `fluid/FlowingFluid.java` | source level/source flag 136–145; height 374–375 | `183894d0a4f7a85becd97eb2c0f6cedc1d5adc4648dc6d3d2b5624df173df28b`; `da81f379a77ef5da26e87503a1f605df4efbf5d5c02e6b6f68a916dc327df5dd` |
| `util/math/BlockPos.java`, `util/math/Vec3i.java` | entity-to-position constructor at 33; floor conversion at 20 | `ee71cfdedcaa27458f48168ab761b435cf9c878b04f21e971ddbafb6254aa3fd`; `481827f072a5d36ae3a7f133afed5396988e3ce7c0501b2777a92b60c6a498b8` |

## Concrete fixed configuration

Use block coordinates relative to any loaded, ordinary world area. Let the approach and channel run along +X, with the player centered at Z=0.5:

- Floor: `minecraft:stone` default state at `(x,63,0)` for `x=0..5`.
- Water: `minecraft:water[level=0]` source state at `(x,64,0)` for `x=0..5`; also at `(x,65,0)` for the open approach `x=0..1`.
- Roof: `minecraft:stone` default state at `(x,65,0)` for `x=2..5`; cells above the approach at `(0..1,65,0)` are water, not roof.
- The center line is open at its sides; the witness body stays within the cited cells. Stone is a full-cube collision shape by `Block.getShape`/`getCollisionShape` defaults, is a cube, has solid-blocking stone material, and is not a signal source. It therefore gives `isSolid()==true` and `material.blocksMovement()==true`. Water's block properties specify `Material.WATER` and `.noCollision()`; its default level is 0, whose fluid state is the water source (`getLevel()==8`, `isSource()==true`).

On the approach, let the local avatar be non-flying, non-spectating, unmounted, sprinting, and at `(x,y,z)=(1.5,64.2,0.5)` in source water. Before the swim bit is set its normal height is 1.8, so eye Y is `65.82`: this is inside the source block at `(1,65,0)`. `Entity.isSubmergedIn` samples the eye block and accepts this source because its strict height comparison is `d < blockY + (fluidHeight + 0.11111111F)`; source height is `8/9`, so the bound is 66.0. The body volume also intersects source water at y=64. With sprint true, the non-already-swimming inherited branch can set swimming true. At the end of this player tick, `PlayerEntity.updatePlayerPose` requests width/height `0.6F/0.6F` for a swimmer and applies that size when the resized box has no collisions. The approach has no roof and the water has no collision, so this shrink fits.

Move forward into the covered cells while remaining in the same water layer. At the witness position `(2.5,64.2,0.5)`, the swim-pose box is `[2.2,2.8] x [64.2,64.8] x [0.2,0.8]`: it is clear of the floor top at y=64 and stone-roof underside at y=65, with the entire body inside the water block at `(2,64,0)`. Its eye is `64.6`, in that source-water cell. This is a legal one-block-high passage for the source-defined swim pose. The pose was acquired in the open approach before crossing under the full-cube roof, so the normal 1.8-high body need not pass through the roof.

## Local input / tick staging and exact gate outcome

`LocalClientPlayerEntity.mobTick()` calls `input.tick()` and reads the resulting `movementForward`; while swimming, sprint is cleared when the player is airborne, not sneaking, and forward input drops below `0.8F` (the `bl6` condition). Thus, after entering the roofed channel, release forward input. The current tick's base-entity water/update phase has already run by the time local `mobTick` processes that input; it can clear sprint there. Keep the swim-pose avatar off the floor (`y=64.2`) and within the covered water row. On the next player tick's base phase, the stored swim bit is still true and sprint is now false.

At that next update, `new BlockPos(this)` floors `(2.5,64.2,0.5)` to `(2,64,0)`, and `.up()` is `(2,65,0)`. The lower state is water, so `shouldSuffocate(lower)` is true (`!lower.isSolid()`). The upper stone is solid, making `doesNotSuffocate(lower)` false. Java short-circuit evaluation therefore makes the outer `doesNotSuffocate(lower) && !upper.material.blocksMovement()` false before the material predicate is needed. The 1.13 override skips `super.updateSwimming()` and writes no swim bit; `isSwimming()` remains true for this non-flying, non-spectating player. The already-swimming inherited branch, if delegated in 1.13.1, evaluates `false && true && !false`, and writes swimming false.

After baseTick returns, this same `LivingEntity.tick()` reaches travel and dynamically calls `PlayerEntity.moveRelative`. Its first branch reads `isSwimming() && !isRiding()`. Choose a downward look so `g=getLookVector().y <= 0`; then, with other inputs held equal, 1.13 enters the swim vertical-response branch and 1.13.1 does not. Since `g<=0` is the first disjunct, this consumer path does not need to read its separate head-cell fluid predicate. This ties the concrete environment to the already accepted direct same-tick player movement consumer without expanding the claim to general fluid or collision behavior.

## Limit

The exact sources demonstrate a normal input path, collision-free dimensions, block positions, and predicate outcomes sufficient for this witness. They do not supply an executed local game trace; no runtime or trajectory-parity claim is made. Exact patch introduction within 1.13–1.13.1 and broader pair coverage remain outside this witness.
