# Correction review: piston stair push candidate 38

**Verdict: ACCEPT, bounded to the assigned 1.10.1/1.10-family operation.**
**Reviewed candidate:** `.native-candidates/piston-stair-push38`, commit `c286cb193d797362222c507b143884a281ec212f` (after `1f8793403de80bef5835db280276f53443f90471`).
**Supersedes:** the `REQUEST CHANGES` verdict in sibling `review.md` where this correction explicitly retracts findings below.

## Correction to the prior review

The prior review incorrectly treated the `V1_9` native barrier as a blocker. That barrier was part of the assigned bounded scope: preserve fallback for unexamined older profiles pending the separate alias audit. This review does not expand the candidate into 1.9 or 1.8.9, and makes no claim that those profiles are covered. Their alias work remains independent.

The previous code defects are corrected in `c286cb1`: the two callback injections now use distinct Java method names, and the response accumulator is seeded before the per-stair-box loop rather than modified at a comparison jump.

## Compiled target evidence

I inspected the existing local cached merged deobfuscated 26.2 JAR read-only:

`C:\Users\Wolfi\.gradle\caches\fabric-loom\minecraftMaven\net\minecraft\minecraft-merged-deobf\26.2\minecraft-merged-deobf-26.2.jar`
SHA-256: `0468742d98fe5ec68508493ef2155c6e5209f991055bc0c17ddf26872eb0d110`.

`javap -p -s` confirms the compiled target descriptors:

- `moveCollidedEntities(Level, BlockPos, float, PistonMovingBlockEntity): void` — `(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;FLnet/minecraft/world/level/block/piston/PistonMovingBlockEntity;)V`
- `moveEntityByPiston(Direction, Entity, double, Direction): void` — `(Lnet/minecraft/core/Direction;Lnet/minecraft/world/entity/Entity;DLnet/minecraft/core/Direction;)V`

`javap -p -c -l` confirms `moveCollidedEntities` initializes `delta` in local slot 14 with `DSTORE 14` at bytecode offset 266, then calls `List.iterator()` for the `shapeAabbs` loop at offsets 268–270. This is the second `List.iterator()` invocation in that method; the local-variable table marks `delta` in slot 14 from offset 268. The corrected `@ModifyVariable` binding at `INVOKE List.iterator ordinal = 1`, `index = 14` therefore targets the initialized accumulator immediately before the per-box loop. The real native comparison later loads the accumulator and evaluates `DCMPG`/`IFGT` at offsets 365–375; the former jump injection was both the wrong opcode and too late to change the already-computed comparison.

## Static behavior review

- For an eligible local player and the registered cobblestone-stair hook, the candidate seeds local `delta` with the positive historical clearance before the modern per-box loop and stores that clearance in `LEGACY_PISTON_CLEARANCE`. The modern loop may leave the accumulator unchanged or raise it through `Math.max`; this cannot alter the final requested amount because the one native `Math.min(DD)D` call redirects to the saved historical clearance. Native `+0.01` is still applied afterward. Any native `delta >= deltaProgress` break only skips further pure AABB intersection/clearance arithmetic; it does not skip a movement or callback within that loop.
- For the accepted 1.10.2 bottom-straight-stair witness, the hook returns unpadded clearance `1 - 0.65 = 0.35`; the native offset yields the old `+0.36 X` response. The hook’s old-outline intersection predicate uses the strict AABB intersection gate, matching the source evidence.
- If the historical unit-outline union does not intersect the recipient, the hook returns zero and does not set the saved-clearance override. The modern 26.2 stair collision boxes are subsets of the unit block: `StairBlock.java:37–45` constructs the base and upper portions within the local `[0,16]` coordinates and rotates them within the block. The moving-piston swept AABBs are those boxes translated over the same directional progress interval; each is therefore contained in the historical swept unit-outline union. No modern per-box intersection can then create a positive delta, so the native no-push result is preserved.
- The capture redirect calls `getPistonPushReaction()` exactly once, returns its native value, and records a recipient only if the reaction is not `IGNORE`, the entity is the client’s local player, and `MovementRuntime.appliesTo(entity)` is true. This preserves native recipient enumeration/order and leaves other recipients on their native branch. The candidate mixin is client-listed, so it does not install on the logical server.
- The hook is keyed to `minecraft:cobblestone_stairs`, the accepted semantic identity for the historical cobblestone-backed `stone_stairs`. The operation rejects retraction and only changes the local-player response accumulator; it does not replace world entity enumeration, block states, the full piston loop, or world stair shapes.
- The native `moveEntityByPiston` invocation remains in place. Thus the native PISTON/noClip setup, `Entity.move`, effect callback invocation, movement-record cleanup, and other product movement adaptations continue to compose. This review does not establish full equivalence of all callback, edge, limiter, or noClip behavior.

## Product integration constraints and limits

The product checkout already has `PistonMovingBlockEntityMixin` at the same package/name for the callback scan-origin adapter. Integration must merge the new response hooks into that class and retain the existing `@ModifyArg`; it must not add a second class. Product `legacyparkourcompat.mixins.json` currently has no `PistonMovingBlockEntityMixin` entry, so register the merged mixin in its client list. No duplicate `PistonStairPushHook` operation was found in the current product sources; the existing `PistonMovementHook` is a separate movement-input limiter adaptation and must remain independently resolved.

The resolver's closest-applicable direction is consistent with the candidate's `V1_10_1` historical hook and assigned lower `V1_9` native barrier. This acceptance is only for the bounded assigned range and its accepted `+0.36` witness. No build, test, game launch, or runtime-parity verification was performed; exact patch applicability outside the accepted evidence remains open.
