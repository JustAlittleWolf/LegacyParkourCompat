# MOVE-002: Beds reverse a player's downward velocity on landing

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: 4.1, 5.1 / bed landing callback
- Classification: added mechanic
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A bed class: `decompiled_minecraft/1.11.2/ornithe-feather/net/minecraft/block/BedBlock.java`, SHA-256 `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1`. It defines the 0.5625-high bed shape at line 29 and `getShape()` at 134; there is no `beforeCollision` override. Searching the source tree shows only `Block.beforeCollision` and `SlimeBlock.beforeCollision` overrides on A.
- B bed class: `decompiled_minecraft/1.12.2/ornithe-feather/net/minecraft/block/BedBlock.java`, `net.minecraft.block.BedBlock#beforeCollision(World,Entity)`, lines 141-149; SHA-256 `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369`. If sneaking, it calls the base no-op. Otherwise, when `entity.velocityY < 0.0`, it writes `entity.velocityY = -entity.velocityY * 0.66F`; non-living entities then receive an additional `*= 0.8`.
- A/B base block files: `net/minecraft/block/Block.java`, SHA-256 `ab873436b24487ab0ee8055bf7a478349c7f7978398aea8e387796a6c820996a` / `e4a90eca411e7b0e14f5018f7385ec29891f6cdf1a1e917fa648b5712f9b33a1`. The inherited `beforeCollision(World,Entity)` is empty on both sides.
- A/B movement caller: `decompiled_minecraft/<version>/ornithe-feather/net/minecraft/entity/Entity.java`, `Entity.move(MoverType,double,double,double)`, at the support/collision callback around A line 724 and B line 727; SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` / `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`. When vertical movement is clipped (`o != y`), the resolved block's `beforeCollision(world,this)` callback runs.
- Bytecode cross-check for B: `javap -classpath <B client-ornithe-feather.jar> -c -p net.minecraft.block.BedBlock` confirms the callback's sneaking branch, negative-velocity guard, `dneg`, and multiplication by the float constant `0.66F` (represented as double `0.6600000262260437` in the mapped bytecode). The A `BedBlock` method inventory has no `beforeCollision` override; the A base `Block` callback is empty. Both javap invocations emitted an internal AccessDenied diagnostic after printing bounded method output; the exact B callback body was present.

## Source-level difference

When a player's downward movement is clipped by a bed, 1.11.2 calls the inherited no-op callback. In 1.12.2, a non-sneaking bed callback reflects negative Y velocity and scales it by `0.66F`; the extra `0.8` scale is guarded by `!(entity instanceof LivingEntity)`, so it does not apply to a player. A sneaking player receives the inherited no-op. The bed's collision shape remains the same 0.5625-high box in the inspected sources.

## Reachability and dependencies

The player uses `Entity.move` during living travel and collision resolution. A landing that clips downward movement against a bed reaches the block callback. The change writes player `velocityY`, directly affecting the movement state for the next tick. The source does not establish a particular measured bounce height or trajectory.

## Consequence and uncertainty

Source-proven: on the stated non-sneaking landing condition, B changes negative player Y velocity into positive velocity at 0.66 magnitude; A does not. Predicted consequence: the player rebounds from beds in B. No trajectory was run. Bed block state remains vanilla and this catalog does not alter modern-only block applicability.

## Handoff

Independent landing-velocity mechanic. Do not infer its first release from these endpoints. Runtime validation and implementation decisions are deferred.
