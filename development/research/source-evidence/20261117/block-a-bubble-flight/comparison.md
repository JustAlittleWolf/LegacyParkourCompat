# Bubble callback comparison evidence

## Scope and verdict

Independently checked LocalPlayer-reachable bubble-column callback behavior for 1.21.1 -> 1.21.2, and sampled source snapshots 1.13, 1.13.1, 1.13.2, 1.14.4, 1.15.2, 1.16.5, 1.19.4, 1.20.6, 1.21.1 plus 26.2 control. The changed eligibility is established only for the adjacent 1.21.1 -> 1.21.2 comparison. Earlier snapshots are sampled no-difference checks for player-specific callback eligibility; they do not establish an introduction boundary for any uninspected release. 26.2 is a control sample only.

The authorized prior-audit file matched its assigned SHA-256 `ff61816eb35f6523bc3da22fee743789ee944bb369d0bac213c7ac9b8067bbd3`. I discarded its rejected inside-column claim and verified both callback guards independently against the exact 1.21.2 source.

## Reproduction notes

- `LocalPlayer` extends `AbstractClientPlayer`, which extends `Player` in both 1.21.1 and 1.21.2. LocalPlayer therefore uses the `Player` callback implementations.
- In 1.21.1 `Player.java`, neither bubble callback is overridden. `Entity.onAboveBubbleCol` and `Entity.onInsideBubbleColumn` write Y movement using the historical formulas; the inside method then calls `resetFallDistance()`.
- In 1.21.2 `Player.java:355-366`, both callbacks guard the `super` call with `!this.getAbilities().flying`. Active flying therefore suppresses both Y writes. Since the inside reset is inside the inherited method, it is suppressed too.
- `BubbleColumnBlock.entityInside` checks whether the block above is air and calls `onAboveBubbleCol` if so, otherwise `onInsideBubbleColumn`. That dispatch is unchanged across 1.21.1 and 1.21.2.
- The 1.21.2 local-client path remains eligible: `LivingEntity` calls `applyEffectsFromBlocks()` when the level is server-side or the entity is controlled locally. `Entity.checkInsideBlocks` obtains each block's entity-inside shape and invokes `entityInside` when the shape is `Shapes.block()` or the movement intersects a non-default shape. `BlockBehaviour` returns `Shapes.block()` by default; BubbleColumnBlock does not replace this shape.
- `Player.isPushedByFluid()` is a separate predicate returning `!abilities.flying`. BubbleColumnBlock invokes the callbacks directly, so that predicate does not gate this path. The callback condition is active `flying`; `mayfly` and flight-toggle/conversion behavior are outside scope.
- 26.2 still contains Player guards for both callbacks, but its above callback signature/dispatch has an added `BlockPos` argument and static helper route. This control does not extend the exact 1.21.1 -> 1.21.2 conclusion.

## Sampled source checks

For each listed sample before 1.21.2, inspected the bubble block dispatch, entity callback implementation, and player class; no player-specific bubble callback override was present. 1.13 snapshots use `onBubbleColumnSurfaceCollision` / `onBubbleColumnCollision` in Entity and call them directly from BubbleColumnBlock. The 1.14.4 through 1.21.1 snapshots call Entity's `onAboveBubbleCol` / `onInsideBubbleColumn` directly. No claim is made that the historical callback math is identical across these samples.

| Version | Mapping | Evidence source paths relative to project | SHA-256 of evidence files (in table order) |
| --- | --- | --- | --- |
| 1.13 | Ornithe Feather | `.../Entity.java`; `.../BubbleColumnBlock.java`; `.../PlayerEntity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`; `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e`; `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13.1 | Ornithe Feather | `.../Entity.java`; `.../BubbleColumnBlock.java`; `.../PlayerEntity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e`; `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` |
| 1.13.2 | Ornithe Feather | `.../Entity.java`; `.../BubbleColumnBlock.java`; `.../PlayerEntity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`; `0320e1b70b215dfff209f8e5003b97cac3a247fd3d642597f2db8ab46d251b2e`; `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` |
| 1.14.4 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java` | `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`; `e9ce5eb18c5581ffe2b610b273ffd85786587a00ba853e5dba0ac96593622b12`; `2cab08014222e6e3173986078b3fc9b64348384b978c868e583cd2caa30c93be2` |
| 1.15.2 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aac676f5f97c0a10e`; `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793`; `0f4b28ef76195b31cec9e45e3e3dd94fca26d1e851ce3c0a72748ea607a05879` |
| 1.16.5 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; `49e6ddc09f2d99056b0940ff0178c02bd0fe62d7758936eef751a3b9500f2ba5` |
| 1.19.4 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b`; `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2`; `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452` |
| 1.20.6 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java` | `71cd6b9f6c002684154dce11d3745e8714d82f13c8e1b3aa56743930131c18f3`; `785d93ccc94e1f912e545b2b0c355edeeb352b44ee8e83a69364daec35266dbe2`; `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c` |
| 1.21.1 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java`; `.../world/level/block/state/BlockBehaviour.java` | `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`; `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71`; `39c4da51460f4c2c06927f15de65a9ee2ae2f8bee0a2c9289cea375ad5d53a7c`; `35420ee4fca120ccf1d1542a6b1746fcdd1e9dafd125cf0b8193f9b8b406b292` |
| 1.21.2 | Mojmap | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java`; `.../world/level/block/state/BlockBehaviour.java` | `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`; `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de`; `9427b3e617753f5b3adc7a724fd80c87d4aa0e2f5d0eb38cd2ca34092e449b47`; `4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133` |
| 26.2 control | Unobfuscated | `.../world/entity/Entity.java`; `.../world/entity/player/Player.java`; `.../world/level/block/BubbleColumnBlock.java`; `.../world/level/block/state/BlockBehaviour.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`; `ece83c008e6f8391b468dafe445c288630215fdff490defee50cb7766771e039`; `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |

The ellipses in the table are path prefixes only: all paths are rooted at `decompiled_minecraft/<version>/<mapping>/`; the suffixes identify exact files. 
## Local-player reachability file digests

The lineage and dispatch-eligibility files consulted for the adjacent pair are also pinned here:

| Version | Relative source file | SHA-256 |
| --- | --- | --- |
| 1.21.1 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583` |
| 1.21.1 | `mojmap/net/minecraft/client/player/AbstractClientPlayer.java` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` |
| 1.21.1 | `mojmap/net/minecraft/world/entity/LivingEntity.java` | `324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8` |
| 1.21.2 | `mojmap/net/minecraft/client/player/LocalPlayer.java` | `fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0` |
| 1.21.2 | `mojmap/net/minecraft/client/player/AbstractClientPlayer.java` | `fa15cd69238b46b410bc709c8ae0a629d5f3f817a6bc454e70a1d60bf7aa785c` |
| 1.21.2 | `mojmap/net/minecraft/world/entity/LivingEntity.java` | `087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52` |

Each path is rooted at `decompiled_minecraft/<version>/`; its mapping directory is `mojmap`.