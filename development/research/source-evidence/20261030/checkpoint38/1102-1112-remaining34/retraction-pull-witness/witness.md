# Retraction follow-up-pull witness: 1.10.2 → 1.11.2

## Verdict

A paired source-level local-player response witness exists for the first source-retraction half-step. It meets the new 1.11.2 gates in sequence: the primary response has raw `e > 0`; its solver-clipped result leaves the player intersecting the moving-block cell's full cube; the helper's two opposing clearances are equal; and the second PISTON move executes. On the same response-entry AABB, 1.10.2 instead makes one untyped westward move and has no follow-up pull.

The coordinates below are a source-sequence construction, not a game run. The response entry is locally collision-compatible with the ordinary extended head in both endpoints. A preceding upward local-player move of `+0.05` Y is preserved by both endpoint solvers and puts the player at the stated response-entry height. The exact earlier control/velocity history that produces that particular upward request is not traced here, so this closes the concrete response geometry and call gates, not a full player-input trajectory or runtime parity claim.

## Bound source evidence

Canonical read-only source roots are `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{1.10.2,1.11.2}/ornithe-feather`, mapping `ornithe-feather`. The source manifests are bound in the closure at 1.10.2 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` and 1.11.2 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`. I rehashed the directly consulted files from those roots:

| File | 1.10.2 SHA-256 | 1.11.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/block/entity/MovingBlockEntity.java` | `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1` | `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e` |
| `net/minecraft/block/PistonBaseBlock.java` | `4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36` | `3a055f33949305827c3159bb5fbddf8bf51d0ec942f1b30f1fa59ff1bab9be64` |
| `net/minecraft/block/PistonHeadBlock.java` | `9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579` | `da30307abf1e88698a220ab3b61538be9b2f5f16d6648c8f41a2671dc06b7ed9` |
| `net/minecraft/block/MovingBlock.java` | `4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6` | `f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18` |
| `net/minecraft/util/math/Box.java` | `f529ef075bd933b88e3bace9020a5e23cb18b65e816154520f10bd2f26f61155` | `f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58` |

The authorized source-only inputs were `.native-source/20261030/1102-1112-remaining34/moving-block-algorithm/closure.md` and `moving-block-algorithm-audit/audit.md`; the `piston-response-closure/report.md` and `solver-route-closure/report.md` inputs under `1102-1112-pool31`; and `piston-retraction-witness/report.md` plus `correction-supplement.md` under `1102-1112-pool31`. The audit's prior open gate is specifically the absence of a paired trace through primary movement, cell intersection, clearance equality, and the second call; the corrected piston-head supplement rules out transferring its separate progress-1 SHORT-arm result to this question.

## Client route and concrete response-entry state

Use an east-facing sticky piston at base position `P=(0,0,0)`, with no solid blocks on the player's westward travel path. Its source retraction creates a `MovingBlockEntity` at `P` with the extended piston-base moved state, `facing=EAST`, `extending=false`, and `source=true` (1.10.2 `PistonBaseBlock.java:172–182`; 1.11.2 `:172–186`). At ordinary source-retraction progress 0, `getAmountExtended(0)=1`. The 1.11.2 player/entity phase precedes block-entity ticks (`World.java:1271–1325`; the paired 1.10.2 ordering is `:1270–1325`), so the following player AABB is used at the BE response call after the player tick.

Set the ordinary local player's dimensions to `0.6 × 1.8` (1.10.2 `PlayerEntity.java:399`; 1.11.2 `:403`) and use the response-entry AABB, rounded to displayed decimal endpoints:

```text
x = [1.01, 1.61], y = [0.75, 2.55], z = [0.20, 0.80]
```

For a concrete preceding player-phase check, start at the same X/Z and `y=[0.70,2.50]` airborne (`onGround=false`), with an upward local `SELF` request of `+0.05` Y. In 1.10.2, the progress-0 moving-block collision box is the extended base at offset 1, `[1,1.75]×[0,1]×[0,1]`; the player's Y-positive clip condition is false because its `maxY` is above the obstacle's `minY` (`Entity.java:446–537`, `Box.java:163–177`). In 1.11.2 the source head arm ends at `y=0.625`, the plate does not overlap this X interval, and the source base ends at `x=0.75`; the same `+0.05` is unclipped (`Entity.java:459–598`). The two response-entry AABBs therefore agree. This is a collision-compatible local-player call state; its exact prior control history is outside this bounded trace.

This placement is clear of the *stationary* east piston head before retraction: at head-block position `(1,0,0)`, both endpoints' EAST arm is world `x=[0.75,1.75], y=[0.375,0.625], z=[0.375,0.625]`, and the plate is `x=[1.75,2], y/z=[0,1]` (`PistonHeadBlock.java:25,35` in 1.11.2; old shape/arm definitions and selector at `:64–85`). The player's minimum Y is above the arm maximum, and its maximum X is before the plate minimum. It also clears the extended base at `P`, whose EAST shape is `x=[0,0.75], y/z=[0,1]` (`PistonBaseBlock.java:28–68`). The upward move state is airborne; a `+0.05` Y request does not depend on sneak-edge or grounded-step behavior. The local player has the ordinary `NORMAL` piston behavior (base `Entity.getPistonMoveBehavior`, old `:2306–2308`, new `:2412–2414`; `PlayerEntity` does not replace it). Its local class enters the ordinary superclass tick on the loaded path (old `LocalClientPlayerEntity.java:152–154`; new `:159–161`).

## 1.11.2 primary push and follow-up pull

At stored progress 0, 1.11.2 `moveEntities(f)` uses `f=0.5`, with `d=f-this.progress=0.5` while stored progress remains 0 (`MovingBlockEntity.java:116–123,273–289`). The source direct-response selector is the default full-arm piston-head state (`:100–114`), not the collision collector's progress-selected SHORT state. Its EAST plate at the base-relative origin is `[0.75,1]×[0,1]×[0,1]` (`PistonHeadBlock.java:25,71–74`). `moveBox` translates it by `+1` to world X `[1.75,2]`; the WEST swept area for the half-step is `[1.25,1.75]×[0,1]×[0,1]` (`MovingBlockEntity.java:203–229`). The response-entry player box intersects this area. The WEST clearance is:

```text
e = player.maxX - swept.minX = 1.61 - 1.25 = 0.36 > 0
primary requested distance = min(e, d) + 0.01 = 0.37 west
```

The call is made as `MoverType.PISTON` (`MovingBlockEntity.java:143–164`). For a tick with no earlier PISTON move for this player, the axis accumulator is zero; the mover-type budget admits `-0.37` before ordinary clipping, and the 1.11.2 PISTON cap and call accounting are in `Entity.java:464–499`. During that primary call `NOCLIP_DIR=WEST`. The moving-block collision provider adds the extended base shape at `P`, `[0,0.75]×[0,1]×[0,1]`, and omits its head boxes for this retracting direction while progress is below 1 (`MovingBlockEntity.java:318–345`; `MovingBlock.java:135–139`; `PistonBaseBlock.java:29–68`). The player's Y/Z intervals overlap the base and its starting `minX=1.01` is east of `base.maxX=0.75`, so `Entity.move` clips the westward request to `0.75-1.01=-0.26` (`Entity.java:576–585`; `Box.java:146–160`). Immediately after this primary move its X interval is `[0.75,1.35]`; Y/Z are unchanged. This post-primary box strictly intersects the block-entity full cell `[0,1]^3`.

The caller now enters `moveEntity(entity, WEST, 0.5)` because `!extending && source` (`MovingBlockEntity.java:156–164`). For the full cell and post-primary box, both clearances use EAST:

```text
d = 1 - 0.75 + 0.01 = 0.26
e = 1 - intersection.minX(0.75) + 0.01 = 0.26
abs(d - e) = 0 < 0.01
second requested distance = min(0.26, 0.5) + 0.01 = 0.27 east
```

The second PISTON move is not suppressed by the accumulated cap: the first request recorded `-0.37`; adding `+0.27` yields `-0.10`, still in the `[-0.51,0.51]` bound, so the call's full `+0.27` delta remains (`Entity.java:471–478`). It moves away from the base's east face and is not clipped. With auto-jump disabled for this local player state, the `LocalClientPlayerEntity.move` wrapper adds no secondary jump (`:823–838`). The post-pull X interval is `[1.02,1.62]`. Thus the first push leaves the player cell-intersecting and satisfies the equality gate; the source invokes and completes the opposite-direction pull.

## Paired 1.10.2 response from the same response-entry AABB

The old tick advances stored progress `0→0.5` before response (`MovingBlockEntity.java:166–184`). Its `getShape` always uses `movedState`, which is the extended east piston-base shape `[0,0.75]×[0,1]×[0,1]`; the current and previous offsets are `+0.5` and `+1` (`:76–89`; `PistonBaseBlock.java:28–68`). Their union at `P` is `[0.5,1.75]×[0,1]×[0,1]`. The same response-entry AABB intersects it. With direction WEST, old `moveEntities()` requests:

```text
distance = player.maxX - union.minX + 0.01
         = 1.61 - 0.50 + 0.01 = 1.12 west
```

The old path calls untyped `entity.move(...)` and has no PISTON cap or follow-up helper (`MovingBlockEntity.java:91–147`). During this move the old MBE collision box is the same `[0.5,1.75]` union. The player starts already intersecting it, so the negative-X clip precondition `player.minX >= obstacle.maxX` is false (`Box.java:143–160`); with a clear westward route the full `-1.12` applies. Its X interval ends at approximately `[-0.11,0.49]`. No second move follows. The paired response therefore differs in both request and final X: 1.11.2 performs west `0.26` after base clipping, then east `0.27`; 1.10.2 performs west `1.12` and stops.

## Scope and method

This closes the paired primary-push/follow-up-helper call gates for the stated response-entry AABB and reports the coordinate response at that call. It does not infer a general retraction trajectory, establish exact production of the upward `+0.05` from prior controls, cover the separate old progress-1 extra response pass, establish an intermediate patch boundary, freeze the pair, or claim runtime parity. Local-client world/entity/BE ordering and caller reachability are source facts (`World.java` above); the typed local-player wrapper's conditional auto-jump was explicitly neutralized by selecting its disabled setting.

Static exact-source inspection and sequence arithmetic only. No wiki, product source/history, backlog, tests, build, game launch, simulation, decompilation, or canonical-source write was used.
