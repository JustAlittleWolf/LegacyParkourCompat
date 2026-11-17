# Independent source audit: 1.20.2 → 1.20.4 launch-item direct impulses

Terminal: **COMPLETED**. Verdict: **confirmed, scoped no-difference** for the assigned Riptide and attached-firework player responses. This is not a full-pair freeze and does not establish intermediate patch boundaries or runtime parity.

## Immutable claim binding

Audited input: `c-queue-1202-1204-launch-item/report.md`, SHA-256 `8c2b4069dff3194af6038bf7b5de25efffcb2763f1b61da5288512c34247db2d` (verified against the supplied identity).

Canonical roots: `decompiled_minecraft/1.20.2/mojmap/` and `decompiled_minecraft/1.20.4/mojmap/`. Both ready markers say `ready`, match their declared version and `mojmap`, and point to source-manifest SHA-256 values `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` and `fd3c8668483e5ff208c847474f6cbd3952a909c4aa602ed98b24e3d11a9602a1`; those manifest hashes were verified from the files.

The full source files for `world/item/TridentItem.java`, `world/item/FireworkRocketItem.java`, and `world/entity/projectile/FireworkRocketEntity.java` have identical hashes across the releases: `e86934edeb81b85e65e02c4ae8b0ce76485c48e4cc7774ee80f5de3884713c57`, `4e9276f08a09a81ebdb3ee587f6ffb2561256e9cc2223536cb270b2ae7c408ba`, and `93b1aa2842c6aa83f3167745956726e85a128dc456c812d38ce3dd45145e347b` respectively. Both endpoint bodies were checked, not just their names or manifest entries.

## Riptide

In both versions `TridentItem.releaseUsing` accepts the release path after at least 10 use ticks and the enclosing gate is `riptideLevel <= 0 || player.isInWaterOrRain()`. The durability operation and ordinary thrown-trident creation are inside `!level.isClientSide`; the Riptide player response is after that side gate and guarded by `riptideLevel > 0`. The direction components are calculated as floats from yaw and pitch, normalized using the float square root, scaled by `3.0F * ((1.0F + level) / 4.0F)`, then passed in x/y/z order to `Player.push`. `Entity.push(double,double,double)` adds those values to current delta movement and marks `hasImpulse`. `startAutoSpinAttack(20)` follows; it stores local spin ticks and gates only shoulder-entity removal / living flag 4 on the logical server. If `onGround()` then holds, `move(MoverType.SELF, new Vec3(0.0, 1.1999999F, 0.0))` follows. The local multiplayer release caller sends `RELEASE_USE_ITEM` and calls local `Player.releaseUsingItem`; the server packet handler handles the same action by calling the server player's method. Thus the launch impulse itself is directly applied on the responding actor and is not guarded by the server-only thrown-trident producer. No player pose write is in this response.

The Riptide gate, float math, operation order, authority split, additive helper behavior, spin call, grounded predicate and lift operand match at both endpoints. No difference found.

## Attached firework

In both versions `FireworkRocketItem.use` requires `player.isFallFlying()`, and only the logical server creates the attached rocket and consumes the item. `FireworkRocketEntity(Level, ItemStack, LivingEntity)` stores the target entity ID in synchronized data and retains the local target. In `tick`, if needed, the rocket resolves that ID through its own level's entity table and accepts a `LivingEntity`. For a resolved fall-flying target it reads look vector then current delta movement and writes `old + (look * 0.1 + (look * 1.5 - old) * 0.5)` component-wise; constants are doubles, and there is no clamp in this branch. There is no side gate around this target velocity write. Therefore client and server rocket ticks can each apply the response to their respective resolved actors; the server-side tick is authoritative for server movement. The rocket then copies the target's resulting movement. This is a direct player response when the item-created target is the player, distinct from ordinary unattached rocket acceleration.

The item-use gate and producer authority, target ID synchronization/resolution, fall-flying check, operand/read order, constants and direct write match at both endpoints. No difference found.

## Coverage boundary

A paired bounded search of each release's `world/item` Java sources for direct `push`, `setDeltaMovement`, `jumpFromGround`, `startAutoSpinAttack` and `move(MoverType, ...)` writers found only the Trident item direct response; the firework response is in its entity tick. No additional era-existing launch-item direct player impulse path was identified in that search. The report's slice is confirmed.

Not audited: full movement inventory for the pair, exact intermediate patch releases, runtime parity, damage or bed-entry eligibility, gliding/orientation algorithm, rendering, non-player physics, generic projectile hit/knockback, explosion/damage response, modern-only items, and general motion-packet behavior. No code, tests, source generation, product implementation, wiki inventory, Git history, or game/server launch was used.