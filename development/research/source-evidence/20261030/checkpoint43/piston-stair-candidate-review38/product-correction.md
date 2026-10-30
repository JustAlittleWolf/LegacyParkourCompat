# Product integration correction review: piston stair push

**Verdict: ACCEPT, bounded to the assigned source-supported 1.9–1.10.2 stair response and 26.2 implementation seam.**
**Reviewed integration:** `.native-integrations/piston-product40`, commit `cc338efd3894c90bec6731e72e8f8c897178e283`, atop `0210138581ec2c89515817974962489f5dd2e15c`.
**Supersedes:** the `REQUEST CHANGES` in sibling `product-review.md` for the missing positive-gate seed.

## Gate, cache and final native request

The prior product review correctly found that wrapping only `moveEntityByPiston` could not create the call for the accepted stair witness. This commit now adds `@ModifyVariable` at the second `List.iterator()` call with `index = 14` and `require = 1` in `PistonMovingBlockEntityMixin.java:74–80`. The target method descriptor is explicit. The local cached 26.2 merged deobfuscated JAR reviewed earlier (SHA-256 `0468742d98fe5ec68508493ef2155c6e5209f991055bc0c17ddf26872eb0d110`) confirms this is the `shapeAabbs` loop invocation: local slot 14 is initialized by `DSTORE 14` at offset 266; the second iterator invokes at offset 270; and the local variable table has `delta` at slot 14 beginning at offset 268. This seeds the initialized accumulator immediately before the per-box loop and before the actual `DLOAD 14`/`DCMPG`/`IFGT` no-push gate at offsets 365–375.

For a positive old request, `Double.MIN_VALUE` guarantees that the native positive branch executes even when modern stair boxes produce no intersections. The request hook result is cached in the response context at lines 93–111. Native `Math.max` processing and the cap still run; then `WrapOperation` replaces only the distance argument at the native `moveEntityByPiston` call with the cached historical final request (`PistonMovingBlockEntityMixin.java:114–127`). Thus the accepted bottom-straight stair witness, which has no native per-box intersection, reaches the call and requests the old `clearance + 0.01 = +0.36 X`.

For an old-outline nonintersection, the hook returns present zero at `CobblestoneStairPush.java:46–50`, leaving the seed at zero. The 26.2 stair collision boxes are all built within the local `[0,16]` block coordinates and rotated within the block (`StairBlock.java:37–45`). `PistonMath.getMovementArea` moves each translated box from its face by the progress delta; unioning that with the original box keeps every modern swept box within the old unit-outline swept union. Therefore no modern per-box intersection can raise the accumulator when the old unit union does not intersect the entity. The no-push gate remains native in that case; no ghost push is introduced.

The movement-direction formulas and final padding are consistent: the hook computes the old union's directional edge clearance and adds `0.01` once (`CobblestoneStairPush.java:38–61`). The wrap operation supplies this final distance after native `Math.min(...)+0.01`; it does not double-add the offset. Even if native `Math.max` raises its accumulator above the sentinel or its loop exits early at the native cap condition, the final wrapped request is the cached old distance.

## Scope and composition

- `getPistonPushReaction()` is called once and its result reused. Only a non-`IGNORE` entity identical to `Minecraft.getInstance().player` with `MovementRuntime.appliesTo(entity)` is recorded (`PistonMovingBlockEntityMixin.java:58–71`). The distance wrapper additionally checks identity against that recipient; all other recipients retain the original native call and ordering.
- The wrapper changes only the scalar argument to the original `moveEntityByPiston`. Native NOCLIP setup, `Entity.move`, effect callback, and movement-record cleanup remain in that method. The existing callback scan-origin `@ModifyArg` remains present at lines 129–141. Existing piston limiter and sneak-edge operations are separate product hooks and remain in the same movement pipeline; this change does not write position directly or replace a piston loop.
- The `WrapMethod` stores prior context and restores it in `finally` (`PistonMovingBlockEntityMixin.java:42–56`), so normal return, exception and nested invocations restore the outer context. `withRecipient` clears the cached distance for every newly visited recipient; nested `moveCollidedEntities` calls save/restore the complete immutable record.
- The mixin remains registered once in the client resource config; `fabric.mod.json` marks that config client-only. The callback-origin adapter stays in the same mixin. `PistonStairPushHook` remains one separately discovered operation, with the exact `minecraft:cobblestone_stairs` semantic key.
- The closest-applicable resolver and the current `V1_8_9` native barrier match the assigned accepted boundary: 1.9 aliases select the `V1_10_1` rule; the distinct 1.8.9 `+0.5625` rule is explicitly not implemented here. This review does not extend applicability beyond the assigned accepted reports.

## Limits

This is static implementation review against the committed source and existing compiled 26.2 target class. It does not establish runtime parity or complete pair coverage. No build, tests, game launch or source generation was performed.
