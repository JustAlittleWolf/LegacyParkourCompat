# Static integration audit: recent mixin seams 44

## Scope and identity

Read-only review of the step-descent, eye-water, piston stair, red-bed water-current, and Jump Boost receiver mixin seams in product HEAD `a7b5a4c1008101616c96f1efa7e11cd81e617833`. Compared source annotations and resource registration with `javap -p -s -c` output from the compiled merged deobfuscated Minecraft 26.2 jar:

`C:\Users\Wolfi\.gradle\caches\fabric-loom\minecraftMaven\net\minecraft\minecraft-merged-deobf\26.2\minecraft-merged-deobf-26.2.jar`

Jar SHA-256: `0468742d98fe5ec68508493ef2155c6e5209f991055bc0c17ddf26872eb0d110`.

## Result

**No confirmed startup-risk error found in the assigned seams.** Exact target owners/descriptors and callback parameter shapes matched the compiled 26.2 class files. The mixins are present once in the client-only config, and those targets are reachable in the specified bytecode. This is static inspection only; Mixin application and runtime behavior were not executed.

## Evidence

- **Step descent:** `EntityStepUpCollisionMixin` wraps `Entity.collide(Vec3):Vec3` with the explicit `(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;` descriptor. The jar contains that private instance method and descriptor. The mixin is listed once in `legacyparkourcompat.client.mixins.json`.
- **Eye-water:** `PlayerMixin` redirects `Player.isEyeInFluid(TagKey):boolean` inside `Player.updateIsUnderwater():boolean`. The compiled method has that exact invokevirtual owner and descriptor. The callback's `(Player, TagKey<Fluid>)boolean` shape agrees with the invocation. The client config lists `PlayerMixin` once.
- **Piston stair and callback origin:** the jar contains `PistonMovingBlockEntity.moveCollidedEntities(Level, BlockPos, float, PistonMovingBlockEntity):void`, `moveEntityByPiston(Direction, Entity, double, Direction):void`, and the targeted `Entity.getPistonPushReaction():PushReaction` call. In compiled `moveCollidedEntities`, the entity-list iterator is ordinal 0 and the shape-AABB iterator is ordinal 1. The accumulating double is local slot 14 (`DSTORE 14` at bytecode offset 266); the second iterator invocation occurs at offsets 268–270, after initialization and before the delta gate at offsets 365–375. Thus the `@ModifyVariable(index = 14, ordinal = 1)` seam is attached to an existing local and invocation. The wrapped `moveEntityByPiston` invocation is present at offset 396. The `applyEffectsFromBlocks(Vec3, Vec3):void` invocation targeted by the callback-origin argument modifier is present in `moveEntityByPiston`. `PistonMovingBlockEntityMixin` and its accessor are each listed once in the client config.
- **Water beside bed:** `FlowingFluid.isSolidFace(BlockGetter, BlockPos, Direction):boolean` invokes `BlockState.isFaceSturdy(BlockGetter, BlockPos, Direction):boolean`, matching `FlowingFluidMixin`'s wrap target and handler arguments. `FlowingFluid.getFlow` has two `isSolidFace` calls, side then above, so the wrapper is reachable for either native predicate call. `fluid.FlowingFluidMixin` is listed once in the client config; its local-player context preserves the native supplier path when no eligible profile context exists.
- **Jump Boost receiver:** `ClientPacketListener.handleUpdateMobEffect(ClientboundUpdateMobEffectPacket):void` and `handleRemoveMobEffect(ClientboundRemoveMobEffectPacket):void` match the two packet callback signatures. The compiled update handler calls `getEntityId`, `getEffect`, `getEffectDurationTicks`, and `getEffectAmplifier`; the removal handler calls `getEntity(Level)` and `effect()`. Both `@Inject` targets resolve to those existing methods. The client config lists this receiver once. `LocalPlayerJumpBoostMixin`'s `LocalPlayer.hasEffect(Holder):boolean` redirect matches the compiled auto-jump gate owner and descriptor; its amplifier redirect matches `MobEffectInstance.getAmplifier():int`. The living-entity tick/power injections target existing `tickEffects()` and `getJumpBoostPower():float` methods.

`legacyparkourcompat.client.mixins.json` is registered once under the `environment: client` mixin entry in `fabric.mod.json`; none of these client mixins is duplicated in the common config.

## Limits

No product files were changed. No Gradle task, tests, Minecraft launch, or Mixin application was run. This review does not claim movement parity.
