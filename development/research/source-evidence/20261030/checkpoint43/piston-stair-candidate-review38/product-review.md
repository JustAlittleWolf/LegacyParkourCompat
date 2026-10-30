# Product integration review: piston stair push

**Verdict: REQUEST CHANGES**
**Integration:** `.native-integrations/piston-product40`, commit `0210138581ec2c89515817974962489f5dd2e15c`
**Base:** `405b19c3bf1bfdc135b083a419cae7d1e37e1d10`
**Primary issue:** the integration wraps only the existing `moveEntityByPiston` invocation and does not open the native nonpositive-delta gate. The accepted `+0.36 X` witness still makes no move.

## Required correction

In `src/client/java/me/wolfii/legacyparkourcompat/mixin/PistonMovingBlockEntityMixin.java:68-87`, `@WrapOperation` substitutes the distance only if native code has already invoked `moveEntityByPiston`. In the accepted bottom-straight stair witness, the 26.2 per-box swept AABBs do not intersect the player: the base box only face-touches at Y=0.5, while the upper step ends at Z=0.5 and the player begins at Z=0.65. `delta` remains zero, so native `moveCollidedEntities` takes its `delta <= 0` path and loops to the next entity without reaching this wrapper. It therefore cannot restore the old full-outline push.

Add a local-player/stair override before this gate which seeds a positive accumulator when the historical unit-outline response is positive, while preserving native behavior otherwise. The prior independently reviewed candidate seam is verified against compiled 26.2 bytecode: `delta` is local slot 14, initialized by `DSTORE 14` at offset 266; the second `List.iterator()` invocation is the `shapeAabbs` loop at offsets 268–270. A precise MixinExtras `@ModifyVariable` at that second iterator invocation with `index = 14` runs after initialization and before per-box clipping. A positive sentinel such as `Double.MIN_VALUE` opens only the native positive branch. The existing local capture can retain the final historical distance, and the wrapper at `moveEntityByPiston` can still replace the final distance there. A no-intersection historical result should leave delta at zero; modern stair boxes are subsets of the unit outline swept over the same progress interval, so no modern box can create a positive delta when the old union does not intersect. An equivalent seam is acceptable if it demonstrably opens the branch before the invocation and keeps the gate/no-push behavior exact.

The compiled `moveCollidedEntities` target descriptor is `(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLnet/minecraft/world/level/block/piston/PistonMovingBlockEntity;)V`. The inspected cached merged deobfuscated 26.2 JAR is `C:\Users\Wolfi\.gradle\caches\fabric-loom\minecraftMaven\net\minecraft\minecraft-merged-deobf\26.2\minecraft-merged-deobf-26.2.jar`, SHA-256 `0468742d98fe5ec68508493ef2155c6e5209f991055bc0c17ddf26872eb0d110`. Its bytecode contains the `delta <= 0` gate as `DLOAD 14`, `DCONST_0`, `DCMPG`, `IFGT` at offsets 365–375. The wrapped invocation is later, at offset 396. This review used the compiled class read-only; no build or launch was performed.

## Confirmed bounded integration behavior

- `CobblestoneStairPush.requestedDistance` reconstructs the old extending union-outline clearance from current and previous progress and returns `directional clearance + 0.01`. When its hook reaches the wrapper, that value is the correct final old request; native `Math.min` and `+0.01` already occurred before the wrapped call, so replacing only the final distance avoids double padding.
- The hook is keyed to `minecraft:cobblestone_stairs`, the semantic current analogue of historical cobblestone-backed `stone_stairs`. It rejects retraction. Its nonintersection result is empty, which preserves native behavior; given the subset relation, this does not create a ghost modern response.
- The response context uses a nested-safe `@WrapMethod` `try/finally` restore. The native push-reaction method is called exactly once and its returned value is reused. Context admits only non-`IGNORE` entities which are exactly the current client player and pass `MovementRuntime.appliesTo`. The distance wrapper additionally requires that context recipient identity match its target entity. Other recipients retain their original distance and call order.
- The response still routes through native `moveEntityByPiston`, so piston noClip setup, `Entity.move`, callback processing and movement-record cleanup remain in the native call path. The existing callback-scan-origin `@ModifyArg` remains in this same mixin.
- The client mixin is registered once in `src/client/resources/legacyparkourcompat.client.mixins.json` (line 51), which is client-only in `fabric.mod.json`. The product's common `legacyparkourcompat.mixins.json` does not receive a duplicate entry. The new `PistonStairPushHook` is independently typed and discovered; no duplicate piston-stair hook exists in the integration base.
- Resolver direction is correct for the assigned scope: the independently accepted 1.9 aliases resolve to the `V1_10_1` historical change, while the `V1_8_9` native barrier preserves the separately unimplemented 1.8.9 behavior. This review does not claim 1.8.9 parity or expand the assigned boundary.
- Existing `PistonMovement` and `SneakEdgeMover` adaptations remain separate. The wrapped call preserves the native PISTON mover and does not replace movement with position writes; existing per-operation limiter and edge hooks continue to compose. Their broader runtime parity is not reassessed here.

## Limits

This is a static review of the cited integration commit and exact local 26.2 compiled target bytecode. It does not establish runtime parity. No code was edited; no build, tests or game launch were run.
