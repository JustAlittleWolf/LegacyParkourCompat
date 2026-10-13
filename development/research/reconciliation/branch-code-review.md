# Independent review of adapted incoming movement changes

Decision: **ACCEPT**, bounded to the two operations and their static integration in the current `fix/workflow-and-client-architecture` tree. This reviews the adapted code rather than automatically transferring the earlier candidate verdict. No substantive blocker remains in the inspected patch.

## Auto-jump collision order

The incoming candidate is `fix/autojump-order-2026-10-10` at `2e9a155d892bb8d5aeaa8d1fe5800fd643a1ad38`; its historical code review is the descendant `8b9411cc145c2a7bcbc6f3bf4178876c3fa0f3e5`, accepting candidate code `13270f3536d134fe4fe5e2eb2631f00db8f598b1`. Source acceptance is `49d8a4a4a809ed7e4342640333b98e226bc0e0d0` for snapshot `a5870a155106dbdf2a45010b267ae146c7735a8b`. Exact 1.17.1/1.18/1.18.1/1.18.2 source checks and that independent closure establish the first changed stable release as **1.18**.

The historical review incorrectly identified the invocation owner as `CollisionGetter`. Independent `javap -c -p` inspection of the current 26.2 Loom client-only JAR shows `LocalPlayer.updateAutoJump(FF)V` at bytecode offset 544 invokes:

```text
invokevirtual net/minecraft/world/level/Level.getCollisions:
(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Ljava/lang/Iterable;
```

The adapted `LocalPlayerMixin` targets this exact `Level` owner and uses a `Level` receiver. There is one matching call in `updateAutoJump`; `require = 1` is retained. `AutoJumpCollisionOrderHook` supplies block candidates before entity candidates using the same player and query AABB. The unchanged native iterator flattens shapes to AABBs and stops at its first intersecting candidate, preserving the accepted finding-specific ordering consequence. With no applicable hook, the wrapper calls the original operation exactly once with its original receiver and arguments.

`MovementChangeCatalog` registers this single hook at `V1_17_1`. Closest-change resolution therefore applies the old ordering through that profile and retains native ordering on/after the accepted 1.18 boundary. This is an ordering delta, not a claim that every current provider/filter reproduces all earlier versions.

## Retracting source-piston head shape

The incoming candidate is `fix/piston-head-shape-2026-10-10` at `ceb39918e9510b03347b8d573a8fc9040e0401f6`. Independent source acceptance binds snapshot `58e56ee59d7cc4015d499e3e2a37ec1d45049d56`; independent boundary supplement `005d5338f9a944f71651b8010ac90f73e1cf9d2c` establishes the first changed stable release as **1.16**. That boundary supplement is not an ancestor of the code candidate and must retain its separate evidence identity. No earlier independent implementation acceptance was located for `ceb39918`; this report reviews the adapted implementation.

Exact source comparison: 1.15.2 Mojmap `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java`, `getCollisionShape`, lines 333–361; 1.16 Mojmap same file/member, lines 319–347; current 26.2 unobfuscated same file/member, lines 366–394. The manifest-matched 1.15.2 file SHA-256 is `43ef9a249446cac093eb1f7555bd55c4db5416474fa1668772f0f1257672e57a`; the older prose transcribes a different digest. This correction identifies actual source bytes without changing any immutable historical report.

The adapted Change keeps the existing four-argument `BlockCollisionShapeHook` contract. It replaces the candidate's indirect native-shape identity check and interpolated `getProgress(1.0F)` read with native raw state accessors. Static inspection of the current common JAR confirms instance field `progress` has descriptor `F`, static field `NOCLIP` has descriptor `Ljava/lang/ThreadLocal;`, and public `getMovementDirection()` has descriptor `()Lnet/minecraft/core/Direction;`.

For a retracting source piston with a piston-base moved state, the Change returns `Optional.empty()` when `progress < 1.0 && NOCLIP.get() == piston.getMovementDirection()`. The collision hook runs after the native query; this empty result preserves the native matching-NOCLIP early return. Outside that gate, the exact old expression `isExtending() != 1.0F - progress < 4.0F` selects the head shape. Java evaluates the relational comparison before boolean `!=`. The subtraction remains float; each direction integer times that float is evaluated before widening to double. The extended-base shape precedes the offset head shape in `Shapes.or`, as in the source. Extending, non-source and non-piston-base cases preserve native behavior.

The catalog registers the `minecraft:moving_piston` shape variant once at `V1_15_2`, consistent with the accepted 1.16 cutover. This bounded threshold delta does not establish parity for every earlier moving-piston provider or dynamic piston response.

## Logical-side and verification limits

`LocalPlayerMixin`, `BlockStateCollisionShapeMixin`, and the new `PistonMovingBlockEntityAccessor` are registered only in the client mixin list. `MovementRuntime.find` also requires an enabled client-side player, so native/current profiles, non-player queries and logical servers, including integrated servers, retain native behavior. The piston accessor reads state without mutation and fails visibly if its static transformation is absent.

Checks performed: incoming/current diff inspection, exact historical/current source bodies, accepted source and boundary reports, current vanilla JAR invocation/field descriptors, native fallback, operation arithmetic/order, hook granularity, catalog registration and logical-side gates. No edits to product code, compilation, tests, decompilation, client/server/TAS/Gym launches or runtime verification were performed by this reviewer. Compilation and actual Mixin application remain separate integration checks; this ACCEPT does not establish runtime parity or a measured trajectory.
