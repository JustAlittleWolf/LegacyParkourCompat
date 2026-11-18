# Powder snow frost alias comparison — revision 1

## Revision binding and scope

This is a revised exact-source comparison for the same bounded slice and release set as the initial artifact: `1.17`, `1.17.1`, `1.18`, `1.18.2`, `1.19`, `1.19.3`, `1.19.4`, `1.20`, and `1.20.2`, all in the canonical local Mojmap roots. It addresses the frost update in `LivingEntity.aiStep()`, modifier gate and math, state selection for the non-air check, direct local speed dispatch, and source-defined frozen metadata. It does not claim full movement or geometry parity and includes no runtime evidence.

The original raw files are retained unchanged. This revision binds them by SHA-256:

- `comparison.md`: `ae8388c786d8f78199566eab0bb5f9c3e2858ee7cbfc4db4a110674a6a93f9ef`
- `findings/1.19.4--1.20/powder-snow-frost-gate.md`: `08134c5b1c8efc185f598d01a46c71788b376c5b542eebd38fce31570a5df795`

The original comparison contains the nine exact-version official Mojang client and client-mapping pins and complete source-file digests for `LivingEntity.java`, `Entity.java`, and `LocalPlayer.java`. These identities remain the bound comparison basis. The following additional complete-file digests were recomputed and matched each release's published source manifest, or the mapping-root provenance source map for 1.20:

| Release | `Player.java` | `AbstractClientPlayer.java` | `CollisionGetter.java` |
|---|---|---|---|
| 1.19.4 | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf` | `ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c` |
| 1.20 | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` | `393b88eb4f41a84528f584311bb7aefc02adbf474c2744f3555acc0663c85dbf` | `8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130` |
| 1.20.2 | `25f263692fd6b2a737aa813315022a0caf4e18c3661ba3bd706c6297f909f25c` | `759f8f03b89dab1f6b65c73e1e25a68ae6a6083cb49b5d2719136edda7edf696` | `f21daa1ed0365cc16a2638373d702fbaeb4242ad12a80a41105cf43c711e6d95` |

For 1.19.4, `CollisionGetter.java` exists under the complete source manifest but has no `findSupportingBlock` implementation. The new support selection path is present in 1.20 and unchanged in 1.20.2.

## Frozen modifier update

The operation is in `LivingEntity.aiStep()`, after its travel work. Across all nine checked releases the freeze-count mutation is guarded by `!level.isClientSide && !isDeadOrDying()`: when `isInPowderSnow && canFreeze()`, it stores `min(getTicksRequiredToFreeze(), n + 1)`; otherwise it stores `max(0, n - 2)`. After that guarded block, both logical sides call `removeFrost()` and then `tryAddFrost()`.

`removeFrost()` gets `Attributes.MOVEMENT_SPEED`; if that attribute exists, it removes an existing modifier with UUID `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce`. `tryAddFrost()` checks, in order, that `getBlockStateOnLegacy().isAir()` is false, `getTicksFrozen() > 0`, and the movement-speed attribute exists. It computes `-0.05F * getPercentFrozen()` and adds a transient modifier with the same UUID, name `Powder snow slow`, and operation `AttributeModifier.Operation.ADDITION`. Modifier identity, operation order, amount expression and frozen-count gate are the same in the checked source files.

## Non-air state selection

In 1.19.4, `getBlockStateOnLegacy()` calls `getOnPosLegacy()`. The latter delegates to a calculation using `floor(position.x)`, `floor(position.y - 0.2F)`, and `floor(position.z)`. If the resulting state is air, it reads the cell below and returns that lower cell when its state is a fence, wall, or fence gate; otherwise it returns the original cell.

In 1.20 and 1.20.2, `getBlockStateOnLegacy()` still calls `getOnPosLegacy()`, but the method now consults `mainSupportingBlockPos` when present. That position is populated by `checkSupportingBlock()` via `CollisionGetter.findSupportingBlock()` from a thin box under the entity bounding box. When a support is found, `getOnPos(0.2F)` uses that support position's X/Z. For a non-fence/wall/gate support, it returns the support position with Y set to `Mth.floor(position.y - offset)`; for fence/wall/gate support, it returns the support position itself. If no main support is present, it uses the floor-X/Z and floor-Y path but does not perform the old below-cell fence/wall/gate fallback.

In both old and new code, `position.y` is double and the offset is `0.2F` (float). Java binary numeric promotion widens the float offset for the subtraction, so `Mth.floor(position.y - 0.2F)` floors a double result. The Y expression is preserved; the changed inputs are the support-derived X/Z when available and the removed below-cell fallback when support is empty.

The support selector in `CollisionGetter.findSupportingBlock()` iterates collision positions in the supplied AABB, selects the block position with the smallest squared distance from its center to `entity.position()`, and breaks equal distances with `BlockPos.compareTo`. `Entity.checkSupportingBlock()` first queries the box spanning the entity's footprint and `1.0E-6` below its minimum Y. If on-ground support is empty and known movement is available, it queries a horizontally moved copy of that box. When the entity is not on ground it clears a present main support. This makes an empty-support state source-reachable during airborne movement; the `removeFrost()` / `tryAddFrost()` calls are not guarded by `onGround`.

## Concrete equal-input gate case

For the same vanilla block states, player double position and positive frozen-tick input, consider the source condition where the cell at `(floor(x), floor(y - 0.2F), floor(z))` is air and the cell immediately below it is a fence, wall, or fence gate, while the main support is empty. The 1.19.4 calculation returns the below cell, so the non-air gate passes. The 1.20 and 1.20.2 empty-support branch returns the air cell without checking below, so the non-air gate fails. No block state production or alternate physics is assumed in this comparison. This establishes a changed source predicate for matching vanilla inputs; it does not establish that a particular full player geometry or trajectory reaches a selected coordinate arrangement.

The support-present branch is a second changed state-selection route: its X/Z comes from the selected collision support rather than current floored X/Z. No claim about which candidate wins in every geometry or about downstream trajectory is made beyond the literal `CollisionGetter` selection procedure above.

## Local player speed dispatch and metadata

`LocalPlayer.updateAutoJump()` performs an ordinary `this.getSpeed()` read. The class chain in all three inspected releases is `LocalPlayer extends AbstractClientPlayer extends Player`. `AbstractClientPlayer` does not override `getSpeed()`; `Player.getSpeed()` directly returns `(float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)`. The local read therefore resolves to the player movement-speed attribute. It happens during travel, before the post-travel frost modifier update, and does not itself reset or recreate the modifier. A subsequent ordinary speed read can observe a modifier installed by the preceding `tryAddFrost()` call.

The frozen counter is the `EntityDataAccessor<Integer> DATA_TICKS_FROZEN`, defined with `SynchedEntityData.defineId(Entity.class, EntityDataSerializers.INT)` and read by `getTicksFrozen()`. Client `LivingEntity.aiStep()` does not locally increment or decrement this counter under the server-only gate; its frost modifier calculation consumes the available frozen metadata value and the current block-contact state. No numeric network accessor ID is asserted.

## Exact anchors and profile boundary

- 1.19.4 `LivingEntity.aiStep()`: `1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java`, line 2454 onward; freeze branch and ordered calls at lines 2554–2565.
- 1.20 `LivingEntity.aiStep()`: same relative source path, line 2473 onward; frost calls at lines 2586–2587.
- 1.20.2 `LivingEntity.aiStep()`: same relative source path, line 2509 onward; frost calls at lines 2615–2616.
- 1.19.4 legacy position / getter: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, `getOnPosLegacy` and private `getOnPos(float)` at lines 720–742; `getBlockStateOnLegacy` at 1140–1142.
- 1.20 legacy position / support setup: `1.20/mojmap/net/minecraft/world/entity/Entity.java`, `checkSupportingBlock` lines 567–587 and `getOnPosLegacy` / `getOnPos(float)` lines 786–815; getter at 1244–1245.
- 1.20.2 equivalent: `1.20.2/mojmap/net/minecraft/world/entity/Entity.java`, `checkSupportingBlock` lines 569–589 and `getOnPosLegacy` / `getOnPos(float)` lines 788–817; getter at 1254–1255.
- 1.20 `CollisionGetter.findSupportingBlock`: `1.20/mojmap/net/minecraft/world/level/CollisionGetter.java`, lines 95–109; 1.20.2: corresponding lines 105–119.
- Local read: `LocalPlayer.updateAutoJump()` in each release's `client/player/LocalPlayer.java`; inherited direct getter: 1.19.4 `Player.getSpeed()` lines 1483–1485, 1.20 lines 1489–1491, 1.20.2 lines 1493–1495. Class declarations: `LocalPlayer` line 86 (1.19.4) / 88 (1.20 and 1.20.2); `AbstractClientPlayer extends Player` line 26 (1.19.4 and 1.20) / 18 (1.20.2).

Among the checked versions, the eligible source boundary is after 1.19.4 and present by 1.20; the support-aware and empty-support behavior remains in 1.20.2. These are explicit checked profile endpoints only. No intermediate version was examined to set an exact patch cutover, and no source claim is made about unlisted releases.
