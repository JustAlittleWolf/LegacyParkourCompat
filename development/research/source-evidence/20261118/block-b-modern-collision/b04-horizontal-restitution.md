# B04 — horizontal solid-collision restitution

Status: COMPLETED (source-only bounded finding; no implementation, tests, gameplay, or pair-freeze claim).

## Immutable inputs

Canonical exact-release source trees are `decompiled_minecraft/{26.1,26.1.1,26.1.2,26.2}/unobfuscated`. SHA-256 of each `unobfuscated.sources.sha256` release-root manifest, in order: `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`, `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`, `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`, `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` (all matched the supplied pins).

The approved cutover-control artifact `build/source-lane-262-collision-restitution/26.1.2--26.2/collision-restitution.md` matched SHA-256 `22da61a0ec3f02d1e9759788480cd5e47127540961b5f5d55b669dc4e2a8036d`. It was consulted only for the specifically authorized 26.2 witness and does not establish intermediate history.

Every source file listed below matched its entry in that release's `unobfuscated.sources.sha256`; the three pre-control Entity.java files are byte-identical. Member/source file SHA-256 identities:

| Release(s) | Source file | SHA-256 |
|---|---|---|
| 26.1, 26.1.1, 26.1.2 | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1, 26.1.1, 26.1.2 | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1, 26.1.1, 26.1.2 | `net/minecraft/world/level/block/Block.java` | `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977` |
| 26.2 | `net/minecraft/world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 | `net/minecraft/world/entity/player/Player.java` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| 26.2 | `net/minecraft/world/entity/LivingEntity.java` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/Attributes.java` | `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/AttributeInstance.java` | `c7cbe38bfdc1b4aafcf623bd1b604200cc6190641091369ee9bb9303e8f07ad5` |
| 26.2 | `net/minecraft/world/entity/ai/attributes/AttributeMap.java` | `675b87b1757a73c6354db2dcfe52ec65c903bd9b3f5fcb97a648be252ea3f7f6` |
| 26.2 | `net/minecraft/server/commands/AttributeCommand.java` | `3234f11758052d5d10cabb8ceb1454d39bde8c53b3205914538e77759ca31e7c` |
| 26.2 | `net/minecraft/world/level/block/Block.java` | `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab` |

## Finding and exact pair limits

- **26.1 → 26.1.1: accepted, no change in assigned operation.** `Entity.move` (`Entity.java:704-799`) derives X/Z clipping from `!Mth.equal(delta.component, movement.component)`, defines horizontal collision as either axis clipped, then writes each clipped velocity component to `0.0` while preserving the other components (`:776-779`). The files and full method body are identical by source-file SHA-256 across these releases.
- **26.1.1 → 26.1.2: accepted, no change in assigned operation.** Same evidence and same conclusion; the complete `Entity.java` file hash is identical. These two accepted adjacent patch edges bound only this operation, not other mechanics.
- **26.2 control: changed operation, not a patch-boundary verdict.** `Entity.move` (`Entity.java:711-800`) keeps the clipped-axis calculation and sets `horizontalCollision`, then calls `restituteMovementAfterCollisions` only when `canSimulateMovement()` and `(movedVertically && verticalCollision || horizontalCollision)` (`:784-785`). The helper (`:802-842`) takes the current entity bounciness unless bounce is suppressed and writes each collided X/Z component as `-currentMovement.component * restitution` (`:803-812`), followed by one `setDeltaMovement` write (`:842`). Thus with positive effective entity bounciness a clipped axis reverses and scales instead of being zeroed; with zero bounciness or suppressed bounce that component remains zero. It does not use block restitution or the suppresses-bounce block tag for the X/Z branch; those checks are in the vertical branch and excluded here.

The 26.1.2 player gate (`Player.java:1269-1271`) and 26.2 gate (`Player.java:1290-1292`) both return `!level.isClientSide() || isLocalPlayer()`. On a logical client, local player movement is simulated and nonlocal player movement is not. `Entity.move`'s `noPhysics` branch only changes position and clears collision flags, bypassing this ordinary solid-collision response. Clip response follows collision resolution and position update. It occurs after the fall-damage callback and before movement-emission processing and the existing block-speed-factor X/Z scaling; those other effects were not assessed.

## Effective BOUNCINESS route and nonzero witness

In 26.2, `Entity.getEntityBounciness()` defaults to `0.0` (`Entity.java:854-855`); `LivingEntity` overrides it to `getAttributeValue(Attributes.BOUNCINESS)` (`LivingEntity.java:2202-2203`). The LivingEntity attribute set includes `Attributes.BOUNCINESS` (`LivingEntity.java:355`), whose `RangedAttribute` default is `0.0` with declared range `[0.0, 1.0]` (`Attributes.java:31`). LivingEntity.getAttributeValue delegates through AttributeMap.getValue to the instance value (LivingEntity.java:2210-2212, AttributeMap.java:62-64, AttributeInstance.java:139-144); each AttributeInstance initializes its base from the attribute default (AttributeInstance.java:34), so the default player route is zero. The command route obtains the target living entity's attribute instance and sets its base (AttributeCommand.java:233-242,301-304). A legal nonzero source witness exists: `/attribute <target> minecraft:bounciness base set <value>` is registered behind the gamemaster permission requirement (`AttributeCommand.java:43-46`), accepts a double argument (`:73-82`), and calls `setBaseValue(value)` (`:301-304`). The 26.2 control draft confirms this command path. This establishes a reachable nonzero attribute route under its permission gate, without claiming that ordinary defaults bounce.

## Residuals / exclusions

No source from 26.1.3 through 26.2 was examined. The exact introduction point of the changed restitution logic is unknown within the broad 26.1.2→26.2 interval; the supplied control artifact does not close that gap. This is not full-pair coverage or independent audit. Landing callbacks, beds, slime/honey behavior, vertical restitution, other speed effects, step/collector math, and packaged defaults were not accepted or analyzed here. No packaged defaults were needed for the confirmed default route because the exact 26.2 `Attributes` constructor and `AttributeInstance` initialization establish its default directly.

